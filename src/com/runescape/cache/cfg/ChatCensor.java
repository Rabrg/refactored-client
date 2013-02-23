package com.runescape.cache.cfg;

import com.runescape.cache.Archive;
import com.runescape.net.Buffer;
import com.runescape.util.SignLink;

/* ChatCensor - Decompiled by JODE
 * Visit http://jode.sourceforge.net/
 */

public class ChatCensor {
	private static boolean aBoolean156;
	private static int anInt157 = 748;
	private static byte aByte161 = -117;
	private static int anInt162 = -575;
	private static boolean aBoolean163 = true;
	private static int anInt164 = -720;
	private static byte aByte166 = 4;
	private static int anInt167 = 8801;
	private static boolean aBoolean168 = true;
	private static int[] fragments;
	private static char[][] badWords;
	private static byte[][][] badBytes;
	private static char[][] domains;
	private static char[][] topLevelDomains;
	private static int[] topLevelDomainsType;
	private static final String[] aStringArray175 = { "cook", "cook's", "cooks", "seeks", "sheet", "woop", "woops",
			"faq", "noob", "noobs" };

	public static final void load(Archive archive) {
		Buffer fragmentsEnc = new Buffer(archive.getFile("fragmentsenc.txt"));
		Buffer badEnc = new Buffer(archive.getFile("badenc.txt"));
		Buffer domainEnc = new Buffer(archive.getFile("domainenc.txt"));
		Buffer topLevelDomainsBuffer = new Buffer(archive.getFile("tldlist.txt"));
		ChatCensor.loadDictionaries(fragmentsEnc, badEnc, domainEnc, topLevelDomainsBuffer);
	}

	private static final void loadDictionaries(Buffer fragmentsEnc, Buffer badEnc, Buffer domainEnc,
			Buffer topLevelDomainsBuffer) {
		ChatCensor.loadBadEnc(badEnc);
		ChatCensor.loadDomainEnc(domainEnc);
		ChatCensor.loadFragmentsEnc(fragmentsEnc, true);
		ChatCensor.loadTopLevelDomains(topLevelDomainsBuffer);
	}

	private static final void loadTopLevelDomains(Buffer buffer) {
		int length = buffer.getInt();
		ChatCensor.topLevelDomains = new char[length][];
		ChatCensor.topLevelDomainsType = new int[length];
		for (int index = 0; index < length; index++) {
			ChatCensor.topLevelDomainsType[index] = buffer.getUnsignedByte();
			char[] topLevelDomain = new char[buffer.getUnsignedByte()];
			for (int character = 0; character < topLevelDomain.length; character++) {
				topLevelDomain[character] = (char) buffer.getUnsignedByte();
			}
			ChatCensor.topLevelDomains[index] = topLevelDomain;
		}
	}

	private static final void loadBadEnc(Buffer buffer) {
		int length = buffer.getInt();
		ChatCensor.badWords = new char[length][];
		ChatCensor.badBytes = new byte[length][][];
		ChatCensor.loadBadWords(buffer, ChatCensor.badWords, ChatCensor.badBytes);
	}

	private static final void loadDomainEnc(Buffer buffer) {
		int length = buffer.getInt();
		ChatCensor.domains = new char[length][];
		ChatCensor.loadDomains(ChatCensor.domains, buffer);
	}

	private static final void loadFragmentsEnc(Buffer buffer, boolean bool) {
		ChatCensor.fragments = new int[buffer.getInt()];
		for (int index = 0; index < ChatCensor.fragments.length; index++) {
			ChatCensor.fragments[index] = buffer.getUnsignedLEShort();
		}
	}

	private static final void loadBadWords(Buffer buffer, char[][] badWords, byte[][][] badBytes) {
		for (int index = 0; index < badWords.length; index++) {
			char[] badWord = new char[buffer.getUnsignedByte()];
			for (int i_10_ = 0; i_10_ < badWord.length; i_10_++) {
				badWord[i_10_] = (char) buffer.getUnsignedByte();
			}
			badWords[index] = badWord;
			byte[][] badByte = new byte[buffer.getUnsignedByte()][2];
			for (int i_12_ = 0; i_12_ < badByte.length; i_12_++) {
				badByte[i_12_][0] = (byte) buffer.getUnsignedByte();
				badByte[i_12_][1] = (byte) buffer.getUnsignedByte();
			}
			if (badByte.length > 0) {
				badBytes[index] = badByte;
			}
		}
	}

	private static final void loadDomains(char[][] cs, Buffer buffer) {
		for (int index = 0; index < cs.length; index++) {
			char[] domainEnc = new char[buffer.getUnsignedByte()];
			for (int character = 0; character < domainEnc.length; character++) {
				domainEnc[character] = (char) buffer.getUnsignedByte();
			}
			cs[index] = domainEnc;
		}
	}

	private static final void formatLegalCharacters(char[] characters) {
		int character = 0;
		for (int index = 0; index < characters.length; index++) {
			if (ChatCensor.isLegalCharacter(characters[index])) {
				characters[character] = characters[index];
			} else {
				characters[character] = ' ';
			}
			if (character == 0 || characters[character] != ' ' || characters[character - 1] != ' ') {
				character++;
			}
		}
		for (int characterIndex = character; characterIndex < characters.length; characterIndex++) {
			characters[characterIndex] = ' ';
		}
	}

	private static final boolean isLegalCharacter(char character) {
		if ((character < ' ' || character > '\u007f') && character != ' ' && character != '\n' && character != '\t'
				&& character != '\u00a3' && character != '\u20ac') {
			return false;
		}
		return true;
	}

	public static final String censorString(String string) {
		char[] censoredString = string.toCharArray();
		ChatCensor.formatLegalCharacters(censoredString);
		String censoredStringTrimmed = new String(censoredString).trim();
		censoredString = censoredStringTrimmed.toLowerCase().toCharArray();
		String censoredStringLowercased = censoredStringTrimmed.toLowerCase();
		ChatCensor.method193(false, censoredString);
		ChatCensor.method188(censoredString, true);
		ChatCensor.method189((byte) 0, censoredString);
		ChatCensor.method202(censoredString, -511);
		for (String element : ChatCensor.aStringArray175) {
			int i_21_ = -1;
			while ((i_21_ = censoredStringLowercased.indexOf(element, i_21_ + 1)) != -1) {
				char[] cs_22_ = element.toCharArray();
				for (int i_23_ = 0; i_23_ < cs_22_.length; i_23_++) {
					censoredString[i_23_ + i_21_] = cs_22_[i_23_];
				}
			}
		}
		ChatCensor.method186(censoredStringTrimmed.toCharArray(), 2, censoredString);
		ChatCensor.method187(0, censoredString);
		return new String(censoredString).trim();
	}

	private static final void method186(char[] cs, int i, char[] cs_25_) {
		try {
			for (int i_26_ = 0; i_26_ < cs.length; i_26_++) {
				if (cs_25_[i_26_] != '*' && ChatCensor.method210(true, cs[i_26_])) {
					cs_25_[i_26_] = cs[i_26_];
				}
			}
			if (i != 2) {
				return;
			}
		} catch (RuntimeException runtimeexception) {
			SignLink.reportError("66493, " + new String(cs) + ", " + i + ", " + new String(cs_25_) + ", "
					+ runtimeexception.toString());
			throw new RuntimeException();
		}
	}

	private static final void method187(int i, char[] cs) {
		try {
			boolean bool = true;
			for (int i_27_ = 0; i_27_ < cs.length; i_27_++) {
				char c = cs[i_27_];
				if (ChatCensor.method207(c, -46837)) {
					if (bool) {
						if (ChatCensor.method209(c, 1)) {
							bool = false;
						}
					} else if (ChatCensor.method210(true, c)) {
						cs[i_27_] = (char) (c + 'a' - 'A');
					}
				} else {
					bool = true;
				}
			}
			if (i != 0) {
				return;
			}
		} catch (RuntimeException runtimeexception) {
			SignLink.reportError("29891, " + i + ", " + new String(cs) + ", " + runtimeexception.toString());
			throw new RuntimeException();
		}
	}

	private static final void method188(char[] cs, boolean bool) {
		try {
			if (bool) {
				for (int i = 0; i < 2; i++) {
					for (int i_28_ = ChatCensor.badWords.length - 1; i_28_ >= 0; i_28_--) {
						ChatCensor.method197(ChatCensor.badBytes[i_28_], cs, ChatCensor.anInt162,
								ChatCensor.badWords[i_28_]);
					}
				}
			}
		} catch (RuntimeException runtimeexception) {
			SignLink.reportError("1109, " + new String(cs) + ", " + bool + ", " + runtimeexception.toString());
			throw new RuntimeException();
		}
	}

	private static final void method189(byte b, char[] cs) {
		try {
			char[] cs_29_ = cs.clone();
			char[] cs_30_ = { '(', 'a', ')' };
			ChatCensor.method197(null, cs_29_, ChatCensor.anInt162, cs_30_);
			char[] cs_31_ = cs.clone();
			char[] cs_32_ = { 'd', 'o', 't' };
			ChatCensor.method197(null, cs_31_, ChatCensor.anInt162, cs_32_);
			for (int i = ChatCensor.domains.length - 1; i >= 0; i--) {
				ChatCensor.method190(29200, cs, ChatCensor.domains[i], cs_31_, cs_29_);
			}
			if (b != 0) {
				return;
			}
		} catch (RuntimeException runtimeexception) {
			SignLink.reportError("73832, " + b + ", " + new String(cs) + ", " + runtimeexception.toString());
			throw new RuntimeException();
		}
	}

	private static final void method190(int i, char[] cs, char[] cs_33_, char[] cs_34_, char[] cs_35_) {
		try {
			if (i == 29200 && cs_33_.length <= cs.length) {
				int i_37_;
				for (int i_36_ = 0; i_36_ <= cs.length - cs_33_.length; i_36_ += i_37_) {
					int i_38_ = i_36_;
					int i_39_ = 0;
					i_37_ = 1;
					while (i_38_ < cs.length) {
						char c = cs[i_38_];
						char c_41_ = '\0';
						if (i_38_ + 1 < cs.length) {
							c_41_ = cs[i_38_ + 1];
						}
						int i_42_;
						if (i_39_ < cs_33_.length && (i_42_ = ChatCensor.method199(43, c, cs_33_[i_39_], c_41_)) > 0) {
							i_38_ += i_42_;
							i_39_++;
						} else {
							if (i_39_ == 0) {
								break;
							}
							if ((i_42_ = ChatCensor.method199(43, c, cs_33_[i_39_ - 1], c_41_)) > 0) {
								i_38_ += i_42_;
								if (i_39_ == 1) {
									i_37_++;
								}
							} else {
								if (i_39_ >= cs_33_.length || !ChatCensor.method205(-12789, c)) {
									break;
								}
								i_38_++;
							}
						}
					}
					if (i_39_ >= cs_33_.length) {
						boolean bool_43_ = false;
						int i_44_ = ChatCensor.method191(cs, 4, cs_35_, i_36_);
						int i_45_ = ChatCensor.method192(ChatCensor.aByte161, cs_34_, i_38_ - 1, cs);
						if (i_44_ > 2 || i_45_ > 2) {
							bool_43_ = true;
						}
						if (bool_43_) {
							for (int i_46_ = i_36_; i_46_ < i_38_; i_46_++) {
								cs[i_46_] = '*';
							}
						}
					}
				}
			}
		} catch (RuntimeException runtimeexception) {
			SignLink.reportError("48695, " + i + ", " + new String(cs) + ", " + new String(cs_33_) + ", "
					+ new String(cs_34_) + ", " + new String(cs_35_) + ", " + runtimeexception.toString());
			throw new RuntimeException();
		}
	}

	private static final int method191(char[] cs, int i, char[] cs_47_, int i_48_) {
		try {
			if (i < 4 || i > 4) {
				return 2;
			}
			if (i_48_ == 0) {
				return 2;
			}
			for (int i_49_ = i_48_ - 1; i_49_ >= 0; i_49_--) {
				if (!ChatCensor.method205(-12789, cs[i_49_])) {
					break;
				}
				if (cs[i_49_] == '@') {
					return 3;
				}
			}
			int i_50_ = 0;
			for (int i_51_ = i_48_ - 1; i_51_ >= 0; i_51_--) {
				if (!ChatCensor.method205(-12789, cs_47_[i_51_])) {
					break;
				}
				if (cs_47_[i_51_] == '*') {
					i_50_++;
				}
			}
			if (i_50_ >= 3) {
				return 4;
			}
			if (ChatCensor.method205(-12789, cs[i_48_ - 1])) {
				return 1;
			}
			return 0;
		} catch (RuntimeException runtimeexception) {
			SignLink.reportError("87152, " + new String(cs) + ", " + i + ", " + new String(cs_47_) + ", " + i_48_
					+ ", " + runtimeexception.toString());
			throw new RuntimeException();
		}
	}

	private static final int method192(byte b, char[] cs, int i, char[] cs_52_) {
		try {
			if (i + 1 == cs_52_.length) {
				return 2;
			}
			for (int i_53_ = i + 1; i_53_ < cs_52_.length; i_53_++) {
				if (!ChatCensor.method205(-12789, cs_52_[i_53_])) {
					break;
				}
				if (cs_52_[i_53_] == '.' || cs_52_[i_53_] == ',') {
					return 3;
				}
			}
			if (b != -117) {
				return ChatCensor.anInt162;
			}
			int i_54_ = 0;
			for (int i_55_ = i + 1; i_55_ < cs_52_.length; i_55_++) {
				if (!ChatCensor.method205(-12789, cs[i_55_])) {
					break;
				}
				if (cs[i_55_] == '*') {
					i_54_++;
				}
			}
			if (i_54_ >= 3) {
				return 4;
			}
			if (ChatCensor.method205(-12789, cs_52_[i + 1])) {
				return 1;
			}
			return 0;
		} catch (RuntimeException runtimeexception) {
			SignLink.reportError("50081, " + b + ", " + new String(cs) + ", " + i + ", " + new String(cs_52_) + ", "
					+ runtimeexception.toString());
			throw new RuntimeException();
		}
	}

	private static final void method193(boolean bool, char[] cs) {
		try {
			char[] cs_56_ = cs.clone();
			char[] cs_57_ = { 'd', 'o', 't' };
			if (!bool) {
				ChatCensor.method197(null, cs_56_, ChatCensor.anInt162, cs_57_);
				char[] cs_58_ = cs.clone();
				char[] cs_59_ = { 's', 'l', 'a', 's', 'h' };
				ChatCensor.method197(null, cs_58_, ChatCensor.anInt162, cs_59_);
				for (int i = 0; i < ChatCensor.topLevelDomains.length; i++) {
					ChatCensor.method194(cs_58_, ChatCensor.topLevelDomains[i], ChatCensor.topLevelDomainsType[i],
							(byte) 51, cs_56_, cs);
				}
			}
		} catch (RuntimeException runtimeexception) {
			SignLink.reportError("41909, " + bool + ", " + new String(cs) + ", " + runtimeexception.toString());
			throw new RuntimeException();
		}
	}

	private static final void method194(char[] cs, char[] cs_60_, int i, byte b, char[] cs_61_, char[] cs_62_) {
		do {
			try {
				if (cs_60_.length <= cs_62_.length) {
					int i_63_;
					for (int i_64_ = 0; i_64_ <= cs_62_.length - cs_60_.length; i_64_ += i_63_) {
						int i_65_ = i_64_;
						int i_66_ = 0;
						i_63_ = 1;
						while (i_65_ < cs_62_.length) {
							char c = cs_62_[i_65_];
							char c_68_ = '\0';
							if (i_65_ + 1 < cs_62_.length) {
								c_68_ = cs_62_[i_65_ + 1];
							}
							int i_69_;
							if (i_66_ < cs_60_.length
									&& (i_69_ = ChatCensor.method199(43, c, cs_60_[i_66_], c_68_)) > 0) {
								i_65_ += i_69_;
								i_66_++;
							} else {
								if (i_66_ == 0) {
									break;
								}
								if ((i_69_ = ChatCensor.method199(43, c, cs_60_[i_66_ - 1], c_68_)) > 0) {
									i_65_ += i_69_;
									if (i_66_ == 1) {
										i_63_++;
									}
								} else {
									if (i_66_ >= cs_60_.length || !ChatCensor.method205(-12789, c)) {
										break;
									}
									i_65_++;
								}
							}
						}
						if (i_66_ >= cs_60_.length) {
							boolean bool_70_ = false;
							int i_71_ = ChatCensor.method195(36209, cs_62_, i_64_, cs_61_);
							int i_72_ = ChatCensor.method196(false, cs_62_, cs, i_65_ - 1);
							if (i == 1 && i_71_ > 0 && i_72_ > 0) {
								bool_70_ = true;
							}
							if (i == 2 && (i_71_ > 2 && i_72_ > 0 || i_71_ > 0 && i_72_ > 2)) {
								bool_70_ = true;
							}
							if (i == 3 && i_71_ > 0 && i_72_ > 2) {
								bool_70_ = true;
							}
							if (i == 3 && i_71_ > 2 && i_72_ > 0) {
								/* empty */
							}
							if (bool_70_) {
								int i_73_ = i_64_;
								int i_74_ = i_65_ - 1;
								if (i_71_ > 2) {
									if (i_71_ == 4) {
										boolean bool_75_ = false;
										for (int i_76_ = i_73_ - 1; i_76_ >= 0; i_76_--) {
											if (bool_75_) {
												if (cs_61_[i_76_] != '*') {
													break;
												}
												i_73_ = i_76_;
											} else if (cs_61_[i_76_] == '*') {
												i_73_ = i_76_;
												bool_75_ = true;
											}
										}
									}
									boolean bool_77_ = false;
									for (int i_78_ = i_73_ - 1; i_78_ >= 0; i_78_--) {
										if (bool_77_) {
											if (ChatCensor.method205(-12789, cs_62_[i_78_])) {
												break;
											}
											i_73_ = i_78_;
										} else if (!ChatCensor.method205(-12789, cs_62_[i_78_])) {
											bool_77_ = true;
											i_73_ = i_78_;
										}
									}
								}
								if (i_72_ > 2) {
									if (i_72_ == 4) {
										boolean bool_79_ = false;
										for (int i_80_ = i_74_ + 1; i_80_ < cs_62_.length; i_80_++) {
											if (bool_79_) {
												if (cs[i_80_] != '*') {
													break;
												}
												i_74_ = i_80_;
											} else if (cs[i_80_] == '*') {
												i_74_ = i_80_;
												bool_79_ = true;
											}
										}
									}
									boolean bool_81_ = false;
									for (int i_82_ = i_74_ + 1; i_82_ < cs_62_.length; i_82_++) {
										if (bool_81_) {
											if (ChatCensor.method205(-12789, cs_62_[i_82_])) {
												break;
											}
											i_74_ = i_82_;
										} else if (!ChatCensor.method205(-12789, cs_62_[i_82_])) {
											bool_81_ = true;
											i_74_ = i_82_;
										}
									}
								}
								for (int i_83_ = i_73_; i_83_ <= i_74_; i_83_++) {
									cs_62_[i_83_] = '*';
								}
							}
						}
					}
					if (b == 51) {
						break;
					}
					ChatCensor.aBoolean168 = !ChatCensor.aBoolean168;
				}
			} catch (RuntimeException runtimeexception) {
				SignLink.reportError("17314, " + new String(cs) + ", " + new String(cs_60_) + ", " + i + ", " + b
						+ ", " + new String(cs_61_) + ", " + new String(cs_62_) + ", " + runtimeexception.toString());
				throw new RuntimeException();
			}
			break;
		} while (false);
	}

	private static final int method195(int i, char[] cs, int i_84_, char[] cs_85_) {
		try {
			if (i_84_ == 0) {
				return 2;
			}
			for (int i_86_ = i_84_ - 1; i_86_ >= 0; i_86_--) {
				if (!ChatCensor.method205(-12789, cs[i_86_])) {
					break;
				}
				if (cs[i_86_] == ',' || cs[i_86_] == '.') {
					return 3;
				}
			}
			int i_87_ = 0;
			for (int i_88_ = i_84_ - 1; i_88_ >= 0; i_88_--) {
				if (!ChatCensor.method205(-12789, cs_85_[i_88_])) {
					break;
				}
				if (cs_85_[i_88_] == '*') {
					i_87_++;
				}
			}
			if (i != 36209) {
				ChatCensor.aBoolean168 = !ChatCensor.aBoolean168;
			}
			if (i_87_ >= 3) {
				return 4;
			}
			if (ChatCensor.method205(-12789, cs[i_84_ - 1])) {
				return 1;
			}
			return 0;
		} catch (RuntimeException runtimeexception) {
			SignLink.reportError("50325, " + i + ", " + new String(cs) + ", " + i_84_ + ", " + new String(cs_85_)
					+ ", " + runtimeexception.toString());
			throw new RuntimeException();
		}
	}

	private static final int method196(boolean bool, char[] cs, char[] cs_89_, int i) {
		try {
			if (bool) {
				ChatCensor.anInt157 = 391;
			}
			if (i + 1 == cs.length) {
				return 2;
			}
			for (int i_90_ = i + 1; i_90_ < cs.length; i_90_++) {
				if (!ChatCensor.method205(-12789, cs[i_90_])) {
					break;
				}
				if (cs[i_90_] == '\\' || cs[i_90_] == '/') {
					return 3;
				}
			}
			int i_91_ = 0;
			for (int i_92_ = i + 1; i_92_ < cs.length; i_92_++) {
				if (!ChatCensor.method205(-12789, cs_89_[i_92_])) {
					break;
				}
				if (cs_89_[i_92_] == '*') {
					i_91_++;
				}
			}
			if (i_91_ >= 5) {
				return 4;
			}
			if (ChatCensor.method205(-12789, cs[i + 1])) {
				return 1;
			}
			return 0;
		} catch (RuntimeException runtimeexception) {
			SignLink.reportError("27208, " + bool + ", " + new String(cs) + ", " + new String(cs_89_) + ", " + i + ", "
					+ runtimeexception.toString());
			throw new RuntimeException();
		}
	}

	public static final void method197(byte[][] bs, char[] cs, int i, char[] cs_93_) {
		try {
			if (i < 0 && cs_93_.length <= cs.length) {
				int i_95_;
				for (int i_94_ = 0; i_94_ <= cs.length - cs_93_.length; i_94_ += i_95_) {
					int i_96_ = i_94_;
					int i_97_ = 0;
					int i_98_ = 0;
					i_95_ = 1;
					boolean bool_99_ = false;
					boolean bool_100_ = false;
					boolean bool_101_ = false;
					while (i_96_ < cs.length && (!bool_100_ || !bool_101_)) {
						char c = cs[i_96_];
						char c_103_ = '\0';
						if (i_96_ + 1 < cs.length) {
							c_103_ = cs[i_96_ + 1];
						}
						int i_104_;
						if (i_97_ < cs_93_.length
								&& (i_104_ = ChatCensor.method200(c_103_, c, ChatCensor.aBoolean163, cs_93_[i_97_])) > 0) {
							if (i_104_ == 1 && ChatCensor.method208(c, -976)) {
								bool_100_ = true;
							}
							if (i_104_ == 2 && (ChatCensor.method208(c, -976) || ChatCensor.method208(c_103_, -976))) {
								bool_100_ = true;
							}
							i_96_ += i_104_;
							i_97_++;
						} else {
							if (i_97_ == 0) {
								break;
							}
							if ((i_104_ = ChatCensor.method200(c_103_, c, ChatCensor.aBoolean163, cs_93_[i_97_ - 1])) > 0) {
								i_96_ += i_104_;
								if (i_97_ == 1) {
									i_95_++;
								}
							} else {
								if (i_97_ >= cs_93_.length || !ChatCensor.method206(false, c)) {
									break;
								}
								if (ChatCensor.method205(-12789, c) && c != '\'') {
									bool_99_ = true;
								}
								if (ChatCensor.method208(c, -976)) {
									bool_101_ = true;
								}
								i_96_++;
								if (++i_98_ * 100 / (i_96_ - i_94_) > 90) {
									break;
								}
							}
						}
					}
					if (i_97_ >= cs_93_.length && (!bool_100_ || !bool_101_)) {
						boolean bool_105_ = true;
						if (!bool_99_) {
							char c = ' ';
							if (i_94_ - 1 >= 0) {
								c = cs[i_94_ - 1];
							}
							char c_106_ = ' ';
							if (i_96_ < cs.length) {
								c_106_ = cs[i_96_];
							}
							byte b = ChatCensor.method201(c, ChatCensor.anInt164);
							byte b_107_ = ChatCensor.method201(c_106_, ChatCensor.anInt164);
							if (bs != null && ChatCensor.method198(b, (byte) 8, bs, b_107_)) {
								bool_105_ = false;
							}
						} else {
							boolean bool_108_ = false;
							boolean bool_109_ = false;
							if (i_94_ - 1 < 0 || ChatCensor.method205(-12789, cs[i_94_ - 1]) && cs[i_94_ - 1] != '\'') {
								bool_108_ = true;
							}
							if (i_96_ >= cs.length || ChatCensor.method205(-12789, cs[i_96_]) && cs[i_96_] != '\'') {
								bool_109_ = true;
							}
							if (!bool_108_ || !bool_109_) {
								boolean bool_110_ = false;
								int i_111_ = i_94_ - 2;
								if (bool_108_) {
									i_111_ = i_94_;
								}
								for (/**/; !bool_110_ && i_111_ < i_96_; i_111_++) {
									if (i_111_ >= 0
											&& (!ChatCensor.method205(-12789, cs[i_111_]) || cs[i_111_] == '\'')) {
										char[] cs_112_ = new char[3];
										int i_113_;
										for (i_113_ = 0; i_113_ < 3; i_113_++) {
											if (i_111_ + i_113_ >= cs.length
													|| ChatCensor.method205(-12789, cs[i_111_ + i_113_])
													&& cs[i_111_ + i_113_] != '\'') {
												break;
											}
											cs_112_[i_113_] = cs[i_111_ + i_113_];
										}
										boolean bool_114_ = true;
										if (i_113_ == 0) {
											bool_114_ = false;
										}
										if (i_113_ < 3
												&& i_111_ - 1 >= 0
												&& (!ChatCensor.method205(-12789, cs[i_111_ - 1]) || cs[i_111_ - 1] == '\'')) {
											bool_114_ = false;
										}
										if (bool_114_ && !ChatCensor.method211(cs_112_, (byte) 4)) {
											bool_110_ = true;
										}
									}
								}
								if (!bool_110_) {
									bool_105_ = false;
								}
							}
						}
						if (bool_105_) {
							int i_115_ = 0;
							int i_116_ = 0;
							int i_117_ = -1;
							for (int i_118_ = i_94_; i_118_ < i_96_; i_118_++) {
								if (ChatCensor.method208(cs[i_118_], -976)) {
									i_115_++;
								} else if (ChatCensor.method207(cs[i_118_], -46837)) {
									i_116_++;
									i_117_ = i_118_;
								}
							}
							if (i_117_ > -1) {
								i_115_ -= i_96_ - 1 - i_117_;
							}
							if (i_115_ <= i_116_) {
								for (int i_119_ = i_94_; i_119_ < i_96_; i_119_++) {
									cs[i_119_] = '*';
								}
							} else {
								i_95_ = 1;
							}
						}
					}
				}
			}
		} catch (RuntimeException runtimeexception) {
			SignLink.reportError("25459, " + bs + ", " + new String(cs) + ", " + i + ", " + new String(cs_93_) + ", "
					+ runtimeexception.toString());
			throw new RuntimeException();
		}
	}

	private static final boolean method198(byte b, byte b_120_, byte[][] bs, byte b_121_) {
		try {
			int i = 0;
			if (b_120_ != 8) {
				ChatCensor.anInt162 = 308;
			}
			if (bs[i][0] == b && bs[i][1] == b_121_) {
				return true;
			}
			int i_122_ = bs.length - 1;
			if (bs[i_122_][0] == b && bs[i_122_][1] == b_121_) {
				return true;
			}
			do {
				int i_123_ = (i + i_122_) / 2;
				if (bs[i_123_][0] == b && bs[i_123_][1] == b_121_) {
					return true;
				}
				if (b < bs[i_123_][0] || b == bs[i_123_][0] && b_121_ < bs[i_123_][1]) {
					i_122_ = i_123_;
				} else {
					i = i_123_;
				}
			} while (i != i_122_ && i + 1 != i_122_);
			return false;
		} catch (RuntimeException runtimeexception) {
			SignLink.reportError("67276, " + b + ", " + b_120_ + ", " + bs + ", " + b_121_ + ", "
					+ runtimeexception.toString());
			throw new RuntimeException();
		}
	}

	private static final int method199(int i, char c, char c_124_, char c_125_) {
		try {
			if (i <= 0) {
				return ChatCensor.anInt157;
			}
			if (c_124_ == c) {
				return 1;
			}
			if (c_124_ == 'o' && c == '0') {
				return 1;
			}
			if (c_124_ == 'o' && c == '(' && c_125_ == ')') {
				return 2;
			}
			if (c_124_ == 'c' && (c == '(' || c == '<' || c == '[')) {
				return 1;
			}
			if (c_124_ == 'e' && c == '\u20ac') {
				return 1;
			}
			if (c_124_ == 's' && c == '$') {
				return 1;
			}
			if (c_124_ == 'l' && c == 'i') {
				return 1;
			}
			return 0;
		} catch (RuntimeException runtimeexception) {
			SignLink.reportError("60577, " + i + ", " + c + ", " + c_124_ + ", " + c_125_ + ", "
					+ runtimeexception.toString());
			throw new RuntimeException();
		}
	}

	private static final int method200(char c, char c_126_, boolean bool, char c_127_) {
		try {
			if (!bool) {
				ChatCensor.anInt162 = -260;
			}
			if (c_127_ == c_126_) {
				return 1;
			}
			if (c_127_ >= 'a' && c_127_ <= 'm') {
				if (c_127_ == 'a') {
					if (c_126_ == '4' || c_126_ == '@' || c_126_ == '^') {
						return 1;
					}
					if (c_126_ == '/' && c == '\\') {
						return 2;
					}
					return 0;
				}
				if (c_127_ == 'b') {
					if (c_126_ == '6' || c_126_ == '8') {
						return 1;
					}
					if (c_126_ == '1' && c == '3' || c_126_ == 'i' && c == '3') {
						return 2;
					}
					return 0;
				}
				if (c_127_ == 'c') {
					if (c_126_ == '(' || c_126_ == '<' || c_126_ == '{' || c_126_ == '[') {
						return 1;
					}
					return 0;
				}
				if (c_127_ == 'd') {
					if (c_126_ == '[' && c == ')' || c_126_ == 'i' && c == ')') {
						return 2;
					}
					return 0;
				}
				if (c_127_ == 'e') {
					if (c_126_ == '3' || c_126_ == '\u20ac') {
						return 1;
					}
					return 0;
				}
				if (c_127_ == 'f') {
					if (c_126_ == 'p' && c == 'h') {
						return 2;
					}
					if (c_126_ == '\u00a3') {
						return 1;
					}
					return 0;
				}
				if (c_127_ == 'g') {
					if (c_126_ == '9' || c_126_ == '6' || c_126_ == 'q') {
						return 1;
					}
					return 0;
				}
				if (c_127_ == 'h') {
					if (c_126_ == '#') {
						return 1;
					}
					return 0;
				}
				if (c_127_ == 'i') {
					if (c_126_ == 'y' || c_126_ == 'l' || c_126_ == 'j' || c_126_ == '1' || c_126_ == '!'
							|| c_126_ == ':' || c_126_ == ';' || c_126_ == '|') {
						return 1;
					}
					return 0;
				}
				if (c_127_ == 'j') {
					return 0;
				}
				if (c_127_ == 'k') {
					return 0;
				}
				if (c_127_ == 'l') {
					if (c_126_ == '1' || c_126_ == '|' || c_126_ == 'i') {
						return 1;
					}
					return 0;
				}
				if (c_127_ == 'm') {
					return 0;
				}
			}
			if (c_127_ >= 'n' && c_127_ <= 'z') {
				if (c_127_ == 'n') {
					return 0;
				}
				if (c_127_ == 'o') {
					if (c_126_ == '0' || c_126_ == '*') {
						return 1;
					}
					if (c_126_ == '(' && c == ')' || c_126_ == '[' && c == ']' || c_126_ == '{' && c == '}'
							|| c_126_ == '<' && c == '>') {
						return 2;
					}
					return 0;
				}
				if (c_127_ == 'p') {
					return 0;
				}
				if (c_127_ == 'q') {
					return 0;
				}
				if (c_127_ == 'r') {
					return 0;
				}
				if (c_127_ == 's') {
					if (c_126_ == '5' || c_126_ == 'z' || c_126_ == '$' || c_126_ == '2') {
						return 1;
					}
					return 0;
				}
				if (c_127_ == 't') {
					if (c_126_ == '7' || c_126_ == '+') {
						return 1;
					}
					return 0;
				}
				if (c_127_ == 'u') {
					if (c_126_ == 'v') {
						return 1;
					}
					if (c_126_ == '\\' && c == '/' || c_126_ == '\\' && c == '|' || c_126_ == '|' && c == '/') {
						return 2;
					}
					return 0;
				}
				if (c_127_ == 'v') {
					if (c_126_ == '\\' && c == '/' || c_126_ == '\\' && c == '|' || c_126_ == '|' && c == '/') {
						return 2;
					}
					return 0;
				}
				if (c_127_ == 'w') {
					if (c_126_ == 'v' && c == 'v') {
						return 2;
					}
					return 0;
				}
				if (c_127_ == 'x') {
					if (c_126_ == ')' && c == '(' || c_126_ == '}' && c == '{' || c_126_ == ']' && c == '['
							|| c_126_ == '>' && c == '<') {
						return 2;
					}
					return 0;
				}
				if (c_127_ == 'y') {
					return 0;
				}
				if (c_127_ == 'z') {
					return 0;
				}
			}
			if (c_127_ >= '0' && c_127_ <= '9') {
				if (c_127_ == '0') {
					if (c_126_ == 'o' || c_126_ == 'O') {
						return 1;
					}
					if (c_126_ == '(' && c == ')' || c_126_ == '{' && c == '}' || c_126_ == '[' && c == ']') {
						return 2;
					}
					return 0;
				}
				if (c_127_ == '1') {
					if (c_126_ == 'l') {
						return 1;
					}
					return 0;
				}
				return 0;
			}
			if (c_127_ == ',') {
				if (c_126_ == '.') {
					return 1;
				}
				return 0;
			}
			if (c_127_ == '.') {
				if (c_126_ == ',') {
					return 1;
				}
				return 0;
			}
			if (c_127_ == '!') {
				if (c_126_ == 'i') {
					return 1;
				}
				return 0;
			}
			return 0;
		} catch (RuntimeException runtimeexception) {
			SignLink.reportError("9538, " + c + ", " + c_126_ + ", " + bool + ", " + c_127_ + ", "
					+ runtimeexception.toString());
			throw new RuntimeException();
		}
	}

	private static final byte method201(char c, int i) {
		try {
			while (i >= 0) {
			}
			if (c >= 'a' && c <= 'z') {
				return (byte) (c - 'a' + '\001');
			}
			if (c == '\'') {
				return (byte) 28;
			}
			if (c >= '0' && c <= '9') {
				return (byte) (c - '0' + '\035');
			}
			return (byte) 27;
		} catch (RuntimeException runtimeexception) {
			SignLink.reportError("52349, " + c + ", " + i + ", " + runtimeexception.toString());
			throw new RuntimeException();
		}
	}

	private static final void method202(char[] cs, int i) {
		try {
			int i_128_ = 0;
			int i_129_ = 0;
			int i_130_ = 0;
			if (i >= 0) {
				ChatCensor.aBoolean156 = !ChatCensor.aBoolean156;
			}
			int i_131_;
			while ((i_131_ = ChatCensor.method203(cs, i_128_, 319)) != -1) {
				boolean bool_132_ = false;
				for (int i_133_ = i_128_; i_133_ >= 0 && i_133_ < i_131_ && !bool_132_; i_133_++) {
					if (!ChatCensor.method205(-12789, cs[i_133_]) && !ChatCensor.method206(false, cs[i_133_])) {
						bool_132_ = true;
					}
				}
				if (bool_132_) {
					i_129_ = 0;
				}
				if (i_129_ == 0) {
					i_130_ = i_131_;
				}
				i_128_ = ChatCensor.method204(cs, 0, i_131_);
				int i_134_ = 0;
				for (int i_135_ = i_131_; i_135_ < i_128_; i_135_++) {
					i_134_ = i_134_ * 10 + cs[i_135_] - 48;
				}
				if (i_134_ > 255 || i_128_ - i_131_ > 8) {
					i_129_ = 0;
				} else {
					i_129_++;
				}
				if (i_129_ == 4) {
					for (int i_136_ = i_130_; i_136_ < i_128_; i_136_++) {
						cs[i_136_] = '*';
					}
					i_129_ = 0;
				}
			}
		} catch (RuntimeException runtimeexception) {
			SignLink.reportError("38921, " + new String(cs) + ", " + i + ", " + runtimeexception.toString());
			throw new RuntimeException();
		}
	}

	private static final int method203(char[] cs, int i, int i_137_) {
		try {
			i_137_ = 23 / i_137_;
			for (int i_138_ = i; i_138_ < cs.length && i_138_ >= 0; i_138_++) {
				if (cs[i_138_] >= '0' && cs[i_138_] <= '9') {
					return i_138_;
				}
			}
			return -1;
		} catch (RuntimeException runtimeexception) {
			SignLink.reportError("27983, " + new String(cs) + ", " + i + ", " + i_137_ + ", "
					+ runtimeexception.toString());
			throw new RuntimeException();
		}
	}

	private static final int method204(char[] cs, int i, int i_139_) {
		try {
			for (int i_140_ = i_139_; i_140_ < cs.length && i_140_ >= 0; i_140_++) {
				if (cs[i_140_] < '0' || cs[i_140_] > '9') {
					return i_140_;
				}
			}
			if (i != 0) {
				return 3;
			}
			return cs.length;
		} catch (RuntimeException runtimeexception) {
			SignLink.reportError("1466, " + new String(cs) + ", " + i + ", " + i_139_ + ", "
					+ runtimeexception.toString());
			throw new RuntimeException();
		}
	}

	private static final boolean method205(int i, char c) {
		try {
			if (i != -12789) {
				throw new NullPointerException();
			}
			if (ChatCensor.method207(c, -46837) || ChatCensor.method208(c, -976)) {
				return false;
			}
			return true;
		} catch (RuntimeException runtimeexception) {
			SignLink.reportError("18641, " + i + ", " + c + ", " + runtimeexception.toString());
			throw new RuntimeException();
		}
	}

	private static final boolean method206(boolean bool, char c) {
		try {
			if (bool) {
				ChatCensor.anInt164 = -233;
			}
			if (c < 'a' || c > 'z') {
				return true;
			}
			if (c == 'v' || c == 'x' || c == 'j' || c == 'q' || c == 'z') {
				return true;
			}
			return false;
		} catch (RuntimeException runtimeexception) {
			SignLink.reportError("32846, " + bool + ", " + c + ", " + runtimeexception.toString());
			throw new RuntimeException();
		}
	}

	private static final boolean method207(char c, int i) {
		try {
			if (i != -46837) {
				for (int i_141_ = 1; i_141_ > 0; i_141_++) {
					/* empty */
				}
			}
			if ((c < 'a' || c > 'z') && (c < 'A' || c > 'Z')) {
				return false;
			}
			return true;
		} catch (RuntimeException runtimeexception) {
			SignLink.reportError("61160, " + c + ", " + i + ", " + runtimeexception.toString());
			throw new RuntimeException();
		}
	}

	private static final boolean method208(char c, int i) {
		try {
			if (i >= 0) {
				ChatCensor.anInt164 = 254;
			}
			if (c < '0' || c > '9') {
				return false;
			}
			return true;
		} catch (RuntimeException runtimeexception) {
			SignLink.reportError("30488, " + c + ", " + i + ", " + runtimeexception.toString());
			throw new RuntimeException();
		}
	}

	private static final boolean method209(char c, int i) {
		try {
			if (i != 1) {
				for (int i_142_ = 1; i_142_ > 0; i_142_++) {
					/* empty */
				}
			}
			if (c < 'a' || c > 'z') {
				return false;
			}
			return true;
		} catch (RuntimeException runtimeexception) {
			SignLink.reportError("25533, " + c + ", " + i + ", " + runtimeexception.toString());
			throw new RuntimeException();
		}
	}

	private static final boolean method210(boolean bool, char c) {
		try {
			if (!bool) {
				throw new NullPointerException();
			}
			if (c < 'A' || c > 'Z') {
				return false;
			}
			return true;
		} catch (RuntimeException runtimeexception) {
			SignLink.reportError("1272, " + bool + ", " + c + ", " + runtimeexception.toString());
			throw new RuntimeException();
		}
	}

	private static final boolean method211(char[] cs, byte b) {
		try {
			if (b == ChatCensor.aByte166) {
				b = (byte) 0;
			} else {
				throw new NullPointerException();
			}
			boolean bool = true;
			for (int i = 0; i < cs.length; i++) {
				if (!ChatCensor.method208(cs[i], -976) && cs[i] != 0) {
					bool = false;
				}
			}
			if (bool) {
				return true;
			}
			int i = ChatCensor.method212(cs, 8801);
			int i_143_ = 0;
			int i_144_ = ChatCensor.fragments.length - 1;
			if (i == ChatCensor.fragments[i_143_] || i == ChatCensor.fragments[i_144_]) {
				return true;
			}
			do {
				int i_145_ = (i_143_ + i_144_) / 2;
				if (i == ChatCensor.fragments[i_145_]) {
					return true;
				}
				if (i < ChatCensor.fragments[i_145_]) {
					i_144_ = i_145_;
				} else {
					i_143_ = i_145_;
				}
			} while (i_143_ != i_144_ && i_143_ + 1 != i_144_);
			return false;
		} catch (RuntimeException runtimeexception) {
			SignLink.reportError("62482, " + new String(cs) + ", " + b + ", " + runtimeexception.toString());
			throw new RuntimeException();
		}
	}

	public static final int method212(char[] cs, int i) {
		try {
			if (i != ChatCensor.anInt167) {
				for (int i_146_ = 1; i_146_ > 0; i_146_++) {
					/* empty */
				}
			}
			if (cs.length > 6) {
				return 0;
			}
			int i_147_ = 0;
			for (int i_148_ = 0; i_148_ < cs.length; i_148_++) {
				int i_149_ = cs[cs.length - i_148_ - 1];
				if (i_149_ >= 97 && i_149_ <= 122) {
					i_147_ = i_147_ * 38 + (i_149_ - 97 + 1);
				} else if (i_149_ == 39) {
					i_147_ = i_147_ * 38 + 27;
				} else if (i_149_ >= 48 && i_149_ <= 57) {
					i_147_ = i_147_ * 38 + (i_149_ - 48 + 28);
				} else if (i_149_ != 0) {
					return 0;
				}
			}
			return i_147_;
		} catch (RuntimeException runtimeexception) {
			SignLink.reportError("67682, " + new String(cs) + ", " + i + ", " + runtimeexception.toString());
			throw new RuntimeException();
		}
	}
}
