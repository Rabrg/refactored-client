package com.runescape;

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

import com.runescape.cache.media.ImageRGB;
import com.runescape.media.ProducingGraphicsBuffer;

@SuppressWarnings("serial")
public class GameShell extends Applet implements Runnable, MouseListener, MouseMotionListener, KeyListener,
		FocusListener, WindowListener {

	private int gameState;
	private int deltime = 20;
	public int mindel = 1;
	private long[] optims = new long[10];
	public int fps;
	public boolean dumpRequested = false;
	public int width;
	public int height;
	public Graphics gameGraphics;
	public ProducingGraphicsBuffer producingGraphicsBuffer;
	public ImageRGB[] anImageRGBArray14 = new ImageRGB[6];
	public GameFrame gameFrame;
	public boolean clearScreen = true;
	public boolean awtFocus = true;
	public int idleTime;
	public int mouseButtonPressed;
	public int mouseEventX;
	public int mouseEventY;
	public int eventMouseButtonPressed;
	public int eventClickX;
	public int eventClickY;
	public long lastClick;
	public int clickType;
	public int clickX;
	public int clickY;
	public long clickTime;
	public int[] keyStatus = new int[128];
	private int[] inputBuffer = new int[128];
	private int readIndex;
	private int writeIndex;

	public final void initializeApplication(int width, int height) {
		this.width = width;
		this.height = height;
		gameFrame = new GameFrame(this, width, height);
		gameGraphics = getComponent().getGraphics();
		producingGraphicsBuffer = new ProducingGraphicsBuffer(width, height, getComponent());
		startRunnable(this, 1);
	}

	public final void initializeApplet(int width, int height) {
		this.width = width;
		this.height = height;
		gameGraphics = getComponent().getGraphics();
		producingGraphicsBuffer = new ProducingGraphicsBuffer(width, height, getComponent());
		startRunnable(this, 1);
	}

	@Override
	public void run() {
		getComponent().addMouseListener(this);
		getComponent().addMouseMotionListener(this);
		getComponent().addKeyListener(this);
		getComponent().addFocusListener(this);
		if (gameFrame != null) {
			gameFrame.addWindowListener(this);
		}
		drawLoadingText(0, "Loading...");
		startup();
		int opos = 0;
		int ratio = 256;
		int del = 1;
		int count = 0;
		int intex = 0;
		for (int optim = 0; optim < 10; optim++) {
			optims[optim] = System.currentTimeMillis();
		}
		long currentTime = System.currentTimeMillis();
		while (gameState >= 0) {
			if (gameState > 0) {
				gameState--;
				if (gameState == 0) {
					exit();
					return;
				}
			}
			ratio = 300;
			del = 1;
			currentTime = System.currentTimeMillis();
			if (currentTime > optims[opos]) {
				ratio = (int) (2560 * deltime / (currentTime - optims[opos]));
			}
			if (ratio < 25) {
				ratio = 25;
			}
			if (ratio > 256) {
				ratio = 256;
				del = (int) (deltime - (currentTime - optims[opos]) / 10L);
			}
			if (del > deltime) {
				del = deltime;
			}
			optims[opos] = currentTime;
			opos = (opos + 1) % 10;
			if (del > 1) {
				for (int optim = 0; optim < 10; optim++) {
					if (optims[optim] != 0L) {
						optims[optim] += del;
					}
				}
			}
			if (del < mindel) {
				del = mindel;
			}
			try {
				Thread.sleep(del);
			} catch (InterruptedException interruptedexception) {
				intex++;
			}
			for (; count < 256; count += ratio) {
				clickType = eventMouseButtonPressed;
				clickX = eventClickX;
				clickY = eventClickY;
				clickTime = lastClick;
				eventMouseButtonPressed = 0;
				doLogic();
				readIndex = writeIndex;
			}
			count &= 0xff;
			if (deltime > 0) {
				fps = 1000 * ratio / (deltime * 256);
			}
			repaintGame();
			if (dumpRequested) {
				System.out.println("ntime:" + currentTime);
				for (int i = 0; i < 10; i++) {
					int optim = (opos - i - 1 + 20) % 10;
					System.out.println("otim" + optim + ":" + optims[optim]);
				}
				System.out.println("fps:" + fps + " ratio:" + ratio + " count:" + count);
				System.out.println("del:" + del + " deltime:" + deltime + " mindel:" + mindel);
				System.out.println("intex:" + intex + " opos:" + opos);
				dumpRequested = false;
				intex = 0;
			}
		}
		if (gameState == -1) {
			exit();
		}
	}

	public final void exit() {
		gameState = -2;
		shutdown();
		if (gameFrame != null) {
			try {
				Thread.sleep(1000L);
			} catch (Exception exception) {
			}
			System.exit(0);
		}
	}

	public final void setFrameRate(int frameRate) {
		deltime = 1000 / frameRate;
	}

	@Override
	public final void start() {
		if (gameState >= 0) {
			gameState = 0;
		}
	}

	@Override
	public final void stop() {
		if (gameState >= 0) {
			gameState = 4000 / deltime;
		}
	}

	@Override
	public final void destroy() {
		gameState = -1;
		try {
			Thread.sleep(5000L);
		} catch (Exception exception) {
		}
		if (gameState == -1) {
			exit();
		}
	}

	@Override
	public final void update(Graphics graphics) {
		if (gameGraphics == null) {
			gameGraphics = graphics;
		}
		clearScreen = true;
		redraw();
	}

	@Override
	public final void paint(Graphics graphics) {
		if (gameGraphics == null) {
			gameGraphics = graphics;
		}
		clearScreen = true;
		redraw();
	}

	protected void redraw() {

	}

	@Override
	public final void mousePressed(MouseEvent mouseevent) {
		int mouseX = mouseevent.getX();
		int mouseY = mouseevent.getY();
		if (gameFrame != null) {
			mouseX -= 4;
			mouseY -= 22;
		}
		idleTime = 0;
		eventClickX = mouseX;
		eventClickY = mouseY;
		lastClick = System.currentTimeMillis();
		if (mouseevent.isMetaDown()) {
			eventMouseButtonPressed = 2;
			mouseButtonPressed = 2;
		} else {
			eventMouseButtonPressed = 1;
			mouseButtonPressed = 1;
		}
	}

	@Override
	public final void mouseReleased(MouseEvent mouseevent) {
		idleTime = 0;
		mouseButtonPressed = 0;
	}

	@Override
	public final void mouseClicked(MouseEvent mouseevent) {

	}

	@Override
	public final void mouseEntered(MouseEvent mouseevent) {

	}

	@Override
	public final void mouseExited(MouseEvent mouseevent) {
		idleTime = 0;
		mouseEventX = -1;
		mouseEventY = -1;
	}

	@Override
	public final void mouseDragged(MouseEvent mouseevent) {
		int mouseX = mouseevent.getX();
		int mouseY = mouseevent.getY();
		if (gameFrame != null) {
			mouseX -= 4;
			mouseY -= 22;
		}
		idleTime = 0;
		mouseEventX = mouseX;
		mouseEventY = mouseY;
	}

	@Override
	public final void mouseMoved(MouseEvent mouseevent) {
		int mouseX = mouseevent.getX();
		int mouseY = mouseevent.getY();
		if (gameFrame != null) {
			mouseX -= 4;
			mouseY -= 22;
		}
		idleTime = 0;
		mouseEventX = mouseX;
		mouseEventY = mouseY;
	}

	@Override
	public final void keyPressed(KeyEvent keyevent) {
		idleTime = 0;
		int keyCode = keyevent.getKeyCode();
		int keyChar = keyevent.getKeyChar();
		if (keyChar < 30) {
			keyChar = 0;
		}
		if (keyCode == 37) {
			keyChar = 1;
		}
		if (keyCode == 39) {
			keyChar = 2;
		}
		if (keyCode == 38) {
			keyChar = 3;
		}
		if (keyCode == 40) {
			keyChar = 4;
		}
		if (keyCode == 17) {
			keyChar = 5;
		}
		if (keyCode == 8) {
			keyChar = 8;
		}
		if (keyCode == 127) {
			keyChar = 8;
		}
		if (keyCode == 9) {
			keyChar = 9;
		}
		if (keyCode == 10) {
			keyChar = 10;
		}
		if (keyCode >= 112 && keyCode <= 123) {
			keyChar = 1008 + keyCode - 112;
		}
		if (keyCode == 36) {
			keyChar = 1000;
		}
		if (keyCode == 35) {
			keyChar = 1001;
		}
		if (keyCode == 33) {
			keyChar = 1002;
		}
		if (keyCode == 34) {
			keyChar = 1003;
		}
		if (keyChar > 0 && keyChar < 128) {
			keyStatus[keyChar] = 1;
		}
		if (keyChar > 4) {
			inputBuffer[writeIndex] = keyChar;
			writeIndex = writeIndex + 1 & 0x7f;
		}
	}

	@Override
	public final void keyReleased(KeyEvent keyevent) {
		idleTime = 0;
		int keyCode = keyevent.getKeyCode();
		char keyChar = keyevent.getKeyChar();
		if (keyChar < '\036') {
			keyChar = '\0';
		}
		if (keyCode == 37) {
			keyChar = '\001';
		}
		if (keyCode == 39) {
			keyChar = '\002';
		}
		if (keyCode == 38) {
			keyChar = '\003';
		}
		if (keyCode == 40) {
			keyChar = '\004';
		}
		if (keyCode == 17) {
			keyChar = '\005';
		}
		if (keyCode == 8) {
			keyChar = '\010';
		}
		if (keyCode == 127) {
			keyChar = '\010';
		}
		if (keyCode == 9) {
			keyChar = '\t';
		}
		if (keyCode == 10) {
			keyChar = '\n';
		}
		if (keyChar > 0 && keyChar < '\u0080') {
			keyStatus[keyChar] = 0;
		}
	}

	@Override
	public final void keyTyped(KeyEvent keyevent) {

	}

	public final int readCharacter() {
		int character = -1;
		if (writeIndex != readIndex) {
			character = inputBuffer[readIndex];
			readIndex = readIndex + 1 & 0x7f;
		}
		return character;
	}

	@Override
	public final void focusGained(FocusEvent focusevent) {
		awtFocus = true;
		clearScreen = true;
		redraw();
	}

	@Override
	public final void focusLost(FocusEvent focusevent) {
		awtFocus = false;
		for (int key = 0; key < 128; key++) {
			keyStatus[key] = 0;
		}
	}

	@Override
	public final void windowActivated(WindowEvent windowevent) {

	}

	@Override
	public final void windowClosed(WindowEvent windowevent) {

	}

	@Override
	public final void windowClosing(WindowEvent windowevent) {
		destroy();
	}

	@Override
	public final void windowDeactivated(WindowEvent windowevent) {

	}

	@Override
	public final void windowDeiconified(WindowEvent windowevent) {

	}

	@Override
	public final void windowIconified(WindowEvent windowevent) {

	}

	@Override
	public final void windowOpened(WindowEvent windowevent) {

	}

	public void startup() {
	}

	public void doLogic() {
	}

	public void shutdown() {
	}

	public void repaintGame() {
	}

	public Component getComponent() {
		if (gameFrame != null) {
			return gameFrame;
		}
		return this;
	}

	public void startRunnable(Runnable runnable, int priority) {
		Thread thread = new Thread(runnable);
		thread.start();
		thread.setPriority(priority);
	}

	public void drawLoadingText(int percentage, String string) {
		while (gameGraphics == null) {
			gameGraphics = getComponent().getGraphics();
			try {
				getComponent().repaint();
				Thread.sleep(1000L);
			} catch (Exception exception) {
			}
		}
		Font helveticaBold = new Font("Helvetica", 1, 13);
		FontMetrics fontmetrics = getComponent().getFontMetrics(helveticaBold);
		Font helvetica = new Font("Helvetica", 0, 13);
		getComponent().getFontMetrics(helvetica);
		if (clearScreen) {
			gameGraphics.setColor(Color.black);
			gameGraphics.fillRect(0, 0, width, height);
			clearScreen = false;
		}
		Color color = new Color(140, 17, 17);
		int centerHeight = height / 2 - 18;
		gameGraphics.setColor(color);
		gameGraphics.drawRect(width / 2 - 152, centerHeight, 304, 34);
		gameGraphics.fillRect(width / 2 - 150, centerHeight + 2, percentage * 3, 30);
		gameGraphics.setColor(Color.black);
		gameGraphics.fillRect(width / 2 - 150 + percentage * 3, centerHeight + 2, 300 - percentage * 3, 30);
		gameGraphics.setFont(helveticaBold);
		gameGraphics.setColor(Color.white);
		gameGraphics.drawString(string, (width - fontmetrics.stringWidth(string)) / 2, centerHeight + 22);
	}
}
