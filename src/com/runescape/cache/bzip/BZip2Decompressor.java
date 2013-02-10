package com.runescape.cache.bzip;

/* BZip2Decompressor - Decompiled by JODE
 * Visit http://jode.sourceforge.net/
 */

public class BZip2Decompressor {
	private static BZip2Context aBZip2Context136 = new BZip2Context();

	public static int decompress(byte[] bs, int i, byte[] bs_0_, int i_1_, int i_2_) {
		synchronized (BZip2Decompressor.aBZip2Context136) {
			BZip2Decompressor.aBZip2Context136.aByteArray97 = bs_0_;
			BZip2Decompressor.aBZip2Context136.anInt98 = i_2_;
			BZip2Decompressor.aBZip2Context136.aByteArray102 = bs;
			BZip2Decompressor.aBZip2Context136.anInt103 = 0;
			BZip2Decompressor.aBZip2Context136.anInt99 = i_1_;
			BZip2Decompressor.aBZip2Context136.anInt104 = i;
			BZip2Decompressor.aBZip2Context136.anInt111 = 0;
			BZip2Decompressor.aBZip2Context136.anInt110 = 0;
			BZip2Decompressor.aBZip2Context136.anInt100 = 0;
			BZip2Decompressor.aBZip2Context136.anInt101 = 0;
			BZip2Decompressor.aBZip2Context136.anInt105 = 0;
			BZip2Decompressor.aBZip2Context136.anInt106 = 0;
			BZip2Decompressor.aBZip2Context136.anInt113 = 0;
			BZip2Decompressor.method166(BZip2Decompressor.aBZip2Context136);
			i -= BZip2Decompressor.aBZip2Context136.anInt104;
			return i;
		}
	}

	private static void method165(BZip2Context bzip2context) {
		byte b = bzip2context.aByte107;
		int i = bzip2context.anInt108;
		int i_3_ = bzip2context.anInt118;
		int i_4_ = bzip2context.anInt116;
		int[] is = BZip2Context.anIntArray121;
		int i_5_ = bzip2context.anInt115;
		byte[] bs = bzip2context.aByteArray102;
		int i_6_ = bzip2context.anInt103;
		int i_7_ = bzip2context.anInt104;
		int i_8_ = i_7_;
		int i_9_ = bzip2context.anInt135 + 1;
		while_0_: for (;;) {
			if (i > 0) {
				for (;;) {
					if (i_7_ == 0) {
						break while_0_;
					}
					if (i == 1) {
						break;
					}
					bs[i_6_] = b;
					i--;
					i_6_++;
					i_7_--;
				}
				if (i_7_ == 0) {
					i = 1;
					break;
				}
				bs[i_6_] = b;
				i_6_++;
				i_7_--;
			}
			boolean bool = true;
			while (bool) {
				bool = false;
				if (i_3_ == i_9_) {
					i = 0;
					break while_0_;
				}
				b = (byte) i_4_;
				i_5_ = is[i_5_];
				int i_10_ = (byte) (i_5_ & 0xff);
				i_5_ >>= 8;
				i_3_++;
				if (i_10_ != i_4_) {
					i_4_ = i_10_;
					if (i_7_ == 0) {
						i = 1;
						break while_0_;
					}
					bs[i_6_] = b;
					i_6_++;
					i_7_--;
					bool = true;
				} else if (i_3_ == i_9_) {
					if (i_7_ == 0) {
						i = 1;
						break while_0_;
					}
					bs[i_6_] = b;
					i_6_++;
					i_7_--;
					bool = true;
				}
			}
			i = 2;
			i_5_ = is[i_5_];
			int i_11_ = (byte) (i_5_ & 0xff);
			i_5_ >>= 8;
			if (++i_3_ != i_9_) {
				if (i_11_ != i_4_) {
					i_4_ = i_11_;
				} else {
					i = 3;
					i_5_ = is[i_5_];
					i_11_ = (byte) (i_5_ & 0xff);
					i_5_ >>= 8;
					if (++i_3_ != i_9_) {
						if (i_11_ != i_4_) {
							i_4_ = i_11_;
						} else {
							i_5_ = is[i_5_];
							i_11_ = (byte) (i_5_ & 0xff);
							i_5_ >>= 8;
							i_3_++;
							i = (i_11_ & 0xff) + 4;
							i_5_ = is[i_5_];
							i_4_ = (byte) (i_5_ & 0xff);
							i_5_ >>= 8;
							i_3_++;
						}
					}
				}
			}
		}
		int i_12_ = bzip2context.anInt105;
		bzip2context.anInt105 += i_8_ - i_7_;
		if (bzip2context.anInt105 < i_12_) {
			bzip2context.anInt106++;
		}
		bzip2context.aByte107 = b;
		bzip2context.anInt108 = i;
		bzip2context.anInt118 = i_3_;
		bzip2context.anInt116 = i_4_;
		BZip2Context.anIntArray121 = is;
		bzip2context.anInt115 = i_5_;
		bzip2context.aByteArray102 = bs;
		bzip2context.anInt103 = i_6_;
		bzip2context.anInt104 = i_7_;
	}

	private static void method166(BZip2Context bzip2context) {
		int i = 0;
		int[] is = null;
		int[] is_31_ = null;
		int[] is_32_ = null;
		bzip2context.anInt112 = 1;
		if (BZip2Context.anIntArray121 == null) {
			BZip2Context.anIntArray121 = new int[bzip2context.anInt112 * 100000];
		}
		boolean bool_33_ = true;
		while (bool_33_) {
			byte b = BZip2Decompressor.method167(bzip2context);
			if (b == 23) {
				break;
			}
			b = BZip2Decompressor.method167(bzip2context);
			b = BZip2Decompressor.method167(bzip2context);
			b = BZip2Decompressor.method167(bzip2context);
			b = BZip2Decompressor.method167(bzip2context);
			b = BZip2Decompressor.method167(bzip2context);
			bzip2context.anInt113++;
			b = BZip2Decompressor.method167(bzip2context);
			b = BZip2Decompressor.method167(bzip2context);
			b = BZip2Decompressor.method167(bzip2context);
			b = BZip2Decompressor.method167(bzip2context);
			b = BZip2Decompressor.method168(bzip2context);
			if (b != 0) {
				bzip2context.aBoolean109 = true;
			} else {
				bzip2context.aBoolean109 = false;
			}
			if (bzip2context.aBoolean109) {
				System.out.println("PANIC! RANDOMISED BLOCK!");
			}
			bzip2context.anInt114 = 0;
			int i_34_ = BZip2Decompressor.method167(bzip2context);
			bzip2context.anInt114 = bzip2context.anInt114 << 8 | i_34_ & 0xff;
			i_34_ = BZip2Decompressor.method167(bzip2context);
			bzip2context.anInt114 = bzip2context.anInt114 << 8 | i_34_ & 0xff;
			i_34_ = BZip2Decompressor.method167(bzip2context);
			bzip2context.anInt114 = bzip2context.anInt114 << 8 | i_34_ & 0xff;
			for (int i_35_ = 0; i_35_ < 16; i_35_++) {
				b = BZip2Decompressor.method168(bzip2context);
				if (b == 1) {
					bzip2context.aBooleanArray124[i_35_] = true;
				} else {
					bzip2context.aBooleanArray124[i_35_] = false;
				}
			}
			for (int i_36_ = 0; i_36_ < 256; i_36_++) {
				bzip2context.aBooleanArray123[i_36_] = false;
			}
			for (int i_37_ = 0; i_37_ < 16; i_37_++) {
				if (bzip2context.aBooleanArray124[i_37_]) {
					for (int i_38_ = 0; i_38_ < 16; i_38_++) {
						b = BZip2Decompressor.method168(bzip2context);
						if (b == 1) {
							bzip2context.aBooleanArray123[i_37_ * 16 + i_38_] = true;
						}
					}
				}
			}
			BZip2Decompressor.method170(bzip2context);
			int i_39_ = bzip2context.anInt122 + 2;
			int i_40_ = BZip2Decompressor.method169(3, bzip2context);
			int i_41_ = BZip2Decompressor.method169(15, bzip2context);
			for (int i_42_ = 0; i_42_ < i_41_; i_42_++) {
				int i_43_ = 0;
				for (;;) {
					b = BZip2Decompressor.method168(bzip2context);
					if (b == 0) {
						break;
					}
					i_43_++;
				}
				bzip2context.aByteArray129[i_42_] = (byte) i_43_;
			}
			byte[] bs = new byte[6];
			for (byte b_44_ = 0; b_44_ < i_40_; b_44_++) {
				bs[b_44_] = b_44_;
			}
			for (int i_45_ = 0; i_45_ < i_41_; i_45_++) {
				byte b_46_ = bzip2context.aByteArray129[i_45_];
				byte b_47_ = bs[b_46_];
				for (/**/; b_46_ > 0; b_46_--) {
					bs[b_46_] = bs[b_46_ - 1];
				}
				bs[0] = b_47_;
				bzip2context.aByteArray128[i_45_] = b_47_;
			}
			for (int i_48_ = 0; i_48_ < i_40_; i_48_++) {
				int i_49_ = BZip2Decompressor.method169(5, bzip2context);
				for (int i_50_ = 0; i_50_ < i_39_; i_50_++) {
					for (;;) {
						b = BZip2Decompressor.method168(bzip2context);
						if (b == 0) {
							break;
						}
						b = BZip2Decompressor.method168(bzip2context);
						if (b == 0) {
							i_49_++;
						} else {
							i_49_--;
						}
					}
					bzip2context.aByteArrayArray130[i_48_][i_50_] = (byte) i_49_;
				}
			}
			for (int i_51_ = 0; i_51_ < i_40_; i_51_++) {
				int i_52_ = 32;
				byte b_53_ = 0;
				for (int i_54_ = 0; i_54_ < i_39_; i_54_++) {
					if (bzip2context.aByteArrayArray130[i_51_][i_54_] > b_53_) {
						b_53_ = bzip2context.aByteArrayArray130[i_51_][i_54_];
					}
					if (bzip2context.aByteArrayArray130[i_51_][i_54_] < i_52_) {
						i_52_ = bzip2context.aByteArrayArray130[i_51_][i_54_];
					}
				}
				BZip2Decompressor.method171(bzip2context.anIntArrayArray131[i_51_],
						bzip2context.anIntArrayArray132[i_51_], bzip2context.anIntArrayArray133[i_51_],
						bzip2context.aByteArrayArray130[i_51_], i_52_, b_53_, i_39_);
				bzip2context.anIntArray134[i_51_] = i_52_;
			}
			int i_55_ = bzip2context.anInt122 + 1;
			int i_57_ = -1;
			int i_58_ = 0;
			for (int i_59_ = 0; i_59_ <= 255; i_59_++) {
				bzip2context.anIntArray117[i_59_] = 0;
			}
			int i_60_ = 4095;
			for (int i_61_ = 15; i_61_ >= 0; i_61_--) {
				for (int i_62_ = 15; i_62_ >= 0; i_62_--) {
					bzip2context.aByteArray126[i_60_] = (byte) (i_61_ * 16 + i_62_);
					i_60_--;
				}
				bzip2context.anIntArray127[i_61_] = i_60_ + 1;
			}
			int i_63_ = 0;
			if (i_58_ == 0) {
				i_57_++;
				i_58_ = 50;
				byte b_64_ = bzip2context.aByteArray128[i_57_];
				i = bzip2context.anIntArray134[b_64_];
				is = bzip2context.anIntArrayArray131[b_64_];
				is_32_ = bzip2context.anIntArrayArray133[b_64_];
				is_31_ = bzip2context.anIntArrayArray132[b_64_];
			}
			i_58_--;
			int i_65_ = i;
			int i_66_;
			int i_67_;
			for (i_67_ = BZip2Decompressor.method169(i_65_, bzip2context); i_67_ > is[i_65_]; i_67_ = i_67_ << 1
					| i_66_) {
				i_65_++;
				i_66_ = BZip2Decompressor.method168(bzip2context);
			}
			int i_68_ = is_32_[i_67_ - is_31_[i_65_]];
			while (i_68_ != i_55_) {
				if (i_68_ == 0 || i_68_ == 1) {
					int i_69_ = -1;
					int i_70_ = 1;
					do {
						if (i_68_ == 0) {
							i_69_ += i_70_;
						} else if (i_68_ == 1) {
							i_69_ += 2 * i_70_;
						}
						i_70_ *= 2;
						if (i_58_ == 0) {
							i_57_++;
							i_58_ = 50;
							byte b_71_ = bzip2context.aByteArray128[i_57_];
							i = bzip2context.anIntArray134[b_71_];
							is = bzip2context.anIntArrayArray131[b_71_];
							is_32_ = bzip2context.anIntArrayArray133[b_71_];
							is_31_ = bzip2context.anIntArrayArray132[b_71_];
						}
						i_58_--;
						i_65_ = i;
						for (i_67_ = BZip2Decompressor.method169(i_65_, bzip2context); i_67_ > is[i_65_]; i_67_ = i_67_ << 1
								| i_66_) {
							i_65_++;
							i_66_ = BZip2Decompressor.method168(bzip2context);
						}
						i_68_ = is_32_[i_67_ - is_31_[i_65_]];
					} while (i_68_ == 0 || i_68_ == 1);
					i_69_++;
					i_34_ = bzip2context.aByteArray125[bzip2context.aByteArray126[bzip2context.anIntArray127[0]] & 0xff];
					bzip2context.anIntArray117[i_34_ & 0xff] += i_69_;
					for (/**/; i_69_ > 0; i_69_--) {
						BZip2Context.anIntArray121[i_63_] = i_34_ & 0xff;
						i_63_++;
					}
				} else {
					int i_72_ = i_68_ - 1;
					if (i_72_ < 16) {
						int i_73_ = bzip2context.anIntArray127[0];
						b = bzip2context.aByteArray126[i_73_ + i_72_];
						for (/**/; i_72_ > 3; i_72_ -= 4) {
							int i_74_ = i_73_ + i_72_;
							bzip2context.aByteArray126[i_74_] = bzip2context.aByteArray126[i_74_ - 1];
							bzip2context.aByteArray126[i_74_ - 1] = bzip2context.aByteArray126[i_74_ - 2];
							bzip2context.aByteArray126[i_74_ - 2] = bzip2context.aByteArray126[i_74_ - 3];
							bzip2context.aByteArray126[i_74_ - 3] = bzip2context.aByteArray126[i_74_ - 4];
						}
						for (/**/; i_72_ > 0; i_72_--) {
							bzip2context.aByteArray126[i_73_ + i_72_] = bzip2context.aByteArray126[i_73_ + i_72_ - 1];
						}
						bzip2context.aByteArray126[i_73_] = b;
					} else {
						int i_75_ = i_72_ / 16;
						int i_76_ = i_72_ % 16;
						int i_77_ = bzip2context.anIntArray127[i_75_] + i_76_;
						b = bzip2context.aByteArray126[i_77_];
						for (/**/; i_77_ > bzip2context.anIntArray127[i_75_]; i_77_--) {
							bzip2context.aByteArray126[i_77_] = bzip2context.aByteArray126[i_77_ - 1];
						}
						bzip2context.anIntArray127[i_75_]++;
						for (/**/; i_75_ > 0; i_75_--) {
							bzip2context.anIntArray127[i_75_]--;
							bzip2context.aByteArray126[bzip2context.anIntArray127[i_75_]] = bzip2context.aByteArray126[bzip2context.anIntArray127[i_75_ - 1] + 16 - 1];
						}
						bzip2context.anIntArray127[0]--;
						bzip2context.aByteArray126[bzip2context.anIntArray127[0]] = b;
						if (bzip2context.anIntArray127[0] == 0) {
							int i_78_ = 4095;
							for (int i_79_ = 15; i_79_ >= 0; i_79_--) {
								for (int i_80_ = 15; i_80_ >= 0; i_80_--) {
									bzip2context.aByteArray126[i_78_] = bzip2context.aByteArray126[bzip2context.anIntArray127[i_79_]
											+ i_80_];
									i_78_--;
								}
								bzip2context.anIntArray127[i_79_] = i_78_ + 1;
							}
						}
					}
					bzip2context.anIntArray117[bzip2context.aByteArray125[b & 0xff] & 0xff]++;
					BZip2Context.anIntArray121[i_63_] = bzip2context.aByteArray125[b & 0xff] & 0xff;
					i_63_++;
					if (i_58_ == 0) {
						i_57_++;
						i_58_ = 50;
						byte b_81_ = bzip2context.aByteArray128[i_57_];
						i = bzip2context.anIntArray134[b_81_];
						is = bzip2context.anIntArrayArray131[b_81_];
						is_32_ = bzip2context.anIntArrayArray133[b_81_];
						is_31_ = bzip2context.anIntArrayArray132[b_81_];
					}
					i_58_--;
					i_65_ = i;
					for (i_67_ = BZip2Decompressor.method169(i_65_, bzip2context); i_67_ > is[i_65_]; i_67_ = i_67_ << 1
							| i_66_) {
						i_65_++;
						i_66_ = BZip2Decompressor.method168(bzip2context);
					}
					i_68_ = is_32_[i_67_ - is_31_[i_65_]];
				}
			}
			bzip2context.anInt108 = 0;
			bzip2context.aByte107 = (byte) 0;
			bzip2context.anIntArray119[0] = 0;
			for (int i_82_ = 1; i_82_ <= 256; i_82_++) {
				bzip2context.anIntArray119[i_82_] = bzip2context.anIntArray117[i_82_ - 1];
			}
			for (int i_83_ = 1; i_83_ <= 256; i_83_++) {
				bzip2context.anIntArray119[i_83_] += bzip2context.anIntArray119[i_83_ - 1];
			}
			for (int i_84_ = 0; i_84_ < i_63_; i_84_++) {
				i_34_ = (byte) (BZip2Context.anIntArray121[i_84_] & 0xff);
				BZip2Context.anIntArray121[bzip2context.anIntArray119[i_34_ & 0xff]] |= i_84_ << 8;
				bzip2context.anIntArray119[i_34_ & 0xff]++;
			}
			bzip2context.anInt115 = BZip2Context.anIntArray121[bzip2context.anInt114] >> 8;
			bzip2context.anInt118 = 0;
			bzip2context.anInt115 = BZip2Context.anIntArray121[bzip2context.anInt115];
			bzip2context.anInt116 = (byte) (bzip2context.anInt115 & 0xff);
			bzip2context.anInt115 >>= 8;
			bzip2context.anInt118++;
			bzip2context.anInt135 = i_63_;
			BZip2Decompressor.method165(bzip2context);
			if (bzip2context.anInt118 == bzip2context.anInt135 + 1 && bzip2context.anInt108 == 0) {
				bool_33_ = true;
			} else {
				bool_33_ = false;
			}
		}
	}

	private static byte method167(BZip2Context bzip2context) {
		return (byte) BZip2Decompressor.method169(8, bzip2context);
	}

	private static byte method168(BZip2Context bzip2context) {
		return (byte) BZip2Decompressor.method169(1, bzip2context);
	}

	private static int method169(int i, BZip2Context bzip2context) {
		int i_85_;
		for (;;) {
			if (bzip2context.anInt111 >= i) {
				int i_86_ = bzip2context.anInt110 >> bzip2context.anInt111 - i & (1 << i) - 1;
				bzip2context.anInt111 -= i;
				i_85_ = i_86_;
				break;
			}
			bzip2context.anInt110 = bzip2context.anInt110 << 8 | bzip2context.aByteArray97[bzip2context.anInt98] & 0xff;
			bzip2context.anInt111 += 8;
			bzip2context.anInt98++;
			bzip2context.anInt99--;
			bzip2context.anInt100++;
			if (bzip2context.anInt100 == 0) {
				bzip2context.anInt101++;
			}
		}
		return i_85_;
	}

	private static void method170(BZip2Context bzip2context) {
		bzip2context.anInt122 = 0;
		for (int i = 0; i < 256; i++) {
			if (bzip2context.aBooleanArray123[i]) {
				bzip2context.aByteArray125[bzip2context.anInt122] = (byte) i;
				bzip2context.anInt122++;
			}
		}
	}

	private static void method171(int[] is, int[] is_87_, int[] is_88_, byte[] bs, int i, int i_89_, int i_90_) {
		int i_91_ = 0;
		for (int i_92_ = i; i_92_ <= i_89_; i_92_++) {
			for (int i_93_ = 0; i_93_ < i_90_; i_93_++) {
				if (bs[i_93_] == i_92_) {
					is_88_[i_91_] = i_93_;
					i_91_++;
				}
			}
		}
		for (int i_94_ = 0; i_94_ < 23; i_94_++) {
			is_87_[i_94_] = 0;
		}
		for (int i_95_ = 0; i_95_ < i_90_; i_95_++) {
			is_87_[bs[i_95_] + 1]++;
		}
		for (int i_96_ = 1; i_96_ < 23; i_96_++) {
			is_87_[i_96_] += is_87_[i_96_ - 1];
		}
		for (int i_97_ = 0; i_97_ < 23; i_97_++) {
			is[i_97_] = 0;
		}
		int i_98_ = 0;
		for (int i_99_ = i; i_99_ <= i_89_; i_99_++) {
			i_98_ += is_87_[i_99_ + 1] - is_87_[i_99_];
			is[i_99_] = i_98_ - 1;
			i_98_ <<= 1;
		}
		for (int i_100_ = i + 1; i_100_ <= i_89_; i_100_++) {
			is_87_[i_100_] = (is[i_100_ - 1] + 1 << 1) - is_87_[i_100_];
		}
	}
}
