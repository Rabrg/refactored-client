package com.runescape.media.renderable;

import com.runescape.Game;
import com.runescape.cache.cfg.VarBit;
import com.runescape.cache.def.GameObjectDefinition;
import com.runescape.cache.media.AnimationSequence;

public class GameObject extends Renderable {

	private int animationFrame;
	protected int[] childrenIds;
	protected int varbitId;
	protected int configId;
	private int vertexHeight;
	private int vertexHeightRight;
	private int vertexHeightTopRight;
	private int vertexHeightTop;
	private AnimationSequence animationSequence;
	private int animationCycleDelay;
	public static Game client;
	private int id;
	private int clickType;
	private int face;

	@Override
	public final Model getRotatedModel() {
		int animation = -1;
		if (animationSequence != null) {
			int step = Game.currentCycle - animationCycleDelay;
			if (step > 100 && animationSequence.frameStep > 0) {
				step = 100;
			}
			while (step > animationSequence.getFrameLength(animationFrame)) {
				step -= animationSequence.getFrameLength(animationFrame);
				animationFrame++;
				if (animationFrame >= animationSequence.frameCount) {
					animationFrame -= animationSequence.frameStep;
					if (animationFrame < 0 || animationFrame >= animationSequence.frameCount) {
						animationSequence = null;
						break;
					}
				}
			}
			animationCycleDelay = Game.currentCycle - step;
			if (animationSequence != null) {
				animation = animationSequence.frame2Ids[animationFrame];
			}
		}
		GameObjectDefinition gameObjectDefinition;
		if (childrenIds != null) {
			gameObjectDefinition = getChildDefinition();
		} else {
			gameObjectDefinition = GameObjectDefinition.getDefinition(id);
		}
		if (gameObjectDefinition == null) {
			return null;
		}
		Model model = gameObjectDefinition.getGameObjectModel(clickType, face, vertexHeight, vertexHeightRight,
				vertexHeightTopRight, vertexHeightTop, animation);
		return model;
	}

	public final GameObjectDefinition getChildDefinition() {
		int child = -1;
		if (varbitId != -1) {
			VarBit varbit = VarBit.cache[varbitId];
			int configId = varbit.configId;
			int leastSignificantBit = varbit.leastSignificantBit;
			int mostSignificantBit = varbit.mostSignificantBit;
			int bit = Game.BITFIELD_MAX_VALUE[mostSignificantBit - leastSignificantBit];
			child = GameObject.client.widgetSettings[configId] >> leastSignificantBit & bit;
		} else if (configId != -1) {
			child = GameObject.client.widgetSettings[configId];
		}
		if (child < 0 || child >= childrenIds.length || childrenIds[child] == -1) {
			return null;
		}
		return GameObjectDefinition.getDefinition(childrenIds[child]);
	}

	public GameObject(int id, int face, int clickType, int vertexHeightRight, int vertexHeightTopRight,
			int vertexHeight, int vertexHeightTop, int animationId, boolean bool) {
		this.id = id;
		this.clickType = clickType;
		this.face = face;
		this.vertexHeight = vertexHeight;
		this.vertexHeightRight = vertexHeightRight;
		this.vertexHeightTopRight = vertexHeightTopRight;
		this.vertexHeightTop = vertexHeightTop;
		if (animationId != -1) {
			animationSequence = AnimationSequence.cache[animationId];
			animationFrame = 0;
			animationCycleDelay = Game.currentCycle;
			if (bool && animationSequence.frameStep != -1) {
				animationFrame = (int) (Math.random() * animationSequence.frameCount);
				animationCycleDelay -= (int) (Math.random() * animationSequence.getFrameLength(animationFrame));
			}
		}
		GameObjectDefinition gameObjectDefinition = GameObjectDefinition.getDefinition(id);
		varbitId = gameObjectDefinition.varbitId;
		configId = gameObjectDefinition.configId;
		childrenIds = gameObjectDefinition.childrenIds;
	}
}
