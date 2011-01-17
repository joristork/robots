package javaBot;

import java.awt.Image;
import java.awt.image.ImageObserver;
import java.awt.image.PixelGrabber;

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
class PixelGrab implements ImageObserver
{
	
	 private int m_iWidth, m_iHeight;
	 
	/**
	 * Creates a new PixelGrab object.
	 */
	public PixelGrab(Image m_image)
	{ 
		m_iWidth=m_image.getWidth(this);
		m_iHeight=m_image.getHeight(this); 
	}

	/**
	 * TODO METHOD: DOCUMENT ME!
	 *
	 * @param x TODO PARAM: param description
	 * @param y TODO PARAM: param description
	 * @param pixel TODO PARAM: param description
	 *
	 * @return $returnType$ TODO RETURN: return description
	 */
	public int handleSinglePixel(int pixel)
	{
//		int alpha = (pixel >> 24) & 0xff;
		int red = (pixel >> 16) & 0xff;
		int green = (pixel >> 8) & 0xff;
		int blue = (pixel) & 0xff;
		int pix = red + green + blue;
		// Here we must rescale the value to match the output of the real JoBot sensor
//		System.out.println("Pix=" + pix + " R=" + red + " G=" + green + " B=" + blue);
		return pix;
	}

	/**
	 * TODO METHOD: DOCUMENT ME!
	 *
	 * @param img TODO PARAM: param description
	 * @param x TODO PARAM: param description
	 * @param y TODO PARAM: param description
	 * @param w TODO PARAM: param description
	 * @param h TODO PARAM: param description
	 *
	 * @return $returnType$ TODO RETURN: return description
	 */
	public int handlePixels(Image img, int x, int y, int w, int h)
	{
		long pixval = 0;
		int pixCount = w * h;
		int[] pixels = new int[pixCount];
		PixelGrabber pg = new PixelGrabber(img, x, y, w, h, pixels, 0, w);

		try
		{
			pg.grabPixels();
		}
		catch (InterruptedException e)
		{
			System.err.println("interrupted waiting for pixels!");
			return -1;
		}

		if ((pg.getStatus() & ImageObserver.ABORT) != 0)
		{
			System.err.println("image fetch aborted or error");
			return -1;
		}
		
		for (int i=0; i < pixCount; i++) {
			pixval += handleSinglePixel(pixels[i]);
		}
		return (int) pixval/pixCount;
	}
	
	  public int getWidth()
	  {
	    return m_iWidth;
	  }

	  public int getHeight()
	  {
	    return m_iHeight;
	  }
	
//	 we need this method just because we're extending ImageObserver.
	  public boolean imageUpdate(Image img, int infoflags, int x, int y, 
	    int width, int height) 
	  {
	    return true;	  
	  }  
}
