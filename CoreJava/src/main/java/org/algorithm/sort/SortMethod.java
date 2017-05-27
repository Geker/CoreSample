package org.algorithm.sort;

import java.util.Arrays;
import java.util.List;

public class SortMethod {

    public static <E> void swap(E[] arr, int i, int j) {
        E temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    public static <E> void showArray(List<E> arr) {
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

    // 快速排序
    public static <E extends Comparable<E>> void quickSort(E[] arr, int low, int high) {
     
         
         //对arr[low..high]快速排序
          int pivotpos; //划分后的基准记录的位置
          if(low<high){//仅当区间长度大于1时才须排序
            pivotpos = Partition(arr, low, high); // 对R[low..high]做划分
            quickSort(arr, low, pivotpos - 1); // 对左区间递归排序
            quickSort(arr, pivotpos + 1, high); // 对右区间递归排序
           }
           //QuickSort
    }

    private static <E extends Comparable<E>> int Partition(E[] arr, int low, int high) {
        int i = low + 1;
        int j = high;
        E value = arr[low]; // 选取low为pivotpos
        while (true) {
            while ((arr[i].compareTo(value) < 0))
                i++;
            while ((arr[j].compareTo(value) > 0))
                j--;
            if (i < j)
                swap(arr, i, j);
            else
                break;
        }
        swap(arr, low, j); // 因为上次 ij发生了交换，因此目前j实际上的位置比low小。
        return j;
    }

    public static void main(String[] args) {
        Integer[] arr = new Integer[] { 1, 21, 9, 3, 5, 99, 3 };
        // showArray(Arrays.asList(arr));
        // insertSort(arr);
        // int i = BinarySearch.binSearch(arr, 21);

        quickSort(arr, 0, arr.length - 1);
        
        showArray(Arrays.asList(arr));
        // System.out.println(i);
        // System.out.println(Arrays.asList(new String[] { "a", "b" }, new String[] { "c", "d" }));
    }

}
