package org.corejava.concurrent;

import java.util.concurrent.Executors;

public class ThreadPool {
    public static void main(String[] args) {
        Executors.newFixedThreadPool(1).submit(new Runnable() {
            
            @Override
            public void run() {
                System.out.println("my thread is :" +Thread.currentThread());
            }
        });

    }
}
