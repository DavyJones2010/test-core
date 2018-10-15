package edu.xmu.test.effectivejava.sample.other;

import java.util.List;

import org.junit.Test;

import com.google.common.collect.Lists;

public class Chapter7NullMethod43Test {
	@Test
	public void nullValueTest() {
		List<Integer> values = getValuesWithNullRisk();
		if (null != values && values.contains(1)) {
			System.out.println("Contains 1");
		} else {
			System.out.println("Doesn't contain 1");
		}
		values = getValuesWithoutNullRisk();
		if (values.contains(1)) {
			System.out.println("Contains 1");
		} else {
			System.out.println("Doesn't contain 1");
		}
	}

	private List<Integer> getValuesWithNullRisk() {
		// ...
		return null;
	}

	/**
	 * We don't need to worry about the extra memory cost, because empty list is constant and shared globally
	 */
	private List<Integer> getValuesWithoutNullRisk() {
		// ...
		return Lists.newArrayList();
	}
}
