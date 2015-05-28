package gameengine.runners;

import java.awt.Rectangle;

public interface CollisionObject {
	public Rectangle getBoundingBox();
	public void move(int x, int i);
	public boolean isMobableByCollision();
	public boolean isFloor();
	public boolean isFloor(boolean setValue);
}
