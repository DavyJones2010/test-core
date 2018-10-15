package edu.xmu.leetcode;

import org.junit.Test;

/**
 * Solution for {@link <a href="https://leetcode.com/problems/find-peak-element/">Find Peak Element</a>}
 *
 */
public class FindPeakElement {

	public int findPeakElement(int[] nums) {
		return findPeakElement(nums, 0, nums.length - 1);
	}

	int findPeakElement(int[] nums, int low, int high) {
		if (low < 0 || low > nums.length - 1 || high < 0 || high > nums.length - 1 || low > high) {
			return -1;
		}
		if (nums[low] >= getValue(nums, low + 1) && nums[low] >= getValue(nums, low - 1)) {
			return low;
		}
		if (nums[high] >= getValue(nums, high - 1) && nums[high] >= getValue(nums, high + 1)) {
			return high;
		}
		int middle = (high + low) / 2;
		if (nums[middle] >= getValue(nums, middle - 1) && nums[middle] >= getValue(nums, middle + 1)) {
			return middle;
		}
		int leftPeak = findPeakElement(nums, low, middle - 1);
		if (leftPeak < 0) {
			int rightPeak = findPeakElement(nums, middle + 1, high);
			return rightPeak;
		}
		return leftPeak;
	}

	int getValue(int[] nums, int index) {
		return (index < 0 || index > nums.length - 1) ? Integer.MIN_VALUE : nums[index];
	}

	@Test
	public void test() {
		int[] ints = new int[] { 1, 2, 3, 1 };
		System.out.println(findPeakElement(ints));

		ints = new int[] {};
		System.out.println(findPeakElement(ints));

		ints = new int[] { 1 };
		System.out.println(findPeakElement(ints));

		ints = new int[] { -2147483648 };
		System.out.println(findPeakElement(ints));
		// System.out.println(Integer.MIN_VALUE);

		ints = new int[] { 3, 4, 3, 2, 1 };
		System.out.println(findPeakElement(ints));
	}
}
