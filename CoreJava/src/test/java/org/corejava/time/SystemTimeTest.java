package org.corejava.time;

import org.junit.Test;

import java.util.concurrent.atomic.LongAdder;

public class SystemTimeTest {

    @Test
    public void test_milli_speed1() {
        long sum = 1;
        int N = 100000000;
        long t1 = System.currentTimeMillis();
        for (int i = 0; i < N; i++)
            sum+=(System.currentTimeMillis());
        long t2 = System.currentTimeMillis();
        System.out.println("Sum = " + sum + "; time = " + (t2 - t1) +
                "; or " + (t2 - t1) * 1.0E6 / N + " ns / iter");
    }

    @Test
    public void test_milli_speed() {
        LongAdder sum = new LongAdder();
        int N = 100000000;
        long t1 = System.currentTimeMillis();
        for (int i = 0; i < N; i++)
            sum.add(System.currentTimeMillis());
        long t2 = System.currentTimeMillis();
        System.out.println("Sum = " + sum + "; time = " + (t2 - t1) +
                "; or " + (t2 - t1) * 1.0E6 / N + " ns / iter");
    }

    @Test
    public void test_nano_speed() {
        LongAdder sum = new LongAdder();
        int N = 100000000;
        long t1 = System.nanoTime();
        for (int i = 0; i < N; i++)
            sum.add(System.nanoTime()/1000000);
        long t2 = System.nanoTime();
        System.out.println("Sum = " + sum.sum() + "; time = " + (t2 - t1) +
                "; or " + (t2 - t1) * 1.0E6 / N + " ns / iter");
    }

}
