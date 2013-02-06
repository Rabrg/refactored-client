package com.runescape.cache.def;

import com.runescape.Client;
import com.runescape.anim.Animation;
import com.runescape.cache.Archive;
import com.runescape.cache.requester.OnDemandRequester;
import com.runescape.graphic.Model;
import com.runescape.net.Buffer;
import com.runescape.node.Cache;

public class GameObjectDefinition {

	public boolean aBoolean241;
	private byte brightness;
	private int anInt243;
	public String name;
	private int modelSizeZ;
	private static Model[] models = new Model[4];
	private byte contrast;
	public int width;
	private int anInt250;
	public int icon;
	private int[] originalModelColors;
	private int modelSizeX;
	public int anInt254;
	private boolean aBoolean256;
	public static boolean lowMemory;
	private static Buffer buffer;
	public int id = -1;
	private static int[] bufferOffsets;
	private static int definitionCount;
	public boolean walkable;
	public int anInt263;
	public int[] anIntArray264;
	public int solidInt;
	public int height;
	public boolean adjustToTerrain;
	public boolean aBoolean269;
	public static Client client;
	public boolean aBoolean271;
	public boolean solid;
	public int anInt273;
	private boolean nonFlatShading;
	private static int cacheIndex;
	private int modelSizeY;
	private int[] modelIds;
	public int anInt279;
	public int anInt280;
	private int[] modelTypes;
	public byte[] description;
	public boolean actionsBoolean;
	public boolean aBoolean284;
	public static Cache modelCache2 = new Cache(30);
	public int animationId;
	private static GameObjectDefinition[] cache;
	private int anInt288;
	private int[] modifiedModelColors;
	public static Cache modelCache = new Cache(500);
	public String[] actions;

	public static final GameObjectDefinition get(int id) {
		for (int index = 0; index < 20; index++) {
			if (GameObjectDefinition.cache[index].id == id) {
				return GameObjectDefinition.cache[index];
			}
		}
		GameObjectDefinition.cacheIndex = (GameObjectDefinition.cacheIndex + 1) % 20;
		GameObjectDefinition definition = GameObjectDefinition.cache[GameObjectDefinition.cacheIndex];
		GameObjectDefinition.buffer.offset = GameObjectDefinition.bufferOffsets[id];
		definition.id = id;
		definition.setDefaultValues();
		definition.load(GameObjectDefinition.buffer);
		return definition;
	}

	private final void setDefaultValues() {
		modelIds = null;
		modelTypes = null;
		name = null;
		description = null;
		modifiedModelColors = null;
		originalModelColors = null;
		width = 1;
		height = 1;
		solid = true;
		walkable = true;
		actionsBoolean = false;
		adjustToTerrain = false;
		nonFlatShading = false;
		aBoolean269 = false;
		animationId = -1;
		anInt280 = 16;
		brightness = (byte) 0;
		contrast = (byte) 0;
		actions = null;
		icon = -1;
		anInt263 = -1;
		aBoolean256 = false;
		aBoolean284 = true;
		modelSizeX = 128;
		modelSizeY = 128;
		modelSizeZ = 128;
		anInt273 = 0;
		anInt243 = 0;
		anInt250 = 0;
		anInt288 = 0;
		aBoolean241 = false;
		aBoolean271 = false;
		solidInt = -1;
		anInt279 = -1;
		anInt254 = -1;
		anIntArray264 = null;
	}

	public final void passiveRequestModels(OnDemandRequester onDemandRequester) {
		if (modelIds != null) {
			for (int model = 0; model < modelIds.length; model++) {
				onDemandRequester.passiveRequest(modelIds[model] & 0xffff, 0);
			}
		}
	}

	public static final void reset() {
		GameObjectDefinition.modelCache = null;
		GameObjectDefinition.modelCache2 = null;
		GameObjectDefinition.bufferOffsets = null;
		GameObjectDefinition.cache = null;
		GameObjectDefinition.buffer = null;
	}

	public static final void load(Archive archive) {
		GameObjectDefinition.buffer = new Buffer(archive.getFile("loc.dat"));
		Buffer buffer = new Buffer(archive.getFile("loc.idx"));
		GameObjectDefinition.definitionCount = buffer.getUnsignedLEShort();
		GameObjectDefinition.bufferOffsets = new int[GameObjectDefinition.definitionCount];
		int offset = 2;
		for (int index = 0; index < GameObjectDefinition.definitionCount; index++) {
			GameObjectDefinition.bufferOffsets[index] = offset;
			offset += buffer.getUnsignedLEShort();
		}
		GameObjectDefinition.cache = new GameObjectDefinition[20];
		for (int definition = 0; definition < 20; definition++) {
			GameObjectDefinition.cache[definition] = new GameObjectDefinition();
		}
	}

	public final boolean unknown(int model) {
		if (modelTypes == null) {
			if (modelIds == null) {
				return true;
			}
			if (model != 10) {
				return true;
			}
			boolean isCached = true;
			for (int index = 0; index < modelIds.length; index++) {
				isCached &= Model.isCached(modelIds[index] & 0xffff);
			}
			return isCached;
		}
		for (int index = 0; index < modelTypes.length; index++) {
			if (modelTypes[index] == model) {
				return Model.isCached(modelIds[index] & 0xffff);
			}
		}
		return true;
	}

	public final Model getGameObjectModel(int type, int face, int i_8_, int i_9_, int i_10_, int i_11_, int animationId) {
		Model model = getGameObjectAnimatedModel(type, animationId, face);
		if (model == null) {
			return null;
		}
		if (adjustToTerrain || nonFlatShading) {
			model = new Model(adjustToTerrain, -819, nonFlatShading, model);
		}
		if (adjustToTerrain) {
			int i_13_ = (i_8_ + i_9_ + i_10_ + i_11_) / 4;
			for (int vertex = 0; vertex < model.vertexCount; vertex++) {
				int vertexX = model.verticesX[vertex];
				int vertexY = model.verticesZ[vertex];
				int i_17_ = i_8_ + (i_9_ - i_8_) * (vertexX + 64) / 128;
				int i_18_ = i_11_ + (i_10_ - i_11_) * (vertexX + 64) / 128;
				int i_19_ = i_17_ + (i_18_ - i_17_) * (vertexY + 64) / 128;
				model.verticesY[vertex] += i_19_ - i_13_;
			}
			model.normalise(false);
		}
		return model;
	}

	public final boolean isModelCached() {
		if (modelIds == null) {
			return true;
		}
		boolean cached = true;
		for (int model = 0; model < modelIds.length; model++) {
			cached &= Model.isCached(modelIds[model] & 0xffff);
		}
		return cached;
	}

	public final GameObjectDefinition unknown2() {
		int i = -1;
		if (anInt279 != -1) {
			VarBit varbit = VarBit.cache[anInt279];
			int i_21_ = varbit.configId;
			int i_22_ = varbit.leastSignificantBit;
			int i_23_ = varbit.mostSignificantBit;
			int i_24_ = Client.BITFIELD_MAX_VALUE[i_23_ - i_22_];
			i = GameObjectDefinition.client.settings[i_21_] >> i_22_ & i_24_;
		} else if (anInt254 != -1) {
			i = GameObjectDefinition.client.settings[anInt254];
		}
		if (i < 0 || i >= anIntArray264.length || anIntArray264[i] == -1) {
			return null;
		}
		return GameObjectDefinition.get(anIntArray264[i]);
	}

	private final Model getGameObjectAnimatedModel(int type, int animationId, int face) {
		Model subModel = null;
		long hash;
		if (modelTypes == null) {
			if (type != 10) {
				return null;
			}
			hash = (id << 6) + face + ((long) (animationId + 1) << 32);
			Model cachedModel = (Model) GameObjectDefinition.modelCache2.get(hash);
			if (cachedModel != null) {
				return cachedModel;
			}
			if (modelIds == null) {
				return null;
			}
			boolean mirror = aBoolean256 ^ face > 3;
			int modelCount = modelIds.length;
			for (int modelId = 0; modelId < modelCount; modelId++) {
				int subModelId = modelIds[modelId];
				if (mirror) {
					subModelId += 65536;
				}
				subModel = (Model) GameObjectDefinition.modelCache.get(subModelId);
				if (subModel == null) {
					subModel = Model.getModel(subModelId & 0xffff);
					if (subModel == null) {
						return null;
					}
					if (mirror) {
						subModel.mirror(0);
					}
					GameObjectDefinition.modelCache.put(subModel, subModelId);
				}
				if (modelCount > 1) {
					GameObjectDefinition.models[modelId] = subModel;
				}
			}
			if (modelCount > 1) {
				subModel = new Model(modelCount, GameObjectDefinition.models);
			}
		} else {
			int modelType = -1;
			for (int index = 0; index < modelTypes.length; index++) {
				if (modelTypes[index] == type) {
					modelType = index;
					break;
				}
			}
			if (modelType == -1) {
				return null;
			}
			hash = (id << 6) + (modelType << 3) + face + ((long) (animationId + 1) << 32);
			Model model = (Model) GameObjectDefinition.modelCache2.get(hash);
			if (model != null) {
				return model;
			}
			int modelId = modelIds[modelType];
			boolean mirror = aBoolean256 ^ face > 3;
			if (mirror) {
				modelId += 65536;
			}
			subModel = (Model) GameObjectDefinition.modelCache.get(modelId);
			if (subModel == null) {
				subModel = Model.getModel(modelId & 0xffff);
				if (subModel == null) {
					return null;
				}
				if (mirror) {
					subModel.mirror(0);
				}
				GameObjectDefinition.modelCache.put(subModel, modelId);
			}
		}
		boolean scale;
		if (modelSizeX != 128 || modelSizeY != 128 || modelSizeZ != 128) {
			scale = true;
		} else {
			scale = false;
		}
		boolean bool_36_;
		if (anInt243 != 0 || anInt250 != 0 || anInt288 != 0) {
			bool_36_ = true;
		} else {
			bool_36_ = false;
		}
		Model model_37_ = new Model(modifiedModelColors == null, Animation.exists(animationId), face == 0
				&& animationId == -1 && !scale && !bool_36_, subModel);
		if (animationId != -1) {
			model_37_.createBones();
			model_37_.applyTransform(animationId);
			model_37_.triangleSkin = null;
			model_37_.vectorSkin = null;
		}
		while (face-- > 0) {
			model_37_.rotate90Degrees(360);
		}
		if (modifiedModelColors != null) {
			for (int i_38_ = 0; i_38_ < modifiedModelColors.length; i_38_++) {
				model_37_.recolor(modifiedModelColors[i_38_], originalModelColors[i_38_]);
			}
		}
		if (scale) {
			model_37_.scaleT(modelSizeX, modelSizeZ, modelSizeY);
		}
		if (bool_36_) {
			model_37_.translate(anInt243, anInt250, anInt288);
		}
		model_37_.applyLighting(64 + brightness, 768 + contrast * 5, -50, -10, -50, !nonFlatShading);
		if (solidInt == 1) {
			model_37_.anInt1647 = model_37_.modelHeight;
		}
		GameObjectDefinition.modelCache2.put(model_37_, hash);
		return model_37_;
	}

	private final void load(Buffer buffer) {
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
				width = buffer.getUnsignedByte();
			} else if (attributeId == 15) {
				height = buffer.getUnsignedByte();
			} else if (attributeId == 17) {
				solid = false;
			} else if (attributeId == 18) {
				walkable = false;
			} else if (attributeId == 19) {
				hasActionsInt = buffer.getUnsignedByte();
				if (hasActionsInt == 1) {
					actionsBoolean = true;
				}
			} else if (attributeId == 21) {
				adjustToTerrain = true;
			} else if (attributeId == 22) {
				nonFlatShading = true;
			} else if (attributeId == 23) {
				aBoolean269 = true;
			} else if (attributeId == 24) {
				animationId = buffer.getUnsignedLEShort();
				if (animationId == 65535) {
					animationId = -1;
				}
			} else if (attributeId == 28) {
				anInt280 = buffer.getUnsignedByte();
			} else if (attributeId == 29) {
				brightness = buffer.get();
			} else if (attributeId == 39) {
				contrast = buffer.get();
			} else if (attributeId >= 30 && attributeId < 39) {
				if (actions == null) {
					actions = new String[5];
				}
				actions[attributeId - 30] = buffer.getString();
				if (actions[attributeId - 30].equalsIgnoreCase("hidden")) {
					actions[attributeId - 30] = null;
				}
			} else if (attributeId == 40) {
				int modelColorCount = buffer.getUnsignedByte();
				modifiedModelColors = new int[modelColorCount];
				originalModelColors = new int[modelColorCount];
				for (int modelColor = 0; modelColor < modelColorCount; modelColor++) {
					modifiedModelColors[modelColor] = buffer.getUnsignedLEShort();
					originalModelColors[modelColor] = buffer.getUnsignedLEShort();
				}
			} else if (attributeId == 60) {
				icon = buffer.getUnsignedLEShort();
			} else if (attributeId == 62) {
				aBoolean256 = true;
			} else if (attributeId == 64) {
				aBoolean284 = false;
			} else if (attributeId == 65) {
				modelSizeX = buffer.getUnsignedLEShort();
			} else if (attributeId == 66) {
				modelSizeY = buffer.getUnsignedLEShort();
			} else if (attributeId == 67) {
				modelSizeZ = buffer.getUnsignedLEShort();
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
				solidInt = buffer.getUnsignedByte();
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
			actionsBoolean = false;
			if (modelIds != null && (modelTypes == null || modelTypes[0] == 10)) {
				actionsBoolean = true;
			}
			if (actions != null) {
				actionsBoolean = true;
			}
		}
		if (aBoolean271) {
			solid = false;
			walkable = false;
		}
		if (solidInt != -1) {
			return;
		}
		solidInt = solid ? 1 : 0;
	}
}
