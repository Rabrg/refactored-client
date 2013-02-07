package com.runescape.cache.def;

import com.runescape.cache.Archive;
import com.runescape.graphic.ImageRGB;
import com.runescape.graphic.Model;
import com.runescape.graphic.Rasterizer;
import com.runescape.graphic.Rasterizer3D;
import com.runescape.net.Buffer;
import com.runescape.node.Cache;

public class ItemDefinition {

	private byte femaleEquipOffset;
	public int value;
	private int[] originalModelColors;
	public int index = -1;
	public static Cache rgbImageCache = new Cache(100);
	public static Cache modelCache = new Cache(50);
	private int[] modifiedModelColors;
	public boolean membersOnly;
	public int femaleEmblem;
	public int noteTemplateIndex;
	private int femaleEquipSecondaryModel;
	private int maleEquipPrimaryModel;
	public int maleDialogueHat;
	private int modelSizeX;
	public String[] groundActions;
	private int modelOffset1;
	public String name;
	private static ItemDefinition[] cache;
	public int femaleDialogueHat;
	private int modelIndex;
	private int maleDialogue;
	public boolean stackable;
	public byte[] description;
	public int noteIndex;
	private static int cacheIndex;
	public int modelZoom;
	public static boolean playerIsMember = true;
	private static Buffer buffer;
	private int shadowModifier;
	public int maleEmblem;
	private int maleEquipSecondaryModel;
	public String[] inventoryActions;
	public int modelRotation1;
	private int modelSizeZ;
	private int modelSizeY;
	public int[] stackableIds;
	private int sine;
	private static int[] bufferOffsets;
	private int lightModifier;
	public int femaleDialogue;
	public int modelRotation2;
	private int femaleEquipPrimaryModel;
	public int[] stackableAmounts;
	public int teamIndex;
	public static int itemCount;
	private int diagonalRotation;
	private byte maleEquipOffset;

	public static final void reset() {
		ItemDefinition.modelCache = null;
		ItemDefinition.rgbImageCache = null;
		ItemDefinition.bufferOffsets = null;
		ItemDefinition.cache = null;
		ItemDefinition.buffer = null;
	}

	public final boolean isDialogueCached(int gender) {
		int modelIndex = gender == 1 ? femaleDialogue : maleDialogue;
		int hatModelIndex = gender == 1 ? femaleDialogueHat : maleDialogueHat;
		if (modelIndex == -1) {
			return true;
		}
		boolean isCached = true;
		if (!Model.isCached(modelIndex)) {
			isCached = false;
		}
		if (hatModelIndex != -1 && !Model.isCached(hatModelIndex)) {
			isCached = false;
		}
		return isCached;
	}

	public static final void load(Archive archive) {
		ItemDefinition.buffer = new Buffer(archive.getFile("obj.dat"));
		Buffer buffer = new Buffer(archive.getFile("obj.idx"));
		ItemDefinition.itemCount = buffer.getUnsignedLEShort();
		ItemDefinition.bufferOffsets = new int[ItemDefinition.itemCount];
		int offset = 2;
		for (int item = 0; item < ItemDefinition.itemCount; item++) {
			ItemDefinition.bufferOffsets[item] = offset;
			offset += buffer.getUnsignedLEShort();
		}
		ItemDefinition.cache = new ItemDefinition[10];
		for (int itemDefinition = 0; itemDefinition < 10; itemDefinition++) {
			ItemDefinition.cache[itemDefinition] = new ItemDefinition();
		}
	}

	public final Model getDialogueModel(int gender) {
		int modelIndex = gender == 1 ? femaleDialogue : maleDialogue;
		int hatModelIndex = gender == 1 ? femaleDialogueHat : maleDialogueHat;
		if (modelIndex == -1) {
			return null;
		}
		Model model = Model.getModel(modelIndex);
		if (hatModelIndex != -1) {
			Model hatModel = Model.getModel(hatModelIndex);
			Model[] models = { model, hatModel };
			model = new Model(2, models);
		}
		if (originalModelColors != null) {
			model.recolor(originalModelColors, modifiedModelColors);
		}
		return model;
	}

	public final boolean isEquipModelCached(int gender) {
		int equipPrimaryModel = maleEquipPrimaryModel;
		int equipSecondaryModel = maleEquipSecondaryModel;
		int emblem = maleEmblem;
		if (gender == 1) {
			equipPrimaryModel = femaleEquipPrimaryModel;
			equipSecondaryModel = femaleEquipSecondaryModel;
			emblem = femaleEmblem;
		}
		if (equipPrimaryModel == -1) {
			return true;
		}
		boolean isCached = true;
		if (!Model.isCached(equipPrimaryModel)) {
			isCached = false;
		}
		if (equipSecondaryModel != -1 && !Model.isCached(equipSecondaryModel)) {
			isCached = false;
		}
		if (emblem != -1 && !Model.isCached(emblem)) {
			isCached = false;
		}
		return isCached;
	}

	public final Model getEquipModel(int gender) {
		int equipPrimaryModel = maleEquipPrimaryModel;
		int equipSecondaryModel = maleEquipSecondaryModel;
		int emblem = maleEmblem;
		if (gender == 1) {
			equipPrimaryModel = femaleEquipPrimaryModel;
			equipSecondaryModel = femaleEquipSecondaryModel;
			emblem = femaleEmblem;
		}
		if (equipPrimaryModel == -1) {
			return null;
		}
		Model modelEquip1 = Model.getModel(equipPrimaryModel);
		if (equipSecondaryModel != -1) {
			if (emblem != -1) {
				Model modelEquip2 = Model.getModel(equipSecondaryModel);
				Model modelEmblem = Model.getModel(emblem);
				Model[] equipModelsWithEmblem = { modelEquip1, modelEquip2, modelEmblem };
				modelEquip1 = new Model(3, equipModelsWithEmblem);
			} else {
				Model modelEquip2 = Model.getModel(equipSecondaryModel);
				Model[] equipModelsWithoutEmblem = { modelEquip1, modelEquip2 };
				modelEquip1 = new Model(2, equipModelsWithoutEmblem);
			}
		}
		if (gender == 0 && maleEquipOffset != 0) {
			modelEquip1.translate(0, maleEquipOffset, 0);
		}
		if (gender == 1 && femaleEquipOffset != 0) {
			modelEquip1.translate(0, femaleEquipOffset, 0);
		}
		if (originalModelColors != null) {
			for (int color = 0; color < originalModelColors.length; color++) {
				modelEquip1.recolor(originalModelColors[color], modifiedModelColors[color]);
			}
		}
		return modelEquip1;
	}

	public final void setDefaultValues() {
		modelIndex = 0;
		name = null;
		description = null;
		originalModelColors = null;
		modifiedModelColors = null;
		modelZoom = 2000;
		modelRotation1 = 0;
		modelRotation2 = 0;
		diagonalRotation = 0;
		modelOffset1 = 0;
		sine = 0;
		stackable = false;
		value = 1;
		membersOnly = false;
		groundActions = null;
		inventoryActions = null;
		maleEquipPrimaryModel = -1;
		maleEquipSecondaryModel = -1;
		maleEquipOffset = (byte) 0;
		femaleEquipPrimaryModel = -1;
		femaleEquipSecondaryModel = -1;
		femaleEquipOffset = (byte) 0;
		maleEmblem = -1;
		femaleEmblem = -1;
		maleDialogue = -1;
		maleDialogueHat = -1;
		femaleDialogue = -1;
		femaleDialogueHat = -1;
		stackableIds = null;
		stackableAmounts = null;
		noteIndex = -1;
		noteTemplateIndex = -1;
		modelSizeX = 128;
		modelSizeY = 128;
		modelSizeZ = 128;
		lightModifier = 0;
		shadowModifier = 0;
		teamIndex = 0;
	}

	public static final ItemDefinition getDefinition(int itemId) {
		for (int i = 0; i < 10; i++) {
			if (ItemDefinition.cache[i].index == itemId) {
				return ItemDefinition.cache[i];
			}
		}
		ItemDefinition.cacheIndex = (ItemDefinition.cacheIndex + 1) % 10;
		ItemDefinition itemDefinition = ItemDefinition.cache[ItemDefinition.cacheIndex];
		ItemDefinition.buffer.offset = ItemDefinition.bufferOffsets[itemId];
		itemDefinition.index = itemId;
		itemDefinition.setDefaultValues();
		itemDefinition.loadDefinition(ItemDefinition.buffer);
		if (itemDefinition.noteTemplateIndex != -1) {
			itemDefinition.toNote();
		}
		if (!ItemDefinition.playerIsMember && itemDefinition.membersOnly) {
			itemDefinition.name = "Members Object";
			itemDefinition.description = "Login to a members' server to use this object.".getBytes();
			itemDefinition.groundActions = null;
			itemDefinition.inventoryActions = null;
			itemDefinition.teamIndex = 0;
		}
		return itemDefinition;
	}

	public void toNote() {
		ItemDefinition noteTemplateDefinition = ItemDefinition.getDefinition(noteTemplateIndex);
		modelIndex = noteTemplateDefinition.modelIndex;
		modelZoom = noteTemplateDefinition.modelZoom;
		modelRotation1 = noteTemplateDefinition.modelRotation1;
		modelRotation2 = noteTemplateDefinition.modelRotation2;
		diagonalRotation = noteTemplateDefinition.diagonalRotation;
		modelOffset1 = noteTemplateDefinition.modelOffset1;
		sine = noteTemplateDefinition.sine;
		originalModelColors = noteTemplateDefinition.originalModelColors;
		modifiedModelColors = noteTemplateDefinition.modifiedModelColors;
		ItemDefinition noteDefinition = ItemDefinition.getDefinition(noteIndex);
		name = noteDefinition.name;
		membersOnly = noteDefinition.membersOnly;
		value = noteDefinition.value;
		String plural = "a";
		char firstCharacter = noteDefinition.name.charAt(0);
		if (firstCharacter == 'A' || firstCharacter == 'E' || firstCharacter == 'I' || firstCharacter == 'O'
				|| firstCharacter == 'U') {
			plural = "an";
		}
		description = ("Swap this note at any bank for " + plural + " " + noteDefinition.name + ".").getBytes();
		stackable = true;
	}

	public static final ImageRGB getSprite(int itemId, int itemAmount, int type) {
		if (type == 0) {
			ImageRGB cachedSprite = (ImageRGB) ItemDefinition.rgbImageCache.get(itemId);
			if (cachedSprite != null && cachedSprite.maxHeight != itemAmount && cachedSprite.maxHeight != -1) {
				cachedSprite.remove();
				cachedSprite = null;
			}
			if (cachedSprite != null) {
				return cachedSprite;
			}
		}
		ItemDefinition itemDefinition = ItemDefinition.getDefinition(itemId);
		if (itemDefinition.stackableIds == null) {
			itemAmount = -1;
		}
		if (itemAmount > 1) {
			int stackedId = -1;
			for (int amount = 0; amount < 10; amount++) {
				if (itemAmount >= itemDefinition.stackableAmounts[amount]
						&& itemDefinition.stackableAmounts[amount] != 0) {
					stackedId = itemDefinition.stackableIds[amount];
				}
			}
			if (stackedId != -1) {
				itemDefinition = ItemDefinition.getDefinition(stackedId);
			}
		}
		Model model = itemDefinition.getAmountModel(1);
		if (model == null) {
			return null;
		}
		ImageRGB noteSprite = null;
		if (itemDefinition.noteTemplateIndex != -1) {
			noteSprite = ItemDefinition.getSprite(itemDefinition.noteIndex, 10, -1);
			if (noteSprite == null) {
				return null;
			}
		}
		ImageRGB itemSprite = new ImageRGB(32, 32);
		int rasterizerCenterX = Rasterizer3D.centerX;
		int rasterizerCenterY = Rasterizer3D.centerY;
		int[] rasterizerLineOffsets = Rasterizer3D.lineOffsets;
		int[] rasterizerPixels = Rasterizer.pixels;
		int rasterizerWidth = Rasterizer.width;
		int rasterizerHeight = Rasterizer.height;
		int rasterizerTopX = Rasterizer.topX;
		int rasterizerBottomX = Rasterizer.bottomX;
		int rasterizerTopY = Rasterizer.topY;
		int rasterizerBottomY = Rasterizer.bottomY;
		Rasterizer3D.textured = false;
		Rasterizer.createRasterizer(itemSprite.pixels, 32, 32);
		Rasterizer.drawFilledRectangle(0, 0, 32, 32, 0);
		Rasterizer3D.setDefaultBoundaries();
		int modelZoom = itemDefinition.modelZoom;
		if (type == -1) {
			modelZoom *= 1.5;
		}
		if (type > 0) {
			modelZoom *= 1.04;
		}
		int sine = Rasterizer3D.SINE[itemDefinition.modelRotation1] * modelZoom >> 16;
		int cosine = Rasterizer3D.COSINE[itemDefinition.modelRotation1] * modelZoom >> 16;
		model.method430(0, itemDefinition.modelRotation2, itemDefinition.diagonalRotation,
				itemDefinition.modelRotation1, itemDefinition.modelOffset1, sine + model.modelHeight / 2
						+ itemDefinition.sine, cosine + itemDefinition.sine);
		for (int pixel = 31; pixel >= 0; pixel--) {
			for (cosine = 31; cosine >= 0; cosine--) {
				if (itemSprite.pixels[pixel + cosine * 32] == 0) {
					if (pixel > 0 && itemSprite.pixels[pixel - 1 + cosine * 32] > 1) {
						itemSprite.pixels[pixel + cosine * 32] = 1;
					} else if (cosine > 0 && itemSprite.pixels[pixel + (cosine - 1) * 32] > 1) {
						itemSprite.pixels[pixel + cosine * 32] = 1;
					} else if (pixel < 31 && itemSprite.pixels[pixel + 1 + cosine * 32] > 1) {
						itemSprite.pixels[pixel + cosine * 32] = 1;
					} else if (cosine < 31 && itemSprite.pixels[pixel + (cosine + 1) * 32] > 1) {
						itemSprite.pixels[pixel + cosine * 32] = 1;
					}
				}
			}
		}
		if (type > 0) {
			for (int pixel = 31; pixel >= 0; pixel--) {
				for (cosine = 31; cosine >= 0; cosine--) {
					if (itemSprite.pixels[pixel + cosine * 32] == 0) {
						if (pixel > 0 && itemSprite.pixels[pixel - 1 + cosine * 32] == 1) {
							itemSprite.pixels[pixel + cosine * 32] = type;
						} else if (cosine > 0 && itemSprite.pixels[pixel + (cosine - 1) * 32] == 1) {
							itemSprite.pixels[pixel + cosine * 32] = type;
						} else if (pixel < 31 && itemSprite.pixels[pixel + 1 + cosine * 32] == 1) {
							itemSprite.pixels[pixel + cosine * 32] = type;
						} else if (cosine < 31 && itemSprite.pixels[pixel + (cosine + 1) * 32] == 1) {
							itemSprite.pixels[pixel + cosine * 32] = type;
						}
					}
				}
			}
		} else if (type == 0) {
			for (int pixel = 31; pixel >= 0; pixel--) {
				for (cosine = 31; cosine >= 0; cosine--) {
					if (itemSprite.pixels[pixel + cosine * 32] == 0 && pixel > 0 && cosine > 0
							&& itemSprite.pixels[pixel - 1 + (cosine - 1) * 32] > 0) {
						itemSprite.pixels[pixel + cosine * 32] = 3153952;
					}
				}
			}
		}
		if (itemDefinition.noteTemplateIndex != -1) {
			int maxWidth = noteSprite.maxWidth;
			int maxHeight = noteSprite.maxHeight;
			noteSprite.maxWidth = 32;
			noteSprite.maxHeight = 32;
			noteSprite.drawImage(0, 0);
			noteSprite.maxWidth = maxWidth;
			noteSprite.maxHeight = maxHeight;
		}
		if (type == 0) {
			ItemDefinition.rgbImageCache.put(itemSprite, itemId);
		}
		Rasterizer.createRasterizer(rasterizerPixels, rasterizerWidth, rasterizerHeight);
		Rasterizer.setCoordinates(rasterizerTopX, rasterizerTopY, rasterizerBottomX, rasterizerBottomY);
		Rasterizer3D.centerX = rasterizerCenterX;
		Rasterizer3D.centerY = rasterizerCenterY;
		Rasterizer3D.lineOffsets = rasterizerLineOffsets;
		Rasterizer3D.textured = true;
		if (itemDefinition.stackable) {
			itemSprite.maxWidth = 33;
		} else {
			itemSprite.maxWidth = 32;
		}
		itemSprite.maxHeight = itemAmount;
		return itemSprite;
	}

	public final Model getAmountModel(int amount) {
		if (stackableIds != null && amount > 1) {
			int stackedItemId = -1;
			for (int index = 0; index < 10; index++) {
				if (amount >= stackableAmounts[index] && stackableAmounts[index] != 0) {
					stackedItemId = stackableIds[index];
				}
			}
			if (stackedItemId != -1) {
				return ItemDefinition.getDefinition(stackedItemId).getAmountModel(1);
			}
		}
		Model stackedModel = (Model) ItemDefinition.modelCache.get(index);
		if (stackedModel != null) {
			return stackedModel;
		}
		stackedModel = Model.getModel(modelIndex);
		if (stackedModel == null) {
			return null;
		}
		if (modelSizeX != 128 || modelSizeY != 128 || modelSizeZ != 128) {
			stackedModel.scaleT(modelSizeX, modelSizeZ, modelSizeY);
		}
		if (originalModelColors != null) {
			for (int modelColor = 0; modelColor < originalModelColors.length; modelColor++) {
				stackedModel.recolor(originalModelColors[modelColor], modifiedModelColors[modelColor]);
			}
		}
		stackedModel.applyLighting(64 + lightModifier, 768 + shadowModifier, -50, -10, -50, true);
		stackedModel.oneSquareModel = true;
		ItemDefinition.modelCache.put(stackedModel, index);
		return stackedModel;
	}

	public final Model getInventoryModel(int amount) {
		if (stackableIds != null && amount > 1) {
			int amountItemId = -1;
			for (int index = 0; index < 10; index++) {
				if (amount >= stackableAmounts[index] && stackableAmounts[index] != 0) {
					amountItemId = stackableIds[index];
				}
			}
			if (amountItemId != -1) {
				return ItemDefinition.getDefinition(amountItemId).getInventoryModel(1);
			}
		}
		Model inventoryModel = Model.getModel(modelIndex);
		if (inventoryModel == null) {
			return null;
		}
		if (originalModelColors != null) {
			for (int color = 0; color < originalModelColors.length; color++) {
				inventoryModel.recolor(originalModelColors[color], modifiedModelColors[color]);
			}
		}
		return inventoryModel;
	}

	public final void loadDefinition(Buffer buffer) {
		while (true) {
			int attributeId = buffer.getUnsignedByte();
			if (attributeId == 0) {
				break;
			}
			if (attributeId == 1) {
				modelIndex = buffer.getUnsignedLEShort();
			} else if (attributeId == 2) {
				name = buffer.getString();
			} else if (attributeId == 3) {
				description = buffer.getStringAsBytes();
			} else if (attributeId == 4) {
				modelZoom = buffer.getUnsignedLEShort();
			} else if (attributeId == 5) {
				modelRotation1 = buffer.getUnsignedLEShort();
			} else if (attributeId == 6) {
				modelRotation2 = buffer.getUnsignedLEShort();
			} else if (attributeId == 7) {
				modelOffset1 = buffer.getUnsignedLEShort();
				if (modelOffset1 > 32767) {
					modelOffset1 -= 65536;
				}
			} else if (attributeId == 8) {
				sine = buffer.getUnsignedLEShort();
				if (sine > 32767) {
					sine -= 65536;
				}
			} else if (attributeId == 10) {
				buffer.getUnsignedLEShort(); // dummy
			} else if (attributeId == 11) {
				stackable = true;
			} else if (attributeId == 12) {
				value = buffer.getInt();
			} else if (attributeId == 16) {
				membersOnly = true;
			} else if (attributeId == 23) {
				maleEquipPrimaryModel = buffer.getUnsignedLEShort();
				maleEquipOffset = buffer.get();
			} else if (attributeId == 24) {
				maleEquipSecondaryModel = buffer.getUnsignedLEShort();
			} else if (attributeId == 25) {
				femaleEquipPrimaryModel = buffer.getUnsignedLEShort();
				femaleEquipOffset = buffer.get();
			} else if (attributeId == 26) {
				femaleEquipSecondaryModel = buffer.getUnsignedLEShort();
			} else if (attributeId >= 30 && attributeId < 35) {
				if (groundActions == null) {
					groundActions = new String[5];
				}
				groundActions[attributeId - 30] = buffer.getString();
				if (groundActions[attributeId - 30].equalsIgnoreCase("hidden")) {
					groundActions[attributeId - 30] = null;
				}
			} else if (attributeId >= 35 && attributeId < 40) {
				if (inventoryActions == null) {
					inventoryActions = new String[5];
				}
				inventoryActions[attributeId - 35] = buffer.getString();
			} else if (attributeId == 40) {
				int modelColorCount = buffer.getUnsignedByte();
				originalModelColors = new int[modelColorCount];
				modifiedModelColors = new int[modelColorCount];
				for (int modelColor = 0; modelColor < modelColorCount; modelColor++) {
					originalModelColors[modelColor] = buffer.getUnsignedLEShort();
					modifiedModelColors[modelColor] = buffer.getUnsignedLEShort();
				}
			} else if (attributeId == 78) {
				maleEmblem = buffer.getUnsignedLEShort();
			} else if (attributeId == 79) {
				femaleEmblem = buffer.getUnsignedLEShort();
			} else if (attributeId == 90) {
				maleDialogue = buffer.getUnsignedLEShort();
			} else if (attributeId == 91) {
				femaleDialogue = buffer.getUnsignedLEShort();
			} else if (attributeId == 92) {
				maleDialogueHat = buffer.getUnsignedLEShort();
			} else if (attributeId == 93) {
				femaleDialogueHat = buffer.getUnsignedLEShort();
			} else if (attributeId == 95) {
				diagonalRotation = buffer.getUnsignedLEShort();
			} else if (attributeId == 97) {
				noteIndex = buffer.getUnsignedLEShort();
			} else if (attributeId == 98) {
				noteTemplateIndex = buffer.getUnsignedLEShort();
			} else if (attributeId >= 100 && attributeId < 110) {
				if (stackableIds == null) {
					stackableIds = new int[10];
					stackableAmounts = new int[10];
				}
				stackableIds[attributeId - 100] = buffer.getUnsignedLEShort();
				stackableAmounts[attributeId - 100] = buffer.getUnsignedLEShort();
			} else if (attributeId == 110) {
				modelSizeX = buffer.getUnsignedLEShort();
			} else if (attributeId == 111) {
				modelSizeY = buffer.getUnsignedLEShort();
			} else if (attributeId == 112) {
				modelSizeZ = buffer.getUnsignedLEShort();
			} else if (attributeId == 113) {
				lightModifier = buffer.get();
			} else if (attributeId == 114) {
				shadowModifier = buffer.get() * 5;
			} else if (attributeId == 115) {
				teamIndex = buffer.getUnsignedByte();
			}
		}
	}
}
