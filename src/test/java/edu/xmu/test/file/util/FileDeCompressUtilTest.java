package edu.xmu.test.file.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import org.junit.Test;

public class FileDeCompressUtilTest {
	@Test
	public void decompressFileTest() throws FileNotFoundException, IOException {
		File file = new File("src/test/resources/compress/compress.zip");

		String destPath = "src/test/resources/compress";

		List<String> decompressFileNameList = FileDeCompressUtil.decompressFile(file, destPath);

		for (String decompressFileName : decompressFileNameList) {
			new File(decompressFileName).delete();
		}
	}
}
