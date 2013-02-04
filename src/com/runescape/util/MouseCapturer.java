package com.runescape.util;

import com.runescape.Client;

public class MouseCapturer implements Runnable {

	public Client client;
	public Object objectLock = new Object();
	public int[] coordsY = new int[500];
	public boolean capturing = true;
	public int[] coordsX = new int[500];
	public int coord;

	@Override
	public void run() {
		while (capturing) {
			synchronized (objectLock) {
				if (coord < 500) {
					coordsX[coord] = client.mouseEventX;
					coordsY[coord] = client.mouseEventY;
					coord++;
				}
			}
			try {
				Thread.sleep(50L);
			} catch (Exception exception) {
			}
		}
	}

	public MouseCapturer(Client client) {
		this.client = client;
	}
}
