/*
 * Created on 20 Feb 2006
 * Copyright: (c) 2006
 * Company: Dancing Bear Software
 *
 * @version $Revision: 1.1 $
 *
 * World combines all physical objects in the world and controls their interaction.
 */
package javaBot;

/**
 */
import java.util.Iterator;
import java.util.Vector;

import javaBot.maze.Maze;

/**
 * Created on 20-02-2006
 * Copyright: (c) 2006
 * Company: Dancing Bear Software
 *
 * @version $Revision$
 * last changed 20-02-2006
 *
 * TODO CLASS: DOCUMENT ME! 
 */
public class World
{
	// What is the gui we're displaying the stuff in
	private Simulator		gui;

	//Keep track if the maze is shown
	private Maze			maze;

	//Keep track if the soccerfield is shown
	private PlayingField	playingfield;

	//Keep track if rescuefield is shown
	private RescueField		rescueField;

	//Keep track if DanceFloor is shown
	private DanceFloor		danceFloor;

	//Contains all the movable objects in the world
	private Vector			movableObjects		= new Vector();

	//Contains all the non-movable objects in the world
	private Vector			nonMovableObjects	= new Vector();

	//Width of the world
	public static double	WIDTH;

	//Height of the world
	public static double	HEIGHT;

	//Size of the grid 
	public static double	GRID_SIZE			= 0.3;
	
	private double          ratioHeight         = 1; 

	/**
	 * Creates a new World object with the defauld values for fps, WIDTH and
	 * HEIGHT. Defaults fps to 25.
	 *
	 * @param gui the gui we want everything to be displayed in!
	 */
	World(Simulator gui)
	{
		Debug.printDebug("Creating world with default settings");
		this.gui = gui;

		NonMovableObject surroundingWall = new SurroundingWalls("walls", WIDTH, HEIGHT);
		addNonMovableObject(surroundingWall);
	}

	/**
	 * Adds a new movable object to world, only adds it to the vector nothing
	 * more
	 *
	 * @param object should be completly instantiated
	 */
	public void addMovableObject(MovableObject object)
	{
		Debug.printDebug("Adding MovableObject " + object.name);
		movableObjects.add(object);
	}

	/**
	 * Adds a new nonMovableObject object to the world and lets it draw itself
	 * on the gui
	 *
	 * @param object should be completly instantiated
	 */
	public void addNonMovableObject(NonMovableObject object)
	{
		Debug.printDebug("Adding NonMovableObject " + object.name);
		nonMovableObjects.add(object);
	}

	/**
	 * Removes a MovableObject object from the world
	 *
	 * @param object should be completly instantiated
	 */
	public void removeMovableObject(MovableObject object)
	{
		Debug.printDebug("Removing MovableObject " + object.name);
		gui.removeGraphicalRepresentation(object.getGraphicalRepresentation());
		movableObjects.remove(object);
	}

	/**
	 * Removes a NonMovableObject object from the world
	 *
	 * @param object should be completly instantiated
	 */
	public void removeNonMovableObject(NonMovableObject object)
	{
		Debug.printDebug("Removing NonMovableObject " + object.name);
		gui.removeGraphicalRepresentation(object.getGraphicalRepresentation());
		nonMovableObjects.remove(object);
	}

	/**
	 * Handles the collisions if any
	 */
	public void handleCollisions()
	{
		MovableObject currentMovableObject;
		MovableObject movable;
		NonMovableObject nonmovable;

		for (int i = 0; i < movableObjects.size(); i++)
		{
			currentMovableObject = (MovableObject) movableObjects.get(i);

			//We start with j=i+1 to prevent to compare the object with it's self and to prevent us from compare the same object twice
			for (int j = i + 1; j < movableObjects.size(); j++)
			{
				movable = (MovableObject) movableObjects.get(j);
				collide(movable, currentMovableObject);
			}

			for (int j = 0; j < nonMovableObjects.size(); j++)
			{
				nonmovable = (NonMovableObject) nonMovableObjects.get(j);
				nonmovable.collideWith(currentMovableObject);
			}
		}
	}

	/**
	 * Update the current state of the world
	 *
	 * @param elapsed
	 */
	public void update(double elapsed)
	{
		MovableObject currentMovableObject;

		handleCollisions();

		// calculate the elapsed time:
		for (int i = 0; i < movableObjects.size(); i++)
		{
			currentMovableObject = (MovableObject) movableObjects.get(i);

			currentMovableObject.update(elapsed);

			if (currentMovableObject instanceof Robot)
			{
				updateSensors((Robot) currentMovableObject);
			}
		}
	}

	/**
	 * Check if the 2 object collided and change there behavior accordingly
	 *
	 * @param movableObject1
	 * @param movableObject2
	 */
	private void collide(MovableObject movableObject1, MovableObject movableObject2)
	{
		double distance = movableObject1.getLocation().distanceTo(movableObject2.getLocation());
		double minimalDistance = (movableObject1.diameter + movableObject2.diameter) / 2;

		if (distance < minimalDistance)
		{
//			double distanceToCorrect = minimalDistance - distance;
//			double speedObject1 = Math.sqrt(movableObject1.getVelocityX()
//					+ movableObject1.getVelocityY());

			//code from previous year no documentation availeble, it calculates the effect of a collision
			double dPxR1 = movableObject2.getLocation().getX()
					- movableObject1.getLocation().getX();
			double dPyR1 = movableObject2.getLocation().getY()
					- movableObject1.getLocation().getY();

			double dPxR2 = movableObject1.getLocation().getX()
					- movableObject2.getLocation().getX();
			double dPyR2 = movableObject1.getLocation().getY()
					- movableObject2.getLocation().getY();

//			double tMass = movableObject1.mass + movableObject2.mass;
			double impactAngle = 0;
			double impuls = 0;

//			double dVxR1 = movableObject1.getVelocityX() - movableObject2.getVelocityX();
//			double dVyR1 = movableObject1.getVelocityY() - movableObject2.getVelocityY();
//
//			double dVxR2 = movableObject2.getVelocityX() - movableObject1.getVelocityX();
//			double dVyR2 = movableObject2.getVelocityY() - movableObject1.getVelocityY();
//
//			double arbeidX;
//			double arbeidY;

			if (((dPxR1 > 0) && (movableObject1.getVelocityX() > 0))
					|| ((dPxR1 < 0) && (movableObject1.getVelocityX() < 0)))
			{ //  R1 arbeid on R2 
				impactAngle = Math.atan(dPyR1 / dPxR1);
				impuls = Math.cos(impactAngle) * movableObject1.mass
						* movableObject1.getVelocityX();

				movableObject1.setVelocityX(movableObject1.getVelocityX()
						- ((impuls / movableObject1.mass) * Math.cos(impactAngle)));
				movableObject1.setVelocityY(movableObject1.getVelocityY()
						- ((impuls / movableObject1.mass) * Math.sin(impactAngle)));
				movableObject2.setVelocityX(movableObject2.getVelocityX()
						+ ((impuls / movableObject2.mass) * Math.cos(impactAngle)));
				movableObject2.setVelocityY(movableObject2.getVelocityY()
						+ ((impuls / movableObject2.mass) * Math.sin(impactAngle)));
			}

			if (((dPxR2 > 0) && (movableObject2.getVelocityX() > 0))
					|| ((dPxR2 < 0) && (movableObject2.getVelocityX() < 0)))
			{ //  R2 arbeid on R1
				impactAngle = Math.atan(dPyR2 / dPxR2);
				impuls = Math.cos(impactAngle) * movableObject2.mass
						* movableObject2.getVelocityX(); // dVxR2;

				movableObject2.setVelocityX(movableObject2.getVelocityX()
						- ((impuls / movableObject2.mass) * Math.cos(impactAngle)));
				movableObject2.setVelocityY(movableObject2.getVelocityY()
						- ((impuls / movableObject2.mass) * Math.sin(impactAngle)));
				movableObject1.setVelocityX(movableObject1.getVelocityX()
						+ ((impuls / movableObject1.mass) * Math.cos(impactAngle)));
				movableObject1.setVelocityY(movableObject1.getVelocityY()
						+ ((impuls / movableObject1.mass) * Math.sin(impactAngle)));
			}

			if (((dPyR1 > 0) && (movableObject1.getVelocityY() > 0))
					|| ((dPyR1 < 0) && (movableObject1.getVelocityY() < 0)))
			{ //  R1 arbeid on R2 
				impactAngle = Math.atan(dPxR1 / dPyR1);
				impuls = Math.cos(impactAngle) * movableObject1.mass
						* movableObject1.getVelocityY();
				movableObject1.setVelocityX(movableObject1.getVelocityX()
						- ((impuls / movableObject1.mass) * Math.sin(impactAngle)));
				movableObject1.setVelocityY(movableObject1.getVelocityY()
						- ((impuls / movableObject1.mass) * Math.cos(impactAngle)));
				movableObject2.setVelocityX(movableObject2.getVelocityX()
						+ ((impuls / movableObject2.mass) * Math.sin(impactAngle)));
				movableObject2.setVelocityY(movableObject2.getVelocityY()
						+ ((impuls / movableObject2.mass) * Math.cos(impactAngle)));
			}

			if (((dPyR2 > 0) && (movableObject2.getVelocityY() > 0))
					|| ((dPyR2 < 0) && (movableObject2.getVelocityY() < 0)))
			{ //  R2 arbeid on R1
				impactAngle = Math.atan(dPxR2 / dPyR2);
				impuls = Math.cos(impactAngle) * movableObject2.mass
						* movableObject2.getVelocityY(); // dVxR2;

				movableObject2.setVelocityX(movableObject2.getVelocityX()
						- ((impuls / movableObject2.mass) * Math.sin(impactAngle)));
				movableObject2.setVelocityY(movableObject2.getVelocityY()
						- ((impuls / movableObject2.mass) * Math.cos(impactAngle)));
				movableObject1.setVelocityX(movableObject1.getVelocityX()
						+ ((impuls / movableObject1.mass) * Math.sin(impactAngle)));
				movableObject1.setVelocityY(movableObject1.getVelocityY()
						+ ((impuls / movableObject1.mass) * Math.cos(impactAngle)));
			}
		}
	}

	/**
	 * call every object in the world to calculate what kind of sensor
	 * information they give to this robot
	 *
	 * @param robot robot we want to let know what his sensor readings are
	 */
	private void updateSensors(Robot robot)
	{
		//here we store the current object we're getting sensor information from
		PhysicalObject object;

		double[] sensorValues = robot.newSensorValues();

		//first we work through all the movable objects
		for (int i = 0; i < movableObjects.size(); i++)
		{
			object = (MovableObject) movableObjects.get(i);

			if (robot != object)
			{
				double[] returnValues = object.giveSensoryInformationTo(robot);

				for (int o = 0; o < sensorValues.length; o++)
				{
					if (Double.compare(sensorValues[o], returnValues[o]) < 0)
					{
						sensorValues[o] = returnValues[o];
					}
				}
			}
		}

		//then through all the nonMovableObjects
		for (int i = 0; i < nonMovableObjects.size(); i++)
		{
			object = (NonMovableObject) nonMovableObjects.get(i);

			if (robot != object)
			{
				double[] returnValues = object.giveSensoryInformationTo(robot);

				for (int j = 0; j < sensorValues.length; j++)
				{
					//if (j==3) Debug.printDebug("ir sensor " + returnValues[j] + " " + sensorValues[j] + " " + (Double.compare(sensorValues[j], returnValues[j]) > 0));
					if (Double.compare(sensorValues[j], returnValues[j]) < 0)
					{
						sensorValues[j] = returnValues[j];
					}
				}
			}
		}

		// if the sensors registered something other than 0, overwrite current sensor values
		for (int o = 0; o < robot.getSensors().length; o++)
		{
			robot.getSensors()[o].setValue(sensorValues[o]);

			//        	if (o==3) Debug.printDebug("Sensor value =" + robot.sensors[o].getValue());
		}
	}

	/**
	 * @return Returns the danceFloor.
	 */
	public DanceFloor getDanceFloor()
	{
		return danceFloor;
	}

	/**
	 * @return Returns the gui.
	 */
	public Simulator getGui()
	{
		return gui;
	}

	/**
	 * @return Returns the maze.
	 */
	public Maze getMaze()
	{
		return maze;
	}

	/**
	 * @return Returns the movableObjects.
	 */
	public Vector getMovableObjects()
	{
		return movableObjects;
	}

	/**
	 * @return Returns the nonMovableObjects.
	 */
	public Vector getNonMovableObjects()
	{
		return nonMovableObjects;
	}

	/**
	 * @return Returns the playingfield.
	 */
	public PlayingField getPlayingfield()
	{
		return playingfield;
	}

	/**
	 * @return Returns the rescueField.
	 */
	public RescueField getRescueField()
	{
		return rescueField;
	}

	/**
	 * @param danceFloor The danceFloor to set.
	 */
	public void setDanceFloor(DanceFloor danceFloor)
	{
		this.danceFloor = danceFloor;
	}

	/**
	 * @param gui The gui to set.
	 */
	public void setGui(Simulator gui)
	{
		this.gui = gui;
	}

	/**
	 * @param maze The maze to set.
	 */
	public void setMaze(Maze maze)
	{
		this.maze = maze;
	}

	/**
	 * @param movableObjects The movableObjects to set.
	 */
	public void setMovableObjects(Vector movableObjects)
	{
		this.movableObjects = movableObjects;
	}

	/**
	 * @param nonMovableObjects The nonMovableObjects to set.
	 */
	public void setNonMovableObjects(Vector nonMovableObjects)
	{
		this.nonMovableObjects = nonMovableObjects;
	}

	/**
	 * @param playingfield The playingfield to set.
	 */
	public void setPlayingfield(PlayingField playingfield)
	{
		this.playingfield = playingfield;
	}

	/**
	 * @param rescueField The rescueField to set.
	 */
	public void setRescueField(RescueField rescueField)
	{
		this.rescueField = rescueField;
	}

	public void resize(double width, double height)
	{
		// Update World WIDTH & HEIGHT public variables
		World.WIDTH = width; 
		World.HEIGHT = height; 
		
		// Calculate the new Ratio
		ratioHeight = (double)gui.getWorldPane().getHeight() / Simulator.DEFAULT_WORLD_PANE_HEIGHT;  
        double newPixelsPerMeter = (Simulator.DEFAULT_WORLD_PANE_HEIGHT / height) * ratioHeight;
        if (newPixelsPerMeter > 0)  
        	Simulator.pixelsPerMeter = newPixelsPerMeter; 
        
        //  Adjust Fields
        // SensorInformationField activeField ....
        //activeField.rescaleImage(...)
        if (getRescueField() != null)
        {
        	resizeField(getRescueField().getGraphicalRepresentation());
        	getRescueField().rescaleImage((int)(width*newPixelsPerMeter),(int)(height*newPixelsPerMeter));
        }
        if (getPlayingfield() != null)
        {
        	resizeField(getPlayingfield().getGraphicalRepresentation());
        	getPlayingfield().rescaleImage((int)(width*newPixelsPerMeter),(int)(height*newPixelsPerMeter));
        }
        if (getDanceFloor() != null)
        {
        	resizeField(getDanceFloor().getGraphicalRepresentation());
        	getDanceFloor().rescaleImage((int)(width*newPixelsPerMeter),(int)(height*newPixelsPerMeter));
        }
        
		// Adjust scale of Moveable Objects
	    Iterator iter = getMovableObjects().iterator(); 
        while (iter.hasNext()) { 
        	MovableObject moveableObj = (MovableObject)iter.next(); 
        	if (moveableObj.getGraphicalRepresentation() == null) 
        		continue;
        	moveableObj.getGraphicalRepresentation().updateScale(); 
        } 
        
		// Adjust Non Moveable Objects
	    iter = getNonMovableObjects().iterator(); 
        while (iter.hasNext()) { 
        	NonMovableObject nonmoveableObj = (NonMovableObject)iter.next();
        	String name = nonmoveableObj.name;
        	// Wall Objects
        	if (name.equals("Wall")) 
        		nonmoveableObj.getGraphicalRepresentation().updateScale();
        	// The SoccerWall
        	else if (name.equals("soccerwall"))
        		((SoccerWalls)nonmoveableObj).resizeWalls();
        	// The SurroundingWalls
        	else if (name.equals("walls")) {
        		// Remember that height & width are still swapped!! 
        		((SurroundingWalls)nonmoveableObj).resizeWalls(height, width);
        	}
        }
	}
	public void resizeField(GraphicalRepresentation graphicalRepresentation)
	{
		graphicalRepresentation.setWidth((int)(World.WIDTH * Simulator.pixelsPerMeter));
		graphicalRepresentation.setHeight((int)(World.HEIGHT * Simulator.pixelsPerMeter));
		graphicalRepresentation.repaint();
	}
}
