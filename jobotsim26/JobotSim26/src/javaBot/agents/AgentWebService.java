/*
 * Created on Jun 2, 2004
 */
package javaBot.agents;

import com.muvium.web.WSDL;

/**
 * DOCUMENT ME!
 *
 * @author James Caska
 */
public interface AgentWebService extends WSDL
{
	/**
	 * TODO METHOD: DOCUMENT ME!
	 *
	 * @param sensor TODO PARAM: param description
	 *
	 * @return $returnType$ TODO RETURN: return description
	 */
	public int getSensor(int sensor);

	/**
	 * TODO METHOD: DOCUMENT ME!
	 *
	 * @return $returnType$ TODO RETURN: return description
	 */
	public int getState();

	/**
	 * TODO METHOD: DOCUMENT ME!
	 *
	 * @param dip TODO PARAM: param description
	 */
	public void setState(int dip);

	/**
	 * TODO METHOD: DOCUMENT ME!
	 *
	 * @param vx TODO PARAM: param description
	 * @param vy TODO PARAM: param description
	 * @param omega TODO PARAM: param description
	 */
	public void vector(int vx, int vy, int omega);

	/**
	 * TODO METHOD: DOCUMENT ME!
	 *
	 * @param vx TODO PARAM: param description
	 * @param vy TODO PARAM: param description
	 * @param vz TODO PARAM: param description
	 */
	public void drive(int vx, int vy, int vz);
}
