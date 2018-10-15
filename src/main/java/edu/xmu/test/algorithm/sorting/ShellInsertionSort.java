package edu.xmu.test.algorithm.sorting;

import java.util.List;

public class ShellInsertionSort {
    public static void shellInsertSort(List<Integer> intList, int delta) {
        for (int i = delta; i < intList.size(); i++) {
            int valueToBeInserted = intList.get(i);
            int indexToBeInserted = i;

            for (int j = i - delta; j >= 0; j -= delta) {
                if (valueToBeInserted < intList.get(j)) {
                    intList.set(j + delta, intList.get(j));
                    indexToBeInserted = j;
                } else {
                    break;
                }
            }
            intList.set(indexToBeInserted, valueToBeInserted);
        }
    }
}
