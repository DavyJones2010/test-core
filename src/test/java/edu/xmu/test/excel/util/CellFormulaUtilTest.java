package edu.xmu.test.excel.util;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class CellFormulaUtilTest {
	@Test
	public void convertCellCoordToCellFormulaPos() {
		assertEquals("$C$8", CellFormulaUtil.convertCellCoordToCellFormulaPos("C8"));
		assertEquals("$AB$21", CellFormulaUtil.convertCellCoordToCellFormulaPos("AB21"));
	}

	@Test
	public void convertCellPosToCellFormulaPos() {
		assertEquals("$C$8", CellFormulaUtil.convertCellPosToCellFormulaPos(2, 7));
		assertEquals("$AB$21", CellFormulaUtil.convertCellPosToCellFormulaPos(27, 20));
	}
}
