package com.haishan.netty;

import api.RpcModel;
import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import io.netty.util.concurrent.EventExecutorGroup;

import java.util.*;

public class Service {
    private ChannelHandlerContext ctx;
    private RpcModel rpcModel;
    private Map<String, List<Map>> mapping;

    public Service(ChannelHandlerContext ctx, RpcModel rpcModel, Map<String, List<Map>> mapping) {
        this.ctx = ctx;
        this.rpcModel = rpcModel;
        this.mapping = mapping;
    }

    public void register(){
        if(mapping.containsKey(rpcModel.getServerName())){
            List<Map> ip = mapping.get(rpcModel.getServerName());
            Map i = new HashMap();
            i.put("ip",rpcModel.getIp());
            i.put("port",rpcModel.getPort());
            ip.add(i);
        }else {
            List<Map> ip = new LinkedList<>();
            Map i = new HashMap();
            i.put("ip",rpcModel.getIp());
            i.put("port",rpcModel.getPort());
            ip.add(i);
            mapping.put(rpcModel.getServerName(),ip);
        }
    }
    public void invork(){
        final ClientHanlder hanlder = new ClientHanlder();
        if(mapping.containsKey(rpcModel.getInvorkServerName())){
            List<Map> ip =  mapping.get(rpcModel.getInvorkServerName());
            Map one;
            if(ip.size()==1){
                one = ip.get(0);
            }else {
                Random random1 = new Random(ip.size()-1);
                one = ip.get(random1.nextInt());
            }
            EventLoopGroup client = new NioEventLoopGroup();
            Bootstrap bootstrap = new Bootstrap();
            try {
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
                ChannelFuture channelFuture = bootstrap.connect(one.get("ip").toString(), Integer.valueOf(one.get("port").toString())).sync();
                channelFuture.channel().writeAndFlush(rpcModel).sync();
                channelFuture.channel().closeFuture().sync();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }finally {
                client.shutdownGracefully();
            }
            ctx.write(hanlder.getResult());
        }else {
            ctx.write("no found invork server");
        }
    }

    private class ClientHanlder extends ChannelInboundHandlerAdapter{
        private Object res;
        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
            this.res = msg;
        }

        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
            System.out.println("register is Exception");
        }

        public Object getResult(){
            return res;
        }
    }
}
