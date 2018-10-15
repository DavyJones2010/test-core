package edu.xmu.leetcode;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

public class UniqueBST {
	Map<Integer, Integer> cache = new HashMap<Integer, Integer>();

	public int numTrees(int n) {
		if (n == 1 || n == 0) {
			return 1;
		}
		if (cache.containsKey(n)) {
			return cache.get(n);
		} else {
			int c = 0;
			for (int i = 0; i < n; i++) {
				c += numTrees(i) * numTrees(n - i - 1);
			}
			cache.put(n, c);
			return c;
		}
	}

	@Test
	public void test() {
		System.out.println(numTrees(2));
		System.out.println(numTrees(3));
		System.out.println(numTrees(4));
		long time = System.nanoTime();
		// System.out.println(time);
		System.out.println(numTrees(19));
		System.out.println(System.nanoTime() - time);
	}
}
