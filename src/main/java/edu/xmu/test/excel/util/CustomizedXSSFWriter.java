package edu.xmu.test.excel.util;

import java.io.FileWriter;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;

import org.apache.log4j.Logger;

import au.com.bytecode.opencsv.CSVWriter;

public class CustomizedXSSFWriter implements Callable<Object> {
	private static final Logger logger = Logger.getLogger(CustomizedXSSFWriter.class);

	private String csvFileName;
	private BlockingQueue<List<String>> rowDataQueue;

	private int currentLineCount = 0;

	public CustomizedXSSFWriter(String csvFileName, BlockingQueue<List<String>> rowDataQueue) {
		super();
		this.csvFileName = csvFileName;
		this.rowDataQueue = rowDataQueue;
	}

	@Override
	public Object call() throws Exception {
		CSVWriter writer = new CSVWriter(new FileWriter(csvFileName), CSVWriter.DEFAULT_SEPARATOR, CSVWriter.NO_QUOTE_CHARACTER);

		while (true) {
			List<String> rowData = rowDataQueue.take();
			if (rowData.isEmpty()) {
				break;
			}
			writer.writeNext(rowData.toArray(new String[rowData.size()]));
			currentLineCount++;
		}

		writer.close();

		logger.info(String.format("Finished write fileName: [%s]. totalLineCount: [%d]", csvFileName, currentLineCount));
		return null;
	}

}
