/*
 * Created on Jun 6, 2004
 */
package javaBot;

/**
 * Abstract class all nonMovable objects should extend.
 */
public abstract class NonMovableObject extends PhysicalObject
{
	/**
	 * Creates a new NonMovableObject object.
	 *
	 * @param name the name of the object
	 */
	public NonMovableObject(String name)
	{
		super(name);
	}

	/**
	 * Check if this movable object collided with you and if it did put it on
	 * the right track and speed again
	 *
	 * @param object The object checking collisions
	 */
	public abstract void collideWith(MovableObject object);
}
