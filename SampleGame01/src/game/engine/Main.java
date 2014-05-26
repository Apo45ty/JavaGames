/**
 * Project: SampleGame01
 * Package: game.engine
 * Author: EltonJohn
 * Date: May 23, 2014
 * Time: 6:36:52 PM
 */
package game.engine;

import static org.lwjgl.opengl.GL11.*; //Use GL11
import static minecraft2d.World.BLOCKS_HEIGHT;
import static minecraft2d.World.BLOCKS_WIDTH;
import static minecraft2d.World.BLOCK_SIZE;
import static minecraft2d.World.saveFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.JOptionPane;

import minecraft2d.Block;
import minecraft2d.BlockGrid;
import minecraft2d.BlockType;
import minecraft2d.World;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;

public class Main {
	public static BlockGrid grid;
	public static BlockType selection;
	public static int SCREEN_WIDTH =BLOCK_SIZE*BLOCKS_WIDTH,SCREEN_HEIGHT =BLOCK_SIZE*BLOCKS_HEIGHT,MOVEMENT_RADIUS=150;
	public static Texture background ;
	public static String backgroundPath = "res/images/background.png";
	private static boolean done = false;
	private static int mousex;
	private static int mousey;
	private static int selector_x = 0;
	private static int selector_y = 0;
	private static boolean mouseEnabled = true;
	private static boolean vsync = false;
	
	public static void createDisplay() {
		try {
			Display.setTitle("Minecraft2D");
			setDisplayMode(SCREEN_WIDTH, SCREEN_HEIGHT, false);
			Display.create();
		} catch (LWJGLException e) {
			e.printStackTrace();
		}
		
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		glOrtho(0, SCREEN_WIDTH, SCREEN_HEIGHT, 0, 1, -1);
		glMatrixMode(GL_MODELVIEW);
		glEnable(GL_TEXTURE_2D);
		glEnable(GL_BLEND);
		
		glClearColor(0.0f, 0.0f, 0.0f, 0.0f);  
			glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
			glViewport(0,0,SCREEN_WIDTH,SCREEN_HEIGHT);
		glMatrixMode(GL_MODELVIEW);
		
		setUpBackground();
		grid = new BlockGrid();
		selection = BlockType.STONE;
	}

	private static void setUpBackground() {
		Texture result = null;
		try {
			result = TextureLoader.getTexture("PNG", new FileInputStream(
					new File(backgroundPath)));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		background = result;
	}
	
	public static void drawSelectionBox(){
		int selection_x = selector_x * BLOCK_SIZE;
		int selection_y = selector_y * BLOCK_SIZE;
		
		if(grid.getAt(selector_x, selector_y).getType() != BlockType.AIR){	
			glBindTexture(GL_TEXTURE_2D, 0);
			glColor4f(1f,0f,0f,0.5f);
			glBegin(GL_QUADS);
				glVertex2i(selection_x,selection_y);
				glVertex2i(selection_x+BLOCK_SIZE,selection_y);
				glVertex2i(selection_x+BLOCK_SIZE, selection_y+BLOCK_SIZE);
				glVertex2i(selection_x, selection_y+BLOCK_SIZE);
			glEnd();
			glColor4f(1f,1f,1f,1f);
		} else {
			glColor4f(1f,1f,1f,0.5f);
			new Block(selection, selection_x, selection_y).draw();
			glColor4f(1f,1f,1f,1f);
		}
	}
	
	public static void gameLoop() { 
		lastFPS = getMilis();
		while(!(done||Display.isCloseRequested()))	{
			//Drawing code
			glClear(GL_COLOR_BUFFER_BIT);
			debug();
			
			//get user input
			input();
			drawBackground();
			grid.draw();
			drawSelectionBox();
			Display.update();
			Display.sync(60);
		}
	}
	private static void drawBackground() {
		background.bind();
		glLoadIdentity();
		glTranslatef(0, 0, 0);
		glBegin(GL_QUADS);
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
	}

	public static void input(){
		if((Math.abs(mousex - Mouse.getX())>MOVEMENT_RADIUS&&(Math.abs(mousey - SCREEN_HEIGHT - Mouse.getY())>MOVEMENT_RADIUS)||Mouse.isButtonDown(0))&&!mouseEnabled){
			mouseEnabled = true;
			return;
		}
		if(mouseEnabled){
			mousex = Mouse.getX();
			mousey = SCREEN_HEIGHT - Mouse.getY();
			selector_x = (int)(mousex/BLOCK_SIZE);
			selector_y = (int)(mousey/BLOCK_SIZE);
			boolean mouseClicked =  Mouse.isButtonDown(0);
			if(mouseClicked){
				grid.setAt(selector_x, selector_y, selection);
			}
		}
		int event=0;
		//catch up with the queue
		while(Keyboard.next()){
			if(event==Keyboard.getEventKey()){
				continue;//Ignore duplicate events, eat away duplicates
			}
			if(Keyboard.getEventKey() == Keyboard.KEY_RIGHT && Keyboard.getEventKeyState()){
				mouseEnabled=false;
				if(selector_x + 1 < BLOCKS_WIDTH-1){
					selector_x++;
				}
			}
			if(Keyboard.getEventKey() == Keyboard.KEY_LEFT && Keyboard.getEventKeyState()){
				mouseEnabled=false;
				if(selector_x - 1 >= 0){
					selector_x--;
				}
			}
			if(Keyboard.getEventKey() == Keyboard.KEY_UP && Keyboard.getEventKeyState()){
				mouseEnabled=false;
				if(selector_y - 1 >= 0){
					selector_y--;
				}
			}
			if(Keyboard.getEventKey() == Keyboard.KEY_DOWN && Keyboard.getEventKeyState()){
				mouseEnabled=false;
				if(!(selector_y + 1 > BLOCKS_HEIGHT-1)){
					selector_y++;
				}
			}
			if(Keyboard.getEventKey() == Keyboard.KEY_SPACE||Keyboard.isKeyDown(Keyboard.KEY_SPACE)){
				grid.setAt(selector_x, selector_y, selection);
			}
			if(Keyboard.getEventKey() == Keyboard.KEY_S){
				grid.save(new File(saveFile));//TODO move this somewhere else
				JOptionPane.showMessageDialog(null, "Done Saving");
			}
			if(Keyboard.getEventKey() == Keyboard.KEY_L){
				grid.load(new File(saveFile));//TODO move this somewhere else
				JOptionPane.showMessageDialog(null, "Done loading");
			}
			if(Keyboard.getEventKey() == Keyboard.KEY_1){
				selection= BlockType.STONE;
			}
			if(Keyboard.getEventKey() == Keyboard.KEY_2){
				selection= BlockType.DIRT;
			}
			if(Keyboard.getEventKey() == Keyboard.KEY_3){
				selection= BlockType.GRASS;
			}
			if(Keyboard.getEventKey() == Keyboard.KEY_4){
				selection= BlockType.AIR;
			}
			if(Keyboard.getEventKey() == Keyboard.KEY_C){
				grid.clear();
			}
			if(Keyboard.getEventKey() == Keyboard.KEY_ESCAPE){
				done = true;
			}
			if (Keyboard.getEventKey() == Keyboard.KEY_V){
				System.out.println("vsync is "+(vsync?"Off":"On" )+".");
	        	vsync  = !vsync;
	        	Display.setVSyncEnabled(vsync);
	        }
			if (Keyboard.getEventKey() == Keyboard.KEY_F) {
				System.out.println("fullscreen is "+(Display.isFullscreen()?"Off":"On" )+".");
	        	setDisplayMode(SCREEN_WIDTH, SCREEN_HEIGHT, !Display.isFullscreen());
	        }
			event = Keyboard.getEventKey();//Save the event poped from the QUEUE to eat away duplicates. 
			System.out.println("event "+event);
		}
	}

	public static void quit() {
		Display.destroy();
	}
	
	public static void main(String[] args) {
		createDisplay();
		gameLoop();
		quit();
	}
	public static long getMilis() {
	    return System.nanoTime() / 1000000;
	    //return (Sys.getTime() * 1000) / Sys.getTimerResolution();
	}

	private static long lastFPS;
	private static int fps;
	
	private static void debug(){
		updateFPS();
	}
	
	private static void updateFPS() {
	    if (getMilis() - lastFPS > 1000) {
	        Display.setTitle("FPS: " + fps);
		    fps = 0;
		    lastFPS += 1000;
	    }
	    fps++;
	}
	
	public static void setDisplayMode(int width, int height, boolean fullscreen) {
		 
	    // return if requested DisplayMode is already set
	    if ((Display.getDisplayMode().getWidth() == width) && 
	        (Display.getDisplayMode().getHeight() == height) && 
		(Display.isFullscreen() == fullscreen)) {
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
	        System.out.println("Unable to setup mode "+width+"x"+height+" fullscreen="+fullscreen + e);
	    }
	}
}