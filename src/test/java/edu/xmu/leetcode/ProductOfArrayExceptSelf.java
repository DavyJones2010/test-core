package edu.xmu.leetcode;

import java.util.Arrays;

import org.junit.Test;

/**
 * {@link <a href="https://leetcode.com/problems/product-of-array-except-self/">Product of Array Except Self</a>} <br>
 * Inspired by {@link <a href="http://www.cnblogs.com/grandyang/p/4650187.html">Solution for Product of Array Except Self</a>}
 */
public class ProductOfArrayExceptSelf {
	public int[] productExceptSelf(int[] nums) {
		int[] prods = new int[nums.length];
		int prod = 1;
		prods[0] = 1;

		for (int i = 0; i < nums.length - 1; i++) {
			prod *= nums[i];
			prods[i + 1] = prod;
		}
		prod = 1;
		for (int i = nums.length - 1; i > 0; i--) {
			prod *= nums[i];
			prods[i - 1] *= prod;
		}

		return prods;
	}

	public int[] productExceptSelf_2(int[] nums) {
		int[] prods = new int[nums.length];
		int forwardProd = 1;
		int backwardProd = 1;

		int i = 0;
		int j = nums.length - 1;
		prods[i] = 1;
		prods[j] = 1;
		while (i < (nums.length - 1) && j > 0) {
			forwardProd *= nums[i];
			backwardProd *= nums[j];

			if ((j - i > 1)) {
				prods[j - 1] = 1;
				prods[i + 1] = 1;
			}

			prods[j - 1] *= backwardProd;
			prods[i + 1] *= forwardProd;
			i++;
			j--;
		}

		return prods;
	}

	@Test
	public void test() {
		System.out.println(Arrays.toString(productExceptSelf_2(new int[] { 1, 2, 0, 4, 5 })));
		System.out.println(Arrays.toString(productExceptSelf_2(new int[] { 1, 2, 3, 4 })));
		System.out.println(Arrays.toString(productExceptSelf_2(new int[] { 1 })));
		System.out.println(Arrays.toString(productExceptSelf_2(new int[] { 2, 3, 5, 0 })));
	}
}
