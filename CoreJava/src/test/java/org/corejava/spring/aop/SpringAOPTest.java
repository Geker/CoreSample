package org.corejava.spring.aop;

import org.corejava.bean.Icalc;
import org.corejava.spring.BaseClassForTests;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class SpringAOPTest extends BaseClassForTests {

    @Autowired
    Icalc calc;
    @Test
    public void test() {
        int i = calc.calc(100, 200);
        System.err.println(i);
    }

}
