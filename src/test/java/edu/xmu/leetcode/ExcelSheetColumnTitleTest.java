package edu.xmu.leetcode;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class ExcelSheetColumnTitleTest {
	@Test
	public void convertToTitleTest() {
		assertEquals("A", convertToTitle(1));
		assertEquals("Z", convertToTitle(26));
		assertEquals("AA", convertToTitle(27));
		assertEquals("AB", convertToTitle(28));
		assertEquals("ZZ", convertToTitle(702));
	}

	public String convertToTitle(int n) {
		StringBuilder sb = new StringBuilder();
		while (n > 0) {
			if (n % 26 != 0) {
				sb.append(convertIntToChar(n % 26));
				n /= 26;
			} else {
				sb.append('Z');
				n /= 26;
				n--;
			}
		}
		return sb.reverse().toString();
	}

	private char convertIntToChar(int n) {
		return (char) (n + 'A' - 1);
	}
}
