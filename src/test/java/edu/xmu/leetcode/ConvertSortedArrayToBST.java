package edu.xmu.leetcode;

import org.junit.Test;

/**
 * {@link <a href="https://leetcode.com/problems/convert-sorted-array-to-binary-search-tree/">Convert Sorted Array to Binary Search Tree</a>}
 *
 */
public class ConvertSortedArrayToBST {
	static class TreeNode {
		int val;
		TreeNode left;
		TreeNode right;

		TreeNode(int x) {
			val = x;
		}

		@Override
		public String toString() {
			return "" + val;
		}
	}

	public TreeNode sortedArrayToBST(int[] nums) {
		return sortedArrayToBST(nums, 0, nums.length - 1);
	}

	TreeNode sortedArrayToBST(int[] nums, int start, int end) {
		TreeNode node = null;
		if (end - start == 0) {
			node = new TreeNode(nums[(end + start) / 2]);
		} else if (end - start > 1) {
			node = new TreeNode(nums[(end + start) / 2]);
			node.left = sortedArrayToBST(nums, start, (end + start) / 2 - 1);
			node.right = sortedArrayToBST(nums, (end + start) / 2 + 1, end);
		} else if ((end - start) == 1) {
			node = new TreeNode(nums[(end + start) / 2]);
			node.right = new TreeNode(nums[(end + start) / 2 + 1]);
		}
		return node;
	}

	@Test
	public void test() {
		int[] nums = new int[] {};
		TreeNode node = sortedArrayToBST(nums);

		nums = new int[] { 1, 2, 3, 4, 5 };
		node = sortedArrayToBST(nums);
		System.out.println(node);

		nums = new int[] { 1, 2, 3, 4, 5, 6, 7 };
		node = sortedArrayToBST(nums);
		System.out.println(node);
	}
}
