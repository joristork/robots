package javaBot.tests;

import javaBot.UVMRescue.JobotBaseController;
import junit.framework.TestCase;
import junit.framework.Assert;

public class FieldSensorTest extends TestCase {
	JobotBaseController bc = new JobotBaseController();
	
	int[] calColor = new int[] {80,200,500,800};

	public void testGetCalibratedValue() {
		int aColor;
		aColor = bc.getCalibratedValue(bc.BLACK);
		Assert.assertEquals(aColor, calColor[bc.BLACK]);
		aColor = bc.getCalibratedValue(bc.GREEN);
		Assert.assertEquals(aColor, calColor[bc.GREEN]);
		aColor = bc.getCalibratedValue(bc.YELLOW);
		Assert.assertEquals(aColor, calColor[bc.YELLOW]);
		aColor = bc.getCalibratedValue(bc.WHITE);
		Assert.assertEquals(aColor, calColor[bc.WHITE]);
	}
	
	public void testSetCalibratedValue() {
		int aColor;
		bc.setCalibratedValue(bc.BLACK, 100);
		aColor = bc.getCalibratedValue(bc.BLACK);
		Assert.assertEquals(aColor, 100);
		bc.setCalibratedValue(bc.BLACK, bc.BLACK);	}

	public void testGetColor() {
		int theColor=0;
		// UNKNOWN < 0
		theColor = bc.getColor(-100);
		Assert.assertEquals("getColor -100=UNKNOWN", -1, theColor);
		
		// BLACK 80
		theColor = bc.getColor(0);
		Assert.assertEquals("getColor 0=BLACK", bc.BLACK, theColor);
		theColor = bc.getColor(80);
		Assert.assertEquals("getColor 80=BLACK", bc.BLACK, theColor);
		theColor = bc.getColor(179);
		Assert.assertEquals("getColor 179=BLACK", bc.BLACK, theColor);
		
		// GREEN 200
		theColor = bc.getColor(180);
		Assert.assertEquals("getColor 180=GREEN", bc.GREEN, theColor);
		theColor = bc.getColor(200);
		Assert.assertEquals("getColor 200=GREEN", bc.GREEN, theColor);
		theColor = bc.getColor(479);
		Assert.assertEquals("getColor 479=GREEN", bc.GREEN, theColor);
		
		// YELLOW 500
		theColor = bc.getColor(480);
		Assert.assertEquals("getColor 480=YELLOW", bc.YELLOW, theColor);
		theColor = bc.getColor(500);
		Assert.assertEquals("getColor 500=YELLOW", bc.YELLOW, theColor);
		theColor = bc.getColor(779);
		Assert.assertEquals("getColor 779=YELLOW", bc.YELLOW, theColor);
		
		// WHITE 800
		theColor = bc.getColor(780);
		Assert.assertEquals("getColor 780=WHITE", bc.WHITE, theColor);
		theColor = bc.getColor(800);
		Assert.assertEquals("getColor 800=WHITE", bc.WHITE, theColor);
		theColor = bc.getColor(1023);
		Assert.assertEquals("getColor 1023=WHITE", bc.WHITE, theColor);
	}

	public void testIsColor() {
		boolean isColor=false;
		// BLACK
		isColor = bc.isColor(800, bc.BLACK);
		Assert.assertEquals("IsColor 800=BLACK", false, isColor);
		isColor = bc.isColor(0, bc.BLACK);
		Assert.assertEquals("IsColor 0=BLACK", true, isColor);
		
		// GREEN
		isColor = bc.isColor(80, bc.GREEN);
		Assert.assertEquals("IsColor 80=GREEN", false, isColor);
		isColor = bc.isColor(200, bc.GREEN);
		Assert.assertEquals("IsColor 200=GREEN", true, isColor);
		
		// YELLOW
		isColor = bc.isColor(80, bc.YELLOW);
		Assert.assertEquals("IsColor 80=YELLOW", false, isColor);
		isColor = bc.isColor(500, bc.YELLOW);
		Assert.assertEquals("IsColor 500=YELLOW", true, isColor);
		
		// WHITE
		isColor = bc.isColor(80, bc.WHITE);
		Assert.assertEquals("IsColor 80=WHITE", false, isColor);
		isColor = bc.isColor(800, bc.WHITE);
		Assert.assertEquals("IsColor 800=WHITE", true, isColor);
	}

	public void testGetColorName() {
		String aColor;
		aColor = bc.getColorName(-1);
		Assert.assertEquals("-1=Unknown", "Unknown", aColor);
		aColor = bc.getColorName(0);
		Assert.assertEquals("0=Black", "Black", aColor);
		aColor = bc.getColorName(80);
		Assert.assertEquals("80=Black", "Black", aColor);
		aColor = bc.getColorName(179);
		Assert.assertEquals("179=Black", "Black", aColor);
		aColor = bc.getColorName(200);
		Assert.assertEquals("200=Green", "Green", aColor);
		aColor = bc.getColorName(479);
		Assert.assertEquals("479=Green", "Green", aColor);
		aColor = bc.getColorName(500);
		Assert.assertEquals("500=Yellow", "Yellow", aColor);
		aColor = bc.getColorName(779);
		Assert.assertEquals("779=Yellow", "Yellow", aColor);
		aColor = bc.getColorName(800);
		Assert.assertEquals("800=White", "White", aColor);
		aColor = bc.getColorName(1023);
		Assert.assertEquals("1023=White", "White", aColor);
	}
	public void testAbs() {
		int val;
		val = bc.abs(20);
		Assert.assertEquals(20, val);
		val = bc.abs(-20);
		Assert.assertEquals(20, val);		
	}

	public void testMax() {
		int val;
		val = bc.max(10, 20);
		Assert.assertEquals(20, 20);
		val = bc.max(20, 10);
		Assert.assertEquals(20, 20);
	}

}
