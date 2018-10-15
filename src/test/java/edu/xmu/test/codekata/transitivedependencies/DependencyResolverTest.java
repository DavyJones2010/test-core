package edu.xmu.test.codekata.transitivedependencies;

import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;

public class DependencyResolverTest {
	DependencyResolver app;
	Multimap<String, String> inputDependency;
	Multimap<String, String> inputDependency2;

	@Before
	public void setUp() {
		app = new DependencyResolver();
		inputDependency = ArrayListMultimap.create();
		inputDependency.put("A", "B");
		inputDependency.put("A", "C");
		inputDependency.put("B", "C");
		inputDependency.put("B", "E");
		inputDependency.put("C", "G");
		inputDependency.put("D", "A");
		inputDependency.put("D", "F");
		inputDependency.put("E", "F");
		inputDependency.put("F", "H");

		inputDependency2 = ArrayListMultimap.create();
		inputDependency2.put("A", "B");
		inputDependency2.put("B", "A");
//		inputDependency2.put("C", "A");
	}

	@Test
	public void resolveTreeMapTest() {
		app.resolveTreeMap(inputDependency);
	}

	@Test
	public void transformToMultimapTest() {
		System.out.println(app.transformToMultimap(app.resolveTreeMap(inputDependency2)));
	}
}
