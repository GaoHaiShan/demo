package com.haishan.proxy.java;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class JavaProxy implements InvocationHandler {
    private Class clazz;

    public JavaProxy(Class clazz) {
        this.clazz = clazz;
    }

    public static  <T> T newInstance(Class<T> clazz){
        JavaProxy javaProxy = new JavaProxy(clazz);
        return (T) Proxy.newProxyInstance(clazz.getClassLoader(),clazz.getInterfaces(),javaProxy);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Object o = clazz.newInstance();
        return method.invoke(o,args);
    }
}
