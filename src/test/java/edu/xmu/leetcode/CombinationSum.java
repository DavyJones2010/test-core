package edu.xmu.leetcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

/**
 * Solution for {@link <a href="https://leetcode.com/problems/combination-sum-iii/">Combination Sum III</a>} using backtracking <br>
 * Actually backtracking = traversing + branchcutting <br>
 * Solution Spaces: a complete tree with the wideth of candidates.size().
 */
public class CombinationSum {
	public List<List<Integer>> combinationSum(int[] candidates, int target) {
		if (candidates.length == 0) {
			return new ArrayList<List<Integer>>();
		} else {
			Arrays.sort(candidates);
			return combinationSumWithOrderding(candidates, 0, target);
		}
	}

	List<List<Integer>> combinationSumWithOrderding(int[] candidates, int startIndex, int target) {
		List<List<Integer>> sets = new ArrayList<List<Integer>>();
		for (int i = startIndex; i < candidates.length; i++) {
			if (target > candidates[i]) {
				List<List<Integer>> subsets = combinationSumWithOrderding(candidates, i, target - candidates[i]);
				for (List<Integer> subset : subsets) {
					subset.add(0, candidates[i]);
					sets.add(subset);
				}
			} else if (target == candidates[i]) {
				List<Integer> set = new ArrayList<Integer>();
				set.add(candidates[i]);
				sets.add(set);
			} else {
				break;
			}
		}
		return sets;
	}

	@Test
	public void test() {
		int[] candidates = new int[] { 2, 3, 6, 7 };
		int target = 7;
		System.out.println(combinationSum(candidates, target));
	}
}
