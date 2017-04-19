package org.corejava.bean.generi;
public class MethodGeneric {

    public static <T> String add(T t)
    {
        return t.getClass().getName();
        
    }

    public static void main(String[] args) {
        System.err.println(add(100L));
    }
}
