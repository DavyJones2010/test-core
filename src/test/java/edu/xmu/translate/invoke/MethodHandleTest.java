package edu.xmu.translate.invoke;

import static org.junit.Assert.assertEquals;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

import org.junit.Test;

public class MethodHandleTest {
	@Test
	public void generateMethodTypesTest() {
		// String.length():int;
		MethodType mt1 = MethodType.methodType(int.class);
		// String.concat(String):String;
		MethodType mt2 = MethodType.methodType(String.class, String.class);
		// String.getChars(int, int, char[], int):void;
		MethodType.methodType(void.class, int.class, int.class, char[].class,
				int.class);
		// String.charAt(int):char; using mt1's param list
		MethodType.methodType(char.class, mt1);
		// String.startsWith(String regex):boolean; using mt2's param list
		MethodType.methodType(boolean.class, mt2);
	}

	@Test
	public void genericMethodTypeTest() {
		MethodType mt1 = MethodType.genericMethodType(3);
		MethodType mt2 = MethodType.genericMethodType(2, true);
	}

	@Test
	public void generateMethodTypesFromDescriptor() {
		// (String):String
		MethodType.fromMethodDescriptorString(
				"(Ljava/lang/String;)Ljava/lang/String;", this.getClass()
						.getClassLoader());
	}
	@Test
	public void modifyMethodTypeTest() {
		MethodType methodType = MethodType.fromMethodDescriptorString(
				"(Ljava/lang/String;)Ljava/lang/String;", this.getClass()
						.getClassLoader());
		methodType = methodType.appendParameterTypes(String.class);
		methodType = methodType.changeParameterType(1, int.class);
		methodType = methodType.changeReturnType(int.class);
		methodType = methodType.dropParameterTypes(0, 1);
	}

	@Test
	public void invokeWithArgsTest() throws Throwable {
		MethodHandle methodHandle = MethodHandles.lookup().findVirtual(
				String.class, "substring",
				MethodType.methodType(String.class, int.class, int.class));
		assertEquals("Hel",
				(String) methodHandle.invokeWithArguments("Hello world", 0, 3));

		methodHandle = MethodHandles.lookup().findStatic(String.class,
				"valueOf", MethodType.methodType(String.class, int.class));
		assertEquals("3", (String) methodHandle.invokeWithArguments(3));
	}

	@Test
	public void invokeWithArgsTest2() throws Throwable {
		MethodHandle methodHandle = MethodHandles.lookup().findVirtual(
				String.class, "substring",
				MethodType.methodType(String.class, int.class, int.class));
		assertEquals("Hel",
				(String) methodHandle.invokeWithArguments("Hello world", 0, 3));
	}

	@Test
	public void bindToTest() throws Throwable {
		MethodHandle methodHandle = MethodHandles.lookup().findVirtual(
				String.class, "indexOf",
				MethodType.methodType(int.class, String.class, int.class));

		methodHandle = methodHandle.bindTo("Hello").bindTo("l");
		assertEquals(2, (int) methodHandle.invoke(0));
	}

	@Test(expected = IllegalAccessException.class)
	public void findSpecialTest() throws Throwable {
		MethodHandle methodHandle = MethodHandles.lookup().findSpecial(
				String.class, "lastIndexOfSupplementary",
				MethodType.methodType(int.class, int.class, int.class),
				MethodHandleTest.class);

		assertEquals(2, (int) methodHandle.invoke("Hello", 1, 2));
	}

	@Test
	public void getMethodHandleFromReflect() throws Throwable {
		Constructor<String> constructor = String.class
				.getConstructor(byte[].class);
		MethodHandle methodHandle = MethodHandles.lookup()
				.unreflectConstructor(constructor);
		assertEquals("Hello World!",
				(String) methodHandle.invoke("Hello World!".getBytes()));

		Method method = String.class.getMethod("substring", new Class[]{
				int.class, int.class});
		methodHandle = MethodHandles.lookup().unreflect(method);
		assertEquals("el", methodHandle.invoke("Hello World!", 1, 3));
	}

	@Test
	public void filterArgsTest() throws Throwable {
		MethodHandle lengthMethodHandle = MethodHandles.lookup().findVirtual(
				String.class, "length", MethodType.methodType(int.class));

		MethodHandle maxMethodHandle = MethodHandles.lookup().findStatic(
				Math.class, "max",
				MethodType.methodType(int.class, int.class, int.class));

		MethodHandle methodHandle = MethodHandles.filterArguments(
				maxMethodHandle, 0, lengthMethodHandle, lengthMethodHandle);
		assertEquals(11, (int) methodHandle.invoke("Hello", "Hello World"));

		methodHandle = MethodHandles.filterArguments(maxMethodHandle, 1,
				lengthMethodHandle);
		assertEquals(13, (int) methodHandle.invoke(13, "Hello World"));

		methodHandle = MethodHandles.filterArguments(maxMethodHandle, 0, null,
				lengthMethodHandle);
		assertEquals(13, (int) methodHandle.invoke(13, "Hello World"));

		MethodHandle minMethodHandle = MethodHandles.lookup().findStatic(
				Math.class, "min",
				MethodType.methodType(int.class, int.class, int.class));

		methodHandle = MethodHandles.filterArguments(minMethodHandle, 0,
				lengthMethodHandle, lengthMethodHandle);
		assertEquals(5, (int) methodHandle.invoke("Hello", "Hello World"));
	}
}
