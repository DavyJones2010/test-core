package edu.xmu.test.javase;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class EncodingTest {

	@Test
	public void lengthTest() {
		String str = "你好";
		assertEquals(2, str.length());
	}
}
