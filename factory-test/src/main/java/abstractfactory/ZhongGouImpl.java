package abstractfactory;

import model.IPay;
import model.WxPay;

public class ZhongGouImpl extends IPayFactory {
    @Override
    public IPay createPay() {
        return new WxPay();
    }

    @Override
    public ITransferAccounts createTransferAccounts() {
        return new WxTransferAccounts();
    }
}
