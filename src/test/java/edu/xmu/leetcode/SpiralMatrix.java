package edu.xmu.leetcode;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

/**
 * Solution for {@link <a href="https://leetcode.com/problems/spiral-matrix/">Spiral Matrix</a>}
 *
 */
public class SpiralMatrix {

	public List<Integer> spiralOrder(int[][] matrix) {
		if (matrix.length == 0) {
			return new ArrayList<Integer>();
		} else {
			return spiralOrder(0, matrix.length - 1, 0, matrix[0].length - 1, matrix);
		}
	}

	List<Integer> spiralOrder(int startX, int endX, int startY, int endY, int[][] matrix) {
		if (startX > endX || startY > endY) {
			return new ArrayList<Integer>();
		}
		if (startX == endX) {
			List<Integer> list = new ArrayList<Integer>();
			for (int y = startY; y <= endY; y++) {
				list.add(matrix[startX][y]);
			}
			return list;
		}
		if (startY == endY) {
			List<Integer> list = new ArrayList<Integer>();
			for (int x = startX; x <= endX; x++) {
				list.add(matrix[x][endY]);
			}
			return list;
		}
		List<Integer> list = new ArrayList<Integer>();
		for (int y = startY; y <= endY; y++) {
			list.add(matrix[startX][y]);
		}
		for (int x = startX + 1; x <= endX; x++) {
			list.add(matrix[x][endY]);
		}

		for (int y = endY - 1; y >= startY; y--) {
			list.add(matrix[endX][y]);
		}
		for (int x = endX - 1; x > startX; x--) {
			list.add(matrix[x][startY]);
		}
		list.addAll(spiralOrder(startX + 1, endX - 1, startY + 1, endY - 1, matrix));
		return list;
	}

	@Test
	public void test() {
		int[][] matrix = new int[][] { { 1, 2, 3 }, { 4, 5, 6 } };
		System.out.println(spiralOrder(matrix));

		matrix = new int[][] { { 1 }, { 4 } };
		System.out.println(spiralOrder(matrix));

		matrix = new int[][] { { 1, 2, 3 } };
		System.out.println(spiralOrder(matrix));

		matrix = new int[][] { { 1, 2, 3, 4 }, { 5, 6, 7, 8 }, { 9, 10, 11, 12 } };
		System.out.println(spiralOrder(matrix));
	}
}
