package edu.xmu.test.scjp.ch07;

import java.util.ArrayList;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;

public class GenericPolymorphismTest {
	public static class GrandParent {
	}

	public static class Parent extends GrandParent {
	}

	public static class Child extends Parent {
	}

	// Cannot take a List<Dog>
	void foo(List<Animal> animals) {
	}

	// Cannot return List<Dog>
	List<Animal> bar() {
		// return new ArrayList<Object>();
		// return new ArrayList<Dog>();
		return new ArrayList<Animal>();
	}

	@Test
	public void listTest() {
		List<List<String>> list = new ArrayList<List<String>>();
		List<ArrayList<String>> list2 = new ArrayList<ArrayList<String>>();
		ArrayList<ArrayList<String>> list3 = new ArrayList<ArrayList<String>>();
	}
	
	/**
	 * The type of variable declaration must match the type you pass to the
	 * actual object type
	 */
	@SuppressWarnings("unused")
	@Test
	public void hierarchyTest() {
		// List<Parent> list = new ArrayList<Child>(); //Compilation error!
		// List<Child> list = new ArrayList<Parent>(); // Compilation error!
		List<Parent> list = new ArrayList<Parent>();
		List<Child> list2 = new ArrayList<Child>();
		Object[] list3 = new Child[3];
		Parent[] list4 = new Child[3];
		List<? extends Parent> list5 = new ArrayList<Child>();
		List<? extends Parent> list6 = new ArrayList<Parent>();
	}

	@Test
	public void polyTest() {
		RoleDoctor doctor = new RoleDoctor();

		GrandParent g = new GrandParent();
		doctor.takeCareRole(g);

		GrandParent[] gs = new Parent[2];
		doctor.takeCareRoles(gs);
		Parent[] ps = new Parent[2];
		doctor.takeCareRoles(ps);

		List<GrandParent> gls = new ArrayList<GrandParent>();
		doctor.takeCareRoles2(gls);
		doctor.takeCareRoles3(gls);
		gls = new ArrayList<GrandParent>();
		doctor.takeCareRoles2(gls);
		doctor.takeCareRoles3(gls);

		List<? extends GrandParent> pls = new ArrayList<Parent>();
		// doctor.takeCareRoles2(pls); // Compilation error
		doctor.takeCareRoles3(pls);

		pls = new ArrayList<GrandParent>();
		doctor.takeCareRoles3(pls);
	}

	public static class RoleDoctor {
		public void takeCareRole(GrandParent p) {
		}

		public void takeCareRoles(GrandParent[] p) {
		}

		public void takeCareRoles2(List<GrandParent> p) {
		}

		public void takeCareRoles3(List<? extends GrandParent> p) {
		}
	}

	public static class Animal {

	}

	public static class Dog extends Animal {

	}

	public static class Cat extends Animal {

	}

	void acceptSubAnimal(List<? extends Animal> animals) {
		// animal.add(new Animal());
		// animals.add(new Dog());
		// animal.add(new Object());
		Animal animal = animals.get(0);
		animals.add(null);
	}

	void processSubAnimal(List<? super Animal> animals) {
		animals.add(new Animal());
		animals.add(new Dog());
		animals.add(new Cat());
		// animal.add(new Object());
		Object obj = animals.get(0);
	}

	/**
	 * It is identical to
	 * {@code void processAnything (List<? extends Object> things)}
	 * 
	 * @param things
	 */
	void acceptAnything(List<?> things) {
		Object obj = things.get(0);
		things.add(null);
		// things.add(new Object());
	}

	void transformAnything(List<? super Object> things) {
		things.add(new Object());
		things.add(new Animal());
		things.add(new Dog());
		things.add(new Cat());
	}

	@Test
	@Ignore
	public void test() {
		List<Animal> animals = new ArrayList<Animal>();
		acceptSubAnimal(animals);
		processSubAnimal(animals);

		List<Dog> dogs = new ArrayList<Dog>();
		acceptSubAnimal(dogs);

		List<? extends Animal> subAnimals = new ArrayList<Animal>();
		acceptSubAnimal(subAnimals);
		// processSuperAnimal(subAnimals);

		List<? super Animal> supAnimals = new ArrayList<Animal>();
		// acceptSubAnimal(supAnimals);
		processSubAnimal(supAnimals);

		List<?> objects = new ArrayList<Animal>();
		// List<?> objects2 = new ArrayList<? extends Animal>();
	}
}
