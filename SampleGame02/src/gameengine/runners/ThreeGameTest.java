package gameengine.runners;

import static org.lwjgl.opengl.GL11.*;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

public class ThreeGameTest {
	public static void main(String[] args) {
		init();
		gameLoop();
		quit();
	}

	private static void init() {
		try {
			Display.setDisplayMode( new DisplayMode(640,480));
			Display.setTitle("3D Stars");
			Display.create();
		} catch (LWJGLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private static void quit() {
		// TODO Auto-generated method stub
		
	}

	private static void gameLoop() {
		// TODO Auto-generated method stub
		while(!Display.isCloseRequested()){
			
		}
	}

}