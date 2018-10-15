package edu.xmu.test.effectivejava.sample.other;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

public class Chapter5PreferListToArray25Test {
	@Test(expected = ArrayStoreException.class)
	public void arrayRTETest() {
		// Compatible types, run time error
		Object[] array = new Long[1];
		array[0] = "Hello world!";
	}

	@Test
	public void listCTETest() {
		// Incompatible types, compilation time error
		// List<Long> list = new ArrayList<Long>();
	}

	@Test(expected = UnsupportedOperationException.class)
	public void arrayToListTest() {
		List<String> list = Arrays.asList("a", "b");
		list.add("c");
	}
}