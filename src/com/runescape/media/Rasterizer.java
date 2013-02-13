package com.runescape.media;

import com.runescape.collection.CacheableNode;

public class Rasterizer extends CacheableNode {

	public static int[] pixels;
	public static int width;
	public static int height;
	public static int topY;
	public static int bottomY;
	public static int topX;
	public static int bottomX;
	public static int virtualBottomX;
	public static int centerX;
	public static int centerY;

	public static void createRasterizer(int[] pixels, int width, int height) {
		Rasterizer.pixels = pixels;
		Rasterizer.width = width;
		Rasterizer.height = height;
		Rasterizer.setCoordinates(0, 0, width, height);
	}

	public static void resetCoordinates() {
		Rasterizer.topX = 0;
		Rasterizer.topY = 0;
		Rasterizer.bottomX = Rasterizer.width;
		Rasterizer.bottomY = Rasterizer.height;
		Rasterizer.virtualBottomX = Rasterizer.bottomX;
		Rasterizer.centerX = Rasterizer.bottomX / 2;
	}

	public static void setCoordinates(int x, int y, int width, int height) {
		if (x < 0) {
			x = 0;
		}
		if (y < 0) {
			y = 0;
		}
		if (width > Rasterizer.width) {
			width = Rasterizer.width;
		}
		if (height > Rasterizer.height) {
			height = Rasterizer.height;
		}
		Rasterizer.topX = x;
		Rasterizer.topY = y;
		Rasterizer.bottomX = width;
		Rasterizer.bottomY = height;
		Rasterizer.virtualBottomX = Rasterizer.bottomX;
		Rasterizer.centerX = Rasterizer.bottomX / 2;
		Rasterizer.centerY = Rasterizer.bottomY / 2;
	}

	public static void resetPixels() {
		int pixelCount = Rasterizer.width * Rasterizer.height;
		for (int pixel = 0; pixel < pixelCount; pixel++) {
			Rasterizer.pixels[pixel] = 0;
		}
	}

	public static void drawFilledRectangleAlhpa(int x, int y, int width, int height, int color, int alpha) {
		if (x < Rasterizer.topX) {
			width -= Rasterizer.topX - x;
			x = Rasterizer.topX;
		}
		if (y < Rasterizer.topY) {
			height -= Rasterizer.topY - y;
			y = Rasterizer.topY;
		}
		if (x + width > Rasterizer.bottomX) {
			width = Rasterizer.bottomX - x;
		}
		if (y + height > Rasterizer.bottomY) {
			height = Rasterizer.bottomY - y;
		}
		int a = 256 - alpha;
		int r = (color >> 16 & 0xff) * alpha;
		int g = (color >> 8 & 0xff) * alpha;
		int b = (color & 0xff) * alpha;
		int widthOffset = Rasterizer.width - width;
		int pixel = x + y * Rasterizer.width;
		for (int heightCounter = 0; heightCounter < height; heightCounter++) {
			for (int widthCounter = -width; widthCounter < 0; widthCounter++) {
				int red = (Rasterizer.pixels[pixel] >> 16 & 0xff) * a;
				int green = (Rasterizer.pixels[pixel] >> 8 & 0xff) * a;
				int blue = (Rasterizer.pixels[pixel] & 0xff) * a;
				int rgba = (r + red >> 8 << 16) + (g + green >> 8 << 8) + (b + blue >> 8);
				Rasterizer.pixels[pixel++] = rgba;
			}
			pixel += widthOffset;
		}
	}

	public static void drawFilledRectangle(int x, int y, int width, int height, int color) {
		if (x < Rasterizer.topX) {
			width -= Rasterizer.topX - x;
			x = Rasterizer.topX;
		}
		if (y < Rasterizer.topY) {
			height -= Rasterizer.topY - y;
			y = Rasterizer.topY;
		}
		if (x + width > Rasterizer.bottomX) {
			width = Rasterizer.bottomX - x;
		}
		if (y + height > Rasterizer.bottomY) {
			height = Rasterizer.bottomY - y;
		}
		int pixelOffset = Rasterizer.width - width;
		int pixel = x + y * Rasterizer.width;
		for (int heightCounter = -height; heightCounter < 0; heightCounter++) {
			for (int widthCounter = -width; widthCounter < 0; widthCounter++) {
				Rasterizer.pixels[pixel++] = color;
			}
			pixel += pixelOffset;
		}
	}

	public static void drawUnfilledRectangle(int x, int y, int width, int height, int color) {
		Rasterizer.drawHorizontalLine(x, y, width, color);
		Rasterizer.drawHorizontalLine(x, y + height - 1, width, color);
		Rasterizer.drawVerticalLine(x, y, height, color);
		Rasterizer.drawVerticalLine(x + width - 1, y, height, color);
	}

	public static void drawUnfilledRectangleAlpha(int x, int y, int width, int height, int color, int alpha) {
		Rasterizer.drawHorizontalLineAlpha(x, y, width, color, alpha);
		Rasterizer.drawHorizontalLineAlpha(x, y + height - 1, width, color, alpha);
		if (height < 3) {
			Rasterizer.drawVerticalLineAlpha(x, y + 1, height - 2, color, alpha);
			Rasterizer.drawVerticalLineAlpha(x + width - 1, y + 1, height - 2, color, alpha);
		}
	}

	public static void drawHorizontalLine(int x, int y, int length, int color) {
		if (y >= Rasterizer.topY && y < Rasterizer.bottomY) {
			if (x < Rasterizer.topX) {
				length -= Rasterizer.topX - x;
				x = Rasterizer.topX;
			}
			if (x + length > Rasterizer.bottomX) {
				length = Rasterizer.bottomX - x;
			}
			int pixelOffset = x + y * Rasterizer.width;
			for (int pixel = 0; pixel < length; pixel++) {
				Rasterizer.pixels[pixelOffset + pixel] = color;
			}
		}
	}

	public static void drawHorizontalLineAlpha(int x, int y, int length, int color, int alpha) {
		if (y >= Rasterizer.topY && y < Rasterizer.bottomY) {
			if (x < Rasterizer.topX) {
				length -= Rasterizer.topX - x;
				x = Rasterizer.topX;
			}
			if (x + length > Rasterizer.bottomX) {
				length = Rasterizer.bottomX - x;
			}
			int a = 256 - alpha;
			int r = (color >> 16 & 0xff) * alpha;
			int g = (color >> 8 & 0xff) * alpha;
			int b = (color & 0xff) * alpha;
			int pixelOffset = x + y * Rasterizer.width;
			for (int lengthCounter = 0; lengthCounter < length; lengthCounter++) {
				int red = (Rasterizer.pixels[pixelOffset] >> 16 & 0xff) * a;
				int green = (Rasterizer.pixels[pixelOffset] >> 8 & 0xff) * a;
				int blue = (Rasterizer.pixels[pixelOffset] & 0xff) * a;
				int rgba = (r + red >> 8 << 16) + (g + green >> 8 << 8) + (b + blue >> 8);
				Rasterizer.pixels[pixelOffset++] = rgba;
			}
		}
	}

	public static void drawVerticalLine(int x, int y, int length, int color) {
		if (x >= Rasterizer.topX && x < Rasterizer.bottomX) {
			if (y < Rasterizer.topY) {
				length -= Rasterizer.topY - y;
				y = Rasterizer.topY;
			}
			if (y + length > Rasterizer.bottomY) {
				length = Rasterizer.bottomY - y;
			}
			int pixelOffset = x + y * Rasterizer.width;
			for (int pixel = 0; pixel < length; pixel++) {
				Rasterizer.pixels[pixelOffset + pixel * Rasterizer.width] = color;
			}
		}
	}

	public static void drawVerticalLineAlpha(int x, int y, int length, int color, int alpha) {
		if (x >= Rasterizer.topX && x < Rasterizer.bottomX) {
			if (y < Rasterizer.topY) {
				length -= Rasterizer.topY - y;
				y = Rasterizer.topY;
			}
			if (y + length > Rasterizer.bottomY) {
				length = Rasterizer.bottomY - y;
			}
			int a = 256 - alpha;
			int r = (color >> 16 & 0xff) * alpha;
			int g = (color >> 8 & 0xff) * alpha;
			int b = (color & 0xff) * alpha;
			int pixel = x + y * Rasterizer.width;
			for (int lengthCounter = 0; lengthCounter < length; lengthCounter++) {
				int red = (Rasterizer.pixels[pixel] >> 16 & 0xff) * a;
				int blue = (Rasterizer.pixels[pixel] >> 8 & 0xff) * a;
				int green = (Rasterizer.pixels[pixel] & 0xff) * a;
				int rgba = (r + red >> 8 << 16) + (g + blue >> 8 << 8) + (b + green >> 8);
				Rasterizer.pixels[pixel] = rgba;
				pixel += Rasterizer.width;
			}
		}
	}
}
