package org.corejava.bean.proxy.cglib;

import java.lang.reflect.Method;

import org.corejava.bean.proxy.Add;
import org.junit.Test;

import net.sf.cglib.proxy.Callback;
import net.sf.cglib.proxy.CallbackFilter;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.FixedValue;
import net.sf.cglib.proxy.InvocationHandler;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

public class CglibProxySample {

  public class MyMethodInterceptor implements MethodInterceptor {
	public Object intercept(Object obj, Method method, Object[] arg, MethodProxy proxy) throws Throwable {
	  System.out.println("Before:" + method);
	  Object object = proxy.invokeSuper(obj, arg);
	  System.out.println("After:" + method);
	  return object;
	}
  }

  @Test
  public void baseEnhancer() {
	Enhancer enhancer = new Enhancer();
	enhancer.setSuperclass(UserSvc.class);
	enhancer.setCallback(new MyMethodInterceptor());
	UserSvc userService = (UserSvc) enhancer.create();
	userService.add();
	userService.show(100);
  }
}
