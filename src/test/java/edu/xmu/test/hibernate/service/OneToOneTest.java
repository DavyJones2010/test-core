package edu.xmu.test.hibernate.service;

import org.junit.Before;
import org.junit.Test;

import edu.xmu.test.hibernate.model.Husband;
import edu.xmu.test.hibernate.model.IDCard;
import edu.xmu.test.hibernate.model.People;
import edu.xmu.test.hibernate.model.Wife;

public class OneToOneTest {
	PersonService personService;

	@Before
	public void setUp() {
		personService = new PersonService();
	}

	@Test
	public void savePeopleTest() {
		IDCard idCard = new IDCard();
		idCard.setCardNo("123456789012");

		People p = new People();
		p.setName("Yang, Kunlun");
		p.setIdCard(idCard);

		// We don't have to save id card before saving people.
		// Won't have TransientObjectException
		// Cascade is set as "all" by default for one-to-one tag
		personService.save(p);
	}

	@Test
	public void getPeopleTest() {
		People p = (People) personService.get(People.class, 1);
		// We have to set lazy="false". If not, there would be problem when we
		// access people's idCard peoperty. As the session is closed, and when
		// we need to access idCard, hibernate tries to fetch that property from
		// table, but now we're out of session scope and exception would occur;
		System.out.println(p);
	}

	@Test
	public void biDirectionalSaveTest() {
		Wife wife = new Wife();
		wife.setName("Wife");

		Husband husband = new Husband();
		husband.setName("Husband");

		husband.setWife(wife);
		wife.setHusband(husband);
		personService.save(husband);
	}

	@Test
	public void biDirectionalGetWithPrimaryKeyAssocTest() {
		Husband husband = (Husband) personService.get(Husband.class, 1);
		System.out.println(husband);

		Wife wife = (Wife) personService.get(Wife.class, 1);
		System.out.println(wife);
	}

	@Test
	public void biDirectionalWithForeignKeyAssocSaveTest() {
		Wife wife = new Wife();
		wife.setName("wife_2");

		Husband husband = new Husband();
		husband.setName("husband_2");

		husband.setWife(wife);
		wife.setHusband(husband);
		personService.save(wife);
		personService.save(husband);
	}

	@Test
	public void biDirectionalGetWithForeignKeyAssocTest() {
		Husband husband = (Husband) personService.get(Husband.class, 2);
		System.out.println(husband);

		Wife wife = (Wife) personService.get(Wife.class, 1);
		System.out.println(wife);
	}

}
