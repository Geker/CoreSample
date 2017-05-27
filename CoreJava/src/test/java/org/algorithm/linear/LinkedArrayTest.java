package org.algorithm.linear;

import org.algorithm.linear.LinkedArray.Node;
import org.junit.Before;
import org.junit.Test;

public class LinkedArrayTest {
    LinkedArray<Integer> la = new LinkedArray<>();

    @Before
    public void init() {
        la.addTail(100);
        la.addTail(93);
        la.addTail(33);
        la.addFirst(238);
        la.InsertIndex(9999, 4);
    }

    @Test
    public void testLa() throws Exception {
        System.out.println(la.getIndex(3));
        Node<Integer> root = la.getRoot();
        Node<Integer> iter = root;
        for (; iter != null; iter = iter.next) {
            System.out.println(iter.value);
        }
    }

    @Test
    public void testLaDel() throws Exception {
        Node<Integer> root = la.getRoot();
        Node<Integer> iter = root;
        for (; iter != null; iter = iter.next) {
            System.err.println(iter.value);
        }
        la.delIndex(1);
        la.delIndex(1);
        la.delIndex(1);
        la.delIndex(1);
        LinkedArray<Integer> newLa = la.delIndex(0);
        Node<Integer> root1 = newLa.getRoot();
        Node<Integer> iter2 = root1;
        for (; iter2 != null; iter2 = iter2.next) {
            System.out.println(iter2.value);
        }
    }
    

}
