package edu.xmu.leetcode;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

/**
 * <b>Dynamic Programming</b> <br>
 * {@link <a href="https://leetcode.com/problems/climbing-stairs/">Climbing Stairs</a>} <br>
 * State Transition Equation: f(i) = f(i-1) + f(i-2); f(1)=1; f(2)=2;
 *
 */
public class ClimbingStairsTest {

	static Map<Integer, Integer> cache;
	static {
		cache = new HashMap<Integer, Integer>();
		ClimbingStairsTest instance = new ClimbingStairsTest();
		for (int i = 1; i <= 10000; i++) {
			cache.put(i, instance.climbStairs(i));
		}
	}

	public int climbStairs(int n) {
		if (n == 1) {
			return 1;
		}
		if (n == 2) {
			return 2;
		}
		if (cache.containsKey(n)) {
			return cache.get(n);
		}
		int prevOne = 2;
		int prevTwo = 1;
		int current = 0;
		for (int i = 3; i <= n; i++) {
			current = prevOne + prevTwo;
			int tmp = prevOne;
			prevOne = current;
			prevTwo = tmp;
		}
		cache.put(n, current);
		return current;
	}

	@Test
	public void climbStairsTest() {
		System.out.println(climbStairs(3));
		System.out.println(climbStairs(4));
		System.out.println(climbStairs(5));
		System.out.println(climbStairs(6));
	}
}
