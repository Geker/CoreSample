package org.corejava.math.bigdecimal;

import java.math.BigDecimal;

import org.junit.Test;

public class BigDecimalTest {

    // bigdecimal的scale
    @Test
    public void test() {
        BigDecimal i = new BigDecimal("100000000000000000222222E10");
        BigDecimal j = new BigDecimal("2");
        BigDecimal r = i.divide(j, BigDecimal.ROUND_HALF_EVEN);
        System.err.println(r.toEngineeringString());
        System.err.println(r.toString());
        System.err.println(r.toPlainString());

    }

}
