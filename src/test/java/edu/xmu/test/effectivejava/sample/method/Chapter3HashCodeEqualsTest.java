package edu.xmu.test.effectivejava.sample.method;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * Pay special attention when override equals <br/>
 * Do not make equals too smart!!!
 */
public class Chapter3HashCodeEqualsTest {

	@Test
	public void equalsSymmetryTest() {
		CaseInsensitiveString s1 = new CaseInsensitiveString("hallo");
		assertTrue(s1.equals("Hallo"));
		assertFalse("Hallo".equals(s1));
	}

	/**
	 * a == b, b == c => a == c
	 */
	@Test
	public void equalsTransitivityTest() {
		Point p = new ColorPoint(1, 2, "RED");
		Point p2 = new Point(1, 2);
		Point p3 = new ColorPoint(1, 2, "BLUE");

		assertTrue(p2.equals(p));
		assertTrue(p2.equals(p3));
		// voilate transitivity!!!
		assertFalse(p.equals(p3));

		// violate symmetry!!!
		assertFalse(p.equals(p2));
	}

	@Test
	public void equalsUsingGeneratedTest() {
		Dummy dummy = new Dummy("Yang", 25);
		Dummy subDummy = new SubDummy("Yang", 25, "Hallo");
		assertFalse(dummy.equals(subDummy));
		assertFalse(subDummy.equals(dummy));
	}

	private static class Dummy {
		String name;
		int age;

		public Dummy(String name, int age) {
			super();
			this.name = name;
			this.age = age;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + age;
			result = prime * result + ((name == null) ? 0 : name.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Dummy other = (Dummy) obj;
			if (age != other.age)
				return false;
			if (name == null) {
				if (other.name != null)
					return false;
			} else if (!name.equals(other.name))
				return false;
			return true;
		}
	}

	private static class SubDummy extends Dummy {
		String pwd;

		public SubDummy(String name, int age, String pwd) {
			super(name, age);
			this.pwd = pwd;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = super.hashCode();
			result = prime * result + ((pwd == null) ? 0 : pwd.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (!super.equals(obj))
				return false;
			if (getClass() != obj.getClass())
				return false;
			SubDummy other = (SubDummy) obj;
			if (pwd == null) {
				if (other.pwd != null)
					return false;
			} else if (!pwd.equals(other.pwd))
				return false;
			return true;
		}
	}

	private static class Point {
		private int x;
		private int y;

		public Point(int x, int y) {
			super();
			this.x = x;
			this.y = y;
		}

		@Override
		public boolean equals(Object obj) {
			if (!(obj instanceof Point)) {
				return false;
			}
			Point p = (Point) obj;
			return p.x == this.x && p.y == this.y;
		}
	}

	private static class ColorPoint extends Point {
		private String color;

		public ColorPoint(int x, int y, String color) {
			super(x, y);
			this.color = color;
		}

		@Override
		public boolean equals(Object obj) {
			if (!(obj instanceof ColorPoint)) {
				return false;
			}
			return super.equals(obj) && ((ColorPoint) obj).color.equals(this.color);
		}
	}

	private static final class CaseInsensitiveString {
		private final String s;

		public CaseInsensitiveString(String s) {
			super();
			this.s = s;
		}

		@Override
		public boolean equals(Object obj) {
			if (obj instanceof CaseInsensitiveString) {
				return s.equalsIgnoreCase(((CaseInsensitiveString) obj).s);
			}
			if (obj instanceof String) {
				return s.equalsIgnoreCase((String) obj);
			}
			return false;
		}
	}

}
