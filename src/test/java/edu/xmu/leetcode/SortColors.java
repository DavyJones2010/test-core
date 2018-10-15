package edu.xmu.leetcode;

import java.util.Arrays;

import org.junit.Test;

/**
 * Solution for {@link <a href="https://leetcode.com/problems/best-time-to-buy-and-sell-stock/">Best Time to Buy and Sell Stock</a>}
 */
public class SortColors {
	public void sortColors(int[] nums) {
		int redCount = 0;
		int whiteCount = 0;
		int bludCount = 0;
		for (int i = 0; i < nums.length; i++) {
			if (nums[i] == 0) {
				redCount++;
			}
			if (nums[i] == 1) {
				whiteCount++;
			}
			if (nums[i] == 2) {
				bludCount++;
			}
		}
		for (int i = 0; i < nums.length; i++) {
			if (redCount != 0) {
				nums[i] = 0;
				redCount--;
			} else if (whiteCount != 0) {
				nums[i] = 1;
				whiteCount--;
			} else if (bludCount != 0) {
				nums[i] = 2;
				bludCount--;
			}
		}
	}

	@SuppressWarnings("unused")
	public void sortColors_enhanced(int[] nums) {
		int redCount = 0;
		int whiteCount = 0;
		int blueCount = 0;
		int frontCursor = 0;
		int endCursor = nums.length - 1;

		for (int i = 0; i < nums.length; i++) {
			while (nums[i] == 0) {
				redCount++;
				frontCursor++;
				i++;
			}
			while (nums[i] == 2) {
				blueCount++;
				endCursor--;
			}
		}
	}

	@Test
	public void test() {
		int[] array = new int[] { 0, 1, 2, 0, 2, 1 };
		sortColors(array);
		System.out.println(Arrays.toString(array));
	}
}
