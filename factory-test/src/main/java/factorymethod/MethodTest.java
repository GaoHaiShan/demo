package factorymethod;

import model.IPay;

public class MethodTest {
    public static void main(String[] args) throws IllegalAccessException {
        IPayFactory payFactory= new AliPayImpl();
        IPay iPay = payFactory.create();
        iPay.send(IPayFactory.objToMap(iPay));

        IPayFactory payFactory1= new WXPayImpl();
        IPay iPay1 = payFactory1.create();
        iPay1.send(IPayFactory.objToMap(iPay1));


        IPayFactory payFactory2= new YinLianPayImpl();
        IPay iPay2 = payFactory2.create();
        iPay2.send(IPayFactory.objToMap(iPay2));

        IPayFactory payFactory3= new PayPalImpl();
        IPay iPay3 = payFactory3.create();
        iPay.send(IPayFactory.objToMap(iPay3));
    }
}
