package org.algorithm.linear;

import java.util.Stack;

public class LinkAdd {
    static class ListNode<T> {
        public T val;
        public ListNode<T> next;

        public ListNode(T t) {
            this.val = t;
        }
    }

    public ListNode<?> addTwoNumbers(ListNode<Integer> l1, ListNode<Integer> l2) {
        Stack<ListNode<Integer>> stack1 = new Stack<>();
        Stack<ListNode<Integer>> stack2 = new Stack<>();
        ListNode index1 = l1, index2 = l2;
        int carry = 0;// 进位符
        while (index1 != null || index2 != null) {
            if (index1 != null) {
                stack1.push(index1);
                index1 = index1.next;

            }
            if (index2 != null) {
                stack2.push(index2);
                index2 = index2.next;

            }
        }

        ListNode<Integer> longList = l1;
        if (stack1.size() < stack2.size()) {
            Stack<ListNode<Integer>> temp = stack1;
            stack1 = stack2;
            stack2 = temp;
            longList = l2;
        }

        while (!stack1.isEmpty()) {
            ListNode<Integer> temp1 = stack1.pop();
            int val2 = stack2.isEmpty() ? 0 : stack2.pop().val;
            int result = temp1.val + val2 + carry;
            temp1.val = result % 10;
            carry = result / 10;
        }
        if (carry == 1) {
            ListNode newNode = new ListNode(1);
            newNode.next = longList;
            return newNode;
        }
        else
            return longList;

    }
}
