package edu.xmu.test.excel.util;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class SheetFormulaUtilTest {
	@Test
	public void convertSheetCoordToSheetFormulaPosTest() {
		assertEquals("'Equity by Geography'!$C$8", SheetFormulaUtil.convertSheetCoordToSheetFormulaPos("Equity by Geography", "C8"));
		assertEquals("'Equity by Geography'!$AB$21", SheetFormulaUtil.convertSheetCoordToSheetFormulaPos("Equity by Geography", "AB21"));
	}

	@Test
	public void convertCellPosToCellFormulaPosTest() {
		assertEquals("'Equity by Geography'!$C$8", SheetFormulaUtil.convertCellPosToCellFormulaPos("Equity by Geography", 2, 7));
		assertEquals("'Equity by Geography'!$AB$21", SheetFormulaUtil.convertCellPosToCellFormulaPos("Equity by Geography", 27, 20));
	}
}
