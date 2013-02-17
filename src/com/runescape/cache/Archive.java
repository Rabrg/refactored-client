package com.runescape.cache;

import com.runescape.cache.bzip.BZip2Decompressor;
import com.runescape.net.Buffer;
import com.runescape.util.TextUtils;

/**
 * Represents an archive in the cache.
 */
public class Archive {

	public byte[] archiveBuffer;
	public int dataSize;
	public int[] nameHashes;
	public int[] uncompressedSizes;
	public int[] compressedSizes;
	public int[] startOffsets;
	private boolean compressed;

	/**
	 * Creates the archive.
	 * 
	 * @param dataBuffer
	 *            The buffer of the archive.
	 */
	public Archive(byte[] dataBuffer) {
		Buffer buffer = new Buffer(dataBuffer);
		int uncompressed = buffer.get24BitInt();
		int compressed = buffer.get24BitInt();
		if (compressed != uncompressed) {
			byte[] data = new byte[uncompressed];
			BZip2Decompressor.decompress(data, uncompressed, dataBuffer, compressed, 6);
			archiveBuffer = data;
			buffer = new Buffer(archiveBuffer);
			this.compressed = true;
		} else {
			archiveBuffer = dataBuffer;
			this.compressed = false;
		}
		dataSize = buffer.getUnsignedLEShort();
		nameHashes = new int[dataSize];
		uncompressedSizes = new int[dataSize];
		compressedSizes = new int[dataSize];
		startOffsets = new int[dataSize];
		int offset = buffer.offset + dataSize * 10;
		for (int index = 0; index < dataSize; index++) {
			nameHashes[index] = buffer.getInt();
			uncompressedSizes[index] = buffer.get24BitInt();
			compressedSizes[index] = buffer.get24BitInt();
			startOffsets[index] = offset;
			offset += compressedSizes[index];
		}
	}

	/**
	 * Gets a file by its name.
	 * 
	 * @param file
	 *            The file name.
	 * @return The file contents.
	 */
	public byte[] getFile(String file) {
		byte dataBuffer[] = null;
		int hash = TextUtils.fileToHash(file);
		for (int index = 0; index < dataSize; index++) {
			if (nameHashes[index] == hash) {
				if (dataBuffer == null) {
					dataBuffer = new byte[uncompressedSizes[index]];
				}
				if (!compressed) {
					BZip2Decompressor.decompress(dataBuffer, uncompressedSizes[index], archiveBuffer,
							compressedSizes[index], startOffsets[index]);
				} else {
					System.arraycopy(archiveBuffer, startOffsets[index], dataBuffer, 0, uncompressedSizes[index]);
				}
				return dataBuffer;
			}
		}
		return null;
	}
}
