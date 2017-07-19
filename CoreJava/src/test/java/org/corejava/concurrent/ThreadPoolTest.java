package org.corejava.concurrent;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.mutable.MutableInt;
import org.junit.Test;

public class ThreadPoolTest {
    ThreadPoolExecutor tp = new ThreadPoolExecutor(3, 3, 0, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(100),
        new ThreadPoolExecutor.CallerRunsPolicy());

    @Test
    public void testName() throws Exception {
        MutableInt i = new MutableInt();
        MutableInt j = new MutableInt();

        i.setValue(1);
        j.setValue(190);
        tp.execute(() ->

        {

            while (i.getValue() > 0) {
                j.setValue(j.getValue() * i.getValue() + 992);
                if (i.getValue() > 0)
                    i.setValue(i.getValue() + 1);
            }

            System.out.println(Thread.currentThread().getName() + " hello1");

        });
        tp.shutdownNow();
        tp.setThreadFactory(Executors.defaultThreadFactory());
        i.setValue(-1009);
        tp.execute(() -> System.out.println(Thread.currentThread().getName() + " hello2"));

    }
}
