

/*  LabWindows/CVI RS232 Example Program

    This RS232 example program allows the user to send and receive
    commands from a device via a serial port.
    First you must Configure the port, then send a command to the
    device, and then read a response from the device.
    It is important to select the correct terminator on sending and
    reading which depends upon your particular RS232 device.  This
    program gives the option of none, line feed or carriage return for
    terminators.  It is also important to make sure you have the correct
    type of cable.  See the LabWindows/CVI documenation for more details
    on cabling and handshaking.
*/

#include <cvirte.h>    /* Needed if linking in external compiler; harmless otherwise */
#include <userint.h>
#include <rs232.h>
#include <utility.h>
#include <formatio.h>
#include <string.h>
#include "serial.h"
#include "Prototype.h"

/* Prototypes */
void SetSerialConfig(void);

/********************************************************************/

int panel_handle,
    config_handle,
    comport,
    baudrate,
    portindex,
    parity,
    databits,
    stopbits,
    inputq,         /* Sets input queue length in OpenComConfig */
    outputq,        /* Sets output queue length in OpenComConfig */
    xmode,
    ctsmode,
    stringsize,
    bytes_sent,
    bytes_read,
    RS232Error,
    config_flag,
    breakstatus,
    port_open,
    com_status,
    send_mode,
    send_byte,
    send_term_index,
    read_term_index,
    read_term,
    inqlen,         /* Stores result from GetInQLen */
    outqlen;        /* Stores result from GetOutQLen */

short read_cnt;

double timeout;

char devicename[30],
     send_data[500],
     read_data[2000],
     tbox_read_data[2000],
     com_msg[500],
     msg[100];

#define QuitHelp        1
#define InputqHelp      2

/********************************************************************/

void DisplayRS232Error (void);
void SetConfigParms (void);
void GetConfigParms (void);
void DisplayHelp (int);
void EnablePanelControls (int);
void DisplayComStatus (void);
void ActivateSendControls (int);
void SendAscii (void);
void SendByte (void);

 
void SetConfigParms (void)
{
    SetCtrlVal (config_handle, CONFIG_COMPORT, comport);
    SetCtrlVal (config_handle, CONFIG_BAUDRATE, baudrate);
    SetCtrlVal (config_handle, CONFIG_PARITY, parity);
    SetCtrlVal (config_handle, CONFIG_DATABITS, databits);
    SetCtrlVal (config_handle, CONFIG_STOPBITS, stopbits);
    SetCtrlVal (config_handle, CONFIG_INPUTQ, inputq);
    SetCtrlVal (config_handle, CONFIG_OUTPUTQ, outputq);
    SetCtrlVal (config_handle, CONFIG_CTSMODE, ctsmode);
    SetCtrlVal (config_handle, CONFIG_XMODE, xmode);
    SetCtrlIndex (config_handle, CONFIG_COMPORT, portindex);
}


/********************************************************************/

void GetConfigParms (void)
{
    GetCtrlVal (config_handle, CONFIG_COMPORT, &comport);
    GetCtrlVal (config_handle, CONFIG_BAUDRATE, &baudrate);
    GetCtrlVal (config_handle, CONFIG_PARITY, &parity);
    GetCtrlVal (config_handle, CONFIG_DATABITS, &databits);
    GetCtrlVal (config_handle, CONFIG_STOPBITS, &stopbits);
    GetCtrlVal (config_handle, CONFIG_INPUTQ, &inputq);
    GetCtrlVal (config_handle, CONFIG_OUTPUTQ, &outputq);
    GetCtrlIndex (config_handle, CONFIG_COMPORT, &portindex);
    #ifdef _NI_unix_
        devicename[0]=0;
    #else
        GetLabelFromIndex (config_handle, CONFIG_COMPORT, portindex,
                       devicename);
    #endif                   
}



void SetSerialConfig(void)
 {
     config_handle = LoadPanel (panel_handle, "serial.uir", CONFIG);
     RecallPanelState(config_handle, "serial.uir", CONFIG);
     
     InstallPopup (config_handle);

            /*  If user already has done configuration, then
                display those new parameters.  If entering
                configuration for 1st time, set config_flag
                and use default settings.
            */
            if (config_flag)    /* Configuration done at least once.*/
                SetConfigParms ();
            else                /* 1st time.*/
                config_flag = 1;
 }    

/********************************************************************/

int CVICALLBACK CloseConfigCallback(int panel, int control, int event, void *callbackData, int eventData1, int eventData2)
{
    switch (event) {
        case EVENT_COMMIT :

				SavePanelState(config_handle, "serial.uir", CONFIG); 
				OpenSerial();
				
            break;

        }
    return(0);
}

void OpenSerial(void)
{
   port_open = 0;  /* initialize flag to 0 - unopened */
   RecallPanelState(config_handle, "serial.uir", CONFIG);
   
   GetConfigParms ();

   DisableBreakOnLibraryErrors ();
   RS232Error = OpenComConfig (comport, devicename, baudrate, parity, databits, stopbits, inputq, outputq);
   EnableBreakOnLibraryErrors ();

   if (RS232Error) DisplayRS232Error ();

   if (RS232Error == 0) {

       port_open = 1;

       GetCtrlVal (config_handle, CONFIG_XMODE, &xmode);
       SetXMode (comport, xmode);

       GetCtrlVal (config_handle, CONFIG_CTSMODE, &ctsmode);
       SetCTSMode (comport, ctsmode);

       GetCtrlVal (config_handle, CONFIG_TIMEOUT, &timeout);
       SetComTime (comport, timeout);

       }

   DiscardPanel (config_handle);
}	
/********************************************************************/

int CVICALLBACK GetInQCallBack(int panel, int control, int event, void *callbackData, int eventData1, int eventData2)
{
    switch (event) {
        case EVENT_COMMIT :
            inqlen = GetInQLen (comport);
            Fmt (msg, "%s<Input queue length = %i", inqlen);
            MessagePopup ("RS232 Message", msg);
            break;
    }

    return(0);
}

/********************************************************************/

//void EnablePanelControls (int enable)
//{
 //   SetCtrlAttribute (panel_handle, SERIAL_SEND, ATTR_DIMMED, enable);
 //   SetCtrlAttribute (panel_handle, SERIAL_READ, ATTR_DIMMED, enable);
 //   SetCtrlAttribute (panel_handle, SERIAL_READ_COUNT, ATTR_DIMMED, enable);
 //   SetCtrlAttribute (panel_handle, SERIAL_TBOX_READ, ATTR_DIMMED, enable);
 //   SetCtrlAttribute (panel_handle, SERIAL_BYTES, ATTR_DIMMED, enable);
 //   SetCtrlAttribute (panel_handle, SERIAL_ERROR, ATTR_DIMMED, enable);
 //   SetCtrlAttribute (panel_handle, SERIAL_FLUSHINQ, ATTR_DIMMED, enable);
 //   SetCtrlAttribute (panel_handle, SERIAL_FLUSHOUTQ, ATTR_DIMMED, enable);
 //   SetCtrlAttribute (panel_handle, SERIAL_GETINQ, ATTR_DIMMED, enable);
 //   SetCtrlAttribute (panel_handle, SERIAL_GETOUTQ, ATTR_DIMMED, enable);
 //   SetCtrlAttribute (panel_handle, SERIAL_COMSTATUS, ATTR_DIMMED, enable);
 //   SetCtrlAttribute (panel_handle, SERIAL_READTERM, ATTR_DIMMED, enable);
 //   SetCtrlAttribute (panel_handle, SERIAL_SENDMODE, ATTR_DIMMED, enable);
 //   SetCtrlAttribute (panel_handle, SERIAL_RCV_HELP_MSG, ATTR_DIMMED, enable);
 //   SetCtrlAttribute (panel_handle, SERIAL_TRANS_HELP_MSG, ATTR_DIMMED, enable);
 //   SetCtrlAttribute (panel_handle, SERIAL_CLEARBOX, ATTR_DIMMED, enable);
 //   ActivateSendControls (enable);
//}



/********************************************************************/

void DisplayRS232Error (void)
{
    char ErrorMessage[200];
    switch (RS232Error) {
        default :
            if (RS232Error < 0) {   /* Bug? ComWrtByte sets rs232error if sent a byte out? */
                Fmt (ErrorMessage, "%s<RS232 error number %i", RS232Error);
                MessagePopup ("RS232 Message", ErrorMessage);
                }
            break;

        case 0  :
            MessagePopup ("RS232 Message", "No errors.");
            break;

        case -2 :
            Fmt (ErrorMessage, "%s", "Invalid port number (must be in the range 1 to 8).");
            MessagePopup ("RS232 Message", ErrorMessage);
            break;

        case -3 :
            Fmt (ErrorMessage, "%s", "No port is open.\n"
                 "Check COM Port setting in Configure.");
            MessagePopup ("RS232 Message", ErrorMessage);
            break;
        case -99 :
            Fmt (ErrorMessage, "%s", "Timeout error.\n\n"
                 "Either increase timeout value,\n"
                 "       check COM Port setting, or\n"
                 "       check device.");

            MessagePopup ("RS232 Message", ErrorMessage);
            break;

    }
}

