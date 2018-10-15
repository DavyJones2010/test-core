package edu.xmu.test.scjp.ch06;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class StringsTest {
	@Test
	public void immutableTest() {
		String s = new String("Hello");
		String s2 = s;
		s2 = s2.concat(" World");
		assertEquals("Hello", s);
	}

	@Test
	public void immutableTest2() {
		String s = new String("Hello");
		s.toUpperCase(); // A new object will be returned
		assertEquals("Hello", s);
	}
}
