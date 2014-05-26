package minecraft2d;

import static minecraft2d.World.BLOCK_SIZE;
import static org.lwjgl.opengl.GL11.*;//Use GL11

import java.io.File;
import java.io.FileInputStream;

import org.newdawn.slick.opengl.TextureLoader;


public class Block {
	private BlockType type= BlockType.AIR;
	private float x;
	private float y;
	public Block(BlockType type, float x, float y) {
		super();
		this.type = type;
		this.x = x;
		this.y = y;
		
	}
	public void draw(){
		type.texture.bind();
		glLoadIdentity();
		glTranslatef(x, y, 0);
		glBegin(GL_QUADS);
			glTexCoord2f(0,0);
			glVertex2f(0,0);
			glTexCoord2f(1,0);
			glVertex2f(BLOCK_SIZE,0);
			glTexCoord2f(1, 1);
			glVertex2f(BLOCK_SIZE, BLOCK_SIZE);
			glTexCoord2f(0, 1);
			glVertex2f(0, BLOCK_SIZE);
		glEnd();
		glLoadIdentity();
	}
	public BlockType getType() {
		return type;
	}
	public void setType(BlockType type) {
		this.type = type;
	}
	public float getX() {
		return x;
	}
	public void setX(float x) {
		this.x = x;
	}
	public float getY() {
		return y;
	}
	public void setY(float y) {
		this.y = y;
	}
	
	
}
