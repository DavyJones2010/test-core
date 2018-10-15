package edu.xmu.test.string.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.junit.Test;

public class URLEncodeTest {
	@Test
	public void encodeTest() throws UnsupportedEncodingException {
		String str = "Q3&i'ouO-_=+,.!@#$%^&()_+-=;'`.xlsx";
		System.out.println(URLEncoder.encode(str, "UTF-8"));
	}

}
