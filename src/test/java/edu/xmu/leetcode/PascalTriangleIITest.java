package edu.xmu.leetcode;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class PascalTriangleIITest {
	public List<Integer> getRow(int rowIndex) {
		List<Integer> intList = new ArrayList<Integer>(rowIndex);
		if (rowIndex < 0) {
			return intList;
		}
		intList.add(1);
		for (int i = 0; i < rowIndex; i++) {
			intList = generateNext(intList);
		}

		return intList;
	}

	private List<Integer> generateNext(List<Integer> intList) {
		List<Integer> nextList = new ArrayList<Integer>(intList.size() + 1);
		nextList.add(1);
		for (int i = 0; i < intList.size() - 1; i++) {
			nextList.add(i + 1, intList.get(i) + intList.get(i + 1));
		}
		nextList.add(1);

		return nextList;
	}

	@Test
	public void getRowtTest() {
		System.out.println(getRow(0));
	}
}
