package com.runescape.scene;

public class GenericTile {
	protected int anInt292;
	protected int anInt293;
	protected int anInt294;
	protected int anInt295;
	protected int texture;
	protected boolean flat = true;
	protected int rgbColor;

	public GenericTile(int i, int i_0_, int i_1_, int i_2_, int texture, int rgbColor, boolean flat) {
		this.anInt292 = i;
		this.anInt293 = i_0_;
		this.anInt294 = i_1_;
		this.anInt295 = i_2_;
		this.texture = texture;
		this.rgbColor = rgbColor;
		this.flat = flat;
	}
}
