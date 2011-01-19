//---------------------------------------------------------------------
//
//							 Software License Agreement
//
// The software supplied herewith by Microchip Technology Incorporated 
// (the “Company”) for its PIC® Microcontroller is intended and 
// supplied to you, the Company’s customer, for use solely and 
// exclusively on Microchip PIC Microcontroller products. The 
// software is owned by the Company and/or its supplier, and is 
// protected under applicable copyright laws. All rights are reserved. 
//  Any use in violation of the foregoing restrictions may subject the 
// user to criminal sanctions under applicable laws, as well as to 
// civil liability for the breach of the terms and conditions of this 
// license.
//
// THIS SOFTWARE IS PROVIDED IN AN “AS IS” CONDITION. NO WARRANTIES, 
// WHETHER EXPRESS, IMPLIED OR STATUTORY, INCLUDING, BUT NOT LIMITED 
// TO, IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A 
// PARTICULAR PURPOSE APPLY TO THIS SOFTWARE. THE COMPANY SHALL NOT, 
// IN ANY CIRCUMSTANCES, BE LIABLE FOR SPECIAL, INCIDENTAL OR 
// CONSEQUENTIAL DAMAGES, FOR ANY REASON WHATSOEVER.
//
//---------------------------------------------------------------------
//	File:		18motr2.c
//
//	Written By:		Stephen Bowling, Microchip Technology
//						
// This code implements a brush-DC servomotor using the PIC18C452 MCU.
// The code was compiled using the MPLAB-C18 compliler ver. 1.10
// The device frequency should be 20 MHz.
//
// The following files should be included in the MPLAB project:
//
//		18motr2.c		-- Main source code file
//		p18c452.lkr		-- Linker script file
//				
//		The following project files are included by the linker script:
//
//		c018i.o			-- C startup code
//		clib.lib			-- Math and function libraries
//		p18c452.lib		-- Processor library
//
//---------------------------------------------------------------------
//
// Revision History
//
// 2/15/02 -- PID compensator calculation modified to support 
// V2.00 of the MPLAB C-18 compiler.
// PID integral is now 32-bits to prevent integral overflow.
//
//---------------------------------------------------------------------

#include	<p18c452.h>					// Register definitions
#include <stdlib.h>
#include <string.h>
#include <i2c.h>						// I2C library functions
#include <pwm.h>						// PWM library functions
#include <adc.h>						// ADC library functions
#include	<portb.h>					// PORTB library function
#include <timers.h>					// Timer library functions

//---------------------------------------------------------------------
//Constant Definitions
//---------------------------------------------------------------------

#define	DIST	0						// Array index for segment distance
#define	VEL	1						// Array index for segment vel. limit
#define	ACCEL	2						// Array index for segment accel.
#define	TIME	3						// Array index for segment delay time
#define	INDEX	PORTBbits.RB0		// Input for encoder index pulse
#define	NLIM	PORTBbits.RB1		// Input for negative limit switch
#define	PLIM	PORTBbits.RB2		// Input for positive limit switch
#define	GPI	PORTBbits.RB3		// General purpose input
#define	MODE1	!PORTBbits.RB4		// DIP switch #1
#define	MODE2	!PORTBbits.RB5		// DIP switch #2
#define	MODE3	!PORTBbits.RB6		// DIP switch #3
#define	MODE4	!PORTBbits.RB7		// DIP switch #4
#define	SPULSE	PORTCbits.RC5	// Software timing pulse output
#define	ADRES	ADRESH				// Redefine for 10-bit A/D converter

//---------------------------------------------------------------------
// Variable declarations
//---------------------------------------------------------------------

const rom char ready[] = "\n\rREADY>";
const rom char badcmd[] = "\n\rERROR!";

char inpbuf[8];							// Input command buffer

unsigned char	
eeadr,										// Pointer to EEPROM address
firstseg,									// First segment of motion profile
lastseg,										// Last segment of motion profile
segnum,										// Current executing segment
parameter,									// Index to profile data
i,												// index to ASCII buffer
comcount,									// index to input string									
udata 										// Received character from USART
;

struct {										// Holds status bits for servo
    unsigned            phase:1;		// Current phase of motion profile
    unsigned         neg_move:1;		// Backwards relative move
    unsigned 			  motion:1;		// Segment execution in progress
    unsigned 		  saturated:1;		// PWM output is saturated
    unsigned         	 bit4:1;
    unsigned 			    bit5:1;		
    unsigned              run:1;		// Enables execution of profile
    unsigned             loop:1;		// Executes profile repeatedly
} stat ;

int
dtime,										// Motion segment delay time
kp,ki,kd,									// PID gain constants
vlim, 										// Velocity limit for segment
mvelocity,									// Measured motor velocity
DnCount,										// Holds accumulated 'up' pulses
UpCount										// Holds accumulated 'down' pulses
;

union LNG									
{
long l;
unsigned long ul;
int i[2];
unsigned int ui[2];
char b[4];
unsigned char ub[4];
};

union LNG
temp,							// Temporary storage
accel,						// Segment acceleration value
error,							// PID error value
integral,               // PID integral value
ypid,							// Holds output of PID calculation
velact,						// Current commanded velocity
phase1dist					// Half of segment distance
;

long              
position,      	   	// Commanded position.
mposition,					// Actual measured position.
fposition,					// Originally commanded position.
flatcount;     		   // Holds the number of sample periods for which 
							   // the velocity limit was reached in the first 
							   // half of the move.  


#pragma udata segdata1 = 0x0100

int segment1[12][4];		// Holds motion segment values in data memory.
int segment2[12][4];		// 						"


#pragma udata

//---------------------------------------------------------------------
// Function Prototypes
//---------------------------------------------------------------------

void servo_isr(void);		// Does servo calculations
void isrhandler(void);		// Located at high interrupt vector

void DoCommand(void);		// Processes command input strings
void Setup(void);				// Configures peripherals and variables
void UpdPos(void);			// Gets new measured position for motor
void CalcError(void);		// Calculates position error
void CalcPID(void);			// Calculates new PWM duty cycle
void UpdTraj(void);			// Calculates new commanded position
void SetupMove(void);		// Gets new parameters for motion profile

// Writes a string from ROM to the USART
void putrsUSART(const rom char *data);

// ExtEEWrite and ExtEERead are used to read or write an integer value to the
// 24C01 EEPROM
																		
void ExtEEWrite(unsigned char address, int data);
int ExtEERead(unsigned char address);


//---------------------------------------------------------------------
// Interrupt Code
//---------------------------------------------------------------------

// Designate servo_isr as an interrupt function and save key registers

#pragma interrupt servo_isr save = PRODL,PRODH,temp

// Locate ISR handler code at interrupt vector

#pragma code isrcode=0x0008

void isrhandler(void)	// This function directs execution to the
{								// actual interrupt code										
_asm
goto servo_isr
_endasm
}


#pragma code

//---------------------------------------------------------------------
// servo_isr()
// Performs the servo calculations
//---------------------------------------------------------------------

void servo_isr(void)
{
SPULSE = 1;								// Toggle output pin for ISR code
											// timing
UpdTraj();								// Get new commanded position
UpdPos();								// Get new measured position
CalcError();							// Calculate new position error
CalcPID();

PIR1bits.TMR2IF = 0;					// Clear Timer2 Interrupt Flag.

SPULSE = 0;								// Toggle output pin for ISR code
}											// timing

//---------------------------------------------------------------------
// UpdTraj()
//	Computes the next required value for the next commanded motor
// position based on the current motion profile variables.  Trapezoidal
// motion profiles are produced.
//---------------------------------------------------------------------

void UpdTraj(void)
{

if(stat.motion && !stat.saturated)
	{
   if(!stat.phase)					// If in the first half of the move.
      {
      if(velact.ui[1] < vlim)	   // If still below the velocity limit
         velact.ul += accel.ul;  // Accelerate
         
                  
      else                      	// If velocity limit has been reached,
         flatcount++;				// increment flatcount.
      	           
      temp.ul = velact.ul;			// Put velocity value into temp
      									// and round to 16 bits
      if(velact.ui[0] == 0x8000)
      	{
      	if(!(velact.ub[2] & 0x01))
      		temp.ui[1]++;
      	}
      else
      
      if(velact.ui[0] > 0x8000) temp.ui[1]++;
                 
      phase1dist.ul -= (unsigned long)temp.ui[1];
   
      if(stat.neg_move)
      	position -= (unsigned long)temp.ui[1];
      else
         position += (unsigned long)temp.ui[1];
   
      if(phase1dist.l <= 0)		// If phase1dist is negative 
         								// first half of the move has 
      	stat.phase = 1;			// completed.
  	   }

   else									// If in the second half of the move,
      {									// Decrement flatcount if not 0.
      if(flatcount) flatcount--;
		
		else
		if(velact.ul)					// If commanded velocity not 0,
			{
			velact.ul -= accel.ul;	// Decelerate
						
			if(velact.i[1] < 0) 
				velact.l = 0;
			}
							
		else								// else								
			{
			if(dtime) dtime--;		// Decrement delay time if not 0.
			else
				{
				stat.motion = 0;		// Move is done, clear motion flag
				position = fposition;
				}
			}
	   	   
      temp.ul = velact.ul;			// Put velocity value into temp
      									// and round to 16 bits
      if(velact.ui[0] == 0x8000)
      	{
      	if(!(velact.ub[2] & 0x01))
      		temp.ui[1]++;
      	}
        
      else
      
      if(velact.ui[0] > 0x8000) temp.ui[1]++;
            
      if(stat.neg_move)				// Update commanded position
        	position -= (unsigned long)temp.ui[1];
      else
      	position += (unsigned long)temp.ui[1];
      }

	}										// END if (stat.motion)

else
	{
	if(stat.run && !stat.motion)	// If motion stopped and profile 
		{									// running, get next segment number
		if(segnum < firstseg) segnum = firstseg;
		if(segnum > lastseg)
			{	
			segnum = firstseg;		// Clear run flag if loop flag not set.
			if(!stat.loop) stat.run = 0;
			}
		else
			{
			SetupMove();				// Get data for next motion segment.
			segnum++;					// Increment segment number.
			}
		}	
	}
}

//---------------------------------------------------------------------
// SetupMove()
// Gets data for next motion segment to be executed
//---------------------------------------------------------------------

void SetupMove(void)
{
if(segnum < 12)								// Get profile segment data from
	{												// data memory.
	phase1dist.i[0] = segment1[segnum][DIST];
	vlim = segment1[segnum][VEL];
	accel.i[0] = segment1[segnum][ACCEL];
	dtime = segment1[segnum][TIME];
	}
else if(segnum < 24)
	{
	phase1dist.i[0] = segment2[segnum - 12][DIST];
	vlim = segment2[segnum - 12][VEL];
	accel.i[0] = segment2[segnum - 12][ACCEL];
	dtime = segment2[segnum - 12][TIME];
	}


phase1dist.b[2] = phase1dist.b[1];		// Rotate phase1dist one byte
phase1dist.b[1] = phase1dist.b[0];		// to the left.
phase1dist.b[0] = 0;
if(phase1dist.b[2] & 0x80) 				// Sign-extend value
   phase1dist.b[3] = 0xff;
else 
   phase1dist.b[3] = 0;

accel.b[3] = 0;								// Rotate accel one byte to 
accel.b[2] = accel.b[1];					// the left
accel.b[1] = accel.b[0];
accel.b[0] = 0;

temp.l = position;

if(temp.ub[0] > 0x7f)						// A fractional value is left 
	temp.l += 0x100;							// over in the 8 LSbits of 
temp.ub[0] = 0;								// position, so round position
													// variable to an integer value
position = temp.l;							// before computing final move
													// position.
fposition = position + phase1dist.l;	// Compute final position for 
   												// the move
if(phase1dist.b[3] & 0x80)					// If the move is negative,   
   {
   stat.neg_move = 1;						// Set flag to indicate negative 
   phase1dist.l = -phase1dist.l;			// move.
   }
else stat.neg_move = 0;						// Clear flag for positive move

phase1dist.l >>= 1;							// phase1dist holds total 
   							          		// move distance, so divide by 2
velact.l = 0;                          // Clear commanded velocity
flatcount = 0;									// Clear flatcount
stat.phase = 0;								// Clear flag:first half of move
if(accel.l && vlim)
stat.motion = 1;								// Enable motion
}

//---------------------------------------------------------------------
// UpdPos()
// Gets the new measured position of the motor based on values 
// accumulated in Timer0 and Timer1
//---------------------------------------------------------------------


void UpdPos(void)
{
// Old timer values are presently stored in UpCount and DnCount, so
// add them into result now.

mvelocity = DnCount;							
mvelocity -= UpCount;

//  Write new timer values into UpCount and DnCount variables.

UpCount = ReadTimer0();
DnCount = ReadTimer1();

// Add new count values into result.

mvelocity += UpCount;
mvelocity -= DnCount;

// Add measured velocity to measured position to get new motor
// measured position.

mposition += (long)mvelocity << 8;
}

//---------------------------------------------------------------------
// CalcError()
// Calculates position error and limits to 16 bit result
//---------------------------------------------------------------------

void CalcError(void)
{
temp.l = position;							// Put commanded pos. in temp
temp.b[0] = 0;									// Mask out fractional bits
error.l = temp.l - mposition;					// Get error
error.b[0] = error.b[1];							// from desired position and
error.b[1] = error.b[2];							// shift to the right to discard
error.b[2] = error.b[3];							// lower 8 bits.

if (error.b[2] & 0x80)							// If error is negative.
	{
	error.b[3] = 0xff;							// Sign-extend to 32 bits.

	if(error.l < -32768)
		error.l = -32768;						// Limit error to 16-bits.
	}

else												// If error is positive.
	{
	error.b[3] = 0x00;

	if(error.l > 32767)
		error.l = 32767;						// Limit error to 16-bits.
	}
}

//---------------------------------------------------------------------
// CalcPID()
// Calculates PID compensator algorithm and determines new value for
// PWM duty cycle
//---------------------------------------------------------------------

void CalcPID(void)
{
if(!stat.saturated)	// If output is not saturated,
   integral.l += error.l;  // modify the integral value.
   	

// Calculate the PID compensator.

ypid.l = (long)error.i[0]*(long)kp + integral.l*(long)ki + 
         (long)mvelocity*(long)kd;					


if(ypid.ub[3] & 0x80)					// If PID result is negative
	{
	if((ypid.ub[3] < 0xff) || !(ypid.ub[2] & 0x80))
		ypid.ul = 0xff800000;			// Limit result to 24-bit value
	}

else											// If PID result is positive
	{
	if(ypid.ub[3] || (ypid.ub[2] > 0x7f))
		ypid.ul = 0x007fffff;			// Limit result to 24-bit value
	}
	
ypid.b[0] = ypid.b[1];					// Shift PID result right to 
ypid.b[1] = ypid.b[2];					// get upper 16 bits.

stat.saturated = 0;						// Clear saturation flag and see
if(ypid.i[0] > 500)						// if present duty cycle output
	{											// exceeds limits.
	ypid.i[0] = 500;
	stat.saturated = 1;
	}
	
if(ypid.i[0] < -500)
	{
	ypid.i[0] = -500;
	stat.saturated = 1;
	}

ypid.i[0] += 512;							// Add offset to get positive 
												// duty cycle value.
												
SetDCPWM1(ypid.i[0]);					// Write the new duty cycle.
}


//---------------------------------------------------------------------
//	Setup() initializes program variables and peripheral registers
//---------------------------------------------------------------------

void Setup(void)
{
firstseg = 0;							// Initialize motion segment
lastseg = 0;							// variables
segnum = 0;
parameter = 0;							// Motion segment parameter#
i = 0;									// Receive buffer index
comcount = 0;							// Input command index
udata = 0;								// Holds USART received data

stat.phase = 0;						// Set flags to 0.
stat.saturated = 0;
stat.motion = 0;
stat.run = 0;
stat.loop = 0;
stat.neg_move = 0;
dtime = 0;
integral.l = 0;
vlim = 0;
mvelocity = 0;
DnCount = 0;
UpCount = 0;
temp.l = 0;
accel.l = 0;
error.l = 0;
ypid.l = 0;
velact.l = 0;
phase1dist.l = 0;
position = 0;
mposition = 0;
fposition = 0;
flatcount = 0;
udata = 0;
memset(inpbuf,0,8);		   // clear the input buffer

// Setup A/D converter
OpenADC(ADC_FOSC_32 & ADC_LEFT_JUST & ADC_1ANA_0REF,
			ADC_CH0 & ADC_INT_OFF);

OpenPWM1(0xff);				// Setup Timer2, CCP1 to provide
									//	19.53 Khz PWM @ 20MHz

OpenTimer2(T2_PS_1_1 & T2_POST_1_10 & TIMER_INT_ON);

SetDCPWM1(512);				// 50% initial duty cycle

EnablePullups();           // Enable PORTB pullups
PORTC = 0;						// Clear PORTC
PORTD = 0;						//	Clear PORTD
PORTE = 0x00;					//	Clear PORTD
TRISC = 0xdb;					// 
TRISD = 0;						// PORTD all outputs.
TRISE = 0;						// PORTE all outputs.

// Setup the USART for 19200 baud @ 20MHz

SPBRG = 15;						// 19200 baud @ 20MHz
TXSTA = 0x20;					// setup USART transmit
RCSTA = 0x90;					// setup USART receive

putrsUSART("\r\nPIC18C452 DC Servomotor");
putrsUSART(ready);

OpenI2C(MASTER,SLEW_OFF);	// Setup MSSP for master I2C
SSPADD = 49;       			// 100KHz @ 20MHz

kp = ExtEERead(122);			// Get PID gain constants
ki = 0;							// from data EEPROM
kd = ExtEERead(126);

TMR0L = 0;						// Clear timers.
TMR0H = 0;
TMR1L = 0;
TMR1H = 0;

OpenTimer0(TIMER_INT_OFF & T0_16BIT & TIMER_INT_OFF & T0_EDGE_RISE &
				T0_SOURCE_EXT & T0_PS_1_1);
OpenTimer1(TIMER_INT_OFF & T1_SOURCE_EXT & T1_16BIT_RW & TIMER_INT_OFF
				& T1_PS_1_1 & T1_SYNC_EXT_ON & T1_OSC1EN_OFF );

// Load motion profile data for segments 1 through 12 from
// data EEPROM

for(segnum=0;segnum < 12;segnum++)              
	{
	for(parameter=0;parameter < 4;parameter++)
		{
		eeadr = (segnum << 3) + (parameter << 1);
		segment1[segnum][parameter] = ExtEERead(eeadr);
		}
	}

segment2[0][DIST] = 29500;			// Motion profile data for segments
segment2[0][VEL] = 4096;			// 13 through 24 are loaded into RAM
segment2[0][ACCEL] = 2048;			// from program memory
segment2[0][TIME] = 1200;

segment2[1][DIST] = -29500;
segment2[1][VEL] = 1024;
segment2[1][ACCEL] = 512;
segment2[1][TIME] = 1200;

segment2[2][DIST] = 737;
segment2[2][VEL] = 4096;
segment2[2][ACCEL] = 2048;
segment2[2][TIME] = 1200;

segment2[3][DIST] = 737;
segment2[3][VEL] = 4096;
segment2[3][ACCEL] = 2048;
segment2[3][TIME] = 1200;

segment2[4][DIST] = 738;
segment2[4][VEL] = 4096;
segment2[4][ACCEL] = 2048;
segment2[4][TIME] = 1200;

segment2[5][DIST] = 738;
segment2[5][VEL] = 4096;
segment2[5][ACCEL] = 2048;
segment2[5][TIME] = 1200;

segment2[6][DIST] = -2950;
segment2[6][VEL] = 1024;
segment2[6][ACCEL] = 128;
segment2[6][TIME] = 1200;

segment2[7][DIST] = 2950;
segment2[7][VEL] = 256;
segment2[7][ACCEL] = 64;
segment2[7][TIME] = 1200;

segment2[8][DIST] = -2950;
segment2[8][VEL] = 4096;
segment2[8][ACCEL] = 512;
segment2[8][TIME] = 1200;

segment2[9][DIST] = 29500;
segment2[9][VEL] = 1024;
segment2[9][ACCEL] = 512;
segment2[9][TIME] = 1200;

segment2[10][DIST] = 29500;
segment2[10][VEL] = 2048;
segment2[10][ACCEL] = 512;
segment2[10][TIME] = 1200;

segment2[11][DIST] = 29500;
segment2[11][VEL] = 4096;
segment2[11][ACCEL] = 1024;
segment2[11][TIME] = 1200;

if(MODE1)								// Check DIP switches at powerup
   stat.loop = 1;						// If SW1 is on, set for loop mode
	    
if(MODE2)								// If SW2 is on, execute 
   {										// segments 12 and 13
   firstseg = 12;
   lastseg = 13;
   segnum = 12;
	stat.run = 1;
   }
else if(MODE3)							// If SW3 is on, execute
   {										// segments 14 through 18
	firstseg = 14;
   lastseg = 18;
   segnum = 14;
	stat.run = 1;
   }
else if(MODE4)							// If SW4 is on, execute
   {										// segments 18 and 19
	firstseg = 18;
   lastseg = 19;
   segnum = 18;
   stat.run = 1;
	}   
											
INTCONbits.PEIE = 1;					// Enable peripheral interrupts
INTCONbits.GIE = 1;					// Enable all interrupts
}

//---------------------------------------------------------------------
// main()
//---------------------------------------------------------------------

void main(void)
{
Setup();									// Setup peripherals and software
											// variables.

while(1)                    		// Loop forever
    {										
   ClrWdt();							// Clear the WDT

   ConvertADC();						// Start an A/D conversion				
   while(BusyADC());					// Wait for the conversion to complete
	PORTD = 0;							// Clear the LED bargraph display.
	PORTE	&= 0x04;						//						"
	
	if(ADRES > 225) 
		{
		PORTE |= 0x03;					// Turn on 10 LEDS
		PORTD = 0xff;
		}
	if(ADRES > 200) 
		{
		PORTE |= 0x01;					// Turn on 9 LEDS
		PORTD = 0xff;
		}
	else if(ADRES > 175) PORTD = 0xff;	// Turn on 8 LEDS
	else if(ADRES > 150) PORTD = 0x7f;	// 7 LEDS
	else if(ADRES > 125) PORTD = 0x3f;	// 6 LEDS
	else if(ADRES > 100) PORTD = 0x1f;	// 5 LEDS
	else if(ADRES > 75) PORTD = 0x0f;	// 4 LEDS
	else if(ADRES > 50) PORTD = 0x07;	// 3 LEDS
	else if(ADRES > 25) PORTD = 0x03;	// 2 LEDS
	else if(ADRES > 0) PORTD = 0x01;		// 1 LED
	
	if(PIR1bits.RCIF)									// Check for USART interrupt
   	{
   	switch(udata = RCREG)
	      {
	      case ',':	DoCommand();				// process the string
	      				memset(inpbuf,0,8);		// clear the input buffer
	                  i = 0;						// clear the buffer index
	                  comcount++;					// increment comma count
	                  TXREG = udata;				// echo the character
	                  break;
	      				
	      				
	      case 0x0d:  DoCommand();				// process the string
	                  memset(inpbuf,0,8);		// clear the input buffer
	                  i = 0;						// clear the buffer index
	                  comcount = 0;				// clear comma count
							segnum = 0;					// clear segment number
							parameter = 0;				// clear paramater 
							putrsUSART(ready);		// put prompt to USART 
	                  break;
	                  
	      default:    inpbuf[i] = udata;		// get received char 
	                  i++;							// increment buffer index
	                  if(i > 7)					// If more than 8 chars
	                     {							// received before getting
	                     putrsUSART(ready); 	// a <CR>, clear input
	                     memset(inpbuf,0,8);	// buffer
	                     i = 0;					// the buffer index
	                     }
	                  else TXREG = udata; 		// echo character
	                  break;						//
         
         }     										//end switch(udata)
	   
	    }       										//end if(RCIF)
    }          										//end while(1)
}


//-------------------------------------------------------------------
// DoCommand()
// Processes incoming USART data.
//-------------------------------------------------------------------

void DoCommand(void)
{
if(comcount == 0)		// If this is the first parameter of the input
	{						// command...
	switch(inpbuf[0])
		{
		case 'X':	parameter = DIST;		// Segment distance change
						break;
					
		case 'V':	parameter = VEL;		// Segment velocity change
						break;
					
		case 'A':	parameter = ACCEL;	// Segment acceleration change
						break;
					
		case 'T':	parameter = TIME;		// Segment delay time change
						break;
					
		case 'P':	parameter = 'P';		// Change proportional gain
						break;
	
		case 'I':	parameter = 'I';		// Change integral gain
						break;
	
		case 'D':	parameter = 'D';		// Change differential gain
						break;
		
		case 'L':	parameter = 'L';		// Loop a range of segments
						break;
		
		case 'S':	stat.run = 0;			// Stop execution of segments
						break;

		case 'G':	parameter = 'G';		// Execute a range of segments
						break;
		
		case 'W':   if(PORTEbits.RE2)		// Enable or disable motor
							{						// driver IC
							putrsUSART("\r\nPWM On");
							PORTEbits.RE2 = 0;
							}
						else
							{
							putrsUSART("\r\nPWM Off");
							PORTEbits.RE2 = 1;
							}
						break;
      	      
	   default:    if(inpbuf[0] != '\0')
	                  {
	                  putrsUSART(badcmd);
	                  }
	               break;
	
		}
	}

else if(comcount == 1)		// If this is the second parameter of the
	{								// input command.
	if(parameter < 4) segnum = atob(inpbuf);
	else
	switch(parameter)									
		{
		case 'P':   kp = atoi(inpbuf);			// proportional gain change
		            ExtEEWrite(122, kp);			// Store value in EEPROM
		            break;

		case 'I':   ki = atoi(inpbuf);			// integral gain change
		            ExtEEWrite(124, ki);			// Store value in EEPROM
		            break;

		case 'D':   kd = atoi(inpbuf);			// differential gain change
		            ExtEEWrite(126, kd);			// Store value in EEPROM
		            break;
      
		case 'G':	firstseg = atob(inpbuf);
						break;							
															// Get the first segment in
      													// the range to be executed.

		case 'L':	firstseg = atob(inpbuf);
						break;
			     
		default:    break;
     	}
	}
	
else if(comcount == 2)
	{
	if(!stat.run)							// If no profile is executing
		{
		if(parameter < 4)					// If this was a segment parameter
			{									// change.
			if(segnum < 12)
				{
				// Write the segment paramater into data memory
				segment1[segnum][parameter] = atoi(inpbuf);
				// Compute EEPROM address and write data to EEPROM
				eeadr = (segnum << 3) + (parameter << 1);
				ExtEEWrite(eeadr, segment1[segnum][parameter]);
				}
			
			else if(segnum < 24)
				// Write segment parameter data into data memory
				segment2[segnum - 12][parameter] = atoi(inpbuf);
			}
		else switch(parameter)									
			{
			case 'G':	lastseg = atob(inpbuf);				// Get value for
			            segnum = firstseg;					// last segment.
							stat.loop = 0;
							stat.run = 1;							// Start profile.
							break;
      
			case 'L':	lastseg = atob(inpbuf);				// Get value for
			            segnum = firstseg;					// last segment.
							stat.loop = 1;							// Enable looping
							stat.run = 1;							// Start profile
							break;
			
			default:    break;
	     	}
	   }
	}
}

//---------------------------------------------------------------------
// ExtEEWrite()
// Writes an integer value to an EEPROM connected to the I2C bus at
// the specified location.
//---------------------------------------------------------------------

void ExtEEWrite(unsigned char address, int data)
{
union 
{
char b[2];
int i;
}
temp;

char error, retry;

temp.i = data;
error = 0;

retry = 10;								// Poll the EEPROM up to 10 times
do
	{
	error = EEAckPolling(0xA0);
	retry--;
	} while(error && retry > 0); 

retry = 10;								// Attempt to write low byte of data
do											// up to 10 times
	{
	error = EEByteWrite(0xA0, address, temp.b[0]);
	retry--;
	} while(error && retry > 0);

retry = 10;								// Poll the EEPROM up to 10 times
do
	{
	error = EEAckPolling(0xA0);
	retry--;
	} while(error && retry > 0);


retry = 10;								// Attempt to write high byte of data
do											// up to 10 times
	{
	error = EEByteWrite(0xA0, address + 1, temp.b[1]);
	retry--;
	} while(error && retry > 0);
	
}

//---------------------------------------------------------------------
// ExtEEWrite()
// Reads an integer value from an EEPROM connected to the I2C bus at
// the specified location.
//---------------------------------------------------------------------

int ExtEERead(unsigned char address)
{
union 
{
char b[2];
int i;
}
data;

union 
{
char b[2];
int i;
}
temp;

char retry;

retry = 10;								// Attempt to read low byte of data
do											// up to 10 times
	{
	temp.i = EERandomRead(0xA0, address);
	retry--;
	} while(temp.b[1] && retry > 0);

if(temp.b[1]) data.b[0] = 0;		// Make read result 0 if error
else data.b[0] = temp.b[0];		// Otherwise get the low byte of data

retry = 10;								// Attempt to read high byte of data
do											// up to 10 times
	{
	temp.i = EERandomRead(0xA0, address + 1);
	retry--;
	} while(temp.b[1] && retry > 0);

if(temp.b[1]) data.b[1] = 0;		// Make read result 0 if error
else data.b[1] = temp.b[0];		// Otherwise get the high byte of data

return data.i;
}

//---------------------------------------------------------------------
// putrsUSART()
// Writes a string of characters in program memory to the USART
//---------------------------------------------------------------------

void putrsUSART(const rom char *data)
{
	do
	{				
		while(!(TXSTA & 0x02));
		TXREG = *data;
	} while( *data++ );
}


