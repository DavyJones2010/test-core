package edu.xmu.test.javase;

import org.junit.Test;

/**
 * Testcase for number format in java
 *
 */
public class NumberFormatTest {

	@Test
	public void formatTest() {
		System.out.println(String.format("%05d", 123));
	}

	@Test
	public void formatTest2() throws Exception {
		String format = String.format("visitCount=%d, hitCount=%d, missCount=%d, hitRate=%.3f", 100L, Long.MAX_VALUE, Long.MAX_VALUE - 1, (double)737287 / 740983);
		System.out.println(format);
		System.out.print("\n");
		System.out.println(format);
	}
}
