package com.runescape.collection;

public class LinkedList {

	public Node head = new Node();
	private Node current;

	public LinkedList() {
		head.previousNode = head;
		head.nextNode = head;
	}

	public void insertBack(Node node) {
		if (node.nextNode != null) {
			node.remove();
		}
		node.nextNode = head.nextNode;
		node.previousNode = head;
		node.nextNode.previousNode = node;
		node.previousNode.nextNode = node;
	}

	public void insertFront(Node node) {
		if (node.nextNode != null) {
			node.remove();
		}
		node.nextNode = head;
		node.previousNode = head.previousNode;
		node.nextNode.previousNode = node;
		node.previousNode.nextNode = node;
	}

	public Node popTail() {
		Node node = head.previousNode;
		if (node == head) {
			return null;
		}
		node.remove();
		return node;
	}

	public Node getBack() {
		Node node = head.previousNode;
		if (node == head) {
			current = null;
			return null;
		}
		current = node.previousNode;
		return node;
	}

	public Node getFront() {
		Node node = head.nextNode;
		if (node == head) {
			current = null;
			return null;
		}
		current = node.nextNode;
		return node;
	}

	public Node getPrevious() {
		Node node = current;
		if (node == head) {
			current = null;
			return null;
		}
		current = node.previousNode;
		return node;
	}

	public Node getNext() {
		Node node = current;
		if (node == head) {
			current = null;
			return null;
		}
		current = node.nextNode;
		return node;
	}

	public void clear() {
		if (head.previousNode != head) {
			for (;;) {
				Node node = head.previousNode;
				if (node == head) {
					break;
				}
				node.remove();
			}
		}
	}
}
