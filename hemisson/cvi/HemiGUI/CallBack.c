#include "HemiGUI.h"
#include <utility.h>
#include <ansi_c.h>
#include <rs232.h>
#include <cvirte.h>		/* Needed if linking in external compiler; harmless otherwise */
#include <userint.h>
#include "HemiGUI.h"
#include "serial.h"
#include "Prototype.h"
#include "globalDefine.h"
#include "toolbox.h"    

extern int canvasTop, canvasLeft;
extern Point joyStick, crossHairCenter;
extern	Point crossHairVerticalStart, crossHairHorizontalStart;
extern	Point crossHairVerticalEnd, crossHairHorizontalEnd;
extern	Point crossHairCenter;

extern	int canvasTop;
extern	int canvasHeigth;
extern	int canvasLeft;
extern	int canvasWidth;

// ----------------------------------------------------------------------------
// UIR callbacks 
// ----------------------------------------------------------------------------
//

// -- 'Exit' Button 
//
int CVICALLBACK CallbackButtonExit (int panel, int control, int event,
		void *callbackData, int eventData1, int eventData2)
{
	switch (event){
		case EVENT_COMMIT:
			HandleStopCommand();
			exit(0);
		break;
	}
	return 0;
}

// -- 'Start' Button 
//
int CVICALLBACK CallbackButtonStart (int panel, int control, int event,
		void *callbackData, int eventData1, int eventData2)
{
	switch (event){
		case EVENT_COMMIT:
			HandleStartCommand();			
		break;
	}
	return 0;
}

// -- 'Stop' Button 
//
int CVICALLBACK CallbackButtonStop (int panel, int control, int event,
		void *callbackData, int eventData1, int eventData2)
{
	switch (event){
		case EVENT_COMMIT:
			HandleStopCommand();
		break;
	}
	return 0;
}

int CVICALLBACK BeepTBCallBack (int panel, int control, int event,
		void *callbackData, int eventData1, int eventData2)
{
	switch (event){
		case EVENT_LEFT_CLICK:
			handleGuiCommand(BEEP);
		break;
	}
	return 0;
}

int CVICALLBACK VersionCBcallback (int panel, int control, int event,
		void *callbackData, int eventData1, int eventData2)
{
	switch (event){
		case EVENT_LEFT_CLICK:
			handleGuiCommand(VERSION);
		break;
	}
	return 0;
}

int CVICALLBACK SwitchesCBcallback (int panel, int control, int event,
		void *callbackData, int eventData1, int eventData2)
{
	switch (event){
		case EVENT_LEFT_CLICK:
			handleGuiCommand(SWITCHES);
		break;
	}
	return 0;
}

int CVICALLBACK ProxyCBcallback (int panel, int control, int event,
		void *callbackData, int eventData1, int eventData2)
{
	switch (event){
		case EVENT_LEFT_CLICK:
			handleGuiCommand(READ_PROXY);
		break;
	}
	return 0;
}

int CVICALLBACK AmbiCBcallback (int panel, int control, int event,
		void *callbackData, int eventData1, int eventData2)
{
	switch (event){
		case EVENT_LEFT_CLICK:
			handleGuiCommand(READ_AMBI);
		break;
	}
	return 0;
}

int CVICALLBACK TvRemoteCBcallback (int panel, int control, int event,
		void *callbackData, int eventData1, int eventData2)
{
	switch (event){
		case EVENT_LEFT_CLICK:
			handleGuiCommand(READ_TVREMOTE);   
		break;
	}
	return 0;
}

int CVICALLBACK HemiResetCBcallback (int panel, int control, int event,
		void *callbackData, int eventData1, int eventData2)
{
	switch (event){
		case EVENT_LEFT_CLICK:
			 handleGuiCommand(HEMI_RESET);
		break;
	}
	return 0;
}

int CVICALLBACK SetSpeedCBcallback (int panel, int control, int event,
		void *callbackData, int eventData1, int eventData2)
{
	switch (event){
		case EVENT_LEFT_CLICK:
			handleGuiCommand(SET_SPEED);
		break;
	}
	return 0;
}

int CVICALLBACK ZeroSpeedCBcallback (int panel, int control, int event,
		void *callbackData, int eventData1, int eventData2)
{
	switch (event){
		case EVENT_LEFT_CLICK:
			handleGuiCommand(ZERO_SPEED);
			break;
		}
	return 0;
}

int CVICALLBACK JoyStickCanCallback (int panel, int control, int event,
		void *callbackData, int eventData1, int eventData2)
{
	int speedUnitVerical;
	int speedTranslation, speedSteer;
	int speedUnitVertical, speedUnitHorizontal;
	
	switch (event){
		case EVENT_COMMIT:
		break;
		
		case EVENT_VAL_CHANGED:
		case EVENT_MOUSE_MOVE :
		case EVENT_LEFT_CLICK:

			/* Remove old line */
			SetCtrlAttribute(panelHandle,  PANEL_JOYSTK_CAN, ATTR_PEN_COLOR, VAL_WHITE);
			CanvasDrawLine (panelHandle, PANEL_JOYSTK_CAN, crossHairCenter, joyStick);
			
			/* Redraw crosshair */
			SetCtrlAttribute(panelHandle,  PANEL_JOYSTK_CAN, ATTR_PEN_COLOR, VAL_BLACK);
			CanvasDrawLine (panelHandle, PANEL_JOYSTK_CAN, crossHairVerticalStart, crossHairVerticalEnd);
			CanvasDrawLine (panelHandle, PANEL_JOYSTK_CAN, crossHairHorizontalStart, crossHairHorizontalEnd);

			/* Draw new joystick line */
			joyStick.y =  eventData1 -canvasTop;
			joyStick.x =  eventData2 - canvasLeft; 
			
			SetCtrlAttribute(panelHandle,  PANEL_JOYSTK_CAN, ATTR_PEN_COLOR, VAL_RED);
			CanvasDrawLine (panelHandle, PANEL_JOYSTK_CAN, crossHairCenter, joyStick);
			
			/* Scale pixels to speed */
			speedUnitVertical = canvasHeigth/18;
			speedUnitHorizontal = canvasWidth/18;
			
			// Translation, steering
			speedTranslation = (crossHairCenter.y - joyStick.y) / speedUnitVertical;
			speedSteer = ( crossHairCenter.x - joyStick.x) / speedUnitHorizontal;
			hemiStat.speedLeft =  speedTranslation - speedSteer;
			hemiStat.speedRight = speedTranslation + speedSteer; 
			
			/* Check for maximum speed */
			if(hemiStat.speedLeft > 9){
				hemiStat.speedLeft = 9;
			}
			if(hemiStat.speedLeft < -9){
				hemiStat.speedLeft = -9;
			}
			if(hemiStat.speedRight > 9){
				hemiStat.speedRight = 9;
			}
			if(hemiStat.speedRight < -9){
				hemiStat.speedRight = -9;
			}
			
			
			SetCtrlVal(panelHandle, PANEL_LEFT_NUM, hemiStat.speedLeft);
			SetCtrlVal(panelHandle, PANEL_RIGHT_NUM, hemiStat.speedRight);
			createCommandStr(CMD_HEMI_SETSPEED);
			sendCommand();
			
			
			printf("Someone clicked me at %d %d\n", eventData1 -canvasTop  , eventData2 - canvasLeft );
			printf("speed left : %d, speed right : %d\n", hemiStat.speedLeft, hemiStat.speedRight);
		break;
	}
	return 0;
}

int CVICALLBACK usVersionCBcallback (int panel, int control, int event,
		void *callbackData, int eventData1, int eventData2)
{
	switch (event){
		case EVENT_LEFT_CLICK:
			handleGuiCommand(US_VERSION);
		break;
	}
	return 0;
}

int CVICALLBACK usMeasCBcallback (int panel, int control, int event,
		void *callbackData, int eventData1, int eventData2)
{
	switch (event){
		case EVENT_LEFT_CLICK:
			handleGuiCommand(US_MEASURE);
			break;
		}
	return 0;
}

int CVICALLBACK usReadCBcallback (int panel, int control, int event,
		void *callbackData, int eventData1, int eventData2)
{
	switch (event){
		case EVENT_LEFT_CLICK:
			handleGuiCommand(US_BRIGHTNESS);
		break;
	}
	return 0;
}

int CVICALLBACK usStartCBcallback (int panel, int control, int event,
		void *callbackData, int eventData1, int eventData2)
{
	switch (event){
		case EVENT_LEFT_CLICK:
			handleGuiCommand(US_START);
		break;
	}
	return 0;
}

int CVICALLBACK usInitCBcallback (int panel, int control, int event,
		void *callbackData, int eventData1, int eventData2)
{
	switch (event){
		case EVENT_LEFT_CLICK:
			handleGuiCommand(US_INIT);
		break;
	}
	return 0;
}

int CVICALLBACK camGetExpCBcallback (int panel, int control, int event,
		void *callbackData, int eventData1, int eventData2)
{
	switch (event){
		case EVENT_LEFT_CLICK:
			handleGuiCommand(CAM_GET_EXP_TIME);
		break;
	}
	return 0;
}

int CVICALLBACK camSetExpCBcallback (int panel, int control, int event,
		void *callbackData, int eventData1, int eventData2)
{
	switch (event){
		case EVENT_LEFT_CLICK:
		handleGuiCommand(CAM_SET_EXP_TIME);
		break;
	}
	return 0;
}

int CVICALLBACK camInitCBcallback (int panel, int control, int event,
		void *callbackData, int eventData1, int eventData2)
{
	switch (event){
		case EVENT_LEFT_CLICK:

		break;
	}
	return 0;
}

int CVICALLBACK camReadPixCBcallback (int panel, int control, int event,
		void *callbackData, int eventData1, int eventData2)
{
	switch (event){
		case EVENT_LEFT_CLICK:
			handleGuiCommand(CAM_READ_PIXELS);
		break;
	}
	return 0;
}

int CVICALLBACK camReadPixThrCBcallback (int panel, int control, int event,
		void *callbackData, int eventData1, int eventData2)
{
	switch (event){
		case EVENT_LEFT_CLICK:
			handleGuiCommand(CAM_READ_PIXELS_THR);
		break;
	}
	return 0;
}

int CVICALLBACK camLedTBCallBack (int panel, int control, int event,
		void *callbackData, int eventData1, int eventData2)
{
	switch (event){
		case EVENT_LEFT_CLICK:
			handleGuiCommand(CAM_SET_LED);
		break;
	}
	return 0;
}

int CVICALLBACK camGetThrCBcallback (int panel, int control, int event,
		void *callbackData, int eventData1, int eventData2)
{
	switch (event){
		case EVENT_LEFT_CLICK:
			handleGuiCommand(CAM_GET_THR);
		break;
	}
	return 0;
}

int CVICALLBACK camSetThrCBcallback (int panel, int control, int event,
		void *callbackData, int eventData1, int eventData2)
{
	switch (event){
		case EVENT_LEFT_CLICK:
			handleGuiCommand(CAM_SET_THR);
		break;
	}
	return 0;
}
