package edu.xmu.leetcode;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class GrayCode {

	public List<Integer> grayCode(int n) {
		int c = (int) Math.pow(2, n);
		List<List<Integer>> solution = new ArrayList<List<Integer>>();
		for (int i = 0; i < c; i++) {
			List<Integer> set = new ArrayList<Integer>();
			for (int j = 0; j < c; j++) {
				set.add(j);
			}
			set.remove(Integer.valueOf(i));
			List<List<Integer>> tmpSolution = getSolution(set, i);
			if (!tmpSolution.isEmpty()) {
				return tmpSolution.get(0);
			}
			solution.addAll(tmpSolution);
		}
		return solution.isEmpty() ? new ArrayList<Integer>() : solution.get(0);
	}

	List<List<Integer>> getSolution(List<Integer> numRepo, int initialNum) {
		List<List<Integer>> solutions = new ArrayList<List<Integer>>();
		for (int i : numRepo) {
			if (isAdjCode(initialNum, i)) {
				List<List<Integer>> solution = new ArrayList<List<Integer>>();
				List<Integer> numRepoBak = new ArrayList<Integer>(numRepo);
				numRepoBak.remove(Integer.valueOf(i));
				if (numRepoBak.isEmpty()) {
					List<Integer> list = new ArrayList<Integer>();
					list.add(initialNum);
					list.add(i);
					solution.add(list);
					return solution;
				} else {
					List<List<Integer>> tmpSolution = getSolution(numRepoBak, i);
					if (!tmpSolution.isEmpty()) {
						for (List<Integer> a : tmpSolution) {
							a.add(0, initialNum);
						}
						solution.addAll(tmpSolution);
					}
					return solution;
				}
				// solutions.addAll(solution);
			}
		}
		return solutions;
	}

	boolean isAdjCode(int i, int j) {
		int res = i ^ j;
		return 0 == (res & (res - 1));
	}

	@Test
	public void test() {
		System.out.println(grayCode(1));
	}
}
