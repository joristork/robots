/*
 * Created on Jun 2, 2004
 */
package javaBot.UVM;

import com.muvium.web.WSDL;

/**
 * @author James Caska
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
