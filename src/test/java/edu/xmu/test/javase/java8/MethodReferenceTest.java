package edu.xmu.test.javase.java8;

import java.util.Arrays;
import java.util.function.Consumer;

import org.junit.Test;

import com.google.common.base.Supplier;

/**
 * Reference to existing methods or constructors of java classes or objects
 */
public class MethodReferenceTest {
	@Test
	public void referenceTest() {
		Person p = Person.createPerson(Person::new);
		p.speak(e -> System.out.println(e.toString()));
		Person.staticSpeak(Person::staticHallo);

		Arrays.asList(p, new Person(), new Person()).forEach(Person::staticHallo);
		Arrays.asList(p, new Person(), new Person()).forEach(new Consumer<Person>() {
			@Override
			public void accept(Person t) {
				Person.staticHallo(t);
			}
		});
		Arrays.asList(p, new Person(), new Person()).forEach(t -> Person.staticHallo(t));

		Arrays.asList(p, new Person(), new Person()).forEach(p::nonStaticHallo);
		Arrays.asList(p, new Person(), new Person()).forEach(new Consumer<Person>() {
			@Override
			public void accept(Person t) {
				p.nonStaticHallo(t);
			}
		});
		Arrays.asList(p, new Person(), new Person()).forEach(t -> p.nonStaticHallo(p));
	}

	public static class Person {
		public static Person createPerson(Supplier<Person> supplier) {
			return supplier.get();
		}

		public void speak(Consumer<Person> p) {
			p.accept(this);
		}

		public static void staticHallo(Person p) {
			System.out.println("StaticHallo: " + p);
		}

		public static void staticSpeak(Consumer<Person> p) {
			p.accept(new Person());
		}

		public void nonStaticHallo(Person p) {
			System.out.println("NonStaticHallo: " + p);
		}
	}
}
