package com.jagex.runescape.net;

import com.jagex.runescape.collection.CacheableNode;
import com.jagex.runescape.collection.LinkedList;

import java.math.BigInteger;

public class Buffer extends CacheableNode {
	/**
	 * The current payload of this buffer.
	 */
	public byte[] payload;

	/**
	 * The current read/write index of this buffer.
	 */
	public int offset;

	/**
	 * The bit offset, used for bit operations.
	 */
	public int bitOffset;

	/**
	 * This seems useless, it is never accessed throughout the client except where
	 * it is initiated.
	 */
	private static int[] CRC_TABLE = new int[256];

	/**
	 * A lookup table of bitmasks used for bit access.
	 */
	private static final int[] BIT_MASKS;

	/**
	 * The {@code ISAACCipher} used to encrypt packet opcodes sent by this buffer.
	 */
	public ISAACCipher isaacCipher;

	/**
	 * The amount of available @{code Buffer}s with capacity of 100.
	 */
	private static int availableMode0Buffers;

	/**
	 * The amount of available {@code Buffer}s with a capacity of 5,000.
	 */
	private static int availableMode1Buffers;

	/**
	 * The amount of available {@code Bufer}s with a capacity of 30,000.
	 */
	private static int availableMode2Buffers;

	/**
	 * A pool of lazily allocated {@code Buffer}s that have a capacity of 100.
	 */
	private static LinkedList mode0Pool;

	/**
	 * A pool of lazily allocated {@code Buffer}s that have a capacity of 5,000.
	 */
	private static LinkedList mode1Pool;

	/**
	 * A pool of lazily allocated {@code Buffer}s that have a capacity of 30,000.
	 */
	private static LinkedList mode2Pool;

	/**
	 * Returns a {@code Buffer} that has been allocated for the specified {@code sizeMode}.
	 *
	 * The following is a table of the different possible {@code sizeMode} combinations.
	 *
	 * <table summary="Size Mode and Buffer Capacity chart" border="1">
	 * 	<tr>
	 * 	    <td>Size Mode</td>
	 * 	    <td>0</td>
	 * 	    <td>1</td>
	 * 	    <td>2 (default)</td>
	 * 	</tr>
	 * 	<tr>
	 * 	    <td>Buffer Capacity</td>
	 * 	    <td>100</td>
	 * 	    <td>5,000</td>
	 * 	    <td>30,000</td>
	 * 	</tr>
	 * </table>
	 *
	 * @param sizeMode the sizeMode used to determine the type of buffer to retrieve
	 * @return a lazily-allocated {@code Buffer}
	 */
	public static Buffer newPooledBuffer(int sizeMode) {
		synchronized (Buffer.mode1Pool) {
			Buffer buffer = null;
			if (sizeMode == 0 && Buffer.availableMode0Buffers > 0) {
				Buffer.availableMode0Buffers--;
				buffer = (Buffer) Buffer.mode0Pool.popTail();
			} else if (sizeMode == 1 && Buffer.availableMode1Buffers > 0) {
				Buffer.availableMode1Buffers--;
				buffer = (Buffer) Buffer.mode1Pool.popTail();
			} else if (sizeMode == 2 && Buffer.availableMode2Buffers > 0) {
				Buffer.availableMode2Buffers--;
				buffer = (Buffer) Buffer.mode2Pool.popTail();
			}
			if (buffer != null) {
				buffer.offset = 0;
				return buffer;
			}
		}
		Buffer buffer = new Buffer(null);
		buffer.offset = 0;
		if (sizeMode == 0) {
			buffer.payload = new byte[100];
		} else if (sizeMode == 1) {
			buffer.payload = new byte[5000];
		} else {
			buffer.payload = new byte[30000];
		}
		return buffer;
	}

	/**
	 * Wrap the specified {@code byte[]} with this {@code Buffer}.
	 *
	 * @param buffer the {@code byte[]} to be wrapped by this buffer
	 */
	public Buffer(byte[] buffer) {
		this.payload = buffer;
		this.offset = 0;
	}

	/**
	 * Puts the specified {@code opcode} as a packet opcode in this buffer.
	 *
	 * This function takes the next integer produced by the {@code ISAACCipher}
	 * and adds it to the specified {@code opcode}. This, in effect, encrypts the
	 * opcode to make it more difficult to sniff out actual packet opcodes.
	 *
	 * It is the server's responsibility to have correctly setup their receiving
	 * {@code ISAACCipher} to mimic the results of this sending {@code ISAACCipher}.
	 *
	 * @param opcode the opcode of the packet
	 */
	public void putOpcode(int opcode) {
		payload[offset++] = (byte) (opcode + isaacCipher.nextInt());
	}

	/**
	 * Places the specified {@code value} into the underlying buffer as
	 * an 8 bit data-type.
	 *
	 * The value written is just an 8 bit data-type. Therefore only the low 8
	 * bits are significant.
	 *
	 * @param value the value to place into the buffer
	 */
	public void put(int value) {
		payload[offset++] = (byte) value;
	}

	/**
	 * Places the specified {@code value} into the underlying buffer as
	 * a 16 bit data-type.
	 *
	 * The value written is just a 16 bit data-type. Therefore only the
	 * lower 16 bits are significant.
	 *
	 * @param value the value to place into the buffer
	 */
	public void putShort(int value) {
		payload[offset++] = (byte) (value >> 8);
		payload[offset++] = (byte) value;
	}

	/**
	 * Places the specified {@code value} into the underlying buffer as
	 * a 16 bit data-type in Little Endian order.
	 *
	 * The value written is just a 16 bit data-type. Therefore only the
	 * lower 16 bits are significant.
	 *
	 * Little Endian is a byte order where the value is written into
	 * the buffer from least-significant byte to most-significant byte.
	 *
	 * @param value the value to place into the buffer
	 */
	public void putLEShort(int value) {
		payload[offset++] = (byte) value;
		payload[offset++] = (byte) (value >> 8);
	}

	/**
	 * Places the specified {@code value} into the underlying buffer as
	 * a 24 bit data-type.
	 *
	 * The value written is just a 24 bit data-type. Therefore only the
	 * lower 24 bits are significant.
	 *
	 * @param value the value to place into the buffer
	 */
	public void put24BitInt(int value) {
		payload[offset++] = (byte) (value >> 16);
		payload[offset++] = (byte) (value >> 8);
		payload[offset++] = (byte) value;
	}

	/**
	 * Places the specified {@code value} into the underlying buffer as
	 * a 32 bit data-type.
	 *
	 * @param value the value to place into the buffer
	 */
	public void putInt(int value) {
		payload[offset++] = (byte) (value >> 24);
		payload[offset++] = (byte) (value >> 16);
		payload[offset++] = (byte) (value >> 8);
		payload[offset++] = (byte) value;
	}

	/**
	 * Places the specified {@code value} into the underlying buffer as
	 * a 32 bit data-type in Little Endian order.
	 *
	 * Little Endian is a byte order where the value is written into
	 * the buffer from least-significant byte to most-significant byte.
	 *
	 * @param value the value to place into the buffer
	 */
	public void putLEInt(int value) {
		payload[offset++] = (byte) value;
		payload[offset++] = (byte) (value >> 8);
		payload[offset++] = (byte) (value >> 16);
		payload[offset++] = (byte) (value >> 24);
	}

	/**
	 * Places the specified {@code value} into the underlying buffer as
	 * a 64 bit data-type.
	 *
	 * @param value the value to place into the buffer
	 */
	public void putLong(long value) {
		payload[offset++] = (byte) (int) (value >> 56);
		payload[offset++] = (byte) (int) (value >> 48);
		payload[offset++] = (byte) (int) (value >> 40);
		payload[offset++] = (byte) (int) (value >> 32);
		payload[offset++] = (byte) (int) (value >> 24);
		payload[offset++] = (byte) (int) (value >> 16);
		payload[offset++] = (byte) (int) (value >> 8);
		payload[offset++] = (byte) (int) value;
	}

	/**
	 * /**
	 * Places the specified {@link java.lang.String} ino the underlying buffer.
	 *
	 * The {@link java.lang.String} written into the buffer with each character
	 * representing a single 8 bit value. The end of the {@link java.lang.String}
	 * is signified by the value 10.
	 *
	 * @param value the value to place into the buffer
	 */
	public void putString(String value) {
		System.arraycopy(value.getBytes(), 0, payload, offset, value.length());
		offset += value.length();
		payload[offset++] = (byte) 10;
	}

	/**
	 * Places the specified {@code byte[]} into the underlying buffer
	 * starting at {@code offset} and writing {@code length} bytes.
	 *
	 * @param src the {@code byte[]} to insert into this buffer
	 * @param offset the offset to start reading from src
	 * @param length the number of bytes to insert
	 */
	public void putBytes(byte[] src, int offset, int length) {
		for (int i = offset; i < offset + length; i++) {
			payload[this.offset++] = src[i];
		}
	}

	/**
	 * Puts the specified {@code size} as the packet size in this buffer.
	 *
	 * @param size the size of the data being sent
	 */
	public void putSizeByte(int size) {
		payload[offset - size - 1] = (byte) size;
	}

	/**
	 * @return the next unsigned 8-bit data-type read from the underlying buffer.
	 */
	public int getUnsignedByte() {
		return payload[offset++] & 0xff;
	}

	/**
	 * @return the next 8-bit data-type read from the underlying buffer.
	 */
	public byte get() {
		return payload[offset++];
	}

	/**
	 * @return the next unsigned 16-bit data-type read from the underlying buffer
	 * in Little Endian order.
	 */
	public int getUnsignedLEShort() {
		offset += 2;
		return ((payload[offset - 1] & 0xff) << 8) + (payload[offset - 2] & 0xff);
	}

	/**
	 * @return the next unsigned 16-bit data-type read from the underlying buffer.
	 */
	public int getUnsignedShort() {
		offset += 2;
		return ((payload[offset - 2] & 0xff) << 8) + (payload[offset - 1] & 0xff);
	}

	/**
	 * @return the next 16-bit data-type read from the underlying buffer.
	 */
	public int getShort() {
		offset += 2;
		int i = ((payload[offset - 2] & 0xff) << 8) + (payload[offset - 1] & 0xff);
		if (i > 32767) {
			i -= 65536;
		}
		return i;
	}

	/**
	 * @return the next unsigned 24-bit data-type read from the underlying buffer.
	 */
	public int get24BitInt() {
		offset += 3;
		return ((payload[offset - 3] & 0xff) << 16) + ((payload[offset - 2] & 0xff) << 8)
				+ (payload[offset - 1] & 0xff);
	}

	/**
	 * @return the next unsigned 32-bit data-type read from the underlying buffer.
	 */
	public int getInt() {
		offset += 4;
		return ((payload[offset - 4] & 0xff) << 24) + ((payload[offset - 3] & 0xff) << 16)
				+ ((payload[offset - 2] & 0xff) << 8) + (payload[offset - 1] & 0xff);
	}

	/**
	 * @return the next unsigned 64-bit data-type read from the underlying buffer.
	 */
	public long getLong() {
		long first = getInt() & 0xffffffffL;
		long second = getInt() & 0xffffffffL;
		return (first << 32) + second;
	}

	/**
	 * A {@link java.lang.String} is read from the underlying buffer by reading in bytes
	 * until a byte with the value '10' is read.  Once this value is read, the
	 * {@code byte[]} is then turned into a new {@link java.lang.String}.
	 *
	 * @return the next string read from the underlying buffer.
	 */
	public String getString() {
		int originalOffset = offset;
		while (payload[offset++] != 10) {
			/* empty */
		}
		return new String(payload, originalOffset, offset - originalOffset - 1);
	}

	/**
	 * @return a {@link java.lang.String} that has been converted into a {@code byte[]}.
	 * @see #getString()
	 */
	public byte[] getStringAsBytes() {
		int originalOffset = offset;
		while (payload[offset++] != 10) {
			/* empty */
		}
		byte[] value = new byte[offset - originalOffset - 1];
		for (int offset = originalOffset; offset < this.offset - 1; offset++) {
			value[offset - originalOffset] = payload[offset];
		}
		return value;
	}

	/**
	 * Read {@code length} bytes from the underlying buffer, putting the result
	 * in {@code out} starting at index {@code offset}.
	 *
	 * @param out the {@code byte[]} to write the data in
	 * @param offset the offset to start writing in the {@code byte[]}
	 * @param length the amount of bytes to put into the {@code byte[]}
	 */
	public void getBytes(byte[] out, int offset, int length) {
		for (int i = offset; i < offset + length; i++) {
			out[i] = payload[this.offset++];
		}
	}

	/**
	 * Initialize bit access in this {@code Buffer}. This function
	 * sets the {@code bitOffset} to the current {@code offset}
	 * multiplied by 8.
	 */
	public void initBitAccess() {
		bitOffset = offset * 8;
	}

	/**
	 *
	 * @param size
	 * @return
	 */
	public int getBits(int size) {
		int byteOffset = bitOffset >> 3;
		int bitsToRead = 8 - (bitOffset & 0x7);
		int value = 0;
		bitOffset += size;
		for (; size > bitsToRead; bitsToRead = 8) {
			value += (payload[byteOffset++] & Buffer.BIT_MASKS[bitsToRead]) << size - bitsToRead;
			size -= bitsToRead;
		}
		if (size == bitsToRead) {
			value += payload[byteOffset] & Buffer.BIT_MASKS[bitsToRead];
		} else {
			value += payload[byteOffset] >> bitsToRead - size & Buffer.BIT_MASKS[size];
		}
		return value;
	}

	/**
	 * Finalize bit access. This function just sets the current byte {@code offset} to
	 * {@code bitOffset} divided by 8. If the resulting calculation is a decimal, then it
	 * always rounds to the next number.
	 */
	public void finishBitAccess() {
		offset = (bitOffset + 7) / 8;
	}


	/**
	 * Read either an unsigned 8-bit data-type or an unsigned 16-bit data-type.
	 *
	 * This function peeks at the next unsigned {@code byte} stored in the buffer. If that
	 * value is less than 128, the {@code value} is calculated to be 64 subtracted
	 * from the initial unsigned {@code byte} read.
	 *
	 * If the initial unsigned {@code byte} read is greater-than or equals-to 128, the result
	 * is 49152 subtracted from the next unsigned 16-bit data-type read from the stream.
	 *
	 * @return the value received
	 */
	public int getSmartA() {
		int value = payload[offset] & 0xff;
		if (value < 128) {
			return getUnsignedByte() - 64;
		}
		return getUnsignedShort() - 49152;
	}

	/**
	 * This function is similar to {@link #getSmartA} except the {@code value}s
	 * will be returned with different arithmetic applied.
	 *
	 * If the initial {@code byte} is less than 128, the unsigned {@code byte} is returned.
	 *
	 * If the initial {@code byte} is greather-than or equal-to 128, the {@code value} is
	 * 32768 subtracted from the next 16-bit data type received.
	 *
	 * @return the value received
	 * @see #getSmartA
	 */
	public int getSmartB() {
		int value = payload[offset] & 0xff;
		if (value < 128) {
			return getUnsignedByte();
		}
		return getUnsignedShort() - 32768;
	}

	/**
	 * Applies the RSA algorithm to the underlying {@code byte[]}.
	 *
	 * @param exponent the public exponent for the RSA encryption
	 * @param modulus the modulus for the RSA encryption
	 */
	public void applyRSA(BigInteger exponent, BigInteger modulus) {
		int originalOffset = offset;
		offset = 0;
		byte[] decodedBuffer = new byte[originalOffset];
		getBytes(decodedBuffer, 0, originalOffset);
		BigInteger decodedBigInteger = new BigInteger(decodedBuffer);
		BigInteger encodedBigInteger = decodedBigInteger.modPow(exponent, modulus);
		byte[] encodedBuffer = encodedBigInteger.toByteArray();
		offset = 0;
		put(encodedBuffer.length);
		putBytes(encodedBuffer, 0, encodedBuffer.length);
	}

	/**
	 * Places the {@code value} into the underlying {@code byte[]}.
	 *
	 * The {@code value} is negated before being placed into the
	 * {@code byte[]}.
	 *
	 * @param value the value to place into the {@code byte[]}
	 */
	public void putByteC(int value) {
		payload[offset++] = (byte) -value;
	}

	/**
	 * Places the {@code value} into the underlying {@code byte[]}.
	 *
	 * The {@code value} is subtracted from 128 before being placed
	 * into the {@code byte[]}.
	 *
	 * @param value he value to place into the {@code byte[]}
	 */
	public void putByteS(int value) {
		payload[offset++] = (byte) (128 - value);
	}

	/**
	 * @return 128 subtracted from the next byte received from the {@code byte[]} represented
	 *         between the values 0 and 255.
	 */
	public int getUnsignedByteA() {
		return payload[offset++] - 128 & 0xff;
	}

	/**
	 * @return the inverse of the next byte received from the {@code byte[]} represented
	 *         between the values 0 and 255.
	 */
	public int getUnsignedByteC() {
		return -payload[offset++] & 0xff;
	}

	/**
	 * @return the next byte received from the {@code byte[]} subtracted from 128 represented
	 *         between the values 0 and 255.
	 */
	public int getUnsignedByteS() {
		return 128 - payload[offset++] & 0xff;
	}

	/**
	 * @return the inverse of the next byte received from the {@code byte[]}
	 */
	public byte getByteC() {
		return (byte) -payload[offset++];
	}

	/**
	 * @return the next byte received from the {@code byte[]} subtracted from 128
	 */
	public byte getByteS() {
		return (byte) (128 - payload[offset++]);
	}

	/**
	 * Places the specified {@code value} into the underlying {@code byte[]}.
	 *
	 * The 16-bit data-type that is placed into the underlying {@code byte[]} is transformed
	 * by having 128 added to the lowest-order byte.
	 *
	 * @param value the value to be placed into the {@code byte[]}.
	 */
	public void putShortA(int value) {
		payload[offset++] = (byte) (value >> 8);
		payload[offset++] = (byte) (value + 128);
	}

	/**
	 * Places the specified {@code value} into the underlying {@code byte[]}.
	 *
	 * The 16-bit data-type that is placed into the underlying {@code byte[]} is transformed
	 * by having 128 added to the lowest-order byte written in Little Endian order.
	 *
	 * @param value the value to be placed into the {@code byte[]}.
	 */
	public void putLEShortA(int value) {
		payload[offset++] = (byte) (value + 128);
		payload[offset++] = (byte) (value >> 8);
	}

	/**
	 * @return the next unsigned 16-bit data-type read from the underlying {@code buffer} transformed
	 *         by substracting 128 from the lowest-order byte.
	 */
	public int getUnsignedShortA() {
		offset += 2;
		return ((payload[offset - 2] & 0xff) << 8) + (payload[offset - 1] - 128 & 0xff);
	}

	/**
	 * @return the next unsigned 16-bit data-type read from the underlying {@code buffer} transformed
	 *         by substracting 128 from the lowest-order byte read in Little Endian order.
	 */
	public int getUnsignedLEShortA() {
		offset += 2;
		return ((payload[offset - 1] & 0xff) << 8) + (payload[offset - 2] - 128 & 0xff);
	}

	/**
	 * @return the next 16-bit data-type read from the underlying {@code buffer} in Little Endian order.
	 */
	public int getLEShort() {
		offset += 2;
		int value = ((payload[offset - 1] & 0xff) << 8) + (payload[offset - 2] & 0xff);
		if (value > 32767) {
			value -= 65536;
		}
		return value;
	}

	/**
	 * @return the next 16-bit data-type read from the underlying {@code byte[]} in Little Endian order
	 *         transformed by subtracted 128 from the lowest-order byte.
	 */
	public int getLEShortA() {
		offset += 2;
		int value = ((payload[offset - 1] & 0xff) << 8) + (payload[offset - 2] - 128 & 0xff);
		if (value > 32767) {
			value -= 65536;
		}
		return value;
	}

	/**
	 * @return the next 32-bit data-typed read from the underlying (@code byte[]) in a Mixed Endian order.
	 */
	public int getInt2() {
		offset += 4;
		return ((payload[offset - 2] & 0xff) << 24) + ((payload[offset - 1] & 0xff) << 16)
				+ ((payload[offset - 4] & 0xff) << 8) + (payload[offset - 3] & 0xff);
	}

	/**
	 * @return the next 32-bit data-typed read from the underlying (@code byte[]) in a Mixed Endian order.
	 */
	public int getInt1() {
		offset += 4;
		return ((payload[offset - 3] & 0xff) << 24) + ((payload[offset - 4] & 0xff) << 16)
				+ ((payload[offset - 1] & 0xff) << 8) + (payload[offset - 2] & 0xff);
	}

	/**
	 * Places {@code length} bytes from {@code src} into the underlying {@code byte[]} starting at
	 * {@code offset}. Each {@code byte} is trasformed by having 128 added to it.
	 *
	 * @param src the {@code byte[]} to insert into the underlying {@code byte[]}
	 * @param offset the offset to begin reading from {@code src}
	 * @param length the number of bytes to place
	 */
	public void putBytesA(byte[] src, int offset, int length) {
		for (int index = length + offset - 1; index >= length; index--) {
			payload[this.offset++] = (byte) (src[index] + 128);
		}
	}

	/**
	 * Reads bytes from the underlying {@code byte[]}.
	 *
	 * This function places the values read from the underlying {@code byte[]} into {@code src}
	 * in a reverse order, so the first {@code byte} read from the underlying {@code byte[]} is the last
	 * byte that's placed in to {@code src}.
	 *
	 * @param src the {@code byte[]} to place the data in
	 * @param offset the offset to start writing to inside of {@code src}
	 * @param length the amount of bytes to read
	 */
	public void getBytesReversed(byte[] src, int offset, int length) {
		for (int index = offset + length - 1; index >= offset; index--) {
			src[index] = payload[this.offset++];
		}
	}

	static {
		for (int index = 0; index < 256; index++) {
			int generatedCRC = index;
			for (int count = 0; count < 8; count++) {
				if ((generatedCRC & 0x1) == 1) {
					generatedCRC = generatedCRC >>> 1 ^ ~0x12477cdf;
				} else {
					generatedCRC >>>= 1;
				}
			}
			Buffer.CRC_TABLE[index] = generatedCRC;
		}
		BIT_MASKS = new int[] { 0, 1, 3, 7, 15, 31, 63, 127, 255, 511, 1023, 2047, 4095, 8191, 16383, 32767, 65535,
				131071, 262143, 524287, 1048575, 2097151, 4194303, 8388607, 16777215, 33554431, 67108863, 134217727,
				268435455, 536870911, 1073741823, 2147483647, -1 };
		Buffer.mode0Pool = new LinkedList();
		Buffer.mode1Pool = new LinkedList();
		Buffer.mode2Pool = new LinkedList();
	}
}
