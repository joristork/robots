
/************** Static Function Declarations **************/

/************** Global Variable Declarations **************/

/************** Global Function Declarations **************/
extern int __cdecl command(int panel,
                           int control, int event, void *callbackData,
                           int eventData1, int eventData2);
extern int __cdecl ProgramDataCallback(int panel,
                               int control, int event, void *callbackData,
                               int eventData1, int eventData2);
extern int __cdecl Quit(int panel, int control,
                        int event, void *callbackData, int eventData1,
                        int eventData2);
extern void ButtonStartCommit(void);

/* HemiGUI.c */
extern void HandleStartCommand(void);
extern void HandleStopCommand(void);
extern void ComCallback(void);

/* Serial.c */
extern void SetSerialConfig(void);
extern void OpenSerial(void);

/* Menu.c   */

/* commands.c */
void handleGuiCommand(int );
void createCommandStr( int );
int cmdLookup(int );
int receiveResult(void);
void sendCommand(void);
void clearCmdResult(void);
void handleCmdResult(void);
int  resultLookup(int);
void handleResultString(char *);


