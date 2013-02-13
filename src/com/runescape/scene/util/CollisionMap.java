package com.runescape.scene.util;

public class CollisionMap {

	public int insetX;
	public int insetY;
	public int width;
	public int height;
	public int[][] adjacency;

	public CollisionMap(int width, int height, boolean bool) {
		insetX = 0;
		insetY = 0;
		this.width = width;
		this.height = height;
		adjacency = new int[width][height];
		reset();
	}

	public void reset() {
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				if (x == 0 || y == 0 || x == width - 1 || y == height - 1) {
					adjacency[x][y] = 0xffffff; /* fully closed */
				} else {
					adjacency[x][y] = 0x1000000; /* uninitialized */
				}
			}
		}
	}

	public void markWall(int x, int y, int position, int orientation, boolean impenetrable) {
		x -= insetX;
		y -= insetY;
		if (position == 0) {
			if (orientation == 0) {
				set(x, y, 0x80); /* wall west */
				set(x - 1, y, 0x8); /* wall east */
			}
			if (orientation == 1) {
				set(x, y, 0x2); /* wall north */
				set(x, y + 1, 0x20); /* wall south */
			}
			if (orientation == 2) {
				set(x, y, 0x8); /* wall east */
				set(x + 1, y, 0x80); /* wall west */
			}
			if (orientation == 3) {
				set(x, y, 0x20); /* wall south */
				set(x, y - 1, 0x2); /* wall north */
			}
		}
		if (position == 1 || position == 3) {
			if (orientation == 0) {
				set(x, y, 0x1); /* wall northwest */
				set(x - 1, y + 1, 0x10); /* wall southeast */
			}
			if (orientation == 1) {
				set(x, y, 0x4); /* wall northeast */
				set(x + 1, y + 1, 0x40); /* wall southwest */
			}
			if (orientation == 2) {
				set(x, y, 0x10); /* wall southeast */
				set(x + 1, y - 1, 0x1); /* wall northwest */
			}
			if (orientation == 3) {
				set(x, y, 0x40); /* wall southwest */
				set(x - 1, y - 1, 0x4); /* wall northeast */
			}
		}
		if (position == 2) {
			if (orientation == 0) {
				set(x, y, 0x82); /* wall west | north */
				set(x - 1, y, 0x8); /* wall east */
				set(x, y + 1, 0x20); /* wall south */
			}
			if (orientation == 1) {
				set(x, y, 0xa); /* wall east | north */
				set(x, y + 1, 0x20); /* wall south */
				set(x + 1, y, 0x80); /* wall west */
			}
			if (orientation == 2) {
				set(x, y, 0x28); /* wall east | south */
				set(x + 1, y, 0x80); /* wall west */
				set(x, y - 1, 0x2); /* wall north */
			}
			if (orientation == 3) {
				set(x, y, 0xa0); /* wall east | south */
				set(x, y - 1, 0x2); /* wall west */
				set(x - 1, y, 0x8); /* wall north */
			}
		}
		if (impenetrable) {
			if (position == 0) {
				if (orientation == 0) {
					set(x, y, 0x10000); /* impenetrable wall west */
					set(x - 1, y, 0x1000); /* impenetrable wall east */
				}
				if (orientation == 1) {
					set(x, y, 0x400); /* impenetrable wall north */
					set(x, y + 1, 0x4000); /* impenetrable wall south */
				}
				if (orientation == 2) {
					set(x, y, 0x1000); /* impenetrable wall east */
					set(x + 1, y, 0x10000); /* impenetrable wall west */
				}
				if (orientation == 3) {
					set(x, y, 0x4000); /* impenetrable wall south */
					set(x, y - 1, 0x400); /* impenetrable wall north */
				}
			}
			if (position == 1 || position == 3) {
				if (orientation == 0) {
					set(x, y, 0x200); /* impenetrable wall northwest */
					set(x - 1, y + 1, 0x2000); /* impenetrable wall southeast */
				}
				if (orientation == 1) {
					set(x, y, 0x800); /* impenetrable wall northeast */
					set(x + 1, y + 1, 0x8000); /* impenetrable wall southwest */
				}
				if (orientation == 2) {
					set(x, y, 0x2000); /* impenetrable wall southeast */
					set(x + 1, y - 1, 0x200); /* impenetrable wall northwest */
				}
				if (orientation == 3) {
					set(x, y, 0x8000); /* impenetrable wall southwest */
					set(x - 1, y - 1, 0x800); /* impenetrable wall southwest */
				}
			}
			if (position == 2) {
				if (orientation == 0) {
					set(x, y, 0x10400); /* impenetrable wall west | north */
					set(x - 1, y, 0x1000); /* impenetrable wall east */
					set(x, y + 1, 0x4000); /* impenetrable wall south */
				}
				if (orientation == 1) {
					set(x, y, 0x1400); /* impenetrable wall east | north */
					set(x, y + 1, 0x4000); /* impenetrable wall south */
					set(x + 1, y, 0x10000); /* impenetrable wall west */
				}
				if (orientation == 2) {
					set(x, y, 0x5000); /* impenetrable wall east | south */
					set(x + 1, y, 0x10000); /* impenetrable wall west */
					set(x, y - 1, 0x400); /* impenetrable wall north */
				}
				if (orientation == 3) {
					set(x, y, 0x14000); /* impenetrable wall east | south */
					set(x, y - 1, 0x400); /* impenetrable wall west */
					set(x - 1, y, 0x1000); /* impenetrable wall north */
				}
			}
		}
	}

	public void markSolidOccupant(int x, int y, int w, int h, int orient, boolean impenetrable) {
		int occupied = 0x100; /* occupied by solid object */
		if (impenetrable) {
			occupied += 0x20000;
		}
		x -= insetX;
		y -= insetY;
		if (orient == 1 || orient == 3) {
			int tmp = w;
			w = h;
			h = tmp;
		}
		for (int l1 = x; l1 < x + w; l1++) {
			if (l1 >= 0 && l1 < width) {
				for (int i2 = y; i2 < y + h; i2++) {
					if (i2 >= 0 && i2 < height) {
						set(l1, i2, occupied);
					}
				}

			}
		}

	}

	public void markBlocked(int x, int y) {
		x -= insetX;
		y -= insetY;
		adjacency[x][y] |= 0x200000; /* blocked */
	}

	private void set(int x, int y, int flag) {
		adjacency[x][y] |= flag;
	}

	public void unmarkWall(int orientation, int x, int y, int position, boolean impenetrable) {
		x -= insetX;
		y -= insetY;
		if (position == 0) {
			if (orientation == 0) {
				unset(x, y, 0x80); /* wall west */
				unset(x - 1, y, 0x8); /* wall east */
			}
			if (orientation == 1) {
				unset(x, y, 0x2); /* wall north */
				unset(x, y + 1, 0x20); /* wall south */
			}
			if (orientation == 2) {
				unset(x, y, 0x8); /* wall east */
				unset(x + 1, y, 0x80); /* wall west */
			}
			if (orientation == 3) {
				unset(x, y, 0x20); /* wall south */
				unset(x, y - 1, 0x2); /* wall north */
			}
		}
		if (position == 1 || position == 3) {
			if (orientation == 0) {
				unset(x, y, 0x1); /* wall northwest */
				unset(x - 1, y + 1, 0x10); /* wall southeast */
			}
			if (orientation == 1) {
				unset(x, y, 0x4); /* wall northeast */
				unset(x + 1, y + 1, 0x40); /* wall southwest */
			}
			if (orientation == 2) {
				unset(x, y, 0x10); /* wall southeast */
				unset(x + 1, y - 1, 0x1); /* wall northwest */
			}
			if (orientation == 3) {
				unset(x, y, 0x40); /* wall southwest */
				unset(x - 1, y - 1, 0x4); /* wall southwest */
			}
		}
		if (position == 2) {
			if (orientation == 0) {
				unset(x, y, 0x82); /* wall west | north */
				unset(x - 1, y, 0x8); /* wall east */
				unset(x, y + 1, 0x20); /* wall south */
			}
			if (orientation == 1) {
				unset(x, y, 0xa); /* wall east | north */
				unset(x, y + 1, 0x20); /* wall south */
				unset(x + 1, y, 0x80); /* wall west */
			}
			if (orientation == 2) {
				unset(x, y, 0x28); /* wall east | south */
				unset(x + 1, y, 0x80); /* wall west */
				unset(x, y - 1, 0x2); /* wall north */
			}
			if (orientation == 3) {
				unset(x, y, 0xa0); /* wall east | south */
				unset(x, y - 1, 0x2); /* wall west */
				unset(x - 1, y, 0x8); /* wall north */
			}
		}
		if (impenetrable) {
			if (position == 0) {
				if (orientation == 0) {
					unset(x, y, 0x10000); /* impenetrable wall west */
					unset(x - 1, y, 0x1000); /* impenetrable wall east */
				}
				if (orientation == 1) {
					unset(x, y, 0x400); /* impenetrable wall north */
					unset(x, y + 1, 0x4000); /* impenetrable wall south */
				}
				if (orientation == 2) {
					unset(x, y, 0x1000); /* impenetrable wall east */
					unset(x + 1, y, 0x10000); /* impenetrable wall west */
				}
				if (orientation == 3) {
					unset(x, y, 0x4000); /* impenetrable wall south */
					unset(x, y - 1, 0x400); /* impenetrable wall north */
				}
			}
			if (position == 1 || position == 3) {
				if (orientation == 0) {
					unset(x, y, 0x200); /* impenetrable wall northwest */
					unset(x - 1, y + 1, 0x2000); /* impenetrable wall southeast */
				}
				if (orientation == 1) {
					unset(x, y, 0x800); /* impenetrable wall northeast */
					unset(x + 1, y + 1, 0x8000); /* impenetrable wall southwest */
				}
				if (orientation == 2) {
					unset(x, y, 0x2000); /* impenetrable wall southeast */
					unset(x + 1, y - 1, 0x200); /* impenetrable wall northwest */
				}
				if (orientation == 3) {
					unset(x, y, 0x8000); /* impenetrable wall southwest */
					unset(x - 1, y - 1, 0x800); /* impenetrable wall southwest */
				}
			}
			if (position == 2) {
				if (orientation == 0) {
					unset(x, y, 0x10400); /* impenetrable wall west | north */
					unset(x - 1, y, 0x1000); /* impenetrable wall east */
					unset(x, y + 1, 0x4000); /* impenetrable wall south */
				}
				if (orientation == 1) {
					unset(x, y, 0x1400); /* impenetrable wall east | north */
					unset(x, y + 1, 0x4000); /* impenetrable wall south */
					unset(x + 1, y, 0x10000); /* impenetrable wall west */
				}
				if (orientation == 2) {
					unset(x, y, 0x5000); /* impenetrable wall east | south */
					unset(x + 1, y, 0x10000); /* impenetrable wall west */
					unset(x, y - 1, 0x400); /* impenetrable wall north */
				}
				if (orientation == 3) {
					unset(x, y, 0x14000); /* impenetrable wall east | south */
					unset(x, y - 1, 0x400); /* impenetrable wall west */
					unset(x - 1, y, 0x1000); /* impenetrable wall north */
				}
			}
		}
	}

	public void unmarkSolidOccupant(int x, int y, int width, int height, int orientation, boolean impenetrable) {
		int occupied = 0x100; /* occupied by solid object */
		if (impenetrable) { /* occupied by impenetrable object */
			occupied += 131072;
		}
		x -= insetX;
		y -= insetY;
		if (orientation == 1 || orientation == 3) {
			int originalWidth = width;
			width = height;
			height = originalWidth;
		}
		for (int xCounter = x; xCounter < x + width; xCounter++) {
			if (xCounter >= 0 && xCounter < this.width) {
				for (int yCounter = y; yCounter < y + height; yCounter++) {
					if (yCounter >= 0 && yCounter < this.height) {
						unset(xCounter, yCounter, occupied);
					}
				}
			}
		}
	}

	private void unset(int x, int y, int flag) {
		adjacency[x][y] &= 16777215 - flag;
	}

	public void unmarkConcealed(int x, int y) {
		x -= insetX;
		y -= insetY;
		adjacency[x][y] &= 0xdfffff; /* not blocked */
	}

	public boolean reachedWall(int currentX, int currentY, int goalX, int goalY, int goalPosition, int goalOrientation) {
		if (currentX == goalX && currentY == goalY) {
			return true;
		}
		currentX -= insetX;
		currentY -= insetY;
		goalX -= insetX;
		goalY -= insetY;
		if (goalPosition == 0) {
			if (goalOrientation == 0) {
				if (currentX == goalX - 1 && currentY == goalY) {
					return true; /*
								 * uninitialized | blocked | ? | solid occupied
								 * | wall south
								 */
				}
				if (currentX == goalX && currentY == goalY + 1 && (adjacency[currentX][currentY] & 0x1280120) == 0) {
					return true; /*
								 * uninitialized | blocked | ? | solid occupied
								 * | wall north
								 */
				}
				if (currentX == goalX && currentY == goalY - 1 && (adjacency[currentX][currentY] & 0x1280102) == 0) {
					return true;
				}
			} else if (goalOrientation == 1) {
				if (currentX == goalX && currentY == goalY + 1) {
					return true; /*
								 * uninitialized | blocked | ? | solid occupied
								 * | wall east
								 */
				}
				if (currentX == goalX - 1 && currentY == goalY && (adjacency[currentX][currentY] & 0x1280108) == 0) {
					return true; /*
								 * uninitialized | blocked | ? | solid occupied
								 * | wall west
								 */
				}
				if (currentX == goalX + 1 && currentY == goalY && (adjacency[currentX][currentY] & 0x1280180) == 0) {
					return true;
				}
			} else if (goalOrientation == 2) {
				if (currentX == goalX + 1 && currentY == goalY) {
					return true; /*
								 * uninitialized | blocked | ? | solid occupied
								 * | wall south
								 */
				}
				if (currentX == goalX && currentY == goalY + 1 && (adjacency[currentX][currentY] & 0x1280120) == 0) {
					return true; /*
								 * uninitialized | blocked | ? | solid occupied
								 * | wall north
								 */
				}
				if (currentX == goalX && currentY == goalY - 1 && (adjacency[currentX][currentY] & 0x1280102) == 0) {
					return true;
				}
			} else if (goalOrientation == 3) {
				if (currentX == goalX && currentY == goalY - 1) {
					return true; /*
								 * uninitialized | blocked | ? | solid occupied
								 * | wall east
								 */
				}
				if (currentX == goalX - 1 && currentY == goalY && (adjacency[currentX][currentY] & 0x1280108) == 0) {
					return true; /*
								 * uninitialized | blocked | ? | solid occupied
								 * | wall west
								 */
				}
				if (currentX == goalX + 1 && currentY == goalY && (adjacency[currentX][currentY] & 0x1280180) == 0) {
					return true;
				}
			}
		}
		if (goalPosition == 2) {
			if (goalOrientation == 0) {
				if (currentX == goalX - 1 && currentY == goalY) {
					return true;
				}
				if (currentX == goalX && currentY == goalY + 1) {
					return true; /*
								 * uninitialized | blocked | ? | solid occupied
								 * | wall west
								 */
				}
				if (currentX == goalX + 1 && currentY == goalY && (adjacency[currentX][currentY] & 0x1280180) == 0) {
					return true; /*
								 * uninitialized | blocked | ? | solid occupied
								 * | wall north
								 */
				}
				if (currentX == goalX && currentY == goalY - 1 && (adjacency[currentX][currentY] & 0x1280102) == 0) {
					return true;
				}
			} else if (goalOrientation == 1) { /*
												 * uninitialized | blocked | ? |
												 * solid occupied | wall east
												 */
				if (currentX == goalX - 1 && currentY == goalY && (adjacency[currentX][currentY] & 0x1280108) == 0) {
					return true;
				}
				if (currentX == goalX && currentY == goalY + 1) {
					return true;
				}
				if (currentX == goalX + 1 && currentY == goalY) {
					return true; /*
								 * uninitialized | blocked | ? | solid occupied
								 * | wall north
								 */
				}
				if (currentX == goalX && currentY == goalY - 1 && (adjacency[currentX][currentY] & 0x1280102) == 0) {
					return true;
				}
			} else if (goalOrientation == 2) { /*
												 * uninitialized | blocked | ? |
												 * solid occupied | wall east
												 */
				if (currentX == goalX - 1 && currentY == goalY && (adjacency[currentX][currentY] & 0x1280108) == 0) {
					return true; /*
								 * uninitialized | blocked | ? | solid occupied
								 * | wall south
								 */
				}
				if (currentX == goalX && currentY == goalY + 1 && (adjacency[currentX][currentY] & 0x1280120) == 0) {
					return true;
				}
				if (currentX == goalX + 1 && currentY == goalY) {
					return true;
				}
				if (currentX == goalX && currentY == goalY - 1) {
					return true;
				}
			} else if (goalOrientation == 3) {
				if (currentX == goalX - 1 && currentY == goalY) {
					return true; /*
								 * uninitialized | blocked | ? | solid occupied
								 * | wall south
								 */
				}
				if (currentX == goalX && currentY == goalY + 1 && (adjacency[currentX][currentY] & 0x1280120) == 0) {
					return true; /*
								 * uninitialized | blocked | ? | solid occupied
								 * | wall west
								 */
				}
				if (currentX == goalX + 1 && currentY == goalY && (adjacency[currentX][currentY] & 0x1280180) == 0) {
					return true;
				}
				if (currentX == goalX && currentY == goalY - 1) {
					return true;
				}
			}
		}
		if (goalPosition == 9) { /* wall south */
			if (currentX == goalX && currentY == goalY + 1 && (adjacency[currentX][currentY] & 0x20) == 0) {
				return true; /* wall north */
			}
			if (currentX == goalX && currentY == goalY - 1 && (adjacency[currentX][currentY] & 0x2) == 0) {
				return true; /* wall east */
			}
			if (currentX == goalX - 1 && currentY == goalY && (adjacency[currentX][currentY] & 0x8) == 0) {
				return true; /* wall west */
			}
			if (currentX == goalX + 1 && currentY == goalY && (adjacency[currentX][currentY] & 0x80) == 0) {
				return true;
			}
		}
		return false;
	}

	public boolean reachedWallDecoration(int currentX, int currentY, int goalX, int goalY, int goalPosition,
			int goalOrientation) {
		if (currentX == goalX && currentY == goalY) {
			return true;
		}
		currentX -= insetX;
		currentY -= insetY;
		goalX -= insetX;
		goalY -= insetY;
		if (goalPosition == 6 || goalPosition == 7) {
			if (goalPosition == 7) {
				goalOrientation = goalOrientation + 2 & 3;
			}
			if (goalOrientation == 0) { /* wall west */
				if (currentX == goalX + 1 && currentY == goalY && (adjacency[currentX][currentY] & 0x80) == 0) {
					return true; /* wall north */
				}
				if (currentX == goalX && currentY == goalY - 1 && (adjacency[currentX][currentY] & 0x2) == 0) {
					return true;
				}
			} else if (goalOrientation == 1) { /* wall east */
				if (currentX == goalX - 1 && currentY == goalY && (adjacency[currentX][currentY] & 0x8) == 0) {
					return true; /* wall north */
				}
				if (currentX == goalX && currentY == goalY - 1 && (adjacency[currentX][currentY] & 0x2) == 0) {
					return true;
				}
			} else if (goalOrientation == 2) { /* wall east */
				if (currentX == goalX - 1 && currentY == goalY && (adjacency[currentX][currentY] & 0x8) == 0) {
					return true; /* wall south */
				}
				if (currentX == goalX && currentY == goalY + 1 && (adjacency[currentX][currentY] & 0x20) == 0) {
					return true;
				}
			} else if (goalOrientation == 3) { /* wall west */
				if (currentX == goalX + 1 && currentY == goalY && (adjacency[currentX][currentY] & 0x80) == 0) {
					return true; /* wall south */
				}
				if (currentX == goalX && currentY == goalY + 1 && (adjacency[currentX][currentY] & 0x20) == 0) {
					return true;
				}
			}
		}
		if (goalPosition == 8) { /* wall south */
			if (currentX == goalX && currentY == goalY + 1 && (adjacency[currentX][currentY] & 0x20) == 0) {
				return true; /* wall north */
			}
			if (currentX == goalX && currentY == goalY - 1 && (adjacency[currentX][currentY] & 0x2) == 0) {
				return true; /* wall east */
			}
			if (currentX == goalX - 1 && currentY == goalY && (adjacency[currentX][currentY] & 0x8) == 0) {
				return true; /* wall west */
			}
			if (currentX == goalX + 1 && currentY == goalY && (adjacency[currentX][currentY] & 0x80) == 0) {
				return true;
			}
		}
		return false;
	}

	public boolean reachedFacingObject(int currentX, int currentY, int goalX, int goalY, int goalDX, int goalDY,
			int surroundings) {
		int goalX2 = (goalX + goalDX) - 1;
		int goalY2 = (goalY + goalDY) - 1;
		if (currentX >= goalX && currentX <= goalX2 && currentY >= goalY && currentY <= goalY2) {
			return true;
		}
		if (currentX == goalX - 1 && currentY >= goalY && currentY <= goalY2
		/* wall east *//* surrounding blocked west */
		&& (adjacency[currentX - insetX][currentY - insetY] & 0x8) == 0 && (surroundings & 8) == 0) {
			return true;
		}
		if (currentX == goalX2 + 1 && currentY >= goalY && currentY <= goalY2
		/* wall west *//* surrounding blocked east */
		&& (adjacency[currentX - insetX][currentY - insetY] & 0x80) == 0 && (surroundings & 2) == 0) {
			return true;
		}
		if (currentY == goalY - 1 && currentX >= goalX && currentX <= goalX2
		/* wall north *//* surrounding blocked south */
		&& (adjacency[currentX - insetX][currentY - insetY] & 0x2) == 0 && (surroundings & 4) == 0) {
			return true;
		}
		return currentY == goalY2 + 1 && currentX >= goalX && currentX <= goalX2
		/* wall south *//* surrounding blocked north */
		&& (adjacency[currentX - insetX][currentY - insetY] & 0x20) == 0 && (surroundings & 1) == 0;
	}
}
