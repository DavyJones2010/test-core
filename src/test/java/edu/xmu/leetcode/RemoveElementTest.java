package edu.xmu.leetcode;

import static org.junit.Assert.assertEquals;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

public class RemoveElementTest {
	@Ignore
	@Test
	public void removeElementTest() {
		int[] A = new int[] { 1, 2, 1, 3, 4, 1 };
		assertEquals(3, removeElement(A, 1));
		Assert.assertArrayEquals(new int[] { 2, 3, 4 }, A);
	}

	public int removeElement(int[] A, int elem) {
		int[] newArray = new int[A.length];
		int newArrayIndex = 0;
		int arrayIndex = 0;
		while (arrayIndex < A.length) {
			if (A[arrayIndex] == elem) {
				arrayIndex++;
			} else {
				newArray[newArrayIndex] = A[arrayIndex];
				arrayIndex++;
				newArrayIndex++;
			}
		}
		System.arraycopy(newArray, 0, A, 0, newArrayIndex);
		return newArrayIndex;
	}
}
