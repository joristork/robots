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

#define  CONFIG                          1
#define  CONFIG_COMPORT                  2
#define  CONFIG_BAUDRATE                 3
#define  CONFIG_PARITY                   4
#define  CONFIG_DATABITS                 5
#define  CONFIG_STOPBITS                 6
#define  CONFIG_INPUTQ                   7
#define  CONFIG_OUTPUTQ                  8
#define  CONFIG_CTSMODE                  9
#define  CONFIG_XMODE                    10
#define  CONFIG_TIMEOUT                  11
#define  CONFIG_CLOSECONFIG              12      /* callback function: CloseConfigCallback */
#define  CONFIG_TIMEOUT_MSG1             13


     /* Menu Bars, Menus, and Menu Items: */

          /* (no menu bars in the resource file) */


     /* Callback Prototypes: */ 

int  CVICALLBACK CloseConfigCallback(int panel, int control, int event, void *callbackData, int eventData1, int eventData2);


#ifdef __cplusplus
    }
#endif
