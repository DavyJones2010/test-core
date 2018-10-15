package edu.xmu.test.algorithm.sorting;

import java.util.List;

public class BubbleSort {
	public static void bubbleSort(List<Integer> intList) {
		for (int i = 0; i < intList.size(); i++) {
			for (int j = 0; j < intList.size() - i - 1; j++) {
				int prevValue = intList.get(j);
				int currValue = intList.get(j + 1);
				// The biggest value bubbles to the end of the list
				if (prevValue > currValue) {
					intList.set(j, currValue);
					intList.set(j + 1, prevValue);
				}
			}
		}
	}
}
