package com.apo45ty.minefront.input;

public class Controller {
	private static int CONTROLS_INVERTED = 1; // 1 == right goes right
	public static double ROTATION_SPEED=0.0005*CONTROLS_INVERTED,WALKING_SPEED=0.005,X_MOVE_SPEED=1,JUMP_HEIGHT=10,CROUCHING_HEIGHT=10,CROUCHING_PENALTY=WALKING_SPEED/8,RUNNING_BOST=WALKING_SPEED;
	public double x, z, y,rotation,xa,za, rotationa;
	public static boolean turnLeft = false,turnRight =false,walking=false ,running=false,crouchWalking = false,idle = false;
	public void tick(boolean forward,boolean backward,boolean left,boolean right,boolean turnleft,boolean turnright,boolean jumping, boolean crouching, boolean sprinting){
		double zMove =0,xMove=0;
		double WALK_SPEED = WALKING_SPEED;
		turnleft=turnleft||turnLeft;
		turnright=turnright||turnRight;
		walking=forward&&!backward||left&&!right;
		walking=walking||(!forward&&backward||!left&&right);
		crouchWalking = walking && crouching;
		running=sprinting;
		idle=!walking&&!crouching&&!jumping&&!(turnleft||turnright);
		if(forward){
			xMove++;
		}
		if(backward){
			xMove--;
		}
		if(right){
			zMove+=X_MOVE_SPEED;
		}
		if(left){
			zMove-=X_MOVE_SPEED;
		}
		if(turnleft){
			rotationa+=ROTATION_SPEED;
		}
		if(turnright){
			rotationa-=ROTATION_SPEED;
		}
		if(jumping){
			y+=JUMP_HEIGHT;
			sprinting = false;
		}
		if(crouching){
			y-=CROUCHING_HEIGHT;
			sprinting = false;
		}
		if(sprinting){
			WALK_SPEED += RUNNING_BOST;
		}
		if(crouching){
			WALK_SPEED -= CROUCHING_PENALTY;
		}
		xa += (xMove*Math.cos(rotation)+zMove*Math.sin(rotation))*WALK_SPEED;
		y*=0.9;
		za += (zMove*Math.cos(rotation)-xMove*Math.sin(rotation))*WALK_SPEED;
		x += xa;
		z += za;
		xa *= 0.1;
		za *= 0.1;
		rotation += rotationa;
		rotationa *= 0.8;
	}
}