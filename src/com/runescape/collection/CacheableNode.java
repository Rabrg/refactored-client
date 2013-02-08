package com.runescape.collection;

public class CacheableNode extends Node {

	public CacheableNode previousNode;
	protected CacheableNode nextNode;

	public void clear() {
		if (nextNode != null) {
			nextNode.previousNode = previousNode;
			previousNode.nextNode = nextNode;
			previousNode = null;
			nextNode = null;
		}
	}
}
