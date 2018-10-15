package edu.xmu.test.javaweb.httpclient;

import java.io.IOException;
import java.util.function.Consumer;
import java.util.function.Supplier;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

public final class HttpClientUtil {
	public static void doInHttpScopeUtil(Supplier<HttpUriRequest> reqProvider, Consumer<CloseableHttpResponse> respConsumer) throws IOException {
		HttpUriRequest req = reqProvider.get();
		try (CloseableHttpClient httpclient = HttpClients.createDefault(); CloseableHttpResponse resp = httpclient.execute(req)) {
			respConsumer.accept(resp);
		}
	}
}
