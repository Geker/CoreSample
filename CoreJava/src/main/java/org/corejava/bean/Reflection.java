package org.corejava.bean;

import java.lang.reflect.Method;

public class Reflection {
    public static void main(String[] args) throws Exception {
        for (int i = 0; i < 10; i++) {
            doRegular();
            doReflection();
        }

    }

    public static void doRegular() throws Exception {
        long start = System.currentTimeMillis();
        Computer a = new Computer();

        for (int i = 0; i < 1000000; i++) {

            a.setCpu(String.valueOf(i));
        }
        System.out.println(System.currentTimeMillis() - start);
    }

    public static void doReflection() throws Exception {
        long start = System.currentTimeMillis();
        Computer a = (Computer) Class.forName("ps.sample.beans.Computer").newInstance();
        for (int i = 0; i < 1000000; i++) {
            Method m = Computer.class.getMethod("setCpu", String.class);
            m.invoke(a, String.valueOf(i));
        }
        System.out.println(System.currentTimeMillis() - start);
    }
}
