package javaBot;

import java.awt.Image;
import java.awt.Point;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.PixelGrabber;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.WindowConstants;

/**
 * Runnable that handles the content of the Mouse Sensor window.
 * When Robot robot possesses a Sensor of the type DXMouseSensor, a pixelfragment
 * from the soccer field background image will be returned to be displayed in the
 * window. The position of the fragment on the bitmap is relative to the robot's
 * position on the field. When robot does not posses DXMouseSensor, a scrolling
 * smiley is returned.
 * 
 * Last modified on 3-7-2006
 */
public class OpticalMouseAdaptor implements Runnable{
    private Robot robot;

    public OpticalMouseAdaptor(Robot robot){
        super();
        this.robot = robot;
    }

    public Location robotLocation = new Location();
    private byte[] inputByte = new byte[256];
    private DataInputStream inputStream = null;
    private boolean bRunning = true;

    public void run(){
        final SerialGUI serialGUI = new SerialGUI();
        serialGUI.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);

        serialGUI.addWindowListener(new WindowAdapter(){
            public void windowClosing(WindowEvent e){
                serialGUI.setVisible(false);
                bRunning = false;
            }
        });

        serialGUI.setVisible(true);
        readInputStream(serialGUI);
    }

    private void readInputStream(SerialGUI serialGUI){
        while (bRunning){
            for (int Offset = 0; Offset <= 13; Offset++){
            	// Reactivate this code when interface with real robot is implemented
            	// Check interface for sound sensor in SoccerDemo for this
                //Read serial input (Real stream input!)
                //inputStream = new
                // DataInputStream(serialPort.getInputStream()); // does not work!

                inputByte = pixelArea(Offset);
                ByteArrayInputStream inputData = new ByteArrayInputStream(inputByte);
                inputStream = new DataInputStream(inputData); 	// Use input data               
                serialGUI.setData(inputStream);  				// Convert to local bytearray               
                serialGUI.DrawOutput();  						// Show data on screen
            }
        }
    }

    /**
     * Grabs an area of pixels from the football playingfield image
     * Currently, it grabs from the football field image regardless
     * of it actually being displayed. In the current implementation
     * there is a slight deviation to the left.
     * Rotations are not supported, due to a high computationality.
     * 
     * @return byte array with pixelvalues
     */
    private byte[] pixelArea(int Offset){

        Sensor[] sar = robot.getSensors();
        for (int i = 0; i < sar.length; i++){
            if (sar[i] instanceof DXMouseSensor
        		&& sar[i+1] instanceof DYMouseSensor){
        		    
              File file = new File(Simulator.IMAGE_FILE);
              int imageWidth = 816;   // Default size of soccer field
              int imageHeight = 479;  
            	
                try{
                    Image img = ImageIO.read(file);
                    PixelGrab gr = new PixelGrab(img);
                    imageWidth = gr.getWidth();  // Get the real image size
                    imageHeight = gr.getHeight();

                    byte[] b = new byte[256];
                    int[] pixels = new int[256];
//                  System.out.println("W=" + imageWidth + ",H=" + imageHeight + 
//                    		          ",WW=" + World.WIDTH + ",WH=" + World.HEIGHT);
                    Location senloc = getSensorLocation(robot, sar[i]);
                    int pixelX = (int)(imageWidth*senloc.getX()/World.WIDTH);
                    int pixelY = (int)(imageHeight-(imageHeight*senloc.getY()/World.HEIGHT));
                    Point loc = Simulator.toPixelCoordinates(senloc);
//                  System.out.println("X=" + pixelX+" "+senloc.getX()+" "+senloc.getX()/World.WIDTH);
//                  System.out.println("Y=" + pixelY+" "+senloc.getY()+" "+senloc.getY()/World.HEIGHT);
                    PixelGrabber pg = new PixelGrabber(img, pixelX-10, pixelY-20, 16, 16, pixels, 0, 16);
                    pg.grabPixels();
                    for (int j = 0; j < 256; j++){
                        b[j] = (byte)handleSinglePixel(pixels[j]);
                    }
                    b[255] = 127;
                    return b;
                }catch (Exception e){
                    Debug.printError("An error occurred in OpticalMouseAdaptor: "+ e.toString());
                }
            }
        }
        return Smilie(Offset);
    }

    /**
     * Geniest piece of simulator, made by Maarten and Jakob! :D
     * 
     * For demo purposes!
     * 
     * @param Offset
     * @return Smilie existence out of byte array
     */
    private byte[] Smilie(int Offset){
        byte[] b = new byte[1024]; // Should be 1024 due the smilie will be
        // drawn beneath the borders

        int Color;
        // Oog links
        Color = 250;
        b[((2 + Offset) * 16) + 3] = (byte)(Color);
        b[((2 + Offset) * 16) + 4] = (byte)(Color);
        b[((3 + Offset) * 16) + 3] = (byte)(Color);
        b[((3 + Offset) * 16) + 4] = (byte)(Color);

        // Oog rechts
        Color = 250;
        b[((2 + Offset) * 16) + 10] = (byte)(Color);
        b[((2 + Offset) * 16) + 11] = (byte)(Color);
        b[((3 + Offset) * 16) + 10] = (byte)(Color);
        b[((3 + Offset) * 16) + 11] = (byte)(Color);

        // Neus
        Color = 30;
        b[((4 + Offset) * 16) + 7] = (byte)(Color);
        b[((5 + Offset) * 16) + 7] = (byte)(Color);
        b[((6 + Offset) * 16) + 7] = (byte)(Color);
        b[((7 + Offset) * 16) + 7] = (byte)(Color);
        b[((8 + Offset) * 16) + 7] = (byte)(Color);
        b[((7 + Offset) * 16) + 6] = (byte)(Color);
        b[((7 + Offset) * 16) + 8] = (byte)(Color);
        b[((8 + Offset) * 16) + 6] = (byte)(Color);
        b[((8 + Offset) * 16) + 8] = (byte)(Color);

        // Mond
        Color = 210;
        b[((9 + Offset) * 16) + 2] = (byte)(Color);
        b[((9 + Offset) * 16) + 12] = (byte)(Color);
        b[((10 + Offset) * 16) + 2] = (byte)(Color);
        b[((10 + Offset) * 16) + 12] = (byte)(Color);
        b[((11 + Offset) * 16) + 3] = (byte)(Color);
        b[((11 + Offset) * 16) + 11] = (byte)(Color);
        b[((12 + Offset) * 16) + 4] = (byte)(Color);
        b[((12 + Offset) * 16) + 5] = (byte)(Color);
        b[((12 + Offset) * 16) + 6] = (byte)(Color);
        b[((12 + Offset) * 16) + 7] = (byte)(Color);
        b[((12 + Offset) * 16) + 8] = (byte)(Color);
        b[((12 + Offset) * 16) + 9] = (byte)(Color);
        b[((12 + Offset) * 16) + 10] = (byte)(Color);

        b[(15 * 16) + 15] = 127;

        return b;
    }

    public Location getSensorLocation(Robot r, Sensor s){
        double diam = Math.sqrt(Math.pow(s.getPosition().getX(), 2)
                + Math.pow(s.getPosition().getY(), 2));
        double posX = r.getLocation().getX();
        double posY = r.getLocation().getY();
        posX += Math.cos(s.getAngle() + r.getRotation()) * diam;
        posY += Math.sin(s.getAngle() + r.getRotation()) * diam;
        return new Location(posX, posY);
    }
    
	public double handleSinglePixel(int pixel)
	{
//		int alpha = (pixel >> 24) & 0xff;
		int red = (pixel >> 16) & 0xff;
		int green = (pixel >> 8) & 0xff;
		int blue = (pixel) & 0xff;
		int pix = (red + green + blue) / 16;
//		System.out.println("Pix=" + pix + " A=" + alpha + " R=" + red + " G=" + green + " B=" + blue);
		return pix;
	}
}
