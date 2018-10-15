package edu.xmu.test.effectivejava.sample.other;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class Chapter8Boxing49Test {
	@SuppressWarnings("unused")
	@Test
	public void boxingPerformanceTest() {
		long start = System.currentTimeMillis();
		Integer val = 0;
		for (int i = 0; i < 1000000; i++) {
			val += i;
		}
		System.out.println("Boxing: " + (System.currentTimeMillis() - start));
		start = System.currentTimeMillis();

		int val2 = 0;
		for (int i = 0; i < 1000000; i++) {
			val2 += i;
		}
		System.out.println("Non-Boxing: " + (System.currentTimeMillis() - start));
		start = System.currentTimeMillis();
	}

	@Test
	public void boxingEqualsTest() {
		Integer a = new Integer(1);
		Integer b = new Integer(1);
		assertFalse(a == b);
		assertTrue(a.equals(b));

		a = 1;
		b = 1;
		assertTrue(a == b);
		assertTrue(a.equals(b));
	}
}
