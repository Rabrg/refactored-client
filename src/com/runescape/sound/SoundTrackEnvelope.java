package com.runescape.sound;

import com.runescape.net.Buffer;

public class SoundTrackEnvelope {

	private int numPhases;
	private int[] phaseDuration;
	private int[] phasePeak;
	protected int smart;
	protected int end;
	protected int form;
	private int critical;
	private int phaseIndex;
	private int step;
	private int amplitude;
	private int ticks;

	public final void decode(Buffer buffer) {
		form = buffer.getUnsignedByte();
		smart = buffer.getInt();
		end = buffer.getInt();
		decodeShape(buffer);
	}

	public final void decodeShape(Buffer buffer) {
		numPhases = buffer.getUnsignedByte();
		phaseDuration = new int[numPhases];
		phasePeak = new int[numPhases];
		for (int phase = 0; phase < numPhases; phase++) {
			phaseDuration[phase] = buffer.getUnsignedLEShort();
			phasePeak[phase] = buffer.getUnsignedLEShort();
		}
	}

	final void reset() {
		critical = 0;
		phaseIndex = 0;
		step = 0;
		amplitude = 0;
		ticks = 0;
	}

	final int step(int period) {
		if (ticks >= critical) {
			amplitude = phasePeak[phaseIndex++] << 15;
			if (phaseIndex >= numPhases) {
				phaseIndex = numPhases - 1;
			}
			critical = (int) (phaseDuration[phaseIndex] / 65536.0 * period);
			if (critical > ticks) {
				step = ((phasePeak[phaseIndex] << 15) - amplitude) / (critical - ticks);
			}
		}
		amplitude += step;
		ticks++;
		return amplitude - step >> 15;
	}
}
