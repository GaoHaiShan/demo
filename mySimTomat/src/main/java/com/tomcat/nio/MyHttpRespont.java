package com.tomcat.nio;

import com.tomcat.IHttpRespont;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class MyHttpRespont implements IHttpRespont {
    //SocketChannel的封装
    private SocketChannel channel;

    public MyHttpRespont(SocketChannel channel) {
        this.channel = channel;
    }

    @Override
    public void write(String s) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("HTTP/1.1 200 OK\n")
                .append("Content-Type:text/html\n")
                .append("\r\n")
                .append(s);
        try {
            channel.write(ByteBuffer.wrap(stringBuilder.toString().getBytes()));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
