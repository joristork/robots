/*
 * Created on Feb 28, 2006
 * Copyright: (c) 2006
 * Company: Dancing Bear Software
 * 
 * @version $Revision: 1.1 $
 */
package javaBot;

public class ReflectionException extends Exception
{
	private String	message	= "";

	public ReflectionException(Exception e, String className)
	{
		super(e);
		message = "Reflection exception occurred in: " + className;
	}

	public String toString()
	{
		return message;
	}

}
