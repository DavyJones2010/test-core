package edu.xmu.test.codekata.codegenerator;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Before;
import org.junit.Test;

public class CodeGeneratorTest {
	private CodeGenerator codeGenerator;

	@Before
	public void setUp() {
		codeGenerator = new CodeGeneratorImpl();
	}

	@Test
	public void testGenerate() {
		System.out.println(codeGenerator.generateCode(String.class, 5));
	}

	@Test
	public void splitTest() {
		String str = "0, , ";
		String[] split = str.split(",");
		String mobile = "1" + RandomStringUtils.randomNumeric(10);
		System.out.println(mobile);
		System.out.println(split.length);
		System.out.println(split[1]);

	}
}
