package gameengine.runners;

import java.awt.Rectangle;

public class DirtBlock extends GameObject implements CollisionObject,
		TimeListener {

	public DirtBlock(Universe universe) {
		super(universe);
		// TODO Auto-generated constructor stub
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
		return new Rectangle(player_x,player_y, TEXTURE_SIZE, TEXTURE_SIZE);
	}

	@Override
	public void move(int x, int y) {
		update(x,y,player_x_dir);
	}

	@Override
	public boolean isMobableByCollision() {
		return false;
	}

}
