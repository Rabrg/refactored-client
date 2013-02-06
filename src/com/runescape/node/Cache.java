package com.runescape.node;

public class Cache {

	private CacheableNode emptyCachableNode = new CacheableNode();
	private int size;
	private int remaining;
	private HashTable hashTable;
	private Queue queue = new Queue();

	public Cache(int size) {
		this.size = size;
		this.remaining = size;
		this.hashTable = new HashTable(1024);
	}

	public CacheableNode get(long id) {
		CacheableNode cacheablenode = (CacheableNode) hashTable.get(id);
		if (cacheablenode != null) {
			queue.insertHead(cacheablenode);
		}
		return cacheablenode;
	}

	public void put(CacheableNode cacheableNode, long id) {
		if (remaining == 0) {
			CacheableNode cacheableNodeTail = queue.popTail();
			cacheableNodeTail.remove();
			cacheableNodeTail.clear();
			if (cacheableNodeTail == emptyCachableNode) {
				cacheableNodeTail = queue.popTail();
				cacheableNodeTail.remove();
				cacheableNodeTail.clear();
			}
		} else {
			remaining--;
		}
		hashTable.remove(cacheableNode, id);
		queue.insertHead(cacheableNode);
	}

	public void removeAll() {
		while (true) {
			CacheableNode cacheablenode = queue.popTail();
			if (cacheablenode == null) {
				break;
			}
			cacheablenode.remove();
			cacheablenode.clear();
		}
		remaining = size;
	}
}
