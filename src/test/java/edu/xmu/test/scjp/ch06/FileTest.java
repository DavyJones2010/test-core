package edu.xmu.test.scjp.ch06;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.apache.commons.io.IOUtils;
import org.junit.Test;

public class FileTest {
	@Test
	public void ioTest() {
		char[] in = new char[128];
		StringBuilder sb = new StringBuilder();
		File file = new File("src/test/resources/ioTest.txt");
		FileWriter writer = null;
		FileReader reader = null;
		try {
			writer = new FileWriter(file);
			writer.write("Hello, world");
			writer.flush();
			writer.close();

			reader = new FileReader(file);
			int c = 0;
			while (-1 != (c = reader.read(in))) {
				sb.append(in, 0, c);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			IOUtils.closeQuietly(writer);
			IOUtils.closeQuietly(reader);
		}
		assertEquals("Hello, world", sb.toString());
	}
}
