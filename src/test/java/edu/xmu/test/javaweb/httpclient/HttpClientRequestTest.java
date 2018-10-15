package edu.xmu.test.javaweb.httpclient;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

/**
 * {@link <a href="https://hc.apache.org/httpcomponents-client-ga/httpclient/examples/org/apache/http/examples/client/ClientWithResponseHandler.java">ClientWithResponseHandler</a>}
 *
 */
public class HttpClientRequestTest {
	@Test
	public void respTest() throws IOException {
		HttpClientUtil.doInHttpScopeUtil(() -> new HttpGet("http://localhost:8080/test-core/home.json"), resp -> {
			int status = resp.getStatusLine().getStatusCode();
			System.out.println(resp.getStatusLine());
			// statusLine: protocol+statusCode+reasonPhrase
				if (status >= 200 && status < 300) {
					HttpEntity entity = resp.getEntity();
					try {
						System.out.println(EntityUtils.toString(entity));
					} catch (Exception e) {
						e.printStackTrace();
					}
				} else {
					throw new RuntimeException("Unexpected response status: " + status);
				}
			});

	}
}
