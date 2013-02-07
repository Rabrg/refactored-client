package com.runescape.cache.def;

import com.runescape.cache.Archive;
import com.runescape.net.Buffer;

public class FloorDefinition {

	public static int count;
	public static FloorDefinition[] cache;
	public String name;
	public int rgbColor;
	public int textureId = -1;
	public boolean aBoolean227 = false;
	public boolean occlude = true;
	public int hue2;
	public int saturation;
	public int lightness;
	public int hue;
	public int hueDivisor;
	public int hslColor2;

	public static void load(Archive archive) {
		Buffer buffer = new Buffer(archive.getFile("flo.dat"));
		FloorDefinition.count = buffer.getUnsignedLEShort();
		if (FloorDefinition.cache == null) {
			FloorDefinition.cache = new FloorDefinition[FloorDefinition.count];
		}
		for (int floor = 0; floor < FloorDefinition.count; floor++) {
			if (FloorDefinition.cache[floor] == null) {
				FloorDefinition.cache[floor] = new FloorDefinition();
			}
			FloorDefinition.cache[floor].loadDefinition(true, buffer);
		}
	}

	public void loadDefinition(boolean bool, Buffer buffer) {
		while (true) {
			int attributeId = buffer.getUnsignedByte();
			if (attributeId == 0) {
				break;
			}
			if (attributeId == 1) {
				rgbColor = buffer.get24BitInt();
				shiftRGBColors(rgbColor);
			} else if (attributeId == 2) {
				textureId = buffer.getUnsignedByte();
			} else if (attributeId == 3) {
				aBoolean227 = true;
			} else if (attributeId == 5) {
				occlude = false;
			} else if (attributeId == 6) {
				name = buffer.getString();
			} else if (attributeId == 7) {
				int oldHue2 = hue2;
				int oldSaturation = saturation;
				int oldLightness = lightness;
				int oldHue = hue;
				shiftRGBColors(buffer.get24BitInt());
				hue2 = oldHue2;
				saturation = oldSaturation;
				lightness = oldLightness;
				hue = oldHue;
				hueDivisor = oldHue;
			} else {
				System.out.println("Error unrecognised config code: " + attributeId);
			}
		}
	}

	private void shiftRGBColors(int color) {
		double r = (color >> 16 & 0xff) / 256.0;
		double b = (color >> 8 & 0xff) / 256.0;
		double g = (color & 0xff) / 256.0;
		double cmin = r;
		if (b < cmin) {
			cmin = b;
		}
		if (g < cmin) {
			cmin = g;
		}
		double cmax = r;
		if (b > cmax) {
			cmax = b;
		}
		if (g > cmax) {
			cmax = g;
		}
		double d_11_ = 0.0;
		double d_12_ = 0.0;
		double d_13_ = (cmin + cmax) / 2.0;
		if (cmin != cmax) {
			if (d_13_ < 0.5) {
				d_12_ = (cmax - cmin) / (cmax + cmin);
			}
			if (d_13_ >= 0.5) {
				d_12_ = (cmax - cmin) / (2.0 - cmax - cmin);
			}
			if (r == cmax) {
				d_11_ = (b - g) / (cmax - cmin);
			} else if (b == cmax) {
				d_11_ = 2.0 + (g - r) / (cmax - cmin);
			} else if (g == cmax) {
				d_11_ = 4.0 + (r - b) / (cmax - cmin);
			}
		}
		d_11_ /= 6.0;
		hue2 = (int) (d_11_ * 256.0);
		saturation = (int) (d_12_ * 256.0);
		lightness = (int) (d_13_ * 256.0);
		if (saturation < 0) {
			saturation = 0;
		} else if (saturation > 255) {
			saturation = 255;
		}
		if (lightness < 0) {
			lightness = 0;
		} else if (lightness > 255) {
			lightness = 255;
		}
		if (d_13_ > 0.5) {
			hueDivisor = (int) ((1.0 - d_13_) * d_12_ * 512.0);
		} else {
			hueDivisor = (int) (d_13_ * d_12_ * 512.0);
		}
		if (hueDivisor < 1) {
			hueDivisor = 1;
		}
		hue = (int) (d_11_ * hueDivisor);
		int huerand = hue2 + (int) (Math.random() * 16.0) - 8;
		if (huerand < 0) {
			huerand = 0;
		} else if (huerand > 255) {
			huerand = 255;
		}
		int satrand = saturation + (int) (Math.random() * 48.0) - 24;
		if (satrand < 0) {
			satrand = 0;
		} else if (satrand > 255) {
			satrand = 255;
		}
		int lightrand = lightness + (int) (Math.random() * 48.0) - 24;
		if (lightrand < 0) {
			lightrand = 0;
		} else if (lightrand > 255) {
			lightrand = 255;
		}
		hslColor2 = shiftHSLColors(huerand, satrand, lightrand);
	}

	private final int shiftHSLColors(int i, int i_17_, int i_18_) {
		if (i_18_ > 179) {
			i_17_ /= 2;
		}
		if (i_18_ > 192) {
			i_17_ /= 2;
		}
		if (i_18_ > 217) {
			i_17_ /= 2;
		}
		if (i_18_ > 243) {
			i_17_ /= 2;
		}
		int i_19_ = (i / 4 << 10) + (i_17_ / 32 << 7) + i_18_ / 2;
		return i_19_;
	}
}
