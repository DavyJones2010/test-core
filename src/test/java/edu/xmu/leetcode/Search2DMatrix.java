package edu.xmu.leetcode;

import java.util.Arrays;

import org.junit.Test;

public class Search2DMatrix {
	public boolean searchMatrix(int[][] matrix, int target) {
		return searchY(matrix, target, 0, matrix.length - 1);
	}

	boolean searchY(int[][] matrix, int target, int startY, int endY) {
		int endX1 = matrix[startY].length - 1;
		int endX2 = matrix[endY].length - 1;
		if (startY == endY) {
			return Arrays.binarySearch(matrix[startY], target) >= 0;
		}
		if ((target < matrix[startY][0]) || (target > matrix[endY][endX2])) {
			return false;
		} else if ((target <= matrix[startY][endX1]) && (target >= matrix[startY][0])) {
			return Arrays.binarySearch(matrix[startY], target) >= 0;
		} else if ((target <= matrix[endY][endX2]) && (target >= matrix[endY][0])) {
			return Arrays.binarySearch(matrix[endY], target) >= 0;
		} else {
			return searchY(matrix, target, startY, (startY + endY) / 2) || searchY(matrix, target, (startY + endY) / 2 + 1, endY);
		}
	}

	@Test
	public void test() {
		int[][] matrix = new int[][] { { 1, 3, 5, 7 }, { 10, 11, 16, 20 }, { 23, 30, 34, 50 } };
		System.out.println(searchMatrix(matrix, 4));
		System.out.println(searchMatrix(matrix, 5));
		System.out.println(searchMatrix(matrix, 12));
		System.out.println(searchMatrix(matrix, 16));
		System.out.println(searchMatrix(matrix, 23));
		System.out.println(searchMatrix(matrix, 31));
	}
}
