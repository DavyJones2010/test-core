package edu.xmu.leetcode;

import org.junit.Test;

/**
 * Solution for {@link <a href="https://leetcode.com/problems/path-sum/">Path Sum</a>}
 */
public class PathSum {
	static class TreeNode {
		int val;
		TreeNode left;
		TreeNode right;

		TreeNode(int x) {
			val = x;
		}
	}

	public boolean hasPathSum(TreeNode root, int sum) {
		if (root == null) {
			return false;
		} else if (root.left == null && root.right == null) {
			int val = root.val;
			int targetSum = sum - val;
			return targetSum == 0;
		} else {
			int val = root.val;
			int targetSum = sum - val;
			if (root.left == null) {
				return hasPathSum(root.right, targetSum);
			}
			if (root.right == null) {
				return hasPathSum(root.left, targetSum);
			}
			return hasPathSum(root.left, targetSum) || hasPathSum(root.right, targetSum);
		}
	}

	@Test
	public void test() {
		TreeNode root = new TreeNode(1);
		System.out.println(hasPathSum(root, 1));
		root.left = new TreeNode(2);
		root.right = new TreeNode(2);
		System.out.println(hasPathSum(root, 3));
		System.out.println(hasPathSum(null, 0));
		root.right.left = new TreeNode(2);
		root.right.right = new TreeNode(2);
		System.out.println(hasPathSum(root, 3));

		root = new TreeNode(5);
		root.left = new TreeNode(3);
		root.left.left = new TreeNode(7);
		root.left.right = new TreeNode(0);
		root.left.right.left = new TreeNode(0);
		root.right = new TreeNode(2);
		root.right.left = new TreeNode(6);
		System.out.println(hasPathSum(root, 15));
	}
}
