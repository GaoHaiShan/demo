package abstractfactory;

import model.IPay;
import model.PayPal;

/**
 * 跨境
 */
public class WaiGouImpl extends IPayFactory{
    @Override
    public IPay createPay() {
        return new PayPal();
    }

    @Override
    public ITransferAccounts createTransferAccounts() {
        return new PayPalTransferAccounts();
    }
}
