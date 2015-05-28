package com.apo45ty.minefront.graphics;

import java.awt.image.*;
import java.io.*;

import javax.imageio.*;
import javax.management.*;

public class Texture {
	public static Render floor = loadBitmap("/textures/floor32x32.png");
	
	public static Render loadBitmap(String filePath){
		Render result = null;
		try{
			BufferedImage image = ImageIO.read(Texture.class.getResource(filePath));
			int width = image.getWidth();
			int heigh = image.getHeight();
			result = new Render(width,heigh);
			image.getRGB(0,0,width,heigh,result.pixels,0,width);
		}catch(IOException e){
			e.printStackTrace();
			System.err.println("Cant load texture in "+filePath);
			throw new RuntimeException(e);
		}
		return result;
	}
}
