package edu.xmu.leetcode;

import org.junit.Test;

/**
 * Solution for: {@link <a href="https://leetcode.com/problems/n-queens-ii/">N-Queens II</a>}
 */
public class NQueensII {

	public int totalNQueens(int n) {
		boolean[][] array = new boolean[n][n];
		int count = getSolution(n - 1, array);
		return count;
	}

	public int getSolution(int n, boolean[][] board) {
		int count = 0;
		for (int x = 0; x < board.length; x++) {
			if (board[x][n] == false) {
				boolean[][] tmpBoard = copyOfArray(board);
				tmpBoard[x][n] = true;
				invalidateRow(n, tmpBoard);
				invalidateColumn(x, tmpBoard);
				invalidateDiag(x, n, tmpBoard);
				if (n == 0) {
					count++;
				} else {
					count += getSolution(n - 1, tmpBoard);
				}
			}
		}
		return count;
	}

	private boolean[][] copyOfArray(boolean[][] board) {
		boolean[][] copy = new boolean[board.length][board.length];
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[i].length; j++) {
				copy[i][j] = board[i][j];
			}
		}
		return copy;
	}

	private void invalidateDiag(int x, int y, boolean[][] board) {
		int currentX = x;
		int currentY = y;
		while ((currentX >= 0 && currentX < board.length) && (currentY >= 0 && currentY < board.length)) {
			board[currentX][currentY] = true;
			currentX--;
			currentY--;
		}
		currentX = x;
		currentY = y;
		while ((currentX >= 0 && currentX < board.length) && (currentY >= 0 && currentY < board.length)) {
			board[currentX][currentY] = true;
			currentX--;
			currentY--;
		}
		currentX = x;
		currentY = y;
		while ((currentX >= 0 && currentX < board.length) && (currentY >= 0 && currentY < board.length)) {
			board[currentX][currentY] = true;
			currentX++;
			currentY--;
		}
		currentX = x;
		currentY = y;
		while ((currentX >= 0 && currentX < board.length) && (currentY >= 0 && currentY < board.length)) {
			board[currentX][currentY] = true;
			currentX--;
			currentY++;
		}
	}

	private void invalidateColumn(int x, boolean[][] board) {
		for (int i = 0; i < board[x].length; i++) {
			board[x][i] = true;
		}
	}

	private void invalidateRow(int y, boolean[][] board) {
		for (int i = 0; i < board.length; i++) {
			board[i][y] = true;
		}
	}

	@Test
	public void test() {
		int count = totalNQueens(7);
		System.out.println(count);
	}
}
