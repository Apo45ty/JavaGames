package game.engine;

import minecraft2d.TimeListener;
import minecraft2d.World;

public abstract class GameObject {
	public GameObject() {
		if(this instanceof PhysicsObject){
			World.addPhysicsObject((PhysicsObject) this);
		}
		if(this instanceof TimeListener){
			World.addTimeListener((TimeListener) this);
		}
	}
}
