package edu.xmu.leetcode;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

/**
 * Solution for {@link <a href="https://leetcode.com/problems/binary-search-tree-iterator/">Binary Search Tree Iterator</a>} <br>
 * with O(1) time complexity and O(N) space compelxity
 */
public class BinarySearchTreeIterator {
	static class TreeNode {
		int val;
		TreeNode left;
		TreeNode right;

		TreeNode(int x) {
			val = x;
		}
	}

	static class BSTIterator {
		TreeNode root;
		List<TreeNode> nodes;
		int index = 0;

		public BSTIterator(TreeNode root) {
			this.root = root;
			if (null != root) {
				nodes = buildNodes(root);
			}
		}

		private List<TreeNode> buildNodes(TreeNode node) {
			List<TreeNode> nodes = new ArrayList<TreeNode>();
			if (node.left == null && node.right == null) {
				nodes.add(node);
				return nodes;
			}
			if (null != node.left) {
				nodes.addAll(buildNodes(node.left));
			}
			nodes.add(node);
			if (null != node.right) {
				nodes.addAll(buildNodes(node.right));
			}
			return nodes;
		}

		/** @return whether we have a next smallest number */
		public boolean hasNext() {
			return (root != null) && (index < nodes.size());
		}

		/** @return the next smallest number */
		public int next() {
			int nextVal = nodes.get(index).val;
			index++;
			return nextVal;
		}
	}

	@Test
	public void test() {
		// TreeNode root = new TreeNode(1);
		// root.left = new TreeNode(-1);
		// root.right = new TreeNode(3);
		// root.right.left = new TreeNode(2);
		// root.right.right = new TreeNode(5);

		BSTIterator iter = new BSTIterator(null);

		while (iter.hasNext()) {
			System.out.print(iter.next() + ", ");
		}
		// root = new TreeNode(1);
		// root.right = new TreeNode(2);
		// root.right.left = new TreeNode(3);
		//
		// root = new TreeNode(1);
		// root.left = new TreeNode(2);
	}
}
