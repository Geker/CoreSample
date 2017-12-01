package org.corejava.tddl.mod;

import org.apache.commons.lang3.StringUtils;

public class App {
    public static void main(String[] args) {
        Long l = Long.valueOf("00020170817100000060");

        long y = l % 16 / 4;
        long n = l % 16;

        System.out.println("db:" + y);
        System.err.println("table:" + StringUtils.leftPad(Long.toString(n), 4, '0'));

    }
}
