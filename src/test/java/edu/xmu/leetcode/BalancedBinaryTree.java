package edu.xmu.leetcode;

import org.junit.Test;

public class BalancedBinaryTree {
	static class TreeNode {
		int val;
		TreeNode left;
		TreeNode right;

		TreeNode(int x) {
			val = x;
		}
	}

	public boolean isBalanced(TreeNode root) {
		if (null == root) {
			return true;
		} else {
			int leftHeight = getHeight(root.left);
			int rightHeight = getHeight(root.right);

			return leftHeight != Integer.MAX_VALUE && rightHeight != Integer.MAX_VALUE && Math.abs(leftHeight - rightHeight) <= 1;
		}
	}

	int getHeight(TreeNode node) {
		if (node == null) {
			return 0;
		}
		int leftHeight = getHeight(node.left);
		int rightHeight = getHeight(node.right);
		if (leftHeight == Integer.MAX_VALUE || rightHeight == Integer.MAX_VALUE || Math.abs(leftHeight - rightHeight) > 1) {
			return Integer.MAX_VALUE;
		}

		return Math.max(leftHeight, rightHeight) + 1;
	}

	@Test
	public void test() {
		TreeNode root = new TreeNode(1);
		root.left = new TreeNode(2);
		root.right = new TreeNode(3);
		root.right.left = new TreeNode(4);
		root.right.right = new TreeNode(5);
		root.right.left.right = new TreeNode(4);
		System.out.println(isBalanced(root));

		root = new TreeNode(1);
		root.right = new TreeNode(2);
		root.right.left = new TreeNode(3);
		System.out.println(isBalanced(root));

		root = new TreeNode(1);
		root.left = new TreeNode(2);
		System.out.println(isBalanced(root));
	}
}
