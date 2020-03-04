package singleton.lazy;

public class InnerLazyImpl {
    private static InnerLazyImpl instance;

    private InnerLazyImpl() throws Exception {
        if(instance != null) {
            throw new Exception("不用非法调用");
        }
    }

    public InnerLazyImpl getInstance(){
        return instance;
    }
    private static class  Inner{
        static {
            try {
                instance = new InnerLazyImpl();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
