package com.runescape.cache.def;

import com.runescape.Client;
import com.runescape.anim.Animation;
import com.runescape.cache.Archive;
import com.runescape.cache.requester.OnDemandRequester;
import com.runescape.graphic.Model;
import com.runescape.net.Buffer;
import com.runescape.node.Cache;
import com.runescape.util.SignLink;

/* GameObjectDefinition - Decompiled by JODE
 * Visit http://jode.sourceforge.net/
 */

public class GameObjectDefinition {
	public boolean aBoolean241;
	private byte aByte242;
	private int anInt243;
	public String name;
	private int anInt245;
	private static Model[] models = new Model[4];
	private byte aByte247;
	public int sizeX;
	private int anInt250;
	public int icon;
	private int[] anIntArray252;
	private int anInt253;
	public int anInt254;
	private boolean aBoolean256;
	public static boolean lowMemory;
	private static Buffer aBuffer258;
	public int anInt259 = -1;
	private static int[] anIntArray260;
	private static int anInt261;
	public boolean isWalkable;
	public int anInt263;
	public int[] anIntArray264;
	public int anInt265;
	public int sizeY;
	public boolean aBoolean267;
	private boolean aBoolean268 = true;
	public boolean aBoolean269;
	public static Client client;
	public boolean aBoolean271;
	public boolean isSolid;
	public int anInt273;
	private boolean aBoolean274;
	private static int anInt276;
	private int anInt277;
	private int[] modelIds;
	public int anInt279;
	public int anInt280;
	private int[] modelTypes;
	public byte[] description;
	public boolean hasActions;
	public boolean aBoolean284;
	public static Cache aCache285 = new Cache(30);
	public int anInt286;
	private static GameObjectDefinition[] aGameObjectDefinitionArray287;
	private int anInt288;
	private int[] anIntArray289;
	public static Cache modelCache = new Cache(500);
	public String[] actions;

	public static final GameObjectDefinition getObjectByHash(int i) {
		for (int i_0_ = 0; i_0_ < 20; i_0_++) {
			if (GameObjectDefinition.aGameObjectDefinitionArray287[i_0_].anInt259 == i) {
				return GameObjectDefinition.aGameObjectDefinitionArray287[i_0_];
			}
		}
		GameObjectDefinition.anInt276 = (GameObjectDefinition.anInt276 + 1) % 20;
		GameObjectDefinition gameobjectdefinition = GameObjectDefinition.aGameObjectDefinitionArray287[GameObjectDefinition.anInt276];
		GameObjectDefinition.aBuffer258.offset = GameObjectDefinition.anIntArray260[i];
		gameobjectdefinition.anInt259 = i;
		gameobjectdefinition.method233();
		gameobjectdefinition.method242(true, GameObjectDefinition.aBuffer258);
		return gameobjectdefinition;
	}

	private final void method233() {
		modelIds = null;
		modelTypes = null;
		name = null;
		description = null;
		anIntArray289 = null;
		anIntArray252 = null;
		sizeX = 1;
		sizeY = 1;
		isSolid = true;
		isWalkable = true;
		hasActions = false;
		aBoolean267 = false;
		aBoolean274 = false;
		aBoolean269 = false;
		anInt286 = -1;
		anInt280 = 16;
		aByte242 = (byte) 0;
		aByte247 = (byte) 0;
		actions = null;
		icon = -1;
		anInt263 = -1;
		aBoolean256 = false;
		aBoolean284 = true;
		anInt253 = 128;
		anInt277 = 128;
		anInt245 = 128;
		anInt273 = 0;
		anInt243 = 0;
		anInt250 = 0;
		anInt288 = 0;
		aBoolean241 = false;
		aBoolean271 = false;
		anInt265 = -1;
		anInt279 = -1;
		anInt254 = -1;
		anIntArray264 = null;
	}

	public final void method234(OnDemandRequester ondemandrequester, int i) {
		try {
			if (modelIds != null) {
				for (int i_1_ = 0; i_1_ < modelIds.length; i_1_++) {
					ondemandrequester.passiveRequest(modelIds[i_1_] & 0xffff, 0);
				}
				while (i >= 0) {
					aBoolean268 = !aBoolean268;
				}
			}
		} catch (RuntimeException runtimeexception) {
			SignLink.reportError("81306, " + ondemandrequester + ", " + i + ", " + runtimeexception.toString());
			throw new RuntimeException();
		}
	}

	public static final void method235(int i) {
		try {
			GameObjectDefinition.modelCache = null;
			GameObjectDefinition.aCache285 = null;
			GameObjectDefinition.anIntArray260 = null;
			GameObjectDefinition.aGameObjectDefinitionArray287 = null;
			GameObjectDefinition.aBuffer258 = null;
			if (i >= 0) {
				return;
			}
		} catch (RuntimeException runtimeexception) {
			SignLink.reportError("56607, " + i + ", " + runtimeexception.toString());
			throw new RuntimeException();
		}
	}

	public static final void method236(Archive archive) {
		GameObjectDefinition.aBuffer258 = new Buffer(archive.getFile("loc.dat"));
		Buffer buffer = new Buffer(archive.getFile("loc.idx"));
		GameObjectDefinition.anInt261 = buffer.getUnsignedLEShort();
		GameObjectDefinition.anIntArray260 = new int[GameObjectDefinition.anInt261];
		int i = 2;
		for (int i_2_ = 0; i_2_ < GameObjectDefinition.anInt261; i_2_++) {
			GameObjectDefinition.anIntArray260[i_2_] = i;
			i += buffer.getUnsignedLEShort();
		}
		GameObjectDefinition.aGameObjectDefinitionArray287 = new GameObjectDefinition[20];
		for (int i_3_ = 0; i_3_ < 20; i_3_++) {
			GameObjectDefinition.aGameObjectDefinitionArray287[i_3_] = new GameObjectDefinition();
		}
	}

	public final boolean method237(int i, boolean bool) {
		try {
			if (!bool) {
				throw new NullPointerException();
			}
			if (modelTypes == null) {
				if (modelIds == null) {
					return true;
				}
				if (i != 10) {
					return true;
				}
				boolean bool_4_ = true;
				for (int i_5_ = 0; i_5_ < modelIds.length; i_5_++) {
					bool_4_ &= Model.isCached(modelIds[i_5_] & 0xffff);
				}
				return bool_4_;
			}
			for (int i_6_ = 0; i_6_ < modelTypes.length; i_6_++) {
				if (modelTypes[i_6_] == i) {
					return Model.isCached(modelIds[i_6_] & 0xffff);
				}
			}
			return true;
		} catch (RuntimeException runtimeexception) {
			SignLink.reportError("89605, " + i + ", " + bool + ", " + runtimeexception.toString());
			throw new RuntimeException();
		}
	}

	public final Model method238(int i, int i_7_, int i_8_, int i_9_, int i_10_, int i_11_, int i_12_) {
		Model model = method241(0, i, i_12_, i_7_);
		if (model == null) {
			return null;
		}
		if (aBoolean267 || aBoolean274) {
			model = new Model(aBoolean267, -819, aBoolean274, model);
		}
		if (aBoolean267) {
			int i_13_ = (i_8_ + i_9_ + i_10_ + i_11_) / 4;
			for (int i_14_ = 0; i_14_ < model.vertexCount; i_14_++) {
				int i_15_ = model.verticesX[i_14_];
				int i_16_ = model.verticesZ[i_14_];
				int i_17_ = i_8_ + (i_9_ - i_8_) * (i_15_ + 64) / 128;
				int i_18_ = i_11_ + (i_10_ - i_11_) * (i_15_ + 64) / 128;
				int i_19_ = i_17_ + (i_18_ - i_17_) * (i_16_ + 64) / 128;
				model.verticesY[i_14_] += i_19_ - i_13_;
			}
			model.method415(false);
		}
		return model;
	}

	public final boolean method239(boolean bool) {
		try {
			if (modelIds == null) {
				return true;
			}
			boolean bool_20_ = true;
			for (int i = 0; i < modelIds.length; i++) {
				bool_20_ &= Model.isCached(modelIds[i] & 0xffff);
			}
			if (!bool) {
				throw new NullPointerException();
			}
			return bool_20_;
		} catch (RuntimeException runtimeexception) {
			SignLink.reportError("29403, " + bool + ", " + runtimeexception.toString());
			throw new RuntimeException();
		}
	}

	public final GameObjectDefinition method240(boolean bool) {
		try {
			if (!bool) {
				throw new NullPointerException();
			}
			int i = -1;
			if (anInt279 != -1) {
				VarBit varbit = VarBit.cache[anInt279];
				int i_21_ = varbit.anInt737;
				int i_22_ = varbit.anInt738;
				int i_23_ = varbit.anInt739;
				int i_24_ = Client.anIntArray1257[i_23_ - i_22_];
				i = GameObjectDefinition.client.settings[i_21_] >> i_22_ & i_24_;
			} else if (anInt254 != -1) {
				i = GameObjectDefinition.client.settings[anInt254];
			}
			if (i < 0 || i >= anIntArray264.length || anIntArray264[i] == -1) {
				return null;
			}
			return GameObjectDefinition.getObjectByHash(anIntArray264[i]);
		} catch (RuntimeException runtimeexception) {
			SignLink.reportError("60219, " + bool + ", " + runtimeexception.toString());
			throw new RuntimeException();
		}
	}

	private final Model method241(int i, int i_25_, int i_26_, int i_27_) {
		try {
			Model model = null;
			long l;
			if (modelTypes == null) {
				if (i_25_ != 10) {
					return null;
				}
				l = (anInt259 << 6) + i_27_ + ((long) (i_26_ + 1) << 32);
				Model model_28_ = (Model) GameObjectDefinition.aCache285.get(l);
				if (model_28_ != null) {
					return model_28_;
				}
				if (modelIds == null) {
					return null;
				}
				boolean bool = aBoolean256 ^ i_27_ > 3;
				int modelCount = modelIds.length;
				for (int modelId = 0; modelId < modelCount; modelId++) {
					int i_31_ = modelIds[modelId];
					if (bool) {
						i_31_ += 65536;
					}
					model = (Model) GameObjectDefinition.modelCache.get(i_31_);
					if (model == null) {
						model = Model.getModel(i_31_ & 0xffff);
						if (model == null) {
							return null;
						}
						if (bool) {
							model.method425(0);
						}
						GameObjectDefinition.modelCache.put(model, i_31_);
					}
					if (modelCount > 1) {
						GameObjectDefinition.models[modelId] = model;
					}
				}
				if (modelCount > 1) {
					model = new Model(modelCount, GameObjectDefinition.models);
				}
			} else {
				int i_32_ = -1;
				for (int i_33_ = 0; i_33_ < modelTypes.length; i_33_++) {
					if (modelTypes[i_33_] == i_25_) {
						i_32_ = i_33_;
						break;
					}
				}
				if (i_32_ == -1) {
					return null;
				}
				l = (anInt259 << 6) + (i_32_ << 3) + i_27_ + ((long) (i_26_ + 1) << 32);
				Model model_34_ = (Model) GameObjectDefinition.aCache285.get(l);
				if (model_34_ != null) {
					return model_34_;
				}
				int i_35_ = modelIds[i_32_];
				boolean bool = aBoolean256 ^ i_27_ > 3;
				if (bool) {
					i_35_ += 65536;
				}
				model = (Model) GameObjectDefinition.modelCache.get(i_35_);
				if (model == null) {
					model = Model.getModel(i_35_ & 0xffff);
					if (model == null) {
						return null;
					}
					if (bool) {
						model.method425(0);
					}
					GameObjectDefinition.modelCache.put(model, i_35_);
				}
			}
			boolean bool;
			if (anInt253 != 128 || anInt277 != 128 || anInt245 != 128) {
				bool = true;
			} else {
				bool = false;
			}
			boolean bool_36_;
			if (anInt243 != 0 || anInt250 != 0 || anInt288 != 0) {
				bool_36_ = true;
			} else {
				bool_36_ = false;
			}
			Model model_37_ = new Model(anIntArray289 == null, Animation.exists(i_26_), i_27_ == 0 && i_26_ == -1
					&& !bool && !bool_36_, model);
			if (i_26_ != -1) {
				model_37_.createBones();
				model_37_.applyTransform(i_26_);
				model_37_.triangleSkin = null;
				model_37_.vectorSkin = null;
			}
			while (i_27_-- > 0) {
				model_37_.rotate90Degrees(360);
			}
			if (anIntArray289 != null) {
				for (int i_38_ = 0; i_38_ < anIntArray289.length; i_38_++) {
					model_37_.recolor(anIntArray289[i_38_], anIntArray252[i_38_]);
				}
			}
			if (bool) {
				model_37_.scaleT(anInt253, anInt245, anInt277);
			}
			if (bool_36_) {
				model_37_.translate(anInt243, anInt250, anInt288);
			}
			model_37_.applyLighting(64 + aByte242, 768 + aByte247 * 5, -50, -10, -50, !aBoolean274);
			if (anInt265 == 1) {
				model_37_.anInt1647 = model_37_.modelHeight;
			}
			GameObjectDefinition.aCache285.put(model_37_, l);
			if (i != 0) {
			}
			return model_37_;
		} catch (RuntimeException runtimeexception) {
			SignLink.reportError("87963, " + i + ", " + i_25_ + ", " + i_26_ + ", " + i_27_ + ", "
					+ runtimeexception.toString());
			throw new RuntimeException();
		}
	}

	private final void method242(boolean bool, Buffer buffer) {
		do {
			try {
				if (!bool) {
				}
				int hasActionsInt = -1;
				while (true) {
					int attributeId = buffer.getUnsignedByte();
					if (attributeId == 0) {
						break;
					}
					if (attributeId == 1) {
						int modelCount = buffer.getUnsignedByte();
						if (modelCount > 0) {
							if (modelIds == null || GameObjectDefinition.lowMemory) {
								modelTypes = new int[modelCount];
								modelIds = new int[modelCount];
								for (int model = 0; model < modelCount; model++) {
									modelIds[model] = buffer.getUnsignedLEShort();
									modelTypes[model] = buffer.getUnsignedByte();
								}
							} else {
								buffer.offset += modelCount * 3;
							}
						}
					} else if (attributeId == 2) {
						name = buffer.getString();
					} else if (attributeId == 3) {
						description = buffer.getStringAsBytes();
					} else if (attributeId == 5) {
						int modelCount = buffer.getUnsignedByte();
						if (modelCount > 0) {
							if (modelIds == null || GameObjectDefinition.lowMemory) {
								modelTypes = null;
								modelIds = new int[modelCount];
								for (int i_43_ = 0; i_43_ < modelCount; i_43_++) {
									modelIds[i_43_] = buffer.getUnsignedLEShort();
								}
							} else {
								buffer.offset += modelCount * 2;
							}
						}
					} else if (attributeId == 14) {
						sizeX = buffer.getUnsignedByte();
					} else if (attributeId == 15) {
						sizeY = buffer.getUnsignedByte();
					} else if (attributeId == 17) {
						isSolid = false;
					} else if (attributeId == 18) {
						isWalkable = false;
					} else if (attributeId == 19) {
						hasActionsInt = buffer.getUnsignedByte();
						if (hasActionsInt == 1) {
							hasActions = true;
						}
					} else if (attributeId == 21) {
						aBoolean267 = true;
					} else if (attributeId == 22) {
						aBoolean274 = true;
					} else if (attributeId == 23) {
						aBoolean269 = true;
					} else if (attributeId == 24) {
						anInt286 = buffer.getUnsignedLEShort();
						if (anInt286 == 65535) {
							anInt286 = -1;
						}
					} else if (attributeId == 28) {
						anInt280 = buffer.getUnsignedByte();
					} else if (attributeId == 29) {
						aByte242 = buffer.get();
					} else if (attributeId == 39) {
						aByte247 = buffer.get();
					} else if (attributeId >= 30 && attributeId < 39) {
						if (actions == null) {
							actions = new String[5];
						}
						actions[attributeId - 30] = buffer.getString();
						if (actions[attributeId - 30].equalsIgnoreCase("hidden")) {
							actions[attributeId - 30] = null;
						}
					} else if (attributeId == 40) {
						int i_44_ = buffer.getUnsignedByte();
						anIntArray289 = new int[i_44_];
						anIntArray252 = new int[i_44_];
						for (int i_45_ = 0; i_45_ < i_44_; i_45_++) {
							anIntArray289[i_45_] = buffer.getUnsignedLEShort();
							anIntArray252[i_45_] = buffer.getUnsignedLEShort();
						}
					} else if (attributeId == 60) {
						icon = buffer.getUnsignedLEShort();
					} else if (attributeId == 62) {
						aBoolean256 = true;
					} else if (attributeId == 64) {
						aBoolean284 = false;
					} else if (attributeId == 65) {
						anInt253 = buffer.getUnsignedLEShort();
					} else if (attributeId == 66) {
						anInt277 = buffer.getUnsignedLEShort();
					} else if (attributeId == 67) {
						anInt245 = buffer.getUnsignedLEShort();
					} else if (attributeId == 68) {
						anInt263 = buffer.getUnsignedLEShort();
					} else if (attributeId == 69) {
						anInt273 = buffer.getUnsignedByte();
					} else if (attributeId == 70) {
						anInt243 = buffer.getShort();
					} else if (attributeId == 71) {
						anInt250 = buffer.getShort();
					} else if (attributeId == 72) {
						anInt288 = buffer.getShort();
					} else if (attributeId == 73) {
						aBoolean241 = true;
					} else if (attributeId == 74) {
						aBoolean271 = true;
					} else if (attributeId == 75) {
						anInt265 = buffer.getUnsignedByte();
					} else if (attributeId == 77) {
						anInt279 = buffer.getUnsignedLEShort();
						if (anInt279 == 65535) {
							anInt279 = -1;
						}
						anInt254 = buffer.getUnsignedLEShort();
						if (anInt254 == 65535) {
							anInt254 = -1;
						}
						int i_46_ = buffer.getUnsignedByte();
						anIntArray264 = new int[i_46_ + 1];
						for (int i_47_ = 0; i_47_ <= i_46_; i_47_++) {
							anIntArray264[i_47_] = buffer.getUnsignedLEShort();
							if (anIntArray264[i_47_] == 65535) {
								anIntArray264[i_47_] = -1;
							}
						}
					}
				}
				if (hasActionsInt == -1) {
					hasActions = false;
					if (modelIds != null && (modelTypes == null || modelTypes[0] == 10)) {
						hasActions = true;
					}
					if (actions != null) {
						hasActions = true;
					}
				}
				if (aBoolean271) {
					isSolid = false;
					isWalkable = false;
				}
				if (anInt265 != -1) {
					break;
				}
				anInt265 = isSolid ? 1 : 0;
			} catch (RuntimeException runtimeexception) {
				SignLink.reportError("46459, " + bool + ", " + buffer + ", " + runtimeexception.toString());
				throw new RuntimeException();
			}
			break;
		} while (false);
	}

	GameObjectDefinition() {
		/* empty */
	}
}
