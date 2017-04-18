package org.springinner.autowiring;

import org.springframework.stereotype.Component;

@Component
public class Location {
    int i = 100;
    int j = 99;

    public int getI() {
        return i;
    }

    public void setI(int i) {
        this.i = i;
    }

    public int getJ() {
        return j;
    }

    public void setJ(int j) {
        this.j = j;
    }

    @Override
    public String toString() {
        return "Location [i=" + i + ", j=" + j + "]";
    }

}
