package com.jagex.runescape.media.renderable;

import com.jagex.runescape.cache.media.SpotAnimation;
import com.jagex.runescape.media.Animation;

public class Projectile extends Renderable {

	public int delay;
	public int endCycle;
	private double speedVectorX;
	private double speedVectorY;
	private double speedVectorScalar;
	private double speedVectorZ;
	private double heightOffset;
	public boolean moving = false;
	public int startX;
	public int startY;
	public int startHeight;
	public int endHeight;
	public double currentX;
	public double currentY;
	public double currentHeight;
	public int startSlope;
	public int startDistanceFromTarget;
	public int targetedEntityId;
	private final SpotAnimation animation;
	private int animationFrame;
	private int duration;
	public int modelRotationY;
	public int modelRotationX;
	public int sceneId;

	public final void trackTarget(int loopCycle, int targetY, int targetZ, int targetX) {
		if (!moving) {
			double distanceX = targetX - startX;
			double distanceY = targetY - startY;
			double distanceScalar = Math.sqrt(distanceX * distanceX + distanceY * distanceY);
			currentX = startX + distanceX * startDistanceFromTarget / distanceScalar;
			currentY = startY + distanceY * startDistanceFromTarget / distanceScalar;
			currentHeight = startHeight;
		}
		double cyclesRemaining = endCycle + 1 - loopCycle;
		speedVectorX = (targetX - currentX) / cyclesRemaining;
		speedVectorY = (targetY - currentY) / cyclesRemaining;
		speedVectorScalar = Math.sqrt(speedVectorX * speedVectorX + speedVectorY * speedVectorY);
		if (!moving) {
			speedVectorZ = -speedVectorScalar * Math.tan(startSlope * 0.02454369);
		}
		heightOffset = 2.0 * (targetZ - currentHeight - speedVectorZ * cyclesRemaining)
				/ (cyclesRemaining * cyclesRemaining);
	}

	@Override
	public final Model getRotatedModel() {
		Model model = animation.getModel();
		if (model == null) {
			return null;
		}
		int frameId = -1;
		if (animation.sequences != null) {
			frameId = animation.sequences.frame2Ids[animationFrame];
		}
		Model projectileModel = new Model(true, Animation.exists(frameId), false, model);
		if (frameId != -1) {
			projectileModel.createBones();
			projectileModel.applyTransform(frameId);
			projectileModel.triangleSkin = null;
			projectileModel.vectorSkin = null;
		}
		if (animation.resizeXY != 128 || animation.resizeZ != 128) {
			projectileModel.scaleT(animation.resizeXY, animation.resizeXY, animation.resizeZ);
		}
		projectileModel.rotateX(modelRotationX, 1);
		projectileModel.applyLighting(64 + animation.modelLightFalloff, 850 + animation.modelLightAmbient, -30, -50,
				-30, true);
		return projectileModel;
	}

	public Projectile(int startSlope, int endHeight, int delay, int speed, int startDistanceFromTarget, int sceneId,
			int height, int projectileY, int projectileX, int targetedEntityIndex, int spotAnimationId) {
		this.animation = SpotAnimation.cache[spotAnimationId];
		this.sceneId = sceneId;
		this.startX = projectileX;
		this.startY = projectileY;
		this.startHeight = height;
		this.delay = delay;
		this.endCycle = speed;
		this.startSlope = startSlope;
		this.startDistanceFromTarget = startDistanceFromTarget;
		this.targetedEntityId = targetedEntityIndex;
		this.endHeight = endHeight;
		this.moving = false;
	}

	public final void move(int time) {
		moving = true;
		currentX += speedVectorX * time;
		currentY += speedVectorY * time;
		currentHeight += speedVectorZ * time + 0.5 * heightOffset * time * time;
		speedVectorZ += heightOffset * time;
		modelRotationY = (int) (Math.atan2(speedVectorX, speedVectorY) * 325.949) + 1024 & 0x7ff;
		modelRotationX = (int) (Math.atan2(speedVectorZ, speedVectorScalar) * 325.949) & 0x7ff;
		if (animation.sequences == null) {
			return;
		}
		duration += time;
		while (duration > animation.sequences.getFrameLength(animationFrame)) {
			duration -= animation.sequences.getFrameLength(animationFrame) + 1;
			animationFrame++;
			if (animationFrame >= animation.sequences.frameCount) {
				animationFrame = 0;
			}
		}
	}
}
