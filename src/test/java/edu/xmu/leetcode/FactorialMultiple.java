package edu.xmu.leetcode;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class FactorialMultiple {
	@Test
	public void factorialMultipleTest() {
		assertEquals(1, factorialMultiple(1));
		assertEquals(2, factorialMultiple(2));
		assertEquals(6, factorialMultiple(3));
		assertEquals(24, factorialMultiple(4));
	}

	int factorialMultiple(int n) {
		int sum = 1;
		for (int i = n; i >= 1; i--) {
			sum *= i;
		}
		return sum;
	}
}
