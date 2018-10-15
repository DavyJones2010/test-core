package edu.xmu.leetcode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

/**
 * Solution for {@link <a href="https://leetcode.com/submissions/detail/36603965/">Letter Combinations of a Phone Number</a>}
 */
public class LetterCombinationsOfAPhoneNumber {
	final static Map<Character, List<String>> map;
	static {
		map = new HashMap<Character, List<String>>();
		List<String> list = new ArrayList<String>();
		list.add("a");
		list.add("b");
		list.add("c");
		map.put('2', list);
		list = new ArrayList<String>();
		list.add("d");
		list.add("e");
		list.add("f");
		map.put('3', list);
		list = new ArrayList<String>();
		list.add("g");
		list.add("h");
		list.add("i");
		map.put('4', list);
		list = new ArrayList<String>();
		list.add("j");
		list.add("k");
		list.add("l");
		map.put('5', list);
		list = new ArrayList<String>();
		list.add("m");
		list.add("n");
		list.add("o");
		map.put('6', list);
		list = new ArrayList<String>();
		list.add("p");
		list.add("q");
		list.add("r");
		list.add("s");
		map.put('7', list);
		list = new ArrayList<String>();
		list.add("t");
		list.add("u");
		list.add("v");
		map.put('8', list);
		list = new ArrayList<String>();
		list.add("w");
		list.add("x");
		list.add("y");
		list.add("z");
		map.put('9', list);
	}

	public List<String> letterCombinations(String digits) {
		if (digits.isEmpty()) {
			return new ArrayList<String>();
		} else {
			return letterCombinations(digits.toCharArray(), 0, digits.length() - 1);
		}
	}

	List<String> letterCombinations(char[] chars, int start, int end) {
		if (start == end) {
			if (map.containsKey(chars[start])) {
				return map.get(chars[start]);
			} else {
				throw new IllegalStateException("Illegal char: " + chars[start]);
			}
		} else {
			List<String> values = new ArrayList<String>();
			char c = chars[start];
			List<String> list = letterCombinations(chars, start + 1, end);
			List<String> prefixs = map.get(c);
			for (String prefix : prefixs) {
				for (String v : list) {
					values.add(prefix + v);
				}
			}
			return values;
		}
	}

	@Test
	public void test() {
		System.out.println(letterCombinations("23"));
	}
}
