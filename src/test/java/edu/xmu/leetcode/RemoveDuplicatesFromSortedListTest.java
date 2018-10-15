package edu.xmu.leetcode;

import static org.junit.Assert.assertNull;

import org.junit.Test;

public class RemoveDuplicatesFromSortedListTest {
	private static class ListNode {
		int val;
		ListNode next;

		ListNode(int x) {
			val = x;
			next = null;
		}
	}

	@Test
	public void deleteDuplicatesTest() {
		ListNode node = new ListNode(1);
		node.next = new ListNode(1);

		node = deleteDuplicates(node);
		assertNull(node.next);

		node = new ListNode(1);
		node.next = new ListNode(2);
		node.next.next = new ListNode(2);

		node = deleteDuplicates(node);
		assertNull(node.next.next);
	}

	public ListNode deleteDuplicates(ListNode head) {
		if (null == head) {
			return head;
		}
		ListNode currentNode = head;
		ListNode nextNode = null;
		while ((nextNode = currentNode.next) != null) {
			if (nextNode.val == currentNode.val) {
				currentNode.next = nextNode.next;
			} else {
				currentNode = nextNode;
			}
		}

		return head;
	}
}
