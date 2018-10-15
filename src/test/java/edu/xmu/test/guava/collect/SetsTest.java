package edu.xmu.test.guava.collect;

import static org.junit.Assert.assertEquals;

import java.util.Set;

import org.junit.Test;

import com.google.common.collect.Sets;

public class SetsTest {
	@Test
	public void partitionTest() {
		Set<String> strSet1 = Sets.newHashSet("A", "B", "C", "D");
		Set<String> strSet2 = Sets.newHashSet("D", "M", "O", "G");
		Set<String> diffSet = Sets.difference(strSet1, strSet2);
		assertEquals(3, diffSet.size());

		Set<String> interSet = Sets.intersection(strSet1, strSet2);
		assertEquals(1, interSet.size());

		Set<String> unionSet = Sets.union(strSet1, strSet2);
		assertEquals(7, unionSet.size());

		strSet1.add("N");
		assertEquals(4, diffSet.size());
	}
}
