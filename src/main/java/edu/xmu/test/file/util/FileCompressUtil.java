package edu.xmu.test.file.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Collection;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.log4j.Logger;

public class FileCompressUtil {
	private static final Logger logger = Logger.getLogger(FileCompressUtil.class);

	private static final int BUFFER_SIZE = 2048;

	public static void compressFile(final Collection<String> fileNameList, final String zippedFileNameWithPath) throws IOException {
		logger.debug(String.format("Start compressFile with fileList: [%s], target file: [%s]", fileNameList, zippedFileNameWithPath));

		if (fileNameList.isEmpty()) {
			logger.warn(String.format("Empty fileNameList: [%s], target file: [%s], thus skip compress File", fileNameList, zippedFileNameWithPath));

			File file = new File(zippedFileNameWithPath);
			file.createNewFile();
			return;
		}

		ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(zippedFileNameWithPath));

		byte[] buffer = new byte[BUFFER_SIZE];
		for (String filePath : fileNameList) {
			File file = new File(filePath);
			ZipEntry e = new ZipEntry(file.getName());
			zos.putNextEntry(e);
			logger.debug(String.format("Start compressFile with file: [%s]", filePath));
			FileInputStream in = new FileInputStream(file);
			try {
				int len;
				while ((len = in.read(buffer)) > 0) {
					zos.write(buffer, 0, len);
				}
			} finally {
				in.close();
			}
			zos.closeEntry();
			logger.debug(String.format("Finished compressFile with file: [%s]", filePath));
		}

		zos.close();
		logger.debug(String.format("Finished compressFile with fileList: [%s], target file: [%s]", fileNameList, zippedFileNameWithPath));
	}

	public static void compressAndDeleteOrigFile(final Collection<String> fileNameList, final String zippedFileNameWithPath) throws IOException {
		compressFile(fileNameList, zippedFileNameWithPath);
		for (String filePath : fileNameList) {
			File file = new File(filePath);
			file.delete();
		}
	}
}
