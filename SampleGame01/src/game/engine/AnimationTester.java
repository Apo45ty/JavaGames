package game.engine;

import static minecraft2d.World.BLOCK_SIZE;
import static org.lwjgl.opengl.GL11.GL_BLEND;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_MODELVIEW;
import static org.lwjgl.opengl.GL11.GL_ONE_MINUS_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_PROJECTION;
import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.GL_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glBlendFunc;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glLoadIdentity;
import static org.lwjgl.opengl.GL11.glMatrixMode;
import static org.lwjgl.opengl.GL11.glOrtho;
import static org.lwjgl.opengl.GL11.glTexCoord2f;
import static org.lwjgl.opengl.GL11.glTranslatef;
import static org.lwjgl.opengl.GL11.glVertex2f;

import java.awt.Font;

import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.newdawn.slick.Color;
import org.newdawn.slick.TrueTypeFont;

public class AnimationTester {

	private static TrueTypeFont font;
	private static TempSprite guy01;

	public static void main(String[] args) {
		init();
		gameLoop();
		quit();
	}

	private static void init() {
		try {
			Display.setDisplayMode(new DisplayMode(640, 480));
			Display.setTitle("3D Stars");
			Display.create();
		} catch (LWJGLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Font awtFont = new Font("Times New Roman", Font.BOLD, 12);
		font = new TrueTypeFont(awtFont, false);
		String[] partNames =
			{
				"Belly.png",
				"Chest.png",
				"Eyes.png",
				"Eyes02.png",
				"Head.png",
				"Hips.png",
				"LeftFoot.png",
				"LeftForearm.png",
				"LeftHand.png",
				"LeftKnee.png",
				"LeftLeg.png",
				"LeftShoulder.png",
				"Pants.png",
				"RightFoot.png",
				"RightForearm.png",
				"RightHand.png",
				"RightKnee.png",
				"RightLeg.png",
				"RightShoulder.png",
				"ChestPlate.png"
			};
		guy01 = new TempSprite("res/images/Characters/guy01/layers/",partNames,null,null, 32,64);
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		glOrtho(0, 640, 480, 0, 1, -1);

		glEnable(GL_TEXTURE_2D);
		glEnable(GL_BLEND);

		glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		glMatrixMode(GL_MODELVIEW);
	}

	private static void quit() {
		Display.destroy();
	}

	private static void gameLoop() {
		while (!Display.isCloseRequested()) {
			glClear(GL_COLOR_BUFFER_BIT);
			
			guy01.Draw();
			debug();
			Display.update();
			Display.sync(60);
		}
	}

	private static void debug() {
		updateFPS();
		font.drawString(0, 0, "FPS: " + fpsCount, Color.white);
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
}
