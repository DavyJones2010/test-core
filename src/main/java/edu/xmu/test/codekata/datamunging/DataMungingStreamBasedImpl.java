package edu.xmu.test.codekata.datamunging;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.lang3.tuple.Pair;

import com.google.common.collect.ComparisonChain;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class DataMungingStreamBasedImpl implements DataMungingInterface {
	List<Map<String, Comparable<?>>> lines;

	private DataMungingStreamBasedImpl(Map<String, Comparable<?>> line) {
		this.lines = Lists.newArrayList(line);
	}

	private DataMungingStreamBasedImpl(List<Map<String, Comparable<?>>> lines) {
		this.lines = lines;
	}

	public static DataMungingInterface start(List<Map<String, Comparable<?>>> lines) {
		return new DataMungingStreamBasedImpl(lines);
	}

	@Override
	public DataMungingInterface gather(String columnName, GatherType filterType, Comparable columnValue) {
		return new DataMungingStreamBasedImpl(lines.stream().filter((row) -> {
			return filterType.apply(row.get(columnName), columnValue);
		}).collect(Collectors.toList()));
	}

	@Override
	public DataMungingInterface max(String columnName) {
		return gather(columnName, GatherType.EQUALS, lines.stream().max((o1, o2) -> {
			Comparable val1 = o1.get(columnName);
			Comparable val2 = o2.get(columnName);
			return val1.compareTo(val2);
		}).get().get(columnName));
	}

	@Override
	@SafeVarargs
	public final DataMungingInterface orderBy(Pair<String, Boolean>... orders) {
		return new DataMungingStreamBasedImpl(lines.stream().sorted((o1, o2) -> {
			ComparisonChain cc = ComparisonChain.start();
			for (Pair<String, Boolean> orderPair : orders) {
				if (orderPair.getRight()) {
					cc = cc.compare(o1.get(orderPair.getLeft()), o2.get(orderPair.getLeft()));
				} else {
					cc = cc.compare(o2.get(orderPair.getLeft()), o1.get(orderPair.getLeft()));
				}
			}
			return cc.result();
		}).collect(Collectors.toList()));
	}

	@Override
	public DataMungingInterface enrich(Pair<String, String> columnNamePair, Operator operator) {
		return new DataMungingStreamBasedImpl(lines.stream().map((line) -> {
			Map<String, Comparable<?>> map = Maps.newHashMap(line);
			Double res = operator.execOps((Number) map.get(columnNamePair.getLeft()), (Number) map.get(columnNamePair.getRight()));
			map.put(operator.getOps(columnNamePair.getLeft(), columnNamePair.getRight()), res);
			return map;
		}).collect(Collectors.toList()));
	}

	@Override
	public DataMungingInterface max(Pair<String, String> columnNamePair, Operator operator) {
		return new DataMungingStreamBasedImpl(enrich(columnNamePair, operator).getLines().stream().max((o1, o2) -> {
			Comparable val1 = o1.get(columnNamePair.getLeft());
			Comparable val2 = o2.get(columnNamePair.getRight());
			return val1.compareTo(val2);
		}).get());
	}

	@Override
	public DataMungingInterface min(String columnName) {
		return gather(columnName, GatherType.EQUALS, lines.stream().min((o1, o2) -> {
			Comparable val1 = o1.get(columnName);
			Comparable val2 = o2.get(columnName);
			return val1.compareTo(val2);
		}).get().get(columnName));
	}

	@Override
	public DataMungingInterface min(Pair<String, String> columnNamePair, Operator operator) {
		return new DataMungingStreamBasedImpl(enrich(columnNamePair, operator).getLines().stream().min((o1, o2) -> {
			Comparable val1 = o1.get(columnNamePair.getLeft());
			Comparable val2 = o2.get(columnNamePair.getRight());
			return val1.compareTo(val2);
		}).get());
	}

	@Override
	public DataMungingInterface limit(int count) {
		return new DataMungingStreamBasedImpl(lines.stream().limit(count).collect(Collectors.toList())) {
			@Override
			public DataMungingStreamBasedImpl limit(int count) {
				throw new IllegalStateException();
			}
		};
	}

	@Override
	public double avg(String colName) {
		return lines.stream().mapToDouble((val) -> {
			return ((Number) val.get(colName)).doubleValue();
		}).average().getAsDouble();
	}

	@Override
	public double sum(String colName) {
		return lines.stream().mapToDouble((val) -> {
			return ((Number) val.get(colName)).doubleValue();
		}).sum();
	}

	@Override
	public List<Comparable> distinct(String colName) {
		return lines.stream().map((val) -> {
			return val.get(colName);
		}).distinct().collect(Collectors.toList());
	}

	@Override
	public DataMungingStreamBasedImpl end() {
		return new DataMungingStreamBasedImpl(lines) {
			@Override
			public double avg(String colName) {
				throw new RuntimeException();
			}

			@Override
			public double sum(String colName) {
				throw new RuntimeException();
			}
		};
	}

	@Override
	public List<Map<String, Comparable<?>>> getLines() {
		return lines;
	}

}
