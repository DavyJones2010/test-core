package edu.xmu.leetcode;

import org.junit.Assert;
import org.junit.Test;

public class MergeSortedArrayTest {
	@Test
	public void mergeTest() {
		int A[] = { 1, 2, 3, 5, 0, 0, 0, 0 };
		int B[] = { 7, 8, 9, 10 };
		merge(A, 4, B, 4);
		Assert.assertArrayEquals(new int[] { 1, 2, 3, 5, 7, 8, 9, 10 }, A);

		A = new int[] { 1, 2, 3, 5, 0, 0, 0, 0 };
		B = new int[] { -1, 4, 7, 8 };
		merge(A, 4, B, 4);
		Assert.assertArrayEquals(new int[] { -1, 1, 2, 3, 4, 5, 7, 8 }, A);
	}

	@Test
	public void insertTest() {
		int[] A = { 1, 2, 3, 5, 0 };
		insert(A, 0, -1, 3);
		Assert.assertArrayEquals(new int[] { -1, 1, 2, 3, 5 }, A);
	}

	public void merge(int A[], int m, int B[], int n) {
		int aLastIndex = m - 1;
		int aCurrIndex = 0;
		int bIndex = 0;

		while (aCurrIndex <= aLastIndex && bIndex < n) {
			int a = A[aCurrIndex];
			int b = B[bIndex];
			if (a > b) {
				insert(A, aCurrIndex, b, aLastIndex);
				aCurrIndex++;
				bIndex++;
				aLastIndex++;
			} else {
				aCurrIndex++;
			}
		}
		if (aCurrIndex == aLastIndex + 1 && bIndex < n) {
			while (bIndex < n) {
				A[aCurrIndex] = B[bIndex];
				bIndex++;
				aCurrIndex++;
			}
		}
	}

	private void insert(int[] a, int aIndex, int b, int lastIndex) {
		for (int i = lastIndex + 1; i > aIndex; i--) {
			a[i] = a[i - 1];
		}
		a[aIndex] = b;
	}
}
