package edu.xmu.test.xml.beans;

import java.io.FileReader;
import java.io.Reader;

import javax.xml.stream.EventFilter;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.events.XMLEvent;

import org.junit.Test;

public class StaxTest {
	@Test
	public void cursorBasedReadTest() throws Exception {
		XMLInputFactory factory = XMLInputFactory.newInstance();
		try (Reader reader = new FileReader("src/main/resources/mooc/webservice/person_jaxb.xml")) {
			XMLStreamReader streamReader = factory.createXMLStreamReader(reader);
			while (streamReader.hasNext()) {
				int next = streamReader.next();
				if (XMLStreamConstants.START_ELEMENT == next) {
					System.out.println(streamReader.getName().toString());
				} else if (XMLStreamConstants.CHARACTERS == next) {
					System.out.println(streamReader.getText().trim());
				} else if (XMLStreamConstants.END_ELEMENT == next) {
					System.out.println("/" + streamReader.getName().toString());
				}
			}
		}
	}

	@Test
	public void cursorBasedReadTest2() throws Exception {
		XMLInputFactory factory = XMLInputFactory.newInstance();
		try (Reader reader = new FileReader("src/main/resources/mooc/webservice/person_jaxb.xml")) {
			XMLStreamReader streamReader = factory.createXMLStreamReader(reader);
			while (streamReader.hasNext()) {
				int next = streamReader.next();
				if (XMLStreamConstants.START_ELEMENT == next) {
					if ("age".equals(streamReader.getName().toString())) {
						System.out.print(streamReader.getElementText() + ":");
					} else if ("name".equals(streamReader.getName().toString())) {
						System.out.print(streamReader.getElementText() + "\n");
					}
				}
			}
		}
	}

	@Test
	public void iteratorBasedReadTest() throws Exception {
		XMLInputFactory factory = XMLInputFactory.newInstance();
		try (Reader reader = new FileReader("src/main/resources/mooc/webservice/person_jaxb.xml")) {
			XMLEventReader eventReader = factory.createXMLEventReader(reader);
			while (eventReader.hasNext()) {
				XMLEvent event = eventReader.nextEvent();
				if (event.isStartElement()) {
					String name = event.asStartElement().getName().toString();
					if ("age".equals(name)) {
						System.out.print(eventReader.getElementText() + ":");
					} else if ("name".equals(name)) {
						System.out.print(eventReader.getElementText() + "\n");
					}
				}
			}
		}
	}

	@Test
	public void filterBasedReadTest() throws Exception {
		XMLInputFactory factory = XMLInputFactory.newInstance();
		try (Reader reader = new FileReader("src/main/resources/mooc/webservice/person_jaxb.xml")) {
			XMLEventReader filterReader = factory.createFilteredReader(factory.createXMLEventReader(reader),
					new EventFilter() {
						@Override
						public boolean accept(XMLEvent event) {
							return event.isStartElement();
						}
					});
			// now the filterReader is a filtered event reader, the source has
			// already been filtered.
			while (filterReader.hasNext()) {
				XMLEvent event = filterReader.nextEvent();
				String name = event.asStartElement().getName().toString();
				if ("age".equals(name)) {
					System.out.print(filterReader.getElementText() + ":");
				} else if ("name".equals(name)) {
					System.out.print(filterReader.getElementText() + "\n");
				}
			}
		}
	}
}
