package edu.xmu.test.javase;

import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class BitWiseMapTest {

	@Test
	public void getTest() {
		BitWiseMap<Long, String> map = new BitWiseMap<>();
		map.put(1L, "2的零次方");
		map.put(2L, "2的一次方");
		map.put(4L, "2的二次方");
		List<String> byKey = map.getByKey(5L);
		System.out.println(byKey.size());
		System.out.println(byKey);

		map.remove(1L);
		byKey = map.getByKey(5L);
		System.out.println(byKey.size());
		System.out.println(byKey);
	}

	@Test
	public void getTest2() {
		Map<Long, List<String>> map = Maps.newHashMap();
		map.put(1L, Lists.newArrayList("黑名单规则1", "黑名单规则2"));
		map.put(2L, Lists.newArrayList("黑名单规则1"));
		map.put(4L, Lists.newArrayList("黑名单规则2", "黑名单规则3"));
		System.out.println(MapUtils.getByKey(map, 15L));
	}
}
