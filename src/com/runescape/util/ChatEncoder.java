package com.runescape.util;

import com.runescape.net.Buffer;

public class ChatEncoder {

	public static char[] message = new char[100];
	private static Buffer messageBuffer = new Buffer(new byte[100]);
	private static final char[] VALID_CHARACTERS = { ' ', 'e', 't', 'a', 'o', 'i', 'h', 'n', 's', 'r', 'd', 'l', 'u',
			'm', 'w', 'c', 'y', 'f', 'g', 'p', 'b', 'v', 'k', 'x', 'j', 'q', 'z', '0', '1', '2', '3', '4', '5', '6',
			'7', '8', '9', ' ', '!', '?', '.', ',', ':', ';', '(', ')', '-', '&', '*', '\\', '\'', '@', '#', '+', '=',
			'\u00a3', '$', '%', '\"', '[', ']' };

	public static String get(int length, Buffer buffer) {
		int count = 0;
		int validCharacterIndex = -1;
		for (int lengthCounter = 0; lengthCounter < length; lengthCounter++) {
			int character = buffer.getUnsignedByte();
			int characterBit = character >> 4 & 0xf;
			if (validCharacterIndex == -1) {
				if (characterBit < 13) {
					ChatEncoder.message[count++] = ChatEncoder.VALID_CHARACTERS[characterBit];
				} else {
					validCharacterIndex = characterBit;
				}
			} else {
				ChatEncoder.message[count++] = ChatEncoder.VALID_CHARACTERS[(validCharacterIndex << 4) + characterBit
						- 195];
				validCharacterIndex = -1;
			}
			characterBit = character & 0xf;
			if (validCharacterIndex == -1) {
				if (characterBit < 13) {
					ChatEncoder.message[count++] = ChatEncoder.VALID_CHARACTERS[characterBit];
				} else {
					validCharacterIndex = characterBit;
				}
			} else {
				ChatEncoder.message[count++] = ChatEncoder.VALID_CHARACTERS[(validCharacterIndex << 4) + characterBit
						- 195];
				validCharacterIndex = -1;
			}
		}
		boolean isSymbol = true;
		for (int messageIndex = 0; messageIndex < count; messageIndex++) {
			char c = ChatEncoder.message[messageIndex];
			if (isSymbol && c >= 'a' && c <= 'z') {
				ChatEncoder.message[messageIndex] += -32;
				isSymbol = false;
			}
			if (c == '.' || c == '!' || c == '?') {
				isSymbol = true;
			}
		}
		return new String(ChatEncoder.message, 0, count);
	}

	public static void put(String chatMessage, Buffer buffer) {
		if (chatMessage.length() > 80) {
			chatMessage = chatMessage.substring(0, 80);
		}
		chatMessage = chatMessage.toLowerCase();
		int chatMessageCharacter = -1;
		for (int index = 0; index < chatMessage.length(); index++) {
			char character = chatMessage.charAt(index);
			int validCharacterIndex = 0;
			for (int validIndex = 0; validIndex < ChatEncoder.VALID_CHARACTERS.length; validIndex++) {
				if (character == ChatEncoder.VALID_CHARACTERS[validIndex]) {
					validCharacterIndex = validIndex;
					break;
				}
			}
			if (validCharacterIndex > 12) {
				validCharacterIndex += 195;
			}
			if (chatMessageCharacter == -1) {
				if (validCharacterIndex < 13) {
					chatMessageCharacter = validCharacterIndex;
				} else {
					buffer.put(validCharacterIndex);
				}
			} else if (validCharacterIndex < 13) {
				buffer.put((chatMessageCharacter << 4) + validCharacterIndex);
				chatMessageCharacter = -1;
			} else {
				buffer.put((chatMessageCharacter << 4) + (validCharacterIndex >> 4));
				chatMessageCharacter = validCharacterIndex & 0xf;
			}
		}
		if (chatMessageCharacter == -1) {
			return;
		}
		buffer.put(chatMessageCharacter << 4);
	}

	public static String formatChatMessage(String chatMessage) {
		ChatEncoder.messageBuffer.offset = 0;
		ChatEncoder.put(chatMessage, ChatEncoder.messageBuffer);
		int offset = ChatEncoder.messageBuffer.offset;
		ChatEncoder.messageBuffer.offset = 0;
		String formatedChatMessage = ChatEncoder.get(offset, ChatEncoder.messageBuffer);
		return formatedChatMessage;
	}
}
