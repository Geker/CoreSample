package org.corejava.bean.utils;

import java.util.Arrays;
import java.util.List;

public class ArraysTest {
    public static void main(String[] args) {
        int[] arrInts = new int[] { 1, 2, 3, 4, 5 };
        List<int[]> a = Arrays.asList(arrInts);
        System.out.println(a);
        System.out.println(arrInts);
        System.err.println(Arrays.toString(arrInts));
    }
}
