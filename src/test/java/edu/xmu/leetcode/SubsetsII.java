package edu.xmu.leetcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

/**
 * Solution for {@link <a href="https://leetcode.com/problems/subsets-ii/">Subsets II</a>} using backtracking <br>
 * Solution Spaces: complete binary tree
 */
public class SubsetsII {
	public List<List<Integer>> subsetsWithDup(int[] nums) {
		Arrays.sort(nums);
		return subsetsWithOrdering(nums);
	}

	List<List<Integer>> subsetsWithOrdering(int[] nums) {
		List<List<Integer>> sets = new ArrayList<List<Integer>>();
		if (nums.length == 0) {
			return sets;
		} else if (nums.length == 1) {
			List<Integer> list = new ArrayList<Integer>();
			list.add(nums[0]);
			sets.add(list);
			sets.add(new ArrayList<Integer>());
			return sets;
		} else {
			int target = nums[nums.length - 1];
			int[] copyOfNums = Arrays.copyOf(nums, nums.length - 1);
			List<List<Integer>> subsets = subsetsWithOrdering(copyOfNums);
			for (List<Integer> sub : subsets) {
				List<Integer> copyOfSub = new ArrayList<Integer>(sub);
				copyOfSub.add(target);
				sets.add(copyOfSub);
				sets.add(sub);
			}
		}
		return sets;
	}

	@Test
	public void test() {
		int[] nums = new int[] { 1, 2, 3 };
		System.out.println(subsetsWithDup(nums));
		nums = new int[] {};
		System.out.println(subsetsWithDup(nums));
		nums = new int[] { 4, 1, 0 };
		System.out.println(subsetsWithDup(nums));
	}
}
