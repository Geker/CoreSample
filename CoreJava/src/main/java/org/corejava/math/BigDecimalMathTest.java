package org.corejava.math;

import java.math.BigDecimal;

import org.junit.Test;

public class BigDecimalMathTest {

    @Test
    public void test() {
        BigDecimal x = new BigDecimal("4.01");
        BigDecimal y = BigDecimalMath.sqrt(x);
        BigDecimal c = BigDecimalMath.exp(x);
        System.err.println(c);
    }

}
