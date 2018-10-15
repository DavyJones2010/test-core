package edu.xmu.leetcode;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

public class SingleNumberTest {

	public int singleNumber(int[] A) {
		Map<Integer, Integer> count = new HashMap<Integer, Integer>();

		for (int i : A) {
			if (count.containsKey(i)) {
				count.remove(i);
			} else {
				count.put(i, 1);
			}
		}

		return count.keySet().iterator().next();
	}

	@Test
	public void singleNumberTest() {
		System.out.println(Integer.MAX_VALUE);
		System.out.println(singleNumber(new int[] { 0, 0, 1, 1, 3 }));
	}
}
