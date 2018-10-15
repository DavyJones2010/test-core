package edu.xmu.leetcode;

import org.junit.Test;

/**
 * Solution for {@link <a href="https://leetcode.com/problems/best-time-to-buy-and-sell-stock/">Best Time to Buy and Sell Stock</a>}
 */
public class BestTimeToBuyAndSellStock {

	public int maxProfit(int[] prices) {
		int buyPrice = Integer.MAX_VALUE;
		int sellPrice = Integer.MIN_VALUE;
		int max = 0;
		if (prices.length == 0 || prices.length == 1) {
			return max;
		}
		buyPrice = prices[0];
		sellPrice = prices[0];
		for (int i = 1; i < prices.length; i++) {
			if (prices[i - 1] >= prices[i] && (sellPrice - prices[i] > max)) {
				buyPrice = prices[i];
			} else if (prices[i - 1] <= prices[i] && (prices[i] - buyPrice > max)) {
				sellPrice = prices[i];
				max = prices[i] - buyPrice;
			}
		}
		return max;
	}

	@Test
	public void test() {
		int[] prices = { 3, 3, 5, 0, 0, 3, 1, 4 };
		System.out.println(maxProfit(prices));

		prices = new int[] {};
		System.out.println(maxProfit(prices));

		prices = new int[] { 1 };
		System.out.println(maxProfit(prices));

		prices = new int[] { 5, 7, 2, 7, 3, 3, 5, 3, 0 };
		System.out.println(maxProfit(prices));

		prices = new int[] { 2, 1, 2, 1, 0, 1, 2 };
		System.out.println(maxProfit(prices));

		prices = new int[] { 3, 2, 1, 2, 3 };
		System.out.println(maxProfit(prices));

		prices = new int[] { 3, 2, 1, 2, 3, 2, 3, 4 };
		System.out.println(maxProfit(prices));

		prices = new int[] { -3, 2, 1, 2, 3, 2, 3, 4, -10, 0 };
		System.out.println(maxProfit(prices));
	}
}
