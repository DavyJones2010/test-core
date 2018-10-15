package edu.xmu.test.javase;

import java.util.List;

import org.junit.Test;

import com.google.common.collect.Lists;

public class ListTest {

	@Test
	public void partitionTest() {
		int taskCount = 1;
		List<String> list = Lists.newArrayList("A", "B", "C", "D", "E");
		List<List<String>> partition = Lists.partition(list, (int) Math.ceil(list.size() / (double) taskCount));
		System.out.println(partition.size());
		System.out.println(partition);
	}
}
