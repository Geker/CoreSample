package org.corejava.bean.factorymethod;

//模板方法能够返回内部类的对象，并且内部类不能在外部被初始化。
public class FactoryMethod {

    public static AddMethod getMethod() {
        return new AddMethod();
    }


    public static class AddMethod {

        private AddMethod() {

        }

        public int add19090(int i) {
            return i + 19090;
        }
    }

}
