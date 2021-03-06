package game.engine;

import static minecraft2d.World.BLOCK_SIZE;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;

import static org.lwjgl.opengl.GL11.*;

public class TempSprite implements Sprite, AnimatableObject {
	int width,height;
	SpritePart[] parts;
	float x = 0;
	float y = 0;
	private int SCALE = 5 ;
	
	public TempSprite(String spriteFolderPath, String[] partName,int[] drawOrder,String XMLFile,int width,int height) {
		this.width = width;
		this.height = height;
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
		partName = partNames;
		
		this.parts = new SpritePart[partName.length];
		for(int i=0;i<partName.length;i++){
			try {
				SpritePart temp =new SpritePartTest();
				((SpritePartTest)temp).texture =
						TextureLoader.getTexture("PNG", new FileInputStream(
						new File(spriteFolderPath+partName[i])));
				temp.name = partName[i].split(".png")[0];
				temp.width=((SpritePartTest)temp).texture.getImageWidth()*SCALE;
				temp.height=((SpritePartTest)temp).texture.getImageHeight()*SCALE;
				switch(partName[i]){
				case "Belly.png":
					temp.relX = 7*SCALE;
					temp.relY = 31*SCALE;
					this.parts[7] = temp;
					break;
				case "Chest.png":
					temp.relX = 5*SCALE;
					temp.relY = 22*SCALE;
					this.parts[12] = temp;
					break;
				case "ChestPlate.png":
					temp.relX = 0*SCALE;
					temp.relY = 16*SCALE;
					this.parts[13] = temp;
					break;
				case "Eyes.png":
					temp.relX = 15*SCALE;
					temp.relY = 17*SCALE;
					this.parts[18] = temp;
					break;
				case "Eyes02.png":
					temp.relX = 15*SCALE;
					temp.relY = 17*SCALE;
					this.parts[19] = temp;//
					break;
				case "Head.png":
					temp.relX = 12*SCALE;
					temp.relY = 13*SCALE;
					this.parts[17] = temp;
					break;
				case "Hips.png":
					temp.relX = 9*SCALE;
					temp.relY = 36*SCALE;
					this.parts[6] = temp;
					break;
				case "LeftFoot.png":
					temp.relX = 16*SCALE;
					temp.relY = 62*SCALE;
					this.parts[0] = temp;
					break;
				case "LeftForearm.png":
					temp.relX = 20*SCALE;
					temp.relY = 30*SCALE;
					this.parts[15] = temp;
					break;
				case "LeftHand.png":
					temp.relX = 18*SCALE;
					temp.relY = 36*SCALE;
					this.parts[16] = temp;
					break;
				case "LeftKnee.png":
					temp.relX = 18*SCALE;
					temp.relY = 41*SCALE;
					this.parts[2] = temp;
					break;
				case "LeftLeg.png":
					temp.relX = 19*SCALE;
					temp.relY = 52*SCALE;
					this.parts[1] = temp;
					break;
				case "LeftShoulder.png":
					temp.relX = 22*SCALE;
					temp.relY = 23*SCALE;
					this.parts[14] = temp;
					break;
				case "Pants.png":
					temp.relX = 8*SCALE;
					temp.relY = 36*SCALE;
//					this.parts[8] = temp;
					break;
				case "RightFoot.png":
					temp.relX = 7*SCALE;
					temp.relY = 62*SCALE;
					this.parts[5] = temp;
					break;
				case "RightForearm.png":
					temp.relX = 3*SCALE;
					temp.relY = 30*SCALE;
					this.parts[10] = temp;
					break;
				case "RightHand.png":
					temp.relX = 1*SCALE;
					temp.relY = 36*SCALE;
					this.parts[9] = temp;
					break;
				case "RightKnee.png":
					temp.relX = 10*SCALE;
					temp.relY = 41*SCALE;
					this.parts[3] = temp;
					break;
				case "RightLeg.png":
					temp.relX = 10*SCALE;
					temp.relY = 52*SCALE;
					this.parts[4] = temp;
					break;
				case "RightShoulder.png":
					temp.relX = 5*SCALE;
					temp.relY = 23*SCALE;
					this.parts[11] = temp;
					break;
				}
				System.out.println("path:\'"+spriteFolderPath+partName[i]+"\';"+"name:"+temp.name+";x:"+temp.relX+";Y:"+temp.relY+";width:"+temp.width+";height:"+temp.height);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
	}
	public TempSprite(String spriteFilePath,String xmlFile) {
		//TODO
	}
	
	public void Draw() {
		for(int i=0;i<parts.length;i++){
			if(this.parts[i]==null||((SpritePartTest)this.parts[i]).texture==null)
				continue;
			glBindTexture(GL_TEXTURE_2D, 0);
			((SpritePartTest)this.parts[i]).texture.bind();
			glLoadIdentity();
			glTranslatef(x, y, 0);
			glBegin(GL_QUADS);
				glTexCoord2f(0,0);
				glVertex2i(this.parts[i].relX,this.parts[i].relY);
				glTexCoord2f(1,0);
				glVertex2i(this.parts[i].relX+this.parts[i].width,this.parts[i].relY);
				glTexCoord2f(1, 1);
				glVertex2i(this.parts[i].relX+this.parts[i].width,this.parts[i].relY+this.parts[i].height);
				glTexCoord2f(0, 1);
				glVertex2i(this.parts[i].relX,this.parts[i].relY+this.parts[i].height);
			glEnd();
			glLoadIdentity();
			System.out.println((x+this.parts[i].relX)+","+(y+this.parts[i].relY));
			System.out.println("name:"+this.parts[i].name+";x:"+this.parts[i].relX+";Y:"+this.parts[i].relY+";width:"+this.parts[i].width+";height:"+this.parts[i].height);
			
		}
	}
}
