package org.corejava.java8;

public interface I2Interface2 extends IDefaultMethod {
    @Override
    public default String getName() {
        return IDefaultMethod.super.getName();
    }
}
