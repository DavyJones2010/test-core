package edu.xmu.leetcode;

import java.util.ArrayList;
import java.util.List;

public class PascalTriangleTest {
	public List<List<Integer>> generate(int numRows) {
		List<List<Integer>> list = new ArrayList<List<Integer>>();
		if (numRows <= 0) {
			return list;
		}

		List<Integer> intList = new ArrayList<Integer>();
		intList.add(1);
		list.add(intList);
		for (int i = 1; i < numRows; i++) {
			intList = generateNext(intList);
			list.add(intList);
		}

		return list;
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

}
