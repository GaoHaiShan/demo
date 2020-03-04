package com.haishan.proxy.my;

import javax.tools.JavaCompiler;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;
import java.io.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

public class MyProxy {

    public static <T> T newInstance(MyClassLoader myClassLoader, Class[] interfaces, MyInvocationHandler m) throws Exception {
        //动态生成 .java
        String src = creatJava(interfaces,m);
        //输出到磁盘
        String path = m.getClass().getResource("").getPath()+"$Proxy0.java";
        String newPath = path.replace("target","src/main").replace("classes","java");
        putComputer(src,newPath);
        //编译 .java 文件
        bianyi(newPath);
        //加载到 jvm 中
        Class clazz = myClassLoader.findClass("$Proxy0");
        Constructor constructor = clazz.getConstructor(MyInvocationHandler.class);
        return (T) constructor.newInstance(m);

    }

    private static void bianyi(String newPath) {
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        StandardJavaFileManager manager = compiler.getStandardFileManager(null,null,null);
        Iterable iterable = manager.getJavaFileObjects(new File(newPath));
        JavaCompiler.CompilationTask task = compiler.getTask(null,manager,null,null,null,iterable);
        task.call();
        try {
            manager.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static void putComputer(String src, String path) throws FileNotFoundException {
        File file = new File(path);
        FileOutputStream o = new FileOutputStream(file);
        try {
            o.write(src.getBytes());
            o.flush();
        } catch (IOException e) {
            e.printStackTrace();
            }
        try {
            o.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String creatJava(Class[] interfaces, MyInvocationHandler h) throws Exception {
        StringBuffer java = new StringBuffer();
        java.append(h.getClass().getPackage()+";\r\n");
        java.append("import java.lang.reflect.Method;\r\n");
        java.append("public class $Proxy0 implements ");
        if(interfaces.length==0){
            throw new Exception("no interfaces");
        }
        for(Class c: interfaces){
            java.append(c.getName());
            if(!c.equals(interfaces[interfaces.length-1])){
                java.append(",");
            }
        }
        java.append("{\r\n");
        java.append("\tprivate MyInvocationHandler handler;\r\n");
        java.append("\tpublic $Proxy0(MyInvocationHandler h){\r\n" +
                "\t\tthis.handler=h;\r\n" +
                "\t}\r\n");
        for(Class c:interfaces){

            Method[] methods = c.getMethods();
            for(Method m :methods){

                java.append("\t@Override\r\n");
                //方法头
                java.append("\tpublic "+m.getGenericReturnType()+" "+m.getName()+"(");
                Class<?>[] parameter = m.getParameterTypes();
                StringBuffer para = new StringBuffer();
                StringBuffer paramtype = new StringBuffer();
                para.append("{");
                if(parameter.length>0){
                    paramtype.append(",");
                }
                int i = 0;
                for(Class p:parameter){
                    paramtype.append(p.getName()+".class");
                    //方法参数
                    java.append(p.getName()+" arg"+i);
                    para.append("arg"+i);
                    if (!p.equals(parameter[parameter.length-1])){
                        java.append(",");
                        para.append(",");
                        paramtype.append(",");
                    }
                    i++;
                }

                para.append("}");
                java.append("){\r\n");
                //方法体
                java.append("\t\tMethod m0 = null;\r\n");
                java.append("\t\ttry{\r\n");
                java.append("\t\t\t m0 = "+m.getDeclaringClass().getName()+".class.getMethod(\""+m.getName()+"\""+paramtype.toString()+");\r\n");
                java.append("\t\t}catch(Exception e){\r\n");
                java.append("\t\t\te.printStackTrace();\r\n");
                java.append("\t\t}\r\n");
                java.append("\t\tObject[] objects = new Object[]"+para+";\r\n");
                java.append("\t\tinvork(m0,objects);\r\n");
                java.append("\t}\r\n");
            }

        }
        java.append("\tpublic Object invork(Method method,Object[] args){\r\n")
                .append("\t\treturn handler.invork(this,method,args);\r\n")
                .append("\t}\r\n");
        java.append("}");
        return java.toString();
    }
}
