package edu.xmu.test.javase;

import static org.junit.Assert.assertEquals;

import java.util.Collections;
import java.util.List;

import org.junit.Test;

import com.google.common.collect.Lists;

public class ArrayListTest {
	@Test(expected = IndexOutOfBoundsException.class)
	public void arrayListTest() {
		List<String> list = Lists.newArrayListWithCapacity(10);
		list.get(0);
	}

	@Test(expected = IndexOutOfBoundsException.class)
	public void arrayListTest2() {
		List<String> list = Lists.newArrayListWithExpectedSize(10);
		list.get(0);
	}

	@Test
	public void arrayListTest3() {
		List<String> list = Collections.nCopies(10, "");
		// the returned list is immutable
		assertEquals("", list.get(0));
	}
}
