package org.example;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import singleton.entry.Person;
import singleton.hungry.EnumSim;
import singleton.lazy.IocLazyImpl;
import singleton.lazy.IocTest;
import singleton.lazy.ThreadLocalImpl;

import java.io.*;

/**
 * Unit test for simple App.
 */
public class AppTest 
{
    @Test
    public void test(){
        EnumSim sim = EnumSim.getInstance();
        sim.setData(new Object());
        System.out.println(sim.getData());
    }
    @Test
    public void testOne() throws IllegalAccessException, InstantiationException, ClassNotFoundException {
        Thread thread= new Thread(new IocTest());
        Thread thread1 = new Thread(new IocTest());
        thread.start();
        thread1.start();
//        Person person = (Person) IocLazyImpl.getInstance(Person.class.getName());
//        System.out.println(person);
    }

    @Test
    public void testTwo() throws IOException, IllegalAccessException, InstantiationException, ClassNotFoundException {
        FileOutputStream fileOutputStream = new FileOutputStream(new File("D://user.txt"));
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
        Person person = (Person) IocLazyImpl.getInstance(Person.class.getName());
        System.out.println(person);
        person.setInstance(person);
        objectOutputStream.writeObject(person);
        objectOutputStream.close();
        fileOutputStream.close();

        FileInputStream fileInputStream = new FileInputStream(new File("D://user.txt"));
        ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
        Person person1 = (Person) objectInputStream.readObject();
        System.out.println(person1);
        fileInputStream.close();
        objectInputStream.close();
    }
    @Test
    public void testThree() throws IllegalAccessException, InstantiationException, ClassNotFoundException {
        Object o = ThreadLocalImpl.getInstance(Person.class.getName());
        System.out.println(o);
        System.out.println(ThreadLocalImpl.getInstance(Person.class.getName()));
    }
}
