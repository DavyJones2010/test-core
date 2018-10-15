package edu.xmu.leetcode;

import org.junit.Test;

public class BestTimeToBuyAndSellStockII {

	public int maxProfit(int[] prices) {
		boolean holdStock = false;
		int buyPosition = -1;
		int sellPosition = -1;
		int profit = 0;
		for (int i = 0; i < prices.length; i++) {
			if (holdStock && shouldSell(prices, i)) {
				holdStock = false;
				sellPosition = i;

				profit += (prices[sellPosition] - prices[buyPosition]);
			} else if (!holdStock && shouldBuy(prices, i)) {
				holdStock = true;
				buyPosition = i;
			}
		}
		return profit;
	}

	boolean shouldBuy(int[] prices, int i) {
		boolean shouldForward = false;
		if (i + 1 < prices.length) {
			shouldForward = true;
		}

		if (shouldForward && prices[i] < prices[i + 1]) {
			return true;
		}
		return false;
	}

	boolean shouldSell(int[] prices, int i) {
		if (i + 1 == prices.length) {
			return true;
		}
		if (prices[i] > prices[i + 1]) {
			return true;
		}
		return false;
	}

	@Test
	public void test() {
		int[] prices = { 1, 2, 3 };
		System.out.println(maxProfit(prices));

		prices = new int[] { 3, 2, 1 };
		System.out.println(maxProfit(prices));

		prices = new int[] { 3, 2, 1, 2, 3 };
		System.out.println(maxProfit(prices));

		prices = new int[] { 3, 2, 1, 2, 3, 2, 3 };
		System.out.println(maxProfit(prices));
	}
}
