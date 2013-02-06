package com.runescape.cache.def;

import com.runescape.cache.Archive;
import com.runescape.graphic.Model;
import com.runescape.net.Buffer;

public class IdentityKit {

	public static int count;
	public static IdentityKit[] cache;
	public int partIndex = -1;
	public int[] modelIndexes;
	public int[] originalModelColors = new int[6];
	public int[] modifiedModelColors = new int[6];
	public int[] dialogueIndexes = { -1, -1, -1, -1, -1 };
	public boolean interfaceDisplayed = false;

	public static void load(Archive archive) {
		Buffer buffer = new Buffer(archive.getFile("idk.dat"));
		IdentityKit.count = buffer.getUnsignedLEShort();
		if (IdentityKit.cache == null) {
			IdentityKit.cache = new IdentityKit[IdentityKit.count];
		}
		for (int identityKit = 0; identityKit < IdentityKit.count; identityKit++) {
			if (IdentityKit.cache[identityKit] == null) {
				IdentityKit.cache[identityKit] = new IdentityKit();
			}
			IdentityKit.cache[identityKit].loadDefinition(buffer);
		}
	}

	public void loadDefinition(Buffer buffer) {
		while (true) {
			int attributeId = buffer.getUnsignedByte();
			if (attributeId == 0) {
				break;
			}
			if (attributeId == 1) {
				partIndex = buffer.getUnsignedByte();
			} else if (attributeId == 2) {
				int modelCount = buffer.getUnsignedByte();
				modelIndexes = new int[modelCount];
				for (int model = 0; model < modelCount; model++) {
					modelIndexes[model] = buffer.getUnsignedLEShort();
				}
			} else if (attributeId == 3) {
				interfaceDisplayed = true;
			} else if (attributeId >= 40 && attributeId < 50) {
				originalModelColors[attributeId - 40] = buffer.getUnsignedLEShort();
			} else if (attributeId >= 50 && attributeId < 60) {
				modifiedModelColors[attributeId - 50] = buffer.getUnsignedLEShort();
			} else if (attributeId >= 60 && attributeId < 70) {
				dialogueIndexes[attributeId - 60] = buffer.getUnsignedLEShort();
			} else {
				System.out.println("Error unrecognised config code: " + attributeId);
			}
		}
	}

	public boolean isBodyModelCached() {
		if (modelIndexes == null) {
			return true;
		}
		boolean isCached = true;
		for (int model = 0; model < modelIndexes.length; model++) {
			if (!Model.isCached(modelIndexes[model])) {
				isCached = false;
			}
		}
		return isCached;
	}

	public Model getBodyModel() {
		if (modelIndexes == null) {
			return null;
		}
		Model[] models = new Model[modelIndexes.length];
		for (int model = 0; model < modelIndexes.length; model++) {
			models[model] = Model.getModel(modelIndexes[model]);
		}
		Model model;
		if (models.length == 1) {
			model = models[0];
		} else {
			model = new Model(models.length, models);
		}
		for (int modelColor = 0; modelColor < 6; modelColor++) {
			if (originalModelColors[modelColor] == 0) {
				break;
			}
			model.recolor(originalModelColors[modelColor], modifiedModelColors[modelColor]);
		}
		return model;
	}

	public boolean isHeadModelCached() {
		boolean isCached = true;
		for (int model = 0; model < 5; model++) {
			if (dialogueIndexes[model] != -1 && !Model.isCached(dialogueIndexes[model])) {
				isCached = false;
			}
		}
		return isCached;
	}

	public Model getHeadModel() {
		Model[] models = new Model[5];
		int modelAmount = 0;
		for (int model = 0; model < 5; model++) {
			if (dialogueIndexes[model] != -1) {
				models[modelAmount++] = Model.getModel(dialogueIndexes[model]);
			}
		}
		Model model = new Model(modelAmount, models);
		for (int modelColor = 0; modelColor < 6; modelColor++) {
			if (originalModelColors[modelColor] == 0) {
				break;
			}
			model.recolor(originalModelColors[modelColor], modifiedModelColors[modelColor]);
		}
		return model;
	}
}
