package com.runescape.media.renderable.actor;

import com.runescape.cache.media.AnimationSequence;
import com.runescape.media.renderable.Renderable;

public class Actor extends Renderable {

	public int[] pathX = new int[10];
	public int[] pathY = new int[10];
	public int interactingEntity = -1;
	public int anInt1523;
	public int anInt1524 = 32;
	public int runAnimationId = -1;
	public String forcedChat;
	public int modelHeight = 200;
	public int anInt1530;
	public int standAnimationId = -1;
	public int standTurnAnimationId = -1;
	public int chatColor;
	public int[] hitDamages = new int[4];
	public int[] hitTypes = new int[4];
	public int[] hitCycles = new int[4];
	public int anInt1537 = -1;
	public int anInt1538;
	public int anInt1539;
	public int spotAnimationId = -1;
	public int currentAnimationFrame;
	public int anInt1542;
	public int spotAnimationEndCycle;
	public int spotAnimationDelay;
	public int pathLength;
	public int animation = -1;
	public int anInt1547;
	public int anInt1548;
	public int aniomationDelay;
	public int anInt1550;
	public int chatEffect;
	public int endCycle = -1000;
	public int maxHealth;
	public int currentHealth;
	public int anInt1555 = 100;
	public int anInt1557;
	public int faceTowardX;
	public int faceTowardY;
	public int boundaryDimension = 1;
	public boolean aBoolean1561 = false;
	public int anInt1562;
	public int anInt1563;
	public int anInt1564;
	public int anInt1565;
	public int anInt1566;
	public int anInt1567;
	public int anInt1568;
	public int anInt1569;
	public int xWithBoundary;
	public int yWithBoundary;
	public int anInt1572;
	public boolean[] pathRun = new boolean[10];
	public int walkAnimationId = -1;
	public int turnAroundAnimationId = -1;
	public int turnRightAnimationId = -1;
	public int turnLeftAnimationId = -1;

	public final void setPosition(int x, int y) {
		if (animation != -1 && AnimationSequence.cache[animation].priority == 1) {
			animation = -1;
		}
		int xOffset = x - pathX[0];
		int yOffset = y - pathY[0];
		if (xOffset >= -8 && xOffset <= 8 && yOffset >= -8 && yOffset <= 8) {
			if (pathLength < 9) {
				pathLength++;
			}
			for (int step = pathLength; step > 0; step--) {
				pathX[step] = pathX[step - 1];
				pathY[step] = pathY[step - 1];
				pathRun[step] = pathRun[step - 1];
			}
			pathX[0] = x;
			pathY[0] = y;
			pathRun[0] = false;
			return;
		}
		pathLength = 0;
		anInt1562 = 0;
		anInt1523 = 0;
		pathX[0] = x;
		pathY[0] = y;
		xWithBoundary = pathX[0] * 128 + boundaryDimension * 64;
		yWithBoundary = pathY[0] * 128 + boundaryDimension * 64;
	}

	public final void resetPath() {
		pathLength = 0;
		anInt1562 = 0;
	}

	public final void updateHits(int hitType, int hitDamage, int hitCycle) {
		for (int hit = 0; hit < 4; hit++) {
			if (hitCycles[hit] <= hitCycle) {
				hitDamages[hit] = hitDamage;
				hitTypes[hit] = hitType;
				hitCycles[hit] = hitCycle + 70;
				return;
			}
		}
	}

	public final void move(int direction, boolean running) {
		int x = pathX[0];
		int y = pathY[0];
		if (direction == 0) {
			x--;
			y++;
		}
		if (direction == 1) {
			y++;
		}
		if (direction == 2) {
			x++;
			y++;
		}
		if (direction == 3) {
			x--;
		}
		if (direction == 4) {
			x++;
		}
		if (direction == 5) {
			x--;
			y--;
		}
		if (direction == 6) {
			y--;
		}
		if (direction == 7) {
			x++;
			y--;
		}
		if (animation != -1 && AnimationSequence.cache[animation].priority == 1) {
			animation = -1;
		}
		if (pathLength < 9) {
			pathLength++;
		}
		for (int step = pathLength; step > 0; step--) {
			pathX[step] = pathX[step - 1];
			pathY[step] = pathY[step - 1];
			pathRun[step] = pathRun[step - 1];
		}
		pathX[0] = x;
		pathY[0] = y;
		pathRun[0] = running;
	}

	public boolean isVisibile() {
		return false;
	}
}
