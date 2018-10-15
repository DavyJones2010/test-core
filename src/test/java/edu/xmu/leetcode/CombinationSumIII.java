package edu.xmu.leetcode;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

/**
 * Solution for {@link <a href="https://leetcode.com/problems/combination-sum-iii/">Combination Sum III</a>}
 */
public class CombinationSumIII {
	public List<List<Integer>> combinationSum3(int k, int n) {
		return combinationSum3(k, n, 1);
	}

	List<List<Integer>> combinationSum3(int k, int n, int currMin) {
		List<List<Integer>> list = new ArrayList<List<Integer>>();
		if (k == 1) {
			if (n >= 1 && n <= 9 && n >= currMin) {
				List<Integer> a = new ArrayList<Integer>();
				a.add(n);
				list.add(a);
			}
		} else {
			for (int i = currMin; i <= n / k; i++) {
				List<List<Integer>> combinationSum3 = combinationSum3(k - 1, n - i, i + 1);
				for (List<Integer> a : combinationSum3) {
					a.add(0, i);
					list.add(a);
				}
			}
		}
		return list;
	}

	@Test
	public void test() {
		System.out.println(combinationSum3(3, 7));
		System.out.println(combinationSum3(3, 9));
		System.out.println(combinationSum3(2, 18));
		System.out.println(combinationSum3(3, 15));
		System.out.println(combinationSum3(9, 45));
	}
}
