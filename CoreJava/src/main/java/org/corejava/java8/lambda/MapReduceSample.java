package org.corejava.java8.lambda;

import java.util.Arrays;
import java.util.HashMap;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.function.BiConsumer;
import java.util.function.UnaryOperator;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MapReduceSample {

    private static final Logger logger = LoggerFactory.getLogger(MapReduceSample.class);

    private List<String> lists = Arrays.asList("beijing", "shanghai", "wuhan", "shenzhen", "guangzhou");
    UnaryOperator<String> unary = input -> input + ",";
    BiConsumer<String, String> bi = (a, b) -> System.out.println(a + ":" + b);
    private Map<String, String> map = new HashMap<>();
    ;
    public static void main(String[] args) {
        logger.debug("hello" + "");
        String a = "xxxx";
        Executors.newFixedThreadPool(1).submit(() -> System.out.println("lambda"));
    }

    @org.junit.Before
    public void Before() {
        map.put("hello", "1");
        map.put("hello2", "2");
        map.put("hello3", "3");
        map.put("hello3", "3");

    }

    @Test
    public void biComsumerTest() {
        lists.stream().map(unary).forEach(System.out::printf);
        map.forEach(bi);
    }

    @Test
    public void testIntSummaryStatistics() throws Exception {
        List<Integer> numbers = Arrays.asList(3, 2, 2, 3, 7, 3, 5);

        IntSummaryStatistics stats = numbers.stream().mapToInt((x) -> x).summaryStatistics();

        System.out.println("Highest number in List : " + stats.getMax());
        System.out.println("Lowest number in List : " + stats.getMin());
        System.out.println("Sum of all numbers : " + stats.getSum());
        System.out.println("Average of all numbers : " + stats.getAverage());
    }

    @Test
    public void testReduce() throws Exception {
        int length =
                Arrays.asList("str1", "str2").stream().reduce(0, (accumulatedInt, str) -> accumulatedInt + str.length(), (a, b) -> a + b);
        System.out.println(length);

    }
}
