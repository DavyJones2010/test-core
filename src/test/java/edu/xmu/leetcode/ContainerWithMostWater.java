package edu.xmu.leetcode;

import org.junit.Test;

public class ContainerWithMostWater {

	public int maxArea(int[] height) {
		int startX = 0;
		int endX = height.length - 1;
		int maxWater = 0;
		while (endX > startX) {
			maxWater = Math.max((endX - startX) * Math.min(height[startX], height[endX]), maxWater);
			if (height[startX] < height[endX]) {
				startX++;
			} else {
				endX--;
			}
		}
		return maxWater;
	}

	public int maxAreaWithOn2Complexity(int[] height) {
		int startX = 0;
		int endX = 0;
		int length = height.length - 1;

		int maxWater = 0;
		while (startX < length) {
			endX = startX;
			int segmentMin = height[startX];
			int expectedMax = (length - startX) * segmentMin;
			if (expectedMax > maxWater) {
				while (endX <= length) {
					if (height[endX] < segmentMin) {
						segmentMin = height[endX];
					}
					int water = (endX - startX) * segmentMin;
					if (water > maxWater) {
						maxWater = water;
					}
					int expectedMaxWater = (length - startX) * segmentMin;
					if (expectedMaxWater < maxWater) {
						startX = endX;
						break;
					}
					endX++;
				}
			}
			startX++;
		}
		return maxWater;
	}

	@Test
	public void test() {
		int[] height = new int[] { 1, 2, 3, 4, 1, 0 };
		System.out.println(maxArea(height));

		height = new int[] {};
		System.out.println(maxArea(height));

		height = new int[] { 1, 2, 4, 3 };
		System.out.println(maxArea(height));
	}
}
