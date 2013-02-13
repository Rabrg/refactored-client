package com.runescape.media.renderable;

import com.runescape.collection.CacheableNode;
import com.runescape.media.VertexNormal;

public class Renderable extends CacheableNode {

	public VertexNormal[] verticesNormal;
	public int modelHeight = 1000;

	public void renderAtPoint(int i, int i_0_, int i_1_, int i_2_, int i_3_, int i_4_, int i_5_, int i_6_, int i_7_) {
		Model model = getRotatedModel();
		if (model != null) {
			modelHeight = model.modelHeight;
			model.renderAtPoint(i, i_0_, i_1_, i_2_, i_3_, i_4_, i_5_, i_6_, i_7_);
		}
	}

	public Model getRotatedModel() {
		return null;
	}
}
