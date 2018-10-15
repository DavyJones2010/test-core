package edu.xmu.test.excel.util;

public final class SheetFormulaUtil {
	/**
	 * Convert Equity by Geography C8 to 'Equity by Geography'!$C$8; Equity by
	 * Geography AB21 to 'Equity by Geography'!$AB$21
	 */
	public static String convertSheetCoordToSheetFormulaPos(String sheetName, String coordName) {
		return "'".concat(sheetName).concat("'!").concat(CellFormulaUtil.convertCellCoordToCellFormulaPos(coordName));
	}

	/**
	 * Convert col:2, row:7 to 'Equity by Geography'!$C$8; 27 20 to 'Equity by
	 * Geography'!$AB$21
	 */
	public static String convertCellPosToCellFormulaPos(String sheetName, int colNum, int rowNum) {
		return "'".concat(sheetName).concat("'!").concat(CellFormulaUtil.convertCellPosToCellFormulaPos(colNum, rowNum));
	}
}
