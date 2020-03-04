package singleton.lazy;

public class SyncLazyImpl {
    private volatile static SyncLazyImpl instance;

    private SyncLazyImpl(){}

    public synchronized static SyncLazyImpl getInstanceOne(){
        if(instance==null){instance = new SyncLazyImpl(); }
        return instance;
    }
    public static SyncLazyImpl getInstanceTwo(){
        synchronized (SyncLazyImpl.class){
            if(instance==null){ instance = new SyncLazyImpl(); }
        }
        return instance;
    }
    public static SyncLazyImpl getInstanceThree() throws InterruptedException {
        if(instance==null){
            synchronized (SyncLazyImpl.class){
                if(instance==null) { instance = new SyncLazyImpl(); }
            }
        }
        return instance;
    }
}
