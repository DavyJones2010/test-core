package edu.xmu.test.excel.util;

import org.apache.commons.lang3.StringUtils;

public class CellReferenceUtil {
	/**
	 * @param regionCoord
	 *            "D8:F12"
	 * @return
	 */
	public static int getStartColIndex(String regionCoord) {
		return getColIndexByCoordName(StringUtils.split(regionCoord, ':')[0]);
	}

	/**
	 * @param regionCoord
	 *            "D8:F12"
	 * @return
	 */
	public static int getEndColIndex(String regionCoord) {
		return getColIndexByCoordName(StringUtils.split(regionCoord, ':')[1]);
	}

	/**
	 * @param regionCoord
	 *            "D8:F12"
	 * @return
	 */
	public static int getStartRowIndex(String regionCoord) {
		return getRowIndexByCoordName(StringUtils.split(regionCoord, ':')[0]);
	}

	/**
	 * @param regionCoord
	 *            "D8:F12"
	 * @return
	 */
	public static int getEndRowIndex(String regionCoord) {
		return getRowIndexByCoordName(StringUtils.split(regionCoord, ':')[1]);
	}

	/**
	 * Input Coordination: C<br/>
	 * Output Column Number: 2<br/>
	 * 
	 * @param coordName
	 * @return colIndex: 0 based index
	 */
	public static int getColIndex(String colName) {
		colName = colName.toUpperCase();

		int value = 0;
		for (int i = 0; i < colName.length(); i++) {
			int delta = colName.charAt(i) - 64;
			value = value * 26 + delta;
		}
		int colIndex = value - 1;
		return colIndex;
	}

	/**
	 * Input column index: 2 <br/>
	 * Output column name: C <br/>
	 * 
	 * @param colIndex
	 * @return
	 */
	public static String getColName(int colIndex) {
		int quotient = (colIndex) / 26;

		if (quotient > 0) {
			return getColName(quotient - 1) + (char) ((colIndex % 26) + 65);
		} else {
			return "" + (char) ((colIndex % 26) + 65);
		}
	}

	/**
	 * Input coord: C8<br/>
	 * Output Row Number: 7<br/>
	 * 
	 * @param coordName
	 * @return rowIndex starts with 0
	 */
	public static int getRowIndex(String rowName) {
		int rowIndex = Integer.parseInt(rowName) - 1;
		return rowIndex;
	}

	/**
	 * Input rowIndex: 7 <br/>
	 * Output rowName: 8 <br/>
	 * 
	 * @param rowIndex
	 * @return
	 */
	public static String getRowName(int rowIndex) {
		int rowName = rowIndex + 1;
		return String.valueOf(rowName);
	}

	/**
	 * Input pos: col = 2, row = 7<br/>
	 * Output coord: C8<br/>
	 * 
	 * @param colIndex
	 * @param rowIndex
	 * @return
	 */
	public static String getCoordName(int colIndex, int rowIndex) {
		String colName = getColName(colIndex);
		String rowName = getRowName(rowIndex);

		return colName + rowName;
	}

	/**
	 * Input coordName: C8 <br/>
	 * Output colIndex: 2 <br/>
	 * 
	 * @param coordName
	 * @return colIndex: Starts with 0
	 */
	public static int getColIndexByCoordName(String coordName) {
		String[] colAndRowName = splitColAndRow(coordName);
		String colName = colAndRowName[0];
		return getColIndex(colName);
	}

	/**
	 * Input coordName: C8 <br/>
	 * Output rowIndex: 7 <br/>
	 * 
	 * @param coordName
	 * @return rowIndex: 0 based index
	 */
	public static int getRowIndexByCoordName(String coordName) {
		String[] colAndRowName = splitColAndRow(coordName);
		String rowName = colAndRowName[1];
		return getRowIndex(rowName);
	}

	/**
	 * Input coordName: C8 <br/>
	 * Output : String[]{C, 8} <br/>
	 * 
	 * @param coordName
	 * @return
	 */
	private static String[] splitColAndRow(String coordName) {
		int rowNumStartIndex = 0;
		for (int i = 0; i < coordName.length(); i++) {
			char ch = coordName.charAt(i);
			if (Character.isDigit(ch)) {
				rowNumStartIndex = i;
				break;
			}
		}

		String colName = coordName.substring(0, rowNumStartIndex);
		String rowName = coordName.substring(rowNumStartIndex);

		return new String[] { colName, rowName };
	}
}
