package edu.xmu.test.guava.basic;

import org.apache.log4j.Logger;
import org.junit.Test;

import com.google.common.base.Predicate;

public class PredicateTest {
	private Logger logger = Logger.getLogger(PredicateTest.class);

	@Test
	public void applyTest() {
		String input = "Davy Jones";
		boolean containsKeyWords = new Predicate<String>() {
			public boolean apply(String input) {
				return input.contains("Davy");
			}
		}.apply(input);
		logger.info(containsKeyWords);
	}
}
