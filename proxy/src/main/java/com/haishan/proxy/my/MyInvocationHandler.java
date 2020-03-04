package com.haishan.proxy.my;

import java.lang.reflect.Method;

public interface MyInvocationHandler {
    Object invork(Object porxy, Method method,Object[] objects);
}
