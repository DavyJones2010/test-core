package edu.xmu.leetcode;

import org.junit.Test;

/**
 * Solution for
 * {@link <a href="https://leetcode.com/problems/minimum-depth-of-binary-tree/">Minimum Depth of Binary Tree</a>}
 *
 */
public class MinDepthOfBinaryTree {
	static class TreeNode {
		int val;
		TreeNode left;
		TreeNode right;

		TreeNode(int x) {
			val = x;
		}
	}

	public int minDepth(TreeNode root) {
		return root == null ? 0 : traverse(root, 0, Integer.MAX_VALUE);
	}

	int traverse(TreeNode root, int currLevel, int min) {
		if (root.left == null && root.right == null) {
			return Math.min(currLevel + 1, min);
		}
		if (currLevel > min) {
			return Integer.MAX_VALUE;
		}
		int leftDepth = Integer.MAX_VALUE;
		if (root.left != null) {
			leftDepth = traverse(root.left, currLevel + 1, min);
		}
		int rightDepth = Integer.MAX_VALUE;
		if (root.right != null) {
			rightDepth = traverse(root.right, currLevel + 1, min);
		}
		return Math.min(leftDepth, rightDepth);
	}

	@Test
	public void test() {
		TreeNode root = new TreeNode(1);
		System.out.println(minDepth(root));

		root.left = new TreeNode(2);
		root.right = new TreeNode(3);
		root.right.left = new TreeNode(4);
		root.right.right = new TreeNode(5);
		System.out.println(minDepth(root));

		root = new TreeNode(1);
		root.right = new TreeNode(2);
		root.right.left = new TreeNode(3);
		System.out.println(minDepth(root));

		root = new TreeNode(1);
		root.left = new TreeNode(2);
		System.out.println(minDepth(root));
	}
}
