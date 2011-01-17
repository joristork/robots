/*
 * Created on 21-feb-2006
 * Copyright: (c) 2006
 * Company: Dancing Bear Software
 * 
 * @version $Revision: 1.1 $
 * 
 * Adapted from: http://www.ling.upenn.edu/~tklee/Projects/dsp/#appletviewer
 * Copyright info: http://www.ling.upenn.edu/~tklee/Projects/dsp/#copyright
 * Copyright info at 21-feb-2006 Do whatever you want with the code.  Feedbacks and improvement welcome.
 */
package javaBot;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

public class GraphPlot extends JPanel
{

	// JDK 1.02 version

	public static final int	SIGNAL			= 1;
	public static final int	SPECTRUM		= 2;

	// Settings used for plotting
	private Color			plotColor		= Color.red;
	private Color			axisColor		= Color.black;
	private Color			gridColor		= Color.black;
	private Color			bgColor			= Color.white;
	private int				plotStyle		= SIGNAL;
	private boolean			tracePlot		= true;
	private boolean			logScale		= false;
	private int				vertSpace		= 20;
	private int				horzSpace		= 20;
	private int				vertIntervals	= 8;
	private int				horzIntervals	= 10;
	private int				nPoints			= 0;
	private float			xmax			= 0.0f;
	//private float			ymax			= 0.0f;
	private float			ymax			= 60.0f;
	private float			xScale, yScale;

	// Used for storing the values shown
	private byte[]			plotValues;

	// Getters and setters for settings
	public void setPlotColor(Color c)
	{
		if (c != null)
			plotColor = c;
	}

	public Color getPlotColor()
	{
		return plotColor;
	}

	public void setAxisColor(Color c)
	{
		if (c != null)
			axisColor = c;
	}

	public Color getAxisColor()
	{
		return axisColor;
	}

	public void setGridColor(Color c)
	{
		if (c != null)
			gridColor = c;
	}

	public Color getGridColor()
	{
		return gridColor;
	}

	public void setBgColor(Color c)
	{
		if (c != null)
			bgColor = c;
	}

	public Color getBgColor()
	{
		return bgColor;
	}

	public void setPlotStyle(int pst)
	{
		if ((pst == SIGNAL) || (pst == SPECTRUM))
			plotStyle = pst;
		else
			plotStyle = SIGNAL;
	}

	public int getPlotStyle()
	{
		return plotStyle;
	}

	public void setTracePlot(boolean b)
	{
		tracePlot = b;
	}

	public boolean isTracePlot()
	{
		return tracePlot;
	}

	public void setLogScale(boolean b)
	{
		logScale = b;
	}

	public boolean isLogScale()
	{
		return logScale;
	}

	public void setVertSpace(int v)
	{
		if (v >= 0)
			vertSpace = v;
		else
			vertSpace = 0;
	}

	public int getVertSpace()
	{
		return vertSpace;
	}

	public void setHorzSpace(int h)
	{
		if (h >= 0)
			horzSpace = h;
		else
			horzSpace = 0;
	}

	public int getHorzSpace()
	{
		return horzSpace;
	}

	public int getVertIntervals()
	{
		return vertIntervals;
	}

	public void setVertIntervals(int i)
	{
		if (i >= 0)
			vertIntervals = i;
		else
			vertIntervals = 0;
	}

	public int getHorzIntervals()
	{
		return horzIntervals;
	}

	public void setHorzIntervals(int i)
	{
		if (i >= 0)
			horzIntervals = i;
		else
			horzIntervals = 0;
	}

	public void setYmax(float m)
	{
		if (m >= 0)
			ymax = m;
		else
			ymax = 0;
	}

	public float getYmax()
	{
		return ymax;
	}

	/**
	 * Will calculate the maximum Y value which can be used to
	 * set the scale of the graph. (Highest peak will reach top of graph)
	 */
	private byte calculateYmax(byte[] values)
	{
		byte maxValue = 0;
		for (int i = 0; i < values.length; i++)
			maxValue = (byte)Math.max(maxValue, Math.abs(values[i]));
		return maxValue;
	}

	/**
	 * Will let the user set a float array for showing by the graph window
	 */
	public void setPlotValues(byte[] values)
	{
		nPoints = values.length;
		plotValues = values;
		//this.setYmax(calculateYmax(values));
		repaint();
	}


	/**
	 * Will return the value at index
	 *
	 * @return float Value plotted at index
	 */
	public float getData(int index)
	{
		return getData()[index];
	}

	/**
	 * Will return the value at index
	 *
	 * @return float Value plotted at index
	 */
	public byte[] getData()
	{
		return plotValues;
	}

	/**
	 * Will return the value at index
	 *
	 * @param g Grahics object used for painting
	 */
	public void paint(Graphics g)
	{
		int x, y;
		int top = vertSpace;
		int bottom = getSize().height - vertSpace;
		int left = horzSpace;
		int right = getSize().width - horzSpace;
		int width = right - left;
		int fullHeight = bottom - top;
		int centre = (top + bottom) / 2;
		int xAxisPos = centre;
		int yHeight = fullHeight / 2;

		//Teken initiele zaken
		super.paintComponent(g);

		if (plotStyle == SPECTRUM)
		{
			xAxisPos = bottom;
			yHeight = fullHeight;
		}
		this.setBackground(bgColor);
		if (logScale)
		{
			xAxisPos = top;
			g.setColor(gridColor);
			// vertical grid lines
			for (int i = 0; i <= vertIntervals; i++)
			{
				x = left + i * width / vertIntervals;
				g.drawLine(x, top, x, bottom);
			}
			// horizontal grid lines
			for (int i = 0; i <= horzIntervals; i++)
			{
				y = top + i * fullHeight / horzIntervals;
				g.drawLine(left, y, right, y);
			}
		}
		g.setColor(axisColor);
		g.drawLine(left, top, left, bottom); // vertical axis
		g.drawLine(left, xAxisPos, right, xAxisPos); // horizontal axis

		if (nPoints != 0)
		{
			g.setColor(plotColor);
			// horizontal scale factor:
			xScale = width / (float) (nPoints - 1);
			// vertical scale factor:
			yScale = yHeight / ymax;
			int[] xCoords = new int[nPoints];
			int[] yCoords = new int[nPoints];
			for (int i = 0; i < nPoints; i++)
			{
				xCoords[i] = left + Math.round(i * xScale);
				yCoords[i] = xAxisPos - Math.round(plotValues[i] * yScale);
			}
			if (tracePlot)
				for (int i = 0; i < nPoints - 1; i++)
					g.drawLine(xCoords[i], yCoords[i], xCoords[i + 1], yCoords[i + 1]);
			else
			{
				for (int i = 0; i < nPoints; i++)
					g.drawLine(xCoords[i], xAxisPos, xCoords[i], yCoords[i]);
			}
		}
	}
}
