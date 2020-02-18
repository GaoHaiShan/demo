package com.tomcat.nio;

import com.tomcat.IHttpRequest;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.HttpRequest;

public class MyHttpRequest implements IHttpRequest {
    private String name;
    private String url;

    public MyHttpRequest(String name, String url) {
        this.name = name;
        this.url = url;
    }

    @Override
    public String getUrl() {
        return url;
    }
    @Override
    public String getMethode(){
        return name;
    }
}
