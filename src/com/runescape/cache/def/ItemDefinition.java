package com.runescape.cache.def;

import com.runescape.cache.Archive;
import com.runescape.graphic.ImageRGB;
import com.runescape.graphic.Model;
import com.runescape.graphic.Rasterizer;
import com.runescape.graphic.Rasterizer3D;
import com.runescape.net.Buffer;
import com.runescape.node.Cache;
import com.runescape.util.SignLink;

public class ItemDefinition {

	private byte femaleEquipOffset;
	public int value;
	private int[] originalModelColors;
	public int itemId = -1;
	public static Cache rgbImageCache = new Cache(100);
	public static Cache modelCache = new Cache(50);
	private int[] modifiedModelColors;
	public boolean membersOnly;
	public int femaleEmblem;
	public int noteTemplateId;
	private int femaleEqupModel2;
	private int maleEquipModel1;
	public int maleDialogueHat;
	private int modelSizeX;
	public String[] groundActions;
	private int modelOffset1;
	public String name;
	private static ItemDefinition[] itemDefinitions;
	public int femaleDialogueHat;
	private int inventoryModelId;
	private int maleDialogue;
	public boolean stackable;
	public byte[] description;
	public int noteId;
	private static int cacheIndex;
	public int modelZoom;
	public static boolean playerIsMember = true;
	private static Buffer dataBuffer;
	private int shadowModifier;
	public int maleEmblem;
	private int maleEquipModel2;
	public String[] inventoryActions;
	public int modelRotation1;
	private int modelSizeZ;
	private int modelSizeY;
	public int[] stackableIds;
	private int modelOffset2;
	private static int[] streamIndices;
	private int lightModifier;
	public int femaleDialogue;
	public int modelRotation2;
	private int femaleEqupModel1;
	public int[] stackableAmounts;
	public int teamId;
	public static int itemCount;
	private int diagonalRotation;
	private byte maleEquipOffset;

	public static final void reset() {
		ItemDefinition.modelCache = null;
		ItemDefinition.rgbImageCache = null;
		ItemDefinition.streamIndices = null;
		ItemDefinition.itemDefinitions = null;
		ItemDefinition.dataBuffer = null;
	}

	public final boolean isDialogueCached(int type) {
		int dialogue = maleDialogue;
		int dialogueHat = maleDialogueHat;
		if (type == 1) {
			dialogue = femaleDialogue;
			dialogueHat = femaleDialogueHat;
		}
		if (dialogue == -1) {
			return true;
		}
		boolean isCached = true;
		if (!Model.isCached(dialogue)) {
			isCached = false;
		}
		if (dialogueHat != -1 && !Model.isCached(dialogueHat)) {
			isCached = false;
		}
		return isCached;
	}

	public static final void load(Archive archive) {
		ItemDefinition.dataBuffer = new Buffer(archive.getFile("obj.dat"));
		Buffer buffer = new Buffer(archive.getFile("obj.idx"));
		ItemDefinition.itemCount = buffer.getUnsignedLEShort();
		ItemDefinition.streamIndices = new int[ItemDefinition.itemCount];
		int offset = 2;
		for (int item = 0; item < ItemDefinition.itemCount; item++) {
			ItemDefinition.streamIndices[item] = offset;
			offset += buffer.getUnsignedLEShort();
		}
		ItemDefinition.itemDefinitions = new ItemDefinition[10];
		for (int itemDefinition = 0; itemDefinition < 10; itemDefinition++) {
			ItemDefinition.itemDefinitions[itemDefinition] = new ItemDefinition();
		}
	}

	public final Model getDialogueModel(int gender) {
		int dialogueModelId = maleDialogue;
		int dialogueHatModelId = maleDialogueHat;
		if (gender == 1) {
			dialogueModelId = femaleDialogue;
			dialogueHatModelId = femaleDialogueHat;
		}
		if (dialogueModelId == -1) {
			return null;
		}
		Model dialogueModel = Model.getModel(dialogueModelId);
		if (dialogueHatModelId != -1) {
			Model dialogueHatModel = Model.getModel(dialogueHatModelId);
			Model[] dialogueModels = { dialogueModel, dialogueHatModel };
			dialogueModel = new Model(2, dialogueModels);
		}
		if (originalModelColors != null) {
			for (int modelColor = 0; modelColor < originalModelColors.length; modelColor++) {
				dialogueModel.recolor(originalModelColors[modelColor], modifiedModelColors[modelColor]);
			}
		}
		return dialogueModel;
	}

	public final boolean isEquipModelCached(int i, int type) {
		int equipModel1 = maleEquipModel1;
		int equipModel2 = maleEquipModel2;
		int emblem = maleEmblem;
		if (type == 1) {
			equipModel1 = femaleEqupModel1;
			equipModel2 = femaleEqupModel2;
			emblem = femaleEmblem;
		}
		if (equipModel1 == -1) {
			return true;
		}
		boolean isCached = true;
		if (!Model.isCached(equipModel1)) {
			isCached = false;
		}
		if (equipModel2 != -1 && !Model.isCached(equipModel2)) {
			isCached = false;
		}
		if (emblem != -1 && !Model.isCached(emblem)) {
			isCached = false;
		}
		return isCached;
	}

	public final Model getEquipModel(int type) {
		int equipModel1 = maleEquipModel1;
		int equipModel2 = maleEquipModel2;
		int emblem = maleEmblem;
		if (type == 1) {
			equipModel1 = femaleEqupModel1;
			equipModel2 = femaleEqupModel2;
			emblem = femaleEmblem;
		}
		if (equipModel1 == -1) {
			return null;
		}
		Model modelEquip1 = Model.getModel(equipModel1);
		if (equipModel2 != -1) {
			if (emblem != -1) {
				Model modelEquip2 = Model.getModel(equipModel2);
				Model modelEmblem = Model.getModel(emblem);
				Model[] equipModelsWithEmblem = { modelEquip1, modelEquip2, modelEmblem };
				modelEquip1 = new Model(3, equipModelsWithEmblem);
			} else {
				Model modelEquip2 = Model.getModel(equipModel2);
				Model[] equipModelsWithoutEmblem = { modelEquip1, modelEquip2 };
				modelEquip1 = new Model(2, equipModelsWithoutEmblem);
			}
		}
		if (type == 0 && maleEquipOffset != 0) {
			modelEquip1.translate(0, maleEquipOffset, 0);
		}
		if (type == 1 && femaleEquipOffset != 0) {
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
		inventoryModelId = 0;
		name = null;
		description = null;
		originalModelColors = null;
		modifiedModelColors = null;
		modelZoom = 2000;
		modelRotation1 = 0;
		modelRotation2 = 0;
		diagonalRotation = 0;
		modelOffset1 = 0;
		modelOffset2 = 0;
		stackable = false;
		value = 1;
		membersOnly = false;
		groundActions = null;
		inventoryActions = null;
		maleEquipModel1 = -1;
		maleEquipModel2 = -1;
		maleEquipOffset = (byte) 0;
		femaleEqupModel1 = -1;
		femaleEqupModel2 = -1;
		femaleEquipOffset = (byte) 0;
		maleEmblem = -1;
		femaleEmblem = -1;
		maleDialogue = -1;
		maleDialogueHat = -1;
		femaleDialogue = -1;
		femaleDialogueHat = -1;
		stackableIds = null;
		stackableAmounts = null;
		noteId = -1;
		noteTemplateId = -1;
		modelSizeX = 128;
		modelSizeY = 128;
		modelSizeZ = 128;
		lightModifier = 0;
		shadowModifier = 0;
		teamId = 0;
	}

	public static final ItemDefinition getDefinition(int itemId) {
		for (int i = 0; i < 10; i++) {
			if (ItemDefinition.itemDefinitions[i].itemId == itemId) {
				return ItemDefinition.itemDefinitions[i];
			}
		}
		ItemDefinition.cacheIndex = (ItemDefinition.cacheIndex + 1) % 10;
		ItemDefinition itemDefinition = ItemDefinition.itemDefinitions[ItemDefinition.cacheIndex];
		ItemDefinition.dataBuffer.offset = ItemDefinition.streamIndices[itemId];
		itemDefinition.itemId = itemId;
		itemDefinition.setDefaultValues();
		itemDefinition.loadDefinition(ItemDefinition.dataBuffer);
		if (itemDefinition.noteTemplateId != -1) {
			itemDefinition.toNote();
		}
		if (!ItemDefinition.playerIsMember && itemDefinition.membersOnly) {
			itemDefinition.name = "Members Object";
			itemDefinition.description = "Login to a members' server to use this object.".getBytes();
			itemDefinition.groundActions = null;
			itemDefinition.inventoryActions = null;
			itemDefinition.teamId = 0;
		}
		return itemDefinition;
	}

	public void toNote() {
		ItemDefinition noteTemplateDefinition = ItemDefinition.getDefinition(noteTemplateId);
		inventoryModelId = noteTemplateDefinition.inventoryModelId;
		modelZoom = noteTemplateDefinition.modelZoom;
		modelRotation1 = noteTemplateDefinition.modelRotation1;
		modelRotation2 = noteTemplateDefinition.modelRotation2;
		diagonalRotation = noteTemplateDefinition.diagonalRotation;
		modelOffset1 = noteTemplateDefinition.modelOffset1;
		modelOffset2 = noteTemplateDefinition.modelOffset2;
		originalModelColors = noteTemplateDefinition.originalModelColors;
		modifiedModelColors = noteTemplateDefinition.modifiedModelColors;
		ItemDefinition noteDefinition = ItemDefinition.getDefinition(noteId);
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

	public static final ImageRGB getSprite(int itemId, int itemAmount, int i_25_) {
		if (i_25_ == 0) {
			ImageRGB imageRGB = (ImageRGB) ItemDefinition.rgbImageCache.get(itemId);
			if (imageRGB != null && imageRGB.maxHeight != itemAmount && imageRGB.maxHeight != -1) {
				imageRGB.remove();
				imageRGB = null;
			}
			if (imageRGB != null) {
				return imageRGB;
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
		ImageRGB imageRGB = null;
		if (itemDefinition.noteTemplateId != -1) {
			imageRGB = ItemDefinition.getSprite(itemDefinition.noteId, 10, -1);
			if (imageRGB == null) {
				return null;
			}
		}
		ImageRGB imagergb_29_ = new ImageRGB(32, 32);
		int i_30_ = Rasterizer3D.centerWidth;
		int i_31_ = Rasterizer3D.centerHeight;
		int[] is = Rasterizer3D.lineOffsets;
		int[] is_32_ = Rasterizer.pixels;
		int i_33_ = Rasterizer.width;
		int i_34_ = Rasterizer.height;
		int i_35_ = Rasterizer.topX;
		int i_36_ = Rasterizer.bottomX;
		int i_37_ = Rasterizer.topY;
		int i_38_ = Rasterizer.bottomY;
		Rasterizer3D.aBoolean1484 = false;
		Rasterizer.createRasterizer(imagergb_29_.pixels, 32, 32);
		Rasterizer.drawFilledRectangle(0, 0, 32, 32, 0);
		Rasterizer3D.method361();
		int i_39_ = itemDefinition.modelZoom;
		if (i_25_ == -1) {
			i_39_ *= 1.5;
		}
		if (i_25_ > 0) {
			i_39_ *= 1.04;
		}
		int i_40_ = Rasterizer3D.anIntArray1490[itemDefinition.modelRotation1] * i_39_ >> 16;
		int i_41_ = Rasterizer3D.anIntArray1491[itemDefinition.modelRotation1] * i_39_ >> 16;
		model.method430(0, itemDefinition.modelRotation2, itemDefinition.diagonalRotation,
				itemDefinition.modelRotation1, itemDefinition.modelOffset1, i_40_ + model.modelHeight / 2
						+ itemDefinition.modelOffset2, i_41_ + itemDefinition.modelOffset2);
		for (int i_42_ = 31; i_42_ >= 0; i_42_--) {
			for (i_41_ = 31; i_41_ >= 0; i_41_--) {
				if (imagergb_29_.pixels[i_42_ + i_41_ * 32] == 0) {
					if (i_42_ > 0 && imagergb_29_.pixels[i_42_ - 1 + i_41_ * 32] > 1) {
						imagergb_29_.pixels[i_42_ + i_41_ * 32] = 1;
					} else if (i_41_ > 0 && imagergb_29_.pixels[i_42_ + (i_41_ - 1) * 32] > 1) {
						imagergb_29_.pixels[i_42_ + i_41_ * 32] = 1;
					} else if (i_42_ < 31 && imagergb_29_.pixels[i_42_ + 1 + i_41_ * 32] > 1) {
						imagergb_29_.pixels[i_42_ + i_41_ * 32] = 1;
					} else if (i_41_ < 31 && imagergb_29_.pixels[i_42_ + (i_41_ + 1) * 32] > 1) {
						imagergb_29_.pixels[i_42_ + i_41_ * 32] = 1;
					}
				}
			}
		}
		if (i_25_ > 0) {
			for (int i_43_ = 31; i_43_ >= 0; i_43_--) {
				for (i_41_ = 31; i_41_ >= 0; i_41_--) {
					if (imagergb_29_.pixels[i_43_ + i_41_ * 32] == 0) {
						if (i_43_ > 0 && imagergb_29_.pixels[i_43_ - 1 + i_41_ * 32] == 1) {
							imagergb_29_.pixels[i_43_ + i_41_ * 32] = i_25_;
						} else if (i_41_ > 0 && imagergb_29_.pixels[i_43_ + (i_41_ - 1) * 32] == 1) {
							imagergb_29_.pixels[i_43_ + i_41_ * 32] = i_25_;
						} else if (i_43_ < 31 && imagergb_29_.pixels[i_43_ + 1 + i_41_ * 32] == 1) {
							imagergb_29_.pixels[i_43_ + i_41_ * 32] = i_25_;
						} else if (i_41_ < 31 && imagergb_29_.pixels[i_43_ + (i_41_ + 1) * 32] == 1) {
							imagergb_29_.pixels[i_43_ + i_41_ * 32] = i_25_;
						}
					}
				}
			}
		} else if (i_25_ == 0) {
			for (int i_44_ = 31; i_44_ >= 0; i_44_--) {
				for (i_41_ = 31; i_41_ >= 0; i_41_--) {
					if (imagergb_29_.pixels[i_44_ + i_41_ * 32] == 0 && i_44_ > 0 && i_41_ > 0
							&& imagergb_29_.pixels[i_44_ - 1 + (i_41_ - 1) * 32] > 0) {
						imagergb_29_.pixels[i_44_ + i_41_ * 32] = 3153952;
					}
				}
			}
		}
		if (itemDefinition.noteTemplateId != -1) {
			int i_45_ = imageRGB.maxWidth;
			int i_46_ = imageRGB.maxHeight;
			imageRGB.maxWidth = 32;
			imageRGB.maxHeight = 32;
			imageRGB.method345(0, 16083, 0);
			imageRGB.maxWidth = i_45_;
			imageRGB.maxHeight = i_46_;
		}
		if (i_25_ == 0) {
			ItemDefinition.rgbImageCache.put(imagergb_29_, itemId);
		}
		Rasterizer.createRasterizer(is_32_, i_33_, i_34_);
		Rasterizer.setCoordinates(i_35_, i_37_, i_36_, i_38_);
		Rasterizer3D.centerWidth = i_30_;
		Rasterizer3D.centerHeight = i_31_;
		Rasterizer3D.lineOffsets = is;
		Rasterizer3D.aBoolean1484 = true;
		if (itemDefinition.stackable) {
			imagergb_29_.maxWidth = 33;
		} else {
			imagergb_29_.maxWidth = 32;
		}
		imagergb_29_.maxHeight = itemAmount;
		return imagergb_29_;
	}

	public final Model getAmountModel(int i) {
		if (stackableIds != null && i > 1) {
			int i_48_ = -1;
			for (int i_49_ = 0; i_49_ < 10; i_49_++) {
				if (i >= stackableAmounts[i_49_] && stackableAmounts[i_49_] != 0) {
					i_48_ = stackableIds[i_49_];
				}
			}
			if (i_48_ != -1) {
				return ItemDefinition.getDefinition(i_48_).getAmountModel(1);
			}
		}
		Model model = (Model) ItemDefinition.modelCache.get(itemId);
		if (model != null) {
			return model;
		}
		model = Model.getModel(inventoryModelId);
		if (model == null) {
			return null;
		}
		if (modelSizeX != 128 || modelSizeY != 128 || modelSizeZ != 128) {
			model.scaleT(modelSizeX, modelSizeZ, modelSizeY);
		}
		if (originalModelColors != null) {
			for (int modelColor = 0; modelColor < originalModelColors.length; modelColor++) {
				model.recolor(originalModelColors[modelColor], modifiedModelColors[modelColor]);
			}
		}
		model.applyLighting(64 + lightModifier, 768 + shadowModifier, -50, -10, -50, true);
		model.aBoolean1652 = true;
		ItemDefinition.modelCache.put(model, itemId);
		return model;
	}

	public final Model getModel(int i, boolean bool) {
		try {
			if (stackableIds != null && i > 1) {
				int i_51_ = -1;
				for (int i_52_ = 0; i_52_ < 10; i_52_++) {
					if (i >= stackableAmounts[i_52_] && stackableAmounts[i_52_] != 0) {
						i_51_ = stackableIds[i_52_];
					}
				}
				if (i_51_ != -1) {
					return ItemDefinition.getDefinition(i_51_).getModel(1, true);
				}
			}
			Model model = Model.getModel(inventoryModelId);
			if (!bool) {
				throw new NullPointerException();
			}
			if (model == null) {
				return null;
			}
			if (originalModelColors != null) {
				for (int i_53_ = 0; i_53_ < originalModelColors.length; i_53_++) {
					model.recolor(originalModelColors[i_53_], modifiedModelColors[i_53_]);
				}
			}
			return model;
		} catch (RuntimeException runtimeexception) {
			SignLink.reportError("80813, " + i + ", " + bool + ", " + runtimeexception.toString());
			throw new RuntimeException();
		}
	}

	public final void loadDefinition(Buffer buffer) {
		while (true) {
			int attributeId = buffer.getUnsignedByte();
			if (attributeId == 0) {
				break;
			}
			if (attributeId == 1) {
				inventoryModelId = buffer.getUnsignedLEShort();
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
				modelOffset2 = buffer.getUnsignedLEShort();
				if (modelOffset2 > 32767) {
					modelOffset2 -= 65536;
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
				maleEquipModel1 = buffer.getUnsignedLEShort();
				maleEquipOffset = buffer.get();
			} else if (attributeId == 24) {
				maleEquipModel2 = buffer.getUnsignedLEShort();
			} else if (attributeId == 25) {
				femaleEqupModel1 = buffer.getUnsignedLEShort();
				femaleEquipOffset = buffer.get();
			} else if (attributeId == 26) {
				femaleEqupModel2 = buffer.getUnsignedLEShort();
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
				noteId = buffer.getUnsignedLEShort();
			} else if (attributeId == 98) {
				noteTemplateId = buffer.getUnsignedLEShort();
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
				teamId = buffer.getUnsignedByte();
			}
		}
	}
}
