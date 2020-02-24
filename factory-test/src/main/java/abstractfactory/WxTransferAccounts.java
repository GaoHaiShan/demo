package abstractfactory;

public class WxTransferAccounts implements ITransferAccounts {
    @Override
    public void transferAccounts() {
        System.out.println("境内转账成功");
    }
}
