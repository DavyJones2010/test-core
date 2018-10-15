package edu.xmu.leetcode;

import org.junit.Test;

/**
 * {@link <a href="https://leetcode.com/problems/power-of-two/">Power of Two</a>} <br>
 */
public class PowerOfTwo {
	@Test
	public void compareVersionTest() {
		System.out.println(isPowerOfTwo(1));
		System.out.println(isPowerOfTwo(2));
		System.out.println(isPowerOfTwo(3));
		System.out.println(isPowerOfTwo(4));
		System.out.println(isPowerOfTwo(5));
		System.out.println(isPowerOfTwo(6));
		System.out.println(isPowerOfTwo(7));
		System.out.println(isPowerOfTwo(8));
	}

	public boolean isPowerOfTwo(int n) {
		return n <= 0 ? false : 0 == (n & (n - 1));
	}
}
