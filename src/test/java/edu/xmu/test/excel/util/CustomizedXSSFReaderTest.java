package edu.xmu.test.excel.util;

import java.io.File;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.junit.Test;

public class CustomizedXSSFReaderTest {
	@Test
	public void processSheetTest() throws Exception {
		String excelFileName = "src/test/resources/excel/source.xlsx";
		String csvFileName = "src/test/resources/csv/target.csv";
		BlockingQueue<List<String>> rowDataQueue = new ArrayBlockingQueue<List<String>>(40000);
		ExecutorService executorService = Executors.newFixedThreadPool(2);

		executorService.submit(new CustomizedXSSFReader(new File(excelFileName), rowDataQueue));
		executorService.submit(new CustomizedXSSFWriter(csvFileName, rowDataQueue));

		executorService.shutdown();
		while (!executorService.isTerminated()) {
			executorService.awaitTermination(2, TimeUnit.SECONDS);
		}
	}
}
