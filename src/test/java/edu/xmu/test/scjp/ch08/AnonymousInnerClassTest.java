package edu.xmu.test.scjp.ch08;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 *
 * Anonymous inner class can impelent only one interface
 */
public class AnonymousInnerClassTest {

	static class Popcorn {
		String name;

		public Popcorn() {
			super();
		}

		public Popcorn(String name) {
			super();
			this.name = name;
		}

		public String pop() {
			return "popcorn";
		}
	}

	static interface Utterable {
		public String utter();
	}

	@Test
	public void poaincTest() {
		Popcorn p = new Popcorn("Hallo") {
			@Override
			public String pop() {
				return "anonymous popcorn";
			}

			@SuppressWarnings("unused")
			public String nameAFew() {
				return "Hallo";
			}
		};
		assertEquals("anonymous popcorn", p.pop());
		// p.nameAFew(); //We never will access the method "nameAFew()"
		p = new Popcorn() {
		}; // Do need comma here for anonymous inner class instance defination.
			// compare with MethodInnerClass declaration
		assertEquals("popcorn", p.pop());
	}

	@Test
	public void poaicTest2() {
		Utterable u = new Utterable() {
			@Override
			public String utter() {
				return "Hallo";
			}
		};
		assertEquals("Hallo", u.utter());
	}

	static interface Bird {
		String fly(Sky sky);
	}

	static interface Sky {
		int showWidth();
	}

	@Test
	public void argumentDefinedAnonymousInnerClassTest() {
		Bird bird = new Bird() {
			@Override
			public String fly(Sky sky) {
				return "The sky is " + sky.showWidth() + " meters width";
			}
		};

		String msg = bird.fly(new Sky() {
			@Override
			public int showWidth() {
				return 12;
			}

		});
		assertEquals("The sky is 12 meters width", msg);
	}
}
