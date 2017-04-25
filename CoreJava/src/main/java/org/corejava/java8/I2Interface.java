package org.corejava.java8;

public interface I2Interface extends IDefaultMethod {
    @Override
    public default String getName() {
        return "1";
    }
}
