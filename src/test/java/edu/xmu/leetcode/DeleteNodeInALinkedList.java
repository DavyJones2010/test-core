package edu.xmu.leetcode;

import org.junit.Test;

public class DeleteNodeInALinkedList {
	static class ListNode {
		int val;
		ListNode next;

		ListNode(int x) {
			val = x;
		}
	}

	public void deleteNode(ListNode node) {
		node.val = node.next.val;
		node.next = node.next.next;
	}

	@Test
	public void test() {
		ListNode root = new ListNode(1);
		root.next = new ListNode(2);
		root.next.next = new ListNode(3);
		root.next.next.next = new ListNode(4);
		deleteNode(root.next.next);
		System.out.println(root);
	}
}
