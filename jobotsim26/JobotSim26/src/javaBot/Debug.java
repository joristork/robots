package javaBot;

import java.util.Date;

/**
 * Used for various levels of output in runtime, all debug/info/error output
 * statements should be routed through this class. The level of detail of the
 * output can be defined setting the <code>debug_level</code> variable.
 */
public class Debug
{
	// Define various levels of debugging

	/** No output at all */
	public final static int	DEBUG_LEVEL_NONE	= 0;

	/** Output only errors */
	public final static int	DEBUG_LEVEL_ERROR	= 1;

	/** Output errors and info statements */
	public final static int	DEBUG_LEVEL_INFO	= 2;

	/** Output everything */
	public final static int	DEBUG_LEVEL_DEBUG	= 3;

	// Set standard level to 0
	public static int		DEBUG_LEVEL			= 0;

	// Set standard output device to System.out
	// public static Simulator gui = null;  // JD: removed - is never used

	/**
	 * Print message on debug level
	 *
	 * @param message TODO PARAM: DOCUMENT ME!
	 */
	public static void printDebug(String message)
	{
		if (DEBUG_LEVEL == DEBUG_LEVEL_DEBUG)
		{
			System.out.print(addPadding(getDate(), 11) + addPadding("<DEBUG>", 10)
					+ addPadding("<" + getCallerClassName() + ">", 40) + message + '\n');
		}
	}

	/**
	 * Print message on information level
	 *
	 * @param message TODO PARAM: DOCUMENT ME!
	 */
	public static void printInfo(String message)
	{
		if (DEBUG_LEVEL >= DEBUG_LEVEL_INFO)
		{
			System.out.print(addPadding(getDate(), 11) + message + '\n');
		}
	}

	/**
	 * Print message on error level
	 *
	 * @param message TODO PARAM: DOCUMENT ME!
	 */
	public static void printError(String message)
	{
		if (DEBUG_LEVEL >= DEBUG_LEVEL_ERROR)
		{
			System.out.print(addPadding(getDate(), 11)
					+ addPadding("<ERROR>", 10)
					+ addPadding("<" + getCallerClassName() + "." + getCallerMethodName() + "()>",
							40) + message + '\n');
		}
	}

	/**
	 * Return short formatted current date
	 *
	 * @return $returnType$ TODO RETURN: return description
	 */
	private static String getDate()
	{
		Date now = new Date(System.currentTimeMillis());
		java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat("HH:mm:ss");

		return formatter.format(now);
	}

	/**
	 * Return caller's classname
	 *
	 * @return $returnType$ TODO RETURN: return description
	 */
	private static String getCallerClassName()
	{
		StackTraceElement parent = (new Exception()).getStackTrace()[2];

		return parent.getClassName();
	}

	/**
	 * Return caller's methodname
	 *
	 * @return $returnType$ TODO RETURN: return description
	 */
	private static String getCallerMethodName()
	{
		StackTraceElement parent = (new Exception()).getStackTrace()[2];

		return parent.getMethodName();
	}

	/**
	 * Add padding to string for output
	 *
	 * @param str TODO PARAM: DOCUMENT ME!
	 * @param SIZE TODO PARAM: DOCUMENT ME!
	 *
	 * @return $returnType$ TODO RETURN: return description
	 */
	private static String addPadding(String str, int size)
	{
		String spaces = "                                                 ";

		return (str + spaces).substring(0, size);
	}
}
