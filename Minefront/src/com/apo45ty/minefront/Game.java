package com.apo45ty.minefront;

import java.awt.event.*;

import com.apo45ty.minefront.input.*;

public class Game {
	public static final int FORWARD_KEY = KeyEvent.VK_W;
	public static final int BACKWARD_KEY = KeyEvent.VK_S;
	public static final int LEFT_KEY = KeyEvent.VK_A;
	public static final int RIGH_KEY = KeyEvent.VK_D;
	public static final int ROTATE_LEFT_KEY = KeyEvent.VK_LEFT;
	public static final int ROTATE_RIGHT_KEY = KeyEvent.VK_RIGHT;
	public static final int JUMP_KEY = KeyEvent.VK_SPACE;
	public static final int SPRINT_KEY = KeyEvent.VK_SHIFT;
	public static final int CROUCH_KEY= KeyEvent.VK_CONTROL;
	public static final int TICK_INCREMENT = 3;
	public long Time;
	public Controller controls;

	public Game() {
		controls = new Controller();
	}
	public void tick(boolean[] key) {
		if(Time*-1L>0){
			Time=0;
		}
		Time+=TICK_INCREMENT;
		boolean forward = key[FORWARD_KEY];
		boolean backward = key[BACKWARD_KEY];
		boolean left = key[LEFT_KEY];
		boolean right = key[RIGH_KEY];
		boolean turnleft = key[ROTATE_LEFT_KEY];
		boolean turnright = key[ROTATE_RIGHT_KEY];
		boolean jumping = key[JUMP_KEY];
		boolean crouching = key[CROUCH_KEY];
		boolean sprinting = key[SPRINT_KEY];
		controls.tick(forward, backward, left, right, turnleft, turnright, jumping,crouching,sprinting);
	}

}
