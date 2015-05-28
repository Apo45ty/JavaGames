import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL11.glColor4f;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glLoadIdentity;
import static org.lwjgl.opengl.GL11.glTexCoord2f;
import static org.lwjgl.opengl.GL11.glTranslatef;
import static org.lwjgl.opengl.GL11.glVertex2f;
import static org.lwjgl.opengl.GL11.glVertex2i;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;


public class Player extends GameObject implements Sprite {
	public static final int jumpHeight = 10;
	public static final int movementSpeed = 10;
	public Texture player;
	public int TEXTURE_SIZE = ThreeGameTest.PLAYER_SIZE;
	public int player_x;
	public int player_y;
	public int player_x_dir;
	
	/* (non-Javadoc)
	 * @see lol#setTexture(java.lang.String)
	 */
	@Override
	public void setTexture(String playerPath){
		try {
			player = TextureLoader.getTexture("PNG", new FileInputStream(
					new File(playerPath)));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/* (non-Javadoc)
	 * @see lol#setXMLMap()
	 */
	@Override
	public void setXMLMap(){
		
	}
	/* (non-Javadoc)
	 * @see lol#setJasonMap()
	 */
	@Override
	public void setJasonMap(){
		
	}
	/* (non-Javadoc)
	 * @see lol#update(int, int, int)
	 */
	@Override
	public void update(int player_x, int player_y,int player_x_dir){
		this.player_x=player_x;
		this.player_y=player_y;
		this.player_x_dir=player_x_dir;
	}
	/* (non-Javadoc)
	 * @see lol#draw()
	 */
	@Override
	public  void draw(){ 
		glBindTexture(GL_TEXTURE_2D, 0);
		glColor4f(0f, 255f, 0f,1f);
		glBegin(GL_QUADS);
		glTexCoord2f(player_x_dir ,0);
			glVertex2f(player_x-TEXTURE_SIZE,player_y-TEXTURE_SIZE*2);
			glVertex2f(player_x,player_y-TEXTURE_SIZE*2);
			glVertex2f(player_x, player_y);
			glVertex2f(player_x-TEXTURE_SIZE, player_y);
		glEnd();
		//Draw texture
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
