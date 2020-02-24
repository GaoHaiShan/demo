package nofactory;

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
            WxPay wxPay = new WxPay();
            System.out.println("请求微信接口，发送数据包 ："+objToMap(wxPay).toString());
        }else if("支付宝".equals(payName)){
            AliPay aliPay = new AliPay();
            System.out.println("请求支付宝接口，发送数据包 ："+objToMap(aliPay).toString());
        }else if("银联".equals(payName)){
            YinLianPay yinLianPay = new YinLianPay();
            System.out.println("请求银联接口，发送数据包 ："+objToMap(yinLianPay).toString());
        }else if("跨境".equals(payName)) {
            PayPal payPal = new PayPal();
            System.out.println("请求跨境接口，发送数据包 ："+objToMap(payPal).toString());
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
