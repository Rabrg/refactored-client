package com.jagex.runescape.collection;

/**
 * {@code LinkedList} is a non-generic container used for {@code Node} traversal.
 *
 * This is more of a DoublyLinkedList.
 *
 * @author Jagex
 */
public class LinkedList {
	/**
	 * The beginning of this {@code LinkedList}.
	 */
	public Node head = new Node();

	/**
	 * The current {@code Node} being traversed.
	 */
	private Node current;

	/**
	 * Construct an empty {@code LinkedList}.
	 */
	public LinkedList() {
		head.prev = head;
		head.next = head;
	}

	/**
	 * Append a {@code Node} to the end of the {@code LinkedList}.
	 *
	 * @param node the {@code Node} to append to the {@code LinkedList}.
	 */
	public void insertBack(Node node) {
		if (node.next != null) {
			node.remove();
		}

		node.next = head.next;
		node.prev = head;
		node.next.prev = node;
		node.prev.next = node;
	}

	public void insertFront(Node node) {
		if (node.next != null) {
			node.remove();
		}
		node.next = head;
		node.prev = head.prev;
		node.next.prev = node;
		node.prev.next = node;
	}

	public Node popTail() {
		Node node = head.prev;
		if (node == head) {
			return null;
		}
		node.remove();
		return node;
	}

	public Node getBack() {
		Node node = head.prev;
		if (node == head) {
			current = null;
			return null;
		}
		current = node.prev;
		return node;
	}

	public Node getFront() {
		Node node = head.next;
		if (node == head) {
			current = null;
			return null;
		}
		current = node.next;
		return node;
	}

	public Node getPrevious() {
		Node node = current;
		if (node == head) {
			current = null;
			return null;
		}
		current = node.prev;
		return node;
	}

	public Node getNext() {
		Node node = current;
		if (node == head) {
			current = null;
			return null;
		}
		current = node.next;
		return node;
	}

	public void clear() {
		if (head.prev != head) {
			for (;;) {
				Node node = head.prev;
				if (node == head) {
					break;
				}
				node.remove();
			}
		}
	}
}
