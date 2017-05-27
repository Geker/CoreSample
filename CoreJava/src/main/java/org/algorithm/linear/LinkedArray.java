package org.algorithm.linear;

public class LinkedArray<T> {

    Node<T> root = null;
    Node<T> tail = null;
    volatile int cnt = 0;

    public int size() {
        return cnt;
    }

    public LinkedArray<T> addTail(T value) {
        if (root == null || tail == null) {
            root = new Node<>(value);
            tail = root;

        }
        else {
            Node<T> newNode = new Node<>(value);
            tail.setNext(newNode);
            newNode.setPre(tail);
            tail = newNode;

        }
        cnt++;
        return this;
    }

    public LinkedArray<T> addFirst(T value) {
        if (root == null || tail == null) {
            root = new Node<>(value);
            tail = root;

        }
        else {
            Node<T> newNode = new Node<>(value);
            root.setPre(newNode);
            newNode.setNext(root);
            root = newNode;

        }
        cnt++;
        return this;
    }

    public T getIndex(int i) {
        if (i > cnt)
            throw new IndexOutOfBoundsException("size:" + cnt);
        Node<T> itr = root;
        for (int j = 0; j < i; j++) {
            itr = itr.next;
        }
        return itr.value;
    }

    public LinkedArray<T> InsertIndex(T value, int i) {
        if (i == 0) {
            return addFirst(value);
        }
        if (i == cnt) {
            return addTail(value);
        }
        if (i > cnt)
            throw new IndexOutOfBoundsException("size:" + cnt);
        Node<T> itr = root;
        for (int j = 0; j < i; j++) {
            itr = itr.next;
        }
        Node<T> newNode = new Node<>(value);
        Node<T> pre = itr.pre;
        pre.setNext(newNode);
        newNode.setNext(itr);
        newNode.setPre(pre);
        itr.setPre(newNode);
        cnt++;
        return this;
    }

    public LinkedArray<T> delIndex(int i) {
        if (i > cnt)
            throw new IndexOutOfBoundsException("size:" + cnt);

        if (i == 0) {
            root = root.next;
            cnt--;
            if (root == null)
                tail = null;
            return this;
        }
        if (i == cnt) {
            tail = tail.pre;
            cnt--;
            if (tail == null)
                root = null;
            return this;
        }
        Node<T> itr = root;
        for (int j = 0; j < i; j++) {
            itr = itr.next;
        }
        Node<T> pre = itr.pre;
        pre.setNext(itr.next);
        if (itr.next != null)
            itr.next.setPre(pre);
        itr.setPre(null);
        cnt--;
        return this;
    }

    public LinkedArray<T> delFirst() {
        return delIndex(0);
    }

    public LinkedArray<T> delTail() {
        return delIndex(cnt);
    }
    public Node<T> getRoot() {
        return root;
    }

    public static class Node<T> {
        T value;
        Node<T> pre = null;
        Node<T> next = null;

        public Node(T node) {
            this.value = node;
        }

        public Node<T> getPre() {
            return pre;
        }

        private void setPre(Node<T> pre) {
            this.pre = pre;
        }

        public Node<T> getNext() {
            return next;
        }

        private void setNext(Node<T> next) {
            this.next = next;
        }

        @Override
        public String toString() {
            return "Node [value=" + value + ", pre=" + pre + ", next=" + next + "]";
        }

    }

    @Override
    public String toString() {
        return "LinkedArray [root=" + root + ", tail=" + tail + ", cnt=" + cnt + "]";
    }

}
