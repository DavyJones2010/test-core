package edu.xmu.test.scjp.ch07;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;

import org.junit.Test;

public class SearchArrayTest {
	@Test
	public void searchArrayTest() {
		// the array need to be binary searched must be asc sorted
		int[] array = new int[] { -9, -2, -1, 0, 1, 3, 9, 10 };
		int index = Arrays.binarySearch(array, 0);
		assertEquals(3, index); // found

		index = Arrays.binarySearch(array, 8);
		assertEquals(-7, index); // not found, suggest insert at the 7th

		index = Arrays.binarySearch(array, 13);
		assertEquals(-9, index); // not found, suggested insert at the 9th

		/*
		 * If the arry is not asc sorted, the result would be unpredictable
		 */
		array = new int[] { 10, 9, 3, 1, 0, -1, -2, -9 };
		index = Arrays.binarySearch(array, 0);
		assertTrue(index < 0);
	}
}
