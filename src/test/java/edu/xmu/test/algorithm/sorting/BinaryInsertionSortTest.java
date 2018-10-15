package edu.xmu.test.algorithm.sorting;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;

import com.google.common.collect.Lists;

public class BinaryInsertionSortTest {
	@Test
	public void insertSortTest() {
		List<Integer> intList = Lists.newArrayList(49, 38, 65, 97, 76, 13, 27);
		List<Integer> expIntList = Lists.newArrayList(13, 27, 38, 49, 65, 76, 97);

		List<Integer> sortedIntList = BinaryInsertionSort.binaryInsertSort(intList);
		assertEquals(expIntList, sortedIntList);
	}

	@Test
	public void searchIndexToBeInsertedTest() {
		List<Integer> sortedIntList = Lists.newArrayList(1, 2, 3, 0);
		int hotIndex = 3;
		int indexToBeInserted = BinaryInsertionSort.searchIndexToBeInserted(hotIndex, sortedIntList);
		assertEquals(0, indexToBeInserted);

		sortedIntList = Lists.newArrayList(1, 2, 3, 4, 0);
		hotIndex = 4;
		indexToBeInserted = BinaryInsertionSort.searchIndexToBeInserted(hotIndex, sortedIntList);
		assertEquals(0, indexToBeInserted);

		sortedIntList = Lists.newArrayList(2, 4, 6, 8, 10);
		hotIndex = 4;
		indexToBeInserted = BinaryInsertionSort.searchIndexToBeInserted(hotIndex, sortedIntList);
		assertEquals(4, indexToBeInserted);

		sortedIntList = Lists.newArrayList(2, 4, 6, 8, 10, 5);
		hotIndex = 5;
		indexToBeInserted = BinaryInsertionSort.searchIndexToBeInserted(hotIndex, sortedIntList);
		assertEquals(2, indexToBeInserted);
	}
}
