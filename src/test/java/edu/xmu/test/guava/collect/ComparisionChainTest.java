package edu.xmu.test.guava.collect;

import java.util.Set;

import org.apache.log4j.Logger;
import org.junit.Test;

import com.google.common.collect.ComparisonChain;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

public class ComparisionChainTest {

	Logger logger = Logger.getLogger(ComparisionChainTest.class);

	@Test
	public void sortTest() {
		Set<Person> persons = Sets.newTreeSet(Lists.newArrayList(new Person("Yang", 23), new Person("Yang", 20), new Person("Yang", 33), new Person("Li", 12), new Person("Zhang",
				21)));

		logger.info(persons);
	}

	static class Person implements Comparable<Person> {
		private String name;
		private int age;

		public Person(String name, int age) {
			super();
			this.name = name;
			this.age = age;
		}

		public int compareTo(Person o) {
			return ComparisonChain.start().compare(this.name, o.name).compare(this.age, o.age).result();
		}

		@Override
		public String toString() {
			return "Person [name=" + name + ", age=" + age + "]";
		}

	}
}
