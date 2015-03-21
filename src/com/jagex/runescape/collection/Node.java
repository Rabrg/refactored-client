package com.jagex.runescape.collection;

/* Node - Decompiled by JODE
 * Visit http://jode.sourceforge.net/
 */

/**
 * A {@code Node} is
 *
 * @author Jagex
 */
public class Node {
	/**
	 * A unique identifier for this {@code Node} -- I thinkS
	 */
	public long nodeId;

	/**
	 * The previous {@code Node} linked to by this {@code Node}.
	 */
	public Node prev;

	/**
	 * The prev {@code Node} linked to by this {@code Node}.
	 */
	protected Node next;

	/**
	 * Detach this {@code Node} from it's previous and prev {@code Node}.
	 *
	 * I suppose another name for this function could be "unlink".
	 */
	public void remove() {
		if (next != null) {
			next.prev = prev;
			prev.next = next;
			prev = null;
			next = null;
		}
	}
}
