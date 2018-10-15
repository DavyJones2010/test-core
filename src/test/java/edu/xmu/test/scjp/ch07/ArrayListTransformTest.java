package edu.xmu.test.scjp.ch07;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import com.google.common.collect.Lists;
import com.google.common.primitives.Ints;

public class ArrayListTransformTest {
	@Test
	public void asListTest() {
		int[] array = new int[] { 1, 2, 3, 4, 5 };
		List<Integer> list = Ints.asList(array);
		assertEquals(Lists.newArrayList(1, 2, 3, 4, 5), list);

		/*
		 * We can not make any modification to list2. The "array2" is stored in
		 * "list2", and "list2" is actually a view of "array2" but with any
		 * modification operation disabled
		 */
		Object[] array2 = new Object[] { 1, 2, 3, 4, 5 };
		List<Object> list2 = Arrays.asList(array2);
		assertEquals(Lists.newArrayList(1, 2, 3, 4, 5), list2);
		// list2.add(new Integer(6)); // exception thrown

		Object[] array3 = new Object[4];
		array3[0] = 1;
		List<Object> list3 = Arrays.asList(array3);
		assertEquals(1, list3.get(0));
		assertEquals(Lists.newArrayList(1, null, null, null), list3);
		array3[1] = 2;
		assertEquals(2, list3.get(1));
		assertEquals(Lists.newArrayList(1, 2, null, null), list3);
	}

	/**
	 * Source code for toArray:<br/>
	 * public Object[] toArray() { return Arrays.copyOf(elementData, size); }
	 */
	@Test
	public void toArrayTest() {
		List<Integer> list = Lists.newArrayList(1, 2, 3, 4, 5);
		Integer[] array = list.toArray(new Integer[] {});
		assertArrayEquals(new Integer[] { 1, 2, 3, 4, 5 }, array);

		// Array remain not changed
		list.add(6);
		assertArrayEquals(new Integer[] { 1, 2, 3, 4, 5 }, array);
	}
}
