package edu.xmu.leetcode;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

/**
 * Solution for {@link <a href="https://leetcode.com/problems/triangle/">Triangle</a>}
 */
public class Triangle {
	public int minimumTotal(List<List<Integer>> triangle) {
		if (triangle.isEmpty()) {
			return 0;
		}
		if (triangle.size() == 1) {
			int min = Integer.MAX_VALUE;
			for (int i : triangle.get(0)) {
				min = Math.min(min, i);
			}
			return min;
		}
		List<Integer> first = triangle.get(0);
		List<Integer> second = triangle.get(1);
		for (int i = 0; i < second.size(); i++) {
			if (i == 0) {
				int prev = first.get(0);
				int curr = second.get(i);
				second.set(i, prev + curr);
			} else if (i == (second.size() - 1)) {
				int prev = first.get(i - 1);
				int curr = second.get(i);
				second.set(i, prev + curr);
			} else {
				int prev = Math.min(first.get(i - 1), first.get(i));
				int curr = second.get(i);
				second.set(i, prev + curr);
			}
		}
		triangle.remove(0);
		return minimumTotal(triangle);
	}

	@Test
	public void test() {
		List<List<Integer>> triangle = new ArrayList<List<Integer>>();

		List<Integer> first = new ArrayList<Integer>();
		first.add(2);
		triangle.add(first);

		first = new ArrayList<Integer>();
		first.add(3);
		first.add(4);
		triangle.add(first);

		first = new ArrayList<Integer>();
		first.add(6);
		first.add(5);
		first.add(7);
		triangle.add(first);

		first = new ArrayList<Integer>();
		first.add(4);
		first.add(1);
		first.add(8);
		first.add(3);
		triangle.add(first);

		System.out.println(minimumTotal(triangle));
		// System.out.println(combine(5, 1));
	}
}
