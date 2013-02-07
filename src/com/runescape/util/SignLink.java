package com.runescape.util;

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
	public static int storeIndex = 32;
	public static RandomAccessFile mainCache = null;
	public static RandomAccessFile[] cacheIndexes = new RandomAccessFile[5];
	public static Applet applet = null;
	private static boolean active;
	private static InetAddress nextInetAddress;
	private static int nextPort;
	private static Socket socket = null;
	private static int nextRunnablePriority = 1;
	private static Runnable nextRunnable = null;
	private static String lastIP = null;
	public static String lastHostname = null;
	private static String nextURL = null;
	private static DataInputStream nextURLStream = null;
	private static int nextWriteLength;
	private static String nextWriteName = null;
	private static byte[] nextWriteData = null;
	private static boolean writeMusic;
	private static int nextMidiIndex;
	public static String nextSongName = null;
	public static int midiVolume;
	public static int midiFade;
	private static boolean writeSoundEffect;
	private static int nextWaveIndex;
	public static String nextEffectName = null;
	public static int waveVolume;
	public static boolean accessible = true;
	public static String lastUsername = "";

	public static final void initialize(InetAddress inetAddress) {
		if (SignLink.active) {
			try {
				Thread.sleep(500L);
			} catch (Exception exception) {
				/* empty */
			}
			SignLink.active = false;
		}
		SignLink.nextPort = 0;
		SignLink.nextRunnable = null;
		SignLink.lastIP = null;
		SignLink.nextWriteName = null;
		SignLink.nextURL = null;
		SignLink.nextInetAddress = inetAddress;
		Thread thread = new Thread(new SignLink());
		thread.setDaemon(true);
		thread.start();
		while (!SignLink.active) {
			try {
				Thread.sleep(50L);
			} catch (Exception exception) {
				/* empty */
			}
		}
	}

	@Override
	public final void run() {
		SignLink.active = true;
		String directory = SignLink.getCacheDirectory();
		SignLink.uid = SignLink.getUID(directory);

		try {
			File file = new File(directory + "main_file_cache.dat");
			if (file.exists() && file.length() > 0x3200000) {
				file.delete();
			}
			SignLink.mainCache = new RandomAccessFile(directory + "main_file_cache.dat", "rw");
			for (int i = 0; i < 5; i++) {
				SignLink.cacheIndexes[i] = new RandomAccessFile(directory + "main_file_cache.idx" + i, "rw");
			}
		} catch (Exception exception) {
			exception.printStackTrace();
		}

		while (true) {
			if (SignLink.nextPort != 0) {
				try {
					SignLink.socket = new Socket(SignLink.nextInetAddress, SignLink.nextPort);
				} catch (Exception exception) {
					SignLink.socket = null;
				}
				SignLink.nextPort = 0;
			} else if (SignLink.nextRunnable != null) {
				Thread thread = new Thread(SignLink.nextRunnable);
				thread.setDaemon(true);
				thread.start();
				thread.setPriority(SignLink.nextRunnablePriority);
				SignLink.nextRunnable = null;
			} else if (SignLink.lastIP != null) {
				try {
					SignLink.lastHostname = InetAddress.getByName(SignLink.lastIP).getHostName();
				} catch (Exception exception) {
					SignLink.lastHostname = "unknown";
				}
				SignLink.lastIP = null;
			} else if (SignLink.nextWriteName != null) {
				if (SignLink.nextWriteData != null) {
					try {
						FileOutputStream out = new FileOutputStream(directory + SignLink.nextWriteName);
						out.write(SignLink.nextWriteData, 0, SignLink.nextWriteLength);
						out.close();
					} catch (Exception exception) {
						/* empty */
					}
				}
				if (SignLink.writeSoundEffect) {
					SignLink.nextEffectName = directory + SignLink.nextWriteName;
					SignLink.writeSoundEffect = false;
				}
				if (SignLink.writeMusic) {
					SignLink.nextSongName = directory + SignLink.nextWriteName;
					SignLink.writeMusic = false;
				}
				SignLink.nextWriteName = null;
			} else if (SignLink.nextURL != null) {
				try {
					SignLink.nextURLStream = new DataInputStream(new URL(SignLink.applet.getCodeBase(),
							SignLink.nextURL).openStream());
				} catch (Exception exception) {
					SignLink.nextURLStream = null;
				}
				SignLink.nextURL = null;
			}
			try {
				Thread.sleep(50L);
			} catch (Exception exception) {
				/* empty */
			}
		}
	}

	public static final String getCacheDirectory() {
		return "./cache/";
	}

	private static final int getUID(String directory) {
		try {
			File file = new File(directory + "uid.dat");
			if (!file.exists() || file.length() < 4L) {
				DataOutputStream out = new DataOutputStream(new FileOutputStream(directory + "uid.dat"));
				out.writeInt((int) (Math.random() * 9.9999999E7));
				out.close();
			}
		} catch (Exception exception) {
			/* empty */
		}
		try {
			DataInputStream in = new DataInputStream(new FileInputStream(directory + "uid.dat"));
			int i = in.readInt();
			in.close();
			return i + 1;
		} catch (Exception exception) {
			return 0;
		}
	}

	public static final synchronized Socket openPort(int port) throws IOException {
		SignLink.nextPort = port;
		while (SignLink.nextPort != 0) {
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

	public static final synchronized DataInputStream getURLStream(String url) throws IOException {
		SignLink.nextURL = url;
		while (SignLink.nextURL != null) {
			try {
				Thread.sleep(50L);
			} catch (Exception exception) {
				/* empty */
			}
		}
		if (SignLink.nextURLStream == null) {
			throw new IOException("could not open: " + url);
		}
		return SignLink.nextURLStream;
	}

	public static final synchronized void setLastIP(String string) {
		SignLink.lastHostname = string;
		SignLink.lastIP = string;
	}

	public static final synchronized void startRunnable(Runnable runnable, int priority) {
		SignLink.nextRunnablePriority = priority;
		SignLink.nextRunnable = runnable;
	}

	public static final synchronized boolean writeWave(byte[] data, int length) {
		if (length > 0x1E8480) {
			return false;
		}
		if (SignLink.nextWriteName != null) {
			return false;
		}
		SignLink.nextWaveIndex = (SignLink.nextWaveIndex + 1) % 5;
		SignLink.nextWriteLength = length;
		SignLink.nextWriteData = data;
		SignLink.writeSoundEffect = true;
		SignLink.nextWriteName = "sound" + SignLink.nextWaveIndex + ".wav";
		return true;
	}

	public static final synchronized boolean rewriteWave() {
		if (SignLink.nextWriteName != null) {
			return false;
		}
		SignLink.nextWriteData = null;
		SignLink.writeSoundEffect = true;
		SignLink.nextWriteName = "sound" + SignLink.nextWaveIndex + ".wav";
		return true;
	}

	public static final synchronized void writeMidi(byte[] data, int i) {
		if (i <= 0x1E8480 && SignLink.nextWriteName == null) {
			SignLink.nextMidiIndex = (SignLink.nextMidiIndex + 1) % 5;
			SignLink.nextWriteLength = i;
			SignLink.nextWriteData = data;
			SignLink.writeMusic = true;
			SignLink.nextWriteName = "jingle" + SignLink.nextMidiIndex + ".mid";
		}
	}

	public static final void reportError(String error) {
		if (SignLink.accessible && SignLink.active) {
			System.out.println("Error: " + error);
			try {
				error = error.replace(':', '_');
				error = error.replace('@', '_');
				error = error.replace('&', '_');
				error = error.replace('#', '_');
				DataInputStream in = SignLink.getURLStream("reporterror" + 317 + ".cgi?error=" + SignLink.lastUsername
						+ " " + error);
				in.readUTF();
				in.close();
			} catch (IOException ioexception) {
				/* empty */
			}
		}
	}
}
