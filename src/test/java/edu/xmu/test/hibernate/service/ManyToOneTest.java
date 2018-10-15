package edu.xmu.test.hibernate.service;

import org.junit.Before;
import org.junit.Test;

import edu.xmu.test.hibernate.model.Person;
import edu.xmu.test.hibernate.model.PersonGroup;

public class ManyToOneTest {
	PersonService personService;

	@Before
	public void setUp() {
		personService = new PersonService();
	}

	@Test
	public void savePersonTest() {
		PersonGroup group = new PersonGroup();
		group.setName("GROUP_A");

		Person p = new Person();
		p.setName("Yang, Kunlun");
		p.setPersonGroup(group);

		Person p2 = new Person();
		p2.setName("Davy, Jones");
		p2.setPersonGroup(group);

		// We have to save group before saving person.
		// If not, TransientObjectException will be thrown.
		// It is because group is transient state, it do not have id assigned.
		personService.save(group);

		personService.save(p);
		personService.save(p2);
	}

	@Test
	public void savePersonCascadeTest() {
		PersonGroup group = new PersonGroup();
		group.setName("GROUP_B");

		Person p = new Person();
		p.setName("Calypso");
		p.setPersonGroup(group);

		Person p2 = new Person();
		p2.setName("Dow, Jones");
		p2.setPersonGroup(group);

		// Don't have to save group in advance, because cascade is set as
		// "save-update"
		personService.save(p);
		personService.save(p2);
	}
}
