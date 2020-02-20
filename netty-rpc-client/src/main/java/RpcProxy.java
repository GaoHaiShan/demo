import java.lang.reflect.Proxy;

public class RpcProxy {
    public static <T> T create(Class<T> clazz){
        MethodProxy proxy = new MethodProxy(clazz);
        Class<?>[] interfaces = clazz.isInterface() ?
                new Class[]{clazz}:
                clazz.getInterfaces();
        T result = (T) Proxy.newProxyInstance(clazz.getClassLoader(),interfaces,proxy);
        return result;
    }
}
