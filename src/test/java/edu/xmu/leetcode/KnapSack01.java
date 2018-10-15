package edu.xmu.leetcode;

import java.util.Arrays;

import org.junit.Test;

/**
 * {@link <a href="http://shmilyaw-hotmail-com.iteye.com/blog/2009761">0-1 KnapSack</a>}
 */
public class KnapSack01 {

	int maxValue(int[] values, int[] weights, int limit) {
		if (limit == 0) {
			return 0;
		}
		if (values.length == 0) {
			return 0;
		}
		int value = values[values.length - 1];
		int weight = weights[weights.length - 1];

		int[] subValues = Arrays.copyOf(values, values.length - 1);
		int[] subWeights = Arrays.copyOf(weights, weights.length - 1);
		if (weight > limit) {
			return maxValue(subValues, subWeights, limit);
		} else {
			return Math.max(maxValue(subValues, subWeights, limit), (maxValue(subValues, subWeights, limit - weight) + value));
		}
	}

	@Test
	public void test() {
		int[] values = new int[] { 1, 2, 3, 4, 5 };
		int[] weights = new int[] { 3, 3, 3, 3, 3 };
		System.out.println(maxValue(values, weights, 10));

		values = new int[] { 1, 2, 3, 4, 5 };
		weights = new int[] { 1, 2, 3, 4, 5 };
		System.out.println(maxValue(values, weights, 10));

	}
}
