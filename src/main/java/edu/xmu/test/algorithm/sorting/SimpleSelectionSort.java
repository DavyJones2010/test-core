package edu.xmu.test.algorithm.sorting;

import java.util.List;

public class SimpleSelectionSort {
	public static void selectionSort(List<Integer> intList) {
		int minValue;
		int indexToBeSwapped;
		for (int i = 0; i < intList.size(); i++) {
			minValue = Integer.MAX_VALUE;
			indexToBeSwapped = 0;

			for (int j = i; j < intList.size(); j++) {
				int temp = intList.get(j);
				if (minValue > temp) {
					minValue = temp;
					indexToBeSwapped = j;
				}
			}
			int value = intList.get(i);
			intList.set(i, minValue);
			intList.set(indexToBeSwapped, value);
		}
	}
}
