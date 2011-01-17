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


public final class FastFourierTransform
{

	private int	n, nu;

	// private int sampleSize;

	/**
	 * Constructor for the Fast Fourier Transform class.
	 */
	public FastFourierTransform()
	{
	}

	/**
	 * Method for converting the output of the
	 * sensor to a real byte array. The sensor
	 * uses char but tje interface outputs a byte
	 * array. This causes a problem with the sign
	 * bit because chars run from 0..255 and byte
	 * from -128..127. These need to be converted.
	 * So char value 0..128 is converted to the
	 * range -128..0 And char value 129..255 is
	 * converted to the range 0..127
	 */
	public static byte[] convertData(byte[] originalData) {		
		byte [] convertedData = new byte[originalData.length];
		
		// Used for getting rid of the sign bit.
		int [] intData = new int[originalData.length];
		
		for (int i = 0; i < originalData.length; i++) 
		{
			intData[i] = originalData[i] & 0xFF;
			convertedData[i] = (byte)(intData[i] - 128);
		}
		return convertedData;
	}

	private int bitrev(int j)
	{

		int j2;
		int j1 = j;
		int k = 0;
		for (int i = 1; i <= nu; i++)
		{
			j2 = j1 / 2;
			k = 2 * k + j1 - 2 * j2;
			j1 = j2;
		}
		return k;
	}
	
	/**
	 * Method for calculating the FFT of the array given as input
	 * 
	 * @param originalData Contains the data which will be analysed. The length must be a power of 2. 
	 */
	public final byte[] fftMag(byte[] originalData)
	{
		// assume n is a power of 2
		n = originalData.length;
		nu = (int) (Math.log(n) / Math.log(2));
		int n2 = n / 2;
		int nu1 = nu - 1;

		float[] realNumbers = new float[n];

		float[] imaginairNumbers = new float[n];
		
		// Contains the concatenation of real and imaginaire numbers
		float[] resultingVector = new float[n2];
		
		// Array used for returning the values in bytes
		// Calculation is converted to bytes because floats pretend
		// an accuracy that is not available in the Robot.
		byte[] fftValues = new byte[n2];
		
		float tr, ti, p, arg, c, s;
		// Initialize the original data in the real part and set the imaginair
		// numbers to zero. Prepare for calculation
		for (int i = 0; i < n; i++)
		{
			realNumbers[i] = originalData[i];
			imaginairNumbers[i] = 0.0f;
		}
		int k = 0;

		for (int l = 1; l <= nu; l++)
		{
			while (k < n)
			{
				for (int i = 1; i <= n2; i++)
				{
					p = bitrev(k >> nu1);
					arg = 2 * (float) Math.PI * p / n;
					c = (float) Math.cos(arg);
					s = (float) Math.sin(arg);
					tr = realNumbers[k + n2] * c + imaginairNumbers[k + n2] * s;
					ti = imaginairNumbers[k + n2] * c - realNumbers[k + n2] * s;
					realNumbers[k + n2] = realNumbers[k] - tr;
					imaginairNumbers[k + n2] = imaginairNumbers[k] - ti;
					realNumbers[k] += tr;
					imaginairNumbers[k] += ti;
					k++;
				}
				k += n2;
			}
			k = 0;
			nu1--;
			n2 = n2 / 2;
		}
		k = 0;
		int r;
		while (k < n)
		{
			r = bitrev(k);
			if (r > k)
			{
				tr = realNumbers[k];
				ti = imaginairNumbers[k];
				realNumbers[k] = realNumbers[r];
				imaginairNumbers[k] = imaginairNumbers[r];
				realNumbers[r] = tr;
				imaginairNumbers[r] = ti;
			}
			k++;
		}

		resultingVector[0] = (float) (Math.sqrt(realNumbers[0] * realNumbers[0] + imaginairNumbers[0] * imaginairNumbers[0])) / n;
		for (int i = 1; i < n / 2; i++) {
			resultingVector[i] = 2 * (float) (Math.sqrt(realNumbers[i] * realNumbers[i] + imaginairNumbers[i] * imaginairNumbers[i])) / n;
			// Conversion because this FFT method uses a precion that
			// is not possible in the robot
			fftValues[i] = (byte)resultingVector[i];
			
		}
		return fftValues;
	}
}
