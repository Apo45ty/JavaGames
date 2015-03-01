package gameengine.runners;


public abstract class GameObject extends Sprite{
	public GameObject(Universe universe) {
		if(this instanceof PhysicsObject){
			universe.addPhysicsObject((PhysicsObject) this);
		}
		if(this instanceof CollisionObject){
			universe.addCollisionObject((CollisionObject) this);
		}
		if(this instanceof TimeListener){
			universe.addTimeListener((TimeListener) this);
		}
	}
}
