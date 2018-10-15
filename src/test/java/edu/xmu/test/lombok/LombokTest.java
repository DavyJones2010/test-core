package edu.xmu.test.lombok;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import lombok.Cleanup;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;
import lombok.Value;
import lombok.experimental.NonFinal;
import lombok.extern.log4j.Log4j;

import org.junit.Test;

//@Log4j
//public class LombokTest {
//	@Test
//	public void doLog() {
//		log.error("Something's wrong here");
//	}
//
//	@Test
//	public void getterSetterTest() {
//		Person p = new Person();
//		p.setId(1);
//		p.setName("Yang");
//
//		assertEquals(1, p.getId());
//		assertEquals("Yang", p.getName());
//		System.out.println(p);
//
//		Person p2 = new Person();
//		p2.setId(1);
//		p2.setName("Yang");
//
//		assertEquals(1, p2.getId());
//		assertEquals("Yang", p2.getName());
//
//		assertEquals(p, p2);
//	}
//
//	@Test
//	public void cleanUpTest() throws IOException {
//		try (InputStream is = new ByteArrayInputStream(new byte[] { 'a', 'b', 'c' })) {
//			int v;
//			while (-1 != (v = is.read())) {
//				System.out.println((char) v);
//			}
//		}
//
//		@Cleanup
//		InputStream is = new ByteArrayInputStream(new byte[] { 'a', 'b', 'c' });
//		int v;
//		while (-1 != (v = is.read())) {
//			System.out.println((char) v);
//		}
//	}
//
//	@Test(expected = NullPointerException.class)
//	public void notNullTest() {
//		someMethod("A");
//		someMethod(null);
//	}
//
//	@Test
//	public void immutableTest() {
//		People p = new People("Yang", 99.9, "AAA");
//		System.out.println(p);
//	}
//
//	private void someMethod(@NonNull String arg) {
//		System.out.println(arg);
//	}
//
//	@EqualsAndHashCode
//	@Getter
//	@Setter
//	@ToString
//	private static class Person {
//		private int id;
//		private String name;
//	}
//
//	@Value
//	private static class People {
//		String name;
//		double score;
//		@NonFinal
//		String optionalProp;
//	}
//
//}
