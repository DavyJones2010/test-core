package edu.xmu.test.algorithm.sorting;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;

import com.google.common.collect.Lists;

public class MergeSortTest {
	@Test
	public void mergeSortTest() {
		List<Integer> intList = Lists.newArrayList(49, 38, 65, 97, 76, 13, 27);
		List<Integer> expIntList = Lists.newArrayList(13, 27, 38, 49, 65, 76, 97);
		List<Integer> sortedIntList = MergeSort.mergeSort(intList, 0, intList.size() - 1);
		assertEquals(expIntList, sortedIntList);
	}

	@Test
	public void mergeListTest() {
		List<Integer> leftList = Lists.newArrayList(1, 3, 5, 7);
		List<Integer> rightList = Lists.newArrayList(0, 2);

		List<Integer> expectedMergedList = Lists.newArrayList(0, 1, 2, 3, 5, 7);
		List<Integer> mergedList = MergeSort.mergeSortedList(leftList, rightList);

		assertEquals(expectedMergedList, mergedList);

		leftList = Lists.newArrayList(1, 3, 5, 7);
		rightList = Lists.newArrayList(0, 2, 4, 6, 8, 9);

		expectedMergedList = Lists.newArrayList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9);
		mergedList = MergeSort.mergeSortedList(leftList, rightList);

		assertEquals(expectedMergedList, mergedList);
	}
}
