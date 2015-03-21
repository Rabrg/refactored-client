package com.jagex.runescape.collection;

public class HashTable {
	private final int size;
	private final Node[] cache;

	public HashTable(int size) {
		this.size = size;
		cache = new Node[size];
		for (int nodeId = 0; nodeId < size; nodeId++) {
			Node node = cache[nodeId] = new Node();
			node.prev = node;
			node.next = node;
		}
	}

	public Node get(long nodeId) {
		Node node = cache[(int) (nodeId & size - 1)];
		for (Node nodePrevious_ = node.prev; nodePrevious_ != node; nodePrevious_ = nodePrevious_.prev) {
			if (nodePrevious_.nodeId == nodeId) {
				return nodePrevious_;
			}
		}
		return null;
	}

	public void remove(Node node, long nodeId) {
		if (node.next != null) {
			node.remove();
		}
		Node cachedNode = cache[(int) (nodeId & size - 1)];
		node.next = cachedNode.next;
		node.prev = cachedNode;
		node.next.prev = node;
		node.prev.next = node;
		node.nodeId = nodeId;
	}
}
