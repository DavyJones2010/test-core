package edu.xmu.test.effectivejava.sample.other;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Test;

public class Chapter6Overload41Test {
	@Test
	public void goodPracticeTest() throws FileNotFoundException, IOException {
		ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(new File("src/test/resources/data/obj.ser")));
		oos.writeInt(42);
		oos.writeBytes("Hallo world!");
		oos.close();
	}

	@Test
	public void badPracticeTest() {
		Collection<?>[] collections = { new HashSet<String>(), new ArrayList<String>(), new HashMap<String, String>().values() };
		for (Collection<?> c : collections) {
			// Always print "Collections"
			System.out.println(classify(c));
		}
		System.out.println(classify(new HashSet<String>()));
		Collection<String> c = new HashSet<String>();
		System.out.println(classify(c));
	}

	@Test
	public void badPracticeTest2() {
		Object var = new char[] { 'a', 'b', 'c' };
		System.out.println(String.valueOf(var));

		char[] var2 = new char[] { 'a', 'b', 'c' };
		System.out.println(String.valueOf(var2));
	}

	public String classify(Set<?> s) {
		return "Set";
	}

	public String classify(List<?> s) {
		return "List";
	}

	public String classify(Collection<?> s) {
		return "Collection";
	}

}
