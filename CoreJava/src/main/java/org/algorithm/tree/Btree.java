package org.algorithm.tree;

import java.util.Stack;

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
		genNodes(rootNode, 10);
		Btree<String> tree = new Btree<>(rootNode);
//		CruiseTree(tree.root);
		preOrder(tree.root);
		System.out.println("----------");
		CruiseTree(tree.root);
	}

	private static void genNodes(BTreeNode<String> rootNode, int level) {
		if (level == 0) {
			rootNode.setValue("V:" + level);
			BTreeNode<String> left = new BTreeNode<>();
			left.setValue("L:" + level);
			rootNode.setLeft(left);
			BTreeNode<String> right = new BTreeNode<>();
			right.setValue("R:" + level);
			rootNode.setRight(right);
		} else {
			rootNode.left = new BTreeNode<>();
			rootNode.right = new BTreeNode<>();
			genNodes(rootNode.left, level - 1);
			rootNode.setValue("V:" + level);
			genNodes(rootNode.right, level - 1);

		}
	}

	static private void CruiseTree(BTreeNode<String> rootNode) {
		if (rootNode == null)
			return;
		else {

			CruiseTree(rootNode.left);
			System.out.print(rootNode.value + " ");
			CruiseTree(rootNode.right);

		}
	}

	public static void preOrder(BTreeNode<String>   root) {
		Stack<BTreeNode<String>> s = new Stack<BTreeNode<String>>();
		s.push(root);
		BTreeNode<String>  p = null;
		while (!s.empty()) {
			p = s.pop();
			if (p != null) {
				System.out.print(p.value + " ");
				s.push(p.right);
				s.push(p.left);
			}
		}
	}

}
