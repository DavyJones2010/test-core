package edu.xmu.test.algorithm.sorting;

import java.util.List;

import com.google.common.collect.Lists;

public class TreeSelectionSort {
	public static List<Integer> treeSelectionSort(List<Integer> intList) {
		BinarySelectionTreeNode rootNode = buildTree(intList);
		List<Integer> orderedIntList = Lists.newArrayListWithCapacity(intList.size());
		int minValue;
		for (int i = 0; i < intList.size(); i++) {
			rootNode.reElectMinValue();

			minValue = rootNode.getValue();
			orderedIntList.add(minValue);

			rootNode.removeMinValue(minValue);
		}
		return orderedIntList;
	}

	public static BinarySelectionTreeNode buildTree(List<Integer> intList) {
		List<BinarySelectionTreeNode> leafNodes = Lists.newArrayListWithExpectedSize(intList.size());

		for (int value : intList) {
			BinarySelectionTreeNode leafNode = new BinarySelectionTreeNode();
			leafNode.setValue(value);
			leafNodes.add(leafNode);
		}

		List<BinarySelectionTreeNode> rootNode = buildFromNodes(leafNodes);
		return rootNode.get(0);
	}

	private static List<BinarySelectionTreeNode> buildFromNodes(List<BinarySelectionTreeNode> nodes) {
		if (nodes.size() <= 1) {
			return nodes;
		}

		if (nodes.size() % 2 != 0) {
			nodes.add(new BinarySelectionTreeNode());
		}
		List<BinarySelectionTreeNode> mergedNodes = Lists.newArrayListWithCapacity(nodes.size() / 2);

		for (int i = 0; i < nodes.size(); i += 2) {
			BinarySelectionTreeNode mergedNode = new BinarySelectionTreeNode();
			BinarySelectionTreeNode lChildNode = nodes.get(i);
			BinarySelectionTreeNode rChildNode = nodes.get(i + 1);

			mergedNode.setlChild(lChildNode);
			mergedNode.setrChild(rChildNode);
			mergedNodes.add(mergedNode);
		}
		return buildFromNodes(mergedNodes);
	}

	public static class BinarySelectionTreeNode {
		private int value = Integer.MAX_VALUE;
		private BinarySelectionTreeNode lChild;
		private BinarySelectionTreeNode rChild;

		public void removeMinValue(int minValue) {
			if (null == lChild && null == rChild) {
				if (minValue == this.value) {
					this.value = Integer.MAX_VALUE;
				}
			} else {
				if (this.value == minValue) {
					this.value = Integer.MAX_VALUE;
					lChild.removeMinValue(minValue);
					rChild.removeMinValue(minValue);
				}
			}
		}

		public int reElectMinValue() {
			int minValue = Integer.MAX_VALUE;
			if (null == lChild && null == rChild) {
				minValue = this.value;
			} else {
				int lMinValue = lChild == null ? Integer.MAX_VALUE : lChild.reElectMinValue();
				int rMinValue = rChild == null ? Integer.MAX_VALUE : rChild.reElectMinValue();
				minValue = lMinValue < rMinValue ? lMinValue : rMinValue;
			}
			this.value = minValue;
			return minValue;
		}

		public int getValue() {
			return value;
		}

		public void setValue(int value) {
			this.value = value;
		}

		public BinarySelectionTreeNode getlChild() {
			return lChild;
		}

		public void setlChild(BinarySelectionTreeNode lChild) {
			this.lChild = lChild;
		}

		public BinarySelectionTreeNode getrChild() {
			return rChild;
		}

		public void setrChild(BinarySelectionTreeNode rChild) {
			this.rChild = rChild;
		}

	}
}
