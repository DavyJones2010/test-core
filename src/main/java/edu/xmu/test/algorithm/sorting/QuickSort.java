package edu.xmu.test.algorithm.sorting;

import java.util.List;

/**
 * @author davywalker
 */
public class QuickSort {
    public static void quickSort(List<Integer> intList, int startIndex, int endIndex) {
        if (startIndex < endIndex) {
            int pivotIndex = splitList(intList, startIndex, endIndex);
            quickSort(intList, startIndex, pivotIndex - 1);
            quickSort(intList, pivotIndex + 1, endIndex);
        }
    }

    public static int splitList(List<Integer> intList, int pivotIndex, int high) {
        int pivotValue = intList.get(pivotIndex);
        int low = pivotIndex;

        boolean shouldHighMove = true;
        while (low < high) {
            if (shouldHighMove) {
                if (intList.get(high) < pivotValue) {
                    int lowValue = intList.get(low);
                    int highValue = intList.get(high);
                    intList.set(low, highValue);
                    intList.set(high, lowValue);
                    low++;
                    shouldHighMove = false;
                } else {
                    shouldHighMove = true;
                    high--;
                }
            } else {
                if (intList.get(low) > pivotValue) {
                    int lowValue = intList.get(low);
                    int highValue = intList.get(high);
                    intList.set(low, highValue);
                    intList.set(high, lowValue);
                    high--;
                    shouldHighMove = true;
                } else {
                    shouldHighMove = false;
                    low++;
                }
            }
        }
        if (high != low) {
            throw new RuntimeException("Defacted Algorithm!");
        }
        intList.set(low, pivotValue);
        return low;
    }
}
