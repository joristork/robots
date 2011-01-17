package javaBot.UVMMouse;
import com.muvium.apt.*;

public class ChaseBallBehavior extends Behavior {
	int s0 = 0;				//Drie variabelen voor vier sensoren
	int s1 = 0;
	int s2 = 0;

	public ChaseBallBehavior(JobotBaseController initJoBot,
			PeriodicTimer initServiceTick, int servicePeriod) {
		super(initJoBot, initServiceTick, servicePeriod);
	}

	/*
	 * doBehavior wordt continu uitgevoerd en opnieuw aangeroepen
	 */
	public void doBehavior() {

		int s0 = 0;
		int s1 = 0;
		int s2 = 0;
		int vx = 0;
		int vy = 0;
		int vz = 0;
		
		s0 = getJoBot().getSensor(0);
		s1 = getJoBot().getSensor(1);
		s2 = getJoBot().getSensor(2);
		
		if (s0 < 10 && s1 < 10 && s2 < 10) {
			vx = 0;
			vy = 0;
			vz = 50;
		}
		
		else {
			
			if (s0 > s1 && s0  > s2) {
				vx = 0;
				vy = -100;
				vz = 100;
			}
			
			if (s1 > s0 && s1  > s2)
			{
				vx = 100;
				vy = 0;
				vz = -100;
				
			}
			
			if (s2 > s0 && s2  > s1)
			{
				vx = -100;
				vy = 100;
				vz = 0;
			}
		}
		
		getJoBot().setStatusLeds(false, false, false);
		getJoBot().drive(vx, vy, vz);	
		}
}

