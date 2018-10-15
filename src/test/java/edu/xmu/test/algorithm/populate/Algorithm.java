package edu.xmu.test.algorithm.populate;

import java.security.MessageDigest;

public class Algorithm {
	public static String md5Encrypt(String string) {
		try {
			MessageDigest alg = MessageDigest.getInstance("MD5");
			alg.update(string.getBytes("ISO8859-1"));
			byte[] userPwd = alg.digest();
			return bytes2Hex(userPwd);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	public static String sha1Encrypt(String string) {
		try {
			MessageDigest alg = MessageDigest.getInstance("SHA-1");
			alg.update(string.getBytes("UTF-8"));
			byte[] userPwd = alg.digest();
			return bytes2Hex(userPwd);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static String bytes2Hex(byte[] bts) {
		StringBuilder buffer = new StringBuilder();
		String tmp = null;
		for (int i = 0; i < bts.length; i++) {
			tmp = (Integer.toHexString(bts[i] & 0xFF));
			if (tmp.length() == 1) {
				buffer.append("0");
			}
			buffer.append(tmp);
		}
		return buffer.toString();
	}
}
