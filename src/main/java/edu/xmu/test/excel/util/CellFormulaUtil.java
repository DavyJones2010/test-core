package edu.xmu.test.excel.util;

public class CellFormulaUtil {
	/**
	 * Convert C8 to $C$8; AB12 to $AB$12
	 */
	public static String convertCellCoordToCellFormulaPos(String cellPos) {
		return convertCellPosToCellFormulaPos(CellReferenceUtil.getColIndexByCoordName(cellPos), CellReferenceUtil.getRowIndexByCoordName(cellPos));
	}

	/**
	 * Convert C8 to $C$8; AB12 to $AB$12
	 */
	public static String convertCellPosToCellFormulaPos(int colNum, int rowNum) {
		String colName = CellReferenceUtil.getColName(colNum);
		String rowName = CellReferenceUtil.getRowName(rowNum);
		return "$".concat(colName).concat("$").concat(rowName);
	}
}
