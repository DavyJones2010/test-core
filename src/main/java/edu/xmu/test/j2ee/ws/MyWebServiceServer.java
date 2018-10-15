package edu.xmu.test.j2ee.ws;

import javax.xml.ws.Endpoint;

public class MyWebServiceServer {
	public static void main(String[] args) {
		String addr = "http://localhost:12345/ns";
		Endpoint.publish(addr, new MyServiceImpl());
	}
}
