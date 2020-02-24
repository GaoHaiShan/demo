package factorymethod;

import model.IPay;
import model.YinLianPay;

public class YinLianPayImpl extends IPayFactory {
    @Override
    public IPay create() {
        return new YinLianPay();
    }
}
