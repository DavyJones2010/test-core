package edu.xmu.leetcode;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

/**
 * {@link <a href="https://leetcode.com/problems/single-number-ii/">Single Number II</a>}
 *
 */
public class SingleNumberII {
	public int singleNumber(int[] nums) {
		Map<Integer, Integer> count = new HashMap<Integer, Integer>();

		for (int i : nums) {
			if (count.containsKey(i)) {
				if (2 == count.get(i)) {
					count.remove(i);
				} else {
					count.put(i, count.get(i) + 1);
				}
			} else {
				count.put(i, 1);
			}
		}
		return count.keySet().iterator().next();
	}

	@Test
	public void test() {
		System.out.println(singleNumber(new int[] { 1, 3, 1, 1 }));
		System.out.println(singleNumber(new int[] { 1, 3, 3, 3 }));
	}
}
