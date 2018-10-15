package edu.xmu.leetcode;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class ImplementStrStrTest {
	@Test
	public void strStrTest() {
		assertEquals(0, strStr("ABCDEFG", "A"));
		assertEquals(4, strStr("ABCDEFG", "EFG"));
		assertEquals(-1, strStr("ABCDEFG", "AFG"));
		assertEquals(0, strStr("a", ""));
		assertEquals(0, strStr("", ""));
		assertEquals(-1, strStr("", "a"));
		assertEquals(-1, strStr("aaa", "aaaaa"));
		assertEquals(-1, strStr("mississippi", "issipi"));
	}

	public int strStr(String haystack, String needle) {
		if (haystack.isEmpty() || needle.isEmpty()) {
			if (haystack.isEmpty() && !needle.isEmpty()) {
				return -1;
			} else {
				return 0;
			}
		}
		if (needle.length() > haystack.length()) {
			return -1;
		}
		char[] haystackChars = haystack.toCharArray();
		char[] needleChars = needle.toCharArray();

		for (int i = 0; i < haystackChars.length - needleChars.length + 1; i++) {
			char c = haystackChars[i];
			if (needleChars[0] == c) {
				boolean found = true;
				for (int j = 1; j < needleChars.length; j++) {
					if ((j + i) < haystackChars.length
							&& needleChars[j] != haystackChars[j + i]) {
						found = false;
					}
				}
				if (found) {
					return i;
				}
			}
		}

		return -1;
	}
}
