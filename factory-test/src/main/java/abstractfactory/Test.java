package abstractfactory;

import model.IPay;

public class Test {
    public static void main(String[] args) throws IllegalAccessException {
        IPayFactory payFactory = new WaiGouImpl();
        ITransferAccounts transferAccounts = payFactory.createTransferAccounts();
        transferAccounts.transferAccounts();
        IPay pay = payFactory.createPay();
        pay.send(IPayFactory.objToMap(pay));


        IPayFactory payFactory1 = new ZhongGouImpl();
        ITransferAccounts transferAccounts1 = payFactory1.createTransferAccounts();
        transferAccounts1.transferAccounts();
        IPay pay1 = payFactory1.createPay();
        pay1.send(IPayFactory.objToMap(pay1));
    }
}
