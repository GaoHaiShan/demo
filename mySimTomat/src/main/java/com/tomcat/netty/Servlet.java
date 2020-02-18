package com.tomcat.netty;

import com.tomcat.IHttpRequest;
import com.tomcat.IHttpRespont;

public abstract class Servlet {
    public void service(IHttpRequest myHttpRequest, IHttpRespont myHttpRespont){
        if("GET".equals(myHttpRequest.getMethode())){
            try {
                doGet(myHttpRequest,myHttpRespont);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else {
            try {
                doPost(myHttpRequest,myHttpRespont);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    public abstract void doGet(IHttpRequest request, IHttpRespont response) throws Exception;

    public abstract void doPost(IHttpRequest request,IHttpRespont response) throws Exception;
}
