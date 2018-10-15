package edu.xmu.test.guava.basic;

import org.junit.Test;

import com.google.common.base.Preconditions;

public class PreconditionsTest {
	@Test(expected = IndexOutOfBoundsException.class)
	public void conditionsTest() {
		String[] data = new String[] { "A", "B", "C", "D" };
		getValue(data, 4);
	}

	private void getValue(String[] data, int i) {
		Preconditions.checkElementIndex(data.length, i);
	}
}
