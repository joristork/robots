/*
 * Created on 22-feb-2006 Copyright: (c) 2006
 * Company: Dancing Bear Software
 * 
 * @version $Revision: 1.1 $
 */
package javaBot.tests;

import javaBot.FastFourierTransform;
import junit.framework.Assert;
import junit.framework.TestCase;

public class FastFourierTransformTest extends TestCase
{
	private FastFourierTransform	fft;
	private int						sampleRate;
	private int						highestPeak;

	public void setUp()
	{
		fft = new FastFourierTransform();
		// MUST BE power of 2 (128, 256, 512 etc.)
		// Precondition for FFT
		sampleRate = 128;
		// Must not exceed the maximum for bytes
		highestPeak = 127;
	}

	/*
	 * Will check if the FFT analysis went ok,
	 * searches for the peak at the correct
	 * location.
	 */
	private void checkFFTResult(int frequencyPeak, int highestPeak, byte[] outFFT)
	{
		// Frequency is 5 so this frequency must
		// have a peak and the rest must be small
		for (int i = 0; i < outFFT.length; i++)
		{
			if (i == frequencyPeak)
			{
				// Check the high spot
				Assert.assertEquals(highestPeak, outFFT[i], 1.0f);
			}
			else
			{
				// Values must be small
				Assert.assertEquals(0, outFFT[i], 1.0f);
			}
		}
	}

	/*
	 * Help method which makes it possible to
	 * construct a float array which represents a
	 * sinus signal.
	 */
	private byte[] constructSignal(int frequency, int highestPeak)
	{
		// Parameters used for constucting a sinus
		// signal
		int duration = 1;
		double RAD = 2.0 * Math.PI;

		// Construct buffer to store sample
		byte[] buf = new byte[(int) Math.round(sampleRate * duration)];
		// Construct the sinus with the specified
		// "frequency"
		for (int i = 0; i < buf.length; i++)
		{
			buf[i] = (byte) (Math.sin(RAD * (double) (frequency) / sampleRate * i) * highestPeak);
		}

		return buf;
	}

	/*
	 * Test method for
	 * 'javaBot.FastFourierTransform.fftMag(byte[])'
	 */
	public void testFftMag()
	{
		// sampleRate/2 because of Nyquist factor.
		// This is the maximum frequency
		// that can be extracted with 100%
		// assurance of given the samplerate.
		for (int frequency = 1; frequency <= (sampleRate / 2); frequency++)
		{
			// Get the FFT analysis
			byte[] inputSignal = constructSignal(frequency, highestPeak);
			byte[] outFFT = fft.fftMag(inputSignal);
			// Frequency peak must exist
			checkFFTResult(frequency, highestPeak, outFFT);
		}
	}

	/*
	 * Test method for
	 * 'javaBot.FastFourierTransform.convertData(byte[]
	 * originalData)' So char value 0..128 is
	 * converted to the range -128..0
	 */
	public void testConvertDataFrom0Untill128()
	{
		byte[] testData = new byte[128];
		byte[] convertedData;
		// Numbers from 0..127 are tested. They
		// must be changed to the
		// range -128..-1. This is because the
		// values are read as characters
		// from the sensor (so in range 0..255)
		// but received as byte (-128..127)
		for (int i = 0; i < testData.length; i++)
		{
			testData[i] = (byte) i;
		}
		convertedData = FastFourierTransform.convertData(testData);
		// Check if the converteddata is correct.
		// This means that all
		// values arre correctly converted.
		// Example:
		// Byte from sensor #00000010# = 2
		// Converted from sensor #00000010# = 2
		if (convertedData.length == testData.length)
		{
			for (int i = 0; i < testData.length; i++)
			{
				Assert.assertEquals((testData[i] - 128), convertedData[i]);
			}
		}
		else
		{
			Assert.assertTrue(false);
		}
	}

	/*
	 * Test method for
	 * 'javaBot.FastFourierTransform.convertData(byte[]
	 * originalData)'\ And char value 129..255 (is
	 * byte value -128..-1 is converted to the
	 * range 0..127
	 */
	public void testConvertDataFromMinus128Untill0()
	{
		byte[] testData = new byte[128];
		byte[] convertedData;
		// Numbers from -128..-1 are tested. They
		// must be changed to the
		// range 0..127. This is because the
		// values are read as characters
		// from the sensor (so in range 0..255)
		// but received as byte (-128..127)
		// Sign bit must be treated as 128 and not
		// as negative number indicator
		for (int i = 0; i < testData.length; i++)
		{
			testData[i] = (byte) (i - 128);
		}
		convertedData = FastFourierTransform.convertData(testData);
		// Check if the converteddata is correct.
		// This means that all
		// values arre correctly converted.
		// Example:
		// Byte from sensor #00000010# = 2
		// Converted from sensor #00000010# = 2
		if (convertedData.length == testData.length)
		{
			for (int i = 0; i < testData.length; i++)
			{
				Assert.assertEquals((testData[i] + 128), convertedData[i]);
			}
		}
		else
		{
			Assert.assertTrue(false);
		}
	}

	/*
	 * Used for checking the bit pattersn of the
	 * char and byte values
	 */
	private void printBits(byte pattern)
	{
		System.out.print(pattern + "#");
		int filter = 0x80;
		for (int i = 1; i <= 8; i++)
		{
			if ((pattern & filter) != 0)
			{
				System.out.print("1");
			}
			else
			{
				System.out.print("0");
			}
			filter >>= 1;
		}
		System.out.println("#");
	}
}
