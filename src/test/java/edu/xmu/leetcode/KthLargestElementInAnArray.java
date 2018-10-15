package edu.xmu.leetcode;

import org.junit.Test;

/**
 * Solution for {@link <a href="https://leetcode.com/problems/kth-largest-element-in-an-array/">Kth Largest Element in an Array</a>}
 */
public class KthLargestElementInAnArray {
	public int findKthLargest(int[] nums, int k) {
		int[] largest = new int[k];
		for (int i = 0; i < k; i++) {
			largest[i] = Integer.MIN_VALUE;
		}
		for (int num : nums) {
			for (int j = 0; j < k; j++) {
				if (num > largest[j]) {
					for (int m = k - 1; m > j; m--) {
						largest[m] = largest[m - 1];
					}
					largest[j] = num;
					break;
				}
			}
		}

		return largest[k - 1];
	}

	@Test
	public void test() {
		int[] nums = new int[] { 3, 2, 1, 5, 6, 4 };
		System.out.println(findKthLargest(nums, 2));
		nums = new int[] { 3 };
		System.out.println(findKthLargest(nums, 1));
		nums = new int[] { 3, 5 };
		System.out.println(findKthLargest(nums, 2));
		System.out.println(findKthLargest(nums, 1));
	}
}
