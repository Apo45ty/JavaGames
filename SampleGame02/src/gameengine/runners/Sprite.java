package gameengine.runners;

import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glColor4f;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glLoadIdentity;
import static org.lwjgl.opengl.GL11.glTexCoord2f;
import static org.lwjgl.opengl.GL11.glTranslatef;
import static org.lwjgl.opengl.GL11.glVertex2f;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;

public abstract class Sprite {
	public Texture player;
	public int TEXTURE_SIZE = ThreeGameTest.PLAYER_SIZE;
	public int player_x;
	public int player_y;
	public int player_x_dir;
	
	public void setTexture(String playerPath){
		try {
			player = TextureLoader.getTexture("PNG", new FileInputStream(
					new File(playerPath)));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void setXMLMap(){
		
	}
	public void setJasonMap(){
		
	}
	public void update(int player_x, int player_y,int player_x_dir){
		this.player_x=player_x;
		this.player_y=player_y;
		this.player_x_dir=player_x_dir;
	}
	public  void draw(){ 
		glLoadIdentity();
		glTranslatef(0, 0, 0);
		player.bind();
		glBegin(GL_QUADS);
			glTexCoord2f(player_x_dir ,0);
			glVertex2f(player_x-TEXTURE_SIZE,player_y-TEXTURE_SIZE*2);
			glTexCoord2f(1-player_x_dir,0);
			glVertex2f(player_x,player_y-TEXTURE_SIZE*2);
			glTexCoord2f(1-player_x_dir, 1);
			glVertex2f(player_x, player_y);
			glTexCoord2f(player_x_dir, 1);
			glVertex2f(player_x-TEXTURE_SIZE, player_y);
		glEnd();
		glLoadIdentity();
	}
}
