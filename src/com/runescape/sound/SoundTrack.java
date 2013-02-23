package com.runescape.sound;

import com.runescape.net.Buffer;

public class SoundTrack {

	private static SoundTrack[] tracks = new SoundTrack[5000];
	public static int[] trackDelays = new int[5000];
	private static byte[] _buffer;
	private static Buffer buffer;
	private final SoundTrackInstrument[] instruments = new SoundTrackInstrument[10];
	private int loopBegin;
	private int loopEnd;

	public static final void load(Buffer buffer) {
		SoundTrack._buffer = new byte[441000];
		SoundTrack.buffer = new Buffer(SoundTrack._buffer);
		SoundTrackInstrument.initialize();
		while (true) {
			int trackId = buffer.getUnsignedLEShort();
			if (trackId == 65535) {
				break;
			}
			SoundTrack.tracks[trackId] = new SoundTrack();
			SoundTrack.tracks[trackId].decode(buffer);
			SoundTrack.trackDelays[trackId] = SoundTrack.tracks[trackId].delay();
		}
	}

	public static final Buffer data(int trackId, int loops) {
		if (SoundTrack.tracks[trackId] != null) {
			SoundTrack soundtrack = SoundTrack.tracks[trackId];
			return soundtrack.encode(loops);
		}
		return null;
	}

	private final void decode(Buffer buffer) {
		for (int instrument = 0; instrument < 10; instrument++) {
			int active = buffer.getUnsignedByte();
			if (active != 0) {
				buffer.offset--;
				instruments[instrument] = new SoundTrackInstrument();
				instruments[instrument].decode(buffer);
			}
		}
		loopBegin = buffer.getUnsignedLEShort();
		loopEnd = buffer.getUnsignedLEShort();
	}

	private final int delay() {
		int delay = 9999999;
		for (int instrument = 0; instrument < 10; instrument++) {
			if (instruments[instrument] != null && instruments[instrument].begin / 20 < delay) {
				delay = instruments[instrument].begin / 20;
			}
		}
		if (loopBegin < loopEnd && loopBegin / 20 < delay) {
			delay = loopBegin / 20;
		}
		if (delay == 9999999 || delay == 0) {
			return 0;
		}
		for (int instrument = 0; instrument < 10; instrument++) {
			if (instruments[instrument] != null) {
				instruments[instrument].begin -= delay * 20;
			}
		}
		if (loopBegin < loopEnd) {
			loopBegin -= delay * 20;
			loopEnd -= delay * 20;
		}
		return delay;
	}

	private final Buffer encode(int loops) {
		int size = mix(loops);
		SoundTrack.buffer.offset = 0;
		SoundTrack.buffer.putInt(1380533830);
		SoundTrack.buffer.putLEInt(36 + size);
		SoundTrack.buffer.putInt(1463899717);
		SoundTrack.buffer.putInt(1718449184);
		SoundTrack.buffer.putLEInt(16);
		SoundTrack.buffer.putLEShort(1);
		SoundTrack.buffer.putLEShort(1);
		SoundTrack.buffer.putLEInt(22050);
		SoundTrack.buffer.putLEInt(22050);
		SoundTrack.buffer.putLEShort(1);
		SoundTrack.buffer.putLEShort(8);
		SoundTrack.buffer.putInt(1684108385);
		SoundTrack.buffer.putLEInt(size);
		SoundTrack.buffer.offset += size;
		return SoundTrack.buffer;
	}

	private final int mix(int loops) {
		int _dur = 0;
		for (int instrument = 0; instrument < 10; instrument++) {
			if (instruments[instrument] != null
					&& instruments[instrument].duration + instruments[instrument].begin > _dur) {
				_dur = instruments[instrument].duration + instruments[instrument].begin;
			}
		}
		if (_dur == 0) {
			return 0;
		}
		int nS = 22050 * _dur / 1000;
		int loopBegin = 22050 * this.loopBegin / 1000;
		int loopEnd = 22050 * this.loopEnd / 1000;
		if (loopBegin < 0 || loopBegin > nS || loopEnd < 0 || loopEnd > nS || loopBegin >= loopEnd) {
			loops = 0;
		}
		int length = nS + (loopEnd - loopBegin) * (loops - 1);
		for (int position = 44; position < length + 44; position++) {
			SoundTrack._buffer[position] = (byte) -128;
		}
		for (int instrument = 0; instrument < 10; instrument++) {
			if (instruments[instrument] != null) {
				int dur = instruments[instrument].duration * 22050 / 1000;
				int offset = instruments[instrument].begin * 22050 / 1000;
				int[] samples = instruments[instrument].synthesize(dur, instruments[instrument].duration);
				for (int position = 0; position < dur; position++) {
					SoundTrack._buffer[position + offset + 44] += (byte) (samples[position] >> 8);
				}
			}
		}
		if (loops > 1) {
			loopBegin += 44;
			loopEnd += 44;
			nS += 44;
			length += 44;
			int offset = length - nS;
			for (int position = nS - 1; position >= loopEnd; position--) {
				SoundTrack._buffer[position + offset] = SoundTrack._buffer[position];
			}
			for (int loopCounter = 1; loopCounter < loops; loopCounter++) {
				offset = (loopEnd - loopBegin) * loopCounter;
				for (int position = loopBegin; position < loopEnd; position++) {
					SoundTrack._buffer[position + offset] = SoundTrack._buffer[position];
				}
			}
			length -= 44;
		}
		return length;
	}
}
