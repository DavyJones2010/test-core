package edu.xmu.leetcode;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class MaxDepthOfBinaryTreeTest {
	static class TreeNode {
		int val;
		TreeNode left;
		TreeNode right;

		TreeNode(int x) {
			val = x;
		}
	}

	public int maxDepth(TreeNode root) {
		if (null == root) {
			return 0;
		}
		Map<Integer, List<TreeNode>> nodeMap = new TreeMap<Integer, List<TreeNode>>();
		getNodes(root, nodeMap, 1);
		int max = 0;
		for (int i : nodeMap.keySet()) {
			if (i > max) {
				max = i;
			}
		}
		return max;
	}

	public void getNodes(TreeNode root, Map<Integer, List<TreeNode>> nodeMap,
			int currentLevel) {
		if (root == null) {
			return;
		}
		if (nodeMap.containsKey(currentLevel)) {
			nodeMap.get(currentLevel).add(root);
		} else {
			List<TreeNode> nodeList = new ArrayList<TreeNode>();
			nodeList.add(root);
			nodeMap.put(currentLevel, nodeList);
		}
		if (null != root.left) {
			getNodes(root.left, nodeMap, currentLevel + 1);
		}
		if (null != root.right) {
			getNodes(root.right, nodeMap, currentLevel + 1);
		}
	}
}
