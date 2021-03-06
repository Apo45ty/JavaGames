package minecraft2d;

import game.engine.PhysicsObject;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.Timer;

public class World {
	public static final int BLOCK_SIZE = 32;
	public static final int BLOCKS_WIDTH = 24, BLOCKS_HEIGHT = 20;
	public static final String saveFile = "res/worlds/world01.xml";
	public static final int GAME_HOURS_IN_MILIS = 100;
	public static final int PHYSICS_UPDATE_IN_MILIS = 100;
	public static final int DAY_DURATION = 16;
	public static final int NIGHT_DURATION = 24 - DAY_DURATION;
	public static final float changeForDayNight = 0.5f;
	public static final float NIGHT_INCREMENT = 2 * changeForDayNight
			/ (24 - DAY_DURATION);
	public static final float DAY_INCREMENT = 2 * -1 * changeForDayNight
			/ DAY_DURATION;
	public static float DAY_OPACITY = 0f;
	public static int tick = 0;
	public static int day = 0;
	public static long years = 0;
	public static boolean paused = false;
	public static Timer TIME_TIMER = new Timer(GAME_HOURS_IN_MILIS,
			new ActionListener() {
				private float change;

				@Override
				public void actionPerformed(ActionEvent arg0) {
					if (paused)
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
			});
	/**
	 * physics engine implementation
	 */
	public static Timer PHYSICS_TIMER = new Timer(PHYSICS_UPDATE_IN_MILIS,
			new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent arg0) {
					// TODO Auto-generated method stub

				}

			});

	public static void init() {
		TIME_TIMER.start();
	}

	public static void destroy() {
		TIME_TIMER.stop();
	}

	private static final ArrayList<TimeListener> timeObjects = new ArrayList<TimeListener>();

	public static void addTimeListener(TimeListener lis) {
		timeObjects.add(lis);
	}

	public static void removeTimeListener(TimeListener lis) {
		timeObjects.remove(lis);
	}

	private static final ArrayList<PhysicsObject> physicsObjects = new ArrayList<PhysicsObject>();

	public static void addPhysicsObject(PhysicsObject lis) {
		physicsObjects.add(lis);
	}

	public static void removePhysicObject(PhysicsObject lis) {
		physicsObjects.remove(lis);
	}
}
