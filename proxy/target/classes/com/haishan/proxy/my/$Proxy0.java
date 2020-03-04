package com.haishan.proxy.my;
import java.lang.reflect.Method;
public class $Proxy0 implements com.haishan.proxy.entry.IPerson{
	private MyClassLoader  myClassLoader;
	private MyInvocationHandler handler;
	public $Proxy0(MyInvocationHandler h, MyClassLoader myClassLoader){
		this.handler=h;
		this.myClassLoader=myClassLoader;
	}
	@Override
	public void foundLove(){
		Method m0 = null;
		try{
			 m0 = this.getClass().getMethod("foundLove");
		}catch(Exception e){
			e.printStackTrace();
		}
		invork(m0,m0.getParameters());
	}
	@Override
	public void foundWork(){
		Method m0 = null;
		try{
			 m0 = this.getClass().getMethod("foundWork");
		}catch(Exception e){
			e.printStackTrace();
		}
		invork(m0,m0.getParameters());
	}
	public Object invork(Method method,Object[] args){
		return handler.invork(myClassLoader,method,args);
	}
}