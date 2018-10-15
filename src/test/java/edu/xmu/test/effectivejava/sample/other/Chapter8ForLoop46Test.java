package edu.xmu.test.effectivejava.sample.other;

import static org.junit.Assert.assertEquals;

import java.util.ConcurrentModificationException;
import java.util.List;

import org.junit.Test;

import com.google.common.collect.Lists;

public class Chapter8ForLoop46Test {
	@Test(expected = ConcurrentModificationException.class)
	public void loopTest() {
		List<String> strs = Lists.newArrayList("a", "b", "c", "d", "e");
		for (int i = 0; i < strs.size(); i++) {
			if ("c".equals(strs.get(i))) {
				// It is ok when we are using index
				strs.remove(strs.get(i));
			}
		}
		assertEquals(4, strs.size());

		strs = Lists.newArrayList("a", "b", "c", "d", "e");
		for (String str : strs) {
			if ("d".equals(str)) {
				strs.remove(str);
				break;
			}
		}
		assertEquals(4, strs.size());

		strs = Lists.newArrayList("a", "b", "c", "d", "e");
		for (String str : strs) {
			if ("d".equals(str)) {
				strs.remove(str);
			} else {
				// "e" will never be printed, because hasNext() returns "false"
				System.out.println(str);
			}
		}
		assertEquals(4, strs.size());

		strs = Lists.newArrayList("a", "b", "c", "d", "e");
		// Exception will be thrown because hasNext() returns "true" and next() will check modCount
		for (String str : strs) {
			if ("c".equals(str)) {
				strs.remove(str);
			}
		}
	}

	@Test
	public void loopTest2() {
		// getSize will be invoked twice!
		for (int i = 0; i < getSize(); i++) {
			// do something
		}

		// getString will be invoked only once!
		for (String str : getString()) {
			System.out.println(str);
		}
	}

	private Iterable<String> getString() {
		return Lists.newArrayList("A", "B");
	}

	private int getSize() {
		System.out.println("getSize() invoked");
		return 2;
	}
}
