package abstractfactory;

public class PayPalTransferAccounts implements ITransferAccounts {
    @Override
    public void transferAccounts() {
        System.out.println("跨境转账成功");
    }
}
