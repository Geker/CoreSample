package org.corejava;

import java.io.IOException;
import java.math.BigDecimal;

public class APP {
    public static void main(String[] args) throws IOException {
        BigDecimal sum = new BigDecimal("0.00");
        // BigDecimal bd = new BigDecimal(0.01);
        // System.out.println(bd);
        double yid = Math.pow(sum.divide(new BigDecimal("10000")).add(new BigDecimal("1")).doubleValue(), 365.0 / 5);
        BigDecimal yidBigDecimal = new BigDecimal(0.0);
        System.err.println(yidBigDecimal);
    }
}
