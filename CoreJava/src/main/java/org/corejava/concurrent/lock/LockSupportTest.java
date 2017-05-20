package org.corejava.concurrent.lock;

import java.util.concurrent.locks.LockSupport;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * junit的test方法执行完毕之后，不会因为还存在thread deamon false就等待，而是直接exit jvm
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
 * @since 2017年5月5日
 */
public class LockSupportTest {

    private static final Logger logger = LoggerFactory.getLogger(LockSupportTest.class);

    @Test
    public void parktest() throws Exception {
        
        Thread t=new Thread(new Runnable() {
            
            @Override
            public void run() {
                logger.debug("before");
                try {
                    Thread.sleep(10000);
                }
                catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                LockSupport.park();
                logger.debug("after");

            }
        });
        t.setDaemon(false);

        t.start();
        LockSupport.unpark(t);

        Thread.sleep(2000);
        // System.err.println(t.getStackTrace());

     
    }

    public static void main(String[] args) throws Exception {
        LockSupportTest s = new LockSupportTest();
        s.parktest();
    }


}
