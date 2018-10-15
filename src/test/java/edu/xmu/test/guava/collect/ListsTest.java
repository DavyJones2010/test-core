package edu.xmu.test.guava.collect;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;

import com.google.common.base.Function;
import com.google.common.collect.Lists;

public class ListsTest {
	@Test
	public void partitionTest() {
		List<String> strList = Lists.newArrayList("A", "B", "C", "D");

		List<List<String>> partedStrList = Lists.partition(strList, 2);
		assertEquals(2, partedStrList.size());
		assertEquals(2, partedStrList.get(0).size());
		assertEquals(2, partedStrList.get(1).size());

		partedStrList = Lists.partition(strList, 3);
		assertEquals(2, partedStrList.size());
		assertEquals(3, partedStrList.get(0).size());
		assertEquals(1, partedStrList.get(1).size());
	}

	@Test
	public void transformTest() {
		List<String> strList = Lists.newArrayList("A", "B", "C", "D");

		List<String> transformedStrList = Lists.transform(strList, new Function<String, String>() {
			public String apply(String input) {
				return input.concat("_POSTFIX");
			}
		});
		System.out.println(transformedStrList);
		strList.add("E");
		System.out.println(transformedStrList);
	}
}
