package com.runescape.renderable;

import com.runescape.Client;
import com.runescape.anim.Animation;
import com.runescape.anim.AnimationSequence;
import com.runescape.anim.SpotAnimation;
import com.runescape.cache.def.IdentityKit;
import com.runescape.cache.def.ItemDefinition;
import com.runescape.cache.def.NpcDefinition;
import com.runescape.graphic.Model;
import com.runescape.net.Buffer;
import com.runescape.node.Cache;
import com.runescape.util.SignLink;
import com.runescape.util.TextUtils;

/* Player - Decompiled by JODE
 * Visit http://jode.sourceforge.net/
 */

public class Player extends Actor {
	public long aLong1717 = -1L;
	public NpcDefinition npcDefinition;
	public boolean aBoolean1719 = false;
	public int[] anIntArray1720 = new int[5];
	public int teamId;
	public int gender;
	public String aString1723;
	public static Cache aCache1724 = new Cache(260);
	public int combatLevel;
	public int anInt1726;
	public int anInt1727;
	public int anInt1728;
	public int anInt1729;
	public boolean visibile = false;
	public int anInt1731;
	public int anInt1732;
	public int anInt1733;
	public Model aModel1734;
	public int[] appearanceModels = new int[12];
	public long aLong1738;
	public int anInt1739;
	public int anInt1740;
	public int anInt1741;
	public int anInt1742;
	public int anInt1743;

	@Override
	public final Model getRotatedModel() {
		if (!visibile) {
			return null;
		}
		Model model = method402(0);
		if (model == null) {
			return null;
		}
		anInt1527 = model.modelHeight;
		model.aBoolean1652 = true;
		if (aBoolean1719) {
			return model;
		}
		if (spotAnimationId != -1 && currentAnimationFrame != -1) {
			SpotAnimation spotanimation = SpotAnimation.cache[spotAnimationId];
			Model model_0_ = spotanimation.getModel();
			if (model_0_ != null) {
				Model model_1_ = new Model(true, Animation.exists(currentAnimationFrame), false, model_0_);
				model_1_.translate(0, -anInt1544, 0);
				model_1_.createBones();
				model_1_.applyTransform(spotanimation.animationSequences.animationForFrame[currentAnimationFrame]);
				model_1_.triangleSkin = null;
				model_1_.vectorSkin = null;
				if (spotanimation.resizeXY != 128 || spotanimation.resizeZ != 128) {
					model_1_.scaleT(spotanimation.resizeXY, spotanimation.resizeXY, spotanimation.resizeZ);
				}
				model_1_.applyLighting(64 + spotanimation.modelBrightness, 850 + spotanimation.modelShadow, -30, -50,
						-30, true);
				Model[] models = { model, model_1_ };
				model = new Model(2, -819, true, models);
			}
		}
		if (aModel1734 != null) {
			if (Client.currentCycle >= anInt1728) {
				aModel1734 = null;
			}
			if (Client.currentCycle >= anInt1727 && Client.currentCycle < anInt1728) {
				Model model_2_ = aModel1734;
				model_2_.translate(anInt1731 - xWithBoundary, anInt1732 - anInt1729, anInt1733 - yWithBoundary);
				if (anInt1530 == 512) {
					model_2_.rotate90Degrees(360);
					model_2_.rotate90Degrees(360);
					model_2_.rotate90Degrees(360);
				} else if (anInt1530 == 1024) {
					model_2_.rotate90Degrees(360);
					model_2_.rotate90Degrees(360);
				} else if (anInt1530 == 1536) {
					model_2_.rotate90Degrees(360);
				}
				Model[] models = { model, model_2_ };
				model = new Model(2, -819, true, models);
				if (anInt1530 == 512) {
					model_2_.rotate90Degrees(360);
				} else if (anInt1530 == 1024) {
					model_2_.rotate90Degrees(360);
					model_2_.rotate90Degrees(360);
				} else if (anInt1530 == 1536) {
					model_2_.rotate90Degrees(360);
					model_2_.rotate90Degrees(360);
					model_2_.rotate90Degrees(360);
				}
				model_2_.translate(xWithBoundary - anInt1731, anInt1729 - anInt1732, yWithBoundary - anInt1733);
			}
		}
		model.aBoolean1652 = true;
		return model;
	}

	public final void method401(int i, Buffer buffer) {
		try {
			buffer.offset = 0;
			gender = buffer.getUnsignedByte();
			anInt1726 = buffer.getUnsignedByte();
			if (i == 0) {
				npcDefinition = null;
				teamId = 0;
				for (int i_3_ = 0; i_3_ < 12; i_3_++) {
					int i_4_ = buffer.getUnsignedByte();
					if (i_4_ == 0) {
						appearanceModels[i_3_] = 0;
					} else {
						int i_5_ = buffer.getUnsignedByte();
						appearanceModels[i_3_] = (i_4_ << 8) + i_5_;
						if (i_3_ == 0 && appearanceModels[0] == 65535) {
							npcDefinition = NpcDefinition.getDefinition(buffer.getUnsignedLEShort());
							break;
						}
						if (appearanceModels[i_3_] >= 512 && appearanceModels[i_3_] - 512 < ItemDefinition.itemCount) {
							int teamId = ItemDefinition.getDefinition(appearanceModels[i_3_] - 512).teamId;
							if (teamId != 0) {
								this.teamId = teamId;
							}
						}
					}
				}
				for (int i_7_ = 0; i_7_ < 5; i_7_++) {
					int i_8_ = buffer.getUnsignedByte();
					if (i_8_ < 0 || i_8_ >= Client.anIntArrayArray1028[i_7_].length) {
						i_8_ = 0;
					}
					anIntArray1720[i_7_] = i_8_;
				}
				anInt1531 = buffer.getUnsignedLEShort();
				if (anInt1531 == 65535) {
					anInt1531 = -1;
				}
				anInt1532 = buffer.getUnsignedLEShort();
				if (anInt1532 == 65535) {
					anInt1532 = -1;
				}
				anInt1574 = buffer.getUnsignedLEShort();
				if (anInt1574 == 65535) {
					anInt1574 = -1;
				}
				anInt1575 = buffer.getUnsignedLEShort();
				if (anInt1575 == 65535) {
					anInt1575 = -1;
				}
				anInt1576 = buffer.getUnsignedLEShort();
				if (anInt1576 == 65535) {
					anInt1576 = -1;
				}
				anInt1577 = buffer.getUnsignedLEShort();
				if (anInt1577 == 65535) {
					anInt1577 = -1;
				}
				anInt1525 = buffer.getUnsignedLEShort();
				if (anInt1525 == 65535) {
					anInt1525 = -1;
				}
				aString1723 = TextUtils.formatName(TextUtils.longToName(buffer.getLong()));
				combatLevel = buffer.getUnsignedByte();
				anInt1743 = buffer.getUnsignedLEShort();
				visibile = true;
				aLong1738 = 0L;
				for (int i_9_ = 0; i_9_ < 12; i_9_++) {
					aLong1738 <<= 4;
					if (appearanceModels[i_9_] >= 256) {
						aLong1738 += appearanceModels[i_9_] - 256;
					}
				}
				if (appearanceModels[0] >= 256) {
					aLong1738 += appearanceModels[0] - 256 >> 4;
				}
				if (appearanceModels[1] >= 256) {
					aLong1738 += appearanceModels[1] - 256 >> 8;
				}
				for (int i_10_ = 0; i_10_ < 5; i_10_++) {
					aLong1738 <<= 3;
					aLong1738 += anIntArray1720[i_10_];
				}
				aLong1738 <<= 1;
				aLong1738 += gender;
			}
		} catch (RuntimeException runtimeexception) {
			SignLink.reportError("28951, " + i + ", " + buffer + ", " + runtimeexception.toString());
			throw new RuntimeException();
		}
	}

	private final Model method402(int i) {
		try {
			if (npcDefinition != null) {
				int i_11_ = -1;
				if (animation >= 0 && anInt1549 == 0) {
					i_11_ = AnimationSequence.animationSequences[animation].animationForFrame[anInt1547];
				} else if (anInt1537 >= 0) {
					i_11_ = AnimationSequence.animationSequences[anInt1537].animationForFrame[anInt1538];
				}
				Model model = npcDefinition.method442(0, -1, i_11_, null);
				return model;
			}
			long l = aLong1738;
			int i_12_ = -1;
			int i_13_ = -1;
			int i_14_ = -1;
			int i_15_ = -1;
			if (animation >= 0 && anInt1549 == 0) {
				AnimationSequence animationsequence = AnimationSequence.animationSequences[animation];
				i_12_ = animationsequence.animationForFrame[anInt1547];
				if (anInt1537 >= 0 && anInt1537 != anInt1531) {
					i_13_ = AnimationSequence.animationSequences[anInt1537].animationForFrame[anInt1538];
				}
				if (animationsequence.anInt58 >= 0) {
					i_14_ = animationsequence.anInt58;
					l += i_14_ - appearanceModels[5] << 8;
				}
				if (animationsequence.anInt59 >= 0) {
					i_15_ = animationsequence.anInt59;
					l += i_15_ - appearanceModels[3] << 16;
				}
			} else if (anInt1537 >= 0) {
				i_12_ = AnimationSequence.animationSequences[anInt1537].animationForFrame[anInt1538];
			}
			Model model = (Model) Player.aCache1724.get(l);
			if (i != 0) {
				for (int i_16_ = 1; i_16_ > 0; i_16_++) {
					/* empty */
				}
			}
			if (model == null) {
				boolean bool = false;
				for (int i_17_ = 0; i_17_ < 12; i_17_++) {
					int i_18_ = appearanceModels[i_17_];
					if (i_15_ >= 0 && i_17_ == 3) {
						i_18_ = i_15_;
					}
					if (i_14_ >= 0 && i_17_ == 5) {
						i_18_ = i_14_;
					}
					if (i_18_ >= 256 && i_18_ < 512 && !IdentityKit.identityKitCache[i_18_ - 256].isBodyModelCached()) {
						bool = true;
					}
					if (i_18_ >= 512 && !ItemDefinition.getDefinition(i_18_ - 512).isEquipModelCached(40903, gender)) {
						bool = true;
					}
				}
				if (bool) {
					if (aLong1717 != -1L) {
						model = (Model) Player.aCache1724.get(aLong1717);
					}
					if (model == null) {
						return null;
					}
				}
			}
			if (model == null) {
				Model[] models = new Model[12];
				int i_19_ = 0;
				for (int i_20_ = 0; i_20_ < 12; i_20_++) {
					int i_21_ = appearanceModels[i_20_];
					if (i_15_ >= 0 && i_20_ == 3) {
						i_21_ = i_15_;
					}
					if (i_14_ >= 0 && i_20_ == 5) {
						i_21_ = i_14_;
					}
					if (i_21_ >= 256 && i_21_ < 512) {
						Model model_22_ = IdentityKit.identityKitCache[i_21_ - 256].getBodyModel();
						if (model_22_ != null) {
							models[i_19_++] = model_22_;
						}
					}
					if (i_21_ >= 512) {
						Model model_23_ = ItemDefinition.getDefinition(i_21_ - 512).getEquipModel(gender);
						if (model_23_ != null) {
							models[i_19_++] = model_23_;
						}
					}
				}
				model = new Model(i_19_, models);
				for (int i_24_ = 0; i_24_ < 5; i_24_++) {
					if (anIntArray1720[i_24_] != 0) {
						model.recolor(Client.anIntArrayArray1028[i_24_][0],
								Client.anIntArrayArray1028[i_24_][anIntArray1720[i_24_]]);
						if (i_24_ == 1) {
							model.recolor(Client.anIntArray1229[0], Client.anIntArray1229[anIntArray1720[i_24_]]);
						}
					}
				}
				model.createBones();
				model.applyLighting(64, 850, -30, -50, -30, true);
				Player.aCache1724.put(model, l);
				aLong1717 = l;
			}
			if (aBoolean1719) {
				return model;
			}
			Model model_25_ = Model.aModel1614;
			model_25_.method412(7, model, Animation.exists(i_12_) & Animation.exists(i_13_));
			if (i_12_ != -1 && i_13_ != -1) {
				model_25_.method419(-20491, AnimationSequence.animationSequences[animation].anIntArray55, i_13_, i_12_);
			} else if (i_12_ != -1) {
				model_25_.applyTransform(i_12_);
			}
			model_25_.method414(false);
			model_25_.triangleSkin = null;
			model_25_.vectorSkin = null;
			return model_25_;
		} catch (RuntimeException runtimeexception) {
			SignLink.reportError("88397, " + i + ", " + runtimeexception.toString());
			throw new RuntimeException();
		}
	}

	@Override
	public final boolean isVisibile() {
		if (!visibile) {
			return false;
		}
		return true;
	}

	public final Model getModel() {
		if (!visibile) {
			return null;
		}
		if (npcDefinition != null) {
			return npcDefinition.getModel(true);
		}
		boolean bool = false;
		for (int i = 0; i < 12; i++) {
			int i_26_ = appearanceModels[i];
			if (i_26_ >= 256 && i_26_ < 512 && !IdentityKit.identityKitCache[i_26_ - 256].isHeadModelCached()) {
				bool = true;
			}
			if (i_26_ >= 512 && !ItemDefinition.getDefinition(i_26_ - 512).isDialogueCached(gender)) {
				bool = true;
			}
		}
		if (bool) {
			return null;
		}
		Model[] subModels = new Model[12];
		int subModelOffset = 0;
		for (int modelIndex = 0; modelIndex < 12; modelIndex++) {
			int modelId = appearanceModels[modelIndex];
			if (modelId >= 256 && modelId < 512) {
				Model subModel = IdentityKit.identityKitCache[modelId - 256].getHeadModel();
				if (subModel != null) {
					subModels[subModelOffset++] = subModel;
				}
			}
			if (modelId >= 512) {
				Model subModel = ItemDefinition.getDefinition(modelId - 512).getDialogueModel(gender);
				if (subModel != null) {
					subModels[subModelOffset++] = subModel;
				}
			}
		}
		Model model = new Model(subModelOffset, subModels);
		for (int i_29_ = 0; i_29_ < 5; i_29_++) {
			if (anIntArray1720[i_29_] != 0) {
				model.recolor(Client.anIntArrayArray1028[i_29_][0],
						Client.anIntArrayArray1028[i_29_][anIntArray1720[i_29_]]);
				if (i_29_ == 1) {
					model.recolor(Client.anIntArray1229[0], Client.anIntArray1229[anIntArray1720[i_29_]]);
				}
			}
		}
		return model;
	}
}
