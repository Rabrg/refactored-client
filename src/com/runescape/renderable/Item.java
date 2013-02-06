package com.runescape.renderable;

import com.runescape.cache.def.ItemDefinition;
import com.runescape.graphic.Model;

public class Item extends Renderable {

	public int itemId;
	public int itemCount;

	@Override
	public final Model getRotatedModel() {
		ItemDefinition itemdefinition = ItemDefinition.get(itemId);
		return itemdefinition.getAmountModel(itemCount);
	}
}
