package edu.xmu.test.javase.javapuzzlers;

import java.math.BigDecimal;

import org.junit.Test;

public class Puzzler2FloatPrecision {
	@Test
	public void test() {
		// print 0.8999999999999999 instead of 0.90
		System.out.println(2.00 - 1.10);
		// print 0.90, but actually the result is still float, cannot be precisely presented
		System.out.printf("%.2f%n", 2.00 - 1.10);
		// we can use int instead
		System.out.println((200 - 100) + " cents");
		// we can use BigDecimal when calculating currency related problems
		System.out.println(BigDecimal.valueOf(2.00).subtract(BigDecimal.valueOf(1.10)));
	}
}
