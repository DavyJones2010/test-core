package edu.xmu.leetcode;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class RemoveDuplicatesFromSortedArrayTest {
	@Test
	public void removeDuplicatesTest() {
		assertEquals(4, removeDuplicatesTimeExceeded(new int[] { 1, 1, 2, 2, 3,
				4, 4, 4, 4 }));
		assertEquals(1, removeDuplicatesTimeExceeded(new int[] { 1, 1 }));
		assertEquals(2, removeDuplicatesTimeExceeded(new int[] { 1, 1, 2 }));

		assertEquals(4,
				removeDuplicates(new int[] { 1, 1, 2, 2, 3, 4, 4, 4, 4 }));
		assertEquals(1, removeDuplicates(new int[] { 1, 1 }));
		assertEquals(2, removeDuplicates(new int[] { 1, 1, 2 }));
	}

	@Test
	public void moveForwardTest() {
		int[] a = new int[] { 1, 1, 2, 2, 3, 4, 4, 4, 4 };
		moveForward(a, 3, 9, 2);
		System.out.println(a);
	}

	public int removeDuplicates(int[] A) {
		if (A.length == 0) {
			return 0;
		}
		int newArrayIndex = 0;
		int currArrayIndex = 0;
		while (currArrayIndex < A.length - 1) {
			if (A[currArrayIndex] != A[currArrayIndex + 1]) {
				newArrayIndex++;
				currArrayIndex++;
				if (newArrayIndex != currArrayIndex) {
					A[newArrayIndex] = A[currArrayIndex];
				}
			} else {
				currArrayIndex++;
			}
		}
		for (int i = newArrayIndex + 1; i < A.length; i++) {
			A[i] = 0;
		}
		return newArrayIndex + 1;
	}

	public int removeDuplicatesTimeExceeded(int[] A) {
		int actualLength = A.length;
		int forwardStep = 0;
		for (int i = 0; i < actualLength - 1;) {
			if (A[i] == A[i + 1]) {
				forwardStep++;
				i++;
				if (i == actualLength - 1) {
					moveForward(A, i, actualLength, forwardStep);
					actualLength -= forwardStep;
					i = i - forwardStep + 1;
				}
			} else {
				if (forwardStep != 0) {
					moveForward(A, i + 1, actualLength, forwardStep);
					actualLength -= forwardStep;
					i = i - forwardStep + 1;
					forwardStep = 0;
				} else {
					i++;
				}
			}
		}
		return actualLength;
	}

	private void moveForward(int[] a, int startIndex, int actualLength,
			int forwardStep) {
		for (int i = startIndex; i < actualLength; i++) {
			a[i - forwardStep] = a[i];
		}
		for (int j = actualLength - 1; j > actualLength - forwardStep - 1; j--) {
			a[j] = 0;
		}
	}
}
