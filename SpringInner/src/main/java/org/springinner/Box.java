package org.springinner;

import org.springframework.stereotype.Component;

@Component
public class Box implements ISay, IObj {

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Box [name=" + name + "]";
    }

    public Box() {
        name = "box name";
    }

}
