package edu.xmu.test.guava.collect;

public class People {
	private String name;
	private int age;

	public People(String name, int age) {
		super();
		this.name = name;
		this.age = age;
	}

	@Override
	public String toString() {
		return "People [name=" + name + ", age=" + age + "]";
	}

}
