package com.haishan.proxy.my;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class MyProxyTest implements MyInvocationHandler {
    private Class clazz;

    public MyProxyTest(Class clazz) {
        this.clazz = clazz;
    }
    public static <T> T newInstance(Class clazz) throws Exception {
        MyProxyTest m = new MyProxyTest(clazz);
        return MyProxy.newInstance(new MyClassLoader(),clazz.getInterfaces(),m);
    }

    @Override
    public Object invork(Object porxy, Method method, Object[] objects) {
        Object o = null;

        try {
            o = clazz.newInstance();
            return method.invoke(o,objects);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
        return null;
    }
}
