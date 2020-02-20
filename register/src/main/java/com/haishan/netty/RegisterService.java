package com.haishan.netty;


import api.RpcModel;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.serialization.ClassResolver;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import io.netty.util.concurrent.*;

import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class RegisterService {
    private int port = 8080;
    private static Map<String,List<Map>> mapping = new LinkedHashMap<>();
    //1启动监听端口
    public void start(){
        ServerBootstrap bootstrap = new ServerBootstrap();
        EventLoopGroup boss = new NioEventLoopGroup();
        EventLoopGroup work = new NioEventLoopGroup();
        bootstrap.group(boss,work)
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                       ChannelPipeline pipeline = socketChannel.pipeline();
                       pipeline.addLast(new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE,0,4,0,4));
                       pipeline.addLast(new LengthFieldPrepender(4));
                       pipeline.addLast("encoder",new ObjectEncoder());
                       pipeline.addLast("decoder",new ObjectDecoder(Integer.MAX_VALUE, ClassResolvers.cacheDisabled(null)));
                       pipeline.addLast(new RegisterHanlder(mapping));
                    }
                }).option(ChannelOption.SO_BACKLOG, 128)
                // 针对子线程的配置 保持长连接
                .childOption(ChannelOption.SO_KEEPALIVE, true);
        try {
            ChannelFuture channelFuture = bootstrap.bind(this.port).sync();
            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    class RegisterHanlder extends ChannelInboundHandlerAdapter {
        private Map<String, List<Map>> mapping;
        public RegisterHanlder(Map<String, List<Map>> mapping) {
            this.mapping = mapping;
        }

        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
            System.out.println(msg.toString());
            if(msg instanceof RpcModel) {
                RpcModel rpcModel = (RpcModel) msg;
                Service service = new Service(ctx,rpcModel,mapping);
                service.register();
                if(!rpcModel.getServer()){
                    service.invork();
                }else {
                    ctx.write("服务注册成功");
                }
                ctx.flush();
                ctx.close();
            }
        }

        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
            super.exceptionCaught(ctx,cause);
            System.out.println("register is Exection");
        }
    }
}
