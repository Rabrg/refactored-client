package com.runescape.renderable;

import com.runescape.Client;
import com.runescape.anim.Animation;
import com.runescape.anim.AnimationSequence;
import com.runescape.anim.SpotAnimation;
import com.runescape.cache.def.ActorDefinition;
import com.runescape.cache.def.IdentityKit;
import com.runescape.cache.def.ItemDefinition;
import com.runescape.graphic.Model;
import com.runescape.net.Buffer;
import com.runescape.node.Cache;
import com.runescape.util.TextUtils;

public class Player extends Actor {

	public long aLong1717 = -1L;
	public ActorDefinition npcDefinition;
	public boolean aBoolean1719 = false;
	public int[] appearanceColors = new int[5];
	public int teamId;
	public int gender;
	public String playerName;
	public static Cache modelCache = new Cache(260);
	public int combatLevel;
	public int headIcon;
	public int anInt1727;
	public int anInt1728;
	public int anInt1729;
	public boolean visibile = false;
	public int anInt1731;
	public int anInt1732;
	public int anInt1733;
	public Model aModel1734;
	public int[] appearance = new int[12];
	public long appearanceOffset;
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
		Model model = getAnimatedModel();
		if (model == null) {
			return null;
		}
		modelHeight = model.modelHeight;
		model.oneSquareModel = true;
		if (aBoolean1719) {
			return model;
		}
		if (spotAnimationId != -1 && currentAnimationFrame != -1) {
			SpotAnimation spotAnimation = SpotAnimation.cache[spotAnimationId];
			Model spotAnimationModel = spotAnimation.getModel();
			if (spotAnimationModel != null) {
				Model spotAnimationModel2 = new Model(true, Animation.exists(currentAnimationFrame), false,
						spotAnimationModel);
				spotAnimationModel2.translate(0, -spotAnimationDelay, 0);
				spotAnimationModel2.createBones();
				spotAnimationModel2.applyTransform(spotAnimation.sequences.animationForFrame[currentAnimationFrame]);
				spotAnimationModel2.triangleSkin = null;
				spotAnimationModel2.vectorSkin = null;
				if (spotAnimation.resizeXY != 128 || spotAnimation.resizeZ != 128) {
					spotAnimationModel2.scaleT(spotAnimation.resizeXY, spotAnimation.resizeXY, spotAnimation.resizeZ);
				}
				spotAnimationModel2.applyLighting(64 + spotAnimation.modelBrightness, 850 + spotAnimation.modelShadow,
						-30, -50, -30, true);
				Model[] models = { model, spotAnimationModel2 };
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
		model.oneSquareModel = true;
		return model;
	}

	public final void updatePlayer(Buffer buffer) {
		buffer.offset = 0;
		gender = buffer.getUnsignedByte();
		headIcon = buffer.getUnsignedByte();
		npcDefinition = null;
		teamId = 0;
		for (int index = 0; index < 12; index++) {
			int i_4_ = buffer.getUnsignedByte();
			if (i_4_ == 0) {
				appearance[index] = 0;
			} else {
				int i_5_ = buffer.getUnsignedByte();
				appearance[index] = (i_4_ << 8) + i_5_;
				if (index == 0 && appearance[0] == 65535) {
					npcDefinition = ActorDefinition.get(buffer.getUnsignedLEShort());
					break;
				}
				if (appearance[index] >= 512 && appearance[index] - 512 < ItemDefinition.itemCount) {
					int teamId = ItemDefinition.get(appearance[index] - 512).teamIndex;
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
			appearanceColors[i_7_] = i_8_;
		}
		standAnimationId = buffer.getUnsignedLEShort();
		if (standAnimationId == 65535) {
			standAnimationId = -1;
		}
		standTurnAnimationId = buffer.getUnsignedLEShort();
		if (standTurnAnimationId == 65535) {
			standTurnAnimationId = -1;
		}
		walkAnimationId = buffer.getUnsignedLEShort();
		if (walkAnimationId == 65535) {
			walkAnimationId = -1;
		}
		turnAroundAnimationId = buffer.getUnsignedLEShort();
		if (turnAroundAnimationId == 65535) {
			turnAroundAnimationId = -1;
		}
		turnRightAnimationId = buffer.getUnsignedLEShort();
		if (turnRightAnimationId == 65535) {
			turnRightAnimationId = -1;
		}
		turnLeftAnimationId = buffer.getUnsignedLEShort();
		if (turnLeftAnimationId == 65535) {
			turnLeftAnimationId = -1;
		}
		runAnimationId = buffer.getUnsignedLEShort();
		if (runAnimationId == 65535) {
			runAnimationId = -1;
		}
		playerName = TextUtils.formatName(TextUtils.longToName(buffer.getLong()));
		combatLevel = buffer.getUnsignedByte();
		anInt1743 = buffer.getUnsignedLEShort();
		visibile = true;
		appearanceOffset = 0L;
		for (int index = 0; index < 12; index++) {
			appearanceOffset <<= 4;
			if (appearance[index] >= 256) {
				appearanceOffset += appearance[index] - 256;
			}
		}
		if (appearance[0] >= 256) {
			appearanceOffset += appearance[0] - 256 >> 4;
		}
		if (appearance[1] >= 256) {
			appearanceOffset += appearance[1] - 256 >> 8;
		}
		for (int index = 0; index < 5; index++) {
			appearanceOffset <<= 3;
			appearanceOffset += appearanceColors[index];
		}
		appearanceOffset <<= 1;
		appearanceOffset += gender;
	}

	private final Model getAnimatedModel() {
		if (npcDefinition != null) {
			int i_11_ = -1;
			if (animation >= 0 && aniomationDelay == 0) {
				i_11_ = AnimationSequence.cache[animation].animationForFrame[anInt1547];
			} else if (anInt1537 >= 0) {
				i_11_ = AnimationSequence.cache[anInt1537].animationForFrame[anInt1538];
			}
			Model model = npcDefinition.getChildModel(-1, i_11_, null);
			return model;
		}
		long l = appearanceOffset;
		int i_12_ = -1;
		int i_13_ = -1;
		int i_14_ = -1;
		int i_15_ = -1;
		if (animation >= 0 && aniomationDelay == 0) {
			AnimationSequence animationsequence = AnimationSequence.cache[animation];
			i_12_ = animationsequence.animationForFrame[anInt1547];
			if (anInt1537 >= 0 && anInt1537 != standAnimationId) {
				i_13_ = AnimationSequence.cache[anInt1537].animationForFrame[anInt1538];
			}
			if (animationsequence.anInt58 >= 0) {
				i_14_ = animationsequence.anInt58;
				l += i_14_ - appearance[5] << 8;
			}
			if (animationsequence.anInt59 >= 0) {
				i_15_ = animationsequence.anInt59;
				l += i_15_ - appearance[3] << 16;
			}
		} else if (anInt1537 >= 0) {
			i_12_ = AnimationSequence.cache[anInt1537].animationForFrame[anInt1538];
		}
		Model model = (Model) Player.modelCache.get(l);
		if (model == null) {
			boolean bool = false;
			for (int i_17_ = 0; i_17_ < 12; i_17_++) {
				int i_18_ = appearance[i_17_];
				if (i_15_ >= 0 && i_17_ == 3) {
					i_18_ = i_15_;
				}
				if (i_14_ >= 0 && i_17_ == 5) {
					i_18_ = i_14_;
				}
				if (i_18_ >= 256 && i_18_ < 512 && !IdentityKit.cache[i_18_ - 256].isBodyModelCached()) {
					bool = true;
				}
				if (i_18_ >= 512 && !ItemDefinition.get(i_18_ - 512).isEquipModelCached(gender)) {
					bool = true;
				}
			}
			if (bool) {
				if (aLong1717 != -1L) {
					model = (Model) Player.modelCache.get(aLong1717);
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
				int i_21_ = appearance[i_20_];
				if (i_15_ >= 0 && i_20_ == 3) {
					i_21_ = i_15_;
				}
				if (i_14_ >= 0 && i_20_ == 5) {
					i_21_ = i_14_;
				}
				if (i_21_ >= 256 && i_21_ < 512) {
					Model model_22_ = IdentityKit.cache[i_21_ - 256].getBodyModel();
					if (model_22_ != null) {
						models[i_19_++] = model_22_;
					}
				}
				if (i_21_ >= 512) {
					Model model_23_ = ItemDefinition.get(i_21_ - 512).getEquipModel(gender);
					if (model_23_ != null) {
						models[i_19_++] = model_23_;
					}
				}
			}
			model = new Model(i_19_, models);
			for (int i_24_ = 0; i_24_ < 5; i_24_++) {
				if (appearanceColors[i_24_] != 0) {
					model.recolor(Client.anIntArrayArray1028[i_24_][0],
							Client.anIntArrayArray1028[i_24_][appearanceColors[i_24_]]);
					if (i_24_ == 1) {
						model.recolor(Client.anIntArray1229[0], Client.anIntArray1229[appearanceColors[i_24_]]);
					}
				}
			}
			model.createBones();
			model.applyLighting(64, 850, -30, -50, -30, true);
			Player.modelCache.put(model, l);
			aLong1717 = l;
		}
		if (aBoolean1719) {
			return model;
		}
		Model model_25_ = Model.aModel1614;
		model_25_.method412(7, model, Animation.exists(i_12_) & Animation.exists(i_13_));
		if (i_12_ != -1 && i_13_ != -1) {
			model_25_.mixAnimationFrames(-20491, AnimationSequence.cache[animation].anIntArray55, i_13_, i_12_);
		} else if (i_12_ != -1) {
			model_25_.applyTransform(i_12_);
		}
		model_25_.calculateDiagonals(false);
		model_25_.triangleSkin = null;
		model_25_.vectorSkin = null;
		return model_25_;
	}

	@Override
	public final boolean isVisibile() {
		if (!visibile) {
			return false;
		}
		return true;
	}

	public final Model getHeadModel() {
		if (!visibile) {
			return null;
		}
		if (npcDefinition != null) {
			return npcDefinition.getHeadModel();
		}
		boolean cached = false;
		for (int index = 0; index < 12; index++) {
			int appearanceId = appearance[index];
			if (appearanceId >= 256 && appearanceId < 512 && !IdentityKit.cache[appearanceId - 256].isHeadModelCached()) {
				cached = true;
			}
			if (appearanceId >= 512 && !ItemDefinition.get(appearanceId - 512).isDialogueCached(gender)) {
				cached = true;
			}
		}
		if (cached) {
			return null;
		}
		Model[] headModels = new Model[12];
		int headModelsOffset = 0;
		for (int modelIndex = 0; modelIndex < 12; modelIndex++) {
			int appearanceId = appearance[modelIndex];
			if (appearanceId >= 256 && appearanceId < 512) {
				Model subModel = IdentityKit.cache[appearanceId - 256].getHeadModel();
				if (subModel != null) {
					headModels[headModelsOffset++] = subModel;
				}
			}
			if (appearanceId >= 512) {
				Model subModel = ItemDefinition.get(appearanceId - 512).getDialogueModel(gender);
				if (subModel != null) {
					headModels[headModelsOffset++] = subModel;
				}
			}
		}
		Model headModel = new Model(headModelsOffset, headModels);
		for (int index = 0; index < 5; index++) {
			if (appearanceColors[index] != 0) {
				headModel.recolor(Client.anIntArrayArray1028[index][0],
						Client.anIntArrayArray1028[index][appearanceColors[index]]);
				if (index == 1) {
					headModel.recolor(Client.anIntArray1229[0], Client.anIntArray1229[appearanceColors[index]]);
				}
			}
		}
		return headModel;
	}
}
