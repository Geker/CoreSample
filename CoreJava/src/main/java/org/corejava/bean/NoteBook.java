package org.corejava.bean;
public class NoteBook {

    private String cpu;
    private String mem;
    private String keyboard;
    private String mouse;
    private String touchPad;

    private long speed;

    public long getSpeed() {
        return speed;
    }

    public void setSpeed(long speed) {
        this.speed = speed;
    }

    public String getTouchPad() {
        return touchPad;
    }

    public void setTouchPad(String touchPad) {
        this.touchPad = touchPad;
    }

    public String getCpu() {
        return cpu;
    }

    public void setCpu(String cpu) {
        this.cpu = cpu;
    }

    public String getMem() {
        return mem;
    }

    public void setMem(String mem) {
        this.mem = mem;
    }

    public String getKeyboard() {
        return keyboard;
    }

    public void setKeyboard(String keyboard) {
        this.keyboard = keyboard;
    }

    public String getMouse() {
        return mouse;
    }

    public void setMouse(String mouse) {
        this.mouse = mouse;
    }

}
