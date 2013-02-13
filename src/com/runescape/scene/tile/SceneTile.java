package com.runescape.scene.tile;

import com.runescape.collection.Node;
import com.runescape.scene.CameraAngle;
import com.runescape.scene.SceneSpawnRequest;

public class SceneTile extends Node {

	public int anInt1318;
	public int anInt1319;
	public int anInt1320;
	public int anInt1321;
	public GenericTile genericTile;
	public ComplexTile complexTile;
	public Wall wall;
	public WallDecoration wallDecoration;
	public FloorDecoration floorDecoration;
	public CameraAngle cameraAngle;
	public int sceneSpawnRequestCount;
	public SceneSpawnRequest[] sceneSpawnRequests = new SceneSpawnRequest[5];
	public int[] anIntArray1330 = new int[5];
	public int anInt1331;
	public int anInt1332;
	public boolean aBoolean1333;
	public boolean aBoolean1334;
	public boolean aBoolean1335;
	public int anInt1336;
	public int anInt1337;
	public int anInt1338;
	public int anInt1339;
	public SceneTile sceneTile;

	public SceneTile(int i, int i_0_, int i_1_) {
		anInt1321 = anInt1318 = i;
		anInt1319 = i_0_;
		anInt1320 = i_1_;
	}
}
