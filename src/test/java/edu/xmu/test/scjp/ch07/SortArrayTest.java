package edu.xmu.test.scjp.ch07;

import static org.junit.Assert.assertArrayEquals;

import java.util.Arrays;
import java.util.Comparator;

import org.junit.Test;

public class SortArrayTest {
	@Test
	public void sortArrayTest() {
		int[] array = new int[] { 0, 3, -1, -9, -2, 1, 9, 10 };
		Arrays.sort(array);
		assertArrayEquals(new int[] { -9, -2, -1, 0, 1, 3, 9, 10 }, array);

		Object[] array2 = new Object[] { 0, 3, -1, -9, -2, 1, 9, 10 };
		Arrays.sort(array2, new Comparator<Object>() {
			@Override
			public int compare(Object o1, Object o2) {
				return ((Integer) o2).compareTo((Integer) o1); // descending
			}
		});
		assertArrayEquals(new Object[] { 10, 9, 3, 1, 0, -1, -2, -9 }, array2);
	}
}
