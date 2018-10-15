package edu.xmu.test.excel.util;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

public class RowUtil {
	private static final int EMPTY_CELL_COUNT_THRESHOLD = 100;

	private RowUtil() {
	}

	/**
	 * <p>
	 * Get the intuitive last cell num in row <br/>
	 * NPE Safe<br/>
	 * </p>
	 * <p>
	 * Use the same check machanism with {@link SheetUtil#getLastRowNumber(org.apache.poi.ss.usermodel.Sheet)}
	 * </p>
	 * 
	 * @param row
	 * @return
	 */
	public static int getLastCellNum(Row row) {
		int lastCellNumber = 0;

		if (isRowEmpty(row)) {
			return lastCellNumber;
		}
		for (int i = 0; i < row.getLastCellNum(); i++) {
			Cell cell = row.getCell(i);
			lastCellNumber = i;

			if (CellUtil.isCellEmpty(cell) && isCellsAllEmpty(row, i, i + EMPTY_CELL_COUNT_THRESHOLD)) {
				lastCellNumber = i - 1;
				break;
			}
		}
		return lastCellNumber;
	}

	/**
	 * <p>
	 * Whether all the cells from startCellNum to endCellNum are empty <br/>
	 * NPE Safe <br/>
	 * </p>
	 * 
	 * @param row
	 * @param startCellNum
	 * @param endCellNum
	 * @return
	 */
	public static boolean isCellsAllEmpty(Row row, int startCellNum, int endCellNum) {
		if (isRowEmpty(row)) {
			return true;
		}
		for (int i = startCellNum; i <= endCellNum; i++) {
			Cell cell = row.getCell(i);
			if (!CellUtil.isCellEmpty(cell)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * <p>
	 * Judge if the row is empty when the last cell number is unknown <br/>
	 * NPE Safe<br/>
	 * </p>
	 * 
	 * @param row
	 * @return
	 */
	public static boolean isRowEmpty(Row row) {
		return (null == row) ? true : isRowEmpty(row, row.getLastCellNum());
	}

	/**
	 * Judge if the row is empty when the last cell number is known
	 * 
	 * @param row
	 * @param lastCellNum
	 * @return
	 */
	public static boolean isRowEmpty(Row row, int lastCellNum) {
		if (null == row) {
			return true;
		}

		if (-1 == row.getFirstCellNum()) {
			return true;
		}
		for (int c = row.getFirstCellNum(); c <= lastCellNum; c++) {
			if (!CellUtil.isCellEmpty(row.getCell(c))) {
				return false;
			}
		}
		return true;
	}

	/**
	 * <p>
	 * If all cells are filled in current Row <br/>
	 * NPE Safe<br/>
	 * </p>
	 * <p>
	 * All the blank cells at the end of row will not be detected <br/>
	 * </p>
	 * 
	 * @return
	 */
	public static boolean isRowFullyFilled(Row row) {
		return RowUtil.isRowEmpty(row) ? false : isRowFullyFilled(row, RowUtil.getLastCellNum(row));
	}

	public static boolean isRowFullyFilled(Row row, int endColNum) {
		return RowUtil.isRowEmpty(row) ? false : isRowFullyFilled(row, 0, endColNum);
	}

	public static boolean isRowFullyFilled(Row row, int startColNum, int endColNum) {
		if (RowUtil.isRowEmpty(row)) {
			return false;
		}

		boolean isAllFilled = true;

		for (int i = startColNum; i <= endColNum; i++) {
			Cell cell = org.apache.poi.ss.util.CellUtil.getCell(row, i);
			if (CellUtil.isCellEmpty(cell)) {
				isAllFilled = false;
			}
		}
		return isAllFilled;
	}
}
