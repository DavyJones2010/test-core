package edu.xmu.test.javase.security;

import org.junit.Test;

public class SystemEnvTest {

	/**
	 * <a href=
	 * "http://stackoverflow.com/questions/7054972/java-system-properties-and-environment-variables"
	 * >System properties and Environment variables</a>
	 */
	@Test
	public void getSystemEnvTest() {
		/*
		 * Eclipse: set env at environment tab <br/>
		 * CMD: it is specified in OS level, set name=value
		 */
		System.out.println(System.getenv("username"));
		/*
		 * Eclipse: set system property at Arugments->VM Arguments tab <br/>
		 * CMD: java -Dname=value -Dname=value
		 */
		System.out.println(System.getProperty("username"));
		System.setProperty("username", "davy");
		System.out.println(System.getProperty("username"));
	}

}
