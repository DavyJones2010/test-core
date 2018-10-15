package edu.xmu.test.algorithm.sorting;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import com.google.common.collect.Lists;

public class RecurisiveAlgorithm {

	/**
	 * Recursive solution
	 */
	public void selectionSort(int start, int[] array) {
		int n = array.length;
		if (start < n) {
			int min = start;
			for (int j = start + 1; j < n; j++) {
				if (array[j] < array[start]) {
					min = j;
				}
			}
			if (min != start) {
				int tmp = array[min];
				array[min] = array[start];
				array[start] = tmp;
			}
			selectionSort(start + 1, array);
		}
	}

	/**
	 * Recursive solution
	 */
	public void insertionSort(int targetIndex, int[] array) {
		int n = array.length;
		if (targetIndex < n) {
			int insertionIndex = targetIndex;
			for (int j = 0; j < targetIndex; j--) {
				if (array[j] > array[insertionIndex]) {
					insertionIndex = j;
					break;
				}
			}
			if (insertionIndex != targetIndex) {
				int targetVal = array[targetIndex];
				for (int i = targetIndex; i > insertionIndex; i--) {
					array[i] = array[i - 1];
				}
				array[insertionIndex] = targetVal;
			}
			insertionSort(targetIndex + 1, array);
		}
	}

	/**
	 * Calculate x^m with O(log(n))
	 */
	public long exponentialCalc(int x, int m) {
		if (m == 0) {
			return 1;
		} else {
			long y = exponentialCalc(x, m / 2);
			y = y * y;
			if (m % 2 != 0) {
				return y * x;
			} else {
				return y;
			}
		}
	}

	public List<List<Integer>> permutation(int i) {
		List<List<Integer>> res = new ArrayList<List<Integer>>();
		if (i == 1) {
			List<Integer> pos = new ArrayList<Integer>();
			pos.add(Integer.valueOf(1));
			res.add(pos);
		} else {
			List<List<Integer>> set = permutation(i - 1);
			for (List<Integer> sub : set) {
				for (int j = 0; j < i; j++) {
					List<Integer> copyOfSub = Lists.newArrayList(sub);
					copyOfSub.add(j, i);
					res.add(copyOfSub);
				}
			}
		}
		return res;
	}

	@Test
	public void test() {
		//int[] array = new int[] { 4, 3, 2, 1 };
		//selectionSort(0, array);
		//System.out.println(Arrays.toString(array));
        //
		//array = new int[] { 4, 3, 2, 1 };
		//insertionSort(1, array);
		//System.out.println(Arrays.toString(array));

		System.out.println(exponentialCalc(2, 52));

		//System.out.println(permutation(3));
	}
}
