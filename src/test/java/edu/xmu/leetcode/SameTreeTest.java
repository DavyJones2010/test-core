package edu.xmu.leetcode;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class SameTreeTest {
	private static class TreeNode {
		int val;
		TreeNode left;
		TreeNode right;

		TreeNode(int x) {
			val = x;
		}
	}

	public boolean isSameTree(TreeNode p, TreeNode q) {
		boolean isSame = true;

		Map<Integer, List<TreeNode>> pNodeMap = new TreeMap<Integer, List<TreeNode>>();
		getNodes(p, pNodeMap, 1);

		Map<Integer, List<TreeNode>> qNodeMap = new TreeMap<Integer, List<TreeNode>>();
		getNodes(q, qNodeMap, 1);

		if (pNodeMap.size() != qNodeMap.size()) {
			isSame = false;
		} else {
			for (Map.Entry<Integer, List<TreeNode>> pNodeEntry : pNodeMap
					.entrySet()) {
				int levelNo = pNodeEntry.getKey();

				List<TreeNode> pNodes = pNodeEntry.getValue();
				List<TreeNode> qNodes = qNodeMap.get(levelNo);
				if (!isSameForNodeList(pNodes, qNodes)) {
					isSame = false;
					break;
				}
			}
		}

		return isSame;
	}

	private boolean isSameForNodeList(List<TreeNode> pNodes,
			List<TreeNode> qNodes) {
		boolean isSame = true;
		if (pNodes.size() != qNodes.size()) {
			isSame = false;
		} else {
			for (int i = 0; i < pNodes.size(); i++) {
				if (pNodes.get(i).val != qNodes.get(i).val) {
					isSame = false;
					break;
				}
			}
		}
		return isSame;
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
		if (root.val == Integer.MAX_VALUE) {
			return;
		}
		if (null != root.left) {
			getNodes(root.left, nodeMap, currentLevel + 1);
		} else {
			root.left = new TreeNode(Integer.MAX_VALUE);
			getNodes(root.left, nodeMap, currentLevel + 1);
		}
		if (null != root.right) {
			getNodes(root.right, nodeMap, currentLevel + 1);
		} else {
			root.right = new TreeNode(Integer.MAX_VALUE);
			getNodes(root.right, nodeMap, currentLevel + 1);
		}
	}
}
