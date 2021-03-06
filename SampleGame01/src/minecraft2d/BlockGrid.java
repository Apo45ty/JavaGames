package minecraft2d;


import static minecraft2d.World.BLOCKS_HEIGHT;
import static minecraft2d.World.BLOCKS_WIDTH;
import static minecraft2d.World.BLOCK_SIZE;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.XMLOutputter;

public class BlockGrid {
	Block blocks[][] = new Block[BLOCKS_WIDTH][BLOCKS_HEIGHT];
	public BlockGrid() {
		clear();
	}

	public void clear() {
		for(int x=0;x<BLOCKS_WIDTH;x++){
			for(int y=0;y<BLOCKS_HEIGHT;y++){
				blocks[x][y] = new Block(BlockType.AIR, x*BLOCK_SIZE, y*BLOCK_SIZE);
			}
		}
	}
	
	public void setAt(int x,int y,BlockType b){
		if(x>BLOCKS_WIDTH-1||y>BLOCKS_HEIGHT-1){
			return;
		}
		blocks[x][y] = new Block(b,x*BLOCK_SIZE,y*BLOCK_SIZE);
	}
	
	public Block getAt(int x,int y){
		if(x>BLOCKS_WIDTH-1||y>BLOCKS_HEIGHT-1||x<0||y<0){
			return null;
		}
		return blocks[x][y];
	}
	
	public void draw(int translate_x, int translate_y){
		for(int x=0;x<BLOCKS_WIDTH;x++)
			for(int y=0;y<BLOCKS_HEIGHT;y++)
				blocks[x][y].draw(translate_x,translate_y);
	}
	
	public void load(File loadFile){
		SAXBuilder builder = new SAXBuilder();
		try {
			Document document = builder.build(loadFile);
			Element root = document.getRootElement();
			for(Element e:root.getChildren()){
				setAt(Integer.parseInt(e.getAttributeValue("x")),
						Integer.parseInt(e.getAttributeValue("y")),
						BlockType.valueOf(e.getAttributeValue("type"))
					);
			}
		} catch (JDOMException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void save(File saveFile){
		Document document = new Document();
		Element root = new Element("blocks");
		document.setRootElement(root);
		for(int x=0;x<BLOCKS_WIDTH;x++){
			for(int y=0;y<BLOCKS_HEIGHT;y++){
				Element block = new Element("Block");
				block.setAttribute("x",x+"");
				block.setAttribute("y",y+"");
				block.setAttribute("type",blocks[x][y].getType()+"");
				root.addContent(block);
			}
		}
		XMLOutputter output = new XMLOutputter();
		try {
			output.output(document, new FileOutputStream(saveFile));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
			
			
}
