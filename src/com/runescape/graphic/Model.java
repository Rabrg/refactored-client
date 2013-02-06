package com.runescape.graphic;

import com.runescape.anim.Animation;
import com.runescape.cache.requester.Requester;
import com.runescape.net.Buffer;
import com.runescape.renderable.Renderable;
import com.runescape.util.SignLink;

public class Model extends Renderable
{
	private boolean aBoolean1608 = false;
	private int anInt1610 = 1;
	private boolean aBoolean1611 = true;
	public static Model aModel1614 = new Model(true);
	private static int[] anIntArray1615 = new int[2000];
	private static int[] anIntArray1616 = new int[2000];
	private static int[] anIntArray1617 = new int[2000];
	private static int[] anIntArray1618 = new int[2000];
	public int vertexCount;
	public int[] verticesX;
	public int[] verticesY;
	public int[] verticesZ;
	public int triangleCount;
	public int[] trianglePointsX;
	public int[] trianglePointsY;
	public int[] trianglePointsZ;
	public int[] anIntArray1627;
	public int[] anIntArray1628;
	public int[] anIntArray1629;
	public int[] texturePoints;
	public int[] trianglePriorities;
	public int[] triangleAlphaValues;
	public int[] triangleColorValues;
	public int anInt1634;
	public int texturedTriangleCount;
	public int[] texturedTrianglePointsX;
	public int[] texturedTrianglePointsY;
	public int[] texturedTrianglePointsZ;
	public int anInt1639;
	public int anInt1640;
	public int anInt1641;
	public int anInt1642;
	public int anInt1643;
	public int anInt1644;
	public int anInt1645;
	public int anInt1646;
	public int anInt1647;
	public int[] vertexSkins;
	public int[] triangleSkinValues;
	public int[][] vectorSkin;
	public int[][] triangleSkin;
	public boolean oneSquareModel = false;
	public VertexNormal[] aVertexNormalArray1653;
	static ModelHeader[] modelHeaders;
	static Requester requester;
	static boolean[] aBooleanArray1656 = new boolean[4096];
	static boolean[] aBooleanArray1657 = new boolean[4096];
	static int[] anIntArray1658 = new int[4096];
	static int[] anIntArray1659 = new int[4096];
	static int[] anIntArray1660 = new int[4096];
	static int[] anIntArray1661 = new int[4096];
	static int[] anIntArray1662 = new int[4096];
	static int[] anIntArray1663 = new int[4096];
	static int[] anIntArray1664 = new int[1500];
	static int[][] anIntArrayArray1665 = new int[1500][512];
	static int[] anIntArray1666 = new int[12];
	static int[][] anIntArrayArray1667 = new int[12][2000];
	static int[] anIntArray1668 = new int[2000];
	static int[] anIntArray1669 = new int[2000];
	static int[] anIntArray1670 = new int[12];
	static int[] anIntArray1671 = new int[10];
	static int[] anIntArray1672 = new int[10];
	static int[] anIntArray1673 = new int[10];
	static int vertexXModifier;
	static int vertexYModifier;
	static int vertexZModifier;
	public static boolean aBoolean1677;
	public static int anInt1678;
	public static int anInt1679;
	public static int anInt1680;
	public static int[] anIntArray1681 = new int[1000];
	public static int[] anIntArray1682 = Rasterizer3D.SINE;
	public static int[] anIntArray1683 = Rasterizer3D.COSINE;
	static int[] anIntArray1684 = Rasterizer3D.getRgbLookupTableId;
	static int[] anIntArray1685 = Rasterizer3D.anIntArray1489;

	public static void reset() {
		Model.modelHeaders = null;
		Model.aBooleanArray1656 = null;
		Model.aBooleanArray1657 = null;
		Model.anIntArray1658 = null;
		Model.anIntArray1659 = null;
		Model.anIntArray1660 = null;
		Model.anIntArray1661 = null;
		Model.anIntArray1662 = null;
		Model.anIntArray1663 = null;
		Model.anIntArray1664 = null;
		Model.anIntArrayArray1665 = null;
		Model.anIntArray1666 = null;
		Model.anIntArrayArray1667 = null;
		Model.anIntArray1668 = null;
		Model.anIntArray1669 = null;
		Model.anIntArray1670 = null;
		Model.anIntArray1682 = null;
		Model.anIntArray1683 = null;
		Model.anIntArray1684 = null;
		Model.anIntArray1685 = null;
	}

	public static void init(int modelCount, Requester requester) {
		Model.modelHeaders = new ModelHeader[modelCount];
		Model.requester = requester;
	}

	public static void loadModelHeader(byte[] modelData, int modelId) {
		if (modelData == null) {
			ModelHeader modelheader = Model.modelHeaders[modelId] = new ModelHeader();
			modelheader.vertexCount = 0;
			modelheader.triangleCount = 0;
			modelheader.texturedTriangleCount = 0;
		} else {
			Buffer buffer = new Buffer(modelData);
			buffer.offset = modelData.length - 18;
			ModelHeader modelheader = Model.modelHeaders[modelId] = new ModelHeader();
			modelheader.modelData = modelData;
			modelheader.vertexCount = buffer.getUnsignedLEShort();
			modelheader.triangleCount = buffer.getUnsignedLEShort();
			modelheader.texturedTriangleCount = buffer.getUnsignedByte();
			int useTextures = buffer.getUnsignedByte();
			int useTrianglePriority = buffer.getUnsignedByte();
			int useTransparency = buffer.getUnsignedByte();
			int useTriangleSkinning = buffer.getUnsignedByte();
			int useVertexSkinning = buffer.getUnsignedByte();
			int xDataLength = buffer.getUnsignedLEShort();
			int yDataLength = buffer.getUnsignedLEShort();
			int zDataLength = buffer.getUnsignedLEShort();
			int triangleDataLength = buffer.getUnsignedLEShort();
			int offset = 0;
			modelheader.vertexDirectionOffset = offset;
			offset += modelheader.vertexCount;
			modelheader.triangleTypeOffset = offset;
			offset += modelheader.triangleCount;
			modelheader.trianglePriorityOffset = offset;
			if (useTrianglePriority == 255) {
				offset += modelheader.triangleCount;
			} else {
				modelheader.trianglePriorityOffset = -useTrianglePriority - 1;
			}
			modelheader.triangleSkinOffset = offset;
			if (useTriangleSkinning == 1) {
				offset += modelheader.triangleCount;
			} else {
				modelheader.triangleSkinOffset = -1;
			}
			modelheader.texturePointerOffset = offset;
			if (useTextures == 1) {
				offset += modelheader.triangleCount;
			} else {
				modelheader.texturePointerOffset = -1;
			}
			modelheader.vertexSkinOffset = offset;
			if (useVertexSkinning == 1) {
				offset += modelheader.vertexCount;
			} else {
				modelheader.vertexSkinOffset = -1;
			}
			modelheader.triangleAlphaOffset = offset;
			if (useTransparency == 1) {
				offset += modelheader.triangleCount;
			} else {
				modelheader.triangleAlphaOffset = -1;
			}
			modelheader.triangleDataOffset = offset;
			offset += triangleDataLength;
			modelheader.colorDataOffset = offset;
			offset += modelheader.triangleCount * 2;
			modelheader.uvMapTriangleOffset = offset;
			offset += modelheader.texturedTriangleCount * 6;
			modelheader.xDataOffset = offset;
			offset += xDataLength;
			modelheader.yDataOffset = offset;
			offset += yDataLength;
			modelheader.zDataOffset = offset;
			offset += zDataLength;
		}
	}

	public static void method409(int i, int i_11_) {
		do {
			try {
				Model.modelHeaders[i_11_] = null;
				if (i > 0) {
					break;
				}
			} catch (RuntimeException runtimeexception) {
				SignLink.reportError("72035, " + i + ", " + i_11_ + ", " + runtimeexception.toString());
				throw new RuntimeException();
			}
			break;
		} while (false);
	}

	public static Model getModel(int i_12_) {
		if (Model.modelHeaders == null) {
			return null;
		}
		ModelHeader modelheader = Model.modelHeaders[i_12_];
		if (modelheader == null) {
			Model.requester.request(i_12_);
			return null;
		}
		return new Model(i_12_);
	}

	public static boolean isCached(int i) {
		if (Model.modelHeaders == null) {
			return false;
		}
		ModelHeader modelheader = Model.modelHeaders[i];
		if (modelheader == null) {
			Model.requester.request(i);
			return false;
		}
		return true;
	}

	private Model(boolean bool)
	{
		do {
			try {
				if (bool) {
					break;
				}
				aBoolean1611 = !aBoolean1611;
			} catch (RuntimeException runtimeexception) {
				SignLink.reportError("59290, " + bool + ", " + runtimeexception.toString());
				throw new RuntimeException();
			}
			break;
		} while (false);
	}

	private Model(int modelId)
	{
		ModelHeader modelheader = Model.modelHeaders[modelId];
		vertexCount = modelheader.vertexCount;
		triangleCount = modelheader.triangleCount;
		texturedTriangleCount = modelheader.texturedTriangleCount;
		verticesX = new int[vertexCount];
		verticesY = new int[vertexCount];
		verticesZ = new int[vertexCount];
		trianglePointsX = new int[triangleCount];
		trianglePointsY = new int[triangleCount];
		trianglePointsZ = new int[triangleCount];
		texturedTrianglePointsX = new int[texturedTriangleCount];
		texturedTrianglePointsY = new int[texturedTriangleCount];
		texturedTrianglePointsZ = new int[texturedTriangleCount];
		if (modelheader.vertexSkinOffset >= 0) {
			vertexSkins = new int[vertexCount];
		}
		if (modelheader.texturePointerOffset >= 0) {
			texturePoints = new int[triangleCount];
		}
		if (modelheader.trianglePriorityOffset >= 0) {
			trianglePriorities = new int[triangleCount];
		} else {
			anInt1634 = -modelheader.trianglePriorityOffset - 1;
		}
		if (modelheader.triangleAlphaOffset >= 0) {
			triangleAlphaValues = new int[triangleCount];
		}
		if (modelheader.triangleSkinOffset >= 0) {
			triangleSkinValues = new int[triangleCount];
		}
		triangleColorValues = new int[triangleCount];
		Buffer vertexDirectionOffsetBuffer = new Buffer(modelheader.modelData);
		vertexDirectionOffsetBuffer.offset = modelheader.vertexDirectionOffset;
		Buffer xDataOffsetBuffer = new Buffer(modelheader.modelData);
		xDataOffsetBuffer.offset = modelheader.xDataOffset;
		Buffer yDataOffsetBuffer = new Buffer(modelheader.modelData);
		yDataOffsetBuffer.offset = modelheader.yDataOffset;
		Buffer zDataOffsetBuffer = new Buffer(modelheader.modelData);
		zDataOffsetBuffer.offset = modelheader.zDataOffset;
		Buffer vertexSkinOffsetBuffer = new Buffer(modelheader.modelData);
		vertexSkinOffsetBuffer.offset = modelheader.vertexSkinOffset;
		int baseOffsetX = 0;
		int baseOffsetY = 0;
		int baseOffsetZ = 0;
		for (int vertex = 0; vertex < vertexCount; vertex++) {
			int flag = vertexDirectionOffsetBuffer.getUnsignedByte();
			int currentOffsetX = 0;
			if ((flag & 0x1) != 0) {
				currentOffsetX = xDataOffsetBuffer.getSmartA();
			}
			int currentOffsetY = 0;
			if ((flag & 0x2) != 0) {
				currentOffsetY = yDataOffsetBuffer.getSmartA();
			}
			int currentOffsetZ = 0;
			if ((flag & 0x4) != 0) {
				currentOffsetZ = zDataOffsetBuffer.getSmartA();
			}
			verticesX[vertex] = baseOffsetX + currentOffsetX;
			verticesY[vertex] = baseOffsetY + currentOffsetY;
			verticesZ[vertex] = baseOffsetZ + currentOffsetZ;
			baseOffsetX = verticesX[vertex];
			baseOffsetY = verticesY[vertex];
			baseOffsetZ = verticesZ[vertex];
			if (vertexSkins != null) {
				vertexSkins[vertex] = vertexSkinOffsetBuffer.getUnsignedByte();
			}
		}
		vertexDirectionOffsetBuffer.offset = modelheader.colorDataOffset;
		xDataOffsetBuffer.offset = modelheader.texturePointerOffset;
		yDataOffsetBuffer.offset = modelheader.trianglePriorityOffset;
		zDataOffsetBuffer.offset = modelheader.triangleAlphaOffset;
		vertexSkinOffsetBuffer.offset = modelheader.triangleSkinOffset;
		for (int triangle = 0; triangle < triangleCount; triangle++) {
			triangleColorValues[triangle] = vertexDirectionOffsetBuffer.getUnsignedLEShort();
			if (texturePoints != null) {
				texturePoints[triangle] = xDataOffsetBuffer.getUnsignedByte();
			}
			if (trianglePriorities != null) {
				trianglePriorities[triangle] = yDataOffsetBuffer.getUnsignedByte();
			}
			if (triangleAlphaValues != null) {
				triangleAlphaValues[triangle] = zDataOffsetBuffer.getUnsignedByte();
			}
			if (triangleSkinValues != null) {
				triangleSkinValues[triangle] = vertexSkinOffsetBuffer.getUnsignedByte();
			}
		}
		vertexDirectionOffsetBuffer.offset = modelheader.triangleDataOffset;
		xDataOffsetBuffer.offset = modelheader.triangleTypeOffset;
		int trianglePointOffsetX = 0;
		int trianglePointOffsetY = 0;
		int trianglePointOffsetZ = 0;
		int offset = 0;
		for (int triangle = 0; triangle < triangleCount; triangle++) {
			int type = xDataOffsetBuffer.getUnsignedByte();
			if (type == 1) {
				trianglePointOffsetX = vertexDirectionOffsetBuffer.getSmartA() + offset;
				offset = trianglePointOffsetX;
				trianglePointOffsetY = vertexDirectionOffsetBuffer.getSmartA() + offset;
				offset = trianglePointOffsetY;
				trianglePointOffsetZ = vertexDirectionOffsetBuffer.getSmartA() + offset;
				offset = trianglePointOffsetZ;
				trianglePointsX[triangle] = trianglePointOffsetX;
				trianglePointsY[triangle] = trianglePointOffsetY;
				trianglePointsZ[triangle] = trianglePointOffsetZ;
			}
			if (type == 2) {
				trianglePointOffsetY = trianglePointOffsetZ;
				trianglePointOffsetZ = vertexDirectionOffsetBuffer.getSmartA() + offset;
				offset = trianglePointOffsetZ;
				trianglePointsX[triangle] = trianglePointOffsetX;
				trianglePointsY[triangle] = trianglePointOffsetY;
				trianglePointsZ[triangle] = trianglePointOffsetZ;
			}
			if (type == 3) {
				trianglePointOffsetX = trianglePointOffsetZ;
				trianglePointOffsetZ = vertexDirectionOffsetBuffer.getSmartA() + offset;
				offset = trianglePointOffsetZ;
				trianglePointsX[triangle] = trianglePointOffsetX;
				trianglePointsY[triangle] = trianglePointOffsetY;
				trianglePointsZ[triangle] = trianglePointOffsetZ;
			}
			if (type == 4) {
				int oldTrianglePointOffsetX = trianglePointOffsetX;
				trianglePointOffsetX = trianglePointOffsetY;
				trianglePointOffsetY = oldTrianglePointOffsetX;
				trianglePointOffsetZ = vertexDirectionOffsetBuffer.getSmartA() + offset;
				offset = trianglePointOffsetZ;
				trianglePointsX[triangle] = trianglePointOffsetX;
				trianglePointsY[triangle] = trianglePointOffsetY;
				trianglePointsZ[triangle] = trianglePointOffsetZ;
			}
		}
		vertexDirectionOffsetBuffer.offset = modelheader.uvMapTriangleOffset;
		for (int triangle = 0; triangle < texturedTriangleCount; triangle++) {
			texturedTrianglePointsX[triangle] = vertexDirectionOffsetBuffer.getUnsignedLEShort();
			texturedTrianglePointsY[triangle] = vertexDirectionOffsetBuffer.getUnsignedLEShort();
			texturedTrianglePointsZ[triangle] = vertexDirectionOffsetBuffer.getUnsignedLEShort();
		}
	}

	public Model(int i, Model[] models)
	{
		boolean bool = false;
		boolean bool_37_ = false;
		boolean bool_38_ = false;
		boolean bool_39_ = false;
		vertexCount = 0;
		triangleCount = 0;
		texturedTriangleCount = 0;
		anInt1634 = -1;
		for (int i_40_ = 0; i_40_ < i; i_40_++) {
			Model model_41_ = models[i_40_];
			if (model_41_ != null) {
				vertexCount += model_41_.vertexCount;
				triangleCount += model_41_.triangleCount;
				texturedTriangleCount += model_41_.texturedTriangleCount;
				bool = bool | model_41_.texturePoints != null;
				if (model_41_.trianglePriorities != null) {
					bool_37_ = true;
				} else {
					if (anInt1634 == -1) {
						anInt1634 = model_41_.anInt1634;
					}
					if (anInt1634 != model_41_.anInt1634) {
						bool_37_ = true;
					}
				}
				bool_38_ = bool_38_ | model_41_.triangleAlphaValues != null;
				bool_39_ = bool_39_ | model_41_.triangleSkinValues != null;
			}
		}
		verticesX = new int[vertexCount];
		verticesY = new int[vertexCount];
		verticesZ = new int[vertexCount];
		vertexSkins = new int[vertexCount];
		trianglePointsX = new int[triangleCount];
		trianglePointsY = new int[triangleCount];
		trianglePointsZ = new int[triangleCount];
		texturedTrianglePointsX = new int[texturedTriangleCount];
		texturedTrianglePointsY = new int[texturedTriangleCount];
		texturedTrianglePointsZ = new int[texturedTriangleCount];
		if (bool) {
			texturePoints = new int[triangleCount];
		}
		if (bool_37_) {
			trianglePriorities = new int[triangleCount];
		}
		if (bool_38_) {
			triangleAlphaValues = new int[triangleCount];
		}
		if (bool_39_) {
			triangleSkinValues = new int[triangleCount];
		}
		triangleColorValues = new int[triangleCount];
		vertexCount = 0;
		triangleCount = 0;
		texturedTriangleCount = 0;
		int i_42_ = 0;
		for (int i_43_ = 0; i_43_ < i; i_43_++) {
			Model model_44_ = models[i_43_];
			if (model_44_ != null) {
				for (int i_45_ = 0; i_45_ < model_44_.triangleCount; i_45_++) {
					if (bool) {
						if (model_44_.texturePoints == null) {
							texturePoints[triangleCount] = 0;
						} else {
							int i_46_ = model_44_.texturePoints[i_45_];
							if ((i_46_ & 0x2) == 2) {
								i_46_ += i_42_ << 2;
							}
							texturePoints[triangleCount] = i_46_;
						}
					}
					if (bool_37_) {
						if (model_44_.trianglePriorities == null) {
							trianglePriorities[triangleCount] = model_44_.anInt1634;
						} else {
							trianglePriorities[triangleCount] = model_44_.trianglePriorities[i_45_];
						}
					}
					if (bool_38_) {
						if (model_44_.triangleAlphaValues == null) {
							triangleAlphaValues[triangleCount] = 0;
						} else {
							triangleAlphaValues[triangleCount] = model_44_.triangleAlphaValues[i_45_];
						}
					}
					if (bool_39_ && model_44_.triangleSkinValues != null) {
						triangleSkinValues[triangleCount] = model_44_.triangleSkinValues[i_45_];
					}
					triangleColorValues[triangleCount] = model_44_.triangleColorValues[i_45_];
					trianglePointsX[triangleCount] = method413(model_44_, model_44_.trianglePointsX[i_45_]);
					trianglePointsY[triangleCount] = method413(model_44_, model_44_.trianglePointsY[i_45_]);
					trianglePointsZ[triangleCount] = method413(model_44_, model_44_.trianglePointsZ[i_45_]);
					triangleCount++;
				}
				for (int i_47_ = 0; i_47_ < model_44_.texturedTriangleCount; i_47_++) {
					texturedTrianglePointsX[texturedTriangleCount] = method413(model_44_, model_44_.texturedTrianglePointsX[i_47_]);
					texturedTrianglePointsY[texturedTriangleCount] = method413(model_44_, model_44_.texturedTrianglePointsY[i_47_]);
					texturedTrianglePointsZ[texturedTriangleCount] = method413(model_44_, model_44_.texturedTrianglePointsZ[i_47_]);
					texturedTriangleCount++;
				}
				i_42_ += model_44_.texturedTriangleCount;
			}
		}
	}

	public Model(int i, int i_48_, boolean bool, Model[] models)
	{
		try {
			boolean bool_49_ = false;
			boolean bool_50_ = false;
			boolean bool_51_ = false;
			boolean bool_52_ = false;
			vertexCount = 0;
			triangleCount = 0;
			texturedTriangleCount = 0;
			anInt1634 = -1;
			for (int i_53_ = 0; i_53_ < i; i_53_++) {
				Model model_54_ = models[i_53_];
				if (model_54_ != null) {
					vertexCount += model_54_.vertexCount;
					triangleCount += model_54_.triangleCount;
					texturedTriangleCount += model_54_.texturedTriangleCount;
					bool_49_ = bool_49_ | model_54_.texturePoints != null;
					if (model_54_.trianglePriorities != null) {
						bool_50_ = true;
					} else {
						if (anInt1634 == -1) {
							anInt1634 = model_54_.anInt1634;
						}
						if (anInt1634 != model_54_.anInt1634) {
							bool_50_ = true;
						}
					}
					bool_51_ = bool_51_ | model_54_.triangleAlphaValues != null;
					bool_52_ = bool_52_ | model_54_.triangleColorValues != null;
				}
			}
			verticesX = new int[vertexCount];
			verticesY = new int[vertexCount];
			verticesZ = new int[vertexCount];
			trianglePointsX = new int[triangleCount];
			trianglePointsY = new int[triangleCount];
			trianglePointsZ = new int[triangleCount];
			anIntArray1627 = new int[triangleCount];
			anIntArray1628 = new int[triangleCount];
			anIntArray1629 = new int[triangleCount];
			texturedTrianglePointsX = new int[texturedTriangleCount];
			texturedTrianglePointsY = new int[texturedTriangleCount];
			texturedTrianglePointsZ = new int[texturedTriangleCount];
			if (i_48_ >= 0) {
				for (int i_55_ = 1; i_55_ > 0; i_55_++) {
					/* empty */
				}
			}
			if (bool_49_) {
				texturePoints = new int[triangleCount];
			}
			if (bool_50_) {
				trianglePriorities = new int[triangleCount];
			}
			if (bool_51_) {
				triangleAlphaValues = new int[triangleCount];
			}
			if (bool_52_) {
				triangleColorValues = new int[triangleCount];
			}
			vertexCount = 0;
			triangleCount = 0;
			texturedTriangleCount = 0;
			int i_56_ = 0;
			for (int i_57_ = 0; i_57_ < i; i_57_++) {
				Model model_58_ = models[i_57_];
				if (model_58_ != null) {
					int i_59_ = vertexCount;
					for (int i_60_ = 0; i_60_ < model_58_.vertexCount; i_60_++) {
						verticesX[vertexCount] = model_58_.verticesX[i_60_];
						verticesY[vertexCount] = model_58_.verticesY[i_60_];
						verticesZ[vertexCount] = model_58_.verticesZ[i_60_];
						vertexCount++;
					}
					for (int i_61_ = 0; i_61_ < model_58_.triangleCount; i_61_++) {
						trianglePointsX[triangleCount] = model_58_.trianglePointsX[i_61_] + i_59_;
						trianglePointsY[triangleCount] = model_58_.trianglePointsY[i_61_] + i_59_;
						trianglePointsZ[triangleCount] = model_58_.trianglePointsZ[i_61_] + i_59_;
						anIntArray1627[triangleCount] = model_58_.anIntArray1627[i_61_];
						anIntArray1628[triangleCount] = model_58_.anIntArray1628[i_61_];
						anIntArray1629[triangleCount] = model_58_.anIntArray1629[i_61_];
						if (bool_49_) {
							if (model_58_.texturePoints == null) {
								texturePoints[triangleCount] = 0;
							} else {
								int i_62_ = model_58_.texturePoints[i_61_];
								if ((i_62_ & 0x2) == 2) {
									i_62_ += i_56_ << 2;
								}
								texturePoints[triangleCount] = i_62_;
							}
						}
						if (bool_50_) {
							if (model_58_.trianglePriorities == null) {
								trianglePriorities[triangleCount] = model_58_.anInt1634;
							} else {
								trianglePriorities[triangleCount] = model_58_.trianglePriorities[i_61_];
							}
						}
						if (bool_51_) {
							if (model_58_.triangleAlphaValues == null) {
								triangleAlphaValues[triangleCount] = 0;
							} else {
								triangleAlphaValues[triangleCount] = model_58_.triangleAlphaValues[i_61_];
							}
						}
						if (bool_52_ && model_58_.triangleColorValues != null) {
							triangleColorValues[triangleCount] = model_58_.triangleColorValues[i_61_];
						}
						triangleCount++;
					}
					for (int i_63_ = 0; i_63_ < model_58_.texturedTriangleCount; i_63_++) {
						texturedTrianglePointsX[texturedTriangleCount] = model_58_.texturedTrianglePointsX[i_63_] + i_59_;
						texturedTrianglePointsY[texturedTriangleCount] = model_58_.texturedTrianglePointsY[i_63_] + i_59_;
						texturedTrianglePointsZ[texturedTriangleCount] = model_58_.texturedTrianglePointsZ[i_63_] + i_59_;
						texturedTriangleCount++;
					}
					i_56_ += model_58_.texturedTriangleCount;
				}
			}
			calculateDiagonals(false);
		} catch (RuntimeException runtimeexception) {
			SignLink.reportError("65900, " + i + ", " + i_48_ + ", " + bool + ", " + models + ", " + runtimeexception.toString());
			throw new RuntimeException();
		}
	}

	public Model(boolean bool, boolean bool_64_, boolean bool_65_, Model model)
	{
		vertexCount = model.vertexCount;
		triangleCount = model.triangleCount;
		texturedTriangleCount = model.texturedTriangleCount;
		if (bool_65_) {
			verticesX = model.verticesX;
			verticesY = model.verticesY;
			verticesZ = model.verticesZ;
		} else {
			verticesX = new int[vertexCount];
			verticesY = new int[vertexCount];
			verticesZ = new int[vertexCount];
			for (int i_67_ = 0; i_67_ < vertexCount; i_67_++) {
				verticesX[i_67_] = model.verticesX[i_67_];
				verticesY[i_67_] = model.verticesY[i_67_];
				verticesZ[i_67_] = model.verticesZ[i_67_];
			}
		}
		if (bool) {
			triangleColorValues = model.triangleColorValues;
		} else {
			triangleColorValues = new int[triangleCount];
			for (int i_68_ = 0; i_68_ < triangleCount; i_68_++) {
				triangleColorValues[i_68_] = model.triangleColorValues[i_68_];
			}
		}
		if (bool_64_) {
			triangleAlphaValues = model.triangleAlphaValues;
		} else {
			triangleAlphaValues = new int[triangleCount];
			if (model.triangleAlphaValues == null) {
				for (int i_69_ = 0; i_69_ < triangleCount; i_69_++) {
					triangleAlphaValues[i_69_] = 0;
				}
			} else {
				for (int i_70_ = 0; i_70_ < triangleCount; i_70_++) {
					triangleAlphaValues[i_70_] = model.triangleAlphaValues[i_70_];
				}
			}
		}
		vertexSkins = model.vertexSkins;
		triangleSkinValues = model.triangleSkinValues;
		texturePoints = model.texturePoints;
		trianglePointsX = model.trianglePointsX;
		trianglePointsY = model.trianglePointsY;
		trianglePointsZ = model.trianglePointsZ;
		trianglePriorities = model.trianglePriorities;
		anInt1634 = model.anInt1634;
		texturedTrianglePointsX = model.texturedTrianglePointsX;
		texturedTrianglePointsY = model.texturedTrianglePointsY;
		texturedTrianglePointsZ = model.texturedTrianglePointsZ;
	}

	public Model(boolean bool, int i, boolean bool_72_, Model model)
	{
		try {
			vertexCount = model.vertexCount;
			triangleCount = model.triangleCount;
			texturedTriangleCount = model.texturedTriangleCount;
			if (bool) {
				verticesY = new int[vertexCount];
				for (int vertex = 0; vertex < vertexCount; vertex++) {
					verticesY[vertex] = model.verticesY[vertex];
				}
			} else {
				verticesY = model.verticesY;
			}
			if (bool_72_) {
				anIntArray1627 = new int[triangleCount];
				anIntArray1628 = new int[triangleCount];
				anIntArray1629 = new int[triangleCount];
				for (int i_75_ = 0; i_75_ < triangleCount; i_75_++) {
					anIntArray1627[i_75_] = model.anIntArray1627[i_75_];
					anIntArray1628[i_75_] = model.anIntArray1628[i_75_];
					anIntArray1629[i_75_] = model.anIntArray1629[i_75_];
				}
				texturePoints = new int[triangleCount];
				if (model.texturePoints == null) {
					for (int i_76_ = 0; i_76_ < triangleCount; i_76_++) {
						texturePoints[i_76_] = 0;
					}
				} else {
					for (int i_77_ = 0; i_77_ < triangleCount; i_77_++) {
						texturePoints[i_77_] = model.texturePoints[i_77_];
					}
				}
				verticesNormal = new VertexNormal[vertexCount];
				for (int vertex = 0; vertex < vertexCount; vertex++) {
					VertexNormal vertexnormal = verticesNormal[vertex] = new VertexNormal();
					VertexNormal vertexnormal_79_ = model.verticesNormal[vertex];
					vertexnormal.x = vertexnormal_79_.x;
					vertexnormal.y = vertexnormal_79_.y;
					vertexnormal.z = vertexnormal_79_.z;
					vertexnormal.magnitude = vertexnormal_79_.magnitude;
				}
				aVertexNormalArray1653 = model.aVertexNormalArray1653;
			} else {
				anIntArray1627 = model.anIntArray1627;
				anIntArray1628 = model.anIntArray1628;
				anIntArray1629 = model.anIntArray1629;
				texturePoints = model.texturePoints;
			}
			verticesX = model.verticesX;
			verticesZ = model.verticesZ;
			triangleColorValues = model.triangleColorValues;
			triangleAlphaValues = model.triangleAlphaValues;
			trianglePriorities = model.trianglePriorities;
			anInt1634 = model.anInt1634;
			trianglePointsX = model.trianglePointsX;
			trianglePointsY = model.trianglePointsY;
			trianglePointsZ = model.trianglePointsZ;
			texturedTrianglePointsX = model.texturedTrianglePointsX;
			texturedTrianglePointsY = model.texturedTrianglePointsY;
			texturedTrianglePointsZ = model.texturedTrianglePointsZ;
			modelHeight = model.modelHeight;
			anInt1644 = model.anInt1644;
			if (i >= 0) {
				throw new NullPointerException();
			}
			anInt1643 = model.anInt1643;
			anInt1646 = model.anInt1646;
			anInt1645 = model.anInt1645;
			anInt1639 = model.anInt1639;
			anInt1641 = model.anInt1641;
			anInt1642 = model.anInt1642;
			anInt1640 = model.anInt1640;
		} catch (RuntimeException runtimeexception) {
			SignLink.reportError("16425, " + bool + ", " + i + ", " + bool_72_ + ", " + model + ", " + runtimeexception.toString());
			throw new RuntimeException();
		}
	}

	public void method412(int i, Model model_80_, boolean bool) {
		try {
			vertexCount = model_80_.vertexCount;
			if (i != 7) {
				for (int i_81_ = 1; i_81_ > 0; i_81_++) {
					/* empty */
				}
			}
			triangleCount = model_80_.triangleCount;
			texturedTriangleCount = model_80_.texturedTriangleCount;
			if (Model.anIntArray1615.length < vertexCount) {
				Model.anIntArray1615 = new int[vertexCount + 100];
				Model.anIntArray1616 = new int[vertexCount + 100];
				Model.anIntArray1617 = new int[vertexCount + 100];
			}
			verticesX = Model.anIntArray1615;
			verticesY = Model.anIntArray1616;
			verticesZ = Model.anIntArray1617;
			for (int i_82_ = 0; i_82_ < vertexCount; i_82_++) {
				verticesX[i_82_] = model_80_.verticesX[i_82_];
				verticesY[i_82_] = model_80_.verticesY[i_82_];
				verticesZ[i_82_] = model_80_.verticesZ[i_82_];
			}
			if (bool) {
				triangleAlphaValues = model_80_.triangleAlphaValues;
			} else {
				if (Model.anIntArray1618.length < triangleCount) {
					Model.anIntArray1618 = new int[triangleCount + 100];
				}
				triangleAlphaValues = Model.anIntArray1618;
				if (model_80_.triangleAlphaValues == null) {
					for (int i_83_ = 0; i_83_ < triangleCount; i_83_++) {
						triangleAlphaValues[i_83_] = 0;
					}
				} else {
					for (int i_84_ = 0; i_84_ < triangleCount; i_84_++) {
						triangleAlphaValues[i_84_] = model_80_.triangleAlphaValues[i_84_];
					}
				}
			}
			texturePoints = model_80_.texturePoints;
			triangleColorValues = model_80_.triangleColorValues;
			trianglePriorities = model_80_.trianglePriorities;
			anInt1634 = model_80_.anInt1634;
			triangleSkin = model_80_.triangleSkin;
			vectorSkin = model_80_.vectorSkin;
			trianglePointsX = model_80_.trianglePointsX;
			trianglePointsY = model_80_.trianglePointsY;
			trianglePointsZ = model_80_.trianglePointsZ;
			anIntArray1627 = model_80_.anIntArray1627;
			anIntArray1628 = model_80_.anIntArray1628;
			anIntArray1629 = model_80_.anIntArray1629;
			texturedTrianglePointsX = model_80_.texturedTrianglePointsX;
			texturedTrianglePointsY = model_80_.texturedTrianglePointsY;
			texturedTrianglePointsZ = model_80_.texturedTrianglePointsZ;
		} catch (RuntimeException runtimeexception) {
			SignLink.reportError("18331, " + i + ", " + model_80_ + ", " + bool + ", " + runtimeexception.toString());
			throw new RuntimeException();
		}
	}

	private final int method413(Model model_85_, int i) {
		int i_86_ = -1;
		int i_87_ = model_85_.verticesX[i];
		int i_88_ = model_85_.verticesY[i];
		int i_89_ = model_85_.verticesZ[i];
		for (int i_90_ = 0; i_90_ < vertexCount; i_90_++) {
			if (i_87_ == verticesX[i_90_] && i_88_ == verticesY[i_90_] && i_89_ == verticesZ[i_90_]) {
				i_86_ = i_90_;
				break;
			}
		}
		if (i_86_ == -1) {
			verticesX[vertexCount] = i_87_;
			verticesY[vertexCount] = i_88_;
			verticesZ[vertexCount] = i_89_;
			if (model_85_.vertexSkins != null) {
				vertexSkins[vertexCount] = model_85_.vertexSkins[i];
			}
			i_86_ = vertexCount++;
		}
		return i_86_;
	}

	public void calculateDiagonals(boolean bool) {
		try {
			modelHeight = 0;
			anInt1643 = 0;
			anInt1644 = 0;
			for (int i = 0; i < vertexCount; i++) {
				int i_91_ = verticesX[i];
				int i_92_ = verticesY[i];
				int i_93_ = verticesZ[i];
				if (-i_92_ > modelHeight) {
					modelHeight = -i_92_;
				}
				if (i_92_ > anInt1644) {
					anInt1644 = i_92_;
				}
				int i_94_ = i_91_ * i_91_ + i_93_ * i_93_;
				if (i_94_ > anInt1643) {
					anInt1643 = i_94_;
				}
			}
			if (bool) {
			}
			anInt1643 = (int) (Math.sqrt(anInt1643) + 0.99);
			anInt1646 = (int) (Math.sqrt(anInt1643 * anInt1643 + modelHeight * modelHeight) + 0.99);
			anInt1645 = anInt1646 + (int) (Math.sqrt(anInt1643 * anInt1643 + anInt1644 * anInt1644) + 0.99);
		} catch (RuntimeException runtimeexception) {
			SignLink.reportError("41353, " + bool + ", " + runtimeexception.toString());
			throw new RuntimeException();
		}
	}

	public void normalise(boolean bool) {
		try {
			modelHeight = 0;
			anInt1644 = 0;
			if (bool) {
				aBoolean1608 = !aBoolean1608;
			}
			for (int i = 0; i < vertexCount; i++) {
				int i_95_ = verticesY[i];
				if (-i_95_ > modelHeight) {
					modelHeight = -i_95_;
				}
				if (i_95_ > anInt1644) {
					anInt1644 = i_95_;
				}
			}
			anInt1646 = (int) (Math.sqrt(anInt1643 * anInt1643 + modelHeight * modelHeight) + 0.99);
			anInt1645 = anInt1646 + (int) (Math.sqrt(anInt1643 * anInt1643 + anInt1644 * anInt1644) + 0.99);
		} catch (RuntimeException runtimeexception) {
			SignLink.reportError("87212, " + bool + ", " + runtimeexception.toString());
			throw new RuntimeException();
		}
	}

	public void method416(int i) {
		try {
			modelHeight = 0;
			anInt1643 = 0;
			anInt1644 = 0;
			anInt1639 = 999999;
			anInt1640 = -999999;
			anInt1641 = -99999;
			anInt1642 = 99999;
			for (int i_96_ = 0; i_96_ < vertexCount; i_96_++) {
				int i_97_ = verticesX[i_96_];
				int i_98_ = verticesY[i_96_];
				int i_99_ = verticesZ[i_96_];
				if (i_97_ < anInt1639) {
					anInt1639 = i_97_;
				}
				if (i_97_ > anInt1640) {
					anInt1640 = i_97_;
				}
				if (i_99_ < anInt1642) {
					anInt1642 = i_99_;
				}
				if (i_99_ > anInt1641) {
					anInt1641 = i_99_;
				}
				if (-i_98_ > modelHeight) {
					modelHeight = -i_98_;
				}
				if (i_98_ > anInt1644) {
					anInt1644 = i_98_;
				}
				int i_100_ = i_97_ * i_97_ + i_99_ * i_99_;
				if (i_100_ > anInt1643) {
					anInt1643 = i_100_;
				}
			}
			anInt1643 = (int) Math.sqrt(anInt1643);
			anInt1646 = (int) Math.sqrt(anInt1643 * anInt1643 + modelHeight * modelHeight);
			if (i == 21073) {
				anInt1645 = anInt1646 + (int) Math.sqrt(anInt1643 * anInt1643 + anInt1644 * anInt1644);
			}
		} catch (RuntimeException runtimeexception) {
			SignLink.reportError("2042, " + i + ", " + runtimeexception.toString());
			throw new RuntimeException();
		}
	}

	public void createBones() {
		if (vertexSkins != null) {
			int[] is = new int[256];
			int i = 0;
			for (int i_101_ = 0; i_101_ < vertexCount; i_101_++) {
				int i_102_ = vertexSkins[i_101_];
				is[i_102_]++;
				if (i_102_ > i) {
					i = i_102_;
				}
			}
			vectorSkin = new int[i + 1][];
			for (int i_103_ = 0; i_103_ <= i; i_103_++) {
				vectorSkin[i_103_] = new int[is[i_103_]];
				is[i_103_] = 0;
			}
			for (int i_104_ = 0; i_104_ < vertexCount; i_104_++) {
				int i_105_ = vertexSkins[i_104_];
				vectorSkin[i_105_][is[i_105_]++] = i_104_;
			}
			vertexSkins = null;
		}
		if (triangleSkinValues == null) {
			return;
		}
		int[] is = new int[256];
		int i = 0;
		for (int i_106_ = 0; i_106_ < triangleCount; i_106_++) {
			int i_107_ = triangleSkinValues[i_106_];
			is[i_107_]++;
			if (i_107_ > i) {
				i = i_107_;
			}
		}
		triangleSkin = new int[i + 1][];
		for (int i_108_ = 0; i_108_ <= i; i_108_++) {
			triangleSkin[i_108_] = new int[is[i_108_]];
			is[i_108_] = 0;
		}
		for (int i_109_ = 0; i_109_ < triangleCount; i_109_++) {
			int i_110_ = triangleSkinValues[i_109_];
			triangleSkin[i_110_][is[i_110_]++] = i_109_;
		}
		triangleSkinValues = null;
	}

	public void applyTransform(int frameId) {
		if (vectorSkin != null && frameId != -1) {
			Animation animation = Animation.getAnimation(frameId);
			if (animation != null) {
				Skins skins = animation.animationSkins;
				Model.vertexXModifier = 0;
				Model.vertexYModifier = 0;
				Model.vertexZModifier = 0;
				for (int stepId = 0; stepId < animation.stepCount; stepId++) {
					int opcode = animation.opcodeTable[stepId];
					transformStep(skins.opcodes[opcode], skins.skinList[opcode], animation.modifier1[stepId], animation.modifier2[stepId], animation.modifier3[stepId]);
				}
			}
		}
	}

	public void mixAnimationFrames(int i, int[] is, int i_114_, int i_115_) {
		try {
			if (i_115_ != -1) {
				if (is == null || i_114_ == -1) {
					applyTransform(i_115_);
				} else {
					Animation animation = Animation.getAnimation(i_115_);
					if (animation != null) {
						Animation animation_116_ = Animation.getAnimation(i_114_);
						if (i == -20491) {
							if (animation_116_ == null) {
								applyTransform(i_115_);
							} else {
								Skins skins = animation.animationSkins;
								Model.vertexXModifier = 0;
								Model.vertexYModifier = 0;
								Model.vertexZModifier = 0;
								int i_117_ = 0;
								int i_118_ = is[i_117_++];
								for (int i_119_ = 0; i_119_ < animation.stepCount; i_119_++) {
									int i_120_;
									for (i_120_ = animation.opcodeTable[i_119_]; i_120_ > i_118_; i_118_ = is[i_117_++]) {
										/* empty */
									}
									if (i_120_ != i_118_ || skins.opcodes[i_120_] == 0) {
										transformStep(skins.opcodes[i_120_], skins.skinList[i_120_], animation.modifier1[i_119_], animation.modifier2[i_119_], animation.modifier3[i_119_]);
									}
								}
								Model.vertexXModifier = 0;
								Model.vertexYModifier = 0;
								Model.vertexZModifier = 0;
								i_117_ = 0;
								i_118_ = is[i_117_++];
								for (int i_121_ = 0; i_121_ < animation_116_.stepCount; i_121_++) {
									int i_122_;
									for (i_122_ = animation_116_.opcodeTable[i_121_]; i_122_ > i_118_; i_118_ = is[i_117_++]) {
										/* empty */
									}
									if (i_122_ == i_118_ || skins.opcodes[i_122_] == 0) {
										transformStep(skins.opcodes[i_122_], skins.skinList[i_122_], animation_116_.modifier1[i_121_], animation_116_.modifier2[i_121_], animation_116_.modifier3[i_121_]);
									}
								}
							}
						}
					}
				}
			}
		} catch (RuntimeException runtimeexception) {
			SignLink.reportError("63853, " + i + ", " + is + ", " + i_114_ + ", " + i_115_ + ", " + runtimeexception.toString());
			throw new RuntimeException();
		}
	}

	private void transformStep(int i, int[] is, int i_123_, int i_124_, int i_125_) {
		int i_126_ = is.length;
		if (i == 0) {
			int i_127_ = 0;
			Model.vertexXModifier = 0;
			Model.vertexYModifier = 0;
			Model.vertexZModifier = 0;
			for (int i_128_ = 0; i_128_ < i_126_; i_128_++) {
				int i_129_ = is[i_128_];
				if (i_129_ < vectorSkin.length) {
					int[] is_130_ = vectorSkin[i_129_];
					for (int i_131_ = 0; i_131_ < is_130_.length; i_131_++) {
						int i_132_ = is_130_[i_131_];
						Model.vertexXModifier += verticesX[i_132_];
						Model.vertexYModifier += verticesY[i_132_];
						Model.vertexZModifier += verticesZ[i_132_];
						i_127_++;
					}
				}
			}
			if (i_127_ > 0) {
				Model.vertexXModifier = Model.vertexXModifier / i_127_ + i_123_;
				Model.vertexYModifier = Model.vertexYModifier / i_127_ + i_124_;
				Model.vertexZModifier = Model.vertexZModifier / i_127_ + i_125_;
			} else {
				Model.vertexXModifier = i_123_;
				Model.vertexYModifier = i_124_;
				Model.vertexZModifier = i_125_;
			}
		} else if (i == 1) {
			for (int i_133_ = 0; i_133_ < i_126_; i_133_++) {
				int i_134_ = is[i_133_];
				if (i_134_ < vectorSkin.length) {
					int[] is_135_ = vectorSkin[i_134_];
					for (int i_136_ = 0; i_136_ < is_135_.length; i_136_++) {
						int i_137_ = is_135_[i_136_];
						verticesX[i_137_] += i_123_;
						verticesY[i_137_] += i_124_;
						verticesZ[i_137_] += i_125_;
					}
				}
			}
		} else if (i == 2) {
			for (int i_138_ = 0; i_138_ < i_126_; i_138_++) {
				int i_139_ = is[i_138_];
				if (i_139_ < vectorSkin.length) {
					int[] is_140_ = vectorSkin[i_139_];
					for (int i_141_ = 0; i_141_ < is_140_.length; i_141_++) {
						int i_142_ = is_140_[i_141_];
						verticesX[i_142_] -= Model.vertexXModifier;
						verticesY[i_142_] -= Model.vertexYModifier;
						verticesZ[i_142_] -= Model.vertexZModifier;
						int i_143_ = (i_123_ & 0xff) * 8;
						int i_144_ = (i_124_ & 0xff) * 8;
						int i_145_ = (i_125_ & 0xff) * 8;
						if (i_145_ != 0) {
							int i_146_ = Model.anIntArray1682[i_145_];
							int i_147_ = Model.anIntArray1683[i_145_];
							int i_148_ = verticesY[i_142_] * i_146_ + verticesX[i_142_] * i_147_ >> 16;
							verticesY[i_142_] = verticesY[i_142_] * i_147_ - verticesX[i_142_] * i_146_ >> 16;
							verticesX[i_142_] = i_148_;
						}
						if (i_143_ != 0) {
							int i_149_ = Model.anIntArray1682[i_143_];
							int i_150_ = Model.anIntArray1683[i_143_];
							int i_151_ = verticesY[i_142_] * i_150_ - verticesZ[i_142_] * i_149_ >> 16;
							verticesZ[i_142_] = verticesY[i_142_] * i_149_ + verticesZ[i_142_] * i_150_ >> 16;
							verticesY[i_142_] = i_151_;
						}
						if (i_144_ != 0) {
							int i_152_ = Model.anIntArray1682[i_144_];
							int i_153_ = Model.anIntArray1683[i_144_];
							int i_154_ = verticesZ[i_142_] * i_152_ + verticesX[i_142_] * i_153_ >> 16;
							verticesZ[i_142_] = verticesZ[i_142_] * i_153_ - verticesX[i_142_] * i_152_ >> 16;
							verticesX[i_142_] = i_154_;
						}
						verticesX[i_142_] += Model.vertexXModifier;
						verticesY[i_142_] += Model.vertexYModifier;
						verticesZ[i_142_] += Model.vertexZModifier;
					}
				}
			}
		} else if (i == 3) {
			for (int i_155_ = 0; i_155_ < i_126_; i_155_++) {
				int i_156_ = is[i_155_];
				if (i_156_ < vectorSkin.length) {
					int[] is_157_ = vectorSkin[i_156_];
					for (int i_158_ = 0; i_158_ < is_157_.length; i_158_++) {
						int i_159_ = is_157_[i_158_];
						verticesX[i_159_] -= Model.vertexXModifier;
						verticesY[i_159_] -= Model.vertexYModifier;
						verticesZ[i_159_] -= Model.vertexZModifier;
						verticesX[i_159_] = verticesX[i_159_] * i_123_ / 128;
						verticesY[i_159_] = verticesY[i_159_] * i_124_ / 128;
						verticesZ[i_159_] = verticesZ[i_159_] * i_125_ / 128;
						verticesX[i_159_] += Model.vertexXModifier;
						verticesY[i_159_] += Model.vertexYModifier;
						verticesZ[i_159_] += Model.vertexZModifier;
					}
				}
			}
		} else if (i == 5 && triangleSkin != null && triangleAlphaValues != null) {
			for (int i_160_ = 0; i_160_ < i_126_; i_160_++) {
				int i_161_ = is[i_160_];
				if (i_161_ < triangleSkin.length) {
					int[] is_162_ = triangleSkin[i_161_];
					for (int i_163_ = 0; i_163_ < is_162_.length; i_163_++) {
						int i_164_ = is_162_[i_163_];
						triangleAlphaValues[i_164_] += i_123_ * 8;
						if (triangleAlphaValues[i_164_] < 0) {
							triangleAlphaValues[i_164_] = 0;
						}
						if (triangleAlphaValues[i_164_] > 255) {
							triangleAlphaValues[i_164_] = 255;
						}
					}
				}
			}
		}
	}

	public void rotate90Degrees(int i) {
		try {
			if (i > 0) {
				for (int i_165_ = 0; i_165_ < vertexCount; i_165_++) {
					int i_166_ = verticesX[i_165_];
					verticesX[i_165_] = verticesZ[i_165_];
					verticesZ[i_165_] = -i_166_;
				}
			}
		} catch (RuntimeException runtimeexception) {
			SignLink.reportError("59385, " + i + ", " + runtimeexception.toString());
			throw new RuntimeException();
		}
	}

	public void rotateX(int i, int i_167_) {
		try {
			int i_168_ = Model.anIntArray1682[i];
			int i_169_ = Model.anIntArray1683[i];
			for (int i_170_ = 0; i_170_ < vertexCount; i_170_++) {
				int i_171_ = verticesY[i_170_] * i_169_ - verticesZ[i_170_] * i_168_ >> 16;
				verticesZ[i_170_] = verticesY[i_170_] * i_168_ + verticesZ[i_170_] * i_169_ >> 16;
				verticesY[i_170_] = i_171_;
			}
			if (i_167_ < anInt1610 || i_167_ > anInt1610) {
				anInt1610 = 324;
			}
		} catch (RuntimeException runtimeexception) {
			SignLink.reportError("13317, " + i + ", " + i_167_ + ", " + runtimeexception.toString());
			throw new RuntimeException();
		}
	}

	public void translate(int x, int y, int z) {
		for (int i = 0; i < vertexCount; i++) {
			verticesX[i] += x;
			verticesY[i] += y;
			verticesZ[i] += z;
		}
	}

	public void recolor(int[] from, int[] to) {
		for (int colorIndex = 0; colorIndex < from.length; colorIndex++) {
			if (colorIndex > to.length) {
				break;
			}
			for (int i = 0; i < triangleCount; i++) {
				if (triangleColorValues[i] == from[colorIndex]) {
					triangleColorValues[i] = to[colorIndex];
				}
			}
		}
	}

	public void recolor(int from, int to) {
		for (int i = 0; i < triangleCount; i++) {
			if (triangleColorValues[i] == from) {
				triangleColorValues[i] = to;
			}
		}
	}

	public void mirror(int i) {
		do {
			try {
				for (int i_178_ = 0; i_178_ < vertexCount; i_178_++) {
					verticesZ[i_178_] = -verticesZ[i_178_];
				}
				for (int i_179_ = 0; i_179_ < triangleCount; i_179_++) {
					int i_180_ = trianglePointsX[i_179_];
					trianglePointsX[i_179_] = trianglePointsZ[i_179_];
					trianglePointsZ[i_179_] = i_180_;
				}
				if (i == 0) {
					break;
				}
			} catch (RuntimeException runtimeexception) {
				SignLink.reportError("2772, " + i + ", " + runtimeexception.toString());
				throw new RuntimeException();
			}
			break;
		} while (false);
	}

	public void scaleT(int i, int i_181_, int i_183_) {
		for (int i_184_ = 0; i_184_ < vertexCount; i_184_++) {
			verticesX[i_184_] = verticesX[i_184_] * i / 128;
			verticesY[i_184_] = verticesY[i_184_] * i_183_ / 128;
			verticesZ[i_184_] = verticesZ[i_184_] * i_181_ / 128;
		}
	}

	public final void applyLighting(int i, int i_185_, int i_186_, int i_187_, int i_188_, boolean bool) {
		int i_189_ = (int) Math.sqrt(i_186_ * i_186_ + i_187_ * i_187_ + i_188_ * i_188_);
		int i_190_ = i_185_ * i_189_ >> 8;
		if (anIntArray1627 == null) {
			anIntArray1627 = new int[triangleCount];
			anIntArray1628 = new int[triangleCount];
			anIntArray1629 = new int[triangleCount];
		}
		if (verticesNormal == null) {
			verticesNormal = new VertexNormal[vertexCount];
			for (int i_191_ = 0; i_191_ < vertexCount; i_191_++) {
				verticesNormal[i_191_] = new VertexNormal();
			}
		}
		for (int i_192_ = 0; i_192_ < triangleCount; i_192_++) {
			int i_193_ = trianglePointsX[i_192_];
			int i_194_ = trianglePointsY[i_192_];
			int i_195_ = trianglePointsZ[i_192_];
			int i_196_ = verticesX[i_194_] - verticesX[i_193_];
			int i_197_ = verticesY[i_194_] - verticesY[i_193_];
			int i_198_ = verticesZ[i_194_] - verticesZ[i_193_];
			int i_199_ = verticesX[i_195_] - verticesX[i_193_];
			int i_200_ = verticesY[i_195_] - verticesY[i_193_];
			int i_201_ = verticesZ[i_195_] - verticesZ[i_193_];
			int i_202_ = i_197_ * i_201_ - i_200_ * i_198_;
			int i_203_ = i_198_ * i_199_ - i_201_ * i_196_;
			int i_204_;
			for (i_204_ = i_196_ * i_200_ - i_199_ * i_197_; i_202_ > 8192 || i_203_ > 8192 || i_204_ > 8192 || i_202_ < -8192 || i_203_ < -8192 || i_204_ < -8192; i_204_ >>= 1) {
				i_202_ >>= 1;
				i_203_ >>= 1;
			}
			int i_205_ = (int) Math.sqrt(i_202_ * i_202_ + i_203_ * i_203_ + i_204_ * i_204_);
			if (i_205_ <= 0) {
				i_205_ = 1;
			}
			i_202_ = i_202_ * 256 / i_205_;
			i_203_ = i_203_ * 256 / i_205_;
			i_204_ = i_204_ * 256 / i_205_;
			if (texturePoints == null || (texturePoints[i_192_] & 0x1) == 0) {
				VertexNormal vertexnormal = verticesNormal[i_193_];
				vertexnormal.x += i_202_;
				vertexnormal.y += i_203_;
				vertexnormal.z += i_204_;
				vertexnormal.magnitude++;
				vertexnormal = verticesNormal[i_194_];
				vertexnormal.x += i_202_;
				vertexnormal.y += i_203_;
				vertexnormal.z += i_204_;
				vertexnormal.magnitude++;
				vertexnormal = verticesNormal[i_195_];
				vertexnormal.x += i_202_;
				vertexnormal.y += i_203_;
				vertexnormal.z += i_204_;
				vertexnormal.magnitude++;
			} else {
				int i_206_ = i + (i_186_ * i_202_ + i_187_ * i_203_ + i_188_ * i_204_) / (i_190_ + i_190_ / 2);
				anIntArray1627[i_192_] = Model.method429(triangleColorValues[i_192_], i_206_, texturePoints[i_192_]);
			}
		}
		if (bool) {
			method428(i, i_190_, i_186_, i_187_, i_188_);
		} else {
			aVertexNormalArray1653 = new VertexNormal[vertexCount];
			for (int i_207_ = 0; i_207_ < vertexCount; i_207_++) {
				VertexNormal vertexnormal = verticesNormal[i_207_];
				VertexNormal vertexnormal_208_ = aVertexNormalArray1653[i_207_] = new VertexNormal();
				vertexnormal_208_.x = vertexnormal.x;
				vertexnormal_208_.y = vertexnormal.y;
				vertexnormal_208_.z = vertexnormal.z;
				vertexnormal_208_.magnitude = vertexnormal.magnitude;
			}
		}
		if (bool) {
			calculateDiagonals(false);
		} else {
			method416(21073);
		}
	}

	public final void method428(int i, int i_209_, int i_210_, int i_211_, int i_212_) {
		for (int i_213_ = 0; i_213_ < triangleCount; i_213_++) {
			int i_214_ = trianglePointsX[i_213_];
			int i_215_ = trianglePointsY[i_213_];
			int i_216_ = trianglePointsZ[i_213_];
			if (texturePoints == null) {
				int i_217_ = triangleColorValues[i_213_];
				VertexNormal vertexnormal = verticesNormal[i_214_];
				int i_218_ = i + (i_210_ * vertexnormal.x + i_211_ * vertexnormal.y + i_212_ * vertexnormal.z) / (i_209_ * vertexnormal.magnitude);
				anIntArray1627[i_213_] = Model.method429(i_217_, i_218_, 0);
				vertexnormal = verticesNormal[i_215_];
				i_218_ = i + (i_210_ * vertexnormal.x + i_211_ * vertexnormal.y + i_212_ * vertexnormal.z) / (i_209_ * vertexnormal.magnitude);
				anIntArray1628[i_213_] = Model.method429(i_217_, i_218_, 0);
				vertexnormal = verticesNormal[i_216_];
				i_218_ = i + (i_210_ * vertexnormal.x + i_211_ * vertexnormal.y + i_212_ * vertexnormal.z) / (i_209_ * vertexnormal.magnitude);
				anIntArray1629[i_213_] = Model.method429(i_217_, i_218_, 0);
			} else if ((texturePoints[i_213_] & 0x1) == 0) {
				int i_219_ = triangleColorValues[i_213_];
				int i_220_ = texturePoints[i_213_];
				VertexNormal vertexnormal = verticesNormal[i_214_];
				int i_221_ = i + (i_210_ * vertexnormal.x + i_211_ * vertexnormal.y + i_212_ * vertexnormal.z) / (i_209_ * vertexnormal.magnitude);
				anIntArray1627[i_213_] = Model.method429(i_219_, i_221_, i_220_);
				vertexnormal = verticesNormal[i_215_];
				i_221_ = i + (i_210_ * vertexnormal.x + i_211_ * vertexnormal.y + i_212_ * vertexnormal.z) / (i_209_ * vertexnormal.magnitude);
				anIntArray1628[i_213_] = Model.method429(i_219_, i_221_, i_220_);
				vertexnormal = verticesNormal[i_216_];
				i_221_ = i + (i_210_ * vertexnormal.x + i_211_ * vertexnormal.y + i_212_ * vertexnormal.z) / (i_209_ * vertexnormal.magnitude);
				anIntArray1629[i_213_] = Model.method429(i_219_, i_221_, i_220_);
			}
		}
		verticesNormal = null;
		aVertexNormalArray1653 = null;
		vertexSkins = null;
		triangleSkinValues = null;
		if (texturePoints != null) {
			for (int i_222_ = 0; i_222_ < triangleCount; i_222_++) {
				if ((texturePoints[i_222_] & 0x2) == 2) {
					return;
				}
			}
		}
		triangleColorValues = null;
	}

	public static final int method429(int i, int i_223_, int i_224_) {
		if ((i_224_ & 0x2) == 2) {
			if (i_223_ < 0) {
				i_223_ = 0;
			} else if (i_223_ > 127) {
				i_223_ = 127;
			}
			i_223_ = 127 - i_223_;
			return i_223_;
		}
		i_223_ = i_223_ * (i & 0x7f) >> 7;
		if (i_223_ < 2) {
			i_223_ = 2;
		} else if (i_223_ > 126) {
			i_223_ = 126;
		}
		return (i & 0xff80) + i_223_;
	}

	public final void method430(int i, int i_225_, int i_226_, int i_227_, int i_228_, int i_229_, int i_230_) {
		int i_231_ = Rasterizer3D.centerX;
		int i_232_ = Rasterizer3D.centerY;
		int i_233_ = Model.anIntArray1682[i];
		int i_234_ = Model.anIntArray1683[i];
		int i_235_ = Model.anIntArray1682[i_225_];
		int i_236_ = Model.anIntArray1683[i_225_];
		int i_237_ = Model.anIntArray1682[i_226_];
		int i_238_ = Model.anIntArray1683[i_226_];
		int i_239_ = Model.anIntArray1682[i_227_];
		int i_240_ = Model.anIntArray1683[i_227_];
		int i_241_ = i_229_ * i_239_ + i_230_ * i_240_ >> 16;
		for (int i_242_ = 0; i_242_ < vertexCount; i_242_++) {
			int i_243_ = verticesX[i_242_];
			int i_244_ = verticesY[i_242_];
			int i_245_ = verticesZ[i_242_];
			if (i_226_ != 0) {
				int i_246_ = i_244_ * i_237_ + i_243_ * i_238_ >> 16;
				i_244_ = i_244_ * i_238_ - i_243_ * i_237_ >> 16;
				i_243_ = i_246_;
			}
			if (i != 0) {
				int i_247_ = i_244_ * i_234_ - i_245_ * i_233_ >> 16;
				i_245_ = i_244_ * i_233_ + i_245_ * i_234_ >> 16;
				i_244_ = i_247_;
			}
			if (i_225_ != 0) {
				int i_248_ = i_245_ * i_235_ + i_243_ * i_236_ >> 16;
				i_245_ = i_245_ * i_236_ - i_243_ * i_235_ >> 16;
				i_243_ = i_248_;
			}
			i_243_ += i_228_;
			i_244_ += i_229_;
			i_245_ += i_230_;
			int i_249_ = i_244_ * i_240_ - i_245_ * i_239_ >> 16;
			i_245_ = i_244_ * i_239_ + i_245_ * i_240_ >> 16;
			i_244_ = i_249_;
			Model.anIntArray1660[i_242_] = i_245_ - i_241_;
			Model.anIntArray1658[i_242_] = i_231_ + (i_243_ << 9) / i_245_;
			Model.anIntArray1659[i_242_] = i_232_ + (i_244_ << 9) / i_245_;
			if (texturedTriangleCount > 0) {
				Model.anIntArray1661[i_242_] = i_243_;
				Model.anIntArray1662[i_242_] = i_244_;
				Model.anIntArray1663[i_242_] = i_245_;
			}
		}
		try {
			method431(false, false, 0);
		} catch (Exception exception) {
			/* empty */
		}
	}

	@Override
	public final void renderAtPoint(int i, int i_250_, int i_251_, int i_252_, int i_253_, int i_254_, int i_255_, int i_256_, int i_257_) {
		int i_258_ = i_256_ * i_253_ - i_254_ * i_252_ >> 16;
		int i_259_ = i_255_ * i_250_ + i_258_ * i_251_ >> 16;
		int i_260_ = anInt1643 * i_251_ >> 16;
		int i_261_ = i_259_ + i_260_;
		if (i_261_ > 50 && i_259_ < 3500) {
			int i_262_ = i_256_ * i_252_ + i_254_ * i_253_ >> 16;
			int i_263_ = i_262_ - anInt1643 << 9;
			if (i_263_ / i_261_ < Rasterizer.centerX) {
				int i_264_ = i_262_ + anInt1643 << 9;
				if (i_264_ / i_261_ > -Rasterizer.centerX) {
					int i_265_ = i_255_ * i_251_ - i_258_ * i_250_ >> 16;
					int i_266_ = anInt1643 * i_250_ >> 16;
					int i_267_ = i_265_ + i_266_ << 9;
					if (i_267_ / i_261_ > -Rasterizer.centerY) {
						int i_268_ = i_266_ + (modelHeight * i_251_ >> 16);
						int i_269_ = i_265_ - i_268_ << 9;
						if (i_269_ / i_261_ < Rasterizer.centerY) {
							int i_270_ = i_260_ + (modelHeight * i_250_ >> 16);
							boolean bool = false;
							if (i_259_ - i_270_ <= 50) {
								bool = true;
							}
							boolean bool_271_ = false;
							if (i_257_ > 0 && Model.aBoolean1677) {
								int i_272_ = i_259_ - i_260_;
								if (i_272_ <= 50) {
									i_272_ = 50;
								}
								if (i_262_ > 0) {
									i_263_ /= i_261_;
									i_264_ /= i_272_;
								} else {
									i_264_ /= i_261_;
									i_263_ /= i_272_;
								}
								if (i_265_ > 0) {
									i_269_ /= i_261_;
									i_267_ /= i_272_;
								} else {
									i_267_ /= i_261_;
									i_269_ /= i_272_;
								}
								int i_273_ = Model.anInt1678 - Rasterizer3D.centerX;
								int i_274_ = Model.anInt1679 - Rasterizer3D.centerY;
								if (i_273_ > i_263_ && i_273_ < i_264_ && i_274_ > i_269_ && i_274_ < i_267_) {
									if (oneSquareModel) {
										Model.anIntArray1681[Model.anInt1680++] = i_257_;
									} else {
										bool_271_ = true;
									}
								}
							}
							int i_275_ = Rasterizer3D.centerX;
							int i_276_ = Rasterizer3D.centerY;
							int i_277_ = 0;
							int i_278_ = 0;
							if (i != 0) {
								i_277_ = Model.anIntArray1682[i];
								i_278_ = Model.anIntArray1683[i];
							}
							for (int i_279_ = 0; i_279_ < vertexCount; i_279_++) {
								int i_280_ = verticesX[i_279_];
								int i_281_ = verticesY[i_279_];
								int i_282_ = verticesZ[i_279_];
								if (i != 0) {
									int i_283_ = i_282_ * i_277_ + i_280_ * i_278_ >> 16;
									i_282_ = i_282_ * i_278_ - i_280_ * i_277_ >> 16;
									i_280_ = i_283_;
								}
								i_280_ += i_254_;
								i_281_ += i_255_;
								i_282_ += i_256_;
								int i_284_ = i_282_ * i_252_ + i_280_ * i_253_ >> 16;
								i_282_ = i_282_ * i_253_ - i_280_ * i_252_ >> 16;
								i_280_ = i_284_;
								i_284_ = i_281_ * i_251_ - i_282_ * i_250_ >> 16;
								i_282_ = i_281_ * i_250_ + i_282_ * i_251_ >> 16;
								i_281_ = i_284_;
								Model.anIntArray1660[i_279_] = i_282_ - i_259_;
								if (i_282_ >= 50) {
									Model.anIntArray1658[i_279_] = i_275_ + (i_280_ << 9) / i_282_;
									Model.anIntArray1659[i_279_] = i_276_ + (i_281_ << 9) / i_282_;
								} else {
									Model.anIntArray1658[i_279_] = -5000;
									bool = true;
								}
								if (bool || texturedTriangleCount > 0) {
									Model.anIntArray1661[i_279_] = i_280_;
									Model.anIntArray1662[i_279_] = i_281_;
									Model.anIntArray1663[i_279_] = i_282_;
								}
							}
							try {
								method431(bool, bool_271_, i_257_);
							} catch (Exception exception) {
								/* empty */
							}
						}
					}
				}
			}
		}
	}

	private final void method431(boolean bool, boolean bool_285_, int i) {
		for (int i_286_ = 0; i_286_ < anInt1645; i_286_++) {
			Model.anIntArray1664[i_286_] = 0;
		}
		for (int i_287_ = 0; i_287_ < triangleCount; i_287_++) {
			if (texturePoints == null || texturePoints[i_287_] != -1) {
				int i_288_ = trianglePointsX[i_287_];
				int i_289_ = trianglePointsY[i_287_];
				int i_290_ = trianglePointsZ[i_287_];
				int i_291_ = Model.anIntArray1658[i_288_];
				int i_292_ = Model.anIntArray1658[i_289_];
				int i_293_ = Model.anIntArray1658[i_290_];
				if (bool && (i_291_ == -5000 || i_292_ == -5000 || i_293_ == -5000)) {
					Model.aBooleanArray1657[i_287_] = true;
					int i_294_ = (Model.anIntArray1660[i_288_] + Model.anIntArray1660[i_289_] + Model.anIntArray1660[i_290_]) / 3 + anInt1646;
					Model.anIntArrayArray1665[i_294_][Model.anIntArray1664[i_294_]++] = i_287_;
				} else {
					if (bool_285_ && method434(Model.anInt1678, Model.anInt1679, Model.anIntArray1659[i_288_], Model.anIntArray1659[i_289_], Model.anIntArray1659[i_290_], i_291_, i_292_, i_293_)) {
						Model.anIntArray1681[Model.anInt1680++] = i;
						bool_285_ = false;
					}
					if ((i_291_ - i_292_) * (Model.anIntArray1659[i_290_] - Model.anIntArray1659[i_289_]) - (Model.anIntArray1659[i_288_] - Model.anIntArray1659[i_289_]) * (i_293_ - i_292_) > 0) {
						Model.aBooleanArray1657[i_287_] = false;
						if (i_291_ < 0 || i_292_ < 0 || i_293_ < 0 || i_291_ > Rasterizer.virtualBottomX || i_292_ > Rasterizer.virtualBottomX || i_293_ > Rasterizer.virtualBottomX) {
							Model.aBooleanArray1656[i_287_] = true;
						} else {
							Model.aBooleanArray1656[i_287_] = false;
						}
						int i_295_ = (Model.anIntArray1660[i_288_] + Model.anIntArray1660[i_289_] + Model.anIntArray1660[i_290_]) / 3 + anInt1646;
						Model.anIntArrayArray1665[i_295_][Model.anIntArray1664[i_295_]++] = i_287_;
					}
				}
			}
		}
		if (trianglePriorities == null) {
			for (int i_296_ = anInt1645 - 1; i_296_ >= 0; i_296_--) {
				int i_297_ = Model.anIntArray1664[i_296_];
				if (i_297_ > 0) {
					int[] is = Model.anIntArrayArray1665[i_296_];
					for (int i_298_ = 0; i_298_ < i_297_; i_298_++) {
						method432(is[i_298_]);
					}
				}
			}
		} else {
			for (int i_299_ = 0; i_299_ < 12; i_299_++) {
				Model.anIntArray1666[i_299_] = 0;
				Model.anIntArray1670[i_299_] = 0;
			}
			for (int i_300_ = anInt1645 - 1; i_300_ >= 0; i_300_--) {
				int i_301_ = Model.anIntArray1664[i_300_];
				if (i_301_ > 0) {
					int[] is = Model.anIntArrayArray1665[i_300_];
					for (int i_302_ = 0; i_302_ < i_301_; i_302_++) {
						int i_303_ = is[i_302_];
						int i_304_ = trianglePriorities[i_303_];
						int i_305_ = Model.anIntArray1666[i_304_]++;
						Model.anIntArrayArray1667[i_304_][i_305_] = i_303_;
						if (i_304_ < 10) {
							Model.anIntArray1670[i_304_] += i_300_;
						} else if (i_304_ == 10) {
							Model.anIntArray1668[i_305_] = i_300_;
						} else {
							Model.anIntArray1669[i_305_] = i_300_;
						}
					}
				}
			}
			int i_306_ = 0;
			if (Model.anIntArray1666[1] > 0 || Model.anIntArray1666[2] > 0) {
				i_306_ = (Model.anIntArray1670[1] + Model.anIntArray1670[2]) / (Model.anIntArray1666[1] + Model.anIntArray1666[2]);
			}
			int i_307_ = 0;
			if (Model.anIntArray1666[3] > 0 || Model.anIntArray1666[4] > 0) {
				i_307_ = (Model.anIntArray1670[3] + Model.anIntArray1670[4]) / (Model.anIntArray1666[3] + Model.anIntArray1666[4]);
			}
			int i_308_ = 0;
			if (Model.anIntArray1666[6] > 0 || Model.anIntArray1666[8] > 0) {
				i_308_ = (Model.anIntArray1670[6] + Model.anIntArray1670[8]) / (Model.anIntArray1666[6] + Model.anIntArray1666[8]);
			}
			int i_309_ = 0;
			int i_310_ = Model.anIntArray1666[10];
			int[] is = Model.anIntArrayArray1667[10];
			int[] is_311_ = Model.anIntArray1668;
			if (i_309_ == i_310_) {
				i_309_ = 0;
				i_310_ = Model.anIntArray1666[11];
				is = Model.anIntArrayArray1667[11];
				is_311_ = Model.anIntArray1669;
			}
			int i_312_;
			if (i_309_ < i_310_) {
				i_312_ = is_311_[i_309_];
			} else {
				i_312_ = -1000;
			}
			for (int i_313_ = 0; i_313_ < 10; i_313_++) {
				while (i_313_ == 0) {
					if (i_312_ <= i_306_) {
						break;
					}
					method432(is[i_309_++]);
					if (i_309_ == i_310_ && is != Model.anIntArrayArray1667[11]) {
						i_309_ = 0;
						i_310_ = Model.anIntArray1666[11];
						is = Model.anIntArrayArray1667[11];
						is_311_ = Model.anIntArray1669;
					}
					if (i_309_ < i_310_) {
						i_312_ = is_311_[i_309_];
					} else {
						i_312_ = -1000;
					}
				}
				while (i_313_ == 3) {
					if (i_312_ <= i_307_) {
						break;
					}
					method432(is[i_309_++]);
					if (i_309_ == i_310_ && is != Model.anIntArrayArray1667[11]) {
						i_309_ = 0;
						i_310_ = Model.anIntArray1666[11];
						is = Model.anIntArrayArray1667[11];
						is_311_ = Model.anIntArray1669;
					}
					if (i_309_ < i_310_) {
						i_312_ = is_311_[i_309_];
					} else {
						i_312_ = -1000;
					}
				}
				while (i_313_ == 5 && i_312_ > i_308_) {
					method432(is[i_309_++]);
					if (i_309_ == i_310_ && is != Model.anIntArrayArray1667[11]) {
						i_309_ = 0;
						i_310_ = Model.anIntArray1666[11];
						is = Model.anIntArrayArray1667[11];
						is_311_ = Model.anIntArray1669;
					}
					if (i_309_ < i_310_) {
						i_312_ = is_311_[i_309_];
					} else {
						i_312_ = -1000;
					}
				}
				int i_314_ = Model.anIntArray1666[i_313_];
				int[] is_315_ = Model.anIntArrayArray1667[i_313_];
				for (int i_316_ = 0; i_316_ < i_314_; i_316_++) {
					method432(is_315_[i_316_]);
				}
			}
			while (i_312_ != -1000) {
				method432(is[i_309_++]);
				if (i_309_ == i_310_ && is != Model.anIntArrayArray1667[11]) {
					i_309_ = 0;
					is = Model.anIntArrayArray1667[11];
					i_310_ = Model.anIntArray1666[11];
					is_311_ = Model.anIntArray1669;
				}
				if (i_309_ < i_310_) {
					i_312_ = is_311_[i_309_];
				} else {
					i_312_ = -1000;
				}
			}
		}
	}

	private final void method432(int i) {
		if (Model.aBooleanArray1657[i]) {
			method433(i);
		} else {
			int i_317_ = trianglePointsX[i];
			int i_318_ = trianglePointsY[i];
			int i_319_ = trianglePointsZ[i];
			Rasterizer3D.aBoolean1482 = Model.aBooleanArray1656[i];
			if (triangleAlphaValues == null) {
				Rasterizer3D.anInt1485 = 0;
			} else {
				Rasterizer3D.anInt1485 = triangleAlphaValues[i];
			}
			int i_320_;
			if (texturePoints == null) {
				i_320_ = 0;
			} else {
				i_320_ = texturePoints[i] & 0x3;
			}
			if (i_320_ == 0) {
				Rasterizer3D.method371(Model.anIntArray1659[i_317_], Model.anIntArray1659[i_318_], Model.anIntArray1659[i_319_], Model.anIntArray1658[i_317_], Model.anIntArray1658[i_318_], Model.anIntArray1658[i_319_], anIntArray1627[i], anIntArray1628[i], anIntArray1629[i]);
			} else if (i_320_ == 1) {
				Rasterizer3D.method373(Model.anIntArray1659[i_317_], Model.anIntArray1659[i_318_], Model.anIntArray1659[i_319_], Model.anIntArray1658[i_317_], Model.anIntArray1658[i_318_], Model.anIntArray1658[i_319_], Model.anIntArray1684[anIntArray1627[i]]);
			} else if (i_320_ == 2) {
				int i_321_ = texturePoints[i] >> 2;
				int i_322_ = texturedTrianglePointsX[i_321_];
				int i_323_ = texturedTrianglePointsY[i_321_];
				int i_324_ = texturedTrianglePointsZ[i_321_];
				Rasterizer3D.method375(Model.anIntArray1659[i_317_], Model.anIntArray1659[i_318_], Model.anIntArray1659[i_319_], Model.anIntArray1658[i_317_], Model.anIntArray1658[i_318_], Model.anIntArray1658[i_319_], anIntArray1627[i], anIntArray1628[i], anIntArray1629[i], Model.anIntArray1661[i_322_], Model.anIntArray1661[i_323_], Model.anIntArray1661[i_324_], Model.anIntArray1662[i_322_], Model.anIntArray1662[i_323_], Model.anIntArray1662[i_324_], Model.anIntArray1663[i_322_], Model.anIntArray1663[i_323_], Model.anIntArray1663[i_324_], triangleColorValues[i]);
			} else if (i_320_ == 3) {
				int i_325_ = texturePoints[i] >> 2;
				int i_326_ = texturedTrianglePointsX[i_325_];
				int i_327_ = texturedTrianglePointsY[i_325_];
				int i_328_ = texturedTrianglePointsZ[i_325_];
				Rasterizer3D.method375(Model.anIntArray1659[i_317_], Model.anIntArray1659[i_318_], Model.anIntArray1659[i_319_], Model.anIntArray1658[i_317_], Model.anIntArray1658[i_318_], Model.anIntArray1658[i_319_], anIntArray1627[i], anIntArray1627[i], anIntArray1627[i], Model.anIntArray1661[i_326_], Model.anIntArray1661[i_327_], Model.anIntArray1661[i_328_], Model.anIntArray1662[i_326_], Model.anIntArray1662[i_327_], Model.anIntArray1662[i_328_], Model.anIntArray1663[i_326_], Model.anIntArray1663[i_327_], Model.anIntArray1663[i_328_], triangleColorValues[i]);
			}
		}
	}

	private final void method433(int i) {
		int i_329_ = Rasterizer3D.centerX;
		int i_330_ = Rasterizer3D.centerY;
		int i_331_ = 0;
		int i_332_ = trianglePointsX[i];
		int i_333_ = trianglePointsY[i];
		int i_334_ = trianglePointsZ[i];
		int i_335_ = Model.anIntArray1663[i_332_];
		int i_336_ = Model.anIntArray1663[i_333_];
		int i_337_ = Model.anIntArray1663[i_334_];
		if (i_335_ >= 50) {
			Model.anIntArray1671[i_331_] = Model.anIntArray1658[i_332_];
			Model.anIntArray1672[i_331_] = Model.anIntArray1659[i_332_];
			Model.anIntArray1673[i_331_++] = anIntArray1627[i];
		} else {
			int i_338_ = Model.anIntArray1661[i_332_];
			int i_339_ = Model.anIntArray1662[i_332_];
			int i_340_ = anIntArray1627[i];
			if (i_337_ >= 50) {
				int i_341_ = (50 - i_335_) * Model.anIntArray1685[i_337_ - i_335_];
				Model.anIntArray1671[i_331_] = i_329_ + (i_338_ + ((Model.anIntArray1661[i_334_] - i_338_) * i_341_ >> 16) << 9) / 50;
				Model.anIntArray1672[i_331_] = i_330_ + (i_339_ + ((Model.anIntArray1662[i_334_] - i_339_) * i_341_ >> 16) << 9) / 50;
				Model.anIntArray1673[i_331_++] = i_340_ + ((anIntArray1629[i] - i_340_) * i_341_ >> 16);
			}
			if (i_336_ >= 50) {
				int i_342_ = (50 - i_335_) * Model.anIntArray1685[i_336_ - i_335_];
				Model.anIntArray1671[i_331_] = i_329_ + (i_338_ + ((Model.anIntArray1661[i_333_] - i_338_) * i_342_ >> 16) << 9) / 50;
				Model.anIntArray1672[i_331_] = i_330_ + (i_339_ + ((Model.anIntArray1662[i_333_] - i_339_) * i_342_ >> 16) << 9) / 50;
				Model.anIntArray1673[i_331_++] = i_340_ + ((anIntArray1628[i] - i_340_) * i_342_ >> 16);
			}
		}
		if (i_336_ >= 50) {
			Model.anIntArray1671[i_331_] = Model.anIntArray1658[i_333_];
			Model.anIntArray1672[i_331_] = Model.anIntArray1659[i_333_];
			Model.anIntArray1673[i_331_++] = anIntArray1628[i];
		} else {
			int i_343_ = Model.anIntArray1661[i_333_];
			int i_344_ = Model.anIntArray1662[i_333_];
			int i_345_ = anIntArray1628[i];
			if (i_335_ >= 50) {
				int i_346_ = (50 - i_336_) * Model.anIntArray1685[i_335_ - i_336_];
				Model.anIntArray1671[i_331_] = i_329_ + (i_343_ + ((Model.anIntArray1661[i_332_] - i_343_) * i_346_ >> 16) << 9) / 50;
				Model.anIntArray1672[i_331_] = i_330_ + (i_344_ + ((Model.anIntArray1662[i_332_] - i_344_) * i_346_ >> 16) << 9) / 50;
				Model.anIntArray1673[i_331_++] = i_345_ + ((anIntArray1627[i] - i_345_) * i_346_ >> 16);
			}
			if (i_337_ >= 50) {
				int i_347_ = (50 - i_336_) * Model.anIntArray1685[i_337_ - i_336_];
				Model.anIntArray1671[i_331_] = i_329_ + (i_343_ + ((Model.anIntArray1661[i_334_] - i_343_) * i_347_ >> 16) << 9) / 50;
				Model.anIntArray1672[i_331_] = i_330_ + (i_344_ + ((Model.anIntArray1662[i_334_] - i_344_) * i_347_ >> 16) << 9) / 50;
				Model.anIntArray1673[i_331_++] = i_345_ + ((anIntArray1629[i] - i_345_) * i_347_ >> 16);
			}
		}
		if (i_337_ >= 50) {
			Model.anIntArray1671[i_331_] = Model.anIntArray1658[i_334_];
			Model.anIntArray1672[i_331_] = Model.anIntArray1659[i_334_];
			Model.anIntArray1673[i_331_++] = anIntArray1629[i];
		} else {
			int i_348_ = Model.anIntArray1661[i_334_];
			int i_349_ = Model.anIntArray1662[i_334_];
			int i_350_ = anIntArray1629[i];
			if (i_336_ >= 50) {
				int i_351_ = (50 - i_337_) * Model.anIntArray1685[i_336_ - i_337_];
				Model.anIntArray1671[i_331_] = i_329_ + (i_348_ + ((Model.anIntArray1661[i_333_] - i_348_) * i_351_ >> 16) << 9) / 50;
				Model.anIntArray1672[i_331_] = i_330_ + (i_349_ + ((Model.anIntArray1662[i_333_] - i_349_) * i_351_ >> 16) << 9) / 50;
				Model.anIntArray1673[i_331_++] = i_350_ + ((anIntArray1628[i] - i_350_) * i_351_ >> 16);
			}
			if (i_335_ >= 50) {
				int i_352_ = (50 - i_337_) * Model.anIntArray1685[i_335_ - i_337_];
				Model.anIntArray1671[i_331_] = i_329_ + (i_348_ + ((Model.anIntArray1661[i_332_] - i_348_) * i_352_ >> 16) << 9) / 50;
				Model.anIntArray1672[i_331_] = i_330_ + (i_349_ + ((Model.anIntArray1662[i_332_] - i_349_) * i_352_ >> 16) << 9) / 50;
				Model.anIntArray1673[i_331_++] = i_350_ + ((anIntArray1627[i] - i_350_) * i_352_ >> 16);
			}
		}
		int i_353_ = Model.anIntArray1671[0];
		int i_354_ = Model.anIntArray1671[1];
		int i_355_ = Model.anIntArray1671[2];
		int i_356_ = Model.anIntArray1672[0];
		int i_357_ = Model.anIntArray1672[1];
		int i_358_ = Model.anIntArray1672[2];
		if ((i_353_ - i_354_) * (i_358_ - i_357_) - (i_356_ - i_357_) * (i_355_ - i_354_) > 0) {
			Rasterizer3D.aBoolean1482 = false;
			if (i_331_ == 3) {
				if (i_353_ < 0 || i_354_ < 0 || i_355_ < 0 || i_353_ > Rasterizer.virtualBottomX || i_354_ > Rasterizer.virtualBottomX || i_355_ > Rasterizer.virtualBottomX) {
					Rasterizer3D.aBoolean1482 = true;
				}
				int i_359_;
				if (texturePoints == null) {
					i_359_ = 0;
				} else {
					i_359_ = texturePoints[i] & 0x3;
				}
				if (i_359_ == 0) {
					Rasterizer3D.method371(i_356_, i_357_, i_358_, i_353_, i_354_, i_355_, Model.anIntArray1673[0], Model.anIntArray1673[1], Model.anIntArray1673[2]);
				} else if (i_359_ == 1) {
					Rasterizer3D.method373(i_356_, i_357_, i_358_, i_353_, i_354_, i_355_, Model.anIntArray1684[anIntArray1627[i]]);
				} else if (i_359_ == 2) {
					int i_360_ = texturePoints[i] >> 2;
					int i_361_ = texturedTrianglePointsX[i_360_];
					int i_362_ = texturedTrianglePointsY[i_360_];
					int i_363_ = texturedTrianglePointsZ[i_360_];
					Rasterizer3D.method375(i_356_, i_357_, i_358_, i_353_, i_354_, i_355_, Model.anIntArray1673[0], Model.anIntArray1673[1], Model.anIntArray1673[2], Model.anIntArray1661[i_361_], Model.anIntArray1661[i_362_], Model.anIntArray1661[i_363_], Model.anIntArray1662[i_361_], Model.anIntArray1662[i_362_], Model.anIntArray1662[i_363_], Model.anIntArray1663[i_361_], Model.anIntArray1663[i_362_], Model.anIntArray1663[i_363_], triangleColorValues[i]);
				} else if (i_359_ == 3) {
					int i_364_ = texturePoints[i] >> 2;
					int i_365_ = texturedTrianglePointsX[i_364_];
					int i_366_ = texturedTrianglePointsY[i_364_];
					int i_367_ = texturedTrianglePointsZ[i_364_];
					Rasterizer3D.method375(i_356_, i_357_, i_358_, i_353_, i_354_, i_355_, anIntArray1627[i], anIntArray1627[i], anIntArray1627[i], Model.anIntArray1661[i_365_], Model.anIntArray1661[i_366_], Model.anIntArray1661[i_367_], Model.anIntArray1662[i_365_], Model.anIntArray1662[i_366_], Model.anIntArray1662[i_367_], Model.anIntArray1663[i_365_], Model.anIntArray1663[i_366_], Model.anIntArray1663[i_367_], triangleColorValues[i]);
				}
			}
			if (i_331_ == 4) {
				if (i_353_ < 0 || i_354_ < 0 || i_355_ < 0 || i_353_ > Rasterizer.virtualBottomX || i_354_ > Rasterizer.virtualBottomX || i_355_ > Rasterizer.virtualBottomX || Model.anIntArray1671[3] < 0 || Model.anIntArray1671[3] > Rasterizer.virtualBottomX) {
					Rasterizer3D.aBoolean1482 = true;
				}
				int i_368_;
				if (texturePoints == null) {
					i_368_ = 0;
				} else {
					i_368_ = texturePoints[i] & 0x3;
				}
				if (i_368_ == 0) {
					Rasterizer3D.method371(i_356_, i_357_, i_358_, i_353_, i_354_, i_355_, Model.anIntArray1673[0], Model.anIntArray1673[1], Model.anIntArray1673[2]);
					Rasterizer3D.method371(i_356_, i_358_, Model.anIntArray1672[3], i_353_, i_355_, Model.anIntArray1671[3], Model.anIntArray1673[0], Model.anIntArray1673[2], Model.anIntArray1673[3]);
				} else if (i_368_ == 1) {
					int i_369_ = Model.anIntArray1684[anIntArray1627[i]];
					Rasterizer3D.method373(i_356_, i_357_, i_358_, i_353_, i_354_, i_355_, i_369_);
					Rasterizer3D.method373(i_356_, i_358_, Model.anIntArray1672[3], i_353_, i_355_, Model.anIntArray1671[3], i_369_);
				} else if (i_368_ == 2) {
					int i_370_ = texturePoints[i] >> 2;
					int i_371_ = texturedTrianglePointsX[i_370_];
					int i_372_ = texturedTrianglePointsY[i_370_];
					int i_373_ = texturedTrianglePointsZ[i_370_];
					Rasterizer3D.method375(i_356_, i_357_, i_358_, i_353_, i_354_, i_355_, Model.anIntArray1673[0], Model.anIntArray1673[1], Model.anIntArray1673[2], Model.anIntArray1661[i_371_], Model.anIntArray1661[i_372_], Model.anIntArray1661[i_373_], Model.anIntArray1662[i_371_], Model.anIntArray1662[i_372_], Model.anIntArray1662[i_373_], Model.anIntArray1663[i_371_], Model.anIntArray1663[i_372_], Model.anIntArray1663[i_373_], triangleColorValues[i]);
					Rasterizer3D.method375(i_356_, i_358_, Model.anIntArray1672[3], i_353_, i_355_, Model.anIntArray1671[3], Model.anIntArray1673[0], Model.anIntArray1673[2], Model.anIntArray1673[3], Model.anIntArray1661[i_371_], Model.anIntArray1661[i_372_], Model.anIntArray1661[i_373_], Model.anIntArray1662[i_371_], Model.anIntArray1662[i_372_], Model.anIntArray1662[i_373_], Model.anIntArray1663[i_371_], Model.anIntArray1663[i_372_], Model.anIntArray1663[i_373_], triangleColorValues[i]);
				} else if (i_368_ == 3) {
					int i_374_ = texturePoints[i] >> 2;
					int i_375_ = texturedTrianglePointsX[i_374_];
					int i_376_ = texturedTrianglePointsY[i_374_];
					int i_377_ = texturedTrianglePointsZ[i_374_];
					Rasterizer3D.method375(i_356_, i_357_, i_358_, i_353_, i_354_, i_355_, anIntArray1627[i], anIntArray1627[i], anIntArray1627[i], Model.anIntArray1661[i_375_], Model.anIntArray1661[i_376_], Model.anIntArray1661[i_377_], Model.anIntArray1662[i_375_], Model.anIntArray1662[i_376_], Model.anIntArray1662[i_377_], Model.anIntArray1663[i_375_], Model.anIntArray1663[i_376_], Model.anIntArray1663[i_377_], triangleColorValues[i]);
					Rasterizer3D.method375(i_356_, i_358_, Model.anIntArray1672[3], i_353_, i_355_, Model.anIntArray1671[3], anIntArray1627[i], anIntArray1627[i], anIntArray1627[i], Model.anIntArray1661[i_375_], Model.anIntArray1661[i_376_], Model.anIntArray1661[i_377_], Model.anIntArray1662[i_375_], Model.anIntArray1662[i_376_], Model.anIntArray1662[i_377_], Model.anIntArray1663[i_375_], Model.anIntArray1663[i_376_], Model.anIntArray1663[i_377_], triangleColorValues[i]);
				}
			}
		}
	}

	private final boolean method434(int i, int i_378_, int i_379_, int i_380_, int i_381_, int i_382_, int i_383_, int i_384_) {
		if (i_378_ < i_379_ && i_378_ < i_380_ && i_378_ < i_381_) {
			return false;
		}
		if (i_378_ > i_379_ && i_378_ > i_380_ && i_378_ > i_381_) {
			return false;
		}
		if (i < i_382_ && i < i_383_ && i < i_384_) {
			return false;
		}
		if (i > i_382_ && i > i_383_ && i > i_384_) {
			return false;
		}
		return true;
	}
}
