package org.corejava.concurrent;

/**
 * <p>
 * Title: VolatileByteCodes
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: ufo Copyright (C) 2017
 * </p>
 *
 * @author SONGQQ
 * @version
 * @since 2017年9月29日
 */

// 在字节码层面不存在对volatile的支持。
public class VolatileByteCodes {

    static volatile int i = 100;

    public static void main(String[] args) {
        i++;
        int j = 100;
        Thread t =new Thread(new Runnable() {

            @Override
            public void run() {
                int a = j;
                int c = i;
                int i = a;
                int j=i+1;

                System.out.println(j);
            }
        });
        t.start();
        System.out.println(i);
    }

}
