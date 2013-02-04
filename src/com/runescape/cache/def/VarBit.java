package com.runescape.cache.def;

import com.runescape.cache.Archive;
import com.runescape.net.Buffer;
import com.runescape.util.SignLink;

/* VarBit - Decompiled by JODE
 * Visit http://jode.sourceforge.net/
 */

public class VarBit {
	public static int anInt734;
	public static VarBit[] aVarBitArray735;
	public String aString736;
	public int anInt737;
	public int anInt738;
	public int anInt739;
	public boolean aBoolean740 = false;
	public int anInt741 = -1;
	public int anInt742;

	public static void method590(int i, Archive archive) {
		do {
			try {
				if (i != 0) {
				}
				Buffer buffer = new Buffer(archive.getFile("varbit.dat"));
				VarBit.anInt734 = buffer.getUnsignedLEShort();
				if (VarBit.aVarBitArray735 == null) {
					VarBit.aVarBitArray735 = new VarBit[VarBit.anInt734];
				}
				for (int i_0_ = 0; i_0_ < VarBit.anInt734; i_0_++) {
					if (VarBit.aVarBitArray735[i_0_] == null) {
						VarBit.aVarBitArray735[i_0_] = new VarBit();
					}
					VarBit.aVarBitArray735[i_0_].method591(buffer, false, i_0_);
					if (VarBit.aVarBitArray735[i_0_].aBoolean740) {
						Varp.aVarpArray746[VarBit.aVarBitArray735[i_0_].anInt737].aBoolean758 = true;
					}
				}
				if (buffer.offset == buffer.payload.length) {
					break;
				}
				System.out.println("varbit load mismatch");
			} catch (RuntimeException runtimeexception) {
				SignLink.reportError("95423, " + i + ", " + archive + ", " + runtimeexception.toString());
				throw new RuntimeException();
			}
			break;
		} while (false);
	}

	public void method591(Buffer buffer, boolean bool, int i) {
		try {
			if (!bool) {
				for (;;) {
					int i_1_ = buffer.getUnsignedByte();
					if (i_1_ == 0) {
						break;
					}
					if (i_1_ == 1) {
						anInt737 = buffer.getUnsignedLEShort();
						anInt738 = buffer.getUnsignedByte();
						anInt739 = buffer.getUnsignedByte();
					} else if (i_1_ == 10) {
						aString736 = buffer.getString();
					} else if (i_1_ == 2) {
						aBoolean740 = true;
					} else if (i_1_ == 3) {
						anInt741 = buffer.getInt();
					} else if (i_1_ == 4) {
						anInt742 = buffer.getInt();
					} else {
						System.out.println("Error unrecognised config code: " + i_1_);
					}
				}
			}
		} catch (RuntimeException runtimeexception) {
			SignLink.reportError("71039, " + buffer + ", " + bool + ", " + i + ", " + runtimeexception.toString());
			throw new RuntimeException();
		}
	}
}
