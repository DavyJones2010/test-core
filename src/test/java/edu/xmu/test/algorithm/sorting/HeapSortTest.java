package edu.xmu.test.algorithm.sorting;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;

import com.google.common.collect.Lists;

public class HeapSortTest {
	@Test
	public void heapSortTest() {
		// 49
		// 38 65
		// 97 76 13 27
		List<Integer> intList = Lists.newArrayList(Integer.MAX_VALUE, 49, 38, 65, 97, 76, 13, 27);
		List<Integer> expIntList = Lists.newArrayList(Integer.MAX_VALUE, 13, 27, 38, 49, 65, 76, 97);
		HeapSort.heapSort(intList);
		assertEquals(expIntList, intList);
	}
}
