package com.runescape.media;

import com.runescape.net.Buffer;
import com.runescape.util.SignLink;

public class Animation {
	private static Animation[] cache;
	public int displayLength;
	public Skins animationSkins;
	public int stepCount;
	public int[] opcodeTable;
	public int[] modifier1;
	public int[] modifier2;
	public int[] modifier3;
	private static boolean[] aBooleanArray45;

	public static void method148(int i) {
		Animation.cache = new Animation[i + 1];
		Animation.aBooleanArray45 = new boolean[i + 1];
		for (int i_0_ = 0; i_0_ < i + 1; i_0_++) {
			Animation.aBooleanArray45[i_0_] = true;
		}
	}

	public static void method149(byte[] bs, boolean bool) {
		try {
			Buffer buffer = new Buffer(bs);
			buffer.offset = bs.length - 8;
			int i = buffer.getUnsignedLEShort();
			int i_1_ = buffer.getUnsignedLEShort();
			int i_2_ = buffer.getUnsignedLEShort();
			int i_3_ = buffer.getUnsignedLEShort();
			int i_4_ = 0;
			Buffer buffer_5_ = new Buffer(bs);
			buffer_5_.offset = i_4_;
			i_4_ += i + 2;
			Buffer buffer_6_ = new Buffer(bs);
			buffer_6_.offset = i_4_;
			i_4_ += i_1_;
			Buffer buffer_7_ = new Buffer(bs);
			buffer_7_.offset = i_4_;
			i_4_ += i_2_;
			Buffer buffer_8_ = new Buffer(bs);
			buffer_8_.offset = i_4_;
			i_4_ += i_3_;
			Buffer buffer_9_ = new Buffer(bs);
			buffer_9_.offset = i_4_;
			if (bool) {
				for (int i_10_ = 1; i_10_ > 0; i_10_++) {
					/* empty */
				}
			}
			Skins skins = new Skins(buffer_9_);
			int animationAmount = buffer_5_.getUnsignedLEShort();
			int[] is = new int[500];
			int[] is_12_ = new int[500];
			int[] is_13_ = new int[500];
			int[] is_14_ = new int[500];
			for (int animationCounter = 0; animationCounter < animationAmount; animationCounter++) {
				int animationId = buffer_5_.getUnsignedLEShort();
				Animation animation = Animation.cache[animationId] = new Animation();
				animation.displayLength = buffer_8_.getUnsignedByte();
				animation.animationSkins = skins;
				int i_17_ = buffer_5_.getUnsignedByte();
				int i_18_ = -1;
				int stepCount = 0;
				for (int i_20_ = 0; i_20_ < i_17_; i_20_++) {
					int i_21_ = buffer_6_.getUnsignedByte();
					if (i_21_ > 0) {
						if (skins.opcodes[i_20_] != 0) {
							for (int i_22_ = i_20_ - 1; i_22_ > i_18_; i_22_--) {
								if (skins.opcodes[i_22_] == 0) {
									is[stepCount] = i_22_;
									is_12_[stepCount] = 0;
									is_13_[stepCount] = 0;
									is_14_[stepCount] = 0;
									stepCount++;
									break;
								}
							}
						}
						is[stepCount] = i_20_;
						int i_23_ = 0;
						if (skins.opcodes[i_20_] == 3) {
							i_23_ = 128;
						}
						if ((i_21_ & 0x1) != 0) {
							is_12_[stepCount] = buffer_7_.getSmartA();
						} else {
							is_12_[stepCount] = i_23_;
						}
						if ((i_21_ & 0x2) != 0) {
							is_13_[stepCount] = buffer_7_.getSmartA();
						} else {
							is_13_[stepCount] = i_23_;
						}
						if ((i_21_ & 0x4) != 0) {
							is_14_[stepCount] = buffer_7_.getSmartA();
						} else {
							is_14_[stepCount] = i_23_;
						}
						i_18_ = i_20_;
						stepCount++;
						if (skins.opcodes[i_20_] == 5) {
							Animation.aBooleanArray45[animationId] = false;
						}
					}
				}
				animation.stepCount = stepCount;
				animation.opcodeTable = new int[stepCount];
				animation.modifier1 = new int[stepCount];
				animation.modifier2 = new int[stepCount];
				animation.modifier3 = new int[stepCount];
				for (int i_24_ = 0; i_24_ < stepCount; i_24_++) {
					animation.opcodeTable[i_24_] = is[i_24_];
					animation.modifier1[i_24_] = is_12_[i_24_];
					animation.modifier2[i_24_] = is_13_[i_24_];
					animation.modifier3[i_24_] = is_14_[i_24_];
				}
			}
		} catch (RuntimeException runtimeexception) {
			SignLink.reportError("72235, " + bs + ", " + bool + ", " + runtimeexception.toString());
			throw new RuntimeException();
		}
	}

	public static void reset() {
		Animation.cache = null;
	}

	public static Animation getAnimation(int animationId) {
		if (Animation.cache == null) {
			return null;
		}
		return Animation.cache[animationId];
	}

	public static boolean exists(int i) {
		if (i == -1) {
			return true;
		}
		return false;
	}
}
