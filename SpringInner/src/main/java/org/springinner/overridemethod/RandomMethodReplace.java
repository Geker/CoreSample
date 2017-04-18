package org.springinner.overridemethod;

import java.lang.reflect.Method;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.support.MethodReplacer;
import org.springframework.stereotype.Component;

@Component
public class RandomMethodReplace implements MethodReplacer {
    @Autowired
    RandomGen gen;

    @Override
    public Object reimplement(Object obj, Method method, Object[] args) throws Throwable {
        System.out.println(method.getName() + " 方法已经被替换!");
        return gen;
    }

}
