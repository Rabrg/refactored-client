package com.runescape.graphic;

import com.runescape.Client;
import com.runescape.anim.Animation;
import com.runescape.cache.Archive;
import com.runescape.cache.def.ActorDefinition;
import com.runescape.cache.def.ItemDefinition;
import com.runescape.net.Buffer;
import com.runescape.node.Cache;
import com.runescape.util.TextUtils;

public class Widget {

	public ImageRGB disabledSprite;
	public int animationDuration;
	public ImageRGB[] sprites;
	public static Widget[] cache;
	public int unknownOne;
	public int[] conditionValues;
	public int contentType;
	public int[] spritesX;
	public int disabledHoveredColor;
	public int actionType;
	public String spellName;
	public int enabledColor;
	public int widgetWidth;
	public String tooltip;
	public String selectedActionName;
	public boolean typeFaceCentered;
	public int scrollPosition;
	public String[] actions;
	public int[][] widgetOpcodes;
	public boolean filled;
	public String enabledText;
	public int hoveredPopup;
	public int itemSpritePadsX;
	public int disabledColor;
	public int modelType;
	public int modelId;
	public boolean itemDeletesDraged;
	public int widgetParentId;
	public int spellUsableOn;
	private static Cache spriteCache;
	public int enabledHoveredColor;
	public int[] children;
	public int[] childrenX;
	public boolean itemUsable;
	public TypeFace typeFaces;
	public int itemSpritePadsY;
	public int[] conditionTypes;
	public int animationFrame;
	public int[] spritesY;
	public String disabledText;
	public boolean itemWidget;
	public int widgetId;
	public boolean unknownTwo;
	public int[] itemAmounts;
	public int[] items;
	public byte widgetAlpha;
	public int enabledModelType;
	public int enabledModelId;
	public int disabledAnimation;
	public int enabledAnimation;
	public boolean itemSwapable;
	public ImageRGB enabledSprite;
	public int scrollLimit;
	public int widgetType;
	public int widgetPositionX;
	static Cache modelCache = new Cache(30);
	public int widgetPositionY;
	public boolean hiddenUntilHovered;
	public int widgetHeight;
	public boolean typeFaceShadowed;
	public int zoom;
	public int rotationX;
	public int rotationY;
	public int[] childrenY;

	public void swapItems(int originalSlot, int newSlot) {
		int originalItem = items[originalSlot];
		items[originalSlot] = items[newSlot];
		items[newSlot] = originalItem;
		originalItem = itemAmounts[originalSlot];
		itemAmounts[originalSlot] = itemAmounts[newSlot];
		itemAmounts[newSlot] = originalItem;
	}

	public static void load(Archive widgetArchive, TypeFace[] typeFaces, Archive mediaArchive) {
		Widget.spriteCache = new Cache(50000);
		Buffer buffer = new Buffer(widgetArchive.getFile("data"));
		int parentId = -1;
		int widgetCount = buffer.getUnsignedLEShort();
		Widget.cache = new Widget[widgetCount];
		while (buffer.offset < buffer.payload.length) {
			int widgetId = buffer.getUnsignedLEShort();
			if (widgetId == 65535) {
				parentId = buffer.getUnsignedLEShort();
				widgetId = buffer.getUnsignedLEShort();
			}
			Widget widget = Widget.cache[widgetId] = new Widget();
			widget.widgetId = widgetId;
			widget.widgetParentId = parentId;
			widget.widgetType = buffer.getUnsignedByte();
			widget.actionType = buffer.getUnsignedByte();
			widget.contentType = buffer.getUnsignedLEShort();
			widget.widgetWidth = buffer.getUnsignedLEShort();
			widget.widgetHeight = buffer.getUnsignedLEShort();
			widget.widgetAlpha = (byte) buffer.getUnsignedByte();
			widget.hoveredPopup = buffer.getUnsignedByte();
			if (widget.hoveredPopup != 0) {
				widget.hoveredPopup = (widget.hoveredPopup - 1 << 8) + buffer.getUnsignedByte();
			} else {
				widget.hoveredPopup = -1;
			}
			int conditionCount = buffer.getUnsignedByte();
			if (conditionCount > 0) {
				widget.conditionTypes = new int[conditionCount];
				widget.conditionValues = new int[conditionCount];
				for (int condition = 0; condition < conditionCount; condition++) {
					widget.conditionTypes[condition] = buffer.getUnsignedByte();
					widget.conditionValues[condition] = buffer.getUnsignedLEShort();
				}
			}
			int opcodeCount = buffer.getUnsignedByte();
			if (opcodeCount > 0) {
				widget.widgetOpcodes = new int[opcodeCount][];
				for (int opcode = 0; opcode < opcodeCount; opcode++) {
					int subOpcodeCount = buffer.getUnsignedLEShort();
					widget.widgetOpcodes[opcode] = new int[subOpcodeCount];
					for (int subOpcode = 0; subOpcode < subOpcodeCount; subOpcode++) {
						widget.widgetOpcodes[opcode][subOpcode] = buffer.getUnsignedLEShort();
					}
				}
			}
			if (widget.widgetType == 0) {
				widget.scrollLimit = buffer.getUnsignedLEShort();
				widget.hiddenUntilHovered = buffer.getUnsignedByte() == 1;
				int childrenCount = buffer.getUnsignedLEShort();
				widget.children = new int[childrenCount];
				widget.childrenX = new int[childrenCount];
				widget.childrenY = new int[childrenCount];
				for (int child = 0; child < childrenCount; child++) {
					widget.children[child] = buffer.getUnsignedLEShort();
					widget.childrenX[child] = buffer.getShort();
					widget.childrenY[child] = buffer.getShort();
				}
			}
			if (widget.widgetType == 1) {
				widget.unknownOne = buffer.getUnsignedLEShort();
				widget.unknownTwo = buffer.getUnsignedByte() == 1;
			}
			if (widget.widgetType == 2) {
				widget.items = new int[widget.widgetWidth * widget.widgetHeight];
				widget.itemAmounts = new int[widget.widgetWidth * widget.widgetHeight];
				widget.itemSwapable = buffer.getUnsignedByte() == 1;
				widget.itemWidget = buffer.getUnsignedByte() == 1;
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
			if (widget.widgetType == 3) {
				widget.filled = buffer.getUnsignedByte() == 1;
			}
			if (widget.widgetType == 4 || widget.widgetType == 1) {
				widget.typeFaceCentered = buffer.getUnsignedByte() == 1;
				int typeFace = buffer.getUnsignedByte();
				if (typeFaces != null) {
					widget.typeFaces = typeFaces[typeFace];
				}
				widget.typeFaceShadowed = buffer.getUnsignedByte() == 1;
			}
			if (widget.widgetType == 4) {
				widget.disabledText = buffer.getString();
				widget.enabledText = buffer.getString();
			}
			if (widget.widgetType == 1 || widget.widgetType == 3 || widget.widgetType == 4) {
				widget.disabledColor = buffer.getInt();
			}
			if (widget.widgetType == 3 || widget.widgetType == 4) {
				widget.enabledColor = buffer.getInt();
				widget.disabledHoveredColor = buffer.getInt();
				widget.enabledHoveredColor = buffer.getInt();
			}
			if (widget.widgetType == 5) {
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
			if (widget.widgetType == 6) {
				widgetId = buffer.getUnsignedByte();
				if (widgetId != 0) {
					widget.modelType = 1;
					widget.modelId = (widgetId - 1 << 8) + buffer.getUnsignedByte();
				}
				widgetId = buffer.getUnsignedByte();
				if (widgetId != 0) {
					widget.enabledModelType = 1;
					widget.enabledModelId = (widgetId - 1 << 8) + buffer.getUnsignedByte();
				}
				widgetId = buffer.getUnsignedByte();
				if (widgetId != 0) {
					widget.disabledAnimation = (widgetId - 1 << 8) + buffer.getUnsignedByte();
				} else {
					widget.disabledAnimation = -1;
				}
				widgetId = buffer.getUnsignedByte();
				if (widgetId != 0) {
					widget.enabledAnimation = (widgetId - 1 << 8) + buffer.getUnsignedByte();
				} else {
					widget.enabledAnimation = -1;
				}
				widget.zoom = buffer.getUnsignedLEShort();
				widget.rotationX = buffer.getUnsignedLEShort();
				widget.rotationY = buffer.getUnsignedLEShort();
			}
			if (widget.widgetType == 7) {
				widget.items = new int[widget.widgetWidth * widget.widgetHeight];
				widget.itemAmounts = new int[widget.widgetWidth * widget.widgetHeight];
				widget.typeFaceCentered = buffer.getUnsignedByte() == 1;
				int typeFaceCount = buffer.getUnsignedByte();
				if (typeFaces != null) {
					widget.typeFaces = typeFaces[typeFaceCount];
				}
				widget.typeFaceShadowed = buffer.getUnsignedByte() == 1;
				widget.disabledColor = buffer.getInt();
				widget.itemSpritePadsX = buffer.getShort();
				widget.itemSpritePadsY = buffer.getShort();
				widget.itemWidget = buffer.getUnsignedByte() == 1;
				widget.actions = new String[5];
				for (int action = 0; action < 5; action++) {
					widget.actions[action] = buffer.getString();
					if (widget.actions[action].length() == 0) {
						widget.actions[action] = null;
					}
				}
			}
			if (widget.actionType == 2 || widget.widgetType == 2) {
				widget.selectedActionName = buffer.getString();
				widget.spellName = buffer.getString();
				widget.spellUsableOn = buffer.getUnsignedLEShort();
			}
			if (widget.widgetType == 8) {
				widget.disabledText = buffer.getString();
			}
			if (widget.actionType == 1 || widget.actionType == 4 || widget.actionType == 5 || widget.actionType == 6) {
				widget.tooltip = buffer.getString();
				if (widget.tooltip.length() == 0) {
					if (widget.actionType == 1) {
						widget.tooltip = "Ok";
					}
					if (widget.actionType == 4) {
						widget.tooltip = "Select";
					}
					if (widget.actionType == 5) {
						widget.tooltip = "Select";
					}
					if (widget.actionType == 6) {
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
			model = ActorDefinition.get(modelId).getHeadModel();
		}
		if (modelType == 3) {
			model = Client.clientsPlayer.getHeadModel();
		}
		if (modelType == 4) {
			model = ItemDefinition.get(modelId).getInventoryModel(50);
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

	public Model getAnimatedModel(int frame1Id, int frame2Id, boolean modelEnabled) {
		Model model;
		if (modelEnabled) {
			model = getModel(enabledModelType, enabledModelId);
		} else {
			model = getModel(modelType, modelId);
		}
		if (model == null) {
			return null;
		}
		if (frame2Id == -1 && frame1Id == -1 && model.triangleColorValues == null) {
			return model;
		}
		Model animatedModel = new Model(true, Animation.exists(frame2Id) & Animation.exists(frame1Id), false, model);
		if (frame2Id != -1 || frame1Id != -1) {
			animatedModel.createBones();
		}
		if (frame2Id != -1) {
			animatedModel.applyTransform(frame2Id);
		}
		if (frame1Id != -1) {
			animatedModel.applyTransform(frame1Id);
		}
		animatedModel.applyLighting(64, 768, -50, -10, -50, true);
		return animatedModel;
	}
}