package edu.xmu.test.javase.cypher;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;

public class DecodeTest2 {
	public static void main(String[] args) throws Exception {
		String key = "***";
		String salt = "***";

		// 解密下载文件
		// 下载校验文件
		String encreptFile = "/Users/davyjones/Downloads/***";
		String signFile = "/Users/davyjones/Downloads/***.sign";

		System.out.println(DigestUtils.md5Hex("2015-10-31" + salt));

		String md5sum = org.apache.commons.io.FileUtils.readFileToString(new File(signFile));
		String encreptFileMD5 = DigestUtils.md5Hex(IOUtils.toByteArray(new FileInputStream(new File(encreptFile))));

		if (!StringUtils.equals(md5sum, encreptFileMD5)) {
			System.out.println("md5 not the same");
			return;
		}

		String zipFile = "/Users/davyjones/Downloads/2015-10-31.zip";

		// 解密
		CipherUtil.decrypt(encreptFile, zipFile, key);

		String targetFile = "/Users/davyjones/Downloads";

		// File f = new File(targetFile);
		// f.createNewFile();

		// 解压
		ZipUtils.unZip(zipFile, targetFile);

		File file = new File(targetFile + "/2015-10-31.txt");

		System.out.println("targeetFile" + file.getAbsolutePath());
		BufferedReader br = new BufferedReader(new FileReader(file));
		String s;
		while ((s = br.readLine()) != null) {
			System.out.println(s);
		}
		br.close();

	}

}
