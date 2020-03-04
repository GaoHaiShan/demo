package com.haishan.proxy.entry;

import com.haishan.proxy.java.JavaProxy;
import com.haishan.proxy.my.MyProxy;
import com.haishan.proxy.my.MyProxyTest;

import java.io.File;

public class Test {
    public static void main(String[] args) throws Exception {
        IPerson person = JavaProxy.newInstance(Person.class);
        person.foundLove("海山");
        person.foundWork("海山");
        IPerson person1 = MyProxyTest.newInstance(Person.class);
        person1.foundLove("海山");
        person1.foundWork("海山");
        String path = person1.getClass().getResource("").getPath();
        String newPath = path.replace("target","src/main").replace("classes","java");
        System.out.println(newPath);
        File file1 = new File(newPath+"$Proxy0.java");
        file1.delete();
        File file = new File(newPath+"$Proxy0.class");
        file.delete();
    }
}
