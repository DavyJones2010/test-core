package edu.xmu.leetcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

/**
 * Solution for {@link <a href="https://leetcode.com/problems/permutations-ii/">Permutations II</a>}
 */
public class PermutationII {

	public List<List<Integer>> permuteUnique(int[] nums) {
		Map<String, List<Integer>> res = permuteUniqueMap(nums);
		return new ArrayList<List<Integer>>(res.values());
	}

	public Map<String, List<Integer>> permuteUniqueMap(int[] nums) {
		Map<String, List<Integer>> res = new HashMap<String, List<Integer>>();
		if (1 == nums.length) {
			List<Integer> pos = new ArrayList<Integer>();
			pos.add(Integer.valueOf(nums[0]));
			res.put(Arrays.toString(pos.toArray()), pos);
		} else {
			int[] subNums = Arrays.copyOf(nums, nums.length - 1);
			int target = nums[nums.length - 1];
			List<List<Integer>> set = permuteUnique(subNums);
			for (List<Integer> sub : set) {
				for (int j = 0; j <= sub.size(); j++) {
					List<Integer> copyOfSub = new ArrayList<Integer>(sub);
					copyOfSub.add(j, target);
					String newKey = Arrays.toString(copyOfSub.toArray());
					if (!res.containsKey(newKey)) {
						res.put(newKey, copyOfSub);
					}
				}
			}
		}

		return res;
	}

	@Test
	public void test() {
		System.out.println(permuteUnique(new int[] { 1, 2, 1 }));
	}
}
