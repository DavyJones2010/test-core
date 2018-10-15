package edu.xmu.leetcode;

import org.junit.Test;

/**
 * Solution for {@link <a href="https://leetcode.com/problems/factorial-trailing-zeroes/">Factorial Trailing Zeroes</a>}
 */
public class FactorialTrailingZeroes {
	public int trailingZeroes(int n) {
		int count = 0;
		int normalized = n - n % 5;
		if (normalized == 0) {
			return count;
		}
		count = normalized / 5;
		count += trailingZeroes(count - count % 5);

		return count;
	}

	@Test
	public void test() {
		System.out.println(trailingZeroes(1));
		System.out.println(trailingZeroes(2));
		System.out.println(trailingZeroes(5));
		System.out.println(trailingZeroes(7));
		System.out.println(trailingZeroes(10));
		System.out.println(trailingZeroes(15));
		System.out.println(trailingZeroes(25));
		System.out.println(trailingZeroes(50));
		System.out.println(trailingZeroes(125));
		long a = System.currentTimeMillis();
		System.out.println(a);
		System.out.println(trailingZeroes(1808548329));
		System.out.println(System.currentTimeMillis() - a);
		// System.out.println(trailingZeroes(50));
	}
}
