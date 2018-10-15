package edu.xmu.test.javase.cypher;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;

/**
 * 类T.java的实现描述：TODO 类实现描述
 * 
 * @author 粥五 2015年10月31日 下午6:43:14
 */
public class DecodeTest {
	public static void main(String[] args) throws Exception {
		String key = "***";
		String salt = "***";

		// 解密下载文件
		// 下载 校验文件
		String encreptFile = "/home/dir/file";
		String signFile = "/home/dir/***.sign";

		System.out.println(DigestUtils.md5Hex("2015-10-31" + salt));

		String md5sum = org.apache.commons.io.FileUtils.readFileToString(new File(signFile));
		String encreptFileMD5 = DigestUtils.md5Hex(IOUtils.toByteArray(new FileInputStream(new File(encreptFile))));

		if (!StringUtils.equals(md5sum, encreptFileMD5)) {
			System.out.println("md5 not the same");
			return;
		}

		String zipFile = "/home/dir/2015-10-31.zip";

		// 解密
		CipherUtil.decrypt(encreptFile, zipFile, key);

		String targetFile = "/home/dir/";

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
