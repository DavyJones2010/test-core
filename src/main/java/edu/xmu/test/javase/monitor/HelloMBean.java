package edu.xmu.test.javase.monitor;

public interface HelloMBean {
	public int getAge();

	public String getName();

	public String getEmail();

	public void setEmail(String email);

	public String sayHello();
}
