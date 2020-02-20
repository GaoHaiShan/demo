import api.IHelloWorK;

public class client {


    public static void main(String[] args) {
        IHelloWorK helloWorK = RpcProxy.create(IHelloWorK.class);
        System.out.println(helloWorK.testOne("12454"));
        System.out.println(helloWorK.testTwo("海山"));
    }
}
