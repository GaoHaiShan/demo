package factorymethod;

import model.AliPay;
import model.IPay;

public class AliPayImpl extends IPayFactory {
    @Override
    public IPay create() {
        return new AliPay();
    }
}
