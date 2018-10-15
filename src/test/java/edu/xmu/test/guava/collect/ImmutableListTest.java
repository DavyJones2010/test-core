package edu.xmu.test.guava.collect;

import java.util.Collections;
import java.util.List;

import org.junit.Test;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;

public class ImmutableListTest {
	@Test
	public void immutableTest() {
		List<String> strList = Lists.newArrayList("A", "B", "C");

		List<String> unStrList = ImmutableList.<String> builder().addAll(strList).build();
		System.out.println(unStrList);

		strList.add("D");
		System.out.println(unStrList);

		strList.remove(0);
		System.out.println(unStrList);
	}

	@Test
	public void unmodifiableTest() {
		List<String> strList = Lists.newArrayList("A", "B", "C");

		List<String> unStrList = Collections.unmodifiableList(strList);
		System.out.println(unStrList);

		strList.add("D");
		System.out.println(unStrList);

		strList.remove(0);
		System.out.println(unStrList);
	}

}
