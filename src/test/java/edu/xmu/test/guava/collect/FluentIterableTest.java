package edu.xmu.test.guava.collect;

import static org.junit.Assert.assertEquals;

import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.google.common.base.Function;
import com.google.common.base.Functions;
import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class FluentIterableTest {
	@Test
	public void filterTest() {
		List<String> list = Lists.newArrayList("A", "B", "C", "D");

		List<String> filteredList = FluentIterable.from(list).filter(Predicates.and(new Predicate<String>() {
			public boolean apply(String input) {
				return "A".compareTo(input) < 0;
			}
		}, new Predicate<String>() {
			public boolean apply(String input) {
				return "D".compareTo(input) > 0;
			}
		})).toList();

		assertEquals(2, filteredList.size());
		assertEquals("B", filteredList.get(0));
		assertEquals("C", filteredList.get(1));
	}

	@Test
	public void filterTest2() {
		List<String> strList = Lists.newArrayList("A", "B", "C", "D", "E", "F");

		List<String> filteredStrList = FluentIterable.from(strList).filter(new Predicate<String>() {
			public boolean apply(String input) {
				return !"A".equals(input) && !"F".equals(input);
			}
		}).toList();
		System.out.println(filteredStrList);
	}

	@Test
	public void transformTest() {
		List<String> list = Lists.newArrayList("A", "B", "C", "D", "E");

		Map<String, String> map = Maps.newHashMap();
		map.put("A", "A for Alcohol");
		map.put("B", "B for Boycott");
		map.put("C", "C for Combine");
		List<String> transformedList = FluentIterable.from(list).transform(Functions.forMap(map, "Not Defined")).toList();

		List<String> expectedList = Lists.newArrayList("A for Alcohol", "B for Boycott", "C for Combine", "Not Defined", "Not Defined");

		assertEquals(expectedList, transformedList);
	}

	@Test
	public void toMapTest() {
		List<String> list = Lists.newArrayList("A", "B", "C", "D", "E");

		Map<String, String> map = FluentIterable.from(list).toMap(new Function<String, String>() {
			public String apply(String input) {
				return input.concat("_VALUE");
			}
		});
		System.out.println(map);
	}

	@Test
	public void uniqueIndexTest() {
		List<String> list = Lists.newArrayList("A", "B", "C", "D", "E");

		Map<String, String> map = FluentIterable.from(list).uniqueIndex(new Function<String, String>() {
			public String apply(String input) {
				return input.concat("_KEY");
			}
		});
		System.out.println(map);
	}
}
