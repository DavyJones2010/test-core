package edu.xmu.leetcode;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class ReverseIntegerTest {
	public int reverse(int x) {
		int returnedInt = 0;
		String str = String.valueOf(x);
		boolean isNegative = false;
		char[] chars = new char[str.length()];
		char[] reversedChars = new char[str.length()];
		int i = 0;
		for (char c : str.toCharArray()) {
			if (c == '-') {
				isNegative = true;
				continue;
			} else {
				chars[i] = c;
				i++;
			}
		}
		if (isNegative) {
			chars[i] = '-';
		} else {
			i--;
		}
		int j = 0;
		for (; i >= 0; i--) {
			reversedChars[j] = chars[i];
			j++;
		}
		try {
			returnedInt = Integer.valueOf(String.valueOf(reversedChars));
		} catch (NumberFormatException e) {
		}
		return returnedInt;
	}

	@Test
	public void reverseIntegerTest() {
		assertEquals(12, reverse(21));
		assertEquals(-12, reverse(-21));
	}
}
