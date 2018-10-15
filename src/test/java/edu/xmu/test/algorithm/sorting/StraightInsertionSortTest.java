package edu.xmu.test.algorithm.sorting;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;

import com.google.common.collect.Lists;

public class StraightInsertionSortTest {
	@Test
	public void insertSortTest() {
		List<Integer> intList = Lists.newArrayList(49, 38, 65, 97, 76, 13, 27);
		List<Integer> expIntList = Lists.newArrayList(13, 27, 38, 49, 65, 76, 97);

		List<Integer> sortedIntList = StraightInsertionSort.insertSort(intList);
		assertEquals(expIntList, sortedIntList);
	}
}
