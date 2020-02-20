import api.RpcModel;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

public class MethodProxy implements InvocationHandler {
    private Class<?> clazz;
    public MethodProxy(Class<?> clazz) {
        this.clazz = clazz;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        return rpcInvoke(proxy,method, args);
    }

    private Object rpcInvoke(Object proxy, Method method, Object[] args) {
        RpcModel rpcModel = new RpcModel();
        rpcModel.setServerName("rpc-client");
        rpcModel.setInvorkServerName("rpc-service");
        rpcModel.setClassName(method.getDeclaringClass().getName());
        final ServiceHanlder serviceHanlder = new ServiceHanlder();
        try {
            rpcModel.setIp(getLocalIp4AddressFromNetworkInterface().get(0).getHostAddress());
        } catch (SocketException e) {
            e.printStackTrace();
        }
        rpcModel.setPort(8082);
        rpcModel.setMethodName(method.getName());
        rpcModel.setType(method.getParameterTypes());
        rpcModel.setParam(args);
        rpcModel.setServer(false);
        EventLoopGroup client = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(client)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        ChannelPipeline pipeline = socketChannel.pipeline();
                        pipeline.addLast(new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 0, 4, 0, 4));
                        pipeline.addLast(new LengthFieldPrepender(4));
                        pipeline.addLast("encoder", new ObjectEncoder());
                        pipeline.addLast("decoder", new ObjectDecoder(Integer.MAX_VALUE, ClassResolvers.cacheDisabled(null)));
                        pipeline.addLast(serviceHanlder);
                    }
                });
        try {
            ChannelFuture channelFuture = bootstrap.connect("127.0.0.1",8080).sync();
            channelFuture.channel().writeAndFlush(rpcModel).sync();
            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            client.shutdownGracefully();
        }
        return serviceHanlder.getResult();
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
}
