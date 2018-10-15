package edu.xmu.test.javax.ognl;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ognl.Ognl;
import ognl.OgnlContext;
import ognl.OgnlException;

import org.junit.Test;

public class OgnlContextTest {
	static class Person {
		String name;
		int age;
		Dog dog;

		public Person() {
			super();
		}

		public Person(Dog dog) {
			super();
			this.dog = dog;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public Dog getDog() {
			return dog;
		}

		public void setDog(Dog dog) {
			this.dog = dog;
		}

		public int getAge() {
			return age;
		}

		public void setAge(int age) {
			this.age = age;
		}

		@Override
		public String toString() {
			return "Person [name=" + name + ", age=" + age + ", dog=" + dog + "]";
		}

	}

	static class Dog {
		String name;

		public Dog(String name) {
			super();
			this.name = name;
		}

		public Dog() {
			super();
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		@Override
		public String toString() {
			return "Dog [name=" + name + "]";
		}

	}

	@Test
	public void test() throws OgnlException {
		Person rootPerson = new Person();
		rootPerson.setName("RootPerson");
		rootPerson.setAge(22);
		rootPerson.setDog(new Dog("Albert"));

		Dog d = new Dog();
		d.setName("Wangcai");
		Person p = new Person();
		p.setName("AAA");
		p.setAge(24);
		p.setDog(new Dog("LiAi"));

		OgnlContext ognlContext = new OgnlContext();
		ognlContext.put("person", p);
		ognlContext.put("dog", d);

		Object object2 = Ognl.getValue("name", ognlContext, rootPerson);
		System.out.println(object2);
		System.out.println(ognlContext.getRoot());
	}

	/**
	 * Ognl.getValue(String expression, Object root); <br>
	 * Ognl.getValue(String expression, Map context, Object root); <br>
	 */
	@Test
	public void test2() throws OgnlException {
		Person rootPerson = new Person();
		rootPerson.setName("RootPerson");
		rootPerson.setAge(22);
		rootPerson.setDog(new Dog("Albert"));
		assertEquals("Albert", Ognl.getValue("dog.name", rootPerson));

		Map<String, Object> context = new HashMap<String, Object>();
		context.put("dog", new Dog("LiAi"));
		assertEquals("LiAi", Ognl.getValue("#dog.name", context, rootPerson));
		assertEquals(22, Ognl.getValue("age", context, rootPerson));
	}

	@Test
	public void rootContextTest() throws OgnlException {
		Person p = new Person();
		p.setName("AAA");
		p.setAge(24);
		p.setDog(new Dog("LiAi"));

		Person rootPerson = new Person();
		rootPerson.setName("RootPerson");
		rootPerson.setAge(22);
		rootPerson.setDog(new Dog("Albert"));

		Dog d = new Dog();
		d.setName("Wangcai");

		OgnlContext ognlContext = new OgnlContext();
		ognlContext.put("person", p);
		ognlContext.put("dog", d);

		ognlContext.setRoot(rootPerson);
		System.out.println(ognlContext.getRoot());

		Object object = Ognl.parseExpression("name");
		System.out.println(object);
		Object object2 = Ognl.getValue(object, ognlContext, ognlContext.getRoot());
		System.out.println(object2);
		System.out.println(Ognl.getValue("age", ognlContext, ognlContext.getRoot()));
		System.out.println(Ognl.getValue("dog.name", ognlContext, ognlContext.getRoot()));

		Object object4 = Ognl.getValue("#person.name", ognlContext, ognlContext.getRoot());
		System.out.println(object4);
		System.out.println(Ognl.getValue("#person.age", ognlContext, ognlContext.getRoot()));
		System.out.println(Ognl.getValue("#person.dog.getName().toUpperCase()", ognlContext, ognlContext.getRoot()));

		Object object5 = Ognl.parseExpression("#dog.name");
		System.out.println(object5);
		Object object6 = Ognl.getValue(object5, ognlContext, ognlContext.getRoot());
		System.out.println(object6);
	}

	@Test
	public void rootContextTest2() throws OgnlException {
		Person p = new Person();
		p.setName("AAA");
		p.setAge(24);
		p.setDog(new Dog("LiAi"));

		Person rootPerson = new Person();
		rootPerson.setName("RootPerson");
		rootPerson.setAge(22);
		rootPerson.setDog(new Dog("Albert"));

		Dog d = new Dog();
		d.setName("Wangcai");

		OgnlContext ognlContext = new OgnlContext();
		ognlContext.put("person", p);
		ognlContext.put("dog", d);

		ognlContext.setRoot(rootPerson);
		System.out.println(ognlContext.getRoot());

		System.out.println(Ognl.getValue("name", rootPerson));

		System.out.println(Ognl.getValue("name", ognlContext, p));

		System.out.println(ognlContext.getRoot());
		System.out.println(Ognl.getValue("name", ognlContext));
	}

	/**
	 * "#object.method().method2()"
	 *
	 * @throws OgnlException
	 */
	@Test
	public void instanceMethodInvokeTest() throws OgnlException {
		OgnlContext context = new OgnlContext();

		Dog dog = new Dog("Wangcai");
		context.put("dog", dog);

		Object object = Ognl.parseExpression("#dog.getName().toUpperCase().length()");
		System.out.println(Ognl.getValue(object, context, context.getRoot()));
	}

	/**
	 * "@classFullName@staticMethod()" or "@@staticMethod()"
	 *
	 * @throws OgnlException
	 */
	@Test
	public void staticMethodInvokeTest() throws OgnlException {
		OgnlContext context = new OgnlContext();

		Object object = Ognl.parseExpression("@java.lang.Integer@toBinaryString(1024)");
		System.out.println(object);
		System.out.println(Ognl.getValue(object, context, context.getRoot()));

		object = Ognl.parseExpression("@@min(2, 3)");
		// object = Ognl.parseExpression("@java.lang.Math@min(2, 3)");
		System.out.println(object);
		System.out.println(Ognl.getValue(object, context, context.getRoot()));
	}

	/**
	 * "@classFullName@staticProperty" or "@@staticProperty"
	 *
	 * @throws OgnlException
	 */
	@Test
	public void staticPropertyTest() throws OgnlException {
		OgnlContext context = new OgnlContext();

		Object object = Ognl.parseExpression("@java.lang.Math@E");
		System.out.println(object);
		System.out.println(Ognl.getValue(object, context, context.getRoot()));

		object = Ognl.parseExpression("@@E");
		// object = Ognl.parseExpression("@java.lang.Math@E");
		System.out.println(object);
		System.out.println(Ognl.getValue(object, context, context.getRoot()));
	}

	@Test
	public void newObjectTest() throws OgnlException {
		OgnlContext context = new OgnlContext();

		Object object = Ognl.parseExpression("new java.util.ArrayList()");
		System.out.println(object);
		System.out.println(Ognl.getValue(object, context, context.getRoot()));
	}

	@Test
	public void newListTest() throws OgnlException {
		OgnlContext context = new OgnlContext();
		System.out.println(Ognl.getValue("{'aa', 'bb', 'cc'}", context, context.getRoot()));
		System.out.println(Ognl.getValue("{'aa', 'bb', 'cc'}[2]", context, context.getRoot()));
	}

	@Test
	public void newMapTest() throws OgnlException {
		OgnlContext context = new OgnlContext();
		System.out.println(Ognl.getValue("#{'key1':'value1', 'key2':'value2', 'key3':'value3'}", context, context.getRoot()));
		System.out.println(Ognl.getValue("#{'key1':'value1', 'key2':'value2', 'key3':'value3'}['key2']", context, context.getRoot()));
	}

	/**
	 * Filtering Syntax: "collection.{? expression}" <br/>
	 * Eg. "#persons.{? #this.name.length>4}" <br/>
	 * "collection.isEmpty"=="collection.isEmpty()", <br/>
	 * "collection.size" == "collection.size()" <br/>
	 * DummyProperty
	 *
	 * @throws OgnlException
	 */
	@Test
	public void filteringTest() throws OgnlException {
		OgnlContext context = new OgnlContext();
		List<Person> list = new ArrayList<Person>();

		Person p1 = new Person();
		p1.setName("Yang");
		Person p2 = new Person();
		p2.setName("Zhang");
		Person p3 = new Person();
		p3.setName("Wang");

		list.add(p1);
		list.add(p2);
		list.add(p3);
		context.put("persons", list);
		Object object = Ognl.getValue("#persons.{? #this.name.length()<=4}", context, context.getRoot());
		System.out.println(object);

		object = Ognl.getValue("#persons.{? #this.name.length()<=4}.size", context, context.getRoot());
		System.out.println(object);
	}

	/**
	 * Get the first/last element in collection that matches the filter <br/>
	 * Eg. "persons.{^ #this.name.length()>4}": Get the first element(with the
	 * format of array) in persons whose name's length > 4 <br/>
	 * Eg. "persons.{$ #this.name.length()>4}": Get the last element(with the
	 * format of array) in persons whose name's length > 4 <br/>
	 * Just like Filtering in Guava, or
	 * "SELECT * FROM DB_TABLE WHERE NAME.LENGTH>3" in SQL
	 *
	 * @throws OgnlException
	 */
	@Test
	public void collectionTest() throws OgnlException {
		OgnlContext context = new OgnlContext();
		List<Person> list = new ArrayList<Person>();
		Person p1 = new Person();
		p1.setName("Yang");
		Person p2 = new Person();
		p2.setName("Zhang");
		Person p3 = new Person();
		p3.setName("Wang");
		list.add(p1);
		list.add(p2);
		list.add(p3);
		context.put("persons", list);

		Object object = Ognl.getValue("#persons[0]", context, context.getRoot());
		System.out.println(object);

		object = Ognl.getValue("#persons.{^ #this.name.length() > 3}", context, context.getRoot());
		System.out.println(object);

		object = Ognl.getValue("#persons.{$ #this.name.length() > 3}", context, context.getRoot());
		System.out.println(object);
	}

	/**
	 * Eg. "#persons.{#this.name}" or "persions.{name}"<br/>
	 * Just like Transform in Guava or "SELECT NAME, AGE FROM DB_TABLE" in SQL
	 *
	 * @throws OgnlException
	 */
	@Test
	public void projectionTest() throws OgnlException {
		OgnlContext context = new OgnlContext();
		List<Person> list = new ArrayList<Person>();
		Person p1 = new Person();
		p1.setName("Yang");
		Person p2 = new Person();
		p2.setName("Zhang");
		Person p3 = new Person();
		p3.setName("Wang");
		list.add(p1);
		list.add(p2);
		list.add(p3);
		context.put("persons", list);

		Object object = Ognl.getValue("#persons.{#this.name}", context, context.getRoot());
		System.out.println(object);

		object = Ognl.getValue("#persons.{#this.name.length()>=5? #this.name : 'AAA'}", context, context.getRoot());
		System.out.println(object);
	}
}
