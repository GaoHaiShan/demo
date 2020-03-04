package singleton.lazy;

public class LazySimImpl {

    private static LazySimImpl instance;

    private LazySimImpl(){}

    public static LazySimImpl getInstance(){
        if(instance==null){
            instance = new LazySimImpl();
        }
        return instance;
    }
}
