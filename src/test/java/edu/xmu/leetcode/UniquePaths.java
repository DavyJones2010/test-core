package edu.xmu.leetcode;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

/**
 * Solution for: {@link <a href="https://leetcode.com/problems/unique-paths/">Unique Paths</a>} <br>
 */
public class UniquePaths {
	Map<String, Integer> cache = new HashMap<String, Integer>();

	public int uniquePaths2(int m, int n) {
		int[][] array = new int[m][n];
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++) {
				if (i == 0 || j == 0) {
					array[i][j] = 1;
				} else {
					array[i][j] = array[i - 1][j] + array[i][j - 1];
				}
			}
		}

		return array[m - 1][n - 1];
	}

	public int uniquePaths(int m, int n) {
		String key = m + "" + n;
		if (cache.containsKey(key)) {
			int num = cache.get(key);
			return num;
		}
		if (m == 0) {
			// cache.put(key, 1);
			return 1;
		}
		if (n == 0) {
			// cache.put(key, 1);
			return 1;
		}

		int num = uniquePaths(m - 1, n) + uniquePaths(m, n - 1);
		// cache.put(key, num);
		// cache.put(n + "" + m, num);
		return num;
	}

	@Test
	public void test() {
		System.out.println(uniquePaths2(1, 1));
		System.out.println(uniquePaths2(1, 2));
		System.out.println(uniquePaths2(2, 1));
		System.out.println(uniquePaths2(2, 2));
		System.out.println(uniquePaths2(3, 2));
		System.out.println(uniquePaths2(3, 1));
		System.out.println(uniquePaths2(2, 3));
		System.out.println(uniquePaths2(4, 4));
		System.out.println(uniquePaths2(2, 3));
		System.out.println(uniquePaths2(7, 3));
		System.out.println(uniquePaths2(3, 7));
		System.out.println(uniquePaths2(11, 7));
		System.out.println(uniquePaths2(7, 11));
		long start = System.currentTimeMillis();
		System.out.println(uniquePaths2(23, 12));
		System.out.println(System.currentTimeMillis() - start);
		System.out.println(uniquePaths2(12, 23));
	}
}
