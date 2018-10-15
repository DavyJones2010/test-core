package edu.xmu.test.javase.cypher;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class ZipUtils {
	private static void directoryZip(ZipOutputStream out, File f, String base) throws Exception {
		if (f.isDirectory()) {
			File[] fl = f.listFiles();
			out.putNextEntry(new ZipEntry(base + "/"));
			if (base.length() == 0) {
				base = "";
			} else {
				base = base + "/";
			}
			for (int i = 0; i < fl.length; i++) {
				directoryZip(out, fl[i], base + fl[i].getName());
			}
		} else {
			out.putNextEntry(new ZipEntry(base));
			FileInputStream in = new FileInputStream(f);
			byte[] bb = new byte[10240];
			int aa = 0;
			while ((aa = in.read(bb)) != -1) {
				out.write(bb, 0, aa);
			}
			FileUtils.safeClose(in);
		}
	}

	private static void fileZip(ZipOutputStream zos, File file) throws Exception {
		if (file.isFile()) {
			zos.putNextEntry(new ZipEntry(file.getName()));
			FileInputStream fis = new FileInputStream(file);
			byte[] bb = new byte[10240];// 1M
			int aa = 0;
			while ((aa = fis.read(bb)) != -1) {
				zos.write(bb, 0, aa);
			}
			FileUtils.safeClose(fis);
		} else {
			directoryZip(zos, file, "");
		}
	}

	/**
	 * ��ѹ���ļ�
	 * 
	 * @param zis
	 * @param file
	 * @throws Exception
	 */
	private static void fileUnZip(ZipInputStream zis, File file) throws Exception {
		ZipEntry zip = zis.getNextEntry();
		if (zip == null)
			return;
		String name = zip.getName();
		File f = new File(file.getAbsolutePath() + "/" + name);
		if (zip.isDirectory()) {
			f.mkdirs();
			fileUnZip(zis, f);
		} else {
			f.createNewFile();
			FileOutputStream fos = new FileOutputStream(f);
			byte b[] = new byte[10240];
			int aa = 0;
			while ((aa = zis.read(b)) != -1) {
				fos.write(b, 0, aa);
			}
			FileUtils.safeClose(fos);
			fileUnZip(zis, file);
		}
	}

	public static void zip(String source, String target) throws Exception {
		File file = FileUtils.mkdirFiles(target);
		ZipOutputStream zout = new ZipOutputStream(new FileOutputStream(file));
		fileZip(zout, new File(source));
		FileUtils.safeClose(zout);
	}

	public static void unZip(String sourceFile, String targetDir) throws Exception {
		File file = new File(sourceFile);
		if (!file.exists()) {
			throw new FileNotFoundException(sourceFile + "not found");
		}
		ZipInputStream zis = new ZipInputStream(new FileInputStream(file));
		File f = new File(targetDir);
		f.mkdirs();
		fileUnZip(zis, f);
		zis.close();
	}
}
