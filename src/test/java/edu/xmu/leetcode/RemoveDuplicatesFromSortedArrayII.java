package edu.xmu.leetcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

/**
 * Solution for {@link <a href="https://leetcode.com/problems/remove-duplicates-from-sorted-array-ii/">Remove Duplicates from Sorted Array II</a>}
 */
public class RemoveDuplicatesFromSortedArrayII {

	public int removeDuplicates(int[] nums) {
		if (nums.length <= 1) {
			return nums.length;
		}
		int[] back = Arrays.copyOf(nums, nums.length);
		List<Integer> indexToBeRemoved = new ArrayList<Integer>();
		int totalDupCount = 0;
		int dupCount = 0;
		int currVal = nums[1];
		int prevVal = nums[0];
		for (int i = 1; i < nums.length; i++) {
			currVal = nums[i];
			if (prevVal == currVal) {
				dupCount++;
				if (dupCount >= 2) {
					indexToBeRemoved.add(i);
					totalDupCount++;
				}
			} else {
				dupCount = 0;
			}
			prevVal = currVal;
		}
		int realIndex = 0;
		for (int i = 0; i < back.length; i++) {
			if (!indexToBeRemoved.contains(i)) {
				nums[realIndex] = back[i];
				realIndex++;
			}
		}
		return nums.length - totalDupCount;
	}

	@Test
	public void test() {
		int[] nums = new int[] { 1, 1, 1, 2, 2, 2, 3 };
		System.out.println(removeDuplicates(nums));
		nums = new int[] { 1, 1, 1, 1, 1 };
		System.out.println(removeDuplicates(nums));
	}
}
