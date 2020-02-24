package factorymethod;



import model.IPay;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public abstract class IPayFactory {

    public static Map objToMap(Object object) throws IllegalAccessException {
        Map<String,String> map = new HashMap<>();
        Class<?> clazz = object.getClass();
        for (Field field : clazz.getDeclaredFields()) {
            field.setAccessible(true);
            String fieldName = field.getName();
            String value = (String) field.get(object);
            map.put(fieldName,value);
        }
        return map;
    }
    public abstract IPay create();
}
