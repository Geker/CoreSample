package org.algorithm.sort;

import java.util.Arrays;

public class FullSeq {
    static int n = 2;
    static int m;
    static void listArr(int a[], int pre[]) {
        if (a.length == 1) {
            for (int j : pre) {
                System.err.print(j + ",");
            }
            System.err.println(a[0] + ";");
            return;
        }
        for (int i = 0; i < a.length; i++) {
            int b[] = Arrays.copyOf(a, a.length);

            int tmp = b[i];
            b[i] = b[0];
            b[0] = tmp;
            int c[] = Arrays.copyOfRange(b, 1, b.length);
            int newPre[]=new int[pre.length+1];
            newPre = Arrays.copyOf(pre, pre.length + 1);
            newPre[pre.length]=tmp;

            listArr(c, newPre);
        }
        // return a;

    }

    public static void main(String[] args) {
        int[] a = { 99, 44, 33, 22, 77, 88, 55 };
        m = a.length;
        listArr(a, new int[] {});
    }
}
