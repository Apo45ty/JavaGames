package gameengine.runners;

import java.awt.Color;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.Timer;

public class Universe {
	public static final int GAME_HOURS_IN_MILIS = 1000;
	public static final int GRAVITY_STRENGTH = 10;
	public static final int PHYSICS_UPDATE_IN_MILIS = 100;
	private static final int COLLISION_UPDATE_IN_MILIS = 100;
	public static final int DAY_DURATION = 16;
	public static final int NIGHT_DURATION = 24 - DAY_DURATION;
	public static final float changeForDayNight = 0.5f;
	public static final float NIGHT_INCREMENT = 2 * changeForDayNight
			/ (24 - DAY_DURATION);
	public static final float DAY_INCREMENT = 2 * -1 * changeForDayNight
			/ DAY_DURATION;
	public float DAY_OPACITY = 0f;
	public int tick = 0;
	public int day = 0;
	public long years = 0;
	public boolean timeIsPaused = false;
	private final ArrayList<ActionListener> managers = new ArrayList<ActionListener>();;
	public Timer TIME_TIMER = new Timer(GAME_HOURS_IN_MILIS,
			new ManagerListener(managers) {
				private float change;

				@Override
				public void actionPerformed(ActionEvent arg0) {
					if (timeIsPaused)
						return;
					try {
						if (tick % 24 == NIGHT_DURATION) {
							change = DAY_INCREMENT;
						} else if (tick % 24 == NIGHT_DURATION / 2) {
							change = 0;// Stay at this level of darkness
						} else if (tick % 24 == NIGHT_DURATION + DAY_DURATION
								/ 2) {
							change = 0;// Stay at this level of darkness
						} else if (tick % 24 == 0) {
							change = NIGHT_INCREMENT; // Set the sun
							tick = 0;
							day++;
							TimeEvent e = new TimeEvent(day, years, tick);
							for (TimeListener lis : timeObjects) {
								lis.newDayEvent(e);
							}
						}
						tick++;
						if (day == 365) {
							day = 0;
							years++;
							TimeEvent e = new TimeEvent(day, years, tick);
							for (TimeListener lis : timeObjects) {
								lis.newYearEvent(e);
							}
						}

						TimeEvent e = new TimeEvent(day, years, tick);
						for (TimeListener lis : timeObjects) {
							lis.newHourEvent(e);
						}
						DAY_OPACITY += change;
					} catch (Exception exception) {
						System.err.println(exception.getMessage());
					}
					// System.out.println(DAY_OPACITY);
				}

				@Override
				public void OnPlayerColideWithFloor() {

				}

				@Override
				public void init() {
					// TODO Auto-generated method stub

				}

				@Override
				public void OnPlayerNotColideWithFloor() {
					// TODO Auto-generated method stub

				}
			});
	/**
	 * physics engine implementation
	 */
	public Timer PHYSICS_TIMER = new Timer(PHYSICS_UPDATE_IN_MILIS,
			new ManagerListener(managers) {

				private PhysicsObject player;

				@Override
				public void actionPerformed(ActionEvent arg0) {
					for (PhysicsObject obj : physicsObjects) {
						if (obj instanceof Player) {
							this.player = obj;
						}
						if (obj.canBeAppliedVerticalForce())
							obj.applyVerticalForce(GRAVITY_STRENGTH);
					}
				}

				@Override
				public void OnPlayerColideWithFloor() {
					player.canBeAppliedVerticalForce(false);
				}

				@Override
				public void init() {
					// TODO Auto-generated method stub

				}

				@Override
				public void OnPlayerNotColideWithFloor() {
					// TODO Auto-generated method stub
					player.canBeAppliedVerticalForce(false);
				}

			});
	/**
	 * Collision engine implementation
	 */
	public Timer COLLISION_TIMER = new Timer(COLLISION_UPDATE_IN_MILIS,
			new ManagerListener(managers) {
				Player player;

				@Override
				public void actionPerformed(ActionEvent arg0) {
					if (player != null) {
						// float count=0;
						// for(int i=0;i<collisionObjects.size();i++){
						// CollisionObject obj=collisionObjects.get(i);
						// if(obj.isFloor()){
						// notifyOthersPlayerColideWithFloor();
						// count++;
						// }
						// }
						// if(count==0){
						// notifyOtherPlayerIsNotOnFloor();
						// }
					}
					for (int i = 0; i < collisionObjects.size(); i++) {
						CollisionObject obj = collisionObjects.get(i);
						if (obj instanceof Player)
							player = (Player) obj;
						for (int j = i; j < collisionObjects.size(); j++) {
							CollisionObject obj2 = collisionObjects.get(j);
							if (obj2 instanceof Player)
								player = (Player) obj2;
							if (obj == obj2)
								continue;
							if (obj.getBoundingBox().intersects(
									obj2.getBoundingBox())) {
								Rectangle rect1 = obj.getBoundingBox();
								Rectangle rect2 = obj2.getBoundingBox();
								ThreeGameTest.logger(ThreeGameTest.UNIVERSE_COLLISION,ThreeGameTest.TEST1,"collisionCheck="+rect1 + "," + rect2);
								// object1 is been touched by object2 on the top
								if (rect1.intersectsLine(rect2.x, rect2.y,
										rect2.x + rect2.width, rect2.y)) {
									if (obj2.isMobableByCollision())
										obj2.move(rect2.x, rect1.y
												- rect2.height);
									else
										obj.move(rect1.x, rect2.y
												- rect1.height);
									continue;
								}
								// object1 is been touched by object2 underneath
								if (rect2.intersectsLine(rect1.x, rect1.y,
										rect1.x + rect1.width, rect1.y)) {
									if (obj2.isMobableByCollision())
										obj2.move(rect2.x, rect1.y
												+ rect1.height);
									else
										obj.move(rect1.x, rect2.y
												+ rect2.height);
									continue;
								}
								// object1 is been touched by object2 on the
								// right
								if (rect1.intersectsLine(rect2.x, rect2.y,
										rect2.x, rect2.y + rect2.height)) {
									if (obj2.isMobableByCollision())
										obj2.move(rect1.x + rect1.width,
												rect2.y);
									else
										obj.move(rect2.x + rect2.width, rect1.y);
									continue;
								}
								// object1 is been touched by object2 on the
								// left
								if (rect1.intersectsLine(rect2.x + rect2.width,
										rect2.y, rect2.x + rect2.width, rect2.y
												+ rect2.height)) {
									if (obj2.isMobableByCollision())
										obj2.move(rect1.x - rect2.width,
												rect2.y);
									else
										obj.move(rect2.x - rect1.width, rect1.y);
									continue;
								}
							}
						}
					}

				}

				@Override
				public void OnPlayerColideWithFloor() {
					// TODO Auto-generated method stub

				}

				@Override
				public void init() {
					// TODO Auto-generated method stub

				}

				@Override
				public void OnPlayerNotColideWithFloor() {
					// TODO Auto-generated method stub

				}

			});

	private boolean isPaused = false;
		
	public void init() {
		pause();
	}

	public void pause() {
		if (!isPaused) {
			TIME_TIMER.start();
			PHYSICS_TIMER.start();
			COLLISION_TIMER.start();
		} else {
			TIME_TIMER.stop();
			PHYSICS_TIMER.stop();
			COLLISION_TIMER.stop();
		}
		isPaused=!isPaused;
	}

	public void destroy() {
		pause();
	}

	private final ArrayList<TimeListener> timeObjects = new ArrayList<TimeListener>();

	public void addTimeListener(TimeListener lis) {
		timeObjects.add(lis);
	}

	public void removeTimeListener(TimeListener lis) {
		timeObjects.remove(lis);
	}

	private final ArrayList<PhysicsObject> physicsObjects = new ArrayList<PhysicsObject>();

	public void addPhysicsObject(PhysicsObject lis) {
		physicsObjects.add(lis);
	}

	public void removePhysicObject(PhysicsObject lis) {
		physicsObjects.remove(lis);
	}

	private final ArrayList<CollisionObject> collisionObjects = new ArrayList<CollisionObject>();

	public void addCollisionObject(CollisionObject lis) {
		collisionObjects.add(lis);
	}

	public void removeCollisionObject(CollisionObject lis) {
		collisionObjects.remove(lis);
	}
}
