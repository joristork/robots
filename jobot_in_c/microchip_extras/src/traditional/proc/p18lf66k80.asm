        LIST P=18LF66K80
        NOLIST
;-------------------------------------------------------------------------
; MPLAB-Cxx  PIC18LF66K80 processor definition module
; 
; (c) Copyright 1999-2010 Microchip Technology, All rights reserved
;-------------------------------------------------------------------------

SFR_UNBANKED0       UDATA_ACS H'F60'

RXB0CON             
RXB0CONbits         RES 1     ; 0xF60
RXB0SIDH            
RXB0SIDHbits        RES 1     ; 0xF61
RXB0SIDL            
RXB0SIDLbits        RES 1     ; 0xF62
RXB0EIDH            
RXB0EIDHbits        RES 1     ; 0xF63
RXB0EIDL            
RXB0EIDLbits        RES 1     ; 0xF64
RXB0DLC             
RXB0DLCbits         RES 1     ; 0xF65
RXB0D0              
RXB0D0bits          RES 1     ; 0xF66
RXB0D1              
RXB0D1bits          RES 1     ; 0xF67
RXB0D2              
RXB0D2bits          RES 1     ; 0xF68
RXB0D3              
RXB0D3bits          RES 1     ; 0xF69
RXB0D4              
RXB0D4bits          RES 1     ; 0xF6A
RXB0D5              
RXB0D5bits          RES 1     ; 0xF6B
RXB0D6              
RXB0D6bits          RES 1     ; 0xF6C
RXB0D7              
RXB0D7bits          RES 1     ; 0xF6D
CANSTAT             
CANSTATbits         RES 1     ; 0xF6E
CANCON              
CANCONbits          RES 1     ; 0xF6F
CIOCON              
CIOCONbits          RES 1     ; 0xF70
COMSTAT             
COMSTATbits         RES 1     ; 0xF71
ECANCON             
ECANCONbits         RES 1     ; 0xF72
EEDATA              RES 1     ; 0xF73
EEADR               RES 1     ; 0xF74
EEADRH              RES 1     ; 0xF75
PIE5                
PIE5bits            RES 1     ; 0xF76
PIR5                
PIR5bits            RES 1     ; 0xF77
IPR5                
IPR5bits            RES 1     ; 0xF78
TXREG2              RES 1     ; 0xF79
RCREG2              RES 1     ; 0xF7A
SPBRG2              RES 1     ; 0xF7B
SPBRGH2             RES 1     ; 0xF7C
SPBRGH1             RES 1     ; 0xF7D
EECON2              RES 1     ; 0xF7E
EECON1              
EECON1bits          RES 1     ; 0xF7F
PORTA               
PORTAbits           RES 1     ; 0xF80
PORTB               
PORTBbits           RES 1     ; 0xF81
PORTC               
PORTCbits           RES 1     ; 0xF82
PORTD               
PORTDbits           RES 1     ; 0xF83
PORTE               
PORTEbits           RES 1     ; 0xF84
PORTF               
PORTFbits           RES 1     ; 0xF85
PORTG               
PORTGbits           RES 1     ; 0xF86
TMR4                RES 1     ; 0xF87
T4CON               
T4CONbits           RES 1     ; 0xF88
LATA                
LATAbits            RES 1     ; 0xF89
LATB                
LATBbits            RES 1     ; 0xF8A
LATC                
LATCbits            RES 1     ; 0xF8B
LATD                
LATDbits            RES 1     ; 0xF8C
LATE                
LATEbits            RES 1     ; 0xF8D
LATF                
LATFbits            RES 1     ; 0xF8E
LATG                
LATGbits            RES 1     ; 0xF8F
SLRCON              
SLRCONbits          RES 1     ; 0xF90
ODCON               
ODCONbits           RES 1     ; 0xF91
TRISA               
TRISAbits           RES 1     ; 0xF92
TRISB               
TRISBbits           RES 1     ; 0xF93
TRISC               
TRISCbits           RES 1     ; 0xF94
TRISD               
TRISDbits           RES 1     ; 0xF95
TRISE               
TRISEbits           RES 1     ; 0xF96
TRISF               
TRISFbits           RES 1     ; 0xF97
TRISG               
TRISGbits           RES 1     ; 0xF98
CCPTMRS             
CCPTMRSbits         RES 1     ; 0xF99
REFOCON             
REFOCONbits         RES 1     ; 0xF9A
OSCTUNE             
OSCTUNEbits         RES 1     ; 0xF9B
PSTR1CON            
PSTR1CONbits        RES 1     ; 0xF9C
PIE1                
PIE1bits            RES 1     ; 0xF9D
PIR1                
PIR1bits            RES 1     ; 0xF9E
IPR1                
IPR1bits            RES 1     ; 0xF9F
PIE2                
PIE2bits            RES 1     ; 0xFA0
PIR2                
PIR2bits            RES 1     ; 0xFA1
IPR2                
IPR2bits            RES 1     ; 0xFA2
PIE3                
PIE3bits            RES 1     ; 0xFA3
PIR3                
PIR3bits            RES 1     ; 0xFA4
IPR3                
IPR3bits            RES 1     ; 0xFA5
RCSTA2              
RCSTA2bits          RES 1     ; 0xFA6
BAUDCON1            
BAUDCON1bits        RES 1     ; 0xFA7
HLVDCON             
HLVDCONbits         RES 1     ; 0xFA8
PR4                 RES 1     ; 0xFA9
T1GCON              
T1GCONbits          RES 1     ; 0xFAA
RCSTA               
RCSTAbits           
RCSTA1              
RCSTA1bits          RES 1     ; 0xFAB
TXSTA               
TXSTAbits           
TXSTA1              
TXSTA1bits          RES 1     ; 0xFAC
TXREG               
TXREG1              RES 1     ; 0xFAD
RCREG               
RCREG1              RES 1     ; 0xFAE
SPBRG               
SPBRG1              RES 1     ; 0xFAF
T3GCON              
T3GCONbits          RES 1     ; 0xFB0
T3CON               
T3CONbits           RES 1     ; 0xFB1
TMR3L               RES 1     ; 0xFB2
TMR3H               RES 1     ; 0xFB3
CMSTAT              
CMSTATbits          
CMSTATUS            
CMSTATUSbits        RES 1     ; 0xFB4
CVRCON              
CVRCONbits          RES 1     ; 0xFB5
PIE4                
PIE4bits            RES 1     ; 0xFB6
PIR4                
PIR4bits            RES 1     ; 0xFB7
IPR4                
IPR4bits            RES 1     ; 0xFB8
BAUDCON2            
BAUDCON2bits        RES 1     ; 0xFB9
TXSTA2              
TXSTA2bits          RES 1     ; 0xFBA
CCP1CON             
CCP1CONbits         
ECCP1CON            
ECCP1CONbits        RES 1     ; 0xFBB
CCPR1               
CCPR1L              RES 1     ; 0xFBC
CCPR1H              RES 1     ; 0xFBD
ECCP1DEL            
ECCP1DELbits        
PWM1CON             
PWM1CONbits         RES 1     ; 0xFBE
ECCP1AS             
ECCP1ASbits         RES 1     ; 0xFBF
ADCON2              
ADCON2bits          RES 1     ; 0xFC0
ADCON1              
ADCON1bits          RES 1     ; 0xFC1
ADCON0              
ADCON0bits          RES 1     ; 0xFC2
ADRES               
ADRESL              RES 1     ; 0xFC3
ADRESH              RES 1     ; 0xFC4
SSPCON2             
SSPCON2bits         RES 1     ; 0xFC5
SSPCON1             
SSPCON1bits         RES 1     ; 0xFC6
SSPSTAT             
SSPSTATbits         RES 1     ; 0xFC7
SSPADD              
SSPADDbits          RES 1     ; 0xFC8
SSPBUF              RES 1     ; 0xFC9
T2CON               
T2CONbits           RES 1     ; 0xFCA
PR2                 RES 1     ; 0xFCB
TMR2                RES 1     ; 0xFCC
T1CON               
T1CONbits           RES 1     ; 0xFCD
TMR1L               RES 1     ; 0xFCE
TMR1H               RES 1     ; 0xFCF
RCON                
RCONbits            RES 1     ; 0xFD0
WDTCON              
WDTCONbits          RES 1     ; 0xFD1
OSCCON2             
OSCCON2bits         RES 1     ; 0xFD2
OSCCON              
OSCCONbits          RES 1     ; 0xFD3
                    RES 1
T0CON               
T0CONbits           RES 1     ; 0xFD5
TMR0L               RES 1     ; 0xFD6
TMR0H               RES 1     ; 0xFD7
STATUS              
STATUSbits          RES 1     ; 0xFD8
FSR2                
FSR2L               RES 1     ; 0xFD9
FSR2H               
FSR2Hbits           RES 1     ; 0xFDA
PLUSW2              RES 1     ; 0xFDB
PREINC2             RES 1     ; 0xFDC
POSTDEC2            RES 1     ; 0xFDD
POSTINC2            RES 1     ; 0xFDE
INDF2               RES 1     ; 0xFDF
BSR                 
BSRbits             RES 1     ; 0xFE0
FSR1                
FSR1L               RES 1     ; 0xFE1
FSR1H               
FSR1Hbits           RES 1     ; 0xFE2
PLUSW1              RES 1     ; 0xFE3
PREINC1             RES 1     ; 0xFE4
POSTDEC1            RES 1     ; 0xFE5
POSTINC1            RES 1     ; 0xFE6
INDF1               RES 1     ; 0xFE7
WREG                RES 1     ; 0xFE8
FSR0                
FSR0L               RES 1     ; 0xFE9
FSR0H               
FSR0Hbits           RES 1     ; 0xFEA
PLUSW0              RES 1     ; 0xFEB
PREINC0             RES 1     ; 0xFEC
POSTDEC0            RES 1     ; 0xFED
POSTINC0            RES 1     ; 0xFEE
INDF0               RES 1     ; 0xFEF
INTCON3             
INTCON3bits         RES 1     ; 0xFF0
INTCON2             
INTCON2bits         RES 1     ; 0xFF1
INTCON              
INTCONbits          
INTCON1             
INTCON1bits         RES 1     ; 0xFF2
PROD                
PRODL               RES 1     ; 0xFF3
PRODH               RES 1     ; 0xFF4
TABLAT              RES 1     ; 0xFF5
TBLPTR              
TBLPTRL             RES 1     ; 0xFF6
TBLPTRH             RES 1     ; 0xFF7
TBLPTRU             
TBLPTRUbits         RES 1     ; 0xFF8
PC                  
PCL                 RES 1     ; 0xFF9
PCLATH              RES 1     ; 0xFFA
PCLATU              
PCLATUbits          RES 1     ; 0xFFB
STKPTR              
STKPTRbits          RES 1     ; 0xFFC
TOS                 
TOSL                RES 1     ; 0xFFD
TOSH                RES 1     ; 0xFFE
TOSU                
TOSUbits            RES 1     ; 0xFFF

SFR_BANKED0         UDATA H'E41'

RXERRCNT            
RXERRCNTbits        RES 1     ; 0xE41
TXERRCNT            
TXERRCNTbits        RES 1     ; 0xE42
BRGCON1             
BRGCON1bits         RES 1     ; 0xE43
BRGCON2             
BRGCON2bits         RES 1     ; 0xE44
BRGCON3             
BRGCON3bits         RES 1     ; 0xE45
RXFCON1             
RXFCON1bits         RES 1     ; 0xE46
RXFCON0             
RXFCON0bits         RES 1     ; 0xE47
RXF6SIDH            
RXF6SIDHbits        RES 1     ; 0xE48
RXF6SIDL            
RXF6SIDLbits        RES 1     ; 0xE49
RXF6EIDH            
RXF6EIDHbits        RES 1     ; 0xE4A
RXF6EIDL            
RXF6EIDLbits        RES 1     ; 0xE4B
RXF7SIDH            
RXF7SIDHbits        RES 1     ; 0xE4C
RXF7SIDL            
RXF7SIDLbits        RES 1     ; 0xE4D
RXF7EIDH            
RXF7EIDHbits        RES 1     ; 0xE4E
RXF7EIDL            
RXF7EIDLbits        RES 1     ; 0xE4F
RXF8SIDH            
RXF8SIDHbits        RES 1     ; 0xE50
RXF8SIDL            
RXF8SIDLbits        RES 1     ; 0xE51
RXF8EIDH            
RXF8EIDHbits        RES 1     ; 0xE52
RXF8EIDL            
RXF8EIDLbits        RES 1     ; 0xE53
RXF9SIDH            
RXF9SIDHbits        RES 1     ; 0xE54
RXF9SIDL            
RXF9SIDLbits        RES 1     ; 0xE55
RXF9EIDH            
RXF9EIDHbits        RES 1     ; 0xE56
RXF9EIDL            
RXF9EIDLbits        RES 1     ; 0xE57
RXF10SIDH           
RXF10SIDHbits       RES 1     ; 0xE58
RXF10SIDL           
RXF10SIDLbits       RES 1     ; 0xE59
RXF10EIDH           
RXF10EIDHbits       RES 1     ; 0xE5A
RXF10EIDL           
RXF10EIDLbits       RES 1     ; 0xE5B
RXF11SIDH           
RXF11SIDHbits       RES 1     ; 0xE5C
RXF11SIDL           
RXF11SIDLbits       RES 1     ; 0xE5D
RXF11EIDH           
RXF11EIDHbits       RES 1     ; 0xE5E
RXF11EIDL           
RXF11EIDLbits       RES 1     ; 0xE5F
RXF12SIDH           
RXF12SIDHbits       RES 1     ; 0xE60
RXF12SIDL           
RXF12SIDLbits       RES 1     ; 0xE61
RXF12EIDH           
RXF12EIDHbits       RES 1     ; 0xE62
RXF12EIDL           
RXF12EIDLbits       RES 1     ; 0xE63
RXF13SIDH           
RXF13SIDHbits       RES 1     ; 0xE64
RXF13SIDL           
RXF13SIDLbits       RES 1     ; 0xE65
RXF13EIDH           
RXF13EIDHbits       RES 1     ; 0xE66
RXF13EIDL           
RXF13EIDLbits       RES 1     ; 0xE67
RXF14SIDH           
RXF14SIDHbits       RES 1     ; 0xE68
RXF14SIDL           
RXF14SIDLbits       RES 1     ; 0xE69
RXF14EIDH           
RXF14EIDHbits       RES 1     ; 0xE6A
RXF14EIDL           
RXF14EIDLbits       RES 1     ; 0xE6B
RXF15SIDH           
RXF15SIDHbits       RES 1     ; 0xE6C
RXF15SIDL           
RXF15SIDLbits       RES 1     ; 0xE6D
RXF15EIDH           
RXF15EIDHbits       RES 1     ; 0xE6E
RXF15EIDL           
RXF15EIDLbits       RES 1     ; 0xE6F
SDFLC               
SDFLCbits           RES 1     ; 0xE70
RXFBCON0            
RXFBCON0bits        RES 1     ; 0xE71
RXFBCON1            
RXFBCON1bits        RES 1     ; 0xE72
RXFBCON2            
RXFBCON2bits        RES 1     ; 0xE73
RXFBCON3            
RXFBCON3bits        RES 1     ; 0xE74
RXFBCON4            
RXFBCON4bits        RES 1     ; 0xE75
RXFBCON5            
RXFBCON5bits        RES 1     ; 0xE76
RXFBCON6            
RXFBCON6bits        RES 1     ; 0xE77
RXFBCON7            
RXFBCON7bits        RES 1     ; 0xE78
MSEL0               
MSEL0bits           RES 1     ; 0xE79
MSEL1               
MSEL1bits           RES 1     ; 0xE7A
MSEL2               
MSEL2bits           RES 1     ; 0xE7B
MSEL3               
MSEL3bits           RES 1     ; 0xE7C
BSEL0               
BSEL0bits           RES 1     ; 0xE7D
BIE0                
BIE0bits            RES 1     ; 0xE7E
TXBIE               
TXBIEbits           RES 1     ; 0xE7F
B0CON               
B0CONbits           RES 1     ; 0xE80
B0SIDH              
B0SIDHbits          RES 1     ; 0xE81
B0SIDL              
B0SIDLbits          RES 1     ; 0xE82
B0EIDH              
B0EIDHbits          RES 1     ; 0xE83
B0EIDL              
B0EIDLbits          RES 1     ; 0xE84
B0DLC               
B0DLCbits           RES 1     ; 0xE85
B0D0                
B0D0bits            RES 1     ; 0xE86
B0D1                
B0D1bits            RES 1     ; 0xE87
B0D2                
B0D2bits            RES 1     ; 0xE88
B0D3                
B0D3bits            RES 1     ; 0xE89
B0D4                
B0D4bits            RES 1     ; 0xE8A
B0D5                
B0D5bits            RES 1     ; 0xE8B
B0D6                
B0D6bits            RES 1     ; 0xE8C
B0D7                
B0D7bits            RES 1     ; 0xE8D
CANSTAT_RO9         
CANSTAT_RO9bits     RES 1     ; 0xE8E
CANCON_RO9          
CANCON_RO9bits      RES 1     ; 0xE8F
B1CON               
B1CONbits           RES 1     ; 0xE90
B1SIDH              
B1SIDHbits          RES 1     ; 0xE91
B1SIDL              
B1SIDLbits          RES 1     ; 0xE92
B1EIDH              
B1EIDHbits          RES 1     ; 0xE93
B1EIDL              
B1EIDLbits          RES 1     ; 0xE94
B1DLC               
B1DLCbits           RES 1     ; 0xE95
B1D0                
B1D0bits            RES 1     ; 0xE96
B1D1                
B1D1bits            RES 1     ; 0xE97
B1D2                
B1D2bits            RES 1     ; 0xE98
B1D3                
B1D3bits            RES 1     ; 0xE99
B1D4                
B1D4bits            RES 1     ; 0xE9A
B1D5                
B1D5bits            RES 1     ; 0xE9B
B1D6                
B1D6bits            RES 1     ; 0xE9C
B1D7                
B1D7bits            RES 1     ; 0xE9D
CANSTAT_RO8         
CANSTAT_RO8bits     RES 1     ; 0xE9E
CANCON_RO8          
CANCON_RO8bits      RES 1     ; 0xE9F
B2CON               
B2CONbits           RES 1     ; 0xEA0
B2SIDH              
B2SIDHbits          RES 1     ; 0xEA1
B2SIDL              
B2SIDLbits          RES 1     ; 0xEA2
B2EIDH              
B2EIDHbits          RES 1     ; 0xEA3
B2EIDL              
B2EIDLbits          RES 1     ; 0xEA4
B2DLC               
B2DLCbits           RES 1     ; 0xEA5
B2D0                
B2D0bits            RES 1     ; 0xEA6
B2D1                
B2D1bits            RES 1     ; 0xEA7
B2D2                
B2D2bits            RES 1     ; 0xEA8
B2D3                
B2D3bits            RES 1     ; 0xEA9
B2D4                
B2D4bits            RES 1     ; 0xEAA
B2D5                
B2D5bits            RES 1     ; 0xEAB
B2D6                
B2D6bits            RES 1     ; 0xEAC
B2D7                
B2D7bits            RES 1     ; 0xEAD
CANSTAT_RO7         
CANSTAT_RO7bits     RES 1     ; 0xEAE
CANCON_RO7          
CANCON_RO7bits      RES 1     ; 0xEAF
B3CON               
B3CONbits           RES 1     ; 0xEB0
B3SIDH              
B3SIDHbits          RES 1     ; 0xEB1
B3SIDL              
B3SIDLbits          RES 1     ; 0xEB2
B3EIDH              
B3EIDHbits          RES 1     ; 0xEB3
B3EIDL              
B3EIDLbits          RES 1     ; 0xEB4
B3DLC               
B3DLCbits           RES 1     ; 0xEB5
B3D0                
B3D0bits            RES 1     ; 0xEB6
B3D1                
B3D1bits            RES 1     ; 0xEB7
B3D2                
B3D2bits            RES 1     ; 0xEB8
B3D3                
B3D3bits            RES 1     ; 0xEB9
B3D4                
B3D4bits            RES 1     ; 0xEBA
B3D5                
B3D5bits            RES 1     ; 0xEBB
B3D6                
B3D6bits            RES 1     ; 0xEBC
B3D7                
B3D7bits            RES 1     ; 0xEBD
CANSTAT_RO6         
CANSTAT_RO6bits     RES 1     ; 0xEBE
CANCON_RO6          
CANCON_RO6bits      RES 1     ; 0xEBF
B4CON               
B4CONbits           RES 1     ; 0xEC0
B4SIDH              
B4SIDHbits          RES 1     ; 0xEC1
B4SIDL              
B4SIDLbits          RES 1     ; 0xEC2
B4EIDH              
B4EIDHbits          RES 1     ; 0xEC3
B4EIDL              
B4EIDLbits          RES 1     ; 0xEC4
B4DLC               
B4DLCbits           RES 1     ; 0xEC5
B4D0                
B4D0bits            RES 1     ; 0xEC6
B4D1                
B4D1bits            RES 1     ; 0xEC7
B4D2                
B4D2bits            RES 1     ; 0xEC8
B4D3                
B4D3bits            RES 1     ; 0xEC9
B4D4                
B4D4bits            RES 1     ; 0xECA
B4D5                
B4D5bits            RES 1     ; 0xECB
B4D6                
B4D6bits            RES 1     ; 0xECC
B4D7                
B4D7bits            RES 1     ; 0xECD
CANSTAT_RO5         
CANSTAT_RO5bits     RES 1     ; 0xECE
CANCON_RO5          
CANCON_RO5bits      RES 1     ; 0xECF
B5CON               
B5CONbits           RES 1     ; 0xED0
B5SIDH              
B5SIDHbits          RES 1     ; 0xED1
B5SIDL              
B5SIDLbits          RES 1     ; 0xED2
B5EIDH              
B5EIDHbits          RES 1     ; 0xED3
B5EIDL              
B5EIDLbits          RES 1     ; 0xED4
B5DLC               
B5DLCbits           RES 1     ; 0xED5
B5D0                
B5D0bits            RES 1     ; 0xED6
B5D1                
B5D1bits            RES 1     ; 0xED7
B5D2                
B5D2bits            RES 1     ; 0xED8
B5D3                
B5D3bits            RES 1     ; 0xED9
B5D4                
B5D4bits            RES 1     ; 0xEDA
B5D5                
B5D5bits            RES 1     ; 0xEDB
B5D6                
B5D6bits            RES 1     ; 0xEDC
B5D7                
B5D7bits            RES 1     ; 0xEDD
CANSTAT_RO4         
CANSTAT_RO4bits     RES 1     ; 0xEDE
CANCON_RO4          
CANCON_RO4bits      RES 1     ; 0xEDF
RXF0SIDH            
RXF0SIDHbits        RES 1     ; 0xEE0
RXF0SIDL            
RXF0SIDLbits        RES 1     ; 0xEE1
RXF0EIDH            
RXF0EIDHbits        RES 1     ; 0xEE2
RXF0EIDL            
RXF0EIDLbits        RES 1     ; 0xEE3
RXF1SIDH            
RXF1SIDHbits        RES 1     ; 0xEE4
RXF1SIDL            
RXF1SIDLbits        RES 1     ; 0xEE5
RXF1EIDH            
RXF1EIDHbits        RES 1     ; 0xEE6
RXF1EIDL            
RXF1EIDLbits        RES 1     ; 0xEE7
RXF2SIDH            
RXF2SIDHbits        RES 1     ; 0xEE8
RXF2SIDL            
RXF2SIDLbits        RES 1     ; 0xEE9
RXF2EIDH            
RXF2EIDHbits        RES 1     ; 0xEEA
RXF2EIDL            
RXF2EIDLbits        RES 1     ; 0xEEB
RXF3SIDH            
RXF3SIDHbits        RES 1     ; 0xEEC
RXF3SIDL            
RXF3SIDLbits        RES 1     ; 0xEED
RXF3EIDH            
RXF3EIDHbits        RES 1     ; 0xEEE
RXF3EIDL            
RXF3EIDLbits        RES 1     ; 0xEEF
RXF4SIDH            
RXF4SIDHbits        RES 1     ; 0xEF0
RXF4SIDL            
RXF4SIDLbits        RES 1     ; 0xEF1
RXF4EIDH            
RXF4EIDHbits        RES 1     ; 0xEF2
RXF4EIDL            
RXF4EIDLbits        RES 1     ; 0xEF3
RXF5SIDH            
RXF5SIDHbits        RES 1     ; 0xEF4
RXF5SIDL            
RXF5SIDLbits        RES 1     ; 0xEF5
RXF5EIDH            
RXF5EIDHbits        RES 1     ; 0xEF6
RXF5EIDL            
RXF5EIDLbits        RES 1     ; 0xEF7
RXM0SIDH            
RXM0SIDHbits        RES 1     ; 0xEF8
RXM0SIDL            
RXM0SIDLbits        RES 1     ; 0xEF9
RXM0EIDH            
RXM0EIDHbits        RES 1     ; 0xEFA
RXM0EIDL            
RXM0EIDLbits        RES 1     ; 0xEFB
RXM1SIDH            
RXM1SIDHbits        RES 1     ; 0xEFC
RXM1SIDL            
RXM1SIDLbits        RES 1     ; 0xEFD
RXM1EIDH            
RXM1EIDHbits        RES 1     ; 0xEFE
RXM1EIDL            
RXM1EIDLbits        RES 1     ; 0xEFF

SFR_BANKED1         UDATA H'F00'
TXB2CON             
TXB2CONbits         RES 1     ; 0xF00
TXB2SIDH            
TXB2SIDHbits        RES 1     ; 0xF01
TXB2SIDL            
TXB2SIDLbits        RES 1     ; 0xF02
TXB2EIDH            
TXB2EIDHbits        RES 1     ; 0xF03
TXB2EIDL            
TXB2EIDLbits        RES 1     ; 0xF04
TXB2DLC             
TXB2DLCbits         RES 1     ; 0xF05
TXB2D0              
TXB2D0bits          RES 1     ; 0xF06
TXB2D1              
TXB2D1bits          RES 1     ; 0xF07
TXB2D2              
TXB2D2bits          RES 1     ; 0xF08
TXB2D3              
TXB2D3bits          RES 1     ; 0xF09
TXB2D4              
TXB2D4bits          RES 1     ; 0xF0A
TXB2D5              
TXB2D5bits          RES 1     ; 0xF0B
TXB2D6              
TXB2D6bits          RES 1     ; 0xF0C
TXB2D7              
TXB2D7bits          RES 1     ; 0xF0D
CANSTAT_RO3         
CANSTAT_RO3bits     RES 1     ; 0xF0E
CANCON_RO3          
CANCON_RO3bits      RES 1     ; 0xF0F
TXB1CON             
TXB1CONbits         RES 1     ; 0xF10
TXB1SIDH            
TXB1SIDHbits        RES 1     ; 0xF11
TXB1SIDL            
TXB1SIDLbits        RES 1     ; 0xF12
TXB1EIDH            
TXB1EIDHbits        RES 1     ; 0xF13
TXB1EIDL            
TXB1EIDLbits        RES 1     ; 0xF14
TXB1DLC             
TXB1DLCbits         RES 1     ; 0xF15
TXB1D0              
TXB1D0bits          RES 1     ; 0xF16
TXB1D1              
TXB1D1bits          RES 1     ; 0xF17
TXB1D2              
TXB1D2bits          RES 1     ; 0xF18
TXB1D3              
TXB1D3bits          RES 1     ; 0xF19
TXB1D4              
TXB1D4bits          RES 1     ; 0xF1A
TXB1D5              
TXB1D5bits          RES 1     ; 0xF1B
TXB1D6              
TXB1D6bits          RES 1     ; 0xF1C
TXB1D7              
TXB1D7bits          RES 1     ; 0xF1D
CANSTAT_RO2         
CANSTAT_RO2bits     RES 1     ; 0xF1E
CANCON_RO2          
CANCON_RO2bits      RES 1     ; 0xF1F
TXB0CON             
TXB0CONbits         RES 1     ; 0xF20
TXB0SIDH            
TXB0SIDHbits        RES 1     ; 0xF21
TXB0SIDL            
TXB0SIDLbits        RES 1     ; 0xF22
TXB0EIDH            
TXB0EIDHbits        RES 1     ; 0xF23
TXB0EIDL            
TXB0EIDLbits        RES 1     ; 0xF24
TXB0DLC             
TXB0DLCbits         RES 1     ; 0xF25
TXB0D0              
TXB0D0bits          RES 1     ; 0xF26
TXB0D1              
TXB0D1bits          RES 1     ; 0xF27
TXB0D2              
TXB0D2bits          RES 1     ; 0xF28
TXB0D3              
TXB0D3bits          RES 1     ; 0xF29
TXB0D4              
TXB0D4bits          RES 1     ; 0xF2A
TXB0D5              
TXB0D5bits          RES 1     ; 0xF2B
TXB0D6              
TXB0D6bits          RES 1     ; 0xF2C
TXB0D7              
TXB0D7bits          RES 1     ; 0xF2D
CANSTAT_RO1         
CANSTAT_RO1bits     RES 1     ; 0xF2E
CANCON_RO1          
CANCON_RO1bits      RES 1     ; 0xF2F
RXB1CON             
RXB1CONbits         RES 1     ; 0xF30
RXB1SIDH            
RXB1SIDHbits        RES 1     ; 0xF31
RXB1SIDL            
RXB1SIDLbits        RES 1     ; 0xF32
RXB1EIDH            
RXB1EIDHbits        RES 1     ; 0xF33
RXB1EIDL            
RXB1EIDLbits        RES 1     ; 0xF34
RXB1DLC             
RXB1DLCbits         RES 1     ; 0xF35
RXB1D0              
RXB1D0bits          RES 1     ; 0xF36
RXB1D1              
RXB1D1bits          RES 1     ; 0xF37
RXB1D2              
RXB1D2bits          RES 1     ; 0xF38
RXB1D3              
RXB1D3bits          RES 1     ; 0xF39
RXB1D4              
RXB1D4bits          RES 1     ; 0xF3A
RXB1D5              
RXB1D5bits          RES 1     ; 0xF3B
RXB1D6              
RXB1D6bits          RES 1     ; 0xF3C
RXB1D7              
RXB1D7bits          RES 1     ; 0xF3D
CANSTAT_RO0         
CANSTAT_RO0bits     RES 1     ; 0xF3E
CANCON_RO0          
CANCON_RO0bits      RES 1     ; 0xF3F

SFR_BANKED2         UDATA H'F42'
MDCARL              
MDCARLbits          RES 1     ; 0xF42
MDCARH              
MDCARHbits          RES 1     ; 0xF43
MDSRC               
MDSRCbits           RES 1     ; 0xF44
MDCON               
MDCONbits           RES 1     ; 0xF45
PSPCON              
PSPCONbits          RES 1     ; 0xF46
CCP5CON             
CCP5CONbits         RES 1     ; 0xF47
CCPR5               
CCPR5L              RES 1     ; 0xF48
CCPR5H              RES 1     ; 0xF49
CCP4CON             
CCP4CONbits         RES 1     ; 0xF4A
CCPR4               
CCPR4L              RES 1     ; 0xF4B
CCPR4H              RES 1     ; 0xF4C
CCP3CON             
CCP3CONbits         RES 1     ; 0xF4D
CCPR3               
CCPR3L              RES 1     ; 0xF4E
CCPR3H              RES 1     ; 0xF4F
CCP2CON             
CCP2CONbits         
ECCP2CON            
ECCP2CONbits        RES 1     ; 0xF50
CCPR2               
CCPR2L              RES 1     ; 0xF51
CCPR2H              RES 1     ; 0xF52
CTMUICON            
CTMUICONbits        RES 1     ; 0xF53
CTMUCONL            
CTMUCONLbits        RES 1     ; 0xF54
CTMUCONH            
CTMUCONHbits        RES 1     ; 0xF55
PADCFG1             
PADCFG1bits         RES 1     ; 0xF56
PMD2                
PMD2bits            RES 1     ; 0xF57
PMD1                
PMD1bits            RES 1     ; 0xF58
PMD0                
PMD0bits            RES 1     ; 0xF59
IOCB                
IOCBbits            RES 1     ; 0xF5A
WPUB                
WPUBbits            RES 1     ; 0xF5B
ANCON1              
ANCON1bits          RES 1     ; 0xF5C
ANCON0              
ANCON0bits          RES 1     ; 0xF5D
CM2CON              
CM2CONbits          
CM2CON1             
CM2CON1bits         RES 1     ; 0xF5E
CM1CON              
CM1CONbits          
CM1CON1             
CM1CON1bits         RES 1     ; 0xF5F

;*** Set all of memory to zeroes ***/
; Use FSR0 to increment through memory from address 0x0
; to the end of the last bank skipping sfrs and
; unimplemented addresses.

    CODE
__zero_memory
    movlw  0x41      ;low byte of (end address + 1)
    movwf  PRODL, 0
    lfsr   0, 0x0    ;start address
    movlw  0xE       ;high byte of (end address + 1)
    rcall zero_block
    return 0
zero_block
loop_h
    cpfslt FSR0H, 0
    bra    compare_l
    clrf   POSTINC0, 0
    bra    loop_h
compare_l
    movf   PRODL, 0, 0
loop_l
    cpfslt FSR0L, 0
    return 0
    clrf   POSTINC0, 0
    bra    loop_l

        GLOBAL __zero_memory

        GLOBAL RXERRCNTbits
        GLOBAL RXERRCNT
        GLOBAL TXERRCNTbits
        GLOBAL TXERRCNT
        GLOBAL BRGCON1bits
        GLOBAL BRGCON1
        GLOBAL BRGCON2bits
        GLOBAL BRGCON2
        GLOBAL BRGCON3bits
        GLOBAL BRGCON3
        GLOBAL RXFCON1bits
        GLOBAL RXFCON1
        GLOBAL RXFCON0bits
        GLOBAL RXFCON0
        GLOBAL RXF6SIDHbits
        GLOBAL RXF6SIDH
        GLOBAL RXF6SIDLbits
        GLOBAL RXF6SIDL
        GLOBAL RXF6EIDHbits
        GLOBAL RXF6EIDH
        GLOBAL RXF6EIDLbits
        GLOBAL RXF6EIDL
        GLOBAL RXF7SIDHbits
        GLOBAL RXF7SIDH
        GLOBAL RXF7SIDLbits
        GLOBAL RXF7SIDL
        GLOBAL RXF7EIDHbits
        GLOBAL RXF7EIDH
        GLOBAL RXF7EIDLbits
        GLOBAL RXF7EIDL
        GLOBAL RXF8SIDHbits
        GLOBAL RXF8SIDH
        GLOBAL RXF8SIDLbits
        GLOBAL RXF8SIDL
        GLOBAL RXF8EIDHbits
        GLOBAL RXF8EIDH
        GLOBAL RXF8EIDLbits
        GLOBAL RXF8EIDL
        GLOBAL RXF9SIDHbits
        GLOBAL RXF9SIDH
        GLOBAL RXF9SIDLbits
        GLOBAL RXF9SIDL
        GLOBAL RXF9EIDHbits
        GLOBAL RXF9EIDH
        GLOBAL RXF9EIDLbits
        GLOBAL RXF9EIDL
        GLOBAL RXF10SIDHbits
        GLOBAL RXF10SIDH
        GLOBAL RXF10SIDLbits
        GLOBAL RXF10SIDL
        GLOBAL RXF10EIDHbits
        GLOBAL RXF10EIDH
        GLOBAL RXF10EIDLbits
        GLOBAL RXF10EIDL
        GLOBAL RXF11SIDHbits
        GLOBAL RXF11SIDH
        GLOBAL RXF11SIDLbits
        GLOBAL RXF11SIDL
        GLOBAL RXF11EIDHbits
        GLOBAL RXF11EIDH
        GLOBAL RXF11EIDLbits
        GLOBAL RXF11EIDL
        GLOBAL RXF12SIDHbits
        GLOBAL RXF12SIDH
        GLOBAL RXF12SIDLbits
        GLOBAL RXF12SIDL
        GLOBAL RXF12EIDHbits
        GLOBAL RXF12EIDH
        GLOBAL RXF12EIDLbits
        GLOBAL RXF12EIDL
        GLOBAL RXF13SIDHbits
        GLOBAL RXF13SIDH
        GLOBAL RXF13SIDLbits
        GLOBAL RXF13SIDL
        GLOBAL RXF13EIDHbits
        GLOBAL RXF13EIDH
        GLOBAL RXF13EIDLbits
        GLOBAL RXF13EIDL
        GLOBAL RXF14SIDHbits
        GLOBAL RXF14SIDH
        GLOBAL RXF14SIDLbits
        GLOBAL RXF14SIDL
        GLOBAL RXF14EIDHbits
        GLOBAL RXF14EIDH
        GLOBAL RXF14EIDLbits
        GLOBAL RXF14EIDL
        GLOBAL RXF15SIDHbits
        GLOBAL RXF15SIDH
        GLOBAL RXF15SIDLbits
        GLOBAL RXF15SIDL
        GLOBAL RXF15EIDHbits
        GLOBAL RXF15EIDH
        GLOBAL RXF15EIDLbits
        GLOBAL RXF15EIDL
        GLOBAL SDFLCbits
        GLOBAL SDFLC
        GLOBAL RXFBCON0bits
        GLOBAL RXFBCON0
        GLOBAL RXFBCON1bits
        GLOBAL RXFBCON1
        GLOBAL RXFBCON2bits
        GLOBAL RXFBCON2
        GLOBAL RXFBCON3bits
        GLOBAL RXFBCON3
        GLOBAL RXFBCON4bits
        GLOBAL RXFBCON4
        GLOBAL RXFBCON5bits
        GLOBAL RXFBCON5
        GLOBAL RXFBCON6bits
        GLOBAL RXFBCON6
        GLOBAL RXFBCON7bits
        GLOBAL RXFBCON7
        GLOBAL MSEL0bits
        GLOBAL MSEL0
        GLOBAL MSEL1bits
        GLOBAL MSEL1
        GLOBAL MSEL2bits
        GLOBAL MSEL2
        GLOBAL MSEL3bits
        GLOBAL MSEL3
        GLOBAL BSEL0bits
        GLOBAL BSEL0
        GLOBAL BIE0bits
        GLOBAL BIE0
        GLOBAL TXBIEbits
        GLOBAL TXBIE
        GLOBAL B0CONbits
        GLOBAL B0CON
        GLOBAL B0SIDHbits
        GLOBAL B0SIDH
        GLOBAL B0SIDLbits
        GLOBAL B0SIDL
        GLOBAL B0EIDHbits
        GLOBAL B0EIDH
        GLOBAL B0EIDLbits
        GLOBAL B0EIDL
        GLOBAL B0DLCbits
        GLOBAL B0DLC
        GLOBAL B0D0bits
        GLOBAL B0D0
        GLOBAL B0D1bits
        GLOBAL B0D1
        GLOBAL B0D2bits
        GLOBAL B0D2
        GLOBAL B0D3bits
        GLOBAL B0D3
        GLOBAL B0D4bits
        GLOBAL B0D4
        GLOBAL B0D5bits
        GLOBAL B0D5
        GLOBAL B0D6bits
        GLOBAL B0D6
        GLOBAL B0D7bits
        GLOBAL B0D7
        GLOBAL CANSTAT_RO9bits
        GLOBAL CANSTAT_RO9
        GLOBAL CANCON_RO9bits
        GLOBAL CANCON_RO9
        GLOBAL B1CONbits
        GLOBAL B1CON
        GLOBAL B1SIDHbits
        GLOBAL B1SIDH
        GLOBAL B1SIDLbits
        GLOBAL B1SIDL
        GLOBAL B1EIDHbits
        GLOBAL B1EIDH
        GLOBAL B1EIDLbits
        GLOBAL B1EIDL
        GLOBAL B1DLCbits
        GLOBAL B1DLC
        GLOBAL B1D0bits
        GLOBAL B1D0
        GLOBAL B1D1bits
        GLOBAL B1D1
        GLOBAL B1D2bits
        GLOBAL B1D2
        GLOBAL B1D3bits
        GLOBAL B1D3
        GLOBAL B1D4bits
        GLOBAL B1D4
        GLOBAL B1D5bits
        GLOBAL B1D5
        GLOBAL B1D6bits
        GLOBAL B1D6
        GLOBAL B1D7bits
        GLOBAL B1D7
        GLOBAL CANSTAT_RO8bits
        GLOBAL CANSTAT_RO8
        GLOBAL CANCON_RO8bits
        GLOBAL CANCON_RO8
        GLOBAL B2CONbits
        GLOBAL B2CON
        GLOBAL B2SIDHbits
        GLOBAL B2SIDH
        GLOBAL B2SIDLbits
        GLOBAL B2SIDL
        GLOBAL B2EIDHbits
        GLOBAL B2EIDH
        GLOBAL B2EIDLbits
        GLOBAL B2EIDL
        GLOBAL B2DLCbits
        GLOBAL B2DLC
        GLOBAL B2D0bits
        GLOBAL B2D0
        GLOBAL B2D1bits
        GLOBAL B2D1
        GLOBAL B2D2bits
        GLOBAL B2D2
        GLOBAL B2D3bits
        GLOBAL B2D3
        GLOBAL B2D4bits
        GLOBAL B2D4
        GLOBAL B2D5bits
        GLOBAL B2D5
        GLOBAL B2D6bits
        GLOBAL B2D6
        GLOBAL B2D7bits
        GLOBAL B2D7
        GLOBAL CANSTAT_RO7bits
        GLOBAL CANSTAT_RO7
        GLOBAL CANCON_RO7bits
        GLOBAL CANCON_RO7
        GLOBAL B3CONbits
        GLOBAL B3CON
        GLOBAL B3SIDHbits
        GLOBAL B3SIDH
        GLOBAL B3SIDLbits
        GLOBAL B3SIDL
        GLOBAL B3EIDHbits
        GLOBAL B3EIDH
        GLOBAL B3EIDLbits
        GLOBAL B3EIDL
        GLOBAL B3DLCbits
        GLOBAL B3DLC
        GLOBAL B3D0bits
        GLOBAL B3D0
        GLOBAL B3D1bits
        GLOBAL B3D1
        GLOBAL B3D2bits
        GLOBAL B3D2
        GLOBAL B3D3bits
        GLOBAL B3D3
        GLOBAL B3D4bits
        GLOBAL B3D4
        GLOBAL B3D5bits
        GLOBAL B3D5
        GLOBAL B3D6bits
        GLOBAL B3D6
        GLOBAL B3D7bits
        GLOBAL B3D7
        GLOBAL CANSTAT_RO6bits
        GLOBAL CANSTAT_RO6
        GLOBAL CANCON_RO6bits
        GLOBAL CANCON_RO6
        GLOBAL B4CONbits
        GLOBAL B4CON
        GLOBAL B4SIDHbits
        GLOBAL B4SIDH
        GLOBAL B4SIDLbits
        GLOBAL B4SIDL
        GLOBAL B4EIDHbits
        GLOBAL B4EIDH
        GLOBAL B4EIDLbits
        GLOBAL B4EIDL
        GLOBAL B4DLCbits
        GLOBAL B4DLC
        GLOBAL B4D0bits
        GLOBAL B4D0
        GLOBAL B4D1bits
        GLOBAL B4D1
        GLOBAL B4D2bits
        GLOBAL B4D2
        GLOBAL B4D3bits
        GLOBAL B4D3
        GLOBAL B4D4bits
        GLOBAL B4D4
        GLOBAL B4D5bits
        GLOBAL B4D5
        GLOBAL B4D6bits
        GLOBAL B4D6
        GLOBAL B4D7bits
        GLOBAL B4D7
        GLOBAL CANSTAT_RO5bits
        GLOBAL CANSTAT_RO5
        GLOBAL CANCON_RO5bits
        GLOBAL CANCON_RO5
        GLOBAL B5CONbits
        GLOBAL B5CON
        GLOBAL B5SIDHbits
        GLOBAL B5SIDH
        GLOBAL B5SIDLbits
        GLOBAL B5SIDL
        GLOBAL B5EIDHbits
        GLOBAL B5EIDH
        GLOBAL B5EIDLbits
        GLOBAL B5EIDL
        GLOBAL B5DLCbits
        GLOBAL B5DLC
        GLOBAL B5D0bits
        GLOBAL B5D0
        GLOBAL B5D1bits
        GLOBAL B5D1
        GLOBAL B5D2bits
        GLOBAL B5D2
        GLOBAL B5D3bits
        GLOBAL B5D3
        GLOBAL B5D4bits
        GLOBAL B5D4
        GLOBAL B5D5bits
        GLOBAL B5D5
        GLOBAL B5D6bits
        GLOBAL B5D6
        GLOBAL B5D7bits
        GLOBAL B5D7
        GLOBAL CANSTAT_RO4bits
        GLOBAL CANSTAT_RO4
        GLOBAL CANCON_RO4bits
        GLOBAL CANCON_RO4
        GLOBAL RXF0SIDHbits
        GLOBAL RXF0SIDH
        GLOBAL RXF0SIDLbits
        GLOBAL RXF0SIDL
        GLOBAL RXF0EIDHbits
        GLOBAL RXF0EIDH
        GLOBAL RXF0EIDLbits
        GLOBAL RXF0EIDL
        GLOBAL RXF1SIDHbits
        GLOBAL RXF1SIDH
        GLOBAL RXF1SIDLbits
        GLOBAL RXF1SIDL
        GLOBAL RXF1EIDHbits
        GLOBAL RXF1EIDH
        GLOBAL RXF1EIDLbits
        GLOBAL RXF1EIDL
        GLOBAL RXF2SIDHbits
        GLOBAL RXF2SIDH
        GLOBAL RXF2SIDLbits
        GLOBAL RXF2SIDL
        GLOBAL RXF2EIDHbits
        GLOBAL RXF2EIDH
        GLOBAL RXF2EIDLbits
        GLOBAL RXF2EIDL
        GLOBAL RXF3SIDHbits
        GLOBAL RXF3SIDH
        GLOBAL RXF3SIDLbits
        GLOBAL RXF3SIDL
        GLOBAL RXF3EIDHbits
        GLOBAL RXF3EIDH
        GLOBAL RXF3EIDLbits
        GLOBAL RXF3EIDL
        GLOBAL RXF4SIDHbits
        GLOBAL RXF4SIDH
        GLOBAL RXF4SIDLbits
        GLOBAL RXF4SIDL
        GLOBAL RXF4EIDHbits
        GLOBAL RXF4EIDH
        GLOBAL RXF4EIDLbits
        GLOBAL RXF4EIDL
        GLOBAL RXF5SIDHbits
        GLOBAL RXF5SIDH
        GLOBAL RXF5SIDLbits
        GLOBAL RXF5SIDL
        GLOBAL RXF5EIDHbits
        GLOBAL RXF5EIDH
        GLOBAL RXF5EIDLbits
        GLOBAL RXF5EIDL
        GLOBAL RXM0SIDHbits
        GLOBAL RXM0SIDH
        GLOBAL RXM0SIDLbits
        GLOBAL RXM0SIDL
        GLOBAL RXM0EIDHbits
        GLOBAL RXM0EIDH
        GLOBAL RXM0EIDLbits
        GLOBAL RXM0EIDL
        GLOBAL RXM1SIDHbits
        GLOBAL RXM1SIDH
        GLOBAL RXM1SIDLbits
        GLOBAL RXM1SIDL
        GLOBAL RXM1EIDHbits
        GLOBAL RXM1EIDH
        GLOBAL RXM1EIDLbits
        GLOBAL RXM1EIDL
        GLOBAL TXB2CONbits
        GLOBAL TXB2CON
        GLOBAL TXB2SIDHbits
        GLOBAL TXB2SIDH
        GLOBAL TXB2SIDLbits
        GLOBAL TXB2SIDL
        GLOBAL TXB2EIDHbits
        GLOBAL TXB2EIDH
        GLOBAL TXB2EIDLbits
        GLOBAL TXB2EIDL
        GLOBAL TXB2DLCbits
        GLOBAL TXB2DLC
        GLOBAL TXB2D0bits
        GLOBAL TXB2D0
        GLOBAL TXB2D1bits
        GLOBAL TXB2D1
        GLOBAL TXB2D2bits
        GLOBAL TXB2D2
        GLOBAL TXB2D3bits
        GLOBAL TXB2D3
        GLOBAL TXB2D4bits
        GLOBAL TXB2D4
        GLOBAL TXB2D5bits
        GLOBAL TXB2D5
        GLOBAL TXB2D6bits
        GLOBAL TXB2D6
        GLOBAL TXB2D7bits
        GLOBAL TXB2D7
        GLOBAL CANSTAT_RO3bits
        GLOBAL CANSTAT_RO3
        GLOBAL CANCON_RO3bits
        GLOBAL CANCON_RO3
        GLOBAL TXB1CONbits
        GLOBAL TXB1CON
        GLOBAL TXB1SIDHbits
        GLOBAL TXB1SIDH
        GLOBAL TXB1SIDLbits
        GLOBAL TXB1SIDL
        GLOBAL TXB1EIDHbits
        GLOBAL TXB1EIDH
        GLOBAL TXB1EIDLbits
        GLOBAL TXB1EIDL
        GLOBAL TXB1DLCbits
        GLOBAL TXB1DLC
        GLOBAL TXB1D0bits
        GLOBAL TXB1D0
        GLOBAL TXB1D1bits
        GLOBAL TXB1D1
        GLOBAL TXB1D2bits
        GLOBAL TXB1D2
        GLOBAL TXB1D3bits
        GLOBAL TXB1D3
        GLOBAL TXB1D4bits
        GLOBAL TXB1D4
        GLOBAL TXB1D5bits
        GLOBAL TXB1D5
        GLOBAL TXB1D6bits
        GLOBAL TXB1D6
        GLOBAL TXB1D7bits
        GLOBAL TXB1D7
        GLOBAL CANSTAT_RO2bits
        GLOBAL CANSTAT_RO2
        GLOBAL CANCON_RO2bits
        GLOBAL CANCON_RO2
        GLOBAL TXB0CONbits
        GLOBAL TXB0CON
        GLOBAL TXB0SIDHbits
        GLOBAL TXB0SIDH
        GLOBAL TXB0SIDLbits
        GLOBAL TXB0SIDL
        GLOBAL TXB0EIDHbits
        GLOBAL TXB0EIDH
        GLOBAL TXB0EIDLbits
        GLOBAL TXB0EIDL
        GLOBAL TXB0DLCbits
        GLOBAL TXB0DLC
        GLOBAL TXB0D0bits
        GLOBAL TXB0D0
        GLOBAL TXB0D1bits
        GLOBAL TXB0D1
        GLOBAL TXB0D2bits
        GLOBAL TXB0D2
        GLOBAL TXB0D3bits
        GLOBAL TXB0D3
        GLOBAL TXB0D4bits
        GLOBAL TXB0D4
        GLOBAL TXB0D5bits
        GLOBAL TXB0D5
        GLOBAL TXB0D6bits
        GLOBAL TXB0D6
        GLOBAL TXB0D7bits
        GLOBAL TXB0D7
        GLOBAL CANSTAT_RO1bits
        GLOBAL CANSTAT_RO1
        GLOBAL CANCON_RO1bits
        GLOBAL CANCON_RO1
        GLOBAL RXB1CONbits
        GLOBAL RXB1CON
        GLOBAL RXB1SIDHbits
        GLOBAL RXB1SIDH
        GLOBAL RXB1SIDLbits
        GLOBAL RXB1SIDL
        GLOBAL RXB1EIDHbits
        GLOBAL RXB1EIDH
        GLOBAL RXB1EIDLbits
        GLOBAL RXB1EIDL
        GLOBAL RXB1DLCbits
        GLOBAL RXB1DLC
        GLOBAL RXB1D0bits
        GLOBAL RXB1D0
        GLOBAL RXB1D1bits
        GLOBAL RXB1D1
        GLOBAL RXB1D2bits
        GLOBAL RXB1D2
        GLOBAL RXB1D3bits
        GLOBAL RXB1D3
        GLOBAL RXB1D4bits
        GLOBAL RXB1D4
        GLOBAL RXB1D5bits
        GLOBAL RXB1D5
        GLOBAL RXB1D6bits
        GLOBAL RXB1D6
        GLOBAL RXB1D7bits
        GLOBAL RXB1D7
        GLOBAL CANSTAT_RO0bits
        GLOBAL CANSTAT_RO0
        GLOBAL CANCON_RO0bits
        GLOBAL CANCON_RO0
        GLOBAL MDCARLbits
        GLOBAL MDCARL
        GLOBAL MDCARHbits
        GLOBAL MDCARH
        GLOBAL MDSRCbits
        GLOBAL MDSRC
        GLOBAL MDCONbits
        GLOBAL MDCON
        GLOBAL PSPCONbits
        GLOBAL PSPCON
        GLOBAL CCP5CONbits
        GLOBAL CCP5CON
        GLOBAL CCPR5
        GLOBAL CCPR5L
        GLOBAL CCPR5H
        GLOBAL CCP4CONbits
        GLOBAL CCP4CON
        GLOBAL CCPR4
        GLOBAL CCPR4L
        GLOBAL CCPR4H
        GLOBAL CCP3CONbits
        GLOBAL CCP3CON
        GLOBAL CCPR3
        GLOBAL CCPR3L
        GLOBAL CCPR3H
        GLOBAL CCP2CONbits
        GLOBAL CCP2CON
        GLOBAL ECCP2CONbits
        GLOBAL ECCP2CON
        GLOBAL CCPR2
        GLOBAL CCPR2L
        GLOBAL CCPR2H
        GLOBAL CTMUICONbits
        GLOBAL CTMUICON
        GLOBAL CTMUCONLbits
        GLOBAL CTMUCONL
        GLOBAL CTMUCONHbits
        GLOBAL CTMUCONH
        GLOBAL PADCFG1bits
        GLOBAL PADCFG1
        GLOBAL PMD2bits
        GLOBAL PMD2
        GLOBAL PMD1bits
        GLOBAL PMD1
        GLOBAL PMD0bits
        GLOBAL PMD0
        GLOBAL IOCBbits
        GLOBAL IOCB
        GLOBAL WPUBbits
        GLOBAL WPUB
        GLOBAL ANCON1bits
        GLOBAL ANCON1
        GLOBAL ANCON0bits
        GLOBAL ANCON0
        GLOBAL CM2CONbits
        GLOBAL CM2CON
        GLOBAL CM2CON1bits
        GLOBAL CM2CON1
        GLOBAL CM1CONbits
        GLOBAL CM1CON
        GLOBAL CM1CON1bits
        GLOBAL CM1CON1
        GLOBAL RXB0CONbits
        GLOBAL RXB0CON
        GLOBAL RXB0SIDHbits
        GLOBAL RXB0SIDH
        GLOBAL RXB0SIDLbits
        GLOBAL RXB0SIDL
        GLOBAL RXB0EIDHbits
        GLOBAL RXB0EIDH
        GLOBAL RXB0EIDLbits
        GLOBAL RXB0EIDL
        GLOBAL RXB0DLCbits
        GLOBAL RXB0DLC
        GLOBAL RXB0D0bits
        GLOBAL RXB0D0
        GLOBAL RXB0D1bits
        GLOBAL RXB0D1
        GLOBAL RXB0D2bits
        GLOBAL RXB0D2
        GLOBAL RXB0D3bits
        GLOBAL RXB0D3
        GLOBAL RXB0D4bits
        GLOBAL RXB0D4
        GLOBAL RXB0D5bits
        GLOBAL RXB0D5
        GLOBAL RXB0D6bits
        GLOBAL RXB0D6
        GLOBAL RXB0D7bits
        GLOBAL RXB0D7
        GLOBAL CANSTATbits
        GLOBAL CANSTAT
        GLOBAL CANCONbits
        GLOBAL CANCON
        GLOBAL CIOCONbits
        GLOBAL CIOCON
        GLOBAL COMSTATbits
        GLOBAL COMSTAT
        GLOBAL ECANCONbits
        GLOBAL ECANCON
        GLOBAL EEDATA
        GLOBAL EEADR
        GLOBAL EEADRH
        GLOBAL PIE5bits
        GLOBAL PIE5
        GLOBAL PIR5bits
        GLOBAL PIR5
        GLOBAL IPR5bits
        GLOBAL IPR5
        GLOBAL TXREG2
        GLOBAL RCREG2
        GLOBAL SPBRG2
        GLOBAL SPBRGH2
        GLOBAL SPBRGH1
        GLOBAL EECON2
        GLOBAL EECON1bits
        GLOBAL EECON1
        GLOBAL PORTAbits
        GLOBAL PORTA
        GLOBAL PORTBbits
        GLOBAL PORTB
        GLOBAL PORTCbits
        GLOBAL PORTC
        GLOBAL PORTDbits
        GLOBAL PORTD
        GLOBAL PORTEbits
        GLOBAL PORTE
        GLOBAL PORTFbits
        GLOBAL PORTF
        GLOBAL PORTGbits
        GLOBAL PORTG
        GLOBAL TMR4
        GLOBAL T4CONbits
        GLOBAL T4CON
        GLOBAL LATAbits
        GLOBAL LATA
        GLOBAL LATBbits
        GLOBAL LATB
        GLOBAL LATCbits
        GLOBAL LATC
        GLOBAL LATDbits
        GLOBAL LATD
        GLOBAL LATEbits
        GLOBAL LATE
        GLOBAL LATFbits
        GLOBAL LATF
        GLOBAL LATGbits
        GLOBAL LATG
        GLOBAL SLRCONbits
        GLOBAL SLRCON
        GLOBAL ODCONbits
        GLOBAL ODCON
        GLOBAL TRISAbits
        GLOBAL TRISA
        GLOBAL TRISBbits
        GLOBAL TRISB
        GLOBAL TRISCbits
        GLOBAL TRISC
        GLOBAL TRISDbits
        GLOBAL TRISD
        GLOBAL TRISEbits
        GLOBAL TRISE
        GLOBAL TRISFbits
        GLOBAL TRISF
        GLOBAL TRISGbits
        GLOBAL TRISG
        GLOBAL CCPTMRSbits
        GLOBAL CCPTMRS
        GLOBAL REFOCONbits
        GLOBAL REFOCON
        GLOBAL OSCTUNEbits
        GLOBAL OSCTUNE
        GLOBAL PSTR1CONbits
        GLOBAL PSTR1CON
        GLOBAL PIE1bits
        GLOBAL PIE1
        GLOBAL PIR1bits
        GLOBAL PIR1
        GLOBAL IPR1bits
        GLOBAL IPR1
        GLOBAL PIE2bits
        GLOBAL PIE2
        GLOBAL PIR2bits
        GLOBAL PIR2
        GLOBAL IPR2bits
        GLOBAL IPR2
        GLOBAL PIE3bits
        GLOBAL PIE3
        GLOBAL PIR3bits
        GLOBAL PIR3
        GLOBAL IPR3bits
        GLOBAL IPR3
        GLOBAL RCSTA2bits
        GLOBAL RCSTA2
        GLOBAL BAUDCON1bits
        GLOBAL BAUDCON1
        GLOBAL HLVDCONbits
        GLOBAL HLVDCON
        GLOBAL PR4
        GLOBAL T1GCONbits
        GLOBAL T1GCON
        GLOBAL RCSTAbits
        GLOBAL RCSTA
        GLOBAL RCSTA1bits
        GLOBAL RCSTA1
        GLOBAL TXSTAbits
        GLOBAL TXSTA
        GLOBAL TXSTA1bits
        GLOBAL TXSTA1
        GLOBAL TXREG
        GLOBAL TXREG1
        GLOBAL RCREG
        GLOBAL RCREG1
        GLOBAL SPBRG
        GLOBAL SPBRG1
        GLOBAL T3GCONbits
        GLOBAL T3GCON
        GLOBAL T3CONbits
        GLOBAL T3CON
        GLOBAL TMR3L
        GLOBAL TMR3H
        GLOBAL CMSTATbits
        GLOBAL CMSTAT
        GLOBAL CMSTATUSbits
        GLOBAL CMSTATUS
        GLOBAL CVRCONbits
        GLOBAL CVRCON
        GLOBAL PIE4bits
        GLOBAL PIE4
        GLOBAL PIR4bits
        GLOBAL PIR4
        GLOBAL IPR4bits
        GLOBAL IPR4
        GLOBAL BAUDCON2bits
        GLOBAL BAUDCON2
        GLOBAL TXSTA2bits
        GLOBAL TXSTA2
        GLOBAL CCP1CONbits
        GLOBAL CCP1CON
        GLOBAL ECCP1CONbits
        GLOBAL ECCP1CON
        GLOBAL CCPR1
        GLOBAL CCPR1L
        GLOBAL CCPR1H
        GLOBAL ECCP1DELbits
        GLOBAL ECCP1DEL
        GLOBAL PWM1CONbits
        GLOBAL PWM1CON
        GLOBAL ECCP1ASbits
        GLOBAL ECCP1AS
        GLOBAL ADCON2bits
        GLOBAL ADCON2
        GLOBAL ADCON1bits
        GLOBAL ADCON1
        GLOBAL ADCON0bits
        GLOBAL ADCON0
        GLOBAL ADRES
        GLOBAL ADRESL
        GLOBAL ADRESH
        GLOBAL SSPCON2bits
        GLOBAL SSPCON2
        GLOBAL SSPCON1bits
        GLOBAL SSPCON1
        GLOBAL SSPSTATbits
        GLOBAL SSPSTAT
        GLOBAL SSPADDbits
        GLOBAL SSPADD
        GLOBAL SSPBUF
        GLOBAL T2CONbits
        GLOBAL T2CON
        GLOBAL PR2
        GLOBAL TMR2
        GLOBAL T1CONbits
        GLOBAL T1CON
        GLOBAL TMR1L
        GLOBAL TMR1H
        GLOBAL RCONbits
        GLOBAL RCON
        GLOBAL WDTCONbits
        GLOBAL WDTCON
        GLOBAL OSCCON2bits
        GLOBAL OSCCON2
        GLOBAL OSCCONbits
        GLOBAL OSCCON
        GLOBAL T0CONbits
        GLOBAL T0CON
        GLOBAL TMR0L
        GLOBAL TMR0H
        GLOBAL STATUSbits
        GLOBAL STATUS
        GLOBAL FSR2
        GLOBAL FSR2L
        GLOBAL FSR2Hbits
        GLOBAL FSR2H
        GLOBAL PLUSW2
        GLOBAL PREINC2
        GLOBAL POSTDEC2
        GLOBAL POSTINC2
        GLOBAL INDF2
        GLOBAL BSRbits
        GLOBAL BSR
        GLOBAL FSR1
        GLOBAL FSR1L
        GLOBAL FSR1Hbits
        GLOBAL FSR1H
        GLOBAL PLUSW1
        GLOBAL PREINC1
        GLOBAL POSTDEC1
        GLOBAL POSTINC1
        GLOBAL INDF1
        GLOBAL WREG
        GLOBAL FSR0
        GLOBAL FSR0L
        GLOBAL FSR0Hbits
        GLOBAL FSR0H
        GLOBAL PLUSW0
        GLOBAL PREINC0
        GLOBAL POSTDEC0
        GLOBAL POSTINC0
        GLOBAL INDF0
        GLOBAL INTCON3bits
        GLOBAL INTCON3
        GLOBAL INTCON2bits
        GLOBAL INTCON2
        GLOBAL INTCONbits
        GLOBAL INTCON
        GLOBAL INTCON1bits
        GLOBAL INTCON1
        GLOBAL PROD
        GLOBAL PRODL
        GLOBAL PRODH
        GLOBAL TABLAT
        GLOBAL TBLPTR
        GLOBAL TBLPTRL
        GLOBAL TBLPTRH
        GLOBAL TBLPTRUbits
        GLOBAL TBLPTRU
        GLOBAL PC
        GLOBAL PCL
        GLOBAL PCLATH
        GLOBAL PCLATUbits
        GLOBAL PCLATU
        GLOBAL STKPTRbits
        GLOBAL STKPTR
        GLOBAL TOS
        GLOBAL TOSL
        GLOBAL TOSH
        GLOBAL TOSUbits
        GLOBAL TOSU

;-------------------------------------------------------------------------

        LIST
        END
