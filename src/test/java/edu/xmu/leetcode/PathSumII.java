package edu.xmu.leetcode;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

/**
 * Solution for {@link <a href="https://leetcode.com/problems/path-sum-ii/">Path Sum II</a>}
 */
public class PathSumII {
	static class TreeNode {
		int val;
		TreeNode left;
		TreeNode right;

		TreeNode(int x) {
			val = x;
		}
	}

	public List<List<Integer>> pathSum(TreeNode root, int sum) {
		List<List<Integer>> list = new ArrayList<List<Integer>>();
		if (root == null) {
			return list;
		} else if (root.left == null && root.right == null) {
			int val = root.val;
			if (sum == val) {
				List<Integer> a = new ArrayList<Integer>();
				a.add(val);
				list.add(a);
			}
			return list;
		} else {
			int val = root.val;
			int targetSum = sum - val;
			if (root.left != null) {
				List<List<Integer>> sub = pathSum(root.left, targetSum);
				for (List<Integer> a : sub) {
					a.add(0, val);
				}
				list.addAll(sub);
			}
			if (root.right != null) {
				List<List<Integer>> sub = pathSum(root.right, targetSum);
				for (List<Integer> a : sub) {
					a.add(0, val);
				}
				list.addAll(sub);
			}
		}
		return list;
	}

	@Test
	public void test() {
		TreeNode root = new TreeNode(1);
		System.out.println(pathSum(root, 1));
		root.left = new TreeNode(2);
		root.right = new TreeNode(2);
		System.out.println(pathSum(root, 3));
		System.out.println(pathSum(null, 0));
		root.right.left = new TreeNode(2);
		root.right.right = new TreeNode(2);
		System.out.println(pathSum(root, 3));

		root = new TreeNode(5);
		root.left = new TreeNode(3);
		root.left.left = new TreeNode(7);
		root.left.right = new TreeNode(0);
		root.left.right.left = new TreeNode(0);
		root.right = new TreeNode(2);
		root.right.left = new TreeNode(6);
		System.out.println(pathSum(root, 15));
	}
}
