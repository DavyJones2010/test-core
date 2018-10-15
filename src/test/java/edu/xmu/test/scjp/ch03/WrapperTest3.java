package edu.xmu.test.scjp.ch03;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class WrapperTest3 {
	@Test
	public void logicTest() {
		boolean b = false;
		assertFalse(b = false);
		assertTrue(b = true);
	}
}
