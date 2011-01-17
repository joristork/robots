/*
 * Created on Jun 2, 2004
 */
package javaBot.JPB2;

import com.muvium.web.WSDL;

/**
 * @author James Caska
 * The webservice interface allows a computer to execute commands
 * on the robot. The following commands are provided in the
 * interface.
 */
public interface AgentWebService extends WSDL
{

	public int getSensor(int sensor);

	public int getState();

	public void setState(int dip);

	public void vector(int vx, int vy, int omega);

	public void drive(int vx, int vy, int vz);
	
	public void reportState (int level);

}
