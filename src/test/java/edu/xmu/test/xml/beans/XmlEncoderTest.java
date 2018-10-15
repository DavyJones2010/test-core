package edu.xmu.test.xml.beans;

import java.beans.XMLEncoder;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import org.junit.Test;

import com.google.common.collect.Lists;

import edu.xmu.test.xml.beans.Address;
import edu.xmu.test.xml.beans.Person;
import edu.xmu.test.xml.beans.Pet;
import edu.xmu.test.xml.beans.Pets;

public class XmlEncoderTest {

	@Test
	public void encodeToStringTest() throws FileNotFoundException {
		Person p = new Person(1, "Yang", 25, new Address("China", "Shanghai",
				"Shanghai", "Guanglan. RD", 1000));
		p.setPets(new Pets(Lists.newArrayList(new Pet(1, "Woofee"), new Pet(2,
				"Doggy"))));
		XMLEncoder encoder = new XMLEncoder(new FileOutputStream(
				"src/test/resources/xml/person.xml"));
		encoder.writeObject(p);
		encoder.close();
	}
}
