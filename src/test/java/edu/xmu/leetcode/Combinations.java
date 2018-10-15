package edu.xmu.leetcode;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

/**
 * Solution for {@link <a href="https://leetcode.com/submissions/detail/35326607/">Combinations</a>}
 */
public class Combinations {
	public List<List<Integer>> combine(int n, int k) {
		return select(1, n, k, new ArrayList<Integer>());
	}

	public List<List<Integer>> select(int startNum, int endNum, int k, List<Integer> prefix) {
		List<List<Integer>> values = new ArrayList<List<Integer>>();
		if (k == 1) {
			for (int i = startNum; i <= endNum; i++) {
				List<Integer> list = new ArrayList<Integer>();
				list.addAll(prefix);
				list.add(i);
				values.add(list);
			}
		} else {
			for (int i = startNum; i <= (endNum - k + 1); i++) {
				List<Integer> tmpPrefix = new ArrayList<Integer>(prefix);
				tmpPrefix.add(i);
				List<List<Integer>> tmp = select(i + 1, endNum, k - 1, tmpPrefix);
				values.addAll(tmp);
			}
		}
		return values;
	}

	@Test
	public void test() {
		System.out.println(combine(5, 2));
		System.out.println(combine(5, 1));
	}
}
