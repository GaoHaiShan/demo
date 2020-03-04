package com.haishan.proxy.entry;

public class Person implements IPerson {
    @Override
    public void foundLove(String name) {
        System.out.println("找对象"+name);
    }

    @Override
    public void foundWork(String name) {
        System.out.println("找工作"+name);
    }
}
