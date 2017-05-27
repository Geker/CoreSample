package org.algorithm.sort;

import java.util.Arrays;
import java.util.List;

public class SortMethod {

    public static <E> void swap(E[] arr, int i, int j) {
        E temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    public static <E> void showArray(List<E> arr)
    {
        System.out.println(arr);
    }

    // 插入排序
    public static <E extends Comparable<E>> void insertSort(E[] arr) {
        for (int i = 1; i < arr.length; i++) {
            for (int j = 0; j < i; j++) {
                if (arr[i].compareTo(arr[j]) < 0) {
                    swap(arr, i, j);
                }
            }
        }
    }

    public static void main(String[] args) {
        Integer[] arr = new Integer[] { 1, 21, 9, 3, 5, 99, 3 };
        showArray(Arrays.asList(arr));
        insertSort(arr);
        showArray(Arrays.asList(arr));
        System.out.println(Arrays.asList(new String[] { "a", "b" }, new String[] { "c", "d" }));
    }

}
