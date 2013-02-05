package com.runescape.graphic;

import com.runescape.Client;
import com.runescape.anim.Animation;
import com.runescape.cache.Archive;
import com.runescape.cache.def.ItemDefinition;
import com.runescape.cache.def.NpcDefinition;
import com.runescape.net.Buffer;
import com.runescape.node.Cache;
import com.runescape.util.TextUtils;

public class Widget {

	public ImageRGB disabledSprite;
	public int animationDuration;
	public ImageRGB[] sprites;
	public static Widget[] widgets;
	public int interfaceUnusedOne;
	public int[] interfaceConditionValues;
	public int interfaceContentType;
	public int[] spritesX;
	public int disabledHoveredColor;
	public int interfaceActionType;
	public String spellName;
	public int enabledColor;
	public int interfaceWidth;
	public String tooltip;
	public String selectedActionName;
	public boolean typeFaceCentered;
	public int interfaceScrollPosition;
	public String[] actions;
	public int[][] widgetOpcodes;
	public boolean filled;
	public String enabledText;
	public int interfaceHoveredPopup;
	public int itemSpritePadsX;
	public int disabledColor;
	public int modelType;
	public int modelId;
	public boolean itemDeletesDraged;
	public int interfaceParentId;
	public int spellUsableOn;
	private static Cache spriteCache;
	public int enabledHoveredColor;
	public int[] interfaceChildren;
	public int[] interfaceChildrenX;
	public boolean itemUsable;
	public TypeFace typeFaces;
	public int itemSpritePadsY;
	public int[] interfaceConditionTypes;
	public int animationFrame;
	public int[] spritesY;
	public String disabledText;
	public boolean itemInterface;
	public int interfaceId;
	public boolean interfaceUnusedTwo;
	public int[] itemAmounts;
	public int[] items;
	public byte interfaceAlpha;
	public int enabledModelType;
	public int enabledModelId;
	public int disabledAnimation;
	public int enabledAnimation;
	public boolean itemSwapable;
	public ImageRGB enabledSprite;
	public int interfaceScrollLimit;
	public int interfaceType;
	public int interfacePositionX;
	static Cache modelCache = new Cache(30);
	public int interfacePositionY;
	public boolean hiddenUntilHovered;
	public int interfaceHeight;
	public boolean typeFaceShadowed;
	public int zoom;
	public int rotationX;
	public int rotationY;
	public int[] interfaceChildrenY;

	public void swapItems(int originalSlot, int newSlot) {
		int originalItem = items[originalSlot];
		items[originalSlot] = items[newSlot];
		items[newSlot] = originalItem;
		originalItem = itemAmounts[originalSlot];
		itemAmounts[originalSlot] = itemAmounts[newSlot];
		itemAmounts[newSlot] = originalItem;
	}

	public static void load(Archive interfaceArchive, TypeFace[] typeFaces, Archive mediaArchive) {
		Widget.spriteCache = new Cache(50000);
		Buffer buffer = new Buffer(interfaceArchive.getFile("data"));
		int parentId = -1;
		int interfaceCount = buffer.getUnsignedLEShort();
		Widget.widgets = new Widget[interfaceCount];
		while (buffer.offset < buffer.payload.length) {
			int interfaceId = buffer.getUnsignedLEShort();
			if (interfaceId == 65535) {
				parentId = buffer.getUnsignedLEShort();
				interfaceId = buffer.getUnsignedLEShort();
			}
			Widget widget = Widget.widgets[interfaceId] = new Widget();
			widget.interfaceId = interfaceId;
			widget.interfaceParentId = parentId;
			widget.interfaceType = buffer.getUnsignedByte();
			widget.interfaceActionType = buffer.getUnsignedByte();
			widget.interfaceContentType = buffer.getUnsignedLEShort();
			widget.interfaceWidth = buffer.getUnsignedLEShort();
			widget.interfaceHeight = buffer.getUnsignedLEShort();
			widget.interfaceAlpha = (byte) buffer.getUnsignedByte();
			widget.interfaceHoveredPopup = buffer.getUnsignedByte();
			if (widget.interfaceHoveredPopup != 0) {
				widget.interfaceHoveredPopup = (widget.interfaceHoveredPopup - 1 << 8) + buffer.getUnsignedByte();
			} else {
				widget.interfaceHoveredPopup = -1;
			}
			int conditionCount = buffer.getUnsignedByte();
			if (conditionCount > 0) {
				widget.interfaceConditionTypes = new int[conditionCount];
				widget.interfaceConditionValues = new int[conditionCount];
				for (int interfaceCondition = 0; interfaceCondition < conditionCount; interfaceCondition++) {
					widget.interfaceConditionTypes[interfaceCondition] = buffer.getUnsignedByte();
					widget.interfaceConditionValues[interfaceCondition] = buffer.getUnsignedLEShort();
				}
			}
			int formulaCount = buffer.getUnsignedByte();
			if (formulaCount > 0) {
				widget.widgetOpcodes = new int[formulaCount][];
				for (int interfaceFormula = 0; interfaceFormula < formulaCount; interfaceFormula++) {
					int interfaceSubFormulaCount = buffer.getUnsignedLEShort();
					widget.widgetOpcodes[interfaceFormula] = new int[interfaceSubFormulaCount];
					for (int interfaceSubFormula = 0; interfaceSubFormula < interfaceSubFormulaCount; interfaceSubFormula++) {
						widget.widgetOpcodes[interfaceFormula][interfaceSubFormula] = buffer.getUnsignedLEShort();
					}
				}
			}
			if (widget.interfaceType == 0) {
				widget.interfaceScrollLimit = buffer.getUnsignedLEShort();
				widget.hiddenUntilHovered = buffer.getUnsignedByte() == 1;
				int childrenCount = buffer.getUnsignedLEShort();
				widget.interfaceChildren = new int[childrenCount];
				widget.interfaceChildrenX = new int[childrenCount];
				widget.interfaceChildrenY = new int[childrenCount];
				for (int interfaceChild = 0; interfaceChild < childrenCount; interfaceChild++) {
					widget.interfaceChildren[interfaceChild] = buffer.getUnsignedLEShort();
					widget.interfaceChildrenX[interfaceChild] = buffer.getShort();
					widget.interfaceChildrenY[interfaceChild] = buffer.getShort();
				}
			}
			if (widget.interfaceType == 1) {
				widget.interfaceUnusedOne = buffer.getUnsignedLEShort();
				widget.interfaceUnusedTwo = buffer.getUnsignedByte() == 1;
			}
			if (widget.interfaceType == 2) {
				widget.items = new int[widget.interfaceWidth * widget.interfaceHeight];
				widget.itemAmounts = new int[widget.interfaceWidth * widget.interfaceHeight];
				widget.itemSwapable = buffer.getUnsignedByte() == 1;
				widget.itemInterface = buffer.getUnsignedByte() == 1;
				widget.itemUsable = buffer.getUnsignedByte() == 1;
				widget.itemDeletesDraged = buffer.getUnsignedByte() == 1;
				widget.itemSpritePadsX = buffer.getUnsignedByte();
				widget.itemSpritePadsY = buffer.getUnsignedByte();
				widget.spritesX = new int[20];
				widget.spritesY = new int[20];
				widget.sprites = new ImageRGB[20];
				for (int sprite = 0; sprite < 20; sprite++) {
					int hasSprite = buffer.getUnsignedByte();
					if (hasSprite == 1) {
						widget.spritesX[sprite] = buffer.getShort();
						widget.spritesY[sprite] = buffer.getShort();
						String spriteName = buffer.getString();
						if (mediaArchive != null && spriteName.length() > 0) {
							int spriteId = spriteName.lastIndexOf(",");
							widget.sprites[sprite] = Widget.getSprite(
									Integer.parseInt(spriteName.substring(spriteId + 1)), mediaArchive,
									spriteName.substring(0, spriteId));
						}
					}
				}
				widget.actions = new String[5];
				for (int action = 0; action < 5; action++) {
					widget.actions[action] = buffer.getString();
					if (widget.actions[action].length() == 0) {
						widget.actions[action] = null;
					}
				}
			}
			if (widget.interfaceType == 3) {
				widget.filled = buffer.getUnsignedByte() == 1;
			}
			if (widget.interfaceType == 4 || widget.interfaceType == 1) {
				widget.typeFaceCentered = buffer.getUnsignedByte() == 1;
				int typeFace = buffer.getUnsignedByte();
				if (typeFaces != null) {
					widget.typeFaces = typeFaces[typeFace];
				}
				widget.typeFaceShadowed = buffer.getUnsignedByte() == 1;
			}
			if (widget.interfaceType == 4) {
				widget.disabledText = buffer.getString();
				widget.enabledText = buffer.getString();
			}
			if (widget.interfaceType == 1 || widget.interfaceType == 3 || widget.interfaceType == 4) {
				widget.disabledColor = buffer.getInt();
			}
			if (widget.interfaceType == 3 || widget.interfaceType == 4) {
				widget.enabledColor = buffer.getInt();
				widget.disabledHoveredColor = buffer.getInt();
				widget.enabledHoveredColor = buffer.getInt();
			}
			if (widget.interfaceType == 5) {
				String spriteName = buffer.getString();
				if (mediaArchive != null && spriteName.length() > 0) {
					int spriteId = spriteName.lastIndexOf(",");
					widget.disabledSprite = Widget.getSprite(Integer.parseInt(spriteName.substring(spriteId + 1)),
							mediaArchive, spriteName.substring(0, spriteId));
				}
				spriteName = buffer.getString();
				if (mediaArchive != null && spriteName.length() > 0) {
					int spriteId = spriteName.lastIndexOf(",");
					widget.enabledSprite = Widget.getSprite(Integer.parseInt(spriteName.substring(spriteId + 1)),
							mediaArchive, spriteName.substring(0, spriteId));
				}
			}
			if (widget.interfaceType == 6) {
				interfaceId = buffer.getUnsignedByte();
				if (interfaceId != 0) {
					widget.modelType = 1;
					widget.modelId = (interfaceId - 1 << 8) + buffer.getUnsignedByte();
				}
				interfaceId = buffer.getUnsignedByte();
				if (interfaceId != 0) {
					widget.enabledModelType = 1;
					widget.enabledModelId = (interfaceId - 1 << 8) + buffer.getUnsignedByte();
				}
				interfaceId = buffer.getUnsignedByte();
				if (interfaceId != 0) {
					widget.disabledAnimation = (interfaceId - 1 << 8) + buffer.getUnsignedByte();
				} else {
					widget.disabledAnimation = -1;
				}
				interfaceId = buffer.getUnsignedByte();
				if (interfaceId != 0) {
					widget.enabledAnimation = (interfaceId - 1 << 8) + buffer.getUnsignedByte();
				} else {
					widget.enabledAnimation = -1;
				}
				widget.zoom = buffer.getUnsignedLEShort();
				widget.rotationX = buffer.getUnsignedLEShort();
				widget.rotationY = buffer.getUnsignedLEShort();
			}
			if (widget.interfaceType == 7) {
				widget.items = new int[widget.interfaceWidth * widget.interfaceHeight];
				widget.itemAmounts = new int[widget.interfaceWidth * widget.interfaceHeight];
				widget.typeFaceCentered = buffer.getUnsignedByte() == 1;
				int typeFaceCount = buffer.getUnsignedByte();
				if (typeFaces != null) {
					widget.typeFaces = typeFaces[typeFaceCount];
				}
				widget.typeFaceShadowed = buffer.getUnsignedByte() == 1;
				widget.disabledColor = buffer.getInt();
				widget.itemSpritePadsX = buffer.getShort();
				widget.itemSpritePadsY = buffer.getShort();
				widget.itemInterface = buffer.getUnsignedByte() == 1;
				widget.actions = new String[5];
				for (int action = 0; action < 5; action++) {
					widget.actions[action] = buffer.getString();
					if (widget.actions[action].length() == 0) {
						widget.actions[action] = null;
					}
				}
			}
			if (widget.interfaceActionType == 2 || widget.interfaceType == 2) {
				widget.selectedActionName = buffer.getString();
				widget.spellName = buffer.getString();
				widget.spellUsableOn = buffer.getUnsignedLEShort();
			}
			if (widget.interfaceType == 8) {
				widget.disabledText = buffer.getString();
			}
			if (widget.interfaceActionType == 1 || widget.interfaceActionType == 4 || widget.interfaceActionType == 5
					|| widget.interfaceActionType == 6) {
				widget.tooltip = buffer.getString();
				if (widget.tooltip.length() == 0) {
					if (widget.interfaceActionType == 1) {
						widget.tooltip = "Ok";
					}
					if (widget.interfaceActionType == 4) {
						widget.tooltip = "Select";
					}
					if (widget.interfaceActionType == 5) {
						widget.tooltip = "Select";
					}
					if (widget.interfaceActionType == 6) {
						widget.tooltip = "Continue";
					}
				}
			}
		}
		Widget.spriteCache = null;
	}

	private Model getModel(int modelType, int modelId) {
		Model model = (Model) Widget.modelCache.get((modelType << 16) + modelId);
		if (model != null) {
			return model;
		}
		if (modelType == 1) {
			model = Model.getModel(modelId);
		}
		if (modelType == 2) {
			model = NpcDefinition.getDefinition(modelId).getModel(true);
		}
		if (modelType == 3) {
			model = Client.clientsPlayer.getHeadModel();
		}
		if (modelType == 4) {
			model = ItemDefinition.getDefinition(modelId).getModel(50, true);
		}
		if (modelType == 5) {
			model = null;
		}
		if (model != null) {
			Widget.modelCache.put(model, (modelType << 16) + modelId);
		}
		return model;
	}

	private static ImageRGB getSprite(int spriteId, Archive mediaArchive, String spriteName) {
		long spriteHash = (TextUtils.spriteToHash(spriteName) << 8) + spriteId;
		ImageRGB sprite = (ImageRGB) Widget.spriteCache.get(spriteHash);
		if (sprite != null) {
			return sprite;
		}
		try {
			sprite = new ImageRGB(mediaArchive, spriteName, spriteId);
			Widget.spriteCache.put(sprite, spriteHash);
		} catch (Exception exception) {
			return null;
		}
		return sprite;
	}

	public static void setModel(int modelId, int modelType, Model model) {
		Widget.modelCache.removeAll();
		Widget.modelCache.put(model, (modelType << 16) + modelId);
	}

	public Model getAnimatedModel(int frame1Id, int fram2Id, boolean modelEnabled) {
		Model model;
		if (modelEnabled) {
			model = getModel(enabledModelType, enabledModelId);
		} else {
			model = getModel(modelType, modelId);
		}
		if (model == null) {
			return null;
		}
		if (fram2Id == -1 && frame1Id == -1 && model.triangleColorValues == null) {
			return model;
		}
		Model animatedModel = new Model(true, Animation.exists(fram2Id) & Animation.exists(frame1Id), false, model);
		if (fram2Id != -1 || frame1Id != -1) {
			animatedModel.createBones();
		}
		if (fram2Id != -1) {
			animatedModel.applyTransform(fram2Id);
		}
		if (frame1Id != -1) {
			animatedModel.applyTransform(frame1Id);
		}
		animatedModel.applyLighting(64, 768, -50, -10, -50, true);
		return animatedModel;
	}
}