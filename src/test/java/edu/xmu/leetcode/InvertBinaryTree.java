package edu.xmu.leetcode;

import org.junit.Test;

public class InvertBinaryTree {
	public class TreeNode {
		int val;
		TreeNode left;
		TreeNode right;

		TreeNode(int x) {
			val = x;
		}

		@Override
		public String toString() {
			return "TreeNode [val=" + val + "]";
		}

	}

	public TreeNode invertTree(TreeNode root) {
		if(null == root){
			return null;
		}
		if (root.left != null || root.right != null) {
			TreeNode tmp = root.left;
			root.left = root.right;
			root.right = tmp;
		}
		if (root.left != null) {
			invertTree(root.left);
		}
		if (root.right != null) {
			invertTree(root.right);
		}

		return root;
	}

	@Test
	public void test() {
		TreeNode root = new TreeNode(4);
		root.left = new TreeNode(2);
		root.left.left = new TreeNode(1);
		root.left.right = new TreeNode(3);

		root.right = new TreeNode(7);
		root.right.left = new TreeNode(6);
		root.right.right = new TreeNode(9);
		invertTree(root);

		System.out.println(root);
	}
}
