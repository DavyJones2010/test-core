package edu.xmu.test.scjp.ch07;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import edu.xmu.test.scjp.ch07.GenericPolymorphismTest.Animal;
import edu.xmu.test.scjp.ch07.GenericPolymorphismTest.Cat;
import edu.xmu.test.scjp.ch07.GenericPolymorphismTest.Dog;

public class GenericContainerTest {
	static class Holder<E> {
		List<E> es = new ArrayList<E>();

		public void add(E e) {
			es.add(e);
		}

		public E remove(int i) {
			return es.remove(i);
		}
	}

	/**
	 * The following declaration just doesn't work!
	 * 
	 * <pre>
	 * static class SubHolder2<? extends Animal> {
	 * 		public ? remove(int i){
	 * 			//...
	 * 		}
	 * 	}
	 * </pre>
	 **/
	static class SubHolder<E extends Animal> {
		List<E> es = new ArrayList<E>();

		public void add(E e) {
			es.add(e);
		}

		public E remove(int i) {
			return es.remove(i);
		}
	}

	// static class SuperHolder<E super Dog>{ //Compilation error
	// }

	<T> void genericMethod(T t) {
	}

	<T extends Animal> void genericMethod2(T t) {
	}

	// <T super Dog> void genericMethod3(T t){ //Compilation error
	// }
	<T> T genericMethod4(T t) {
		return t;
	}

	@Test
	public void genericTest() {
		Holder<String> strHolder = new Holder<String>();
		strHolder.add("");
		Holder<Animal> animalHolder = new Holder<Animal>();
		animalHolder.add(new Animal());
		animalHolder.add(new Dog());
		animalHolder.add(new Cat());

		Animal animal = animalHolder.remove(0);
	}

	@Test
	public void genericTest2() {
		SubHolder<Animal> animalHolder = new SubHolder<Animal>();
		animalHolder.add(new Animal());
		animalHolder.add(new Dog());
		animalHolder.add(new Cat());

		SubHolder<Dog> animalHolder2 = new SubHolder<Dog>();
		// animalHolder2.add(new Animal());
		animalHolder2.add(new Dog());
		// animalHolder2.add(new Cat());

		Dog dog = animalHolder2.remove(0);
	}

	@Test
	public void genericMethodTest() {
		genericMethod(new Object());
		genericMethod(new Animal());
		genericMethod("Hallo");

		// genericMethod2(new Object());
		genericMethod2(new Animal());
		genericMethod2(new Dog());

		Object obj = genericMethod4(new Object());
		Animal animal = genericMethod4(new Dog());
	}

}
