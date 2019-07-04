package org.corejava.concurrent;

import java.util.LinkedList;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;

import com.alibaba.fastjson.JSON;

public class LinkedListMultiThreadAddTest {
    ThreadPoolExecutor tp = new ThreadPoolExecutor(100, 100, 3, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(100),
        new ThreadPoolExecutor.CallerRunsPolicy());

    @Test
    public void testName() throws Exception {
        tp.prestartAllCoreThreads();
        int n=10000;
        LinkedList<String> ll = new LinkedList<>();
        CountDownLatch cdl=new CountDownLatch(n);
        for (int i = 0; i < n; i++)
            tp.execute(() ->

            {
                ll.add("hello");
                cdl.countDown();
            });
        cdl.await();
        tp.setThreadFactory(Executors.defaultThreadFactory());
        System.out.println(ll.toString());
        System.err.println(ll.size());

    }
}
