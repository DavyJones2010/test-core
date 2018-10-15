package edu.xmu.leetcode;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

/**
 * Solution for: {@link <a href="https://leetcode.com/problems/n-queens/">N-Queens</a>}
 */
public class NQueens {

	public List<List<String>> solveNQueens(int n) {
		String[][] array = new String[n][n];
		for (int i = 0; i < array.length; i++) {
			for (int j = 0; j < array[i].length; j++) {
				array[i][j] = "N";
			}
		}
		List<String[][]> solutions = getSolution(n - 1, array);
		return transformToListOfString(solutions);
	}

	private List<List<String>> transformToListOfString(List<String[][]> solutions) {
		List<List<String>> res = new ArrayList<List<String>>();
		for (String[][] solution : solutions) {
			List<String> list = new ArrayList<String>();
			for (String[] col : solution) {
				StringBuilder row = new StringBuilder();
				for (String cell : col) {
					if ("Q".equals(cell)) {
						row.append("Q");
					} else {
						row.append(".");
					}
				}
				list.add(row.toString());
			}
			res.add(list);
		}
		return res;
	}

	public List<String[][]> getSolution(int n, String[][] board) {
		List<String[][]> solutions = new ArrayList<String[][]>();
		for (int x = 0; x < board.length; x++) {
			if ("N".equals(board[x][n])) {
				String[][] tmpBoard = copyOfArray(board);
				tmpBoard[x][n] = "Q";
				if (n == 0) {
					solutions.add(tmpBoard);
				} else {
					invalidateRow(n, tmpBoard);
					invalidateColumn(x, tmpBoard);
					invalidateDiag(x, n, tmpBoard);
					List<String[][]> tmpSolutions = getSolution(n - 1, tmpBoard);
					if (!tmpSolutions.isEmpty()) {
						solutions.addAll(tmpSolutions);
					}
				}
			}
		}
		return solutions;
	}

	private String[][] copyOfArray(String[][] board) {
		String[][] copy = new String[board.length][board.length];
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[i].length; j++) {
				copy[i][j] = board[i][j];
			}
		}
		return copy;
	}

	private void invalidateDiag(int x, int y, String[][] board) {
		int currentX = x;
		int currentY = y;
		while ((currentX >= 0 && currentX < board.length) && (currentY >= 0 && currentY < board.length)) {
			if ("N".equals(board[currentX][currentY])) {
				board[currentX][currentY] = "Y";
			}
			currentX--;
			currentY--;
		}
		currentX = x;
		currentY = y;
		while ((currentX >= 0 && currentX < board.length) && (currentY >= 0 && currentY < board.length)) {
			if ("N".equals(board[currentX][currentY])) {
				board[currentX][currentY] = "Y";
			}
			currentX--;
			currentY--;
		}
		currentX = x;
		currentY = y;
		while ((currentX >= 0 && currentX < board.length) && (currentY >= 0 && currentY < board.length)) {
			if ("N".equals(board[currentX][currentY])) {
				board[currentX][currentY] = "Y";
			}
			currentX++;
			currentY--;
		}
		currentX = x;
		currentY = y;
		while ((currentX >= 0 && currentX < board.length) && (currentY >= 0 && currentY < board.length)) {
			if ("N".equals(board[currentX][currentY])) {
				board[currentX][currentY] = "Y";
			}
			currentX--;
			currentY++;
		}
	}

	private void invalidateColumn(int x, String[][] board) {
		for (int i = 0; i < board[x].length; i++) {
			if ("N".equals(board[x][i])) {
				board[x][i] = "Y";
			}
		}
	}

	private void invalidateRow(int y, String[][] board) {
		for (int i = 0; i < board.length; i++) {
			if ("N".equals(board[i][y])) {
				board[i][y] = "Y";
			}
		}
	}

	@Test
	public void test() {
		List<List<String>> res = solveNQueens(4);
		System.out.println(res.size());
		System.out.println(res);
	}
}
