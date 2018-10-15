package edu.xmu.leetcode;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

/**
 * {@link <a href="https://oj.leetcode.com/problems/count-and-say/">CountAndSay</a>}
 *
 */
public class CountAndSayTest {
	public String countAndSay(int n) {
		int i = 1;
		String str = "1";
		while (i < n) {
			str = generateNext(str);
			i++;
		}
		return str;
	}

	String generateNext(String str) {
		char[] chars = str.toCharArray();

		List<Character> transformedChars = new ArrayList<Character>();

		int sameCharCount = 1;
		char prevChar = chars[0];
		int toIndex = 0;
		if (1 == chars.length) {
			transformedChars.add(0, (char) (sameCharCount + '0'));
			transformedChars.add(1, prevChar);
			return convertCharListToString(transformedChars);
		}

		for (int i = 1; i < chars.length; i++) {
			char currChar = chars[i];
			if (prevChar == currChar) {
				sameCharCount++;
			} else {
				transformedChars.add(toIndex, (char) (sameCharCount + '0'));
				toIndex++;
				transformedChars.add(toIndex, prevChar);
				toIndex++;
				prevChar = currChar;
				sameCharCount = 1;
			}
		}

		transformedChars.add(toIndex, (char) (sameCharCount + '0'));
		toIndex++;
		transformedChars.add(toIndex, chars[chars.length - 1]);

		return convertCharListToString(transformedChars);
	}

	String convertCharListToString(List<Character> transformedChars) {
		char[] tmp = new char[transformedChars.size()];
		for (int i = 0; i < tmp.length; i++) {
			tmp[i] = transformedChars.get(i);
		}
		return String.valueOf(tmp);
	}

	@Test
	public void test() {
		System.out.println(countAndSay(5));
		// System.out.println(generateNext("111221"));
	}
}
