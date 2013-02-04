package com.runescape.sound;

import com.runescape.net.Buffer;

public class SoundFilter {

	protected int[] numPairs = new int[2];
	protected int[][][] pairPhase = new int[2][2][4];
	protected int[][][] magnitude = new int[2][2][4];
	protected int[] unity = new int[2];
	static float[][] _coefficient = new float[2][8];
	static int[][] coefficient = new int[2][8];
	static float _invUnity;
	static int invUnity;

	private float adaptMagnitude(int dir, int i, float f) {
		float alpha = magnitude[dir][0][i] + f * (magnitude[dir][1][i] - magnitude[dir][0][i]);
		alpha *= 0.0015258789F;
		return 1.0F - (float) Math.pow(10.0, -alpha / 20.0F);
	}

	private float normalize(float f) {
		float f_2_ = 32.703197F * (float) Math.pow(2.0, f);
		return f_2_ * 3.1415927F / 11025.0F;
	}

	private float adaptPhase(int dir, float f, int i) {
		float f_4_ = pairPhase[dir][0][i] + f * (pairPhase[dir][1][i] - pairPhase[dir][0][i]);
		f_4_ *= 1.2207031E-4F;
		return normalize(f_4_);
	}

	public int compute(int dir, float f) {
		if (dir == 0) {
			float f_6_ = unity[0] + (unity[1] - unity[0]) * f;
			f_6_ *= 0.0030517578F;
			SoundFilter._invUnity = (float) Math.pow(0.1, f_6_ / 20.0F);
			SoundFilter.invUnity = (int) (SoundFilter._invUnity * 65536.0F);
		}
		if (numPairs[dir] == 0) {
			return 0;
		}
		float f_7_ = adaptMagnitude(dir, 0, f);
		SoundFilter._coefficient[dir][0] = -2.0F * f_7_ * (float) Math.cos(adaptPhase(dir, f, 0));
		SoundFilter._coefficient[dir][1] = f_7_ * f_7_;
		for (int term = 1; term < numPairs[dir]; term++) {
			f_7_ = adaptMagnitude(dir, term, f);
			float f_9_ = -2.0F * f_7_ * (float) Math.cos(adaptPhase(dir, f, term));
			float f_10_ = f_7_ * f_7_;
			SoundFilter._coefficient[dir][term * 2 + 1] = SoundFilter._coefficient[dir][term * 2 - 1] * f_10_;
			SoundFilter._coefficient[dir][term * 2] = SoundFilter._coefficient[dir][term * 2 - 1] * f_9_
					+ SoundFilter._coefficient[dir][term * 2 - 2] * f_10_;
			for (int i = term * 2 - 1; i >= 2; i--) {
				SoundFilter._coefficient[dir][i] += SoundFilter._coefficient[dir][i - 1] * f_9_
						+ SoundFilter._coefficient[dir][i - 2] * f_10_;
			}
			SoundFilter._coefficient[dir][1] += SoundFilter._coefficient[dir][0] * f_9_ + f_10_;
			SoundFilter._coefficient[dir][0] += f_9_;
		}
		if (dir == 0) {
			for (int i = 0; i < numPairs[0] * 2; i++) {
				SoundFilter._coefficient[0][i] *= SoundFilter._invUnity;
			}
		}
		for (int term = 0; term < numPairs[dir] * 2; term++) {
			SoundFilter.coefficient[dir][term] = (int) (SoundFilter._coefficient[dir][term] * 65536.0F);
		}
		return numPairs[dir] * 2;
	}

	public final void decode(Buffer buffer, boolean bool, SoundTrackEnvelope soundtrackenvelope) {
		int numPair = buffer.getUnsignedByte();
		numPairs[0] = numPair >> 4;
		numPairs[1] = numPair & 0xf;
		if (numPair != 0) {
			unity[0] = buffer.getUnsignedLEShort();
			unity[1] = buffer.getUnsignedLEShort();
			int migrated = buffer.getUnsignedByte();
			for (int dir = 0; dir < 2; dir++) {
				for (int term = 0; term < numPairs[dir]; term++) {
					pairPhase[dir][0][term] = buffer.getUnsignedLEShort();
					magnitude[dir][0][term] = buffer.getUnsignedLEShort();
				}
			}
			for (int dir = 0; dir < 2; dir++) {
				for (int term = 0; term < numPairs[dir]; term++) {
					if ((migrated & 1 << dir * 4 << term) != 0) {
						pairPhase[dir][1][term] = buffer.getUnsignedLEShort();
						magnitude[dir][1][term] = buffer.getUnsignedLEShort();
					} else {
						pairPhase[dir][1][term] = pairPhase[dir][0][term];
						magnitude[dir][1][term] = magnitude[dir][0][term];
					}
				}
			}
			if (migrated != 0 || unity[1] != unity[0]) {
				soundtrackenvelope.decodeShape(buffer);
			}
		} else {
			unity[0] = unity[1] = 0;
		}
	}
}
