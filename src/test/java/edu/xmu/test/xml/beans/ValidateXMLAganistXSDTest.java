package edu.xmu.test.xml.beans;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.xml.bind.JAXBException;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import org.junit.Test;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

public class ValidateXMLAganistXSDTest {
	@Test(expected = SAXParseException.class)
	public void validateXMLAganistXSDTest() throws JAXBException, FileNotFoundException, SAXException, IOException {
		SchemaFactory schemaFactory = SchemaFactory.newInstance("http://www.w3.org/2001/XMLSchema");
		Source schemaSource = new StreamSource(new File("src/test/resources/xml/purchase_order.xsd"));
		Schema schema = schemaFactory.newSchema(schemaSource);
		schema.newValidator().validate(new StreamSource(new FileInputStream("src/test/resources/xml/purchase_order.xml")));
	}
}
