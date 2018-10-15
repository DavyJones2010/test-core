package edu.xmu.leetcode;

import java.util.Arrays;

/**
 * Solution for {@link <a href="https://leetcode.com/problems/sudoku-solver/">Sudoku Solver</a>}
 */
public class SudokuSolver {
	static char[] nums = { '1', '2', '3', '4', '5', '6', '7', '8', '9' };

	public void solveSudoku(char[][] board) {
		findSolution(board, 0, 0);
	}

	private boolean findSolution(char[][] board, int startRow, int startCol) {
		char[] numRepo = Arrays.copyOf(nums, 9);
		for (int i = startRow; i < 9; i++) {
			for (int j = startCol; j < 9; j++) {
				if (board[i][j] == '.') {
					int kRemainder = i / 3;
					int mRemainder = j / 3;
					for (int k = 0; k < 9; k++) {
						for (int m = 0; m < 9; m++) {
							if (board[k][m] != '.') {
								// exclude collision values
								if (k == i || m == j || k / 3 == kRemainder && m / 3 == mRemainder) {
									numRepo[board[k][m] - '1'] = '0';
								}
							}
						}
					}
					for (char candidate : numRepo) {
						if ('0' != candidate) {
							board[i][j] = candidate;
							if (findSolution(board, i, 0)) {
								return true;
							} else {
								board[i][j] = '.';
							}
						}
					}
					return false;
				}
			}
		}
		return true;
	}
}
