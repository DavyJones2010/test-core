package edu.xmu.leetcode;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Stack;

import org.junit.Test;

public class ValidParenthesesTest {
	@Test
	public void isValidTest() {
		assertTrue(isValid("{}"));
		assertTrue(isValid("()[]{}"));
		assertTrue(isValid("{([])}"));
		assertFalse(isValid("(]"));
		assertFalse(isValid("([)]"));
		assertFalse(isValid(")}{({))[{{[}"));
	}

	public boolean isValid(String s) {
		if (s.length() == 0 || s.length() % 2 != 0) {
			return false;
		}
		char[] chars = s.toCharArray();
		Stack<Character> charStack = new Stack<Character>();
		for (char c : chars) {
			if (charStack.isEmpty()) {
				charStack.push(c);
			} else if ('}' == c || ']' == c || ')' == c) {
				char rc = charStack.peek();
				if ('}' == c && '{' == rc) {
					charStack.pop();
				} else if (']' == c && '[' == rc) {
					charStack.pop();
				} else if (')' == c && '(' == rc) {
					charStack.pop();
				} else {
					charStack.push(c);
				}
			} else {
				charStack.push(c);
			}
		}

		return charStack.isEmpty();
	}
}
