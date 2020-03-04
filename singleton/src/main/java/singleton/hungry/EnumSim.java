package singleton.hungry;

public enum EnumSim {
    INSTANCE;

    private Object data;

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public static EnumSim getInstance(){
        return INSTANCE;
    }
}
