package factorymethod;

import model.IPay;
import model.WxPay;

public class WXPayImpl extends IPayFactory {
    @Override
    public IPay create() {
        return new WxPay();
    }
}
