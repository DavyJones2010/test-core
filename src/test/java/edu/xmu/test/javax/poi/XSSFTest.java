package edu.xmu.test.javax.poi;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.apache.commons.io.FileUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.After;
import org.junit.Test;

/**
 * EventModel: based on SAX, read only <br>
 * UserModel: based on DOM, read and write <br>
 * StreamModel: based on DOM and flush window size, write only <br>
 */
public class XSSFTest {
	File f = new File("src/test/resources/xlsx/dummy.xlsx");

	/**
	 * DOM writing file
	 */
	@Test
	public void userModelWriteFileTest() throws Exception {
		OutputStream os = new FileOutputStream(f);
		Workbook wb = new XSSFWorkbook();
		wb.createSheet("Sheet_A");
		wb.write(os);
		os.close();
	}

	/**
	 * DOM reading file
	 */
	@Test
	public void userModelReadFileTest() throws Exception {
		File f2 = new File("src/test/resources/xlsx/Sample.xlsx");
		Workbook wb = WorkbookFactory.create(f2);
		Cell c = wb.getSheetAt(0).getRow(0).getCell(0);
		System.out.println(c.getStringCellValue());
	}

	/**
	 * Streaming Model, built on DOM, write only
	 */
	@Test
	public void streamModelWriteFileTest() throws Exception {
		int windowSize = 10;
		OutputStream os = new FileOutputStream(f);
		Workbook wb = new SXSSFWorkbook(windowSize);
		Sheet sheet = wb.createSheet("Sheet_A");
		for (int i = 0; i < 100; i++) {
			sheet.createRow(i);
			if (i > windowSize) {
				Row prevRow = sheet.getRow(i - windowSize);
				// we can no longer access the row that has been flushed before
				assertNull(prevRow);
				prevRow = sheet.getRow(i - windowSize + 1);
				assertNotNull(prevRow);
			}
		}
		wb.write(os);
		os.close();
	}

	/**
	 * Event Model, built on SAX, read only <br>
	 * Refer to: {@link edu.xmu.test.excel.util.CustomizedXSSFReader}
	 */
	@Test
	public void eventModelReadFileTest() throws Exception {
	}

	@After
	public void tearDown() throws IOException {
		FileUtils.forceDelete(f);
	}
}
