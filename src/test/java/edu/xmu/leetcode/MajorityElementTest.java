package edu.xmu.leetcode;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

public class MajorityElementTest {
	@Test
	public void majorityElementTest() {
		assertEquals(1, majorityElement(new int[] { 1, 2, 3, 1 }));
		assertEquals(2, majorityElement(new int[] { 2, 2, 3, 1 }));
	}

	public int majorityElement(int[] num) {
		Map<Integer, Integer> maxMap = new HashMap<Integer, Integer>();
		int maxCount = 0;
		int majorityNum = Integer.MIN_VALUE;
		for (int val : num) {
			if (maxMap.containsKey(val)) {
				int currCount = maxMap.get(val) + 1;
				if (currCount > maxCount) {
					maxCount = currCount;
					majorityNum = val;
				}
				maxMap.put(val, currCount);
			} else {
				int currCount = 1;
				if (currCount > maxCount) {
					maxCount = currCount;
					majorityNum = val;
				}
				maxMap.put(val, currCount);
			}
		}
		return majorityNum;
	}
}
