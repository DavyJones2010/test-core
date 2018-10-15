package edu.xmu.test.codekata.datamunging;

import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;

public class DataMungingPhaseTwo {
	List<Map<String, Comparable<?>>> lines;

	public DataMungingPhaseTwo() {
		super();
	}

	public DataMungingPhaseTwo(List<Map<String, Comparable<?>>> lines) {
		super();
		this.lines = lines;
	}

	public DataMungingPhaseTwo setLines(List<Map<String, Comparable<?>>> lines) {
		this.lines = lines;
		return this;
	}

	public List<Map<String, Comparable<?>>> getExact(String colName, Comparable colValue) {
		List<Map<String, Comparable<?>>> res = Lists.newArrayList();
		for (Map<String, Comparable<?>> map : lines) {
			Comparable val = map.get(colName);
			if (val.compareTo(colValue) == 0) {
				res.add(map);
			}
		}
		return res;
	}

	public Comparable getMax(String colName) {
		Comparable max = null;
		for (Map<String, Comparable<?>> map : lines) {
			Comparable val = map.get(colName);
			if (null == max || val.compareTo(max) > 0) {
				max = val;
			}
		}
		return max;
	}

	public List<Map<String, Comparable<?>>> getMaxEntries(String colName) {
		return getExact(colName, getMax(colName));
	}

	public Number getMax(String colA, String colB) {
		Number max = null;
		for (Map<String, Comparable<?>> map : lines) {
			Number valA = (Number) map.get(colA);
			Number valB = (Number) map.get(colB);
			Number sum = valA.doubleValue() + valB.doubleValue();
			if (null == max || sum.doubleValue() - max.doubleValue() > 0) {
				max = sum;
			}
		}

		return max;
	}

	public Object getMax(String colA, String colB, String ops) {
		Number max = null;
		for (Map<String, Comparable<?>> map : lines) {
			Number valA = (Number) map.get(colA);
			Number valB = (Number) map.get(colB);
			Number sum = valA.doubleValue() + valB.doubleValue();
			if (null == max || sum.doubleValue() - max.doubleValue() > 0) {
				max = sum;
			}
		}

		return max;
	}

	public Comparable<?> getMin(String colName) {
		Comparable min = null;
		for (Map<String, Comparable<?>> map : lines) {
			Comparable val = map.get(colName);
			if (null == min || val.compareTo(min) < 0) {
				min = val;
			}
		}

		return min;
	}

	public Object getMin(String colA, String colB) {
		Number min = null;
		for (Map<String, Comparable<?>> map : lines) {
			Number valA = (Number) map.get(colA);
			Number valB = (Number) map.get(colB);
			Number sum = valA.doubleValue() + valB.doubleValue();
			if (null == min || sum.doubleValue() - min.doubleValue() < 0) {
				min = sum;
			}
		}

		return min;
	}

	public List<Map<String, Comparable<?>>> getMinEntries(String colName) {
		return getExact(colName, getMin(colName));
	}

	public List<Map<String, Comparable<?>>> limit(int limitCount) {
		limitCount = limitCount > lines.size() ? lines.size() : limitCount;
		return lines.subList(0, limitCount);
	}

	public Number getSum(String colName) {
		Number sum = new Double("0");
		for (Map<String, Comparable<?>> map : lines) {
			Number val = (Number) map.get(colName);
			sum = sum.doubleValue() + val.doubleValue();
		}
		return sum;
	}

	public Number getAvg(String colName) {
		Number sum = new Double("0");
		for (Map<String, Comparable<?>> map : lines) {
			Number val = (Number) map.get(colName);
			sum = sum.doubleValue() + val.doubleValue();
		}
		return sum.doubleValue() / lines.size();
	}

}
