package com.runescape.scene.tile;

import com.runescape.util.SignLink;

public class ComplexTile {
	public int[] anIntArray195;
	public int[] anIntArray196;
	public int[] anIntArray197;
	public int[] anIntArray198;
	public int[] anIntArray199;
	public int[] anIntArray200;
	public int[] anIntArray201;
	public int[] anIntArray202;
	public int[] anIntArray203;
	public int[] anIntArray204;
	public boolean aBoolean205 = true;
	public int anInt206;
	public int anInt207;
	public int anInt208;
	public int anInt209;
	public static int[] anIntArray210 = new int[6];
	public static int[] anIntArray211 = new int[6];
	public static int[] anIntArray212 = new int[6];
	public static int[] anIntArray213 = new int[6];
	public static int[] anIntArray214 = new int[6];
	static int[] anIntArray215 = { 1, 0 };
	static int[] anIntArray216 = { 2, 1 };
	static int[] anIntArray217 = { 3, 3 };
	static final int[][] anIntArrayArray218 = { { 1, 3, 5, 7 }, { 1, 3, 5, 7 }, { 1, 3, 5, 7 }, { 1, 3, 5, 7, 6 },
			{ 1, 3, 5, 7, 6 }, { 1, 3, 5, 7, 6 }, { 1, 3, 5, 7, 6 }, { 1, 3, 5, 7, 2, 6 }, { 1, 3, 5, 7, 2, 8 },
			{ 1, 3, 5, 7, 2, 8 }, { 1, 3, 5, 7, 11, 12 }, { 1, 3, 5, 7, 11, 12 }, { 1, 3, 5, 7, 13, 14 } };
	static final int[][] anIntArrayArray219 = { { 0, 1, 2, 3, 0, 0, 1, 3 }, { 1, 1, 2, 3, 1, 0, 1, 3 },
			{ 0, 1, 2, 3, 1, 0, 1, 3 }, { 0, 0, 1, 2, 0, 0, 2, 4, 1, 0, 4, 3 }, { 0, 0, 1, 4, 0, 0, 4, 3, 1, 1, 2, 4 },
			{ 0, 0, 4, 3, 1, 0, 1, 2, 1, 0, 2, 4 }, { 0, 1, 2, 4, 1, 0, 1, 4, 1, 0, 4, 3 },
			{ 0, 4, 1, 2, 0, 4, 2, 5, 1, 0, 4, 5, 1, 0, 5, 3 }, { 0, 4, 1, 2, 0, 4, 2, 3, 0, 4, 3, 5, 1, 0, 4, 5 },
			{ 0, 0, 4, 5, 1, 4, 1, 2, 1, 4, 2, 3, 1, 4, 3, 5 },
			{ 0, 0, 1, 5, 0, 1, 4, 5, 0, 1, 2, 4, 1, 0, 5, 3, 1, 5, 4, 3, 1, 4, 2, 3 },
			{ 1, 0, 1, 5, 1, 1, 4, 5, 1, 1, 2, 4, 0, 0, 5, 3, 0, 5, 4, 3, 0, 4, 2, 3 },
			{ 1, 0, 5, 4, 1, 0, 1, 5, 0, 0, 4, 3, 0, 4, 5, 3, 0, 5, 2, 3, 0, 1, 2, 5 } };

	public ComplexTile(int i, int i_0_, int i_1_, int i_2_, int i_3_, int i_4_, int i_5_, int i_6_, int i_7_, int i_8_,
			int i_9_, int i_10_, int i_11_, int i_12_, int i_13_, int i_14_, int i_15_, int i_16_, int i_17_, int i_18_) {
		try {
			if (i_11_ != i_10_ || i_11_ != i_2_ || i_11_ != i_9_) {
				aBoolean205 = false;
			}
			anInt206 = i_12_;
			anInt207 = i_5_;
			anInt208 = i_7_;
			anInt209 = i_18_;
			int i_19_ = 128;
			int i_20_ = i_19_ / 2;
			int i_21_ = i_19_ / 4;
			int i_22_ = i_19_ * 3 / 4;
			int[] is = ComplexTile.anIntArrayArray218[i_12_];
			int i_23_ = is.length;
			anIntArray195 = new int[i_23_];
			anIntArray196 = new int[i_23_];
			anIntArray197 = new int[i_23_];
			int[] is_24_ = new int[i_23_];
			int[] is_25_ = new int[i_23_];
			int i_26_ = i_17_ * i_19_;
			int i_27_ = i * i_19_;
			for (int i_28_ = 0; i_28_ < i_23_; i_28_++) {
				int i_29_ = is[i_28_];
				if ((i_29_ & 0x1) == 0 && i_29_ <= 8) {
					i_29_ = (i_29_ - i_5_ - i_5_ - 1 & 0x7) + 1;
				}
				if (i_29_ > 8 && i_29_ <= 12) {
					i_29_ = (i_29_ - 9 - i_5_ & 0x3) + 9;
				}
				if (i_29_ > 12 && i_29_ <= 16) {
					i_29_ = (i_29_ - 13 - i_5_ & 0x3) + 13;
				}
				int i_30_;
				int i_31_;
				int i_32_;
				int i_33_;
				int i_34_;
				if (i_29_ == 1) {
					i_30_ = i_26_;
					i_31_ = i_27_;
					i_32_ = i_11_;
					i_33_ = i_6_;
					i_34_ = i_0_;
				} else if (i_29_ == 2) {
					i_30_ = i_26_ + i_20_;
					i_31_ = i_27_;
					i_32_ = i_11_ + i_10_ >> 1;
					i_33_ = i_6_ + i_15_ >> 1;
					i_34_ = i_0_ + i_14_ >> 1;
				} else if (i_29_ == 3) {
					i_30_ = i_26_ + i_19_;
					i_31_ = i_27_;
					i_32_ = i_10_;
					i_33_ = i_15_;
					i_34_ = i_14_;
				} else if (i_29_ == 4) {
					i_30_ = i_26_ + i_19_;
					i_31_ = i_27_ + i_20_;
					i_32_ = i_10_ + i_2_ >> 1;
					i_33_ = i_15_ + i_8_ >> 1;
					i_34_ = i_14_ + i_4_ >> 1;
				} else if (i_29_ == 5) {
					i_30_ = i_26_ + i_19_;
					i_31_ = i_27_ + i_19_;
					i_32_ = i_2_;
					i_33_ = i_8_;
					i_34_ = i_4_;
				} else if (i_29_ == 6) {
					i_30_ = i_26_ + i_20_;
					i_31_ = i_27_ + i_19_;
					i_32_ = i_2_ + i_9_ >> 1;
					i_33_ = i_8_ + i_1_ >> 1;
					i_34_ = i_4_ + i_13_ >> 1;
				} else if (i_29_ == 7) {
					i_30_ = i_26_;
					i_31_ = i_27_ + i_19_;
					i_32_ = i_9_;
					i_33_ = i_1_;
					i_34_ = i_13_;
				} else if (i_29_ == 8) {
					i_30_ = i_26_;
					i_31_ = i_27_ + i_20_;
					i_32_ = i_9_ + i_11_ >> 1;
					i_33_ = i_1_ + i_6_ >> 1;
					i_34_ = i_13_ + i_0_ >> 1;
				} else if (i_29_ == 9) {
					i_30_ = i_26_ + i_20_;
					i_31_ = i_27_ + i_21_;
					i_32_ = i_11_ + i_10_ >> 1;
					i_33_ = i_6_ + i_15_ >> 1;
					i_34_ = i_0_ + i_14_ >> 1;
				} else if (i_29_ == 10) {
					i_30_ = i_26_ + i_22_;
					i_31_ = i_27_ + i_20_;
					i_32_ = i_10_ + i_2_ >> 1;
					i_33_ = i_15_ + i_8_ >> 1;
					i_34_ = i_14_ + i_4_ >> 1;
				} else if (i_29_ == 11) {
					i_30_ = i_26_ + i_20_;
					i_31_ = i_27_ + i_22_;
					i_32_ = i_2_ + i_9_ >> 1;
					i_33_ = i_8_ + i_1_ >> 1;
					i_34_ = i_4_ + i_13_ >> 1;
				} else if (i_29_ == 12) {
					i_30_ = i_26_ + i_21_;
					i_31_ = i_27_ + i_20_;
					i_32_ = i_9_ + i_11_ >> 1;
					i_33_ = i_1_ + i_6_ >> 1;
					i_34_ = i_13_ + i_0_ >> 1;
				} else if (i_29_ == 13) {
					i_30_ = i_26_ + i_21_;
					i_31_ = i_27_ + i_21_;
					i_32_ = i_11_;
					i_33_ = i_6_;
					i_34_ = i_0_;
				} else if (i_29_ == 14) {
					i_30_ = i_26_ + i_22_;
					i_31_ = i_27_ + i_21_;
					i_32_ = i_10_;
					i_33_ = i_15_;
					i_34_ = i_14_;
				} else if (i_29_ == 15) {
					i_30_ = i_26_ + i_22_;
					i_31_ = i_27_ + i_22_;
					i_32_ = i_2_;
					i_33_ = i_8_;
					i_34_ = i_4_;
				} else {
					i_30_ = i_26_ + i_21_;
					i_31_ = i_27_ + i_22_;
					i_32_ = i_9_;
					i_33_ = i_1_;
					i_34_ = i_13_;
				}
				anIntArray195[i_28_] = i_30_;
				anIntArray196[i_28_] = i_32_;
				anIntArray197[i_28_] = i_31_;
				is_24_[i_28_] = i_33_;
				is_25_[i_28_] = i_34_;
			}
			int[] is_35_ = ComplexTile.anIntArrayArray219[i_12_];
			int i_36_ = is_35_.length / 4;
			anIntArray201 = new int[i_36_];
			anIntArray202 = new int[i_36_];
			anIntArray203 = new int[i_36_];
			anIntArray198 = new int[i_36_];
			anIntArray199 = new int[i_36_];
			anIntArray200 = new int[i_36_];
			if (i_3_ != -1) {
				anIntArray204 = new int[i_36_];
			}
			int i_37_ = 0;
			for (int i_38_ = 0; i_38_ < i_36_; i_38_++) {
				int i_39_ = is_35_[i_37_];
				int i_40_ = is_35_[i_37_ + 1];
				int i_41_ = is_35_[i_37_ + 2];
				int i_42_ = is_35_[i_37_ + 3];
				i_37_ += 4;
				if (i_40_ < 4) {
					i_40_ = i_40_ - i_5_ & 0x3;
				}
				if (i_41_ < 4) {
					i_41_ = i_41_ - i_5_ & 0x3;
				}
				if (i_42_ < 4) {
					i_42_ = i_42_ - i_5_ & 0x3;
				}
				anIntArray201[i_38_] = i_40_;
				anIntArray202[i_38_] = i_41_;
				anIntArray203[i_38_] = i_42_;
				if (i_39_ == 0) {
					anIntArray198[i_38_] = is_24_[i_40_];
					anIntArray199[i_38_] = is_24_[i_41_];
					anIntArray200[i_38_] = is_24_[i_42_];
					if (anIntArray204 != null) {
						anIntArray204[i_38_] = -1;
					}
				} else {
					anIntArray198[i_38_] = is_25_[i_40_];
					anIntArray199[i_38_] = is_25_[i_41_];
					anIntArray200[i_38_] = is_25_[i_42_];
					if (anIntArray204 != null) {
						anIntArray204[i_38_] = i_3_;
					}
				}
			}
			int i_43_ = i_11_;
			int i_44_ = i_10_;
			if (i_10_ < i_43_) {
				i_43_ = i_10_;
			}
			if (i_10_ > i_44_) {
				i_44_ = i_10_;
			}
			if (i_2_ < i_43_) {
				i_43_ = i_2_;
			}
			if (i_2_ > i_44_) {
				i_44_ = i_2_;
			}
			if (i_9_ < i_43_) {
				i_43_ = i_9_;
			}
			if (i_9_ > i_44_) {
				i_44_ = i_9_;
			}
			i_43_ /= 14;
			i_44_ /= 14;
			if (i_16_ < 3 || i_16_ > 3) {
				for (int i_45_ = 1; i_45_ > 0; i_45_++) {
					/* empty */
				}
			}
		} catch (RuntimeException runtimeexception) {
			SignLink.reportError("18048, " + i + ", " + i_0_ + ", " + i_1_ + ", " + i_2_ + ", " + i_3_ + ", " + i_4_
					+ ", " + i_5_ + ", " + i_6_ + ", " + i_7_ + ", " + i_8_ + ", " + i_9_ + ", " + i_10_ + ", " + i_11_
					+ ", " + i_12_ + ", " + i_13_ + ", " + i_14_ + ", " + i_15_ + ", " + i_16_ + ", " + i_17_ + ", "
					+ i_18_ + ", " + runtimeexception.toString());
			throw new RuntimeException();
		}
	}
}
