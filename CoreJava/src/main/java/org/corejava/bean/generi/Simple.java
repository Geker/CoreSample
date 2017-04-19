package org.corejava.bean.generi;

import java.util.Arrays;

public class Simple implements ItoStr {
    Object obj;
    String[] strs;
    public Simple(Object obj) {
        this.obj = obj;
    }

    @Override
    public String toString() {
        return "Simple [obj=" + obj + ", strs=" + Arrays.toString(strs) + "]";
    }

    @Override
    public String toStr() {
        return toString();
    }

}
