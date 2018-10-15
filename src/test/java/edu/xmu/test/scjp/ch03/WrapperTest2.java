package edu.xmu.test.scjp.ch03;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class WrapperTest2 {
	@Test
	public void wideningTest() {
		assertEquals("Go Long", go(123)); // Widening beats Auto-Boxing
		assertEquals("Go Int", go(new Integer(123)));
		assertEquals("Go Auto-Boxing", go(1, 2)); // Auto-Boxing beats Var-Args
	}

	public String go(long a) {
		return "Go Long";
	}
	public String go(Integer a) {
		return "Go Int";
	}
	public String go(int... a) {
		return "Go Var-args";
	}
	public String go(Integer a, Integer b) {
		return "Go Auto-Boxing";
	}
}
