package com.runescape.scene;

import com.runescape.Client;
import com.runescape.anim.AnimationSequence;
import com.runescape.cache.def.GameObjectDefinition;
import com.runescape.cache.def.VarBit;
import com.runescape.graphic.Model;
import com.runescape.renderable.Renderable;
import com.runescape.util.SignLink;

/* GameObject - Decompiled by JODE
 * Visit http://jode.sourceforge.net/
 */

public class GameObject extends Renderable {
	private byte aByte1589 = 7;
	private int anInt1590;
	protected int[] anIntArray1591;
	protected int anInt1592;
	protected int anInt1593;
	private int anInt1594;
	private int anInt1595;
	private int anInt1596;
	private int anInt1597;
	private AnimationSequence anAnimationSequence1598;
	private int anInt1599;
	public static Client aClient1600;
	private int anInt1601;
	private int anInt1602;
	private int anInt1603;

	@Override
	public final Model getRotatedModel() {
		int i_0_ = -1;
		if (anAnimationSequence1598 != null) {
			int i_1_ = Client.currentCycle - anInt1599;
			if (i_1_ > 100 && anAnimationSequence1598.anInt54 > 0) {
				i_1_ = 100;
			}
			while (i_1_ > anAnimationSequence1598.getFrameLength(anInt1590, (byte) -39)) {
				i_1_ -= anAnimationSequence1598.getFrameLength(anInt1590, (byte) -39);
				anInt1590++;
				if (anInt1590 >= anAnimationSequence1598.anInt50) {
					anInt1590 -= anAnimationSequence1598.anInt54;
					if (anInt1590 < 0 || anInt1590 >= anAnimationSequence1598.anInt50) {
						anAnimationSequence1598 = null;
						break;
					}
				}
			}
			anInt1599 = Client.currentCycle - i_1_;
			if (anAnimationSequence1598 != null) {
				i_0_ = anAnimationSequence1598.animationForFrame[anInt1590];
			}
		}
		GameObjectDefinition gameobjectdefinition;
		if (anIntArray1591 != null) {
			gameobjectdefinition = method405(true);
		} else {
			gameobjectdefinition = GameObjectDefinition.getObjectByHash(anInt1601);
		}
		if (gameobjectdefinition == null) {
			return null;
		}
		Model model = gameobjectdefinition.method238(anInt1602, anInt1603, anInt1594, anInt1595, anInt1596, anInt1597,
				i_0_);
		return model;
	}

	public final GameObjectDefinition method405(boolean bool) {
		try {
			int i = -1;
			if (!bool) {
				for (int i_2_ = 1; i_2_ > 0; i_2_++) {
					/* empty */
				}
			}
			if (anInt1592 != -1) {
				VarBit varbit = VarBit.cache[anInt1592];
				int i_3_ = varbit.anInt737;
				int i_4_ = varbit.anInt738;
				int i_5_ = varbit.anInt739;
				int i_6_ = Client.anIntArray1257[i_5_ - i_4_];
				i = GameObject.aClient1600.settings[i_3_] >> i_4_ & i_6_;
			} else if (anInt1593 != -1) {
				i = GameObject.aClient1600.settings[anInt1593];
			}
			if (i < 0 || i >= anIntArray1591.length || anIntArray1591[i] == -1) {
				return null;
			}
			return GameObjectDefinition.getObjectByHash(anIntArray1591[i]);
		} catch (RuntimeException runtimeexception) {
			SignLink.reportError("17301, " + bool + ", " + runtimeexception.toString());
			throw new RuntimeException();
		}
	}

	public GameObject(int i, int i_7_, int i_8_, int i_9_, byte b, int i_10_, int i_11_, int i_12_, int i_13_,
			boolean bool) {
		try {
			if (b != aByte1589) {
				for (int i_14_ = 1; i_14_ > 0; i_14_++) {
					/* empty */
				}
			}
			anInt1601 = i;
			anInt1602 = i_8_;
			anInt1603 = i_7_;
			anInt1594 = i_11_;
			anInt1595 = i_9_;
			anInt1596 = i_10_;
			anInt1597 = i_12_;
			if (i_13_ != -1) {
				anAnimationSequence1598 = AnimationSequence.animationSequences[i_13_];
				anInt1590 = 0;
				anInt1599 = Client.currentCycle;
				if (bool && anAnimationSequence1598.anInt54 != -1) {
					anInt1590 = (int) (Math.random() * anAnimationSequence1598.anInt50);
					anInt1599 -= (int) (Math.random() * anAnimationSequence1598.getFrameLength(anInt1590, (byte) -39));
				}
			}
			GameObjectDefinition gameobjectdefinition = GameObjectDefinition.getObjectByHash(anInt1601);
			anInt1592 = gameobjectdefinition.anInt279;
			anInt1593 = gameobjectdefinition.anInt254;
			anIntArray1591 = gameobjectdefinition.anIntArray264;
		} catch (RuntimeException runtimeexception) {
			SignLink.reportError("84816, " + i + ", " + i_7_ + ", " + i_8_ + ", " + i_9_ + ", " + b + ", " + i_10_
					+ ", " + i_11_ + ", " + i_12_ + ", " + i_13_ + ", " + bool + ", " + runtimeexception.toString());
			throw new RuntimeException();
		}
	}
}
