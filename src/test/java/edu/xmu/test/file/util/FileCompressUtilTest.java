package edu.xmu.test.file.util;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import org.apache.commons.io.FileUtils;
import org.junit.Before;
import org.junit.Test;

public class FileCompressUtilTest {
	private Collection<String> fileNameList;
	private String zipFileName;

	@Before
	public void setUp() {
		fileNameList = new ArrayList<String>();
		String filePath1 = "src/test/resources/compress/compress.txt";
		String filePath2 = "src/test/resources/compress/compress_2.txt";
		fileNameList.add(filePath1);
		fileNameList.add(filePath2);

		zipFileName = "src/test/resources/compress/test_compress.zip";
	}

	@Test
	public void testCompressFile() throws IOException {
		FileCompressUtil.compressFile(fileNameList, zipFileName);
	}

	@Test
	public void compressAndDeleteOrigFileTest() throws IOException {
		Collection<String> fileList = new ArrayList<String>();
		for (String filePath : fileNameList) {
			File tmpFile = File.createTempFile("tmp", ".txt");
			FileUtils.copyFile(new File(filePath), tmpFile);

			fileList.add(tmpFile.getAbsolutePath());
		}

		FileCompressUtil.compressAndDeleteOrigFile(fileList, zipFileName);
	}
}
