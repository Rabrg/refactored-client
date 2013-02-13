package com.runescape.cache.media;

import java.awt.Component;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.Toolkit;
import java.awt.image.PixelGrabber;

import com.runescape.cache.Archive;
import com.runescape.media.Rasterizer;
import com.runescape.net.Buffer;
import com.runescape.util.SignLink;

public class ImageRGB extends Rasterizer {

	public int[] pixels;
	public int width;
	public int height;
	public int offsetX;
	public int offsetY;
	public int maxWidth;
	public int maxHeight;

	public ImageRGB(int width, int height) {
		pixels = new int[width * height];
		this.width = maxWidth = width;
		this.height = maxHeight = height;
		offsetX = offsetY = 0;
	}

	public ImageRGB(byte[] imagedata, Component component) {
		try {
			Image image = Toolkit.getDefaultToolkit().createImage(imagedata);
			MediaTracker mediaTracker = new MediaTracker(component);
			mediaTracker.addImage(image, 0);
			mediaTracker.waitForAll();
			width = image.getWidth(component);
			height = image.getHeight(component);
			maxWidth = width;
			maxHeight = height;
			offsetX = 0;
			offsetY = 0;
			pixels = new int[width * height];
			PixelGrabber pixelgrabber = new PixelGrabber(image, 0, 0, width, height, pixels, 0, width);
			pixelgrabber.grabPixels();
		} catch (Exception exception) {
			System.out.println("Error converting jpg");
		}
	}

	public ImageRGB(Archive archive, String string, int archiveIndex) {
		Buffer dataBuffer = new Buffer(archive.getFile(string + ".dat"));
		Buffer indexBuffer = new Buffer(archive.getFile("index.dat"));
		indexBuffer.offset = dataBuffer.getUnsignedLEShort();
		maxWidth = indexBuffer.getUnsignedLEShort();
		maxHeight = indexBuffer.getUnsignedLEShort();
		int length = indexBuffer.getUnsignedByte();
		int[] pixels = new int[length];
		for (int pixel = 0; pixel < length - 1; pixel++) {
			pixels[pixel + 1] = indexBuffer.get24BitInt();
			if (pixels[pixel + 1] == 0) {
				pixels[pixel + 1] = 1;
			}
		}
		for (int index = 0; index < archiveIndex; index++) {
			indexBuffer.offset += 2;
			dataBuffer.offset += indexBuffer.getUnsignedLEShort() * indexBuffer.getUnsignedLEShort();
			indexBuffer.offset++;
		}
		offsetX = indexBuffer.getUnsignedByte();
		offsetY = indexBuffer.getUnsignedByte();
		width = indexBuffer.getUnsignedLEShort();
		height = indexBuffer.getUnsignedLEShort();
		int type = indexBuffer.getUnsignedByte();
		int pixelCount = width * height;
		this.pixels = new int[pixelCount];
		if (type == 0) {
			for (int pixel = 0; pixel < pixelCount; pixel++) {
				this.pixels[pixel] = pixels[dataBuffer.getUnsignedByte()];
			}
		} else if (type == 1) {
			for (int x = 0; x < width; x++) {
				for (int y = 0; y < height; y++) {
					this.pixels[x + y * width] = pixels[dataBuffer.getUnsignedByte()];
				}
			}
		}
	}

	public void createRasterizer() {
		Rasterizer.createRasterizer(pixels, width, height);
	}

	public void adjustRGB(int redOffset, int greenOffset, int blueOffset) {
		for (int pixel = 0; pixel < pixels.length; pixel++) {
			int originalColor = pixels[pixel];
			if (originalColor != 0) {
				int red = originalColor >> 16 & 0xff;
				red += redOffset;
				if (red < 1) {
					red = 1;
				} else if (red > 255) {
					red = 255;
				}
				int green = originalColor >> 8 & 0xff;
				green += greenOffset;
				if (green < 1) {
					green = 1;
				} else if (green > 255) {
					green = 255;
				}
				int blue = originalColor & 0xff;
				blue += blueOffset;
				if (blue < 1) {
					blue = 1;
				} else if (blue > 255) {
					blue = 255;
				}
				pixels[pixel] = (red << 16) + (green << 8) + blue;
			}
		}
	}

	public void trim() {
		int[] newPixels = new int[maxWidth * maxHeight];
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				newPixels[(y + offsetY) * maxWidth + (x + offsetX)] = pixels[y * width + x];
			}
		}
		pixels = newPixels;
		width = maxWidth;
		height = maxHeight;
		offsetX = 0;
		offsetY = 0;
	}

	public void drawInverse(int x, int y) {
		x += offsetX;
		y += offsetY;
		int rasterizerPixel = x + y * Rasterizer.width;
		int pixel = 0;
		int newHeight = height;
		int newWidth = width;
		int rasterizerPixelOffset = Rasterizer.width - newWidth;
		int pixelOffset = 0;
		if (y < Rasterizer.topY) {
			int yOffset = Rasterizer.topY - y;
			newHeight -= yOffset;
			y = Rasterizer.topY;
			pixel += yOffset * newWidth;
			rasterizerPixel += yOffset * Rasterizer.width;
		}
		if (y + newHeight > Rasterizer.bottomY) {
			newHeight -= y + newHeight - Rasterizer.bottomY;
		}
		if (x < Rasterizer.topX) {
			int xOffset = Rasterizer.topX - x;
			newWidth -= xOffset;
			x = Rasterizer.topX;
			pixel += xOffset;
			rasterizerPixel += xOffset;
			pixelOffset += xOffset;
			rasterizerPixelOffset += xOffset;
		}
		if (x + newWidth > Rasterizer.bottomX) {
			int widthOffset = x + newWidth - Rasterizer.bottomX;
			newWidth -= widthOffset;
			pixelOffset += widthOffset;
			rasterizerPixelOffset += widthOffset;
		}
		if (newWidth > 0 && newHeight > 0) {
			copyPixels(pixels, Rasterizer.pixels, pixel, rasterizerPixel, pixelOffset, rasterizerPixelOffset, newWidth,
					newHeight);
		}
	}

	private void copyPixels(int[] pixels, int[] rasterizerPixels, int pixel, int rasterizerPixel, int pixelOffset,
			int rasterizerPixelOffset, int width, int height) {
		int shiftedWidth = -(width >> 2);
		width = -(width & 0x3);
		for (int heightCounter = -height; heightCounter < 0; heightCounter++) {
			for (int widthCounter = shiftedWidth; widthCounter < 0; widthCounter++) {
				rasterizerPixels[rasterizerPixel++] = pixels[pixel++];
				rasterizerPixels[rasterizerPixel++] = pixels[pixel++];
				rasterizerPixels[rasterizerPixel++] = pixels[pixel++];
				rasterizerPixels[rasterizerPixel++] = pixels[pixel++];
			}
			for (int widthCounter = width; widthCounter < 0; widthCounter++) {
				rasterizerPixels[rasterizerPixel++] = pixels[pixel++];
			}
			rasterizerPixel += rasterizerPixelOffset;
			pixel += pixelOffset;
		}
	}

	public void drawImage(int x, int y) {
		x += offsetX;
		y += offsetY;
		int rasterizerOffset = x + y * Rasterizer.width;
		int pixelOffset = 0;
		int imageHeight = height;
		int imageWidth = width;
		int deviation = Rasterizer.width - imageWidth;
		int originalDeviation = 0;
		if (y < Rasterizer.topY) {
			int yOffset = Rasterizer.topY - y;
			imageHeight -= yOffset;
			y = Rasterizer.topY;
			pixelOffset += yOffset * imageWidth;
			rasterizerOffset += yOffset * Rasterizer.width;
		}
		if (y + imageHeight > Rasterizer.bottomY) {
			imageHeight -= y + imageHeight - Rasterizer.bottomY;
		}
		if (x < Rasterizer.topX) {
			int xOffset = Rasterizer.topX - x;
			imageWidth -= xOffset;
			x = Rasterizer.topX;
			pixelOffset += xOffset;
			rasterizerOffset += xOffset;
			originalDeviation += xOffset;
			deviation += xOffset;
		}
		if (x + imageWidth > Rasterizer.bottomX) {
			int xOffset = x + imageWidth - Rasterizer.bottomX;
			imageWidth -= xOffset;
			originalDeviation += xOffset;
			deviation += xOffset;
		}
		if (imageWidth > 0 && imageHeight > 0) {
			shapeImageToPixels(pixels, Rasterizer.pixels, pixelOffset, rasterizerOffset, imageWidth, imageHeight,
					originalDeviation, deviation, 0);
		}
	}

	private void shapeImageToPixels(int[] pixels, int[] rasterizerPixels, int pixel, int rasterizerPixel, int width,
			int height, int pixelOffset, int rasterizerPixelOffset, int pixelColor) {
		int shiftedWidth = -(width >> 2);
		width = -(width & 0x3);
		for (int heightCounter = -height; heightCounter < 0; heightCounter++) {
			for (int widthCounter = shiftedWidth; widthCounter < 0; widthCounter++) {
				pixelColor = pixels[pixel++];
				if (pixelColor != 0) {
					rasterizerPixels[rasterizerPixel++] = pixelColor;
				} else {
					rasterizerPixel++;
				}
				pixelColor = pixels[pixel++];
				if (pixelColor != 0) {
					rasterizerPixels[rasterizerPixel++] = pixelColor;
				} else {
					rasterizerPixel++;
				}
				pixelColor = pixels[pixel++];
				if (pixelColor != 0) {
					rasterizerPixels[rasterizerPixel++] = pixelColor;
				} else {
					rasterizerPixel++;
				}
				pixelColor = pixels[pixel++];
				if (pixelColor != 0) {
					rasterizerPixels[rasterizerPixel++] = pixelColor;
				} else {
					rasterizerPixel++;
				}
			}
			for (int widthCounter = width; widthCounter < 0; widthCounter++) {
				pixelColor = pixels[pixel++];
				if (pixelColor != 0) {
					rasterizerPixels[rasterizerPixel++] = pixelColor;
				} else {
					rasterizerPixel++;
				}
			}
			rasterizerPixel += rasterizerPixelOffset;
			pixel += pixelOffset;
		}
	}

	public void drawImageAlpha(int x, int y, int alpha) {
		x += offsetX;
		y += offsetY;
		int rasterizerPixel = x + y * Rasterizer.width;
		int pixel = 0;
		int newHeight = height;
		int newWidth = width;
		int rasterizerPixelOffset = Rasterizer.width - newWidth;
		int pixelOffset = 0;
		if (y < Rasterizer.topY) {
			int yOffset = Rasterizer.topY - y;
			newHeight -= yOffset;
			y = Rasterizer.topY;
			pixel += yOffset * newWidth;
			rasterizerPixel += yOffset * Rasterizer.width;
		}
		if (y + newHeight > Rasterizer.bottomY) {
			newHeight -= y + newHeight - Rasterizer.bottomY;
		}
		if (x < Rasterizer.topX) {
			int xOffset = Rasterizer.topX - x;
			newWidth -= xOffset;
			x = Rasterizer.topX;
			pixel += xOffset;
			rasterizerPixel += xOffset;
			pixelOffset += xOffset;
			rasterizerPixelOffset += xOffset;
		}
		if (x + newWidth > Rasterizer.bottomX) {
			int xOffset = x + newWidth - Rasterizer.bottomX;
			newWidth -= xOffset;
			pixelOffset += xOffset;
			rasterizerPixelOffset += xOffset;
		}
		if (newWidth > 0 && newHeight > 0) {
			copyPixelsAlpha(pixels, Rasterizer.pixels, pixel, rasterizerPixel, pixelOffset, rasterizerPixelOffset,
					newWidth, newHeight, 0, alpha);
		}
	}

	private void copyPixelsAlpha(int[] pixels, int[] rasterizerPixels, int pixel, int rasterizerPixel, int pixelOffset,
			int rasterizerPixelOffset, int width, int height, int color, int alpha) {
		int alphaValue = 256 - alpha;
		for (int heightCounter = -height; heightCounter < 0; heightCounter++) {
			for (int widthCounter = -width; widthCounter < 0; widthCounter++) {
				color = pixels[pixel++];
				if (color != 0) {
					int rasterizerPixelColor = rasterizerPixels[rasterizerPixel];
					rasterizerPixels[rasterizerPixel++] = ((color & 0xff00ff) * alpha
							+ (rasterizerPixelColor & 0xff00ff) * alphaValue & ~0xff00ff)
							+ ((color & 0xff00) * alpha + (rasterizerPixelColor & 0xff00) * alphaValue & 0xff0000) >> 8;
				} else {
					rasterizerPixel++;
				}
			}
			rasterizerPixel += rasterizerPixelOffset;
			pixel += pixelOffset;
		}
	}

	public void shapeImageToPixels(int i, int i_89_, int[] is, int i_90_, int[] is_91_, int i_93_, int i_94_,
			int i_95_, int i_96_, int i_97_) {
		int i_98_ = -i_96_ / 2;
		int i_99_ = -i / 2;
		int i_100_ = (int) (Math.sin(i_89_ / 326.11) * 65536.0);
		int i_101_ = (int) (Math.cos(i_89_ / 326.11) * 65536.0);
		i_100_ = i_100_ * i_90_ >> 8;
		i_101_ = i_101_ * i_90_ >> 8;
		int i_102_ = (i_97_ << 16) + (i_99_ * i_100_ + i_98_ * i_101_);
		int i_103_ = (i_93_ << 16) + (i_99_ * i_101_ - i_98_ * i_100_);
		int i_104_ = i_95_ + i_94_ * Rasterizer.width;
		for (i_94_ = 0; i_94_ < i; i_94_++) {
			int i_105_ = is_91_[i_94_];
			int rasterizerPixel = i_104_ + i_105_;
			int i_107_ = i_102_ + i_101_ * i_105_;
			int i_108_ = i_103_ - i_100_ * i_105_;
			for (i_95_ = -is[i_94_]; i_95_ < 0; i_95_++) {
				Rasterizer.pixels[rasterizerPixel++] = pixels[(i_107_ >> 16) + (i_108_ >> 16) * width];
				i_107_ += i_101_;
				i_108_ -= i_100_;
			}
			i_102_ += i_100_;
			i_103_ += i_101_;
			i_104_ += Rasterizer.width;
		}
	}

	public void method350(int i, int i_109_, int i_110_, int i_111_, int i_112_, int i_113_, int i_114_, double d,
			int i_115_) {
		try {
			if (i_112_ == 41960) {
				try {
					int i_116_ = -i_110_ / 2;
					int i_117_ = -i_114_ / 2;
					int i_118_ = (int) (Math.sin(d) * 65536.0);
					int i_119_ = (int) (Math.cos(d) * 65536.0);
					i_118_ = i_118_ * i_113_ >> 8;
					i_119_ = i_119_ * i_113_ >> 8;
					int i_120_ = (i_111_ << 16) + (i_117_ * i_118_ + i_116_ * i_119_);
					int i_121_ = (i_109_ << 16) + (i_117_ * i_119_ - i_116_ * i_118_);
					int i_122_ = i_115_ + i * Rasterizer.width;
					for (i = 0; i < i_114_; i++) {
						int i_123_ = i_122_;
						int i_124_ = i_120_;
						int i_125_ = i_121_;
						for (i_115_ = -i_110_; i_115_ < 0; i_115_++) {
							int i_126_ = pixels[(i_124_ >> 16) + (i_125_ >> 16) * width];
							if (i_126_ != 0) {
								Rasterizer.pixels[i_123_++] = i_126_;
							} else {
								i_123_++;
							}
							i_124_ += i_119_;
							i_125_ -= i_118_;
						}
						i_120_ += i_118_;
						i_121_ += i_119_;
						i_122_ += Rasterizer.width;
					}
				} catch (Exception exception) {
					/* empty */
				}
			}
		} catch (RuntimeException runtimeexception) {
			SignLink.reportError("71953, " + i + ", " + i_109_ + ", " + i_110_ + ", " + i_111_ + ", " + i_112_ + ", "
					+ i_113_ + ", " + i_114_ + ", " + d + ", " + i_115_ + ", " + runtimeexception.toString());
			throw new RuntimeException();
		}
	}

	public void method351(IndexedImage indexedimage, boolean bool, int i, int i_127_) {
		try {
			i_127_ += offsetX;
			i += offsetY;
			int i_128_ = i_127_ + i * Rasterizer.width;
			int i_129_ = 0;
			if (bool) {
			}
			int i_130_ = height;
			int i_131_ = width;
			int i_132_ = Rasterizer.width - i_131_;
			int i_133_ = 0;
			if (i < Rasterizer.topY) {
				int i_134_ = Rasterizer.topY - i;
				i_130_ -= i_134_;
				i = Rasterizer.topY;
				i_129_ += i_134_ * i_131_;
				i_128_ += i_134_ * Rasterizer.width;
			}
			if (i + i_130_ > Rasterizer.bottomY) {
				i_130_ -= i + i_130_ - Rasterizer.bottomY;
			}
			if (i_127_ < Rasterizer.topX) {
				int i_135_ = Rasterizer.topX - i_127_;
				i_131_ -= i_135_;
				i_127_ = Rasterizer.topX;
				i_129_ += i_135_;
				i_128_ += i_135_;
				i_133_ += i_135_;
				i_132_ += i_135_;
			}
			if (i_127_ + i_131_ > Rasterizer.bottomX) {
				int i_136_ = i_127_ + i_131_ - Rasterizer.bottomX;
				i_131_ -= i_136_;
				i_133_ += i_136_;
				i_132_ += i_136_;
			}
			if (i_131_ > 0 && i_130_ > 0) {
				method352(pixels, i_131_, indexedimage.pixels, i_130_, Rasterizer.pixels, 0, i_132_, i_128_, i_133_,
						i_129_);
			}
		} catch (RuntimeException runtimeexception) {
			SignLink.reportError("70668, " + indexedimage + ", " + bool + ", " + i + ", " + i_127_ + ", "
					+ runtimeexception.toString());
			throw new RuntimeException();
		}
	}

	private void method352(int[] is, int i, byte[] bs, int i_137_, int[] is_138_, int i_139_, int i_140_, int i_141_,
			int i_142_, int i_143_) {
		int i_144_ = -(i >> 2);
		i = -(i & 0x3);
		for (int i_146_ = -i_137_; i_146_ < 0; i_146_++) {
			for (int i_147_ = i_144_; i_147_ < 0; i_147_++) {
				i_139_ = is[i_143_++];
				if (i_139_ != 0 && bs[i_141_] == 0) {
					is_138_[i_141_++] = i_139_;
				} else {
					i_141_++;
				}
				i_139_ = is[i_143_++];
				if (i_139_ != 0 && bs[i_141_] == 0) {
					is_138_[i_141_++] = i_139_;
				} else {
					i_141_++;
				}
				i_139_ = is[i_143_++];
				if (i_139_ != 0 && bs[i_141_] == 0) {
					is_138_[i_141_++] = i_139_;
				} else {
					i_141_++;
				}
				i_139_ = is[i_143_++];
				if (i_139_ != 0 && bs[i_141_] == 0) {
					is_138_[i_141_++] = i_139_;
				} else {
					i_141_++;
				}
			}
			for (int i_148_ = i; i_148_ < 0; i_148_++) {
				i_139_ = is[i_143_++];
				if (i_139_ != 0 && bs[i_141_] == 0) {
					is_138_[i_141_++] = i_139_;
				} else {
					i_141_++;
				}
			}
			i_141_ += i_140_;
			i_143_ += i_142_;
		}
	}
}
