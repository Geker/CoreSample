package org.corejava.concurrent;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;

public class OwnThreadLocalTest {
    OwnThreadLocal local = new OwnThreadLocal();

    @Test
    public void TestThreadLocalInit() throws IOException {
        ExecutorService executors = Executors.newFixedThreadPool(3);
        for (int i = 0; i < 10; i++)
            executors.submit(new Runnable() {

                @Override
                public void run() {
                    // System.out.println( Thread.currentThread().getName()+" "+local.get());
                    local.set(RandomStringUtils.randomAlphanumeric(100000));

                }
            });
        System.err.println("ok");
        System.in.read();
    }

    @Test
    public void TestHu() throws IOException {
        MyWaitNotify my=new MyWaitNotify();
        Thread t=new Thread(new Runnable() {
            
            @Override
            public void run() {
                System.err.println("start wait..");
                my.doWait();
            }
        });
        t.start();
        Thread t1 = new Thread(new Runnable() {

            @Override
            public void run() {
                System.err.println("start notify..");
                my.doNotify();
            }
        });
        t1.start();
        System.in.read();
    }

    public class MonitorObject {

    }

    public class MyWaitNotify {

        MonitorObject myMonitorObject = new MonitorObject();
        public void doWait() {
            synchronized (myMonitorObject) {
                try {
                    System.out.println("wait...........");
                    myMonitorObject.wait();
                    System.out.println("wait was notify");
                }
                catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        public void doNotify() {
            synchronized (myMonitorObject) {
                myMonitorObject.notify();
                System.out.println("send notify");

            }
        }
    }

    /**
     * 返回的是线程数
     */
    @Test
    public void testCoreOrCPUS() {
        int n = Runtime.getRuntime().availableProcessors();
        System.out.println("Processors:" + n);
    }

}
