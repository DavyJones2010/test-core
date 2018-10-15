package edu.xmu.test.javase.monitor;

public class Hello implements HelloMBean {
	private final String name;
	private final int age;
	private String email;

	public Hello(String name, int age, String email) {
		this.name = name;
		this.age = age;
		this.email = email;
	}

	@Override
	public int getAge() {
		return age;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public String getEmail() {
		return email;
	}

	@Override
	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String sayHello() {
		return "Hello, I'm " + name + ".";
	}
}
