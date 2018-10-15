package edu.xmu.leetcode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

/**
 * Solution for {@link <a href="https://leetcode.com/problems/binary-tree-zigzag-level-order-traversal/">Binary Tree Zigzag Level Order Traversal</a>} <br>
 */
public class BinaryTreeZigzagLevelOrderTraversal {
	static class TreeNode {
		int val;
		TreeNode left;
		TreeNode right;

		TreeNode(int x) {
			val = x;
		}
	}

	public List<List<Integer>> zigzagLevelOrder(TreeNode root) {
		if (null == root) {
			return new ArrayList<List<Integer>>();
		} else {
			List<TreeNode> roots = new ArrayList<TreeNode>();
			roots.add(root);
			return zigzagLevelOrder(roots, true);
		}
	}

	List<List<Integer>> zigzagLevelOrder(List<TreeNode> roots, boolean fromLeftToRight) {
		List<TreeNode> nextLevel = new ArrayList<TreeNode>();
		List<Integer> currLevelValue = new ArrayList<Integer>();
		List<List<Integer>> list = new ArrayList<List<Integer>>();
		if (roots.isEmpty()) {
			return list;
		}
		if (fromLeftToRight) {
			for (TreeNode node : roots) {
				currLevelValue.add(node.val);
				if (node.left != null) {
					nextLevel.add(node.left);
				}
				if (node.right != null) {
					nextLevel.add(node.right);
				}
			}
			list.add(currLevelValue);
			list.addAll(zigzagLevelOrder(nextLevel, false));
		} else {
			for (TreeNode node : roots) {
				if (node.left != null) {
					nextLevel.add(node.left);
				}
				if (node.right != null) {
					nextLevel.add(node.right);
				}
				currLevelValue.add(node.val);
			}
			Collections.reverse(currLevelValue);
			list.add(currLevelValue);
			list.addAll(zigzagLevelOrder(nextLevel, true));
		}
		return list;
	}

	@Test
	public void test() {
		TreeNode root = new TreeNode(1);

		// root.left = new TreeNode(-1);
		// root.left.left = new TreeNode(4);
		//
		// root.right = new TreeNode(3);
		// root.right.left = new TreeNode(2);
		// root.right.right = new TreeNode(5);
		System.out.println(zigzagLevelOrder(root));
	}
}
