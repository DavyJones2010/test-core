package edu.xmu.test.xml.beans;

import java.io.File;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.junit.Test;

import com.google.common.collect.Lists;

import edu.xmu.test.xml.beans.Address;
import edu.xmu.test.xml.beans.Person;
import edu.xmu.test.xml.beans.Pet;
import edu.xmu.test.xml.beans.Pets;

public class JAXBTest {
	@Test
	public void marshalTest() throws JAXBException {
		Person p = new Person(1, "Yang", 25, new Address("China", "Shanghai", "Shanghai", "Guanglan. RD", 1000));
		Pets pets = new Pets();
		pets.setPets(Lists.newArrayList(new Pet(1, "Woofee"), new Pet(2, "Doggy")));
		p.setPets(pets);
		JAXBContext context = JAXBContext.newInstance(Person.class);
		Marshaller marshaller = context.createMarshaller();

		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		marshaller.marshal(p, new File("src/test/resources/xml/person_jaxb.xml"));
	}

	@Test
	public void unmarshalTest() throws JAXBException {
		JAXBContext context = JAXBContext.newInstance(Person.class);
		Unmarshaller unmarshaller = context.createUnmarshaller();
		Person p = (Person) unmarshaller.unmarshal(new File("src/test/resources/xml/person_jaxb.xml"));
		System.out
				.println(p.getAge() + ", " + p.getAddress().getCity() + ", " + p.getPets().getPets().get(0).getName());
	}
}
