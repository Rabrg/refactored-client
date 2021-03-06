package com.jagex.runescape.media.renderable;

import com.jagex.runescape.cache.def.ItemDefinition;

public class Item extends Renderable {

	public int itemId;
	public int itemCount;

	@Override
	public final Model getRotatedModel() {
		ItemDefinition itemdefinition = ItemDefinition.getDefinition(itemId);
		return itemdefinition.getAmountModel(itemCount);
	}
}
