package com.runescape.media;

/* Rasterizer3D - Decompiled by JODE
 * Visit http://jode.sourceforge.net/
 */
import com.runescape.cache.Archive;
import com.runescape.cache.media.IndexedImage;
import com.runescape.util.SignLink;

public class Rasterizer3D extends Rasterizer {
	public static boolean lowMemory = true;
	public static boolean aBoolean1482;
	static boolean aBoolean1483;
	public static boolean textured = true;
	public static int anInt1485;
	public static int centerX;
	public static int centerY;
	public static int[] shadowDecay = new int[512];
	public static int[] anIntArray1489 = new int[2048];
	public static int[] SINE = new int[2048];
	public static int[] COSINE = new int[2048];
	public static int[] lineOffsets;
	static int anInt1493;
	public static IndexedImage[] indexedImages;
	static boolean[] aBooleanArray1495;
	static int[] anIntArray1496;
	static int anInt1497;
	static int[][] anIntArrayArray1498;
	static int[][] anIntArrayArray1499;
	public static int[] anIntArray1500;
	public static int anInt1501;
	public static int[] getRgbLookupTableId;
	static int[][] anIntArrayArray1503;

	public static final void reset() {
		Rasterizer3D.shadowDecay = null;
		Rasterizer3D.shadowDecay = null;
		Rasterizer3D.SINE = null;
		Rasterizer3D.COSINE = null;
		Rasterizer3D.lineOffsets = null;
		Rasterizer3D.indexedImages = null;
		Rasterizer3D.aBooleanArray1495 = null;
		Rasterizer3D.anIntArray1496 = null;
		Rasterizer3D.anIntArrayArray1498 = null;
		Rasterizer3D.anIntArrayArray1499 = null;
		Rasterizer3D.anIntArray1500 = null;
		Rasterizer3D.getRgbLookupTableId = null;
		Rasterizer3D.anIntArrayArray1503 = null;
	}

	public static final void setDefaultBoundaries() {
		Rasterizer3D.lineOffsets = new int[Rasterizer.height];
		for (int lineOffset = 0; lineOffset < Rasterizer.height; lineOffset++) {
			Rasterizer3D.lineOffsets[lineOffset] = Rasterizer.width * lineOffset;
		}
		Rasterizer3D.centerX = Rasterizer.width / 2;
		Rasterizer3D.centerY = Rasterizer.height / 2;
	}

	public static final void method362(int i, int i_1_, int i_2_) {
		try {
			Rasterizer3D.lineOffsets = new int[i_2_];
			if (i < 0) {
				for (int i_3_ = 0; i_3_ < i_2_; i_3_++) {
					Rasterizer3D.lineOffsets[i_3_] = i_1_ * i_3_;
				}
				Rasterizer3D.centerX = i_1_ / 2;
				Rasterizer3D.centerY = i_2_ / 2;
			}
		} catch (RuntimeException runtimeexception) {
			SignLink.reportError("8612, " + i + ", " + i_1_ + ", " + i_2_ + ", " + runtimeexception.toString());
			throw new RuntimeException();
		}
	}

	public static final void method363(int i) {
		try {
			if (i >= 0 && i <= 0) {
				Rasterizer3D.anIntArrayArray1498 = null;
				for (int i_4_ = 0; i_4_ < 50; i_4_++) {
					Rasterizer3D.anIntArrayArray1499[i_4_] = null;
				}
			}
		} catch (RuntimeException runtimeexception) {
			SignLink.reportError("84003, " + i + ", " + runtimeexception.toString());
			throw new RuntimeException();
		}
	}

	public static final void method364(int i, boolean bool) {
		do {
			try {
				if (!bool) {
					for (int i_5_ = 1; i_5_ > 0; i_5_++) {
						/* empty */
					}
				}
				if (Rasterizer3D.anIntArrayArray1498 != null) {
					break;
				}
				Rasterizer3D.anInt1497 = i;
				if (Rasterizer3D.lowMemory) {
					Rasterizer3D.anIntArrayArray1498 = new int[Rasterizer3D.anInt1497][16384];
				} else {
					Rasterizer3D.anIntArrayArray1498 = new int[Rasterizer3D.anInt1497][65536];
				}
				for (int i_6_ = 0; i_6_ < 50; i_6_++) {
					Rasterizer3D.anIntArrayArray1499[i_6_] = null;
				}
			} catch (RuntimeException runtimeexception) {
				SignLink.reportError("54075, " + i + ", " + bool + ", " + runtimeexception.toString());
				throw new RuntimeException();
			}
			break;
		} while (false);
	}

	public static final void loadIndexedImages(Archive archive) {
		Rasterizer3D.anInt1493 = 0;
		for (int indexImageId = 0; indexImageId < 50; indexImageId++) {
			Rasterizer3D.indexedImages[indexImageId] = new IndexedImage(archive, String.valueOf(indexImageId), 0);
			if (Rasterizer3D.lowMemory && Rasterizer3D.indexedImages[indexImageId].maxWidth == 128) {
				Rasterizer3D.indexedImages[indexImageId].resizeToHalfMax();
			} else {
				Rasterizer3D.indexedImages[indexImageId].resizeToMax();
			}
			Rasterizer3D.anInt1493++;
		}
	}

	public static final int getAverageRgbColorForTexture(int i, int i_8_) {
		try {
			if (i_8_ != 12660) {
				return 2;
			}
			if (Rasterizer3D.anIntArray1496[i] != 0) {
				return Rasterizer3D.anIntArray1496[i];
			}
			int i_9_ = 0;
			int i_10_ = 0;
			int i_11_ = 0;
			int i_12_ = Rasterizer3D.anIntArrayArray1503[i].length;
			for (int i_13_ = 0; i_13_ < i_12_; i_13_++) {
				i_9_ += Rasterizer3D.anIntArrayArray1503[i][i_13_] >> 16 & 0xff;
				i_10_ += Rasterizer3D.anIntArrayArray1503[i][i_13_] >> 8 & 0xff;
				i_11_ += Rasterizer3D.anIntArrayArray1503[i][i_13_] & 0xff;
			}
			int i_14_ = (i_9_ / i_12_ << 16) + (i_10_ / i_12_ << 8) + i_11_ / i_12_;
			i_14_ = Rasterizer3D.method370(i_14_, 1.4);
			if (i_14_ == 0) {
				i_14_ = 1;
			}
			Rasterizer3D.anIntArray1496[i] = i_14_;
			return i_14_;
		} catch (RuntimeException runtimeexception) {
			SignLink.reportError("10237, " + i + ", " + i_8_ + ", " + runtimeexception.toString());
			throw new RuntimeException();
		}
	}

	public static final void method367(int i, int i_15_) {
		try {
			if (Rasterizer3D.anIntArrayArray1499[i] != null) {
				Rasterizer3D.anIntArrayArray1498[Rasterizer3D.anInt1497++] = Rasterizer3D.anIntArrayArray1499[i];
				while (i_15_ >= 0) {
				}
				Rasterizer3D.anIntArrayArray1499[i] = null;
			}
		} catch (RuntimeException runtimeexception) {
			SignLink.reportError("64331, " + i + ", " + i_15_ + ", " + runtimeexception.toString());
			throw new RuntimeException();
		}
	}

	public static final int[] method368(int i) {
		Rasterizer3D.anIntArray1500[i] = Rasterizer3D.anInt1501++;
		if (Rasterizer3D.anIntArrayArray1499[i] != null) {
			return Rasterizer3D.anIntArrayArray1499[i];
		}
		int[] is;
		if (Rasterizer3D.anInt1497 > 0) {
			is = Rasterizer3D.anIntArrayArray1498[--Rasterizer3D.anInt1497];
			Rasterizer3D.anIntArrayArray1498[Rasterizer3D.anInt1497] = null;
		} else {
			int i_16_ = 0;
			int i_17_ = -1;
			for (int i_18_ = 0; i_18_ < Rasterizer3D.anInt1493; i_18_++) {
				if (Rasterizer3D.anIntArrayArray1499[i_18_] != null
						&& (Rasterizer3D.anIntArray1500[i_18_] < i_16_ || i_17_ == -1)) {
					i_16_ = Rasterizer3D.anIntArray1500[i_18_];
					i_17_ = i_18_;
				}
			}
			is = Rasterizer3D.anIntArrayArray1499[i_17_];
			Rasterizer3D.anIntArrayArray1499[i_17_] = null;
		}
		Rasterizer3D.anIntArrayArray1499[i] = is;
		IndexedImage indexedimage = Rasterizer3D.indexedImages[i];
		int[] is_19_ = Rasterizer3D.anIntArrayArray1503[i];
		if (Rasterizer3D.lowMemory) {
			Rasterizer3D.aBooleanArray1495[i] = false;
			for (int i_20_ = 0; i_20_ < 4096; i_20_++) {
				int i_21_ = is[i_20_] = is_19_[indexedimage.pixels[i_20_]] & 0xf8f8ff;
				if (i_21_ == 0) {
					Rasterizer3D.aBooleanArray1495[i] = true;
				}
				is[4096 + i_20_] = i_21_ - (i_21_ >>> 3) & 0xf8f8ff;
				is[8192 + i_20_] = i_21_ - (i_21_ >>> 2) & 0xf8f8ff;
				is[12288 + i_20_] = i_21_ - (i_21_ >>> 2) - (i_21_ >>> 3) & 0xf8f8ff;
			}
		} else {
			if (indexedimage.width == 64) {
				for (int i_22_ = 0; i_22_ < 128; i_22_++) {
					for (int i_23_ = 0; i_23_ < 128; i_23_++) {
						is[i_23_ + (i_22_ << 7)] = is_19_[indexedimage.pixels[(i_23_ >> 1) + (i_22_ >> 1 << 6)]];
					}
				}
			} else {
				for (int i_24_ = 0; i_24_ < 16384; i_24_++) {
					is[i_24_] = is_19_[indexedimage.pixels[i_24_]];
				}
			}
			Rasterizer3D.aBooleanArray1495[i] = false;
			for (int i_25_ = 0; i_25_ < 16384; i_25_++) {
				is[i_25_] &= 0xf8f8ff;
				int i_26_ = is[i_25_];
				if (i_26_ == 0) {
					Rasterizer3D.aBooleanArray1495[i] = true;
				}
				is[16384 + i_25_] = i_26_ - (i_26_ >>> 3) & 0xf8f8ff;
				is[32768 + i_25_] = i_26_ - (i_26_ >>> 2) & 0xf8f8ff;
				is[49152 + i_25_] = i_26_ - (i_26_ >>> 2) - (i_26_ >>> 3) & 0xf8f8ff;
			}
		}
		return is;
	}

	public static final void method369(double d) {
		try {
			d += Math.random() * 0.03 - 0.015;
			int i = 0;
			for (int i_27_ = 0; i_27_ < 512; i_27_++) {
				double d_28_ = i_27_ / 8 / 64.0 + 0.0078125;
				double d_29_ = (i_27_ & 0x7) / 8.0 + 0.0625;
				for (int i_30_ = 0; i_30_ < 128; i_30_++) {
					double d_31_ = i_30_ / 128.0;
					double d_32_ = d_31_;
					double d_33_ = d_31_;
					double d_34_ = d_31_;
					if (d_29_ != 0.0) {
						double d_35_;
						if (d_31_ < 0.5) {
							d_35_ = d_31_ * (1.0 + d_29_);
						} else {
							d_35_ = d_31_ + d_29_ - d_31_ * d_29_;
						}
						double d_36_ = 2.0 * d_31_ - d_35_;
						double d_37_ = d_28_ + 0.3333333333333333;
						if (d_37_ > 1.0) {
							d_37_--;
						}
						double d_38_ = d_28_;
						double d_39_ = d_28_ - 0.3333333333333333;
						if (d_39_ < 0.0) {
							d_39_++;
						}
						if (6.0 * d_37_ < 1.0) {
							d_32_ = d_36_ + (d_35_ - d_36_) * 6.0 * d_37_;
						} else if (2.0 * d_37_ < 1.0) {
							d_32_ = d_35_;
						} else if (3.0 * d_37_ < 2.0) {
							d_32_ = d_36_ + (d_35_ - d_36_) * (0.6666666666666666 - d_37_) * 6.0;
						} else {
							d_32_ = d_36_;
						}
						if (6.0 * d_38_ < 1.0) {
							d_33_ = d_36_ + (d_35_ - d_36_) * 6.0 * d_38_;
						} else if (2.0 * d_38_ < 1.0) {
							d_33_ = d_35_;
						} else if (3.0 * d_38_ < 2.0) {
							d_33_ = d_36_ + (d_35_ - d_36_) * (0.6666666666666666 - d_38_) * 6.0;
						} else {
							d_33_ = d_36_;
						}
						if (6.0 * d_39_ < 1.0) {
							d_34_ = d_36_ + (d_35_ - d_36_) * 6.0 * d_39_;
						} else if (2.0 * d_39_ < 1.0) {
							d_34_ = d_35_;
						} else if (3.0 * d_39_ < 2.0) {
							d_34_ = d_36_ + (d_35_ - d_36_) * (0.6666666666666666 - d_39_) * 6.0;
						} else {
							d_34_ = d_36_;
						}
					}
					int i_40_ = (int) (d_32_ * 256.0);
					int i_41_ = (int) (d_33_ * 256.0);
					int i_42_ = (int) (d_34_ * 256.0);
					int i_43_ = (i_40_ << 16) + (i_41_ << 8) + i_42_;
					i_43_ = Rasterizer3D.method370(i_43_, d);
					if (i_43_ == 0) {
						i_43_ = 1;
					}
					Rasterizer3D.getRgbLookupTableId[i++] = i_43_;
				}
			}
			for (int i_44_ = 0; i_44_ < 50; i_44_++) {
				if (Rasterizer3D.indexedImages[i_44_] != null) {
					int[] is = Rasterizer3D.indexedImages[i_44_].palette;
					Rasterizer3D.anIntArrayArray1503[i_44_] = new int[is.length];
					for (int i_45_ = 0; i_45_ < is.length; i_45_++) {
						Rasterizer3D.anIntArrayArray1503[i_44_][i_45_] = Rasterizer3D.method370(is[i_45_], d);
						if ((Rasterizer3D.anIntArrayArray1503[i_44_][i_45_] & 0xf8f8ff) == 0 && i_45_ != 0) {
							Rasterizer3D.anIntArrayArray1503[i_44_][i_45_] = 1;
						}
					}
				}
			}
			for (int i_46_ = 0; i_46_ < 50; i_46_++) {
				Rasterizer3D.method367(i_46_, -477);
			}
		} catch (RuntimeException runtimeexception) {
			SignLink.reportError("71578, " + d + ", " + runtimeexception.toString());
			throw new RuntimeException();
		}
	}

	public static int method370(int i, double d) {
		double d_47_ = (i >> 16) / 256.0;
		double d_48_ = (i >> 8 & 0xff) / 256.0;
		double d_49_ = (i & 0xff) / 256.0;
		d_47_ = Math.pow(d_47_, d);
		d_48_ = Math.pow(d_48_, d);
		d_49_ = Math.pow(d_49_, d);
		int i_50_ = (int) (d_47_ * 256.0);
		int i_51_ = (int) (d_48_ * 256.0);
		int i_52_ = (int) (d_49_ * 256.0);
		return (i_50_ << 16) + (i_51_ << 8) + i_52_;
	}

	public static final void method371(int i, int i_53_, int i_54_, int i_55_, int i_56_, int i_57_, int i_58_,
			int i_59_, int i_60_) {
		int i_61_ = 0;
		int i_62_ = 0;
		if (i_53_ != i) {
			i_61_ = (i_56_ - i_55_ << 16) / (i_53_ - i);
			i_62_ = (i_59_ - i_58_ << 15) / (i_53_ - i);
		}
		int i_63_ = 0;
		int i_64_ = 0;
		if (i_54_ != i_53_) {
			i_63_ = (i_57_ - i_56_ << 16) / (i_54_ - i_53_);
			i_64_ = (i_60_ - i_59_ << 15) / (i_54_ - i_53_);
		}
		int i_65_ = 0;
		int i_66_ = 0;
		if (i_54_ != i) {
			i_65_ = (i_55_ - i_57_ << 16) / (i - i_54_);
			i_66_ = (i_58_ - i_60_ << 15) / (i - i_54_);
		}
		if (i <= i_53_ && i <= i_54_) {
			if (i < Rasterizer.bottomY) {
				if (i_53_ > Rasterizer.bottomY) {
					i_53_ = Rasterizer.bottomY;
				}
				if (i_54_ > Rasterizer.bottomY) {
					i_54_ = Rasterizer.bottomY;
				}
				if (i_53_ < i_54_) {
					i_57_ = i_55_ <<= 16;
					i_60_ = i_58_ <<= 15;
					if (i < 0) {
						i_57_ -= i_65_ * i;
						i_55_ -= i_61_ * i;
						i_60_ -= i_66_ * i;
						i_58_ -= i_62_ * i;
						i = 0;
					}
					i_56_ <<= 16;
					i_59_ <<= 15;
					if (i_53_ < 0) {
						i_56_ -= i_63_ * i_53_;
						i_59_ -= i_64_ * i_53_;
						i_53_ = 0;
					}
					if (i != i_53_ && i_65_ < i_61_ || i == i_53_ && i_65_ > i_63_) {
						i_54_ -= i_53_;
						i_53_ -= i;
						i = Rasterizer3D.lineOffsets[i];
						while (--i_53_ >= 0) {
							Rasterizer3D.method372(Rasterizer.pixels, i, 0, 0, i_57_ >> 16, i_55_ >> 16, i_60_ >> 7,
									i_58_ >> 7);
							i_57_ += i_65_;
							i_55_ += i_61_;
							i_60_ += i_66_;
							i_58_ += i_62_;
							i += Rasterizer.width;
						}
						while (--i_54_ >= 0) {
							Rasterizer3D.method372(Rasterizer.pixels, i, 0, 0, i_57_ >> 16, i_56_ >> 16, i_60_ >> 7,
									i_59_ >> 7);
							i_57_ += i_65_;
							i_56_ += i_63_;
							i_60_ += i_66_;
							i_59_ += i_64_;
							i += Rasterizer.width;
						}
					} else {
						i_54_ -= i_53_;
						i_53_ -= i;
						i = Rasterizer3D.lineOffsets[i];
						while (--i_53_ >= 0) {
							Rasterizer3D.method372(Rasterizer.pixels, i, 0, 0, i_55_ >> 16, i_57_ >> 16, i_58_ >> 7,
									i_60_ >> 7);
							i_57_ += i_65_;
							i_55_ += i_61_;
							i_60_ += i_66_;
							i_58_ += i_62_;
							i += Rasterizer.width;
						}
						while (--i_54_ >= 0) {
							Rasterizer3D.method372(Rasterizer.pixels, i, 0, 0, i_56_ >> 16, i_57_ >> 16, i_59_ >> 7,
									i_60_ >> 7);
							i_57_ += i_65_;
							i_56_ += i_63_;
							i_60_ += i_66_;
							i_59_ += i_64_;
							i += Rasterizer.width;
						}
					}
				} else {
					i_56_ = i_55_ <<= 16;
					i_59_ = i_58_ <<= 15;
					if (i < 0) {
						i_56_ -= i_65_ * i;
						i_55_ -= i_61_ * i;
						i_59_ -= i_66_ * i;
						i_58_ -= i_62_ * i;
						i = 0;
					}
					i_57_ <<= 16;
					i_60_ <<= 15;
					if (i_54_ < 0) {
						i_57_ -= i_63_ * i_54_;
						i_60_ -= i_64_ * i_54_;
						i_54_ = 0;
					}
					if (i != i_54_ && i_65_ < i_61_ || i == i_54_ && i_63_ > i_61_) {
						i_53_ -= i_54_;
						i_54_ -= i;
						i = Rasterizer3D.lineOffsets[i];
						while (--i_54_ >= 0) {
							Rasterizer3D.method372(Rasterizer.pixels, i, 0, 0, i_56_ >> 16, i_55_ >> 16, i_59_ >> 7,
									i_58_ >> 7);
							i_56_ += i_65_;
							i_55_ += i_61_;
							i_59_ += i_66_;
							i_58_ += i_62_;
							i += Rasterizer.width;
						}
						while (--i_53_ >= 0) {
							Rasterizer3D.method372(Rasterizer.pixels, i, 0, 0, i_57_ >> 16, i_55_ >> 16, i_60_ >> 7,
									i_58_ >> 7);
							i_57_ += i_63_;
							i_55_ += i_61_;
							i_60_ += i_64_;
							i_58_ += i_62_;
							i += Rasterizer.width;
						}
					} else {
						i_53_ -= i_54_;
						i_54_ -= i;
						i = Rasterizer3D.lineOffsets[i];
						while (--i_54_ >= 0) {
							Rasterizer3D.method372(Rasterizer.pixels, i, 0, 0, i_55_ >> 16, i_56_ >> 16, i_58_ >> 7,
									i_59_ >> 7);
							i_56_ += i_65_;
							i_55_ += i_61_;
							i_59_ += i_66_;
							i_58_ += i_62_;
							i += Rasterizer.width;
						}
						while (--i_53_ >= 0) {
							Rasterizer3D.method372(Rasterizer.pixels, i, 0, 0, i_55_ >> 16, i_57_ >> 16, i_58_ >> 7,
									i_60_ >> 7);
							i_57_ += i_63_;
							i_55_ += i_61_;
							i_60_ += i_64_;
							i_58_ += i_62_;
							i += Rasterizer.width;
						}
					}
				}
			}
		} else if (i_53_ <= i_54_) {
			if (i_53_ < Rasterizer.bottomY) {
				if (i_54_ > Rasterizer.bottomY) {
					i_54_ = Rasterizer.bottomY;
				}
				if (i > Rasterizer.bottomY) {
					i = Rasterizer.bottomY;
				}
				if (i_54_ < i) {
					i_55_ = i_56_ <<= 16;
					i_58_ = i_59_ <<= 15;
					if (i_53_ < 0) {
						i_55_ -= i_61_ * i_53_;
						i_56_ -= i_63_ * i_53_;
						i_58_ -= i_62_ * i_53_;
						i_59_ -= i_64_ * i_53_;
						i_53_ = 0;
					}
					i_57_ <<= 16;
					i_60_ <<= 15;
					if (i_54_ < 0) {
						i_57_ -= i_65_ * i_54_;
						i_60_ -= i_66_ * i_54_;
						i_54_ = 0;
					}
					if (i_53_ != i_54_ && i_61_ < i_63_ || i_53_ == i_54_ && i_61_ > i_65_) {
						i -= i_54_;
						i_54_ -= i_53_;
						i_53_ = Rasterizer3D.lineOffsets[i_53_];
						while (--i_54_ >= 0) {
							Rasterizer3D.method372(Rasterizer.pixels, i_53_, 0, 0, i_55_ >> 16, i_56_ >> 16,
									i_58_ >> 7, i_59_ >> 7);
							i_55_ += i_61_;
							i_56_ += i_63_;
							i_58_ += i_62_;
							i_59_ += i_64_;
							i_53_ += Rasterizer.width;
						}
						while (--i >= 0) {
							Rasterizer3D.method372(Rasterizer.pixels, i_53_, 0, 0, i_55_ >> 16, i_57_ >> 16,
									i_58_ >> 7, i_60_ >> 7);
							i_55_ += i_61_;
							i_57_ += i_65_;
							i_58_ += i_62_;
							i_60_ += i_66_;
							i_53_ += Rasterizer.width;
						}
					} else {
						i -= i_54_;
						i_54_ -= i_53_;
						i_53_ = Rasterizer3D.lineOffsets[i_53_];
						while (--i_54_ >= 0) {
							Rasterizer3D.method372(Rasterizer.pixels, i_53_, 0, 0, i_56_ >> 16, i_55_ >> 16,
									i_59_ >> 7, i_58_ >> 7);
							i_55_ += i_61_;
							i_56_ += i_63_;
							i_58_ += i_62_;
							i_59_ += i_64_;
							i_53_ += Rasterizer.width;
						}
						while (--i >= 0) {
							Rasterizer3D.method372(Rasterizer.pixels, i_53_, 0, 0, i_57_ >> 16, i_55_ >> 16,
									i_60_ >> 7, i_58_ >> 7);
							i_55_ += i_61_;
							i_57_ += i_65_;
							i_58_ += i_62_;
							i_60_ += i_66_;
							i_53_ += Rasterizer.width;
						}
					}
				} else {
					i_57_ = i_56_ <<= 16;
					i_60_ = i_59_ <<= 15;
					if (i_53_ < 0) {
						i_57_ -= i_61_ * i_53_;
						i_56_ -= i_63_ * i_53_;
						i_60_ -= i_62_ * i_53_;
						i_59_ -= i_64_ * i_53_;
						i_53_ = 0;
					}
					i_55_ <<= 16;
					i_58_ <<= 15;
					if (i < 0) {
						i_55_ -= i_65_ * i;
						i_58_ -= i_66_ * i;
						i = 0;
					}
					if (i_61_ < i_63_) {
						i_54_ -= i;
						i -= i_53_;
						i_53_ = Rasterizer3D.lineOffsets[i_53_];
						while (--i >= 0) {
							Rasterizer3D.method372(Rasterizer.pixels, i_53_, 0, 0, i_57_ >> 16, i_56_ >> 16,
									i_60_ >> 7, i_59_ >> 7);
							i_57_ += i_61_;
							i_56_ += i_63_;
							i_60_ += i_62_;
							i_59_ += i_64_;
							i_53_ += Rasterizer.width;
						}
						while (--i_54_ >= 0) {
							Rasterizer3D.method372(Rasterizer.pixels, i_53_, 0, 0, i_55_ >> 16, i_56_ >> 16,
									i_58_ >> 7, i_59_ >> 7);
							i_55_ += i_65_;
							i_56_ += i_63_;
							i_58_ += i_66_;
							i_59_ += i_64_;
							i_53_ += Rasterizer.width;
						}
					} else {
						i_54_ -= i;
						i -= i_53_;
						i_53_ = Rasterizer3D.lineOffsets[i_53_];
						while (--i >= 0) {
							Rasterizer3D.method372(Rasterizer.pixels, i_53_, 0, 0, i_56_ >> 16, i_57_ >> 16,
									i_59_ >> 7, i_60_ >> 7);
							i_57_ += i_61_;
							i_56_ += i_63_;
							i_60_ += i_62_;
							i_59_ += i_64_;
							i_53_ += Rasterizer.width;
						}
						while (--i_54_ >= 0) {
							Rasterizer3D.method372(Rasterizer.pixels, i_53_, 0, 0, i_56_ >> 16, i_55_ >> 16,
									i_59_ >> 7, i_58_ >> 7);
							i_55_ += i_65_;
							i_56_ += i_63_;
							i_58_ += i_66_;
							i_59_ += i_64_;
							i_53_ += Rasterizer.width;
						}
					}
				}
			}
		} else if (i_54_ < Rasterizer.bottomY) {
			if (i > Rasterizer.bottomY) {
				i = Rasterizer.bottomY;
			}
			if (i_53_ > Rasterizer.bottomY) {
				i_53_ = Rasterizer.bottomY;
			}
			if (i < i_53_) {
				i_56_ = i_57_ <<= 16;
				i_59_ = i_60_ <<= 15;
				if (i_54_ < 0) {
					i_56_ -= i_63_ * i_54_;
					i_57_ -= i_65_ * i_54_;
					i_59_ -= i_64_ * i_54_;
					i_60_ -= i_66_ * i_54_;
					i_54_ = 0;
				}
				i_55_ <<= 16;
				i_58_ <<= 15;
				if (i < 0) {
					i_55_ -= i_61_ * i;
					i_58_ -= i_62_ * i;
					i = 0;
				}
				if (i_63_ < i_65_) {
					i_53_ -= i;
					i -= i_54_;
					i_54_ = Rasterizer3D.lineOffsets[i_54_];
					while (--i >= 0) {
						Rasterizer3D.method372(Rasterizer.pixels, i_54_, 0, 0, i_56_ >> 16, i_57_ >> 16, i_59_ >> 7,
								i_60_ >> 7);
						i_56_ += i_63_;
						i_57_ += i_65_;
						i_59_ += i_64_;
						i_60_ += i_66_;
						i_54_ += Rasterizer.width;
					}
					while (--i_53_ >= 0) {
						Rasterizer3D.method372(Rasterizer.pixels, i_54_, 0, 0, i_56_ >> 16, i_55_ >> 16, i_59_ >> 7,
								i_58_ >> 7);
						i_56_ += i_63_;
						i_55_ += i_61_;
						i_59_ += i_64_;
						i_58_ += i_62_;
						i_54_ += Rasterizer.width;
					}
				} else {
					i_53_ -= i;
					i -= i_54_;
					i_54_ = Rasterizer3D.lineOffsets[i_54_];
					while (--i >= 0) {
						Rasterizer3D.method372(Rasterizer.pixels, i_54_, 0, 0, i_57_ >> 16, i_56_ >> 16, i_60_ >> 7,
								i_59_ >> 7);
						i_56_ += i_63_;
						i_57_ += i_65_;
						i_59_ += i_64_;
						i_60_ += i_66_;
						i_54_ += Rasterizer.width;
					}
					while (--i_53_ >= 0) {
						Rasterizer3D.method372(Rasterizer.pixels, i_54_, 0, 0, i_55_ >> 16, i_56_ >> 16, i_58_ >> 7,
								i_59_ >> 7);
						i_56_ += i_63_;
						i_55_ += i_61_;
						i_59_ += i_64_;
						i_58_ += i_62_;
						i_54_ += Rasterizer.width;
					}
				}
			} else {
				i_55_ = i_57_ <<= 16;
				i_58_ = i_60_ <<= 15;
				if (i_54_ < 0) {
					i_55_ -= i_63_ * i_54_;
					i_57_ -= i_65_ * i_54_;
					i_58_ -= i_64_ * i_54_;
					i_60_ -= i_66_ * i_54_;
					i_54_ = 0;
				}
				i_56_ <<= 16;
				i_59_ <<= 15;
				if (i_53_ < 0) {
					i_56_ -= i_61_ * i_53_;
					i_59_ -= i_62_ * i_53_;
					i_53_ = 0;
				}
				if (i_63_ < i_65_) {
					i -= i_53_;
					i_53_ -= i_54_;
					i_54_ = Rasterizer3D.lineOffsets[i_54_];
					while (--i_53_ >= 0) {
						Rasterizer3D.method372(Rasterizer.pixels, i_54_, 0, 0, i_55_ >> 16, i_57_ >> 16, i_58_ >> 7,
								i_60_ >> 7);
						i_55_ += i_63_;
						i_57_ += i_65_;
						i_58_ += i_64_;
						i_60_ += i_66_;
						i_54_ += Rasterizer.width;
					}
					while (--i >= 0) {
						Rasterizer3D.method372(Rasterizer.pixels, i_54_, 0, 0, i_56_ >> 16, i_57_ >> 16, i_59_ >> 7,
								i_60_ >> 7);
						i_56_ += i_61_;
						i_57_ += i_65_;
						i_59_ += i_62_;
						i_60_ += i_66_;
						i_54_ += Rasterizer.width;
					}
				} else {
					i -= i_53_;
					i_53_ -= i_54_;
					i_54_ = Rasterizer3D.lineOffsets[i_54_];
					while (--i_53_ >= 0) {
						Rasterizer3D.method372(Rasterizer.pixels, i_54_, 0, 0, i_57_ >> 16, i_55_ >> 16, i_60_ >> 7,
								i_58_ >> 7);
						i_55_ += i_63_;
						i_57_ += i_65_;
						i_58_ += i_64_;
						i_60_ += i_66_;
						i_54_ += Rasterizer.width;
					}
					while (--i >= 0) {
						Rasterizer3D.method372(Rasterizer.pixels, i_54_, 0, 0, i_57_ >> 16, i_56_ >> 16, i_60_ >> 7,
								i_59_ >> 7);
						i_56_ += i_61_;
						i_57_ += i_65_;
						i_59_ += i_62_;
						i_60_ += i_66_;
						i_54_ += Rasterizer.width;
					}
				}
			}
		}
	}

	public static final void method372(int[] is, int i, int i_67_, int i_68_, int i_69_, int i_70_, int i_71_, int i_72_) {
		if (Rasterizer3D.textured) {
			int i_73_;
			if (Rasterizer3D.aBoolean1482) {
				if (i_70_ - i_69_ > 3) {
					i_73_ = (i_72_ - i_71_) / (i_70_ - i_69_);
				} else {
					i_73_ = 0;
				}
				if (i_70_ > Rasterizer.virtualBottomX) {
					i_70_ = Rasterizer.virtualBottomX;
				}
				if (i_69_ < 0) {
					i_71_ -= i_69_ * i_73_;
					i_69_ = 0;
				}
				if (i_69_ >= i_70_) {
					return;
				}
				i += i_69_;
				i_68_ = i_70_ - i_69_ >> 2;
				i_73_ <<= 2;
			} else {
				if (i_69_ >= i_70_) {
					return;
				}
				i += i_69_;
				i_68_ = i_70_ - i_69_ >> 2;
				if (i_68_ > 0) {
					i_73_ = (i_72_ - i_71_) * Rasterizer3D.shadowDecay[i_68_] >> 15;
				} else {
					i_73_ = 0;
				}
			}
			if (Rasterizer3D.anInt1485 == 0) {
				while (--i_68_ >= 0) {
					i_67_ = Rasterizer3D.getRgbLookupTableId[i_71_ >> 8];
					i_71_ += i_73_;
					is[i++] = i_67_;
					is[i++] = i_67_;
					is[i++] = i_67_;
					is[i++] = i_67_;
				}
				i_68_ = i_70_ - i_69_ & 0x3;
				if (i_68_ > 0) {
					i_67_ = Rasterizer3D.getRgbLookupTableId[i_71_ >> 8];
					do {
						is[i++] = i_67_;
					} while (--i_68_ > 0);
				}
			} else {
				int i_74_ = Rasterizer3D.anInt1485;
				int i_75_ = 256 - Rasterizer3D.anInt1485;
				while (--i_68_ >= 0) {
					i_67_ = Rasterizer3D.getRgbLookupTableId[i_71_ >> 8];
					i_71_ += i_73_;
					i_67_ = ((i_67_ & 0xff00ff) * i_75_ >> 8 & 0xff00ff) + ((i_67_ & 0xff00) * i_75_ >> 8 & 0xff00);
					is[i++] = i_67_ + ((is[i] & 0xff00ff) * i_74_ >> 8 & 0xff00ff)
							+ ((is[i] & 0xff00) * i_74_ >> 8 & 0xff00);
					is[i++] = i_67_ + ((is[i] & 0xff00ff) * i_74_ >> 8 & 0xff00ff)
							+ ((is[i] & 0xff00) * i_74_ >> 8 & 0xff00);
					is[i++] = i_67_ + ((is[i] & 0xff00ff) * i_74_ >> 8 & 0xff00ff)
							+ ((is[i] & 0xff00) * i_74_ >> 8 & 0xff00);
					is[i++] = i_67_ + ((is[i] & 0xff00ff) * i_74_ >> 8 & 0xff00ff)
							+ ((is[i] & 0xff00) * i_74_ >> 8 & 0xff00);
				}
				i_68_ = i_70_ - i_69_ & 0x3;
				if (i_68_ > 0) {
					i_67_ = Rasterizer3D.getRgbLookupTableId[i_71_ >> 8];
					i_67_ = ((i_67_ & 0xff00ff) * i_75_ >> 8 & 0xff00ff) + ((i_67_ & 0xff00) * i_75_ >> 8 & 0xff00);
					do {
						is[i++] = i_67_ + ((is[i] & 0xff00ff) * i_74_ >> 8 & 0xff00ff)
								+ ((is[i] & 0xff00) * i_74_ >> 8 & 0xff00);
					} while (--i_68_ > 0);
				}
			}
		} else if (i_69_ < i_70_) {
			int i_76_ = (i_72_ - i_71_) / (i_70_ - i_69_);
			if (Rasterizer3D.aBoolean1482) {
				if (i_70_ > Rasterizer.virtualBottomX) {
					i_70_ = Rasterizer.virtualBottomX;
				}
				if (i_69_ < 0) {
					i_71_ -= i_69_ * i_76_;
					i_69_ = 0;
				}
				if (i_69_ >= i_70_) {
					return;
				}
			}
			i += i_69_;
			i_68_ = i_70_ - i_69_;
			if (Rasterizer3D.anInt1485 == 0) {
				do {
					is[i++] = Rasterizer3D.getRgbLookupTableId[i_71_ >> 8];
					i_71_ += i_76_;
				} while (--i_68_ > 0);
			} else {
				int i_77_ = Rasterizer3D.anInt1485;
				int i_78_ = 256 - Rasterizer3D.anInt1485;
				do {
					i_67_ = Rasterizer3D.getRgbLookupTableId[i_71_ >> 8];
					i_71_ += i_76_;
					i_67_ = ((i_67_ & 0xff00ff) * i_78_ >> 8 & 0xff00ff) + ((i_67_ & 0xff00) * i_78_ >> 8 & 0xff00);
					is[i++] = i_67_ + ((is[i] & 0xff00ff) * i_77_ >> 8 & 0xff00ff)
							+ ((is[i] & 0xff00) * i_77_ >> 8 & 0xff00);
				} while (--i_68_ > 0);
			}
		}
	}

	public static final void method373(int i, int i_79_, int i_80_, int i_81_, int i_82_, int i_83_, int i_84_) {
		int i_85_ = 0;
		if (i_79_ != i) {
			i_85_ = (i_82_ - i_81_ << 16) / (i_79_ - i);
		}
		int i_86_ = 0;
		if (i_80_ != i_79_) {
			i_86_ = (i_83_ - i_82_ << 16) / (i_80_ - i_79_);
		}
		int i_87_ = 0;
		if (i_80_ != i) {
			i_87_ = (i_81_ - i_83_ << 16) / (i - i_80_);
		}
		if (i <= i_79_ && i <= i_80_) {
			if (i < Rasterizer.bottomY) {
				if (i_79_ > Rasterizer.bottomY) {
					i_79_ = Rasterizer.bottomY;
				}
				if (i_80_ > Rasterizer.bottomY) {
					i_80_ = Rasterizer.bottomY;
				}
				if (i_79_ < i_80_) {
					i_83_ = i_81_ <<= 16;
					if (i < 0) {
						i_83_ -= i_87_ * i;
						i_81_ -= i_85_ * i;
						i = 0;
					}
					i_82_ <<= 16;
					if (i_79_ < 0) {
						i_82_ -= i_86_ * i_79_;
						i_79_ = 0;
					}
					if (i != i_79_ && i_87_ < i_85_ || i == i_79_ && i_87_ > i_86_) {
						i_80_ -= i_79_;
						i_79_ -= i;
						i = Rasterizer3D.lineOffsets[i];
						while (--i_79_ >= 0) {
							Rasterizer3D.method374(Rasterizer.pixels, i, i_84_, 0, i_83_ >> 16, i_81_ >> 16);
							i_83_ += i_87_;
							i_81_ += i_85_;
							i += Rasterizer.width;
						}
						while (--i_80_ >= 0) {
							Rasterizer3D.method374(Rasterizer.pixels, i, i_84_, 0, i_83_ >> 16, i_82_ >> 16);
							i_83_ += i_87_;
							i_82_ += i_86_;
							i += Rasterizer.width;
						}
					} else {
						i_80_ -= i_79_;
						i_79_ -= i;
						i = Rasterizer3D.lineOffsets[i];
						while (--i_79_ >= 0) {
							Rasterizer3D.method374(Rasterizer.pixels, i, i_84_, 0, i_81_ >> 16, i_83_ >> 16);
							i_83_ += i_87_;
							i_81_ += i_85_;
							i += Rasterizer.width;
						}
						while (--i_80_ >= 0) {
							Rasterizer3D.method374(Rasterizer.pixels, i, i_84_, 0, i_82_ >> 16, i_83_ >> 16);
							i_83_ += i_87_;
							i_82_ += i_86_;
							i += Rasterizer.width;
						}
					}
				} else {
					i_82_ = i_81_ <<= 16;
					if (i < 0) {
						i_82_ -= i_87_ * i;
						i_81_ -= i_85_ * i;
						i = 0;
					}
					i_83_ <<= 16;
					if (i_80_ < 0) {
						i_83_ -= i_86_ * i_80_;
						i_80_ = 0;
					}
					if (i != i_80_ && i_87_ < i_85_ || i == i_80_ && i_86_ > i_85_) {
						i_79_ -= i_80_;
						i_80_ -= i;
						i = Rasterizer3D.lineOffsets[i];
						while (--i_80_ >= 0) {
							Rasterizer3D.method374(Rasterizer.pixels, i, i_84_, 0, i_82_ >> 16, i_81_ >> 16);
							i_82_ += i_87_;
							i_81_ += i_85_;
							i += Rasterizer.width;
						}
						while (--i_79_ >= 0) {
							Rasterizer3D.method374(Rasterizer.pixels, i, i_84_, 0, i_83_ >> 16, i_81_ >> 16);
							i_83_ += i_86_;
							i_81_ += i_85_;
							i += Rasterizer.width;
						}
					} else {
						i_79_ -= i_80_;
						i_80_ -= i;
						i = Rasterizer3D.lineOffsets[i];
						while (--i_80_ >= 0) {
							Rasterizer3D.method374(Rasterizer.pixels, i, i_84_, 0, i_81_ >> 16, i_82_ >> 16);
							i_82_ += i_87_;
							i_81_ += i_85_;
							i += Rasterizer.width;
						}
						while (--i_79_ >= 0) {
							Rasterizer3D.method374(Rasterizer.pixels, i, i_84_, 0, i_81_ >> 16, i_83_ >> 16);
							i_83_ += i_86_;
							i_81_ += i_85_;
							i += Rasterizer.width;
						}
					}
				}
			}
		} else if (i_79_ <= i_80_) {
			if (i_79_ < Rasterizer.bottomY) {
				if (i_80_ > Rasterizer.bottomY) {
					i_80_ = Rasterizer.bottomY;
				}
				if (i > Rasterizer.bottomY) {
					i = Rasterizer.bottomY;
				}
				if (i_80_ < i) {
					i_81_ = i_82_ <<= 16;
					if (i_79_ < 0) {
						i_81_ -= i_85_ * i_79_;
						i_82_ -= i_86_ * i_79_;
						i_79_ = 0;
					}
					i_83_ <<= 16;
					if (i_80_ < 0) {
						i_83_ -= i_87_ * i_80_;
						i_80_ = 0;
					}
					if (i_79_ != i_80_ && i_85_ < i_86_ || i_79_ == i_80_ && i_85_ > i_87_) {
						i -= i_80_;
						i_80_ -= i_79_;
						i_79_ = Rasterizer3D.lineOffsets[i_79_];
						while (--i_80_ >= 0) {
							Rasterizer3D.method374(Rasterizer.pixels, i_79_, i_84_, 0, i_81_ >> 16, i_82_ >> 16);
							i_81_ += i_85_;
							i_82_ += i_86_;
							i_79_ += Rasterizer.width;
						}
						while (--i >= 0) {
							Rasterizer3D.method374(Rasterizer.pixels, i_79_, i_84_, 0, i_81_ >> 16, i_83_ >> 16);
							i_81_ += i_85_;
							i_83_ += i_87_;
							i_79_ += Rasterizer.width;
						}
					} else {
						i -= i_80_;
						i_80_ -= i_79_;
						i_79_ = Rasterizer3D.lineOffsets[i_79_];
						while (--i_80_ >= 0) {
							Rasterizer3D.method374(Rasterizer.pixels, i_79_, i_84_, 0, i_82_ >> 16, i_81_ >> 16);
							i_81_ += i_85_;
							i_82_ += i_86_;
							i_79_ += Rasterizer.width;
						}
						while (--i >= 0) {
							Rasterizer3D.method374(Rasterizer.pixels, i_79_, i_84_, 0, i_83_ >> 16, i_81_ >> 16);
							i_81_ += i_85_;
							i_83_ += i_87_;
							i_79_ += Rasterizer.width;
						}
					}
				} else {
					i_83_ = i_82_ <<= 16;
					if (i_79_ < 0) {
						i_83_ -= i_85_ * i_79_;
						i_82_ -= i_86_ * i_79_;
						i_79_ = 0;
					}
					i_81_ <<= 16;
					if (i < 0) {
						i_81_ -= i_87_ * i;
						i = 0;
					}
					if (i_85_ < i_86_) {
						i_80_ -= i;
						i -= i_79_;
						i_79_ = Rasterizer3D.lineOffsets[i_79_];
						while (--i >= 0) {
							Rasterizer3D.method374(Rasterizer.pixels, i_79_, i_84_, 0, i_83_ >> 16, i_82_ >> 16);
							i_83_ += i_85_;
							i_82_ += i_86_;
							i_79_ += Rasterizer.width;
						}
						while (--i_80_ >= 0) {
							Rasterizer3D.method374(Rasterizer.pixels, i_79_, i_84_, 0, i_81_ >> 16, i_82_ >> 16);
							i_81_ += i_87_;
							i_82_ += i_86_;
							i_79_ += Rasterizer.width;
						}
					} else {
						i_80_ -= i;
						i -= i_79_;
						i_79_ = Rasterizer3D.lineOffsets[i_79_];
						while (--i >= 0) {
							Rasterizer3D.method374(Rasterizer.pixels, i_79_, i_84_, 0, i_82_ >> 16, i_83_ >> 16);
							i_83_ += i_85_;
							i_82_ += i_86_;
							i_79_ += Rasterizer.width;
						}
						while (--i_80_ >= 0) {
							Rasterizer3D.method374(Rasterizer.pixels, i_79_, i_84_, 0, i_82_ >> 16, i_81_ >> 16);
							i_81_ += i_87_;
							i_82_ += i_86_;
							i_79_ += Rasterizer.width;
						}
					}
				}
			}
		} else if (i_80_ < Rasterizer.bottomY) {
			if (i > Rasterizer.bottomY) {
				i = Rasterizer.bottomY;
			}
			if (i_79_ > Rasterizer.bottomY) {
				i_79_ = Rasterizer.bottomY;
			}
			if (i < i_79_) {
				i_82_ = i_83_ <<= 16;
				if (i_80_ < 0) {
					i_82_ -= i_86_ * i_80_;
					i_83_ -= i_87_ * i_80_;
					i_80_ = 0;
				}
				i_81_ <<= 16;
				if (i < 0) {
					i_81_ -= i_85_ * i;
					i = 0;
				}
				if (i_86_ < i_87_) {
					i_79_ -= i;
					i -= i_80_;
					i_80_ = Rasterizer3D.lineOffsets[i_80_];
					while (--i >= 0) {
						Rasterizer3D.method374(Rasterizer.pixels, i_80_, i_84_, 0, i_82_ >> 16, i_83_ >> 16);
						i_82_ += i_86_;
						i_83_ += i_87_;
						i_80_ += Rasterizer.width;
					}
					while (--i_79_ >= 0) {
						Rasterizer3D.method374(Rasterizer.pixels, i_80_, i_84_, 0, i_82_ >> 16, i_81_ >> 16);
						i_82_ += i_86_;
						i_81_ += i_85_;
						i_80_ += Rasterizer.width;
					}
				} else {
					i_79_ -= i;
					i -= i_80_;
					i_80_ = Rasterizer3D.lineOffsets[i_80_];
					while (--i >= 0) {
						Rasterizer3D.method374(Rasterizer.pixels, i_80_, i_84_, 0, i_83_ >> 16, i_82_ >> 16);
						i_82_ += i_86_;
						i_83_ += i_87_;
						i_80_ += Rasterizer.width;
					}
					while (--i_79_ >= 0) {
						Rasterizer3D.method374(Rasterizer.pixels, i_80_, i_84_, 0, i_81_ >> 16, i_82_ >> 16);
						i_82_ += i_86_;
						i_81_ += i_85_;
						i_80_ += Rasterizer.width;
					}
				}
			} else {
				i_81_ = i_83_ <<= 16;
				if (i_80_ < 0) {
					i_81_ -= i_86_ * i_80_;
					i_83_ -= i_87_ * i_80_;
					i_80_ = 0;
				}
				i_82_ <<= 16;
				if (i_79_ < 0) {
					i_82_ -= i_85_ * i_79_;
					i_79_ = 0;
				}
				if (i_86_ < i_87_) {
					i -= i_79_;
					i_79_ -= i_80_;
					i_80_ = Rasterizer3D.lineOffsets[i_80_];
					while (--i_79_ >= 0) {
						Rasterizer3D.method374(Rasterizer.pixels, i_80_, i_84_, 0, i_81_ >> 16, i_83_ >> 16);
						i_81_ += i_86_;
						i_83_ += i_87_;
						i_80_ += Rasterizer.width;
					}
					while (--i >= 0) {
						Rasterizer3D.method374(Rasterizer.pixels, i_80_, i_84_, 0, i_82_ >> 16, i_83_ >> 16);
						i_82_ += i_85_;
						i_83_ += i_87_;
						i_80_ += Rasterizer.width;
					}
				} else {
					i -= i_79_;
					i_79_ -= i_80_;
					i_80_ = Rasterizer3D.lineOffsets[i_80_];
					while (--i_79_ >= 0) {
						Rasterizer3D.method374(Rasterizer.pixels, i_80_, i_84_, 0, i_83_ >> 16, i_81_ >> 16);
						i_81_ += i_86_;
						i_83_ += i_87_;
						i_80_ += Rasterizer.width;
					}
					while (--i >= 0) {
						Rasterizer3D.method374(Rasterizer.pixels, i_80_, i_84_, 0, i_83_ >> 16, i_82_ >> 16);
						i_82_ += i_85_;
						i_83_ += i_87_;
						i_80_ += Rasterizer.width;
					}
				}
			}
		}
	}

	public static final void method374(int[] is, int i, int i_88_, int i_89_, int i_90_, int i_91_) {
		if (Rasterizer3D.aBoolean1482) {
			if (i_91_ > Rasterizer.virtualBottomX) {
				i_91_ = Rasterizer.virtualBottomX;
			}
			if (i_90_ < 0) {
				i_90_ = 0;
			}
		}
		if (i_90_ < i_91_) {
			i += i_90_;
			i_89_ = i_91_ - i_90_ >> 2;
			if (Rasterizer3D.anInt1485 == 0) {
				while (--i_89_ >= 0) {
					is[i++] = i_88_;
					is[i++] = i_88_;
					is[i++] = i_88_;
					is[i++] = i_88_;
				}
				i_89_ = i_91_ - i_90_ & 0x3;
				while (--i_89_ >= 0) {
					is[i++] = i_88_;
				}
			} else {
				int i_92_ = Rasterizer3D.anInt1485;
				int i_93_ = 256 - Rasterizer3D.anInt1485;
				i_88_ = ((i_88_ & 0xff00ff) * i_93_ >> 8 & 0xff00ff) + ((i_88_ & 0xff00) * i_93_ >> 8 & 0xff00);
				while (--i_89_ >= 0) {
					is[i++] = i_88_ + ((is[i] & 0xff00ff) * i_92_ >> 8 & 0xff00ff)
							+ ((is[i] & 0xff00) * i_92_ >> 8 & 0xff00);
					is[i++] = i_88_ + ((is[i] & 0xff00ff) * i_92_ >> 8 & 0xff00ff)
							+ ((is[i] & 0xff00) * i_92_ >> 8 & 0xff00);
					is[i++] = i_88_ + ((is[i] & 0xff00ff) * i_92_ >> 8 & 0xff00ff)
							+ ((is[i] & 0xff00) * i_92_ >> 8 & 0xff00);
					is[i++] = i_88_ + ((is[i] & 0xff00ff) * i_92_ >> 8 & 0xff00ff)
							+ ((is[i] & 0xff00) * i_92_ >> 8 & 0xff00);
				}
				i_89_ = i_91_ - i_90_ & 0x3;
				while (--i_89_ >= 0) {
					is[i++] = i_88_ + ((is[i] & 0xff00ff) * i_92_ >> 8 & 0xff00ff)
							+ ((is[i] & 0xff00) * i_92_ >> 8 & 0xff00);
				}
			}
		}
	}

	public static final void method375(int i, int i_94_, int i_95_, int i_96_, int i_97_, int i_98_, int i_99_,
			int i_100_, int i_101_, int i_102_, int i_103_, int i_104_, int i_105_, int i_106_, int i_107_, int i_108_,
			int i_109_, int i_110_, int i_111_) {
		int[] is = Rasterizer3D.method368(i_111_);
		Rasterizer3D.aBoolean1483 = !Rasterizer3D.aBooleanArray1495[i_111_];
		i_103_ = i_102_ - i_103_;
		i_106_ = i_105_ - i_106_;
		i_109_ = i_108_ - i_109_;
		i_104_ -= i_102_;
		i_107_ -= i_105_;
		i_110_ -= i_108_;
		int i_112_ = i_104_ * i_105_ - i_107_ * i_102_ << 14;
		int i_113_ = i_107_ * i_108_ - i_110_ * i_105_ << 8;
		int i_114_ = i_110_ * i_102_ - i_104_ * i_108_ << 5;
		int i_115_ = i_103_ * i_105_ - i_106_ * i_102_ << 14;
		int i_116_ = i_106_ * i_108_ - i_109_ * i_105_ << 8;
		int i_117_ = i_109_ * i_102_ - i_103_ * i_108_ << 5;
		int i_118_ = i_106_ * i_104_ - i_103_ * i_107_ << 14;
		int i_119_ = i_109_ * i_107_ - i_106_ * i_110_ << 8;
		int i_120_ = i_103_ * i_110_ - i_109_ * i_104_ << 5;
		int i_121_ = 0;
		int i_122_ = 0;
		if (i_94_ != i) {
			i_121_ = (i_97_ - i_96_ << 16) / (i_94_ - i);
			i_122_ = (i_100_ - i_99_ << 16) / (i_94_ - i);
		}
		int i_123_ = 0;
		int i_124_ = 0;
		if (i_95_ != i_94_) {
			i_123_ = (i_98_ - i_97_ << 16) / (i_95_ - i_94_);
			i_124_ = (i_101_ - i_100_ << 16) / (i_95_ - i_94_);
		}
		int i_125_ = 0;
		int i_126_ = 0;
		if (i_95_ != i) {
			i_125_ = (i_96_ - i_98_ << 16) / (i - i_95_);
			i_126_ = (i_99_ - i_101_ << 16) / (i - i_95_);
		}
		if (i <= i_94_ && i <= i_95_) {
			if (i < Rasterizer.bottomY) {
				if (i_94_ > Rasterizer.bottomY) {
					i_94_ = Rasterizer.bottomY;
				}
				if (i_95_ > Rasterizer.bottomY) {
					i_95_ = Rasterizer.bottomY;
				}
				if (i_94_ < i_95_) {
					i_98_ = i_96_ <<= 16;
					i_101_ = i_99_ <<= 16;
					if (i < 0) {
						i_98_ -= i_125_ * i;
						i_96_ -= i_121_ * i;
						i_101_ -= i_126_ * i;
						i_99_ -= i_122_ * i;
						i = 0;
					}
					i_97_ <<= 16;
					i_100_ <<= 16;
					if (i_94_ < 0) {
						i_97_ -= i_123_ * i_94_;
						i_100_ -= i_124_ * i_94_;
						i_94_ = 0;
					}
					int i_127_ = i - Rasterizer3D.centerY;
					i_112_ += i_114_ * i_127_;
					i_115_ += i_117_ * i_127_;
					i_118_ += i_120_ * i_127_;
					if (i != i_94_ && i_125_ < i_121_ || i == i_94_ && i_125_ > i_123_) {
						i_95_ -= i_94_;
						i_94_ -= i;
						i = Rasterizer3D.lineOffsets[i];
						while (--i_94_ >= 0) {
							Rasterizer3D.method376(Rasterizer.pixels, is, 0, 0, i, i_98_ >> 16, i_96_ >> 16,
									i_101_ >> 8, i_99_ >> 8, i_112_, i_115_, i_118_, i_113_, i_116_, i_119_);
							i_98_ += i_125_;
							i_96_ += i_121_;
							i_101_ += i_126_;
							i_99_ += i_122_;
							i += Rasterizer.width;
							i_112_ += i_114_;
							i_115_ += i_117_;
							i_118_ += i_120_;
						}
						while (--i_95_ >= 0) {
							Rasterizer3D.method376(Rasterizer.pixels, is, 0, 0, i, i_98_ >> 16, i_97_ >> 16,
									i_101_ >> 8, i_100_ >> 8, i_112_, i_115_, i_118_, i_113_, i_116_, i_119_);
							i_98_ += i_125_;
							i_97_ += i_123_;
							i_101_ += i_126_;
							i_100_ += i_124_;
							i += Rasterizer.width;
							i_112_ += i_114_;
							i_115_ += i_117_;
							i_118_ += i_120_;
						}
					} else {
						i_95_ -= i_94_;
						i_94_ -= i;
						i = Rasterizer3D.lineOffsets[i];
						while (--i_94_ >= 0) {
							Rasterizer3D.method376(Rasterizer.pixels, is, 0, 0, i, i_96_ >> 16, i_98_ >> 16,
									i_99_ >> 8, i_101_ >> 8, i_112_, i_115_, i_118_, i_113_, i_116_, i_119_);
							i_98_ += i_125_;
							i_96_ += i_121_;
							i_101_ += i_126_;
							i_99_ += i_122_;
							i += Rasterizer.width;
							i_112_ += i_114_;
							i_115_ += i_117_;
							i_118_ += i_120_;
						}
						while (--i_95_ >= 0) {
							Rasterizer3D.method376(Rasterizer.pixels, is, 0, 0, i, i_97_ >> 16, i_98_ >> 16,
									i_100_ >> 8, i_101_ >> 8, i_112_, i_115_, i_118_, i_113_, i_116_, i_119_);
							i_98_ += i_125_;
							i_97_ += i_123_;
							i_101_ += i_126_;
							i_100_ += i_124_;
							i += Rasterizer.width;
							i_112_ += i_114_;
							i_115_ += i_117_;
							i_118_ += i_120_;
						}
					}
				} else {
					i_97_ = i_96_ <<= 16;
					i_100_ = i_99_ <<= 16;
					if (i < 0) {
						i_97_ -= i_125_ * i;
						i_96_ -= i_121_ * i;
						i_100_ -= i_126_ * i;
						i_99_ -= i_122_ * i;
						i = 0;
					}
					i_98_ <<= 16;
					i_101_ <<= 16;
					if (i_95_ < 0) {
						i_98_ -= i_123_ * i_95_;
						i_101_ -= i_124_ * i_95_;
						i_95_ = 0;
					}
					int i_128_ = i - Rasterizer3D.centerY;
					i_112_ += i_114_ * i_128_;
					i_115_ += i_117_ * i_128_;
					i_118_ += i_120_ * i_128_;
					if (i != i_95_ && i_125_ < i_121_ || i == i_95_ && i_123_ > i_121_) {
						i_94_ -= i_95_;
						i_95_ -= i;
						i = Rasterizer3D.lineOffsets[i];
						while (--i_95_ >= 0) {
							Rasterizer3D.method376(Rasterizer.pixels, is, 0, 0, i, i_97_ >> 16, i_96_ >> 16,
									i_100_ >> 8, i_99_ >> 8, i_112_, i_115_, i_118_, i_113_, i_116_, i_119_);
							i_97_ += i_125_;
							i_96_ += i_121_;
							i_100_ += i_126_;
							i_99_ += i_122_;
							i += Rasterizer.width;
							i_112_ += i_114_;
							i_115_ += i_117_;
							i_118_ += i_120_;
						}
						while (--i_94_ >= 0) {
							Rasterizer3D.method376(Rasterizer.pixels, is, 0, 0, i, i_98_ >> 16, i_96_ >> 16,
									i_101_ >> 8, i_99_ >> 8, i_112_, i_115_, i_118_, i_113_, i_116_, i_119_);
							i_98_ += i_123_;
							i_96_ += i_121_;
							i_101_ += i_124_;
							i_99_ += i_122_;
							i += Rasterizer.width;
							i_112_ += i_114_;
							i_115_ += i_117_;
							i_118_ += i_120_;
						}
					} else {
						i_94_ -= i_95_;
						i_95_ -= i;
						i = Rasterizer3D.lineOffsets[i];
						while (--i_95_ >= 0) {
							Rasterizer3D.method376(Rasterizer.pixels, is, 0, 0, i, i_96_ >> 16, i_97_ >> 16,
									i_99_ >> 8, i_100_ >> 8, i_112_, i_115_, i_118_, i_113_, i_116_, i_119_);
							i_97_ += i_125_;
							i_96_ += i_121_;
							i_100_ += i_126_;
							i_99_ += i_122_;
							i += Rasterizer.width;
							i_112_ += i_114_;
							i_115_ += i_117_;
							i_118_ += i_120_;
						}
						while (--i_94_ >= 0) {
							Rasterizer3D.method376(Rasterizer.pixels, is, 0, 0, i, i_96_ >> 16, i_98_ >> 16,
									i_99_ >> 8, i_101_ >> 8, i_112_, i_115_, i_118_, i_113_, i_116_, i_119_);
							i_98_ += i_123_;
							i_96_ += i_121_;
							i_101_ += i_124_;
							i_99_ += i_122_;
							i += Rasterizer.width;
							i_112_ += i_114_;
							i_115_ += i_117_;
							i_118_ += i_120_;
						}
					}
				}
			}
		} else if (i_94_ <= i_95_) {
			if (i_94_ < Rasterizer.bottomY) {
				if (i_95_ > Rasterizer.bottomY) {
					i_95_ = Rasterizer.bottomY;
				}
				if (i > Rasterizer.bottomY) {
					i = Rasterizer.bottomY;
				}
				if (i_95_ < i) {
					i_96_ = i_97_ <<= 16;
					i_99_ = i_100_ <<= 16;
					if (i_94_ < 0) {
						i_96_ -= i_121_ * i_94_;
						i_97_ -= i_123_ * i_94_;
						i_99_ -= i_122_ * i_94_;
						i_100_ -= i_124_ * i_94_;
						i_94_ = 0;
					}
					i_98_ <<= 16;
					i_101_ <<= 16;
					if (i_95_ < 0) {
						i_98_ -= i_125_ * i_95_;
						i_101_ -= i_126_ * i_95_;
						i_95_ = 0;
					}
					int i_129_ = i_94_ - Rasterizer3D.centerY;
					i_112_ += i_114_ * i_129_;
					i_115_ += i_117_ * i_129_;
					i_118_ += i_120_ * i_129_;
					if (i_94_ != i_95_ && i_121_ < i_123_ || i_94_ == i_95_ && i_121_ > i_125_) {
						i -= i_95_;
						i_95_ -= i_94_;
						i_94_ = Rasterizer3D.lineOffsets[i_94_];
						while (--i_95_ >= 0) {
							Rasterizer3D.method376(Rasterizer.pixels, is, 0, 0, i_94_, i_96_ >> 16, i_97_ >> 16,
									i_99_ >> 8, i_100_ >> 8, i_112_, i_115_, i_118_, i_113_, i_116_, i_119_);
							i_96_ += i_121_;
							i_97_ += i_123_;
							i_99_ += i_122_;
							i_100_ += i_124_;
							i_94_ += Rasterizer.width;
							i_112_ += i_114_;
							i_115_ += i_117_;
							i_118_ += i_120_;
						}
						while (--i >= 0) {
							Rasterizer3D.method376(Rasterizer.pixels, is, 0, 0, i_94_, i_96_ >> 16, i_98_ >> 16,
									i_99_ >> 8, i_101_ >> 8, i_112_, i_115_, i_118_, i_113_, i_116_, i_119_);
							i_96_ += i_121_;
							i_98_ += i_125_;
							i_99_ += i_122_;
							i_101_ += i_126_;
							i_94_ += Rasterizer.width;
							i_112_ += i_114_;
							i_115_ += i_117_;
							i_118_ += i_120_;
						}
					} else {
						i -= i_95_;
						i_95_ -= i_94_;
						i_94_ = Rasterizer3D.lineOffsets[i_94_];
						while (--i_95_ >= 0) {
							Rasterizer3D.method376(Rasterizer.pixels, is, 0, 0, i_94_, i_97_ >> 16, i_96_ >> 16,
									i_100_ >> 8, i_99_ >> 8, i_112_, i_115_, i_118_, i_113_, i_116_, i_119_);
							i_96_ += i_121_;
							i_97_ += i_123_;
							i_99_ += i_122_;
							i_100_ += i_124_;
							i_94_ += Rasterizer.width;
							i_112_ += i_114_;
							i_115_ += i_117_;
							i_118_ += i_120_;
						}
						while (--i >= 0) {
							Rasterizer3D.method376(Rasterizer.pixels, is, 0, 0, i_94_, i_98_ >> 16, i_96_ >> 16,
									i_101_ >> 8, i_99_ >> 8, i_112_, i_115_, i_118_, i_113_, i_116_, i_119_);
							i_96_ += i_121_;
							i_98_ += i_125_;
							i_99_ += i_122_;
							i_101_ += i_126_;
							i_94_ += Rasterizer.width;
							i_112_ += i_114_;
							i_115_ += i_117_;
							i_118_ += i_120_;
						}
					}
				} else {
					i_98_ = i_97_ <<= 16;
					i_101_ = i_100_ <<= 16;
					if (i_94_ < 0) {
						i_98_ -= i_121_ * i_94_;
						i_97_ -= i_123_ * i_94_;
						i_101_ -= i_122_ * i_94_;
						i_100_ -= i_124_ * i_94_;
						i_94_ = 0;
					}
					i_96_ <<= 16;
					i_99_ <<= 16;
					if (i < 0) {
						i_96_ -= i_125_ * i;
						i_99_ -= i_126_ * i;
						i = 0;
					}
					int i_130_ = i_94_ - Rasterizer3D.centerY;
					i_112_ += i_114_ * i_130_;
					i_115_ += i_117_ * i_130_;
					i_118_ += i_120_ * i_130_;
					if (i_121_ < i_123_) {
						i_95_ -= i;
						i -= i_94_;
						i_94_ = Rasterizer3D.lineOffsets[i_94_];
						while (--i >= 0) {
							Rasterizer3D.method376(Rasterizer.pixels, is, 0, 0, i_94_, i_98_ >> 16, i_97_ >> 16,
									i_101_ >> 8, i_100_ >> 8, i_112_, i_115_, i_118_, i_113_, i_116_, i_119_);
							i_98_ += i_121_;
							i_97_ += i_123_;
							i_101_ += i_122_;
							i_100_ += i_124_;
							i_94_ += Rasterizer.width;
							i_112_ += i_114_;
							i_115_ += i_117_;
							i_118_ += i_120_;
						}
						while (--i_95_ >= 0) {
							Rasterizer3D.method376(Rasterizer.pixels, is, 0, 0, i_94_, i_96_ >> 16, i_97_ >> 16,
									i_99_ >> 8, i_100_ >> 8, i_112_, i_115_, i_118_, i_113_, i_116_, i_119_);
							i_96_ += i_125_;
							i_97_ += i_123_;
							i_99_ += i_126_;
							i_100_ += i_124_;
							i_94_ += Rasterizer.width;
							i_112_ += i_114_;
							i_115_ += i_117_;
							i_118_ += i_120_;
						}
					} else {
						i_95_ -= i;
						i -= i_94_;
						i_94_ = Rasterizer3D.lineOffsets[i_94_];
						while (--i >= 0) {
							Rasterizer3D.method376(Rasterizer.pixels, is, 0, 0, i_94_, i_97_ >> 16, i_98_ >> 16,
									i_100_ >> 8, i_101_ >> 8, i_112_, i_115_, i_118_, i_113_, i_116_, i_119_);
							i_98_ += i_121_;
							i_97_ += i_123_;
							i_101_ += i_122_;
							i_100_ += i_124_;
							i_94_ += Rasterizer.width;
							i_112_ += i_114_;
							i_115_ += i_117_;
							i_118_ += i_120_;
						}
						while (--i_95_ >= 0) {
							Rasterizer3D.method376(Rasterizer.pixels, is, 0, 0, i_94_, i_97_ >> 16, i_96_ >> 16,
									i_100_ >> 8, i_99_ >> 8, i_112_, i_115_, i_118_, i_113_, i_116_, i_119_);
							i_96_ += i_125_;
							i_97_ += i_123_;
							i_99_ += i_126_;
							i_100_ += i_124_;
							i_94_ += Rasterizer.width;
							i_112_ += i_114_;
							i_115_ += i_117_;
							i_118_ += i_120_;
						}
					}
				}
			}
		} else if (i_95_ < Rasterizer.bottomY) {
			if (i > Rasterizer.bottomY) {
				i = Rasterizer.bottomY;
			}
			if (i_94_ > Rasterizer.bottomY) {
				i_94_ = Rasterizer.bottomY;
			}
			if (i < i_94_) {
				i_97_ = i_98_ <<= 16;
				i_100_ = i_101_ <<= 16;
				if (i_95_ < 0) {
					i_97_ -= i_123_ * i_95_;
					i_98_ -= i_125_ * i_95_;
					i_100_ -= i_124_ * i_95_;
					i_101_ -= i_126_ * i_95_;
					i_95_ = 0;
				}
				i_96_ <<= 16;
				i_99_ <<= 16;
				if (i < 0) {
					i_96_ -= i_121_ * i;
					i_99_ -= i_122_ * i;
					i = 0;
				}
				int i_131_ = i_95_ - Rasterizer3D.centerY;
				i_112_ += i_114_ * i_131_;
				i_115_ += i_117_ * i_131_;
				i_118_ += i_120_ * i_131_;
				if (i_123_ < i_125_) {
					i_94_ -= i;
					i -= i_95_;
					i_95_ = Rasterizer3D.lineOffsets[i_95_];
					while (--i >= 0) {
						Rasterizer3D.method376(Rasterizer.pixels, is, 0, 0, i_95_, i_97_ >> 16, i_98_ >> 16,
								i_100_ >> 8, i_101_ >> 8, i_112_, i_115_, i_118_, i_113_, i_116_, i_119_);
						i_97_ += i_123_;
						i_98_ += i_125_;
						i_100_ += i_124_;
						i_101_ += i_126_;
						i_95_ += Rasterizer.width;
						i_112_ += i_114_;
						i_115_ += i_117_;
						i_118_ += i_120_;
					}
					while (--i_94_ >= 0) {
						Rasterizer3D.method376(Rasterizer.pixels, is, 0, 0, i_95_, i_97_ >> 16, i_96_ >> 16,
								i_100_ >> 8, i_99_ >> 8, i_112_, i_115_, i_118_, i_113_, i_116_, i_119_);
						i_97_ += i_123_;
						i_96_ += i_121_;
						i_100_ += i_124_;
						i_99_ += i_122_;
						i_95_ += Rasterizer.width;
						i_112_ += i_114_;
						i_115_ += i_117_;
						i_118_ += i_120_;
					}
				} else {
					i_94_ -= i;
					i -= i_95_;
					i_95_ = Rasterizer3D.lineOffsets[i_95_];
					while (--i >= 0) {
						Rasterizer3D.method376(Rasterizer.pixels, is, 0, 0, i_95_, i_98_ >> 16, i_97_ >> 16,
								i_101_ >> 8, i_100_ >> 8, i_112_, i_115_, i_118_, i_113_, i_116_, i_119_);
						i_97_ += i_123_;
						i_98_ += i_125_;
						i_100_ += i_124_;
						i_101_ += i_126_;
						i_95_ += Rasterizer.width;
						i_112_ += i_114_;
						i_115_ += i_117_;
						i_118_ += i_120_;
					}
					while (--i_94_ >= 0) {
						Rasterizer3D.method376(Rasterizer.pixels, is, 0, 0, i_95_, i_96_ >> 16, i_97_ >> 16,
								i_99_ >> 8, i_100_ >> 8, i_112_, i_115_, i_118_, i_113_, i_116_, i_119_);
						i_97_ += i_123_;
						i_96_ += i_121_;
						i_100_ += i_124_;
						i_99_ += i_122_;
						i_95_ += Rasterizer.width;
						i_112_ += i_114_;
						i_115_ += i_117_;
						i_118_ += i_120_;
					}
				}
			} else {
				i_96_ = i_98_ <<= 16;
				i_99_ = i_101_ <<= 16;
				if (i_95_ < 0) {
					i_96_ -= i_123_ * i_95_;
					i_98_ -= i_125_ * i_95_;
					i_99_ -= i_124_ * i_95_;
					i_101_ -= i_126_ * i_95_;
					i_95_ = 0;
				}
				i_97_ <<= 16;
				i_100_ <<= 16;
				if (i_94_ < 0) {
					i_97_ -= i_121_ * i_94_;
					i_100_ -= i_122_ * i_94_;
					i_94_ = 0;
				}
				int i_132_ = i_95_ - Rasterizer3D.centerY;
				i_112_ += i_114_ * i_132_;
				i_115_ += i_117_ * i_132_;
				i_118_ += i_120_ * i_132_;
				if (i_123_ < i_125_) {
					i -= i_94_;
					i_94_ -= i_95_;
					i_95_ = Rasterizer3D.lineOffsets[i_95_];
					while (--i_94_ >= 0) {
						Rasterizer3D.method376(Rasterizer.pixels, is, 0, 0, i_95_, i_96_ >> 16, i_98_ >> 16,
								i_99_ >> 8, i_101_ >> 8, i_112_, i_115_, i_118_, i_113_, i_116_, i_119_);
						i_96_ += i_123_;
						i_98_ += i_125_;
						i_99_ += i_124_;
						i_101_ += i_126_;
						i_95_ += Rasterizer.width;
						i_112_ += i_114_;
						i_115_ += i_117_;
						i_118_ += i_120_;
					}
					while (--i >= 0) {
						Rasterizer3D.method376(Rasterizer.pixels, is, 0, 0, i_95_, i_97_ >> 16, i_98_ >> 16,
								i_100_ >> 8, i_101_ >> 8, i_112_, i_115_, i_118_, i_113_, i_116_, i_119_);
						i_97_ += i_121_;
						i_98_ += i_125_;
						i_100_ += i_122_;
						i_101_ += i_126_;
						i_95_ += Rasterizer.width;
						i_112_ += i_114_;
						i_115_ += i_117_;
						i_118_ += i_120_;
					}
				} else {
					i -= i_94_;
					i_94_ -= i_95_;
					i_95_ = Rasterizer3D.lineOffsets[i_95_];
					while (--i_94_ >= 0) {
						Rasterizer3D.method376(Rasterizer.pixels, is, 0, 0, i_95_, i_98_ >> 16, i_96_ >> 16,
								i_101_ >> 8, i_99_ >> 8, i_112_, i_115_, i_118_, i_113_, i_116_, i_119_);
						i_96_ += i_123_;
						i_98_ += i_125_;
						i_99_ += i_124_;
						i_101_ += i_126_;
						i_95_ += Rasterizer.width;
						i_112_ += i_114_;
						i_115_ += i_117_;
						i_118_ += i_120_;
					}
					while (--i >= 0) {
						Rasterizer3D.method376(Rasterizer.pixels, is, 0, 0, i_95_, i_98_ >> 16, i_97_ >> 16,
								i_101_ >> 8, i_100_ >> 8, i_112_, i_115_, i_118_, i_113_, i_116_, i_119_);
						i_97_ += i_121_;
						i_98_ += i_125_;
						i_100_ += i_122_;
						i_101_ += i_126_;
						i_95_ += Rasterizer.width;
						i_112_ += i_114_;
						i_115_ += i_117_;
						i_118_ += i_120_;
					}
				}
			}
		}
	}

	public static final void method376(int[] is, int[] is_133_, int i, int i_134_, int i_135_, int i_136_, int i_137_,
			int i_138_, int i_139_, int i_140_, int i_141_, int i_142_, int i_143_, int i_144_, int i_145_) {
		if (i_136_ < i_137_) {
			int i_146_;
			int i_147_;
			if (Rasterizer3D.aBoolean1482) {
				i_147_ = (i_139_ - i_138_) / (i_137_ - i_136_);
				if (i_137_ > Rasterizer.virtualBottomX) {
					i_137_ = Rasterizer.virtualBottomX;
				}
				if (i_136_ < 0) {
					i_138_ -= i_136_ * i_147_;
					i_136_ = 0;
				}
				if (i_136_ >= i_137_) {
					return;
				}
				i_146_ = i_137_ - i_136_ >> 3;
				i_147_ <<= 12;
				i_138_ <<= 9;
			} else {
				if (i_137_ - i_136_ > 7) {
					i_146_ = i_137_ - i_136_ >> 3;
					i_147_ = (i_139_ - i_138_) * Rasterizer3D.shadowDecay[i_146_] >> 6;
				} else {
					i_146_ = 0;
					i_147_ = 0;
				}
				i_138_ <<= 9;
			}
			i_135_ += i_136_;
			if (Rasterizer3D.lowMemory) {
				int i_148_ = 0;
				int i_149_ = 0;
				int i_150_ = i_136_ - Rasterizer3D.centerX;
				i_140_ += (i_143_ >> 3) * i_150_;
				i_141_ += (i_144_ >> 3) * i_150_;
				i_142_ += (i_145_ >> 3) * i_150_;
				int i_151_ = i_142_ >> 12;
				if (i_151_ != 0) {
					i = i_140_ / i_151_;
					i_134_ = i_141_ / i_151_;
					if (i < 0) {
						i = 0;
					} else if (i > 4032) {
						i = 4032;
					}
				}
				i_140_ += i_143_;
				i_141_ += i_144_;
				i_142_ += i_145_;
				i_151_ = i_142_ >> 12;
				if (i_151_ != 0) {
					i_148_ = i_140_ / i_151_;
					i_149_ = i_141_ / i_151_;
					if (i_148_ < 7) {
						i_148_ = 7;
					} else if (i_148_ > 4032) {
						i_148_ = 4032;
					}
				}
				int i_152_ = i_148_ - i >> 3;
				int i_153_ = i_149_ - i_134_ >> 3;
				i += (i_138_ & 0x600000) >> 3;
				int i_154_ = i_138_ >> 23;
				if (Rasterizer3D.aBoolean1483) {
					while (i_146_-- > 0) {
						is[i_135_++] = is_133_[(i_134_ & 0xfc0) + (i >> 6)] >>> i_154_;
						i += i_152_;
						i_134_ += i_153_;
						is[i_135_++] = is_133_[(i_134_ & 0xfc0) + (i >> 6)] >>> i_154_;
						i += i_152_;
						i_134_ += i_153_;
						is[i_135_++] = is_133_[(i_134_ & 0xfc0) + (i >> 6)] >>> i_154_;
						i += i_152_;
						i_134_ += i_153_;
						is[i_135_++] = is_133_[(i_134_ & 0xfc0) + (i >> 6)] >>> i_154_;
						i += i_152_;
						i_134_ += i_153_;
						is[i_135_++] = is_133_[(i_134_ & 0xfc0) + (i >> 6)] >>> i_154_;
						i += i_152_;
						i_134_ += i_153_;
						is[i_135_++] = is_133_[(i_134_ & 0xfc0) + (i >> 6)] >>> i_154_;
						i += i_152_;
						i_134_ += i_153_;
						is[i_135_++] = is_133_[(i_134_ & 0xfc0) + (i >> 6)] >>> i_154_;
						i += i_152_;
						i_134_ += i_153_;
						is[i_135_++] = is_133_[(i_134_ & 0xfc0) + (i >> 6)] >>> i_154_;
						i = i_148_;
						i_134_ = i_149_;
						i_140_ += i_143_;
						i_141_ += i_144_;
						i_142_ += i_145_;
						i_151_ = i_142_ >> 12;
						if (i_151_ != 0) {
							i_148_ = i_140_ / i_151_;
							i_149_ = i_141_ / i_151_;
							if (i_148_ < 7) {
								i_148_ = 7;
							} else if (i_148_ > 4032) {
								i_148_ = 4032;
							}
						}
						i_152_ = i_148_ - i >> 3;
						i_153_ = i_149_ - i_134_ >> 3;
						i_138_ += i_147_;
						i += (i_138_ & 0x600000) >> 3;
						i_154_ = i_138_ >> 23;
					}
					i_146_ = i_137_ - i_136_ & 0x7;
					while (i_146_-- > 0) {
						is[i_135_++] = is_133_[(i_134_ & 0xfc0) + (i >> 6)] >>> i_154_;
						i += i_152_;
						i_134_ += i_153_;
					}
				} else {
					while (i_146_-- > 0) {
						int i_155_;
						if ((i_155_ = is_133_[(i_134_ & 0xfc0) + (i >> 6)] >>> i_154_) != 0) {
							is[i_135_] = i_155_;
						}
						i_135_++;
						i += i_152_;
						i_134_ += i_153_;
						if ((i_155_ = is_133_[(i_134_ & 0xfc0) + (i >> 6)] >>> i_154_) != 0) {
							is[i_135_] = i_155_;
						}
						i_135_++;
						i += i_152_;
						i_134_ += i_153_;
						if ((i_155_ = is_133_[(i_134_ & 0xfc0) + (i >> 6)] >>> i_154_) != 0) {
							is[i_135_] = i_155_;
						}
						i_135_++;
						i += i_152_;
						i_134_ += i_153_;
						if ((i_155_ = is_133_[(i_134_ & 0xfc0) + (i >> 6)] >>> i_154_) != 0) {
							is[i_135_] = i_155_;
						}
						i_135_++;
						i += i_152_;
						i_134_ += i_153_;
						if ((i_155_ = is_133_[(i_134_ & 0xfc0) + (i >> 6)] >>> i_154_) != 0) {
							is[i_135_] = i_155_;
						}
						i_135_++;
						i += i_152_;
						i_134_ += i_153_;
						if ((i_155_ = is_133_[(i_134_ & 0xfc0) + (i >> 6)] >>> i_154_) != 0) {
							is[i_135_] = i_155_;
						}
						i_135_++;
						i += i_152_;
						i_134_ += i_153_;
						if ((i_155_ = is_133_[(i_134_ & 0xfc0) + (i >> 6)] >>> i_154_) != 0) {
							is[i_135_] = i_155_;
						}
						i_135_++;
						i += i_152_;
						i_134_ += i_153_;
						if ((i_155_ = is_133_[(i_134_ & 0xfc0) + (i >> 6)] >>> i_154_) != 0) {
							is[i_135_] = i_155_;
						}
						i_135_++;
						i = i_148_;
						i_134_ = i_149_;
						i_140_ += i_143_;
						i_141_ += i_144_;
						i_142_ += i_145_;
						i_151_ = i_142_ >> 12;
						if (i_151_ != 0) {
							i_148_ = i_140_ / i_151_;
							i_149_ = i_141_ / i_151_;
							if (i_148_ < 7) {
								i_148_ = 7;
							} else if (i_148_ > 4032) {
								i_148_ = 4032;
							}
						}
						i_152_ = i_148_ - i >> 3;
						i_153_ = i_149_ - i_134_ >> 3;
						i_138_ += i_147_;
						i += (i_138_ & 0x600000) >> 3;
						i_154_ = i_138_ >> 23;
					}
					i_146_ = i_137_ - i_136_ & 0x7;
					while (i_146_-- > 0) {
						int i_156_;
						if ((i_156_ = is_133_[(i_134_ & 0xfc0) + (i >> 6)] >>> i_154_) != 0) {
							is[i_135_] = i_156_;
						}
						i_135_++;
						i += i_152_;
						i_134_ += i_153_;
					}
				}
			} else {
				int i_157_ = 0;
				int i_158_ = 0;
				int i_159_ = i_136_ - Rasterizer3D.centerX;
				i_140_ += (i_143_ >> 3) * i_159_;
				i_141_ += (i_144_ >> 3) * i_159_;
				i_142_ += (i_145_ >> 3) * i_159_;
				int i_160_ = i_142_ >> 14;
				if (i_160_ != 0) {
					i = i_140_ / i_160_;
					i_134_ = i_141_ / i_160_;
					if (i < 0) {
						i = 0;
					} else if (i > 16256) {
						i = 16256;
					}
				}
				i_140_ += i_143_;
				i_141_ += i_144_;
				i_142_ += i_145_;
				i_160_ = i_142_ >> 14;
				if (i_160_ != 0) {
					i_157_ = i_140_ / i_160_;
					i_158_ = i_141_ / i_160_;
					if (i_157_ < 7) {
						i_157_ = 7;
					} else if (i_157_ > 16256) {
						i_157_ = 16256;
					}
				}
				int i_161_ = i_157_ - i >> 3;
				int i_162_ = i_158_ - i_134_ >> 3;
				i += i_138_ & 0x600000;
				int i_163_ = i_138_ >> 23;
				if (Rasterizer3D.aBoolean1483) {
					while (i_146_-- > 0) {
						is[i_135_++] = is_133_[(i_134_ & 0x3f80) + (i >> 7)] >>> i_163_;
						i += i_161_;
						i_134_ += i_162_;
						is[i_135_++] = is_133_[(i_134_ & 0x3f80) + (i >> 7)] >>> i_163_;
						i += i_161_;
						i_134_ += i_162_;
						is[i_135_++] = is_133_[(i_134_ & 0x3f80) + (i >> 7)] >>> i_163_;
						i += i_161_;
						i_134_ += i_162_;
						is[i_135_++] = is_133_[(i_134_ & 0x3f80) + (i >> 7)] >>> i_163_;
						i += i_161_;
						i_134_ += i_162_;
						is[i_135_++] = is_133_[(i_134_ & 0x3f80) + (i >> 7)] >>> i_163_;
						i += i_161_;
						i_134_ += i_162_;
						is[i_135_++] = is_133_[(i_134_ & 0x3f80) + (i >> 7)] >>> i_163_;
						i += i_161_;
						i_134_ += i_162_;
						is[i_135_++] = is_133_[(i_134_ & 0x3f80) + (i >> 7)] >>> i_163_;
						i += i_161_;
						i_134_ += i_162_;
						is[i_135_++] = is_133_[(i_134_ & 0x3f80) + (i >> 7)] >>> i_163_;
						i = i_157_;
						i_134_ = i_158_;
						i_140_ += i_143_;
						i_141_ += i_144_;
						i_142_ += i_145_;
						i_160_ = i_142_ >> 14;
						if (i_160_ != 0) {
							i_157_ = i_140_ / i_160_;
							i_158_ = i_141_ / i_160_;
							if (i_157_ < 7) {
								i_157_ = 7;
							} else if (i_157_ > 16256) {
								i_157_ = 16256;
							}
						}
						i_161_ = i_157_ - i >> 3;
						i_162_ = i_158_ - i_134_ >> 3;
						i_138_ += i_147_;
						i += i_138_ & 0x600000;
						i_163_ = i_138_ >> 23;
					}
					i_146_ = i_137_ - i_136_ & 0x7;
					while (i_146_-- > 0) {
						is[i_135_++] = is_133_[(i_134_ & 0x3f80) + (i >> 7)] >>> i_163_;
						i += i_161_;
						i_134_ += i_162_;
					}
				} else {
					while (i_146_-- > 0) {
						int i_164_;
						if ((i_164_ = is_133_[(i_134_ & 0x3f80) + (i >> 7)] >>> i_163_) != 0) {
							is[i_135_] = i_164_;
						}
						i_135_++;
						i += i_161_;
						i_134_ += i_162_;
						if ((i_164_ = is_133_[(i_134_ & 0x3f80) + (i >> 7)] >>> i_163_) != 0) {
							is[i_135_] = i_164_;
						}
						i_135_++;
						i += i_161_;
						i_134_ += i_162_;
						if ((i_164_ = is_133_[(i_134_ & 0x3f80) + (i >> 7)] >>> i_163_) != 0) {
							is[i_135_] = i_164_;
						}
						i_135_++;
						i += i_161_;
						i_134_ += i_162_;
						if ((i_164_ = is_133_[(i_134_ & 0x3f80) + (i >> 7)] >>> i_163_) != 0) {
							is[i_135_] = i_164_;
						}
						i_135_++;
						i += i_161_;
						i_134_ += i_162_;
						if ((i_164_ = is_133_[(i_134_ & 0x3f80) + (i >> 7)] >>> i_163_) != 0) {
							is[i_135_] = i_164_;
						}
						i_135_++;
						i += i_161_;
						i_134_ += i_162_;
						if ((i_164_ = is_133_[(i_134_ & 0x3f80) + (i >> 7)] >>> i_163_) != 0) {
							is[i_135_] = i_164_;
						}
						i_135_++;
						i += i_161_;
						i_134_ += i_162_;
						if ((i_164_ = is_133_[(i_134_ & 0x3f80) + (i >> 7)] >>> i_163_) != 0) {
							is[i_135_] = i_164_;
						}
						i_135_++;
						i += i_161_;
						i_134_ += i_162_;
						if ((i_164_ = is_133_[(i_134_ & 0x3f80) + (i >> 7)] >>> i_163_) != 0) {
							is[i_135_] = i_164_;
						}
						i_135_++;
						i = i_157_;
						i_134_ = i_158_;
						i_140_ += i_143_;
						i_141_ += i_144_;
						i_142_ += i_145_;
						i_160_ = i_142_ >> 14;
						if (i_160_ != 0) {
							i_157_ = i_140_ / i_160_;
							i_158_ = i_141_ / i_160_;
							if (i_157_ < 7) {
								i_157_ = 7;
							} else if (i_157_ > 16256) {
								i_157_ = 16256;
							}
						}
						i_161_ = i_157_ - i >> 3;
						i_162_ = i_158_ - i_134_ >> 3;
						i_138_ += i_147_;
						i += i_138_ & 0x600000;
						i_163_ = i_138_ >> 23;
					}
					i_146_ = i_137_ - i_136_ & 0x7;
					while (i_146_-- > 0) {
						int i_165_;
						if ((i_165_ = is_133_[(i_134_ & 0x3f80) + (i >> 7)] >>> i_163_) != 0) {
							is[i_135_] = i_165_;
						}
						i_135_++;
						i += i_161_;
						i_134_ += i_162_;
					}
				}
			}
		}
	}

	static {
		for (int shadow = 1; shadow < 512; shadow++) {
			Rasterizer3D.shadowDecay[shadow] = 32768 / shadow;
		}
		for (int i = 1; i < 2048; i++) {
			Rasterizer3D.anIntArray1489[i] = 65536 / i;
		}
		for (int i = 0; i < 2048; i++) {
			Rasterizer3D.SINE[i] = (int) (65536.0 * Math.sin(i * 0.0030679615));
			Rasterizer3D.COSINE[i] = (int) (65536.0 * Math.cos(i * 0.0030679615));
		}
		Rasterizer3D.indexedImages = new IndexedImage[50];
		Rasterizer3D.aBooleanArray1495 = new boolean[50];
		Rasterizer3D.anIntArray1496 = new int[50];
		Rasterizer3D.anIntArrayArray1499 = new int[50][];
		Rasterizer3D.anIntArray1500 = new int[50];
		Rasterizer3D.getRgbLookupTableId = new int[65536];
		Rasterizer3D.anIntArrayArray1503 = new int[50][];
	}
}
