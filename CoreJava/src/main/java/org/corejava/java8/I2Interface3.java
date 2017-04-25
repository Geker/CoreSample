package org.corejava.java8;

public interface I2Interface3 extends I2Interface2 {
    @Override
    default public String getName() {
        return "3";
    }
}
