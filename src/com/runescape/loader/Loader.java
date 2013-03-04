package com.runescape.loader;

import java.applet.Applet;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.URL;
import java.security.MessageDigest;
import java.util.zip.ZipFile;

import com.runescape.util.SignLink;

@SuppressWarnings("serial")
public class Loader extends Applet implements Runnable {

	private Applet applet;
	private static String cacheDirectory;

	@Override
	public final void init() {
		try {
			try {
				SignLink.storeId = Integer.parseInt(getParameter("store"));
			} catch (Exception _ex) {
			}
			SignLink.applet = this;
			SignLink.initialize(InetAddress.getByName(getCodeBase().getHost()));
			String s = System.getProperties().getProperty("java.vendor");
			System.out.println("Vend:" + s);
			if (s.toLowerCase().indexOf("sun") != -1) {
				SignLink.sunjava = true;
			}
			if (s.toLowerCase().indexOf("apple") != -1) {
				SignLink.sunjava = true;
			}
			Thread thread = new Thread(this);
			thread.start();
			return;
		} catch (Exception exception) {
			exception.printStackTrace();
		}
	}

	@Override
	public final void run() {
		try {
			Loader.cacheDirectory = SignLink.getCacheDirectory();
			if (Loader.cacheDirectory == null) {
				error("findcache");
			}
			byte abyte0[] = loadFile(Loader.cacheDirectory + "/code.dat");
			if (!verify(abyte0)) {
				byte abyte1[] = downloadFile(false);
				if (abyte1 == null) {
					abyte1 = downloadFile(true);
				}
				if (abyte1 == null) {
					error("download");
				}
				saveFile(Loader.cacheDirectory + "/code.dat", abyte1);
				abyte1 = loadFile(Loader.cacheDirectory + "/code.dat");
				if (!verify(abyte1)) {
					error("mismatch");
				}
			}
			ClassLoader cloader1 = new ClassLoader();
			cloader1.zip = new ZipFile(Loader.cacheDirectory + "/code.dat");
			cloader1.link = Class.forName("sign.signlink");
			applet = (Applet) cloader1.loadClass("client").newInstance();
			applet.init();
			applet.start();
			return;
		} catch (Exception exception) {
			exception.printStackTrace();
		}
	}

	private final byte[] downloadFile(boolean jaggrab) {
		Graphics g = getGraphics();
		Font font = new Font("Helvetica", 1, 13);
		FontMetrics fontmetrics = getFontMetrics(font);
		Font font1 = new Font("Helvetica", 0, 13);
		getFontMetrics(font1);
		Color color = new Color(140, 17, 17);
		byte buffer[] = new byte[0x366ad];
		String s = "";
		for (int i = 0; i < 10; i++) {
			s = s + Signature.sha[i];
		}

		try {
			InputStream inputstream;
			if (jaggrab) {
				@SuppressWarnings("resource")
				Socket socket = new Socket(InetAddress.getByName(getCodeBase().getHost()), 43595);
				socket.setSoTimeout(10000);
				OutputStream outputstream = socket.getOutputStream();
				outputstream.write(("JAGGRAB /runescape" + s + ".jar\n\n").getBytes());
				inputstream = socket.getInputStream();
			} else {
				inputstream = (new URL(getCodeBase(), "runescape" + s + ".jar")).openStream();
			}
			int j = -1;
			for (int k = 0; k < 0x366ad;) {
				int l = 0x366ad - k;
				if (l > 1000) {
					l = 1000;
				}
				int i1 = inputstream.read(buffer, k, l);
				if (i1 < 0) {
					throw new EOFException();
				}
				k += i1;
				int j1 = (k * 100) / 0x366ad;
				if (j1 != j) {
					g.setColor(Color.black);
					g.fillRect(0, 0, 765, 503);
					g.setColor(color);
					g.drawRect(230, 233, 304, 34);
					String s1 = "Loading game code - " + j1 + "%";
					g.setFont(font);
					g.setColor(Color.white);
					g.drawString(s1, (765 - fontmetrics.stringWidth(s1)) / 2, 255);
					j = j1;
				}
			}

			inputstream.close();
		} catch (IOException e) {
			return null;
		}
		if (!verify(buffer)) {
			return null;
		} else {
			return buffer;
		}
	}

	private final boolean verify(byte sha[]) {
		try {
			if (sha == null) {
				return false;
			}
			MessageDigest messagedigest = MessageDigest.getInstance("SHA");
			messagedigest.reset();
			messagedigest.update(sha);
			byte abyte1[] = messagedigest.digest();
			for (int i = 0; i < 20; i++) {
				if (abyte1[i] != Signature.sha[i]) {
					return false;
				}
			}

		} catch (Exception e) {
			error("sha");
		}
		return true;
	}

	private final byte[] loadFile(String directory) {
		try {
			File file = new File(directory);
			if (!file.exists()) {
				return null;
			} else {
				int length = (int) file.length();
				byte buffer[] = new byte[length];
				DataInputStream datainputstream = new DataInputStream(new BufferedInputStream(new FileInputStream(directory)));
				datainputstream.readFully(buffer, 0, length);
				datainputstream.close();
				return buffer;
			}
		} catch (IOException e) {
			return null;
		}
	}

	private final void saveFile(String directory, byte file[]) {
		try {
			FileOutputStream out = new FileOutputStream(directory);
			out.write(file, 0, file.length);
			out.close();
			return;
		} catch (IOException e) {
			error("savefile");
		}
	}

	private final void error(String error) {
		System.out.println(error);
		try {
			getAppletContext().showDocument(new URL(getCodeBase(), "loaderror_" + error + ".html"));
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		do {
			try {
				Thread.sleep(1000L);
			} catch (Exception e) {
			}
		} while (true);
	}

	@Override
	public final void start() {
		if (applet != null) {
			applet.start();
		}
	}

	@Override
	public final void stop() {
		if (applet != null) {
			applet.stop();
		}
	}

	@Override
	public final void destroy() {
		if (applet != null) {
			applet.destroy();
		}
	}

	@Override
	public final void update(Graphics g) {
		if (applet != null) {
			applet.update(g);
		}
	}

	@Override
	public final void paint(Graphics g) {
		if (applet != null) {
			applet.paint(g);
		}
	}

	public final String getMidi() {
		if (SignLink.midi == null) {
			return "none";
		} else {
			String midi = SignLink.midi;
			SignLink.midi = null;
			return midi;
		}
	}

	public final int getMidiVolume() {
		return SignLink.midiVolume;
	}

	public final int getMidiFade() {
		return SignLink.midiFade;
	}

	public final String getWave() {
		if (SignLink.wave == null) {
			return "none";
		} else {
			String s = SignLink.wave;
			SignLink.wave = null;
			return s;
		}
	}

	public final int getWaveVolume() {
		return SignLink.waveVolume;
	}
}