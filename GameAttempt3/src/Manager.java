import java.awt.event.ActionListener;
import java.util.ArrayList;


public abstract class Manager implements ActionListener  {
	public ArrayList<Manager> managers;
	public Manager(ArrayList<Manager> managers){
		managers=managers;
		managers.add(this);
	}
	public abstract void init();
}
