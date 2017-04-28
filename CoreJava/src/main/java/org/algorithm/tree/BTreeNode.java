package org.algorithm.tree;

public class BTreeNode<T> {
    T value;
    BTreeNode<T> left;
    BTreeNode<T> right;

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    public BTreeNode<T> getLeft() {
        return left;
    }

    public void setLeft(BTreeNode<T> left) {
        this.left = left;
    }

    public BTreeNode<T> getRight() {
        return right;
    }

    public void setRight(BTreeNode<T> right) {
        this.right = right;
    }

};
