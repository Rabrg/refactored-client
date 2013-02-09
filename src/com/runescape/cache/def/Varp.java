package com.runescape.cache.def;

import com.runescape.cache.Archive;
import com.runescape.net.Buffer;
import com.runescape.util.SignLink;

public class Varp {
	private static boolean aBoolean743 = true;
	public static int varpCount;
	public static Varp[] cache;
	public static int currentIndex;
	public static int[] anIntArray748;
	public String aString749;
	public int anInt750;
	public int anInt751;
	public boolean aBoolean752 = false;
	public boolean aBoolean753 = true;
	public int anInt754;
	public boolean aBoolean755 = false;
	public int anInt756;
	public int anInt757;
	public boolean aBoolean758 = false;
	public int anInt759 = -1;

	public static void load(Archive archive) {
				Buffer buffer = new Buffer(archive.getFile("varp.dat"));
				Varp.currentIndex = 0;
				Varp.varpCount = buffer.getUnsignedLEShort();
				if (Varp.cache == null) {
					Varp.cache = new Varp[Varp.varpCount];
				}
				if (Varp.anIntArray748 == null) {
					Varp.anIntArray748 = new int[Varp.varpCount];
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
					anInt750 = buffer.getUnsignedByte();
				} else if (attributeId == 2) {
					anInt751 = buffer.getUnsignedByte();
				} else if (attributeId == 3) {
					aBoolean752 = true;
					Varp.anIntArray748[Varp.currentIndex++] = index;
				} else if (attributeId == 4) {
					aBoolean753 = false;
				} else if (attributeId == 5) {
					anInt754 = buffer.getUnsignedLEShort();
				} else if (attributeId == 6) {
					aBoolean755 = true;
				} else if (attributeId == 7) {
					anInt756 = buffer.getInt();
				} else if (attributeId == 8) {
					anInt757 = 1;
					aBoolean758 = true;
				} else if (attributeId == 10) {
					aString749 = buffer.getString();
				} else if (attributeId == 11) {
					aBoolean758 = true;
				} else if (attributeId == 12) {
					anInt759 = buffer.getInt();
				} else if (attributeId == 13) {
					anInt757 = 2;
				} else {
					System.out.println("Error unrecognised config code: " + attributeId);
				}
			}
	}
}
