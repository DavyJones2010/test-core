package edu.xmu.test.excel.util;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class CellReferenceUtilTest {
	@Test
	public void getColIndexTest() {
		int colIndex = CellReferenceUtil.getColIndex("A");
		assertEquals(0, colIndex);

		colIndex = CellReferenceUtil.getColIndex("AA");
		assertEquals(26, colIndex);

		colIndex = CellReferenceUtil.getColIndex("AAA");
		assertEquals(26 * 26 + 26, colIndex);
	}

	@Test
	public void getColNameTest() {
		String colName = CellReferenceUtil.getColName(0);
		assertEquals("A", colName);

		colName = CellReferenceUtil.getColName(26);
		assertEquals("AA", colName);

		colName = CellReferenceUtil.getColName(26 * 26 + 26);
		assertEquals("AAA", colName);
	}

	@Test
	public void getRowIndexTest() {
		int rowIndex = CellReferenceUtil.getRowIndex("8");
		assertEquals(7, rowIndex);

		rowIndex = CellReferenceUtil.getRowIndex("27");
		assertEquals(26, rowIndex);
	}

	@Test
	public void getRowNameTest() {
		String rowName = CellReferenceUtil.getRowName(7);
		assertEquals("8", rowName);

		rowName = CellReferenceUtil.getRowName(26);
		assertEquals("27", rowName);
	}

	@Test
	public void getColIndexByCoordNameTest() {
		int colIndex = CellReferenceUtil.getColIndexByCoordName("C8");
		assertEquals(2, colIndex);

		colIndex = CellReferenceUtil.getColIndexByCoordName("AA21");
		assertEquals(26, colIndex);

		colIndex = CellReferenceUtil.getColIndexByCoordName("AAA21");
		assertEquals(26 * 26 + 26, colIndex);
	}

	@Test
	public void getRowIndexByCoordNameTest() {
		int rowIndex = CellReferenceUtil.getRowIndexByCoordName("C8");
		assertEquals(7, rowIndex);

		rowIndex = CellReferenceUtil.getRowIndexByCoordName("AA21");
		assertEquals(20, rowIndex);

		rowIndex = CellReferenceUtil.getRowIndexByCoordName("AA222");
		assertEquals(221, rowIndex);
	}

	@Test
	public void getCoordNameTest() {
		String coordName = CellReferenceUtil.getCoordName(2, 7);
		assertEquals("C8", coordName);

		coordName = CellReferenceUtil.getCoordName(26, 21);
		assertEquals("AA22", coordName);

		coordName = CellReferenceUtil.getCoordName(26 * 26 + 26, 21);
		assertEquals("AAA22", coordName);
	}
}
