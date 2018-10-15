package edu.xmu.test.javase.cypher;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.GeneralSecurityException;
import java.security.Key;
import java.security.SecureRandom;
import java.util.Random;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;

public class CipherUtil {

	// private static final String type = "DES";
	private static final String type = "AES";
	private static Cipher encryptCipher;
	private static Cipher decryptCipher;

	public static String encrypt(String source) throws Exception {
		if (StringUtils.isBlank(source)) {
			return null;
		}
		byte[] bytes = encryptCipher.doFinal(source.getBytes("utf-8"));
		return new String(Base64.encodeBase64(bytes));
	}

	public static String decrypt(String source) {
		if (StringUtils.isBlank(source)) {
			return null;
		}
		try {
			byte[] bytes = Base64.decodeBase64(source.getBytes());
			return new String(decryptCipher.doFinal(bytes), "utf-8");
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 把文件srcFile加密后存储为destFile
	 * 
	 * @param srcFile
	 *            加密前的文件
	 * @param destFile
	 *            加密后的文件
	 * @param privateKey
	 *            密钥
	 * @throws GeneralSecurityException
	 * @throws IOException
	 */
	public static void encrypt(String srcFile, String destFile, String privateKey)
			throws GeneralSecurityException, IOException {
		Key key = getKey(privateKey);
		// SecretKeyFactory factory=SecretKeyFactory.getInstance(type);
		// byte[] salt = {1,2,3,4,5,6,7,8};
		// KeySpec keySpec=new
		// PBEKeySpec(privateKey.toCharArray(),salt,1024,128);
		// SecretKey tmp=factory.generateSecret(keySpec);
		// SecretKey key=new SecretKeySpec(tmp.getEncoded(),"AES");
		Cipher cipher = Cipher.getInstance(type + "/ECB/PKCS5Padding");
		cipher.init(Cipher.ENCRYPT_MODE, key);

		FileInputStream fis = null;
		FileOutputStream fos = null;
		fis = new FileInputStream(srcFile);
		fos = new FileOutputStream(FileUtils.mkdirFiles(destFile));
		crypt(fis, fos, cipher);

	}

	/**
	 * 把文件srcFile解密后存储为destFile
	 * 
	 * @param srcFile
	 *            解密前的文件
	 * @param destFile
	 *            解密后的文件
	 * @param privateKey
	 *            密钥
	 * @throws GeneralSecurityException
	 * @throws IOException
	 */
	public static void decrypt(String srcFile, String destFile, String privateKey)
			throws GeneralSecurityException, IOException {
		Key key = getKey(privateKey);
		Cipher cipher = Cipher.getInstance(type + "/ECB/PKCS5Padding");
		// Cipher cipher = Cipher.getInstance(type + "/ECB/NoPadding");
		cipher.init(Cipher.DECRYPT_MODE, key);

		FileInputStream fis = null;
		FileOutputStream fos = null;
		fis = new FileInputStream(srcFile);
		fos = new FileOutputStream(FileUtils.mkdirFiles(destFile));
		crypt(fis, fos, cipher);

	}

	/**
	 * 生成指定字符串的密钥
	 * 
	 * @param secret
	 *            要生成密钥的字符串
	 * @return secretKey 生成后的密钥
	 * @throws GeneralSecurityException
	 */
	private static Key getKey(String secret) throws GeneralSecurityException {
		SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
		secureRandom.setSeed(secret.getBytes());
		KeyGenerator kgen = KeyGenerator.getInstance(type);
		kgen.init(128, secureRandom);
		SecretKey secretKey = kgen.generateKey();
		return secretKey;
	}

	/**
	 * 加密解密流
	 * 
	 * @param in
	 *            加密解密前的流
	 * @param out
	 *            加密解密后的流
	 * @param cipher
	 *            加密解密
	 * @throws IOException
	 * @throws GeneralSecurityException
	 */
	private static void crypt(InputStream in, OutputStream out, Cipher cipher)
			throws IOException, GeneralSecurityException {
		int blockSize = cipher.getBlockSize() * 1000;
		int outputSize = cipher.getOutputSize(blockSize);

		byte[] inBytes = new byte[blockSize];
		byte[] outBytes = new byte[outputSize];

		int inLength = 0;
		boolean more = true;
		while (more) {
			inLength = in.read(inBytes);
			if (inLength == blockSize) {
				int outLength = cipher.update(inBytes, 0, blockSize, outBytes);
				out.write(outBytes, 0, outLength);
			} else {
				more = false;
			}
		}
		if (inLength > 0)
			outBytes = cipher.doFinal(inBytes, 0, inLength);
		else
			outBytes = cipher.doFinal();
		out.write(outBytes);
	}

	public static void main(String[] args) throws Exception {
		String str = "test1234";
		System.out.println("before-encrypt str=" + str);
		str = encrypt(str);
		System.out.println(" after-encrypt str=" + str);
		str = decrypt(str);
		System.out.println("before-decrypt str=" + str);
		testSecretKey();

	}

	private static void testSecretKey() {
		for (int i = 0; i < 5; i++) {
			System.out.println(randomStr(32));
		}
	}

	private static String str1 = "****";

	private static String randomStr(int size) {
		StringBuilder result = new StringBuilder();
		Random ran = new Random();
		for (int i = 0; i < size; i++) {
			result.append(str1.charAt(ran.nextInt(str1.length())));
		}
		return result.toString();
	}

}
