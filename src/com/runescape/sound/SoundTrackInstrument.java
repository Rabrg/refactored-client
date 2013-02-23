package com.runescape.sound;

import com.runescape.net.Buffer;

public class SoundTrackInstrument {

	private SoundTrackEnvelope pitchEnvelope;
	private SoundTrackEnvelope volumeEnvelope;
	private SoundTrackEnvelope pitchModEnvelope;
	private SoundTrackEnvelope pitchModAmpEnvelope;
	private SoundTrackEnvelope volumeModEnvelope;
	private SoundTrackEnvelope volumeModAmpEnvelope;
	private SoundTrackEnvelope gatingReleaseEnvelope;
	private SoundTrackEnvelope gatingAttackEnvelope;
	private final int[] oscillVolume = new int[5];
	private final int[] oscillPitchDelta = new int[5];
	private final int[] oscillDelay = new int[5];
	private int delayTime;
	private int delayFeedback = 100;
	private SoundFilter filter;
	private SoundTrackEnvelope filterEnvelope;
	protected int duration = 500;
	protected int begin;
	private static int[] buffer;
	private static int[] noise;
	private static int[] sine;
	private static int[] phases = new int[5];
	private static int[] delays = new int[5];
	private static int[] volumeStep = new int[5];
	private static int[] pitchStep = new int[5];
	private static int[] pitchBaseStep = new int[5];

	public static final void initialize() {
		SoundTrackInstrument.noise = new int[32768];
		for (int noiseId = 0; noiseId < 32768; noiseId++) {
			if (Math.random() > 0.5) {
				SoundTrackInstrument.noise[noiseId] = 1;
			} else {
				SoundTrackInstrument.noise[noiseId] = -1;
			}
		}
		SoundTrackInstrument.sine = new int[32768];
		for (int sineId = 0; sineId < 32768; sineId++) {
			SoundTrackInstrument.sine[sineId] = (int) (Math.sin(sineId / 5215.1903) * 16384.0);
		}
		SoundTrackInstrument.buffer = new int[220500];
	}

	public final int[] synthesize(int nS, int dt) {
		for (int position = 0; position < nS; position++) {
			SoundTrackInstrument.buffer[position] = 0;
		}
		if (dt < 10) {
			return SoundTrackInstrument.buffer;
		}
		double fS = nS / (dt + 0.0);
		pitchEnvelope.reset();
		volumeEnvelope.reset();
		int pitchModStep = 0;
		int pitchModBaseStep = 0;
		int pitchModPhase = 0;
		if (pitchModEnvelope != null) {
			pitchModEnvelope.reset();
			pitchModAmpEnvelope.reset();
			pitchModStep = (int) ((pitchModEnvelope.end - pitchModEnvelope.smart) * 32.768 / fS);
			pitchModBaseStep = (int) (pitchModEnvelope.smart * 32.768 / fS);
		}
		int volumeModStep = 0;
		int volumeModBaseStep = 0;
		int volumeModPhase = 0;
		if (volumeModEnvelope != null) {
			volumeModEnvelope.reset();
			volumeModAmpEnvelope.reset();
			volumeModStep = (int) ((volumeModEnvelope.end - volumeModEnvelope.smart) * 32.768 / fS);
			volumeModBaseStep = (int) (volumeModEnvelope.smart * 32.768 / fS);
		}
		for (int oscillVolumeId = 0; oscillVolumeId < 5; oscillVolumeId++) {
			if (oscillVolume[oscillVolumeId] != 0) {
				SoundTrackInstrument.phases[oscillVolumeId] = 0;
				SoundTrackInstrument.delays[oscillVolumeId] = (int) (oscillDelay[oscillVolumeId] * fS);
				SoundTrackInstrument.volumeStep[oscillVolumeId] = (oscillVolume[oscillVolumeId] << 14) / 100;
				SoundTrackInstrument.pitchStep[oscillVolumeId] = (int) ((pitchEnvelope.end - pitchEnvelope.smart)
						* 32.768 * Math.pow(1.0057929410678534, oscillPitchDelta[oscillVolumeId]) / fS);
				SoundTrackInstrument.pitchBaseStep[oscillVolumeId] = (int) (pitchEnvelope.smart * 32.768 / fS);
			}
		}
		for (int offset = 0; offset < nS; offset++) {
			int pitchChange = pitchEnvelope.step(nS);
			int volumeChange = volumeEnvelope.step(nS);
			if (pitchModEnvelope != null) {
				int mod = pitchModEnvelope.step(nS);
				int modAmp = pitchModAmpEnvelope.step(nS);
				pitchChange += evaluateWave(modAmp, pitchModPhase, pitchModEnvelope.form) >> 1;
				pitchModPhase += (mod * pitchModStep >> 16) + pitchModBaseStep;
			}
			if (volumeModEnvelope != null) {
				int mod = volumeModEnvelope.step(nS);
				int modAmp = volumeModAmpEnvelope.step(nS);
				volumeChange = volumeChange
						* ((evaluateWave(modAmp, volumeModPhase, volumeModEnvelope.form) >> 1) + 32768) >> 15;
				volumeModPhase += (mod * volumeModStep >> 16) + volumeModBaseStep;
			}
			for (int oscillVolumeId = 0; oscillVolumeId < 5; oscillVolumeId++) {
				if (oscillVolume[oscillVolumeId] != 0) {
					int position = offset + SoundTrackInstrument.delays[oscillVolumeId];
					if (position < nS) {
						SoundTrackInstrument.buffer[position] += evaluateWave(volumeChange
								* SoundTrackInstrument.volumeStep[oscillVolumeId] >> 15,
								SoundTrackInstrument.phases[oscillVolumeId], pitchEnvelope.form);
						SoundTrackInstrument.phases[oscillVolumeId] += (pitchChange
								* SoundTrackInstrument.pitchStep[oscillVolumeId] >> 16)
								+ SoundTrackInstrument.pitchBaseStep[oscillVolumeId];
					}
				}
			}
		}
		if (gatingReleaseEnvelope != null) {
			gatingReleaseEnvelope.reset();
			gatingAttackEnvelope.reset();
			int counter = 0;
			boolean muted = true;
			for (int position = 0; position < nS; position++) {
				int onStep = gatingReleaseEnvelope.step(nS);
				int offStep = gatingAttackEnvelope.step(nS);
				int threshold;
				if (muted) {
					threshold = gatingReleaseEnvelope.smart
							+ ((gatingReleaseEnvelope.end - gatingReleaseEnvelope.smart) * onStep >> 8);
				} else {
					threshold = gatingReleaseEnvelope.smart
							+ ((gatingReleaseEnvelope.end - gatingReleaseEnvelope.smart) * offStep >> 8);
				}
				counter += 256;
				if (counter >= threshold) {
					counter = 0;
					muted = !muted;
				}
				if (muted) {
					SoundTrackInstrument.buffer[position] = 0;
				}
			}
		}
		if (delayTime > 0 && delayFeedback > 0) {
			int delay = (int) (delayTime * fS);
			for (int position = delay; position < nS; position++) {
				SoundTrackInstrument.buffer[position] += SoundTrackInstrument.buffer[position - delay] * delayFeedback
						/ 100;
			}
		}
		if (filter.numPairs[0] > 0 || filter.numPairs[1] > 0) {
			filterEnvelope.reset();
			int t = filterEnvelope.step(nS + 1);
			int M = filter.compute(0, t / 65536.0F);
			int N = filter.compute(1, t / 65536.0F);
			if (nS >= M + N) {
				int n = 0;
				int delay = N;
				if (delay > nS - M) {
					delay = nS - M;
				}
				for (; n < delay; n++) {
					int y = (int) ((long) SoundTrackInstrument.buffer[n + M] * (long) SoundFilter.invUnity >> 16);
					for (int position = 0; position < M; position++) {
						y += (int) ((long) SoundTrackInstrument.buffer[n + M - 1 - position]
								* (long) SoundFilter.coefficient[0][position] >> 16);
					}
					for (int position = 0; position < n; position++) {
						y -= (int) ((long) SoundTrackInstrument.buffer[n - 1 - position]
								* (long) SoundFilter.coefficient[1][position] >> 16);
					}
					SoundTrackInstrument.buffer[n] = y;
					t = filterEnvelope.step(nS + 1);
				}
				int offset = 128;
				delay = offset;
				while (true) {
					if (delay > nS - M) {
						delay = nS - M;
					}
					for (; n < delay; n++) {
						int y = (int) ((long) SoundTrackInstrument.buffer[n + M] * (long) SoundFilter.invUnity >> 16);
						for (int position = 0; position < M; position++) {
							y += (int) ((long) SoundTrackInstrument.buffer[n + M - 1 - position]
									* (long) SoundFilter.coefficient[0][position] >> 16);
						}
						for (int position = 0; position < N; position++) {
							y -= (int) ((long) SoundTrackInstrument.buffer[n - 1 - position]
									* (long) SoundFilter.coefficient[1][position] >> 16);
						}
						SoundTrackInstrument.buffer[n] = y;
						t = filterEnvelope.step(nS + 1);
					}
					if (n >= nS - M) {
						break;
					}
					M = filter.compute(0, t / 65536.0F);
					N = filter.compute(1, t / 65536.0F);
					delay += offset;
				}
				for (; n < nS; n++) {
					int y = 0;
					for (int position = n + M - nS; position < M; position++) {
						y += (int) ((long) SoundTrackInstrument.buffer[n + M - 1 - position]
								* (long) SoundFilter.coefficient[0][position] >> 16);
					}
					for (int position = 0; position < N; position++) {
						y -= (int) ((long) SoundTrackInstrument.buffer[n - 1 - position]
								* (long) SoundFilter.coefficient[1][position] >> 16);
					}
					SoundTrackInstrument.buffer[n] = y;
					t = filterEnvelope.step(nS + 1);
				}
			}
		}
		for (int position = 0; position < nS; position++) {
			if (SoundTrackInstrument.buffer[position] < -32768) {
				SoundTrackInstrument.buffer[position] = -32768;
			}
			if (SoundTrackInstrument.buffer[position] > 32767) {
				SoundTrackInstrument.buffer[position] = 32767;
			}
		}
		return SoundTrackInstrument.buffer;
	}

	private final int evaluateWave(int amplitude, int phase, int table) {
		if (table == 1) {
			if ((phase & 0x7fff) < 16384) {
				return amplitude;
			}
			return -amplitude;
		}
		if (table == 2) {
			return SoundTrackInstrument.sine[phase & 0x7fff] * amplitude >> 14;
		}
		if (table == 3) {
			return ((phase & 0x7fff) * amplitude >> 14) - amplitude;
		}
		if (table == 4) {
			return SoundTrackInstrument.noise[phase / 2607 & 0x7fff] * amplitude;
		}
		return 0;
	}

	public final void decode(Buffer buffer) {
		pitchEnvelope = new SoundTrackEnvelope();
		pitchEnvelope.decode(buffer);
		volumeEnvelope = new SoundTrackEnvelope();
		volumeEnvelope.decode(buffer);
		int option = buffer.getUnsignedByte();
		if (option != 0) {
			buffer.offset--;
			pitchModEnvelope = new SoundTrackEnvelope();
			pitchModEnvelope.decode(buffer);
			pitchModAmpEnvelope = new SoundTrackEnvelope();
			pitchModAmpEnvelope.decode(buffer);
		}
		option = buffer.getUnsignedByte();
		if (option != 0) {
			buffer.offset--;
			volumeModEnvelope = new SoundTrackEnvelope();
			volumeModEnvelope.decode(buffer);
			volumeModAmpEnvelope = new SoundTrackEnvelope();
			volumeModAmpEnvelope.decode(buffer);
		}
		option = buffer.getUnsignedByte();
		if (option != 0) {
			buffer.offset--;
			gatingReleaseEnvelope = new SoundTrackEnvelope();
			gatingReleaseEnvelope.decode(buffer);
			gatingAttackEnvelope = new SoundTrackEnvelope();
			gatingAttackEnvelope.decode(buffer);
		}
		for (int oscillId = 0; oscillId < 10; oscillId++) {
			int volume = buffer.getSmartB();
			if (volume == 0) {
				break;
			}
			oscillVolume[oscillId] = volume;
			oscillPitchDelta[oscillId] = buffer.getSmartA();
			oscillDelay[oscillId] = buffer.getSmartB();
		}
		delayTime = buffer.getSmartB();
		delayFeedback = buffer.getSmartB();
		duration = buffer.getUnsignedLEShort();
		begin = buffer.getUnsignedLEShort();
		filter = new SoundFilter();
		filterEnvelope = new SoundTrackEnvelope();
		filter.decode(buffer, false, filterEnvelope);
	}
}
