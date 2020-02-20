package service;

import api.RpcModel;
import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.lang.reflect.Method;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.*;

public class Service implements ApplicationContextAware, InitializingBean {

    private  int port = 8081;
    private static Map<String, Object> map = new HashMap<>();


    @Override
    public void afterPropertiesSet() throws Exception {

    }
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
       Map<String,Object> map1 = applicationContext.getBeansWithAnnotation(RpcService.class);
        if(!map1.isEmpty()){
            for(Object service:map1.values()){
                RpcService rpcService =service.getClass().getAnnotation(RpcService.class);
                map.put(rpcService.value().getName(),service);
            }
        }
        init();
        invork();
    }

    //开放监听端口
    private void invork() {
        EventLoopGroup boss = new NioEventLoopGroup();
        EventLoopGroup worker = new NioEventLoopGroup();
        ServerBootstrap serverBootstrap = new ServerBootstrap();
        serverBootstrap.group(boss,worker)
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        ChannelPipeline pipeline = socketChannel.pipeline();
                        pipeline.addLast(new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE,0,4,0,4));
                        pipeline.addLast(new LengthFieldPrepender(4));
                        pipeline.addLast("encoder",new ObjectEncoder());
                        pipeline.addLast("decoder",new ObjectDecoder(Integer.MAX_VALUE, ClassResolvers.cacheDisabled(null)));
                        pipeline.addLast(new RegisterHanlder(map));
                    }
                }).option(ChannelOption.SO_BACKLOG, 128)
                // 针对子线程的配置 保持长连接
                .childOption(ChannelOption.SO_KEEPALIVE, true);
        try {
            ChannelFuture future = serverBootstrap.bind(port).sync();
            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    //注册服务
    public void init() {
        RpcModel rpcModel = new RpcModel();
        final ClientHanlder hanlder = new ClientHanlder();
        EventLoopGroup client = new NioEventLoopGroup();
        try {
            rpcModel.setIp(getLocalIp4AddressFromNetworkInterface().get(0).getHostAddress());
            rpcModel.setPort(port);
            rpcModel.setServerName("rpc-service");
            rpcModel.setServer(true);
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(client)
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY, true)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            ChannelPipeline pipeline = socketChannel.pipeline();
                            pipeline.addLast(new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 0, 4, 0, 4));
                            pipeline.addLast(new LengthFieldPrepender(4));
                            pipeline.addLast("encoder", new ObjectEncoder());
                            pipeline.addLast("decoder", new ObjectDecoder(Integer.MAX_VALUE, ClassResolvers.cacheDisabled(null)));
                            pipeline.addLast(hanlder);
                        }
                    });
            ChannelFuture channelFuture = bootstrap.connect("127.0.0.1",8080).sync();
            channelFuture.channel().writeAndFlush(rpcModel).sync();
            channelFuture.channel().closeFuture().sync();

        } catch (SocketException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            client.shutdownGracefully();
        }
        System.out.println(hanlder.getResult().toString());
    }

    //获取本机ip
    public static List<Inet4Address> getLocalIp4AddressFromNetworkInterface() throws SocketException {
        List<Inet4Address> addresses = new ArrayList<>(1);
        Enumeration e = NetworkInterface.getNetworkInterfaces();
        if (e == null) {
            return addresses;
        }
        while (e.hasMoreElements()) {
            NetworkInterface n = (NetworkInterface) e.nextElement();
            if (!isValidInterface(n)) {
                continue;
            }
            Enumeration ee = n.getInetAddresses();
            while (ee.hasMoreElements()) {
                InetAddress i = (InetAddress) ee.nextElement();
                if (isValidAddress(i)) {
                    addresses.add((Inet4Address) i);
                }
            }
        }
        return addresses;
    }
    private static boolean isValidInterface(NetworkInterface ni) throws SocketException {
        return !ni.isLoopback() && !ni.isPointToPoint() && ni.isUp() && !ni.isVirtual()
                && (ni.getName().startsWith("eth") || ni.getName().startsWith("ens"));
    }
    private static boolean isValidAddress(InetAddress address) {
        return address instanceof Inet4Address && address.isSiteLocalAddress() && !address.isLoopbackAddress();
    }



    //netty返回监听事件
    private class ClientHanlder extends ChannelInboundHandlerAdapter {
        private Object res;
        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
            this.res = msg;
        }

        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
            System.out.println("service is Exception");
        }

        public Object getResult(){
            return res;
        }
    }

    private class RegisterHanlder  extends ChannelInboundHandlerAdapter {
        public Map<String, Object> map;
        public RegisterHanlder(Map<String, Object> map) {
            this.map = map;
        }

        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
            if(msg instanceof RpcModel){
                RpcModel rpcModel = (RpcModel) msg;
                if(map.containsKey(rpcModel.getClassName())){
                    Object service = map.get(rpcModel.getClassName());
                    Class clazz = Class.forName(rpcModel.getClassName());
                    Method method = clazz.getMethod(rpcModel.getMethodName(),rpcModel.getType());
                    ctx.write(method.invoke(service,rpcModel.getParam()));
                }else {
                    ctx.write("class is Exception");
                }
            }else {
                ctx.write("model is Exception");
            }
            ctx.flush();
            ctx.close();
        }

        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
            System.out.println("service is Exception");
        }
    }
}
