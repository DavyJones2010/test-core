package edu.xmu.test.algorithm.sorting;

import java.util.List;

public class HeapSort {
	public static void heapSort(List<Integer> intList) {
		for (int i = intList.size() / 2; i >= 1; i--) {
			adjustHeap(intList, i, intList.size() - 1);
		}

		for (int i = intList.size() - 1; i >= 1; i--) {
			int maxValue = intList.get(1);
			int tailValue = intList.get(i);

			intList.set(1, tailValue);
			intList.set(i, maxValue);
			adjustHeap(intList, 1, i - 1);
		}
	}

	// Max Heap
	private static void adjustHeap(List<Integer> intList, int startIndex, int endIndex) {
		int rootNodeValue = intList.get(startIndex);
		int currentIndex = startIndex;
		for (int i = startIndex * 2; i <= endIndex; i *= 2) {
			if (i + 1 > endIndex) {
				// For the benefit of obsolete index
				// When we put the max value at the end of array
				// Then [start, end - 1]
				// But here intList.get(i + 1) may unintentionally get the
				// already set as max and thus unreachable
			} else if (intList.get(i) < intList.get(i + 1)) {
				i += 1;
			}

			if (rootNodeValue > intList.get(i)) {
				// rootNode is bigger than its children
				break;
			}

			intList.set(currentIndex, intList.get(i));
			currentIndex = i;
		}
		intList.set(currentIndex, rootNodeValue);
	}
}
