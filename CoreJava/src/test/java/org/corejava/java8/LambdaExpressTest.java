package org.corejava.java8;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

public class LambdaExpressTest {
    List<String> list = Arrays.asList("s100", "a100", "s101", "a104", "s003");

    @Test
    public void testSortUseLambda() throws Exception {

        Collections.sort(list, (s, t) -> {
            return StringUtils.compare(s, t);
        });
        System.out.println(list);
    }

    @Test
    public void testStream() throws Exception {
        list.parallelStream().forEach(s -> System.out.println(Thread.currentThread().getName() + " " + s));
    }
}
