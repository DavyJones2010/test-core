package edu.xmu.test.scjp.ch06;

import java.text.NumberFormat;
import java.util.Locale;

import org.junit.Test;

public class NumberFormatTest {
	@Test
	public void formatTest() {
		float f1 = 123.4567f;

		System.out.println(NumberFormat.getInstance(Locale.FRENCH).format(f1));
		System.out.println(NumberFormat.getInstance(Locale.CHINESE).format(f1));

		System.out.println(NumberFormat.getCurrencyInstance(Locale.FRENCH)
				.format(f1));
		System.out.println(NumberFormat.getCurrencyInstance(Locale.CHINESE)
				.format(f1));
	}
}
