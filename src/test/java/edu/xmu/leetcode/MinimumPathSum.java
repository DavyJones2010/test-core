package edu.xmu.leetcode;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

/**
 * Solution for {@link <a href="https://leetcode.com/problems/minimum-path-sum/">Minimum Path Sum</a>}
 */
public class MinimumPathSum {
	Map<String, Integer> cache = new HashMap<String, Integer>();

	public int minPathSum(int[][] grid) {
		cache = new HashMap<String, Integer>();
		return minPathSum(grid, grid.length - 1, grid[0].length - 1, cache);
	}

	int minPathSum(int[][] grid, int m, int n, Map<String, Integer> cache) {
		String key = m + "-" + n;
		if (cache.containsKey(key)) {
			return cache.get(key);
		}
		int min = 0;
		if (m == 0) {
			for (int i = 0; i <= n; i++) {
				min += grid[m][i];
			}
		} else if (n == 0) {
			for (int i = 0; i <= m; i++) {
				min += grid[i][n];
			}
		} else {
			min = Math.min(grid[m][n] + minPathSum(grid, m - 1, n, cache), grid[m][n] + minPathSum(grid, m, n - 1, cache));
		}
		cache.put(key, min);
		return min;
	}

	@Test
	public void test() {
		int[][] grid = new int[][] { { 1, 2, 3 }, { 1, 4, 3 }, { 1, 3, 4 } };
		System.out.println(minPathSum(grid));

		grid = new int[][] { { 1, 2, 3, 0 }, { 1, 4, 3, 0 }, { 1, 3, 4, 0 } };
		System.out.println(minPathSum(grid));

		grid = new int[][] { { 1, 2, 3, 10 } };
		System.out.println(minPathSum(grid));

		grid = new int[][] { { 1, 0, 3, 2 }, { 3, 0, 4, 2 } };
		System.out.println(minPathSum(grid));

		grid = new int[][] { { 1 }, { 3 }, { 4 } };
		System.out.println(minPathSum(grid));

		grid = new int[][] { { 3, 4, 2, 1 }, { 2, 1, 1, 1 } };
		System.out.println(minPathSum(grid));

		grid = new int[][] { { 5, 1, 0, 4, 0, 1, 1, 6, 7, 3, 9, 9, 4, 6, 8, 1 }, { 9, 1, 0, 6, 4, 2, 8, 0, 1, 6, 0, 2, 7, 9, 0, 4 },
				{ 3, 2, 0, 3, 3, 3, 1, 3, 7, 3, 2, 1, 1, 2, 2, 0 }, { 5, 2, 8, 2, 7, 6, 2, 0, 5, 3, 2, 4, 4, 4, 8, 9 }, { 7, 0, 5, 2, 4, 6, 7, 1, 1, 1, 2, 2, 6, 6, 4, 1 },
				{ 0, 3, 5, 9, 1, 8, 0, 6, 3, 4, 0, 9, 9, 0, 9, 8 }, { 3, 4, 0, 7, 2, 8, 0, 4, 9, 4, 8, 5, 2, 5, 9, 4 }, { 0, 4, 4, 1, 4, 6, 0, 7, 0, 2, 7, 1, 3, 8, 9, 8 },
				{ 2, 0, 7, 4, 0, 7, 0, 1, 1, 1, 9, 5, 6, 8, 9, 6 }, { 4, 3, 9, 9, 1, 9, 8, 4, 2, 7, 5, 7, 5, 5, 5, 9 }, { 7, 4, 6, 9, 1, 8, 0, 4, 9, 9, 9, 7, 9, 8, 3, 4 },
				{ 4, 3, 5, 7, 4, 5, 1, 8, 3, 7, 7, 0, 4, 4, 2, 3 }, { 8, 0, 2, 9, 8, 2, 5, 8, 4, 4, 7, 3, 5, 1, 9, 1 }, { 6, 4, 8, 2, 2, 2, 1, 7, 1, 8, 7, 5, 5, 1, 0, 3 },
				{ 1, 2, 5, 0, 6, 0, 0, 0, 7, 7, 6, 4, 0, 5, 5, 8 }, { 2, 5, 1, 4, 9, 4, 1, 0, 2, 0, 5, 7, 4, 7, 3, 5 }, { 9, 8, 7, 8, 8, 9, 8, 5, 9, 6, 9, 9, 2, 6, 0, 6 },
				{ 4, 1, 2, 3, 5, 5, 4, 9, 5, 1, 9, 9, 9, 2, 7, 0 }, { 0, 6, 8, 0, 6, 9, 8, 7, 5, 7, 8, 9, 6, 8, 5, 0 } };
		System.out.println(minPathSum(grid));
	}
}
