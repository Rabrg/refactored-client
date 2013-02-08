package com.runescape.graphic;

import java.util.Random;

import com.runescape.cache.Archive;
import com.runescape.net.Buffer;

public class TypeFace extends Rasterizer {

	protected byte[][] characterPixels = new byte[256][];
	protected int[] characterWidths = new int[256];
	protected int[] characterHeights = new int[256];
	protected int[] characterXOffsets = new int[256];
	protected int[] characterYOffsets = new int[256];
	public int[] characterScreenWidths = new int[256];
	public int characterDefaultHeight;
	protected Random random = new Random();
	protected boolean strikeThrough = false;

	public TypeFace(boolean large, String archiveName, Archive archive) {
		Buffer dataBuffer = new Buffer(archive.getFile(archiveName + ".dat"));
		Buffer indexBuffer = new Buffer(archive.getFile("index.dat"));
		indexBuffer.offset = dataBuffer.getUnsignedLEShort() + 7;
		indexBuffer.getUnsignedByte(); // dummy

		for (int character = 0; character < 256; character++) {
			characterXOffsets[character] = indexBuffer.getUnsignedByte();
			characterYOffsets[character] = indexBuffer.getUnsignedByte();
			int characterWidth = characterWidths[character] = indexBuffer.getUnsignedLEShort();
			int characterHeight = characterHeights[character] = indexBuffer.getUnsignedLEShort();
			int characterType = indexBuffer.getUnsignedByte();
			int characterSize = characterWidth * characterHeight;
			characterPixels[character] = new byte[characterSize];
			if (characterType == 0) {
				for (int pixel = 0; pixel < characterSize; pixel++) {
					characterPixels[character][pixel] = dataBuffer.get();
				}
			} else if (characterType == 1) {
				for (int characterX = 0; characterX < characterWidth; characterX++) {
					for (int characterY = 0; characterY < characterHeight; characterY++) {
						characterPixels[character][characterX + characterY * characterWidth] = dataBuffer.get();
					}
				}
			}
			if (characterHeight > characterDefaultHeight && character < 128) {
				characterDefaultHeight = characterHeight;
			}
			characterXOffsets[character] = 1;
			characterScreenWidths[character] = characterWidth + 2;
			int pixelCount = 0;
			for (int characterY = characterHeight / 7; characterY < characterHeight; characterY++) {
				pixelCount += characterPixels[character][characterY * characterWidth];
			}
			if (pixelCount <= characterHeight / 7) {
				characterScreenWidths[character]--;
				characterXOffsets[character] = 0;
			}
			pixelCount = 0;
			for (int characterY = characterHeight / 7; characterY < characterHeight; characterY++) {
				pixelCount += characterPixels[character][characterWidth - 1 + characterY * characterWidth];
			}
			if (pixelCount <= characterHeight / 7) {
				characterScreenWidths[character]--;
			}
		}
		if (large) {
			characterScreenWidths[32] = characterScreenWidths[73];
		} else {
			characterScreenWidths[32] = characterScreenWidths[105];
		}
	}

	public void drawStringRight(String string, int x, int y, int color) {
		drawString(string, x - getStringWidth(string), y, color);
	}

	public void drawStringLeft(String string, int x, int y, int color) {
		drawString(string, x - getStringWidth(string) / 2, y, color);
	}

	public void drawStringCenter(String string, int x, int y, int color, boolean shadowed) {
		drawShadowedString(string, x - getStringEffectWidth(string) / 2, y, shadowed, color);
	}

	public int getStringEffectWidth(String string) {
		if (string == null) {
			return 0;
		}
		int width = 0;
		for (int character = 0; character < string.length(); character++) {
			if (string.charAt(character) == '@' && character + 4 < string.length()
					&& string.charAt(character + 4) == '@') {
				character += 4;
			} else {
				width += characterScreenWidths[string.charAt(character)];
			}
		}
		return width;
	}

	public int getStringWidth(String string) {
		if (string == null) {
			return 0;
		}
		int width = 0;
		for (int character = 0; character < string.length(); character++) {
			width += characterScreenWidths[string.charAt(character)];
		}
		return width;
	}

	public void drawString(String string, int x, int y, int color) {
		if (string != null) {
			y -= characterDefaultHeight;
			for (int index = 0; index < string.length(); index++) {
				char character = string.charAt(index);
				if (character != ' ') {
					drawCharacter(characterPixels[character], x + characterXOffsets[character], y
							+ characterYOffsets[character], characterWidths[character], characterHeights[character],
							color);
				}
				x += characterScreenWidths[character];
			}
		}
	}

	public void drawCenteredStringWaveY(String string, int x, int y, int wave, int color) {
		if (string != null) {
			x -= getStringWidth(string) / 2;
			y -= characterDefaultHeight;
			for (int index = 0; index < string.length(); index++) {
				char character = string.charAt(index);
				if (character != ' ') {
					drawCharacter(characterPixels[character], x + characterXOffsets[character], y
							+ characterYOffsets[character] + (int) (Math.sin(index / 2.0 + wave / 5.0) * 5.0),
							characterWidths[character], characterHeights[character], color);
				}
				x += characterScreenWidths[character];
			}
		}
	}

	public void drawCeneteredStringWaveXY(String string, int x, int y, int wave, int color) {
		if (string != null) {
			x -= getStringWidth(string) / 2;
			y -= characterDefaultHeight;
			for (int index = 0; index < string.length(); index++) {
				char character = string.charAt(index);
				if (character != ' ') {
					drawCharacter(characterPixels[character],
							x + characterXOffsets[character] + (int) (Math.sin(index / 5.0 + wave / 5.0) * 5.0), y
									+ characterYOffsets[character] + (int) (Math.sin(index / 3.0 + wave / 5.0) * 5.0),
							characterWidths[character], characterHeights[character], color);
				}
				x += characterScreenWidths[character];
			}
		}
	}

	public void drawCenteredStringWaveXYMove(String string, int x, int y, int waveAmount, int waveSpeed, int color) {
		if (string != null) {
			double speed = 7.0 - waveSpeed / 8.0;
			if (speed < 0.0) {
				speed = 0.0;
			}
			x -= getStringWidth(string) / 2;
			y -= characterDefaultHeight;
			for (int index = 0; index < string.length(); index++) {
				char character = string.charAt(index);
				if (character != ' ') {
					drawCharacter(characterPixels[character], x + characterXOffsets[character], y
							+ characterYOffsets[character] + (int) (Math.sin(index / 1.5 + waveAmount) * speed),
							characterWidths[character], characterHeights[character], color);
				}
				x += characterScreenWidths[character];
			}
		}
	}

	public void drawShadowedString(String string, int x, int y, boolean shadow, int color) {
		strikeThrough = false;
		int originalX = x;
		if (string != null) {
			y -= characterDefaultHeight;
			for (int character = 0; character < string.length(); character++) {
				if (string.charAt(character) == '@' && character + 4 < string.length()
						&& string.charAt(character + 4) == '@') {
					int stringColor = getColor(string.substring(character + 1, character + 4));
					if (stringColor != -1) {
						color = stringColor;
					}
					character += 4;
				} else {
					char c = string.charAt(character);
					if (c != ' ') {
						if (shadow) {
							drawCharacter(characterPixels[c], x + characterXOffsets[c] + 1, y + characterYOffsets[c]
									+ 1, characterWidths[c], characterHeights[c], 0);
						}
						drawCharacter(characterPixels[c], x + characterXOffsets[c], y + characterYOffsets[c],
								characterWidths[c], characterHeights[c], color);
					}
					x += characterScreenWidths[c];
				}
			}
			if (!strikeThrough) {
				return;
			}
			Rasterizer.drawHorizontalLine(originalX, y + (int) (characterDefaultHeight * 0.7), x - originalX, 8388608);
		}
	}

	public void drawShadowedSeededAlphaString(String string, int x, int y, int seed, int color) {
		if (string != null) {
			random.setSeed(seed);
			int alpha = 192 + (random.nextInt() & 0x1f);
			y -= characterDefaultHeight;
			for (int index = 0; index < string.length(); index++) {
				if (string.charAt(index) == '@' && index + 4 < string.length() && string.charAt(index + 4) == '@') {
					int stringColor = getColor(string.substring(index + 1, index + 4));
					if (stringColor != -1) {
						color = stringColor;
					}
					index += 4;
				} else {
					char c = string.charAt(index);
					if (c != ' ') {
						drawAlphaCharacter(192, x + characterXOffsets[c] + 1, characterPixels[c], characterWidths[c], y
								+ characterYOffsets[c] + 1, characterHeights[c], 0);
						drawAlphaCharacter(alpha, x + characterXOffsets[c], characterPixels[c], characterWidths[c], y
								+ characterYOffsets[c], characterHeights[c], color);
					}
					x += characterScreenWidths[c];
					if ((random.nextInt() & 0x3) == 0) {
						x++;
					}
				}
			}
		}
	}

	public int getColor(String color) {
		if (color.equals("red")) {
			return 16711680;
		}
		if (color.equals("gre")) {
			return 65280;
		}
		if (color.equals("blu")) {
			return 255;
		}
		if (color.equals("yel")) {
			return 16776960;
		}
		if (color.equals("cya")) {
			return 65535;
		}
		if (color.equals("mag")) {
			return 16711935;
		}
		if (color.equals("whi")) {
			return 16777215;
		}
		if (color.equals("bla")) {
			return 0;
		}
		if (color.equals("lre")) {
			return 16748608;
		}
		if (color.equals("dre")) {
			return 8388608;
		}
		if (color.equals("dbl")) {
			return 128;
		}
		if (color.equals("or1")) {
			return 16756736;
		}
		if (color.equals("or2")) {
			return 16740352;
		}
		if (color.equals("or3")) {
			return 16723968;
		}
		if (color.equals("gr1")) {
			return 12648192;
		}
		if (color.equals("gr2")) {
			return 8453888;
		}
		if (color.equals("gr3")) {
			return 4259584;
		}
		if (color.equals("str")) {
			strikeThrough = true;
		}
		if (color.equals("end")) {
			strikeThrough = false;
		}
		return -1;
	}

	private void drawCharacter(byte[] pixels, int x, int y, int width, int height, int color) {
		int rasterizerPixel = x + y * Rasterizer.width;
		int remainingWidth = Rasterizer.width - width;
		int characterPixelOffset = 0;
		int characterPixel = 0;
		if (y < Rasterizer.topY) {
			int offsetY = Rasterizer.topY - y;
			height -= offsetY;
			y = Rasterizer.topY;
			characterPixel += offsetY * width;
			rasterizerPixel += offsetY * Rasterizer.width;
		}
		if (y + height >= Rasterizer.bottomY) {
			height -= y + height - Rasterizer.bottomY + 1;
		}
		if (x < Rasterizer.topX) {
			int offsetX = Rasterizer.topX - x;
			width -= offsetX;
			x = Rasterizer.topX;
			characterPixel += offsetX;
			rasterizerPixel += offsetX;
			characterPixelOffset += offsetX;
			remainingWidth += offsetX;
		}
		if (x + width >= Rasterizer.bottomX) {
			int endOffsetX = x + width - Rasterizer.bottomX + 1;
			width -= endOffsetX;
			characterPixelOffset += endOffsetX;
			remainingWidth += endOffsetX;
		}
		if (width > 0 && height > 0) {
			drawCharacterPixels(pixels, Rasterizer.pixels, characterPixel, rasterizerPixel, characterPixelOffset,
					remainingWidth, width, height, color);
		}
	}

	private void drawCharacterPixels(byte[] characterPixels, int[] rasterizerPixels, int characterPixel,
			int rasterizerPixel, int characterPixelOffset, int rasterizerPixelOffset, int width, int height, int color) {
		int negativeQuaterWidth = -(width >> 2);
		int negativeFirstTwoWidthBits = -(width & 0x3);
		for (int heightCounter = -height; heightCounter < 0; heightCounter++) {
			for (int widthCounter = negativeQuaterWidth; widthCounter < 0; widthCounter++) {
				if (characterPixels[characterPixel++] != 0) {
					rasterizerPixels[rasterizerPixel++] = color;
				} else {
					rasterizerPixel++;
				}
				if (characterPixels[characterPixel++] != 0) {
					rasterizerPixels[rasterizerPixel++] = color;
				} else {
					rasterizerPixel++;
				}
				if (characterPixels[characterPixel++] != 0) {
					rasterizerPixels[rasterizerPixel++] = color;
				} else {
					rasterizerPixel++;
				}
				if (characterPixels[characterPixel++] != 0) {
					rasterizerPixels[rasterizerPixel++] = color;
				} else {
					rasterizerPixel++;
				}
			}
			for (int widthCounter = negativeFirstTwoWidthBits; widthCounter < 0; widthCounter++) {
				if (characterPixels[characterPixel++] != 0) {
					rasterizerPixels[rasterizerPixel++] = color;
				} else {
					rasterizerPixel++;
				}
			}
			rasterizerPixel += rasterizerPixelOffset;
			characterPixel += characterPixelOffset;
		}
	}

	private void drawAlphaCharacter(int alpha, int x, byte[] characterPixels, int width, int y, int height, int color) {
		int rasterizerPixel = x + y * Rasterizer.width;
		int rasterizerPixelOffset = Rasterizer.width - width;
		int characterPixelOffset = 0;
		int characterPixel = 0;
		if (y < Rasterizer.topY) {
			int yOffset = Rasterizer.topY - y;
			height -= yOffset;
			y = Rasterizer.topY;
			characterPixel += yOffset * width;
			rasterizerPixel += yOffset * Rasterizer.width;
		}
		if (y + height >= Rasterizer.bottomY) {
			height -= y + height - Rasterizer.bottomY + 1;
		}
		if (x < Rasterizer.topX) {
			int xOffset = Rasterizer.topX - x;
			width -= xOffset;
			x = Rasterizer.topX;
			characterPixel += xOffset;
			rasterizerPixel += xOffset;
			characterPixelOffset += xOffset;
			rasterizerPixelOffset += xOffset;
		}
		if (x + width >= Rasterizer.bottomX) {
			int widthoffset = x + width - Rasterizer.bottomX + 1;
			width -= widthoffset;
			characterPixelOffset += widthoffset;
			rasterizerPixelOffset += widthoffset;
		}
		if (width > 0 && height > 0) {
			drawCharacterPixelsAlpha(characterPixels, Rasterizer.pixels, characterPixel, rasterizerPixel,
					characterPixelOffset, rasterizerPixelOffset, width, height, color, alpha);
		}
	}

	private void drawCharacterPixelsAlpha(byte[] characterPixels, int[] rasterizerPixels, int characterPixel,
			int rasterizerPixel, int characterPixelOffset, int rasterizerPixelOffset, int width, int height, int color,
			int alpha) {
		color = ((color & 0xff00ff) * alpha & ~0xff00ff) + ((color & 0xff00) * alpha & 0xff0000) >> 8;
		alpha = 256 - alpha;
		for (int heightCounter = -height; heightCounter < 0; heightCounter++) {
			for (int widthCounter = -width; widthCounter < 0; widthCounter++) {
				if (characterPixels[characterPixel++] != 0) {
					int rasterizerPixelColor = rasterizerPixels[rasterizerPixel];
					rasterizerPixels[rasterizerPixel++] = (((rasterizerPixelColor & 0xff00ff) * alpha & ~0xff00ff)
							+ ((rasterizerPixelColor & 0xff00) * alpha & 0xff0000) >> 8)
							+ color;
				} else {
					rasterizerPixel++;
				}
			}
			rasterizerPixel += rasterizerPixelOffset;
			characterPixel += characterPixelOffset;
		}
	}
}
