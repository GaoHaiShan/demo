package singleton.hungry;

public class HungryOneImpl {

    //实现方式一
    private static HungryOneImpl instance = new HungryOneImpl();

    //构造方法私有化
    private HungryOneImpl(){}

    //对外访问接口
    public static HungryOneImpl getInstance(){
        return instance;
    }
    public static void main(String[] args) {
        System.out.println(HungryOneImpl.getInstance());
        System.out.println(HungryOneImpl.getInstance());
    }
}
