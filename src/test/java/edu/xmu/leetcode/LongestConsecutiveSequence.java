package edu.xmu.leetcode;

import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

import org.junit.Test;

/**
 * Solution for {@link <a href="https://leetcode.com/problems/longest-consecutive-sequence/">Longest Consecutive Sequence</a>}
 */
public class LongestConsecutiveSequence {

	public int longestConsecutive(int[] nums) {
		Set<Integer> set = new TreeSet<Integer>();
		for (int i : nums) {
			set.add(i);
		}
		if (set.size() == 1) {
			return 1;
		}
		Iterator<Integer> iterator = set.iterator();
		int curr = iterator.next();
		int next = iterator.next();
		int count = 1;
		int max = 1;

		if (next == (curr + 1)) {
			count++;
			max = 2;
		}
		while (iterator.hasNext()) {
			curr = next;
			next = iterator.next();
			if (next == (curr + 1)) {
				count++;
				if (count > max) {
					max = count;
				}
			} else {
				count = 1;
			}
		}
		return max;
	}

	@Test
	public void test() {
		int[] ints = new int[] { 1, 2, 3, 4, 10, 5, 11, 13, 12, 9, 8 };
		System.out.println(longestConsecutive(ints));
		ints = new int[] { 1, 3 };
		System.out.println(longestConsecutive(ints));
		ints = new int[] { 100, 4, 200, 1, 3, 2 };
		System.out.println(longestConsecutive(ints));
	}
}
