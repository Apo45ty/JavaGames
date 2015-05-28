package gameengine.runners;

import java.awt.event.ActionListener;
import java.util.ArrayList;

public abstract class ManagerListener implements ActionListener{
	private ArrayList<ManagerListener> manager;
	ManagerListener(ArrayList managers){
		super();
		managers.add(this);
		this.manager=managers;
		init();
	}
	/**
	 * So we dont have to create constructors or a lot of different classes.
	 */
	public abstract void init();
	
	public void notifyOthersPlayerColideWithFloor(){
		for(ManagerListener man:manager){
			man.OnPlayerColideWithFloor();
		}
	}
	public abstract void OnPlayerColideWithFloor();
	
	public void notifyOtherPlayerIsNotOnFloor(){
		for(ManagerListener man:manager){
			man.OnPlayerNotColideWithFloor();
		}
	}
	public abstract void OnPlayerNotColideWithFloor();
}
