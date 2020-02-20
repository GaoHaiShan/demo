import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class ServiceHanlder extends ChannelInboundHandlerAdapter {
    private Object result;
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        this.result =  msg;
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println("client is Exception");
    }

    public Object getResult() {
        return result;
    }
}
