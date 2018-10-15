package edu.xmu.test.effectivejava.sample.object;

import org.junit.Test;

public class Chapter2Builder2Test {
	@Test
	public void traditionalBuildTest() {
		MutableStudent s = new MutableStudent("Davy", "24");
		MutableStudent is = s.getImmutableCopy();
		System.out.println(is);

		s.setAge("25");
		System.out.println(is);
		is = s.getImmutableCopy();
		System.out.println(is);

		is.getImmutableCopy();
	}

	@Test
	public void builderBuildTest() {
		MutableStudent2 s = MutableStudent2.builder("Davy").age("24").build();
		System.out.println(s);
	}

	static class MutableStudent {
		String name;
		String age;

		public MutableStudent(String name) {
			super();
			this.name = name;
		}

		public MutableStudent(String name, String age) {
			super();
			this.name = name;
			this.age = age;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getAge() {
			return age;
		}

		public void setAge(String age) {
			this.age = age;
		}

		@Override
		public String toString() {
			return "Student [name=" + name + ", age=" + age + "]";
		}

		public ImmutableStudent getImmutableCopy() {
			return this instanceof ImmutableStudent ? (ImmutableStudent) this : new ImmutableStudent(name, age);
		}

		static class ImmutableStudent extends MutableStudent {

			public ImmutableStudent(String name, String age) {
				super(name, age);
			}

			@Override
			public void setName(String name) {
				throw new UnsupportedOperationException();
			}

			@Override
			public void setAge(String age) {
				throw new UnsupportedOperationException();
			}
		}
	}

	static class MutableStudent2 {
		final String name;
		final String age;

		private MutableStudent2(String name, String age) {
			super();
			this.name = name;
			this.age = age;
		}

		public String getName() {
			return name;
		}

		public String getAge() {
			return age;
		}

		@Override
		public String toString() {
			return "Student [name=" + name + ", age=" + age + "]";
		}

		public static StudentBuilder builder(String name) {
			return new StudentBuilder(name);
		}

		static class StudentBuilder {
			final String name;
			String age;

			public StudentBuilder(String name) {
				this.name = name;
			}

			public StudentBuilder age(String age) {
				this.age = age;
				return this;
			}

			public MutableStudent2 build() {
				return new MutableStudent2(this.name, this.age);
			}
		}
	}
}
