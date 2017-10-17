package org.corejava.genericAndRtti;

public class AddObject<T extends CharSequence> {

    public T add(T t1, T t2) {
        return (T) (t1.toString() + t2.toString());
    }

}
