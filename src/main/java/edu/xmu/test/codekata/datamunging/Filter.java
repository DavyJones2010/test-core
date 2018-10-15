package edu.xmu.test.codekata.datamunging;

import java.util.Comparator;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class Filter {
	private Function<Stream<Map<String, Comparable>>, Stream<Map<String, Comparable>>> function = x -> x;

	private Filter() {
	}

	public Filter create() {
		return new Filter();
	}

	public Filter addCondition(Condition condition) {
		function = function.andThen(condition::filter);
		return this;
	}

	public Filter addOrder(Order order) {
		function = function.andThen(order::order);
		return this;
	}

	public static class Order {
		Comparator<Map<String, Comparable>> comparator = (x, y) -> 0;

		public Order asc(String columnName) {
			comparator = comparator.thenComparing((x, y) -> x.get(columnName).compareTo(y.get(columnName)));
			return this;
		}

		public Order desc(String columnName) {
			comparator = comparator.thenComparing((x, y) -> y.get(columnName).compareTo(x.get(columnName)));
			return this;
		}

		public Stream<Map<String, Comparable>> order(Stream<Map<String, Comparable>> stream) {
			return stream.sorted(comparator);
		}
	}

	public static class Condition {
		Predicate<Map<String, Comparable>> predicate = x -> true;

		public Condition equals(String columnName, Comparable expectedValue) {
			predicate = predicate.and((row) -> {
				return expectedValue.equals(row.get(columnName));
			});
			return this;
		}

		public Condition notEquals(String columnName, Comparable expectedValue) {
			predicate = predicate.and((row) -> {
				return !expectedValue.equals(row.get(columnName));
			});
			return this;
		}

		public Condition greaterThan(String columnName, Comparable expectedValue) {
			predicate = predicate.and((row) -> {
				return expectedValue.compareTo(row.get(columnName)) > 0;
			});
			return this;
		}

		public Condition lessThan(String columnName, Comparable expectedValue) {
			predicate = predicate.and((row) -> {
				return expectedValue.compareTo(row.get(columnName)) < 0;
			});
			return this;
		}

		public Stream<Map<String, Comparable>> filter(Stream<Map<String, Comparable>> stream) {
			return stream.filter(predicate);
		}
	}
}
