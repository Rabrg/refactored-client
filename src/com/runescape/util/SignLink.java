package com.runescape.util;

/* SignLink - Decompiled by JODE
 * Visit http://jode.sourceforge.net/
 */
import java.applet.Applet;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.InetAddress;
import java.net.Socket;
import java.net.URL;

public class SignLink implements Runnable {
	public static int uid;
	public static int storeId = 32;
	public static RandomAccessFile aRandomAccessFile613 = null;
	public static RandomAccessFile[] aRandomAccessFileArray614 = new RandomAccessFile[5];
	public static boolean aBoolean615;
	public static Applet applet = null;
	private static boolean aBoolean617;
	private static int anInt618;
	private static InetAddress anInetAddress619;
	private static int anInt620;
	private static Socket socket = null;
	private static int anInt621 = 1;
	private static Runnable aRunnable622 = null;
	private static String aString623 = null;
	public static String aString624 = null;
	private static String aString625 = null;
	private static DataInputStream aDataInputStream626 = null;
	private static int anInt627;
	private static String aString628 = null;
	private static byte[] aByteArray629 = null;
	private static boolean aBoolean630;
	private static int anInt631;
	public static String aString632 = null;
	public static int anInt633;
	public static int anInt634;
	private static boolean aBoolean635;
	private static int anInt636;
	public static String aString637 = null;
	public static int anInt638;
	public static boolean aBoolean639 = true;
	public static String username = "";

	public static final void method547(InetAddress inetaddress) {
		SignLink.anInt618 = (int) (Math.random() * 9.9999999E7);
		if (SignLink.aBoolean617) {
			try {
				Thread.sleep(500L);
			} catch (Exception exception) {
				/* empty */
			}
			SignLink.aBoolean617 = false;
		}
		SignLink.anInt620 = 0;
		SignLink.aRunnable622 = null;
		SignLink.aString623 = null;
		SignLink.aString628 = null;
		SignLink.aString625 = null;
		SignLink.anInetAddress619 = inetaddress;
		Thread thread = new Thread(new SignLink());
		thread.setDaemon(true);
		thread.start();
		while (!SignLink.aBoolean617) {
			try {
				Thread.sleep(50L);
			} catch (Exception exception) {
				/* empty */
			}
		}
	}

	@Override
	public final void run() {
		SignLink.aBoolean617 = true;
		String string = SignLink.method548();
		SignLink.uid = SignLink.method549(string);
		try {
			File file = new File(string + "main_file_cache.dat");
			if (file.exists() && file.length() > 52428800L) {
				file.delete();
			}
			SignLink.aRandomAccessFile613 = new RandomAccessFile(string + "main_file_cache.dat", "rw");
			for (int i = 0; i < 5; i++) {
				SignLink.aRandomAccessFileArray614[i] = new RandomAccessFile(string + "main_file_cache.idx" + i, "rw");
			}
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		int i = SignLink.anInt618;
		while (SignLink.anInt618 == i) {
			if (SignLink.anInt620 != 0) {
				try {
					SignLink.socket = new Socket(SignLink.anInetAddress619, SignLink.anInt620);
				} catch (Exception exception) {
					SignLink.socket = null;
				}
				SignLink.anInt620 = 0;
			} else if (SignLink.aRunnable622 != null) {
				Thread thread = new Thread(SignLink.aRunnable622);
				thread.setDaemon(true);
				thread.start();
				thread.setPriority(SignLink.anInt621);
				SignLink.aRunnable622 = null;
			} else if (SignLink.aString623 != null) {
				try {
					SignLink.aString624 = InetAddress.getByName(SignLink.aString623).getHostName();
				} catch (Exception exception) {
					SignLink.aString624 = "unknown";
				}
				SignLink.aString623 = null;
			} else if (SignLink.aString628 != null) {
				if (SignLink.aByteArray629 != null) {
					try {
						FileOutputStream fileoutputstream = new FileOutputStream(string + SignLink.aString628);
						fileoutputstream.write(SignLink.aByteArray629, 0, SignLink.anInt627);
						fileoutputstream.close();
					} catch (Exception exception) {
						/* empty */
					}
				}
				if (SignLink.aBoolean635) {
					SignLink.aString637 = string + SignLink.aString628;
					SignLink.aBoolean635 = false;
				}
				if (SignLink.aBoolean630) {
					SignLink.aString632 = string + SignLink.aString628;
					SignLink.aBoolean630 = false;
				}
				SignLink.aString628 = null;
			} else if (SignLink.aString625 != null) {
				try {
					SignLink.aDataInputStream626 = new DataInputStream(new URL(SignLink.applet.getCodeBase(),
							SignLink.aString625).openStream());
				} catch (Exception exception) {
					SignLink.aDataInputStream626 = null;
				}
				SignLink.aString625 = null;
			}
			try {
				Thread.sleep(50L);
			} catch (Exception exception) {
				/* empty */
			}
		}
	}

	public static final String method548() {
		return "./cache/";
	}

	private static final int method549(String string) {
		try {
			File file = new File(string + "uid.dat");
			if (!file.exists() || file.length() < 4L) {
				DataOutputStream dataoutputstream = new DataOutputStream(new FileOutputStream(string + "uid.dat"));
				dataoutputstream.writeInt((int) (Math.random() * 9.9999999E7));
				dataoutputstream.close();
			}
		} catch (Exception exception) {
			/* empty */
		}
		try {
			DataInputStream datainputstream = new DataInputStream(new FileInputStream(string + "uid.dat"));
			int i = datainputstream.readInt();
			datainputstream.close();
			return i + 1;
		} catch (Exception exception) {
			return 0;
		}
	}

	public static final synchronized Socket method550(int i) throws IOException {
		SignLink.anInt620 = i;
		while (SignLink.anInt620 != 0) {
			try {
				Thread.sleep(50L);
			} catch (Exception exception) {
				/* empty */
			}
		}
		if (SignLink.socket == null) {
			throw new IOException("could not open socket");
		}
		return SignLink.socket;
	}

	public static final synchronized DataInputStream openURL(String string) throws IOException {
		SignLink.aString625 = string;
		while (SignLink.aString625 != null) {
			try {
				Thread.sleep(50L);
			} catch (Exception exception) {
				/* empty */
			}
		}
		if (SignLink.aDataInputStream626 == null) {
			throw new IOException("could not open: " + string);
		}
		return SignLink.aDataInputStream626;
	}

	public static final synchronized void method552(String string) {
		SignLink.aString624 = string;
		SignLink.aString623 = string;
	}

	public static final synchronized void method553(Runnable runnable, int i) {
		SignLink.anInt621 = i;
		SignLink.aRunnable622 = runnable;
	}

	public static final synchronized boolean method554(byte[] bs, int i) {
		if (i > 2000000) {
			return false;
		}
		if (SignLink.aString628 != null) {
			return false;
		}
		SignLink.anInt636 = (SignLink.anInt636 + 1) % 5;
		SignLink.anInt627 = i;
		SignLink.aByteArray629 = bs;
		SignLink.aBoolean635 = true;
		SignLink.aString628 = "sound" + SignLink.anInt636 + ".wav";
		return true;
	}

	public static final synchronized boolean method555() {
		if (SignLink.aString628 != null) {
			return false;
		}
		SignLink.aByteArray629 = null;
		SignLink.aBoolean635 = true;
		SignLink.aString628 = "sound" + SignLink.anInt636 + ".wav";
		return true;
	}

	public static final synchronized void method556(byte[] bs, int i) {
		if (i <= 2000000 && SignLink.aString628 == null) {
			SignLink.anInt631 = (SignLink.anInt631 + 1) % 5;
			SignLink.anInt627 = i;
			SignLink.aByteArray629 = bs;
			SignLink.aBoolean630 = true;
			SignLink.aString628 = "jingle" + SignLink.anInt631 + ".mid";
		}
	}

	public static final void reportError(String string) {
		if (SignLink.aBoolean639 && SignLink.aBoolean617) {
			System.out.println("Error: " + string);
			try {
				string = string.replace(':', '_');
				string = string.replace('@', '_');
				string = string.replace('&', '_');
				string = string.replace('#', '_');
				DataInputStream datainputstream = SignLink.openURL("reporterror" + 317 + ".cgi?error="
						+ SignLink.username + " " + string);
				datainputstream.readUTF();
				datainputstream.close();
			} catch (IOException ioexception) {
				/* empty */
			}
		}
	}
}
