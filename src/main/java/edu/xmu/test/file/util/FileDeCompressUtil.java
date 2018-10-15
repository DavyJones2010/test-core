package edu.xmu.test.file.util;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.apache.log4j.Logger;

public class FileDeCompressUtil {
	private static final Logger logger = Logger.getLogger(FileDeCompressUtil.class);

	private static final int BUFFER_SIZE = 2048;

	private FileDeCompressUtil() {
	}

	public static List<String> decompressFileAndDeleteOrig(File zippedFile, String destPath) throws IOException {
		List<String> decompressFileNameList = decompressFile(zippedFile, destPath);

		if (zippedFile.exists()) {
			zippedFile.delete();
		} else {
			logger.error(String.format("zippedFile: [%s] doesn't exists, thus failed delete", zippedFile.getName()));
		}
		return decompressFileNameList;
	}

	public static List<String> decompressFile(File zippedFile, String destPath) throws IOException {
		InputStream is = new FileInputStream(zippedFile);
		List<String> decompressFileNameList = decompressFile(is, destPath);
		is.close();

		return decompressFileNameList;
	}

	public static List<String> decompressFileWithHeader(InputStream is, String destPath, String header) throws IOException {
		List<String> decompressFileNameList = new ArrayList<String>();

		byte[] buffer = new byte[BUFFER_SIZE];
		logger.info(String.format("Start decompressFile, destPath: [%s]", destPath));

		File folder = new File(destPath);
		if (!folder.exists()) {
			logger.warn(String.format("destPath: [%s] doesn't exits, thus create dir for it", destPath));
			folder.mkdirs();
		}
		ZipInputStream zis = new ZipInputStream(is);

		ZipEntry ze = zis.getNextEntry();

		while (ze != null) {
			String fileName = ze.getName();
			String unzippedFileNameWithPath = destPath + File.separator + fileName;
			File newFile = new File(unzippedFileNameWithPath);
			File parentDir = new File(newFile.getParent());

			logger.info(String.format("decompressed file: [%s]", unzippedFileNameWithPath));

			decompressFileNameList.add(unzippedFileNameWithPath);

			if (!parentDir.exists()) {
				logger.warn(String.format("parentDir: [%s] doesn't exits, thus create dir for it", parentDir.getAbsolutePath()));

				parentDir.mkdirs();
			}

			FileOutputStream fos = new FileOutputStream(newFile, true);

			try {
				int headerLen = 0;
				if (header != null) {
					InputStream zisHeader = new ByteArrayInputStream(header.getBytes());
					while ((headerLen = zisHeader.read(buffer)) > 0) {
						fos.write(buffer, 0, headerLen);
					}
				}

				int len;
				while ((len = zis.read(buffer)) > 0) {
					fos.write(buffer, 0, len);
				}

				fos.close();
				ze = zis.getNextEntry();
			} finally {
				if (fos != null)
					fos.close();
			}
		}

		zis.closeEntry();
		zis.close();

		logger.info(String.format("Finished decompressFile. decompressFileNameList: [%s]", decompressFileNameList));

		return decompressFileNameList;
	}

	public static List<String> decompressFile(InputStream is, String destPath) throws IOException {
		return decompressFileWithHeader(is, destPath, null);
	}
}
