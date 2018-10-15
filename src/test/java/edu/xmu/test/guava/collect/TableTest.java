package edu.xmu.test.guava.collect;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Map;

import org.junit.Test;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.ImmutableTable;
import com.google.common.collect.Sets;
import com.google.common.collect.Table;

public class TableTest {
	@Test(expected = UnsupportedOperationException.class)
	public void tableViewTest() {
		Table<Integer, Integer, String> table = HashBasedTable.create();
		table.put(0, 0, "A");
		table.put(0, 1, "B");
		table.put(0, 2, "C");
		table.put(1, 0, "D");
		table.put(1, 1, "E");

		assertEquals("C", table.row(0).get(2));
		assertEquals("D", table.row(1).get(0));
		assertEquals("E", table.row(1).get(1));

		Map<Integer, String> rowMap = table.row(0);
		rowMap.put(3, "F"); // table is updated
		assertEquals("F", table.row(0).get(3));

		Map<Integer, String> colMap = table.column(2);
		colMap.put(3, "G"); // table is updated
		assertEquals("G", table.row(3).get(2));
		assertEquals("G", table.column(2).get(3));

		Table<Integer, Integer, String> immutableTable = ImmutableTable.<Integer, Integer, String> builder().putAll(table).build();
		rowMap = immutableTable.row(0);
		rowMap.put(4, "H"); // UnsupporttedOperationException will be thrown
	}

	/**
	 * {@link <a href="https://groups.google.com/forum/#!topic/guava-discuss/l056qV6acXU">Remove entire row/column</a>}
	 */
	@Test
	public void removeColumnRowTest() {
		Table<Integer, Integer, String> table = HashBasedTable.create();
		table.put(0, 0, "A");
		table.put(0, 1, "B");
		table.put(0, 2, "C");
		table.put(1, 0, "D");
		table.put(1, 1, "E");
		table.put(2, 0, "F");
		table.put(2, 1, "G");
		table.put(2, 2, "H");

		// remove entire row
		table.row(0).clear();
		assertTrue(table.row(0).isEmpty());// empty map will be returned
		assertEquals(Sets.newHashSet(1, 2), table.rowKeySet()); // removed row will not be iterated

		// remove entire column
		table.column(0).clear();
		assertTrue(table.column(0).isEmpty()); // empty map will be returned
		assertEquals(Sets.newHashSet(1, 2), table.columnKeySet());
	}
}
