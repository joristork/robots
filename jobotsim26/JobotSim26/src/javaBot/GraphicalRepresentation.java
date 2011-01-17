/*
 * Created on 16-jun-2004
 */
package javaBot;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

/**
 * Used to create visual representations in the GUI of various objects in the
 * application. Objects that should be visible in the GUI should override the
 * <code>createGraphicalRepresentation()</code> and return a subclass of
 * GraphicalRepresentation or a GraphicalRepresentation using standard
 * settings: <br>
 * Basic shape:<br>
 * <code><br>
 * public GraphicalRepresentation createGraphicalRepresentation() {<br>
 * return new GraphicalRepresentation(this);<br>
 * }<br>
 * </code><br>
 * Including an image:<br>
 * <code><br>
 * public GraphicalRepresentation createGraphicalRepresentation() {<br>
 * return new GraphicalRepresentation(this, IMAGE_FILE, DIAMETER_IN_IMAGE);<br>
 * }<br>
 * </code><br>
 * For comples classes an inner class can be created subclassing
 * GraphicalRepresentation and then overriding the paintComponent(Graphics g)
 * method to do custom paintjobs;
 */
public class GraphicalRepresentation extends JLabel
{
	/**
	 * Determines if labels are to be added to any new graphical
	 * representations by default
	 */
	public static boolean		addLabels			= false;

	/*
	 * Constant value indicating the amount of space to save for
	 * the representation's label.
	 */
	private static final int	LABEL_SPACE			= 20;

	// Keep track of what object this is linked to
	private PhysicalObject		physicalObject		= null;

	/*
	 * The source image for this graphical representation is kept
	 * in BufferedImage sourceImage
	 */
	private BufferedImage		sourceImage			= null;

	/*
	 * rotating flag determines if the image rotates of not
	 */
	private boolean				rotating			= false;

	/*
	 * The horizontal offset from the top left corner of the
	 * representation to it's center, measured in pixels. This value is
	 * used to calculate the correct location of the representation in
	 * the GUI.
	 */
	private double				offsetX;

	/*
	 * The vertical offset from the top left corner of the
	 * representation to it's center, measured in pixels. This value is
	 * used to calculate the correct location of the representation in
	 * the GUI.
	 */
	private double				offsetY;

	/*
	 * The object's rotation (orientation) in the previous frame
	 * is stored in previousRotation. This allows update to check
	 * if a new image has to be generated when the image is rotating
	 */
	private double				previousRotation	= 0.0;

	/*
	 * scaling factor
	 */
	private double				scale;
	
	int 						width;
	
	int 						height;

	/*
	 * The object's diameter in pixels within the image. Usually the
	 * image dimensions are bigger than the part of the image representing
	 * the object. To be able to scale the image correctly this value must
	 * be set.
	 */
	private int					diameterInImage;

	/**
	 * what layer the object should be shown in, these are constants in the
	 * Simulator default this value should not be overridden
	 */
	public Integer				layer				= Simulator.UNDEFINED_LAYER;

	/**
	 * Creates a default GraphicalRepresentation with no fancy features
	 *
	 * @param physicalObject Any PhysicalObject which needs to be visible
	 */
	public GraphicalRepresentation(PhysicalObject physicalObject)
	{
		super();
		this.physicalObject = physicalObject;

		if (addLabels)
		{
			setText(physicalObject.name);
		}

		// SIZE is rounded up to make sure the representation fits in the container
		int size = (int) Math.ceil(Simulator.pixelsPerMeter * physicalObject.diameter);
		offsetX = (size + 1) / 2;
		offsetY = (size + 1) / 2;
		setSize(size, size + LABEL_SPACE);
		setIconTextGap(0);
		setVerticalAlignment(TOP);
		setVerticalTextPosition(BOTTOM);
		setHorizontalTextPosition(CENTER);
		setOpaque(false);
		setVisible(true);
	}

	/**
	 * Creates a new GraphicalRepresentation object using an image
	 *
	 * @param physicalObject Any PhysicalObject which needs to be visible
	 * @param imagePath Path to the image that should be displayed
	 * @param diameterInImage diameter of the image
	 */
	public GraphicalRepresentation(PhysicalObject physicalObject, String imagePath,
			int diameterInImage)
	{
		this(physicalObject);

		try
		{
			sourceImage = ImageIO.read(new File(imagePath));
		}
		catch (IOException e)
		{
			Debug.printError("An error occurred while reading file " + imagePath + "\n"
					+ e.getMessage());
			sourceImage = null;

			return;
		}

		this.diameterInImage = diameterInImage;
		scale = (Simulator.pixelsPerMeter * physicalObject.diameter) / diameterInImage;

		BufferedImage image = getTransformedImage();
		setIcon(new ImageIcon(image));

		int width = image.getWidth();
		int height = image.getHeight();
		offsetX = width / 2.0;
		offsetY = height / 2.0;
		setSize(width, height + LABEL_SPACE);
	}

	/**
	 * Creates a new GraphicalRepresentation object using a rotating  image
	 * (image rotates according to object's rotation
	 *
	 * @param physicalObject Any PhysicalObject which needs to be visible
	 * @param imagePath Path to the image that should be displayed
	 * @param diameterInImage diameter of the image
	 * @param rotating should the image rotate?
	 */
	public GraphicalRepresentation(PhysicalObject physicalObject, String imagePath,
			int diameterInImage, boolean rotating)
	{
		this(physicalObject, imagePath, diameterInImage);
		this.rotating = rotating;
	}

	/**
	 * Gets the horizontal offset from the top left corner of the
	 * representation to it's center, measured in pixels.
	 *
	 * @return $returnType$ TODO RETURN: return description
	 */
	public double getOffsetX()
	{
		return offsetX;
	}

	/**
	 * Gets the vertical offset from the top left corner of the representation
	 * to it's center, measured in pixels.
	 *
	 * @return $returnType$ TODO RETURN: return description
	 */
	public double getOffsetY()
	{
		return offsetY;
	}

	/**
	 * Turns the label for this graphical representation on or off.
	 *
	 * @param show TODO PARAM: DOCUMENT ME!
	 */
	public void setLabeled(boolean show)
	{
		if (show)
		{
			setText(physicalObject.name);

			Debug.printDebug("Setting label for " + physicalObject.name);
		}
		else
		{
			Debug.printDebug("Removing label for " + physicalObject.name);
			setText("");
		}
	}

	/**
	 * Gets the PhysicalObject that this representation belongs to.
	 *
	 * @return $returnType$ TODO RETURN: return description
	 */
	public PhysicalObject getPhysicalObject()
	{
		return physicalObject;
	}

	/**
	 * Updates the representation's location, and if neccesary the image. If
	 * the representation not an image it draws the alternative
	 * representation.
	 *
	 * @param g TODO PARAM: DOCUMENT ME!
	 */
	public void paintComponent(Graphics g)
	{
		setLocation(getGUILocation());

		if (sourceImage == null)
		{
			// Draw a circle
			super.paintComponent(g);

			int m = (int) (((Simulator.pixelsPerMeter * physicalObject.diameter) / 2.0) + 0.5);
			int d = (int) Math.ceil(Simulator.pixelsPerMeter * physicalObject.diameter);
			g.setColor(Color.DARK_GRAY);
			g.fillOval(m - 1, m - 1, 3, 3);
			g.drawOval(0, 0, d - 1, d - 1);
		}
		else
		{ // An image exists, so prospone super method to end

			if (rotating && (physicalObject.rotation != previousRotation))
			{
				previousRotation = physicalObject.rotation;
				((ImageIcon) getIcon()).setImage(getTransformedImage());
			}

			super.paintComponent(g);
		}
	}

	/**
	 * Calculates at which point the top left corner of the swing component
	 * should be placed within the GUI in pixel coordinates. It depends on the
	 * world dimensions, the simulator resolution and the offset values for
	 * this graphical representation.
	 *
	 * @return $returnType$ TODO RETURN: return description
	 */
	private Point getGUILocation()
	{
		double worldX = physicalObject.getLocation().getX();
		double worldY = physicalObject.getLocation().getY();
		int x = (int) ((Simulator.pixelsPerMeter * worldX) - offsetX + .5);
		int y = (int) ((Simulator.pixelsPerMeter * (World.HEIGHT - worldY)) - offsetY + .5);

		return new Point(x, y);
	}

	/**
	 * Generates the rotated and scaled version of the image acting as
	 * graphical representation.
	 *
	 * @return $returnType$ TODO RETURN: return description
	 */
	private BufferedImage getTransformedImage()
	{
		int sourceWidth = sourceImage.getWidth();
		int sourceHeight = sourceImage.getHeight();
		AffineTransform transformationMatrix = AffineTransform.getScaleInstance(scale, scale);
		AffineTransform rotationMatrix = AffineTransform.getRotateInstance(
				-physicalObject.rotation, sourceWidth / 2.0, sourceHeight / 2.0);
		transformationMatrix.concatenate(rotationMatrix);

		AffineTransformOp transformation = new AffineTransformOp(transformationMatrix,
				AffineTransformOp.TYPE_BILINEAR);

		BufferedImage image = transformation.filter(sourceImage, null);
		int newWidth = Math.min(image.getWidth(), (int) ((sourceWidth * scale) + 1));
		int newHeight = Math.min(image.getHeight(), (int) ((sourceHeight * scale) + 1));

		return image.getSubimage(0, 0, newWidth, newHeight);
	}

	/**
	 * Set the layer to display this object on in the Simulator. Default this
	 * should not be overridden
	 *
	 * @param layer What layer to use, these are statics in the Simulator
	 *        class.
	 */
	public void setLayer(Integer layer)
	{
		this.layer = layer;
	}
	
	public void setWidth(int newWidth) 
	{
		width = newWidth;
		setSize(width, getHeight());
	}
	public void setHeight(int newHeight)
	{
		height = newHeight;
		setSize(getWidth(), height);
	}
	
	public void updateScale()
	{		 
		scale = (Simulator.pixelsPerMeter * physicalObject.diameter) / diameterInImage; 
		BufferedImage image = getTransformedImage(); 
		setIcon(new ImageIcon(image)); 
 
		int width = image.getWidth(); 
		int height = image.getHeight(); 
		offsetX = width / 2.0; 
		offsetY = height / 2.0; 
		setSize(width, height + LABEL_SPACE); 
	} 
}
