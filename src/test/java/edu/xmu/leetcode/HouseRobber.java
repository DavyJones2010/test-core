package edu.xmu.leetcode;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

/**
 * Solution for {@link <a href="https://leetcode.com/problems/house-robber/">House Robber</a>} <br>
 * Dynamic Programming, state transition equation: f(x) = Math.max(v[x] + f(x-2), f(x-1));
 */
public class HouseRobber {
	Map<Integer, Integer> cache;

	public int rob(int[] nums) {
		cache = new HashMap<Integer, Integer>();
		if (nums.length == 0) {
			return 0;
		} else {
			return rob(nums, nums.length - 1);
		}
	}

	int rob(int[] nums, int x) {
		if (cache.containsKey(x)) {
			return cache.get(x);
		}
		if (x == 1) {
			int max = Math.max(nums[x], nums[x - 1]);
			cache.put(x, max);
			return max;
		}
		if (x == 0) {
			cache.put(x, nums[x]);
			return nums[x];
		}
		int max = Math.max(rob(nums, x - 2) + nums[x], rob(nums, x - 1));
		cache.put(x, max);
		return max;
	}

	@Test
	public void test() {
		int[] nums = new int[] { 1, 2 };
		System.out.println(rob(nums));
		nums = new int[] { 1, 2, 3 };
		System.out.println(rob(nums));
		nums = new int[] { 1, 2, 3, 8, 5 };
		System.out.println(rob(nums));
	}
}
