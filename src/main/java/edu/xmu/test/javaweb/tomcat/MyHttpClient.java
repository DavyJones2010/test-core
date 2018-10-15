package edu.xmu.test.javaweb.tomcat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.util.EntityUtils;

import edu.xmu.test.javaweb.httpclient.HttpClientUtil;

public class MyHttpClient {

	public static void main(String[] args) throws IOException {
		// sendRequestUsingHttpClient();
		sendRequestUsingSocket();
		System.exit(0);
	}

	static void sendRequestUsingHttpClient() throws IOException {
		HttpClientUtil.doInHttpScopeUtil(() -> new HttpGet("http://localhost:1234/b.html"), resp -> {
			int status = resp.getStatusLine().getStatusCode();
			System.out.println(resp.getStatusLine());
			if (status >= 200 && status < 300) {
				HttpEntity entity = resp.getEntity();
				try {
					System.out.println(EntityUtils.toString(entity));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	static void sendRequestUsingSocket() throws UnknownHostException, IOException {
		Socket socket = new Socket("localhost", 1234);
		PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
		BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

		writer.println("GET /a.html HTTP/1.1");
		writer.println("Host: localhost:1234");
		writer.println("Connection: keep-alive");
		writer.println("Cache-Control: max-age=0");
		writer.println("Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
		writer.println("User-Agent: Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/38.0.2125.104 Safari/537.36");
		writer.println("Accept-Encoding: gzip,deflate,sdch");
		writer.println("Accept-Language: en-US,en;q=0.8");
		writer.println("");
		socket.shutdownOutput();

		String line = null;
		while (null != (line = reader.readLine())) {
			System.out.println(line);
		}
		socket.shutdownInput();

		socket.close();
	}
}
