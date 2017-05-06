package org.corejava.bean.proxy;

import java.lang.reflect.Method;

import org.junit.Test;

import net.sf.cglib.proxy.Callback;
import net.sf.cglib.proxy.CallbackFilter;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.FixedValue;
import net.sf.cglib.proxy.InvocationHandler;

public class CglibProxySample {
    @Test
    public void baseEnhancer() {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(Add.class);
        CallbackFilter filter = new CallbackFilter() {

            @Override
            public int accept(Method arg0) {
                if (arg0.getName().equals("addInt"))
                    return 0;
                else
                    return 1;

            }
        };
        enhancer.setCallbackFilter(filter);
        enhancer.setCallbacks(new Callback[]{(FixedValue) () -> {
            return 2;
        }, (InvocationHandler) (proxy, method, args) -> {
            return method.invoke(proxy, args);
        } });
        Add add = (Add) enhancer.create();
        int i = add.addInt(1, 10);
        System.out.println(add.toString());
    }
}
