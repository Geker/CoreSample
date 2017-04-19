package org.corejava.bean.generi;

import java.util.Arrays;
import java.util.List;

//上边界 没有什么卵用
public class SimpleGeneric2 {

    static void print(List<? super SubSimple> t) {
        t.forEach(x -> {
            System.err.println(x.toString());

        });
    }

    public static void main(String[] args) {
        print(Arrays.asList(new Simple[] { new Simple(100), new SubSimple(200) }));
    }
}
