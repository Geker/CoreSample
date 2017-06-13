package org.corejava.concurrent;

import java.util.concurrent.locks.LockSupport;

import org.junit.Test;

/**
 * ;
 * <p>
 * Title: LockSupportTest
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: ufo Copyright (C) 2017
 * </p>
 *
 * @author SONGQQ
 * @version
 * @since 2017年6月10日
 */
public class LockSupportTest {
    volatile static int i;

    @Test
    public void testName() throws Exception {
        Thread t = new Thread(new Runnable() {

            @Override
            public void run() {

                try {
                    Thread.sleep(1000);
                }
                catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                System.out.println(Thread.interrupted());
                while (true) {
                    System.out.println("begin park");
                    // LockSupport.park();
                    LockSupport.park();
                    System.out.println("after park");
                }
            }
        });
        t.start();
        i++;
        t.interrupt();
        i++;
        // LockSupport.unpark(t);

        System.in.read();

    }
}
