package gameengine.runners;

import static org.lwjgl.opengl.GL11.GL_BLEND;
import static org.lwjgl.opengl.GL11.GL_MODELVIEW;
import static org.lwjgl.opengl.GL11.GL_ONE_MINUS_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_PROJECTION;
import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.GL_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL11.glBlendFunc;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.opengl.GL11.glColor4f;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glLoadIdentity;
import static org.lwjgl.opengl.GL11.glMatrixMode; //Use GL11
import static org.lwjgl.opengl.GL11.glOrtho;
import static org.lwjgl.opengl.GL11.glPopMatrix;
import static org.lwjgl.opengl.GL11.glPushMatrix;
import static org.lwjgl.opengl.GL11.glTexCoord2f;
import static org.lwjgl.opengl.GL11.glTranslatef;
import static org.lwjgl.opengl.GL11.glVertex2f;
import static org.lwjgl.opengl.GL11.glVertex2i;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.swing.JOptionPane;
import javax.swing.Timer;

import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.input.Controller;
import org.lwjgl.input.Controllers;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Color;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;

public class CopyOfThreeGameTest {
	public static boolean ASYNC_INPUT=false;
	public static Timer INPUTPOLL_TIMER;
	public static int TIME_FOR_INPUT_POLL = 1000;
	public static boolean settupVSync=false;
	public static boolean vsync=false;
	public static boolean settupFullScreen=false;
	public static int SCREEN_HEIGHT=480;
	public static int SCREEN_WIDTH=640;
	public final static int LAYERS_IN_GAME = 4;
	public static float DAY_OPACITY=0f;
	public static int MOVEMENT_RADIUS;
	public static int mousex;
	public static int mousey;
	public static boolean mouseEnabled=false;
	public static String playerPath = "res/images/guy01.png";
	public static Texture player;
	public static int player_y=0;
	public static int player_x=0;
	public static int player_x_dir=0;
	public static boolean done=false;
	public static TrueTypeFont font=null;
	private static int loops=0;
	private static double interpolation;
	private static boolean FRAME_CAP = false;
	private static int PLAYER_SIZE=32;
	public final static int FRAMES_PER_SECOND = 60;
	public final static int TICKS_PER_SECOND = FRAMES_PER_SECOND;
	public final static int SKIP_TICKS = 1000 / TICKS_PER_SECOND;
	public final static int MAX_FRAMESKIP = 5;
	private static long next_game_tick = 0;

	public static void main(String[] args) {
		init();
		System.out.println("Open GL Version: "
				+ GL11.glGetString(GL11.GL_VERSION));
		gameLoop();
		quit();
	}

	private static void init() {
		try {
			Display.setTitle("The Game:");
			setDisplayMode(SCREEN_WIDTH, SCREEN_HEIGHT, false);
			Display.create();
		} catch (LWJGLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Font awtFont = new Font("Times New Roman", Font.BOLD, 12);
		font = new TrueTypeFont(awtFont, false);
		
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		glOrtho(0, SCREEN_WIDTH, SCREEN_HEIGHT, 0, 1, -1);
		
		glEnable(GL_TEXTURE_2D);
		glEnable(GL_BLEND);
		
		glClearColor(0.0f, 0.0f, 0.0f, 0.0f);  
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		glMatrixMode(GL_MODELVIEW);
		
		try {
			player = TextureLoader.getTexture("PNG", new FileInputStream(
					new File(playerPath)));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for(int c=0;c<Controllers.getControllerCount();c++){
			Controller control = Controllers.getController(c);
			System.out.println(control.getName());
			for (int i = 0; i < control.getButtonCount(); i++) {
				System.out.println(control.getButtonName(i));
			}
			System.out.println("Axis");
			for (int i = 0; i < control.getAxisCount(); i++) {
				System.out.println(control.getAxisName(i));
			}
		}
	}

	public static void quit() {
		Display.destroy();
		if (ASYNC_INPUT) {
			INPUTPOLL_TIMER.stop();
		}
	}

	private static void gameLoop() {
		lastFPS = getMilis();
		// get async input
		if (ASYNC_INPUT) {
			INPUTPOLL_TIMER = new Timer(TIME_FOR_INPUT_POLL,
					new ActionListener() {

						@Override
						public void actionPerformed(ActionEvent arg0) {
							// get user input
							input();
						}
					});
			INPUTPOLL_TIMER.start();
		}
		while (!(done||Display.isCloseRequested())) {
			
			loops = 0;
	        while( getMilis() > next_game_tick && loops < MAX_FRAMESKIP) {
	            update_game();

	            next_game_tick += SKIP_TICKS;
	            loops++;
	        }
	        interpolation = (double)( getMilis() + SKIP_TICKS - next_game_tick )
                    / (double) ( SKIP_TICKS );
			display_game(interpolation);
		}
	}
	
	private static void update_game() {
		
	}

	

	private static void display_game(double interpolation) {
		// Drawing code
		updateFPS();
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
	    GL11.glClear(GL11.GL_DEPTH_BUFFER_BIT);
		glPushMatrix();

		if (!ASYNC_INPUT) {
			input();// fetch input in the update
		}
		if (settupVSync) {
			vsync = !vsync;
			Display.setVSyncEnabled(vsync);
			settupVSync = false;
		}
		if (settupFullScreen) {
			settupFullScreen = false;
			setDisplayMode(SCREEN_WIDTH, SCREEN_HEIGHT, !Display.isFullscreen());
		}

		for (int i = 0; i < LAYERS_IN_GAME; i++){
			drawLayer(i);// layer
		}
		debug();// debug layer
		glPopMatrix();

		Display.update();
		if(FRAME_CAP)
			Display.sync(FRAMES_PER_SECOND);
	}

	private static void drawLayer(int i) {
		switch (i) {
		case 0:
			drawBackground();
			break;
		case LAYERS_IN_GAME - 3:
			drawLevel();
			break;
		case LAYERS_IN_GAME - 2:
			drawPlayer();
			break;
		case LAYERS_IN_GAME - 1:
			drawWeather();
			break;
		}

	}

	private static void drawLevel() {
		// TODO Auto-generated method stub

	}

	private static void drawPlayer() {
		glLoadIdentity();
		glTranslatef(0, 0, 0);
		player.bind();
		glBegin(GL_QUADS);
			glTexCoord2f(player_x_dir ,0);
			glVertex2f(player_x-PLAYER_SIZE,player_y-PLAYER_SIZE*2);
			glTexCoord2f(1-player_x_dir,0);
			glVertex2f(player_x,player_y-PLAYER_SIZE*2);
			glTexCoord2f(1-player_x_dir, 1);
			glVertex2f(player_x, player_y);
			glTexCoord2f(player_x_dir, 1);
			glVertex2f(player_x-PLAYER_SIZE, player_y);
		glEnd();
		glLoadIdentity();
	}

	private static void drawBackground() {
		glLoadIdentity();
		glBindTexture(GL_TEXTURE_2D, 0);
		glBegin(GL_QUADS);
		glColor4f(0f,0f,1f,1f);
			glTexCoord2f(0,0);
			glVertex2f(0,0);
			glTexCoord2f(1,0);
			glVertex2f(SCREEN_WIDTH,0);
			glTexCoord2f(1, 1);
			glVertex2f(SCREEN_WIDTH, SCREEN_HEIGHT);
			glTexCoord2f(0, 1);
			glVertex2f(0, SCREEN_HEIGHT);
		glEnd();
		glLoadIdentity();
		glColor4f(1f, 1f, 1f, 1f);
	}

	private static void drawWeather() {
		glBindTexture(GL_TEXTURE_2D, 0);
		glColor4f(0f, 0f, 0f, DAY_OPACITY);
		glBegin(GL_QUADS);
			glVertex2i(0, 0);
			glVertex2i(SCREEN_WIDTH, 0);
			glVertex2i(SCREEN_WIDTH, SCREEN_HEIGHT);
			glVertex2i(0, SCREEN_HEIGHT);
		glEnd();
		glColor4f(1f, 1f, 1f, 1f);
		glLoadIdentity();
	}

	/**
	 * Read user input
	 */
	public static void input() {
		if ((Math.abs(mousex - Mouse.getX()) > MOVEMENT_RADIUS
				&& (Math.abs(mousey - SCREEN_HEIGHT - Mouse.getY()) > MOVEMENT_RADIUS) || Mouse
					.isButtonDown(0)) && !mouseEnabled) {
			mouseEnabled = true;
			return;
		}
		if (mouseEnabled) {
			mousex = Mouse.getX();// -translate_x;
			mousey = SCREEN_HEIGHT - Mouse.getY();// -translate_y;
			boolean mouseClicked = Mouse.isButtonDown(0);
			if (mouseClicked) {
				// System.out.println("Mouse x:"+mousex+",  y:"+mousey);
			}
		}

		// Keyboard
		if (Keyboard.isKeyDown(Keyboard.KEY_W)) {
			player_y -= 10;
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_S)) {
			player_y += 10;
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_A)) {
			player_x -= 10;
			player_x_dir = 0;
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_D)) {
			player_x += 10;
			player_x_dir = 1;
		}

		int event = 0;
		// catch up with the queue
		while (Keyboard.next()) {
			int key = Keyboard.getEventKey();
			if (event == key) {
				continue;// Ignore duplicate events, eat away duplicates
			}
			if (key == Keyboard.KEY_P) {
				JOptionPane.showMessageDialog(null, "Done Saving");
			}
			if (key == Keyboard.KEY_L) {
				JOptionPane.showMessageDialog(null, "Done Loading");
			}

			if (key == Keyboard.KEY_ESCAPE) {
				done = true;
			}
			if (key == Keyboard.KEY_V) {
				// System.out.println("vsync is "+(vsync?"Off":"On" )+".");
				settupVSync = true;
			}
			if (key == Keyboard.KEY_F) {
				// System.out.println("fullscreen is "+(Display.isFullscreen()?"Off":"On"
				// )+".");
				settupFullScreen = true;
			}
			event = key;// Save the event poped from the QUEUE to eat away
						// duplicates.
			// System.out.println("event "+event);
		}
	}

	private static void debug() {
		//glColor4f(1f,1f,1f,1f);
		glEnable(GL_BLEND);
		font.drawString(0, 0, "FPS: " + fpsCount, Color.white);
		font.drawString(0, 10, "VSync: " + (vsync ? "ON" : "OFF"), Color.white);
		font.drawString(0, 20, "Player Position ("+player_x+","+player_y+")");
		font.drawString(0, 30, "Interpolation " + interpolation);
	}

	private static long lastFPS;
	private static int fps;
	private static int fpsCount;

	public static long getMilis() {
		// return System.nanoTime() / 1000000;
		return (Sys.getTime() * 1000) / Sys.getTimerResolution();
	}

	private static void updateFPS() {
		if (getMilis() - lastFPS > 1000) {
			fpsCount = fps;
			fps = 0;
			lastFPS += 1000;
		}
		fps++;
	}

	public static void setDisplayMode(int width, int height, boolean fullscreen) {

		// return if requested DisplayMode is already set
		if ((Display.getDisplayMode().getWidth() == width)
				&& (Display.getDisplayMode().getHeight() == height)
				&& (Display.isFullscreen() == fullscreen)) {
			return;
		}

		try {
			DisplayMode targetDisplayMode = null;

			if (fullscreen) {
				DisplayMode[] modes = Display.getAvailableDisplayModes();
				int freq = 0;

				for (int i = 0; i < modes.length; i++) {
					DisplayMode current = modes[i];

					if ((current.getWidth() >= width)
							&& (current.getHeight() >= height)) {
						if ((targetDisplayMode == null)
								|| (current.getFrequency() >= freq)) {
							if ((targetDisplayMode == null)
									|| (current.getBitsPerPixel() > targetDisplayMode
											.getBitsPerPixel())) {
								targetDisplayMode = current;
								freq = targetDisplayMode.getFrequency();
							}
						}

						if ((current.getBitsPerPixel() == Display
								.getDesktopDisplayMode().getBitsPerPixel())
								&& (current.getFrequency() == Display
										.getDesktopDisplayMode().getFrequency())) {
							targetDisplayMode = current;
							break;
						}
					}
				}
			} else {
				targetDisplayMode = new DisplayMode(width, height);
			}

			if (targetDisplayMode == null) {
				System.out.println("Failed to find value mode: " + width + "x"
						+ height + " fs=" + fullscreen);
				return;
			}

			Display.setDisplayMode(targetDisplayMode);
			Display.setFullscreen(fullscreen);

		} catch (LWJGLException e) {
			System.out.println("Unable to setup mode " + width + "x" + height
					+ " fullscreen=" + fullscreen + e);
		}
	}

}
