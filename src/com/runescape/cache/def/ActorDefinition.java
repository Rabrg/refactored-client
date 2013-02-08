package com.runescape.cache.def;

import com.runescape.Client;
import com.runescape.anim.Animation;
import com.runescape.cache.Archive;
import com.runescape.collection.Cache;
import com.runescape.graphic.Model;
import com.runescape.net.Buffer;

public class ActorDefinition {

	public int turnLeftAnimationId = -1;
	private static int bufferIndex;
	public int varBitId = -1;
	public int turnAroundAnimationId = -1;
	public int settingId = -1;
	private static Buffer buffer;
	public int combatLevel = -1;
	public static int size;
	public String name;
	public String[] actions;
	public int walkAnimationId = -1;
	public byte boundaryDimension = 1;
	private int[] originalModelColors;
	private static int[] bufferOffsets;
	private int[] headModelIndexes;
	public int headIcon = -1;
	private int[] modifiedModelColors;
	public int standAnimationId = -1;
	public long id = -1;
	public int degreesToTurn = 32;
	private static ActorDefinition[] cache;
	public static Client client;
	public int turnRightAnimationId = -1;
	public boolean clickable = true;
	private int brightness;
	private int sizeY = 128;
	public boolean minimapVisible = true;
	public int[] childrenIndexes;
	public byte[] description;
	private int sizeXZ = 128;
	private int contrast;
	public boolean visible = false;
	private int[] modelIds;
	public static Cache modelCache = new Cache(30);

	public static final ActorDefinition getDefinition(int id) {
		for (int i = 0; i < 20; i++) {
			if (ActorDefinition.cache[i].id == id) {
				return ActorDefinition.cache[i];
			}
		}
		ActorDefinition.bufferIndex = (ActorDefinition.bufferIndex + 1) % 20;
		ActorDefinition definition = ActorDefinition.cache[ActorDefinition.bufferIndex] = new ActorDefinition();
		ActorDefinition.buffer.offset = ActorDefinition.bufferOffsets[id];
		definition.id = id;
		definition.loadDefinition(true, ActorDefinition.buffer);
		return definition;
	}

	public final Model getHeadModel() {
		if (childrenIndexes != null) {
			ActorDefinition definition = getChildDefinition();
			if (definition == null) {
				return null;
			}
			return definition.getHeadModel();
		}
		if (headModelIndexes == null) {
			return null;
		}
		boolean cached = false;
		for (int headModel = 0; headModel < headModelIndexes.length; headModel++) {
			if (!Model.isCached(headModelIndexes[headModel])) {
				cached = true;
			}
		}
		if (cached) {
			return null;
		}
		Model[] headModels = new Model[headModelIndexes.length];
		for (int model = 0; model < headModelIndexes.length; model++) {
			headModels[model] = Model.getModel(headModelIndexes[model]);
		}
		Model headModel;
		if (headModels.length == 1) {
			headModel = headModels[0];
		} else {
			headModel = new Model(headModels.length, headModels);
		}
		if (modifiedModelColors != null) {
			for (int color = 0; color < modifiedModelColors.length; color++) {
				headModel.recolor(modifiedModelColors[color], originalModelColors[color]);
			}
		}
		return headModel;
	}

	public final ActorDefinition getChildDefinition() {
		int childId = -1;
		if (varBitId != -1) {
			VarBit varbit = VarBit.cache[varBitId];
			int configId = varbit.configId;
			int leastSignificantBit = varbit.leastSignificantBit;
			int mostSignificantBit = varbit.mostSignificantBit;
			int bit = Client.BITFIELD_MAX_VALUE[mostSignificantBit - leastSignificantBit];
			childId = ActorDefinition.client.settings[configId] >> leastSignificantBit & bit;
		} else if (settingId != -1) {
			childId = ActorDefinition.client.settings[settingId];
		}
		if (childId < 0 || childId >= childrenIndexes.length || childrenIndexes[childId] == -1) {
			return null;
		}
		return ActorDefinition.getDefinition(childrenIndexes[childId]);
	}

	public static final void load(Archive archive) {
		ActorDefinition.buffer = new Buffer(archive.getFile("npc.dat"));
		Buffer buffer = new Buffer(archive.getFile("npc.idx"));
		ActorDefinition.size = buffer.getUnsignedLEShort();
		ActorDefinition.bufferOffsets = new int[ActorDefinition.size];
		int offset = 2;
		for (int bufferIndex = 0; bufferIndex < ActorDefinition.size; bufferIndex++) {
			ActorDefinition.bufferOffsets[bufferIndex] = offset;
			offset += buffer.getUnsignedLEShort();
		}
		ActorDefinition.cache = new ActorDefinition[20];
		for (int cacheIndex = 0; cacheIndex < 20; cacheIndex++) {
			ActorDefinition.cache[cacheIndex] = new ActorDefinition();
		}
	}

	public static final void reset() {
		ActorDefinition.modelCache = null;
		ActorDefinition.bufferOffsets = null;
		ActorDefinition.cache = null;
		ActorDefinition.buffer = null;
	}

	public final Model getChildModel(int frameId2, int frameId, int[] framesFrom2) {
		if (childrenIndexes != null) {
			ActorDefinition childDefinition = getChildDefinition();
			if (childDefinition == null) {
				return null;
			}
			return childDefinition.getChildModel(frameId2, frameId, framesFrom2);
		}
		Model childIdModel = (Model) ActorDefinition.modelCache.get(id);
		if (childIdModel == null) {
			boolean cached = false;
			for (int model = 0; model < modelIds.length; model++) {
				if (Model.isCached(modelIds[model])) {
					cached = true;
				}
			}
			if (!cached) {
				return null;
			}
			Model[] childModels = new Model[modelIds.length];
			for (int model = 0; model < modelIds.length; model++) {
				childModels[model] = Model.getModel(modelIds[model]);
			}
			if (childModels.length == 1) {
				childIdModel = childModels[0];
			} else {
				childIdModel = new Model(childModels.length, childModels);
			}
			if (modifiedModelColors != null) {
				for (int color = 0; color < modifiedModelColors.length; color++) {
					childIdModel.recolor(modifiedModelColors[color], originalModelColors[color]);
				}
			}
			childIdModel.createBones();
			childIdModel.applyLighting(64 + brightness, 850 + contrast, -30, -50, -30, true);
			ActorDefinition.modelCache.put(childIdModel, id);
		}
		Model childModel = Model.aModel1614;
		childModel.method412(7, childIdModel, Animation.exists(frameId) & Animation.exists(frameId2));
		if (frameId != -1 && frameId2 != -1) {
			childModel.mixAnimationFrames(-20491, framesFrom2, frameId2, frameId);
		} else if (frameId != -1) {
			childModel.applyTransform(frameId);
		}
		if (sizeXZ != 128 || sizeY != 128) {
			childModel.scaleT(sizeXZ, sizeXZ, sizeY);
		}
		childModel.calculateDiagonals(false);
		childModel.triangleSkin = null;
		childModel.vectorSkin = null;
		if (boundaryDimension == 1) {
			childModel.oneSquareModel = true;
		}
		return childModel;
	}

	private final void loadDefinition(boolean bool, Buffer buffer) {
		while (true) {
			int attributeId = buffer.getUnsignedByte();
			if (attributeId == 0) {
				break;
			}
			if (attributeId == 1) {
				int modelCount = buffer.getUnsignedByte();
				modelIds = new int[modelCount];
				for (int model = 0; model < modelCount; model++) {
					modelIds[model] = buffer.getUnsignedLEShort();
				}
			} else if (attributeId == 2) {
				name = buffer.getString();
			} else if (attributeId == 3) {
				description = buffer.getStringAsBytes();
			} else if (attributeId == 12) {
				boundaryDimension = buffer.get();
			} else if (attributeId == 13) {
				standAnimationId = buffer.getUnsignedLEShort();
			} else if (attributeId == 14) {
				walkAnimationId = buffer.getUnsignedLEShort();
			} else if (attributeId == 17) {
				walkAnimationId = buffer.getUnsignedLEShort();
				turnAroundAnimationId = buffer.getUnsignedLEShort();
				turnRightAnimationId = buffer.getUnsignedLEShort();
				turnLeftAnimationId = buffer.getUnsignedLEShort();
			} else if (attributeId >= 30 && attributeId < 40) {
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
				for (int color = 0; color < modelColorCount; color++) {
					modifiedModelColors[color] = buffer.getUnsignedLEShort();
					originalModelColors[color] = buffer.getUnsignedLEShort();
				}
			} else if (attributeId == 60) {
				int additionalModelCount = buffer.getUnsignedByte();
				headModelIndexes = new int[additionalModelCount];
				for (int model = 0; model < additionalModelCount; model++) {
					headModelIndexes[model] = buffer.getUnsignedLEShort();
				}
			} else if (attributeId == 90) {
				buffer.getUnsignedLEShort(); // dummy
			} else if (attributeId == 91) {
				buffer.getUnsignedLEShort(); // dummy
			} else if (attributeId == 92) {
				buffer.getUnsignedLEShort(); // dummy
			} else if (attributeId == 93) {
				minimapVisible = false;
			} else if (attributeId == 95) {
				combatLevel = buffer.getUnsignedLEShort();
			} else if (attributeId == 97) {
				sizeXZ = buffer.getUnsignedLEShort();
			} else if (attributeId == 98) {
				sizeY = buffer.getUnsignedLEShort();
			} else if (attributeId == 99) {
				visible = true;
			} else if (attributeId == 100) {
				brightness = buffer.get();
			} else if (attributeId == 101) {
				contrast = buffer.get() * 5;
			} else if (attributeId == 102) {
				headIcon = buffer.getUnsignedLEShort();
			} else if (attributeId == 103) {
				degreesToTurn = buffer.getUnsignedLEShort();
			} else if (attributeId == 106) {
				varBitId = buffer.getUnsignedLEShort();
				if (varBitId == 65535) {
					varBitId = -1;
				}
				settingId = buffer.getUnsignedLEShort();
				if (settingId == 65535) {
					settingId = -1;
				}
				int childrenCount = buffer.getUnsignedByte();
				childrenIndexes = new int[childrenCount + 1];
				for (int child = 0; child <= childrenCount; child++) {
					childrenIndexes[child] = buffer.getUnsignedLEShort();
					if (childrenIndexes[child] == 65535) {
						childrenIndexes[child] = -1;
					}
				}
			} else if (attributeId == 107) {
				clickable = false;
			}
		}
	}
}
