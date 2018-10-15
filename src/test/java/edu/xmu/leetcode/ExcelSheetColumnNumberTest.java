package edu.xmu.leetcode;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class ExcelSheetColumnNumberTest {

	@Test
	public void titleToNumberTest() {
		assertEquals(1, titleToNumber("A"));
		assertEquals(26, titleToNumber("Z"));
		assertEquals(27, titleToNumber("AA"));
		assertEquals(28, titleToNumber("AB"));
		System.out.println(titleToNumber("ZZ"));
		System.out.println(26 * 26 + 26);
	}

	public int titleToNumber(String s) {
		char[] chars = s.toCharArray();
		int sum = 0;
		for (char c : chars) {
			sum += sum * 25 + (int) (c - 'A' + 1);
		}

		return sum;
	}
}
