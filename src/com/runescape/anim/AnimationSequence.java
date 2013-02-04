package com.runescape.anim;

/* AnimationSequence - Decompiled by JODE
 * Visit http://jode.sourceforge.net/
 */
import com.runescape.cache.Archive;
import com.runescape.net.Buffer;
import com.runescape.util.SignLink;

public class AnimationSequence {
	private boolean aBoolean47 = false;
	public static int anInt48;
	public static AnimationSequence[] animationSequences;
	public int anInt50;
	public int[] animationForFrame;
	public int[] anIntArray52;
	private int[] anIntArray53;
	public int anInt54 = -1;
	public int[] anIntArray55;
	public boolean aBoolean56 = false;
	public int anInt57 = 5;
	public int anInt58 = -1;
	public int anInt59 = -1;
	public int anInt60 = 99;
	public int anInt61 = -1;
	public int priority = -1;
	public int anInt63 = 2;
	public int anInt64;

	public static void method153(int i, Archive archive) {
		do {
			try {
				Buffer buffer = new Buffer(archive.getFile("seq.dat"));
				AnimationSequence.anInt48 = buffer.getUnsignedLEShort();
				if (AnimationSequence.animationSequences == null) {
					AnimationSequence.animationSequences = new AnimationSequence[AnimationSequence.anInt48];
				}
				for (int i_0_ = 0; i_0_ < AnimationSequence.anInt48; i_0_++) {
					if (AnimationSequence.animationSequences[i_0_] == null) {
						AnimationSequence.animationSequences[i_0_] = new AnimationSequence();
					}
					AnimationSequence.animationSequences[i_0_].method155(true, buffer);
				}
				if (i == 0) {
					break;
				}
				for (int i_1_ = 1; i_1_ > 0; i_1_++) {
					/* empty */
				}
			} catch (RuntimeException runtimeexception) {
				SignLink.reportError("95447, " + i + ", " + archive + ", " + runtimeexception.toString());
				throw new RuntimeException();
			}
			break;
		} while (false);
	}

	public int getFrameLength(int i, byte b) {
		try {
			int i_2_ = anIntArray53[i];
			if (b != -39) {
				for (int i_3_ = 1; i_3_ > 0; i_3_++) {
					/* empty */
				}
			}
			if (i_2_ == 0) {
				Animation animation = Animation.getAnimation(animationForFrame[i]);
				if (animation != null) {
					i_2_ = anIntArray53[i] = animation.displayLength;
				}
			}
			if (i_2_ == 0) {
				i_2_ = 1;
			}
			return i_2_;
		} catch (RuntimeException runtimeexception) {
			SignLink.reportError("1006, " + i + ", " + b + ", " + runtimeexception.toString());
			throw new RuntimeException();
		}
	}

	public void method155(boolean bool, Buffer buffer) {
		do {
			try {
				if (!bool) {
					aBoolean47 = !aBoolean47;
				}
				for (;;) {
					int i = buffer.getUnsignedByte();
					if (i == 0) {
						break;
					}
					if (i == 1) {
						anInt50 = buffer.getUnsignedByte();
						animationForFrame = new int[anInt50];
						anIntArray52 = new int[anInt50];
						anIntArray53 = new int[anInt50];
						for (int i_4_ = 0; i_4_ < anInt50; i_4_++) {
							animationForFrame[i_4_] = buffer.getUnsignedLEShort();
							anIntArray52[i_4_] = buffer.getUnsignedLEShort();
							if (anIntArray52[i_4_] == 65535) {
								anIntArray52[i_4_] = -1;
							}
							anIntArray53[i_4_] = buffer.getUnsignedLEShort();
						}
					} else if (i == 2) {
						anInt54 = buffer.getUnsignedLEShort();
					} else if (i == 3) {
						int i_5_ = buffer.getUnsignedByte();
						anIntArray55 = new int[i_5_ + 1];
						for (int i_6_ = 0; i_6_ < i_5_; i_6_++) {
							anIntArray55[i_6_] = buffer.getUnsignedByte();
						}
						anIntArray55[i_5_] = 9999999;
					} else if (i == 4) {
						aBoolean56 = true;
					} else if (i == 5) {
						anInt57 = buffer.getUnsignedByte();
					} else if (i == 6) {
						anInt58 = buffer.getUnsignedLEShort();
					} else if (i == 7) {
						anInt59 = buffer.getUnsignedLEShort();
					} else if (i == 8) {
						anInt60 = buffer.getUnsignedByte();
					} else if (i == 9) {
						anInt61 = buffer.getUnsignedByte();
					} else if (i == 10) {
						priority = buffer.getUnsignedByte();
					} else if (i == 11) {
						anInt63 = buffer.getUnsignedByte();
					} else if (i == 12) {
						anInt64 = buffer.getInt();
					} else {
						System.out.println("Error unrecognised seq config code: " + i);
					}
				}
				if (anInt50 == 0) {
					anInt50 = 1;
					animationForFrame = new int[1];
					animationForFrame[0] = -1;
					anIntArray52 = new int[1];
					anIntArray52[0] = -1;
					anIntArray53 = new int[1];
					anIntArray53[0] = -1;
				}
				if (anInt61 == -1) {
					if (anIntArray55 != null) {
						anInt61 = 2;
					} else {
						anInt61 = 0;
					}
				}
				if (priority != -1) {
					break;
				}
				if (anIntArray55 != null) {
					priority = 2;
				} else {
					priority = 0;
				}
			} catch (RuntimeException runtimeexception) {
				SignLink.reportError("49098, " + bool + ", " + buffer + ", " + runtimeexception.toString());
				throw new RuntimeException();
			}
			break;
		} while (false);
	}
}
