package org.algorithm.sort;
public class BinarySearch {
    public static <T extends Comparable<T>> int binSearch(T[] array, T target) {
        int i = 0;
        int j = array.length;
        int mid ;
        while (i < j) {
            mid = (i + j) / 2;
            if (array[mid].compareTo(target) < 0)
            {
                i = mid;
            }
            else
            if (array[mid].compareTo(target) > 0) {
                j = mid;
            }
            else {
                return mid;
            }
        }
        return -1;
    }
}
