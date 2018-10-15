package edu.xmu.leetcode;

import org.junit.Test;

/**
 * {@link <a href="https://leetcode.com/problems/search-insert-position/">Search Insert Position</a>}
 *
 */
public class SearchInsertPosition {
	public int searchInsert(int[] nums, int target) {
		return getIndex(nums, 0, nums.length - 1, target);
	}

	public int getIndex(int[] nums, int start, int end, int target) {
		int index;
		if (nums[start] >= target) {
			index = start;
			return index;
		} else {
			start++;
		}

		if (nums[end] == target) {
			index = end;
			return index;
		} else if (nums[end] < target) {
			index = end + 1;
			return index;
		} else {
			end--;
		}

		return getIndex(nums, start, end, target);
	}

	@Test
	public void test() {
		System.out.println(searchInsert(new int[] { 1, 3 }, 5));
		System.out.println(searchInsert(new int[] { 1, 3 }, 2));
		System.out.println(searchInsert(new int[] { 1, 3 }, 7));
		System.out.println(searchInsert(new int[] { 1, 3 }, 0));
	}
}
