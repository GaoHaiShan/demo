package com.tomcat.netty;

import com.tomcat.IHttpRequest;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.HttpRequest;

public class MyHttpRequest implements IHttpRequest {
    private HttpRequest request;
    private ChannelHandlerContext channelHandlerContext;
    public MyHttpRequest(ChannelHandlerContext ctx, HttpRequest req) {
        request = req;
        channelHandlerContext = ctx;
    }

    @Override
    public String getUrl() {
        return request.uri();
    }
    @Override
    public String getMethode(){
        return request.method().name();
    }
}
