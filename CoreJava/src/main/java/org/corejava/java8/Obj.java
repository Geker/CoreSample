package org.corejava.java8;

//如果implements  I2Interface2 I2Interface3 就会报错，因为无法判断是否具象接口。没有继承关系
public class Obj implements IDefaultMethod, I2Interface3 {

  private String name;

    public static class innerClass {
        private String age;
    }
}
