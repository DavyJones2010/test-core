package edu.xmu.leetcode;

import org.junit.Test;

public class LongestCommonPrefixTest {
	public String longestCommonPrefix(String[] strs) {
		if (strs.length <= 0) {
			return "";
		}
		char[] chars = strs[0].toCharArray();

		int firstDiffIndex = chars.length;
		for (String str : strs) {
			if (str.isEmpty()) {
				firstDiffIndex = 0;
				break;
			}
			int currentCheckIndex = 0;

			char[] currChars = str.toCharArray();

			for (; (currentCheckIndex <= firstDiffIndex - 1)
					&& (currentCheckIndex < currChars.length); currentCheckIndex++) {
				if (chars[currentCheckIndex] != currChars[currentCheckIndex]
						&& currentCheckIndex < firstDiffIndex) {
					firstDiffIndex = currentCheckIndex;
					break;
				}
			}
			if (currentCheckIndex < firstDiffIndex) {
				firstDiffIndex = currentCheckIndex;
			}
		}

		char[] commonChars = new char[firstDiffIndex];
		for (int i = 0; i < firstDiffIndex; i++) {
			commonChars[i] = chars[i];
		}
		return String.valueOf(commonChars);
	}

	@Test
	public void commonTest() {
		System.out.println(longestCommonPrefix(new String[] { "aa", "a" }));
	}

}
