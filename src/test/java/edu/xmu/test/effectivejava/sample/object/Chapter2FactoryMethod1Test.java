package edu.xmu.test.effectivejava.sample.object;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.math.BigInteger;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.junit.Test;

import com.google.common.cache.CacheBuilder;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Lists;
import com.google.common.collect.Table;

/**
 * Consider use static factory method instead of constructor
 *
 */
public class Chapter2FactoryMethod1Test {
	@Test
	public void factoryMethodHasNamesTest() {
		BigInteger val = BigInteger.probablePrime(10, new Random());
		System.out.println(val);
	}

	@Test
	public void factoryMethodHasPoolingCapabilityTest() {
		Person p = PersonFactory.getPerson("Yang", 25);
		Person p2 = PersonFactory.getPerson("Yang", 25);
		assertEquals(p, p2);
	}

	@Test
	public void factoryMethodHasPoolingCapabilityTest2() {
		assertTrue(Boolean.valueOf(true) == Boolean.valueOf(true));
	}

	@Test
	public void factoryMethodCanReturnSubClassesTest() {
		CacheBuilder.newBuilder().build(); // Returns LocalManualCache
	}

	/**
	 * SPI (Service Provider Interface)
	 */
	@Test
	public void factoryMethodCanReturnSubClassesTest2() {
		// JDBC API: DriverManager/Connection/
		// JNDI API: LocateRegistry.getRegistry().bind("adder", stub);/LocateRegistry.getRegistry(12345).lookup("adder");
		// JAXP API: <a href="http://en.wikipedia.org/wiki/Java_API_for_XML_Processing">JAXP</a>
	}

	@SuppressWarnings("unused")
	@Test
	public void factoryMethodTypeInference() {
		List<String> list = Lists.newArrayList();
		Table<String, String, String> tab = HashBasedTable.create();
	}

	@Test
	public void factoryMethodNameCannotBeDistinguishedFromNormalMethod() {
		// we have to search a long time or look up the javadoc, extra effort
		new Date();
		Calendar.getInstance();
		// goot practice: getInstance(); newInstance(); valueOf()/of();
		// Summary of Constructor/Factory/SpringIOC
	}

	static class PersonFactory {
		private final static Table<String, Integer, Person> personStore = HashBasedTable.create();

		public static Person getPerson(String name, int age) {
			if (personStore.contains(name, age)) {
				return personStore.get(name, age);
			} else {
				Person p = new Person(name, age);
				personStore.put(name, age, p);
				return p;
			}
		}
	}

	private static class Person {
		final String name;
		final int age;

		public Person(String name, int age) {
			super();
			this.name = name;
			this.age = age;
		}

		@Override
		public String toString() {
			return "Person [name=" + name + ", age=" + age + "]";
		}
	}

}
