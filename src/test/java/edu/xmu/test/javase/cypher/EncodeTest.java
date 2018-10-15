package edu.xmu.test.javase.cypher;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.IOUtils;

public class EncodeTest {

	public static void main(String[] args) throws Exception {

		String source = "/home/dir/2015-10-31.txt";
		File sourceFile = new File(source);
		String dir = sourceFile.getParentFile().getAbsolutePath() + "/";
		// 压缩文件名称
		String target = dir + sourceFile.getName() + ".zip";
		File targetFile = new File(target);
		if (targetFile.exists()) {
			targetFile.delete();
		}
		String key = "***";
		String salt = "***";
		// 压缩
		System.out.println(target);
		ZipUtils.zip(source, target);
		// 加密
		// Date date=new Date();

		String fileName = "2015-10-31";
		// 加密文件名
		String md5FileName = dir + DigestUtils.md5Hex(fileName + salt);
		File md5File = new File(md5FileName);
		targetFile.deleteOnExit();
		System.out.println("fileName=" + fileName + ",md5FileName=" + md5FileName);
		CipherUtil.encrypt(target, md5FileName, key);
		// 生成文件签名
		File signFile = new File(md5File + ".sign");
		// signFile.deleteOnExit();
		FileInputStream in = new FileInputStream(md5File);
		String sign = DigestUtils.md5Hex(IOUtils.toByteArray(in));
		FileUtils.safeClose(in);
		FileUtils.mkdirFiles(signFile);
		FileWriter writer = new FileWriter(signFile);
		writer.write(sign);
		writer.flush();
		writer.close();

	}
}
