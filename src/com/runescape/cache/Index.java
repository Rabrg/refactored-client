package com.runescape.cache;

import java.io.IOException;
import java.io.RandomAccessFile;

public class Index {

	static byte[] buffer = new byte[520];
	protected RandomAccessFile dataFile;
	protected RandomAccessFile indexFile;
	protected int storeId;

	public Index(RandomAccessFile dataFile, RandomAccessFile indexFile, int storeId) {
		this.storeId = storeId;
		this.dataFile = dataFile;
		this.indexFile = indexFile;
	}

	public synchronized byte[] get(int index) {
		try {
			seek(indexFile, index * 6);
			int fileSize;
			for (int indexPart = 0; indexPart < 6; indexPart += fileSize) {
				fileSize = indexFile.read(Index.buffer, indexPart, 6 - indexPart);
				if (fileSize == -1) {
					return null;
				}
			}
			fileSize = ((Index.buffer[0] & 0xff) << 16) + ((Index.buffer[1] & 0xff) << 8) + (Index.buffer[2] & 0xff);
			int sector = ((Index.buffer[3] & 0xff) << 16) + ((Index.buffer[4] & 0xff) << 8) + (Index.buffer[5] & 0xff);
			if (fileSize < 0) {
				return null;
			}
			if (sector <= 0 || sector > dataFile.length() / 520L) {
				return null;
			}
			byte[] fileBuffer = new byte[fileSize];
			int read = 0;
			int bytesRead3 = 0;
			while (read < fileSize) {
				if (sector == 0) {
					return null;
				}
				seek(dataFile, sector * 520);
				int bytesRead = 0;
				int remaining = fileSize - read;
				if (remaining > 512) {
					remaining = 512;
				}
				int index_;
				for (; bytesRead < remaining + 8; bytesRead += index_) {
					index_ = dataFile.read(Index.buffer, bytesRead, remaining + 8 - bytesRead);
					if (index_ == -1) {
						return null;
					}
				}
				index_ = ((Index.buffer[0] & 0xff) << 8) + (Index.buffer[1] & 0xff);
				int bytesRead_ = ((Index.buffer[2] & 0xff) << 8) + (Index.buffer[3] & 0xff);
				int nextSector = ((Index.buffer[4] & 0xff) << 16) + ((Index.buffer[5] & 0xff) << 8)
						+ (Index.buffer[6] & 0xff);
				int currentCache = Index.buffer[7] & 0xff;
				if (index_ != index || bytesRead_ != bytesRead3 || currentCache != storeId) {
					return null;
				}
				if (nextSector < 0 || nextSector > dataFile.length() / 520L) {
					return null;
				}
				for (int offset = 0; offset < remaining; offset++) {
					fileBuffer[read++] = Index.buffer[offset + 8];
				}
				sector = nextSector;
				bytesRead3++;
			}
			return fileBuffer;
		} catch (IOException ioexception) {
			return null;
		}
	}

	public synchronized boolean put(int length, byte[] buffer, int index) {
		boolean exists = put(true, index, length, buffer);
		if (!exists) {
			exists = put(false, index, length, buffer);
		}
		return exists;
	}

	private synchronized boolean put(boolean bool, int index, int length, byte[] bs) {
		try {
			int sector;
			if (bool) {
				seek(indexFile, index * 6);
				int len;
				for (int offset = 0; offset < 6; offset += len) {
					len = indexFile.read(Index.buffer, offset, 6 - offset);
					if (len == -1) {
						return false;
					}
				}
				sector = ((Index.buffer[3] & 0xff) << 16) + ((Index.buffer[4] & 0xff) << 8) + (Index.buffer[5] & 0xff);
				if (sector <= 0 || sector > dataFile.length() / 520L) {
					return false;
				}
			} else {
				sector = (int) ((dataFile.length() + 519L) / 520L);
				if (sector == 0) {
					sector = 1;
				}
			}
			Index.buffer[0] = (byte) (length >> 16);
			Index.buffer[1] = (byte) (length >> 8);
			Index.buffer[2] = (byte) length;
			Index.buffer[3] = (byte) (sector >> 16);
			Index.buffer[4] = (byte) (sector >> 8);
			Index.buffer[5] = (byte) sector;
			seek(indexFile, index * 6);
			indexFile.write(Index.buffer, 0, 6);
			int written = 0;
			int zero = 0;
			while (written < length) {
				int nextSector = 0;
				if (bool) {
					seek(dataFile, sector * 520);
					int currentFile;
					int idx;
					for (idx = 0; idx < 8; idx += currentFile) {
						currentFile = dataFile.read(Index.buffer, idx, 8 - idx);
						if (currentFile == -1) {
							break;
						}
					}
					if (idx == 8) {
						currentFile = ((Index.buffer[0] & 0xff) << 8) + (Index.buffer[1] & 0xff);
						int currentPart = ((Index.buffer[2] & 0xff) << 8) + (Index.buffer[3] & 0xff);
						nextSector = ((Index.buffer[4] & 0xff) << 16) + ((Index.buffer[5] & 0xff) << 8)
								+ (Index.buffer[6] & 0xff);
						int currentCache = Index.buffer[7] & 0xff;
						if (currentFile != index || currentPart != zero || currentCache != storeId) {
							return false;
						}
						if (nextSector < 0 || nextSector > dataFile.length() / 520L) {
							return false;
						}
					}
				}
				if (nextSector == 0) {
					bool = false;
					nextSector = (int) ((dataFile.length() + 519L) / 520L);
					if (nextSector == 0) {
						nextSector++;
					}
					if (nextSector == sector) {
						nextSector++;
					}
				}
				if (length - written <= 512) {
					nextSector = 0;
				}
				Index.buffer[0] = (byte) (index >> 8);
				Index.buffer[1] = (byte) index;
				Index.buffer[2] = (byte) (zero >> 8);
				Index.buffer[3] = (byte) zero;
				Index.buffer[4] = (byte) (nextSector >> 16);
				Index.buffer[5] = (byte) (nextSector >> 8);
				Index.buffer[6] = (byte) nextSector;
				Index.buffer[7] = (byte) storeId;
				seek(dataFile, sector * 520);
				dataFile.write(Index.buffer, 0, 8);
				int remaining = length - written;
				if (remaining > 512) {
					remaining = 512;
				}
				dataFile.write(bs, written, remaining);
				written += remaining;
				sector = nextSector;
			}
			return true;
		} catch (IOException e) {
			return false;
		}
	}

	public synchronized void seek(RandomAccessFile dataFile, int position) throws IOException {
		if (position < 0 || position > 62914560) {
			System.out.println("Badseek - pos:" + position + " len:" + dataFile.length());
			position = 62914560;
			try {
				Thread.sleep(1000L);
			} catch (Exception exception) {
				/* empty */
			}
		}
		dataFile.seek(position);
	}
}
