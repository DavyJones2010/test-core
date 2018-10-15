package edu.xmu.test.ehcache;

import java.io.UnsupportedEncodingException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ConvertFileName {

	public static void main(String args[]) {
		Pattern p = Pattern.compile("(%[0-9a-z]+)");
		String str = "%0052%0041%0050%0054%004f%0052-%0043-20150129_153739712%0045%0052%0052%004f%0052_%0053%0054%0041%0043%004b_%0054%0052%0041%0043%0045";
		Matcher m = p.matcher(str);
		StringBuffer sb = new StringBuffer();
		while (m.find()) {
			String s = m.group();
			char c = (char) Integer.valueOf(s.substring(1, 5), 16).intValue();
			m.appendReplacement(sb, c + s.substring(5));
		}
		m.appendTail(sb);
		System.out.println(sb.toString());
	}

	public static void print() throws UnsupportedEncodingException {
//		Character.UnicodeBlock.of('□');
//		System.out.println(Integer.toHexString('□'));
		System.out.println('\u221a');
		System.out.println('\u2611');
		System.out.println('\u2612');
		System.out.println('\u2610');
		System.out.println('\u2613');
		System.out.println('\u2609');
		System.out.println('\u2608');
		System.out.println(Long.MAX_VALUE);

		for (byte b : new String("\u2610").getBytes("unicode")) {
			System.out.println(b);
		}
	}
}
