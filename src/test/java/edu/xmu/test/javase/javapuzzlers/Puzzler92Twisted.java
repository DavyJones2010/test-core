package edu.xmu.test.javase.javapuzzlers;

import org.junit.Test;

public class Puzzler92Twisted {

	static class Twisted {
		private final String name;

		public Twisted(String name) {
			super();
			this.name = name;
		}

		private String name() {
			return name;
		}

		void printName() {
			System.out.println(name);
		}

		void reproduce() {
			final Twisted instance = this;
			new Twisted("reproduce") {
				void printName() {
					// print "main"; because name() is private cannot be inherited;
					// if we change name() to protected, the result would be "reproduce";
					System.out.println(name());
					// print "main"
					System.out.println(instance.name());

					Twisted instance2 = this;
					// print "reproduce"
					System.out.println(instance2.name());
				};
			}.printName();
			// print "reproduce"
			new Twisted("reproduce").printName();
		}
	}

	@Test
	public void test() {
		new Twisted("main").reproduce();
	}
}
