//--------------------------------------------------------------------------------//
//-                   HemiOs ( Hemisson Operating System )                       -//
//-                                                                              -//
//-  Copyright (C) Alexandre Colot, K-Team S.A. 2002                             -//
//-  This library is free software; you can redistribute it and/or               -//
//-  modify it under the terms of the GNU Lesser General Public                  -//
//-  License as published by the Free Software Foundation; either                -//
//-  version 2.1 of the License, or any later version.                           -//
//-                                                                              -//
//-  This library is distributed in the hope that it will be useful,             -//
//-  but WITHOUT ANY WARRANTY; without even the implied warranty of              -//
//-  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU           -//
//-  Lesser General Public License for more details.                             -//
//-                                                                              -//
//-  You should have received a copy of the GNU Lesser General Public            -//
//-  License along with this library; if not, write to the Free Software         -//
//-  Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA   -//
//-                                                                              -//
//-                               __  __  ________                               -//
//- K-Team S.A.                  |  |/  /|__    __|___  _____  ___  ___          -//
//- Chemin de Vuasset, CP 111    |     / __ |  | _____|/  _  \|   \/   |         -//
//- 1028 Preverenges             |  |  \    |  | ____|/  /_\  |        |         -//
//- Switzerland                  |__|\__\   |__|______|_/   \_|__|\/|__|         -//
//- alexandre.colot@k-team.com   tel:+41 21 802 5472 fax:+41 21 802 5471         -//
//-                                                                              -//
//--------------------------------------------------------------------------------//

////////////////////////////////////////////////////////////////////////////////
/*!   \file main.c
      \brief The main function where you can add your own code
*/
////////////////////////////////////////////////////////////////////////////////

#device PIC16F877 *=16 ADC=8	// Use 16 bit pointer for ram address > 255 and ADC = 8 bits resolution

#include "16f877.h"

#fuses HS,NOWDT,NOPROTECT,NOPUT,NOBROWNOUT,NOLVP,WRT,NOCPD                       // Configuration Bits

#use delay(clock=20000000)                                                       // Clock @ 20 MHz
#use rs232(baud=115200,parity=N,xmit=PIN_C6,rcv=PIN_C7)                          // Serial Configuration

#use fast_io(a)
#use fast_io(b)
#use fast_io(c)
#use fast_io(d)
#use fast_io(e)

int adns2051[0x12]; // shadow of ADNS2051 registers

#include "constants.h"

#include "hemisson_task1.c"
#include "hemisson_task2.c"
#include "hemisson_task3.c"
#include "hemisson_task4.c"

#include "ADNS2051.c"
#include "hemisson.c"
#include "hemisson_functions.c"

#include "IRleds.c"

// Bootloader Protection
#org 0x1F00, 0x1FFF {}                                                             

void main()
{
	int i, temp;

// Initialise
	IR_trigger = 0;
	IR_threshold = THRESHOLD;
	IR_armed = FALSE;
	FreeZone = TRUE;
		
   	__hemisson_init();        // Start Hemisson Initialisation
	init_ADNS2051();		// Init ADNS-2051 mouse sensor
 	hemisson_delay_s(1);	

// Stop Hemisson
	hemisson_set_speed(0,0);

//	Set free zone reference level 
	CalibrateIR();
	info_ADNS2051(READ_PRODUCT_ID);
	printf("%d\n\r", adns2051[PRODUCT_ID]);

   while(1)
   {
	if( command_in > 2 && command_in < 0xB){
		printf("%u ", command_in);

		switch (command_in ){
			case 3 :
				Right90();
			break;
			   	
			case 4 :
				Left90();
			break;
			  
			case 5 :
				FreeZone = TRUE;
				IR_trigger = 0;
				IR_armed = TRUE;
			break;
			   	
			case 6 :
				Forward();
			break;
	
			case 7 :
				Reverse();
			break;
	
			case 8 :
				IR_threshold = chartohex(__SerialBuffer[2]);
			break;
	
			case 9 :
				HalfReverse();
			break;

			case 0xA :
				
				info_ADNS2051(__SerialBuffer[1]);
				for(i=0; i<0x12; i++){
					printf("%x = %d\n\r", i, ADNS2051[i]);
				}
			break;
	
			default :
			break;
		}
	/* Reset command */
   		command_in = 0;
	}

/* Check if black line is crossed */
	if(FreeZone == TRUE){	 
		IRleds();
	}
	
	}
}

