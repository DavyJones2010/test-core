package edu.xmu.test.javaweb.httpclient;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.junit.Ignore;
import org.junit.Test;

public class HttpClientTest {
	//@Test
	//public void httpGetTest() throws IOException {
	//	HttpClientUtil.doInHttpScopeUtil(() -> new HttpGet("http://localhost:8080/test-core/home.json"), resp -> {
	//		System.out.println(resp.getStatusLine());
	//		HttpEntity entity = resp.getEntity();
	//		ContentType contentType = ContentType.getOrDefault(entity);
	//		System.out.println(contentType);
	//		// FileUtils.copyInputStreamToFile(entity.getContent(), new File("src/test/resources/javaweb/xml/home.json"));
	//			try (FileOutputStream fos = new FileOutputStream(new File("src/test/resources/javaweb/xml/home.json"))) {
	//				entity.writeTo(fos);
	//			} catch (Exception e) {
	//				e.printStackTrace();
	//			}
	//		});
	//}
    //
	//@Ignore
	//@Test
	//public void httpPostTest() throws ClientProtocolException, IOException {
	//	HttpClientUtil.doInHttpScopeUtil(() -> {
	//		return new HttpPost("http://localhost:8080/test-core/login");
	//	}, (resp) -> {
	//		System.out.println(resp.getStatusLine());
	//		HttpEntity entity = resp.getEntity();
	//		ContentType contentType = ContentType.getOrDefault(entity);
	//		System.out.println(contentType);
	//		try {
	//			FileUtils.copyInputStreamToFile(entity.getContent(), new File("src/test/resources/javaweb/xml/data.html"));
	//		} catch (Exception e) {
	//			e.printStackTrace();
	//		}
	//	});
	//}

	// public void fluentTest(){
	// Request.Get("http://targethost/homepage")
	// .execute().returnContent();
	// Request.Post("http://targethost/login")
	// .bodyForm(Form.form().add("username", "vip").add("password", "secret").build())
	// .execute().returnContent();
	// }

}
