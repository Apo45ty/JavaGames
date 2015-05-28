package gameengine.runners;

public interface PhysicsObject {

	void applyVerticalForce(int gravityStrength);
	boolean canBeAppliedVerticalForce();
	boolean canBeAppliedVerticalForce(boolean setvalue);
	
}
