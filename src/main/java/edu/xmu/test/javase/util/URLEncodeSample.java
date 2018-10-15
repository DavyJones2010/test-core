package edu.xmu.test.javase.util;

import java.io.IOException;
import java.net.URLEncoder;

import com.google.common.net.UrlEscapers;

public class URLEncodeSample {
	public static void main(String[] args) throws IOException {
		// URLEncoder is used to encode form data; space would be encoded to "+"
		System.out.println(URLEncoder.encode("Hello World", "UTF-8"));
		// In URL, the space should be encoded to "%20" instead
		System.out.println(UrlEscapers.urlFragmentEscaper().escape("Hello World"));
	}
}
