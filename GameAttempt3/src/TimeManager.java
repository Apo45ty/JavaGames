import java.awt.event.ActionEvent;
import java.util.ArrayList;


public class TimeManager extends Manager {
	
	public TimeManager(ArrayList<Manager> managers) {
		super(managers);
	}

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
//				TimeEvent e = new TimeEvent(day, years, tick);
//				for (TimeListener lis : timeObjects) {
//					lis.newDayEvent(e);
//				}
			}
			tick++;
			if (day == 365) {
				day = 0;
				years++;
//				TimeEvent e = new TimeEvent(day, years, tick);
//				for (TimeListener lis : timeObjects) {
//					lis.newYearEvent(e);
//				}
			}

//			TimeEvent e = new TimeEvent(day, years, tick);
//			for (TimeListener lis : timeObjects) {
//				lis.newHourEvent(e);
//			}
			DAY_OPACITY += change;
		} catch (Exception exception) {
			System.err.println(exception.getMessage());
		}
		// System.out.println(DAY_OPACITY);
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub
		
	}


}
