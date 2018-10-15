package edu.xmu.leetcode;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

/**
 * Solution for {@link <a href="https://leetcode.com/submissions/detail/34708398/">Binary Tree Postorder Traversal </a>}
 */
public class BinaryTreePostorderTraversal {
	static class TreeNode {
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

	public List<Integer> postorderTraversal(TreeNode root) {
		List<Integer> list = new ArrayList<Integer>();
		if (null == root) {
			return list;
		}
		list.addAll(postorderTraversal(root.left));
		list.addAll(postorderTraversal(root.right));
		list.add(root.val);
		return list;
	}

	@Test
	public void test() {
		TreeNode root = new TreeNode(1);
		root.right = new TreeNode(2);
		root.right.left = new TreeNode(3);
		List<Integer> list = postorderTraversal(root);
		System.out.println(list);
	}
}
