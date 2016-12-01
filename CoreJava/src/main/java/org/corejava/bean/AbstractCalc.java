package org.corejava.bean;

public class AbstractCalc implements Icalc {

    @Override
    public int calc(int i, int j) {
        return i + j;
    }

}
