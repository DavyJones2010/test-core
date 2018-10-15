package edu.xmu.leetcode;

import org.junit.Test;

public class NumberOfOneBits {
	// you need to treat n as an unsigned value
	public int hammingWeight(int n) {
		int count = 0;
		boolean shouldConvert = false;
		if (n < 0) {
			n = ~n;
			shouldConvert = true;
		}
		while (n > 0) {
			if (1 == n % 2) {
				count++;
			}
			n /= 2;
		}
		return shouldConvert ? 32 - count : count;
	}

	@Test
	public void test() {
		System.out.println(hammingWeight(11));
		System.out.println(hammingWeight(12));
		System.out.println(hammingWeight(13));
		System.out.println(hammingWeight(0XFFFFFFFF));
		System.out.println(hammingWeight(0XFFFFFFF0));
		System.out.println(hammingWeight(0XFFFFFFF1));
	}
}
