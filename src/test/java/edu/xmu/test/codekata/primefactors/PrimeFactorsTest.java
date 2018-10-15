package edu.xmu.test.codekata.primefactors;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.google.common.collect.Lists;

public class PrimeFactorsTest {
	@Test
	public void generateTest() {
		assertEquals(Lists.newArrayList(2), PrimeFactors.generate(2));
		assertEquals(Lists.newArrayList(3), PrimeFactors.generate(3));
		assertEquals(Lists.newArrayList(2, 2), PrimeFactors.generate(4));
		assertEquals(Lists.newArrayList(7), PrimeFactors.generate(7));
		assertEquals(Lists.newArrayList(3, 3), PrimeFactors.generate(9));
		assertEquals(Lists.newArrayList(2, 2, 3), PrimeFactors.generate(12));
		assertEquals(Lists.newArrayList(2, 2, 2, 3), PrimeFactors.generate(24));
		assertEquals(Lists.newArrayList(31), PrimeFactors.generate(31));
	}
}
