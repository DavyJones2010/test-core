package edu.xmu.leetcode;

import java.util.Arrays;

import org.junit.Test;

/**
 * Solution for {@link <a href="https://leetcode.com/problems/spiral-matrix-ii/">Spiral Matrix II</a>}
 *
 */
public class SpiralMatrixII {

	public int[][] generateMatrix(int n) {
		int[][] matrix = new int[n][n];
		fillMatrix(0, n - 1, 0, n - 1, 1, matrix);
		return matrix;
	}

	void fillMatrix(int startX, int endX, int startY, int endY, int startNum, int[][] matrix) {
		if (startX > endX || startY > endY) {
			return;
		}
		if ((startX == endX) && (startY == endY)) {
			matrix[startX][startY] = startNum;
			return;
		}
		for (int y = startY; y <= endY; y++) {
			matrix[startX][y] = startNum;
			startNum++;
		}
		for (int x = startX + 1; x <= endX; x++) {
			matrix[x][endY] = startNum;
			startNum++;
		}

		for (int y = endY - 1; y >= startY; y--) {
			matrix[endX][y] = startNum;
			startNum++;
		}
		for (int x = endX - 1; x > startX; x--) {
			matrix[x][startY] = startNum;
			startNum++;
		}
		fillMatrix(startX + 1, endX - 1, startY + 1, endY - 1, startNum, matrix);
	}

	@Test
	public void test() {
		int[][] matrix = generateMatrix(2);
		for (int i = 0; i < matrix.length; i++) {
			System.out.println(Arrays.toString(matrix[i]));
		}
	}
}
