#include "toolbox.h"
#include <utility.h>
#include <ansi_c.h>
#include <rs232.h>
#include <cvirte.h>		/* Needed if linking in external compiler; harmless otherwise */
#include <userint.h>
#include "HemiGUI.h"
#include "serial.h"
#include "Prototype.h"
#include "globalDefine.h"

/* Externals - Serial.c */
extern int config_handle;
extern int comport;

 int panelHandle;
	int canvasTop;
	int canvasHeigth;
	int canvasLeft;
	int canvasWidth;
	
	Point joyStick;
	Point start, end;
	Point crossHairVerticalStart, crossHairHorizontalStart;
	Point crossHairVerticalEnd, crossHairHorizontalEnd;
	Point crossHairCenter;

 /* Commands */
char *startCommand = "b\r";
char *stopCommand = "s\r";

/* globals */
int notifyCount = 0;
int eventChar = '\r';
int eventMask =  LWRS_RXFLAG ;
double timeElapsed = 0;

/*---------------------------------------------------------------------------------------------------*/
/* main()
/*
/*---------------------------------------------------------------------------------------------------*/


int main (int argc, char *argv[])
{
	int i,j;
	
		  
	if (InitCVIRTE (0, argv, 0) == 0)	/* Needed if linking in external compiler; harmless otherwise */
		return -1;	/* out of memory */

	if ((panelHandle = LoadPanel (0, "HemiGUI.uir", PANEL)) < 0)
		return -1;
	
	if ((config_handle = LoadPanel (0, "Serial.uir", CONFIG)) < 0)
		return -1;
	
/* Open the serial port */
	OpenSerial();

              
/* Init */
	doPlot = FALSE;
	debugMessages = ON;
	
/* -- Joystick canvas initialisation -- */	

/* Get dimensions of canvas */
	GetCtrlAttribute(panelHandle, PANEL_JOYSTK_CAN, ATTR_TOP, &canvasTop);
	GetCtrlAttribute(panelHandle, PANEL_JOYSTK_CAN, ATTR_HEIGHT, &canvasHeigth);
	GetCtrlAttribute(panelHandle, PANEL_JOYSTK_CAN, ATTR_LEFT, &canvasLeft);
	GetCtrlAttribute(panelHandle, PANEL_JOYSTK_CAN, ATTR_WIDTH, &canvasWidth);
	
/* Calculate outer coordinates of both crosshairs */ 
	crossHairVerticalStart.x = canvasWidth/2; 
	crossHairVerticalStart.y = 0; 
	crossHairVerticalEnd.x = canvasWidth/2; 
	crossHairVerticalEnd.y = canvasHeigth; 

	crossHairHorizontalStart.x = 0; 
	crossHairHorizontalStart.y = canvasHeigth/2; 
	crossHairHorizontalEnd.x = canvasWidth; 
	crossHairHorizontalEnd.y = canvasHeigth/2;
	
	crossHairCenter.x = canvasWidth/2;
	crossHairCenter.y = canvasHeigth/2;
	
/* Draw crosshairs in canvas */
	CanvasDrawLine (panelHandle, PANEL_JOYSTK_CAN, crossHairVerticalStart, crossHairVerticalEnd);
	CanvasDrawLine (panelHandle, PANEL_JOYSTK_CAN, crossHairHorizontalStart, crossHairHorizontalEnd);

//	EnableExtendedMouseEvents(panelHandle,  PANEL_JOYSTK_CAN, 0.5);
// Must handle (ie disregard) coordinates outside canvas */

/* -- HemlinCam initialisation -- */
/* Clear the hemlincam pixel shadow arrays */
	for(i=0;i<sizeof(hemiStat.camPixels)/sizeof(int);i++){
		hemiStat.camPixels[i] = 0;
	}

	for(i=0;i<sizeof(hemiStat.camPixelsThr)/sizeof(int);i++){
		hemiStat.camPixelsThr[i] = 0;
	}

/* Plot both arrays ti initialise the plotHandles */ 
	plotHandle = PlotY (panelHandle, PANEL_CAMPIX_GR, hemiStat.camPixels, NUM_OF_PIXELS, VAL_INTEGER	, VAL_THIN_LINE, VAL_CONNECTED_POINTS, VAL_SOLID	, 1, VAL_GREEN	);
	plotHandleThr = PlotY (panelHandle, PANEL_CAMPIX_GR, hemiStat.camPixelsThr, NUM_OF_PIXELS, VAL_INTEGER	, VAL_THIN_LINE, VAL_CONNECTED_POINTS, VAL_SOLID	, 1, VAL_RED	);

/* Initialise colortable */
	for(i=0;i<256;i++){
		colorTable[i] = (i<<16) | (i<<8) | i;			  /* Linear color table */
	}
	
/* -- HemLinCam Bitimages initialisation -- */

/* Initialise colortable */
	for(i=0;i<256;i++){
		colorTable[i] = (i<<16) | (i<<8) | i;
	}
	
	destinationRect.top = 0;
	destinationRect.left = 0;
	destinationRect.height = HEMLINCAM_HEIGHT_CANVAS;
	destinationRect.width = NUM_OF_PIXELS;
		
/* Draw black bitimages */
	for(j=0;j<HEMLINCAM_HEIGHT_CANVAS;j++){
		for(i=0;i<NUM_OF_PIXELS;i++){
			HemlinCamPixels[NUM_OF_PIXELS*j+i] = hemiStat.camPixels[i];	
		}	
	}

	NewBitmap (NUM_OF_PIXELS, HEMLINCAM_PIXDEPTH_CANVAS, NUM_OF_PIXELS, HEMLINCAM_HEIGHT_CANVAS, colorTable, HemlinCamPixels, NULL, &bitMapId);
	CanvasDrawBitmap (panelHandle, PANEL_CAM_CAN, bitMapId, VAL_ENTIRE_OBJECT, destinationRect);
	
	for(j=0;j<HEMLINCAM_HEIGHT_CANVAS;j++){
		for(i=0;i<NUM_OF_PIXELS;i++){
			HemlinCamPixels[NUM_OF_PIXELS*j+i] = hemiStat.camPixelsThr[i];	
		}	
	}
	
/* Draw pixel values in a greyvalue bitmap */
	DiscardBitmap(bitMapId);
	NewBitmap (NUM_OF_PIXELS, HEMLINCAM_PIXDEPTH_CANVAS, NUM_OF_PIXELS, HEMLINCAM_HEIGHT_CANVAS, colorTable, HemlinCamPixels, NULL, &bitMapId);
	CanvasDrawBitmap (panelHandle, PANEL_CAMTHR_CAN, bitMapId, VAL_ENTIRE_OBJECT, destinationRect);
		

/* Start running the user interface */
	DisplayPanel (panelHandle);
	RunUserInterface ();
	return 0;
}



			
// ----------------------------------------------------------------------------
// User routines 
// ----------------------------------------------------------------------------
//
// -- 
void HandleStartCommand(void)
{
	
	FlushInQ (comport);
	
	timeElapsed = Timer();

/* Install the serial port callback function */
	InstallComCallback (comport, eventMask, notifyCount,
                    eventChar, (ComCallbackPtr) ComCallback, NULL);
                    
   SetCtrlVal(panelHandle, PANEL_RS232_LED, ON);
   
   if(debugMessages == ON){
   	printf("-- Serial port %1d opened at time : %f --\n", comport, timeElapsed);
	}
	
}

void HandleStopCommand(void)
{
	timeElapsed = Timer(); 
	
/* Install the serial port callback function */
	InstallComCallback (comport, 0, notifyCount,
                    eventChar, (ComCallbackPtr) ComCallback, NULL);
   
   SetCtrlVal(panelHandle, PANEL_RS232_LED, OFF);

   if(debugMessages == ON){
   	printf("-- Serial port %1d closed at time : %f --\n", comport, timeElapsed);
	}
}


//
// -- Get the data from the microcontroller and display it in the stripchart
//
void ComCallback(void)
{
	char CharFromHemisson;
	int count;
	
/* Check to see if there is something there */
	if( GetInQLen(comport) == 0){
		printf("RS232 inQ emprty\n");
		return;
	}
	
/* Read the input buffer */
	count = ComRdTerm(comport, cmdResult, SIZE_CMD_BUFFER, eventChar);

	timeElapsed = Timer();
	
	if(debugMessages == ON){
		printf("< %s (%d) at time %f\n", cmdResult, count, timeElapsed);
	}
	
	FlushInQ(comport);
	
	handleCmdResult();
		
}
