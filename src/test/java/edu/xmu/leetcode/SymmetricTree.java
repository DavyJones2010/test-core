package edu.xmu.leetcode;

import org.junit.Test;

public class SymmetricTree {
	static class TreeNode {
		int val;
		TreeNode left;
		TreeNode right;

		TreeNode(int x) {
			val = x;
		}
	}

	public boolean isSymmetric(TreeNode root) {
		return root == null ? true : isSymmetric(root.left, root.right);
	}

	boolean isSymmetric(TreeNode left, TreeNode right) {
		if (left == null && right == null) {
			return true;
		} else if ((left == null && right != null) || (left != null && right == null)) {
			return false;
		} else {
			return (left.val == right.val) && isSymmetric(left.left, right.right) && isSymmetric(left.right, right.left);
		}
	}

	@Test
	public void test() {
		TreeNode root = new TreeNode(1);
		root.left = new TreeNode(2);
		root.right = new TreeNode(3);
		root.right.left = new TreeNode(4);
		root.right.right = new TreeNode(5);
		System.out.println(isSymmetric(root));

		root = new TreeNode(1);
		root.right = new TreeNode(2);
		root.right.left = new TreeNode(3);
		System.out.println(isSymmetric(root));

		root = new TreeNode(1);
		root.left = new TreeNode(2);
		System.out.println(isSymmetric(root));
	}
}
