package edu.xmu.leetcode;

import org.junit.Test;

public class ValidPalindromeTest {
	public boolean isPalindrome(String s) {
		boolean isPanlidrome = true;

		char[] chars = new char[s.length()];
		int index = 0;
		for (char c : s.toCharArray()) {
			if (Character.isAlphabetic(c) || Character.isDigit(c)) {
				char lowerCasedC = Character.isUpperCase(c) ? Character
						.toLowerCase(c) : c;
				chars[index] = lowerCasedC;
				index++;
			}
		}
		index--;

		char[] reversedChars = new char[index + 1];
		for (int i = 0; i <= index; i++) {
			reversedChars[i] = chars[index - i];
		}

		for (int i = 0; i < reversedChars.length; i++) {
			if (chars[i] != reversedChars[i]) {
				isPanlidrome = false;
			}
		}

		return isPanlidrome;
	}

	@Test
	public void isPalindromeTest() {
		System.out.println(isPalindrome("1a2a1"));
	}

}
