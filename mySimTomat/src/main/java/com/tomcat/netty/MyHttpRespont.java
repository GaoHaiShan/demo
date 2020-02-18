package com.tomcat.netty;

import com.tomcat.IHttpRespont;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.*;

import java.io.UnsupportedEncodingException;

public class MyHttpRespont implements IHttpRespont {
    //SocketChannel的封装
    private ChannelHandlerContext ctx;

    private HttpRequest req;
    public MyHttpRespont(ChannelHandlerContext ctx, HttpRequest req) {
        this.ctx = ctx;
        this.req = req;
    }

    @Override
    public void write(String s) {
        FullHttpResponse response = null;
        try {
            response = new DefaultFullHttpResponse(
                    // 设置http版本为1.1
                    HttpVersion.HTTP_1_1,
                    // 设置响应状态码
                    HttpResponseStatus.OK,
                    // 将输出值写出 编码为UTF-8
                    Unpooled.wrappedBuffer(s.getBytes("UTF-8")));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        response.headers().set("Content-Type", "text/html;");
        ctx.write(response);
        ctx.flush();
        ctx.close();
    }
}
