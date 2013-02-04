package com.runescape;

/* GameStub - Decompiled by JODE
 * Visit http://jode.sourceforge.net/
 */
import java.applet.Applet;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import com.runescape.graphic.ImageRGB;
import com.runescape.graphic.ProducingGraphicsBuffer;
import com.runescape.util.SignLink;

@SuppressWarnings("serial")
public class GameStub extends Applet implements Runnable, MouseListener, MouseMotionListener, KeyListener,
		FocusListener, WindowListener {
	private int anInt2 = 24869;
	private int anInt4;
	private int anInt5 = 20;
	public int anInt6 = 1;
	private long[] aLongArray7 = new long[10];
	public int anInt8;
	public boolean aBoolean9 = false;
	public int width;
	public int height;
	public Graphics gameGraphics;
	public ProducingGraphicsBuffer producingGraphicsBuffer;
	public ImageRGB[] anImageRGBArray14 = new ImageRGB[6];
	public GameFrame gameFrame;
	public boolean aBoolean16 = true;
	public boolean aBoolean17 = true;
	public int anInt18;
	public int anInt19;
	public int anInt20;
	public int anInt21;
	public int anInt22;
	public int anInt23;
	public int anInt24;
	public long aLong25;
	public int anInt26;
	public int anInt27;
	public int anInt28;
	public long aLong29;
	public int[] keyArray = new int[128];
	private int[] anIntArray31 = new int[128];
	private int anInt32;
	private int anInt33;

	public final void method1(int height, int width) {
		this.width = width;
		this.height = height;
		gameFrame = new GameFrame(this, width, height);
		gameGraphics = method11(0).getGraphics();
		producingGraphicsBuffer = new ProducingGraphicsBuffer(width, height, method11(0));
		startRunnable(this, 1);
	}

	public final void method2(int height, boolean bool, int width) {
		try {
			this.width = width;
			this.height = height;
			gameGraphics = method11(0).getGraphics();
			producingGraphicsBuffer = new ProducingGraphicsBuffer(width, height, method11(0));
			startRunnable(this, 1);
			if (bool) {
				return;
			}
		} catch (RuntimeException runtimeexception) {
			SignLink.reportError("44058, " + height + ", " + bool + ", " + width + ", " + runtimeexception.toString());
			throw new RuntimeException();
		}
	}

	@Override
	public void run() {
		method11(0).addMouseListener(this);
		method11(0).addMouseMotionListener(this);
		method11(0).addKeyListener(this);
		method11(0).addFocusListener(this);
		if (gameFrame != null) {
			gameFrame.addWindowListener(this);
		}
		drawLoadingText(0, "Loading...");
		method6();
		int i = 0;
		int i_2_ = 256;
		int i_3_ = 1;
		int i_4_ = 0;
		int i_5_ = 0;
		for (int i_6_ = 0; i_6_ < 10; i_6_++) {
			aLongArray7[i_6_] = System.currentTimeMillis();
		}
		long l = System.currentTimeMillis();
		while (anInt4 >= 0) {
			if (anInt4 > 0) {
				anInt4--;
				if (anInt4 == 0) {
					method3(4747);
					return;
				}
			}
			int i_7_ = i_2_;
			int i_8_ = i_3_;
			i_2_ = 300;
			i_3_ = 1;
			l = System.currentTimeMillis();
			if (aLongArray7[i] == 0L) {
				i_2_ = i_7_;
				i_3_ = i_8_;
			} else if (l > aLongArray7[i]) {
				i_2_ = (int) (2560 * anInt5 / (l - aLongArray7[i]));
			}
			if (i_2_ < 25) {
				i_2_ = 25;
			}
			if (i_2_ > 256) {
				i_2_ = 256;
				i_3_ = (int) (anInt5 - (l - aLongArray7[i]) / 10L);
			}
			if (i_3_ > anInt5) {
				i_3_ = anInt5;
			}
			aLongArray7[i] = l;
			i = (i + 1) % 10;
			if (i_3_ > 1) {
				for (int i_9_ = 0; i_9_ < 10; i_9_++) {
					if (aLongArray7[i_9_] != 0L) {
						aLongArray7[i_9_] += i_3_;
					}
				}
			}
			if (i_3_ < anInt6) {
				i_3_ = anInt6;
			}
			try {
				Thread.sleep(i_3_);
			} catch (InterruptedException interruptedexception) {
				i_5_++;
			}
			for (/**/; i_4_ < 256; i_4_ += i_2_) {
				anInt26 = anInt22;
				anInt27 = anInt23;
				anInt28 = anInt24;
				aLong29 = aLong25;
				anInt22 = 0;
				method7(24869);
				anInt32 = anInt33;
			}
			i_4_ &= 0xff;
			if (anInt5 > 0) {
				anInt8 = 1000 * i_2_ / (anInt5 * 256);
			}
			method9(0);
			if (aBoolean9) {
				System.out.println("ntime:" + l);
				for (int i_10_ = 0; i_10_ < 10; i_10_++) {
					int i_11_ = (i - i_10_ - 1 + 20) % 10;
					System.out.println("otim" + i_11_ + ":" + aLongArray7[i_11_]);
				}
				System.out.println("fps:" + anInt8 + " ratio:" + i_2_ + " count:" + i_4_);
				System.out.println("del:" + i_3_ + " deltime:" + anInt5 + " mindel:" + anInt6);
				System.out.println("intex:" + i_5_ + " opos:" + i);
				aBoolean9 = false;
				i_5_ = 0;
			}
		}
		if (anInt4 == -1) {
			method3(4747);
		}
	}

	public final void method3(int i) {
		try {
			anInt4 = -2;
			method8(493);
			if (i == 4747) {
				if (gameFrame != null) {
					try {
						Thread.sleep(1000L);
					} catch (Exception exception) {
						/* empty */
					}
					try {
						System.exit(0);
					} catch (Throwable throwable) {
						/* empty */
					}
				}
			}
		} catch (RuntimeException runtimeexception) {
			SignLink.reportError("13735, " + i + ", " + runtimeexception.toString());
			throw new RuntimeException();
		}
	}

	public final void method4(boolean bool, int i) {
		try {
			if (!bool) {
				anInt5 = 1000 / i;
			}
		} catch (RuntimeException runtimeexception) {
			SignLink.reportError("88292, " + bool + ", " + i + ", " + runtimeexception.toString());
			throw new RuntimeException();
		}
	}

	@Override
	public final void start() {
		if (anInt4 >= 0) {
			anInt4 = 0;
		}
	}

	@Override
	public final void stop() {
		if (anInt4 >= 0) {
			anInt4 = 4000 / anInt5;
		}
	}

	@Override
	public final void destroy() {
		anInt4 = -1;
		try {
			Thread.sleep(5000L);
		} catch (Exception exception) {
			/* empty */
		}
		if (anInt4 == -1) {
			method3(4747);
		}
	}

	@Override
	public final void update(Graphics graphics) {
		if (gameGraphics == null) {
			gameGraphics = graphics;
		}
		aBoolean16 = true;
		method10((byte) 1);
	}

	@Override
	public final void paint(Graphics graphics) {
		if (gameGraphics == null) {
			gameGraphics = graphics;
		}
		aBoolean16 = true;
		method10((byte) 1);
	}

	@Override
	public final void mousePressed(MouseEvent mouseevent) {
		int i = mouseevent.getX();
		int i_12_ = mouseevent.getY();
		if (gameFrame != null) {
			i -= 4;
			i_12_ -= 22;
		}
		anInt18 = 0;
		anInt23 = i;
		anInt24 = i_12_;
		aLong25 = System.currentTimeMillis();
		if (mouseevent.isMetaDown()) {
			anInt22 = 2;
			anInt19 = 2;
		} else {
			anInt22 = 1;
			anInt19 = 1;
		}
	}

	@Override
	public final void mouseReleased(MouseEvent mouseevent) {
		anInt18 = 0;
		anInt19 = 0;
	}

	@Override
	public final void mouseClicked(MouseEvent mouseevent) {
		/* empty */
	}

	@Override
	public final void mouseEntered(MouseEvent mouseevent) {
		/* empty */
	}

	@Override
	public final void mouseExited(MouseEvent mouseevent) {
		anInt18 = 0;
		anInt20 = -1;
		anInt21 = -1;
	}

	@Override
	public final void mouseDragged(MouseEvent mouseevent) {
		int i = mouseevent.getX();
		int i_13_ = mouseevent.getY();
		if (gameFrame != null) {
			i -= 4;
			i_13_ -= 22;
		}
		anInt18 = 0;
		anInt20 = i;
		anInt21 = i_13_;
	}

	@Override
	public final void mouseMoved(MouseEvent mouseevent) {
		int i = mouseevent.getX();
		int i_14_ = mouseevent.getY();
		if (gameFrame != null) {
			i -= 4;
			i_14_ -= 22;
		}
		anInt18 = 0;
		anInt20 = i;
		anInt21 = i_14_;
	}

	@Override
	public final void keyPressed(KeyEvent keyevent) {
		anInt18 = 0;
		int i = keyevent.getKeyCode();
		int i_15_ = keyevent.getKeyChar();
		if (i_15_ < 30) {
			i_15_ = 0;
		}
		if (i == 37) {
			i_15_ = 1;
		}
		if (i == 39) {
			i_15_ = 2;
		}
		if (i == 38) {
			i_15_ = 3;
		}
		if (i == 40) {
			i_15_ = 4;
		}
		if (i == 17) {
			i_15_ = 5;
		}
		if (i == 8) {
			i_15_ = 8;
		}
		if (i == 127) {
			i_15_ = 8;
		}
		if (i == 9) {
			i_15_ = 9;
		}
		if (i == 10) {
			i_15_ = 10;
		}
		if (i >= 112 && i <= 123) {
			i_15_ = 1008 + i - 112;
		}
		if (i == 36) {
			i_15_ = 1000;
		}
		if (i == 35) {
			i_15_ = 1001;
		}
		if (i == 33) {
			i_15_ = 1002;
		}
		if (i == 34) {
			i_15_ = 1003;
		}
		if (i_15_ > 0 && i_15_ < 128) {
			keyArray[i_15_] = 1;
		}
		if (i_15_ > 4) {
			anIntArray31[anInt33] = i_15_;
			anInt33 = anInt33 + 1 & 0x7f;
		}
	}

	@Override
	public final void keyReleased(KeyEvent keyevent) {
		anInt18 = 0;
		int i = keyevent.getKeyCode();
		char c = keyevent.getKeyChar();
		if (c < '\036') {
			c = '\0';
		}
		if (i == 37) {
			c = '\001';
		}
		if (i == 39) {
			c = '\002';
		}
		if (i == 38) {
			c = '\003';
		}
		if (i == 40) {
			c = '\004';
		}
		if (i == 17) {
			c = '\005';
		}
		if (i == 8) {
			c = '\010';
		}
		if (i == 127) {
			c = '\010';
		}
		if (i == 9) {
			c = '\t';
		}
		if (i == 10) {
			c = '\n';
		}
		if (c > 0 && c < '\u0080') {
			keyArray[c] = 0;
		}
	}

	@Override
	public final void keyTyped(KeyEvent keyevent) {
		/* empty */
	}

	public final int method5(int i) {
		try {
			while (i >= 0) {
				for (int i_16_ = 1; i_16_ > 0; i_16_++) {
					/* empty */
				}
			}
			int i_17_ = -1;
			if (anInt33 != anInt32) {
				i_17_ = anIntArray31[anInt32];
				anInt32 = anInt32 + 1 & 0x7f;
			}
			return i_17_;
		} catch (RuntimeException runtimeexception) {
			SignLink.reportError("66235, " + i + ", " + runtimeexception.toString());
			throw new RuntimeException();
		}
	}

	@Override
	public final void focusGained(FocusEvent focusevent) {
		aBoolean17 = true;
		aBoolean16 = true;
		method10((byte) 1);
	}

	@Override
	public final void focusLost(FocusEvent focusevent) {
		aBoolean17 = false;
		for (int i = 0; i < 128; i++) {
			keyArray[i] = 0;
		}
	}

	@Override
	public final void windowActivated(WindowEvent windowevent) {
		/* empty */
	}

	@Override
	public final void windowClosed(WindowEvent windowevent) {
		/* empty */
	}

	@Override
	public final void windowClosing(WindowEvent windowevent) {
		destroy();
	}

	@Override
	public final void windowDeactivated(WindowEvent windowevent) {
		/* empty */
	}

	@Override
	public final void windowDeiconified(WindowEvent windowevent) {
		/* empty */
	}

	@Override
	public final void windowIconified(WindowEvent windowevent) {
		/* empty */
	}

	@Override
	public final void windowOpened(WindowEvent windowevent) {
		/* empty */
	}

	public void method6() {
		/* empty */
	}

	public void method7(int i) {
		try {
			if (i != anInt2) {
				return;
			}
		} catch (RuntimeException runtimeexception) {
			SignLink.reportError("38427, " + i + ", " + runtimeexception.toString());
			throw new RuntimeException();
		}
	}

	public void method8(int i) {
		try {
			i = 91 / i;
		} catch (RuntimeException runtimeexception) {
			SignLink.reportError("49106, " + i + ", " + runtimeexception.toString());
			throw new RuntimeException();
		}
	}

	public void method9(int i) {
		do {
			try {
				if (i == 0) {
					break;
				}
				for (int i_18_ = 1; i_18_ > 0; i_18_++) {
					/* empty */
				}
			} catch (RuntimeException runtimeexception) {
				SignLink.reportError("62415, " + i + ", " + runtimeexception.toString());
				throw new RuntimeException();
			}
			break;
		} while (false);
	}

	public void method10(byte b) {
		try {
			if (b == 1) {
				b = (byte) 0;
			}
		} catch (RuntimeException runtimeexception) {
			SignLink.reportError("6639, " + b + ", " + runtimeexception.toString());
			throw new RuntimeException();
		}
	}

	public Component method11(int i) {
		try {
			if (i != 0) {
				for (int i_19_ = 1; i_19_ > 0; i_19_++) {
					/* empty */
				}
			}
			if (gameFrame != null) {
				return gameFrame;
			}
			return this;
		} catch (RuntimeException runtimeexception) {
			SignLink.reportError("82353, " + i + ", " + runtimeexception.toString());
			throw new RuntimeException();
		}
	}

	public void startRunnable(Runnable runnable, int i) {
		Thread thread = new Thread(runnable);
		thread.start();
		thread.setPriority(i);
	}

	public void drawLoadingText(int i, String string) {
		while (gameGraphics == null) {
			gameGraphics = method11(0).getGraphics();
			try {
				method11(0).repaint();
			} catch (Exception exception) {
				/* empty */
			}
			try {
				Thread.sleep(1000L);
			} catch (Exception exception) {
				/* empty */
			}
		}
		Font font = new Font("Helvetica", 1, 13);
		FontMetrics fontmetrics = method11(0).getFontMetrics(font);
		Font font_20_ = new Font("Helvetica", 0, 13);
		method11(0).getFontMetrics(font_20_);
		if (aBoolean16) {
			gameGraphics.setColor(Color.black);
			gameGraphics.fillRect(0, 0, width, height);
			aBoolean16 = false;
		}
		Color color = new Color(140, 17, 17);
		int i_21_ = height / 2 - 18;
		gameGraphics.setColor(color);
		gameGraphics.drawRect(width / 2 - 152, i_21_, 304, 34);
		gameGraphics.fillRect(width / 2 - 150, i_21_ + 2, i * 3, 30);
		gameGraphics.setColor(Color.black);
		gameGraphics.fillRect(width / 2 - 150 + i * 3, i_21_ + 2, 300 - i * 3, 30);
		gameGraphics.setFont(font);
		gameGraphics.setColor(Color.white);
		gameGraphics.drawString(string, (width - fontmetrics.stringWidth(string)) / 2, i_21_ + 22);
	}
}
