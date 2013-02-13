package com.runescape.scene;

import com.runescape.collection.LinkedList;
import com.runescape.media.Rasterizer;
import com.runescape.media.Rasterizer3D;
import com.runescape.media.VertexNormal;
import com.runescape.media.renderable.Model;
import com.runescape.media.renderable.Renderable;
import com.runescape.scene.tile.ComplexTile;
import com.runescape.scene.tile.FloorDecoration;
import com.runescape.scene.tile.GenericTile;
import com.runescape.scene.tile.SceneTile;
import com.runescape.scene.tile.Wall;
import com.runescape.scene.tile.WallDecoration;
import com.runescape.util.SignLink;

public class Scene {

	private boolean aBoolean509 = true;
	private int anInt510;
	private boolean aBoolean514 = true;
	private boolean aBoolean515 = false;
	public static boolean lowMemory = true;
	protected int anInt517;
	protected int anInt518;
	protected int anInt519;
	protected int[][][] anIntArrayArrayArray520;
	protected SceneTile[][][] tiles;
	protected int anInt522;
	protected int anInt523;
	protected SceneSpawnRequest[] aSceneSpawnRequestArray524 = new SceneSpawnRequest[5000];
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
	static int anInt535;
	static int anInt536;
	static int anInt537;
	static int anInt538;
	static int anInt539;
	static int anInt540;
	static int anInt541;
	static SceneSpawnRequest[] aSceneSpawnRequestArray542 = new SceneSpawnRequest[100];
	static final int[] anIntArray543 = { 53, -53, -53, 53 };
	static final int[] anIntArray544 = { -53, -53, 53, 53 };
	static final int[] anIntArray545 = { -45, 45, 45, -45 };
	static final int[] anIntArray546 = { 45, 45, -45, -45 };
	static boolean aBoolean547;
	static int anInt548;
	static int anInt549;
	public static int anInt550 = -1;
	public static int anInt551 = -1;
	static int anInt552 = 4;
	static int[] anIntArray553 = new int[Scene.anInt552];
	static SceneCluster[][] aSceneClusterArrayArray554 = new SceneCluster[Scene.anInt552][500];
	public static int anInt555;
	static SceneCluster[] aSceneClusterArray556 = new SceneCluster[500];
	static LinkedList aLinkedList557 = new LinkedList();
	static final int[] anIntArray558 = { 19, 55, 38, 155, 255, 110, 137, 205, 76 };
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
	protected int[][] anIntArrayArray569 = { new int[16], { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 },
			{ 1, 0, 0, 0, 1, 1, 0, 0, 1, 1, 1, 0, 1, 1, 1, 1 }, { 1, 1, 0, 0, 1, 1, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0 },
			{ 0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 0, 1, 0, 0, 0, 1 }, { 0, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 },
			{ 1, 1, 1, 0, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1 }, { 1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0 },
			{ 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 1, 0, 0 }, { 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 1, 0, 0, 1, 1 },
			{ 1, 1, 1, 1, 1, 1, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 1, 1, 0, 1, 1, 1, 0, 1, 1, 1 },
			{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 1, 1, 1, 1 } };
	protected int[][] anIntArrayArray570 = { { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15 },
			{ 12, 8, 4, 0, 13, 9, 5, 1, 14, 10, 6, 2, 15, 11, 7, 3 },
			{ 15, 14, 13, 12, 11, 10, 9, 8, 7, 6, 5, 4, 3, 2, 1, 0 },
			{ 3, 7, 11, 15, 2, 6, 10, 14, 1, 5, 9, 13, 0, 4, 8, 12 } };
	static boolean[][][][] aBooleanArrayArrayArrayArray571 = new boolean[8][32][51][51];
	static boolean[][] aBooleanArrayArray572;
	static int anInt573;
	static int anInt574;
	static int anInt575;
	static int anInt576;
	static int anInt577;
	static int anInt578;

	public Scene(int i, byte b, int i_0_, int[][][] is, int i_1_) {
		try {
			anInt517 = i_1_;
			anInt518 = i_0_;
			anInt519 = i;
			if (b != 43) {
				throw new NullPointerException();
			}
			tiles = new SceneTile[i_1_][i_0_][i];
			anIntArrayArrayArray525 = new int[i_1_][i_0_ + 1][i + 1];
			anIntArrayArrayArray520 = is;
			method496(619);
		} catch (RuntimeException runtimeexception) {
			SignLink.reportError("29434, " + i + ", " + b + ", " + i_0_ + ", " + is + ", " + i_1_ + ", "
					+ runtimeexception.toString());
			throw new RuntimeException();
		}
	}

	public static void method495(int i) {
		try {
			Scene.aSceneSpawnRequestArray542 = null;
			while (i >= 0) {
			}
			Scene.anIntArray553 = null;
			Scene.aSceneClusterArrayArray554 = null;
			Scene.aLinkedList557 = null;
			Scene.aBooleanArrayArrayArrayArray571 = null;
			Scene.aBooleanArrayArray572 = null;
		} catch (RuntimeException runtimeexception) {
			SignLink.reportError("40481, " + i + ", " + runtimeexception.toString());
			throw new RuntimeException();
		}
	}

	public void method496(int i) {
		try {
			for (int i_2_ = 0; i_2_ < anInt517; i_2_++) {
				for (int i_3_ = 0; i_3_ < anInt518; i_3_++) {
					for (int i_4_ = 0; i_4_ < anInt519; i_4_++) {
						tiles[i_2_][i_3_][i_4_] = null;
					}
				}
			}
			i = 37 / i;
			for (int i_5_ = 0; i_5_ < Scene.anInt552; i_5_++) {
				for (int i_6_ = 0; i_6_ < Scene.anIntArray553[i_5_]; i_6_++) {
					Scene.aSceneClusterArrayArray554[i_5_][i_6_] = null;
				}
				Scene.anIntArray553[i_5_] = 0;
			}
			for (int i_7_ = 0; i_7_ < anInt523; i_7_++) {
				aSceneSpawnRequestArray524[i_7_] = null;
			}
			anInt523 = 0;
			for (int i_8_ = 0; i_8_ < Scene.aSceneSpawnRequestArray542.length; i_8_++) {
				Scene.aSceneSpawnRequestArray542[i_8_] = null;
			}
		} catch (RuntimeException runtimeexception) {
			SignLink.reportError("83723, " + i + ", " + runtimeexception.toString());
			throw new RuntimeException();
		}
	}

	public void method497(int i, int i_9_) {
		try {
			if (i_9_ == -34686) {
				anInt522 = i;
				for (int i_10_ = 0; i_10_ < anInt518; i_10_++) {
					for (int i_11_ = 0; i_11_ < anInt519; i_11_++) {
						if (tiles[i][i_10_][i_11_] == null) {
							tiles[i][i_10_][i_11_] = new SceneTile(i, i_10_, i_11_);
						}
					}
				}
			}
		} catch (RuntimeException runtimeexception) {
			SignLink.reportError("44284, " + i + ", " + i_9_ + ", " + runtimeexception.toString());
			throw new RuntimeException();
		}
	}

	public void setBridgeMode(int i, int i_12_, int i_13_) {
		try {
			SceneTile scenetile = tiles[0][i_12_][i];
			for (int i_14_ = 0; i_14_ < 3; i_14_++) {
				SceneTile scenetile_15_ = tiles[i_14_][i_12_][i] = tiles[i_14_ + 1][i_12_][i];
				if (scenetile_15_ != null) {
					scenetile_15_.anInt1318--;
					for (int i_16_ = 0; i_16_ < scenetile_15_.sceneSpawnRequestCount; i_16_++) {
						SceneSpawnRequest scenespawnrequest = scenetile_15_.sceneSpawnRequests[i_16_];
						if ((scenespawnrequest.anInt609 >> 29 & 0x3) == 2 && scenespawnrequest.x == i_12_
								&& scenespawnrequest.y == i) {
							scenespawnrequest.anInt597--;
						}
					}
				}
			}
			while (i_13_ >= 0) {
				for (int i_17_ = 1; i_17_ > 0; i_17_++) {
					/* empty */
				}
			}
			if (tiles[0][i_12_][i] == null) {
				tiles[0][i_12_][i] = new SceneTile(0, i_12_, i);
			}
			tiles[0][i_12_][i].sceneTile = scenetile;
			tiles[3][i_12_][i] = null;
		} catch (RuntimeException runtimeexception) {
			SignLink.reportError("63341, " + i + ", " + i_12_ + ", " + i_13_ + ", " + runtimeexception.toString());
			throw new RuntimeException();
		}
	}

	public static void createCullingOcclussionBox(int i, int i_18_, int i_19_, int i_20_, int i_21_, int i_22_,
			int i_23_, int i_24_, int i_25_) {
		try {
			while (i_23_ >= 0) {
			}
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
			Scene.aSceneClusterArrayArray554[i][Scene.anIntArray553[i]++] = scenecluster;
		} catch (RuntimeException runtimeexception) {
			SignLink.reportError("14863, " + i + ", " + i_18_ + ", " + i_19_ + ", " + i_20_ + ", " + i_21_ + ", "
					+ i_22_ + ", " + i_23_ + ", " + i_24_ + ", " + i_25_ + ", " + runtimeexception.toString());
			throw new RuntimeException();
		}
	}

	public void method500(int i, int i_26_, int i_27_, int i_28_) {
		SceneTile scenetile = tiles[i][i_26_][i_27_];
		if (scenetile != null) {
			tiles[i][i_26_][i_27_].anInt1332 = i_28_;
		}
	}

	public void method501(int i, int i_29_, int i_30_, int i_31_, int i_32_, int i_33_, int i_34_, int i_35_,
			int i_36_, int i_37_, int i_38_, int i_39_, int i_40_, int i_41_, int i_42_, int i_43_, int i_44_,
			int i_45_, int i_46_, int i_47_) {
		if (i_31_ == 0) {
			GenericTile generictile = new GenericTile(i_38_, i_39_, i_40_, i_41_, -1, i_46_, false);
			for (int i_48_ = i; i_48_ >= 0; i_48_--) {
				if (tiles[i_48_][i_29_][i_30_] == null) {
					tiles[i_48_][i_29_][i_30_] = new SceneTile(i_48_, i_29_, i_30_);
				}
			}
			tiles[i][i_29_][i_30_].genericTile = generictile;
		} else if (i_31_ == 1) {
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
					i_40_, i_37_, i_35_, i_34_, i_31_, i_45_, i_43_, i_39_, 3, i_29_, i_47_);
			for (int i_50_ = i; i_50_ >= 0; i_50_--) {
				if (tiles[i_50_][i_29_][i_30_] == null) {
					tiles[i_50_][i_29_][i_30_] = new SceneTile(i_50_, i_29_, i_30_);
				}
			}
			tiles[i][i_29_][i_30_].complexTile = complextile;
		}
	}

	public void addGroundDecoration(int i, int i_51_, int i_52_, int i_53_, Renderable renderable, byte b, int i_54_,
			int i_55_) {
		try {
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
		} catch (RuntimeException runtimeexception) {
			SignLink.reportError("11891, " + i + ", " + i_51_ + ", " + i_52_ + ", " + i_53_ + ", " + renderable + ", "
					+ b + ", " + i_54_ + ", " + i_55_ + ", " + runtimeexception.toString());
			throw new RuntimeException();
		}
	}

	public void method503(byte b, int x, int i_56_, Renderable renderable, int i_57_, Renderable renderable_58_,
			Renderable renderable_59_, int plane, int y) {
		try {
			CameraAngle cameraAngle = new CameraAngle();
			cameraAngle.aRenderable150 = renderable_59_;
			cameraAngle.y = x * 128 + 64;
			cameraAngle.z = y * 128 + 64;
			if (b == 7) {
				cameraAngle.x = i_57_;
				cameraAngle.anInt153 = i_56_;
				cameraAngle.aRenderable151 = renderable;
				cameraAngle.aRenderable152 = renderable_58_;
				int i_62_ = 0;
				SceneTile scenetile = tiles[plane][x][y];
				if (scenetile != null) {
					for (int sceneSpawnRequest = 0; sceneSpawnRequest < scenetile.sceneSpawnRequestCount; sceneSpawnRequest++) {
						if (scenetile.sceneSpawnRequests[sceneSpawnRequest].aRenderable601 instanceof Model) {
							int i_64_ = ((Model) scenetile.sceneSpawnRequests[sceneSpawnRequest].aRenderable601).anInt1647;
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
		} catch (RuntimeException runtimeexception) {
			SignLink.reportError("4061, " + b + ", " + x + ", " + i_56_ + ", " + renderable + ", " + i_57_ + ", "
					+ renderable_58_ + ", " + renderable_59_ + ", " + plane + ", " + y + ", "
					+ runtimeexception.toString());
			throw new RuntimeException();
		}
	}

	public void method504(int faceUnknown, Renderable renderable, boolean bool, int hash, int y, byte config, int x,
			Renderable renderable_68_, int plane, int face, int i_71_) {
		try {
			if (!bool) {
				aBoolean514 = !aBoolean514;
			}
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
		} catch (RuntimeException runtimeexception) {
			SignLink.reportError("65870, " + faceUnknown + ", " + renderable + ", " + bool + ", " + hash + ", " + y
					+ ", " + config + ", " + x + ", " + renderable_68_ + ", " + plane + ", " + face + ", " + i_71_
					+ ", " + runtimeexception.toString());
			throw new RuntimeException();
		}
	}

	void addWallDecoration(int x, int xOffset, int y, int yOffset, int z, int plane, int face, int faceUnknown,
			byte config, int hash, Renderable renderable) {
		if (renderable != null) {
			WallDecoration wallDecoration = new WallDecoration();
			wallDecoration.hash = hash;
			wallDecoration.config = config;
			wallDecoration.y = x * 128 + 64 + xOffset;
			wallDecoration.x = y * 128 + 64 + yOffset;
			wallDecoration.plane = z;
			wallDecoration.renderable = renderable;
			wallDecoration.faceUnknown = faceUnknown;
			wallDecoration.face = face;
			for (int planeCounter = plane; planeCounter >= 0; planeCounter--) {
				if (tiles[planeCounter][x][y] == null) {
					tiles[planeCounter][x][y] = new SceneTile(planeCounter, x, y);
				}
			}
			tiles[plane][x][y].wallDecoration = wallDecoration;
		}
	}

	public boolean method506(int i, byte b, int i_83_, int i_84_, Renderable renderable, int i_85_, int i_86_,
			int i_87_, byte b_88_, int i_89_, int i_90_) {
		try {
			if (b_88_ != 110) {
			}
			if (renderable == null) {
				return true;
			}
			int i_91_ = i_90_ * 128 + 64 * i_85_;
			int i_92_ = i_89_ * 128 + 64 * i_84_;
			return method509(i_86_, i_90_, i_89_, i_85_, i_84_, i_91_, i_92_, i_83_, renderable, i_87_, false, i, b);
		} catch (RuntimeException runtimeexception) {
			SignLink.reportError("94021, " + i + ", " + b + ", " + i_83_ + ", " + i_84_ + ", " + renderable + ", "
					+ i_85_ + ", " + i_86_ + ", " + i_87_ + ", " + b_88_ + ", " + i_89_ + ", " + i_90_ + ", "
					+ runtimeexception.toString());
			throw new RuntimeException();
		}
	}

	public boolean method507(int i, int i_93_, byte b, int i_94_, int i_95_, int i_96_, int i_97_, int i_98_,
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
				if (i_93_ > 640 && i_93_ < 1408) {
					i_102_ += 128;
				}
				if (i_93_ > 1152 && i_93_ < 1920) {
					i_101_ += 128;
				}
				if (i_93_ > 1664 || i_93_ < 384) {
					i_100_ -= 128;
				}
				if (i_93_ > 128 && i_93_ < 896) {
					i_99_ -= 128;
				}
			}
			i_99_ /= 128;
			if (b == 6) {
				b = (byte) 0;
			} else {
				throw new NullPointerException();
			}
			i_100_ /= 128;
			i_101_ /= 128;
			i_102_ /= 128;
			return method509(i, i_99_, i_100_, i_101_ - i_99_ + 1, i_102_ - i_100_ + 1, i_98_, i_96_, i_94_,
					renderable, i_93_, true, i_95_, (byte) 0);
		} catch (RuntimeException runtimeexception) {
			SignLink.reportError("2234, " + i + ", " + i_93_ + ", " + b + ", " + i_94_ + ", " + i_95_ + ", " + i_96_
					+ ", " + i_97_ + ", " + i_98_ + ", " + renderable + ", " + bool + ", "
					+ runtimeexception.toString());
			throw new RuntimeException();
		}
	}

	public boolean method508(int i, int i_103_, int i_104_, Renderable renderable, int i_105_, int i_106_, int i_107_,
			int i_108_, int i_109_, int i_110_, int i_111_, int i_112_, byte b) {
		try {
			if (b != 35) {
				for (int i_113_ = 1; i_113_ > 0; i_113_++) {
					/* empty */
				}
			}
			if (renderable == null) {
				return true;
			}
			return method509(i_103_, i_109_, i_112_, i_110_ - i_109_ + 1, i_106_ - i_112_ + 1, i_107_, i_104_, i_108_,
					renderable, i_105_, true, i_111_, (byte) 0);
		} catch (RuntimeException runtimeexception) {
			SignLink.reportError("43595, " + i + ", " + i_103_ + ", " + i_104_ + ", " + renderable + ", " + i_105_
					+ ", " + i_106_ + ", " + i_107_ + ", " + i_108_ + ", " + i_109_ + ", " + i_110_ + ", " + i_111_
					+ ", " + i_112_ + ", " + b + ", " + runtimeexception.toString());
			throw new RuntimeException();
		}
	}

	private boolean method509(int i, int i_114_, int i_115_, int i_116_, int i_117_, int i_118_, int i_119_,
			int i_120_, Renderable renderable, int i_121_, boolean bool, int i_122_, byte b) {
		for (int i_123_ = i_114_; i_123_ < i_114_ + i_116_; i_123_++) {
			for (int i_124_ = i_115_; i_124_ < i_115_ + i_117_; i_124_++) {
				if (i_123_ < 0 || i_124_ < 0 || i_123_ >= anInt518 || i_124_ >= anInt519) {
					return false;
				}
				SceneTile scenetile = tiles[i][i_123_][i_124_];
				if (scenetile != null && scenetile.sceneSpawnRequestCount >= 5) {
					return false;
				}
			}
		}
		SceneSpawnRequest scenespawnrequest = new SceneSpawnRequest();
		scenespawnrequest.anInt609 = i_122_;
		scenespawnrequest.config = b;
		scenespawnrequest.anInt597 = i;
		scenespawnrequest.anInt599 = i_118_;
		scenespawnrequest.anInt600 = i_119_;
		scenespawnrequest.anInt598 = i_120_;
		scenespawnrequest.aRenderable601 = renderable;
		scenespawnrequest.anInt602 = i_121_;
		scenespawnrequest.x = i_114_;
		scenespawnrequest.y = i_115_;
		scenespawnrequest.anInt604 = i_114_ + i_116_ - 1;
		scenespawnrequest.anInt606 = i_115_ + i_117_ - 1;
		for (int i_125_ = i_114_; i_125_ < i_114_ + i_116_; i_125_++) {
			for (int i_126_ = i_115_; i_126_ < i_115_ + i_117_; i_126_++) {
				int i_127_ = 0;
				if (i_125_ > i_114_) {
					i_127_++;
				}
				if (i_125_ < i_114_ + i_116_ - 1) {
					i_127_ += 4;
				}
				if (i_126_ > i_115_) {
					i_127_ += 8;
				}
				if (i_126_ < i_115_ + i_117_ - 1) {
					i_127_ += 2;
				}
				for (int i_128_ = i; i_128_ >= 0; i_128_--) {
					if (tiles[i_128_][i_125_][i_126_] == null) {
						tiles[i_128_][i_125_][i_126_] = new SceneTile(i_128_, i_125_, i_126_);
					}
				}
				SceneTile scenetile = tiles[i][i_125_][i_126_];
				scenetile.sceneSpawnRequests[scenetile.sceneSpawnRequestCount] = scenespawnrequest;
				scenetile.anIntArray1330[scenetile.sceneSpawnRequestCount] = i_127_;
				scenetile.anInt1331 |= i_127_;
				scenetile.sceneSpawnRequestCount++;
			}
		}
		if (bool) {
			aSceneSpawnRequestArray524[anInt523++] = scenespawnrequest;
		}
		return true;
	}

	public void method510(byte b) {
		try {
			if (b != 104) {
				aBoolean515 = !aBoolean515;
			}
			for (int i = 0; i < anInt523; i++) {
				SceneSpawnRequest scenespawnrequest = aSceneSpawnRequestArray524[i];
				method511(-997, scenespawnrequest);
				aSceneSpawnRequestArray524[i] = null;
			}
			anInt523 = 0;
		} catch (RuntimeException runtimeexception) {
			SignLink.reportError("28445, " + b + ", " + runtimeexception.toString());
			throw new RuntimeException();
		}
	}

	private void method511(int i, SceneSpawnRequest scenespawnrequest) {
		try {
			if (i < 0) {
				for (int i_129_ = scenespawnrequest.x; i_129_ <= scenespawnrequest.anInt604; i_129_++) {
					for (int i_130_ = scenespawnrequest.y; i_130_ <= scenespawnrequest.anInt606; i_130_++) {
						SceneTile scenetile = tiles[scenespawnrequest.anInt597][i_129_][i_130_];
						if (scenetile != null) {
							for (int i_131_ = 0; i_131_ < scenetile.sceneSpawnRequestCount; i_131_++) {
								if (scenetile.sceneSpawnRequests[i_131_] == scenespawnrequest) {
									scenetile.sceneSpawnRequestCount--;
									for (int i_132_ = i_131_; i_132_ < scenetile.sceneSpawnRequestCount; i_132_++) {
										scenetile.sceneSpawnRequests[i_132_] = scenetile.sceneSpawnRequests[i_132_ + 1];
										scenetile.anIntArray1330[i_132_] = scenetile.anIntArray1330[i_132_ + 1];
									}
									scenetile.sceneSpawnRequests[scenetile.sceneSpawnRequestCount] = null;
									break;
								}
							}
							scenetile.anInt1331 = 0;
							for (int i_133_ = 0; i_133_ < scenetile.sceneSpawnRequestCount; i_133_++) {
								scenetile.anInt1331 |= scenetile.anIntArray1330[i_133_];
							}
						}
					}
				}
			}
		} catch (RuntimeException runtimeexception) {
			SignLink.reportError("43159, " + i + ", " + scenespawnrequest + ", " + runtimeexception.toString());
			throw new RuntimeException();
		}
	}

	public void method512(int i, int i_134_, int i_135_, int i_136_, int i_137_) {
		try {
			SceneTile scenetile = tiles[i_137_][i_136_][i];
			if (i_134_ <= 0) {
				aBoolean509 = !aBoolean509;
			}
			if (scenetile != null) {
				WallDecoration walldecoration = scenetile.wallDecoration;
				if (walldecoration != null) {
					int i_138_ = i_136_ * 128 + 64;
					int i_139_ = i * 128 + 64;
					walldecoration.y = i_138_ + (walldecoration.y - i_138_) * i_135_ / 16;
					walldecoration.x = i_139_ + (walldecoration.x - i_139_) * i_135_ / 16;
				}
			}
		} catch (RuntimeException runtimeexception) {
			SignLink.reportError("49418, " + i + ", " + i_134_ + ", " + i_135_ + ", " + i_136_ + ", " + i_137_ + ", "
					+ runtimeexception.toString());
			throw new RuntimeException();
		}
	}

	public void method513(int i, int i_140_, int i_141_, byte b) {
		try {
			SceneTile scenetile = tiles[i_140_][i][i_141_];
			if (b != -119) {
				aBoolean514 = !aBoolean514;
			}
			if (scenetile != null) {
				scenetile.wall = null;
			}
		} catch (RuntimeException runtimeexception) {
			SignLink.reportError("67055, " + i + ", " + i_140_ + ", " + i_141_ + ", " + b + ", "
					+ runtimeexception.toString());
			throw new RuntimeException();
		}
	}

	public void method514(int i, int i_142_, int i_143_, int i_144_) {
		try {
			SceneTile scenetile = tiles[i_143_][i_144_][i_142_];
			if (scenetile != null) {
				scenetile.wallDecoration = null;
				if (i != 0) {
					return;
				}
			}
		} catch (RuntimeException runtimeexception) {
			SignLink.reportError("18618, " + i + ", " + i_142_ + ", " + i_143_ + ", " + i_144_ + ", "
					+ runtimeexception.toString());
			throw new RuntimeException();
		}
	}

	public void method515(int i, int i_145_, int i_146_, int i_147_) {
		try {
			if (i_145_ >= 0) {
				for (int i_148_ = 1; i_148_ > 0; i_148_++) {
					/* empty */
				}
			}
			SceneTile scenetile = tiles[i][i_146_][i_147_];
			if (scenetile != null) {
				for (int i_149_ = 0; i_149_ < scenetile.sceneSpawnRequestCount; i_149_++) {
					SceneSpawnRequest scenespawnrequest = scenetile.sceneSpawnRequests[i_149_];
					if ((scenespawnrequest.anInt609 >> 29 & 0x3) == 2 && scenespawnrequest.x == i_146_
							&& scenespawnrequest.y == i_147_) {
						method511(-997, scenespawnrequest);
						break;
					}
				}
			}
		} catch (RuntimeException runtimeexception) {
			SignLink.reportError("59016, " + i + ", " + i_145_ + ", " + i_146_ + ", " + i_147_ + ", "
					+ runtimeexception.toString());
			throw new RuntimeException();
		}
	}

	public void method516(byte b, int i, int i_150_, int i_151_) {
		try {
			SceneTile scenetile = tiles[i][i_151_][i_150_];
			if (scenetile != null) {
				scenetile.floorDecoration = null;
				if (b == 9) {
					b = (byte) 0;
				}
			}
		} catch (RuntimeException runtimeexception) {
			SignLink.reportError("33570, " + b + ", " + i + ", " + i_150_ + ", " + i_151_ + ", "
					+ runtimeexception.toString());
			throw new RuntimeException();
		}
	}

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
			if ((sceneSpawnRequest.anInt609 >> 29 & 0x3) == 2 && sceneSpawnRequest.x == x && sceneSpawnRequest.y == y) {
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

	public int method522(int i, int i_166_, int i_167_) {
		SceneTile scenetile = tiles[i][i_166_][i_167_];
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

	public int method524(int i, int i_171_, int i_172_) {
		SceneTile scenetile = tiles[i][i_171_][i_172_];
		if (scenetile == null) {
			return 0;
		}
		for (int i_173_ = 0; i_173_ < scenetile.sceneSpawnRequestCount; i_173_++) {
			SceneSpawnRequest scenespawnrequest = scenetile.sceneSpawnRequests[i_173_];
			if ((scenespawnrequest.anInt609 >> 29 & 0x3) == 2 && scenespawnrequest.x == i_171_
					&& scenespawnrequest.y == i_172_) {
				return scenespawnrequest.anInt609;
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

	public int getConfig(int plane, int x, int y, int i_178_) {
		SceneTile sceneTile = tiles[plane][x][y];
		if (sceneTile == null) {
			return -1;
		}
		if (sceneTile.wall != null && sceneTile.wall.hash == i_178_) {
			return sceneTile.wall.config & 0xff;
		}
		if (sceneTile.wallDecoration != null && sceneTile.wallDecoration.hash == i_178_) {
			return sceneTile.wallDecoration.config & 0xff;
		}
		if (sceneTile.floorDecoration != null && sceneTile.floorDecoration.hash == i_178_) {
			return sceneTile.floorDecoration.config & 0xff;
		}
		for (int sceneSpawnRequest = 0; sceneSpawnRequest < sceneTile.sceneSpawnRequestCount; sceneSpawnRequest++) {
			if (sceneTile.sceneSpawnRequests[sceneSpawnRequest].anInt609 == i_178_) {
				return sceneTile.sceneSpawnRequests[sceneSpawnRequest].config & 0xff;
			}
		}
		return -1;
	}

	public void method527(int i, byte b, int i_180_, int i_181_, int i_182_, int i_183_) {
		try {
			int i_184_ = (int) Math.sqrt(i_181_ * i_181_ + i * i + i_183_ * i_183_);
			int i_185_ = i_182_ * i_184_ >> 8;
			if (b != 3) {
				aBoolean514 = !aBoolean514;
			}
			for (int i_186_ = 0; i_186_ < anInt517; i_186_++) {
				for (int i_187_ = 0; i_187_ < anInt518; i_187_++) {
					for (int i_188_ = 0; i_188_ < anInt519; i_188_++) {
						SceneTile scenetile = tiles[i_186_][i_187_][i_188_];
						if (scenetile != null) {
							Wall wall = scenetile.wall;
							if (wall != null && wall.aRenderable769 != null
									&& wall.aRenderable769.verticesNormal != null) {
								method529(i_186_, 1, 1, i_187_, (byte) 115, i_188_, (Model) wall.aRenderable769);
								if (wall.aRenderable770 != null && wall.aRenderable770.verticesNormal != null) {
									method529(i_186_, 1, 1, i_187_, (byte) 115, i_188_, (Model) wall.aRenderable770);
									method530((Model) wall.aRenderable769, (Model) wall.aRenderable770, 0, 0, 0, false);
									((Model) wall.aRenderable770).method428(i_180_, i_185_, i_181_, i, i_183_);
								}
								((Model) wall.aRenderable769).method428(i_180_, i_185_, i_181_, i, i_183_);
							}
							for (int i_189_ = 0; i_189_ < scenetile.sceneSpawnRequestCount; i_189_++) {
								SceneSpawnRequest scenespawnrequest = scenetile.sceneSpawnRequests[i_189_];
								if (scenespawnrequest != null && scenespawnrequest.aRenderable601 != null
										&& scenespawnrequest.aRenderable601.verticesNormal != null) {
									method529(i_186_, scenespawnrequest.anInt604 - scenespawnrequest.x + 1,
											scenespawnrequest.anInt606 - scenespawnrequest.y + 1, i_187_, (byte) 115,
											i_188_, (Model) scenespawnrequest.aRenderable601);
									((Model) scenespawnrequest.aRenderable601).method428(i_180_, i_185_, i_181_, i,
											i_183_);
								}
							}
							FloorDecoration floordecoration = scenetile.floorDecoration;
							if (floordecoration != null && floordecoration.renderable.verticesNormal != null) {
								method528(i_187_, i_186_, (Model) floordecoration.renderable, (byte) 37, i_188_);
								((Model) floordecoration.renderable).method428(i_180_, i_185_, i_181_, i, i_183_);
							}
						}
					}
				}
			}
		} catch (RuntimeException runtimeexception) {
			SignLink.reportError("77749, " + i + ", " + b + ", " + i_180_ + ", " + i_181_ + ", " + i_182_ + ", "
					+ i_183_ + ", " + runtimeexception.toString());
			throw new RuntimeException();
		}
	}

	private void method528(int i, int i_190_, Model model, byte b, int i_191_) {
		do {
			try {
				if (b != 37) {
					for (int i_192_ = 1; i_192_ > 0; i_192_++) {
						/* empty */
					}
				}
				if (i < anInt518) {
					SceneTile scenetile = tiles[i_190_][i + 1][i_191_];
					if (scenetile != null && scenetile.floorDecoration != null
							&& scenetile.floorDecoration.renderable.verticesNormal != null) {
						method530(model, (Model) scenetile.floorDecoration.renderable, 128, 0, 0, true);
					}
				}
				if (i_191_ < anInt518) {
					SceneTile scenetile = tiles[i_190_][i][i_191_ + 1];
					if (scenetile != null && scenetile.floorDecoration != null
							&& scenetile.floorDecoration.renderable.verticesNormal != null) {
						method530(model, (Model) scenetile.floorDecoration.renderable, 0, 0, 128, true);
					}
				}
				if (i < anInt518 && i_191_ < anInt519) {
					SceneTile scenetile = tiles[i_190_][i + 1][i_191_ + 1];
					if (scenetile != null && scenetile.floorDecoration != null
							&& scenetile.floorDecoration.renderable.verticesNormal != null) {
						method530(model, (Model) scenetile.floorDecoration.renderable, 128, 0, 128, true);
					}
				}
				if (i >= anInt518 || i_191_ <= 0) {
					break;
				}
				SceneTile scenetile = tiles[i_190_][i + 1][i_191_ - 1];
				if (scenetile == null || scenetile.floorDecoration == null
						|| scenetile.floorDecoration.renderable.verticesNormal == null) {
					break;
				}
				method530(model, (Model) scenetile.floorDecoration.renderable, 128, 0, -128, true);
			} catch (RuntimeException runtimeexception) {
				SignLink.reportError("40901, " + i + ", " + i_190_ + ", " + model + ", " + b + ", " + i_191_ + ", "
						+ runtimeexception.toString());
				throw new RuntimeException();
			}
			break;
		} while (false);
	}

	private void method529(int i, int i_193_, int i_194_, int i_195_, byte b, int i_196_, Model model) {
		try {
			boolean bool = true;
			if (b != 115) {
			}
			int i_197_ = i_195_;
			int i_198_ = i_195_ + i_193_;
			int i_199_ = i_196_ - 1;
			int i_200_ = i_196_ + i_194_;
			for (int i_201_ = i; i_201_ <= i + 1; i_201_++) {
				if (i_201_ != anInt517) {
					for (int i_202_ = i_197_; i_202_ <= i_198_; i_202_++) {
						if (i_202_ >= 0 && i_202_ < anInt518) {
							for (int i_203_ = i_199_; i_203_ <= i_200_; i_203_++) {
								if (i_203_ >= 0
										&& i_203_ < anInt519
										&& (!bool || i_202_ >= i_198_ || i_203_ >= i_200_ || i_203_ < i_196_
												&& i_202_ != i_195_)) {
									SceneTile scenetile = tiles[i_201_][i_202_][i_203_];
									if (scenetile != null) {
										int i_204_ = (anIntArrayArrayArray520[i_201_][i_202_][i_203_]
												+ anIntArrayArrayArray520[i_201_][i_202_ + 1][i_203_]
												+ anIntArrayArrayArray520[i_201_][i_202_][i_203_ + 1] + anIntArrayArrayArray520[i_201_][i_202_ + 1][i_203_ + 1])
												/ 4
												- (anIntArrayArrayArray520[i][i_195_][i_196_]
														+ anIntArrayArrayArray520[i][i_195_ + 1][i_196_]
														+ anIntArrayArrayArray520[i][i_195_][i_196_ + 1] + anIntArrayArrayArray520[i][i_195_ + 1][i_196_ + 1])
												/ 4;
										Wall wall = scenetile.wall;
										if (wall != null && wall.aRenderable769 != null
												&& wall.aRenderable769.verticesNormal != null) {
											method530(model, (Model) wall.aRenderable769, (i_202_ - i_195_) * 128
													+ (1 - i_193_) * 64, i_204_, (i_203_ - i_196_) * 128 + (1 - i_194_)
													* 64, bool);
										}
										if (wall != null && wall.aRenderable770 != null
												&& wall.aRenderable770.verticesNormal != null) {
											method530(model, (Model) wall.aRenderable770, (i_202_ - i_195_) * 128
													+ (1 - i_193_) * 64, i_204_, (i_203_ - i_196_) * 128 + (1 - i_194_)
													* 64, bool);
										}
										for (int i_205_ = 0; i_205_ < scenetile.sceneSpawnRequestCount; i_205_++) {
											SceneSpawnRequest scenespawnrequest = scenetile.sceneSpawnRequests[i_205_];
											if (scenespawnrequest != null && scenespawnrequest.aRenderable601 != null
													&& scenespawnrequest.aRenderable601.verticesNormal != null) {
												int i_206_ = scenespawnrequest.anInt604 - scenespawnrequest.x + 1;
												int i_207_ = scenespawnrequest.anInt606 - scenespawnrequest.y + 1;
												method530(model, (Model) scenespawnrequest.aRenderable601,
														(scenespawnrequest.x - i_195_) * 128 + (i_206_ - i_193_) * 64,
														i_204_, (scenespawnrequest.y - i_196_) * 128
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
			SignLink.reportError("11529, " + i + ", " + i_193_ + ", " + i_194_ + ", " + i_195_ + ", " + b + ", "
					+ i_196_ + ", " + model + ", " + runtimeexception.toString());
			throw new RuntimeException();
		}
	}

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

	public void renderMinimapDot(int[] is, int i, int i_223_, int i_224_, int i_225_, int i_226_) {
		SceneTile scenetile = tiles[i_224_][i_225_][i_226_];
		if (scenetile != null) {
			GenericTile generictile = scenetile.genericTile;
			if (generictile != null) {
				int i_227_ = generictile.rgbColor;
				if (i_227_ != 0) {
					for (int i_228_ = 0; i_228_ < 4; i_228_++) {
						is[i] = i_227_;
						is[i + 1] = i_227_;
						is[i + 2] = i_227_;
						is[i + 3] = i_227_;
						i += i_223_;
					}
				}
			} else {
				ComplexTile complextile = scenetile.complexTile;
				if (complextile != null) {
					int i_229_ = complextile.anInt206;
					int i_230_ = complextile.anInt207;
					int i_231_ = complextile.anInt208;
					int i_232_ = complextile.anInt209;
					int[] is_233_ = anIntArrayArray569[i_229_];
					int[] is_234_ = anIntArrayArray570[i_230_];
					int i_235_ = 0;
					if (i_231_ != 0) {
						for (int i_236_ = 0; i_236_ < 4; i_236_++) {
							is[i] = is_233_[is_234_[i_235_++]] == 0 ? i_231_ : i_232_;
							is[i + 1] = is_233_[is_234_[i_235_++]] == 0 ? i_231_ : i_232_;
							is[i + 2] = is_233_[is_234_[i_235_++]] == 0 ? i_231_ : i_232_;
							is[i + 3] = is_233_[is_234_[i_235_++]] == 0 ? i_231_ : i_232_;
							i += i_223_;
						}
					} else {
						for (int i_237_ = 0; i_237_ < 4; i_237_++) {
							if (is_233_[is_234_[i_235_++]] != 0) {
								is[i] = i_232_;
							}
							if (is_233_[is_234_[i_235_++]] != 0) {
								is[i + 1] = i_232_;
							}
							if (is_233_[is_234_[i_235_++]] != 0) {
								is[i + 2] = i_232_;
							}
							if (is_233_[is_234_[i_235_++]] != 0) {
								is[i + 3] = i_232_;
							}
							i += i_223_;
						}
					}
				}
			}
		}
	}

	public static void method532(int i, int i_238_, int i_239_, int i_240_, int[] is, boolean bool) {
		try {
			Scene.anInt575 = 0;
			Scene.anInt576 = 0;
			Scene.anInt577 = i_239_;
			Scene.anInt578 = i_240_;
			Scene.anInt573 = i_239_ / 2;
			Scene.anInt574 = i_240_ / 2;
			boolean[][][][] bools = new boolean[9][32][53][53];
			if (bool) {
			}
			for (int i_241_ = 128; i_241_ <= 384; i_241_ += 32) {
				for (int i_242_ = 0; i_242_ < 2048; i_242_ += 64) {
					Scene.anInt538 = Model.SINE[i_241_];
					Scene.anInt539 = Model.COSINE[i_241_];
					Scene.anInt540 = Model.SINE[i_242_];
					Scene.anInt541 = Model.COSINE[i_242_];
					int i_243_ = (i_241_ - 128) / 32;
					int i_244_ = i_242_ / 64;
					for (int i_245_ = -26; i_245_ <= 26; i_245_++) {
						for (int i_246_ = -26; i_246_ <= 26; i_246_++) {
							int i_247_ = i_245_ * 128;
							int i_248_ = i_246_ * 128;
							boolean bool_249_ = false;
							for (int i_250_ = -i; i_250_ <= i_238_; i_250_ += 128) {
								if (Scene.method533((byte) 9, is[i_243_] + i_250_, i_248_, i_247_)) {
									bool_249_ = true;
									break;
								}
							}
							bools[i_243_][i_244_][i_245_ + 25 + 1][i_246_ + 25 + 1] = bool_249_;
						}
					}
				}
			}
			for (int i_251_ = 0; i_251_ < 8; i_251_++) {
				for (int i_252_ = 0; i_252_ < 32; i_252_++) {
					for (int i_253_ = -25; i_253_ < 25; i_253_++) {
						for (int i_254_ = -25; i_254_ < 25; i_254_++) {
							boolean bool_255_ = false;
							while_8_: for (int i_256_ = -1; i_256_ <= 1; i_256_++) {
								for (int i_257_ = -1; i_257_ <= 1; i_257_++) {
									if (bools[i_251_][i_252_][i_253_ + i_256_ + 25 + 1][i_254_ + i_257_ + 25 + 1]) {
										bool_255_ = true;
										break while_8_;
									}
									if (bools[i_251_][(i_252_ + 1) % 31][i_253_ + i_256_ + 25 + 1][i_254_ + i_257_ + 25
											+ 1]) {
										bool_255_ = true;
										break while_8_;
									}
									if (bools[i_251_ + 1][i_252_][i_253_ + i_256_ + 25 + 1][i_254_ + i_257_ + 25 + 1]) {
										bool_255_ = true;
										break while_8_;
									}
									if (bools[i_251_ + 1][(i_252_ + 1) % 31][i_253_ + i_256_ + 25 + 1][i_254_ + i_257_
											+ 25 + 1]) {
										bool_255_ = true;
										break while_8_;
									}
								}
							}
							Scene.aBooleanArrayArrayArrayArray571[i_251_][i_252_][i_253_ + 25][i_254_ + 25] = bool_255_;
						}
					}
				}
			}
		} catch (RuntimeException runtimeexception) {
			SignLink.reportError("5468, " + i + ", " + i_238_ + ", " + i_239_ + ", " + i_240_ + ", " + is + ", " + bool
					+ ", " + runtimeexception.toString());
			throw new RuntimeException();
		}
	}

	public static boolean method533(byte b, int i, int i_258_, int i_259_) {
		try {
			int i_260_ = i_258_ * Scene.anInt540 + i_259_ * Scene.anInt541 >> 16;
			int i_261_ = i_258_ * Scene.anInt541 - i_259_ * Scene.anInt540 >> 16;
			if (b != 9) {
			}
			int i_262_ = i * Scene.anInt538 + i_261_ * Scene.anInt539 >> 16;
			int i_263_ = i * Scene.anInt539 - i_261_ * Scene.anInt538 >> 16;
			if (i_262_ < 50 || i_262_ > 3500) {
				return false;
			}
			int i_264_ = Scene.anInt573 + (i_260_ << 9) / i_262_;
			int i_265_ = Scene.anInt574 + (i_263_ << 9) / i_262_;
			if (i_264_ < Scene.anInt575 || i_264_ > Scene.anInt577 || i_265_ < Scene.anInt576
					|| i_265_ > Scene.anInt578) {
				return false;
			}
			return true;
		} catch (RuntimeException runtimeexception) {
			SignLink.reportError("3265, " + b + ", " + i + ", " + i_258_ + ", " + i_259_ + ", "
					+ runtimeexception.toString());
			throw new RuntimeException();
		}
	}

	public void method534(boolean bool, int i, int i_266_) {
		do {
			try {
				Scene.aBoolean547 = true;
				Scene.anInt548 = i_266_;
				Scene.anInt549 = i;
				Scene.anInt550 = -1;
				Scene.anInt551 = -1;
				if (!bool) {
					break;
				}
				for (int i_267_ = 1; i_267_ > 0; i_267_++) {
					/* empty */
				}
			} catch (RuntimeException runtimeexception) {
				SignLink.reportError("24272, " + bool + ", " + i + ", " + i_266_ + ", " + runtimeexception.toString());
				throw new RuntimeException();
			}
			break;
		} while (false);
	}

	public void method535(int i, int i_268_, int i_269_, int i_270_, int i_271_, int i_272_, boolean bool) {
		try {
			if (i < 0) {
				i = 0;
			} else if (i >= anInt518 * 128) {
				i = anInt518 * 128 - 1;
			}
			if (i_268_ < 0) {
				i_268_ = 0;
			} else if (i_268_ >= anInt519 * 128) {
				i_268_ = anInt519 * 128 - 1;
			}
			Scene.anInt528++;
			Scene.anInt538 = Model.SINE[i_272_];
			Scene.anInt539 = Model.COSINE[i_272_];
			if (!bool) {
				Scene.anInt540 = Model.SINE[i_269_];
				Scene.anInt541 = Model.COSINE[i_269_];
				Scene.aBooleanArrayArray572 = Scene.aBooleanArrayArrayArrayArray571[(i_272_ - 128) / 32][i_269_ / 64];
				Scene.anInt535 = i;
				Scene.anInt536 = i_270_;
				Scene.anInt537 = i_268_;
				Scene.anInt533 = i / 128;
				Scene.anInt534 = i_268_ / 128;
				Scene.anInt527 = i_271_;
				Scene.anInt529 = Scene.anInt533 - 25;
				if (Scene.anInt529 < 0) {
					Scene.anInt529 = 0;
				}
				Scene.anInt531 = Scene.anInt534 - 25;
				if (Scene.anInt531 < 0) {
					Scene.anInt531 = 0;
				}
				Scene.anInt530 = Scene.anInt533 + 25;
				if (Scene.anInt530 > anInt518) {
					Scene.anInt530 = anInt518;
				}
				Scene.anInt532 = Scene.anInt534 + 25;
				if (Scene.anInt532 > anInt519) {
					Scene.anInt532 = anInt519;
				}
				method541(0);
				Scene.anInt526 = 0;
				for (int i_273_ = anInt522; i_273_ < anInt517; i_273_++) {
					SceneTile[][] scenetiles = tiles[i_273_];
					for (int i_274_ = Scene.anInt529; i_274_ < Scene.anInt530; i_274_++) {
						for (int i_275_ = Scene.anInt531; i_275_ < Scene.anInt532; i_275_++) {
							SceneTile scenetile = scenetiles[i_274_][i_275_];
							if (scenetile != null) {
								if (scenetile.anInt1332 > i_271_
										|| !Scene.aBooleanArrayArray572[i_274_ - Scene.anInt533 + 25][i_275_
												- Scene.anInt534 + 25]
										&& anIntArrayArrayArray520[i_273_][i_274_][i_275_] - i_270_ < 2000) {
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
									Scene.anInt526++;
								}
							}
						}
					}
				}
				for (int i_276_ = anInt522; i_276_ < anInt517; i_276_++) {
					SceneTile[][] scenetiles = tiles[i_276_];
					for (int i_277_ = -25; i_277_ <= 0; i_277_++) {
						int i_278_ = Scene.anInt533 + i_277_;
						int i_279_ = Scene.anInt533 - i_277_;
						if (i_278_ >= Scene.anInt529 || i_279_ < Scene.anInt530) {
							for (int i_280_ = -25; i_280_ <= 0; i_280_++) {
								int i_281_ = Scene.anInt534 + i_280_;
								int i_282_ = Scene.anInt534 - i_280_;
								if (i_278_ >= Scene.anInt529) {
									if (i_281_ >= Scene.anInt531) {
										SceneTile scenetile = scenetiles[i_278_][i_281_];
										if (scenetile != null && scenetile.aBoolean1333) {
											method536(scenetile, true);
										}
									}
									if (i_282_ < Scene.anInt532) {
										SceneTile scenetile = scenetiles[i_278_][i_282_];
										if (scenetile != null && scenetile.aBoolean1333) {
											method536(scenetile, true);
										}
									}
								}
								if (i_279_ < Scene.anInt530) {
									if (i_281_ >= Scene.anInt531) {
										SceneTile scenetile = scenetiles[i_279_][i_281_];
										if (scenetile != null && scenetile.aBoolean1333) {
											method536(scenetile, true);
										}
									}
									if (i_282_ < Scene.anInt532) {
										SceneTile scenetile = scenetiles[i_279_][i_282_];
										if (scenetile != null && scenetile.aBoolean1333) {
											method536(scenetile, true);
										}
									}
								}
								if (Scene.anInt526 == 0) {
									Scene.aBoolean547 = false;
									return;
								}
							}
						}
					}
				}
				for (int i_283_ = anInt522; i_283_ < anInt517; i_283_++) {
					SceneTile[][] scenetiles = tiles[i_283_];
					for (int i_284_ = -25; i_284_ <= 0; i_284_++) {
						int i_285_ = Scene.anInt533 + i_284_;
						int i_286_ = Scene.anInt533 - i_284_;
						if (i_285_ >= Scene.anInt529 || i_286_ < Scene.anInt530) {
							for (int i_287_ = -25; i_287_ <= 0; i_287_++) {
								int i_288_ = Scene.anInt534 + i_287_;
								int i_289_ = Scene.anInt534 - i_287_;
								if (i_285_ >= Scene.anInt529) {
									if (i_288_ >= Scene.anInt531) {
										SceneTile scenetile = scenetiles[i_285_][i_288_];
										if (scenetile != null && scenetile.aBoolean1333) {
											method536(scenetile, false);
										}
									}
									if (i_289_ < Scene.anInt532) {
										SceneTile scenetile = scenetiles[i_285_][i_289_];
										if (scenetile != null && scenetile.aBoolean1333) {
											method536(scenetile, false);
										}
									}
								}
								if (i_286_ < Scene.anInt530) {
									if (i_288_ >= Scene.anInt531) {
										SceneTile scenetile = scenetiles[i_286_][i_288_];
										if (scenetile != null && scenetile.aBoolean1333) {
											method536(scenetile, false);
										}
									}
									if (i_289_ < Scene.anInt532) {
										SceneTile scenetile = scenetiles[i_286_][i_289_];
										if (scenetile != null && scenetile.aBoolean1333) {
											method536(scenetile, false);
										}
									}
								}
								if (Scene.anInt526 == 0) {
									Scene.aBoolean547 = false;
									return;
								}
							}
						}
					}
				}
				Scene.aBoolean547 = false;
			}
		} catch (RuntimeException runtimeexception) {
			SignLink.reportError("93114, " + i + ", " + i_268_ + ", " + i_269_ + ", " + i_270_ + ", " + i_271_ + ", "
					+ i_272_ + ", " + bool + ", " + runtimeexception.toString());
			throw new RuntimeException();
		}
	}

	public void method536(SceneTile scenetile, boolean bool) {
		Scene.aLinkedList557.insertBack(scenetile);
		for (;;) {
			SceneTile scenetile_290_ = (SceneTile) Scene.aLinkedList557.popTail();
			if (scenetile_290_ == null) {
				break;
			}
			if (scenetile_290_.aBoolean1334) {
				int i = scenetile_290_.anInt1319;
				int i_291_ = scenetile_290_.anInt1320;
				int i_292_ = scenetile_290_.anInt1318;
				int i_293_ = scenetile_290_.anInt1321;
				SceneTile[][] scenetiles = tiles[i_292_];
				if (scenetile_290_.aBoolean1333) {
					if (bool) {
						if (i_292_ > 0) {
							SceneTile scenetile_294_ = tiles[i_292_ - 1][i][i_291_];
							if (scenetile_294_ != null && scenetile_294_.aBoolean1334) {
								continue;
							}
						}
						if (i <= Scene.anInt533 && i > Scene.anInt529) {
							SceneTile scenetile_295_ = scenetiles[i - 1][i_291_];
							if (scenetile_295_ != null && scenetile_295_.aBoolean1334
									&& (scenetile_295_.aBoolean1333 || (scenetile_290_.anInt1331 & 0x1) == 0)) {
								continue;
							}
						}
						if (i >= Scene.anInt533 && i < Scene.anInt530 - 1) {
							SceneTile scenetile_296_ = scenetiles[i + 1][i_291_];
							if (scenetile_296_ != null && scenetile_296_.aBoolean1334
									&& (scenetile_296_.aBoolean1333 || (scenetile_290_.anInt1331 & 0x4) == 0)) {
								continue;
							}
						}
						if (i_291_ <= Scene.anInt534 && i_291_ > Scene.anInt531) {
							SceneTile scenetile_297_ = scenetiles[i][i_291_ - 1];
							if (scenetile_297_ != null && scenetile_297_.aBoolean1334
									&& (scenetile_297_.aBoolean1333 || (scenetile_290_.anInt1331 & 0x8) == 0)) {
								continue;
							}
						}
						if (i_291_ >= Scene.anInt534 && i_291_ < Scene.anInt532 - 1) {
							SceneTile scenetile_298_ = scenetiles[i][i_291_ + 1];
							if (scenetile_298_ != null && scenetile_298_.aBoolean1334
									&& (scenetile_298_.aBoolean1333 || (scenetile_290_.anInt1331 & 0x2) == 0)) {
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
							if (!method542(0, i, i_291_)) {
								method537(scenetile_299_.genericTile, 0, Scene.anInt538, Scene.anInt539,
										Scene.anInt540, Scene.anInt541, i, i_291_);
							}
						} else if (scenetile_299_.complexTile != null && !method542(0, i, i_291_)) {
							method538(i, (byte) 99, Scene.anInt538, Scene.anInt540, scenetile_299_.complexTile,
									Scene.anInt539, i_291_, Scene.anInt541);
						}
						Wall wall = scenetile_299_.wall;
						if (wall != null) {
							wall.aRenderable769.renderAtPoint(0, Scene.anInt538, Scene.anInt539, Scene.anInt540,
									Scene.anInt541, wall.x - Scene.anInt535, wall.plane - Scene.anInt536, wall.y
											- Scene.anInt537, wall.hash);
						}
						for (int i_300_ = 0; i_300_ < scenetile_299_.sceneSpawnRequestCount; i_300_++) {
							SceneSpawnRequest scenespawnrequest = scenetile_299_.sceneSpawnRequests[i_300_];
							if (scenespawnrequest != null) {
								scenespawnrequest.aRenderable601.renderAtPoint(scenespawnrequest.anInt602,
										Scene.anInt538, Scene.anInt539, Scene.anInt540, Scene.anInt541,
										scenespawnrequest.anInt599 - Scene.anInt535, scenespawnrequest.anInt598
												- Scene.anInt536, scenespawnrequest.anInt600 - Scene.anInt537,
										scenespawnrequest.anInt609);
							}
						}
					}
					boolean bool_301_ = false;
					if (scenetile_290_.genericTile != null) {
						if (!method542(i_293_, i, i_291_)) {
							bool_301_ = true;
							method537(scenetile_290_.genericTile, i_293_, Scene.anInt538, Scene.anInt539,
									Scene.anInt540, Scene.anInt541, i, i_291_);
						}
					} else if (scenetile_290_.complexTile != null && !method542(i_293_, i, i_291_)) {
						bool_301_ = true;
						method538(i, (byte) 99, Scene.anInt538, Scene.anInt540, scenetile_290_.complexTile,
								Scene.anInt539, i_291_, Scene.anInt541);
					}
					int i_302_ = 0;
					int i_303_ = 0;
					Wall wall = scenetile_290_.wall;
					WallDecoration walldecoration = scenetile_290_.wallDecoration;
					if (wall != null || walldecoration != null) {
						if (Scene.anInt533 == i) {
							i_302_++;
						} else if (Scene.anInt533 < i) {
							i_302_ += 2;
						}
						if (Scene.anInt534 == i_291_) {
							i_302_ += 3;
						} else if (Scene.anInt534 > i_291_) {
							i_302_ += 6;
						}
						i_303_ = Scene.anIntArray558[i_302_];
						scenetile_290_.anInt1339 = Scene.anIntArray560[i_302_];
					}
					if (wall != null) {
						if ((wall.faceUnknown & Scene.anIntArray559[i_302_]) != 0) {
							if (wall.faceUnknown == 16) {
								scenetile_290_.anInt1336 = 3;
								scenetile_290_.anInt1337 = Scene.anIntArray561[i_302_];
								scenetile_290_.anInt1338 = 3 - scenetile_290_.anInt1337;
							} else if (wall.faceUnknown == 32) {
								scenetile_290_.anInt1336 = 6;
								scenetile_290_.anInt1337 = Scene.anIntArray562[i_302_];
								scenetile_290_.anInt1338 = 6 - scenetile_290_.anInt1337;
							} else if (wall.faceUnknown == 64) {
								scenetile_290_.anInt1336 = 12;
								scenetile_290_.anInt1337 = Scene.anIntArray563[i_302_];
								scenetile_290_.anInt1338 = 12 - scenetile_290_.anInt1337;
							} else {
								scenetile_290_.anInt1336 = 9;
								scenetile_290_.anInt1337 = Scene.anIntArray564[i_302_];
								scenetile_290_.anInt1338 = 9 - scenetile_290_.anInt1337;
							}
						} else {
							scenetile_290_.anInt1336 = 0;
						}
						if ((wall.faceUnknown & i_303_) != 0 && !method543(i_293_, i, i_291_, wall.faceUnknown)) {
							wall.aRenderable769.renderAtPoint(0, Scene.anInt538, Scene.anInt539, Scene.anInt540,
									Scene.anInt541, wall.x - Scene.anInt535, wall.plane - Scene.anInt536, wall.y
											- Scene.anInt537, wall.hash);
						}
						if ((wall.face & i_303_) != 0 && !method543(i_293_, i, i_291_, wall.face)) {
							wall.aRenderable770.renderAtPoint(0, Scene.anInt538, Scene.anInt539, Scene.anInt540,
									Scene.anInt541, wall.x - Scene.anInt535, wall.plane - Scene.anInt536, wall.y
											- Scene.anInt537, wall.hash);
						}
					}
					if (walldecoration != null && !method544(i_293_, i, i_291_, walldecoration.renderable.modelHeight)) {
						if ((walldecoration.faceUnknown & i_303_) != 0) {
							walldecoration.renderable.renderAtPoint(walldecoration.face, Scene.anInt538,
									Scene.anInt539, Scene.anInt540, Scene.anInt541, walldecoration.y - Scene.anInt535,
									walldecoration.plane - Scene.anInt536, walldecoration.x - Scene.anInt537,
									walldecoration.hash);
						} else if ((walldecoration.faceUnknown & 0x300) != 0) {
							int i_304_ = walldecoration.y - Scene.anInt535;
							int i_305_ = walldecoration.plane - Scene.anInt536;
							int i_306_ = walldecoration.x - Scene.anInt537;
							int i_307_ = walldecoration.face;
							int i_308_;
							if (i_307_ == 1 || i_307_ == 2) {
								i_308_ = -i_304_;
							} else {
								i_308_ = i_304_;
							}
							int i_309_;
							if (i_307_ == 2 || i_307_ == 3) {
								i_309_ = -i_306_;
							} else {
								i_309_ = i_306_;
							}
							if ((walldecoration.faceUnknown & 0x100) != 0 && i_309_ < i_308_) {
								int i_310_ = i_304_ + Scene.anIntArray543[i_307_];
								int i_311_ = i_306_ + Scene.anIntArray544[i_307_];
								walldecoration.renderable.renderAtPoint(i_307_ * 512 + 256, Scene.anInt538,
										Scene.anInt539, Scene.anInt540, Scene.anInt541, i_310_, i_305_, i_311_,
										walldecoration.hash);
							}
							if ((walldecoration.faceUnknown & 0x200) != 0 && i_309_ > i_308_) {
								int i_312_ = i_304_ + Scene.anIntArray545[i_307_];
								int i_313_ = i_306_ + Scene.anIntArray546[i_307_];
								walldecoration.renderable.renderAtPoint(i_307_ * 512 + 1280 & 0x7ff, Scene.anInt538,
										Scene.anInt539, Scene.anInt540, Scene.anInt541, i_312_, i_305_, i_313_,
										walldecoration.hash);
							}
						}
					}
					if (bool_301_) {
						FloorDecoration floordecoration = scenetile_290_.floorDecoration;
						if (floordecoration != null) {
							floordecoration.renderable.renderAtPoint(0, Scene.anInt538, Scene.anInt539, Scene.anInt540,
									Scene.anInt541, floordecoration.y - Scene.anInt535, floordecoration.x
											- Scene.anInt536, floordecoration.z - Scene.anInt537, floordecoration.hash);
						}
						CameraAngle cameraangle = scenetile_290_.cameraAngle;
						if (cameraangle != null && cameraangle.anInt154 == 0) {
							if (cameraangle.aRenderable151 != null) {
								cameraangle.aRenderable151.renderAtPoint(0, Scene.anInt538, Scene.anInt539,
										Scene.anInt540, Scene.anInt541, cameraangle.y - Scene.anInt535, cameraangle.x
												- Scene.anInt536, cameraangle.z - Scene.anInt537, cameraangle.anInt153);
							}
							if (cameraangle.aRenderable152 != null) {
								cameraangle.aRenderable152.renderAtPoint(0, Scene.anInt538, Scene.anInt539,
										Scene.anInt540, Scene.anInt541, cameraangle.y - Scene.anInt535, cameraangle.x
												- Scene.anInt536, cameraangle.z - Scene.anInt537, cameraangle.anInt153);
							}
							if (cameraangle.aRenderable150 != null) {
								cameraangle.aRenderable150.renderAtPoint(0, Scene.anInt538, Scene.anInt539,
										Scene.anInt540, Scene.anInt541, cameraangle.y - Scene.anInt535, cameraangle.x
												- Scene.anInt536, cameraangle.z - Scene.anInt537, cameraangle.anInt153);
							}
						}
					}
					int i_314_ = scenetile_290_.anInt1331;
					if (i_314_ != 0) {
						if (i < Scene.anInt533 && (i_314_ & 0x4) != 0) {
							SceneTile scenetile_315_ = scenetiles[i + 1][i_291_];
							if (scenetile_315_ != null && scenetile_315_.aBoolean1334) {
								Scene.aLinkedList557.insertBack(scenetile_315_);
							}
						}
						if (i_291_ < Scene.anInt534 && (i_314_ & 0x2) != 0) {
							SceneTile scenetile_316_ = scenetiles[i][i_291_ + 1];
							if (scenetile_316_ != null && scenetile_316_.aBoolean1334) {
								Scene.aLinkedList557.insertBack(scenetile_316_);
							}
						}
						if (i > Scene.anInt533 && (i_314_ & 0x1) != 0) {
							SceneTile scenetile_317_ = scenetiles[i - 1][i_291_];
							if (scenetile_317_ != null && scenetile_317_.aBoolean1334) {
								Scene.aLinkedList557.insertBack(scenetile_317_);
							}
						}
						if (i_291_ > Scene.anInt534 && (i_314_ & 0x8) != 0) {
							SceneTile scenetile_318_ = scenetiles[i][i_291_ - 1];
							if (scenetile_318_ != null && scenetile_318_.aBoolean1334) {
								Scene.aLinkedList557.insertBack(scenetile_318_);
							}
						}
					}
				}
				if (scenetile_290_.anInt1336 != 0) {
					boolean bool_319_ = true;
					for (int i_320_ = 0; i_320_ < scenetile_290_.sceneSpawnRequestCount; i_320_++) {
						if (scenetile_290_.sceneSpawnRequests[i_320_].anInt608 != Scene.anInt528
								&& (scenetile_290_.anIntArray1330[i_320_] & scenetile_290_.anInt1336) == scenetile_290_.anInt1337) {
							bool_319_ = false;
							break;
						}
					}
					if (bool_319_) {
						Wall wall = scenetile_290_.wall;
						if (!method543(i_293_, i, i_291_, wall.faceUnknown)) {
							wall.aRenderable769.renderAtPoint(0, Scene.anInt538, Scene.anInt539, Scene.anInt540,
									Scene.anInt541, wall.x - Scene.anInt535, wall.plane - Scene.anInt536, wall.y
											- Scene.anInt537, wall.hash);
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
							if (scenespawnrequest.anInt608 != Scene.anInt528) {
								for (int i_324_ = scenespawnrequest.x; i_324_ <= scenespawnrequest.anInt604; i_324_++) {
									for (int i_325_ = scenespawnrequest.y; i_325_ <= scenespawnrequest.anInt606; i_325_++) {
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
											if (i_324_ < scenespawnrequest.anInt604) {
												i_327_ += 4;
											}
											if (i_325_ > scenespawnrequest.y) {
												i_327_ += 8;
											}
											if (i_325_ < scenespawnrequest.anInt606) {
												i_327_ += 2;
											}
											if ((i_327_ & scenetile_326_.anInt1336) == scenetile_290_.anInt1338) {
												scenetile_290_.aBoolean1335 = true;
												continue while_10_;
											}
										}
									}
								}
								Scene.aSceneSpawnRequestArray542[i_322_++] = scenespawnrequest;
								int i_328_ = Scene.anInt533 - scenespawnrequest.x;
								int i_329_ = scenespawnrequest.anInt604 - Scene.anInt533;
								if (i_329_ > i_328_) {
									i_328_ = i_329_;
								}
								int i_330_ = Scene.anInt534 - scenespawnrequest.y;
								int i_331_ = scenespawnrequest.anInt606 - Scene.anInt534;
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
								SceneSpawnRequest scenespawnrequest = Scene.aSceneSpawnRequestArray542[i_334_];
								if (scenespawnrequest.anInt608 != Scene.anInt528) {
									if (scenespawnrequest.anInt607 > i_332_) {
										i_332_ = scenespawnrequest.anInt607;
										i_333_ = i_334_;
									} else if (scenespawnrequest.anInt607 == i_332_) {
										int i_335_ = scenespawnrequest.anInt599 - Scene.anInt535;
										int i_336_ = scenespawnrequest.anInt600 - Scene.anInt537;
										int i_337_ = Scene.aSceneSpawnRequestArray542[i_333_].anInt599 - Scene.anInt535;
										int i_338_ = Scene.aSceneSpawnRequestArray542[i_333_].anInt600 - Scene.anInt537;
										if (i_335_ * i_335_ + i_336_ * i_336_ > i_337_ * i_337_ + i_338_ * i_338_) {
											i_333_ = i_334_;
										}
									}
								}
							}
							if (i_333_ == -1) {
								break;
							}
							SceneSpawnRequest scenespawnrequest = Scene.aSceneSpawnRequestArray542[i_333_];
							scenespawnrequest.anInt608 = Scene.anInt528;
							if (!method545(i_293_, scenespawnrequest.x, scenespawnrequest.anInt604,
									scenespawnrequest.y, scenespawnrequest.anInt606,
									scenespawnrequest.aRenderable601.modelHeight)) {
								scenespawnrequest.aRenderable601.renderAtPoint(scenespawnrequest.anInt602,
										Scene.anInt538, Scene.anInt539, Scene.anInt540, Scene.anInt541,
										scenespawnrequest.anInt599 - Scene.anInt535, scenespawnrequest.anInt598
												- Scene.anInt536, scenespawnrequest.anInt600 - Scene.anInt537,
										scenespawnrequest.anInt609);
							}
							for (int i_339_ = scenespawnrequest.x; i_339_ <= scenespawnrequest.anInt604; i_339_++) {
								for (int i_340_ = scenespawnrequest.y; i_340_ <= scenespawnrequest.anInt606; i_340_++) {
									SceneTile scenetile_341_ = scenetiles[i_339_][i_340_];
									if (scenetile_341_.anInt1336 != 0) {
										Scene.aLinkedList557.insertBack(scenetile_341_);
									} else if ((i_339_ != i || i_340_ != i_291_) && scenetile_341_.aBoolean1334) {
										Scene.aLinkedList557.insertBack(scenetile_341_);
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
					if (i <= Scene.anInt533 && i > Scene.anInt529) {
						SceneTile scenetile_342_ = scenetiles[i - 1][i_291_];
						if (scenetile_342_ != null && scenetile_342_.aBoolean1334) {
							continue;
						}
					}
					if (i >= Scene.anInt533 && i < Scene.anInt530 - 1) {
						SceneTile scenetile_343_ = scenetiles[i + 1][i_291_];
						if (scenetile_343_ != null && scenetile_343_.aBoolean1334) {
							continue;
						}
					}
					if (i_291_ <= Scene.anInt534 && i_291_ > Scene.anInt531) {
						SceneTile scenetile_344_ = scenetiles[i][i_291_ - 1];
						if (scenetile_344_ != null && scenetile_344_.aBoolean1334) {
							continue;
						}
					}
					if (i_291_ >= Scene.anInt534 && i_291_ < Scene.anInt532 - 1) {
						SceneTile scenetile_345_ = scenetiles[i][i_291_ + 1];
						if (scenetile_345_ != null && scenetile_345_.aBoolean1334) {
							continue;
						}
					}
					scenetile_290_.aBoolean1334 = false;
					Scene.anInt526--;
					CameraAngle cameraangle = scenetile_290_.cameraAngle;
					if (cameraangle != null && cameraangle.anInt154 != 0) {
						if (cameraangle.aRenderable151 != null) {
							cameraangle.aRenderable151.renderAtPoint(0, Scene.anInt538, Scene.anInt539, Scene.anInt540,
									Scene.anInt541, cameraangle.y - Scene.anInt535, cameraangle.x - Scene.anInt536
											- cameraangle.anInt154, cameraangle.z - Scene.anInt537,
									cameraangle.anInt153);
						}
						if (cameraangle.aRenderable152 != null) {
							cameraangle.aRenderable152.renderAtPoint(0, Scene.anInt538, Scene.anInt539, Scene.anInt540,
									Scene.anInt541, cameraangle.y - Scene.anInt535, cameraangle.x - Scene.anInt536
											- cameraangle.anInt154, cameraangle.z - Scene.anInt537,
									cameraangle.anInt153);
						}
						if (cameraangle.aRenderable150 != null) {
							cameraangle.aRenderable150.renderAtPoint(0, Scene.anInt538, Scene.anInt539, Scene.anInt540,
									Scene.anInt541, cameraangle.y - Scene.anInt535, cameraangle.x - Scene.anInt536
											- cameraangle.anInt154, cameraangle.z - Scene.anInt537,
									cameraangle.anInt153);
						}
					}
					if (scenetile_290_.anInt1339 != 0) {
						WallDecoration walldecoration = scenetile_290_.wallDecoration;
						if (walldecoration != null
								&& !method544(i_293_, i, i_291_, walldecoration.renderable.modelHeight)) {
							if ((walldecoration.faceUnknown & scenetile_290_.anInt1339) != 0) {
								walldecoration.renderable.renderAtPoint(walldecoration.face, Scene.anInt538,
										Scene.anInt539, Scene.anInt540, Scene.anInt541, walldecoration.y
												- Scene.anInt535, walldecoration.plane - Scene.anInt536,
										walldecoration.x - Scene.anInt537, walldecoration.hash);
							} else if ((walldecoration.faceUnknown & 0x300) != 0) {
								int i_346_ = walldecoration.y - Scene.anInt535;
								int i_347_ = walldecoration.plane - Scene.anInt536;
								int i_348_ = walldecoration.x - Scene.anInt537;
								int i_349_ = walldecoration.face;
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
								if ((walldecoration.faceUnknown & 0x100) != 0 && i_351_ >= i_350_) {
									int i_352_ = i_346_ + Scene.anIntArray543[i_349_];
									int i_353_ = i_348_ + Scene.anIntArray544[i_349_];
									walldecoration.renderable.renderAtPoint(i_349_ * 512 + 256, Scene.anInt538,
											Scene.anInt539, Scene.anInt540, Scene.anInt541, i_352_, i_347_, i_353_,
											walldecoration.hash);
								}
								if ((walldecoration.faceUnknown & 0x200) != 0 && i_351_ <= i_350_) {
									int i_354_ = i_346_ + Scene.anIntArray545[i_349_];
									int i_355_ = i_348_ + Scene.anIntArray546[i_349_];
									walldecoration.renderable.renderAtPoint(i_349_ * 512 + 1280 & 0x7ff,
											Scene.anInt538, Scene.anInt539, Scene.anInt540, Scene.anInt541, i_354_,
											i_347_, i_355_, walldecoration.hash);
								}
							}
						}
						Wall wall = scenetile_290_.wall;
						if (wall != null) {
							if ((wall.face & scenetile_290_.anInt1339) != 0 && !method543(i_293_, i, i_291_, wall.face)) {
								wall.aRenderable770.renderAtPoint(0, Scene.anInt538, Scene.anInt539, Scene.anInt540,
										Scene.anInt541, wall.x - Scene.anInt535, wall.plane - Scene.anInt536, wall.y
												- Scene.anInt537, wall.hash);
							}
							if ((wall.faceUnknown & scenetile_290_.anInt1339) != 0
									&& !method543(i_293_, i, i_291_, wall.faceUnknown)) {
								wall.aRenderable769.renderAtPoint(0, Scene.anInt538, Scene.anInt539, Scene.anInt540,
										Scene.anInt541, wall.x - Scene.anInt535, wall.plane - Scene.anInt536, wall.y
												- Scene.anInt537, wall.hash);
							}
						}
					}
					if (i_292_ < anInt517 - 1) {
						SceneTile scenetile_356_ = tiles[i_292_ + 1][i][i_291_];
						if (scenetile_356_ != null && scenetile_356_.aBoolean1334) {
							Scene.aLinkedList557.insertBack(scenetile_356_);
						}
					}
					if (i < Scene.anInt533) {
						SceneTile scenetile_357_ = scenetiles[i + 1][i_291_];
						if (scenetile_357_ != null && scenetile_357_.aBoolean1334) {
							Scene.aLinkedList557.insertBack(scenetile_357_);
						}
					}
					if (i_291_ < Scene.anInt534) {
						SceneTile scenetile_358_ = scenetiles[i][i_291_ + 1];
						if (scenetile_358_ != null && scenetile_358_.aBoolean1334) {
							Scene.aLinkedList557.insertBack(scenetile_358_);
						}
					}
					if (i > Scene.anInt533) {
						SceneTile scenetile_359_ = scenetiles[i - 1][i_291_];
						if (scenetile_359_ != null && scenetile_359_.aBoolean1334) {
							Scene.aLinkedList557.insertBack(scenetile_359_);
						}
					}
					if (i_291_ > Scene.anInt534) {
						SceneTile scenetile_360_ = scenetiles[i][i_291_ - 1];
						if (scenetile_360_ != null && scenetile_360_.aBoolean1334) {
							Scene.aLinkedList557.insertBack(scenetile_360_);
						}
					}
				}
			}
		}
	}

	public void method537(GenericTile generictile, int i, int i_361_, int i_362_, int i_363_, int i_364_, int i_365_,
			int i_366_) {
		int i_368_;
		int i_367_ = i_368_ = (i_365_ << 7) - Scene.anInt535;
		int i_370_;
		int i_369_ = i_370_ = (i_366_ << 7) - Scene.anInt537;
		int i_372_;
		int i_371_ = i_372_ = i_367_ + 128;
		int i_374_;
		int i_373_ = i_374_ = i_369_ + 128;
		int i_375_ = anIntArrayArrayArray520[i][i_365_][i_366_] - Scene.anInt536;
		int i_376_ = anIntArrayArrayArray520[i][i_365_ + 1][i_366_] - Scene.anInt536;
		int i_377_ = anIntArrayArrayArray520[i][i_365_ + 1][i_366_ + 1] - Scene.anInt536;
		int i_378_ = anIntArrayArrayArray520[i][i_365_][i_366_ + 1] - Scene.anInt536;
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
							if (Scene.aBoolean547
									&& method540(Scene.anInt548, Scene.anInt549, i_385_, i_387_, i_383_, i_384_,
											i_386_, i_382_)) {
								Scene.anInt550 = i_365_;
								Scene.anInt551 = i_366_;
							}
							if (generictile.texture == -1) {
								if (generictile.anInt294 != 12345678) {
									Rasterizer3D.method371(i_385_, i_387_, i_383_, i_384_, i_386_, i_382_,
											generictile.anInt294, generictile.anInt295, generictile.anInt293);
								}
							} else if (!Scene.lowMemory) {
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
								int i_388_ = Scene.anIntArray565[generictile.texture];
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
							if (Scene.aBoolean547
									&& method540(Scene.anInt548, Scene.anInt549, i_381_, i_383_, i_387_, i_380_,
											i_382_, i_386_)) {
								Scene.anInt550 = i_365_;
								Scene.anInt551 = i_366_;
							}
							if (generictile.texture == -1) {
								if (generictile.anInt292 != 12345678) {
									Rasterizer3D.method371(i_381_, i_383_, i_387_, i_380_, i_382_, i_386_,
											generictile.anInt292, generictile.anInt293, generictile.anInt295);
								}
							} else if (!Scene.lowMemory) {
								Rasterizer3D.method375(i_381_, i_383_, i_387_, i_380_, i_382_, i_386_,
										generictile.anInt292, generictile.anInt293, generictile.anInt295, i_367_,
										i_371_, i_368_, i_375_, i_376_, i_378_, i_369_, i_370_, i_374_,
										generictile.texture);
							} else {
								int i_389_ = Scene.anIntArray565[generictile.texture];
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
					int i_397_ = complextile.anIntArray195[i_396_] - Scene.anInt535;
					int i_398_ = complextile.anIntArray196[i_396_] - Scene.anInt536;
					int i_399_ = complextile.anIntArray197[i_396_] - Scene.anInt537;
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
						if (Scene.aBoolean547
								&& method540(Scene.anInt548, Scene.anInt549, i_408_, i_409_, i_410_, i_405_, i_406_,
										i_407_)) {
							Scene.anInt550 = i;
							Scene.anInt551 = i_393_;
						}
						if (complextile.anIntArray204 == null || complextile.anIntArray204[i_401_] == -1) {
							if (complextile.anIntArray198[i_401_] != 12345678) {
								Rasterizer3D.method371(i_408_, i_409_, i_410_, i_405_, i_406_, i_407_,
										complextile.anIntArray198[i_401_], complextile.anIntArray199[i_401_],
										complextile.anIntArray200[i_401_]);
							}
						} else if (!Scene.lowMemory) {
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
							int i_411_ = Scene.anIntArray565[complextile.anIntArray204[i_401_]];
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
				int i_424_ = Scene.anIntArray553[Scene.anInt527];
				SceneCluster[] sceneclusters = Scene.aSceneClusterArrayArray554[Scene.anInt527];
				Scene.anInt555 = 0;
				int i_425_ = 0;
				for (/**/; i_425_ < i_424_; i_425_++) {
					SceneCluster scenecluster = sceneclusters[i_425_];
					if (scenecluster.anInt583 == 1) {
						int i_426_ = scenecluster.anInt579 - Scene.anInt533 + 25;
						if (i_426_ >= 0 && i_426_ <= 50) {
							int i_427_ = scenecluster.anInt581 - Scene.anInt534 + 25;
							if (i_427_ < 0) {
								i_427_ = 0;
							}
							int i_428_ = scenecluster.anInt582 - Scene.anInt534 + 25;
							if (i_428_ > 50) {
								i_428_ = 50;
							}
							boolean bool = false;
							while (i_427_ <= i_428_) {
								if (Scene.aBooleanArrayArray572[i_426_][i_427_++]) {
									bool = true;
									break;
								}
							}
							if (bool) {
								int i_429_ = Scene.anInt535 - scenecluster.anInt584;
								if (i_429_ > 32) {
									scenecluster.anInt590 = 1;
								} else {
									if (i_429_ >= -32) {
										continue;
									}
									scenecluster.anInt590 = 2;
									i_429_ = -i_429_;
								}
								scenecluster.anInt593 = (scenecluster.anInt586 - Scene.anInt537 << 8) / i_429_;
								scenecluster.anInt594 = (scenecluster.anInt587 - Scene.anInt537 << 8) / i_429_;
								scenecluster.anInt595 = (scenecluster.anInt588 - Scene.anInt536 << 8) / i_429_;
								scenecluster.anInt596 = (scenecluster.anInt589 - Scene.anInt536 << 8) / i_429_;
								Scene.aSceneClusterArray556[Scene.anInt555++] = scenecluster;
							}
						}
					} else if (scenecluster.anInt583 == 2) {
						int i_430_ = scenecluster.anInt581 - Scene.anInt534 + 25;
						if (i_430_ >= 0 && i_430_ <= 50) {
							int i_431_ = scenecluster.anInt579 - Scene.anInt533 + 25;
							if (i_431_ < 0) {
								i_431_ = 0;
							}
							int i_432_ = scenecluster.anInt580 - Scene.anInt533 + 25;
							if (i_432_ > 50) {
								i_432_ = 50;
							}
							boolean bool = false;
							while (i_431_ <= i_432_) {
								if (Scene.aBooleanArrayArray572[i_431_++][i_430_]) {
									bool = true;
									break;
								}
							}
							if (bool) {
								int i_433_ = Scene.anInt537 - scenecluster.anInt586;
								if (i_433_ > 32) {
									scenecluster.anInt590 = 3;
								} else {
									if (i_433_ >= -32) {
										continue;
									}
									scenecluster.anInt590 = 4;
									i_433_ = -i_433_;
								}
								scenecluster.anInt591 = (scenecluster.anInt584 - Scene.anInt535 << 8) / i_433_;
								scenecluster.anInt592 = (scenecluster.anInt585 - Scene.anInt535 << 8) / i_433_;
								scenecluster.anInt595 = (scenecluster.anInt588 - Scene.anInt536 << 8) / i_433_;
								scenecluster.anInt596 = (scenecluster.anInt589 - Scene.anInt536 << 8) / i_433_;
								Scene.aSceneClusterArray556[Scene.anInt555++] = scenecluster;
							}
						}
					} else if (scenecluster.anInt583 == 4) {
						int i_434_ = scenecluster.anInt588 - Scene.anInt536;
						if (i_434_ > 128) {
							int i_435_ = scenecluster.anInt581 - Scene.anInt534 + 25;
							if (i_435_ < 0) {
								i_435_ = 0;
							}
							int i_436_ = scenecluster.anInt582 - Scene.anInt534 + 25;
							if (i_436_ > 50) {
								i_436_ = 50;
							}
							if (i_435_ <= i_436_) {
								int i_437_ = scenecluster.anInt579 - Scene.anInt533 + 25;
								if (i_437_ < 0) {
									i_437_ = 0;
								}
								int i_438_ = scenecluster.anInt580 - Scene.anInt533 + 25;
								if (i_438_ > 50) {
									i_438_ = 50;
								}
								boolean bool = false;
								while_11_: for (int i_439_ = i_437_; i_439_ <= i_438_; i_439_++) {
									for (int i_440_ = i_435_; i_440_ <= i_436_; i_440_++) {
										if (Scene.aBooleanArrayArray572[i_439_][i_440_]) {
											bool = true;
											break while_11_;
										}
									}
								}
								if (bool) {
									scenecluster.anInt590 = 5;
									scenecluster.anInt591 = (scenecluster.anInt584 - Scene.anInt535 << 8) / i_434_;
									scenecluster.anInt592 = (scenecluster.anInt585 - Scene.anInt535 << 8) / i_434_;
									scenecluster.anInt593 = (scenecluster.anInt586 - Scene.anInt537 << 8) / i_434_;
									scenecluster.anInt594 = (scenecluster.anInt587 - Scene.anInt537 << 8) / i_434_;
									Scene.aSceneClusterArray556[Scene.anInt555++] = scenecluster;
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
		if (i_443_ == -Scene.anInt528) {
			return false;
		}
		if (i_443_ == Scene.anInt528) {
			return true;
		}
		int i_444_ = i_441_ << 7;
		int i_445_ = i_442_ << 7;
		if (method546(i_444_ + 1, anIntArrayArrayArray520[i][i_441_][i_442_], i_445_ + 1)
				&& method546(i_444_ + 128 - 1, anIntArrayArrayArray520[i][i_441_ + 1][i_442_], i_445_ + 1)
				&& method546(i_444_ + 128 - 1, anIntArrayArrayArray520[i][i_441_ + 1][i_442_ + 1], i_445_ + 128 - 1)
				&& method546(i_444_ + 1, anIntArrayArrayArray520[i][i_441_][i_442_ + 1], i_445_ + 128 - 1)) {
			anIntArrayArrayArray525[i][i_441_][i_442_] = Scene.anInt528;
			return true;
		}
		anIntArrayArrayArray525[i][i_441_][i_442_] = -Scene.anInt528;
		return false;
	}

	private boolean method543(int i, int i_446_, int i_447_, int i_448_) {
		if (!method542(i, i_446_, i_447_)) {
			return false;
		}
		int i_449_ = i_446_ << 7;
		int i_450_ = i_447_ << 7;
		int i_451_ = anIntArrayArrayArray520[i][i_446_][i_447_] - 1;
		int i_452_ = i_451_ - 120;
		int i_453_ = i_451_ - 230;
		int i_454_ = i_451_ - 238;
		if (i_448_ < 16) {
			if (i_448_ == 1) {
				if (i_449_ > Scene.anInt535) {
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
				if (i_450_ < Scene.anInt537) {
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
				if (i_449_ < Scene.anInt535) {
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
				if (i_450_ > Scene.anInt537) {
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

	private boolean method544(int i, int i_455_, int i_456_, int i_457_) {
		if (!method542(i, i_455_, i_456_)) {
			return false;
		}
		int i_458_ = i_455_ << 7;
		int i_459_ = i_456_ << 7;
		if (method546(i_458_ + 1, anIntArrayArrayArray520[i][i_455_][i_456_] - i_457_, i_459_ + 1)
				&& method546(i_458_ + 128 - 1, anIntArrayArrayArray520[i][i_455_ + 1][i_456_] - i_457_, i_459_ + 1)
				&& method546(i_458_ + 128 - 1, anIntArrayArrayArray520[i][i_455_ + 1][i_456_ + 1] - i_457_,
						i_459_ + 128 - 1)
				&& method546(i_458_ + 1, anIntArrayArrayArray520[i][i_455_][i_456_ + 1] - i_457_, i_459_ + 128 - 1)) {
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
			if (method546(i_465_ + 1, anIntArrayArrayArray520[i][i_460_][i_462_] - i_464_, i_466_ + 1)
					&& method546(i_465_ + 128 - 1, anIntArrayArrayArray520[i][i_460_ + 1][i_462_] - i_464_, i_466_ + 1)
					&& method546(i_465_ + 128 - 1, anIntArrayArrayArray520[i][i_460_ + 1][i_462_ + 1] - i_464_,
							i_466_ + 128 - 1)
					&& method546(i_465_ + 1, anIntArrayArrayArray520[i][i_460_][i_462_ + 1] - i_464_, i_466_ + 128 - 1)) {
				return true;
			}
			return false;
		}
		for (int i_467_ = i_460_; i_467_ <= i_461_; i_467_++) {
			for (int i_468_ = i_462_; i_468_ <= i_463_; i_468_++) {
				if (anIntArrayArrayArray525[i][i_467_][i_468_] == -Scene.anInt528) {
					return false;
				}
			}
		}
		int i_469_ = (i_460_ << 7) + 1;
		int i_470_ = (i_462_ << 7) + 2;
		int i_471_ = anIntArrayArrayArray520[i][i_460_][i_462_] - i_464_;
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
		for (int i_476_ = 0; i_476_ < Scene.anInt555; i_476_++) {
			SceneCluster scenecluster = Scene.aSceneClusterArray556[i_476_];
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
