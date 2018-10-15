package edu.xmu.test.codekata.datamunging;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.tuple.Pair;

public interface DataMungingInterface {
	public abstract DataMungingInterface gather(String columnName, GatherType filterType, Comparable columnValue);

	public abstract DataMungingInterface max(String columnName);

	public abstract DataMungingInterface orderBy(Pair<String, Boolean>... orders);

	public abstract DataMungingInterface enrich(Pair<String, String> columnNamePair, Operator operator);

	public abstract DataMungingInterface max(Pair<String, String> columnNamePair, Operator operator);

	public abstract DataMungingInterface min(String columnName);

	public abstract DataMungingInterface min(Pair<String, String> columnNamePair, Operator operator);

	public abstract DataMungingInterface limit(int count);

	public abstract double avg(String colName);

	public abstract double sum(String colName);

	public abstract List<Comparable> distinct(String colName);

	public abstract DataMungingInterface end();

	public abstract List<Map<String, Comparable<?>>> getLines();

	static enum Operator {
		ADD {
			@Override
			public String getOps(String opsA, String opsB) {
				return "Sum Of " + opsA + " and " + opsB;
			}

			@Override
			public Double execOps(Number numA, Number numB) {
				return numA.doubleValue() + numB.doubleValue();
			}

		},
		SUBTRACT {
			@Override
			public String getOps(String opsA, String opsB) {
				return "Diff Of " + opsA + " and " + opsB;
			}

			@Override
			public Double execOps(Number numA, Number numB) {
				return numA.doubleValue() - numB.doubleValue();
			}
		},
		MULTIPLY {
			@Override
			public String getOps(String opsA, String opsB) {
				return "Product Of " + opsA + " and " + opsB;
			}

			@Override
			public Double execOps(Number numA, Number numB) {
				return numA.doubleValue() * numB.doubleValue();
			}
		},
		DIVIDE {
			@Override
			public String getOps(String opsA, String opsB) {
				return "Dividend Of " + opsA + " and " + opsB;
			}

			@Override
			public Double execOps(Number numA, Number numB) {
				return numA.doubleValue() / numB.doubleValue();
			}
		};

		public abstract String getOps(String opsA, String opsB);

		public abstract Double execOps(Number numA, Number numB);
	}

	static enum GatherType {
		EQUALS {
			@Override
			public boolean apply(Comparable actualValue, Comparable expectedValue) {
				return expectedValue.equals(actualValue);
			}
		},
		NOT_EQUALS {
			@Override
			public boolean apply(Comparable actualValue, Comparable expectedValue) {
				return !expectedValue.equals(actualValue);
			}
		},
		LESS_THAN {
			@Override
			public boolean apply(Comparable actualValue, Comparable expectedValue) {
				return actualValue.compareTo(expectedValue) < 0;
			}
		},
		GREATER_THAN {
			@Override
			public boolean apply(Comparable actualValue, Comparable expectedValue) {
				return actualValue.compareTo(expectedValue) > 0;
			}
		};
		public abstract boolean apply(Comparable actualValue, Comparable expectedValue);
	}
}
