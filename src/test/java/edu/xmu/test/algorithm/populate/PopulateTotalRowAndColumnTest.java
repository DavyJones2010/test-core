package edu.xmu.test.algorithm.populate;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;

import edu.xmu.test.algorithm.populate.PopulateTotalRowAndColumn.Cell;

public class PopulateTotalRowAndColumnTest {
	/**
	 * 
	 * <pre>
	 * \	a	b	c	SUM
	 * A	1	2	3	6
	 * B	2	3	4	9
	 * C	3	4	5	12
	 * D	4	5	6	15
	 * SUM	10	14	18	42
	 * </pre>
	 */
	@Test
	public void populateGroupByFuncTest() {
		PopulateTotalRowAndColumn instance = new PopulateTotalRowAndColumn();
		Table<String, String, Cell> table = HashBasedTable.create();
		table.put("A", "a", new Cell("A", "a", 1));
		table.put("B", "a", new Cell("B", "a", 2));
		table.put("C", "a", new Cell("C", "a", 3));
		table.put("D", "a", new Cell("D", "a", 4));

		table.put("A", "b", new Cell("A", "b", 2));
		table.put("B", "b", new Cell("B", "b", 3));
		table.put("C", "b", new Cell("C", "b", 4));
		table.put("D", "b", new Cell("D", "b", 5));

		table.put("A", "c", new Cell("A", "c", 3));
		table.put("B", "c", new Cell("B", "c", 4));
		table.put("C", "c", new Cell("C", "c", 5));
		table.put("D", "c", new Cell("D", "c", 6));
		Table<String, String, Cell> populatedTable = instance.populateGroupByFunc(table);

		assertEquals(20, populatedTable.values().size());

		assertEquals(6, populatedTable.column("SUM").get("A").value, 0.001);
		assertEquals(9, populatedTable.column("SUM").get("B").value, 0.001);
		assertEquals(12, populatedTable.column("SUM").get("C").value, 0.001);
		assertEquals(15, populatedTable.column("SUM").get("D").value, 0.001);
		assertEquals(42, populatedTable.column("SUM").get("SUM").value, 0.001);

		assertEquals(10, populatedTable.column("a").get("SUM").value, 0.001);
		assertEquals(14, populatedTable.column("b").get("SUM").value, 0.001);
		assertEquals(18, populatedTable.column("c").get("SUM").value, 0.001);
		assertEquals(42, populatedTable.column("SUM").get("SUM").value, 0.001);
	}
}
