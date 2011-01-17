/*
 * Created on 22-feb-2006
 * Copyright: (c) 2006
 * Company: Dancing Bear Software
 * 
 * @version $Revision: 1.1 $
 */
package javaBot.tests;

import java.awt.Color;
import javaBot.GraphPlot;

import junit.framework.Assert;
import junit.framework.TestCase;

public class GraphPlotTest extends TestCase
{
	Color[]	colors	= {
			Color.BLACK,
			Color.BLUE,
			Color.CYAN,
			Color.DARK_GRAY,
			Color.GRAY,
			Color.GREEN,
			Color.LIGHT_GRAY,
			Color.MAGENTA,
			Color.ORANGE,
			Color.PINK,
			Color.RED,
			Color.WHITE,
			Color.YELLOW};

	/*
	 * Test method for 'javaBot.GraphPlot.setPlotColor(Color)'
	 */
	public void testSetPlotColor()
	{
		GraphPlot graphTest = new GraphPlot();
		for (int i = 0; i < colors.length; i++)
		{
			graphTest.setPlotColor(colors[i]);
			Assert.assertEquals(colors[i], graphTest.getPlotColor());
		}
	}

	/*
	 * Test method for 'javaBot.GraphPlot.setAxisColor(Color)'
	 */
	public void testSetAxisColor()
	{
		GraphPlot graphTest = new GraphPlot();
		for (int i = 0; i < colors.length; i++)
		{
			graphTest.setAxisColor(colors[i]);
			Assert.assertEquals(colors[i], graphTest.getAxisColor());
		}
	}

	/*
	 * Test method for 'javaBot.GraphPlot.setGridColor(Color)'
	 */
	public void testSetGridColor()
	{
		GraphPlot graphTest = new GraphPlot();
		for (int i = 0; i < colors.length; i++)
		{
			graphTest.setGridColor(colors[i]);
			Assert.assertEquals(colors[i], graphTest.getGridColor());
		}
	}

	/*
	 * Test method for 'javaBot.GraphPlot.setBgColor(Color)'
	 */
	public void testSetBgColor()
	{
		GraphPlot graphTest = new GraphPlot();
		for (int i = 0; i < colors.length; i++)
		{
			graphTest.setBgColor(colors[i]);
			Assert.assertEquals(colors[i], graphTest.getBgColor());
		}
	}

	/*
	 * Test method for 'javaBot.GraphPlot.setPlotStyle(int)'
	 */
	public void testSetPlotStyle()
	{
		GraphPlot graphTest = new GraphPlot();
		// Constructor value check
		Assert.assertEquals(GraphPlot.SIGNAL, graphTest.getPlotStyle());
		// Set check
		graphTest.setPlotStyle(GraphPlot.SIGNAL);
		Assert.assertEquals(GraphPlot.SIGNAL, graphTest.getPlotStyle());
		graphTest.setPlotStyle(GraphPlot.SPECTRUM);
		Assert.assertEquals(GraphPlot.SPECTRUM, graphTest.getPlotStyle());
	}

	/*
	 * Test method for 'javaBot.GraphPlot.setTracePlot(boolean)'
	 */
	public void testSetTracePlot()
	{
		GraphPlot graphTest = new GraphPlot();
		graphTest.setTracePlot(false);
		Assert.assertTrue(!graphTest.isTracePlot());
		graphTest.setTracePlot(true);
		Assert.assertTrue(graphTest.isTracePlot());
	}

	/*
	 * Test method for 'javaBot.GraphPlot.setLogScale(boolean)'
	 */
	public void testSetLogScale()
	{
		GraphPlot graphTest = new GraphPlot();
		graphTest.setLogScale(false);
		Assert.assertTrue(!graphTest.isLogScale());
		graphTest.setLogScale(true);
		Assert.assertTrue(graphTest.isLogScale());
	}

	/*
	 * Test method for 'javaBot.GraphPlot.setVertSpace(int)'
	 */
	public void testSetVertSpace()
	{
		GraphPlot graphTest = new GraphPlot();
		graphTest.setVertSpace(0);
		Assert.assertEquals(0, graphTest.getVertSpace());
		graphTest.setVertSpace(20);
		Assert.assertEquals(20, graphTest.getVertSpace());
		graphTest.setVertSpace(-10);
		Assert.assertEquals(0, graphTest.getVertSpace());
	}

	/*
	 * Test method for 'javaBot.GraphPlot.setHorzSpace(int)'
	 */
	public void testSetHorzSpace()
	{
		GraphPlot graphTest = new GraphPlot();
		graphTest.setHorzSpace(0);
		Assert.assertEquals(0, graphTest.getHorzSpace());
		graphTest.setHorzSpace(30);
		Assert.assertEquals(30, graphTest.getHorzSpace());
		graphTest.setHorzSpace(-15);
		Assert.assertEquals(0, graphTest.getHorzSpace());
	}

	/*
	 * Test method for 'javaBot.GraphPlot.setVertIntervals(int)'
	 */
	public void testSetVertIntervals()
	{
		GraphPlot graphTest = new GraphPlot();
		graphTest.setVertIntervals(0);
		Assert.assertEquals(0, graphTest.getVertIntervals());
		graphTest.setVertIntervals(25);
		Assert.assertEquals(25, graphTest.getVertIntervals());
		graphTest.setVertIntervals(-12);
		Assert.assertEquals(0, graphTest.getVertIntervals());
	}

	/*
	 * Test method for 'javaBot.GraphPlot.setHorzIntervals(int)'
	 */
	public void testSetHorzIntervals()
	{
		GraphPlot graphTest = new GraphPlot();
		graphTest.setHorzIntervals(0);
		Assert.assertEquals(0, graphTest.getHorzIntervals());
		graphTest.setHorzIntervals(56);
		Assert.assertEquals(56, graphTest.getHorzIntervals());
		graphTest.setHorzIntervals(-170);
		Assert.assertEquals(0, graphTest.getHorzIntervals());
	}

	/*
	 * Test method for 'javaBot.GraphPlot.setYmax(float)'
	 */
	public void testSetYmax()
	{
		GraphPlot graphTest = new GraphPlot();
		graphTest.setYmax(0.0f);
		Assert.assertEquals(0.0f, graphTest.getYmax(), 0.0f);
		graphTest.setYmax(1.4f);
		Assert.assertEquals(1.4f, graphTest.getYmax(), 0.0f);
		graphTest.setYmax(-80.4f);
		Assert.assertEquals(0.0f, graphTest.getYmax(), 0.0f);
	}

	/*
	 * Test method for 'javaBot.GraphPlot.setBytePlotValues(float[])'
	 */
	public void testSetPlotValues()
	{
		GraphPlot graphTest = new GraphPlot();
		byte[] testBytes = {-127, 125, 45, 23, 90};
		graphTest.setPlotValues(testBytes);

		if (graphTest.getData().length != testBytes.length)
			Assert.assertTrue(false);

		for (int i = 0; i < graphTest.getData().length; i++)
		{
			Assert.assertEquals((float) testBytes[i], graphTest.getData(i), 0.0f);
		}
	}
}
