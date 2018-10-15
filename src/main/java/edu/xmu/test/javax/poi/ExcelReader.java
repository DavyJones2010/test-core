package edu.xmu.test.javax.poi;

import java.io.File;
import java.io.IOException;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import com.google.common.base.Joiner;
import com.google.common.collect.Sets;

public class ExcelReader {
	static Set<String> readFile(Workbook wb) {
		Set<String> list = Sets.newHashSet();
		Sheet sheet = wb.getSheet("Sheet10");
		for (int i = 1; i <= 3516; i++) {
			Row row = sheet.getRow(i);
			if (row != null) {
				Cell cell = row.getCell(1);
				String province = cell.getStringCellValue();
				cell = row.getCell(3);
				String collegeName = cell.getStringCellValue();
				list.add(Joiner.on(',').join(collegeName.trim(), province.trim()));
			}
		}

		return list;
	}

	public static void main(String[] args) throws InvalidFormatException, IOException {
		Workbook wb = WorkbookFactory.create(new File("/Users/davyjones/Downloads/collegeName.xls"));
		Set<String> readFile = ExcelReader.readFile(wb);
		System.out.println(readFile);
		FileUtils.writeLines(new File("/Users/davyjones/Downloads/collegeName.csv"), "GBK", readFile);
	}
}
