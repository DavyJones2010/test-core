package edu.xmu.test.excel.util;

import java.io.File;
import java.io.IOException;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;

public class WorkbookIteratorTest {
	@Test
	public void iteratorTest() throws InvalidFormatException, IOException {
		XSSFWorkbook wb = (XSSFWorkbook) WorkbookFactory.create(new File("src/test/resources/xlsx/Sample.xlsx"));
		for (Sheet sheet : wb) {
			System.out.println("Sheet: " + sheet.getSheetName());
			for (Row row : sheet) {
				for (Cell cell : row) {
					System.out.print(CellReferenceUtil.getCoordName(cell.getColumnIndex(), cell.getRowIndex()) + ": " + CellUtil.getCellValue(cell) + "\t");
				}
			}
			System.out.println();
		}
	}
}
