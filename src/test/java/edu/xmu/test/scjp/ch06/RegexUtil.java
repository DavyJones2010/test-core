package edu.xmu.test.scjp.ch06;

public final class RegexUtil {
	public static boolean isNumber(String str) {
		return str.matches("[0-9]*");
	}

	public static boolean isAlphabeta(String str) {
		return str.matches("[A-Za-z]");
	}

	public static boolean isMobilePhone(String str) {
		return str.matches("(086)?(86)?\\d{11}$");
	}
}
