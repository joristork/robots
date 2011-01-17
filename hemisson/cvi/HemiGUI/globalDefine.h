#ifndef GLOBAL_DEFINE
#define GLOBAL_DEFINE


#define FALSE					0
#define TRUE					1

#define	OFF	0
#define	ON		1

/* GUI Command defines */
#define	BEEP 						0
#define	VERSION 					1
#define	SWITCHES 				2
#define	READ_PROXY 				3
#define	READ_AMBI 				4
#define	READ_TVREMOTE			5
#define	HEMI_RESET				6
#define	SET_SPEED				7
#define	ZERO_SPEED				8
#define	JOYSTICK					9
#define	US_VERSION				10
#define	US_BRIGHTNESS			11
#define	US_MEASURE				12
#define	US_START					13
#define	US_INIT					14
#define	CAM_GET_EXP_TIME		15
#define	CAM_SET_EXP_TIME		16
#define	CAM_SET_THR				17
#define	CAM_GET_THR				18
#define	CAM_READ_PIXELS		19
#define	CAM_READ_PIXELS_THR	20
#define	CAM_SET_LED				21
#define	CAM_GET_VERSION		22


/* Hemisson RS232 commands */
#define CMD_HEMI_BEEP			0	/* Switch Beeper on/off                    */
#define CMD_HEMI_VERSION		1	/* Display Hemisson OS version             */
#define CMD_HEMI_SWITCHES		2	/* Display status of the top four switches */
#define CMD_HEMI_PROXY			3	/* Display values of the infrared sensors  */
#define CMD_HEMI_AMBI			4	/* Display status of the top four switches */
#define CMD_HEMI_TVREMOTE		5	/* Display status of the top four switches */
#define CMD_HEMI_RESET			6	/* Display status of the top four switches */
#define CMD_HEMI_SETSPEED		7	/* Set speed of both motors                */
#define CMD_HEMI_RI2C			8	/* Read data from the I2C bus              */
#define CMD_HEMI_WI2C			9	/* Write data to the I2C bus               */

/* Hemisson on-board sensors */
#define	HEMI_PROXY_FRONT				0	/* proximity sensor */
#define	HEMI_PROXY_FRONT_RIGHT		1	/* proximity sensor */
#define	HEMI_PROXY_FRONT_LEFT		2	/* proximity sensor */
#define	HEMI_PROXY_RIGHT				3	/* proximity sensor */
#define	HEMI_PROXY_LEFT				4	/* proximity sensor */
#define	HEMI_PROXY_REAR				5	/* proximity sensor */
#define	HEMI_PROXY_GROUND_RIGHT		6	/* proximity sensor */
#define	HEMI_PROXY_GROUND_LEFT		7	/* proximity sensor */

#define	HEMI_IR_SENSORS				8	/* Total number of infrared sensors */


/*HemUltraSonicSensor */
#define	HEMUSS_I2C_ADDR			0xE0  /* Base I2C address */
#define	HEMUSS_VERSION_REG		0x00	/* Version register */
#define	HEMUSS_BRIGHT_REG			0x01	/* Brightness register */
#define	HEMUSS_START_CM_REG		0x51	/* Start one shot and measure in cm */
#define	HEMUSS_MEASURE_REG		0x02	/* Here start the first echo reading */
#define	HEMUSS_RANGE_REG			0x02	/* For I2C write cycle here the max range is set */

#define	HEMUSS_MAX_RANGE			0xFF	/* Max range = ((Value*43mm)+43mm) */
#define	NUM_OF_MAX_ECHOS			17		/* Number of echos measured after one shot */
#define	HEMUSS_ECHOS_REQ			2		/* Number echos read from the us sensor (multiple of 2)    */

/* HemLinCam */
#define	HEMLINCAM_I2C_ADDRESS 		0xC0
#define	HEMLINCAM_LED_REG				0x30
#define	HEMLINCAM_VERSION_REG		0x00
#define	HEMLINCAM_THR_REG				0x20
#define	HEMLINCAM_EXP_REG				0x21
#define	HEMLINCAM_PIXEL_REG			0x10
#define	HEMLINCAM_PIXEL_THR_REG		0x11
#define	NUM_OF_PIXELS					102 	/* Number of pixels returned from camera */       

#define	HEMLINCAM_HEIGHT_CANVAS		10
#define	HEMLINCAM_PIXDEPTH_CANVAS	8

#define NO_VALID_CMD			-1 /* No command was found in the cmd table */
#define CMD_TABLE_LENGTH	32
#define SIZE_CMD_BUFFER		512


/* Global variables */
int StartPlot;
int StopPlot;
int doPlot;
int plotHandle;
int plotHandleThr;
int debugMessages;
int bitMapId;
char HemlinCamPixels[NUM_OF_PIXELS * HEMLINCAM_HEIGHT_CANVAS];
char HemlinCamThrPix[NUM_OF_PIXELS * HEMLINCAM_HEIGHT_CANVAS];
int colorTable[256];
Rect destinationRect;



/* Externals - Console.c */
extern int panelHandle ;

/* Serial communication */
char cmdToHemisson[SIZE_CMD_BUFFER];
char cmdResult[SIZE_CMD_BUFFER];

/* Command struct */
typedef struct  {
	int cmdNumber;
	char cmdChar;
	char resultChar;
}hemisson;

struct hemissonStatus {
	int command;
	int beepStatus;							  
	int speedLeft;
	int speedRight;
	int switches[4];
	int proximity[8];
	int ambient[8];
	int tvRemote;
/* Hemisson Ultrasonic range sensor */
	int usReading[NUM_OF_MAX_ECHOS];
	int usMaxRange;
	int usVersion;
	int usBrightness;
/* Hemisson linear camera sensor */
	int camExposure;
	int camThreshold;
	int camLedState;
	int camPixels[NUM_OF_PIXELS*sizeof(int)];
	int camPixelsThr[NUM_OF_PIXELS*sizeof(int)];
}hemiStat;





#endif
