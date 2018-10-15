package edu.xmu.test.javase.java8;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import org.junit.Assert;
import org.junit.Test;

import com.google.common.base.Function;
import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

/**
 * Java 8 new feature <b>Stream</b> sample inspired by <a href="">i-feve stream tutorial</a>
 */
public class StreamTest {
	private static enum Status {
		OPEN, CLOSED
	}

	private static final class Task {
		private final Status status;
		private final Integer points;

		Task(final Status status, final Integer points) {
			this.status = status;
			this.points = points;
		}

		public Integer getPoints() {
			return points;
		}

		public Status getStatus() {
			return status;
		}

		@Override
		public String toString() {
			return String.format("[%s, %d]", status, points);
		}
	}

	@Test
	public void streamTest() {
		List<Task> tasks = Arrays.asList(new Task(Status.OPEN, 10), new Task(Status.OPEN, 10), new Task(Status.OPEN, 10), new Task(Status.OPEN, 20), new Task(Status.CLOSED, 10),
				new Task(Status.OPEN, 30));
		List<Integer> values = tasks.stream().filter(t -> Status.OPEN == t.getStatus()).mapToInt(Task::getPoints).boxed().collect(Collectors.toList());
		assertEquals(5, values.size());

		int sum = tasks.stream().filter(t -> Status.OPEN == t.getStatus()).mapToInt(t -> t.getPoints()).sum();
		assertEquals(80, sum);

		assertEquals(90, tasks.stream().map(Task::getPoints).reduce(Integer::sum).orElse(0).intValue());
		assertEquals(90, tasks.stream().map(Task::getPoints).reduce(0, Integer::sum).intValue());
		assertEquals(91, tasks.stream().map(Task::getPoints).reduce(1, Integer::sum).intValue());
	}

	@Test
	public void streamTest2() {
		String str = "empCount=21;deposit=2100|empCount=22;deposit=2000|empCount=50;deposit=3000";
		List<Map<String, Double>> list = Splitter.on('|').splitToList(str).stream().map((s) -> {
			return Maps.transformValues(Splitter.on(';').withKeyValueSeparator('=').split(s), new Function<String, Double>() {
				@Override
				public Double apply(String input) {
					return Double.parseDouble(input);
				}
			});
		}).collect(Collectors.toList());
		System.out.println(list);
		double maxAvgDeposit = list.stream().map((map) -> {
			double avgDeposit = map.get("deposit") / map.get("empCount");
			return avgDeposit;
		}).max(Comparator.naturalOrder()).get();
		System.out.println(maxAvgDeposit);

		double minAvgDeposit = list.stream().map((map) -> {
			double avgDeposit = map.get("deposit") / map.get("empCount");
			return avgDeposit;
		}).max(Comparator.reverseOrder()).get();
		System.out.println(minAvgDeposit);
	}

	@Test
	public void streamConversion_FilterTest() {
		List<String> users = Lists.newArrayList("A", "B", "C", "D");
		List<String> filteredUsers = users.stream().filter((str) -> !"A".equals(str)).collect(Collectors.toList());
		Assert.assertArrayEquals(new String[] { "B", "C", "D" }, filteredUsers.toArray());

		int userCount = (int) users.stream().filter((str) -> "A".equals(str)).count();
		assertEquals(1, userCount);
	}

	@Test
	public void streamConversion_DistinctTest() {
		List<String> users = Lists.newArrayList("A", "B", "C", "D", "A", "B", "E");
		assertEquals("A,B,C,D,E", users.stream().distinct().collect(Collectors.joining(",")));
	}

	@Test
	public void streamConversion_MapTest() {
		List<String> users = Lists.newArrayList("A", "B", "C", "D", "A", "B", "E");
		assertEquals("a,b,c,d,e", users.stream().distinct().map(String::toLowerCase).collect(Collectors.joining(",")));
	}

	@Test
	public void streamConversion_FlatMapTest() {
		List<Developer> devs = Lists.newArrayList(new Developer("Yang", Lists.newArrayList("Chinese", "English")),
				new Developer("Zhang", Lists.newArrayList("Chinese", "Japanese")), new Developer("Li", Lists.newArrayList("English", "German")));
		assertEquals("Chinese,English,Japanese,German",
				devs.stream().distinct().map((a) -> a.getLanguages()).flatMap((langs) -> langs.stream()).distinct().collect(Collectors.joining(",")));

		List<String> users = Lists.newArrayList("A_A", "A_B", "C_C", "C_D");
		assertEquals("A,A,A,B,C,C,C,D", users.stream().flatMap((user) -> Splitter.on('_').splitToList(user).stream()).collect(Collectors.joining(",")));
	}

	@Test
	public void streamConversion_Peek_Limit_Skip_Sort_Test() {
		List<String> users = Lists.newArrayList("A_A", "A_B", "C_C", "C_D");
		users.stream().peek((str) -> {
			System.out.println(str);
		}).flatMap((user) -> Splitter.on('_').splitToList(user).stream()).peek((str) -> {
			System.out.println(str);
		}).collect(Collectors.joining(","));

		assertEquals("C_D,C_C", users.stream().skip(1).sorted(Comparator.reverseOrder()).limit(2).collect(Collectors.joining(",")));
	}

	/**
	 * Comparator chaining sample
	 */
	@Test
	public void streamConversion_Sort_Test() {
		List<String> versions = Lists.newArrayList("1.10.2.3", "2.10.2.3", "2.1.2.3", "11.10.2.3", "11.10.2.1");
		List<String> sortedVersions = versions
				.stream()
				.sorted(Comparator.comparingInt(str -> Integer.valueOf(Splitter.on(".").splitToList((String) str).get(0)))
						.thenComparingInt(str -> Integer.valueOf(Splitter.on(".").splitToList((String) str).get(1)))
						.thenComparingInt(str -> Integer.valueOf(Splitter.on(".").splitToList((String) str).get(2)))
						.thenComparingInt(str -> Integer.valueOf(Splitter.on(".").splitToList((String) str).get(3)))).collect(Collectors.toList());
		Assert.assertArrayEquals(new String[] { "1.10.2.3", "2.1.2.3", "2.10.2.3", "11.10.2.1", "11.10.2.3" }, sortedVersions.toArray());
	}

	@Test
	public void streamReduce_CollectTest() {
		List<String> users = Lists.newArrayList("A_A", "A_B", "C_C", "C_D");

		Assert.assertArrayEquals(new String[] { "A_A", "A_B", "C_C", "C_D" }, users.stream().collect(Collectors.toList()).toArray());

		/*
		 * Supplier supplier是一个工厂函数，用来生成一个新的容器 <br/>
		 * BiConsumer accumulator也是一个函数，用来把Stream中的元素添加到结果容器中 <br/>
		 * BiConsumer combiner还是一个函数，用来把中间状态的多个结果容器合并成为一个（并发的时候会用到）
		 */
		List<String> collectedUsers = users.stream().peek((str) -> {
			System.out.println(str);
		}).collect(() -> {
			System.out.println("Created new ArrayList");
			return new ArrayList<String>();
		}, (a, b) -> {
			System.out.println("Accumulated");
			((ArrayList<String>) a).add(b);
		}, (a, b) -> {
			System.out.println("Consumed");
			a.addAll(b);
		});
		Assert.assertArrayEquals(new String[] { "A_A", "A_B", "C_C", "C_D" }, collectedUsers.stream().collect(Collectors.toList()).toArray());
	}

	@Test
	public void streamReduce_ReduceTest() {
		/**
		 * Demo for: <br/>
		 * Optional<T> reduce(BinaryOperator<T> accumulator);
		 */
		List<Integer> ints = Lists.newArrayList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
		// if ints.size > 1; the first two elements will be passed into accumulator (sum, item)->{...} <br/>
		// The result will be passed into next accumulator (sum, item) -> {...} as "sum", and the third element will be as "item"
		System.out.println("ints sum is:" + ints.stream().reduce((sum, item) -> {
			System.out.println("Accumulating: sum=" + sum + ", item=" + item);
			return sum + item;
		}).get());

		// if ints.size <= 1; the accumulator (sum, item)->{...} will never be invoked, and the first element will be returned directly<br/>
		ints = Lists.newArrayList(1);
		System.out.println("ints sum is:" + ints.stream().reduce((sum, item) -> {
			System.out.println("Accumulating: sum=" + sum + ", item=" + item);
			return sum + item;
		}).get());

		/**
		 * Demo for: <br/>
		 * T reduce(T identity, BinaryOperator<T> accumulator);
		 */
		ints = Lists.newArrayList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
		// The identity is actually the initial value;
		// If ints.size > 0; the identity will be as "sum" and the first element will be as "item" passed into accumulator (sum, item)->{...} <br/>
		// The result will be passed into next accumulator (sum, item) -> {...} as "sum", and the second element will be as "item"
		int sum = ints.stream().reduce(11, (accumulator, item) -> {
			System.out.println("Accumulating: accumulator=" + accumulator + ", item=" + item);
			return accumulator + item;
		}).intValue();
		System.out.println(sum);

		// if ints.size == 0; the accumulator (sum, item)->{...} will never be invoked, and the identity will be returned directly<br/>
		ints = Lists.newArrayList();
		sum = ints.stream().reduce(11, (accumulator, item) -> {
			System.out.println("Accumulating: accumulator=" + accumulator + ", item=" + item);
			return accumulator + item;
		}).intValue();
		System.out.println(sum);
	}

	/**
	 * IntStream这类主要是为了性能考虑，不需要额外的装箱和拆箱
	 */
	@Test
	public void streamReduce_SumMaxMinAvgCountTest() {
		List<Integer> ints = Lists.newArrayList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
		assertEquals(55, ints.stream().mapToInt(i -> i).sum());
		assertEquals(10, ints.stream().mapToInt(i -> i).max().getAsInt());
		assertEquals(1, ints.stream().mapToInt(i -> i).min().getAsInt());
		assertEquals(5.5, ints.stream().mapToInt(i -> i).average().getAsDouble(), 0.001);
		assertEquals(10, ints.stream().mapToInt(i -> i).count());
	}

	@Test
	public void streamSearch_MatchTest() {
		List<String> users = Lists.newArrayList("A_A", "A_B", "C_C", "C_D");
		assertTrue(users.stream().allMatch(str -> str.contains("_")));
		assertFalse(users.stream().anyMatch(str -> str.contains(",")));
		assertTrue(users.stream().noneMatch(str -> str.contains(",")));
		assertFalse(users.stream().filter(str -> str.contains(",")).findFirst().isPresent());
	}

	@Test
	public void streamCollect_JoinerTest() {
		List<String> users = Lists.newArrayList("A", "B", "C", "D");
		assertEquals("[A,B,C,D]", users.stream().collect(Collectors.joining(",", "[", "]")));
	}

	@Test
	public void streamCollect_GroupByTest() {
		List<String> users = Lists.newArrayList("A_A", "A_B", "C_C", "C_D");
		Map<String, List<String>> grouped = users.stream().collect(Collectors.groupingBy(str -> {
			return ((String) str).substring(0, 1);
		}));
		assertEquals(2, grouped.size());
		Assert.assertArrayEquals(new String[] { "A_A", "A_B" }, grouped.get("A").toArray());
	}

	@Test
	public void mapReduceAndGroupByTest() {
		List<List<String>> list = Lists.newArrayList();
		list.add(Lists.newArrayList("A", "1"));
		list.add(Lists.newArrayList("A", "2"));
		list.add(Lists.newArrayList("A", "3"));
		list.add(Lists.newArrayList("B", "4"));
		list.add(Lists.newArrayList("B", "6"));
		Map<String, List<String>> collect = list.stream().collect(Collectors.groupingBy(e -> {
			return e.get(0);
		}, LinkedHashMap::new, Collectors.reducing(new ArrayList<String>(), (accu, item) -> {
			if (accu.isEmpty()) {
				return Lists.newArrayList(item);
			} else {
				int value = Integer.valueOf((accu).get(1)) + Integer.valueOf((item).get(1));
				((List<String>) accu).set(1, String.valueOf(value));
				return accu;
			}
		})));
		System.out.println(collect);

		list = Lists.newArrayList();
		list.add(Lists.newArrayList("A", "1"));
		list.add(Lists.newArrayList("A", "2"));
		list.add(Lists.newArrayList("A", "3"));
		list.add(Lists.newArrayList("B", "4"));
		list.add(Lists.newArrayList("B", "6"));
		Map<String, List<String>> reduce = list.stream().map(e -> {
			Map<String, List<String>> hashMap = new HashMap<String, List<String>>();
			hashMap.put(e.get(0), e);
			return hashMap;
		}).reduce((accu, item) -> {
			Entry<String, List<String>> entry = item.entrySet().iterator().next();
			if (accu.containsKey(entry.getKey())) {
				List<String> oldValue = accu.get(entry.getKey());
				List<String> addValue = entry.getValue();
				oldValue.set(1, String.valueOf(Integer.valueOf(oldValue.get(1)) + Integer.valueOf(addValue.get(1))));
			} else {
				accu.put(entry.getKey(), entry.getValue());
			}
			return accu;
		}).get();
		System.out.println(reduce);
	}

	class Developer {
		String name;
		List<String> languages;

		public Developer(String name, List<String> langs) {
			this.name = name;
			this.languages = langs;
		}

		public void add(String language) {
			this.languages.add(language);
		}

		public List<String> getLanguages() {
			return languages;
		}
	}
}
