package edu.xmu.test.algorithm.sorting;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;

import com.google.common.collect.Lists;

public class QuickSortTest {
	@Test
	public void quickSortTest() {
		List<Integer> expIntList = Lists.newArrayList(13, 27, 38, 49, 65, 76, 97);
		List<Integer> intList = Lists.newArrayList(49, 38, 65, 97, 76, 13, 27);
		QuickSort.quickSort(intList, 0, intList.size() - 1);
		assertEquals(expIntList, intList);
	}

	@Test
	public void splitListTest() {
		List<Integer> expectedIntList = Lists.newArrayList(27, 38, 13, 49, 76, 97, 65);
		List<Integer> intList = Lists.newArrayList(49, 38, 65, 97, 76, 13, 27);
		int newPivotIndex = QuickSort.splitList(intList, 0, 6);
		assertEquals(expectedIntList, intList);
		assertEquals(3, newPivotIndex);
	}
}
