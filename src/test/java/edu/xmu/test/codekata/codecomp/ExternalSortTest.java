package edu.xmu.test.codekata.codecomp;

import java.io.File;

import org.junit.Ignore;
import org.junit.Test;

import edu.xmu.test.codekata.codecomp.ExternalSort;

public class ExternalSortTest {
	@Ignore
	@Test
	public void sortTest() throws Exception {
		File input = new File("src/test/resources/input-b.csv");
		File output = new File("src/test/resources/output-b.csv");
		File tmpFile = new File("src/test/resources");
		ExternalSort.sort(input, output, tmpFile);
	}
}
