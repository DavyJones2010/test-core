package edu.xmu.test.javase;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;

public class FileCleaner {

	@Test
	public void cleanTest() throws IOException {
		File file = new File("/Users/davyjones/software/dship/duplicate_customer_ids_20151218.csv");

		BufferedReader reader = new BufferedReader(new FileReader(file));
		String line = null;
		List<String> duplicateCid = Lists.newArrayList();
		while (null != (line = reader.readLine())) {
			List<String> list = Splitter.on(',').splitToList(line);
			if (list.size() != 5) {
				System.out.println("error");
				continue;
			}
			if (!NumberUtils.isDigits(list.get(4))) {
				System.out.println("error: " + list.get(4));
				continue;
			}
			duplicateCid.add(list.get(4));
		}
		reader.close();
		System.out.println(duplicateCid.size());
		BufferedWriter fileWriter = new BufferedWriter(
				new FileWriter(new File("/Users/davyjones/software/dship/duplicate_customer_idss.csv")));
		IOUtils.writeLines(duplicateCid, IOUtils.LINE_SEPARATOR_UNIX, fileWriter);
		fileWriter.close();
	}

	@Test
	public void cleanTest2() throws IOException {
		File file = new File("/Users/davyjones/software/dship/bmw_customer_tmp_20151229.csv");

		BufferedReader reader = new BufferedReader(new FileReader(file));
		String line = null;
		int count = 0;
		while (null != (line = reader.readLine())) {
			count++;
			if (StringUtils.isBlank(line) || !NumberUtils.isDigits(line)) {
				System.out.println("error: " + line);
				continue;
			}
		}
		reader.close();
		System.out.println("count: " + count);
	}

	@Test
	public void processTest() throws IOException {
		File file = new File("/Users/davyjones/software/dship/transform_2.log");
		BufferedReader reader = new BufferedReader(new FileReader(file));
		String line = null;
		int count = 0;
		File file2 = new File("/Users/davyjones/software/dship/transform.log");
		BufferedWriter writer = new BufferedWriter(new FileWriter(file2));
		while (null != (line = reader.readLine())) {
			line.trim();
			writer.write(line + "\n");
		}
		reader.close();
		System.out.println("count: " + count);
		writer.close();
	}
}
