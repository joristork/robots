/////////////////////////////////////////////////////////////////////////
//
// Filename 	:	IRleds.c                                            
// Revision 	:	1.0                                                   
// Created  	:	25-06-2005                                            
// Project  	:	ZoekenSturenBewegen                                              
// Device		:	PIC16F877                                         
// Development	:	MPLAB / CCS PCM / Hemisson
// Tab setting	:	3                                                
//                                                                   
// Author		:	E.H. Steffens
// Department	:	Informatics Institute, Faculteit NWI
// Copyright	:	Universiteit van Amsterdam
// 
//	Description	:	 
//
/////////////////////////////////////////////////////////////////////////

#define THRESHOLD	30		/* Threshold  */
#define IR_SENSOR_TRIGGER	3		/* Trigger count for IR sensors */

void CheckGroundSensors(void){
/* Read downward looking ir sensors */
//   	__hemisson_refresh_sensors(GroundZone);

	if(__Sensors_Refreshed_Flag == TRUE){
		
	/* Find out state of sensors */
		if( ( __IR_Proximity[ GroundRight ] > GroundRightFree )  
		&& 	( __IR_Proximity[ GroundLeft  ] > GroundLeftFree  ) ){
	
		/* Increase trigger count */
			IR_trigger++;

		/* Apply a simple filter for false sensor readings */
			if (IR_trigger > IR_SENSOR_TRIGGER ){ 
				FreeZone = FALSE;
				hemisson_set_speed(0,0);
				putchar('F');
		   }	
		}
		
	__Sensors_Refreshed_Flag = FALSE;
	}
}

void CalibrateGroundSensors(void)
{
/* Read downward looking ir sensors */
	__hemisson_refresh_sensors(GroundZone);

/* Use these values as a reference, expect both sensors are on the free zone */ 
	GroundRightFree = __IR_Proximity[ GroundRight ] + IR_threshold ;
	GroundLeftFree  = __IR_Proximity[ GroundLeft  ] + IR_threshold ;
	
	printf("%u %u\n\r", GroundRightFree, GroundLeftFree);
}

void ReadGroundSensors(void)
{
/* Read downward looking ir sensors */
//	__hemisson_refresh_sensors(GroundZone);

//	hemisson_delay_ms(1);
	if(__Sensors_Refreshed_Flag == TRUE){
	
		printf("%u %u\n\r", __IR_Proximity[GroundRight], __IR_Proximity[GroundLeft]);
	}
	__Sensors_Refreshed_Flag = FALSE;
}

/* -- Hemisson Drive functions -- */
 
void Right90(void){
	int i;
	int time;

	time = chartohex(serial_in[2]);
	time = (time<<4) + chartohex(serial_in[3]);

// Turn 90 degrees rightt
	hemisson_set_speed(5,-5);		// Go straight
	for(i=0; i<2; i++){
		hemisson_delay_ms(250);		// Wait s
	}
	hemisson_delay_ms(time);		// Wait s
	
	hemisson_set_speed(0,0);		// Stop
}

void Left90(void)
{
	int i;
	int time;

	time = chartohex(serial_in[2]);
	time = (time<<4) + chartohex(serial_in[3]);


// Turn 90 degrees left
	hemisson_set_speed(-5,5);		// Go 
	for(i=0; i<2; i++){
		hemisson_delay_ms(250);		// Wait s
	}
	hemisson_delay_ms(time);			// Wait s
	
	hemisson_set_speed(0,0);		// Stop
	
}

void Forward (void)
{
  	hemisson_set_speed(5,5);		// Go straight
	hemisson_delay_s(1);			// Wait s
	hemisson_set_speed(0,0);
}


void Reverse(void)
{
  	hemisson_set_speed(-5,-5);		// Go straight
	hemisson_delay_s(1);			// Wait s
 	hemisson_set_speed(0,0);
}


void HalfReverse(void)
{
	int time;

	time = chartohex(serial_in[2]);
	time = (time<<4) + chartohex(serial_in[3]);

  	hemisson_set_speed(-5,-5);		// Go straight
	hemisson_delay_ms(time);			// Wait s
 	hemisson_set_speed(0,0);
}
