package edu.xmu.leetcode;

import java.util.Stack;

import org.junit.Test;

/**
 * Solution for {@link <a href="https://leetcode.com/problems/trapping-rain-water/">Trapping Rain Water</a>}
 *
 */
public class TrappingRainWater {
	public int trap(int[] height) {
		// TODO: Find solution
		Stack<Integer> stack = new Stack<Integer>();
		int total = 0;
		stack.push(height[0]);
		for (int i = 0; i < height.length; i++) {
			if (height[i + 1] < height[i]) {
				stack.push(height[i + 1]);
			} else {
				int val = height[i + 1];
				int curr = stack.pop();
				while (!stack.isEmpty() && val > curr) {
					total += (val - curr);
					curr = stack.pop();
				}
				if (!stack.isEmpty()) {
				}
			}
		}
		return total;
	}

	@Test
	public void test() {

	}
}
