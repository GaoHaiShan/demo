package singleton.lazy;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class IocLazyImpl {
    private static Map<String,Object> beanFactory = new ConcurrentHashMap<>();

    private IocLazyImpl(){}

    public static Object getInstance(String className) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        if(!beanFactory.containsKey(className)){
            synchronized (IocLazyImpl.class) {
                if(!beanFactory.containsKey(className)) {
                    beanFactory.put(className, Class.forName(className).newInstance());
                }
            }
        }
        return beanFactory.get(className);
    }
}
