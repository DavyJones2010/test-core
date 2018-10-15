package edu.xmu.test.javase.cypher;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class FileUtils {

	public static void inputstreamToFile(InputStream input, File file) throws IOException {
		mkdirFiles(file);
		FileOutputStream fos = new FileOutputStream(file);
		byte b[] = new byte[10240];
		int aa = 0;
		while ((aa = input.read(b)) != -1) {
			fos.write(b, 0, aa);
		}
		try {
			fos.close();
		} catch (Exception e) {

		}
	}

	public static File mkdirFiles(String filePath) throws IOException {
		File file = new File(filePath);
		mkdirFiles(file);
		return file;
	}

	public static void mkdirFiles(File file) throws IOException {
		if (file.exists()) {
			return;
		}
		if (!file.getParentFile().exists()) {
			file.getParentFile().mkdirs();
		}
		file.createNewFile();
	}

	public static void safeClose(InputStream inputStream) {
		if (inputStream != null) {
			try {
				inputStream.close();
			} catch (IOException e) {
			}
		}
	}

	public static void safeClose(OutputStream outputStream) {
		if (outputStream != null) {
			try {
				outputStream.close();
			} catch (IOException e) {
			}
		}
	}
}
