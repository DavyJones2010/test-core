package edu.xmu.test.scjp.ch01;

public class AnotherChild extends Parent {
	static enum ChildType {
		GOOD_CHILD, BAD_CHILD;
	}

	private static class Inner {
		private void speak() {

		}
	}

	public void test() {
		Parent p = new Parent();
		System.out.println(this.age);
		System.out.println(super.age);
		System.out.println(p.age);
	}

	public static void main(String[] args) {
		AnotherChild child = new AnotherChild();
		child.test();
		System.out.println(AnotherChild.ChildType.GOOD_CHILD);
		System.out.println(child.age);
		System.out.println(Skippable.age);
		int[] aaa;
		aaa = new int[] { 1, 2 };
		aaa = new int[10];
		System.out.println(aaa);
		int a = 0;
		int $a = 0;
		int _b = 0;
		AnotherChild.Inner inner = new AnotherChild.Inner();
		inner.speak();
	}

	@Override
	public void excep() throws RuntimeException {
	}

	class Inner2 {
		public void test() {
			AnotherChild.Inner inner = new AnotherChild.Inner();
			inner.speak();
		}
	}
}
