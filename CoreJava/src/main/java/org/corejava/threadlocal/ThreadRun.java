package org.corejava.threadlocal;

public class ThreadRun implements Runnable {

    private ThreadLocal<String> name = new ThreadLocal<>();

    public String getName() {
        return name.get();
    }

    public void setName(String name) {
        this.name.set(name);
    }

    @Override
    public void run() {
        name.set(Thread.currentThread().getName());
        System.out.println(name.get());
    }

}
