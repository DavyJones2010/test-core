package edu.xmu.leetcode;

import org.junit.Assert;
import org.junit.Test;

public class PlusOneTest {
	@Test
	public void plusOneTest() {
		Assert.assertArrayEquals(new int[] { 1 }, plusOne(new int[] { 0 }));
		Assert.assertArrayEquals(new int[] { 1, 0 }, plusOne(new int[] { 9 }));
	}

	public int[] plusOne(int[] digits) {
		int[] newArray = null;
		boolean plusOne = true;
		boolean addOneSlot = false;
		for (int i = digits.length - 1; i >= 0; i--) {
			if (9 == digits[i] && plusOne) {
				if (i == 0) {
					digits[i] = 0;
					addOneSlot = true;
				} else {
					digits[i] = 0;
					plusOne = true;
				}
			} else if (9 != digits[i] && plusOne) {
				digits[i] = digits[i] + 1;
				plusOne = false;
				break;
			}
		}
		if (addOneSlot) {
			newArray = new int[digits.length + 1];
			newArray[0] = 1;
			for (int i = 0; i < digits.length; i++) {
				newArray[i + 1] = digits[i];
			}
		} else {
			newArray = digits;
		}
		return newArray;
	}
}
