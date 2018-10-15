package edu.xmu.test.j2ee.ws;

import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;

public class MyWebServiceClient {
	public static void main(String[] args) throws MalformedURLException {
		URL url = new URL("http://localhost:12345/ns?wsdl");
		QName qName = new QName("edu.xmu.ws", "MyService");
		Service service = Service.create(url, qName);
		IMyService port = service.getPort(IMyService.class);
		System.out.println(port.add(1, 2));
		System.out.println(port.subtract(4, 5));
	}
}
