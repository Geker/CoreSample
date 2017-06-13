package org.corejava.map;

import java.util.HashMap;

public class HashMapTest {

    /**
     * hashmap在size达到threshold时进行扩容，按照2的倍数扩容。扩容后
     * 不考虑treeBIn的情况下
     * 扩容的时候，table[扩大为2倍]
     * 然后对于oldTable的所有entry重新计算hash和位置，主要的处理技巧是原来的entry list的元素可能会分布在
     * 新table的两个entry中。需要与原来的cap&判断新的entry在数组的高位还是低位
     *
     * @param args
     */
    public static void main(String[] args) {
        HashMap<String, Integer> map = new HashMap<>();
        for (int i = 0; i < 100; i++) {
            map.put("first_" + i, i);
        }

    }

}
