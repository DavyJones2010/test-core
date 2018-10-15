package edu.xmu.leetcode;

import org.junit.Test;

/**
 * Solution for: {@link <a href="https://leetcode.com/problems/find-minimum-in-rotated-sorted-array/">Find Minimum in Rotated Sorted Array</a>} <br>
 */
public class FindMinimumInRotatedSortedArray {
	public int findMin(int[] nums) {
		return findMin(nums, 0, nums.length - 1);
	}

	int findMin(int[] nums, int start, int end) {
		if (end == 0 || start == end) {
			return nums[end];
		}
		if (end - start == 1) {
			return Math.min(nums[start], nums[end]);
		}

		int pivot = (start + end) / 2;
		int pivotVal = nums[pivot];
		int leftVal = nums[pivot - 1];
		int rightVal = nums[pivot + 1];

		if (pivotVal < leftVal && pivotVal < rightVal) {
			return pivotVal;
		} else {
			return Math.min(findMin(nums, start, pivot - 1), findMin(nums, pivot + 1, end));
		}
	}

	@Test
	public void test() {
		int[] array = new int[] { 4, 5, 6, 7, 0, 1, 2 };
		System.out.println(findMin(array));

		array = new int[] { 3, 2, 1, 0, 8, 7, 6, 5, 4 };
		System.out.println(findMin(array));

		array = new int[] { 0 };
		System.out.println(findMin(array));
	}
}
