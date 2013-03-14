package com.runescape.scene;

import com.runescape.cache.media.Model;
import com.runescape.collection.LinkedList;
import com.runescape.media.Rasterizer;
import com.runescape.media.Rasterizer3D;
import com.runescape.media.VertexNormal;
import com.runescape.media.renderable.Renderable;
import com.runescape.scene.tile.ComplexTile;
import com.runescape.scene.tile.FloorDecoration;
import com.runescape.scene.tile.GenericTile;
import com.runescape.scene.tile.SceneTile;
import com.runescape.scene.tile.Wall;
import com.runescape.scene.tile.WallDecoration;
import com.runescape.util.SignLink;

public class Scene {

	private int anInt510;
	private boolean aBoolean515 = false;
	public static boolean lowMemory = true;
	protected int planes;
	protected int width;
	protected int height;
	protected int[][][] tileHeightMap;
	protected SceneTile[][][] tiles;
	protected int currentPlane;
	protected int currentSceneSpawnRequest;
	protected SceneSpawnRequest[] sceneSpawnRequests = new SceneSpawnRequest[5000];
	protected int[][][] anIntArrayArrayArray525;
	static int anInt526;
	static int anInt527;
	static int anInt528;
	static int anInt529;
	static int anInt530;
	static int anInt531;
	static int anInt532;
	static int anInt533;
	static int anInt534;
	static int cameraX;
	static int cameraZ;
	static int cameraY;
	static int sineCurveY;
	static int cosineCurveY;
	static int sineCurveX;
	static int cosineCurveX;
	static SceneSpawnRequest[] aSceneSpawnRequestArray542 = new SceneSpawnRequest[100];
	static final int[] anIntArray543 = { 53, -53, -53, 53 };
	static final int[] anIntArray544 = { -53, -53, 53, 53 };
	static final int[] anIntArray545 = { -45, 45, 45, -45 };
	static final int[] anIntArray546 = { 45, 45, -45, -45 };
	static boolean aBoolean547;
	static int clickX;
	static int clickY;
	public static int tileClickX = -1;
	public static int tileClickY = -1;
	static int anInt552 = 4;
	static int[] cullingClusterPointer = new int[anInt552];
	static SceneCluster[][] cullingClusters = new SceneCluster[anInt552][500];
	public static int anInt555;
	static SceneCluster[] aSceneClusterArray556 = new SceneCluster[500];
	static LinkedList aLinkedList557 = new LinkedList();
	static final int[] wallVisibleBackground = { 19, 55, 38, 155, 255, 110, 137, 205, 76 };
	static final int[] anIntArray559 = { 160, 192, 80, 96, 0, 144, 80, 48, 160 };
	static final int[] anIntArray560 = { 76, 8, 137, 4, 0, 1, 38, 2, 19 };
	static final int[] anIntArray561 = { 0, 0, 2, 0, 0, 2, 1, 1, 0 };
	static final int[] anIntArray562 = { 2, 0, 0, 2, 0, 0, 0, 4, 4 };
	static final int[] anIntArray563 = { 0, 4, 4, 8, 0, 0, 8, 0, 0 };
	static final int[] anIntArray564 = { 1, 1, 0, 0, 0, 8, 0, 0, 8 };
	static final int[] anIntArray565 = { 41, 39248, 41, 4643, 41, 41, 41, 41, 41, 41, 41, 41, 41, 41, 41, 43086, 41,
			41, 41, 41, 41, 41, 41, 8602, 41, 28992, 41, 41, 41, 41, 41, 5056, 41, 41, 41, 7079, 41, 41, 41, 41, 41,
			41, 41, 41, 41, 41, 3131, 41, 41, 41 };
	protected int[] anIntArray566 = new int[10000];
	protected int[] anIntArray567 = new int[10000];
	protected int anInt568;
	protected int[][] tileShapePoints = { new int[16], { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 },
			{ 1, 0, 0, 0, 1, 1, 0, 0, 1, 1, 1, 0, 1, 1, 1, 1 }, { 1, 1, 0, 0, 1, 1, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0 },
			{ 0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 0, 1, 0, 0, 0, 1 }, { 0, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 },
			{ 1, 1, 1, 0, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1 }, { 1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0 },
			{ 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 1, 0, 0 }, { 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 1, 0, 0, 1, 1 },
			{ 1, 1, 1, 1, 1, 1, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 1, 1, 0, 1, 1, 1, 0, 1, 1, 1 },
			{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 1, 1, 1, 1 } };
	protected int[][] tileShapeRotationIndices = { { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15 },
			{ 12, 8, 4, 0, 13, 9, 5, 1, 14, 10, 6, 2, 15, 11, 7, 3 },
			{ 15, 14, 13, 12, 11, 10, 9, 8, 7, 6, 5, 4, 3, 2, 1, 0 },
			{ 3, 7, 11, 15, 2, 6, 10, 14, 1, 5, 9, 13, 0, 4, 8, 12 } };
	static boolean[][][][] tileVisibilityMap = new boolean[8][32][51][51];
	static boolean[][] aBooleanArrayArray572;
	static int midX;
	static int midY;
	static int left;
	static int top;
	static int right;
	static int bottom;

	public Scene(int height, int width, int[][][] is, int planes) {
		this.planes = planes; // x, y, z or x, z, y depending on how you like to consider the dimensions. not sure how they're supposed to be.
		this.width = width;
		this.height = height;
		tiles = new SceneTile[planes][width][height];
		anIntArrayArrayArray525 = new int[planes][width + 1][height + 1];
		tileHeightMap = is;
		initialize();
	}

	public static void releaseReferences() {
		aSceneSpawnRequestArray542 = null;
		cullingClusterPointer = null;
		cullingClusters = null;
		aLinkedList557 = null;
		tileVisibilityMap = null;
		aBooleanArrayArray572 = null;
	}

	public void initialize() {
		for (int z = 0; z < planes; z++) {
			for (int x = 0; x < width; x++) {
				for (int y = 0; y < height; y++) {
					tiles[z][x][y] = null;
				}
			}
		}
		for (int i_5_ = 0; i_5_ < anInt552; i_5_++) {
			for (int i_6_ = 0; i_6_ < cullingClusterPointer[i_5_]; i_6_++) {
				cullingClusters[i_5_][i_6_] = null;
			}
			cullingClusterPointer[i_5_] = 0;
		}
		for (int i_7_ = 0; i_7_ < currentSceneSpawnRequest; i_7_++) {
			sceneSpawnRequests[i_7_] = null;
		}
		currentSceneSpawnRequest = 0;
		for (int i_8_ = 0; i_8_ < aSceneSpawnRequestArray542.length; i_8_++) {
			aSceneSpawnRequestArray542[i_8_] = null;
		}
	}

	public void setPlane(int plane) {
		currentPlane = plane;
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				if (tiles[plane][x][y] == null) {
					tiles[plane][x][y] = new SceneTile(plane, x, y);
				}
			}
		}
	}

	public void setBridgeMode(int y, int x) {
		SceneTile scenetile = tiles[0][x][y];
		for (int plane = 0; plane < 3; plane++) {
			SceneTile tile = tiles[plane][x][y] = tiles[plane + 1][x][y];
			if (tile != null) {
				tile.plane2--;
				for (int i = 0; i < tile.sceneSpawnRequestCount; i++) {
					SceneSpawnRequest request = tile.sceneSpawnRequests[i];
					if ((request.hash >> 29 & 0x3) == 2 && request.x == x && request.y == y) {
						request.plane--;
					}
				}
			}
		}
		if (tiles[0][x][y] == null) {
			tiles[0][x][y] = new SceneTile(0, x, y);
		}
		tiles[0][x][y].sceneTile = scenetile;
		tiles[3][x][y] = null;
	}

	public static void createCullingOcclussionBox(int i, int i_18_, int i_19_, int i_20_, int i_21_, int i_22_,  int i_24_, int i_25_) {
		SceneCluster scenecluster = new SceneCluster();
		scenecluster.anInt579 = i_18_ / 128;
		scenecluster.anInt580 = i_20_ / 128;
		scenecluster.anInt581 = i_24_ / 128;
		scenecluster.anInt582 = i_21_ / 128;
		scenecluster.anInt583 = i_25_;
		scenecluster.anInt584 = i_18_;
		scenecluster.anInt585 = i_20_;
		scenecluster.anInt586 = i_24_;
		scenecluster.anInt587 = i_21_;
		scenecluster.anInt588 = i_22_;
		scenecluster.anInt589 = i_19_;
		cullingClusters[i][cullingClusterPointer[i]++] = scenecluster;
	}

	public void setTileLogicalHeight(int plane, int x, int y, int logicalHeight) {
		SceneTile scenetile = tiles[plane][x][y];
		if (scenetile != null) {
			tiles[plane][x][y].logicalHeight = logicalHeight;
		}
	}

	public void method501(int i, int i_29_, int i_30_, int shape, int i_32_, int i_33_, int i_34_, int i_35_,
			int i_36_, int i_37_, int i_38_, int i_39_, int i_40_, int i_41_, int i_42_, int i_43_, int i_44_,
			int i_45_, int i_46_, int i_47_) {
		if (shape == 0) {
			GenericTile generictile = new GenericTile(i_38_, i_39_, i_40_, i_41_, -1, i_46_, false);
			for (int i_48_ = i; i_48_ >= 0; i_48_--) {
				if (tiles[i_48_][i_29_][i_30_] == null) {
					tiles[i_48_][i_29_][i_30_] = new SceneTile(i_48_, i_29_, i_30_);
				}
			}
			tiles[i][i_29_][i_30_].genericTile = generictile;
		} else if (shape == 1) {
			GenericTile generictile = new GenericTile(i_42_, i_43_, i_44_, i_45_, i_33_, i_47_, i_34_ == i_35_
					&& i_34_ == i_36_ && i_34_ == i_37_);
			for (int i_49_ = i; i_49_ >= 0; i_49_--) {
				if (tiles[i_49_][i_29_][i_30_] == null) {
					tiles[i_49_][i_29_][i_30_] = new SceneTile(i_49_, i_29_, i_30_);
				}
			}
			tiles[i][i_29_][i_30_].genericTile = generictile;
		} else {
			ComplexTile complextile = new ComplexTile(i_30_, i_42_, i_41_, i_36_, i_33_, i_44_, i_32_, i_38_, i_46_,
					i_40_, i_37_, i_35_, i_34_, shape, i_45_, i_43_, i_39_, 3, i_29_, i_47_);
			for (int i_50_ = i; i_50_ >= 0; i_50_--) {
				if (tiles[i_50_][i_29_][i_30_] == null) {
					tiles[i_50_][i_29_][i_30_] = new SceneTile(i_50_, i_29_, i_30_);
				}
			}
			tiles[i][i_29_][i_30_].complexTile = complextile;
		}
	}

	public void addGroundDecoration(int i, int i_51_, int i_52_, int i_53_, Renderable renderable, byte b, int i_54_, int i_55_) {
		if (renderable != null) {
			FloorDecoration floordecoration = new FloorDecoration();
			floordecoration.renderable = renderable;
			floordecoration.y = i_55_ * 128 + 64;
			floordecoration.z = i_52_ * 128 + 64;
			if (i_53_ <= 0) {
				aBoolean515 = !aBoolean515;
			}
			floordecoration.x = i_51_;
			floordecoration.hash = i_54_;
			floordecoration.config = b;
			if (tiles[i][i_55_][i_52_] == null) {
				tiles[i][i_55_][i_52_] = new SceneTile(i, i_55_, i_52_);
			}
			tiles[i][i_55_][i_52_].floorDecoration = floordecoration;
		}
	}

	public void method503(int x, int i_56_, Renderable renderable, int i_57_, Renderable renderable_58_, Renderable renderable_59_, int plane, int y) {
		CameraAngle cameraAngle = new CameraAngle(); // grounditemtile in rn317. i don't think cameraangle is right.
		cameraAngle.aRenderable150 = renderable_59_;
		cameraAngle.y = x * 128 + 64;
		cameraAngle.z = y * 128 + 64;
		cameraAngle.x = i_57_; // this naming has to be all wrong, since y = x, z = y... what?
		cameraAngle.anInt153 = i_56_;
		cameraAngle.aRenderable151 = renderable;
		cameraAngle.aRenderable152 = renderable_58_;
		int i_62_ = 0;
		SceneTile scenetile = tiles[plane][x][y];
		if (scenetile != null) {
			for (int requestID = 0; requestID < scenetile.sceneSpawnRequestCount; requestID++) {
				if (scenetile.sceneSpawnRequests[requestID].renderable instanceof Model) {
					int i_64_ = ((Model) scenetile.sceneSpawnRequests[requestID].renderable).anInt1647;
					if (i_64_ > i_62_) {
						i_62_ = i_64_;
					}
				}
			}
		}
		cameraAngle.anInt154 = i_62_;
		if (tiles[plane][x][y] == null) {
			tiles[plane][x][y] = new SceneTile(plane, x, y);
		}
		tiles[plane][x][y].cameraAngle = cameraAngle;
	}

	public void method504(int faceUnknown, Renderable renderable, int hash, int y, byte config, int x, Renderable renderable_68_, int plane, int face, int i_71_) {
		if (renderable != null || renderable_68_ != null) {
			Wall wall = new Wall();
			wall.hash = hash;
			wall.config = config;
			wall.x = x * 128 + 64;
			wall.y = y * 128 + 64;
			wall.plane = plane;
			wall.aRenderable769 = renderable;
			wall.aRenderable770 = renderable_68_;
			wall.faceUnknown = faceUnknown;
			wall.face = face;
			for (int i_72_ = i_71_; i_72_ >= 0; i_72_--) {
				if (tiles[i_72_][x][y] == null) {
					tiles[i_72_][x][y] = new SceneTile(i_72_, x, y);
				}
			}
			tiles[i_71_][x][y].wall = wall;
		}
	}

	public void addWallDecoration(int x, int xOffset, int y, int yOffset, int z, int plane, int face, int faceUnknown,
			byte config, int hash, Renderable renderable) {
		if (renderable != null) {
			WallDecoration wallDecoration = new WallDecoration();
			wallDecoration.hash = hash;
			wallDecoration.config = config;
			wallDecoration.y = x * 128 + 64 + xOffset;
			wallDecoration.x = y * 128 + 64 + yOffset;
			wallDecoration.z = z;
			wallDecoration.renderable = renderable;
			wallDecoration.hostOrientation = faceUnknown;
			wallDecoration.orientation = face;
			for (int planeCounter = plane; planeCounter >= 0; planeCounter--) {
				if (tiles[planeCounter][x][y] == null) {
					tiles[planeCounter][x][y] = new SceneTile(planeCounter, x, y);
				}
			}
			tiles[plane][x][y].wallDecoration = wallDecoration;
		}
	}

	public boolean createOccupant(int i, byte config, int i_83_, int i_84_, Renderable renderable, int i_85_,
			int i_86_, int i_87_, int i_89_, int i_90_) {
		if (renderable == null) {
			return true;
		}
		int i_91_ = i_90_ * 128 + 64 * i_85_;
		int i_92_ = i_89_ * 128 + 64 * i_84_;
		return method509(i_86_, i_90_, i_89_, i_85_, i_84_, i_91_, i_92_, i_83_, renderable, i_87_, i, config);
	}

	public boolean method507(int plane, int rotation, int i_94_, int i_95_, int i_96_, int i_97_, int i_98_,
			Renderable renderable, boolean bool) {
		try {
			if (renderable == null) {
				return true;
			}
			int i_99_ = i_98_ - i_97_;
			int i_100_ = i_96_ - i_97_;
			int i_101_ = i_98_ + i_97_;
			int i_102_ = i_96_ + i_97_;
			if (bool) {
				if (rotation > 640 && rotation < 1408) {
					i_102_ += 128;
				}
				if (rotation > 1152 && rotation < 1920) {
					i_101_ += 128;
				}
				if (rotation > 1664 || rotation < 384) {
					i_100_ -= 128;
				}
				if (rotation > 128 && rotation < 896) {
					i_99_ -= 128;
				}
			}
			i_99_ /= 128;
			i_100_ /= 128;
			i_101_ /= 128;
			i_102_ /= 128;
			return method509(plane, i_99_, i_100_, i_101_ - i_99_ + 1, i_102_ - i_100_ + 1, i_98_, i_96_, i_94_,
					renderable, rotation, i_95_, (byte) 0);
		} catch (RuntimeException runtimeexception) {
			SignLink.reportError("2234, " + plane + ", " + rotation + ", " + i_94_ + ", " + i_95_ + ", " + i_96_
					+ ", " + i_97_ + ", " + i_98_ + ", " + renderable + ", " + bool + ", "
					+ runtimeexception.toString());
			throw new RuntimeException();
		}
	}

	public boolean method508(int i, int plane, int i_104_, Renderable renderable, int i_105_, int i_106_, int i_107_, int i_108_, int i_109_, int i_110_, int i_111_, int i_112_) {
			if (renderable == null) {
				return true;
			}
			return method509(plane, i_109_, i_112_, i_110_ - i_109_ + 1, i_106_ - i_112_ + 1, i_107_, i_104_, i_108_, renderable, i_105_, i_111_, (byte) 0);
	}

	private boolean method509(int plane, int x, int y, int sizeX, int sizeY, int i_118_, int i_119_, int i_120_, Renderable renderable, int i_121_, int i_122_, byte config) {
		for (int tileX = x; tileX < x + sizeX; tileX++) {
			for (int tileY = y; tileY < y + sizeY; tileY++) {
				if (tileX < 0 || tileY < 0 || tileX >= width || tileY >= height) {
					return false;
				}
				SceneTile sceneTile = tiles[plane][tileX][tileY];
				if (sceneTile != null && sceneTile.sceneSpawnRequestCount >= 5) {
					return false;
				}
			}
		}
		SceneSpawnRequest sceneSpawnRequest = new SceneSpawnRequest();
		sceneSpawnRequest.hash = i_122_;
		sceneSpawnRequest.config = config;
		sceneSpawnRequest.plane = plane;
		sceneSpawnRequest.anInt599 = i_118_;
		sceneSpawnRequest.anInt600 = i_119_;
		sceneSpawnRequest.anInt598 = i_120_;
		sceneSpawnRequest.renderable = renderable;
		sceneSpawnRequest.anInt602 = i_121_;
		sceneSpawnRequest.x = x;
		sceneSpawnRequest.y = y;
		sceneSpawnRequest.sizeX = x + sizeX - 1;
		sceneSpawnRequest.sizeY = y + sizeY - 1;
		for (int tileX = x; tileX < x + sizeX; tileX++) {
			for (int tileY = y; tileY < y + sizeY; tileY++) {
				int sceneSpawnRequestSize = 0;
				if (tileX > x) {
					sceneSpawnRequestSize++;
				}
				if (tileX < x + sizeX - 1) {
					sceneSpawnRequestSize += 4;
				}
				if (tileY > y) {
					sceneSpawnRequestSize += 8;
				}
				if (tileY < y + sizeY - 1) {
					sceneSpawnRequestSize += 2;
				}
				for (int tilePlane = plane; tilePlane >= 0; tilePlane--) {
					if (tiles[tilePlane][tileX][tileY] == null) {
						tiles[tilePlane][tileX][tileY] = new SceneTile(tilePlane, tileX, tileY);
					}
				}
				SceneTile sceneTile = tiles[plane][tileX][tileY];
				sceneTile.sceneSpawnRequests[sceneTile.sceneSpawnRequestCount] = sceneSpawnRequest;
				sceneTile.sceneSpawnRequestsSize[sceneTile.sceneSpawnRequestCount] = sceneSpawnRequestSize;
				sceneTile.sceneSpawnRequestSizeOR |= sceneSpawnRequestSize;
				sceneTile.sceneSpawnRequestCount++;
			}
		}
		sceneSpawnRequests[currentSceneSpawnRequest++] = sceneSpawnRequest;
		return true;
	}

	public void clearSceneSpawnRequests() {
		for (int index = 0; index < currentSceneSpawnRequest; index++) {
			SceneSpawnRequest sceneSpawnRequest = sceneSpawnRequests[index];
			removeSceneSpawnRequest(sceneSpawnRequest);
			sceneSpawnRequests[index] = null;
		}
		currentSceneSpawnRequest = 0;
	}

	private void removeSceneSpawnRequest(SceneSpawnRequest sceneSpawnRequest) {
		for (int x = sceneSpawnRequest.x; x <= sceneSpawnRequest.sizeX; x++) {
			for (int y = sceneSpawnRequest.y; y <= sceneSpawnRequest.sizeY; y++) {
				SceneTile sceneTile = tiles[sceneSpawnRequest.plane][x][y];
				if (sceneTile != null) {
					for (int index = 0; index < sceneTile.sceneSpawnRequestCount; index++) {
						if (sceneTile.sceneSpawnRequests[index] == sceneSpawnRequest) {
							sceneTile.sceneSpawnRequestCount--;
							for (int offset = index; offset < sceneTile.sceneSpawnRequestCount; offset++) {
								sceneTile.sceneSpawnRequests[offset] = sceneTile.sceneSpawnRequests[offset + 1];
								sceneTile.sceneSpawnRequestsSize[offset] = sceneTile.sceneSpawnRequestsSize[offset + 1];
							}
							sceneTile.sceneSpawnRequests[sceneTile.sceneSpawnRequestCount] = null;
							break;
						}
					}
					sceneTile.sceneSpawnRequestSizeOR = 0;
					for (int index = 0; index < sceneTile.sceneSpawnRequestCount; index++) {
						sceneTile.sceneSpawnRequestSizeOR |= sceneTile.sceneSpawnRequestsSize[index];
					}
				}
			}
		}
	}

	public void method512(int y, int i_135_, int x, int plane) {
		SceneTile scenetile = tiles[plane][x][y];
		if (scenetile != null) {
			WallDecoration walldecoration = scenetile.wallDecoration;
			if (walldecoration != null) {
				int i_138_ = x * 128 + 64;
				int i_139_ = y * 128 + 64;
				walldecoration.y = i_138_ + (walldecoration.y - i_138_) * i_135_ / 16;
				walldecoration.x = i_139_ + (walldecoration.x - i_139_) * i_135_ / 16;
			}
		}
	}

	public void removeWall(int x, int plane, int y) {
		SceneTile scenetile = tiles[plane][x][y];
		if (scenetile != null) {
			scenetile.wall = null;
		}
	}

	public void removeWallDecoration(int y, int plane, int x) {
		SceneTile scenetile = tiles[plane][x][y];
		if (scenetile != null) {
			scenetile.wallDecoration = null;
		}
	}

	public void method515(int plane, int x, int y) {
		SceneTile scenetile = tiles[plane][x][y];
		if (scenetile != null) {
			for (int i_149_ = 0; i_149_ < scenetile.sceneSpawnRequestCount; i_149_++) {
				SceneSpawnRequest scenespawnrequest = scenetile.sceneSpawnRequests[i_149_];
				if ((scenespawnrequest.hash >> 29 & 0x3) == 2 && scenespawnrequest.x == x && scenespawnrequest.y == y) {
					removeSceneSpawnRequest(scenespawnrequest);
					break;
				}
			}
		}
	}

	public void removeFloorDecoration(int plane, int y, int x) {
		SceneTile scenetile = tiles[plane][x][y];
		if (scenetile != null) {
			scenetile.floorDecoration = null;
		}
	}
	
	// TODO: figure out what "camera angle" is supposed to be for, i assume not a camera.
	public void resetCameraAngle(int plane, int x, int y) {
		SceneTile sceneTile = tiles[plane][x][y];
		if (sceneTile != null) {
			sceneTile.cameraAngle = null;
		}
	}

	public Wall getWall(int plane, int x, int y) {
		SceneTile sceneTile = tiles[plane][x][y];
		if (sceneTile == null) {
			return null;
		}
		return sceneTile.wall;
	}

	public WallDecoration getWallDecoration(int plane, int x, int y) {
		SceneTile scenetile = tiles[plane][x][y];
		if (scenetile == null) {
			return null;
		}
		return scenetile.wallDecoration;
	}

	public SceneSpawnRequest processSceneSpawnRequests(int plane, int x, int y) {
		SceneTile sceneTile = tiles[plane][x][y];
		if (sceneTile == null) {
			return null;
		}
		for (int sceneSpawnRequestId = 0; sceneSpawnRequestId < sceneTile.sceneSpawnRequestCount; sceneSpawnRequestId++) {
			SceneSpawnRequest sceneSpawnRequest = sceneTile.sceneSpawnRequests[sceneSpawnRequestId];
			if ((sceneSpawnRequest.hash >> 29 & 0x3) == 2 && sceneSpawnRequest.x == x && sceneSpawnRequest.y == y) {
				return sceneSpawnRequest;
			}
		}
		return null;
	}

	public FloorDecoration getFloorDecoration(int plane, int x, int y) {
		SceneTile sceneTile = tiles[plane][x][y];
		if (sceneTile == null || sceneTile.floorDecoration == null) {
			return null;
		}
		return sceneTile.floorDecoration;
	}

	public int getWallHash(int plane, int x, int y) {
		SceneTile scenetile = tiles[plane][x][y];
		if (scenetile == null || scenetile.wall == null) {
			return 0;
		}
		return scenetile.wall.hash;
	}

	public int getWallDecorationHash(int plane, int x, int y) {
		SceneTile sceneTile = tiles[plane][x][y];
		if (sceneTile == null || sceneTile.wallDecoration == null) {
			return 0;
		}
		return sceneTile.wallDecoration.hash;
	}

	public int method524(int plane, int x, int y) {
		SceneTile scenetile = tiles[plane][x][y];
		if (scenetile == null) {
			return 0;
		}
		for (int i = 0; i < scenetile.sceneSpawnRequestCount; i++) {
			SceneSpawnRequest scenespawnrequest = scenetile.sceneSpawnRequests[i];
			if ((scenespawnrequest.hash >> 29 & 0x3) == 2 && scenespawnrequest.x == x && scenespawnrequest.y == y) {
				return scenespawnrequest.hash;
			}
		}
		return 0;
	}

	public int getFloorDecorationHash(int plane, int x, int y) {
		SceneTile scenetile = tiles[plane][x][y];
		if (scenetile == null || scenetile.floorDecoration == null) {
			return 0;
		}
		return scenetile.floorDecoration.hash;
	}

	public int getConfig(int plane, int x, int y, int hash) {
		SceneTile sceneTile = tiles[plane][x][y];
		if (sceneTile == null) {
			return -1;
		}
		if (sceneTile.wall != null && sceneTile.wall.hash == hash) {
			return sceneTile.wall.config & 0xff;
		}
		if (sceneTile.wallDecoration != null && sceneTile.wallDecoration.hash == hash) {
			return sceneTile.wallDecoration.config & 0xff;
		}
		if (sceneTile.floorDecoration != null && sceneTile.floorDecoration.hash == hash) {
			return sceneTile.floorDecoration.config & 0xff;
		}
		for (int sceneSpawnRequest = 0; sceneSpawnRequest < sceneTile.sceneSpawnRequestCount; sceneSpawnRequest++) {
			if (sceneTile.sceneSpawnRequests[sceneSpawnRequest].hash == hash) {
				return sceneTile.sceneSpawnRequests[sceneSpawnRequest].config & 0xff;
			}
		}
		return -1;
	}

	// shading
	public void method527(int i, int i_180_, int i_181_, int i_182_, int i_183_) {
		int i_184_ = (int) Math.sqrt(i_181_ * i_181_ + i * i + i_183_ * i_183_);
		int i_185_ = i_182_ * i_184_ >> 8;
		for (int i_186_ = 0; i_186_ < planes; i_186_++) {
			for (int i_187_ = 0; i_187_ < width; i_187_++) {
				for (int i_188_ = 0; i_188_ < height; i_188_++) {
					SceneTile scenetile = tiles[i_186_][i_187_][i_188_];
					if (scenetile != null) {
						Wall wall = scenetile.wall;
						if (wall != null && wall.aRenderable769 != null
								&& wall.aRenderable769.verticesNormal != null) {
							snapObjectVertices(i_186_, 1, 1, i_187_, i_188_, (Model) wall.aRenderable769);
							if (wall.aRenderable770 != null && wall.aRenderable770.verticesNormal != null) {
								snapObjectVertices(i_186_, 1, 1, i_187_, i_188_, (Model) wall.aRenderable770);
								method530((Model) wall.aRenderable769, (Model) wall.aRenderable770, 0, 0, 0, false);
								((Model) wall.aRenderable770).delayShade(i_180_, i_185_, i_181_, i, i_183_);
							}
							((Model) wall.aRenderable769).delayShade(i_180_, i_185_, i_181_, i, i_183_);
						}
						for (int i_189_ = 0; i_189_ < scenetile.sceneSpawnRequestCount; i_189_++) {
							SceneSpawnRequest scenespawnrequest = scenetile.sceneSpawnRequests[i_189_];
							if (scenespawnrequest != null && scenespawnrequest.renderable != null
									&& scenespawnrequest.renderable.verticesNormal != null) {
								snapObjectVertices(i_186_, scenespawnrequest.sizeX - scenespawnrequest.x + 1,
										scenespawnrequest.sizeY - scenespawnrequest.y + 1, i_187_,
										i_188_, (Model) scenespawnrequest.renderable);
								((Model) scenespawnrequest.renderable)
										.delayShade(i_180_, i_185_, i_181_, i, i_183_);
							}
						}
						FloorDecoration floordecoration = scenetile.floorDecoration;
						if (floordecoration != null && floordecoration.renderable.verticesNormal != null) {
							method528(i_187_, i_186_, (Model) floordecoration.renderable, i_188_);
							((Model) floordecoration.renderable).delayShade(i_180_, i_185_, i_181_, i, i_183_);
						}
					}
				}
			}
		}
	}

	private void method528(int x, int plane, Model model, int y) {
		if (x < width) {
			SceneTile scenetile = tiles[plane][x + 1][y];
			if (scenetile != null && scenetile.floorDecoration != null
					&& scenetile.floorDecoration.renderable.verticesNormal != null) {
				method530(model, (Model) scenetile.floorDecoration.renderable, 128, 0, 0, true);
			}
		}
		if (y < width) {
			SceneTile scenetile = tiles[plane][x][y + 1];
			if (scenetile != null && scenetile.floorDecoration != null
					&& scenetile.floorDecoration.renderable.verticesNormal != null) {
				method530(model, (Model) scenetile.floorDecoration.renderable, 0, 0, 128, true);
			}
		}
		if (x < width && y < height) {
			SceneTile scenetile = tiles[plane][x + 1][y + 1];
			if (scenetile != null && scenetile.floorDecoration != null
					&& scenetile.floorDecoration.renderable.verticesNormal != null) {
				method530(model, (Model) scenetile.floorDecoration.renderable, 128, 0, 128, true);
			}
		}
		if (x >= width || y <= 0) {
			return;
		}
		SceneTile scenetile = tiles[plane][x + 1][y - 1];
		if (scenetile == null || scenetile.floorDecoration == null
				|| scenetile.floorDecoration.renderable.verticesNormal == null) {
			return;
		}
		method530(model, (Model) scenetile.floorDecoration.renderable, 128, 0, -128, true);
	}

	private void snapObjectVertices(int i, int i_193_, int i_194_, int x, int y, Model model) {
		try {
			boolean bool = true;
			int i_197_ = x;
			int i_198_ = x + i_193_;
			int i_199_ = y - 1;
			int i_200_ = y + i_194_;
			for (int zP = i; zP <= i + 1; zP++) {
				if (zP != planes) {
					for (int xP = i_197_; xP <= i_198_; xP++) {
						if (xP >= 0 && xP < width) {
							for (int yP = i_199_; yP <= i_200_; yP++) {
								if (yP >= 0
										&& yP < height
										&& (!bool || xP >= i_198_ || yP >= i_200_ || yP < y
												&& xP != x)) {
									SceneTile scenetile = tiles[zP][xP][yP];
									if (scenetile != null) {
										int i_204_ = (tileHeightMap[zP][xP][yP] + tileHeightMap[zP][xP + 1][yP] + tileHeightMap[zP][xP][yP + 1] + tileHeightMap[zP][xP + 1][yP + 1]) / 4
														- (tileHeightMap[i][x][y] + tileHeightMap[i][x + 1][y] + tileHeightMap[i][x][y + 1] + tileHeightMap[i][x + 1][y + 1]) / 4;
										Wall wall = scenetile.wall;
										if (wall != null && wall.aRenderable769 != null
												&& wall.aRenderable769.verticesNormal != null) {
											method530(model, (Model) wall.aRenderable769, (xP - x) * 128
													+ (1 - i_193_) * 64, i_204_, (yP - y) * 128 + (1 - i_194_)
													* 64, bool);
										}
										if (wall != null && wall.aRenderable770 != null
												&& wall.aRenderable770.verticesNormal != null) {
											method530(model, (Model) wall.aRenderable770, (xP - x) * 128
													+ (1 - i_193_) * 64, i_204_, (yP - y) * 128 + (1 - i_194_)
													* 64, bool);
										}
										for (int i_205_ = 0; i_205_ < scenetile.sceneSpawnRequestCount; i_205_++) {
											SceneSpawnRequest scenespawnrequest = scenetile.sceneSpawnRequests[i_205_];
											if (scenespawnrequest != null && scenespawnrequest.renderable != null
													&& scenespawnrequest.renderable.verticesNormal != null) {
												int i_206_ = scenespawnrequest.sizeX - scenespawnrequest.x + 1;
												int i_207_ = scenespawnrequest.sizeY - scenespawnrequest.y + 1;
												method530(model, (Model) scenespawnrequest.renderable,
														(scenespawnrequest.x - x) * 128 + (i_206_ - i_193_) * 64,
														i_204_, (scenespawnrequest.y - y) * 128
																+ (i_207_ - i_194_) * 64, bool);
											}
										}
									}
								}
							}
						}
					}
					i_197_--;
					bool = false;
				}
			}
		} catch (RuntimeException runtimeexception) {
			SignLink.reportError("11529, " + i + ", " + i_193_ + ", " + i_194_ + ", " + x + ", "
					+ y + ", " + model + ", " + runtimeexception.toString());
			throw new RuntimeException();
		}
	}
	
	// have to figure this one out...
	private void method530(Model model, Model model_208_, int i, int i_209_, int i_210_, boolean bool) {
		anInt568++;
		int i_211_ = 0;
		int[] is = model_208_.verticesX;
		int i_212_ = model_208_.vertexCount;
		for (int i_213_ = 0; i_213_ < model.vertexCount; i_213_++) {
			VertexNormal vertexnormal = model.verticesNormal[i_213_];
			VertexNormal vertexnormal_214_ = model.aVertexNormalArray1653[i_213_];
			if (vertexnormal_214_.magnitude != 0) {
				int i_215_ = model.verticesY[i_213_] - i_209_;
				if (i_215_ <= model_208_.maxY) {
					int i_216_ = model.verticesX[i_213_] - i;
					if (i_216_ >= model_208_.anInt1639 && i_216_ <= model_208_.anInt1640) {
						int i_217_ = model.verticesZ[i_213_] - i_210_;
						if (i_217_ >= model_208_.anInt1642 && i_217_ <= model_208_.anInt1641) {
							for (int i_218_ = 0; i_218_ < i_212_; i_218_++) {
								VertexNormal vertexnormal_219_ = model_208_.verticesNormal[i_218_];
								VertexNormal vertexnormal_220_ = model_208_.aVertexNormalArray1653[i_218_];
								if (i_216_ == is[i_218_] && i_217_ == model_208_.verticesZ[i_218_]
										&& i_215_ == model_208_.verticesY[i_218_] && vertexnormal_220_.magnitude != 0) {
									vertexnormal.x += vertexnormal_220_.x;
									vertexnormal.y += vertexnormal_220_.y;
									vertexnormal.z += vertexnormal_220_.z;
									vertexnormal.magnitude += vertexnormal_220_.magnitude;
									vertexnormal_219_.x += vertexnormal_214_.x;
									vertexnormal_219_.y += vertexnormal_214_.y;
									vertexnormal_219_.z += vertexnormal_214_.z;
									vertexnormal_219_.magnitude += vertexnormal_214_.magnitude;
									i_211_++;
									anIntArray566[i_213_] = anInt568;
									anIntArray567[i_218_] = anInt568;
								}
							}
						}
					}
				}
			}
		}
		if (i_211_ >= 3 && bool) {
			for (int i_221_ = 0; i_221_ < model.triangleCount; i_221_++) {
				if (anIntArray566[model.trianglePointsX[i_221_]] == anInt568
						&& anIntArray566[model.trianglePointsY[i_221_]] == anInt568
						&& anIntArray566[model.trianglePointsZ[i_221_]] == anInt568) {
					model.texturePoints[i_221_] = -1;
				}
			}
			for (int i_222_ = 0; i_222_ < model_208_.triangleCount; i_222_++) {
				if (anIntArray567[model_208_.trianglePointsX[i_222_]] == anInt568
						&& anIntArray567[model_208_.trianglePointsY[i_222_]] == anInt568
						&& anIntArray567[model_208_.trianglePointsZ[i_222_]] == anInt568) {
					model_208_.texturePoints[i_222_] = -1;
				}
			}
		}
	}

	public void renderMinimapDot(int[] pixels, int pixelOffset, int scanLength, int plane, int x, int y) {
		SceneTile scenetile = tiles[plane][x][y];
		if (scenetile != null) {
			GenericTile generictile = scenetile.genericTile;
			if (generictile != null) {
				int rgb = generictile.rgbColor;
				if (rgb != 0) {
					for (int lineOffset = 0; lineOffset < 4; lineOffset++) {
						pixels[pixelOffset] = rgb;
						pixels[pixelOffset + 1] = rgb;
						pixels[pixelOffset + 2] = rgb;
						pixels[pixelOffset + 3] = rgb;
						pixelOffset += scanLength;
					}
				}
			} else {
				ComplexTile complextile = scenetile.complexTile;
				if (complextile != null) {
					int shp = complextile.shape;
					int rot = complextile.rotation;
					int underlayColor = complextile.anInt208;
					int overlayColor = complextile.anInt209;
					int[] shapePoints = tileShapePoints[shp];
					int[] rotationIndices = tileShapeRotationIndices[rot];
					int rotationIndex = 0;
					if (underlayColor != 0) {
						for (int i_236_ = 0; i_236_ < 4; i_236_++) {
							pixels[pixelOffset] = shapePoints[rotationIndices[rotationIndex++]] == 0 ? underlayColor : overlayColor;
							pixels[pixelOffset + 1] = shapePoints[rotationIndices[rotationIndex++]] == 0 ? underlayColor : overlayColor;
							pixels[pixelOffset + 2] = shapePoints[rotationIndices[rotationIndex++]] == 0 ? underlayColor : overlayColor;
							pixels[pixelOffset + 3] = shapePoints[rotationIndices[rotationIndex++]] == 0 ? underlayColor : overlayColor;
							pixelOffset += scanLength;
						}
					} else {
						for (int i_237_ = 0; i_237_ < 4; i_237_++) {
							if (shapePoints[rotationIndices[rotationIndex++]] != 0) {
								pixels[pixelOffset] = overlayColor;
							}
							if (shapePoints[rotationIndices[rotationIndex++]] != 0) {
								pixels[pixelOffset + 1] = overlayColor;
							}
							if (shapePoints[rotationIndices[rotationIndex++]] != 0) {
								pixels[pixelOffset + 2] = overlayColor;
							}
							if (shapePoints[rotationIndices[rotationIndex++]] != 0) {
								pixels[pixelOffset + 3] = overlayColor;
							}
							pixelOffset += scanLength;
						}
					}
				}
			}
		}
	}

	public static void loadViewport(int i, int i_238_, int viewportWidth, int viewportHeight, int[] is) {
		try {
			left = 0;
			top = 0;
			right = viewportWidth;
			bottom = viewportHeight;
			midX = viewportWidth / 2;
			midY = viewportHeight / 2;
			boolean[][][][] visibilityMap = new boolean[9][32][53][53];
			for (int angleY = 128; angleY <= 384; angleY += 32) {
				for (int angleX = 0; angleX < 2048; angleX += 64) {
					sineCurveY = Model.SINE[angleY];
					cosineCurveY = Model.COSINE[angleY];
					sineCurveX = Model.SINE[angleX];
					cosineCurveX = Model.COSINE[angleX];
					int newAngleY = (angleY - 128) / 32;
					int newAngleX = angleX / 64;
					for (int x = -26; x <= 26; x++) {
						for (int y = -26; y <= 26; y++) {
							int actualX = x * 128;
							int actualY = y * 128;
							boolean visible = false;
							for (int i_250_ = -i; i_250_ <= i_238_; i_250_ += 128) {
								if (isOnScreen(is[newAngleY] + i_250_, actualY, actualX)) {
									visible = true;
									break;
								}
							}
							visibilityMap[newAngleY][newAngleX][x + 25 + 1][y + 25 + 1] = visible;
						}
					}
				}
			}
			for (int angleY = 0; angleY < 8; angleY++) {
				for (int angleX = 0; angleX < 32; angleX++) {
					for (int relX = -25; relX < 25; relX++) {
						for (int relY = -25; relY < 25; relY++) {
							boolean flag = false;
							while_8_: for (int i_256_ = -1; i_256_ <= 1; i_256_++) {
								for (int i_257_ = -1; i_257_ <= 1; i_257_++) {
									if (visibilityMap[angleY][angleX][relX + i_256_ + 25 + 1][relY + i_257_ + 25 + 1]) {
										flag = true;
										break while_8_;
									}
									if (visibilityMap[angleY][(angleX + 1) % 31][relX + i_256_ + 25 + 1][relY + i_257_ + 25 + 1]) {
										flag = true;
										break while_8_;
									}
									if (visibilityMap[angleY + 1][angleX][relX + i_256_ + 25 + 1][relY + i_257_ + 25 + 1]) {
										flag = true;
										break while_8_;
									}
									if (visibilityMap[angleY + 1][(angleX + 1) % 31][relX + i_256_ + 25 + 1][relY + i_257_ + 25 + 1]) {
										flag = true;
										break while_8_;
									}
								}
							}
							tileVisibilityMap[angleY][angleX][relX + 25][relY + 25] = flag;
						}
					}
				}
			}
		} catch (RuntimeException runtimeexception) {
			SignLink.reportError("5468, " + i + ", " + i_238_ + ", " + viewportWidth + ", " + viewportHeight + ", " + is + ", " + runtimeexception.toString());
			throw new RuntimeException();
		}
	}

	public static boolean isOnScreen(int plane, int y, int x) {
		int i_260_ = y * sineCurveX + x * cosineCurveX >> 16;
		int i_261_ = y * cosineCurveX - x * sineCurveX >> 16;
		int i_262_ = plane * sineCurveY + i_261_ * cosineCurveY >> 16;
		int i_263_ = plane * cosineCurveY - i_261_ * sineCurveY >> 16;
		if (i_262_ < 50 || i_262_ > 3500) {
			return false;
		}
		int i_264_ = midX + (i_260_ << 9) / i_262_;
		int i_265_ = midY + (i_263_ << 9) / i_262_;
		if (i_264_ < left || i_264_ > right || i_265_ < top || i_265_ > bottom) {
			return false;
		}
		return true;
	}

	public void method534(int y, int x) {
		aBoolean547 = true;
		clickX = x;
		clickY = y;
		tileClickX = -1;
		tileClickY = -1;
	}
	
	public void render(int i, int i_268_, int i_269_, int i_270_, int i_271_, int i_272_) {
		if (i < 0) {
			i = 0;
		} else if (i >= width * 128) {
			i = width * 128 - 1;
		}
		if (i_268_ < 0) {
			i_268_ = 0;
		} else if (i_268_ >= height * 128) {
			i_268_ = height * 128 - 1;
		}
		anInt528++;
		sineCurveY = Model.SINE[i_272_];
		cosineCurveY = Model.COSINE[i_272_];
		sineCurveX = Model.SINE[i_269_];
		cosineCurveX = Model.COSINE[i_269_];
		aBooleanArrayArray572 = tileVisibilityMap[(i_272_ - 128) / 32][i_269_ / 64];
		cameraX = i;
		cameraZ = i_270_;
		cameraY = i_268_;
		anInt533 = i / 128;
		anInt534 = i_268_ / 128;
		anInt527 = i_271_;
		anInt529 = anInt533 - 25;
		if (anInt529 < 0) {
			anInt529 = 0;
		}
		anInt531 = anInt534 - 25;
		if (anInt531 < 0) {
			anInt531 = 0;
		}
		anInt530 = anInt533 + 25;
		if (anInt530 > width) {
			anInt530 = width;
		}
		anInt532 = anInt534 + 25;
		if (anInt532 > height) {
			anInt532 = height;
		}
		method541(0);
		anInt526 = 0;
		for (int i_273_ = currentPlane; i_273_ < planes; i_273_++) {
			SceneTile[][] scenetiles = tiles[i_273_];
			for (int i_274_ = anInt529; i_274_ < anInt530; i_274_++) {
				for (int i_275_ = anInt531; i_275_ < anInt532; i_275_++) {
					SceneTile scenetile = scenetiles[i_274_][i_275_];
					if (scenetile != null) {
						if (scenetile.logicalHeight > i_271_
								|| !aBooleanArrayArray572[i_274_ - anInt533 + 25][i_275_
										- anInt534 + 25]
								&& tileHeightMap[i_273_][i_274_][i_275_] - i_270_ < 2000) {
							scenetile.aBoolean1333 = false;
							scenetile.aBoolean1334 = false;
							scenetile.anInt1336 = 0;
						} else {
							scenetile.aBoolean1333 = true;
							scenetile.aBoolean1334 = true;
							if (scenetile.sceneSpawnRequestCount > 0) {
								scenetile.aBoolean1335 = true;
							} else {
								scenetile.aBoolean1335 = false;
							}
							anInt526++;
						}
					}
				}
			}
		}
		for (int i_276_ = currentPlane; i_276_ < planes; i_276_++) {
			SceneTile[][] scenetiles = tiles[i_276_];
			for (int i_277_ = -25; i_277_ <= 0; i_277_++) {
				int i_278_ = anInt533 + i_277_;
				int i_279_ = anInt533 - i_277_;
				if (i_278_ >= anInt529 || i_279_ < anInt530) {
					for (int i_280_ = -25; i_280_ <= 0; i_280_++) {
						int i_281_ = anInt534 + i_280_;
						int i_282_ = anInt534 - i_280_;
						if (i_278_ >= anInt529) {
							if (i_281_ >= anInt531) {
								SceneTile scenetile = scenetiles[i_278_][i_281_];
								if (scenetile != null && scenetile.aBoolean1333) {
									renderTile(scenetile, true);
								}
							}
							if (i_282_ < anInt532) {
								SceneTile scenetile = scenetiles[i_278_][i_282_];
								if (scenetile != null && scenetile.aBoolean1333) {
									renderTile(scenetile, true);
								}
							}
						}
						if (i_279_ < anInt530) {
							if (i_281_ >= anInt531) {
								SceneTile scenetile = scenetiles[i_279_][i_281_];
								if (scenetile != null && scenetile.aBoolean1333) {
									renderTile(scenetile, true);
								}
							}
							if (i_282_ < anInt532) {
								SceneTile scenetile = scenetiles[i_279_][i_282_];
								if (scenetile != null && scenetile.aBoolean1333) {
									renderTile(scenetile, true);
								}
							}
						}
						if (anInt526 == 0) {
							aBoolean547 = false;
							return;
						}
					}
				}
			}
		}
		for (int i_283_ = currentPlane; i_283_ < planes; i_283_++) {
			SceneTile[][] scenetiles = tiles[i_283_];
			for (int i_284_ = -25; i_284_ <= 0; i_284_++) {
				int i_285_ = anInt533 + i_284_;
				int i_286_ = anInt533 - i_284_;
				if (i_285_ >= anInt529 || i_286_ < anInt530) {
					for (int i_287_ = -25; i_287_ <= 0; i_287_++) {
						int i_288_ = anInt534 + i_287_;
						int i_289_ = anInt534 - i_287_;
						if (i_285_ >= anInt529) {
							if (i_288_ >= anInt531) {
								SceneTile scenetile = scenetiles[i_285_][i_288_];
								if (scenetile != null && scenetile.aBoolean1333) {
									renderTile(scenetile, false);
								}
							}
							if (i_289_ < anInt532) {
								SceneTile scenetile = scenetiles[i_285_][i_289_];
								if (scenetile != null && scenetile.aBoolean1333) {
									renderTile(scenetile, false);
								}
							}
						}
						if (i_286_ < anInt530) {
							if (i_288_ >= anInt531) {
								SceneTile scenetile = scenetiles[i_286_][i_288_];
								if (scenetile != null && scenetile.aBoolean1333) {
									renderTile(scenetile, false);
								}
							}
							if (i_289_ < anInt532) {
								SceneTile scenetile = scenetiles[i_286_][i_289_];
								if (scenetile != null && scenetile.aBoolean1333) {
									renderTile(scenetile, false);
								}
							}
						}
						if (anInt526 == 0) {
							aBoolean547 = false;
							return;
						}
					}
				}
			}
		}
		aBoolean547 = false;
	}

	public void renderTile(SceneTile scenetile, boolean bool) {
		aLinkedList557.insertBack(scenetile);
		for (;;) {
			SceneTile scenetile_290_ = (SceneTile) aLinkedList557.popTail();
			if (scenetile_290_ == null) {
				break;
			}
			if (scenetile_290_.aBoolean1334) {
				int x = scenetile_290_.x;
				int y = scenetile_290_.y;
				int i_292_ = scenetile_290_.plane2;
				int pnative = scenetile_290_.plane;
				SceneTile[][] scenetiles = tiles[i_292_];
				if (scenetile_290_.aBoolean1333) {
					if (bool) {
						if (i_292_ > 0) {
							SceneTile scenetile_294_ = tiles[i_292_ - 1][x][y];
							if (scenetile_294_ != null && scenetile_294_.aBoolean1334) {
								continue;
							}
						}
						if (x <= anInt533 && x > anInt529) {
							SceneTile scenetile_295_ = scenetiles[x - 1][y];
							if (scenetile_295_ != null
									&& scenetile_295_.aBoolean1334
									&& (scenetile_295_.aBoolean1333 || (scenetile_290_.sceneSpawnRequestSizeOR & 0x1) == 0)) {
								continue;
							}
						}
						if (x >= anInt533 && x < anInt530 - 1) {
							SceneTile scenetile_296_ = scenetiles[x + 1][y];
							if (scenetile_296_ != null
									&& scenetile_296_.aBoolean1334
									&& (scenetile_296_.aBoolean1333 || (scenetile_290_.sceneSpawnRequestSizeOR & 0x4) == 0)) {
								continue;
							}
						}
						if (y <= anInt534 && y > anInt531) {
							SceneTile scenetile_297_ = scenetiles[x][y - 1];
							if (scenetile_297_ != null
									&& scenetile_297_.aBoolean1334
									&& (scenetile_297_.aBoolean1333 || (scenetile_290_.sceneSpawnRequestSizeOR & 0x8) == 0)) {
								continue;
							}
						}
						if (y >= anInt534 && y < anInt532 - 1) {
							SceneTile scenetile_298_ = scenetiles[x][y + 1];
							if (scenetile_298_ != null
									&& scenetile_298_.aBoolean1334
									&& (scenetile_298_.aBoolean1333 || (scenetile_290_.sceneSpawnRequestSizeOR & 0x2) == 0)) {
								continue;
							}
						}
					} else {
						bool = true;
					}
					scenetile_290_.aBoolean1333 = false;
					if (scenetile_290_.sceneTile != null) {
						SceneTile scenetile_299_ = scenetile_290_.sceneTile;
						if (scenetile_299_.genericTile != null) {
							if (!method542(0, x, y)) {
								renderGenericTile(scenetile_299_.genericTile, 0, sineCurveY, cosineCurveY,
										sineCurveX, cosineCurveX, x, y);
							}
						} else if (scenetile_299_.complexTile != null && !method542(0, x, y)) {
							method538(x, (byte) 99, sineCurveY, sineCurveX, scenetile_299_.complexTile,
									cosineCurveY, y, cosineCurveX);
						}
						Wall wall = scenetile_299_.wall;
						if (wall != null) {
							wall.aRenderable769.renderAtPoint(0, sineCurveY, cosineCurveY, sineCurveX,
									cosineCurveX, wall.x - cameraX, wall.plane - cameraZ, wall.y
											- cameraY, wall.hash);
						}
						for (int i_300_ = 0; i_300_ < scenetile_299_.sceneSpawnRequestCount; i_300_++) {
							SceneSpawnRequest scenespawnrequest = scenetile_299_.sceneSpawnRequests[i_300_];
							if (scenespawnrequest != null) {
								scenespawnrequest.renderable.renderAtPoint(scenespawnrequest.anInt602, sineCurveY,
										cosineCurveY, sineCurveX, cosineCurveX, scenespawnrequest.anInt599
												- cameraX, scenespawnrequest.anInt598 - cameraZ,
										scenespawnrequest.anInt600 - cameraY, scenespawnrequest.hash);
							}
						}
					}
					boolean bool_301_ = false;
					if (scenetile_290_.genericTile != null) {
						if (!method542(pnative, x, y)) {
							bool_301_ = true;
							renderGenericTile(scenetile_290_.genericTile, pnative, sineCurveY, cosineCurveY,
									sineCurveX, cosineCurveX, x, y);
						}
					} else if (scenetile_290_.complexTile != null && !method542(pnative, x, y)) {
						bool_301_ = true;
						method538(x, (byte) 99, sineCurveY, sineCurveX, scenetile_290_.complexTile,
								cosineCurveY, y, cosineCurveX);
					}
					int i_302_ = 0;
					int visibleBack = 0;
					Wall wall = scenetile_290_.wall;
					WallDecoration wallDecoration = scenetile_290_.wallDecoration;
					if (wall != null || wallDecoration != null) {
						if (anInt533 == x) {
							i_302_++;
						} else if (anInt533 < x) {
							i_302_ += 2;
						}
						if (anInt534 == y) {
							i_302_ += 3;
						} else if (anInt534 > y) {
							i_302_ += 6;
						}
						visibleBack = wallVisibleBackground[i_302_];
						scenetile_290_.anInt1339 = anIntArray560[i_302_];
					}
					if (wall != null) {
						if ((wall.faceUnknown & anIntArray559[i_302_]) != 0) {
							if (wall.faceUnknown == 16) {
								scenetile_290_.anInt1336 = 3;
								scenetile_290_.anInt1337 = anIntArray561[i_302_];
								scenetile_290_.anInt1338 = 3 - scenetile_290_.anInt1337;
							} else if (wall.faceUnknown == 32) {
								scenetile_290_.anInt1336 = 6;
								scenetile_290_.anInt1337 = anIntArray562[i_302_];
								scenetile_290_.anInt1338 = 6 - scenetile_290_.anInt1337;
							} else if (wall.faceUnknown == 64) {
								scenetile_290_.anInt1336 = 12;
								scenetile_290_.anInt1337 = anIntArray563[i_302_];
								scenetile_290_.anInt1338 = 12 - scenetile_290_.anInt1337;
							} else {
								scenetile_290_.anInt1336 = 9;
								scenetile_290_.anInt1337 = anIntArray564[i_302_];
								scenetile_290_.anInt1338 = 9 - scenetile_290_.anInt1337;
							}
						} else {
							scenetile_290_.anInt1336 = 0;
						}
						if ((wall.faceUnknown & visibleBack) != 0 && !method543(pnative, x, y, wall.faceUnknown)) {
							wall.aRenderable769.renderAtPoint(0, sineCurveY, cosineCurveY, sineCurveX,
									cosineCurveX, wall.x - cameraX, wall.plane - cameraZ, wall.y
											- cameraY, wall.hash);
						}
						if ((wall.face & visibleBack) != 0 && !method543(pnative, x, y, wall.face)) {
							wall.aRenderable770.renderAtPoint(0, sineCurveY, cosineCurveY, sineCurveX,
									cosineCurveX, wall.x - cameraX, wall.plane - cameraZ, wall.y
											- cameraY, wall.hash);
						}
					}
					if (wallDecoration != null
							&& !wallDecorationOccluded(pnative, x, y, wallDecoration.renderable.modelHeight)) {
						if ((wallDecoration.hostOrientation & visibleBack) != 0) {
							wallDecoration.renderable.renderAtPoint(wallDecoration.orientation, sineCurveY,
									cosineCurveY, sineCurveX, cosineCurveX, wallDecoration.y - cameraX,
									wallDecoration.z - cameraZ, wallDecoration.x - cameraY,
									wallDecoration.hash);
						} else if ((wallDecoration.hostOrientation & 0x300) != 0) {
							int ry = wallDecoration.y - cameraX;
							int rz = wallDecoration.z - cameraZ;
							int rx = wallDecoration.x - cameraY;
							int orientation = wallDecoration.orientation;
							int sy;
							if (orientation == 1 || orientation == 2) {
								sy = -ry;
							} else {
								sy = ry;
							}
							int sx;
							if (orientation == 2 || orientation == 3) {
								sx = -rx;
							} else {
								sx = rx;
							}
							if ((wallDecoration.hostOrientation & 0x100) != 0 && sx < sy) {
								int i_310_ = ry + anIntArray543[orientation];
								int i_311_ = rx + anIntArray544[orientation];
								wallDecoration.renderable.renderAtPoint(orientation * 512 + 256, sineCurveY,
										cosineCurveY, sineCurveX, cosineCurveX, i_310_, rz, i_311_,
										wallDecoration.hash);
							}
							if ((wallDecoration.hostOrientation & 0x200) != 0 && sx > sy) {
								int _y = ry + anIntArray545[orientation];
								int _x = rx + anIntArray546[orientation];
								wallDecoration.renderable.renderAtPoint(orientation * 512 + 1280 & 0x7ff,
										sineCurveY, cosineCurveY, sineCurveX, cosineCurveX, _y, rz, _x,
										wallDecoration.hash);
							}
						}
					}
					if (bool_301_) {
						FloorDecoration floordecoration = scenetile_290_.floorDecoration;
						if (floordecoration != null) {
							floordecoration.renderable.renderAtPoint(0, sineCurveY, cosineCurveY, sineCurveX,
									cosineCurveX, floordecoration.y - cameraX, floordecoration.x
											- cameraZ, floordecoration.z - cameraY, floordecoration.hash);
						}
						CameraAngle cameraangle = scenetile_290_.cameraAngle;
						if (cameraangle != null && cameraangle.anInt154 == 0) {
							if (cameraangle.aRenderable151 != null) {
								cameraangle.aRenderable151.renderAtPoint(0, sineCurveY, cosineCurveY,
										sineCurveX, cosineCurveX, cameraangle.y - cameraX, cameraangle.x
												- cameraZ, cameraangle.z - cameraY, cameraangle.anInt153);
							}
							if (cameraangle.aRenderable152 != null) {
								cameraangle.aRenderable152.renderAtPoint(0, sineCurveY, cosineCurveY,
										sineCurveX, cosineCurveX, cameraangle.y - cameraX, cameraangle.x
												- cameraZ, cameraangle.z - cameraY, cameraangle.anInt153);
							}
							if (cameraangle.aRenderable150 != null) {
								cameraangle.aRenderable150.renderAtPoint(0, sineCurveY, cosineCurveY,
										sineCurveX, cosineCurveX, cameraangle.y - cameraX, cameraangle.x
												- cameraZ, cameraangle.z - cameraY, cameraangle.anInt153);
							}
						}
					}
					int i_314_ = scenetile_290_.sceneSpawnRequestSizeOR;
					if (i_314_ != 0) {
						if (x < anInt533 && (i_314_ & 0x4) != 0) {
							SceneTile scenetile_315_ = scenetiles[x + 1][y];
							if (scenetile_315_ != null && scenetile_315_.aBoolean1334) {
								aLinkedList557.insertBack(scenetile_315_);
							}
						}
						if (y < anInt534 && (i_314_ & 0x2) != 0) {
							SceneTile scenetile_316_ = scenetiles[x][y + 1];
							if (scenetile_316_ != null && scenetile_316_.aBoolean1334) {
								aLinkedList557.insertBack(scenetile_316_);
							}
						}
						if (x > anInt533 && (i_314_ & 0x1) != 0) {
							SceneTile scenetile_317_ = scenetiles[x - 1][y];
							if (scenetile_317_ != null && scenetile_317_.aBoolean1334) {
								aLinkedList557.insertBack(scenetile_317_);
							}
						}
						if (y > anInt534 && (i_314_ & 0x8) != 0) {
							SceneTile scenetile_318_ = scenetiles[x][y - 1];
							if (scenetile_318_ != null && scenetile_318_.aBoolean1334) {
								aLinkedList557.insertBack(scenetile_318_);
							}
						}
					}
				}
				if (scenetile_290_.anInt1336 != 0) {
					boolean bool_319_ = true;
					for (int i_320_ = 0; i_320_ < scenetile_290_.sceneSpawnRequestCount; i_320_++) {
						if (scenetile_290_.sceneSpawnRequests[i_320_].anInt608 != anInt528
								&& (scenetile_290_.sceneSpawnRequestsSize[i_320_] & scenetile_290_.anInt1336) == scenetile_290_.anInt1337) {
							bool_319_ = false;
							break;
						}
					}
					if (bool_319_) {
						Wall wall = scenetile_290_.wall;
						if (!method543(pnative, x, y, wall.faceUnknown)) {
							wall.aRenderable769.renderAtPoint(0, sineCurveY, cosineCurveY, sineCurveX,
									cosineCurveX, wall.x - cameraX, wall.plane - cameraZ, wall.y
											- cameraY, wall.hash);
						}
						scenetile_290_.anInt1336 = 0;
					}
				}
				if (scenetile_290_.aBoolean1335) {
					try {
						int i_321_ = scenetile_290_.sceneSpawnRequestCount;
						scenetile_290_.aBoolean1335 = false;
						int i_322_ = 0;
						while_10_: for (int i_323_ = 0; i_323_ < i_321_; i_323_++) {
							SceneSpawnRequest scenespawnrequest = scenetile_290_.sceneSpawnRequests[i_323_];
							if (scenespawnrequest.anInt608 != anInt528) {
								for (int i_324_ = scenespawnrequest.x; i_324_ <= scenespawnrequest.sizeX; i_324_++) {
									for (int i_325_ = scenespawnrequest.y; i_325_ <= scenespawnrequest.sizeY; i_325_++) {
										SceneTile scenetile_326_ = scenetiles[i_324_][i_325_];
										if (scenetile_326_.aBoolean1333) {
											scenetile_290_.aBoolean1335 = true;
											continue while_10_;
										}
										if (scenetile_326_.anInt1336 != 0) {
											int i_327_ = 0;
											if (i_324_ > scenespawnrequest.x) {
												i_327_++;
											}
											if (i_324_ < scenespawnrequest.sizeX) {
												i_327_ += 4;
											}
											if (i_325_ > scenespawnrequest.y) {
												i_327_ += 8;
											}
											if (i_325_ < scenespawnrequest.sizeY) {
												i_327_ += 2;
											}
											if ((i_327_ & scenetile_326_.anInt1336) == scenetile_290_.anInt1338) {
												scenetile_290_.aBoolean1335 = true;
												continue while_10_;
											}
										}
									}
								}
								aSceneSpawnRequestArray542[i_322_++] = scenespawnrequest;
								int i_328_ = anInt533 - scenespawnrequest.x;
								int i_329_ = scenespawnrequest.sizeX - anInt533;
								if (i_329_ > i_328_) {
									i_328_ = i_329_;
								}
								int i_330_ = anInt534 - scenespawnrequest.y;
								int i_331_ = scenespawnrequest.sizeY - anInt534;
								if (i_331_ > i_330_) {
									scenespawnrequest.anInt607 = i_328_ + i_331_;
								} else {
									scenespawnrequest.anInt607 = i_328_ + i_330_;
								}
							}
						}
						while (i_322_ > 0) {
							int i_332_ = -50;
							int i_333_ = -1;
							for (int i_334_ = 0; i_334_ < i_322_; i_334_++) {
								SceneSpawnRequest scenespawnrequest = aSceneSpawnRequestArray542[i_334_];
								if (scenespawnrequest.anInt608 != anInt528) {
									if (scenespawnrequest.anInt607 > i_332_) {
										i_332_ = scenespawnrequest.anInt607;
										i_333_ = i_334_;
									} else if (scenespawnrequest.anInt607 == i_332_) {
										int i_335_ = scenespawnrequest.anInt599 - cameraX;
										int i_336_ = scenespawnrequest.anInt600 - cameraY;
										int i_337_ = aSceneSpawnRequestArray542[i_333_].anInt599 - cameraX;
										int i_338_ = aSceneSpawnRequestArray542[i_333_].anInt600 - cameraY;
										if (i_335_ * i_335_ + i_336_ * i_336_ > i_337_ * i_337_ + i_338_ * i_338_) {
											i_333_ = i_334_;
										}
									}
								}
							}
							if (i_333_ == -1) {
								break;
							}
							SceneSpawnRequest scenespawnrequest = aSceneSpawnRequestArray542[i_333_];
							scenespawnrequest.anInt608 = anInt528;
							if (!method545(pnative, scenespawnrequest.x, scenespawnrequest.sizeX, scenespawnrequest.y,
									scenespawnrequest.sizeY, scenespawnrequest.renderable.modelHeight)) {
								scenespawnrequest.renderable.renderAtPoint(scenespawnrequest.anInt602, sineCurveY,
										cosineCurveY, sineCurveX, cosineCurveX, scenespawnrequest.anInt599
												- cameraX, scenespawnrequest.anInt598 - cameraZ,
										scenespawnrequest.anInt600 - cameraY, scenespawnrequest.hash);
							}
							for (int i_339_ = scenespawnrequest.x; i_339_ <= scenespawnrequest.sizeX; i_339_++) {
								for (int i_340_ = scenespawnrequest.y; i_340_ <= scenespawnrequest.sizeY; i_340_++) {
									SceneTile scenetile_341_ = scenetiles[i_339_][i_340_];
									if (scenetile_341_.anInt1336 != 0) {
										aLinkedList557.insertBack(scenetile_341_);
									} else if ((i_339_ != x || i_340_ != y) && scenetile_341_.aBoolean1334) {
										aLinkedList557.insertBack(scenetile_341_);
									}
								}
							}
						}
						if (scenetile_290_.aBoolean1335) {
							continue;
						}
					} catch (Exception exception) {
						scenetile_290_.aBoolean1335 = false;
					}
				}
				if (scenetile_290_.aBoolean1334 && scenetile_290_.anInt1336 == 0) {
					if (x <= anInt533 && x > anInt529) {
						SceneTile scenetile_342_ = scenetiles[x - 1][y];
						if (scenetile_342_ != null && scenetile_342_.aBoolean1334) {
							continue;
						}
					}
					if (x >= anInt533 && x < anInt530 - 1) {
						SceneTile scenetile_343_ = scenetiles[x + 1][y];
						if (scenetile_343_ != null && scenetile_343_.aBoolean1334) {
							continue;
						}
					}
					if (y <= anInt534 && y > anInt531) {
						SceneTile scenetile_344_ = scenetiles[x][y - 1];
						if (scenetile_344_ != null && scenetile_344_.aBoolean1334) {
							continue;
						}
					}
					if (y >= anInt534 && y < anInt532 - 1) {
						SceneTile scenetile_345_ = scenetiles[x][y + 1];
						if (scenetile_345_ != null && scenetile_345_.aBoolean1334) {
							continue;
						}
					}
					scenetile_290_.aBoolean1334 = false;
					anInt526--;
					CameraAngle cameraangle = scenetile_290_.cameraAngle;
					if (cameraangle != null && cameraangle.anInt154 != 0) {
						if (cameraangle.aRenderable151 != null) {
							cameraangle.aRenderable151
									.renderAtPoint(0, sineCurveY, cosineCurveY, sineCurveX, cosineCurveX,
											cameraangle.y - cameraX, cameraangle.x - cameraZ
													- cameraangle.anInt154, cameraangle.z - cameraY,
											cameraangle.anInt153);
						}
						if (cameraangle.aRenderable152 != null) {
							cameraangle.aRenderable152
									.renderAtPoint(0, sineCurveY, cosineCurveY, sineCurveX, cosineCurveX,
											cameraangle.y - cameraX, cameraangle.x - cameraZ
													- cameraangle.anInt154, cameraangle.z - cameraY,
											cameraangle.anInt153);
						}
						if (cameraangle.aRenderable150 != null) {
							cameraangle.aRenderable150
									.renderAtPoint(0, sineCurveY, cosineCurveY, sineCurveX, cosineCurveX,
											cameraangle.y - cameraX, cameraangle.x - cameraZ
													- cameraangle.anInt154, cameraangle.z - cameraY,
											cameraangle.anInt153);
						}
					}
					if (scenetile_290_.anInt1339 != 0) {
						WallDecoration walldecoration = scenetile_290_.wallDecoration;
						if (walldecoration != null
								&& !wallDecorationOccluded(pnative, x, y, walldecoration.renderable.modelHeight)) {
							if ((walldecoration.hostOrientation & scenetile_290_.anInt1339) != 0) {
								walldecoration.renderable.renderAtPoint(walldecoration.orientation, sineCurveY,
										cosineCurveY, sineCurveX, cosineCurveX, walldecoration.y
												- cameraX, walldecoration.z - cameraZ, walldecoration.x
												- cameraY, walldecoration.hash);
							} else if ((walldecoration.hostOrientation & 0x300) != 0) {
								int i_346_ = walldecoration.y - cameraX;
								int i_347_ = walldecoration.z - cameraZ;
								int i_348_ = walldecoration.x - cameraY;
								int i_349_ = walldecoration.orientation;
								int i_350_;
								if (i_349_ == 1 || i_349_ == 2) {
									i_350_ = -i_346_;
								} else {
									i_350_ = i_346_;
								}
								int i_351_;
								if (i_349_ == 2 || i_349_ == 3) {
									i_351_ = -i_348_;
								} else {
									i_351_ = i_348_;
								}
								if ((walldecoration.hostOrientation & 0x100) != 0 && i_351_ >= i_350_) {
									int i_352_ = i_346_ + anIntArray543[i_349_];
									int i_353_ = i_348_ + anIntArray544[i_349_];
									walldecoration.renderable.renderAtPoint(i_349_ * 512 + 256, sineCurveY,
											cosineCurveY, sineCurveX, cosineCurveX, i_352_, i_347_, i_353_,
											walldecoration.hash);
								}
								if ((walldecoration.hostOrientation & 0x200) != 0 && i_351_ <= i_350_) {
									int i_354_ = i_346_ + anIntArray545[i_349_];
									int i_355_ = i_348_ + anIntArray546[i_349_];
									walldecoration.renderable.renderAtPoint(i_349_ * 512 + 1280 & 0x7ff,
											sineCurveY, cosineCurveY, sineCurveX, cosineCurveX, i_354_,
											i_347_, i_355_, walldecoration.hash);
								}
							}
						}
						Wall wall = scenetile_290_.wall;
						if (wall != null) {
							if ((wall.face & scenetile_290_.anInt1339) != 0 && !method543(pnative, x, y, wall.face)) {
								wall.aRenderable770.renderAtPoint(0, sineCurveY, cosineCurveY, sineCurveX,
										cosineCurveX, wall.x - cameraX, wall.plane - cameraZ, wall.y
												- cameraY, wall.hash);
							}
							if ((wall.faceUnknown & scenetile_290_.anInt1339) != 0
									&& !method543(pnative, x, y, wall.faceUnknown)) {
								wall.aRenderable769.renderAtPoint(0, sineCurveY, cosineCurveY, sineCurveX,
										cosineCurveX, wall.x - cameraX, wall.plane - cameraZ, wall.y
												- cameraY, wall.hash);
							}
						}
					}
					if (i_292_ < planes - 1) {
						SceneTile scenetile_356_ = tiles[i_292_ + 1][x][y];
						if (scenetile_356_ != null && scenetile_356_.aBoolean1334) {
							aLinkedList557.insertBack(scenetile_356_);
						}
					}
					if (x < anInt533) {
						SceneTile scenetile_357_ = scenetiles[x + 1][y];
						if (scenetile_357_ != null && scenetile_357_.aBoolean1334) {
							aLinkedList557.insertBack(scenetile_357_);
						}
					}
					if (y < anInt534) {
						SceneTile scenetile_358_ = scenetiles[x][y + 1];
						if (scenetile_358_ != null && scenetile_358_.aBoolean1334) {
							aLinkedList557.insertBack(scenetile_358_);
						}
					}
					if (x > anInt533) {
						SceneTile scenetile_359_ = scenetiles[x - 1][y];
						if (scenetile_359_ != null && scenetile_359_.aBoolean1334) {
							aLinkedList557.insertBack(scenetile_359_);
						}
					}
					if (y > anInt534) {
						SceneTile scenetile_360_ = scenetiles[x][y - 1];
						if (scenetile_360_ != null && scenetile_360_.aBoolean1334) {
							aLinkedList557.insertBack(scenetile_360_);
						}
					}
				}
			}
		}
	}

	public void renderGenericTile(GenericTile generictile, int plane, int i_361_, int i_362_, int i_363_, int i_364_, int x, int y) {
		int i_368_;
		int i_367_ = i_368_ = (x << 7) - cameraX;
		int i_370_;
		int i_369_ = i_370_ = (y << 7) - cameraY;
		int i_372_;
		int i_371_ = i_372_ = i_367_ + 128;
		int i_374_;
		int i_373_ = i_374_ = i_369_ + 128;
		int i_375_ = tileHeightMap[plane][x][y] - cameraZ;
		int i_376_ = tileHeightMap[plane][x + 1][y] - cameraZ;
		int i_377_ = tileHeightMap[plane][x + 1][y + 1] - cameraZ;
		int i_378_ = tileHeightMap[plane][x][y + 1] - cameraZ;
		int i_379_ = i_369_ * i_363_ + i_367_ * i_364_ >> 16;
		i_369_ = i_369_ * i_364_ - i_367_ * i_363_ >> 16;
		i_367_ = i_379_;
		i_379_ = i_375_ * i_362_ - i_369_ * i_361_ >> 16;
		i_369_ = i_375_ * i_361_ + i_369_ * i_362_ >> 16;
		i_375_ = i_379_;
		if (i_369_ >= 50) {
			i_379_ = i_370_ * i_363_ + i_371_ * i_364_ >> 16;
			i_370_ = i_370_ * i_364_ - i_371_ * i_363_ >> 16;
			i_371_ = i_379_;
			i_379_ = i_376_ * i_362_ - i_370_ * i_361_ >> 16;
			i_370_ = i_376_ * i_361_ + i_370_ * i_362_ >> 16;
			i_376_ = i_379_;
			if (i_370_ >= 50) {
				i_379_ = i_373_ * i_363_ + i_372_ * i_364_ >> 16;
				i_373_ = i_373_ * i_364_ - i_372_ * i_363_ >> 16;
				i_372_ = i_379_;
				i_379_ = i_377_ * i_362_ - i_373_ * i_361_ >> 16;
				i_373_ = i_377_ * i_361_ + i_373_ * i_362_ >> 16;
				i_377_ = i_379_;
				if (i_373_ >= 50) {
					i_379_ = i_374_ * i_363_ + i_368_ * i_364_ >> 16;
					i_374_ = i_374_ * i_364_ - i_368_ * i_363_ >> 16;
					i_368_ = i_379_;
					i_379_ = i_378_ * i_362_ - i_374_ * i_361_ >> 16;
					i_374_ = i_378_ * i_361_ + i_374_ * i_362_ >> 16;
					i_378_ = i_379_;
					if (i_374_ >= 50) {
						int i_380_ = Rasterizer3D.centerX + (i_367_ << 9) / i_369_;
						int i_381_ = Rasterizer3D.centerY + (i_375_ << 9) / i_369_;
						int i_382_ = Rasterizer3D.centerX + (i_371_ << 9) / i_370_;
						int i_383_ = Rasterizer3D.centerY + (i_376_ << 9) / i_370_;
						int i_384_ = Rasterizer3D.centerX + (i_372_ << 9) / i_373_;
						int i_385_ = Rasterizer3D.centerY + (i_377_ << 9) / i_373_;
						int i_386_ = Rasterizer3D.centerX + (i_368_ << 9) / i_374_;
						int i_387_ = Rasterizer3D.centerY + (i_378_ << 9) / i_374_;
						Rasterizer3D.anInt1485 = 0;
						if ((i_384_ - i_386_) * (i_383_ - i_387_) - (i_385_ - i_387_) * (i_382_ - i_386_) > 0) {
							Rasterizer3D.aBoolean1482 = false;
							if (i_384_ < 0 || i_386_ < 0 || i_382_ < 0 || i_384_ > Rasterizer.virtualBottomX
									|| i_386_ > Rasterizer.virtualBottomX || i_382_ > Rasterizer.virtualBottomX) {
								Rasterizer3D.aBoolean1482 = true;
							}
							if (aBoolean547
									&& method540(clickX, clickY, i_385_, i_387_, i_383_, i_384_,
											i_386_, i_382_)) {
								tileClickX = x;
								tileClickY = y;
							}
							if (generictile.texture == -1) {
								if (generictile.anInt294 != 12345678) {
									Rasterizer3D.method371(i_385_, i_387_, i_383_, i_384_, i_386_, i_382_,
											generictile.anInt294, generictile.anInt295, generictile.anInt293);
								}
							} else if (!lowMemory) {
								if (generictile.flat) {
									Rasterizer3D.method375(i_385_, i_387_, i_383_, i_384_, i_386_, i_382_,
											generictile.anInt294, generictile.anInt295, generictile.anInt293, i_367_,
											i_371_, i_368_, i_375_, i_376_, i_378_, i_369_, i_370_, i_374_,
											generictile.texture);
								} else {
									Rasterizer3D.method375(i_385_, i_387_, i_383_, i_384_, i_386_, i_382_,
											generictile.anInt294, generictile.anInt295, generictile.anInt293, i_372_,
											i_368_, i_371_, i_377_, i_378_, i_376_, i_373_, i_374_, i_370_,
											generictile.texture);
								}
							} else {
								int i_388_ = anIntArray565[generictile.texture];
								Rasterizer3D.method371(i_385_, i_387_, i_383_, i_384_, i_386_, i_382_,
										method539(-361, i_388_, generictile.anInt294),
										method539(-361, i_388_, generictile.anInt295),
										method539(-361, i_388_, generictile.anInt293));
							}
						}
						if ((i_380_ - i_382_) * (i_387_ - i_383_) - (i_381_ - i_383_) * (i_386_ - i_382_) > 0) {
							Rasterizer3D.aBoolean1482 = false;
							if (i_380_ < 0 || i_382_ < 0 || i_386_ < 0 || i_380_ > Rasterizer.virtualBottomX
									|| i_382_ > Rasterizer.virtualBottomX || i_386_ > Rasterizer.virtualBottomX) {
								Rasterizer3D.aBoolean1482 = true;
							}
							if (aBoolean547
									&& method540(clickX, clickY, i_381_, i_383_, i_387_, i_380_,
											i_382_, i_386_)) {
								tileClickX = x;
								tileClickY = y;
							}
							if (generictile.texture == -1) {
								if (generictile.anInt292 != 12345678) {
									Rasterizer3D.method371(i_381_, i_383_, i_387_, i_380_, i_382_, i_386_,
											generictile.anInt292, generictile.anInt293, generictile.anInt295);
								}
							} else if (!lowMemory) {
								Rasterizer3D.method375(i_381_, i_383_, i_387_, i_380_, i_382_, i_386_,
										generictile.anInt292, generictile.anInt293, generictile.anInt295, i_367_,
										i_371_, i_368_, i_375_, i_376_, i_378_, i_369_, i_370_, i_374_,
										generictile.texture);
							} else {
								int i_389_ = anIntArray565[generictile.texture];
								Rasterizer3D.method371(i_381_, i_383_, i_387_, i_380_, i_382_, i_386_,
										method539(-361, i_389_, generictile.anInt292),
										method539(-361, i_389_, generictile.anInt293),
										method539(-361, i_389_, generictile.anInt295));
							}
						}
					}
				}
			}
		}
	}

	public void method538(int i, byte b, int i_390_, int i_391_, ComplexTile complextile, int i_392_, int i_393_,
			int i_394_) {
		try {
			int i_395_ = complextile.anIntArray195.length;
			if (b == 99) {
				for (int i_396_ = 0; i_396_ < i_395_; i_396_++) {
					int i_397_ = complextile.anIntArray195[i_396_] - cameraX;
					int i_398_ = complextile.anIntArray196[i_396_] - cameraZ;
					int i_399_ = complextile.anIntArray197[i_396_] - cameraY;
					int i_400_ = i_399_ * i_391_ + i_397_ * i_394_ >> 16;
					i_399_ = i_399_ * i_394_ - i_397_ * i_391_ >> 16;
					i_397_ = i_400_;
					i_400_ = i_398_ * i_392_ - i_399_ * i_390_ >> 16;
					i_399_ = i_398_ * i_390_ + i_399_ * i_392_ >> 16;
					i_398_ = i_400_;
					if (i_399_ < 50) {
						return;
					}
					if (complextile.anIntArray204 != null) {
						ComplexTile.anIntArray212[i_396_] = i_397_;
						ComplexTile.anIntArray213[i_396_] = i_398_;
						ComplexTile.anIntArray214[i_396_] = i_399_;
					}
					ComplexTile.anIntArray210[i_396_] = Rasterizer3D.centerX + (i_397_ << 9) / i_399_;
					ComplexTile.anIntArray211[i_396_] = Rasterizer3D.centerY + (i_398_ << 9) / i_399_;
				}
				Rasterizer3D.anInt1485 = 0;
				i_395_ = complextile.anIntArray201.length;
				for (int i_401_ = 0; i_401_ < i_395_; i_401_++) {
					int i_402_ = complextile.anIntArray201[i_401_];
					int i_403_ = complextile.anIntArray202[i_401_];
					int i_404_ = complextile.anIntArray203[i_401_];
					int i_405_ = ComplexTile.anIntArray210[i_402_];
					int i_406_ = ComplexTile.anIntArray210[i_403_];
					int i_407_ = ComplexTile.anIntArray210[i_404_];
					int i_408_ = ComplexTile.anIntArray211[i_402_];
					int i_409_ = ComplexTile.anIntArray211[i_403_];
					int i_410_ = ComplexTile.anIntArray211[i_404_];
					if ((i_405_ - i_406_) * (i_410_ - i_409_) - (i_408_ - i_409_) * (i_407_ - i_406_) > 0) {
						Rasterizer3D.aBoolean1482 = false;
						if (i_405_ < 0 || i_406_ < 0 || i_407_ < 0 || i_405_ > Rasterizer.virtualBottomX
								|| i_406_ > Rasterizer.virtualBottomX || i_407_ > Rasterizer.virtualBottomX) {
							Rasterizer3D.aBoolean1482 = true;
						}
						if (aBoolean547
								&& method540(clickX, clickY, i_408_, i_409_, i_410_, i_405_, i_406_,
										i_407_)) {
							tileClickX = i;
							tileClickY = i_393_;
						}
						if (complextile.anIntArray204 == null || complextile.anIntArray204[i_401_] == -1) {
							if (complextile.anIntArray198[i_401_] != 12345678) {
								Rasterizer3D.method371(i_408_, i_409_, i_410_, i_405_, i_406_, i_407_,
										complextile.anIntArray198[i_401_], complextile.anIntArray199[i_401_],
										complextile.anIntArray200[i_401_]);
							}
						} else if (!lowMemory) {
							if (complextile.aBoolean205) {
								Rasterizer3D.method375(i_408_, i_409_, i_410_, i_405_, i_406_, i_407_,
										complextile.anIntArray198[i_401_], complextile.anIntArray199[i_401_],
										complextile.anIntArray200[i_401_], ComplexTile.anIntArray212[0],
										ComplexTile.anIntArray212[1], ComplexTile.anIntArray212[3],
										ComplexTile.anIntArray213[0], ComplexTile.anIntArray213[1],
										ComplexTile.anIntArray213[3], ComplexTile.anIntArray214[0],
										ComplexTile.anIntArray214[1], ComplexTile.anIntArray214[3],
										complextile.anIntArray204[i_401_]);
							} else {
								Rasterizer3D.method375(i_408_, i_409_, i_410_, i_405_, i_406_, i_407_,
										complextile.anIntArray198[i_401_], complextile.anIntArray199[i_401_],
										complextile.anIntArray200[i_401_], ComplexTile.anIntArray212[i_402_],
										ComplexTile.anIntArray212[i_403_], ComplexTile.anIntArray212[i_404_],
										ComplexTile.anIntArray213[i_402_], ComplexTile.anIntArray213[i_403_],
										ComplexTile.anIntArray213[i_404_], ComplexTile.anIntArray214[i_402_],
										ComplexTile.anIntArray214[i_403_], ComplexTile.anIntArray214[i_404_],
										complextile.anIntArray204[i_401_]);
							}
						} else {
							int i_411_ = anIntArray565[complextile.anIntArray204[i_401_]];
							Rasterizer3D.method371(i_408_, i_409_, i_410_, i_405_, i_406_, i_407_,
									method539(-361, i_411_, complextile.anIntArray198[i_401_]),
									method539(-361, i_411_, complextile.anIntArray199[i_401_]),
									method539(-361, i_411_, complextile.anIntArray200[i_401_]));
						}
					}
				}
			}
		} catch (RuntimeException runtimeexception) {
			SignLink.reportError("37932, " + i + ", " + b + ", " + i_390_ + ", " + i_391_ + ", " + complextile + ", "
					+ i_392_ + ", " + i_393_ + ", " + i_394_ + ", " + runtimeexception.toString());
			throw new RuntimeException();
		}
	}

	public int method539(int i, int i_412_, int i_413_) {
		try {
			i_413_ = 127 - i_413_;
			i_413_ = i_413_ * (i_412_ & 0x7f) / 160;
			if (i >= 0) {
				return anInt510;
			}
			if (i_413_ < 2) {
				i_413_ = 2;
			} else if (i_413_ > 126) {
				i_413_ = 126;
			}
			return (i_412_ & 0xff80) + i_413_;
		} catch (RuntimeException runtimeexception) {
			SignLink.reportError("11440, " + i + ", " + i_412_ + ", " + i_413_ + ", " + runtimeexception.toString());
			throw new RuntimeException();
		}
	}

	public boolean method540(int i, int i_414_, int i_415_, int i_416_, int i_417_, int i_418_, int i_419_, int i_420_) {
		if (i_414_ < i_415_ && i_414_ < i_416_ && i_414_ < i_417_) {
			return false;
		}
		if (i_414_ > i_415_ && i_414_ > i_416_ && i_414_ > i_417_) {
			return false;
		}
		if (i < i_418_ && i < i_419_ && i < i_420_) {
			return false;
		}
		if (i > i_418_ && i > i_419_ && i > i_420_) {
			return false;
		}
		int i_421_ = (i_414_ - i_415_) * (i_419_ - i_418_) - (i - i_418_) * (i_416_ - i_415_);
		int i_422_ = (i_414_ - i_417_) * (i_418_ - i_420_) - (i - i_420_) * (i_415_ - i_417_);
		int i_423_ = (i_414_ - i_416_) * (i_420_ - i_419_) - (i - i_419_) * (i_417_ - i_416_);
		if (i_421_ * i_423_ > 0 && i_423_ * i_422_ > 0) {
			return true;
		}
		return false;
	}

	private void method541(int i) {
		try {
			if (i == 0) {
				int i_424_ = cullingClusterPointer[anInt527];
				SceneCluster[] sceneclusters = cullingClusters[anInt527];
				anInt555 = 0;
				int i_425_ = 0;
				for (/**/; i_425_ < i_424_; i_425_++) {
					SceneCluster scenecluster = sceneclusters[i_425_];
					if (scenecluster.anInt583 == 1) {
						int i_426_ = scenecluster.anInt579 - anInt533 + 25;
						if (i_426_ >= 0 && i_426_ <= 50) {
							int i_427_ = scenecluster.anInt581 - anInt534 + 25;
							if (i_427_ < 0) {
								i_427_ = 0;
							}
							int i_428_ = scenecluster.anInt582 - anInt534 + 25;
							if (i_428_ > 50) {
								i_428_ = 50;
							}
							boolean bool = false;
							while (i_427_ <= i_428_) {
								if (aBooleanArrayArray572[i_426_][i_427_++]) {
									bool = true;
									break;
								}
							}
							if (bool) {
								int i_429_ = cameraX - scenecluster.anInt584;
								if (i_429_ > 32) {
									scenecluster.anInt590 = 1;
								} else {
									if (i_429_ >= -32) {
										continue;
									}
									scenecluster.anInt590 = 2;
									i_429_ = -i_429_;
								}
								scenecluster.anInt593 = (scenecluster.anInt586 - cameraY << 8) / i_429_;
								scenecluster.anInt594 = (scenecluster.anInt587 - cameraY << 8) / i_429_;
								scenecluster.anInt595 = (scenecluster.anInt588 - cameraZ << 8) / i_429_;
								scenecluster.anInt596 = (scenecluster.anInt589 - cameraZ << 8) / i_429_;
								aSceneClusterArray556[anInt555++] = scenecluster;
							}
						}
					} else if (scenecluster.anInt583 == 2) {
						int i_430_ = scenecluster.anInt581 - anInt534 + 25;
						if (i_430_ >= 0 && i_430_ <= 50) {
							int i_431_ = scenecluster.anInt579 - anInt533 + 25;
							if (i_431_ < 0) {
								i_431_ = 0;
							}
							int i_432_ = scenecluster.anInt580 - anInt533 + 25;
							if (i_432_ > 50) {
								i_432_ = 50;
							}
							boolean bool = false;
							while (i_431_ <= i_432_) {
								if (aBooleanArrayArray572[i_431_++][i_430_]) {
									bool = true;
									break;
								}
							}
							if (bool) {
								int i_433_ = cameraY - scenecluster.anInt586;
								if (i_433_ > 32) {
									scenecluster.anInt590 = 3;
								} else {
									if (i_433_ >= -32) {
										continue;
									}
									scenecluster.anInt590 = 4;
									i_433_ = -i_433_;
								}
								scenecluster.anInt591 = (scenecluster.anInt584 - cameraX << 8) / i_433_;
								scenecluster.anInt592 = (scenecluster.anInt585 - cameraX << 8) / i_433_;
								scenecluster.anInt595 = (scenecluster.anInt588 - cameraZ << 8) / i_433_;
								scenecluster.anInt596 = (scenecluster.anInt589 - cameraZ << 8) / i_433_;
								aSceneClusterArray556[anInt555++] = scenecluster;
							}
						}
					} else if (scenecluster.anInt583 == 4) {
						int i_434_ = scenecluster.anInt588 - cameraZ;
						if (i_434_ > 128) {
							int i_435_ = scenecluster.anInt581 - anInt534 + 25;
							if (i_435_ < 0) {
								i_435_ = 0;
							}
							int i_436_ = scenecluster.anInt582 - anInt534 + 25;
							if (i_436_ > 50) {
								i_436_ = 50;
							}
							if (i_435_ <= i_436_) {
								int i_437_ = scenecluster.anInt579 - anInt533 + 25;
								if (i_437_ < 0) {
									i_437_ = 0;
								}
								int i_438_ = scenecluster.anInt580 - anInt533 + 25;
								if (i_438_ > 50) {
									i_438_ = 50;
								}
								boolean bool = false;
								while_11_: for (int i_439_ = i_437_; i_439_ <= i_438_; i_439_++) {
									for (int i_440_ = i_435_; i_440_ <= i_436_; i_440_++) {
										if (aBooleanArrayArray572[i_439_][i_440_]) {
											bool = true;
											break while_11_;
										}
									}
								}
								if (bool) {
									scenecluster.anInt590 = 5;
									scenecluster.anInt591 = (scenecluster.anInt584 - cameraX << 8) / i_434_;
									scenecluster.anInt592 = (scenecluster.anInt585 - cameraX << 8) / i_434_;
									scenecluster.anInt593 = (scenecluster.anInt586 - cameraY << 8) / i_434_;
									scenecluster.anInt594 = (scenecluster.anInt587 - cameraY << 8) / i_434_;
									aSceneClusterArray556[anInt555++] = scenecluster;
								}
							}
						}
					}
				}
			}
		} catch (RuntimeException runtimeexception) {
			SignLink.reportError("66730, " + i + ", " + runtimeexception.toString());
			throw new RuntimeException();
		}
	}

	private boolean method542(int i, int i_441_, int i_442_) {
		int i_443_ = anIntArrayArrayArray525[i][i_441_][i_442_];
		if (i_443_ == -anInt528) {
			return false;
		}
		if (i_443_ == anInt528) {
			return true;
		}
		int i_444_ = i_441_ << 7;
		int i_445_ = i_442_ << 7;
		if (method546(i_444_ + 1, tileHeightMap[i][i_441_][i_442_], i_445_ + 1)
				&& method546(i_444_ + 128 - 1, tileHeightMap[i][i_441_ + 1][i_442_], i_445_ + 1)
				&& method546(i_444_ + 128 - 1, tileHeightMap[i][i_441_ + 1][i_442_ + 1], i_445_ + 128 - 1)
				&& method546(i_444_ + 1, tileHeightMap[i][i_441_][i_442_ + 1], i_445_ + 128 - 1)) {
			anIntArrayArrayArray525[i][i_441_][i_442_] = anInt528;
			return true;
		}
		anIntArrayArrayArray525[i][i_441_][i_442_] = -anInt528;
		return false;
	}

	private boolean method543(int i, int i_446_, int i_447_, int i_448_) {
		if (!method542(i, i_446_, i_447_)) {
			return false;
		}
		int i_449_ = i_446_ << 7;
		int i_450_ = i_447_ << 7;
		int i_451_ = tileHeightMap[i][i_446_][i_447_] - 1;
		int i_452_ = i_451_ - 120;
		int i_453_ = i_451_ - 230;
		int i_454_ = i_451_ - 238;
		if (i_448_ < 16) {
			if (i_448_ == 1) {
				if (i_449_ > cameraX) {
					if (!method546(i_449_, i_451_, i_450_)) {
						return false;
					}
					if (!method546(i_449_, i_451_, i_450_ + 128)) {
						return false;
					}
				}
				if (i > 0) {
					if (!method546(i_449_, i_452_, i_450_)) {
						return false;
					}
					if (!method546(i_449_, i_452_, i_450_ + 128)) {
						return false;
					}
				}
				if (!method546(i_449_, i_453_, i_450_)) {
					return false;
				}
				if (!method546(i_449_, i_453_, i_450_ + 128)) {
					return false;
				}
				return true;
			}
			if (i_448_ == 2) {
				if (i_450_ < cameraY) {
					if (!method546(i_449_, i_451_, i_450_ + 128)) {
						return false;
					}
					if (!method546(i_449_ + 128, i_451_, i_450_ + 128)) {
						return false;
					}
				}
				if (i > 0) {
					if (!method546(i_449_, i_452_, i_450_ + 128)) {
						return false;
					}
					if (!method546(i_449_ + 128, i_452_, i_450_ + 128)) {
						return false;
					}
				}
				if (!method546(i_449_, i_453_, i_450_ + 128)) {
					return false;
				}
				if (!method546(i_449_ + 128, i_453_, i_450_ + 128)) {
					return false;
				}
				return true;
			}
			if (i_448_ == 4) {
				if (i_449_ < cameraX) {
					if (!method546(i_449_ + 128, i_451_, i_450_)) {
						return false;
					}
					if (!method546(i_449_ + 128, i_451_, i_450_ + 128)) {
						return false;
					}
				}
				if (i > 0) {
					if (!method546(i_449_ + 128, i_452_, i_450_)) {
						return false;
					}
					if (!method546(i_449_ + 128, i_452_, i_450_ + 128)) {
						return false;
					}
				}
				if (!method546(i_449_ + 128, i_453_, i_450_)) {
					return false;
				}
				if (!method546(i_449_ + 128, i_453_, i_450_ + 128)) {
					return false;
				}
				return true;
			}
			if (i_448_ == 8) {
				if (i_450_ > cameraY) {
					if (!method546(i_449_, i_451_, i_450_)) {
						return false;
					}
					if (!method546(i_449_ + 128, i_451_, i_450_)) {
						return false;
					}
				}
				if (i > 0) {
					if (!method546(i_449_, i_452_, i_450_)) {
						return false;
					}
					if (!method546(i_449_ + 128, i_452_, i_450_)) {
						return false;
					}
				}
				if (!method546(i_449_, i_453_, i_450_)) {
					return false;
				}
				if (!method546(i_449_ + 128, i_453_, i_450_)) {
					return false;
				}
				return true;
			}
		}
		if (!method546(i_449_ + 64, i_454_, i_450_ + 64)) {
			return false;
		}
		if (i_448_ == 16) {
			if (!method546(i_449_, i_453_, i_450_ + 128)) {
				return false;
			}
			return true;
		}
		if (i_448_ == 32) {
			if (!method546(i_449_ + 128, i_453_, i_450_ + 128)) {
				return false;
			}
			return true;
		}
		if (i_448_ == 64) {
			if (!method546(i_449_ + 128, i_453_, i_450_)) {
				return false;
			}
			return true;
		}
		if (i_448_ == 128) {
			if (!method546(i_449_, i_453_, i_450_)) {
				return false;
			}
			return true;
		}
		System.out.println("Warning unsupported wall type");
		return true;
	}

	private boolean wallDecorationOccluded(int i, int i_455_, int i_456_, int i_457_) {
		if (!method542(i, i_455_, i_456_)) {
			return false;
		}
		int i_458_ = i_455_ << 7;
		int i_459_ = i_456_ << 7;
		if (method546(i_458_ + 1, tileHeightMap[i][i_455_][i_456_] - i_457_, i_459_ + 1)
				&& method546(i_458_ + 128 - 1, tileHeightMap[i][i_455_ + 1][i_456_] - i_457_, i_459_ + 1)
				&& method546(i_458_ + 128 - 1, tileHeightMap[i][i_455_ + 1][i_456_ + 1] - i_457_,
						i_459_ + 128 - 1)
				&& method546(i_458_ + 1, tileHeightMap[i][i_455_][i_456_ + 1] - i_457_, i_459_ + 128 - 1)) {
			return true;
		}
		return false;
	}

	private boolean method545(int i, int i_460_, int i_461_, int i_462_, int i_463_, int i_464_) {
		if (i_460_ == i_461_ && i_462_ == i_463_) {
			if (!method542(i, i_460_, i_462_)) {
				return false;
			}
			int i_465_ = i_460_ << 7;
			int i_466_ = i_462_ << 7;
			if (method546(i_465_ + 1, tileHeightMap[i][i_460_][i_462_] - i_464_, i_466_ + 1)
					&& method546(i_465_ + 128 - 1, tileHeightMap[i][i_460_ + 1][i_462_] - i_464_, i_466_ + 1)
					&& method546(i_465_ + 128 - 1, tileHeightMap[i][i_460_ + 1][i_462_ + 1] - i_464_,
							i_466_ + 128 - 1)
					&& method546(i_465_ + 1, tileHeightMap[i][i_460_][i_462_ + 1] - i_464_, i_466_ + 128 - 1)) {
				return true;
			}
			return false;
		}
		for (int i_467_ = i_460_; i_467_ <= i_461_; i_467_++) {
			for (int i_468_ = i_462_; i_468_ <= i_463_; i_468_++) {
				if (anIntArrayArrayArray525[i][i_467_][i_468_] == -anInt528) {
					return false;
				}
			}
		}
		int i_469_ = (i_460_ << 7) + 1;
		int i_470_ = (i_462_ << 7) + 2;
		int i_471_ = tileHeightMap[i][i_460_][i_462_] - i_464_;
		if (!method546(i_469_, i_471_, i_470_)) {
			return false;
		}
		int i_472_ = (i_461_ << 7) - 1;
		if (!method546(i_472_, i_471_, i_470_)) {
			return false;
		}
		int i_473_ = (i_463_ << 7) - 1;
		if (!method546(i_469_, i_471_, i_473_)) {
			return false;
		}
		if (!method546(i_472_, i_471_, i_473_)) {
			return false;
		}
		return true;
	}

	private boolean method546(int i, int i_474_, int i_475_) {
		for (int i_476_ = 0; i_476_ < anInt555; i_476_++) {
			SceneCluster scenecluster = aSceneClusterArray556[i_476_];
			if (scenecluster.anInt590 == 1) {
				int i_477_ = scenecluster.anInt584 - i;
				if (i_477_ > 0) {
					int i_478_ = scenecluster.anInt586 + (scenecluster.anInt593 * i_477_ >> 8);
					int i_479_ = scenecluster.anInt587 + (scenecluster.anInt594 * i_477_ >> 8);
					int i_480_ = scenecluster.anInt588 + (scenecluster.anInt595 * i_477_ >> 8);
					int i_481_ = scenecluster.anInt589 + (scenecluster.anInt596 * i_477_ >> 8);
					if (i_475_ >= i_478_ && i_475_ <= i_479_ && i_474_ >= i_480_ && i_474_ <= i_481_) {
						return true;
					}
				}
			} else if (scenecluster.anInt590 == 2) {
				int i_482_ = i - scenecluster.anInt584;
				if (i_482_ > 0) {
					int i_483_ = scenecluster.anInt586 + (scenecluster.anInt593 * i_482_ >> 8);
					int i_484_ = scenecluster.anInt587 + (scenecluster.anInt594 * i_482_ >> 8);
					int i_485_ = scenecluster.anInt588 + (scenecluster.anInt595 * i_482_ >> 8);
					int i_486_ = scenecluster.anInt589 + (scenecluster.anInt596 * i_482_ >> 8);
					if (i_475_ >= i_483_ && i_475_ <= i_484_ && i_474_ >= i_485_ && i_474_ <= i_486_) {
						return true;
					}
				}
			} else if (scenecluster.anInt590 == 3) {
				int i_487_ = scenecluster.anInt586 - i_475_;
				if (i_487_ > 0) {
					int i_488_ = scenecluster.anInt584 + (scenecluster.anInt591 * i_487_ >> 8);
					int i_489_ = scenecluster.anInt585 + (scenecluster.anInt592 * i_487_ >> 8);
					int i_490_ = scenecluster.anInt588 + (scenecluster.anInt595 * i_487_ >> 8);
					int i_491_ = scenecluster.anInt589 + (scenecluster.anInt596 * i_487_ >> 8);
					if (i >= i_488_ && i <= i_489_ && i_474_ >= i_490_ && i_474_ <= i_491_) {
						return true;
					}
				}
			} else if (scenecluster.anInt590 == 4) {
				int i_492_ = i_475_ - scenecluster.anInt586;
				if (i_492_ > 0) {
					int i_493_ = scenecluster.anInt584 + (scenecluster.anInt591 * i_492_ >> 8);
					int i_494_ = scenecluster.anInt585 + (scenecluster.anInt592 * i_492_ >> 8);
					int i_495_ = scenecluster.anInt588 + (scenecluster.anInt595 * i_492_ >> 8);
					int i_496_ = scenecluster.anInt589 + (scenecluster.anInt596 * i_492_ >> 8);
					if (i >= i_493_ && i <= i_494_ && i_474_ >= i_495_ && i_474_ <= i_496_) {
						return true;
					}
				}
			} else if (scenecluster.anInt590 == 5) {
				int i_497_ = i_474_ - scenecluster.anInt588;
				if (i_497_ > 0) {
					int i_498_ = scenecluster.anInt584 + (scenecluster.anInt591 * i_497_ >> 8);
					int i_499_ = scenecluster.anInt585 + (scenecluster.anInt592 * i_497_ >> 8);
					int i_500_ = scenecluster.anInt586 + (scenecluster.anInt593 * i_497_ >> 8);
					int i_501_ = scenecluster.anInt587 + (scenecluster.anInt594 * i_497_ >> 8);
					if (i >= i_498_ && i <= i_499_ && i_475_ >= i_500_ && i_475_ <= i_501_) {
						return true;
					}
				}
			}
		}
		return false;
	}
}
