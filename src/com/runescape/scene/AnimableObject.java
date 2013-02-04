package com.runescape.scene;

import com.runescape.anim.Animation;
import com.runescape.anim.SpotAnimation;
import com.runescape.graphic.Model;
import com.runescape.renderable.Renderable;

public class AnimableObject extends Renderable {

	public int plane;
	public int x;
	public int y;
	public int worldZ;
	public int loopCycle;
	public boolean transformCompleted = false;
	private SpotAnimation spotAnimation;
	private int eclapsedFrames;
	private int duration;

	public AnimableObject(int plane, int loopCycle, int loopCycleOffset, int animationId, int worldZ, int y, int x) {
		this.spotAnimation = SpotAnimation.cache[animationId];
		this.plane = plane;
		this.x = x;
		this.y = y;
		this.worldZ = worldZ;
		this.loopCycle = loopCycle + loopCycleOffset;
		this.transformCompleted = false;
	}

	@Override
	public final Model getRotatedModel() {
		Model model = spotAnimation.getModel();
		if (model == null) {
			return null;
		}
		int frame = spotAnimation.animationSequences.animationForFrame[eclapsedFrames];
		Model animatedModel = new Model(true, Animation.exists(frame), false, model);
		if (!transformCompleted) {
			animatedModel.createBones();
			animatedModel.applyTransform(frame);
			animatedModel.triangleSkin = null;
			animatedModel.vectorSkin = null;
		}
		if (spotAnimation.resizeXY != 128 || spotAnimation.resizeZ != 128) {
			animatedModel.scaleT(spotAnimation.resizeXY, spotAnimation.resizeXY, spotAnimation.resizeZ);
		}
		if (spotAnimation.rotation != 0) {
			if (spotAnimation.rotation == 90) {
				animatedModel.rotate90Degrees(360);
			}
			if (spotAnimation.rotation == 180) {
				animatedModel.rotate90Degrees(360);
				animatedModel.rotate90Degrees(360);
			}
			if (spotAnimation.rotation == 270) {
				animatedModel.rotate90Degrees(360);
				animatedModel.rotate90Degrees(360);
				animatedModel.rotate90Degrees(360);
			}
		}
		animatedModel.applyLighting(64 + spotAnimation.modelBrightness, 850 + spotAnimation.modelShadow, -30, -50, -30,
				true);
		return animatedModel;
	}

	public final void method404(int i) {
		duration += i;
		while (duration > spotAnimation.animationSequences.getFrameLength(eclapsedFrames, (byte) -39)) {
			duration -= spotAnimation.animationSequences.getFrameLength(eclapsedFrames, (byte) -39) + 1;
			eclapsedFrames++;
			if (eclapsedFrames >= spotAnimation.animationSequences.anInt50
					&& (eclapsedFrames < 0 || eclapsedFrames >= spotAnimation.animationSequences.anInt50)) {
				eclapsedFrames = 0;
				transformCompleted = true;
			}
		}
	}
}
