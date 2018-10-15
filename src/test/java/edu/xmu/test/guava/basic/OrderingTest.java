package edu.xmu.test.guava.basic;

import static org.junit.Assert.assertEquals;

import java.util.Comparator;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.base.Splitter;
import com.google.common.collect.ComparisonChain;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.Lists;
import com.google.common.collect.Ordering;

public class OrderingTest {
	List<String> versionList;
	List<String> descSortedVersionList;

	@Before
	public void setUp() {
		versionList = Lists.newArrayList("11-0", "1-1", "2-1", "11-21", "11-111", "0-1", "0", "1", "0-", "1-", "-0", "-1", "");
		descSortedVersionList = Lists.newArrayList("11-111", "11-21", "11-0", "2-1", "1-1", "0-1");
	}

	@Test
	public void sortTest() {
		Predicate<String> strSizePredicate = new Predicate<String>() {
			public boolean apply(String input) {
				return 2 == Splitter.on("-").omitEmptyStrings().splitToList(input).size();
			}
		};

		List<String> sortedVersionList = Ordering.from(new Comparator<String>() {
			public int compare(String o1, String o2) {
				Function<String, Integer> parseIntFunc = new Function<String, Integer>() {
					public Integer apply(String input) {
						return Integer.parseInt(input);
					}
				};
				List<Integer> prevVersions = Lists.transform(Splitter.on('-').trimResults().omitEmptyStrings().splitToList(o1), parseIntFunc);

				List<Integer> currVersions = Lists.transform(Splitter.on('-').trimResults().omitEmptyStrings().splitToList(o2), parseIntFunc);

				return ComparisonChain.start().compare(prevVersions.get(0), currVersions.get(0)).compare(prevVersions.get(1), currVersions.get(1)).result();
			}
		}).reverse().sortedCopy(FluentIterable.from(versionList).filter(strSizePredicate).toList());

		assertEquals(descSortedVersionList, sortedVersionList);
	}
}
