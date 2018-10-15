package edu.xmu.test.guava.collect;

import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.google.common.base.Function;
import com.google.common.base.Splitter;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.Maps;

public class MultimapTest {
	@Test
	public void putTest() {
		String str = "name=Davy|age=24\nname=Cal|age=22\nname=Larence|age=20";

		List<Map<String, String>> list = FluentIterable.from(Splitter.on('\n').split(str)).transform(new Function<String, Map<String, String>>() {
			public Map<String, String> apply(String input) {
				return Splitter.on('|').withKeyValueSeparator('=').split(input);
			}
		}).toList();
		System.out.println(list);

		Map<String, String> map = Maps.newHashMap();
		map.put("name", "");
		map.put("age", "");
	}
}
