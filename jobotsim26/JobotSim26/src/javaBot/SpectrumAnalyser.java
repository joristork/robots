package javaBot;

// Credits:
//    Nimrod Raiman, UvA
//	  Maarten Leeuwrik, UvA

//import com.muvium.net.socketImpls.SlipSocketFactory;
//import com.muvium.web.services.protocols.WebRequest;


public class SpectrumAnalyser {
	
	private short counter = 0;		// Count-down for when a word starts.
									// Word-checking is only done when
									// particular conditions are met.	
	private short time = 4;			// Number of cycles you keep detecting
									// after having found a beginning.
	
	private short ambient = 0;		// Average background noise. Actual value
									// yet to be determined.
	private short treshhold = 15;	// Height of spectrum peak that actually
									// registeres as a sound spike. Must be
									// above ambient.
	private short correction = 2;	// Number of 'steps' a peak may be off center
									// to still be recognised. (A peak at 22, for
									// example, could be cleared as a peak at 23)
	
	
	
	private byte[] current_spectrum;	// Global array holding last passed
										// spectrum.
	private int[][] current_peaks;		// Global array holding information
										// on where peaks are, their size, etc.
	

	public SpectrumAnalyser() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	/*
	 * The update(byte[]) function carries out all important bits and pieces
	 * of this module. You pass it an array holding a spectrum of a slice of
	 * sound; the result from an FFT algorithm. This function itself will
	 * analyse this, to try and recognise vowels.
	 * 
	 * The result of this function can either be function calls to send a robot
	 * into specific behaviours, or be returned to a variable. This depends
	 * entirely on the context in which it will be used, and has as such not
	 * yet been implemented.
	 */

	
	public void update(byte[] spectrum)
	{
		current_spectrum = spectrum;			// Keep a local copy
		current_peaks = analyse(spectrum);		// Find all the peaks
		
		/* 
		 * This first bit is some code to check things out. It prints
		 * all peaks that register above treshhold. We used this to
		 * check for the characteristics of particular vowels.
		 */
		
		for (int i = 0; i < current_peaks.length; i++)
		{
			if (current_peaks[i][1] > treshhold)
			{
				System.out.print(current_peaks[i][0]);
				System.out.print(", ");
				System.out.print(current_peaks[i][1]);
				System.out.print(" <=> ");
			}
		}
		
		System.out.println(" \n");
		
		/*
		 * This bit of code is there to test the system. For the given
		 * spectrum, it tries to see if the results match any of the
		 * vowels. Yes, it can indeed return multiple possible vowels
		 * for any given spectrum; this is code just to see how
		 * accuratly things are recognised.
		 */
		
		
		if (vowel_a())
			System.out.println("A");
		if (vowel_o())
			System.out.println("O");
		if (vowel_e())
			System.out.println("E");
		if (vowel_u())
			System.out.println("U");
		if (vowel_i())
			System.out.println("I");
		if (whistle())
			System.out.println("FLUIT!");
		
		System.out.println(" ");
		
		/*
		 * This is the actual program code as we intended it to be,
		 * but couldn't make it work. It first checks to see if there
		 * is either a plosive (p, t) or a fricative (s). If so, there
		 * is a good chance that someone tries to say 'start' or 'stop'.
		 * 
		 * The counter is started, so that for the next T cycles, it
		 * tries to recognise a vowel. If an 'a' closely follows, it is
		 * likely to be 'start'. If an 'o' closely follows, it is likely
		 * to be 'stop'.
		 * 
		 * Currently, this code is commented out, because the system we
		 * use isn't fast enough to detect a plosive or fricative, and then
		 * the vowel in the same word. You need plenty of updates per second
		 * in order to catch these; at our current 2 times per second, it is
		 * not possible. Still, if someone can speed things up, here is the
		 * code that should automaticly detect start and stop!
		 */
		
		/*
		if (plosive() || fricative())	// If there is an 's', 't' or 'p'
		{
			counter = time;				// Start countdown
		}
		
		if (counter > 0)				// Still close enough to a plos/fric?	
		{
			counter--;					// Decrease counter.
			
			boolean a = vowel_a();		// Decide if there is an 'a' or 'o'
			boolean o = vowel_o();
			
			if (a && !o)				// If and only if there is an 'a'
			{
				// TO-DO: React to 'Start'
			}
			else if (!a && o)			// If and only if there is an 'o'
			{
				// TO-DO: React to 'Stop'
			}
		}
		
		*/
	}
	
	/*
	 * The vowel-recognition functions. A vowel can best be recognised
	 * by their characteristical peaks around certain points. Currently
	 * these functions only check for three important peaks; if they are
	 * present, there is a succesful match on the vowel.
	 * 
	 * Future improvement, if there is solid data on which peaks belongs
	 * to which vowels, could include determining if there aren't peaks
	 * around the other vowels, so you could make more accurate estimations
	 * about which vowel is actually being pronounced.
	 */
	
	private boolean vowel_a()
	{
		if (peak_around(24) && peak_around(35) && peak_around(46))
			return true;
		return false;
	}
	
	private boolean vowel_e()
	{
		if (peak_around(24) && peak_around(46) && peak_around(59))
			return true;
		return false;
	}
	
	private boolean vowel_i()
	{
		if (peak_around(24) && peak_around(49) && peak_around(54))
			return true;
		return false;
	}
	
	private boolean vowel_o()
	{
		if (peak_around(24) && peak_around(37) && peak_around(57))
			return true;
		return false;
	}
	
	private boolean vowel_u()
	{
		if (peak_around(24) && peak_around(47) && peak_around(58))
			return true;
		return false;
	}
	
	private boolean whistle()
	{
		if (peak_around(33))
			return true;
		return false;
	}
	
	/*
	 * A simple functions that checks to see if there is indeed a peak
	 * around a given value. Usually there are only four or five peaks,
	 * stored in the 'current_peaks' variable. The amount that a peak
	 * might be 'off' is determined by the 'correction' variable. The
	 * height of the peak that is required for a match is determined by
	 * treshhold.
	 */
	
	private boolean peak_around(int location)
	{
		for (int i = 0; i < current_peaks.length; i++)
		{
			// See if peak is above treshhold, and in right location
			if ((current_peaks[i][0] > location - correction) &&
				(current_peaks[i][0] < location + correction) &&
				(current_peaks[i][1] > treshhold))
				return true;
		}
		return false;
	}
	
	/*
	 * Plosive recognition is not yet possible to implement, due to
	 * lack of fast enough updating. A plosive is a very short burst
	 * of sound, requiring a high resolution (fast updates on the
	 * sound signal).
	 */
	
	private boolean plosive()
	{
		// TO-DO
		return false;
	}
	
	/*
	 * A fricative, in general, is easy to recognise. It is a lot of
	 * noise on a broad spectrum; meaning that there should be plenty
	 * of entries in the spectrum that breaches the treshhold.
	 * 
	 * Depending on which fricative you are listening to, the first few
	 * entries should be below trenshhold, and the rest should all be
	 * above. This function is more-or-less roughly tuned to respond to
	 * an 's', by placing the cutoff value at 1/4'th of the width of the
	 * spectrum.
	 * 
	 * If, on average, the entries before are below treshhold, and behind
	 * are above treshhold, it is likely to be an 's'.
	 */
	
	private boolean fricative()
	{
	
		
		int l = current_spectrum.length;		// Get length of spectrum
		int cutoff = l / 4;						// Determine which bit should be
												// below treshhold. May be rounded
												// up or down.
		int balance = 0;						// Tracker variable for result.
		
		
		/*
		 * While below the cutoff value, give a balance point if an
		 * entry of the spectrum is below treshhold.
		 */
		
		for (int i = 0; i < cutoff; i++)
		{
			if(current_spectrum[i] < treshhold)
				balance++;
			//else
				//balance--;
		}
		
		/*
		 * Above the cutoff value, give a balance point if an entry of the
		 * spectrum is above treshhold.
		 */

		for (int i = cutoff; i < l; i++)
		{
			if(current_spectrum[i] > treshhold)
				balance++;
			//else
				//balance--;		
		}
		
		/*
		 * Check the balance. Currently, the balance is set so that
		 * if half of the entries in the list conforms to the image
		 * of a fricative, there is a match. By increasing the required
		 * value of 'balance', you increase the chance that a match is
		 * indeed a fricative; but you also increase the chance that a
		 * fricative is discarded. You get to pick; quality or quantity?
		 */
		
		if (balance > l / 2)
		{
			return true;		// Probably an 's'
		}
		else
			return false;		// Probably not
	}
	
	/*
	 * This function turns the data from the spectrum into a two-dimensional
	 * array that holds the information on all the important peaks. A peak
	 * is a peak when the entries to the left and right are both lower
	 * than the entry being tested.
	 */
	
	
	private int[][] analyse(byte[] timmy)
	{
		//---------------------------------------
		int een;
		int twee;
		int drie;
		int[][] pieken = new int[64][5];
//		int[] piek = new int[64];
//		int[] links_rechts = new int[64];
		int gem_links_rechts;
		int tellertje =0;
		int hoogteverschil;
//		int hvlinks; // Height difference with left
//		int hvrechts; // Height difference with right
		int vorige_piek = 0;
		
		for (int a = 1; a < (timmy.length-1); a++)
		{
			een = timmy[a-1];
   			twee = timmy[a];
   			drie = timmy[a+1];
   			gem_links_rechts = (een+drie)/2;
   			
   			                 if (twee>een && twee>drie)
   			                 {
   			         			hoogteverschil = twee-gem_links_rechts;
   			        			//hvlinks = twee-een;
   			        			//hvrechts = twee-drie;

   			        		//	System.out.print("\nteller:   ");
   			        		///	System.out.print(tellertje + "  locatie" + timmy[a]);
		                		pieken[tellertje][0] = a;
		                		pieken[tellertje][1] = (int)timmy[a];
		                		pieken[tellertje][2] = hoogteverschil;
		                		pieken[tellertje][3] = a-vorige_piek;
		                		vorige_piek = a;
		                		tellertje++;
   			                 }
		}

		//System.out.print("-------nieuwe plot--------\n");

		for(int b=0; b<=tellertje; b++)
		{
			if(pieken[b][1]!=0){
//				System.out.print("piek op: ");
//				System.out.print(pieken[b][0]);
//				System.out.print("\nsterkte piek: ");
//				System.out.print(pieken[b][1]);
//				System.out.print("\nhoogteverschil: ");
//				System.out.print(pieken[b][2]);
//				System.out.print("\nafstand vorige piek: ");
//				System.out.print(pieken[b][3]);
//				System.out.print("\n");
				//System.out.print("[" + pieken[b][0] + ", " + pieken[b][1] + ", "+ pieken[b][2] + ", "+ pieken[b][3] + "]\n");
				
			}
		}
		
		tellertje=0;
/*		for (int a = 0; a < (pieken.length-1); a++)
		{
			pieken[a][0] = 0;
			pieken[a][1] = 0;
			pieken[a][2] = 0;
			pieken[a][3] = 0;
			pieken[a][4] = 0;
		}*/
		
		
		//---------------------------------------
	
		
		return pieken;

	}

}
