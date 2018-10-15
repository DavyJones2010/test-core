package edu.xmu.test.scjp.ch02;

public class Parent {
	private String name;
	{
		System.out.println("Parent");
		name = "Parent";
	}
	static class Child extends Parent {
		String name;
		{
			System.out.println("ChildA");
			name = "Child";
		}
		Child() {
			super();
			System.out.println("ChildB");
		}
		private String getName() {
			return name;
		}
	}

	private String getName() {
		return name;
	}
	public static void main(String[] args) {
		Parent c = new Child();

	}
}
