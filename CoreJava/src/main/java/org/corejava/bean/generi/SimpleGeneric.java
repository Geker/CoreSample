package org.corejava.bean.generi;

//上边界
public class SimpleGeneric<T extends Simple> {
    T obj;

    public SimpleGeneric(T obj) {
        this.obj = obj;
    }

    public void print()
    {
         System.err.println( "toStr:"+obj.toString());
    }

    public static void main(String[] args) {
        SimpleGeneric s = new SimpleGeneric(new Simple(1900));
        s.print();
    }
}
