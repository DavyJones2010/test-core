package edu.xmu.test.ehcache;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import org.junit.Ignore;
import org.junit.Test;

public class SplitFileTest {
	private final static int FILE_LINE_COUNT = 1000;

	@Ignore
	@Test
	public void test() throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader(new File(
				"src/test/resources/input-b.csv")));
		PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(
				new File("src/test/resources/input-sub.csv"))));

		for (int i = 0; i < FILE_LINE_COUNT; i++) {
			writer.println(reader.readLine());
		}
		reader.close();
		writer.close();
	}
}
