package com.jagex.runescape.cache.def;

import com.jagex.runescape.Game;
import com.jagex.runescape.cache.Archive;
import com.jagex.runescape.cache.cfg.VarBit;
import com.jagex.runescape.collection.Cache;
import com.jagex.runescape.media.Animation;
import com.jagex.runescape.media.renderable.Model;
import com.jagex.runescape.net.Buffer;
import com.jagex.runescape.net.requester.OnDemandRequester;

public class GameObjectDefinition {

	public boolean unknown;
	private byte modelLightFalloff;
	private int translateX;
	public String name;
	private int modelSizeZ;
	private static Model[] models = new Model[4];
	private byte modelLightAmbient;
	public int sizeX;
	private int translateY;
	public int icon;
	private int[] originalModelColors;
	private int modelSizeX;
	public int configId;
	private boolean unknown3;
	public static boolean lowMemory;
	private static Buffer buffer;
	public int id = -1;
	private static int[] bufferOffsets;
	private static int definitionCount;
	public boolean walkable;
	public int mapScene;
	public int[] childrenIds;
	public int solidInt;
	public int sizeY;
	public boolean adjustToTerrain;
	public boolean aBoolean269;
	public static Game client;
	public boolean unwalkableSolid;
	public boolean solid;
	public int face;
	private boolean nonFlatShading;
	private static int cacheIndex;
	private int modelSizeY;
	private int[] modelIds;
	public int varbitId;
	public int unknown4;
	private int[] modelTypes;
	public byte[] description;
	public boolean actionsBoolean;
	public boolean unknown2;
	public static Cache animatedModelCache = new Cache(30);
	public int animationId;
	private static GameObjectDefinition[] cache;
	private int translateZ;
	private int[] modifiedModelColors;
	public static Cache modelCache = new Cache(500);
	public String[] actions;

	public static final GameObjectDefinition getDefinition(int id) {
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
		sizeX = 1;
		sizeY = 1;
		solid = true;
		walkable = true;
		actionsBoolean = false;
		adjustToTerrain = false;
		nonFlatShading = false;
		aBoolean269 = false;
		animationId = -1;
		unknown4 = 16;
		modelLightFalloff = (byte) 0;
		modelLightAmbient = (byte) 0;
		actions = null;
		icon = -1;
		mapScene = -1;
		unknown3 = false;
		unknown2 = true;
		modelSizeX = 128;
		modelSizeY = 128;
		modelSizeZ = 128;
		face = 0;
		translateX = 0;
		translateY = 0;
		translateZ = 0;
		unknown = false;
		unwalkableSolid = false;
		solidInt = -1;
		varbitId = -1;
		configId = -1;
		childrenIds = null;
	}

	public final void passiveRequestModels(OnDemandRequester onDemandRequester) {
		if (modelIds != null) {
			for (int modelId : modelIds) {
				onDemandRequester.passiveRequest(modelId & 0xffff, 0);
			}
		}
	}

	public static final void reset() {
		GameObjectDefinition.modelCache = null;
		GameObjectDefinition.animatedModelCache = null;
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

	public final boolean hasModelType(int modelType) {
		if (modelTypes == null) {
			if (modelIds == null) {
				return true;
			}
			if (modelType != 10) {
				return true;
			}
			boolean cached = true;
			for (int modelId : modelIds) {
				cached &= Model.isCached(modelId & 0xffff);
			}
			return cached;
		}
		for (int model = 0; model < modelTypes.length; model++) {
			if (modelTypes[model] == modelType) {
				return Model.isCached(modelIds[model] & 0xffff);
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
		for (int modelId : modelIds) {
			cached &= Model.isCached(modelId & 0xffff);
		}
		return cached;
	}

	public final GameObjectDefinition getChildDefinition() {
		int child = -1;
		if (varbitId != -1) {
			VarBit varbit = VarBit.cache[varbitId];
			int configId = varbit.configId;
			int leastSignificantBit = varbit.leastSignificantBit;
			int mostSignificantBit = varbit.mostSignificantBit;
			int bit = Game.BITFIELD_MAX_VALUE[mostSignificantBit - leastSignificantBit];
			child = GameObjectDefinition.client.widgetSettings[configId] >> leastSignificantBit & bit;
		} else if (configId != -1) {
			child = GameObjectDefinition.client.widgetSettings[configId];
		}
		if (child < 0 || child >= childrenIds.length || childrenIds[child] == -1) {
			return null;
		}
		return GameObjectDefinition.getDefinition(childrenIds[child]);
	}

	private final Model getGameObjectAnimatedModel(int type, int animationId, int face) {
		Model subModel = null;
		long hash;
		if (modelTypes == null) {
			if (type != 10) {
				return null;
			}
			hash = (id << 6) + face + ((long) (animationId + 1) << 32);
			Model cachedModel = (Model) GameObjectDefinition.animatedModelCache.get(hash);
			if (cachedModel != null) {
				return cachedModel;
			}
			if (modelIds == null) {
				return null;
			}
			boolean mirror = unknown3 ^ face > 3;
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
			Model model = (Model) GameObjectDefinition.animatedModelCache.get(hash);
			if (model != null) {
				return model;
			}
			int modelId = modelIds[modelType];
			boolean mirror = unknown3 ^ face > 3;
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
		boolean needsTranslation;
		if (translateX != 0 || translateY != 0 || translateZ != 0) {
			needsTranslation = true;
		} else {
			needsTranslation = false;
		}
		Model animtedModel = new Model(modifiedModelColors == null, Animation.exists(animationId), face == 0
				&& animationId == -1 && !scale && !needsTranslation, subModel);
		if (animationId != -1) {
			animtedModel.createBones();
			animtedModel.applyTransform(animationId);
			animtedModel.triangleSkin = null;
			animtedModel.vectorSkin = null;
		}
		while (face-- > 0) {
			animtedModel.rotate90Degrees(360);
		}
		if (modifiedModelColors != null) {
			for (int i_38_ = 0; i_38_ < modifiedModelColors.length; i_38_++) {
				animtedModel.recolor(modifiedModelColors[i_38_], originalModelColors[i_38_]);
			}
		}
		if (scale) {
			animtedModel.scaleT(modelSizeX, modelSizeZ, modelSizeY);
		}
		if (needsTranslation) {
			animtedModel.translate(translateX, translateY, translateZ);
		}
		animtedModel.applyLighting(64 + modelLightFalloff, 768 + modelLightAmbient * 5, -50, -10, -50, !nonFlatShading);
		if (solidInt == 1) {
			animtedModel.anInt1647 = animtedModel.modelHeight;
		}
		GameObjectDefinition.animatedModelCache.put(animtedModel, hash);
		return animtedModel;
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
						for (int model = 0; model < modelCount; model++) {
							modelIds[model] = buffer.getUnsignedLEShort();
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
				unknown4 = buffer.getUnsignedByte();
			} else if (attributeId == 29) {
				modelLightFalloff = buffer.get();
			} else if (attributeId == 39) {
				modelLightAmbient = buffer.get();
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
				unknown3 = true;
			} else if (attributeId == 64) {
				unknown2 = false;
			} else if (attributeId == 65) {
				modelSizeX = buffer.getUnsignedLEShort();
			} else if (attributeId == 66) {
				modelSizeY = buffer.getUnsignedLEShort();
			} else if (attributeId == 67) {
				modelSizeZ = buffer.getUnsignedLEShort();
			} else if (attributeId == 68) {
				mapScene = buffer.getUnsignedLEShort();
			} else if (attributeId == 69) {
				face = buffer.getUnsignedByte();
			} else if (attributeId == 70) {
				translateX = buffer.getShort();
			} else if (attributeId == 71) {
				translateY = buffer.getShort();
			} else if (attributeId == 72) {
				translateZ = buffer.getShort();
			} else if (attributeId == 73) {
				unknown = true;
			} else if (attributeId == 74) {
				unwalkableSolid = true;
			} else if (attributeId == 75) {
				solidInt = buffer.getUnsignedByte();
			} else if (attributeId == 77) {
				varbitId = buffer.getUnsignedLEShort();
				if (varbitId == 65535) {
					varbitId = -1;
				}
				configId = buffer.getUnsignedLEShort();
				if (configId == 65535) {
					configId = -1;
				}
				int childrenCount = buffer.getUnsignedByte();
				childrenIds = new int[childrenCount + 1];
				for (int child = 0; child <= childrenCount; child++) {
					childrenIds[child] = buffer.getUnsignedLEShort();
					if (childrenIds[child] == 65535) {
						childrenIds[child] = -1;
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
		if (unwalkableSolid) {
			solid = false;
			walkable = false;
		}
		if (solidInt != -1) {
			return;
		}
		solidInt = solid ? 1 : 0;
	}
}
