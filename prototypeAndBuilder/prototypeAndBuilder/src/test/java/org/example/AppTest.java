package org.example;

import static org.junit.Assert.assertTrue;

import builder.SpruceUp;
import builder.SpruceUpBulider;
import org.junit.Test;
import prototype.shallowClone.OrderForm;
import prototype.shallowClone.ZengPin;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * Unit test for simple App.
 */
public class AppTest 
{
    /**
     * Rigorous Test :-)
     */
    @Test
    public void test() throws IllegalAccessException, InstantiationException {
        //原有对象
        OrderForm oldOrder = new OrderForm();
        oldOrder.setClientName("客户1");
        oldOrder.setProductName("xx牌笔记本电脑");
        oldOrder.setTotlePrice(10000);
        List<String> premiumList = new ArrayList<>();
        premiumList.add("保修卡3年");
        premiumList.add("手提包");
//        oldOrder.setPremium(premiumList);
        //通过set方法赋值
        OrderForm newOrder = new OrderForm();
        newOrder.setPremium(oldOrder.getPremium());
        newOrder.setClientName(oldOrder.getClientName());
        newOrder.setProductName(oldOrder.getProductName());
        newOrder.setTotlePrice(oldOrder.getTotlePrice());
        //通过反射机制赋值
        Class clazz = oldOrder.getClass();
        OrderForm newOrderOne = (OrderForm) clazz.newInstance();
        for (Field field:clazz.getFields()){
            field.setAccessible(true);
            field.set(newOrderOne,field.get(oldOrder));
        }
    }
    @Test
    public void shallowCloneTest() throws CloneNotSupportedException, ClassNotFoundException, IllegalAccessException, InstantiationException, IOException {
        //原有对象
        OrderForm oldOrder = new OrderForm();
        oldOrder.setClientName("客户1");
        oldOrder.setProductName("xx牌笔记本电脑");
        oldOrder.setTotlePrice(10000);
        List<ZengPin> premiumList = new ArrayList<>();
        premiumList.add(new ZengPin("保修卡3年"));
        premiumList.add(new ZengPin("手提包"));
        oldOrder.setPremium(premiumList);
        System.out.println("原有"+oldOrder);
        //新对象
        OrderForm newInfo = oldOrder.jsonDeepClone();
        newInfo.getPremium().get(0).setName("保温杯");

        //保存原信息
        System.out.println("原有"+oldOrder);
        //提供可操作信息
        System.out.println("可操作"+newInfo);
    }
    @Test
    public void testOne(){
        SpruceUp spruceUp = new SpruceUpBulider()
                .createSofa("真皮沙发")
                .createCurtain("紫色窗帘")
                .createFloor("大理石地板")
                .bulider();
        System.out.println(spruceUp);

    }
}
