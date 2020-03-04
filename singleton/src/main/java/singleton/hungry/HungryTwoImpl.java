package singleton.hungry;

public class HungryTwoImpl {

    //实现方式一
    private static HungryTwoImpl instance;

    static {
        instance = new HungryTwoImpl();
    }
    //构造方法私有化
    private HungryTwoImpl(){}

    //对外访问接口
    public static HungryTwoImpl getInstance(){
        return instance;
    }

    public static void main(String[] args) {
        System.out.println(HungryTwoImpl.getInstance());
        System.out.println(HungryTwoImpl.getInstance());
    }
}
