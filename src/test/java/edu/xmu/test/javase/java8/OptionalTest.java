package edu.xmu.test.javase.java8;

import java.util.Optional;

import org.junit.Test;

/**
 * Optional is just a container, it can hold a value of some type T or just be
 * null
 *
 */
public class OptionalTest {
	@Test
	public void optionalTest() {
		Optional<String> fullName = Optional.ofNullable(null);
		System.out.println("Full name is : " + fullName.isPresent());
		System.out.println("Full name is : " + fullName.orElse("Yang"));
		System.out.println("Full name is : " + fullName.orElseGet(() -> "Yang"));

		fullName = Optional.ofNullable("Zhang");
		System.out.println("Full name is : " + fullName.isPresent());
		System.out.println("Full name is : " + fullName.orElse("Yang"));
		System.out.println("Full name is : " + fullName.orElseGet(() -> "Yang"));
	}
}
