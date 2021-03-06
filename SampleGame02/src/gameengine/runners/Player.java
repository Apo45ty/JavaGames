package gameengine.runners;

import java.awt.Rectangle;

public class Player extends GameObject implements AnimatableObject,
		TimeListener, PhysicsObject,CollisionObject {
	
	public int movementSpeed = 10;
	public int jumpHeight = 100;
	public int jumpRizeTimeInMilis = 1000;
	public boolean isJumping;
	public long lastJumpUpdate =0;
	public long jumpStart =0;
	private boolean canBeAppliedVerticalForce;
	public Player(Universe universe) {
		super(universe);
	}

	@Override
	public void newHourEvent(TimeEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void newYearEvent(TimeEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void newDayEvent(TimeEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public Rectangle getBoundingBox() {
		return new Rectangle(player_x,player_y,TEXTURE_SIZE, TEXTURE_SIZE);
	}

	@Override
	public void move(int x, int y) {
		update(x,y,player_x_dir);
	}

	@Override
	public boolean isMobableByCollision() {
		return true;
	}

	@Override
	public void applyVerticalForce(int gravityStrength) {
		update(player_x,player_y+gravityStrength,player_x_dir);
	}

	@Override
	public boolean canBeAppliedVerticalForce() {
		return !isJumping||canBeAppliedVerticalForce;
	}

	@Override
	public boolean isFloor() {
		return false;
	}

	@Override
	public boolean isFloor(boolean setValue) {
		return false;
	}

	@Override
	public boolean canBeAppliedVerticalForce(boolean setvalue) {
		canBeAppliedVerticalForce=setvalue;
		return canBeAppliedVerticalForce;
	}

}
