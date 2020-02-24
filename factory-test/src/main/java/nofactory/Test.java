package nofactory;

import model.*;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class Test {
    public static void main(String[] args) {
        try {
            doPay("微信");
            doPay("支付宝");
            doPay("银联");
            doPay("跨境");
            doPay("qq");
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
    private static void doPay(String payName) throws IllegalAccessException {
        if("微信".equals(payName)){
            IPay pay = new WxPay();
            pay.send(objToMap(pay));
        }else if("支付宝".equals(payName)){
            IPay pay = new AliPay();
            pay.send(objToMap(pay));
        }else if("银联".equals(payName)){
            IPay pay = new YinLianPay();
            pay.send(objToMap(pay));
        }else if("跨境".equals(payName)) {
            IPay pay = new PayPal();
            pay.send(objToMap(pay));
        }else {
            System.out.println("未找到支付方式");
        }
    }
    private static Map objToMap(Object object) throws IllegalAccessException {
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
}
