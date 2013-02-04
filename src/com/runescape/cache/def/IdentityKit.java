package com.runescape.cache.def;

import com.runescape.cache.Archive;
import com.runescape.graphic.Model;
import com.runescape.net.Buffer;

public class IdentityKit {

	public static int identityKitCount;
	public static IdentityKit[] identityKitCache;
	public int partId = -1;
	public int[] bodyModelIds;
	public int[] originalModelColors = new int[6];
	public int[] modifiedModelColors = new int[6];
	public int[] headModels = { -1, -1, -1, -1, -1 };
	public boolean interfaceDisplayed = false;

	public static void load(Archive archive) {
		Buffer buffer = new Buffer(archive.getFile("idk.dat"));
		IdentityKit.identityKitCount = buffer.getUnsignedLEShort();
		if (IdentityKit.identityKitCache == null) {
			IdentityKit.identityKitCache = new IdentityKit[IdentityKit.identityKitCount];
		}
		for (int identityKit = 0; identityKit < IdentityKit.identityKitCount; identityKit++) {
			if (IdentityKit.identityKitCache[identityKit] == null) {
				IdentityKit.identityKitCache[identityKit] = new IdentityKit();
			}
			IdentityKit.identityKitCache[identityKit].loadDefinition(buffer);
		}
	}

	public void loadDefinition(Buffer buffer) {
		while (true) {
			int attributeId = buffer.getUnsignedByte();
			if (attributeId == 0) {
				break;
			}
			if (attributeId == 1) {
				partId = buffer.getUnsignedByte();
			} else if (attributeId == 2) {
				int modelCount = buffer.getUnsignedByte();
				bodyModelIds = new int[modelCount];
				for (int model = 0; model < modelCount; model++) {
					bodyModelIds[model] = buffer.getUnsignedLEShort();
				}
			} else if (attributeId == 3) {
				interfaceDisplayed = true;
			} else if (attributeId >= 40 && attributeId < 50) {
				originalModelColors[attributeId - 40] = buffer.getUnsignedLEShort();
			} else if (attributeId >= 50 && attributeId < 60) {
				modifiedModelColors[attributeId - 50] = buffer.getUnsignedLEShort();
			} else if (attributeId >= 60 && attributeId < 70) {
				headModels[attributeId - 60] = buffer.getUnsignedLEShort();
			} else {
				System.out.println("Error unrecognised config code: " + attributeId);
			}
		}
	}

	public boolean isBodyModelCached() {
		if (bodyModelIds == null) {
			return true;
		}
		boolean isCached = true;
		for (int model = 0; model < bodyModelIds.length; model++) {
			if (!Model.isCached(bodyModelIds[model])) {
				isCached = false;
			}
		}
		return isCached;
	}

	public Model getBodyModel() {
		if (bodyModelIds == null) {
			return null;
		}
		Model[] models = new Model[bodyModelIds.length];
		for (int model = 0; model < bodyModelIds.length; model++) {
			models[model] = Model.getModel(bodyModelIds[model]);
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
			if (headModels[model] != -1 && !Model.isCached(headModels[model])) {
				isCached = false;
			}
		}
		return isCached;
	}

	public Model getHeadModel() {
		Model[] models = new Model[5];
		int modelAmount = 0;
		for (int model = 0; model < 5; model++) {
			if (headModels[model] != -1) {
				models[modelAmount++] = Model.getModel(headModels[model]);
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
