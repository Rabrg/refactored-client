package com.runescape.scene;

import com.runescape.util.SignLink;

public class TiledUtils {

	public static int method586(int i, int i_0_, int i_1_, boolean bool) {
		try {
			i &= 0x3;
			if (bool) {
				for (int i_2_ = 1; i_2_ > 0; i_2_++) {
					/* empty */
				}
			}
			if (i == 0) {
				return i_1_;
			}
			if (i == 1) {
				return i_0_;
			}
			if (i == 2) {
				return 7 - i_1_;
			}
			return 7 - i_0_;
		} catch (RuntimeException runtimeexception) {
			SignLink.reportError("92720, " + i + ", " + i_0_ + ", " + i_1_ + ", " + bool + ", "
					+ runtimeexception.toString());
			throw new RuntimeException();
		}
	}

	public static int method587(int i, int i_3_, int i_5_) {
		i_3_ &= 0x3;
		if (i_3_ == 0) {
			return i;
		}
		if (i_3_ == 1) {
			return 7 - i_5_;
		}
		if (i_3_ == 2) {
			return 7 - i;
		}
		return i_5_;
	}

	public static int method588(int i, int i_6_, int i_7_, int i_8_, int i_9_) {
		i &= 0x3;
		if (i == 0) {
			return i_7_;
		}
		if (i == 1) {
			return i_8_;
		}
		if (i == 2) {
			return 7 - i_7_ - (i_9_ - 1);
		}
		return 7 - i_8_ - (i_6_ - 1);
	}

	public static int method589(int i_10_, int i_11_, int i_12_, int i_13_, int i_14_) {
		i_12_ &= 0x3;
		if (i_12_ == 0) {
			return i_10_;
		}
		if (i_12_ == 1) {
			return 7 - i_14_ - (i_13_ - 1);
		}
		if (i_12_ == 2) {
			return 7 - i_10_ - (i_11_ - 1);
		}
		return i_14_;
	}
}
