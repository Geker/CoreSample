package org.corejava.bean;

//@Component
public class Computer extends AbstractCalc implements Cloneable {

    private String cpu;
    private String mem;
    private String keyboard;
    private String mouse;

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

    static class nest {
        private String s;
        public nest(String s) {
            this.setS(s);
        }
        public String getS() {
            return s;
        }
        public void setS(String s) {
            this.s = s;
        }
    }

    @Override

    public int calc(int i, int j) {
        return super.calc(i, j) + 100;
    }
}
