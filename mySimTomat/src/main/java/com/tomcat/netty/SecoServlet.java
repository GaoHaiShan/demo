package com.tomcat.netty;

import com.tomcat.IHttpRequest;
import com.tomcat.IHttpRespont;

public class SecoServlet extends Servlet {

    @Override
    public void doGet(IHttpRequest request, IHttpRespont response) throws Exception {
        this.doPost(request, response);
    }

    @Override
    public void doPost(IHttpRequest request, IHttpRespont response) throws Exception {
        response.write("This is seco Serlvet");
    }
}
