package com.runescape.scene;

import com.runescape.util.SignLink;

public class CollisionMap {
	private boolean aBoolean182 = true;
	public int anInt190;
	public int anInt191;
	public int anInt192;
	public int anInt193;
	public int[][] flags;

	public CollisionMap(int i, int i_0_, boolean bool) {
		do {
			try {
				anInt190 = 0;
				anInt191 = 0;
				anInt192 = i;
				anInt193 = i_0_;
				flags = new int[anInt192][anInt193];
				method216();
				if (bool) {
					break;
				}
			} catch (RuntimeException runtimeexception) {
				SignLink.reportError("28792, " + i + ", " + i_0_ + ", " + bool + ", " + runtimeexception.toString());
				throw new RuntimeException();
			}
			break;
		} while (false);
	}

	public void method216() {
		for (int i = 0; i < anInt192; i++) {
			for (int i_1_ = 0; i_1_ < anInt193; i_1_++) {
				if (i == 0 || i_1_ == 0 || i == anInt192 - 1 || i_1_ == anInt193 - 1) {
					flags[i][i_1_] = 16777215;
				} else {
					flags[i][i_1_] = 16777216;
				}
			}
		}
	}

	public void method217(int i, int i_2_, int i_3_, int i_4_, byte b, boolean bool) {
		do {
			try {
				i_3_ -= anInt190;
				i -= anInt191;
				if (b == 1) {
					b = (byte) 0;
				} else {
					break;
				}
				if (i_4_ == 0) {
					if (i_2_ == 0) {
						method220(i_3_, i, 128);
						method220(i_3_ - 1, i, 8);
					}
					if (i_2_ == 1) {
						method220(i_3_, i, 2);
						method220(i_3_, i + 1, 32);
					}
					if (i_2_ == 2) {
						method220(i_3_, i, 8);
						method220(i_3_ + 1, i, 128);
					}
					if (i_2_ == 3) {
						method220(i_3_, i, 32);
						method220(i_3_, i - 1, 2);
					}
				}
				if (i_4_ == 1 || i_4_ == 3) {
					if (i_2_ == 0) {
						method220(i_3_, i, 1);
						method220(i_3_ - 1, i + 1, 16);
					}
					if (i_2_ == 1) {
						method220(i_3_, i, 4);
						method220(i_3_ + 1, i + 1, 64);
					}
					if (i_2_ == 2) {
						method220(i_3_, i, 16);
						method220(i_3_ + 1, i - 1, 1);
					}
					if (i_2_ == 3) {
						method220(i_3_, i, 64);
						method220(i_3_ - 1, i - 1, 4);
					}
				}
				if (i_4_ == 2) {
					if (i_2_ == 0) {
						method220(i_3_, i, 130);
						method220(i_3_ - 1, i, 8);
						method220(i_3_, i + 1, 32);
					}
					if (i_2_ == 1) {
						method220(i_3_, i, 10);
						method220(i_3_, i + 1, 32);
						method220(i_3_ + 1, i, 128);
					}
					if (i_2_ == 2) {
						method220(i_3_, i, 40);
						method220(i_3_ + 1, i, 128);
						method220(i_3_, i - 1, 2);
					}
					if (i_2_ == 3) {
						method220(i_3_, i, 160);
						method220(i_3_, i - 1, 2);
						method220(i_3_ - 1, i, 8);
					}
				}
				if (!bool) {
					break;
				}
				if (i_4_ == 0) {
					if (i_2_ == 0) {
						method220(i_3_, i, 65536);
						method220(i_3_ - 1, i, 4096);
					}
					if (i_2_ == 1) {
						method220(i_3_, i, 1024);
						method220(i_3_, i + 1, 16384);
					}
					if (i_2_ == 2) {
						method220(i_3_, i, 4096);
						method220(i_3_ + 1, i, 65536);
					}
					if (i_2_ == 3) {
						method220(i_3_, i, 16384);
						method220(i_3_, i - 1, 1024);
					}
				}
				if (i_4_ == 1 || i_4_ == 3) {
					if (i_2_ == 0) {
						method220(i_3_, i, 512);
						method220(i_3_ - 1, i + 1, 8192);
					}
					if (i_2_ == 1) {
						method220(i_3_, i, 2048);
						method220(i_3_ + 1, i + 1, 32768);
					}
					if (i_2_ == 2) {
						method220(i_3_, i, 8192);
						method220(i_3_ + 1, i - 1, 512);
					}
					if (i_2_ == 3) {
						method220(i_3_, i, 32768);
						method220(i_3_ - 1, i - 1, 2048);
					}
				}
				if (i_4_ != 2) {
					break;
				}
				if (i_2_ == 0) {
					method220(i_3_, i, 66560);
					method220(i_3_ - 1, i, 4096);
					method220(i_3_, i + 1, 16384);
				}
				if (i_2_ == 1) {
					method220(i_3_, i, 5120);
					method220(i_3_, i + 1, 16384);
					method220(i_3_ + 1, i, 65536);
				}
				if (i_2_ == 2) {
					method220(i_3_, i, 20480);
					method220(i_3_ + 1, i, 65536);
					method220(i_3_, i - 1, 1024);
				}
				if (i_2_ != 3) {
					break;
				}
				method220(i_3_, i, 81920);
				method220(i_3_, i - 1, 1024);
				method220(i_3_ - 1, i, 4096);
			} catch (RuntimeException runtimeexception) {
				SignLink.reportError("53275, " + i + ", " + i_2_ + ", " + i_3_ + ", " + i_4_ + ", " + b + ", " + bool
						+ ", " + runtimeexception.toString());
				throw new RuntimeException();
			}
			break;
		} while (false);
	}

	public void method218(boolean bool, int i, int i_5_, int i_6_, int i_7_, int i_8_, int i_9_) {
		try {
			int i_10_ = 256;
			if (bool) {
				i_10_ += 131072;
			}
			i_7_ -= anInt190;
			i = 14 / i;
			i_8_ -= anInt191;
			if (i_9_ == 1 || i_9_ == 3) {
				int i_11_ = i_5_;
				i_5_ = i_6_;
				i_6_ = i_11_;
			}
			for (int i_12_ = i_7_; i_12_ < i_7_ + i_5_; i_12_++) {
				if (i_12_ >= 0 && i_12_ < anInt192) {
					for (int i_13_ = i_8_; i_13_ < i_8_ + i_6_; i_13_++) {
						if (i_13_ >= 0 && i_13_ < anInt193) {
							method220(i_12_, i_13_, i_10_);
						}
					}
				}
			}
		} catch (RuntimeException runtimeexception) {
			SignLink.reportError("32403, " + bool + ", " + i + ", " + i_5_ + ", " + i_6_ + ", " + i_7_ + ", " + i_8_
					+ ", " + i_9_ + ", " + runtimeexception.toString());
			throw new RuntimeException();
		}
	}

	public void setSolidFlag(int x, int y) {
		x -= anInt190;
		y -= anInt191;
		flags[x][y] |= 0x200000;
	}

	private void method220(int i, int i_16_, int i_17_) {
		flags[i][i_16_] |= i_17_;
	}

	public void method221(int i, int i_18_, boolean bool, boolean bool_19_, int i_20_, int i_21_) {
		do {
			try {
				i_20_ -= anInt190;
				i_21_ -= anInt191;
				if (bool_19_) {
					if (i_18_ == 0) {
						if (i == 0) {
							method223(128, i_20_, i_21_, 933);
							method223(8, i_20_ - 1, i_21_, 933);
						}
						if (i == 1) {
							method223(2, i_20_, i_21_, 933);
							method223(32, i_20_, i_21_ + 1, 933);
						}
						if (i == 2) {
							method223(8, i_20_, i_21_, 933);
							method223(128, i_20_ + 1, i_21_, 933);
						}
						if (i == 3) {
							method223(32, i_20_, i_21_, 933);
							method223(2, i_20_, i_21_ - 1, 933);
						}
					}
					if (i_18_ == 1 || i_18_ == 3) {
						if (i == 0) {
							method223(1, i_20_, i_21_, 933);
							method223(16, i_20_ - 1, i_21_ + 1, 933);
						}
						if (i == 1) {
							method223(4, i_20_, i_21_, 933);
							method223(64, i_20_ + 1, i_21_ + 1, 933);
						}
						if (i == 2) {
							method223(16, i_20_, i_21_, 933);
							method223(1, i_20_ + 1, i_21_ - 1, 933);
						}
						if (i == 3) {
							method223(64, i_20_, i_21_, 933);
							method223(4, i_20_ - 1, i_21_ - 1, 933);
						}
					}
					if (i_18_ == 2) {
						if (i == 0) {
							method223(130, i_20_, i_21_, 933);
							method223(8, i_20_ - 1, i_21_, 933);
							method223(32, i_20_, i_21_ + 1, 933);
						}
						if (i == 1) {
							method223(10, i_20_, i_21_, 933);
							method223(32, i_20_, i_21_ + 1, 933);
							method223(128, i_20_ + 1, i_21_, 933);
						}
						if (i == 2) {
							method223(40, i_20_, i_21_, 933);
							method223(128, i_20_ + 1, i_21_, 933);
							method223(2, i_20_, i_21_ - 1, 933);
						}
						if (i == 3) {
							method223(160, i_20_, i_21_, 933);
							method223(2, i_20_, i_21_ - 1, 933);
							method223(8, i_20_ - 1, i_21_, 933);
						}
					}
					if (!bool) {
						break;
					}
					if (i_18_ == 0) {
						if (i == 0) {
							method223(65536, i_20_, i_21_, 933);
							method223(4096, i_20_ - 1, i_21_, 933);
						}
						if (i == 1) {
							method223(1024, i_20_, i_21_, 933);
							method223(16384, i_20_, i_21_ + 1, 933);
						}
						if (i == 2) {
							method223(4096, i_20_, i_21_, 933);
							method223(65536, i_20_ + 1, i_21_, 933);
						}
						if (i == 3) {
							method223(16384, i_20_, i_21_, 933);
							method223(1024, i_20_, i_21_ - 1, 933);
						}
					}
					if (i_18_ == 1 || i_18_ == 3) {
						if (i == 0) {
							method223(512, i_20_, i_21_, 933);
							method223(8192, i_20_ - 1, i_21_ + 1, 933);
						}
						if (i == 1) {
							method223(2048, i_20_, i_21_, 933);
							method223(32768, i_20_ + 1, i_21_ + 1, 933);
						}
						if (i == 2) {
							method223(8192, i_20_, i_21_, 933);
							method223(512, i_20_ + 1, i_21_ - 1, 933);
						}
						if (i == 3) {
							method223(32768, i_20_, i_21_, 933);
							method223(2048, i_20_ - 1, i_21_ - 1, 933);
						}
					}
					if (i_18_ != 2) {
						break;
					}
					if (i == 0) {
						method223(66560, i_20_, i_21_, 933);
						method223(4096, i_20_ - 1, i_21_, 933);
						method223(16384, i_20_, i_21_ + 1, 933);
					}
					if (i == 1) {
						method223(5120, i_20_, i_21_, 933);
						method223(16384, i_20_, i_21_ + 1, 933);
						method223(65536, i_20_ + 1, i_21_, 933);
					}
					if (i == 2) {
						method223(20480, i_20_, i_21_, 933);
						method223(65536, i_20_ + 1, i_21_, 933);
						method223(1024, i_20_, i_21_ - 1, 933);
					}
					if (i != 3) {
						break;
					}
					method223(81920, i_20_, i_21_, 933);
					method223(1024, i_20_, i_21_ - 1, 933);
					method223(4096, i_20_ - 1, i_21_, 933);
				}
			} catch (RuntimeException runtimeexception) {
				SignLink.reportError("6952, " + i + ", " + i_18_ + ", " + bool + ", " + bool_19_ + ", " + i_20_ + ", "
						+ i_21_ + ", " + runtimeexception.toString());
				throw new RuntimeException();
			}
			break;
		} while (false);
	}

	public void method222(int i, int i_22_, int i_23_, int i_24_, byte b, int i_25_, boolean bool) {
		try {
			int i_26_ = 256;
			if (bool) {
				i_26_ += 131072;
			}
			i_23_ -= anInt190;
			i_24_ -= anInt191;
			if (b != 6) {
				aBoolean182 = !aBoolean182;
			}
			if (i == 1 || i == 3) {
				int i_27_ = i_22_;
				i_22_ = i_25_;
				i_25_ = i_27_;
			}
			for (int i_28_ = i_23_; i_28_ < i_23_ + i_22_; i_28_++) {
				if (i_28_ >= 0 && i_28_ < anInt192) {
					for (int i_29_ = i_24_; i_29_ < i_24_ + i_25_; i_29_++) {
						if (i_29_ >= 0 && i_29_ < anInt193) {
							method223(i_26_, i_28_, i_29_, 933);
						}
					}
				}
			}
		} catch (RuntimeException runtimeexception) {
			SignLink.reportError("45433, " + i + ", " + i_22_ + ", " + i_23_ + ", " + i_24_ + ", " + b + ", " + i_25_
					+ ", " + bool + ", " + runtimeexception.toString());
			throw new RuntimeException();
		}
	}

	private void method223(int i, int i_30_, int i_31_, int i_32_) {
		try {
			i_32_ = 36 / i_32_;
			flags[i_30_][i_31_] &= 16777215 - i;
		} catch (RuntimeException runtimeexception) {
			SignLink.reportError("42695, " + i + ", " + i_30_ + ", " + i_31_ + ", " + i_32_ + ", "
					+ runtimeexception.toString());
			throw new RuntimeException();
		}
	}

	public void method224(int i, int i_33_, int i_34_) {
		try {
			i_34_ -= anInt190;
			i_33_ -= anInt191;
			i = 6 / i;
			flags[i_34_][i_33_] &= 0xdfffff;
		} catch (RuntimeException runtimeexception) {
			SignLink.reportError("36463, " + i + ", " + i_33_ + ", " + i_34_ + ", " + runtimeexception.toString());
			throw new RuntimeException();
		}
	}

	public boolean reachedWall(int i, int i_35_, int i_36_, int i_37_, int i_38_, int i_39_, int i_40_) {
		try {
			if (i_37_ != 0) {
				aBoolean182 = !aBoolean182;
			}
			if (i_35_ == i && i_36_ == i_40_) {
				return true;
			}
			i_35_ -= anInt190;
			i_36_ -= anInt191;
			i -= anInt190;
			i_40_ -= anInt191;
			if (i_39_ == 0) {
				if (i_38_ == 0) {
					if (i_35_ == i - 1 && i_36_ == i_40_) {
						return true;
					}
					if (i_35_ == i && i_36_ == i_40_ + 1 && (flags[i_35_][i_36_] & 0x1280120) == 0) {
						return true;
					}
					if (i_35_ == i && i_36_ == i_40_ - 1 && (flags[i_35_][i_36_] & 0x1280102) == 0) {
						return true;
					}
				} else if (i_38_ == 1) {
					if (i_35_ == i && i_36_ == i_40_ + 1) {
						return true;
					}
					if (i_35_ == i - 1 && i_36_ == i_40_ && (flags[i_35_][i_36_] & 0x1280108) == 0) {
						return true;
					}
					if (i_35_ == i + 1 && i_36_ == i_40_ && (flags[i_35_][i_36_] & 0x1280180) == 0) {
						return true;
					}
				} else if (i_38_ == 2) {
					if (i_35_ == i + 1 && i_36_ == i_40_) {
						return true;
					}
					if (i_35_ == i && i_36_ == i_40_ + 1 && (flags[i_35_][i_36_] & 0x1280120) == 0) {
						return true;
					}
					if (i_35_ == i && i_36_ == i_40_ - 1 && (flags[i_35_][i_36_] & 0x1280102) == 0) {
						return true;
					}
				} else if (i_38_ == 3) {
					if (i_35_ == i && i_36_ == i_40_ - 1) {
						return true;
					}
					if (i_35_ == i - 1 && i_36_ == i_40_ && (flags[i_35_][i_36_] & 0x1280108) == 0) {
						return true;
					}
					if (i_35_ == i + 1 && i_36_ == i_40_ && (flags[i_35_][i_36_] & 0x1280180) == 0) {
						return true;
					}
				}
			}
			if (i_39_ == 2) {
				if (i_38_ == 0) {
					if (i_35_ == i - 1 && i_36_ == i_40_) {
						return true;
					}
					if (i_35_ == i && i_36_ == i_40_ + 1) {
						return true;
					}
					if (i_35_ == i + 1 && i_36_ == i_40_ && (flags[i_35_][i_36_] & 0x1280180) == 0) {
						return true;
					}
					if (i_35_ == i && i_36_ == i_40_ - 1 && (flags[i_35_][i_36_] & 0x1280102) == 0) {
						return true;
					}
				} else if (i_38_ == 1) {
					if (i_35_ == i - 1 && i_36_ == i_40_ && (flags[i_35_][i_36_] & 0x1280108) == 0) {
						return true;
					}
					if (i_35_ == i && i_36_ == i_40_ + 1) {
						return true;
					}
					if (i_35_ == i + 1 && i_36_ == i_40_) {
						return true;
					}
					if (i_35_ == i && i_36_ == i_40_ - 1 && (flags[i_35_][i_36_] & 0x1280102) == 0) {
						return true;
					}
				} else if (i_38_ == 2) {
					if (i_35_ == i - 1 && i_36_ == i_40_ && (flags[i_35_][i_36_] & 0x1280108) == 0) {
						return true;
					}
					if (i_35_ == i && i_36_ == i_40_ + 1 && (flags[i_35_][i_36_] & 0x1280120) == 0) {
						return true;
					}
					if (i_35_ == i + 1 && i_36_ == i_40_) {
						return true;
					}
					if (i_35_ == i && i_36_ == i_40_ - 1) {
						return true;
					}
				} else if (i_38_ == 3) {
					if (i_35_ == i - 1 && i_36_ == i_40_) {
						return true;
					}
					if (i_35_ == i && i_36_ == i_40_ + 1 && (flags[i_35_][i_36_] & 0x1280120) == 0) {
						return true;
					}
					if (i_35_ == i + 1 && i_36_ == i_40_ && (flags[i_35_][i_36_] & 0x1280180) == 0) {
						return true;
					}
					if (i_35_ == i && i_36_ == i_40_ - 1) {
						return true;
					}
				}
			}
			if (i_39_ == 9) {
				if (i_35_ == i && i_36_ == i_40_ + 1 && (flags[i_35_][i_36_] & 0x20) == 0) {
					return true;
				}
				if (i_35_ == i && i_36_ == i_40_ - 1 && (flags[i_35_][i_36_] & 0x2) == 0) {
					return true;
				}
				if (i_35_ == i - 1 && i_36_ == i_40_ && (flags[i_35_][i_36_] & 0x8) == 0) {
					return true;
				}
				if (i_35_ == i + 1 && i_36_ == i_40_ && (flags[i_35_][i_36_] & 0x80) == 0) {
					return true;
				}
			}
			return false;
		} catch (RuntimeException runtimeexception) {
			SignLink.reportError("46464, " + i + ", " + i_35_ + ", " + i_36_ + ", " + i_37_ + ", " + i_38_ + ", "
					+ i_39_ + ", " + i_40_ + ", " + runtimeexception.toString());
			throw new RuntimeException();
		}
	}

	public boolean reachedDecoration(int i, int i_41_, int i_42_, int i_43_, int i_44_, int i_45_, int i_46_) {
		try {
			if (i_46_ != 0) {
				throw new NullPointerException();
			}
			if (i_45_ == i && i_42_ == i_41_) {
				return true;
			}
			i_45_ -= anInt190;
			i_42_ -= anInt191;
			i -= anInt190;
			i_41_ -= anInt191;
			if (i_43_ == 6 || i_43_ == 7) {
				if (i_43_ == 7) {
					i_44_ = i_44_ + 2 & 0x3;
				}
				if (i_44_ == 0) {
					if (i_45_ == i + 1 && i_42_ == i_41_ && (flags[i_45_][i_42_] & 0x80) == 0) {
						return true;
					}
					if (i_45_ == i && i_42_ == i_41_ - 1 && (flags[i_45_][i_42_] & 0x2) == 0) {
						return true;
					}
				} else if (i_44_ == 1) {
					if (i_45_ == i - 1 && i_42_ == i_41_ && (flags[i_45_][i_42_] & 0x8) == 0) {
						return true;
					}
					if (i_45_ == i && i_42_ == i_41_ - 1 && (flags[i_45_][i_42_] & 0x2) == 0) {
						return true;
					}
				} else if (i_44_ == 2) {
					if (i_45_ == i - 1 && i_42_ == i_41_ && (flags[i_45_][i_42_] & 0x8) == 0) {
						return true;
					}
					if (i_45_ == i && i_42_ == i_41_ + 1 && (flags[i_45_][i_42_] & 0x20) == 0) {
						return true;
					}
				} else if (i_44_ == 3) {
					if (i_45_ == i + 1 && i_42_ == i_41_ && (flags[i_45_][i_42_] & 0x80) == 0) {
						return true;
					}
					if (i_45_ == i && i_42_ == i_41_ + 1 && (flags[i_45_][i_42_] & 0x20) == 0) {
						return true;
					}
				}
			}
			if (i_43_ == 8) {
				if (i_45_ == i && i_42_ == i_41_ + 1 && (flags[i_45_][i_42_] & 0x20) == 0) {
					return true;
				}
				if (i_45_ == i && i_42_ == i_41_ - 1 && (flags[i_45_][i_42_] & 0x2) == 0) {
					return true;
				}
				if (i_45_ == i - 1 && i_42_ == i_41_ && (flags[i_45_][i_42_] & 0x8) == 0) {
					return true;
				}
				if (i_45_ == i + 1 && i_42_ == i_41_ && (flags[i_45_][i_42_] & 0x80) == 0) {
					return true;
				}
			}
			return false;
		} catch (RuntimeException runtimeexception) {
			SignLink.reportError("67003, " + i + ", " + i_41_ + ", " + i_42_ + ", " + i_43_ + ", " + i_44_ + ", "
					+ i_45_ + ", " + i_46_ + ", " + runtimeexception.toString());
			throw new RuntimeException();
		}
	}

	public boolean reachedFacingObject(byte b, int i, int i_47_, int i_48_, int i_49_, int i_50_, int i_51_, int i_52_) {
		try {
			if (b != 1) {
				throw new NullPointerException();
			}
			int i_53_ = i_47_ + i_51_ - 1;
			int i_54_ = i + i_49_ - 1;
			if (i_48_ >= i_47_ && i_48_ <= i_53_ && i_52_ >= i && i_52_ <= i_54_) {
				return true;
			}
			if (i_48_ == i_47_ - 1 && i_52_ >= i && i_52_ <= i_54_
					&& (flags[i_48_ - anInt190][i_52_ - anInt191] & 0x8) == 0 && (i_50_ & 0x8) == 0) {
				return true;
			}
			if (i_48_ == i_53_ + 1 && i_52_ >= i && i_52_ <= i_54_
					&& (flags[i_48_ - anInt190][i_52_ - anInt191] & 0x80) == 0 && (i_50_ & 0x2) == 0) {
				return true;
			}
			if (i_52_ == i - 1 && i_48_ >= i_47_ && i_48_ <= i_53_
					&& (flags[i_48_ - anInt190][i_52_ - anInt191] & 0x2) == 0 && (i_50_ & 0x4) == 0) {
				return true;
			}
			if (i_52_ == i_54_ + 1 && i_48_ >= i_47_ && i_48_ <= i_53_
					&& (flags[i_48_ - anInt190][i_52_ - anInt191] & 0x20) == 0 && (i_50_ & 0x1) == 0) {
				return true;
			}
			return false;
		} catch (RuntimeException runtimeexception) {
			SignLink.reportError("42625, " + b + ", " + i + ", " + i_47_ + ", " + i_48_ + ", " + i_49_ + ", " + i_50_
					+ ", " + i_51_ + ", " + i_52_ + ", " + runtimeexception.toString());
			throw new RuntimeException();
		}
	}
}
