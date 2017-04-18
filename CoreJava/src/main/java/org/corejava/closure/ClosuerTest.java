package org.corejava.closure;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

public class ClosuerTest {
    public static void main(String[] args) {
        System.err.println(int[].class);
        System.err.println(Integer[].class);
        System.err.println(Array.newInstance(Integer.class, 2));
        System.err.println(sum(new Integer[] { 1, 1, 2, 3 }));
    }

    static int sum(Integer arr[])
    {
        AtomicInteger ii = new AtomicInteger(0);
        int Start = 100;
        final int i = 0;
        Consumer<Integer> action = new Consumer<Integer>() {

            @Override
            public void accept(Integer t) {

                ii.addAndGet(t + Start);
            }

        };
        Arrays.asList(arr).forEach(action);
        return ii.get();
    }
    
    
}
