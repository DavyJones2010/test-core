package edu.xmu.test.javase.io;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import org.junit.Test;

import com.google.common.collect.Lists;

public class CSVGenerator {
	@Test
	public void genereCSV() throws IOException {
		int size = 3000000;
		// 7 + 1 + 5 + 1 + 7 + 10
		List<String> strs = Lists.newArrayListWithExpectedSize(size);
		for (int i = 0; i < size; i++) {
			int id = (int) (size * Math.random());
			String str = "HELLO";
			double val = size * Math.random();
			StringBuffer sb = new StringBuffer();
			strs.add(sb.append(id).append(',').append(str).append(',')
					.append(val).toString());
		}

		PrintWriter bw = new PrintWriter(new BufferedWriter(new FileWriter(
				"src/test/resources/input.csv")));
		for (String str : strs) {
			bw.println(str);
		}
		bw.close();
	}
}