package com.runescape.cache.cfg;

import com.runescape.cache.Archive;
import com.runescape.net.Buffer;

public class Varp {

	public static int varpCount;
	public static Varp[] cache;
	public static int currentIndex;
	public int type;

	public static void load(Archive archive) {
		Buffer buffer = new Buffer(archive.getFile("varp.dat"));
		Varp.currentIndex = 0;
		Varp.varpCount = buffer.getUnsignedLEShort();
		if (Varp.cache == null) {
			Varp.cache = new Varp[Varp.varpCount];
		}
		for (int index = 0; index < Varp.varpCount; index++) {
			if (Varp.cache[index] == null) {
				Varp.cache[index] = new Varp();
			}
			Varp.cache[index].loadDefinition(buffer, index);
		}
		if (buffer.offset == buffer.payload.length) {
			return;
		}
		System.out.println("varptype load mismatch");
	}

	public void loadDefinition(Buffer buffer, int index) {
		while (true) {
			int attributeId = buffer.getUnsignedByte();
			if (attributeId == 0) {
				break;
			}
			if (attributeId == 1) {
				buffer.getUnsignedByte(); // dummy
			} else if (attributeId == 2) {
				buffer.getUnsignedByte(); // dummy
			} else if (attributeId == 5) {
				type = buffer.getUnsignedLEShort();
			} else if (attributeId == 7) {
				buffer.getInt(); // dummy
			} else if (attributeId == 10) {
				buffer.getString(); // dummy
			} else {
				System.out.println("Error unrecognised config code: " + attributeId);
			}
		}
	}
}
