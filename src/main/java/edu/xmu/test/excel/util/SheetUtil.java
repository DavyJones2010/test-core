package edu.xmu.test.excel.util;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellUtil;

import com.google.common.base.Function;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;

public class SheetUtil {
	private final static Logger logger = Logger.getLogger(SheetUtil.class);
	private static final int EMPTY_ROW_COUNT_THRESHOLD = 100;

	public <V> Table<Integer, Integer, V> traverseSheet(Sheet sheet, Function<Cell, V> func) {
		Table<Integer, Integer, V> table = HashBasedTable.create();
		int lastRowNum = SheetUtil.getLastRowNumber(sheet);
		for (int i = 0; i <= lastRowNum; i++) {
			Row row = CellUtil.getRow(i, sheet);
			int lastColNum = RowUtil.getLastCellNum(row);
			for (int j = 0; j <= lastColNum; j++) {
				Cell cell = CellUtil.getCell(row, j);
				table.put(i, j, func.apply(cell));
			}
		}
		return table;
	}

	/**
	 * <p>
	 * Get the intuitive last row number for the sheet
	 * <p>
	 * 
	 * @param sheet
	 * @return
	 */
	public static int getLastRowNumber(Sheet sheet) {
		return getLastRowNumber(sheet, EMPTY_ROW_COUNT_THRESHOLD);
	}

	public static int getLastRowNumber(Sheet sheet, int emptyRowCountThreshold) {
		logger.info(String.format("Start getLastRowNumber, sheet: [%s]", sheet.getSheetName()));

		int lastRowNumber = 0;

		for (int i = 0; i <= sheet.getLastRowNum(); i++) {
			Row row = sheet.getRow(i);
			lastRowNumber = i;

			if (RowUtil.isRowEmpty(row) && isRowsAllEmpty(sheet, i, i + emptyRowCountThreshold)) {
				lastRowNumber = i - 1;
				break;
			}
		}
		logger.info(String.format("Finished getLastRowNumber, lastRowNumber: [%d]", lastRowNumber));
		return lastRowNumber;
	}

	/**
	 * <p>
	 * Whether all the rows from startRowNumber to endRowNumber are empty
	 * </p>
	 * 
	 * @param sheet
	 * @param firstEmptyRowNumber
	 * @param emptyRowCountThreshold
	 */
	public static boolean isRowsAllEmpty(Sheet sheet, int startRowNumber, int endRowNumber) {
		for (int i = startRowNumber; i <= endRowNumber; i++) {
			if (!RowUtil.isRowEmpty(sheet.getRow(i))) {
				return false;
			}
		}
		return true;
	}

	public static Cell getCell(Row row, int i) {
		Cell cell = row.getCell(i);
		if (null == cell) {
			cell = row.createCell(i);
		}
		return cell;
	}
}
