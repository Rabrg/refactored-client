package com.runescape.renderable;

import com.runescape.anim.Animation;
import com.runescape.anim.AnimationSequence;
import com.runescape.anim.SpotAnimation;
import com.runescape.cache.def.ActorDefinition;
import com.runescape.graphic.Model;

public class Npc extends Actor {
	public ActorDefinition npcDefinition;

	private final Model method400() {
		if (animation >= 0 && aniomationDelay == 0) {
			int frameId = AnimationSequence.animationSequences[animation].animationForFrame[anInt1547];
			int frameId2 = -1;
			if (anInt1537 >= 0 && anInt1537 != standAnimationId) {
				frameId2 = AnimationSequence.animationSequences[anInt1537].animationForFrame[anInt1538];
			}
			return npcDefinition.getChildModel(frameId2, frameId,
					AnimationSequence.animationSequences[animation].anIntArray55);
		}
		int i_3_ = -1;
		if (anInt1537 >= 0) {
			i_3_ = AnimationSequence.animationSequences[anInt1537].animationForFrame[anInt1538];
		}
		return npcDefinition.getChildModel(-1, i_3_, null);
	}

	@Override
	public final Model getRotatedModel() {
		if (npcDefinition == null) {
			return null;
		}
		Model model = method400();
		if (model == null) {
			return null;
		}
		modelHeight = model.modelHeight;
		if (spotAnimationId != -1 && currentAnimationFrame != -1) {
			SpotAnimation spotanimation = SpotAnimation.cache[spotAnimationId];
			Model model_4_ = spotanimation.getModel();
			if (model_4_ != null) {
				int animationId = spotanimation.animationSequences.animationForFrame[currentAnimationFrame];
				Model animationModel = new Model(true, Animation.exists(animationId), false, model_4_);
				animationModel.translate(0, -spotAnimationDelay, 0);
				animationModel.createBones();
				animationModel.applyTransform(animationId);
				animationModel.triangleSkin = null;
				animationModel.vectorSkin = null;
				if (spotanimation.resizeXY != 128 || spotanimation.resizeZ != 128) {
					animationModel.scaleT(spotanimation.resizeXY, spotanimation.resizeXY, spotanimation.resizeZ);
				}
				animationModel.applyLighting(64 + spotanimation.modelBrightness, 850 + spotanimation.modelShadow, -30,
						-50, -30, true);
				Model[] models = { model, animationModel };
				model = new Model(2, -819, true, models);
			}
		}
		if (npcDefinition.boundaryDimension == 1) {
			model.oneSquareModel = true;
		}
		return model;
	}

	@Override
	public final boolean isVisibile() {
		if (npcDefinition == null) {
			return false;
		}
		return true;
	}
}
