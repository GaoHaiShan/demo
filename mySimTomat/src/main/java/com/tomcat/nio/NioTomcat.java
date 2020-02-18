package com.tomcat.nio;

import com.tomcat.IHttpRequest;
import com.tomcat.IHttpRespont;
import com.tomcat.netty.Servlet;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.*;

public class NioTomcat {

    private int port = 8080;
    private Properties properties = new Properties();
    private Map<String, Servlet> mapping = new HashMap<>();
    private Selector selector;
    private ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
    public void init(){
        FileInputStream fileInputStream = null;
        try {
            String pa = this.getClass().getResource("/").getPath();
            fileInputStream = new FileInputStream(new File(pa+"web.properties"));
            properties.load(fileInputStream);
            for(Object k:properties.keySet()){
                String key = k.toString();
                if(key.endsWith(".url")){
                    String url = properties.getProperty(key);
                    String pex = key.replaceAll("\\.url$","");
                    String classname = properties.getProperty(pex+".classname");
                    Servlet servlet = (Servlet) Class.forName(classname).newInstance();
                    mapping.put(url,servlet);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        finally {
            if(fileInputStream!=null){
                try {
                    fileInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void start(){
        init();
        try{
           ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
            selector = Selector.open();
            serverSocketChannel.bind(new InetSocketAddress(this.port));
            serverSocketChannel.configureBlocking(false);
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
            listen();
            System.out.println("返回1");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void listen() {
        try {
            while (true){
                selector.select();
                Set<SelectionKey> keys= selector.selectedKeys();
                Iterator<SelectionKey> iter = keys.iterator();
                while (iter.hasNext()){
                    SelectionKey one = iter.next();
                    iter.remove();
                    proess(one);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void proess(SelectionKey one) throws IOException {
        if(one.isAcceptable()){
            ServerSocketChannel serverSocketChannel = (ServerSocketChannel) one.channel();
            SocketChannel socketChannel = serverSocketChannel.accept();
            socketChannel.configureBlocking(false);
            socketChannel.register(selector,SelectionKey.OP_READ);
        }
        else if(one.isReadable()){
            SocketChannel socketChannel = (SocketChannel) one.channel();
            int len = socketChannel.read(byteBuffer);
            if(len > 0){
                byteBuffer.flip();
                String content = new String(byteBuffer.array(),0,len);
                String model = content.split("\n")[0];
                one = socketChannel.register(selector,SelectionKey.OP_WRITE);
                one.attach(model);
            }
        }else if(one.isWritable()){
            SocketChannel socketChannel = (SocketChannel) one.channel();
            String model = (String) one.attachment();
            String url = model.split(" ")[1];
            String type = model.split(" ")[0];
            IHttpRequest request = new MyHttpRequest(type,url);
            IHttpRespont respont = new MyHttpRespont(socketChannel);
            if(mapping.containsKey(url)){
                mapping.get(url).service(request,respont);
            }else {
                IHttpRespont respont1 = new MyHttpRespont(socketChannel);
                respont1.write("404 - notfound");
            }
            socketChannel.close();
        }
    }
}
