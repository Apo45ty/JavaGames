package com.apo45ty.minefront.input;

import java.awt.event.*;

public class InputHandler implements FocusListener,KeyListener,MouseListener,MouseMotionListener {

	public boolean[] key = new boolean[68836];
	public static int mX,mY;
	
	@Override
	public void mouseDragged(MouseEvent event) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mouseMoved(MouseEvent event) {
		mX = event.getX();
		mY = event.getY();
		
	}

	@Override
	public void mouseClicked(MouseEvent event) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent event) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent event) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent event) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent event) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent event) {
		int keyCode = event.getKeyCode();
		if(keyCode>0&&keyCode<key.length){
			key[keyCode] = true;
		}
	}

	@Override
	public void keyReleased(KeyEvent event) {
//		for(int i=0;i<key.length;i++){
//			key[i] = false;
//		}
		int keyCode = event.getKeyCode();
		if(keyCode>0&&keyCode<key.length){
			key[keyCode] = false;
		}
	}

	@Override
	public void keyTyped(KeyEvent event) {
		// TODO Auto-generated method stub
	}

	@Override
	public void focusGained(FocusEvent event) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void focusLost(FocusEvent event) {
		for(int i=0;i<key.length;i++){
			key[i]=false;
		}
	}

}