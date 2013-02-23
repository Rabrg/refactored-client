package com.runescape.collection;

public class HashTable {

	private final int size;
	private final Node[] cache;

	public HashTable(int size) {
		this.size = size;
		cache = new Node[size];
		for (int nodeId = 0; nodeId < size; nodeId++) {
			Node node = cache[nodeId] = new Node();
			node.previousNode = node;
			node.nextNode = node;
		}
	}

	public Node get(long nodeId) {
		Node node = cache[(int) (nodeId & size - 1)];
		for (Node nodePrevious_ = node.previousNode; nodePrevious_ != node; nodePrevious_ = nodePrevious_.previousNode) {
			if (nodePrevious_.nodeId == nodeId) {
				return nodePrevious_;
			}
		}
		return null;
	}

	public void remove(Node node, long nodeId) {
		if (node.nextNode != null) {
			node.remove();
		}
		Node cachedNode = cache[(int) (nodeId & size - 1)];
		node.nextNode = cachedNode.nextNode;
		node.previousNode = cachedNode;
		node.nextNode.previousNode = node;
		node.previousNode.nextNode = node;
		node.nodeId = nodeId;
	}
}
