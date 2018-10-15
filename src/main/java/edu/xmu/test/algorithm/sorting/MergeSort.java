package edu.xmu.test.algorithm.sorting;

import java.util.List;

import com.google.common.collect.Lists;

public class MergeSort {
	public static List<Integer> mergeSort(List<Integer> intList, int startIndex, int endIndex) {
		List<Integer> mergedList;
		if (startIndex == endIndex) {
			mergedList = Lists.newArrayList(intList.get(startIndex));
		} else {
			int middleIndex = (startIndex + endIndex) / 2;

			List<Integer> leftList = mergeSort(intList, startIndex, middleIndex);
			List<Integer> rightList = mergeSort(intList, middleIndex + 1, endIndex);

			mergedList = mergeSortedList(leftList, rightList);
		}
		return mergedList;
	}

	/**
	 * Merge two ascending sorted list into one single asc list
	 * 
	 * @param leftList
	 * @param rightList
	 * @return
	 */
	public static List<Integer> mergeSortedList(List<Integer> leftList, List<Integer> rightList) {
		List<Integer> mergedList = Lists.newArrayList();

		int leftSize = leftList.size();
		int rightSize = rightList.size();
		int leftIndex = 0;
		int rightIndex = 0;
		int leftValue = Integer.MAX_VALUE;
		int rightValue = Integer.MAX_VALUE;

		while (!(leftIndex == leftSize && rightIndex == rightSize)) {
			if (leftIndex < leftSize) {
				leftValue = leftList.get(leftIndex);
			} else {
				leftValue = Integer.MAX_VALUE;
			}
			if (rightIndex < rightSize) {
				rightValue = rightList.get(rightIndex);
			} else {
				rightValue = Integer.MAX_VALUE;
			}

			if (rightValue < leftValue) {
				mergedList.add(rightValue);
				rightIndex++;
			} else {
				mergedList.add(leftValue);
				leftIndex++;
			}
		}
		return mergedList;
	}
}
