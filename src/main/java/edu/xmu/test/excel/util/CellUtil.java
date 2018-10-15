package edu.xmu.test.excel.util;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.FastDateFormat;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;

public class CellUtil {
	private CellUtil() {
	}

	/**
	 * <p>
	 * If cell is null or intuitively empty <br/>
	 * NPE Safe<br/>
	 * </p>
	 * 
	 * @param cell
	 * @return
	 */
	public static boolean isCellEmpty(Cell cell) {
		if (null != cell && Cell.CELL_TYPE_BLANK != cell.getCellType() && !StringUtils.isEmpty(CellUtil.getCellValue(cell))) {
			return false;
		} else {
			return true;
		}
	}

	public static String getCellValue(Cell cell) {
		switch (cell.getCellType()) {
		case Cell.CELL_TYPE_BLANK:
			return "0.0";
		case Cell.CELL_TYPE_STRING:
			return cell.getRichStringCellValue().getString();
		case Cell.CELL_TYPE_NUMERIC:
			if (DateUtil.isCellDateFormatted(cell)) {
				return FastDateFormat.getInstance("yyyy-MM-dd").format(cell.getDateCellValue());
			} else {
				return Double.valueOf(cell.getNumericCellValue()).toString();
			}
		case Cell.CELL_TYPE_BOOLEAN:
			return Boolean.valueOf(cell.getBooleanCellValue()).toString();
		case Cell.CELL_TYPE_FORMULA:
			try {
				return Double.valueOf(cell.getNumericCellValue()).toString();
			} catch (Exception e) {
				return cell.getRichStringCellValue().getString();
			}
		default:
			return "";
		}
	}
}
