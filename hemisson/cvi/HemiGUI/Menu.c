#include <cvirte.h>		/* Needed if linking in external compiler; harmless otherwise */
#include "about.h"
#include <formatio.h>
#include <userint.h>
#include "HemiGUI.h"
#include "ProtoType.h"
#include "globalDefine.h"

static int about;

int panel_handle;
int config_handle;


void CVICALLBACK MenuFileCallback (int menuBar, int menuItem, void *callbackData,
		int panel)
{
	switch (menuItem)
	{
		case MENU_FILE_OPEN :
			
			break;
			
		case MENU_FILE_EXIT :
			QuitUserInterface(0);
			break;
	}
}


void CVICALLBACK MenuOptionsCallback (int menuBar, int menuItem, void *callbackData,
		int panel)
{
	switch (menuItem)
	{
		case MENU_OPTIONS_SERIAL :
			SetSerialConfig();
			break;
	}
}

void CVICALLBACK MenuHelpCallback (int menuBar, int menuItem, void *callbackData,
		int panel)
{
	switch (menuItem)
	{
		case MENU_HELP_ABOUT :
	     config_handle = LoadPanel (panel_handle, "about.uir", ABOUT);
	     InstallPopup (config_handle);
        break;
   }

}

int CVICALLBACK CloseAbout (int panel, int event, void *callbackData,
		int eventData1, int eventData2)
{
	switch (event){
		case EVENT_CLOSE:
			DiscardPanel (config_handle);
			break;

		default:
			break;
	}
	return 0;
}

