package org.corejava.concurrent;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.junit.Test;

public class SynchronousQueueCustTest {
    ThreadPoolExecutor tpe = new ThreadPoolExecutor(2, 2, 0, TimeUnit.DAYS, new SynchronousQueue());
    static int niters = 0;
    @Test
    public void test() throws IOException {
        tpe.prestartAllCoreThreads();

        tpe.submit(new SampleTask());
        System.in.read();
    }

    @Test
    public void test1() throws IOException {
        // ExecutorService ex = Executors.newCachedThreadPool();

        run();

        System.in.read();
    }

    private class MyAction implements Runnable {
        @Override
        public void run() {
            System.out.println("TestAction run " + ++niters);
        }
    }

    void run() {
        Object syncObj = new Object();
        ExecutorService defThreadPool = Executors.newCachedThreadPool();
        synchronized (syncObj) {
            for (int i = 0; i < 100000; i++) {
                defThreadPool.execute(new MyAction());
                try {
                    syncObj.wait(100);
                }
                catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                }
        }
    }

}
