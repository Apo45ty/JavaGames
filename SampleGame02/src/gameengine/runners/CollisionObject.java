package gameengine.runners;

import java.awt.Rectangle;

public interface CollisionObject {
	public Rectangle getBoundingBox();
	public void move(int x, int i);
	public boolean isMobableByCollision();
}
