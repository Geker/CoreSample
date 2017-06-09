package org.corejava.concurrent;

import java.io.IOException;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.junit.Test;

public class SynchronousQueueCustTest {
    ThreadPoolExecutor tpe = new ThreadPoolExecutor(2, 2, 0, TimeUnit.DAYS, new SynchronousQueue());

    @Test
    public void test() throws IOException {
        tpe.prestartAllCoreThreads();

        tpe.submit(new SampleTask());
        System.in.read();
    }

}
