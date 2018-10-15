package edu.xmu.test.guava.collect;

import static org.junit.Assert.assertEquals;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Test;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Maps.EntryTransformer;
import com.google.common.collect.Sets;

public class MapsTest {
	@Test
	public void traditionalTest() {
		List<Person> personList = Lists.newArrayList(new Person("Davy", "Male", 24), new Person("Calypso", "Female", 22));
		Map<String, Person> map = Maps.newHashMap();
		for (Person p : personList) {
			map.put(p.getName(), p);
		}

		assertEquals(2, map.size());
	}

	@Test
	public void uniqueIndexTest() {
		List<Person> personList = Lists.newArrayList(new Person("Davy", "Male", 24), new Person("Calypso", "Female", 22));
		Map<String, Person> map = Maps.uniqueIndex(personList, new Function<Person, String>() {
			public String apply(Person input) {
				return input.getName();
			}
		});

		assertEquals(2, map.size());
	}

	@Test
	public void asMapTest() {
		Set<String> nameSet = Sets.newHashSet("Davy", "Calypso");
		Map<String, Person> map = Maps.asMap(nameSet, new Function<String, Person>() {
			public Person apply(String name) {
				return new Person(name, "UNKNOWN", 0);
			}
		});
		assertEquals(2, map.size());
	}

	@Test
	public void transformEntriesTest() {
		Map<String, Person> personMap = Maps.newHashMap();
		personMap.put("Davy", new Person("Davy", "Male", 24));
		personMap.put("Calypso", new Person("Calypso", "Female", 22));
		Map<String, People> peopleMap = Maps.transformEntries(personMap, new EntryTransformer<String, Person, People>() {
			public People transformEntry(String key, Person value) {
				return new People(value.getName(), value.getGender());
			}
		});
		assertEquals(2, peopleMap.size());
	}

	static class People {
		String name;
		String gender;

		public People(String name, String gender) {
			super();
			this.name = name;
			this.gender = gender;
		}

	}

	static class Person {
		String name;
		String gender;
		int age;

		public Person(String name, String gender, int age) {
			super();
			this.name = name;
			this.gender = gender;
			this.age = age;
		}

		public String getName() {
			return name;
		}

		public String getGender() {
			return gender;
		}

		public int getAge() {
			return age;
		}

		@Override
		public String toString() {
			return "Person [name=" + name + ", gender=" + gender + ", age=" + age + "]";
		}
	}
}
