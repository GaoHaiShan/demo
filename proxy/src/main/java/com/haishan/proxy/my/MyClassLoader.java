package com.haishan.proxy.my;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;

public class MyClassLoader extends ClassLoader {
    private File classPathFile;

    public MyClassLoader() {
       String classPath = MyClassLoader.class.getResource("").getPath();
       String newPath = classPath.replace("target","src/main").replace("classes","java");
       classPathFile = new File(newPath);
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        String className = MyClassLoader.class.getPackage().getName()+"."+name;
        String newClassName = className.replace("target","src/main").replace("classes","java");
        if(classPathFile!=null){
            File classFile = new File(classPathFile,name.replaceAll("\\.","/")+".class");
            if(classFile.exists()){
                FileInputStream fileInputStream = null;
                ByteArrayOutputStream outputStream = null;
                try {
                    fileInputStream = new FileInputStream(classFile);
                    outputStream = new ByteArrayOutputStream();
                    byte[] buff = new byte[1024];
                    int len;
                    while ((len = fileInputStream.read(buff)) != -1){
                        outputStream.write(buff,0,len);
                    }
                    return defineClass(newClassName,outputStream.toByteArray(),0,outputStream.size());
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
        return super.findClass(name);
    }
}
