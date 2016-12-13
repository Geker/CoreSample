package org.corejava.bean.serialize;

public class Bean {
    String name;

    transient public String dev;

    public String getDev() {
        return dev;
    }

    public void setDev(String dev) {
        this.dev = dev;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;

    }

    public String getFullName() {
        return "full" + name;
    }

    @Override
    public String toString() {
        return "Bean [name=" + name + ", dev=" + dev + "]";
    }

}
