package org.corejava.tddl.mod;

import org.apache.commons.lang3.StringUtils;

public class App {
    public static void main(String[] args) {
        Long l = Long.valueOf("00020170926000000566");

        long y = l % 16 / 4;
        long n = l % 16;

        System.out.print("db:" + StringUtils.leftPad(Long.toString(y), 3, '0'));
        System.out.println("   table:" + StringUtils.leftPad(Long.toString(n), 4, '0'));

    }
}
