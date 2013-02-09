package com.runescape.scene;

import com.runescape.Client;
import com.runescape.anim.AnimationSequence;
import com.runescape.cache.def.GameObjectDefinition;
import com.runescape.cache.def.VarBit;
import com.runescape.graphic.Model;
import com.runescape.renderable.Renderable;

public class GameObject extends Renderable {

	private int animationFrame;
	protected int[] anIntArray1591;
	protected int anInt1592;
	protected int anInt1593;
	private int anInt1594;
	private int anInt1595;
	private int anInt1596;
	private int anInt1597;
	private AnimationSequence animationSequence;
	private int animationCycleDelay;
	public static Client client;
	private int anInt1601;
	private int anInt1602;
	private int anInt1603;

	@Override
	public final Model getRotatedModel() {
		int animation = -1;
		if (animationSequence != null) {
			int step = Client.currentCycle - animationCycleDelay;
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
			animationCycleDelay = Client.currentCycle - step;
			if (animationSequence != null) {
				animation = animationSequence.frame2Ids[animationFrame];
			}
		}
		GameObjectDefinition gameObjectDefinition;
		if (anIntArray1591 != null) {
			gameObjectDefinition = method405();
		} else {
			gameObjectDefinition = GameObjectDefinition.getDefinition(anInt1601);
		}
		if (gameObjectDefinition == null) {
			return null;
		}
		Model model = gameObjectDefinition.getGameObjectModel(anInt1602, anInt1603, anInt1594, anInt1595, anInt1596,
				anInt1597, animation);
		return model;
	}

	public final GameObjectDefinition method405() {
		int i = -1;
		if (anInt1592 != -1) {
			VarBit varbit = VarBit.cache[anInt1592];
			int configId = varbit.configId;
			int leastSignificantBit = varbit.leastSignificantBit;
			int mostSignificantBit = varbit.mostSignificantBit;
			int bit = Client.BITFIELD_MAX_VALUE[mostSignificantBit - leastSignificantBit];
			i = GameObject.client.settings[configId] >> leastSignificantBit & bit;
		} else if (anInt1593 != -1) {
			i = GameObject.client.settings[anInt1593];
		}
		if (i < 0 || i >= anIntArray1591.length || anIntArray1591[i] == -1) {
			return null;
		}
		return GameObjectDefinition.getDefinition(anIntArray1591[i]);
	}

	public GameObject(int i, int i_7_, int i_8_, int i_9_, int i_10_, int i_11_, int i_12_, int animationId,
			boolean bool) {
		anInt1601 = i;
		anInt1602 = i_8_;
		anInt1603 = i_7_;
		anInt1594 = i_11_;
		anInt1595 = i_9_;
		anInt1596 = i_10_;
		anInt1597 = i_12_;
		if (animationId != -1) {
			animationSequence = AnimationSequence.cache[animationId];
			animationFrame = 0;
			animationCycleDelay = Client.currentCycle;
			if (bool && animationSequence.frameStep != -1) {
				animationFrame = (int) (Math.random() * animationSequence.frameCount);
				animationCycleDelay -= (int) (Math.random() * animationSequence.getFrameLength(animationFrame));
			}
		}
		GameObjectDefinition gameObjectDefinition = GameObjectDefinition.getDefinition(anInt1601);
		anInt1592 = gameObjectDefinition.varbitid;
		anInt1593 = gameObjectDefinition.configId;
		anIntArray1591 = gameObjectDefinition.childrenIds;
	}
}
