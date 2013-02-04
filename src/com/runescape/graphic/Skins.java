package com.runescape.graphic;

import com.runescape.net.Buffer;

public class Skins {

	public int skinAmount;
	public int[] opcodes;
	public int[][] skinList;

	public Skins(Buffer buffer) {
		skinAmount = buffer.getUnsignedByte();
		opcodes = new int[skinAmount];
		skinList = new int[skinAmount][];
		for (int opcode = 0; opcode < skinAmount; opcode++) {
			opcodes[opcode] = buffer.getUnsignedByte();
		}
		for (int skin = 0; skin < skinAmount; skin++) {
			int subSkinAmount = buffer.getUnsignedByte();
			skinList[skin] = new int[subSkinAmount];
			for (int subSkin = 0; subSkin < subSkinAmount; subSkin++) {
				skinList[skin][subSkin] = buffer.getUnsignedByte();
			}
		}
	}
}
