package edu.xmu.leetcode;

import java.util.Arrays;

import org.junit.Test;

/**
 * Solution for: {@link <a href="https://leetcode.com/problems/rotate-image/">Rotate Image</a>} <br>
 */
public class RotateImage {
	public void rotate(int[][] matrix) {
		int[][] back = new int[matrix.length][matrix.length];
		int y = matrix.length - 1;
		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix.length; j++) {
				back[j][y - i] = matrix[i][j];
			}
		}
		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix.length; j++) {
				matrix[i][j] = back[i][j];
			}
		}
	}

	@Test
	public void test() {
		int[][] matrix = new int[][] { { 1, 2 }, { 3, 4 } };
		for (int i = 0; i < matrix.length; i++) {
			System.out.println(Arrays.toString(matrix[i]));
		}
		rotate(matrix);
		for (int i = 0; i < matrix.length; i++) {
			System.out.println(Arrays.toString(matrix[i]));
		}

		matrix = new int[][] { { 1 } };
		for (int i = 0; i < matrix.length; i++) {
			System.out.println(Arrays.toString(matrix[i]));
		}
		rotate(matrix);
		for (int i = 0; i < matrix.length; i++) {
			System.out.println(Arrays.toString(matrix[i]));
		}
	}
}
