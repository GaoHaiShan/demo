package singleton.entry;

import java.io.Serializable;

public class Person implements Serializable {
    private static Person instance;

    public void setInstance(Person o){
        instance= o;
    }
    public Object readResolve(){
        return instance;
    }
}
