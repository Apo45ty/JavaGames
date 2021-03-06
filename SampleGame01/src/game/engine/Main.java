/**
f * Project: SampleGame01
 * Package: game.engine
 * Author: EltonJohn
 * Date: May 23, 2014
 * Time: 6:36:52 PM
 */
package game.engine;

import static minecraft2d.World.BLOCKS_HEIGHT;
import static minecraft2d.World.BLOCKS_WIDTH;
import static minecraft2d.World.BLOCK_SIZE;
import static minecraft2d.World.DAY_OPACITY;
import static minecraft2d.World.saveFile;
import static org.lwjgl.opengl.GL11.GL_BLEND;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_MODELVIEW;
import static org.lwjgl.opengl.GL11.GL_ONE_MINUS_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_PROJECTION;
import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.GL_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL11.glBlendFunc;
import static org.lwjgl.opengl.GL11.glClear;
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
import static org.lwjgl.opengl.GL11.glViewport;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.JOptionPane;
import javax.swing.Timer;

import minecraft2d.Block;
import minecraft2d.BlockGrid;
import minecraft2d.BlockType;
import minecraft2d.World;

import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.input.Controller;
import org.lwjgl.input.Controllers;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Color;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;

public class Main {
	public static BlockGrid grid;
	public static BlockType selection;
	public static int SCREEN_WIDTH =BLOCK_SIZE*BLOCKS_WIDTH,SCREEN_HEIGHT =BLOCK_SIZE*BLOCKS_HEIGHT,MOVEMENT_RADIUS=150;
	public static Texture background ;
	public static String backgroundPath = "res/images/background.png";
	public static String playerPath = "res/images/guy01.png";
	public static int translate_x = 0;
	public static int translate_y = 0;
	private static boolean done = false;
	private static int mousex;
	private static int mousey;
	private static int selector_x = 0;
	private static int selector_y = 0;
	private static boolean mouseEnabled = true;
	private static boolean vsync = false;
	private static TrueTypeFont font;
	private static int TIME_FOR_INPUT_POLL = 500;
	private static boolean ASYNC_INPUT = true;
	private static Timer INPUTPOLL_TIMER = null;
	private static boolean settupVSync = false;
	private static boolean settupFullScreen;
	private static Texture player;
	private static int player_y = BLOCK_SIZE*2;
	private static int player_x = 0+BLOCK_SIZE;
	private static float player_x_dir =0;
	
	
	public static void createDisplay() {
		
		try {
			Display.setTitle("Minecraft2D");
			setDisplayMode(SCREEN_WIDTH, SCREEN_HEIGHT, false);
			Display.create();
		} catch (LWJGLException e) {
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
		
		setUpBackground();
		World.init();
		grid = new BlockGrid();
		selection = BlockType.STONE;
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

	private static void setUpBackground() {
		Texture result = null;
		try {
			result = TextureLoader.getTexture("PNG", new FileInputStream(
					new File(backgroundPath)));
			player = TextureLoader.getTexture("PNG", new FileInputStream(
					new File(playerPath)));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		background = result;
	}
	
	public static void drawSelectionBox(){
		int selection_x = selector_x * BLOCK_SIZE;
		int selection_y = selector_y * BLOCK_SIZE;
		
		if(grid.getAt(selector_x, selector_y)!=null&&grid.getAt(selector_x, selector_y).getType() != BlockType.AIR){	
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
		//get async input
		if(ASYNC_INPUT ){
			INPUTPOLL_TIMER  = new Timer(TIME_FOR_INPUT_POLL,new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent arg0) {
					//get user input
					input();
				}
			});
			INPUTPOLL_TIMER.start();
		}
		while(!(done||Display.isCloseRequested()))	{
			//Drawing code
			glClear(GL_COLOR_BUFFER_BIT);
			glPushMatrix();
			
			if(!ASYNC_INPUT ){
				input();//fetch input in the update 
			}
			if(settupVSync){
				vsync  = !vsync;
	        	Display.setVSyncEnabled(vsync);
	        	settupVSync = false;
			}
			if(settupFullScreen){
				settupFullScreen =false;
	        	setDisplayMode(SCREEN_WIDTH, SCREEN_HEIGHT, !Display.isFullscreen());
			}
			drawBackground();//background layer
			grid.draw(translate_x,translate_y);
			drawPlayers();
			drawWeather();// weather layer
			drawSelectionBox();//gui layer 0
			debug();//debug layer
			
			glPopMatrix();
			Display.update();
			Display.sync(60);
		}
	}
	private static void drawPlayers() {
		player.bind();
		glLoadIdentity();
		glTranslatef(0, 0, 0);
		glBegin(GL_QUADS);
			glTexCoord2f(player_x_dir ,0);
			glVertex2f(player_x-BLOCK_SIZE,player_y-BLOCK_SIZE*2);
			glTexCoord2f(1-player_x_dir,0);
			glVertex2f(player_x,player_y-BLOCK_SIZE*2);
			glTexCoord2f(1-player_x_dir, 1);
			glVertex2f(player_x, player_y);
			glTexCoord2f(player_x_dir, 1);
			glVertex2f(player_x-BLOCK_SIZE, player_y);
		glEnd();
		glLoadIdentity();
	}

	private static void drawWeather() {
		glBindTexture(GL_TEXTURE_2D, 0);
//		System.out.println(DAY_OPACITY);
		glColor4f(0f,0f,0f,DAY_OPACITY);
		glBegin(GL_QUADS);
			glVertex2i(0,0);
			glVertex2i(SCREEN_WIDTH,0);
			glVertex2i(SCREEN_WIDTH, SCREEN_HEIGHT);
			glVertex2i(0, SCREEN_HEIGHT);
		glEnd();
		glColor4f(1f,1f,1f,1f);
	}

	private static void drawBackground() {
		background.bind();
		glLoadIdentity();
		glTranslatef(translate_x, translate_y, 0);
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
	
	/**
	 * Read user input
	 */
	public static void input(){
		if((Math.abs(mousex - Mouse.getX())>MOVEMENT_RADIUS&&(Math.abs(mousey - SCREEN_HEIGHT - Mouse.getY())>MOVEMENT_RADIUS)||Mouse.isButtonDown(0))&&!mouseEnabled){
			mouseEnabled = true;
			return;
		}
		if(mouseEnabled){
			mousex = Mouse.getX();//-translate_x;
			mousey = SCREEN_HEIGHT - Mouse.getY();//-translate_y;
			selector_x = (int)(mousex/BLOCK_SIZE);
			selector_y = (int)(mousey/BLOCK_SIZE);
			boolean mouseClicked =  Mouse.isButtonDown(0);
			if(mouseClicked){
				grid.setAt(selector_x, selector_y, selection);
			}
			//System.out.println("Mouse x:"+mousex+",  y:"+mousey);
		}
	    
		//Keyboard
		if(Keyboard.isKeyDown( Keyboard.KEY_W)){
			player_y-=10;
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_S)){
			player_y+=10;
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_A)){
			player_x-=10;
			player_x_dir=0;
		}
		if(Keyboard.isKeyDown( Keyboard.KEY_D)){
			player_x+=10;
			player_x_dir=1;
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_RIGHT)&&Keyboard.getEventKeyState()){
			mouseEnabled=false;
			if(selector_x + 1 < BLOCKS_WIDTH){
				selector_x++;
			}
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_LEFT)&&Keyboard.getEventKeyState()){
			mouseEnabled=false;
			if(selector_x - 1 >= 0){
				selector_x--;
			}
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_UP)&&Keyboard.getEventKeyState()){
			mouseEnabled=false;
			if(selector_y - 1 >= 0){
				selector_y--;
			}
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_DOWN)&&Keyboard.getEventKeyState()){
			mouseEnabled=false;
			if(!(selector_y + 1 > BLOCKS_HEIGHT-1)){
				selector_y++;
			}
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_SPACE)){
			grid.setAt(selector_x, selector_y, selection);
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_Q)&&Mouse.getX()>=0&&Mouse.getX()<SCREEN_WIDTH&&Mouse.getY()>=0&&Mouse.getY()<SCREEN_HEIGHT){
			if(Mouse.getDX()>0){
				translate_x+=BLOCK_SIZE;
			} else{
				translate_x-=BLOCK_SIZE;
			}
			if(Mouse.getDY()>0){
				translate_y-=BLOCK_SIZE;
			} else{
				translate_y+=BLOCK_SIZE;
			}
		}
		int event=0;
		//catch up with the queue
		while(Keyboard.next()){
			int key = Keyboard.getEventKey();
			if(event==key){
				continue;//Ignore duplicate events, eat away duplicates
			}
			if(key == Keyboard.KEY_P){
				grid.save(new File(saveFile));//TODO move this somewhere else
				JOptionPane.showMessageDialog(null, "Done Saving");
			}
			if(key == Keyboard.KEY_L){
				grid.load(new File(saveFile));//TODO move this somewhere else
				JOptionPane.showMessageDialog(null, "Done Loading");
			}
			if(key == Keyboard.KEY_1){
				selection= BlockType.STONE;
			}
			if(key == Keyboard.KEY_2){
				selection= BlockType.DIRT;
			}
			if(key == Keyboard.KEY_3){
				selection= BlockType.GRASS;
			}
			if(key == Keyboard.KEY_4){
				selection= BlockType.AIR;
			}
			if(key == Keyboard.KEY_C){
				grid.clear();
			}
			if(key == Keyboard.KEY_ESCAPE){
				done = true;
			}
			if (key == Keyboard.KEY_V){
				//System.out.println("vsync is "+(vsync?"Off":"On" )+".");
				settupVSync  = true;
	        }
			if (key == Keyboard.KEY_F) {
				//System.out.println("fullscreen is "+(Display.isFullscreen()?"Off":"On" )+".");
				settupFullScreen = true;
	        }
			event = key;//Save the event poped from the QUEUE to eat away duplicates. 
			//System.out.println("event "+event);
		}
	}

	public static void quit() {
		World.destroy();
		Display.destroy();
		if(ASYNC_INPUT){
			INPUTPOLL_TIMER.stop();
		}
	}
	
	public static void main(String[] args) {
		createDisplay();
		System.out.println("Open GL Version: "+GL11.glGetString(GL11.GL_VERSION));
		gameLoop();
		quit();
	}

	private static void debug(){
		updateFPS();
		font.drawString(0, 0, "FPS: " + fpsCount,Color.white);
		font.drawString(0, 10, "VSync: " + (vsync?"ON":"OFF"),Color.white);
	}
	private static long lastFPS;
	private static int fps;
	private static int fpsCount;
	
	public static long getMilis() {
//		return System.nanoTime() / 1000000;
		return (Sys.getTime() * 1000) / Sys.getTimerResolution();
	}
	
	private static void updateFPS() {
	    if (getMilis() - lastFPS > 1000) {
	    	fpsCount=fps;
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