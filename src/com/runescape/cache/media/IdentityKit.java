package com.runescape.cache.media;

import com.runescape.cache.Archive;
import com.runescape.media.renderable.Model;
import com.runescape.net.Buffer;

public class IdentityKit {

	public static int count;
	public static IdentityKit[] cache;
	public int partId = -1;
	public int[] modelId;
	public int[] originalModelColors = new int[6];
	public int[] modifiedModelColors = new int[6];
	public int[] headModelId = { -1, -1, -1, -1, -1 };
	public boolean widgetDisplayed = false;

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
				partId = buffer.getUnsignedByte();
			} else if (attributeId == 2) {
				int modelCount = buffer.getUnsignedByte();
				modelId = new int[modelCount];
				for (int model = 0; model < modelCount; model++) {
					modelId[model] = buffer.getUnsignedLEShort();
				}
			} else if (attributeId == 3) {
				widgetDisplayed = true;
			} else if (attributeId >= 40 && attributeId < 50) {
				originalModelColors[attributeId - 40] = buffer.getUnsignedLEShort();
			} else if (attributeId >= 50 && attributeId < 60) {
				modifiedModelColors[attributeId - 50] = buffer.getUnsignedLEShort();
			} else if (attributeId >= 60 && attributeId < 70) {
				headModelId[attributeId - 60] = buffer.getUnsignedLEShort();
			} else {
				System.out.println("Error unrecognised config code: " + attributeId);
			}
		}
	}

	public boolean isBodyModelCached() {
		if (modelId == null) {
			return true;
		}
		boolean isCached = true;
		for (int i = 0; i < modelId.length; i++) {
			if (!Model.isCached(modelId[i])) {
				isCached = false;
			}
		}
		return isCached;
	}

	public Model getBodyModel() {
		if (modelId == null) {
			return null;
		}
		Model[] models = new Model[modelId.length];
		for (int model = 0; model < modelId.length; model++) {
			models[model] = Model.getModel(modelId[model]);
		}
		Model model;
		if (models.length == 1) {
			model = models[0];
		} else {
			model = new Model(models.length, models);
		}
		for (int color = 0; color < 6; color++) {
			if (originalModelColors[color] == 0) {
				break;
			}
			model.recolor(originalModelColors[color], modifiedModelColors[color]);
		}
		return model;
	}

	public boolean isHeadModelCached() {
		boolean cached = true;
		for (int model = 0; model < 5; model++) {
			if (headModelId[model] != -1 && !Model.isCached(headModelId[model])) {
				cached = false;
			}
		}
		return cached;
	}

	public Model getHeadModel() {
		Model[] models = new Model[5];
		int count = 0;
		for (int model = 0; model < 5; model++) {
			if (headModelId[model] != -1) {
				models[count++] = Model.getModel(headModelId[model]);
			}
		}
		Model model = new Model(count, models);
		for (int color = 0; color < 6; color++) {
			if (originalModelColors[color] == 0) {
				break;
			}
			model.recolor(originalModelColors[color], modifiedModelColors[color]);
		}
		return model;
	}
}
