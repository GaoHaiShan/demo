package singleton.lazy;

import singleton.entry.Person;

public class IocTest implements Runnable {
    @Override
    public void run() {
        try {
            Person person= (Person) IocLazyImpl.getInstance(Person.class.getName());
            System.out.println(person);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
    }
}
