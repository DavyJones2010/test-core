package edu.xmu.test.javaweb.httpclient;

import java.net.HttpURLConnection;
import java.net.URL;

import org.junit.Test;

public class CarriageReturnTest {

	@Test
	public void test2() {
	}

	@Test
	public void test() throws Exception {
		HttpURLConnection urlConnection = null;
		URL url = new URL("http://localhost:12345");
		urlConnection = (HttpURLConnection) url.openConnection();
		urlConnection.setDoOutput(true);
		urlConnection.setUseCaches(false);
		urlConnection.setRequestMethod("POST");
		urlConnection.setRequestProperty("action", "upload");
		urlConnection.setRequestProperty("metadata", "aaa=\n");
		urlConnection.setRequestProperty("filename", "\n");
		urlConnection.setRequestProperty("type", "");
		urlConnection.setRequestProperty("correlationId", "1234567");
		urlConnection.getOutputStream().write(1);
	}
}
