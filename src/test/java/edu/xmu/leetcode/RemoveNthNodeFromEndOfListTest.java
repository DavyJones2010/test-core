package edu.xmu.leetcode;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class RemoveNthNodeFromEndOfListTest {
	private static class ListNode {
		int val;
		ListNode next;

		ListNode(int x) {
			val = x;
			next = null;
		}

		@Override
		public String toString() {
			return "ListNode [val=" + val + ", next=" + next + "]";
		}
	}

	private static class CircularFIFOQueue {
		List<ListNode> backList;
		private int size;
		private int currIndex;

		public CircularFIFOQueue(int size) {
			this.backList = new ArrayList<ListNode>(size);
			for (int i = 0; i < size; i++) {
				backList.add(new ListNode(Integer.MAX_VALUE));
			}
			this.size = size;
			this.currIndex = size - 1;
		}

		public void enqueue(ListNode element) {
			if (currIndex > 0) {
				for (int i = currIndex; i < size; i++) {
					backList.set(i - 1, backList.get(i));
				}
				backList.set(size - 1, element);
				currIndex--;
			} else {
				for (int i = 1; i < size; i++) {
					backList.set(i - 1, backList.get(i));
				}
				backList.set(size - 1, element);
			}
		}

		public ListNode peek(int i) {
			return backList.get(i);
		}
	}

	public ListNode removeNthFromEnd(ListNode head, int n) {
		CircularFIFOQueue nodeQueue = new CircularFIFOQueue(n + 1);
		ListNode currentNode = head;
		while (null != currentNode) {
			nodeQueue.enqueue(currentNode);
			currentNode = currentNode.next;
		}
		ListNode prevNode = nodeQueue.peek(0);
		if (prevNode.val == Integer.MAX_VALUE) {
			int i = 1;
			while (prevNode.val == Integer.MAX_VALUE) {
				prevNode = nodeQueue.peek(i);
				i++;
			}
			return prevNode.next;
		} else {
			ListNode nodeToBeRemoved = nodeQueue.peek(1);
			prevNode.next = nodeToBeRemoved.next;
			return head;
		}
	}

	public ListNode removeNthFromEnd_Fast(ListNode head, int n) {
		if (null == head) {
			return head;
		}
		ListNode curr = head;
		ListNode next = head;
		for (int i = 0; i < n; i++) {
			next = next.next;
		}
		if (next == null) {
			return head.next;
		}
		while (next.next != null) {
			curr = curr.next;
			next = next.next;
		}
		curr.next = curr.next.next;
		return head;
	}

	@Test
	public void removeTest() {
		ListNode header = new ListNode(1);
		// header.next = new ListNode(2);
		// header.next.next = new ListNode(3);
		ListNode node = removeNthFromEnd_Fast(header, 1);
		System.out.println(node);
	}
}
