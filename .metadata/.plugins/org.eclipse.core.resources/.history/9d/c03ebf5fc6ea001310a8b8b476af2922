package minecraft2d;

import game.engine.PhysicsObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;

public enum BlockType {
	STONE("res/images/stone.png") {
		@Override
		public Texture getTexture(TimeEvent time, int health) {
			return texture;
		}
	},AIR("res/images/air.png") {
		@Override
		public Texture getTexture(TimeEvent time, int health) {
			return texture;
		}
	},GRASS("res/images/grass.png") {
		@Override
		public Texture getTexture(TimeEvent time, int health) {
			return texture;
		}
	},DIRT("res/images/dirt.png") {
		@Override
		public Texture getTexture(TimeEvent time, int health) {
			return texture;
		}
	};
	public final String location;
	public Texture texture;
	
	BlockType(String location){
		this.location=location;
		loadTextures();
	}
	
	public void loadTextures(){
		Texture result = null;
		try {
			result = TextureLoader.getTexture("PNG", new FileInputStream(
					new File(location)));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.texture = result;
	}

	public abstract Texture getTexture(TimeEvent time, int health);

}
