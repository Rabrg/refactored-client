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
	public static int storeId = 32;
	public static RandomAccessFile cacheDat = null;
	public static RandomAccessFile[] cacheIdx = new RandomAccessFile[5];
	public static Applet applet = null;
	private static boolean active;
	private static InetAddress inetAddress;
	private static int socketRequest;
	private static Socket socket = null;
	private static int threadRequestPriority = 1;
	private static Runnable threadRequest = null;
	private static String dnsRequest = null;
	public static String dns = null;
	private static String urlRequest = null;
	private static DataInputStream nextURLStream = null;
	private static int saveLength;
	private static String saveRequest = null;
	private static byte[] saveBuffer = null;
	private static boolean midiPlay;
	private static int midiPosition;
	public static String midi = null;
	public static int midiVolume;
	public static int midiFade;
	private static boolean wavePlay;
	private static int wavePosition;
	public static String wave = null;
	public static int waveVolume;
	public static boolean reportError = true;
	public static String errorName = "";

	public static final void initialize(InetAddress inetAddress) {
		if (SignLink.active) {
			try {
				Thread.sleep(500L);
			} catch (Exception exception) {
				/* empty */
			}
			SignLink.active = false;
		}
		SignLink.socketRequest = 0;
		SignLink.threadRequest = null;
		SignLink.dnsRequest = null;
		SignLink.saveRequest = null;
		SignLink.urlRequest = null;
		SignLink.inetAddress = inetAddress;
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
			SignLink.cacheDat = new RandomAccessFile(directory + "main_file_cache.dat", "rw");
			for (int idx = 0; idx < 5; idx++) {
				SignLink.cacheIdx[idx] = new RandomAccessFile(directory + "main_file_cache.idx" + idx, "rw");
			}
		} catch (Exception exception) {
			exception.printStackTrace();
		}

		while (true) {
			if (SignLink.socketRequest != 0) {
				try {
					SignLink.socket = new Socket(SignLink.inetAddress, SignLink.socketRequest);
				} catch (Exception exception) {
					SignLink.socket = null;
				}
				SignLink.socketRequest = 0;
			} else if (SignLink.threadRequest != null) {
				Thread thread = new Thread(SignLink.threadRequest);
				thread.setDaemon(true);
				thread.start();
				thread.setPriority(SignLink.threadRequestPriority);
				SignLink.threadRequest = null;
			} else if (SignLink.dnsRequest != null) {
				try {
					SignLink.dns = InetAddress.getByName(SignLink.dnsRequest).getHostName();
				} catch (Exception exception) {
					SignLink.dns = "unknown";
				}
				SignLink.dnsRequest = null;
			} else if (SignLink.saveRequest != null) {
				if (SignLink.saveBuffer != null) {
					try {
						FileOutputStream out = new FileOutputStream(directory + SignLink.saveRequest);
						out.write(SignLink.saveBuffer, 0, SignLink.saveLength);
						out.close();
					} catch (Exception exception) {
						/* empty */
					}
				}
				if (SignLink.wavePlay) {
					SignLink.wave = directory + SignLink.saveRequest;
					SignLink.wavePlay = false;
				}
				if (SignLink.midiPlay) {
					SignLink.midi = directory + SignLink.saveRequest;
					SignLink.midiPlay = false;
				}
				SignLink.saveRequest = null;
			} else if (SignLink.urlRequest != null) {
				try {
					SignLink.nextURLStream = new DataInputStream(new URL(SignLink.applet.getCodeBase(),
							SignLink.urlRequest).openStream());
				} catch (Exception exception) {
					SignLink.nextURLStream = null;
				}
				SignLink.urlRequest = null;
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

	public static final synchronized Socket openSocket(int port) throws IOException {
		SignLink.socketRequest = port;
		while (SignLink.socketRequest != 0) {
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

	public static final synchronized DataInputStream openURL(String url) throws IOException {
		SignLink.urlRequest = url;
		while (SignLink.urlRequest != null) {
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

	public static final synchronized void dnsLookup(String dns) {
		SignLink.dns = dns;
		SignLink.dnsRequest = dns;
	}

	public static final synchronized void startRunnable(Runnable runnable, int priority) {
		SignLink.threadRequestPriority = priority;
		SignLink.threadRequest = runnable;
	}

	public static final synchronized boolean waveSave(byte[] buffer, int length) {
		if (length > 0x1E8480) {
			return false;
		}
		if (SignLink.saveRequest != null) {
			return false;
		}
		SignLink.wavePosition = (SignLink.wavePosition + 1) % 5;
		SignLink.saveLength = length;
		SignLink.saveBuffer = buffer;
		SignLink.wavePlay = true;
		SignLink.saveRequest = "sound" + SignLink.wavePosition + ".wav";
		return true;
	}

	public static final synchronized boolean waveReplay() {
		if (SignLink.saveRequest != null) {
			return false;
		}
		SignLink.saveBuffer = null;
		SignLink.wavePlay = true;
		SignLink.saveRequest = "sound" + SignLink.wavePosition + ".wav";
		return true;
	}

	public static final synchronized void midiSave(byte[] buffer, int length) {
		if (length <= 0x1E8480 && SignLink.saveRequest == null) {
			SignLink.midiPosition = (SignLink.midiPosition + 1) % 5;
			SignLink.saveLength = length;
			SignLink.saveBuffer = buffer;
			SignLink.midiPlay = true;
			SignLink.saveRequest = "jingle" + SignLink.midiPosition + ".mid";
		}
	}

	public static final void reportError(String error) {
		if (SignLink.reportError && SignLink.active) {
			System.out.println("Error: " + error);
			try {
				error = error.replace(':', '_');
				error = error.replace('@', '_');
				error = error.replace('&', '_');
				error = error.replace('#', '_');
				DataInputStream in = SignLink.openURL("reporterror" + 317 + ".cgi?error=" + SignLink.errorName + " "
						+ error);
				in.readUTF();
				in.close();
			} catch (IOException ioexception) {
				/* empty */
			}
		}
	}
}
