package com.runescape.cache.media;

import com.runescape.cache.Archive;
import com.runescape.media.Animation;
import com.runescape.net.Buffer;

public class AnimationSequence {

	public static int count;
	public static AnimationSequence[] cache;
	public int frameCount;
	public int[] frame2Ids;
	public int[] frame1Ids;
	private int[] frameLenghts;
	public int frameStep = -1;
	public int[] flowControl;
	public boolean aBoolean56 = false;
	public int anInt57 = 5;
	public int anInt58 = -1;
	public int anInt59 = -1;
	public int anInt60 = 99;
	public int anInt61 = -1;
	public int priority = -1;
	public int anInt63 = 2;
	public int anInt64;

	public static void load(Archive archive) {
		Buffer buffer = new Buffer(archive.getFile("seq.dat"));
		AnimationSequence.count = buffer.getUnsignedLEShort();
		if (AnimationSequence.cache == null) {
			AnimationSequence.cache = new AnimationSequence[AnimationSequence.count];
		}
		for (int animation = 0; animation < AnimationSequence.count; animation++) {
			if (AnimationSequence.cache[animation] == null) {
				AnimationSequence.cache[animation] = new AnimationSequence();
			}
			AnimationSequence.cache[animation].loadDefinition(true, buffer);
		}
	}

	public int getFrameLength(int animationId) {
		int frameLength = frameLenghts[animationId];
		if (frameLength == 0) {
			Animation animation = Animation.getAnimation(frame2Ids[animationId]);
			if (animation != null) {
				frameLength = frameLenghts[animationId] = animation.displayLength;
			}
		}
		if (frameLength == 0) {
			frameLength = 1;
		}
		return frameLength;
	}

	public void loadDefinition(boolean bool, Buffer buffer) {
		while (true) {
			int attributeId = buffer.getUnsignedByte();
			if (attributeId == 0) {
				break;
			}
			if (attributeId == 1) {
				frameCount = buffer.getUnsignedByte();
				frame2Ids = new int[frameCount];
				frame1Ids = new int[frameCount];
				frameLenghts = new int[frameCount];
				for (int frame = 0; frame < frameCount; frame++) {
					frame2Ids[frame] = buffer.getUnsignedLEShort();
					frame1Ids[frame] = buffer.getUnsignedLEShort();
					if (frame1Ids[frame] == 65535) {
						frame1Ids[frame] = -1;
					}
					frameLenghts[frame] = buffer.getUnsignedLEShort();
				}
			} else if (attributeId == 2) {
				frameStep = buffer.getUnsignedLEShort();
			} else if (attributeId == 3) {
				int flowCount = buffer.getUnsignedByte();
				flowControl = new int[flowCount + 1];
				for (int flow = 0; flow < flowCount; flow++) {
					flowControl[flow] = buffer.getUnsignedByte();
				}
				flowControl[flowCount] = 9999999;
			} else if (attributeId == 4) {
				aBoolean56 = true;
			} else if (attributeId == 5) {
				anInt57 = buffer.getUnsignedByte();
			} else if (attributeId == 6) {
				anInt58 = buffer.getUnsignedLEShort();
			} else if (attributeId == 7) {
				anInt59 = buffer.getUnsignedLEShort();
			} else if (attributeId == 8) {
				anInt60 = buffer.getUnsignedByte();
			} else if (attributeId == 9) {
				anInt61 = buffer.getUnsignedByte();
			} else if (attributeId == 10) {
				priority = buffer.getUnsignedByte();
			} else if (attributeId == 11) {
				anInt63 = buffer.getUnsignedByte();
			} else if (attributeId == 12) {
				anInt64 = buffer.getInt();
			} else {
				System.out.println("Error unrecognised seq config code: " + attributeId);
			}
		}
		if (frameCount == 0) {
			frameCount = 1;
			frame2Ids = new int[1];
			frame2Ids[0] = -1;
			frame1Ids = new int[1];
			frame1Ids[0] = -1;
			frameLenghts = new int[1];
			frameLenghts[0] = -1;
		}
		if (anInt61 == -1) {
			if (flowControl != null) {
				anInt61 = 2;
			} else {
				anInt61 = 0;
			}
		}
		if (priority != -1) {
			return;
		}
		if (flowControl != null) {
			priority = 2;
		} else {
			priority = 0;
		}

	}
}
