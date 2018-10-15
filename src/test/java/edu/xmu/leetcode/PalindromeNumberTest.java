package edu.xmu.leetcode;

import org.junit.Test;

public class PalindromeNumberTest {
	public boolean isPalindrome(int x) {
		if (x < 0) {
			return false;
		}

		int originalX = x;

		int theOtherX = 0;
		int remainder = x % 10;
		x = x / 10;
		while (x != 0) {
			theOtherX = theOtherX * 10 + remainder * 10;
			remainder = x % 10;
			x = x / 10;
		}
		theOtherX += remainder;

		return theOtherX == originalX;
	}

	@Test
	public void isPalindromeTest() {
		System.out.println(isPalindrome(1));
	}
}
