package edu.xmu.test.javax.ognl;

import ognl.Ognl;
import ognl.OgnlContext;
import ognl.OgnlException;

public class OgnlSample {
	static class Person {
		String name;
	}

	static class Dog {
		String name;
	}

	public static void main(String[] args) throws OgnlException {
		Person p = new Person();
		p.name = "Yang";

		Dog d = new Dog();
		d.name = "Li";

		OgnlContext context = new OgnlContext();
		context.put(p.name, p);
		context.put(d.name, d);

		context.setRoot(p);

		Object parseExpression = Ognl.parseExpression("name");

		System.out.println(parseExpression);
	}
}
