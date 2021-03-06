package edu.xmu.leetcode;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class BinaryTreePreOrderTraversal {
	static class TreeNode {
		int val;
		TreeNode left;
		TreeNode right;

		TreeNode(int x) {
			val = x;
		}
	}

	public List<Integer> preorderTraversal(TreeNode root) {
		List<Integer> list = new ArrayList<Integer>();
		if (null == root) {
			return list;
		}
		list.add(root.val);
		if (root.left != null) {
			list.addAll(preorderTraversal(root.left));
		}
		if (root.right != null) {
			list.addAll(preorderTraversal(root.right));
		}

		return list;
	}

	@Test
	public void test() {
		TreeNode root = new TreeNode(1);
		root.left = new TreeNode(2);
		root.right = new TreeNode(3);
		root.right.left = new TreeNode(4);
		root.right.right = new TreeNode(5);
		System.out.println(preorderTraversal(root));

		root = new TreeNode(1);
		root.right = new TreeNode(2);
		root.right.left = new TreeNode(3);
		System.out.println(preorderTraversal(root));

		root = new TreeNode(1);
		root.left = new TreeNode(2);
		System.out.println(preorderTraversal(root));
	}
}
