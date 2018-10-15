package edu.xmu.test.javase.javapuzzlers;

import org.junit.Test;

public class Puzzler80Reflection {

	static class Outer {
		public void greetWorldBad() throws Exception {
			System.out.println(Inner.class.newInstance());
		}

		public void greetWorldGood() throws Exception {
			System.out.println(Inner.class.getConstructor(Outer.class).newInstance(this));
		}

		class Inner {
			Outer outer;

			public Inner() {
			}

			public Inner(Outer outer) {
				this.outer = outer;
			}

			@Override
			public String toString() {
				return "hello";
			}
		}
	}

	@Test(expected = java.lang.InstantiationException.class)
	public void test() throws Exception {
		new Outer().greetWorldBad();
	}

	@Test
	public void test2() throws Exception {
		new Outer().greetWorldGood();
	}
}
