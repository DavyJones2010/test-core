package edu.xmu.leetcode;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

/**
 * Solution for {@link <a href="https://leetcode.com/problems/binary-tree-right-side-view/">Binary Tree Right Side View</a>}
 *
 */
public class BinaryTreeRightSideView {
	static class TreeNode {
		int val;
		TreeNode left;
		TreeNode right;

		TreeNode(int x) {
			val = x;
		}
	}

	public List<Integer> rightSideView(TreeNode root) {
		if (null == root) {
			return new ArrayList<Integer>();
		} else {
			List<TreeNode> nodes = new ArrayList<TreeNode>();
			nodes.add(root);
			return rightSideView(nodes);
		}
	}

	List<Integer> rightSideView(List<TreeNode> roots) {
		List<TreeNode> nexts = new ArrayList<TreeNode>();
		for (TreeNode node : roots) {
			if (node.right != null) {
				nexts.add(node.right);
			}
			if (node.left != null) {
				nexts.add(node.left);
			}
		}
		List<Integer> vals = new ArrayList<Integer>();
		if (nexts.isEmpty()) {
			vals.add(roots.get(0).val);
			return vals;
		} else {
			vals.add(roots.get(0).val);
			vals.addAll(rightSideView(nexts));
			return vals;
		}
	}

	@Test
	public void test() {
		TreeNode root = new TreeNode(1);
		root.left = new TreeNode(2);
		root.right = new TreeNode(3);
		root.right.left = new TreeNode(4);
		root.right.right = new TreeNode(5);
		System.out.println(rightSideView(root));

		root = new TreeNode(1);
		root.right = new TreeNode(2);
		root.right.left = new TreeNode(3);
		System.out.println(rightSideView(root));

		root = new TreeNode(1);
		root.left = new TreeNode(2);
		root.left.right = new TreeNode(5);
		root.left.left = new TreeNode(6);
		root.left.left.right = new TreeNode(7);
		root.right = new TreeNode(3);
		root.right.right = new TreeNode(4);
		System.out.println(rightSideView(root));
	}
}
