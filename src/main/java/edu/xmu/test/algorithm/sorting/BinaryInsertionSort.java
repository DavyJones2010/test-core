package edu.xmu.test.algorithm.sorting;

import java.util.List;

import com.google.common.collect.Lists;

public class BinaryInsertionSort {

	/**
	 * {@linkplain Integer#valueOf(int)}
	 */
	public static List<Integer> binaryInsertSort(List<Integer> intList) {
		List<Integer> sortedIntList = Lists.newArrayList(intList);
		for (int i = 1; i < sortedIntList.size(); i++) {
			int valueToBeInserted = sortedIntList.get(i);
			int indexToBeInserted = searchIndexToBeInserted(i, sortedIntList);

			// Move elements from [indexToBeInserted, i - 1] to
			// [indexToBeInserted + 1, i]
			for (int j = i - 1; j >= indexToBeInserted; j--) {
				if (valueToBeInserted < sortedIntList.get(j)) {
					sortedIntList.set(j + 1, sortedIntList.get(j));
				}
			}
			// Insert element to indexToBeInserted position
			sortedIntList.set(indexToBeInserted, valueToBeInserted);
		}

		return sortedIntList;
	}

	public static int searchIndexToBeInserted(int hotIndex, List<Integer> sortedIntList) {
		int startIndex = 0;
		int endIndex = hotIndex - 1;

		while (startIndex <= endIndex) {
			int middle = (startIndex + endIndex) / 2;
			if (sortedIntList.get(hotIndex) < sortedIntList.get(middle)) {
				endIndex = middle - 1;
			} else {
				startIndex = middle + 1;
			}
		}

		return endIndex + 1;
	}
}
