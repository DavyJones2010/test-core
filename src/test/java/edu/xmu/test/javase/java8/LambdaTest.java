package edu.xmu.test.javase.java8;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.function.Consumer;

import org.junit.Test;

public class LambdaTest {

	@Test
	public void simpleLambdaTest() {
		Consumer<String> func = (String e) -> System.out.println(e);
		Arrays.asList("a", "b", "c").forEach(func);
		func = e -> System.out.println(e);
		Arrays.asList("a", "b", "c").forEach(func);
		Arrays.asList("a", "b", "c").forEach((String e) -> System.out.println(e));
		Arrays.asList("a", "b", "c").forEach(e -> System.out.println(e));
	}

	@Test
	public void simpleLambdaTest2() {
		Consumer<String> func = (String e) -> {
			System.out.println(e);
			System.out.println(e);
		};
		Arrays.asList("a", "b", "c").forEach(func);
		func = e -> {
			System.out.println(e);
			System.out.println(e);
		};
		Arrays.asList("a", "b", "c").forEach(func);
	}

	@Test
	public void simpleLambdaTest3() {
		String postfix = ";";
		Consumer<String> func = (String e) -> {
			System.out.println(e + postfix);
		};
		Arrays.asList("a", "b", "c").forEach(func);
		// We cannot assign other value to postfix, it is implicitly final
		// postfix = ",";
	}

	@Test
	public void andThenTest() {
		Consumer<String> c = (x) -> System.out.println(x.toLowerCase());
		c.andThen(e -> System.out.println(e.toUpperCase())).accept("Hallo");
	}

	@Test
	public void functionalInterfaceTest() {
		final UI button = new UI("BUTTON A");
		button.buttonClicked(() -> {
			System.out.println("Button clicked");
		});

		button.buttonClicked(e -> {
			System.out.println(e + " clicked");
		});

		button.buttonClicked((e, g) -> {
			System.out.println(e + " clicked, gender: " + g);
		});

		button.transformName(e -> e.concat(" transformed"));
		assertEquals("BUTTON A transformed", button.getName());

		button.transformName(e -> {
			return e.concat(" altered");
		});
		assertEquals("BUTTON A transformed altered", button.getName());
	}

	@FunctionalInterface
	public static interface MyNoArgsCallback {
		void call();
	}

	@FunctionalInterface
	public static interface MyCallbackWithArgs {
		void call(String name);
	}

	@FunctionalInterface
	public static interface MyCallbackWithMultiArgs {
		void call(String name, String gender);
	}

	@FunctionalInterface
	public static interface MyFuncWithReturnVal {
		String transform(String str);
	}

	public static class UI {
		private String name;

		public UI(String name) {
			this.name = name;
		}

		public void buttonClicked(MyNoArgsCallback callback) {
			callback.call();
		}

		public void buttonClicked(MyCallbackWithArgs callback) {
			callback.call(name);
		}

		public void buttonClicked(MyCallbackWithMultiArgs callback) {
			callback.call(name, "Male");
		}

		public void transformName(MyFuncWithReturnVal func) {
			this.name = func.transform(name);
		}

		public String getName() {
			return name;
		}

	}
}
