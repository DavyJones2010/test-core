package edu.xmu.test.effectivejava.sample.object;

import org.junit.Test;

public class Chapter2SingletonAndNonInstantiable3Test {
	/**
	 * When should we use singleton and when should use non-instantiable?
	 */
	@Test
	public void singletonBestPracticeTest() {
		// HttpServlet is singleton & heavy-weight objects(SessionFactory)
		// enum
	}

	@Test
	public void singletonBadPracticeTest() {
		// org.apache.commons.io.FileUtils for spring injection purpose
	}

}
