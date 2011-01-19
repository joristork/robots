        LIST P=18F4680
        NOLIST
;-------------------------------------------------------------------------
; MPLAB-Cxx  PIC18F4680 processor definition module
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
BRGCON1             
BRGCON1bits         RES 1     ; 0xF70
BRGCON2             
BRGCON2bits         RES 1     ; 0xF71
BRGCON3             
BRGCON3bits         RES 1     ; 0xF72
CIOCON              
CIOCONbits          RES 1     ; 0xF73
COMSTAT             
COMSTATbits         RES 1     ; 0xF74
RXERRCNT            
RXERRCNTbits        RES 1     ; 0xF75
TXERRCNT            
TXERRCNTbits        RES 1     ; 0xF76
ECANCON             
ECANCONbits         RES 1     ; 0xF77

SFR_UNBANKED1       UDATA_ACS H'F80'
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
                    RES 4
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
                    RES 4
DDRA                
DDRAbits            
TRISA               
TRISAbits           RES 1     ; 0xF92
DDRB                
DDRBbits            
TRISB               
TRISBbits           RES 1     ; 0xF93
DDRC                
DDRCbits            
TRISC               
TRISCbits           RES 1     ; 0xF94
DDRD                
DDRDbits            
TRISD               
TRISDbits           RES 1     ; 0xF95
DDRE                
DDREbits            
TRISE               
TRISEbits           RES 1     ; 0xF96
                    RES 4
OSCTUNE             
OSCTUNEbits         RES 1     ; 0xF9B
                    RES 1
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
EECON1              
EECON1bits          RES 1     ; 0xFA6
EECON2              RES 1     ; 0xFA7
EEDATA              RES 1     ; 0xFA8
EEADR               RES 1     ; 0xFA9
EEADRH              RES 1     ; 0xFAA
RCSTA               
RCSTAbits           RES 1     ; 0xFAB
TXSTA               
TXSTAbits           RES 1     ; 0xFAC
TXREG               RES 1     ; 0xFAD
RCREG               RES 1     ; 0xFAE
SPBRG               RES 1     ; 0xFAF
SPBRGH              RES 1     ; 0xFB0
T3CON               
T3CONbits           RES 1     ; 0xFB1
TMR3L               RES 1     ; 0xFB2
TMR3H               RES 1     ; 0xFB3
CMCON               
CMCONbits           RES 1     ; 0xFB4
CVRCON              
CVRCONbits          RES 1     ; 0xFB5
ECCP1AS             
ECCP1ASbits         RES 1     ; 0xFB6
ECCP1DEL            
ECCP1DELbits        RES 1     ; 0xFB7
BAUDCON             
BAUDCONbits         
BAUDCTL             
BAUDCTLbits         RES 1     ; 0xFB8
                    RES 1
ECCP1CON            
ECCP1CONbits        RES 1     ; 0xFBA
ECCPR1              
ECCPR1L             RES 1     ; 0xFBB
ECCPR1H             RES 1     ; 0xFBC
CCP1CON             
CCP1CONbits         RES 1     ; 0xFBD
CCPR1               
CCPR1L              RES 1     ; 0xFBE
CCPR1H              RES 1     ; 0xFBF
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
SSPADD              RES 1     ; 0xFC8
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
HLVDCON             
HLVDCONbits         
LVDCON              
LVDCONbits          RES 1     ; 0xFD2
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
FSR2H               RES 1     ; 0xFDA
PLUSW2              RES 1     ; 0xFDB
PREINC2             RES 1     ; 0xFDC
POSTDEC2            RES 1     ; 0xFDD
POSTINC2            RES 1     ; 0xFDE
INDF2               RES 1     ; 0xFDF
BSR                 RES 1     ; 0xFE0
FSR1                
FSR1L               RES 1     ; 0xFE1
FSR1H               RES 1     ; 0xFE2
PLUSW1              RES 1     ; 0xFE3
PREINC1             RES 1     ; 0xFE4
POSTDEC1            RES 1     ; 0xFE5
POSTINC1            RES 1     ; 0xFE6
INDF1               RES 1     ; 0xFE7
WREG                RES 1     ; 0xFE8
FSR0                
FSR0L               RES 1     ; 0xFE9
FSR0H               RES 1     ; 0xFEA
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
INTCONbits          RES 1     ; 0xFF2
PROD                
PRODL               RES 1     ; 0xFF3
PRODH               RES 1     ; 0xFF4
TABLAT              RES 1     ; 0xFF5
TBLPTR              
TBLPTRL             RES 1     ; 0xFF6
TBLPTRH             RES 1     ; 0xFF7
TBLPTRU             RES 1     ; 0xFF8
PC                  
PCL                 RES 1     ; 0xFF9
PCLATH              RES 1     ; 0xFFA
PCLATU              RES 1     ; 0xFFB
STKPTR              
STKPTRbits          RES 1     ; 0xFFC
TOS                 
TOSL                RES 1     ; 0xFFD
TOSH                RES 1     ; 0xFFE
TOSU                RES 1     ; 0xFFF

SFR_BANKED0         UDATA H'D60'

RXF6SIDH            
RXF6SIDHbits        RES 1     ; 0xD60
RXF6SIDL            
RXF6SIDLbits        RES 1     ; 0xD61
RXF6EIDH            
RXF6EIDHbits        RES 1     ; 0xD62
RXF6EIDL            
RXF6EIDLbits        RES 1     ; 0xD63
RXF7SIDH            
RXF7SIDHbits        RES 1     ; 0xD64
RXF7SIDL            
RXF7SIDLbits        RES 1     ; 0xD65
RXF7EIDH            
RXF7EIDHbits        RES 1     ; 0xD66
RXF7EIDL            
RXF7EIDLbits        RES 1     ; 0xD67
RXF8SIDH            
RXF8SIDHbits        RES 1     ; 0xD68
RXF8SIDL            
RXF8SIDLbits        RES 1     ; 0xD69
RXF8EIDH            
RXF8EIDHbits        RES 1     ; 0xD6A
RXF8EIDL            
RXF8EIDLbits        RES 1     ; 0xD6B

SFR_BANKED1         UDATA H'D70'
RXF9SIDH            
RXF9SIDHbits        RES 1     ; 0xD70
RXF9SIDL            
RXF9SIDLbits        RES 1     ; 0xD71
RXF9EIDH            
RXF9EIDHbits        RES 1     ; 0xD72
RXF9EIDL            
RXF9EIDLbits        RES 1     ; 0xD73
RXF10SIDH           
RXF10SIDHbits       RES 1     ; 0xD74
RXF10SIDL           
RXF10SIDLbits       RES 1     ; 0xD75
RXF10EIDH           
RXF10EIDHbits       RES 1     ; 0xD76
RXF10EIDL           
RXF10EIDLbits       RES 1     ; 0xD77
RXF11SIDH           
RXF11SIDHbits       RES 1     ; 0xD78
RXF11SIDL           
RXF11SIDLbits       RES 1     ; 0xD79
RXF11EIDH           
RXF11EIDHbits       RES 1     ; 0xD7A
RXF11EIDL           
RXF11EIDLbits       RES 1     ; 0xD7B

SFR_BANKED2         UDATA H'D80'
RXF12SIDH           
RXF12SIDHbits       RES 1     ; 0xD80
RXF12SIDL           
RXF12SIDLbits       RES 1     ; 0xD81
RXF12EIDH           
RXF12EIDHbits       RES 1     ; 0xD82
RXF12EIDL           
RXF12EIDLbits       RES 1     ; 0xD83
RXF13SIDH           
RXF13SIDHbits       RES 1     ; 0xD84
RXF13SIDL           
RXF13SIDLbits       RES 1     ; 0xD85
RXF13EIDH           
RXF13EIDHbits       RES 1     ; 0xD86
RXF13EIDL           
RXF13EIDLbits       RES 1     ; 0xD87
RXF14SIDH           
RXF14SIDHbits       RES 1     ; 0xD88
RXF14SIDL           
RXF14SIDLbits       RES 1     ; 0xD89
RXF14EIDH           
RXF14EIDHbits       RES 1     ; 0xD8A
RXF14EIDL           
RXF14EIDLbits       RES 1     ; 0xD8B

SFR_BANKED3         UDATA H'D90'
RXF15SIDH           
RXF15SIDHbits       RES 1     ; 0xD90
RXF15SIDL           
RXF15SIDLbits       RES 1     ; 0xD91
RXF15EIDH           
RXF15EIDHbits       RES 1     ; 0xD92
RXF15EIDL           
RXF15EIDLbits       RES 1     ; 0xD93

SFR_BANKED4         UDATA H'DD4'
RXFCON0             
RXFCON0bits         RES 1     ; 0xDD4
RXFCON1             
RXFCON1bits         RES 1     ; 0xDD5

SFR_BANKED5         UDATA H'DD8'
SDFLC               
SDFLCbits           RES 1     ; 0xDD8

SFR_BANKED6         UDATA H'DE0'
RXFBCON0            
RXFBCON0bits        RES 1     ; 0xDE0
RXFBCON1            
RXFBCON1bits        RES 1     ; 0xDE1
RXFBCON2            
RXFBCON2bits        RES 1     ; 0xDE2
RXFBCON3            
RXFBCON3bits        RES 1     ; 0xDE3
RXFBCON4            
RXFBCON4bits        RES 1     ; 0xDE4
RXFBCON5            
RXFBCON5bits        RES 1     ; 0xDE5
RXFBCON6            
RXFBCON6bits        RES 1     ; 0xDE6
RXFBCON7            
RXFBCON7bits        RES 1     ; 0xDE7

SFR_BANKED7         UDATA H'DF0'
MSEL0               
MSEL0bits           RES 1     ; 0xDF0
MSEL1               
MSEL1bits           RES 1     ; 0xDF1
MSEL2               
MSEL2bits           RES 1     ; 0xDF2
MSEL3               
MSEL3bits           RES 1     ; 0xDF3

SFR_BANKED8         UDATA H'DF8'
BSEL0               
BSEL0bits           RES 1     ; 0xDF8

SFR_BANKED9         UDATA H'DFA'
BIE0                
BIE0bits            RES 1     ; 0xDFA

SFR_BANKED10        UDATA H'DFC'
TXBIE               
TXBIEbits           RES 1     ; 0xDFC

SFR_BANKED11        UDATA H'E20'
B0CON               
B0CONbits           RES 1     ; 0xE20
B0SIDH              
B0SIDHbits          RES 1     ; 0xE21
B0SIDL              
B0SIDLbits          RES 1     ; 0xE22
B0EIDH              
B0EIDHbits          RES 1     ; 0xE23
B0EIDL              
B0EIDLbits          RES 1     ; 0xE24
B0DLC               
B0DLCbits           RES 1     ; 0xE25
B0D0                
B0D0bits            RES 1     ; 0xE26
B0D1                
B0D1bits            RES 1     ; 0xE27
B0D2                
B0D2bits            RES 1     ; 0xE28
B0D3                
B0D3bits            RES 1     ; 0xE29
B0D4                
B0D4bits            RES 1     ; 0xE2A
B0D5                
B0D5bits            RES 1     ; 0xE2B
B0D6                
B0D6bits            RES 1     ; 0xE2C
B0D7                
B0D7bits            RES 1     ; 0xE2D
CANSTAT_RO9         
CANSTAT_RO9bits     RES 1     ; 0xE2E
CANCON_RO9          
CANCON_RO9bits      RES 1     ; 0xE2F
B1CON               
B1CONbits           RES 1     ; 0xE30
B1SIDH              
B1SIDHbits          RES 1     ; 0xE31
B1SIDL              
B1SIDLbits          RES 1     ; 0xE32
B1EIDH              
B1EIDHbits          RES 1     ; 0xE33
B1EIDL              
B1EIDLbits          RES 1     ; 0xE34
B1DLC               
B1DLCbits           RES 1     ; 0xE35
B1D0                
B1D0bits            RES 1     ; 0xE36
B1D1                
B1D1bits            RES 1     ; 0xE37
B1D2                
B1D2bits            RES 1     ; 0xE38
B1D3                
B1D3bits            RES 1     ; 0xE39
B1D4                
B1D4bits            RES 1     ; 0xE3A
B1D5                
B1D5bits            RES 1     ; 0xE3B
B1D6                
B1D6bits            RES 1     ; 0xE3C
B1D7                
B1D7bits            RES 1     ; 0xE3D
CANSTAT_RO8         
CANSTAT_RO8bits     RES 1     ; 0xE3E
CANCON_RO8          
CANCON_RO8bits      RES 1     ; 0xE3F
B2CON               
B2CONbits           RES 1     ; 0xE40
B2SIDH              
B2SIDHbits          RES 1     ; 0xE41
B2SIDL              
B2SIDLbits          RES 1     ; 0xE42
B2EIDH              
B2EIDHbits          RES 1     ; 0xE43
B2EIDL              
B2EIDLbits          RES 1     ; 0xE44
B2DLC               
B2DLCbits           RES 1     ; 0xE45
B2D0                
B2D0bits            RES 1     ; 0xE46
B2D1                
B2D1bits            RES 1     ; 0xE47
B2D2                
B2D2bits            RES 1     ; 0xE48
B2D3                
B2D3bits            RES 1     ; 0xE49
B2D4                
B2D4bits            RES 1     ; 0xE4A
B2D5                
B2D5bits            RES 1     ; 0xE4B
B2D6                
B2D6bits            RES 1     ; 0xE4C
B2D7                
B2D7bits            RES 1     ; 0xE4D
CANSTAT_RO7         
CANSTAT_RO7bits     RES 1     ; 0xE4E
CANCON_RO7          
CANCON_RO7bits      RES 1     ; 0xE4F
B3CON               
B3CONbits           RES 1     ; 0xE50
B3SIDH              
B3SIDHbits          RES 1     ; 0xE51
B3SIDL              
B3SIDLbits          RES 1     ; 0xE52
B3EIDH              
B3EIDHbits          RES 1     ; 0xE53
B3EIDL              
B3EIDLbits          RES 1     ; 0xE54
B3DLC               
B3DLCbits           RES 1     ; 0xE55
B3D0                
B3D0bits            RES 1     ; 0xE56
B3D1                
B3D1bits            RES 1     ; 0xE57
B3D2                
B3D2bits            RES 1     ; 0xE58
B3D3                
B3D3bits            RES 1     ; 0xE59
B3D4                
B3D4bits            RES 1     ; 0xE5A
B3D5                
B3D5bits            RES 1     ; 0xE5B
B3D6                
B3D6bits            RES 1     ; 0xE5C
B3D7                
B3D7bits            RES 1     ; 0xE5D
CANSTAT_RO6         
CANSTAT_RO6bits     RES 1     ; 0xE5E
CANCON_RO6          
CANCON_RO6bits      RES 1     ; 0xE5F
B4CON               
B4CONbits           RES 1     ; 0xE60
B4SIDH              
B4SIDHbits          RES 1     ; 0xE61
B4SIDL              
B4SIDLbits          RES 1     ; 0xE62
B4EIDH              
B4EIDHbits          RES 1     ; 0xE63
B4EIDL              
B4EIDLbits          RES 1     ; 0xE64
B4DLC               
B4DLCbits           RES 1     ; 0xE65
B4D0                
B4D0bits            RES 1     ; 0xE66
B4D1                
B4D1bits            RES 1     ; 0xE67
B4D2                
B4D2bits            RES 1     ; 0xE68
B4D3                
B4D3bits            RES 1     ; 0xE69
B4D4                
B4D4bits            RES 1     ; 0xE6A
B4D5                
B4D5bits            RES 1     ; 0xE6B
B4D6                
B4D6bits            RES 1     ; 0xE6C
B4D7                
B4D7bits            RES 1     ; 0xE6D
CANSTAT_RO5         
CANSTAT_RO5bits     RES 1     ; 0xE6E
CANCON_RO5          
CANCON_RO5bits      RES 1     ; 0xE6F
B5CON               
B5CONbits           RES 1     ; 0xE70
B5SIDH              
B5SIDHbits          RES 1     ; 0xE71
B5SIDL              
B5SIDLbits          RES 1     ; 0xE72
B5EIDH              
B5EIDHbits          RES 1     ; 0xE73
B5EIDL              
B5EIDLbits          RES 1     ; 0xE74
B5DLC               
B5DLCbits           RES 1     ; 0xE75
B5D0                
B5D0bits            RES 1     ; 0xE76
B5D1                
B5D1bits            RES 1     ; 0xE77
B5D2                
B5D2bits            RES 1     ; 0xE78
B5D3                
B5D3bits            RES 1     ; 0xE79
B5D4                
B5D4bits            RES 1     ; 0xE7A
B5D5                
B5D5bits            RES 1     ; 0xE7B
B5D6                
B5D6bits            RES 1     ; 0xE7C
B5D7                
B5D7bits            RES 1     ; 0xE7D
CANSTAT_RO4         
CANSTAT_RO4bits     RES 1     ; 0xE7E
CANCON_RO4          
CANCON_RO4bits      RES 1     ; 0xE7F

SFR_BANKED12        UDATA H'F00'
RXF0SIDH            
RXF0SIDHbits        RES 1     ; 0xF00
RXF0SIDL            
RXF0SIDLbits        RES 1     ; 0xF01
RXF0EIDH            
RXF0EIDHbits        RES 1     ; 0xF02
RXF0EIDL            
RXF0EIDLbits        RES 1     ; 0xF03
RXF1SIDH            
RXF1SIDHbits        RES 1     ; 0xF04
RXF1SIDL            
RXF1SIDLbits        RES 1     ; 0xF05
RXF1EIDH            
RXF1EIDHbits        RES 1     ; 0xF06
RXF1EIDL            
RXF1EIDLbits        RES 1     ; 0xF07
RXF2SIDH            
RXF2SIDHbits        RES 1     ; 0xF08
RXF2SIDL            
RXF2SIDLbits        RES 1     ; 0xF09
RXF2EIDH            
RXF2EIDHbits        RES 1     ; 0xF0A
RXF2EIDL            
RXF2EIDLbits        RES 1     ; 0xF0B
RXF3SIDH            
RXF3SIDHbits        RES 1     ; 0xF0C
RXF3SIDL            
RXF3SIDLbits        RES 1     ; 0xF0D
RXF3EIDH            
RXF3EIDHbits        RES 1     ; 0xF0E
RXF3EIDL            
RXF3EIDLbits        RES 1     ; 0xF0F
RXF4SIDH            
RXF4SIDHbits        RES 1     ; 0xF10
RXF4SIDL            
RXF4SIDLbits        RES 1     ; 0xF11
RXF4EIDH            
RXF4EIDHbits        RES 1     ; 0xF12
RXF4EIDL            
RXF4EIDLbits        RES 1     ; 0xF13
RXF5SIDH            
RXF5SIDHbits        RES 1     ; 0xF14
RXF5SIDL            
RXF5SIDLbits        RES 1     ; 0xF15
RXF5EIDH            
RXF5EIDHbits        RES 1     ; 0xF16
RXF5EIDL            
RXF5EIDLbits        RES 1     ; 0xF17
RXM0SIDH            
RXM0SIDHbits        RES 1     ; 0xF18
RXM0SIDL            
RXM0SIDLbits        RES 1     ; 0xF19
RXM0EIDH            
RXM0EIDHbits        RES 1     ; 0xF1A
RXM0EIDL            
RXM0EIDLbits        RES 1     ; 0xF1B
RXM1SIDH            
RXM1SIDHbits        RES 1     ; 0xF1C
RXM1SIDL            
RXM1SIDLbits        RES 1     ; 0xF1D
RXM1EIDH            
RXM1EIDHbits        RES 1     ; 0xF1E
RXM1EIDL            
RXM1EIDLbits        RES 1     ; 0xF1F
TXB2CON             
TXB2CONbits         RES 1     ; 0xF20
TXB2SIDH            
TXB2SIDHbits        RES 1     ; 0xF21
TXB2SIDL            
TXB2SIDLbits        RES 1     ; 0xF22
TXB2EIDH            
TXB2EIDHbits        RES 1     ; 0xF23
TXB2EIDL            
TXB2EIDLbits        RES 1     ; 0xF24
TXB2DLC             
TXB2DLCbits         RES 1     ; 0xF25
TXB2D0              
TXB2D0bits          RES 1     ; 0xF26
TXB2D1              
TXB2D1bits          RES 1     ; 0xF27
TXB2D2              
TXB2D2bits          RES 1     ; 0xF28
TXB2D3              
TXB2D3bits          RES 1     ; 0xF29
TXB2D4              
TXB2D4bits          RES 1     ; 0xF2A
TXB2D5              
TXB2D5bits          RES 1     ; 0xF2B
TXB2D6              
TXB2D6bits          RES 1     ; 0xF2C
TXB2D7              
TXB2D7bits          RES 1     ; 0xF2D
CANSTAT_RO3         
CANSTAT_RO3bits     RES 1     ; 0xF2E
CANCON_RO3          
CANCON_RO3bits      RES 1     ; 0xF2F
TXB1CON             
TXB1CONbits         RES 1     ; 0xF30
TXB1SIDH            
TXB1SIDHbits        RES 1     ; 0xF31
TXB1SIDL            
TXB1SIDLbits        RES 1     ; 0xF32
TXB1EIDH            
TXB1EIDHbits        RES 1     ; 0xF33
TXB1EIDL            
TXB1EIDLbits        RES 1     ; 0xF34
TXB1DLC             
TXB1DLCbits         RES 1     ; 0xF35
TXB1D0              
TXB1D0bits          RES 1     ; 0xF36
TXB1D1              
TXB1D1bits          RES 1     ; 0xF37
TXB1D2              
TXB1D2bits          RES 1     ; 0xF38
TXB1D3              
TXB1D3bits          RES 1     ; 0xF39
TXB1D4              
TXB1D4bits          RES 1     ; 0xF3A
TXB1D5              
TXB1D5bits          RES 1     ; 0xF3B
TXB1D6              
TXB1D6bits          RES 1     ; 0xF3C
TXB1D7              
TXB1D7bits          RES 1     ; 0xF3D
CANSTAT_RO2         
CANSTAT_RO2bits     RES 1     ; 0xF3E
CANCON_RO2          
CANCON_RO2bits      RES 1     ; 0xF3F
TXB0CON             
TXB0CONbits         RES 1     ; 0xF40
TXB0SIDH            
TXB0SIDHbits        RES 1     ; 0xF41
TXB0SIDL            
TXB0SIDLbits        RES 1     ; 0xF42
TXB0EIDH            
TXB0EIDHbits        RES 1     ; 0xF43
TXB0EIDL            
TXB0EIDLbits        RES 1     ; 0xF44
TXB0DLC             
TXB0DLCbits         RES 1     ; 0xF45
TXB0D0              
TXB0D0bits          RES 1     ; 0xF46
TXB0D1              
TXB0D1bits          RES 1     ; 0xF47
TXB0D2              
TXB0D2bits          RES 1     ; 0xF48
TXB0D3              
TXB0D3bits          RES 1     ; 0xF49
TXB0D4              
TXB0D4bits          RES 1     ; 0xF4A
TXB0D5              
TXB0D5bits          RES 1     ; 0xF4B
TXB0D6              
TXB0D6bits          RES 1     ; 0xF4C
TXB0D7              
TXB0D7bits          RES 1     ; 0xF4D
CANSTAT_RO1         
CANSTAT_RO1bits     RES 1     ; 0xF4E
CANCON_RO1          
CANCON_RO1bits      RES 1     ; 0xF4F
RXB1CON             
RXB1CONbits         RES 1     ; 0xF50
RXB1SIDH            
RXB1SIDHbits        RES 1     ; 0xF51
RXB1SIDL            
RXB1SIDLbits        RES 1     ; 0xF52
RXB1EIDH            
RXB1EIDHbits        RES 1     ; 0xF53
RXB1EIDL            
RXB1EIDLbits        RES 1     ; 0xF54
RXB1DLC             
RXB1DLCbits         RES 1     ; 0xF55
RXB1D0              
RXB1D0bits          RES 1     ; 0xF56
RXB1D1              
RXB1D1bits          RES 1     ; 0xF57
RXB1D2              
RXB1D2bits          RES 1     ; 0xF58
RXB1D3              
RXB1D3bits          RES 1     ; 0xF59
RXB1D4              
RXB1D4bits          RES 1     ; 0xF5A
RXB1D5              
RXB1D5bits          RES 1     ; 0xF5B
RXB1D6              
RXB1D6bits          RES 1     ; 0xF5C
RXB1D7              
RXB1D7bits          RES 1     ; 0xF5D
CANSTAT_RO0         
CANSTAT_RO0bits     RES 1     ; 0xF5E
CANCON_RO0          
CANCON_RO0bits      RES 1     ; 0xF5F

;*** Set all of memory to zeroes ***/
; Use FSR0 to increment through memory from address 0x0
; to the end of the last bank skipping sfrs and
; unimplemented addresses.

    CODE
__zero_memory
    movlw  0x0       ;low byte of (end address + 1)
    movwf  PRODL, 0
    lfsr   0, 0x0    ;start address
    movlw  0xD       ;high byte of (end address + 1)
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
        GLOBAL RXFCON0bits
        GLOBAL RXFCON0
        GLOBAL RXFCON1bits
        GLOBAL RXFCON1
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
        GLOBAL BRGCON1bits
        GLOBAL BRGCON1
        GLOBAL BRGCON2bits
        GLOBAL BRGCON2
        GLOBAL BRGCON3bits
        GLOBAL BRGCON3
        GLOBAL CIOCONbits
        GLOBAL CIOCON
        GLOBAL COMSTATbits
        GLOBAL COMSTAT
        GLOBAL RXERRCNTbits
        GLOBAL RXERRCNT
        GLOBAL TXERRCNTbits
        GLOBAL TXERRCNT
        GLOBAL ECANCONbits
        GLOBAL ECANCON
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
        GLOBAL DDRAbits
        GLOBAL DDRA
        GLOBAL TRISAbits
        GLOBAL TRISA
        GLOBAL DDRBbits
        GLOBAL DDRB
        GLOBAL TRISBbits
        GLOBAL TRISB
        GLOBAL DDRCbits
        GLOBAL DDRC
        GLOBAL TRISCbits
        GLOBAL TRISC
        GLOBAL DDRDbits
        GLOBAL DDRD
        GLOBAL TRISDbits
        GLOBAL TRISD
        GLOBAL DDREbits
        GLOBAL DDRE
        GLOBAL TRISEbits
        GLOBAL TRISE
        GLOBAL OSCTUNEbits
        GLOBAL OSCTUNE
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
        GLOBAL EECON1bits
        GLOBAL EECON1
        GLOBAL EECON2
        GLOBAL EEDATA
        GLOBAL EEADR
        GLOBAL EEADRH
        GLOBAL RCSTAbits
        GLOBAL RCSTA
        GLOBAL TXSTAbits
        GLOBAL TXSTA
        GLOBAL TXREG
        GLOBAL RCREG
        GLOBAL SPBRG
        GLOBAL SPBRGH
        GLOBAL T3CONbits
        GLOBAL T3CON
        GLOBAL TMR3L
        GLOBAL TMR3H
        GLOBAL CMCONbits
        GLOBAL CMCON
        GLOBAL CVRCONbits
        GLOBAL CVRCON
        GLOBAL ECCP1ASbits
        GLOBAL ECCP1AS
        GLOBAL ECCP1DELbits
        GLOBAL ECCP1DEL
        GLOBAL BAUDCONbits
        GLOBAL BAUDCON
        GLOBAL BAUDCTLbits
        GLOBAL BAUDCTL
        GLOBAL ECCP1CONbits
        GLOBAL ECCP1CON
        GLOBAL ECCPR1
        GLOBAL ECCPR1L
        GLOBAL ECCPR1H
        GLOBAL CCP1CONbits
        GLOBAL CCP1CON
        GLOBAL CCPR1
        GLOBAL CCPR1L
        GLOBAL CCPR1H
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
        GLOBAL HLVDCONbits
        GLOBAL HLVDCON
        GLOBAL LVDCONbits
        GLOBAL LVDCON
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
        GLOBAL FSR2H
        GLOBAL PLUSW2
        GLOBAL PREINC2
        GLOBAL POSTDEC2
        GLOBAL POSTINC2
        GLOBAL INDF2
        GLOBAL BSR
        GLOBAL FSR1
        GLOBAL FSR1L
        GLOBAL FSR1H
        GLOBAL PLUSW1
        GLOBAL PREINC1
        GLOBAL POSTDEC1
        GLOBAL POSTINC1
        GLOBAL INDF1
        GLOBAL WREG
        GLOBAL FSR0
        GLOBAL FSR0L
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
        GLOBAL PROD
        GLOBAL PRODL
        GLOBAL PRODH
        GLOBAL TABLAT
        GLOBAL TBLPTR
        GLOBAL TBLPTRL
        GLOBAL TBLPTRH
        GLOBAL TBLPTRU
        GLOBAL PC
        GLOBAL PCL
        GLOBAL PCLATH
        GLOBAL PCLATU
        GLOBAL STKPTRbits
        GLOBAL STKPTR
        GLOBAL TOS
        GLOBAL TOSL
        GLOBAL TOSH
        GLOBAL TOSU

;-------------------------------------------------------------------------

        LIST
        END
