package edu.xmu.leetcode;

import org.junit.Test;

/**
 * Solution for
 * {@link <a href="https://leetcode.com/problems/flatten-binary-tree-to-linked-list/">Flatten Binary Tree to Linked List</a>}
 *
 */
public class FlattenBinaryTreeToLinkedList {
	static class TreeNode {
		int val;
		TreeNode left;
		TreeNode right;

		TreeNode(int x) {
			val = x;
		}
	}

	public void flatten(TreeNode root) {
		if (null != root) {
			flattenWithReturn(root);
		}
	}

	private TreeNode flattenWithReturn(TreeNode root) {
		if (root.left == null && root.right == null) {
			return root;
		}
		if (root.left != null && root.right != null) {
			TreeNode r = root.right;
			root.right = root.left;
			root.left = null;
			TreeNode leftTail = flattenWithReturn(root.right);
			leftTail.right = r;
			return flattenWithReturn(r);
		} else if (root.left != null && root.right == null) {
			root.right = root.left;
			root.left = null;
			return flattenWithReturn(root.right);
		} else {
			return flattenWithReturn(root.right);
		}
	}

	@Test
	public void test() {
		TreeNode root = new TreeNode(1);
		root.left = new TreeNode(2);
		root.right = new TreeNode(3);
		root.right.left = new TreeNode(4);
		root.right.right = new TreeNode(5);
		flatten(root);
		System.out.println(root);

		root = new TreeNode(1);
		root.right = new TreeNode(2);
		root.right.left = new TreeNode(3);
		flatten(root);
		System.out.println(root);

		root = new TreeNode(1);
		root.left = new TreeNode(2);
		flatten(root);
		System.out.println(root);
	}
}
