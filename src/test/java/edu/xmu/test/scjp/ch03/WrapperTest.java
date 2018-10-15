package edu.xmu.test.scjp.ch03;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class WrapperTest {
	@Test
	public void integerEqualsTest() {
		assertFalse(new Integer(127) == new Integer(127));
		assertTrue((new Integer(127)).equals(new Integer(127)));

		Integer i = 127;
		Integer j = 127;
		assertTrue(i == j);
		assertTrue(i.equals(j));

		i = 128;
		j = 128;
		assertFalse(i == j);
		assertTrue(i.equals(j));
	}

	@Test
	public void booleanEqualsTest() {
		assertFalse(new Boolean("true") == new Boolean("true"));
		assertTrue((new Boolean("true")).equals(new Boolean("true")));

		Boolean a = true;
		Boolean b = true;
		assertTrue(a == b);
	}

	@Test
	public void nullTest() {
		Person p = new Person();
		assertNull(p.age);
	}

	public static class Person {
		Integer age;
	}
}
