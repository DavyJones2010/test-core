package edu.xmu.leetcode;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class RecursiveReverseStringTest {
	public String reverseString(String str) {
		if (str.length() == 1) {
			return str;
		} else {
			return reverseString(str.substring(1)) + str.substring(0, 1);
		}
	}

	@Test
	public void reverseStringTest() {
		assertEquals("CBA", reverseString("ABC"));
		assertEquals("CCACBA", reverseString("ABCACC"));
	}
}
