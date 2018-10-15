package edu.xmu.leetcode;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

/**
 * {@link <a href="https://leetcode.com/problems/set-matrix-zeroes/">Set Matrix Zeroes</a>} <br>
 */
public class SetMatrixZeros {

	public void setZeroes(int[][] matrix) {
		if (matrix.length == 0) {
			return;
		}
		Set<Integer> zeroRows = new HashSet<Integer>();
		Set<Integer> zeroColumns = new HashSet<Integer>();
		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix[i].length; j++) {
				if (matrix[i][j] == 0) {
					zeroRows.add(i);
					zeroColumns.add(j);
				}
			}
		}
		for (int row : zeroRows) {
			matrix[row] = new int[matrix[0].length];
		}
		for (int col : zeroColumns) {
			for (int i = 0; i < matrix.length; i++) {
				matrix[i][col] = 0;
			}
		}
	}

	@Test
	public void climbStairsTest() {
		int[][] matrix = new int[][] { { 0, 0, 0, 5 }, { 4, 3, 1, 4 }, { 0, 1, 1, 4 }, { 1, 2, 1, 3 }, { 0, 0, 1, 1 } };
		setZeroes(matrix);
	}
}
