package com.runescape.cache.requester;

import com.runescape.collection.CacheableNode;

public class OnDemandNode extends CacheableNode {

	public int type;
	public byte[] buffer;
	public int id;
	public boolean immediate = true;
	protected int cyclesSinceSend;
}
