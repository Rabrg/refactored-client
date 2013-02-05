package com.runescape.cache.def;

import com.runescape.cache.Archive;
import com.runescape.net.Buffer;

public class VarBit {

	public static int size;
	public static VarBit[] cache;
	public int configId;
	public int leastSignificantBit;
	public int mostSignificantBit;
	public boolean aBoolean740 = false;

	public static void load(Archive archive) {
		Buffer buffer = new Buffer(archive.getFile("varbit.dat"));
		VarBit.size = buffer.getUnsignedLEShort();
		if (VarBit.cache == null) {
			VarBit.cache = new VarBit[VarBit.size];
		}
		for (int index = 0; index < VarBit.size; index++) {
			if (VarBit.cache[index] == null) {
				VarBit.cache[index] = new VarBit();
			}
			VarBit.cache[index].loadDefinition(buffer);
			if (VarBit.cache[index].aBoolean740) {
				Varp.aVarpArray746[VarBit.cache[index].configId].aBoolean758 = true;
			}
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
				System.out.println(configId + ":" + leastSignificantBit + ":" + leastSignificantBit);
			} else if (attributeId == 10) {
				buffer.getString();
			} else if (attributeId == 2) {
			} else if (attributeId == 3) {
				buffer.getInt();
			} else if (attributeId == 4) {
				buffer.getInt();
			} else {
				System.out.println("Error unrecognised config code: " + attributeId);
			}
		}
	}
}
