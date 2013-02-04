package com.runescape.cache.def;

import com.runescape.Client;
import com.runescape.anim.Animation;
import com.runescape.cache.Archive;
import com.runescape.graphic.Model;
import com.runescape.net.Buffer;
import com.runescape.node.Cache;
import com.runescape.util.SignLink;

/* NpcDefinition - Decompiled by JODE
 * Visit http://jode.sourceforge.net/
 */

public class NpcDefinition {
	public int anInt411 = -1;
	private static int anInt412;
	public int anInt413 = -1;
	public int anInt414 = -1;
	public int anInt415 = -1;
	private static Buffer aBuffer416;
	public int anInt417 = -1;
	public static int anInt418;
	private int anInt420 = 1834;
	public String aString421;
	public String[] aStringArray422;
	public int anInt423 = -1;
	public byte aByte424 = 1;
	private int[] anIntArray426;
	public int anInt427 = -1;
	private static int[] anIntArray428;
	private int[] anIntArray429;
	public int anInt431 = -1;
	private int[] anIntArray432;
	public int anInt433 = -1;
	public long aLong434 = -1L;
	public int anInt435 = 32;
	private static NpcDefinition[] aNpcDefinitionArray436;
	private boolean aBoolean437 = false;
	public static Client aClient438;
	public int anInt439 = -1;
	public boolean aBoolean440 = true;
	private int anInt441;
	private int anInt442 = 128;
	public boolean aBoolean443 = true;
	public int[] anIntArray444;
	public byte[] aByteArray445;
	public int anInt446 = -1;
	private int anInt447 = 128;
	private int anInt448;
	public boolean aBoolean449 = false;
	private int[] anIntArray450;
	public static Cache aCache451 = new Cache(30);
	public int anInt452 = -1;

	public static final NpcDefinition getDefinition(int i) {
		for (int i_0_ = 0; i_0_ < 20; i_0_++) {
			if (NpcDefinition.aNpcDefinitionArray436[i_0_].aLong434 == i) {
				return NpcDefinition.aNpcDefinitionArray436[i_0_];
			}
		}
		NpcDefinition.anInt412 = (NpcDefinition.anInt412 + 1) % 20;
		NpcDefinition npcdefinition = NpcDefinition.aNpcDefinitionArray436[NpcDefinition.anInt412] = new NpcDefinition();
		NpcDefinition.aBuffer416.offset = NpcDefinition.anIntArray428[i];
		npcdefinition.aLong434 = i;
		npcdefinition.method443(true, NpcDefinition.aBuffer416);
		return npcdefinition;
	}

	public final Model getModel(boolean bool) {
		try {
			if (anIntArray444 != null) {
				NpcDefinition npcdefinition_1_ = method439(anInt420);
				if (npcdefinition_1_ == null) {
					return null;
				}
				return npcdefinition_1_.getModel(true);
			}
			if (anIntArray429 == null) {
				return null;
			}
			boolean bool_2_ = false;
			if (!bool) {
				anInt420 = 303;
			}
			for (int i = 0; i < anIntArray429.length; i++) {
				if (!Model.isCached(anIntArray429[i])) {
					bool_2_ = true;
				}
			}
			if (bool_2_) {
				return null;
			}
			Model[] models = new Model[anIntArray429.length];
			for (int i = 0; i < anIntArray429.length; i++) {
				models[i] = Model.getModel(anIntArray429[i]);
			}
			Model model;
			if (models.length == 1) {
				model = models[0];
			} else {
				model = new Model(models.length, models);
			}
			if (anIntArray432 != null) {
				for (int i = 0; i < anIntArray432.length; i++) {
					model.recolor(anIntArray432[i], anIntArray426[i]);
				}
			}
			return model;
		} catch (RuntimeException runtimeexception) {
			SignLink.reportError("61524, " + bool + ", " + runtimeexception.toString());
			throw new RuntimeException();
		}
	}

	public final NpcDefinition method439(int i) {
		try {
			int i_3_ = -1;
			if (i != 1834) {
				aBoolean437 = !aBoolean437;
			}
			if (anInt413 != -1) {
				VarBit varbit = VarBit.cache[anInt413];
				int i_4_ = varbit.anInt737;
				int i_5_ = varbit.anInt738;
				int i_6_ = varbit.anInt739;
				int i_7_ = Client.anIntArray1257[i_6_ - i_5_];
				i_3_ = NpcDefinition.aClient438.settings[i_4_] >> i_5_ & i_7_;
			} else if (anInt415 != -1) {
				i_3_ = NpcDefinition.aClient438.settings[anInt415];
			}
			if (i_3_ < 0 || i_3_ >= anIntArray444.length || anIntArray444[i_3_] == -1) {
				return null;
			}
			return NpcDefinition.getDefinition(anIntArray444[i_3_]);
		} catch (RuntimeException runtimeexception) {
			SignLink.reportError("19218, " + i + ", " + runtimeexception.toString());
			throw new RuntimeException();
		}
	}

	public static final void method440(Archive archive) {
		NpcDefinition.aBuffer416 = new Buffer(archive.getFile("npc.dat"));
		Buffer buffer = new Buffer(archive.getFile("npc.idx"));
		NpcDefinition.anInt418 = buffer.getUnsignedLEShort();
		NpcDefinition.anIntArray428 = new int[NpcDefinition.anInt418];
		int i = 2;
		for (int i_8_ = 0; i_8_ < NpcDefinition.anInt418; i_8_++) {
			NpcDefinition.anIntArray428[i_8_] = i;
			i += buffer.getUnsignedLEShort();
		}
		NpcDefinition.aNpcDefinitionArray436 = new NpcDefinition[20];
		for (int i_9_ = 0; i_9_ < 20; i_9_++) {
			NpcDefinition.aNpcDefinitionArray436[i_9_] = new NpcDefinition();
		}
	}

	public static final void method441(int i) {
		try {
			NpcDefinition.aCache451 = null;
			NpcDefinition.anIntArray428 = null;
			if (i >= 0) {
			}
			NpcDefinition.aNpcDefinitionArray436 = null;
			NpcDefinition.aBuffer416 = null;
		} catch (RuntimeException runtimeexception) {
			SignLink.reportError("86254, " + i + ", " + runtimeexception.toString());
			throw new RuntimeException();
		}
	}

	public final Model method442(int i, int i_10_, int i_11_, int[] is) {
		try {
			if (anIntArray444 != null) {
				NpcDefinition npcdefinition_12_ = method439(anInt420);
				if (npcdefinition_12_ == null) {
					return null;
				}
				return npcdefinition_12_.method442(0, i_10_, i_11_, is);
			}
			Model model = (Model) NpcDefinition.aCache451.get(aLong434);
			if (i != 0) {
				for (int i_13_ = 1; i_13_ > 0; i_13_++) {
					/* empty */
				}
			}
			if (model == null) {
				boolean bool = false;
				for (int i_14_ = 0; i_14_ < anIntArray450.length; i_14_++) {
					if (!Model.isCached(anIntArray450[i_14_])) {
						bool = true;
					}
				}
				if (bool) {
					return null;
				}
				Model[] models = new Model[anIntArray450.length];
				for (int i_15_ = 0; i_15_ < anIntArray450.length; i_15_++) {
					models[i_15_] = Model.getModel(anIntArray450[i_15_]);
				}
				if (models.length == 1) {
					model = models[0];
				} else {
					model = new Model(models.length, models);
				}
				if (anIntArray432 != null) {
					for (int i_16_ = 0; i_16_ < anIntArray432.length; i_16_++) {
						model.recolor(anIntArray432[i_16_], anIntArray426[i_16_]);
					}
				}
				model.createBones();
				model.applyLighting(64 + anInt441, 850 + anInt448, -30, -50, -30, true);
				NpcDefinition.aCache451.put(model, aLong434);
			}
			Model model_17_ = Model.aModel1614;
			model_17_.method412(7, model, Animation.exists(i_11_) & Animation.exists(i_10_));
			if (i_11_ != -1 && i_10_ != -1) {
				model_17_.method419(-20491, is, i_10_, i_11_);
			} else if (i_11_ != -1) {
				model_17_.applyTransform(i_11_);
			}
			if (anInt447 != 128 || anInt442 != 128) {
				model_17_.scaleT(anInt447, anInt447, anInt442);
			}
			model_17_.method414(false);
			model_17_.triangleSkin = null;
			model_17_.vectorSkin = null;
			if (aByte424 == 1) {
				model_17_.aBoolean1652 = true;
			}
			return model_17_;
		} catch (RuntimeException runtimeexception) {
			SignLink.reportError("81939, " + i + ", " + i_10_ + ", " + i_11_ + ", " + is + ", "
					+ runtimeexception.toString());
			throw new RuntimeException();
		}
	}

	private final void method443(boolean bool, Buffer buffer) {
		try {
			if (!bool) {
				throw new NullPointerException();
			}
			for (;;) {
				int i = buffer.getUnsignedByte();
				if (i == 0) {
					break;
				}
				if (i == 1) {
					int i_18_ = buffer.getUnsignedByte();
					anIntArray450 = new int[i_18_];
					for (int i_19_ = 0; i_19_ < i_18_; i_19_++) {
						anIntArray450[i_19_] = buffer.getUnsignedLEShort();
					}
				} else if (i == 2) {
					aString421 = buffer.getString();
				} else if (i == 3) {
					aByteArray445 = buffer.getStringAsBytes();
				} else if (i == 12) {
					aByte424 = buffer.get();
				} else if (i == 13) {
					anInt433 = buffer.getUnsignedLEShort();
				} else if (i == 14) {
					anInt423 = buffer.getUnsignedLEShort();
				} else if (i == 17) {
					anInt423 = buffer.getUnsignedLEShort();
					anInt414 = buffer.getUnsignedLEShort();
					anInt439 = buffer.getUnsignedLEShort();
					anInt411 = buffer.getUnsignedLEShort();
				} else if (i >= 30 && i < 40) {
					if (aStringArray422 == null) {
						aStringArray422 = new String[5];
					}
					aStringArray422[i - 30] = buffer.getString();
					if (aStringArray422[i - 30].equalsIgnoreCase("hidden")) {
						aStringArray422[i - 30] = null;
					}
				} else if (i == 40) {
					int i_20_ = buffer.getUnsignedByte();
					anIntArray432 = new int[i_20_];
					anIntArray426 = new int[i_20_];
					for (int i_21_ = 0; i_21_ < i_20_; i_21_++) {
						anIntArray432[i_21_] = buffer.getUnsignedLEShort();
						anIntArray426[i_21_] = buffer.getUnsignedLEShort();
					}
				} else if (i == 60) {
					int i_22_ = buffer.getUnsignedByte();
					anIntArray429 = new int[i_22_];
					for (int i_23_ = 0; i_23_ < i_22_; i_23_++) {
						anIntArray429[i_23_] = buffer.getUnsignedLEShort();
					}
				} else if (i == 90) {
					anInt452 = buffer.getUnsignedLEShort();
				} else if (i == 91) {
					anInt427 = buffer.getUnsignedLEShort();
				} else if (i == 92) {
					anInt446 = buffer.getUnsignedLEShort();
				} else if (i == 93) {
					aBoolean443 = false;
				} else if (i == 95) {
					anInt417 = buffer.getUnsignedLEShort();
				} else if (i == 97) {
					anInt447 = buffer.getUnsignedLEShort();
				} else if (i == 98) {
					anInt442 = buffer.getUnsignedLEShort();
				} else if (i == 99) {
					aBoolean449 = true;
				} else if (i == 100) {
					anInt441 = buffer.get();
				} else if (i == 101) {
					anInt448 = buffer.get() * 5;
				} else if (i == 102) {
					anInt431 = buffer.getUnsignedLEShort();
				} else if (i == 103) {
					anInt435 = buffer.getUnsignedLEShort();
				} else if (i == 106) {
					anInt413 = buffer.getUnsignedLEShort();
					if (anInt413 == 65535) {
						anInt413 = -1;
					}
					anInt415 = buffer.getUnsignedLEShort();
					if (anInt415 == 65535) {
						anInt415 = -1;
					}
					int i_24_ = buffer.getUnsignedByte();
					anIntArray444 = new int[i_24_ + 1];
					for (int i_25_ = 0; i_25_ <= i_24_; i_25_++) {
						anIntArray444[i_25_] = buffer.getUnsignedLEShort();
						if (anIntArray444[i_25_] == 65535) {
							anIntArray444[i_25_] = -1;
						}
					}
				} else if (i == 107) {
					aBoolean440 = false;
				}
			}
		} catch (RuntimeException runtimeexception) {
			SignLink.reportError("54892, " + bool + ", " + buffer + ", " + runtimeexception.toString());
			throw new RuntimeException();
		}
	}

	NpcDefinition() {
		/* empty */
	}
}
