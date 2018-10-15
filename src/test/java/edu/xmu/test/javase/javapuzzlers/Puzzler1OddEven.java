package edu.xmu.test.javase.javapuzzlers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class Puzzler1OddEven {
	/**
	 * WRONG impl, if i is negative odd value, it will return false because now i % 2 == -1<br>
	 */
	public boolean isOdd(int i) {
		return i % 2 == 1;
	}

	public boolean isOdd_2(int i) {
		return !(i % 2 == 0);
	}

	/**
	 * a more efficient version
	 */
	public boolean isOdd_3(int i) {
		return (i & 1) != 0;
	}

	/**
	 * What is the result of a%b? <br>
	 * [(a / b) * b + (a % b) == a] will always return true
	 */
	@Test
	public void modelTest() {
		assertEquals(1, 3 % 2);
		assertEquals(1, 3 - (3 / 2) * 2);

		assertEquals(-1, -3 % 2);
		assertEquals(-1, -3 - (-3 / 2) * 2);
	}

	@Test
	public void test() {
		assertTrue(isOdd(1));
		assertTrue(isOdd(11));

		// -1 is odd actually
		assertFalse(isOdd(-1));
		assertFalse(isOdd(-2));

		assertTrue(isOdd_2(1));
		assertTrue(isOdd_2(11));

		// -1 is odd actually
		assertTrue(isOdd_2(-1));
		assertFalse(isOdd_2(-2));

		assertTrue(isOdd_3(1));
		assertTrue(isOdd_3(11));

		// -1 is odd actually
		assertTrue(isOdd_3(-1));
		assertFalse(isOdd_3(-2));
	}

}
