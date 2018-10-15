package edu.xmu.test.scjp.ch01;

public class Parent {
	protected int age;

	class ParentException extends Exception {

	}

	public Parent() {
		super();
	}
	private void sayParent() {
		System.out.println("Parent");
	}
	protected void yesParent() {
		System.out.println("Parent");
	}
	void goodParent() {
		System.out.println("Parent");
	}
	public void excep() {

	}
}
