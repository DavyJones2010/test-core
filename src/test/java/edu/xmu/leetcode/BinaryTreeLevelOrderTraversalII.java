package edu.xmu.leetcode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.junit.Test;

public class BinaryTreeLevelOrderTraversalII {
	private static class TreeNode {
		int val;
		TreeNode left;
		TreeNode right;

		@SuppressWarnings("unused")
		TreeNode(int x) {
			val = x;
		}
	}

	public List<List<Integer>> levelOrderBottom(TreeNode root) {
		Map<Integer, List<Integer>> levelMap = new TreeMap<Integer, List<Integer>>();
		getValues(root, levelMap, 0);

		List<List<Integer>> levelList = new ArrayList<List<Integer>>();
		for (Map.Entry<Integer, List<Integer>> levelEntry : levelMap.entrySet()) {
			levelList.add(levelEntry.getValue());
		}
		Collections.reverse(levelList);
		return levelList;
	}

	public void getValues(TreeNode root, Map<Integer, List<Integer>> levelMap, int currentLevel) {
		if (root == null) {
			return;
		}
		if (levelMap.containsKey(currentLevel)) {
			levelMap.get(currentLevel).add(root.val);
		} else {
			List<Integer> levelList = new ArrayList<Integer>();
			levelList.add(root.val);
			levelMap.put(currentLevel, levelList);
		}
		if (null != root.left) {
			getValues(root.left, levelMap, currentLevel + 1);
		}
		if (null != root.right) {
			getValues(root.right, levelMap, currentLevel + 1);
		}
	}

	@Test
	public void levelOrderTest() {

	}
}
