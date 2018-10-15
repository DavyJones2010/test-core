package edu.xmu.test.algorithm.sorting;

import java.util.List;

public class SimpleSelectionSortRefactored {

	public static void selectionSort(List<Integer> intList) {
		for (int i = 0; i < intList.size(); i++) {
			int minValueIndex = getMinValueIndex(i, intList);
			int minValue = intList.get(minValueIndex);

			int tempValue = intList.get(i);
			intList.set(i, minValue);
			intList.set(minValueIndex, tempValue);
		}
	}

	private static int getMinValueIndex(int startIndex, List<Integer> intList) {
		int minValue = Integer.MAX_VALUE;
		int minValueIndex = 0;
		for (int j = startIndex; j < intList.size(); j++) {
			int temp = intList.get(j);
			if (minValue > temp) {
				minValue = temp;
				minValueIndex = j;
			}
		}
		return minValueIndex;
	}
}
