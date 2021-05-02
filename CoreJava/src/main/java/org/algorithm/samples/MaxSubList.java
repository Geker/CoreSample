package org.algorithm.samples;

public class MaxSubList {

    // O(N^3)
    public static int maxSubList(int[] array) {
        int maxvalue = 0;
        int s = 0, e = 0;
        for (int i = 0; i < array.length; i++) { // start
            for (int j = 1; j < array.length - i; j++) { // size
                int sum = 0;
                for (int k = i; k < j; k++) { // sum
                    sum += array[k];
                }
                if (sum > maxvalue) {
                    maxvalue = sum;
                    s = i;
                    e = j;
                }
            }
        }
        System.out.println("start:" + s + " end:" + (e - 1));
        return maxvalue;
    }

    // O(N^2)
    public static int maxSubList2(int[] array) {
        int maxvalue = 0;
        int s = 0, e = 0;
        for (int i = 0; i < array.length; i++) { // start
            int sum = 0;
            for (int j = i; j < array.length; j++) { // size

                sum += array[j];
                if (sum > maxvalue) {
                    maxvalue = sum;
                    s = i;
                    e = j;
                }
            }
        }
        System.out.println("start:" + s + " end:" + (e));
        return maxvalue;
    }

    // O(N )
    public static int maxSubList3(int[] array) {
        int maxvalue = 0;
        int s = 0, e = 0;
        for (int i = 0; i < array.length; i++) { // start
            int sum = 0;

            sum += array[i];
            if (sum > maxvalue) {
                maxvalue = sum;
            }
            if (sum <= 0) // 求和为0，则不可能为起点。此种算法无法确定起点。
            {
                sum = 0;
            }

        }
        System.out.println("start:" + s + " end:" + (e));
        return maxvalue;
    }

    public static void main(String[] args) {
        int max = maxSubList(new int[] { 1, 2, -4, 5, -2, 3, 4, -9, 2, 1, 0 });
        int max2 = maxSubList2(new int[] { 1, 2, -4, 5, -2, 3, 4, -9, 2, 1, 0 });
        int max3 = maxSubList2(new int[] { 1, 2, -4, 5, -2, 3, 4, -9, 2, 1, 0 });

        System.out.println(max);
        System.out.println(max2);
        System.out.println(max3);

    }
}
