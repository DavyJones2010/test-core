package edu.xmu.test.scjp.ch06;

import static org.junit.Assert.assertEquals;

import java.io.Console;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import org.apache.commons.io.IOUtils;
import org.junit.Ignore;
import org.junit.Test;

public class ConsoleTest {
	@Ignore
	@Test
	public void consoleIOTest() {
		Console console = System.console();
		char[] pwd = console.readPassword("%s", "PWD: ");
		console.format("%s", pwd);
	}

	@Test
	public void objectOutputStreamTest() throws ClassNotFoundException {
		ObjectOutputStream oos = null;
		ObjectInputStream ooi = null;
		try {
			oos = new ObjectOutputStream(new FileOutputStream(
					"src/test/resources/dog.ser"));
			oos.writeObject(new Dog("Woofee", 12));
			oos.close();

			ooi = new ObjectInputStream(new FileInputStream(
					"src/test/resources/dog.ser"));
			Dog d = (Dog) ooi.readObject();
			ooi.close();

			assertEquals("Doggy", d.name);
			assertEquals(12, d.age);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			IOUtils.closeQuietly(oos);
		}
	}

	static class Animal {
		String name;

		public Animal() {
			name = "Doggy";
			System.out.println("Animal constructor invoked");
		}

		public Animal(String name) {
			this.name = name;
		}
	}

	static class Dog extends Animal implements Serializable {
		public Dog(String name, int age) {
			super(name);
			this.age = age;
		}

		int age;
	}
}
