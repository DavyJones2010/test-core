package edu.xmu.leetcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import com.google.common.collect.Lists;

/**
 * A sample of how to get all possibilities of permutations <br>
 * The same strategy could be used for {@link <a href="https://leetcode.com/problems/unique-binary-search-trees/">Unique Binary Search Tree</a>} and
 * {@link <a href="https://leetcode.com/problems/climbing-stairs/">Climbing Stairs</a>}
 */
public class Permutation {

	/**
	 * Solution for {@link <a href="https://leetcode.com/problems/permutations/">Permutations</a>}
	 */
	public List<List<Integer>> permute(int[] nums) {
		List<List<Integer>> res = new ArrayList<List<Integer>>();
		if (1 == nums.length) {
			List<Integer> pos = new ArrayList<Integer>();
			pos.add(Integer.valueOf(nums[0]));
			res.add(pos);
		} else {
			int[] subNums = Arrays.copyOf(nums, nums.length - 1);
			int target = nums[nums.length - 1];
			List<List<Integer>> set = permute(subNums);
			for (List<Integer> sub : set) {
				for (int j = 0; j <= sub.size(); j++) {
					List<Integer> copyOfSub = new ArrayList<Integer>(sub);
					copyOfSub.add(j, target);
					res.add(copyOfSub);
				}
			}
		}

		return res;
	}

	/**
	 * Calculate permutation of (1, 2, ..., i)
	 */
	List<List<Integer>> perm(int i) {
		List<List<Integer>> res = new ArrayList<List<Integer>>();
		if (i == 1) {
			List<Integer> pos = new ArrayList<Integer>();
			pos.add(Integer.valueOf(1));
			res.add(pos);
		} else {
			List<List<Integer>> set = perm(i - 1);
			for (List<Integer> sub : set) {
				for (int j = 0; j < i; j++) {
					List<Integer> copyOfSub = Lists.newArrayList(sub);
					copyOfSub.add(j, i);
					res.add(copyOfSub);
				}
			}
		}
		return res;
	}

	@Test
	public void test() {
		System.out.println(permute(new int[] { 1, 2, 4 }));
	}
}
