package edu.xmu.leetcode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Test;

/**
 * Solution for {@link <a href="https://leetcode.com/problems/happy-number/">Happy Number</a>}
 *
 */
public class HappyNumber {
	final static Map<Integer, Boolean> cache = new HashMap<Integer, Boolean>();
	static {
		cache.put(1, true);
		cache.put(2, false);
	}

	public boolean isHappy(int n) {
		return isHappy(n, new HashSet<Integer>());
	}

	boolean isHappy(int n, Set<Integer> traversedCache) {
		if (cache.containsKey(n)) {
			return cache.get(n);
		}
		if (traversedCache.contains(n)) {
			// found loop
			cache.put(n, false);
			return false;
		}
		traversedCache.add(n);
		List<Integer> splits = split(n);
		int sum = calSum(splits);
		boolean is = isHappy(sum, traversedCache);
		cache.put(n, is);
		return is;
	}

	int calSum(List<Integer> splits) {
		return splits.stream().mapToInt(e -> e.intValue() * e.intValue()).sum();
	}

	List<Integer> split(int n) {
		List<Integer> list = new ArrayList<Integer>();
		while (n > 0) {
			int i = n % 10;
			list.add(i);
			n = n / 10;
		}
		return list;
	}

	@Test
	public void test() {
		// System.out.println(split(10));
		// System.out.println(split(101));
		System.out.println(isHappy(1));

		System.out.println(isHappy(2));

		System.out.println(isHappy(3));

		System.out.println(isHappy(4));
	}
}
