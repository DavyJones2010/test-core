package edu.xmu.test.guava.basic;

import static org.junit.Assert.assertTrue;

import org.apache.log4j.Logger;
import org.junit.Test;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.base.Predicates;

public class PredicatesTest {
	private Logger logger = Logger.getLogger(PredicatesTest.class);

	@Test
	public void andTest() {
		String str = "Davy Jones";
		boolean containsKeyWords = Predicates.not(new Predicate<String>() {
			public boolean apply(String input) {
				return input.contains("Davy");
			}
		}).apply(str);

		logger.info(containsKeyWords);
	}

	@Test
	public void composeTest() {
		boolean isPersonMale = Predicates.compose(new Predicate<String>() {
			public boolean apply(String input) {
				return "Male".equals(input);
			}
		}, new Function<Person, String>() {

			public String apply(Person input) {
				return input.getGender();
			}
		}).apply(new Person("Davy", "Male"));
		assertTrue(isPersonMale);
	}

	static class Person {
		String name;
		String gender;

		public Person(String name, String gender) {
			super();
			this.name = name;
			this.gender = gender;
		}

		public String getName() {
			return name;
		}

		public String getGender() {
			return gender;
		}
	}
}
