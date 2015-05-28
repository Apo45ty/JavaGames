import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.Timer;


public class Universe {
	public static final int GAME_HOURS_IN_MILIS = 1000;
	public static final int PHYSICS_UPDATE_IN_MILIS = 100;
	private static final int COLLISION_UPDATE_IN_MILIS = 100;
	public static final int GRAVITY_STRENGTH = 10;
	private static final ArrayList<Manager> managers = new ArrayList<Manager>();
	public static final TimeManager timeManager= new TimeManager(managers) ;
	public Timer TIME_TIMER = new Timer(GAME_HOURS_IN_MILIS, timeManager);
	
	public void init(){
		timeManager.init();
		TIME_TIMER.start();
	}
}
