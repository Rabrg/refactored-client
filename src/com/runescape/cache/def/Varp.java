package com.runescape.cache.def;

import com.runescape.cache.Archive;
import com.runescape.net.Buffer;
import com.runescape.util.SignLink;

public class Varp {
	private static boolean aBoolean743 = true;
	public static int anInt745;
	public static Varp[] aVarpArray746;
	public static int anInt747;
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

	public static void method592(int i, Archive archive) {
		do {
			try {
				Buffer buffer = new Buffer(archive.getFile("varp.dat"));
				Varp.anInt747 = 0;
				Varp.anInt745 = buffer.getUnsignedLEShort();
				if (Varp.aVarpArray746 == null) {
					Varp.aVarpArray746 = new Varp[Varp.anInt745];
				}
				if (Varp.anIntArray748 == null) {
					Varp.anIntArray748 = new int[Varp.anInt745];
				}
				for (int i_0_ = 0; i_0_ < Varp.anInt745; i_0_++) {
					if (Varp.aVarpArray746[i_0_] == null) {
						Varp.aVarpArray746[i_0_] = new Varp();
					}
					Varp.aVarpArray746[i_0_].method593(buffer, false, i_0_);
				}
				if (i != 0) {
					Varp.aBoolean743 = !Varp.aBoolean743;
				}
				if (buffer.offset == buffer.payload.length) {
					break;
				}
				System.out.println("varptype load mismatch");
			} catch (RuntimeException runtimeexception) {
				SignLink.reportError("14989, " + i + ", " + archive + ", " + runtimeexception.toString());
				throw new RuntimeException();
			}
			break;
		} while (false);
	}

	public void method593(Buffer buffer, boolean bool, int i) {
		try {
			if (bool) {
			}
			for (;;) {
				int i_1_ = buffer.getUnsignedByte();
				if (i_1_ == 0) {
					break;
				}
				if (i_1_ == 1) {
					anInt750 = buffer.getUnsignedByte();
				} else if (i_1_ == 2) {
					anInt751 = buffer.getUnsignedByte();
				} else if (i_1_ == 3) {
					aBoolean752 = true;
					Varp.anIntArray748[Varp.anInt747++] = i;
				} else if (i_1_ == 4) {
					aBoolean753 = false;
				} else if (i_1_ == 5) {
					anInt754 = buffer.getUnsignedLEShort();
				} else if (i_1_ == 6) {
					aBoolean755 = true;
				} else if (i_1_ == 7) {
					anInt756 = buffer.getInt();
				} else if (i_1_ == 8) {
					anInt757 = 1;
					aBoolean758 = true;
				} else if (i_1_ == 10) {
					aString749 = buffer.getString();
				} else if (i_1_ == 11) {
					aBoolean758 = true;
				} else if (i_1_ == 12) {
					anInt759 = buffer.getInt();
				} else if (i_1_ == 13) {
					anInt757 = 2;
				} else {
					System.out.println("Error unrecognised config code: " + i_1_);
				}
			}
		} catch (RuntimeException runtimeexception) {
			SignLink.reportError("43224, " + buffer + ", " + bool + ", " + i + ", " + runtimeexception.toString());
			throw new RuntimeException();
		}
	}
}
