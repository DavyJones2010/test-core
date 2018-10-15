package edu.xmu.test.scjp.ch01.sub;

import edu.xmu.test.scjp.ch01.Parent;

public class Child extends Parent {
	protected int age;

	public void sayChild() {
		// super.sayParent();
	}

	public void yesChild() {
		super.yesParent();
		yesParent();
	}

	public void goodChild() {
		// super.goodParent();
		// goodParent();
	}

}
