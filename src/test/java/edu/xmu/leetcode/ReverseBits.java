package edu.xmu.leetcode;

import org.junit.Test;

/**
 * Solution for {@link <a href="https://leetcode.com/problems/reverse-bits/"></a>}
 *
 */
public class ReverseBits {
	public int reverseBits(int n) {
		return Integer.reverse(n);
	}

	@Test
	public void test() {
		System.out.println(reverseBits(43261596));
	}
}
