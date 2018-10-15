package edu.xmu.test.javase;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.junit.Test;

public class MapTest {
	@Test(expected = NullPointerException.class)
	public void sample() {
		Map<String, String> map = new ConcurrentHashMap<>();
		map.get(null);
	}

	@Test
	public void hashMapTest() {
		Map<String, String> map = new HashMap<>();
		assertEquals(null, map.get(null));
	}
}
