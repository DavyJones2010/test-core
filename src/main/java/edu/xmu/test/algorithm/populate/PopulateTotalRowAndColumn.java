package edu.xmu.test.algorithm.populate;

import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Lists;
import com.google.common.collect.Table;

/**
 * Given content and proper group by func, this class will aggregate proper columns/rows, and populate new table/matrix like the populated SUM row/column below:
 * 
 * <pre>
 * \	a	b	c	SUM
 * A	1	2	3	6
 * B	2	3	4	9
 * C	3	4	5	12
 * D	4	5	6	15
 * SUM	10	14	18	42
 * </pre>
 *
 */
public class PopulateTotalRowAndColumn {

	public Table<String, String, Cell> populateGroupByFunc(Table<String, String, Cell> table) {
		GroupByFunction rowFunc = new GroupByFunction(x -> "SUM", y -> y.columnHeader, (prev, curr) -> prev.value + curr.value);
		GroupByFunction colFunc = new GroupByFunction(x -> x.rowHeader, y -> "SUM", (prev, curr) -> prev.value + curr.value);

		List<GroupByFunction> funcs = Lists.newArrayList(rowFunc, colFunc);
		Table<String, String, Cell> populatedTotalTable = HashBasedTable.create(table);

		for (GroupByFunction func : funcs) {
			Table<String, String, Cell> totalTable = HashBasedTable.create();
			populatedTotalTable.values().stream().filter(func.filterFunc).forEach(x -> {
				String rowHeader = func.rowFunc.apply(x);
				String columnHeader = func.colFunc.apply(x);
				if (totalTable.contains(rowHeader, columnHeader)) {
					Cell cell = totalTable.get(rowHeader, columnHeader);
					totalTable.put(rowHeader, columnHeader, new Cell(rowHeader, columnHeader, func.aggregateFunc.add(x, cell)));
				} else {
					totalTable.put(rowHeader, columnHeader, new Cell(rowHeader, columnHeader, x.value));
				}
			});
			populatedTotalTable.putAll(totalTable);
		}
		return populatedTotalTable;
	}

	static interface AggregateFunc {
		public double add(Cell prev, Cell curr);
	}

	static class GroupByFunction {
		Predicate<Cell> filterFunc;
		Function<Cell, String> rowFunc;
		Function<Cell, String> colFunc;
		AggregateFunc aggregateFunc;

		public GroupByFunction(Predicate<Cell> filterFunc, Function<Cell, String> rowFunc, Function<Cell, String> colFunc, AggregateFunc aggregateFunc) {
			super();
			this.filterFunc = filterFunc;
			this.rowFunc = rowFunc;
			this.colFunc = colFunc;
			this.aggregateFunc = aggregateFunc;
		}

		public GroupByFunction(Function<Cell, String> rowFunc, Function<Cell, String> colFunc, AggregateFunc func) {
			super();
			this.filterFunc = new Predicate<PopulateTotalRowAndColumn.Cell>() {
				public boolean test(Cell t) {
					return true;
				}
			};
			this.rowFunc = rowFunc;
			this.colFunc = colFunc;
			this.aggregateFunc = func;
		}

	}

	static class Cell {
		String rowHeader;
		String columnHeader;
		double value;

		public Cell(String rowHeader, String columnHeader, double value) {
			super();
			this.rowHeader = rowHeader;
			this.columnHeader = columnHeader;
			this.value = value;
		}

	}
}
