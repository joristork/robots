#include <utility.h>
#include <ansi_c.h>
#include <rs232.h>
#include <cvirte.h>     /* Needed if linking in external compiler; harmless otherwise */
#include <userint.h>
#include "HemiGUI.h"
#include "serial.h"
#include "Prototype.h"
#include "globalDefine.h"

int panelHandle;
int comport;
char i2cStr[32];
int plotHandle, plotHandleThr;

hemisson hemissonCmd[32] = {
    { CMD_HEMI_BEEP,        'H', 'h' },
    { CMD_HEMI_SETSPEED,    'D', 'd' },
    { CMD_HEMI_VERSION,     'B', 'b' },
    { CMD_HEMI_SWITCHES,    'I', 'i' },
    { CMD_HEMI_PROXY,       'N', 'n' },
    { CMD_HEMI_AMBI ,   'O', 'o' },
    { CMD_HEMI_RI2C,        'R', 'r' },
    { CMD_HEMI_WI2C,        'W', 'w' },
    { CMD_HEMI_TVREMOTE,    'T', 't' },
    { CMD_HEMI_RESET    ,   'Z', 'z' },
};

void handleGuiCommand(int command) {
    switch(command){
        case ZERO_SPEED :
            hemiStat.speedLeft = 0;
            hemiStat.speedRight = 0;
            SetCtrlVal(panelHandle, PANEL_LEFT_NUM, hemiStat.speedLeft);
            SetCtrlVal(panelHandle, PANEL_RIGHT_NUM, hemiStat.speedRight);
            createCommandStr(CMD_HEMI_SETSPEED);
            sendCommand();

            break;

        case SET_SPEED :
            GetCtrlVal(panelHandle, PANEL_LEFT_NUM, &hemiStat.speedLeft);
            GetCtrlVal(panelHandle, PANEL_RIGHT_NUM, &hemiStat.speedRight);

            createCommandStr(CMD_HEMI_SETSPEED);
            sendCommand();
            break;

        case BEEP :
            GetCtrlVal(panelHandle, PANEL_BEEP_TB, &hemiStat.beepStatus);
            createCommandStr(CMD_HEMI_BEEP);
            sendCommand();
            break;

        case VERSION :
            createCommandStr(CMD_HEMI_VERSION);
            sendCommand();
            break;

        case SWITCHES :
            createCommandStr(CMD_HEMI_SWITCHES);
            sendCommand();
            break;

        case READ_PROXY :
            createCommandStr(CMD_HEMI_PROXY);
            sendCommand();
            break;

        case READ_AMBI :
            createCommandStr(CMD_HEMI_AMBI);
            sendCommand();
            break;

        case READ_TVREMOTE :
            createCommandStr(CMD_HEMI_TVREMOTE);
            sendCommand();
            break;

        case HEMI_RESET :
            createCommandStr(CMD_HEMI_RESET);
            sendCommand();
            break;

        case US_VERSION :
            sprintf(i2cStr, "E0,00,01\n\r");
            createCommandStr(CMD_HEMI_RI2C);
            sendCommand();
            break;

        case US_BRIGHTNESS :
            sprintf(i2cStr, "%02X,%02X,%02X\n\r", HEMUSS_I2C_ADDR, HEMUSS_BRIGHT_REG, 1);
            createCommandStr(CMD_HEMI_RI2C);
            sendCommand();
            break;

        case US_START :
            sprintf(i2cStr, "%02X,%02X,%02X\n\r", HEMUSS_I2C_ADDR, HEMUSS_VERSION_REG, HEMUSS_START_CM_REG);
            createCommandStr(CMD_HEMI_WI2C);
            sendCommand();
            break;

        case US_MEASURE :
            sprintf(i2cStr, "%02X,%02X,%02X\n\r", HEMUSS_I2C_ADDR, HEMUSS_MEASURE_REG, HEMUSS_ECHOS_REQ);
            createCommandStr(CMD_HEMI_RI2C);
            sendCommand();
            break;

        case US_INIT :
            sprintf(i2cStr, "E0,02,FF\n\r");
            createCommandStr(CMD_HEMI_WI2C);
            sendCommand();
            break;

        case CAM_SET_EXP_TIME :
            GetCtrlVal(panelHandle, PANEL_CAMEXP_NUM, &hemiStat.camExposure);
            sprintf(i2cStr, "%02X,%02X,%02X\n\r", HEMLINCAM_I2C_ADDRESS, HEMLINCAM_EXP_REG, hemiStat.camExposure);
            createCommandStr(CMD_HEMI_WI2C);
            sendCommand();
            break;

        case CAM_GET_EXP_TIME :
            sprintf(i2cStr, "%02X,%02X,%02X\n\r", HEMLINCAM_I2C_ADDRESS, HEMLINCAM_EXP_REG, 01);
            createCommandStr(CMD_HEMI_RI2C);
            sendCommand();
            break;

        case CAM_GET_THR :
            sprintf(i2cStr, "%02X,%02X,%02X\n\r", HEMLINCAM_I2C_ADDRESS, HEMLINCAM_THR_REG, 01);
            createCommandStr(CMD_HEMI_RI2C);
            sendCommand();
            break;

        case CAM_SET_THR :
            GetCtrlVal(panelHandle, PANEL_CAMTHR_NUM, &hemiStat.camThreshold);
            sprintf(i2cStr, "%02X,%02X,%02X\n\r", HEMLINCAM_I2C_ADDRESS, HEMLINCAM_THR_REG, hemiStat.camThreshold);
            createCommandStr(CMD_HEMI_WI2C);
            sendCommand();
            break;

        case CAM_SET_LED :
            GetCtrlVal(panelHandle, PANEL_CAMLED_TB, &hemiStat.camLedState);
            sprintf(i2cStr, "%02X,%02X,%02X\n\r", HEMLINCAM_I2C_ADDRESS, HEMLINCAM_LED_REG, hemiStat.camLedState);
            createCommandStr(CMD_HEMI_WI2C);
            sendCommand();
            break;

        case CAM_READ_PIXELS :
            sprintf(i2cStr, "%02X,%02X,%02X\n\r", HEMLINCAM_I2C_ADDRESS, HEMLINCAM_PIXEL_REG, NUM_OF_PIXELS);
            createCommandStr(CMD_HEMI_RI2C);
            sendCommand();
            break;

        case CAM_READ_PIXELS_THR :
            sprintf(i2cStr, "%02X,%02X,%02X\n\r", HEMLINCAM_I2C_ADDRESS, HEMLINCAM_PIXEL_THR_REG, NUM_OF_PIXELS);
            createCommandStr(CMD_HEMI_RI2C);
            sendCommand();
            break;

        default :
            break;

    }

    /* Remerber the last command send */
    hemiStat.command = command;


}

void createCommandStr(int command) {
    char cmdChar;
    char tempStr[32];

    int i;
    int cmdNum;

    /* Clear command string buffer */
    for(i=0; i< SIZE_CMD_BUFFER; i++){
        cmdToHemisson[i] = 0;
    }

    /* Clear input buffer */
    clearCmdResult();

    /* Lookup command RS232 command character */
    cmdNum = cmdLookup(command);
    if(cmdNum == NO_VALID_CMD){
        printf("No command in table\n");
        return ;
    }

    /* Compose the command string */
    switch(command){
        case CMD_HEMI_SETSPEED :
            cmdToHemisson[0] = hemissonCmd[cmdNum].cmdChar;
            strcat(cmdToHemisson, ",");
            sprintf(tempStr, "%d,%d\n\r", hemiStat.speedLeft, hemiStat.speedRight);
            strcat(cmdToHemisson, tempStr);

            break;

        case CMD_HEMI_BEEP :
            cmdToHemisson[0] = hemissonCmd[cmdNum].cmdChar;
            strcat(cmdToHemisson, ",");
            sprintf(tempStr, "%1x\n\r", hemiStat.beepStatus);
            strcat(cmdToHemisson, tempStr);

            break;

            /* These are one char commands, with no parameters */
        case CMD_HEMI_VERSION :
        case CMD_HEMI_SWITCHES :
        case CMD_HEMI_PROXY :
        case CMD_HEMI_RESET:
        case CMD_HEMI_TVREMOTE:
        case CMD_HEMI_AMBI :
            cmdToHemisson[0] = hemissonCmd[cmdNum].cmdChar;
            sprintf(tempStr, "\n\r");
            strcat(cmdToHemisson, tempStr);
            break;

        case CMD_HEMI_WI2C :
            cmdToHemisson[0] = hemissonCmd[cmdNum].cmdChar;
            sprintf(tempStr, ",");
            strcat(cmdToHemisson, tempStr);
            strcat(cmdToHemisson, i2cStr);
            break;

        case CMD_HEMI_RI2C :
            cmdToHemisson[0] = hemissonCmd[cmdNum].cmdChar;
            sprintf(tempStr, ",");
            strcat(cmdToHemisson, tempStr);
            strcat(cmdToHemisson, i2cStr);
            break;

        default :
            break;
    }
}


int cmdLookup(int command) {
    int i;
    int cmdFound = FALSE;

    i = 0;

    do{
        if(hemissonCmd[i].cmdNumber == command){
            return i;
        }

        i++;
    }
    while(i<CMD_TABLE_LENGTH && cmdFound == FALSE) ;

    return NO_VALID_CMD;
}

void sendCommand(void) {

    ComWrt(comport, cmdToHemisson, strlen(cmdToHemisson) );
    printf("> %s\n", cmdToHemisson);

}

void clearCmdResult (void) {
    int i;

    for(i=0; i<SIZE_CMD_BUFFER; i++){
        cmdResult[i] = 0;
    }
}


void handleCmdResult(void) {
    char commandChar;
    char parString[SIZE_CMD_BUFFER]={0};

    int cmdNumber;

    /* Strip off command character and returned parameters */
    sscanf(cmdResult, "%c,%s", &commandChar, parString);

    //  printf("%c %s\n\r", commandChar, parString);

    cmdNumber = resultLookup(commandChar);

    switch (cmdNumber){
        case CMD_HEMI_TVREMOTE :
        case CMD_HEMI_SWITCHES :
        case CMD_HEMI_AMBI :
        case CMD_HEMI_PROXY :
        case CMD_HEMI_RI2C :
        case CMD_HEMI_VERSION :
            handleResultString(parString);
            break;

        default :
            break;
    }
}

int resultLookup(int resultChar) {
    int i;
    int cmdFound = FALSE;

    i = 0;

    do{
        if(hemissonCmd[i].resultChar == resultChar){
            return hemissonCmd[i].cmdNumber;
        }

        i++;
    }
    while(i<CMD_TABLE_LENGTH && cmdFound == FALSE) ;

    return NO_VALID_CMD;
}


void handleResultString(char parString[]) {
    int echo1, echo2;
    int i,j;
    Point tableCell;

    switch(hemiStat.command){
        case VERSION :
            printf("Hemi OS version : %s\n\r", parString);
            break;

        case SWITCHES :
            sscanf(parString, "%1d,%1d,%1d,%1d", &hemiStat.switches[0], &hemiStat.switches[1], &hemiStat.switches[2], &hemiStat.switches[3]);
            SetCtrlVal(panelHandle, PANEL_SW1_SW, hemiStat.switches[0]);
            SetCtrlVal(panelHandle, PANEL_SW2_SW, hemiStat.switches[1]);
            SetCtrlVal(panelHandle, PANEL_SW3_SW, hemiStat.switches[2]);
            SetCtrlVal(panelHandle, PANEL_SW4_SW, hemiStat.switches[3]);
            break;

        case READ_TVREMOTE :
            sscanf(parString, "%3d,", &hemiStat.tvRemote);
            SetCtrlVal(panelHandle, PANEL_TV_NUM, hemiStat.tvRemote);
            break;

        case READ_PROXY :
            printf("Proximity readings : ");
            for(i=0;i<HEMI_IR_SENSORS;i++){
                sscanf(&parString[i*4], "%3d,", &hemiStat.proximity[i]);
                printf("%03d, ", hemiStat.proximity[i]);
            }
            printf("\n");

            tableCell.x = 1; /* the proximity column */

            for(i=0;i<HEMI_IR_SENSORS;i++){
                tableCell.y = i+1;
                SetTableCellVal (panelHandle, PANEL_IR_TBL, tableCell, hemiStat.proximity[i]);
            }
            break;

        case READ_AMBI :
            printf("Ambient readings : ");
            for(i=0;i<HEMI_IR_SENSORS;i++){
                sscanf(&parString[i*4], "%3d,", &hemiStat.ambient[i]);
                printf("%03d, ", hemiStat.ambient[i]);
            }
            printf("\n");

            tableCell.x = 2; /* the ambient column */

            for(i=0;i<HEMI_IR_SENSORS;i++){
                tableCell.y = i+1;
                SetTableCellVal (panelHandle, PANEL_IR_TBL, tableCell, hemiStat.ambient[i]);
            }
            break;

        case US_MEASURE :
            for(i=0;i<HEMUSS_ECHOS_REQ/2;i++){
                sscanf(&parString[i*8], "%3d,%3d,", &echo1, &echo2);
                hemiStat.usReading[i] = (echo1 << 8) + echo2;
                printf("Echo %d at : %d cm\n\r", i, hemiStat.usReading[i]);
            }
            break;

        case US_BRIGHTNESS :
            sscanf(parString, "%d", &hemiStat.usBrightness);
            printf("Brightness : %d\n", hemiStat.usBrightness);
            break;

        case CAM_GET_EXP_TIME :
            sscanf(parString, "%d", &hemiStat.camExposure);
            SetCtrlVal(panelHandle, PANEL_CAMEXP_NUM, hemiStat.camExposure);
            break;

        case CAM_GET_THR :
            sscanf(parString, "%d", &hemiStat.camThreshold);
            SetCtrlVal(panelHandle, PANEL_CAMTHR_NUM, hemiStat.camThreshold);
            break;

        case CAM_READ_PIXELS :

            /* Fill pixelarray */
            for(i=0;i<NUM_OF_PIXELS;i++){
                sscanf(&parString[i*4], "%3d,", &hemiStat.camPixels[i]);
            }

            /* Plot the thresholded pixel values in the graph */
            DeleteGraphPlot (panelHandle, PANEL_CAMPIX_GR, plotHandle, VAL_DELAYED_DRAW);
            plotHandle = PlotY (panelHandle, PANEL_CAMPIX_GR, hemiStat.camPixels, NUM_OF_PIXELS, VAL_INTEGER    , VAL_THIN_LINE, VAL_CONNECTED_POINTS, VAL_SOLID    , 1, VAL_GREEN  );

            /* Copy pixels to bitmap */
            for(j=0;j<HEMLINCAM_HEIGHT_CANVAS;j++){
                for(i=0;i<NUM_OF_PIXELS;i++){
                    HemlinCamPixels[NUM_OF_PIXELS*j+i] = hemiStat.camPixels[i];

                }
            }

            /* Draw pixel values in a greyvalue bitmap */
            DiscardBitmap(bitMapId);
            NewBitmap (NUM_OF_PIXELS, HEMLINCAM_PIXDEPTH_CANVAS, NUM_OF_PIXELS, HEMLINCAM_HEIGHT_CANVAS, colorTable, HemlinCamPixels, NULL, &bitMapId);
            CanvasDrawBitmap (panelHandle, PANEL_CAM_CAN, bitMapId, VAL_ENTIRE_OBJECT, destinationRect);

            break;

        case CAM_READ_PIXELS_THR :

            /* Fill pixelarray */
            for(i=0;i<NUM_OF_PIXELS;i++){
                sscanf(&parString[i*4], "%3d,", &hemiStat.camPixelsThr[i]);
            }

            /* Plot the thresholded pixel values in the graph */
            DeleteGraphPlot (panelHandle, PANEL_CAMPIX_GR, plotHandleThr, VAL_DELAYED_DRAW);
            plotHandleThr = PlotY (panelHandle, PANEL_CAMPIX_GR, hemiStat.camPixelsThr, NUM_OF_PIXELS, VAL_INTEGER  , VAL_THIN_LINE, VAL_CONNECTED_POINTS, VAL_SOLID    , 1, VAL_RED    );

            /* Copy pixels to bitmap */
            for(j=0;j<HEMLINCAM_HEIGHT_CANVAS;j++){
                for(i=0;i<NUM_OF_PIXELS;i++){
                    HemlinCamPixels[NUM_OF_PIXELS*j+i] = hemiStat.camPixelsThr[i];

                }
            }

            /* Draw pixel values in a greyvalue bitmap */
            DiscardBitmap(bitMapId);
            NewBitmap (NUM_OF_PIXELS, HEMLINCAM_PIXDEPTH_CANVAS, NUM_OF_PIXELS, HEMLINCAM_HEIGHT_CANVAS, colorTable, HemlinCamPixels, NULL, &bitMapId);
            CanvasDrawBitmap (panelHandle, PANEL_CAMTHR_CAN, bitMapId, VAL_ENTIRE_OBJECT, destinationRect);

            break;

        default :
            break;
    }

}

