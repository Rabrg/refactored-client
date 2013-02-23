package com.runescape.media.renderable.actor;

import com.runescape.Game;
import com.runescape.cache.def.ActorDefinition;
import com.runescape.cache.def.ItemDefinition;
import com.runescape.cache.media.AnimationSequence;
import com.runescape.cache.media.IdentityKit;
import com.runescape.cache.media.Model;
import com.runescape.cache.media.SpotAnimation;
import com.runescape.collection.Cache;
import com.runescape.media.Animation;
import com.runescape.net.Buffer;
import com.runescape.util.TextUtils;

public class Player extends Actor {

	public long cachedModel = -1L;
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
	public Model playerModel;
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
		Model animatedModel = getAnimatedModel();
		if (animatedModel == null) {
			return null;
		}
		modelHeight = animatedModel.modelHeight;
		animatedModel.oneSquareModel = true;
		if (aBoolean1719) {
			return animatedModel;
		}
		if (spotAnimationId != -1 && currentAnimationFrame != -1) {
			SpotAnimation spotAnimation = SpotAnimation.cache[spotAnimationId];
			Model spotAnimationModel = spotAnimation.getModel();
			if (spotAnimationModel != null) {
				Model spotAnimationModel2 = new Model(true, Animation.exists(currentAnimationFrame), false,
						spotAnimationModel);
				spotAnimationModel2.translate(0, -spotAnimationDelay, 0);
				spotAnimationModel2.createBones();
				spotAnimationModel2.applyTransform(spotAnimation.sequences.frame2Ids[currentAnimationFrame]);
				spotAnimationModel2.triangleSkin = null;
				spotAnimationModel2.vectorSkin = null;
				if (spotAnimation.resizeXY != 128 || spotAnimation.resizeZ != 128) {
					spotAnimationModel2.scaleT(spotAnimation.resizeXY, spotAnimation.resizeZ, spotAnimation.resizeXY);
				}
				spotAnimationModel2.applyLighting(64 + spotAnimation.modelLightFalloff,
						850 + spotAnimation.modelLightAmbient, -30, -50, -30, true);
				Model[] models = { animatedModel, spotAnimationModel2 };
				animatedModel = new Model(2, -819, true, models);
			}
		}
		if (playerModel != null) {
			if (Game.currentCycle >= anInt1728) {
				playerModel = null;
			}
			if (Game.currentCycle >= anInt1727 && Game.currentCycle < anInt1728) {
				Model playerModel = this.playerModel;
				playerModel.translate(anInt1731 - xWithBoundary, anInt1732 - anInt1729, anInt1733 - yWithBoundary);
				if (anInt1530 == 512) {
					playerModel.rotate90Degrees(360);
					playerModel.rotate90Degrees(360);
					playerModel.rotate90Degrees(360);
				} else if (anInt1530 == 1024) {
					playerModel.rotate90Degrees(360);
					playerModel.rotate90Degrees(360);
				} else if (anInt1530 == 1536) {
					playerModel.rotate90Degrees(360);
				}
				Model[] models = { animatedModel, playerModel };
				animatedModel = new Model(2, -819, true, models);
				if (anInt1530 == 512) {
					playerModel.rotate90Degrees(360);
				} else if (anInt1530 == 1024) {
					playerModel.rotate90Degrees(360);
					playerModel.rotate90Degrees(360);
				} else if (anInt1530 == 1536) {
					playerModel.rotate90Degrees(360);
					playerModel.rotate90Degrees(360);
					playerModel.rotate90Degrees(360);
				}
				playerModel.translate(xWithBoundary - anInt1731, anInt1729 - anInt1732, yWithBoundary - anInt1733);
			}
		}
		animatedModel.oneSquareModel = true;
		return animatedModel;
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
					npcDefinition = ActorDefinition.getDefinition(buffer.getUnsignedLEShort());
					break;
				}
				if (appearance[index] >= 512 && appearance[index] - 512 < ItemDefinition.itemCount) {
					int teamId = ItemDefinition.getDefinition(appearance[index] - 512).teamIndex;
					if (teamId != 0) {
						this.teamId = teamId;
					}
				}
			}
		}
		for (int i_7_ = 0; i_7_ < 5; i_7_++) {
			int i_8_ = buffer.getUnsignedByte();
			if (i_8_ < 0 || i_8_ >= Game.anIntArrayArray1028[i_7_].length) {
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
				i_11_ = AnimationSequence.cache[animation].frame2Ids[anInt1547];
			} else if (anInt1537 >= 0) {
				i_11_ = AnimationSequence.cache[anInt1537].frame2Ids[anInt1538];
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
			i_12_ = animationsequence.frame2Ids[anInt1547];
			if (anInt1537 >= 0 && anInt1537 != standAnimationId) {
				i_13_ = AnimationSequence.cache[anInt1537].frame2Ids[anInt1538];
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
			i_12_ = AnimationSequence.cache[anInt1537].frame2Ids[anInt1538];
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
				if (i_18_ >= 512 && !ItemDefinition.getDefinition(i_18_ - 512).isEquipModelCached(gender)) {
					bool = true;
				}
			}
			if (bool) {
				if (cachedModel != -1L) {
					model = (Model) Player.modelCache.get(cachedModel);
				}
				if (model == null) {
					return null;
				}
			}
		}
		if (model == null) {
			Model[] models = new Model[12];
			int modelIndex = 0;
			for (int appearanceIndex = 0; appearanceIndex < 12; appearanceIndex++) {
				int appearanceValue = appearance[appearanceIndex];
				if (i_15_ >= 0 && appearanceIndex == 3) {
					appearanceValue = i_15_;
				}
				if (i_14_ >= 0 && appearanceIndex == 5) {
					appearanceValue = i_14_;
				}
				if (appearanceValue >= 256 && appearanceValue < 512) {
					Model model_22_ = IdentityKit.cache[appearanceValue - 256].getBodyModel();
					if (model_22_ != null) {
						models[modelIndex++] = model_22_;
					}
				}
				if (appearanceValue >= 512) {
					Model model_23_ = ItemDefinition.getDefinition(appearanceValue - 512).getEquipModel(gender);
					if (model_23_ != null) {
						models[modelIndex++] = model_23_;
					}
				}
			}
			model = new Model(modelIndex, models);
			for (int i_24_ = 0; i_24_ < 5; i_24_++) {
				if (appearanceColors[i_24_] != 0) {
					model.recolor(Game.anIntArrayArray1028[i_24_][0],
							Game.anIntArrayArray1028[i_24_][appearanceColors[i_24_]]);
					if (i_24_ == 1) {
						model.recolor(Game.anIntArray1229[0], Game.anIntArray1229[appearanceColors[i_24_]]);
					}
				}
			}
			model.createBones();
			model.applyLighting(64, 850, -30, -50, -30, true);
			Player.modelCache.put(model, l);
			cachedModel = l;
		}
		if (aBoolean1719) {
			return model;
		}
		Model model_25_ = Model.aModel1614;
		model_25_.replaceWithModel(model, Animation.exists(i_12_) & Animation.exists(i_13_));
		if (i_12_ != -1 && i_13_ != -1) {
			model_25_.mixAnimationFrames(-20491, AnimationSequence.cache[animation].flowControl, i_13_, i_12_);
		} else if (i_12_ != -1) {
			model_25_.applyTransform(i_12_);
		}
		model_25_.calculateDiagonals();
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
			if (appearanceId >= 512 && !ItemDefinition.getDefinition(appearanceId - 512).isDialogueCached(gender)) {
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
				Model subModel = ItemDefinition.getDefinition(appearanceId - 512).getDialogueModel(gender);
				if (subModel != null) {
					headModels[headModelsOffset++] = subModel;
				}
			}
		}
		Model headModel = new Model(headModelsOffset, headModels);
		for (int index = 0; index < 5; index++) {
			if (appearanceColors[index] != 0) {
				headModel.recolor(Game.anIntArrayArray1028[index][0],
						Game.anIntArrayArray1028[index][appearanceColors[index]]);
				if (index == 1) {
					headModel.recolor(Game.anIntArray1229[0], Game.anIntArray1229[appearanceColors[index]]);
				}
			}
		}
		return headModel;
	}
}
