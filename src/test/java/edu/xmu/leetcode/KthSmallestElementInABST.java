package edu.xmu.leetcode;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

/**
 * Solution for {@link <a href="https://leetcode.com/problems/kth-smallest-element-in-a-bst/">Kth Smallest Element in a BST</a>}
 */
public class KthSmallestElementInABST {
	static class TreeNode {
		int val;
		TreeNode left;
		TreeNode right;

		TreeNode(int x) {
			val = x;
		}
	}

	public int kthSmallest(TreeNode root, int k) {
		List<Integer> values = midTraverse(root);
		return values.get(k - 1);
	}

	private List<Integer> midTraverse(TreeNode root) {
		List<Integer> list = new ArrayList<Integer>();
		if (root.left != null) {
			list.addAll(midTraverse(root.left));
		}
		list.add(root.val);
		if (root.right != null) {
			list.addAll(midTraverse(root.right));
		}
		return list;
	}

	@Test
	public void test() {
		TreeNode root = new TreeNode(1);
		root.left = new TreeNode(0);
		root.left.left = new TreeNode(-1);
		root.right = new TreeNode(5);
		root.right.left = new TreeNode(3);
		root.right.left.left = new TreeNode(2);
		root.right.left.right = new TreeNode(4);
		root.right.right = new TreeNode(6);
		System.out.println(kthSmallest(root, 6));
	}
}
