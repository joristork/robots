/**************************************************************************/
/* LabWindows/CVI User Interface Resource (UIR) Include File              */
/* Copyright (c) National Instruments 2004. All Rights Reserved.          */
/*                                                                        */
/* WARNING: Do not add to, delete from, or otherwise modify the contents  */
/*          of this include file.                                         */
/**************************************************************************/

#include <userint.h>

#ifdef __cplusplus
    extern "C" {
#endif

     /* Panels and Controls: */

#define  PANEL                           1
#define  PANEL_BUTTON_START              2       /* callback function: CallbackButtonStart */
#define  PANEL_BUTTON_STOP               3       /* callback function: CallbackButtonStop */
#define  PANEL_BUTTON_EXIT               4       /* callback function: CallbackButtonExit */
#define  PANEL_CAMLED_TB                 5       /* callback function: camLedTBCallBack */
#define  PANEL_BEEP_TB                   6       /* callback function: BeepTBCallBack */
#define  PANEL_HEMIRESET_CB              7       /* callback function: HemiResetCBcallback */
#define  PANEL_TVREMOTE_CB               8       /* callback function: TvRemoteCBcallback */
#define  PANEL_ZEROSPEED_CB              9       /* callback function: ZeroSpeedCBcallback */
#define  PANEL_SPEED_CB                  10      /* callback function: SetSpeedCBcallback */
#define  PANEL_AMBI_CB                   11      /* callback function: AmbiCBcallback */
#define  PANEL_PROXY_CB                  12      /* callback function: ProxyCBcallback */
#define  PANEL_SWITCHES_CB               13      /* callback function: SwitchesCBcallback */
#define  PANEL_VERSION_CB                14      /* callback function: VersionCBcallback */
#define  PANEL_RIGHT_NUM                 15
#define  PANEL_LEFT_NUM                  16
#define  PANEL_JOYSTK_CAN                17      /* callback function: JoyStickCanCallback */
#define  PANEL_USREAD_CB                 18      /* callback function: usReadCBcallback */
#define  PANEL_SETTHR_CB                 19      /* callback function: camSetThrCBcallback */
#define  PANEL_GETTHR_CB                 20      /* callback function: camGetThrCBcallback */
#define  PANEL_SETEXP_CB                 21      /* callback function: camSetExpCBcallback */
#define  PANEL_GETEXP_CB                 22      /* callback function: camGetExpCBcallback */
#define  PANEL_CAMRTPIX_CB               23      /* callback function: camReadPixThrCBcallback */
#define  PANEL_CAMRPIX_CB                24      /* callback function: camReadPixCBcallback */
#define  PANEL_CAMNIT_CB                 25      /* callback function: camInitCBcallback */
#define  PANEL_USINIT_CB                 26      /* callback function: usInitCBcallback */
#define  PANEL_USSTART_CB                27      /* callback function: usStartCBcallback */
#define  PANEL_USMEAS_CB                 28      /* callback function: usMeasCBcallback */
#define  PANEL_USIVERSION_CB             29      /* callback function: usVersionCBcallback */
#define  PANEL_CAMTHR_NUM                30
#define  PANEL_CAMEXP_NUM                31
#define  PANEL_CAMPIX_GR                 32
#define  PANEL_IR_TBL                    33
#define  PANEL_SW4_SW                    34
#define  PANEL_SW3_SW                    35
#define  PANEL_SW2_SW                    36
#define  PANEL_SW1_SW                    37
#define  PANEL_TV_NUM                    38
#define  PANEL_RS232_LED                 39
#define  PANEL_CAMTHR_CAN                40
#define  PANEL_CAM_CAN                   41
#define  PANEL_TEXTMSG_2                 42
#define  PANEL_TEXTMSG_3                 43
#define  PANEL_DECORATION_3              44
#define  PANEL_TEXTMSG_4                 45
#define  PANEL_DECORATION_4              46
#define  PANEL_DECORATION_5              47
#define  PANEL_TEXTMSG_5                 48
#define  PANEL_DECORATION_2              49
#define  PANEL_DECORATION_6              50
#define  PANEL_DECORATION                51
#define  PANEL_TEXTMSG                   52


     /* Menu Bars, Menus, and Menu Items: */

#define  MENU                            1
#define  MENU_FILE                       2
#define  MENU_FILE_OPEN                  3       /* callback function: MenuFileCallback */
#define  MENU_FILE_SEPARATOR             4
#define  MENU_FILE_EXIT                  5       /* callback function: MenuFileCallback */
#define  MENU_OPTIONS                    6
#define  MENU_OPTIONS_SERIAL             7       /* callback function: MenuOptionsCallback */
#define  MENU_HELP                       8
#define  MENU_HELP_ABOUT                 9       /* callback function: MenuHelpCallback */


     /* Callback Prototypes: */ 

int  CVICALLBACK AmbiCBcallback(int panel, int control, int event, void *callbackData, int eventData1, int eventData2);
int  CVICALLBACK BeepTBCallBack(int panel, int control, int event, void *callbackData, int eventData1, int eventData2);
int  CVICALLBACK CallbackButtonExit(int panel, int control, int event, void *callbackData, int eventData1, int eventData2);
int  CVICALLBACK CallbackButtonStart(int panel, int control, int event, void *callbackData, int eventData1, int eventData2);
int  CVICALLBACK CallbackButtonStop(int panel, int control, int event, void *callbackData, int eventData1, int eventData2);
int  CVICALLBACK camGetExpCBcallback(int panel, int control, int event, void *callbackData, int eventData1, int eventData2);
int  CVICALLBACK camGetThrCBcallback(int panel, int control, int event, void *callbackData, int eventData1, int eventData2);
int  CVICALLBACK camInitCBcallback(int panel, int control, int event, void *callbackData, int eventData1, int eventData2);
int  CVICALLBACK camLedTBCallBack(int panel, int control, int event, void *callbackData, int eventData1, int eventData2);
int  CVICALLBACK camReadPixCBcallback(int panel, int control, int event, void *callbackData, int eventData1, int eventData2);
int  CVICALLBACK camReadPixThrCBcallback(int panel, int control, int event, void *callbackData, int eventData1, int eventData2);
int  CVICALLBACK camSetExpCBcallback(int panel, int control, int event, void *callbackData, int eventData1, int eventData2);
int  CVICALLBACK camSetThrCBcallback(int panel, int control, int event, void *callbackData, int eventData1, int eventData2);
int  CVICALLBACK HemiResetCBcallback(int panel, int control, int event, void *callbackData, int eventData1, int eventData2);
int  CVICALLBACK JoyStickCanCallback(int panel, int control, int event, void *callbackData, int eventData1, int eventData2);
void CVICALLBACK MenuFileCallback(int menubar, int menuItem, void *callbackData, int panel);
void CVICALLBACK MenuHelpCallback(int menubar, int menuItem, void *callbackData, int panel);
void CVICALLBACK MenuOptionsCallback(int menubar, int menuItem, void *callbackData, int panel);
int  CVICALLBACK ProxyCBcallback(int panel, int control, int event, void *callbackData, int eventData1, int eventData2);
int  CVICALLBACK SetSpeedCBcallback(int panel, int control, int event, void *callbackData, int eventData1, int eventData2);
int  CVICALLBACK SwitchesCBcallback(int panel, int control, int event, void *callbackData, int eventData1, int eventData2);
int  CVICALLBACK TvRemoteCBcallback(int panel, int control, int event, void *callbackData, int eventData1, int eventData2);
int  CVICALLBACK usInitCBcallback(int panel, int control, int event, void *callbackData, int eventData1, int eventData2);
int  CVICALLBACK usMeasCBcallback(int panel, int control, int event, void *callbackData, int eventData1, int eventData2);
int  CVICALLBACK usReadCBcallback(int panel, int control, int event, void *callbackData, int eventData1, int eventData2);
int  CVICALLBACK usStartCBcallback(int panel, int control, int event, void *callbackData, int eventData1, int eventData2);
int  CVICALLBACK usVersionCBcallback(int panel, int control, int event, void *callbackData, int eventData1, int eventData2);
int  CVICALLBACK VersionCBcallback(int panel, int control, int event, void *callbackData, int eventData1, int eventData2);
int  CVICALLBACK ZeroSpeedCBcallback(int panel, int control, int event, void *callbackData, int eventData1, int eventData2);


#ifdef __cplusplus
    }
#endif
