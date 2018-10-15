package edu.xmu.leetcode;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

/**
 * Solution for {@link <a href="https://leetcode.com/problems/valid-anagram/">Valid Anagram</a>}
 *
 */
public class ValidAnagram {

	/**
	 * Pay attention to {@link java.lang.Integer.IntegerCache} <br>
	 * When comparing two Integers, we should use equals() instead of == <br>
	 * == will only work when the two ints are in the scope of[-128, 127] because of IntegerCache <br>
	 * Eg. {@code new Integer(-128) == new Integer(-128)} returns true; <br>
	 * However, {@code new Integer(128) == new Integer(128)} returns false;
	 */
	public boolean isAnagram(String s, String t) {
		char[] ss = s.toCharArray();
		char[] ts = t.toCharArray();
		if (ss.length != ts.length) {
			return false;
		}
		Map<Character, Integer> mapA = new HashMap<Character, Integer>();
		Map<Character, Integer> mapB = new HashMap<Character, Integer>();
		for (int i = 0; i < ss.length; i++) {
			if (mapA.containsKey(ss[i])) {
				mapA.put(ss[i], mapA.get(ss[i]) + 1);
			} else {
				mapA.put(ss[i], 1);
			}
			if (mapB.containsKey(ts[i])) {
				mapB.put(ts[i], mapB.get(ts[i]) + 1);
			} else {
				mapB.put(ts[i], 1);
			}
		}
		for (Map.Entry<Character, Integer> entry : mapA.entrySet()) {
			Character c = entry.getKey();
			if (!mapB.containsKey(c)) {
				return false;
			} else {
				if (!entry.getValue().equals(mapB.get(c))) {
					return false;
				}
			}
		}
		return true;
	}

	@Test
	public void test() {
		System.out.println(isAnagram("car", "tar"));
		System.out.println(isAnagram("rat", "car"));
		System.out.println(isAnagram("anagram", "nagaram"));
		System.out.println(isAnagram("car", "arc"));
		System.out.println(isAnagram("aacc", "ccac"));
	}
}
