package com.runescape.collection;

public class Queue {

	public CacheableNode head = new CacheableNode();
	private CacheableNode current;

	public Queue() {
		head.previousNode = head;
		head.nextNode = head;
	}

	public void insertHead(CacheableNode cacheableNode) {
		if (cacheableNode.nextNode != null) {
			cacheableNode.clear();
		}
		cacheableNode.nextNode = head.nextNode;
		cacheableNode.previousNode = head;
		cacheableNode.nextNode.previousNode = cacheableNode;
		cacheableNode.previousNode.nextNode = cacheableNode;
	}

	public CacheableNode popTail() {
		CacheableNode cacheableNode = head.previousNode;
		if (cacheableNode == head) {
			return null;
		}
		cacheableNode.clear();
		return cacheableNode;
	}

	public CacheableNode reverseGetFirst() {
		CacheableNode cacheableNode = head.previousNode;
		if (cacheableNode == head) {
			current = null;
			return null;
		}
		current = cacheableNode.previousNode;
		return cacheableNode;
	}

	public CacheableNode reverseGetNext() {
		CacheableNode cacheableNode = current;
		if (cacheableNode == head) {
			current = null;
			return null;
		}
		current = cacheableNode.previousNode;
		return cacheableNode;
	}

	public int getNodeCount() {
		int nodeCount = 0;
		for (CacheableNode cacheablenode = head.previousNode; cacheablenode != head; cacheablenode = cacheablenode.previousNode) {
			nodeCount++;
		}
		return nodeCount;
	}
}
