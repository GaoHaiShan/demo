package prototype.shallowClone;

import java.io.Serializable;

public class ZengPin implements Serializable {
    private String name;

    public ZengPin(String name) {
        this.name=name;
    }
    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
