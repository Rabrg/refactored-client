package com.runescape.util;

public class TextUtils {

	private static final char[] VALID_CHARACTERS = { '_', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l',
			'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3', '4', '5', '6',
			'7', '8', '9' };

	public static long nameToLong(String name) {
		long longName = 0L;
		for (int i = 0; i < name.length() && i < 12; i++) {
			char c = name.charAt(i);
			longName *= 37L;
			if (c >= 'A' && c <= 'Z') {
				longName += (1 + c) - 65;
			} else if (c >= 'a' && c <= 'z') {
				longName += (1 + c) - 97;
			} else if (c >= '0' && c <= '9') {
				longName += (27 + c) - 48;
			}
		}
		while (longName % 37L == 0L && longName != 0L) {
			longName /= 37L;
		}
		return longName;
	}

	public static String longToName(long longName) {
		if (longName <= 0L || longName >= 6582952005840035281L) {
			return "invalid_name";
		}
		if (longName % 37L == 0L) {
			return "invalid_name";
		}
		int length = 0;
		char name[] = new char[12];
		while (longName != 0L) {
			long l1 = longName;
			longName /= 37L;
			name[11 - length++] = TextUtils.VALID_CHARACTERS[(int) (l1 - longName * 37L)];
		}
		return new String(name, 12 - length, length);
	}

	public static long spriteToHash(String sprite) {
		sprite = sprite.toUpperCase();
		long spriteHash = 0L;
		for (int index = 0; index < sprite.length(); index++) {
			spriteHash = spriteHash * 61L + sprite.charAt(index) - 32L;
			spriteHash = spriteHash + (spriteHash >> 56) & 0xffffffffffffffL;
		}
		return spriteHash;
	}

	public static int fileToHash(String name) {
		int hash = 0;
		name = name.toUpperCase();
		for (int j = 0; j < name.length(); j++) {
			hash = (hash * 61 + name.charAt(j)) - 32;
		}
		return hash;
	}

	public static String decodeAddress(int address) {
		return String.valueOf(address >> 24 & 0xff) + "." + (address >> 16 & 0xff) + "." + (address >> 8 & 0xff) + "."
				+ (address & 0xff);
	}

	public static String formatName(String name) {
		if (name.length() > 0) {
			final char formatedName[] = name.toCharArray();
			for (int j = 0; j < formatedName.length; j++) {
				if (formatedName[j] == '_') {
					formatedName[j] = ' ';
					if ((j + 1 < formatedName.length) && (formatedName[j + 1] >= 'a') && (formatedName[j + 1] <= 'z')) {
						formatedName[j + 1] = (char) ((formatedName[j + 1] + 65) - 97);
					}
				}
			}

			if ((formatedName[0] >= 'a') && (formatedName[0] <= 'z')) {
				formatedName[0] = (char) ((formatedName[0] + 65) - 97);
			}
			return new String(formatedName);
		} else {
			return name;
		}
	}

	public static String censorPassword(String password) {
		StringBuffer censoredPassword = new StringBuffer();
		for (int index = 0; index < password.length(); index++) {
			censoredPassword.append("*");
		}
		return censoredPassword.toString();
	}
}
