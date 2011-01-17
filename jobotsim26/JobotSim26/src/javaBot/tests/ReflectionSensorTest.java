package javaBot.tests;
import javaBot.Location;
import javaBot.ReflectionSensor;

import junit.framework.TestCase;

public class ReflectionSensorTest extends TestCase {
	ReflectionSensor rs = new ReflectionSensor(new Location(0,0),0.0);
	
	public static void main(String[] args) {
//		rs.convertDistanceToValue(0.0);
//		rs.convertValueToDistance(0.0);
//		rs.getDistanceInCM(0);
	}

	public ReflectionSensorTest(String arg0) {
		super(arg0);
	}

	protected void setUp() throws Exception {
		super.setUp();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}
	
	public void testgetDistanceInCM(){
		String s = " 80 80 80 80 80 80 80 80 80 80 80 80 80 80 80 80 80 80 80 80 80 80 80 80 80 80 80 80 80 80 80 80 80 80 80 80 80 80 80 80 80 80 80 80 80 80 80 80 80 80 80 80 80 80 80 80 80 80 80 80 80 80 80 80 80 80 80 80 80 80 80 70 69 68 67 66 65 64 63 62 61 60 59 58 57 57 56 55 54 54 53 53 53 52 52 51 51 51 50 50 49 49 48 48 48 47 47 46 46 46 45 45 44 44 44 43 43 43 42 42 42 42 41 41 41 40 40 40 39 39 39 39 39 38 38 38 38 37 37 37 37 37 36 36 36 36 35 35 35 35 35 34 34 34 34 34 34 33 33 33 33 33 33 32 32 32 32 32 31 31 31 31 31 31 30 30 30 30 30 30 29 29 29 29 29 29 29 29 28 28 28 28 28 28 28 28 28 27 27 27 27 27 27 27 27 26 26 26 26 26 26 26 26 26 25 25 25 25 25 25 25 25 24 24 24 24 24 24 24 24 24 24 23 23 23 23 23 23 23 23 23 23 22 22 22 22 22 22 22 22 22 22 21 21 21 21 21 21 21 21 21 21 20 20 20 20 20 20 20 20 20 20 19 19 19 19 19 19 19 19 19 19 19 19 19 19 19 19 19 19 19 19 18 18 18 18 18 18 18 18 18 18 18 18 18 18 18 17 17 17 17 17 17 17 16 16 16 16 16 16 16 16 16 15 15 15 15 15 15 15 15 15 15 15 15 15 15 15 15 15 15 15 14 14 14 14 14 14 14 14 14 14 14 14 14 14 14 14 14 14 14 14 14 14 14 14 14 13 13 13 13 13 13 13 13 13 13 13 13 13 13 13 13 13 13 13 13 13 13 13 13 13 13 12 12 12 12 12 12 12 12 12 12 12 12 12 12 12 12 12 12 12 12 12 12 12 12 12 12 12 12 12 11 11 11 11 11 11 11 11 11 11 11 11 11 11 11 11 11 11 11 11 11 11 11 11 11 11 11 11 11 11 10 10 10 10 10 10 10 10 10 10 10 10 10 10 10 10 10 10 10 10 10 10 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 8 8 8 7 7 7";
		System.out.println(s.length());
		StringBuffer sb= new StringBuffer(s.length()+100);
		//System.out.println(ReflectionSensor.getDistanceInCM(507));
		for(int i = -1; i<510; i++){
			sb.append(" "+rs.getDistanceInCM(i));
			//System.out.println(" " + ReflectionSensor.getDistanceInCM(i));
		}
		assertEquals(s,sb.toString());
	}
	/* used for manual testing 
	public void testdumpTable(){
		for(int i = 0; i<510; i++){
			System.out.println(i+" " + ReflectionSensor.getDistanceInCM(i));
		}
		for(int i = 0; i<100; i+=2){
			System.out.println(i+" " + ReflectionSensor.convertDistanceToValue(i/100.0)); //value in meters
		}
	}
	/**/
}
