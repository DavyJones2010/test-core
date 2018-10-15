package edu.xmu.leetcode;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

public class ContainsDuplicate {

	public boolean containsDuplicate(int[] nums) {
		Set<Integer> set = new HashSet<Integer>();
		for (int num : nums) {
			if (set.contains(num)) {
				return true;
			} else {
				set.add(num);
			}
		}
		return false;
	}

	@Test
	public void test() {
		assertFalse(containsDuplicate(new int[] { 1, 2, 3 }));
		assertTrue(containsDuplicate(new int[] { 1, 2, 3, 1 }));
	}
}
