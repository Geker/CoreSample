package org.corejava.java8;

public interface IDefaultMethod {
    public default String getName() {
        return "name";
    }
}
