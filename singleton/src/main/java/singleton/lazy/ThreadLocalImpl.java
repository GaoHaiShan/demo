package singleton.lazy;

import java.util.HashMap;
import java.util.Map;

public class ThreadLocalImpl {
    private static ThreadLocal<Map<String,Object>> threadLocal = new ThreadLocal<>();
    static {
        threadLocal.set(new HashMap<String, Object>());
    }
    private ThreadLocalImpl(){}

    public static Object getInstance(String className) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        Map<String,Object> info = threadLocal.get();
        if(!info.containsKey(className)){
            info.put(className,Class.forName(className).newInstance());
        }
        return info.get(className);
    }
}
