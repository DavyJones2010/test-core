package edu.xmu.test.algorithm.sorting;

import java.util.List;

import com.google.common.collect.Lists;

public class StraightInsertionSort {
	public static List<Integer> insertSort(List<Integer> intList) {
		List<Integer> sortedIntList = Lists.newArrayList(intList);
		for (int i = 1; i < sortedIntList.size(); i++) {
			int valueToBeInserted = sortedIntList.get(i);
			int indexToBeInserted = i;

			for (int j = i - 1; j >= 0; j--) {
				if (valueToBeInserted < sortedIntList.get(j)) {
					sortedIntList.set(j + 1, sortedIntList.get(j));
					indexToBeInserted = j;
				} else {
					break;
				}
			}
			sortedIntList.set(indexToBeInserted, valueToBeInserted);
		}
		return sortedIntList;
	}
}
