package org.algorithm.tree;

public class Btree<T> {

    private BTreeNode<T> root;

    public Btree() {
    }

    public Btree(BTreeNode<T> root) {
        this.root = root;
    }

    public static void main(String[] args) {
        BTreeNode<String> rootNode = new BTreeNode<>();
        rootNode.setValue("hello");
        BTreeNode<String> left = new BTreeNode<>();
        rootNode.setLeft(left);
        Btree<String> tree = new Btree<>(rootNode);
    }
}
