package service;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Test {
    public static void main(String[] args) {
        ApplicationContext applicationContext =new AnnotationConfigApplicationContext(SpringConfig.class);
       AnnotationConfigApplicationContext a =  (AnnotationConfigApplicationContext) applicationContext;
        a.start();
    }
}
