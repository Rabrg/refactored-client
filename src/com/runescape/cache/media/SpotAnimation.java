package com.runescape.cache.media;

import com.runescape.cache.Archive;
import com.runescape.collection.Cache;
import com.runescape.media.renderable.Model;
import com.runescape.net.Buffer;

// actually is graphical effects
public class SpotAnimation {

	public static int spotAnimationCount;
	public static SpotAnimation[] cache;
	public int id;
	public int modelId;
	public int animationId = -1;
	public AnimationSequence sequences;
	public int[] originalModelColors = new int[6];
	public int[] modifiedModelColors = new int[6];
	public int resizeXY = 128;
	public int resizeZ = 128;
	public int rotation;
	public int modelLightFalloff;
	public int modelLightAmbient;
	public static Cache modelCache = new Cache(30);

	public static void load(Archive archive) {
		Buffer buffer = new Buffer(archive.getFile("spotanim.dat"));
		SpotAnimation.spotAnimationCount = buffer.getUnsignedLEShort();
		if (SpotAnimation.cache == null) {
			SpotAnimation.cache = new SpotAnimation[SpotAnimation.spotAnimationCount];
		}
		for (int spotAnimation = 0; spotAnimation < SpotAnimation.spotAnimationCount; spotAnimation++) {
			if (SpotAnimation.cache[spotAnimation] == null) {
				SpotAnimation.cache[spotAnimation] = new SpotAnimation();
			}
			SpotAnimation.cache[spotAnimation].id = spotAnimation;
			SpotAnimation.cache[spotAnimation].loadDefinition(buffer);
		}
	}

	public void loadDefinition(Buffer buffer) {
		while (true) {
			int attributeId = buffer.getUnsignedByte();
			if (attributeId == 0) {
				break;
			}
			if (attributeId == 1) {
				modelId = buffer.getUnsignedLEShort();
			} else if (attributeId == 2) {
				animationId = buffer.getUnsignedLEShort();
				if (AnimationSequence.cache != null) {
					sequences = AnimationSequence.cache[animationId];
				}
			} else if (attributeId == 4) {
				resizeXY = buffer.getUnsignedLEShort();
			} else if (attributeId == 5) {
				resizeZ = buffer.getUnsignedLEShort();
			} else if (attributeId == 6) {
				rotation = buffer.getUnsignedLEShort();
			} else if (attributeId == 7) {
				modelLightFalloff = buffer.getUnsignedByte();
			} else if (attributeId == 8) {
				modelLightAmbient = buffer.getUnsignedByte();
			} else if (attributeId >= 40 && attributeId < 50) {
				originalModelColors[attributeId - 40] = buffer.getUnsignedLEShort();
			} else if (attributeId >= 50 && attributeId < 60) {
				modifiedModelColors[attributeId - 50] = buffer.getUnsignedLEShort();
			} else {
				System.out.println("Error unrecognised spotanim config code: " + attributeId);
			}
		}
	}

	public Model getModel() {
		Model model = (Model) SpotAnimation.modelCache.get(id);
		if (model != null) {
			return model;
		}
		model = Model.getModel(modelId);
		if (model == null) {
			return null;
		}
		for (int nodelColor = 0; nodelColor < 6; nodelColor++) {
			if (originalModelColors[0] != 0) {
				model.recolor(originalModelColors[nodelColor], modifiedModelColors[nodelColor]);
			}
		}
		SpotAnimation.modelCache.put(model, id);
		return model;
	}
}
