package service;

import api.IHelloWorK;

@RpcService(value = IHelloWorK.class)
public class HelloWorkImpl implements IHelloWorK {
    @Override
    public String testOne(String name) {
        return "这是"+name+"测试一";
    }

    @Override
    public String testTwo(String name) {
        return "这是"+name+"测试二";
    }
}
