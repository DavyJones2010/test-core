package edu.xmu.test.j2ee.ws;

import java.io.IOException;
import java.net.URL;

import javax.xml.namespace.QName;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPBodyElement;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;
import javax.xml.soap.SOAPPart;
import javax.xml.ws.Dispatch;
import javax.xml.ws.Service;

import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

public class SoapTest {
	@Test
	public void createMsgTest() throws SOAPException, IOException {
		final String namespace = "edu.xmu.ws";
		// 1. Create MessageFactory
		MessageFactory factory = MessageFactory.newInstance();
		// 2. Create SOAPMessage
		SOAPMessage message = factory.createMessage();
		// 3. Create SOAPPart
		SOAPPart part = message.getSOAPPart();
		// 4. Get SOAPEnvelope
		SOAPEnvelope envelope = part.getEnvelope();
		// 5. Get SOAPHeader&SOAPBody via SOAPEnvelope
		SOAPBody body = envelope.getBody();
		// 6. Create XMLNode via QName
		// Create QName: <ns:add xmlns="edu.xmu.ws"/>
		QName qName = new QName(namespace, "add", "ns");
		// If we set value directly, signal < will be transformed to &lt;
		// body.addBodyElement(qName).setValue("<a>123123</a><b>111</b>");
		SOAPBodyElement element = body.addBodyElement(qName);
		element.addChildElement("a").setValue("123");
		element.addChildElement("b").setValue("234");

		message.writeTo(System.out);
	}

	@Test
	public void sendSOAPMsgTest() throws Exception {
		String wsdlUrl = "http://localhost:12345/ns?wsdl";
		URL url = new URL(wsdlUrl);
		String ns = "edu.xmu.ws";
		QName qName = new QName(ns, "MyService");

		// 1. Create Service
		Service service = Service.create(url, qName);
		// 2. Create Dispatcher
		Dispatch<SOAPMessage> dispatch = service.createDispatch(new QName("edu.xmu.ws", "MyServiceImplPort"),
				SOAPMessage.class, Service.Mode.MESSAGE);
		// 3. Create SOAPMessage
		SOAPMessage message = MessageFactory.newInstance().createMessage();
		SOAPPart soapPart = message.getSOAPPart();
		SOAPEnvelope envelope = soapPart.getEnvelope();
		SOAPBody soapBody = envelope.getBody();

		// 4. Create Element
		QName addName = new QName("http://ws.j2ee.test.xmu.edu/", "add", "nn");
		SOAPElement addChildElement = soapBody.addChildElement(addName);
		addChildElement.addChildElement("a").setValue("234");
		addChildElement.addChildElement("b").setValue("234");
		message.writeTo(System.out);
		System.out.println();

		// 5. Send SOAPMessage
		SOAPMessage soapMessage = dispatch.invoke(message);
		soapMessage.writeTo(System.out);
		System.out.println();

		// 6. Resolve returned SOAPMessage
		Document document = soapMessage.getSOAPPart().getEnvelope().getBody().extractContentAsDocument();
		Node item = document.getElementsByTagName("addResult").item(0);
		String value = item.getChildNodes().item(0).getNodeValue();
		System.out.println(value);
	}
}
