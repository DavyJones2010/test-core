package edu.xmu.test.codekata.datamunging;

import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;

public class DataMungingPhaseOne {
	List<Map<String, Comparable<?>>> lines;

	public void setLines(List<Map<String, Comparable<?>>> lines) {
		this.lines = lines;
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
