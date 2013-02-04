package com.runescape.scene;

import com.runescape.cache.def.FloorDefinition;
import com.runescape.cache.def.GameObjectDefinition;
import com.runescape.cache.requester.OnDemandRequester;
import com.runescape.graphic.Model;
import com.runescape.graphic.Rasterizer3D;
import com.runescape.net.Buffer;
import com.runescape.renderable.Renderable;
import com.runescape.util.SignLink;

/* Region - Decompiled by JODE
 * Visit http://jode.sourceforge.net/
 */

public class Region {
	private static int hueRandomizer = (int) (Math.random() * 17.0) - 8;
	private int[] blendedHue;
	private int[] blendedSaturation;
	private int[] blendedLightness;
	private int[] blendedHueDivisor;
	private int[] blendDirectionTracker;
	private int[][][] vertexHeights;
	private byte[][][] overlayFloorIds;
	public static int onBuildTimePlane;
	private static int lightnessRandomizer = (int) (Math.random() * 33.0) - 16;
	private byte[][][] tileShadowIntensity;
	private int[][][] tileCullingBitsets;
	private byte[][][] overlayClippingPaths;
	private static final int[] anIntArray491 = { 1, 0, -1, 0 };
	private static int anInt492 = 323;
	private int[][] tileLightingIntensity;
	private static final int[] anIntArray494 = { 16, 32, 64, 128 };
	private static boolean aBoolean495;
	private byte[][][] underlayFloorIds;
	private boolean aBoolean497 = false;
	private static final int[] anIntArray498 = { 0, -1, 0, 1 };
	public static int lowestPlane = 99;
	private int regionSizeX;
	private int regionSizeY;
	private byte[][][] aByteArrayArrayArray502;
	private byte[][][] renderRuleFlags;
	private int anInt504 = -53;
	public static boolean lowMemory = true;
	private static final int[] anIntArray506 = { 1, 2, 4, 8 };

	public Region(byte[][][] bs, int i, int i_0_, int i_1_, int[][][] is) {
		try {
			Region.lowestPlane = 99;
			regionSizeX = i_1_;
			regionSizeY = i_0_;
			while (i >= 0) {
			}
			vertexHeights = is;
			renderRuleFlags = bs;
			underlayFloorIds = new byte[4][regionSizeX][regionSizeY];
			overlayFloorIds = new byte[4][regionSizeX][regionSizeY];
			overlayClippingPaths = new byte[4][regionSizeX][regionSizeY];
			aByteArrayArrayArray502 = new byte[4][regionSizeX][regionSizeY];
			tileCullingBitsets = new int[4][regionSizeX + 1][regionSizeY + 1];
			tileShadowIntensity = new byte[4][regionSizeX + 1][regionSizeY + 1];
			tileLightingIntensity = new int[regionSizeX + 1][regionSizeY + 1];
			blendedHue = new int[regionSizeY];
			blendedSaturation = new int[regionSizeY];
			blendedLightness = new int[regionSizeY];
			blendedHueDivisor = new int[regionSizeY];
			blendDirectionTracker = new int[regionSizeY];
		} catch (RuntimeException runtimeexception) {
			SignLink.reportError("40803, " + bs + ", " + i + ", " + i_0_ + ", " + i_1_ + ", " + is + ", "
					+ runtimeexception.toString());
			throw new RuntimeException();
		}
	}

	private static final int method452(int i, int i_2_) {
		int i_3_ = i + i_2_ * 57;
		i_3_ = i_3_ << 13 ^ i_3_;
		int i_4_ = i_3_ * (i_3_ * i_3_ * 15731 + 789221) + 1376312589 & 0x7fffffff;
		return i_4_ >> 19 & 0xff;
	}

	public final void createRegionScene(CollisionMap[] collisionMaps, Scene scene) {
		for (int plane = 0; plane < 4; plane++) {
			for (int x = 0; x < 104; x++) {
				for (int y = 0; y < 104; y++) {
					if ((renderRuleFlags[plane][x][y] & 0x1) == 1) {
						int originalPlane = plane;
						if ((renderRuleFlags[1][x][y] & 0x2) == 2) {
							originalPlane--;
						}
						if (originalPlane >= 0) {
							collisionMaps[originalPlane].setSolidFlag(x, y);
						}
					}
				}
			}
		}
		Region.hueRandomizer += (int) (Math.random() * 5.0) - 2;
		if (Region.hueRandomizer < -8) {
			Region.hueRandomizer = -8;
		}
		if (Region.hueRandomizer > 8) {
			Region.hueRandomizer = 8;
		}
		Region.lightnessRandomizer += (int) (Math.random() * 5.0) - 2;
		if (Region.lightnessRandomizer < -16) {
			Region.lightnessRandomizer = -16;
		}
		if (Region.lightnessRandomizer > 16) {
			Region.lightnessRandomizer = 16;
		}
		for (int plane = 0; plane < 4; plane++) {
			byte[][] shadowIntensity = tileShadowIntensity[plane];
			int directionalLightInitialIntensity = 96;
			int specularDistributionFactor = 768;
			int directionalLightX = -50;
			int directionalLightY = -10;
			int directionalLightZ = -50;
			int directionalLightLength = (int) Math.sqrt(directionalLightX * directionalLightX + directionalLightY
					* directionalLightY + directionalLightZ * directionalLightZ);
			int specularDistribution = specularDistributionFactor * directionalLightLength >> 8;
			for (int y = 1; y < regionSizeY - 1; y++) {
				for (int x = 1; x < regionSizeX - 1; x++) {
					int xHeightDifference = vertexHeights[plane][x + 1][y] - vertexHeights[plane][x - 1][y];
					int yHeightDifference = vertexHeights[plane][x][y + 1] - vertexHeights[plane][x][y - 1];
					int normalizedLength = (int) Math.sqrt(xHeightDifference * xHeightDifference + 65536
							+ yHeightDifference * yHeightDifference);
					int normalizedNormalX = (xHeightDifference << 8) / normalizedLength;
					int normalizedNormalY = 65536 / normalizedLength;
					int normalizedNormalZ = (yHeightDifference << 8) / normalizedLength;
					int directionalLightIntensity = directionalLightInitialIntensity
							+ (directionalLightX * normalizedNormalX + directionalLightY * normalizedNormalY + directionalLightZ
									* normalizedNormalZ) / specularDistribution;
					int weightedShadowIntensity = (shadowIntensity[x - 1][y] >> 2) + (shadowIntensity[x + 1][y] >> 3)
							+ (shadowIntensity[x][y - 1] >> 2) + (shadowIntensity[x][y + 1] >> 3)
							+ (shadowIntensity[x][y] >> 1);
					tileLightingIntensity[x][y] = directionalLightIntensity - weightedShadowIntensity;
				}
			}
			for (int y = 0; y < regionSizeY; y++) {
				blendedHue[y] = 0;
				blendedSaturation[y] = 0;
				blendedLightness[y] = 0;
				blendedHueDivisor[y] = 0;
				blendDirectionTracker[y] = 0;
			}
			for (int x = -5; x < regionSizeX + 5; x++) {
				for (int y = 0; y < regionSizeY; y++) {
					int xPositiveOffset = x + 5;
					if (xPositiveOffset >= 0 && xPositiveOffset < regionSizeX) {
						int floorId = underlayFloorIds[plane][xPositiveOffset][y] & 0xff;
						if (floorId > 0) {
							FloorDefinition floor = FloorDefinition.cache[floorId - 1];
							blendedHue[y] += floor.hue;
							blendedSaturation[y] += floor.saturation;
							blendedLightness[y] += floor.lightness;
							blendedHueDivisor[y] += floor.hueDivisor;
							blendDirectionTracker[y]++;
						}
					}
					int xNegativeOffset = x - 5;
					if (xNegativeOffset >= 0 && xNegativeOffset < regionSizeX) {
						int floorId = underlayFloorIds[plane][xNegativeOffset][y] & 0xff;
						if (floorId > 0) {
							FloorDefinition floor = FloorDefinition.cache[floorId - 1];
							blendedHue[y] -= floor.hue;
							blendedSaturation[y] -= floor.saturation;
							blendedLightness[y] -= floor.lightness;
							blendedHueDivisor[y] -= floor.hueDivisor;
							blendDirectionTracker[y]--;
						}
					}
				}
				if (x >= 1 && x < regionSizeX - 1) {
					int i_34_ = 0;
					int i_35_ = 0;
					int i_36_ = 0;
					int i_37_ = 0;
					int i_38_ = 0;
					for (int y = -5; y < regionSizeY + 5; y++) {
						int yPositiveOffset = y + 5;
						if (yPositiveOffset >= 0 && yPositiveOffset < regionSizeY) {
							i_34_ += blendedHue[yPositiveOffset];
							i_35_ += blendedSaturation[yPositiveOffset];
							i_36_ += blendedLightness[yPositiveOffset];
							i_37_ += blendedHueDivisor[yPositiveOffset];
							i_38_ += blendDirectionTracker[yPositiveOffset];
						}
						int yNegativeOffset = y - 5;
						if (yNegativeOffset >= 0 && yNegativeOffset < regionSizeY) {
							i_34_ -= blendedHue[yNegativeOffset];
							i_35_ -= blendedSaturation[yNegativeOffset];
							i_36_ -= blendedLightness[yNegativeOffset];
							i_37_ -= blendedHueDivisor[yNegativeOffset];
							i_38_ -= blendDirectionTracker[yNegativeOffset];
						}
						if (y >= 1
								&& y < regionSizeY - 1
								&& (!Region.lowMemory || (renderRuleFlags[0][x][y] & 0x2) != 0 || (renderRuleFlags[plane][x][y] & 0x10) == 0
										&& getVisibilityPlaneFor(y, plane, x, 0) == Region.onBuildTimePlane)) {
							if (plane < Region.lowestPlane) {
								Region.lowestPlane = plane;
							}
							int underlayFloorId = underlayFloorIds[plane][x][y] & 0xff;
							int overlayFloorId = overlayFloorIds[plane][x][y] & 0xff;
							if (underlayFloorId > 0 || overlayFloorId > 0) {
								int vertexSouthWest = vertexHeights[plane][x][y];
								int vertexSouthEast = vertexHeights[plane][x + 1][y];
								int vertexNorthEast = vertexHeights[plane][x + 1][y + 1];
								int vertexNorthWest = vertexHeights[plane][x][y + 1];
								int lightSouthWest = tileLightingIntensity[x][y];
								int lightSouthEast = tileLightingIntensity[x + 1][y];
								int lightNorthEast = tileLightingIntensity[x + 1][y + 1];
								int lightNorthWest = tileLightingIntensity[x][y + 1];
								int hslBitsetUnmodified = -1;
								int hslBitsetRandomized = -1;
								if (underlayFloorId > 0) {
									int hue = i_34_ * 256 / i_37_;
									int saturation = i_35_ / i_38_;
									int lightness = i_36_ / i_38_;
									hslBitsetUnmodified = getHSLBitset(hue, saturation, lightness);
									hue = hue + Region.hueRandomizer & 0xff;
									lightness += Region.lightnessRandomizer;
									if (lightness < 0) {
										lightness = 0;
									} else if (lightness > 255) {
										lightness = 255;
									}
									hslBitsetRandomized = getHSLBitset(hue, saturation, lightness);
								}
								if (plane > 0) {
									boolean hideUnderlay = true;
									if (underlayFloorId == 0 && overlayClippingPaths[plane][x][y] != 0) {
										hideUnderlay = false;
									}
									if (overlayFloorId > 0 && !FloorDefinition.cache[overlayFloorId - 1].occlude) {
										hideUnderlay = false;
									}
									if (hideUnderlay && vertexSouthWest == vertexSouthEast
											&& vertexSouthWest == vertexNorthEast && vertexSouthWest == vertexNorthWest) {
										tileCullingBitsets[plane][x][y] |= 0x924;
									}
								}
								int rgbBitsetRandomized = 0;
								if (hslBitsetUnmodified != -1) {
									rgbBitsetRandomized = Rasterizer3D.getRgbLookupTableId[Region.trimHSLLightness(
											hslBitsetRandomized, 96)];
								}
								if (overlayFloorId == 0) {
									scene.method501(plane, x, y, 0, 0, -1, vertexSouthWest, vertexSouthEast,
											vertexNorthEast, vertexNorthWest,
											Region.trimHSLLightness(hslBitsetUnmodified, lightSouthWest),
											Region.trimHSLLightness(hslBitsetUnmodified, lightSouthEast),
											Region.trimHSLLightness(hslBitsetUnmodified, lightNorthEast),
											Region.trimHSLLightness(hslBitsetUnmodified, lightNorthWest), 0, 0, 0, 0,
											rgbBitsetRandomized, 0);
								} else {
									int clippingPath = overlayClippingPaths[plane][x][y] + 1;
									byte clippingPathRotation = aByteArrayArrayArray502[plane][x][y];
									FloorDefinition floor = FloorDefinition.cache[overlayFloorId - 1];
									int textureid = floor.textureid;
									int hslBitset;
									int rgbBitset;
									if (textureid >= 0) {
										hslBitset = Rasterizer3D.getAverageRgbColorForTexture(textureid, 12660);
										rgbBitset = -1;
									} else if (floor.rgbColor == 16711935) {
										hslBitset = 0;
										rgbBitset = -2;
										textureid = -1;
									} else {
										rgbBitset = getHSLBitset(floor.hue2, floor.saturation, floor.lightness);
										hslBitset = Rasterizer3D.getRgbLookupTableId[method467(floor.hslColor2, 96)];
									}
									scene.method501(plane, x, y, clippingPath, clippingPathRotation, textureid,
											vertexSouthWest, vertexSouthEast, vertexNorthEast, vertexNorthWest,
											Region.trimHSLLightness(hslBitsetUnmodified, lightSouthWest),
											Region.trimHSLLightness(hslBitsetUnmodified, lightSouthEast),
											Region.trimHSLLightness(hslBitsetUnmodified, lightNorthEast),
											Region.trimHSLLightness(hslBitsetUnmodified, lightNorthWest),
											method467(rgbBitset, lightSouthWest), method467(rgbBitset, lightSouthEast),
											method467(rgbBitset, lightNorthEast), method467(rgbBitset, lightNorthWest),
											rgbBitsetRandomized, hslBitset);
								}
							}
						}
					}
				}
			}
			for (int i_62_ = 1; i_62_ < regionSizeY - 1; i_62_++) {
				for (int i_63_ = 1; i_63_ < regionSizeX - 1; i_63_++) {
					scene.method500(plane, i_63_, i_62_, getVisibilityPlaneFor(i_62_, plane, i_63_, 0));
				}
			}
		}
		scene.method527(-10, (byte) 3, 64, -50, 768, -50);
		for (int y = 0; y < regionSizeX; y++) {
			for (int x = 0; x < regionSizeY; x++) {
				if ((renderRuleFlags[1][y][x] & 0x2) == 2) {
					scene.setBridgeMode(x, y, -438);
				}
			}
		}
		int renderRule1 = 1;
		int renderRule2 = 2;
		int renderRule3 = 4;
		for (int currentPlane = 0; currentPlane < 4; currentPlane++) {
			if (currentPlane > 0) {
				renderRule1 <<= 3;
				renderRule2 <<= 3;
				renderRule3 <<= 3;
			}
			for (int plane = 0; plane <= currentPlane; plane++) {
				for (int y = 0; y <= regionSizeY; y++) {
					for (int x = 0; x <= regionSizeX; x++) {
						if ((tileCullingBitsets[plane][x][y] & renderRule1) != 0) {
							int lowestOcclussionY = y;
							int higestOcclussionY = y;
							int lowestOcclussionPlane = plane;
							int higestOcclussionPlane = plane;
							for (; lowestOcclussionY > 0; lowestOcclussionY--) {
								if ((tileCullingBitsets[plane][x][lowestOcclussionY - 1] & renderRule1) == 0) {
									break;
								}
							}
							for (; higestOcclussionY < regionSizeY; higestOcclussionY++) {
								if ((tileCullingBitsets[plane][x][higestOcclussionY + 1] & renderRule1) == 0) {
									break;
								}
							}
							findLowestOcclussionPlane: for (/**/; lowestOcclussionPlane > 0; lowestOcclussionPlane--) {
								for (int occludedY = lowestOcclussionY; occludedY <= higestOcclussionY; occludedY++) {
									if ((tileCullingBitsets[lowestOcclussionPlane - 1][x][occludedY] & renderRule1) == 0) {
										break findLowestOcclussionPlane;
									}
								}
							}
							findHighestOcclussionPlane: for (/**/; higestOcclussionPlane < currentPlane; higestOcclussionPlane++) {
								for (int occludedY = lowestOcclussionY; occludedY <= higestOcclussionY; occludedY++) {
									if ((tileCullingBitsets[higestOcclussionPlane + 1][x][occludedY] & renderRule1) == 0) {
										break findHighestOcclussionPlane;
									}
								}
							}
							int occlussionSurface = (higestOcclussionPlane + 1 - lowestOcclussionPlane)
									* (higestOcclussionY - lowestOcclussionY + 1);
							if (occlussionSurface >= 8) {
								int highestOcclussionVertexHeightOffset = 240;
								int highestOcclussionVertexHeight = vertexHeights[higestOcclussionPlane][x][lowestOcclussionY]
										- highestOcclussionVertexHeightOffset;
								int lowestOcclussionVertexHeight = vertexHeights[lowestOcclussionPlane][x][lowestOcclussionY];
								Scene.createCullingOcclussionBox(currentPlane, x * 128, lowestOcclussionVertexHeight,
										x * 128, higestOcclussionY * 128 + 128, highestOcclussionVertexHeight,
										anInt504, lowestOcclussionY * 128, 1);
								for (int occludedPlane = lowestOcclussionPlane; occludedPlane <= higestOcclussionPlane; occludedPlane++) {
									for (int occludedY = lowestOcclussionY; occludedY <= higestOcclussionY; occludedY++) {
										tileCullingBitsets[occludedPlane][x][occludedY] &= renderRule1 ^ 0xffffffff;
									}
								}
							}
						}
						if ((tileCullingBitsets[plane][x][y] & renderRule2) != 0) {
							int i_85_ = x;
							int i_86_ = x;
							int i_87_ = plane;
							int i_88_ = plane;
							for (/**/; i_85_ > 0; i_85_--) {
								if ((tileCullingBitsets[plane][i_85_ - 1][y] & renderRule2) == 0) {
									break;
								}
							}
							for (/**/; i_86_ < regionSizeX; i_86_++) {
								if ((tileCullingBitsets[plane][i_86_ + 1][y] & renderRule2) == 0) {
									break;
								}
							}
							while_4_: for (/**/; i_87_ > 0; i_87_--) {
								for (int i_89_ = i_85_; i_89_ <= i_86_; i_89_++) {
									if ((tileCullingBitsets[i_87_ - 1][i_89_][y] & renderRule2) == 0) {
										break while_4_;
									}
								}
							}
							while_5_: for (/**/; i_88_ < currentPlane; i_88_++) {
								for (int i_90_ = i_85_; i_90_ <= i_86_; i_90_++) {
									if ((tileCullingBitsets[i_88_ + 1][i_90_][y] & renderRule2) == 0) {
										break while_5_;
									}
								}
							}
							int i_91_ = (i_88_ + 1 - i_87_) * (i_86_ - i_85_ + 1);
							if (i_91_ >= 8) {
								int i_92_ = 240;
								int i_93_ = vertexHeights[i_88_][i_85_][y] - i_92_;
								int i_94_ = vertexHeights[i_87_][i_85_][y];
								Scene.createCullingOcclussionBox(currentPlane, i_85_ * 128, i_94_, i_86_ * 128 + 128,
										y * 128, i_93_, anInt504, y * 128, 2);
								for (int i_95_ = i_87_; i_95_ <= i_88_; i_95_++) {
									for (int i_96_ = i_85_; i_96_ <= i_86_; i_96_++) {
										tileCullingBitsets[i_95_][i_96_][y] &= renderRule2 ^ 0xffffffff;
									}
								}
							}
						}
						if ((tileCullingBitsets[plane][x][y] & renderRule3) != 0) {
							int i_97_ = x;
							int i_98_ = x;
							int i_99_ = y;
							int i_100_ = y;
							for (/**/; i_99_ > 0; i_99_--) {
								if ((tileCullingBitsets[plane][x][i_99_ - 1] & renderRule3) == 0) {
									break;
								}
							}
							for (/**/; i_100_ < regionSizeY; i_100_++) {
								if ((tileCullingBitsets[plane][x][i_100_ + 1] & renderRule3) == 0) {
									break;
								}
							}
							while_6_: for (/**/; i_97_ > 0; i_97_--) {
								for (int i_101_ = i_99_; i_101_ <= i_100_; i_101_++) {
									if ((tileCullingBitsets[plane][i_97_ - 1][i_101_] & renderRule3) == 0) {
										break while_6_;
									}
								}
							}
							while_7_: for (/**/; i_98_ < regionSizeX; i_98_++) {
								for (int i_102_ = i_99_; i_102_ <= i_100_; i_102_++) {
									if ((tileCullingBitsets[plane][i_98_ + 1][i_102_] & renderRule3) == 0) {
										break while_7_;
									}
								}
							}
							if ((i_98_ - i_97_ + 1) * (i_100_ - i_99_ + 1) >= 4) {
								int i_103_ = vertexHeights[plane][i_97_][i_99_];
								Scene.createCullingOcclussionBox(currentPlane, i_97_ * 128, i_103_, i_98_ * 128 + 128,
										i_100_ * 128 + 128, i_103_, anInt504, i_99_ * 128, 4);
								for (int i_104_ = i_97_; i_104_ <= i_98_; i_104_++) {
									for (int i_105_ = i_99_; i_105_ <= i_100_; i_105_++) {
										tileCullingBitsets[plane][i_104_][i_105_] &= renderRule3 ^ 0xffffffff;
									}
								}
							}
						}
					}
				}
			}
		}
	}

	private static final int method454(int i, int i_106_) {
		int i_107_ = Region.method458(i + 45365, i_106_ + 91923, 4) - 128
				+ (Region.method458(i + 10294, i_106_ + 37821, 2) - 128 >> 1)
				+ (Region.method458(i, i_106_, 1) - 128 >> 2);
		i_107_ = (int) (i_107_ * 0.3) + 35;
		if (i_107_ < 10) {
			i_107_ = 10;
		} else if (i_107_ > 60) {
			i_107_ = 60;
		}
		return i_107_;
	}

	public static final void method455(byte b, Buffer buffer, OnDemandRequester ondemandrequester) {
		try {
			int i = -1;
			if (b != -107) {
				Region.aBoolean495 = !Region.aBoolean495;
			}
			for (;;) {
				int i_108_ = buffer.getSmartB();
				if (i_108_ == 0) {
					break;
				}
				i += i_108_;
				GameObjectDefinition gameobjectdefinition = GameObjectDefinition.getObjectByHash(i);
				gameobjectdefinition.method234(ondemandrequester, -235);
				for (;;) {
					int i_109_ = buffer.getSmartB();
					if (i_109_ == 0) {
						break;
					}
					buffer.getUnsignedByte();
				}
			}
		} catch (RuntimeException runtimeexception) {
			SignLink.reportError("73405, " + b + ", " + buffer + ", " + ondemandrequester + ", "
					+ runtimeexception.toString());
			throw new RuntimeException();
		}
	}

	public final void method456(int i, int i_110_, int i_111_, int i_112_, int i_113_) {
		do {
			try {
				for (int i_114_ = i; i_114_ <= i + i_110_; i_114_++) {
					for (int i_115_ = i_113_; i_115_ <= i_113_ + i_112_; i_115_++) {
						if (i_115_ >= 0 && i_115_ < regionSizeX && i_114_ >= 0 && i_114_ < regionSizeY) {
							tileShadowIntensity[0][i_115_][i_114_] = (byte) 127;
							if (i_115_ == i_113_ && i_115_ > 0) {
								vertexHeights[0][i_115_][i_114_] = vertexHeights[0][i_115_ - 1][i_114_];
							}
							if (i_115_ == i_113_ + i_112_ && i_115_ < regionSizeX - 1) {
								vertexHeights[0][i_115_][i_114_] = vertexHeights[0][i_115_ + 1][i_114_];
							}
							if (i_114_ == i && i_114_ > 0) {
								vertexHeights[0][i_115_][i_114_] = vertexHeights[0][i_115_][i_114_ - 1];
							}
							if (i_114_ == i + i_110_ && i_114_ < regionSizeY - 1) {
								vertexHeights[0][i_115_][i_114_] = vertexHeights[0][i_115_][i_114_ + 1];
							}
						}
					}
				}
				if (i_111_ == 0) {
					break;
				}
			} catch (RuntimeException runtimeexception) {
				SignLink.reportError("73091, " + i + ", " + i_110_ + ", " + i_111_ + ", " + i_112_ + ", " + i_113_
						+ ", " + runtimeexception.toString());
				throw new RuntimeException();
			}
			break;
		} while (false);
	}

	private final void method457(int i, Scene scene, CollisionMap collisionmap, int i_116_, int i_117_, int i_118_,
			int i_119_, boolean bool, int i_120_) {
		try {
			if (!Region.lowMemory || (renderRuleFlags[0][i_118_][i] & 0x2) != 0
					|| (renderRuleFlags[i_117_][i_118_][i] & 0x10) == 0
					&& getVisibilityPlaneFor(i, i_117_, i_118_, 0) == Region.onBuildTimePlane) {
				if (i_117_ < Region.lowestPlane) {
					Region.lowestPlane = i_117_;
				}
				int i_121_ = vertexHeights[i_117_][i_118_][i];
				int i_122_ = vertexHeights[i_117_][i_118_ + 1][i];
				int i_123_ = vertexHeights[i_117_][i_118_ + 1][i + 1];
				int i_124_ = vertexHeights[i_117_][i_118_][i + 1];
				int i_125_ = i_121_ + i_122_ + i_123_ + i_124_ >> 2;
				GameObjectDefinition gameobjectdefinition = GameObjectDefinition.getObjectByHash(i_119_);
				int i_126_ = i_118_ + (i << 7) + (i_119_ << 14) + 1073741824;
				if (!gameobjectdefinition.hasActions) {
					i_126_ += -2147483648;
				}
				byte b = (byte) ((i_120_ << 6) + i_116_);
				if (!bool) {
					if (i_116_ == 22) {
						if (!Region.lowMemory || gameobjectdefinition.hasActions || gameobjectdefinition.aBoolean241) {
							Renderable renderable;
							if (gameobjectdefinition.anInt286 == -1 && gameobjectdefinition.anIntArray264 == null) {
								renderable = gameobjectdefinition.method238(22, i_120_, i_121_, i_122_, i_123_, i_124_,
										-1);
							} else {
								renderable = new GameObject(i_119_, i_120_, 22, i_122_, (byte) 7, i_123_, i_121_,
										i_124_, gameobjectdefinition.anInt286, true);
							}
							scene.method502(i_117_, i_125_, i, 68, renderable, b, i_126_, i_118_);
							if (gameobjectdefinition.isSolid && gameobjectdefinition.hasActions && collisionmap != null) {
								collisionmap.setSolidFlag(i_118_, i);
							}
						}
					} else if (i_116_ == 10 || i_116_ == 11) {
						Renderable renderable;
						if (gameobjectdefinition.anInt286 == -1 && gameobjectdefinition.anIntArray264 == null) {
							renderable = gameobjectdefinition.method238(10, i_120_, i_121_, i_122_, i_123_, i_124_, -1);
						} else {
							renderable = new GameObject(i_119_, i_120_, 10, i_122_, (byte) 7, i_123_, i_121_, i_124_,
									gameobjectdefinition.anInt286, true);
						}
						if (renderable != null) {
							int i_127_ = 0;
							if (i_116_ == 11) {
								i_127_ += 256;
							}
							int i_128_;
							int i_129_;
							if (i_120_ == 1 || i_120_ == 3) {
								i_128_ = gameobjectdefinition.sizeY;
								i_129_ = gameobjectdefinition.sizeX;
							} else {
								i_128_ = gameobjectdefinition.sizeX;
								i_129_ = gameobjectdefinition.sizeY;
							}
							if (scene.method506(i_126_, b, i_125_, i_129_, renderable, i_128_, i_117_, i_127_,
									(byte) 110, i, i_118_) && gameobjectdefinition.aBoolean284) {
								Model model;
								if (renderable instanceof Model) {
									model = (Model) renderable;
								} else {
									model = gameobjectdefinition.method238(10, i_120_, i_121_, i_122_, i_123_, i_124_,
											-1);
								}
								if (model != null) {
									for (int i_130_ = 0; i_130_ <= i_128_; i_130_++) {
										for (int i_131_ = 0; i_131_ <= i_129_; i_131_++) {
											int i_132_ = model.anInt1643 / 4;
											if (i_132_ > 30) {
												i_132_ = 30;
											}
											if (i_132_ > tileShadowIntensity[i_117_][i_118_ + i_130_][i + i_131_]) {
												tileShadowIntensity[i_117_][i_118_ + i_130_][i + i_131_] = (byte) i_132_;
											}
										}
									}
								}
							}
						}
						if (gameobjectdefinition.isSolid && collisionmap != null) {
							collisionmap.method218(gameobjectdefinition.isWalkable, Region.anInt492,
									gameobjectdefinition.sizeX, gameobjectdefinition.sizeY, i_118_, i, i_120_);
						}
					} else if (i_116_ >= 12) {
						Renderable renderable;
						if (gameobjectdefinition.anInt286 == -1 && gameobjectdefinition.anIntArray264 == null) {
							renderable = gameobjectdefinition.method238(i_116_, i_120_, i_121_, i_122_, i_123_, i_124_,
									-1);
						} else {
							renderable = new GameObject(i_119_, i_120_, i_116_, i_122_, (byte) 7, i_123_, i_121_,
									i_124_, gameobjectdefinition.anInt286, true);
						}
						scene.method506(i_126_, b, i_125_, 1, renderable, 1, i_117_, 0, (byte) 110, i, i_118_);
						if (i_116_ >= 12 && i_116_ <= 17 && i_116_ != 13 && i_117_ > 0) {
							tileCullingBitsets[i_117_][i_118_][i] |= 0x924;
						}
						if (gameobjectdefinition.isSolid && collisionmap != null) {
							collisionmap.method218(gameobjectdefinition.isWalkable, Region.anInt492,
									gameobjectdefinition.sizeX, gameobjectdefinition.sizeY, i_118_, i, i_120_);
						}
					} else if (i_116_ == 0) {
						Renderable renderable;
						if (gameobjectdefinition.anInt286 == -1 && gameobjectdefinition.anIntArray264 == null) {
							renderable = gameobjectdefinition.method238(0, i_120_, i_121_, i_122_, i_123_, i_124_, -1);
						} else {
							renderable = new GameObject(i_119_, i_120_, 0, i_122_, (byte) 7, i_123_, i_121_, i_124_,
									gameobjectdefinition.anInt286, true);
						}
						scene.method504(Region.anIntArray506[i_120_], renderable, true, i_126_, i, b, i_118_, null,
								i_125_, 0, i_117_);
						if (i_120_ == 0) {
							if (gameobjectdefinition.aBoolean284) {
								tileShadowIntensity[i_117_][i_118_][i] = (byte) 50;
								tileShadowIntensity[i_117_][i_118_][i + 1] = (byte) 50;
							}
							if (gameobjectdefinition.aBoolean269) {
								tileCullingBitsets[i_117_][i_118_][i] |= 0x249;
							}
						} else if (i_120_ == 1) {
							if (gameobjectdefinition.aBoolean284) {
								tileShadowIntensity[i_117_][i_118_][i + 1] = (byte) 50;
								tileShadowIntensity[i_117_][i_118_ + 1][i + 1] = (byte) 50;
							}
							if (gameobjectdefinition.aBoolean269) {
								tileCullingBitsets[i_117_][i_118_][i + 1] |= 0x492;
							}
						} else if (i_120_ == 2) {
							if (gameobjectdefinition.aBoolean284) {
								tileShadowIntensity[i_117_][i_118_ + 1][i] = (byte) 50;
								tileShadowIntensity[i_117_][i_118_ + 1][i + 1] = (byte) 50;
							}
							if (gameobjectdefinition.aBoolean269) {
								tileCullingBitsets[i_117_][i_118_ + 1][i] |= 0x249;
							}
						} else if (i_120_ == 3) {
							if (gameobjectdefinition.aBoolean284) {
								tileShadowIntensity[i_117_][i_118_][i] = (byte) 50;
								tileShadowIntensity[i_117_][i_118_ + 1][i] = (byte) 50;
							}
							if (gameobjectdefinition.aBoolean269) {
								tileCullingBitsets[i_117_][i_118_][i] |= 0x492;
							}
						}
						if (gameobjectdefinition.isSolid && collisionmap != null) {
							collisionmap
									.method217(i, i_120_, i_118_, i_116_, (byte) 1, gameobjectdefinition.isWalkable);
						}
						if (gameobjectdefinition.anInt280 != 16) {
							scene.method512(i, 441, gameobjectdefinition.anInt280, i_118_, i_117_);
						}
					} else if (i_116_ == 1) {
						Renderable renderable;
						if (gameobjectdefinition.anInt286 == -1 && gameobjectdefinition.anIntArray264 == null) {
							renderable = gameobjectdefinition.method238(1, i_120_, i_121_, i_122_, i_123_, i_124_, -1);
						} else {
							renderable = new GameObject(i_119_, i_120_, 1, i_122_, (byte) 7, i_123_, i_121_, i_124_,
									gameobjectdefinition.anInt286, true);
						}
						scene.method504(Region.anIntArray494[i_120_], renderable, true, i_126_, i, b, i_118_, null,
								i_125_, 0, i_117_);
						if (gameobjectdefinition.aBoolean284) {
							if (i_120_ == 0) {
								tileShadowIntensity[i_117_][i_118_][i + 1] = (byte) 50;
							} else if (i_120_ == 1) {
								tileShadowIntensity[i_117_][i_118_ + 1][i + 1] = (byte) 50;
							} else if (i_120_ == 2) {
								tileShadowIntensity[i_117_][i_118_ + 1][i] = (byte) 50;
							} else if (i_120_ == 3) {
								tileShadowIntensity[i_117_][i_118_][i] = (byte) 50;
							}
						}
						if (gameobjectdefinition.isSolid && collisionmap != null) {
							collisionmap
									.method217(i, i_120_, i_118_, i_116_, (byte) 1, gameobjectdefinition.isWalkable);
						}
					} else if (i_116_ == 2) {
						int i_133_ = i_120_ + 1 & 0x3;
						Renderable renderable;
						Renderable renderable_134_;
						if (gameobjectdefinition.anInt286 == -1 && gameobjectdefinition.anIntArray264 == null) {
							renderable = gameobjectdefinition.method238(2, 4 + i_120_, i_121_, i_122_, i_123_, i_124_,
									-1);
							renderable_134_ = gameobjectdefinition.method238(2, i_133_, i_121_, i_122_, i_123_, i_124_,
									-1);
						} else {
							renderable = new GameObject(i_119_, 4 + i_120_, 2, i_122_, (byte) 7, i_123_, i_121_,
									i_124_, gameobjectdefinition.anInt286, true);
							renderable_134_ = new GameObject(i_119_, i_133_, 2, i_122_, (byte) 7, i_123_, i_121_,
									i_124_, gameobjectdefinition.anInt286, true);
						}
						scene.method504(Region.anIntArray506[i_120_], renderable, true, i_126_, i, b, i_118_,
								renderable_134_, i_125_, Region.anIntArray506[i_133_], i_117_);
						if (gameobjectdefinition.aBoolean269) {
							if (i_120_ == 0) {
								tileCullingBitsets[i_117_][i_118_][i] |= 0x249;
								tileCullingBitsets[i_117_][i_118_][i + 1] |= 0x492;
							} else if (i_120_ == 1) {
								tileCullingBitsets[i_117_][i_118_][i + 1] |= 0x492;
								tileCullingBitsets[i_117_][i_118_ + 1][i] |= 0x249;
							} else if (i_120_ == 2) {
								tileCullingBitsets[i_117_][i_118_ + 1][i] |= 0x249;
								tileCullingBitsets[i_117_][i_118_][i] |= 0x492;
							} else if (i_120_ == 3) {
								tileCullingBitsets[i_117_][i_118_][i] |= 0x492;
								tileCullingBitsets[i_117_][i_118_][i] |= 0x249;
							}
						}
						if (gameobjectdefinition.isSolid && collisionmap != null) {
							collisionmap
									.method217(i, i_120_, i_118_, i_116_, (byte) 1, gameobjectdefinition.isWalkable);
						}
						if (gameobjectdefinition.anInt280 != 16) {
							scene.method512(i, 441, gameobjectdefinition.anInt280, i_118_, i_117_);
						}
					} else if (i_116_ == 3) {
						Renderable renderable;
						if (gameobjectdefinition.anInt286 == -1 && gameobjectdefinition.anIntArray264 == null) {
							renderable = gameobjectdefinition.method238(3, i_120_, i_121_, i_122_, i_123_, i_124_, -1);
						} else {
							renderable = new GameObject(i_119_, i_120_, 3, i_122_, (byte) 7, i_123_, i_121_, i_124_,
									gameobjectdefinition.anInt286, true);
						}
						scene.method504(Region.anIntArray494[i_120_], renderable, true, i_126_, i, b, i_118_, null,
								i_125_, 0, i_117_);
						if (gameobjectdefinition.aBoolean284) {
							if (i_120_ == 0) {
								tileShadowIntensity[i_117_][i_118_][i + 1] = (byte) 50;
							} else if (i_120_ == 1) {
								tileShadowIntensity[i_117_][i_118_ + 1][i + 1] = (byte) 50;
							} else if (i_120_ == 2) {
								tileShadowIntensity[i_117_][i_118_ + 1][i] = (byte) 50;
							} else if (i_120_ == 3) {
								tileShadowIntensity[i_117_][i_118_][i] = (byte) 50;
							}
						}
						if (gameobjectdefinition.isSolid && collisionmap != null) {
							collisionmap
									.method217(i, i_120_, i_118_, i_116_, (byte) 1, gameobjectdefinition.isWalkable);
						}
					} else if (i_116_ == 9) {
						Renderable renderable;
						if (gameobjectdefinition.anInt286 == -1 && gameobjectdefinition.anIntArray264 == null) {
							renderable = gameobjectdefinition.method238(i_116_, i_120_, i_121_, i_122_, i_123_, i_124_,
									-1);
						} else {
							renderable = new GameObject(i_119_, i_120_, i_116_, i_122_, (byte) 7, i_123_, i_121_,
									i_124_, gameobjectdefinition.anInt286, true);
						}
						scene.method506(i_126_, b, i_125_, 1, renderable, 1, i_117_, 0, (byte) 110, i, i_118_);
						if (gameobjectdefinition.isSolid && collisionmap != null) {
							collisionmap.method218(gameobjectdefinition.isWalkable, Region.anInt492,
									gameobjectdefinition.sizeX, gameobjectdefinition.sizeY, i_118_, i, i_120_);
						}
					} else {
						if (gameobjectdefinition.aBoolean267) {
							if (i_120_ == 1) {
								int i_135_ = i_124_;
								i_124_ = i_123_;
								i_123_ = i_122_;
								i_122_ = i_121_;
								i_121_ = i_135_;
							} else if (i_120_ == 2) {
								int i_136_ = i_124_;
								i_124_ = i_122_;
								i_122_ = i_136_;
								i_136_ = i_123_;
								i_123_ = i_121_;
								i_121_ = i_136_;
							} else if (i_120_ == 3) {
								int i_137_ = i_124_;
								i_124_ = i_121_;
								i_121_ = i_122_;
								i_122_ = i_123_;
								i_123_ = i_137_;
							}
						}
						if (i_116_ == 4) {
							Renderable renderable;
							if (gameobjectdefinition.anInt286 == -1 && gameobjectdefinition.anIntArray264 == null) {
								renderable = gameobjectdefinition.method238(4, 0, i_121_, i_122_, i_123_, i_124_, -1);
							} else {
								renderable = new GameObject(i_119_, 0, 4, i_122_, (byte) 7, i_123_, i_121_, i_124_,
										gameobjectdefinition.anInt286, true);
							}
							scene.method505(i_126_, i, i_120_ * 512, -460, i_117_, 0, i_125_, renderable, i_118_, b, 0,
									Region.anIntArray506[i_120_]);
						} else if (i_116_ == 5) {
							int i_138_ = 16;
							int i_139_ = scene.method522(i_117_, i_118_, i);
							if (i_139_ > 0) {
								i_138_ = GameObjectDefinition.getObjectByHash(i_139_ >> 14 & 0x7fff).anInt280;
							}
							Renderable renderable;
							if (gameobjectdefinition.anInt286 == -1 && gameobjectdefinition.anIntArray264 == null) {
								renderable = gameobjectdefinition.method238(4, 0, i_121_, i_122_, i_123_, i_124_, -1);
							} else {
								renderable = new GameObject(i_119_, 0, 4, i_122_, (byte) 7, i_123_, i_121_, i_124_,
										gameobjectdefinition.anInt286, true);
							}
							scene.method505(i_126_, i, i_120_ * 512, -460, i_117_, Region.anIntArray491[i_120_]
									* i_138_, i_125_, renderable, i_118_, b, Region.anIntArray498[i_120_] * i_138_,
									Region.anIntArray506[i_120_]);
						} else if (i_116_ == 6) {
							Renderable renderable;
							if (gameobjectdefinition.anInt286 == -1 && gameobjectdefinition.anIntArray264 == null) {
								renderable = gameobjectdefinition.method238(4, 0, i_121_, i_122_, i_123_, i_124_, -1);
							} else {
								renderable = new GameObject(i_119_, 0, 4, i_122_, (byte) 7, i_123_, i_121_, i_124_,
										gameobjectdefinition.anInt286, true);
							}
							scene.method505(i_126_, i, i_120_, -460, i_117_, 0, i_125_, renderable, i_118_, b, 0, 256);
						} else if (i_116_ == 7) {
							Renderable renderable;
							if (gameobjectdefinition.anInt286 == -1 && gameobjectdefinition.anIntArray264 == null) {
								renderable = gameobjectdefinition.method238(4, 0, i_121_, i_122_, i_123_, i_124_, -1);
							} else {
								renderable = new GameObject(i_119_, 0, 4, i_122_, (byte) 7, i_123_, i_121_, i_124_,
										gameobjectdefinition.anInt286, true);
							}
							scene.method505(i_126_, i, i_120_, -460, i_117_, 0, i_125_, renderable, i_118_, b, 0, 512);
						} else if (i_116_ == 8) {
							Renderable renderable;
							if (gameobjectdefinition.anInt286 == -1 && gameobjectdefinition.anIntArray264 == null) {
								renderable = gameobjectdefinition.method238(4, 0, i_121_, i_122_, i_123_, i_124_, -1);
							} else {
								renderable = new GameObject(i_119_, 0, 4, i_122_, (byte) 7, i_123_, i_121_, i_124_,
										gameobjectdefinition.anInt286, true);
							}
							scene.method505(i_126_, i, i_120_, -460, i_117_, 0, i_125_, renderable, i_118_, b, 0, 768);
						}
					}
				}
			}
		} catch (RuntimeException runtimeexception) {
			SignLink.reportError("16863, " + i + ", " + scene + ", " + collisionmap + ", " + i_116_ + ", " + i_117_
					+ ", " + i_118_ + ", " + i_119_ + ", " + bool + ", " + i_120_ + ", " + runtimeexception.toString());
			throw new RuntimeException();
		}
	}

	private static final int method458(int i, int i_140_, int i_141_) {
		int i_142_ = i / i_141_;
		int i_143_ = i & i_141_ - 1;
		int i_144_ = i_140_ / i_141_;
		int i_145_ = i_140_ & i_141_ - 1;
		int i_146_ = Region.method468(i_142_, i_144_);
		int i_147_ = Region.method468(i_142_ + 1, i_144_);
		int i_148_ = Region.method468(i_142_, i_144_ + 1);
		int i_149_ = Region.method468(i_142_ + 1, i_144_ + 1);
		int i_150_ = Region.method466(i_146_, i_147_, i_143_, i_141_);
		int i_151_ = Region.method466(i_148_, i_149_, i_143_, i_141_);
		return Region.method466(i_150_, i_151_, i_145_, i_141_);
	}

	private final int getHSLBitset(int i, int i_152_, int i_153_) {
		if (i_153_ > 179) {
			i_152_ /= 2;
		}
		if (i_153_ > 192) {
			i_152_ /= 2;
		}
		if (i_153_ > 217) {
			i_152_ /= 2;
		}
		if (i_153_ > 243) {
			i_152_ /= 2;
		}
		int i_154_ = (i / 4 << 10) + (i_152_ / 32 << 7) + i_153_ / 2;
		return i_154_;
	}

	public static final boolean method460(int i, int i_155_, int i_156_) {
		try {
			GameObjectDefinition gameobjectdefinition = GameObjectDefinition.getObjectByHash(i);
			if (i_156_ != 8) {
				for (int i_157_ = 1; i_157_ > 0; i_157_++) {
					/* empty */
				}
			}
			if (i_155_ == 11) {
				i_155_ = 10;
			}
			if (i_155_ >= 5 && i_155_ <= 8) {
				i_155_ = 4;
			}
			return gameobjectdefinition.method237(i_155_, true);
		} catch (RuntimeException runtimeexception) {
			SignLink.reportError("51637, " + i + ", " + i_155_ + ", " + i_156_ + ", " + runtimeexception.toString());
			throw new RuntimeException();
		}
	}

	public final void method461(int i, int i_158_, CollisionMap[] collisionmaps, int i_159_, int i_160_, int i_161_,
			byte[] bs, int i_162_, int i_163_, int i_164_) {
		try {
			for (int i_165_ = 0; i_165_ < 8; i_165_++) {
				for (int i_166_ = 0; i_166_ < 8; i_166_++) {
					if (i_160_ + i_165_ > 0 && i_160_ + i_165_ < 103 && i_164_ + i_166_ > 0 && i_164_ + i_166_ < 103) {
						collisionmaps[i_163_].flags[i_160_ + i_165_][i_164_ + i_166_] &= ~0x1000000;
					}
				}
			}
			if (i_159_ < 9 || i_159_ > 9) {
				for (int i_167_ = 1; i_167_ > 0; i_167_++) {
					/* empty */
				}
			}
			Buffer buffer = new Buffer(bs);
			for (int i_168_ = 0; i_168_ < 4; i_168_++) {
				for (int i_169_ = 0; i_169_ < 64; i_169_++) {
					for (int i_170_ = 0; i_170_ < 64; i_170_++) {
						if (i_168_ == i && i_169_ >= i_161_ && i_169_ < i_161_ + 8 && i_170_ >= i_162_
								&& i_170_ < i_162_ + 8) {
							method463(i_164_ + TiledUtils.method587(i_170_ & 0x7, i_158_, i_169_ & 0x7), 0, buffer,
									i_160_ + TiledUtils.method586(i_158_, i_170_ & 0x7, i_169_ & 0x7, false), i_163_,
									i_158_, 942, 0);
						} else {
							method463(-1, 0, buffer, -1, 0, 0, 942, 0);
						}
					}
				}
			}
		} catch (RuntimeException runtimeexception) {
			SignLink.reportError("28153, " + i + ", " + i_158_ + ", " + collisionmaps + ", " + i_159_ + ", " + i_160_
					+ ", " + i_161_ + ", " + bs + ", " + i_162_ + ", " + i_163_ + ", " + i_164_ + ", "
					+ runtimeexception.toString());
			throw new RuntimeException();
		}
	}

	public final void method462(byte[] bs, int i, int i_171_, int i_172_, int i_173_, byte b,
			CollisionMap[] collisionmaps) {
		do {
			try {
				for (int i_174_ = 0; i_174_ < 4; i_174_++) {
					for (int i_175_ = 0; i_175_ < 64; i_175_++) {
						for (int i_176_ = 0; i_176_ < 64; i_176_++) {
							if (i_171_ + i_175_ > 0 && i_171_ + i_175_ < 103 && i + i_176_ > 0 && i + i_176_ < 103) {
								collisionmaps[i_174_].flags[i_171_ + i_175_][i + i_176_] &= ~0x1000000;
							}
						}
					}
				}
				Buffer buffer = new Buffer(bs);
				for (int i_177_ = 0; i_177_ < 4; i_177_++) {
					for (int i_178_ = 0; i_178_ < 64; i_178_++) {
						for (int i_179_ = 0; i_179_ < 64; i_179_++) {
							method463(i_179_ + i, i_173_, buffer, i_178_ + i_171_, i_177_, 0, 942, i_172_);
						}
					}
				}
				if (b == 4) {
					break;
				}
				aBoolean497 = !aBoolean497;
			} catch (RuntimeException runtimeexception) {
				SignLink.reportError("41691, " + bs + ", " + i + ", " + i_171_ + ", " + i_172_ + ", " + i_173_ + ", "
						+ b + ", " + collisionmaps + ", " + runtimeexception.toString());
				throw new RuntimeException();
			}
			break;
		} while (false);
	}

	private final void method463(int i, int i_180_, Buffer buffer, int i_181_, int i_182_, int i_183_, int i_184_,
			int i_185_) {
		try {
			i_184_ = 36 / i_184_;
			if (i_181_ >= 0 && i_181_ < 104 && i >= 0 && i < 104) {
				renderRuleFlags[i_182_][i_181_][i] = (byte) 0;
				for (;;) {
					int i_186_ = buffer.getUnsignedByte();
					if (i_186_ == 0) {
						if (i_182_ == 0) {
							vertexHeights[0][i_181_][i] = -Region.method454(932731 + i_181_ + i_185_, 556238 + i
									+ i_180_) * 8;
						} else {
							vertexHeights[i_182_][i_181_][i] = vertexHeights[i_182_ - 1][i_181_][i] - 240;
							break;
						}
						break;
					}
					if (i_186_ == 1) {
						int i_187_ = buffer.getUnsignedByte();
						if (i_187_ == 1) {
							i_187_ = 0;
						}
						if (i_182_ == 0) {
							vertexHeights[0][i_181_][i] = -i_187_ * 8;
						} else {
							vertexHeights[i_182_][i_181_][i] = vertexHeights[i_182_ - 1][i_181_][i] - i_187_ * 8;
							break;
						}
						break;
					}
					if (i_186_ <= 49) {
						overlayFloorIds[i_182_][i_181_][i] = buffer.get();
						overlayClippingPaths[i_182_][i_181_][i] = (byte) ((i_186_ - 2) / 4);
						aByteArrayArrayArray502[i_182_][i_181_][i] = (byte) (i_186_ - 2 + i_183_ & 0x3);
					} else if (i_186_ <= 81) {
						renderRuleFlags[i_182_][i_181_][i] = (byte) (i_186_ - 49);
					} else {
						underlayFloorIds[i_182_][i_181_][i] = (byte) (i_186_ - 81);
					}
				}
			} else {
				for (;;) {
					int i_188_ = buffer.getUnsignedByte();
					if (i_188_ == 0) {
						break;
					}
					if (i_188_ == 1) {
						buffer.getUnsignedByte();
						break;
					}
					if (i_188_ <= 49) {
						buffer.getUnsignedByte();
					}
				}
			}
		} catch (RuntimeException runtimeexception) {
			SignLink.reportError("30203, " + i + ", " + i_180_ + ", " + buffer + ", " + i_181_ + ", " + i_182_ + ", "
					+ i_183_ + ", " + i_184_ + ", " + i_185_ + ", " + runtimeexception.toString());
			throw new RuntimeException();
		}
	}

	public int getVisibilityPlaneFor(int i, int i_189_, int i_190_, int i_191_) {
		try {
			if (i_191_ != 0) {
				return 2;
			}
			if ((renderRuleFlags[i_189_][i_190_][i] & 0x8) != 0) {
				return 0;
			}
			if (i_189_ > 0 && (renderRuleFlags[1][i_190_][i] & 0x2) != 0) {
				return i_189_ - 1;
			}
			return i_189_;
		} catch (RuntimeException runtimeexception) {
			SignLink.reportError("5828, " + i + ", " + i_189_ + ", " + i_190_ + ", " + i_191_ + ", "
					+ runtimeexception.toString());
			throw new RuntimeException();
		}
	}

	public final void method465(CollisionMap[] collisionmaps, Scene scene, int i, int i_192_, int i_193_, boolean bool,
			int i_194_, byte[] bs, int i_195_, int i_196_, int i_197_) {
		try {
			Buffer buffer = new Buffer(bs);
			int i_198_ = -1;
			if (!bool) {
				aBoolean497 = !aBoolean497;
			}
			for (;;) {
				int i_199_ = buffer.getSmartB();
				if (i_199_ == 0) {
					break;
				}
				i_198_ += i_199_;
				int i_200_ = 0;
				for (;;) {
					int i_201_ = buffer.getSmartB();
					if (i_201_ == 0) {
						break;
					}
					i_200_ += i_201_ - 1;
					int i_202_ = i_200_ & 0x3f;
					int i_203_ = i_200_ >> 6 & 0x3f;
					int i_204_ = i_200_ >> 12;
					int i_205_ = buffer.getUnsignedByte();
					int i_206_ = i_205_ >> 2;
					int i_207_ = i_205_ & 0x3;
					if (i_204_ == i && i_203_ >= i_195_ && i_203_ < i_195_ + 8 && i_202_ >= i_193_
							&& i_202_ < i_193_ + 8) {
						GameObjectDefinition gameobjectdefinition = GameObjectDefinition.getObjectByHash(i_198_);
						int i_208_ = i_192_
								+ TiledUtils.method588(i_196_, gameobjectdefinition.sizeY, i_203_ & 0x7, i_202_ & 0x7,
										gameobjectdefinition.sizeX);
						int i_209_ = i_197_
								+ TiledUtils.method589(i_202_ & 0x7, gameobjectdefinition.sizeY, i_196_,
										gameobjectdefinition.sizeX, i_203_ & 0x7);
						if (i_208_ > 0 && i_209_ > 0 && i_208_ < 103 && i_209_ < 103) {
							int i_210_ = i_204_;
							if ((renderRuleFlags[1][i_208_][i_209_] & 0x2) == 2) {
								i_210_--;
							}
							CollisionMap collisionmap = null;
							if (i_210_ >= 0) {
								collisionmap = collisionmaps[i_210_];
							}
							method457(i_209_, scene, collisionmap, i_206_, i_194_, i_208_, i_198_, false, i_207_
									+ i_196_ & 0x3);
						}
					}
				}
			}
		} catch (RuntimeException runtimeexception) {
			SignLink.reportError("1483, " + collisionmaps + ", " + scene + ", " + i + ", " + i_192_ + ", " + i_193_
					+ ", " + bool + ", " + i_194_ + ", " + bs + ", " + i_195_ + ", " + i_196_ + ", " + i_197_ + ", "
					+ runtimeexception.toString());
			throw new RuntimeException();
		}
	}

	private static final int method466(int i, int i_211_, int i_212_, int i_213_) {
		int i_214_ = 65536 - Rasterizer3D.COSINE[i_212_ * 1024 / i_213_] >> 1;
		return (i * (65536 - i_214_) >> 16) + (i_211_ * i_214_ >> 16);
	}

	private final int method467(int i, int i_215_) {
		if (i == -2) {
			return 12345678;
		}
		if (i == -1) {
			if (i_215_ < 0) {
				i_215_ = 0;
			} else if (i_215_ > 127) {
				i_215_ = 127;
			}
			i_215_ = 127 - i_215_;
			return i_215_;
		}
		i_215_ = i_215_ * (i & 0x7f) / 128;
		if (i_215_ < 2) {
			i_215_ = 2;
		} else if (i_215_ > 126) {
			i_215_ = 126;
		}
		return (i & 0xff80) + i_215_;
	}

	private static final int method468(int i, int i_216_) {
		int i_217_ = Region.method452(i - 1, i_216_ - 1) + Region.method452(i + 1, i_216_ - 1)
				+ Region.method452(i - 1, i_216_ + 1) + Region.method452(i + 1, i_216_ + 1);
		int i_218_ = Region.method452(i - 1, i_216_) + Region.method452(i + 1, i_216_)
				+ Region.method452(i, i_216_ - 1) + Region.method452(i, i_216_ + 1);
		int i_219_ = Region.method452(i, i_216_);
		return i_217_ / 16 + i_218_ / 8 + i_219_ / 4;
	}

	private static final int trimHSLLightness(int i, int i_220_) {
		if (i == -1) {
			return 12345678;
		}
		i_220_ = i_220_ * (i & 0x7f) / 128;
		if (i_220_ < 2) {
			i_220_ = 2;
		} else if (i_220_ > 126) {
			i_220_ = 126;
		}
		return (i & 0xff80) + i_220_;
	}

	public static final void method470(Scene scene, int i, int i_221_, int i_222_, int i_223_,
			CollisionMap collisionmap, int[][][] is, int i_224_, int i_225_, int i_226_, byte b) {
		try {
			int i_227_ = is[i_223_][i_224_][i_221_];
			int i_228_ = is[i_223_][i_224_ + 1][i_221_];
			int i_229_ = is[i_223_][i_224_ + 1][i_221_ + 1];
			int i_230_ = is[i_223_][i_224_][i_221_ + 1];
			if (b != 93) {
			}
			int i_231_ = i_227_ + i_228_ + i_229_ + i_230_ >> 2;
			GameObjectDefinition gameobjectdefinition = GameObjectDefinition.getObjectByHash(i_225_);
			int i_232_ = i_224_ + (i_221_ << 7) + (i_225_ << 14) + 1073741824;
			if (!gameobjectdefinition.hasActions) {
				i_232_ += -2147483648;
			}
			byte b_233_ = (byte) ((i << 6) + i_222_);
			if (i_222_ == 22) {
				Renderable renderable;
				if (gameobjectdefinition.anInt286 == -1 && gameobjectdefinition.anIntArray264 == null) {
					renderable = gameobjectdefinition.method238(22, i, i_227_, i_228_, i_229_, i_230_, -1);
				} else {
					renderable = new GameObject(i_225_, i, 22, i_228_, (byte) 7, i_229_, i_227_, i_230_,
							gameobjectdefinition.anInt286, true);
				}
				scene.method502(i_226_, i_231_, i_221_, 68, renderable, b_233_, i_232_, i_224_);
				if (gameobjectdefinition.isSolid && gameobjectdefinition.hasActions) {
					collisionmap.setSolidFlag(i_224_, i_221_);
				}
			} else if (i_222_ == 10 || i_222_ == 11) {
				Renderable renderable;
				if (gameobjectdefinition.anInt286 == -1 && gameobjectdefinition.anIntArray264 == null) {
					renderable = gameobjectdefinition.method238(10, i, i_227_, i_228_, i_229_, i_230_, -1);
				} else {
					renderable = new GameObject(i_225_, i, 10, i_228_, (byte) 7, i_229_, i_227_, i_230_,
							gameobjectdefinition.anInt286, true);
				}
				if (renderable != null) {
					int i_234_ = 0;
					if (i_222_ == 11) {
						i_234_ += 256;
					}
					int i_235_;
					int i_236_;
					if (i == 1 || i == 3) {
						i_235_ = gameobjectdefinition.sizeY;
						i_236_ = gameobjectdefinition.sizeX;
					} else {
						i_235_ = gameobjectdefinition.sizeX;
						i_236_ = gameobjectdefinition.sizeY;
					}
					scene.method506(i_232_, b_233_, i_231_, i_236_, renderable, i_235_, i_226_, i_234_, (byte) 110,
							i_221_, i_224_);
				}
				if (gameobjectdefinition.isSolid) {
					collisionmap.method218(gameobjectdefinition.isWalkable, Region.anInt492,
							gameobjectdefinition.sizeX, gameobjectdefinition.sizeY, i_224_, i_221_, i);
				}
			} else if (i_222_ >= 12) {
				Renderable renderable;
				if (gameobjectdefinition.anInt286 == -1 && gameobjectdefinition.anIntArray264 == null) {
					renderable = gameobjectdefinition.method238(i_222_, i, i_227_, i_228_, i_229_, i_230_, -1);
				} else {
					renderable = new GameObject(i_225_, i, i_222_, i_228_, (byte) 7, i_229_, i_227_, i_230_,
							gameobjectdefinition.anInt286, true);
				}
				scene.method506(i_232_, b_233_, i_231_, 1, renderable, 1, i_226_, 0, (byte) 110, i_221_, i_224_);
				if (gameobjectdefinition.isSolid) {
					collisionmap.method218(gameobjectdefinition.isWalkable, Region.anInt492,
							gameobjectdefinition.sizeX, gameobjectdefinition.sizeY, i_224_, i_221_, i);
				}
			} else if (i_222_ == 0) {
				Renderable renderable;
				if (gameobjectdefinition.anInt286 == -1 && gameobjectdefinition.anIntArray264 == null) {
					renderable = gameobjectdefinition.method238(0, i, i_227_, i_228_, i_229_, i_230_, -1);
				} else {
					renderable = new GameObject(i_225_, i, 0, i_228_, (byte) 7, i_229_, i_227_, i_230_,
							gameobjectdefinition.anInt286, true);
				}
				scene.method504(Region.anIntArray506[i], renderable, true, i_232_, i_221_, b_233_, i_224_, null,
						i_231_, 0, i_226_);
				if (gameobjectdefinition.isSolid) {
					collisionmap.method217(i_221_, i, i_224_, i_222_, (byte) 1, gameobjectdefinition.isWalkable);
				}
			} else if (i_222_ == 1) {
				Renderable renderable;
				if (gameobjectdefinition.anInt286 == -1 && gameobjectdefinition.anIntArray264 == null) {
					renderable = gameobjectdefinition.method238(1, i, i_227_, i_228_, i_229_, i_230_, -1);
				} else {
					renderable = new GameObject(i_225_, i, 1, i_228_, (byte) 7, i_229_, i_227_, i_230_,
							gameobjectdefinition.anInt286, true);
				}
				scene.method504(Region.anIntArray494[i], renderable, true, i_232_, i_221_, b_233_, i_224_, null,
						i_231_, 0, i_226_);
				if (gameobjectdefinition.isSolid) {
					collisionmap.method217(i_221_, i, i_224_, i_222_, (byte) 1, gameobjectdefinition.isWalkable);
				}
			} else if (i_222_ == 2) {
				int i_237_ = i + 1 & 0x3;
				Renderable renderable;
				Renderable renderable_238_;
				if (gameobjectdefinition.anInt286 == -1 && gameobjectdefinition.anIntArray264 == null) {
					renderable = gameobjectdefinition.method238(2, 4 + i, i_227_, i_228_, i_229_, i_230_, -1);
					renderable_238_ = gameobjectdefinition.method238(2, i_237_, i_227_, i_228_, i_229_, i_230_, -1);
				} else {
					renderable = new GameObject(i_225_, 4 + i, 2, i_228_, (byte) 7, i_229_, i_227_, i_230_,
							gameobjectdefinition.anInt286, true);
					renderable_238_ = new GameObject(i_225_, i_237_, 2, i_228_, (byte) 7, i_229_, i_227_, i_230_,
							gameobjectdefinition.anInt286, true);
				}
				scene.method504(Region.anIntArray506[i], renderable, true, i_232_, i_221_, b_233_, i_224_,
						renderable_238_, i_231_, Region.anIntArray506[i_237_], i_226_);
				if (gameobjectdefinition.isSolid) {
					collisionmap.method217(i_221_, i, i_224_, i_222_, (byte) 1, gameobjectdefinition.isWalkable);
				}
			} else if (i_222_ == 3) {
				Renderable renderable;
				if (gameobjectdefinition.anInt286 == -1 && gameobjectdefinition.anIntArray264 == null) {
					renderable = gameobjectdefinition.method238(3, i, i_227_, i_228_, i_229_, i_230_, -1);
				} else {
					renderable = new GameObject(i_225_, i, 3, i_228_, (byte) 7, i_229_, i_227_, i_230_,
							gameobjectdefinition.anInt286, true);
				}
				scene.method504(Region.anIntArray494[i], renderable, true, i_232_, i_221_, b_233_, i_224_, null,
						i_231_, 0, i_226_);
				if (gameobjectdefinition.isSolid) {
					collisionmap.method217(i_221_, i, i_224_, i_222_, (byte) 1, gameobjectdefinition.isWalkable);
				}
			} else if (i_222_ == 9) {
				Renderable renderable;
				if (gameobjectdefinition.anInt286 == -1 && gameobjectdefinition.anIntArray264 == null) {
					renderable = gameobjectdefinition.method238(i_222_, i, i_227_, i_228_, i_229_, i_230_, -1);
				} else {
					renderable = new GameObject(i_225_, i, i_222_, i_228_, (byte) 7, i_229_, i_227_, i_230_,
							gameobjectdefinition.anInt286, true);
				}
				scene.method506(i_232_, b_233_, i_231_, 1, renderable, 1, i_226_, 0, (byte) 110, i_221_, i_224_);
				if (gameobjectdefinition.isSolid) {
					collisionmap.method218(gameobjectdefinition.isWalkable, Region.anInt492,
							gameobjectdefinition.sizeX, gameobjectdefinition.sizeY, i_224_, i_221_, i);
				}
			} else {
				if (gameobjectdefinition.aBoolean267) {
					if (i == 1) {
						int i_239_ = i_230_;
						i_230_ = i_229_;
						i_229_ = i_228_;
						i_228_ = i_227_;
						i_227_ = i_239_;
					} else if (i == 2) {
						int i_240_ = i_230_;
						i_230_ = i_228_;
						i_228_ = i_240_;
						i_240_ = i_229_;
						i_229_ = i_227_;
						i_227_ = i_240_;
					} else if (i == 3) {
						int i_241_ = i_230_;
						i_230_ = i_227_;
						i_227_ = i_228_;
						i_228_ = i_229_;
						i_229_ = i_241_;
					}
				}
				if (i_222_ == 4) {
					Renderable renderable;
					if (gameobjectdefinition.anInt286 == -1 && gameobjectdefinition.anIntArray264 == null) {
						renderable = gameobjectdefinition.method238(4, 0, i_227_, i_228_, i_229_, i_230_, -1);
					} else {
						renderable = new GameObject(i_225_, 0, 4, i_228_, (byte) 7, i_229_, i_227_, i_230_,
								gameobjectdefinition.anInt286, true);
					}
					scene.method505(i_232_, i_221_, i * 512, -460, i_226_, 0, i_231_, renderable, i_224_, b_233_, 0,
							Region.anIntArray506[i]);
				} else if (i_222_ == 5) {
					int i_242_ = 16;
					int i_243_ = scene.method522(i_226_, i_224_, i_221_);
					if (i_243_ > 0) {
						i_242_ = GameObjectDefinition.getObjectByHash(i_243_ >> 14 & 0x7fff).anInt280;
					}
					Renderable renderable;
					if (gameobjectdefinition.anInt286 == -1 && gameobjectdefinition.anIntArray264 == null) {
						renderable = gameobjectdefinition.method238(4, 0, i_227_, i_228_, i_229_, i_230_, -1);
					} else {
						renderable = new GameObject(i_225_, 0, 4, i_228_, (byte) 7, i_229_, i_227_, i_230_,
								gameobjectdefinition.anInt286, true);
					}
					scene.method505(i_232_, i_221_, i * 512, -460, i_226_, Region.anIntArray491[i] * i_242_, i_231_,
							renderable, i_224_, b_233_, Region.anIntArray498[i] * i_242_, Region.anIntArray506[i]);
				} else if (i_222_ == 6) {
					Renderable renderable;
					if (gameobjectdefinition.anInt286 == -1 && gameobjectdefinition.anIntArray264 == null) {
						renderable = gameobjectdefinition.method238(4, 0, i_227_, i_228_, i_229_, i_230_, -1);
					} else {
						renderable = new GameObject(i_225_, 0, 4, i_228_, (byte) 7, i_229_, i_227_, i_230_,
								gameobjectdefinition.anInt286, true);
					}
					scene.method505(i_232_, i_221_, i, -460, i_226_, 0, i_231_, renderable, i_224_, b_233_, 0, 256);
				} else if (i_222_ == 7) {
					Renderable renderable;
					if (gameobjectdefinition.anInt286 == -1 && gameobjectdefinition.anIntArray264 == null) {
						renderable = gameobjectdefinition.method238(4, 0, i_227_, i_228_, i_229_, i_230_, -1);
					} else {
						renderable = new GameObject(i_225_, 0, 4, i_228_, (byte) 7, i_229_, i_227_, i_230_,
								gameobjectdefinition.anInt286, true);
					}
					scene.method505(i_232_, i_221_, i, -460, i_226_, 0, i_231_, renderable, i_224_, b_233_, 0, 512);
				} else if (i_222_ == 8) {
					Renderable renderable;
					if (gameobjectdefinition.anInt286 == -1 && gameobjectdefinition.anIntArray264 == null) {
						renderable = gameobjectdefinition.method238(4, 0, i_227_, i_228_, i_229_, i_230_, -1);
					} else {
						renderable = new GameObject(i_225_, 0, 4, i_228_, (byte) 7, i_229_, i_227_, i_230_,
								gameobjectdefinition.anInt286, true);
					}
					scene.method505(i_232_, i_221_, i, -460, i_226_, 0, i_231_, renderable, i_224_, b_233_, 0, 768);
				}
			}
		} catch (RuntimeException runtimeexception) {
			SignLink.reportError("89985, " + scene + ", " + i + ", " + i_221_ + ", " + i_222_ + ", " + i_223_ + ", "
					+ collisionmap + ", " + is + ", " + i_224_ + ", " + i_225_ + ", " + i_226_ + ", " + b + ", "
					+ runtimeexception.toString());
			throw new RuntimeException();
		}
	}

	public static final boolean method471(int i, byte[] bs, int i_244_, int i_245_) {
		try {
			if (i_245_ < 6 || i_245_ > 6) {
				throw new NullPointerException();
			}
			boolean bool = true;
			Buffer buffer = new Buffer(bs);
			int i_246_ = -1;
			for (;;) {
				int i_247_ = buffer.getSmartB();
				if (i_247_ == 0) {
					break;
				}
				i_246_ += i_247_;
				int i_248_ = 0;
				boolean bool_249_ = false;
				for (;;) {
					if (bool_249_) {
						int i_250_ = buffer.getSmartB();
						if (i_250_ == 0) {
							break;
						}
						buffer.getUnsignedByte();
					} else {
						int i_251_ = buffer.getSmartB();
						if (i_251_ == 0) {
							break;
						}
						i_248_ += i_251_ - 1;
						int i_252_ = i_248_ & 0x3f;
						int i_253_ = i_248_ >> 6 & 0x3f;
						int i_254_ = buffer.getUnsignedByte() >> 2;
						int i_255_ = i_253_ + i;
						int i_256_ = i_252_ + i_244_;
						if (i_255_ > 0 && i_256_ > 0 && i_255_ < 103 && i_256_ < 103) {
							GameObjectDefinition gameobjectdefinition = GameObjectDefinition.getObjectByHash(i_246_);
							if (i_254_ != 22 || !Region.lowMemory || gameobjectdefinition.hasActions
									|| gameobjectdefinition.aBoolean241) {
								bool &= gameobjectdefinition.method239(true);
								bool_249_ = true;
							}
						}
					}
				}
			}
			return bool;
		} catch (RuntimeException runtimeexception) {
			SignLink.reportError("14134, " + i + ", " + bs + ", " + i_244_ + ", " + i_245_ + ", "
					+ runtimeexception.toString());
			throw new RuntimeException();
		}
	}

	public final void method472(int i, CollisionMap[] collisionmaps, int i_257_, int i_258_, Scene scene, byte[] bs) {
		try {
			if (i_258_ >= 7 && i_258_ <= 7) {
				Buffer buffer = new Buffer(bs);
				int i_259_ = -1;
				for (;;) {
					int i_260_ = buffer.getSmartB();
					if (i_260_ == 0) {
						break;
					}
					i_259_ += i_260_;
					int i_261_ = 0;
					for (;;) {
						int i_262_ = buffer.getSmartB();
						if (i_262_ == 0) {
							break;
						}
						i_261_ += i_262_ - 1;
						int i_263_ = i_261_ & 0x3f;
						int i_264_ = i_261_ >> 6 & 0x3f;
						int i_265_ = i_261_ >> 12;
						int i_266_ = buffer.getUnsignedByte();
						int i_267_ = i_266_ >> 2;
						int i_268_ = i_266_ & 0x3;
						int i_269_ = i_264_ + i;
						int i_270_ = i_263_ + i_257_;
						if (i_269_ > 0 && i_270_ > 0 && i_269_ < 103 && i_270_ < 103) {
							int i_271_ = i_265_;
							if ((renderRuleFlags[1][i_269_][i_270_] & 0x2) == 2) {
								i_271_--;
							}
							CollisionMap collisionmap = null;
							if (i_271_ >= 0) {
								collisionmap = collisionmaps[i_271_];
							}
							method457(i_270_, scene, collisionmap, i_267_, i_265_, i_269_, i_259_, false, i_268_);
						}
					}
				}
			}
		} catch (RuntimeException runtimeexception) {
			SignLink.reportError("18363, " + i + ", " + collisionmaps + ", " + i_257_ + ", " + i_258_ + ", " + scene
					+ ", " + bs + ", " + runtimeexception.toString());
			throw new RuntimeException();
		}
	}
}
