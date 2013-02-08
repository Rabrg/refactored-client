package com.runescape.collection;

/* Node - Decompiled by JODE
 * Visit http://jode.sourceforge.net/
 */

public class Node {
	public long nodeId;
	public Node previousNode;
	protected Node nextNode;

	public void remove() {
		if (nextNode != null) {
			nextNode.previousNode = previousNode;
			previousNode.nextNode = nextNode;
			previousNode = null;
			nextNode = null;
		}
	}
}
