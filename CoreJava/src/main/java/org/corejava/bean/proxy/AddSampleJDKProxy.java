package org.corejava.bean.proxy;


import org.junit.Test;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Created by songqingqing on 11/18/2016.
 */
public class AddSampleJDKProxy {
    @Test
    public void TestCommonFuncProxy() {
        Add a = new AddSample();
        int n;
        if (a instanceof AddAbstractClass) {
            n = ((AddAbstractClass) a).addC(1, 2);
        } else
            n = a.addInt(1, 2);

        System.out.printf("hello " + n);
    }

    @Test
    public void TestJAVAProxy() {
        final Add a = new AddSample();
        InvocationHandler invocation = new InvocationHandler() {


            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                System.out.println("proxy in");
                return method.invoke(a, args);
            }
        };
        Add proxy = (Add) Proxy.newProxyInstance(AddAbstractClass.class.getClassLoader(), new Class[]{Add.class}, invocation);
        int j = proxy.addInt(1, 2);
        System.out.println(j);

    }
}
