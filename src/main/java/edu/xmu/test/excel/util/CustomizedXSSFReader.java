package edu.xmu.test.excel.util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xssf.eventusermodel.XSSFReader;
import org.apache.poi.xssf.model.SharedStringsTable;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.helpers.XMLReaderFactory;

public class CustomizedXSSFReader implements Callable<Object> {
	private static final Logger logger = Logger.getLogger(CustomizedXSSFReader.class);

	private File excelFile;
	private List<SheetDefination> targetSheetDefList;
	private BlockingQueue<List<String>> rowDataQueue;

	public CustomizedXSSFReader(File excelFile, BlockingQueue<List<String>> rowDataQueue) {
		super();
		this.excelFile = excelFile;
		this.rowDataQueue = rowDataQueue;
	}

	@Override
	public Object call() throws Exception {
		OPCPackage opcPackage = OPCPackage.open(excelFile);
		XSSFReader xssfReader = new XSSFReader(opcPackage);

		readWorkbookDef(xssfReader);
		readSheet(xssfReader);

		return null;
	}

	private void readWorkbookDef(XSSFReader xssfReader) throws IOException, InvalidFormatException, SAXException {
		InputStream workbookDefInputStream = xssfReader.getWorkbookData();
		XMLReader workbookDefXMLReader = XMLReaderFactory.createXMLReader();
		workbookDefXMLReader.setContentHandler(new CustomizedWorkbookDefHandler());
		workbookDefXMLReader.parse(new InputSource(workbookDefInputStream));
	}

	public void readSheet(XSSFReader xssfReader) throws Exception {
		logger.info(String.format("Start readSheet, excelFileName: [%s], targetSheetRID: [%s]", excelFile.getName(), targetSheetDefList.get(0).getSheetRID()));

		SharedStringsTable sharedStringsTable = xssfReader.getSharedStringsTable();

		InputStream inputStream = xssfReader.getSheet(targetSheetDefList.get(0).getSheetRID());
		XMLReader xmlReader = XMLReaderFactory.createXMLReader();
		xmlReader.setContentHandler(new CustomizedContentHandler(sharedStringsTable));

		InputSource inputSource = new InputSource(inputStream);
		xmlReader.parse(inputSource);
		inputStream.close();

		logger.info(String.format("Finished readSheet, excelFileName: [%s]", excelFile.getName()));
	}

	private static class SheetDefination {
		private String sheetName;
		private String sheetId;
		private String sheetRID;

		public SheetDefination(String sheetName, String sheetId, String sheetRID) {
			super();
			this.sheetName = sheetName;
			this.sheetId = sheetId;
			this.sheetRID = sheetRID;
		}

		@SuppressWarnings("unused")
		public String getSheetName() {
			return sheetName;
		}

		@SuppressWarnings("unused")
		public String getSheetId() {
			return sheetId;
		}

		public String getSheetRID() {
			return sheetRID;
		}

		@Override
		public String toString() {
			return "SheetDefination [sheetName=" + sheetName + ", sheetId=" + sheetId + ", sheetRID=" + sheetRID + "]";
		}

	}

	private class CustomizedWorkbookDefHandler extends DefaultHandler {
		private boolean isWorkbookStarted;
		private boolean isSheetsStarted;
		private boolean isSheetStarted;

		@Override
		public void startElement(String uri, String localName, String name, Attributes attributes) throws SAXException {
			if ("workbook".equals(name)) {
				isWorkbookStarted = true;
			} else if ("sheets".equals(name)) {
				isSheetsStarted = true;
				targetSheetDefList = new ArrayList<SheetDefination>();
			} else if ("sheet".equals(name)) {
				isSheetStarted = true;
			}

			if (isWorkbookStarted && isSheetsStarted && isSheetStarted) {
				String sheetName = attributes.getValue("name");
				String sheetId = attributes.getValue("sheetId");
				String sheetRID = attributes.getValue("r:id");
				SheetDefination sheetDefination = new SheetDefination(sheetName, sheetId, sheetRID);
				logger.info(String.format("Added sheetDefination: [%s] into targetSheetDefList.", sheetDefination));
				targetSheetDefList.add(sheetDefination);
			}
		}

		@Override
		public void endElement(String uri, String localName, String name) throws SAXException {
			if ("workbook".equals(name)) {
				logger.info(String.format("Finished resolve workbookDef, targetSheetDefList: [%s]", targetSheetDefList));
				isWorkbookStarted = false;
			} else if ("sheets".equals(name)) {
				logger.info(String.format("Finished resolve sheetsDef, targetSheetDefList: [%s]", targetSheetDefList));
				isSheetsStarted = false;
			} else if ("sheet".equals(name)) {
				isSheetStarted = false;
			}
		}

	}

	private class CustomizedContentHandler extends DefaultHandler {
		private boolean isSheetDataStarted;
		private boolean isRowStarted;
		private boolean isColumnStarted;
		private boolean isValueStarted;
		private boolean valueShouldGetFromSharedStringTable;
		private boolean isInlineString;

		private SharedStringsTable sharedStringsTable;
		private StringBuilder cellValue;

		private int totalRowNum;
		private int totalColumnNum;

		private int currColumnNumCursor = 0;
		private int prevColumnNumCursor = -1;

		private int currRowNumCursor = 0;
		private int prevRowNumCursor = -1;

		private List<String> rowData;

		public CustomizedContentHandler(SharedStringsTable sharedStringsTable) {
			super();
			this.sharedStringsTable = sharedStringsTable;
		}

		private void initSheetInfo(String cellReferenceRegion) {
			logger.info(String.format("Start initSheetInfo, cellReferenceRegion: [%s]", cellReferenceRegion));

			String[] cellReferences = StringUtils.split(cellReferenceRegion, ':');
			if (2 != cellReferences.length) {
				totalColumnNum = 10;
				totalRowNum = 10;

				logger.warn(String.format("cellReferenceRegion: [%s] is not reliable, thus we use default totalColumnNum: [%d], totalRowNum: [%d]", cellReferenceRegion,
						totalColumnNum, totalRowNum));
			} else {
				String startCellReference = cellReferences[0];
				String endCellReference = cellReferences[1];

				int startX = CellReferenceUtil.getColIndexByCoordName(startCellReference);
				int startY = CellReferenceUtil.getRowIndexByCoordName(startCellReference);

				int endX = CellReferenceUtil.getColIndexByCoordName(endCellReference);
				int endY = CellReferenceUtil.getRowIndexByCoordName(endCellReference);
				totalRowNum = endY - startY + 1;
				totalColumnNum = endX - startX + 1;

				logger.info(String.format("Finished initSheetInfo, totalRowNum: [%d], totalColumnNum: [%d]", totalRowNum, totalColumnNum));
			}
		}

		@Override
		public void startElement(String uri, String localName, String name, Attributes attributes) throws SAXException {
			if ("dimension".equals(name)) {
				String cellReferenceRegion = attributes.getValue("ref");
				initSheetInfo(cellReferenceRegion);
			} else if ("sheetData".equals(name)) {
				isSheetDataStarted = true;
			} else if ("row".equals(name)) {
				isRowStarted = true;
				currRowNumCursor = Integer.parseInt(attributes.getValue("r")) - 1;
				rowData = new ArrayList<String>(totalColumnNum);
			} else if ("c".equals(name)) {
				isColumnStarted = true;

				String cellIndex = attributes.getValue("r");
				currColumnNumCursor = CellReferenceUtil.getColIndexByCoordName(cellIndex);

				String cellType = attributes.getValue("t");
				if ("s".equals(cellType)) {
					isInlineString = false;
					valueShouldGetFromSharedStringTable = true;
				} else if ("inlineStr".equals(cellType)) {
					isInlineString = true;
					valueShouldGetFromSharedStringTable = false;
				} else {
					isInlineString = false;
					valueShouldGetFromSharedStringTable = false;
				}
			} else if ("v".equals(name) || "t".equals(name)) {
				isValueStarted = true;
				cellValue = new StringBuilder();
			}
		}

		private void fillEmptyCellValuesIfNecessary() {
			if ((currColumnNumCursor - prevColumnNumCursor) > 1) {
				for (int i = 1; i < currColumnNumCursor - prevColumnNumCursor; i++) {
					rowData.add("");
				}
			}
		}

		private void fillEmptyRowValuesIfNecessary() {
			if ((currRowNumCursor - prevRowNumCursor) > 1) {
				for (int i = 1; i < currRowNumCursor - prevRowNumCursor; i++) {
					try {
						rowDataQueue.put(new ArrayList<String>(totalColumnNum));
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}

		@Override
		public void endElement(String uri, String localName, String name) throws SAXException {
			if ("sheetData".equals(name)) {
				isSheetDataStarted = false;
				try {
					rowDataQueue.put(new ArrayList<String>());
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			} else if ("row".equals(name)) {
				isRowStarted = false;

				fillEmptyRowValuesIfNecessary();
				try {
					rowDataQueue.put(rowData);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				prevRowNumCursor = currRowNumCursor;
			} else if ("c".equals(name)) {
				isColumnStarted = false;
			} else if ("v".equals(name) || "t".equals(name)) {
				if (isSheetDataStarted && isRowStarted && isColumnStarted && isValueStarted) {
					fillEmptyCellValuesIfNecessary();

					if (valueShouldGetFromSharedStringTable) {
						valueShouldGetFromSharedStringTable = false;

						int index = Integer.parseInt(cellValue.toString());
						String cellValueStr = new XSSFRichTextString(sharedStringsTable.getEntryAt(index)).toString();
						rowData.add(cellValueStr);
					} else if (isInlineString) {
						isInlineString = false;
						rowData.add(cellValue.toString());
					} else {
						rowData.add(cellValue.toString());
					}
				} else {
					logger.error(String.format("Error Excel->XML, isSheetDataStarted, all of isRowStarted, isColumnStarted, isValueStarted should  be true "));
				}
				prevColumnNumCursor = currColumnNumCursor;
				isValueStarted = false;
				cellValue = new StringBuilder();
			}
		}

		@Override
		public void characters(char[] ch, int start, int length) throws SAXException {
			if (isSheetDataStarted && isRowStarted && isColumnStarted && isValueStarted) {
				cellValue.append(new String(ch, start, length));
			}
		}
	}

}
