package edu.xmu.test.javase;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;

public class RandomTest {

	@Test
	public void randomStringTest() {
		System.out.println(RandomStringUtils.randomAlphanumeric(10));
	}
}
