package edu.xmu.test.javase.misc;

import java.util.ConcurrentModificationException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.junit.Test;

/**
 * {@link <a href="http://javahungry.blogspot.com/2014/04/fail-fast-iterator-vs-fail-safe-iterator-difference-with-example-in-java.html">Fail Fast vs Fail Safe</a>}
 *
 */
public class FailFastFailSafeIterator {

	@Test(expected = ConcurrentModificationException.class)
	public void faileFastIteratorTest() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("Apple", "iPhone");
		map.put("HTC", "HTC one");
		map.put("Samsung", "S5");
		Iterator<String> iterator = map.keySet().iterator();
		while (iterator.hasNext()) {
			map.get(iterator.next());
			map.put("Sony", "Xperia Z");
		}
	}

	@Test
	public void faileSafeIteratorTest() {
		Map<String, String> map = new ConcurrentHashMap<String, String>();
		map.put("Apple", "iPhone");
		map.put("HTC", "HTC one");
		map.put("Samsung", "S5");
		Iterator<String> iterator = map.keySet().iterator();
		while (iterator.hasNext()) {
			map.get(iterator.next());
			map.put("Sony", "Xperia Z");
		}
	}
}
