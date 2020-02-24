package factorymethod;

import model.IPay;
import model.PayPal;

public class PayPalImpl extends IPayFactory {
    @Override
    public IPay create() {
        return new PayPal();
    }
}
