package com.runescape.net.requester;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.zip.CRC32;
import java.util.zip.GZIPInputStream;

import com.runescape.Game;
import com.runescape.cache.Archive;
import com.runescape.collection.LinkedList;
import com.runescape.collection.Queue;
import com.runescape.net.Buffer;
import com.runescape.util.SignLink;

public class OnDemandRequester extends Requester implements Runnable {

	private int extrasTotal;
	private final LinkedList sentRequests = new LinkedList();
	private int highestPriority;
	public String message = "";
	private int sinceKeepAlive;
	private long lastSocketOpen;
	private int[] regLandIndex;
	private final CRC32 crc32 = new CRC32();
	private final byte[] inputBuffer = new byte[500];
	public int cycle;
	private final byte[][] filePriorities = new byte[4][];
	private Game client;
	private final LinkedList passiveRequests = new LinkedList();
	private int offset;
	private int toRead;
	private int[] midiIndex;
	public int requestFails;
	private int[] regMapIndex;
	private int extrasLoaded;
	private boolean running = true;
	private OutputStream outputStream;
	private int[] regShouldPreload;
	private boolean expectData = false;
	private final LinkedList completed = new LinkedList();
	private final byte[] deflateOut = new byte[65000];
	private int[] animIndex;
	private final Queue immediateRequests = new Queue();
	private InputStream inputStream;
	private Socket socket;
	private final int[][] fileVersions = new int[4][];
	private final int[][] fileCrc = new int[4][];
	private int immediateRequestsSent;
	private int passiveRequestsSent;
	private final LinkedList toRequest = new LinkedList();
	private OnDemandNode onDemandNode;
	private final LinkedList wanted = new LinkedList();
	private int[] regHash;
	private byte[] modelIndex;
	private int idleCycles;

	private final boolean verify(int expectedVersion, int expectedCrc, byte[] data) {
		if (data == null || data.length < 2) {
			return false;
		}
		int length = data.length - 2;
		int version = ((data[length] & 0xff) << 8) + (data[length + 1] & 0xff);
		crc32.reset();
		crc32.update(data, 0, length);
		int crc = (int) crc32.getValue();
		if (version != expectedVersion) {
			return false;
		}
		if (crc != expectedCrc) {
			return false;
		}
		return true;
	}

	private final void handleResp() {
		try {
			int available = inputStream.available();
			if (toRead == 0 && available >= 6) {
				expectData = true;
				for (int i = 0; i < 6; i += inputStream.read(inputBuffer, i, 6 - i)) {
					;
				}
				int type = inputBuffer[0] & 0xff;
				int id = ((inputBuffer[1] & 0xff) << 8) + (inputBuffer[2] & 0xff);
				int size = ((inputBuffer[3] & 0xff) << 8) + (inputBuffer[4] & 0xff);
				int part = inputBuffer[5] & 0xff;
				onDemandNode = null;
				for (OnDemandNode ondemandnode = (OnDemandNode) sentRequests.getBack(); ondemandnode != null; ondemandnode = (OnDemandNode) sentRequests
						.getPrevious()) {
					if (ondemandnode.type == type && ondemandnode.id == id) {
						onDemandNode = ondemandnode;
					}
					if (onDemandNode != null) {
						ondemandnode.cyclesSinceSend = 0;
					}
				}
				if (onDemandNode != null) {
					idleCycles = 0;
					if (size == 0) {
						SignLink.reportError("Rej: " + type + "," + id);
						onDemandNode.buffer = null;
						if (onDemandNode.immediate) {
							synchronized (completed) {
								completed.insertBack(onDemandNode);
							}
						} else {
							onDemandNode.remove();
						}
						onDemandNode = null;
					} else {
						if (onDemandNode.buffer == null && part == 0) {
							onDemandNode.buffer = new byte[size];
						}
						if (onDemandNode.buffer == null && part != 0) {
							throw new IOException("missing start of file");
						}
					}
				}
				offset = part * 500;
				toRead = 500;
				if (toRead > size - part * 500) {
					toRead = size - part * 500;
				}
			}
			if (toRead <= 0 || available < toRead) {
				return;
			}
			expectData = true;
			byte[] buffer = inputBuffer;
			int bufferOffset = 0;
			if (onDemandNode != null) {
				buffer = onDemandNode.buffer;
				bufferOffset = offset;
			}
			for (int i = 0; i < toRead; i += inputStream.read(buffer, i + bufferOffset, toRead - i)) {
				;
			}
			if (toRead + offset >= buffer.length && onDemandNode != null) {
				if (client.stores[0] != null) {
					client.stores[onDemandNode.type + 1].put(buffer.length, buffer, onDemandNode.id);
				}
				if (!onDemandNode.immediate && onDemandNode.type == 3) {
					onDemandNode.immediate = true;
					onDemandNode.type = 93;
				}
				if (onDemandNode.immediate) {
					synchronized (completed) {
						completed.insertBack(onDemandNode);
					}
				} else {
					onDemandNode.remove();
				}
			}
			toRead = 0;
		} catch (IOException ioexception) {
			try {
				socket.close();
			} catch (Exception exception) {
				/* empty */
			}
			socket = null;
			inputStream = null;
			outputStream = null;
			toRead = 0;
		}
	}

	public final void init(Archive archive, Game client) {
		String[] versionFiles = { "model_version", "anim_version", "midi_version", "map_version" };
		for (int version = 0; version < 4; version++) {
			byte[] data = archive.getFile(versionFiles[version]);
			int versionCount = data.length / 2;
			Buffer buffer = new Buffer(data);
			fileVersions[version] = new int[versionCount];
			filePriorities[version] = new byte[versionCount];
			for (int file = 0; file < versionCount; file++) {
				fileVersions[version][file] = buffer.getUnsignedLEShort();
			}
		}
		String[] crcFiles = { "model_crc", "anim_crc", "midi_crc", "map_crc" };
		for (int crc = 0; crc < 4; crc++) {
			byte[] data = archive.getFile(crcFiles[crc]);
			int crcCount = data.length / 4;
			Buffer buffer = new Buffer(data);
			fileCrc[crc] = new int[crcCount];
			for (int file = 0; file < crcCount; file++) {
				fileCrc[crc][file] = buffer.getInt();
			}
		}
		byte[] data = archive.getFile("model_index");
		int count = fileVersions[0].length;
		modelIndex = new byte[count];
		for (int i = 0; i < count; i++) {
			if (i < data.length) {
				modelIndex[i] = data[i];
			} else {
				modelIndex[i] = (byte) 0;
			}
		}
		data = archive.getFile("map_index");
		Buffer buffer = new Buffer(data);
		count = data.length / 7;
		regHash = new int[count];
		regMapIndex = new int[count];
		regLandIndex = new int[count];
		regShouldPreload = new int[count];
		for (int reg = 0; reg < count; reg++) {
			regHash[reg] = buffer.getUnsignedLEShort();
			regMapIndex[reg] = buffer.getUnsignedLEShort();
			regLandIndex[reg] = buffer.getUnsignedLEShort();
			regShouldPreload[reg] = buffer.getUnsignedByte();
		}
		data = archive.getFile("anim_index");
		buffer = new Buffer(data);
		count = data.length / 2;
		animIndex = new int[count];
		for (int i = 0; i < count; i++) {
			animIndex[i] = buffer.getUnsignedLEShort();
		}
		data = archive.getFile("midi_index");
		buffer = new Buffer(data);
		count = data.length;
		midiIndex = new int[count];
		for (int i = 0; i < count; i++) {
			midiIndex[i] = buffer.getUnsignedByte();
		}
		this.client = client;
		this.running = true;
		this.client.startRunnable(this, 2);
	}

	public final int immediateRequestCount() {
		synchronized (immediateRequests) {
			return immediateRequests.getNodeCount();
		}
	}

	public final void stop() {
		running = false;
	}

	public final void preloadRegions(boolean members) {
		for (int reg = 0; reg < regHash.length; reg++) {
			if (members || regShouldPreload[reg] != 0) {
				setPriority((byte) 2, 3, regLandIndex[reg]);
				setPriority((byte) 2, 3, regMapIndex[reg]);
			}
		}
	}

	public final int fileCount(int file) {
		return fileVersions[file].length;
	}

	private final void sendRequest(OnDemandNode onDemandNode) {
		try {
			if (socket == null) {
				long currentTime = System.currentTimeMillis();
				if (currentTime - lastSocketOpen < 4000L) {
					return;
				}
				lastSocketOpen = currentTime;
				socket = client.openSocket(43594 + Game.portOffset);
				inputStream = socket.getInputStream();
				outputStream = socket.getOutputStream();
				outputStream.write(15);
				for (int i = 0; i < 8; i++) {
					inputStream.read();
				}
				idleCycles = 0;
			}
			inputBuffer[0] = (byte) onDemandNode.type;
			inputBuffer[1] = (byte) (onDemandNode.id >> 8);
			inputBuffer[2] = (byte) onDemandNode.id;
			if (onDemandNode.immediate) {
				inputBuffer[3] = (byte) 2;
			} else if (!client.loggedIn) {
				inputBuffer[3] = (byte) 1;
			} else {
				inputBuffer[3] = (byte) 0;
			}
			outputStream.write(inputBuffer, 0, 4);
			sinceKeepAlive = 0;
			requestFails = -10000;
		} catch (IOException ioexception) {
			try {
				socket.close();
			} catch (Exception exception) {
			}
			socket = null;
			inputStream = null;
			outputStream = null;
			toRead = 0;
			requestFails++;
		}
	}

	public final int animCount() {
		return animIndex.length;
	}

	public final void request(int type, int id) {
		if (type >= 0 && type <= fileVersions.length && id >= 0 && id <= fileVersions[type].length
				&& fileVersions[type][id] != 0) {
			synchronized (immediateRequests) {
				for (OnDemandNode onDemandNode = (OnDemandNode) immediateRequests.reverseGetFirst(); onDemandNode != null; onDemandNode = (OnDemandNode) immediateRequests
						.reverseGetNext()) {
					if (onDemandNode.type == type && onDemandNode.id == id) {
						return;
					}
				}
				OnDemandNode ondemandnode = new OnDemandNode();
				ondemandnode.type = type;
				ondemandnode.id = id;
				ondemandnode.immediate = true;
				synchronized (wanted) {
					wanted.insertBack(ondemandnode);
				}
				immediateRequests.insertHead(ondemandnode);
			}
		}
	}

	public final int modelId(int model) {
		return modelIndex[model] & 0xff;
	}

	@Override
	public final void run() {
		try {
			while (running) {
				cycle++;
				int toWait = 20;
				if (highestPriority == 0 && client.stores[0] != null) {
					toWait = 50;
				}
				Thread.sleep(toWait);
				expectData = true;
				for (int i = 0; i < 100; i++) {
					if (!expectData) {
						break;
					}
					expectData = false;
					localComplete();
					remainingRequest();
					if (immediateRequestsSent == 0 && i >= 5) {
						break;
					}
					passivesRequest();
					if (inputStream != null) {
						handleResp();
					}
				}
				boolean idle = false;
				for (OnDemandNode onDemandNode = (OnDemandNode) sentRequests.getBack(); onDemandNode != null; onDemandNode = (OnDemandNode) sentRequests
						.getPrevious()) {
					if (onDemandNode.immediate) {
						idle = true;
						onDemandNode.cyclesSinceSend++;
						if (onDemandNode.cyclesSinceSend > 50) {
							onDemandNode.cyclesSinceSend = 0;
							sendRequest(onDemandNode);
						}
					}
				}
				if (!idle) {
					for (OnDemandNode onDemandNode = (OnDemandNode) sentRequests.getBack(); onDemandNode != null; onDemandNode = (OnDemandNode) sentRequests
							.getPrevious()) {
						idle = true;
						onDemandNode.cyclesSinceSend++;
						if (onDemandNode.cyclesSinceSend > 50) {
							onDemandNode.cyclesSinceSend = 0;
							sendRequest(onDemandNode);
						}
					}
				}
				if (idle) {
					idleCycles++;
					if (idleCycles > 750) {
						socket.close();
						socket = null;
						inputStream = null;
						outputStream = null;
						toRead = 0;
					}
				} else {
					idleCycles = 0;
					message = "";
				}
				if (client.loggedIn && socket != null && outputStream != null
						&& (highestPriority > 0 || client.stores[0] == null)) {
					sinceKeepAlive++;
					if (sinceKeepAlive > 500) {
						sinceKeepAlive = 0;
						inputBuffer[0] = (byte) 0;
						inputBuffer[1] = (byte) 0;
						inputBuffer[2] = (byte) 0;
						inputBuffer[3] = (byte) 10;
						try {
							outputStream.write(inputBuffer, 0, 4);
						} catch (IOException ioexception) {
							idleCycles = 5000;
						}
					}
				}
			}
		} catch (Exception exception) {
			SignLink.reportError("od_ex " + exception.getMessage());
		}
	}

	public final void passiveRequest(int id, int type) {
		if (client.stores[0] != null && fileVersions[type][id] != 0 && filePriorities[type][id] != 0
				&& highestPriority != 0) {
			OnDemandNode onDemandNode = new OnDemandNode();
			onDemandNode.type = type;
			onDemandNode.id = id;
			onDemandNode.immediate = false;
			synchronized (passiveRequests) {
				passiveRequests.insertBack(onDemandNode);
			}
		}
	}

	public final OnDemandNode next() {
		OnDemandNode onDemandNode;
		synchronized (completed) {
			onDemandNode = (OnDemandNode) completed.popTail();
		}
		if (onDemandNode == null) {
			return null;
		}
		synchronized (immediateRequests) {
			onDemandNode.clear();
		}
		if (onDemandNode.buffer == null) {
			return onDemandNode;
		}
		int offset = 0;
		try {
			GZIPInputStream gzipinputstream = new GZIPInputStream(new ByteArrayInputStream(onDemandNode.buffer));
			while (true) {
				if (offset == deflateOut.length) {
					throw new RuntimeException("buffer overflow!");
				}
				int readByte = gzipinputstream.read(deflateOut, offset, deflateOut.length - offset);
				if (readByte == -1) {
					break;
				}
				offset += readByte;
			}
		} catch (IOException ioexception) {
			throw new RuntimeException("error unzipping");
		}
		onDemandNode.buffer = new byte[offset];
		for (int position = 0; position < offset; position++) {
			onDemandNode.buffer[position] = deflateOut[position];
		}
		return onDemandNode;
	}

	public final int regId(int indexType, int regY, int regX) {
		int localRegHash = (regX << 8) + regY;
		for (int reg = 0; reg < regHash.length; reg++) {
			if (regHash[reg] == localRegHash) {
				if (indexType == 0) {
					return regMapIndex[reg];
				}
				return regLandIndex[reg];
			}
		}
		return -1;
	}

	@Override
	public final void request(int id) {
		request(0, id);
	}

	public final void setPriority(byte priority, int type, int id) {
		if (client.stores[0] != null && fileVersions[type][id] != 0) {
			byte[] data = client.stores[type + 1].get(id);
			if (!verify(fileVersions[type][id], fileCrc[type][id], data)) {
				filePriorities[type][id] = priority;
				if (priority > highestPriority) {
					highestPriority = priority;
				}
				extrasTotal++;
			}
		}
	}

	public final boolean landIndexExists(int landIndex) {
		for (int index = 0; index < regHash.length; index++) {
			if (regLandIndex[index] == landIndex) {
				return true;
			}
		}
		return false;
	}

	private final void remainingRequest() {
		immediateRequestsSent = 0;
		passiveRequestsSent = 0;
		for (OnDemandNode onDemandNode = (OnDemandNode) sentRequests.getBack(); onDemandNode != null; onDemandNode = (OnDemandNode) sentRequests
				.getPrevious()) {
			if (onDemandNode.immediate) {
				immediateRequestsSent++;
			} else {
				passiveRequestsSent++;
			}
		}
		while (immediateRequestsSent < 10) {
			OnDemandNode onDemandNode = (OnDemandNode) toRequest.popTail();
			if (onDemandNode == null) {
				break;
			}
			if (filePriorities[onDemandNode.type][onDemandNode.id] != 0) {
				extrasLoaded++;
			}
			filePriorities[onDemandNode.type][onDemandNode.id] = (byte) 0;
			sentRequests.insertBack(onDemandNode);
			immediateRequestsSent++;
			sendRequest(onDemandNode);
			expectData = true;
		}
	}

	public final void clearPassiveRequests() {
		synchronized (passiveRequests) {
			passiveRequests.clear();
		}
	}

	private final void localComplete() {
		OnDemandNode onDemandNode;
		synchronized (wanted) {
			onDemandNode = (OnDemandNode) wanted.popTail();
		}
		while (onDemandNode != null) {
			expectData = true;
			byte[] buffer = null;
			if (client.stores[0] != null) {
				buffer = client.stores[onDemandNode.type + 1].get(onDemandNode.id);
			}
			if (!verify(fileVersions[onDemandNode.type][onDemandNode.id], fileCrc[onDemandNode.type][onDemandNode.id],
					buffer)) {
				buffer = null;
			}
			synchronized (wanted) {
				if (buffer == null) {
					toRequest.insertBack(onDemandNode);
				} else {
					onDemandNode.buffer = buffer;
					synchronized (completed) {
						completed.insertBack(onDemandNode);
					}
				}
				onDemandNode = (OnDemandNode) wanted.popTail();
			}
		}
	}

	private final void passivesRequest() {
		while (true) {
			if (immediateRequestsSent != 0) {
				break;
			}
			if (passiveRequestsSent >= 10) {
				break;
			}
			if (highestPriority == 0) {
				break;
			}
			OnDemandNode onDemandNode;
			synchronized (passiveRequests) {
				onDemandNode = (OnDemandNode) passiveRequests.popTail();
			}
			while (onDemandNode != null) {
				if (filePriorities[onDemandNode.type][onDemandNode.id] != 0) {
					filePriorities[onDemandNode.type][onDemandNode.id] = (byte) 0;
					sentRequests.insertBack(onDemandNode);
					sendRequest(onDemandNode);
					expectData = true;
					if (extrasLoaded < extrasTotal) {
						extrasLoaded++;
					}
					message = "Loading extra files - " + extrasLoaded * 100 / extrasTotal + "%";
					passiveRequestsSent++;
					if (passiveRequestsSent == 10) {
						return;
					}
				}
				synchronized (passiveRequests) {
					onDemandNode = (OnDemandNode) passiveRequests.popTail();
				}
			}
			for (int type = 0; type < 4; type++) {
				byte[] priority = filePriorities[type];
				for (int id = 0; id < priority.length; id++) {
					if (priority[id] == highestPriority) {
						priority[id] = (byte) 0;
						onDemandNode = new OnDemandNode();
						onDemandNode.type = type;
						onDemandNode.id = id;
						onDemandNode.immediate = false;
						sentRequests.insertBack(onDemandNode);
						sendRequest(onDemandNode);
						expectData = true;
						if (extrasLoaded < extrasTotal) {
							extrasLoaded++;
						}
						message = "Loading extra files - " + extrasLoaded * 100 / extrasTotal + "%";
						passiveRequestsSent++;
						if (passiveRequestsSent == 10) {
							return;
						}
					}
				}
			}
			highestPriority--;
		}
	}

	public final boolean midiIdEqualsOne(int index) {
		return midiIndex[index] == 1;
	}
}
