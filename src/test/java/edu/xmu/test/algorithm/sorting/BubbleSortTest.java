package edu.xmu.test.algorithm.sorting;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;

import com.google.common.collect.Lists;

public class BubbleSortTest {
	@Test
	public void bubbleSortTest() {
		List<Integer> expIntList = Lists.newArrayList(13, 27, 38, 49, 65, 76, 97);
		List<Integer> intList = Lists.newArrayList(49, 38, 65, 97, 76, 13, 27);
		BubbleSort.bubbleSort(intList);
		assertEquals(expIntList, intList);
	}
}
