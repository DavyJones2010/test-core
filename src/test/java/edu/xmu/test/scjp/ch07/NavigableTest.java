package edu.xmu.test.scjp.ch07;

import static org.junit.Assert.assertEquals;

import java.util.TreeMap;

import org.junit.Test;

import com.google.common.collect.Maps;

public class NavigableTest {

	@Test
	public void navigableTest() {
		TreeMap<String, String> map = Maps.newTreeMap();
		map.put("C", "C for chance");
		map.put("A", "A for adore");
		map.put("B", "B for bounce");
		map.put("a", "a for announce");
		map.put("e", "e for embrace");
		assertEquals("A", map.lowerKey("B"));
		assertEquals("C", map.higherKey("B"));
		assertEquals(null, map.lowerKey("A"));

		assertEquals("e", map.floorKey("e"));
		assertEquals("C", map.floorKey("K"));
		assertEquals("C", map.ceilingKey("C"));
		assertEquals("a", map.ceilingKey("K"));
	}
}
