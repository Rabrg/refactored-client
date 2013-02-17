package com.runescape.cache.cfg;

import com.runescape.cache.Archive;
import com.runescape.net.Buffer;

public class VarBit {

	public static int count;
	public static VarBit[] cache;
	public int configId;
	public int leastSignificantBit;
	public int mostSignificantBit;

	public static void load(Archive archive) {
		Buffer buffer = new Buffer(archive.getFile("varbit.dat"));
		VarBit.count = buffer.getUnsignedLEShort();
		if (VarBit.cache == null) {
			VarBit.cache = new VarBit[VarBit.count];
		}
		for (int index = 0; index < VarBit.count; index++) {
			if (VarBit.cache[index] == null) {
				VarBit.cache[index] = new VarBit();
			}
			VarBit.cache[index].loadDefinition(buffer);
		}
		if (buffer.offset == buffer.payload.length) {
			return;
		}
		System.out.println("varbit load mismatch");
	}

	public void loadDefinition(Buffer buffer) {
		while (true) {
			int attributeId = buffer.getUnsignedByte();
			if (attributeId == 0) {
				break;
			}
			if (attributeId == 1) {
				configId = buffer.getUnsignedLEShort();
				leastSignificantBit = buffer.getUnsignedByte();
				mostSignificantBit = buffer.getUnsignedByte();
				System.out.println(leastSignificantBit + ":" + mostSignificantBit);
			} else if (attributeId == 10) {
				buffer.getString(); // dummy
			} else if (attributeId == 3) {
				buffer.getInt(); // dummy
			} else if (attributeId == 4) {
				buffer.getInt(); // dummy
			} else {
				System.out.println("Error unrecognised config code: " + attributeId);
			}
		}
	}
}
