package com.tomcat.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class Tomcat {

    private int prot = 8080;
    private Map<String,Servlet> mapping = new HashMap<>();
    private Properties properties = new Properties();

    //初始化web.xml 初始化servletMapping 对象
    public void init(){
        String pa = this.getClass().getResource("/").getPath();
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream(new File(pa+"web.properties"));
            properties.load(fileInputStream);
        for (Object k : properties.keySet()) {
            String key = k.toString();
            if (key.endsWith(".url")) {
                String sevletName = key.replaceAll("\\.url$", "");
                String url = properties.getProperty(key);
                String className = properties.getProperty(sevletName + ".classname");
                Servlet servlet = (Servlet) Class.forName(className).newInstance();
                mapping.put(url, servlet);
            }
        }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }finally {
            if(fileInputStream!=null) {
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
            //建立监听事件
            EventLoopGroup boss = new NioEventLoopGroup();
            EventLoopGroup work = new NioEventLoopGroup();

            try {
                ServerBootstrap server = new ServerBootstrap();
                server.group(boss,work)
                        .channel(NioServerSocketChannel.class)
                        .childHandler(new ChannelInitializer<SocketChannel>() {
                            @Override
                            protected void initChannel(SocketChannel socketChannel) throws Exception {
                                socketChannel.pipeline().addLast(new HttpResponseEncoder());
                                socketChannel.pipeline().addLast(new HttpRequestDecoder());
                                socketChannel.pipeline().addLast(new TomcatHandler());
                            }
                        })// 针对主线程的配置 分配线程最大数量 128
                        .option(ChannelOption.SO_BACKLOG, 128)
                        // 针对子线程的配置 保持长连接
                        .childOption(ChannelOption.SO_KEEPALIVE, true);

                ChannelFuture f = server.bind(prot).sync();
                f.channel().closeFuture().sync();
            }catch (Exception e){
                e.printStackTrace();
            }
            finally {
                boss.shutdownGracefully();
                work.shutdownGracefully();
            }
    }
    class TomcatHandler extends ChannelInboundHandlerAdapter {
        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
            if(msg instanceof HttpRequest){
                HttpRequest req = (HttpRequest) msg;
                MyHttpRequest myHttpRequest = new MyHttpRequest(ctx,req);
                MyHttpRespont myHttpRespont = new MyHttpRespont(ctx,req);
                String url = myHttpRequest.getUrl();
                if(mapping.containsKey(url)){
                    mapping.get(url).service(myHttpRequest, myHttpRespont);
                }else {
                    myHttpRespont.write("404 - Not Found");
                }
            }
        }

        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        }
    }
}
