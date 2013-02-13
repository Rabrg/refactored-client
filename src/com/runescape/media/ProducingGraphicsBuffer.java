package com.runescape.media;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.ColorModel;
import java.awt.image.DirectColorModel;
import java.awt.image.ImageConsumer;
import java.awt.image.ImageObserver;
import java.awt.image.ImageProducer;

public class ProducingGraphicsBuffer implements ImageProducer, ImageObserver {

	public int[] pixels;
	public int width;
	public int height;
	protected ColorModel colorModel;
	protected ImageConsumer imageConsumer;
	public Image image;

	public ProducingGraphicsBuffer(int width, int height, Component component) {
		this.width = width;
		this.height = height;
		pixels = new int[width * height];
		colorModel = new DirectColorModel(32, 16711680, 65280, 255);
		image = component.createImage(this);
		drawPixels();
		component.prepareImage(image, this);
		drawPixels();
		component.prepareImage(image, this);
		drawPixels();
		component.prepareImage(image, this);
		createRasterizer();
	}

	public void createRasterizer() {
		Rasterizer.createRasterizer(pixels, width, height);
	}

	public void drawGraphics(int x, int y, Graphics graphics) {
		drawPixels();
		graphics.drawImage(image, x, y, this);
	}

	@Override
	public synchronized void addConsumer(ImageConsumer imageConsumer) {
		this.imageConsumer = imageConsumer;
		imageConsumer.setDimensions(width, height);
		imageConsumer.setProperties(null);
		imageConsumer.setColorModel(colorModel);
		imageConsumer.setHints(14);
	}

	@Override
	public synchronized boolean isConsumer(ImageConsumer imageConsumer) {
		if (this.imageConsumer == imageConsumer) {
			return true;
		}
		return false;
	}

	@Override
	public synchronized void removeConsumer(ImageConsumer imageConsumer) {
		if (this.imageConsumer == imageConsumer) {
			imageConsumer = null;
		}
	}

	@Override
	public void startProduction(ImageConsumer imageConsumer) {
		addConsumer(imageConsumer);
	}

	@Override
	public void requestTopDownLeftRightResend(ImageConsumer imageConsumer) {
		System.out.println("TDLR");
	}

	public synchronized void drawPixels() {
		if (imageConsumer != null) {
			imageConsumer.setPixels(0, 0, width, height, colorModel, pixels, 0, width);
			imageConsumer.imageComplete(2);
		}
	}

	@Override
	public boolean imageUpdate(Image image, int infoflags, int x, int y, int width, int height) {
		return true;
	}
}
