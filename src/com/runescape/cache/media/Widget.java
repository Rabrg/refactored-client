package com.runescape.cache.media;

import com.runescape.Game;
import com.runescape.cache.Archive;
import com.runescape.cache.def.ActorDefinition;
import com.runescape.cache.def.ItemDefinition;
import com.runescape.collection.Cache;
import com.runescape.media.Animation;
import com.runescape.media.renderable.Model;
import com.runescape.net.Buffer;
import com.runescape.util.TextUtils;

public class Widget {

	public ImageRGB disabledImage;
	public int animationDuration;
	public ImageRGB[] images;
	public static Widget[] cache;
	public int unknownOne;
	public int[] conditionValues;
	public int contentType;
	public int[] imageX;
	public int disabledHoveredColor;
	public int actionType;
	public String spellName;
	public int enabledColor;
	public int width;
	public String tooltip;
	public String selectedActionName;
	public boolean typeFaceCentered;
	public int scrollPosition;
	public String[] actions;
	public int[][] opcodes;
	public boolean filled;
	public String enabledText;
	public int hoveredPopup;
	public int itemSpritePadsX;
	public int disabledColor;
	public int modelType;
	public int modelId;
	public boolean itemDeletesDraged;
	public int parentId;
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
	public int[] imageY;
	public String disabledText;
	public boolean isInventory;
	public int id;
	public boolean unknownTwo;
	public int[] itemAmounts;
	public int[] items;
	public byte alpha;
	public int enabledModelType;
	public int enabledModelId;
	public int disabledAnimation;
	public int enabledAnimation;
	public boolean itemSwapable;
	public ImageRGB enabledImage;
	public int scrollLimit;
	public int type;
	public int x;
	static Cache modelCache = new Cache(30);
	public int y;
	public boolean hiddenUntilHovered;
	public int height;
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

	public static void load(Archive widgetArchive, TypeFace[] fonts, Archive mediaArchive) {
		Widget.spriteCache = new Cache(50000);
		Buffer buffer = new Buffer(widgetArchive.getFile("data"));
		int parentId = -1;
		int widgetCount = buffer.getUnsignedLEShort();
		Widget.cache = new Widget[widgetCount];
		while (buffer.offset < buffer.payload.length) {
			int widgetIndex = buffer.getUnsignedLEShort();
			if (widgetIndex == 65535) {
				parentId = buffer.getUnsignedLEShort();
				widgetIndex = buffer.getUnsignedLEShort();
			}
			Widget widget = Widget.cache[widgetIndex] = new Widget();
			widget.id = widgetIndex;
			widget.parentId = parentId;
			widget.type = buffer.getUnsignedByte();
			widget.actionType = buffer.getUnsignedByte();
			widget.contentType = buffer.getUnsignedLEShort();
			widget.width = buffer.getUnsignedLEShort();
			widget.height = buffer.getUnsignedLEShort();
			widget.alpha = (byte) buffer.getUnsignedByte();
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
				widget.opcodes = new int[opcodeCount][];
				for (int opcode = 0; opcode < opcodeCount; opcode++) {
					int subOpcodeCount = buffer.getUnsignedLEShort();
					widget.opcodes[opcode] = new int[subOpcodeCount];
					for (int subOpcode = 0; subOpcode < subOpcodeCount; subOpcode++) {
						widget.opcodes[opcode][subOpcode] = buffer.getUnsignedLEShort();
					}
				}
			}
			if (widget.type == 0) {
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
			if (widget.type == 1) {
				widget.unknownOne = buffer.getUnsignedLEShort();
				widget.unknownTwo = buffer.getUnsignedByte() == 1;
			}
			if (widget.type == 2) {
				widget.items = new int[widget.width * widget.height];
				widget.itemAmounts = new int[widget.width * widget.height];
				widget.itemSwapable = buffer.getUnsignedByte() == 1;
				widget.isInventory = buffer.getUnsignedByte() == 1;
				widget.itemUsable = buffer.getUnsignedByte() == 1;
				widget.itemDeletesDraged = buffer.getUnsignedByte() == 1;
				widget.itemSpritePadsX = buffer.getUnsignedByte();
				widget.itemSpritePadsY = buffer.getUnsignedByte();
				widget.imageX = new int[20];
				widget.imageY = new int[20];
				widget.images = new ImageRGB[20];
				for (int sprite = 0; sprite < 20; sprite++) {
					int hasSprite = buffer.getUnsignedByte();
					if (hasSprite == 1) {
						widget.imageX[sprite] = buffer.getShort();
						widget.imageY[sprite] = buffer.getShort();
						String spriteName = buffer.getString();
						if (mediaArchive != null && spriteName.length() > 0) {
							int spriteId = spriteName.lastIndexOf(",");
							widget.images[sprite] = Widget.getImage(
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
			if (widget.type == 3) {
				widget.filled = buffer.getUnsignedByte() == 1;
			}
			if (widget.type == 4 || widget.type == 1) {
				widget.typeFaceCentered = buffer.getUnsignedByte() == 1;
				int typeFace = buffer.getUnsignedByte();
				if (fonts != null) {
					widget.typeFaces = fonts[typeFace];
				}
				widget.typeFaceShadowed = buffer.getUnsignedByte() == 1;
			}
			if (widget.type == 4) {
				widget.disabledText = buffer.getString();
				widget.enabledText = buffer.getString();
			}
			if (widget.type == 1 || widget.type == 3 || widget.type == 4) {
				widget.disabledColor = buffer.getInt();
			}
			if (widget.type == 3 || widget.type == 4) {
				widget.enabledColor = buffer.getInt();
				widget.disabledHoveredColor = buffer.getInt();
				widget.enabledHoveredColor = buffer.getInt();
			}
			if (widget.type == 5) {
				String spriteName = buffer.getString();
				if (mediaArchive != null && spriteName.length() > 0) {
					int spriteId = spriteName.lastIndexOf(",");
					widget.disabledImage = Widget.getImage(Integer.parseInt(spriteName.substring(spriteId + 1)),
							mediaArchive, spriteName.substring(0, spriteId));
				}
				spriteName = buffer.getString();
				if (mediaArchive != null && spriteName.length() > 0) {
					int spriteId = spriteName.lastIndexOf(",");
					widget.enabledImage = Widget.getImage(Integer.parseInt(spriteName.substring(spriteId + 1)),
							mediaArchive, spriteName.substring(0, spriteId));
				}
			}
			if (widget.type == 6) {
				widgetIndex = buffer.getUnsignedByte();
				if (widgetIndex != 0) {
					widget.modelType = 1;
					widget.modelId = (widgetIndex - 1 << 8) + buffer.getUnsignedByte();
				}
				widgetIndex = buffer.getUnsignedByte();
				if (widgetIndex != 0) {
					widget.enabledModelType = 1;
					widget.enabledModelId = (widgetIndex - 1 << 8) + buffer.getUnsignedByte();
				}
				widgetIndex = buffer.getUnsignedByte();
				if (widgetIndex != 0) {
					widget.disabledAnimation = (widgetIndex - 1 << 8) + buffer.getUnsignedByte();
				} else {
					widget.disabledAnimation = -1;
				}
				widgetIndex = buffer.getUnsignedByte();
				if (widgetIndex != 0) {
					widget.enabledAnimation = (widgetIndex - 1 << 8) + buffer.getUnsignedByte();
				} else {
					widget.enabledAnimation = -1;
				}
				widget.zoom = buffer.getUnsignedLEShort();
				widget.rotationX = buffer.getUnsignedLEShort();
				widget.rotationY = buffer.getUnsignedLEShort();
			}
			if (widget.type == 7) {
				widget.items = new int[widget.width * widget.height];
				widget.itemAmounts = new int[widget.width * widget.height];
				widget.typeFaceCentered = buffer.getUnsignedByte() == 1;
				int typeFaceCount = buffer.getUnsignedByte();
				if (fonts != null) {
					widget.typeFaces = fonts[typeFaceCount];
				}
				widget.typeFaceShadowed = buffer.getUnsignedByte() == 1;
				widget.disabledColor = buffer.getInt();
				widget.itemSpritePadsX = buffer.getShort();
				widget.itemSpritePadsY = buffer.getShort();
				widget.isInventory = buffer.getUnsignedByte() == 1;
				widget.actions = new String[5];
				for (int action = 0; action < 5; action++) {
					widget.actions[action] = buffer.getString();
					if (widget.actions[action].length() == 0) {
						widget.actions[action] = null;
					}
				}
			}
			if (widget.actionType == 2 || widget.type == 2) {
				widget.selectedActionName = buffer.getString();
				widget.spellName = buffer.getString();
				widget.spellUsableOn = buffer.getUnsignedLEShort();
			}
			if (widget.type == 8) {
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
			model = ActorDefinition.getDefinition(modelId).getHeadModel();
		}
		if (modelType == 3) {
			model = Game.localPlayer.getHeadModel();
		}
		if (modelType == 4) {
			model = ItemDefinition.getDefinition(modelId).getInventoryModel(50);
		}
		if (modelType == 5) {
			model = null;
		}
		if (model != null) {
			Widget.modelCache.put(model, (modelType << 16) + modelId);
		}
		return model;
	}

	private static ImageRGB getImage(int spriteId, Archive mediaArchive, String spriteName) {
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