        LIST P=18F46J13
        NOLIST
;-------------------------------------------------------------------------
; MPLAB-Cxx  PIC18F46J13 processor definition module
; 
; (c) Copyright 1999-2010 Microchip Technology, All rights reserved
;-------------------------------------------------------------------------

SFR_UNBANKED0       UDATA_ACS H'F66'

DMABCH              
DMABCHbits          RES 1     ; 0xF66
DMABCL              RES 1     ; 0xF67
RXADDRH             
RXADDRHbits         RES 1     ; 0xF68
RXADDRL             RES 1     ; 0xF69
TXADDRH             
TXADDRHbits         RES 1     ; 0xF6A
TXADDRL             RES 1     ; 0xF6B
PMDIN1L             RES 1     ; 0xF6C
PMDIN1H             RES 1     ; 0xF6D
PMADDRL             
PMDOUT1L            RES 1     ; 0xF6E
PMADDRH             
PMADDRHbits         
PMDOUT1H            RES 1     ; 0xF6F
CMSTAT              
CMSTATbits          
CMSTATUS            
CMSTATUSbits        RES 1     ; 0xF70
SSP2CON2            
SSP2CON2bits        RES 1     ; 0xF71
SSP2CON1            
SSP2CON1bits        RES 1     ; 0xF72
SSP2STAT            
SSP2STATbits        RES 1     ; 0xF73
SSP2ADD             
SSP2MSK             
SSP2MSKbits         RES 1     ; 0xF74
SSP2BUF             RES 1     ; 0xF75
T4CON               
T4CONbits           RES 1     ; 0xF76
PR4                 RES 1     ; 0xF77
TMR4                RES 1     ; 0xF78
T3CON               
T3CONbits           RES 1     ; 0xF79
TMR3L               RES 1     ; 0xF7A
TMR3H               RES 1     ; 0xF7B
BAUDCON2            
BAUDCON2bits        RES 1     ; 0xF7C
SPBRGH2             RES 1     ; 0xF7D
BAUDCON             
BAUDCONbits         
BAUDCON1            
BAUDCON1bits        
BAUDCTL             
BAUDCTLbits         RES 1     ; 0xF7E
SPBRGH              
SPBRGH1             RES 1     ; 0xF7F
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
HLVDCON             
HLVDCONbits         RES 1     ; 0xF85
DMACON2             
DMACON2bits         RES 1     ; 0xF86
OSCCON2             
OSCCON2bits         RES 1     ; 0xF87
DMACON1             
DMACON1bits         RES 1     ; 0xF88
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
PIE4                
PIE4bits            RES 1     ; 0xF8E
PIR4                
PIR4bits            RES 1     ; 0xF8F
IPR4                
IPR4bits            RES 1     ; 0xF90
PIE5                
PIE5bits            RES 1     ; 0xF91
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
T3GCON              
T3GCONbits          RES 1     ; 0xF97
PIR5                
PIR5bits            RES 1     ; 0xF98
IPR5                
IPR5bits            RES 1     ; 0xF99
T1GCON              
T1GCONbits          RES 1     ; 0xF9A
OSCTUNE             
OSCTUNEbits         RES 1     ; 0xF9B
RCSTA2              
RCSTA2bits          RES 1     ; 0xF9C
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
TXSTA2              
TXSTA2bits          RES 1     ; 0xFA8
TXREG2              RES 1     ; 0xFA9
RCREG2              RES 1     ; 0xFAA
SPBRG2              RES 1     ; 0xFAB
RCSTA               
RCSTAbits           
RCSTA1              
RCSTA1bits          RES 1     ; 0xFAC
TXSTA               
TXSTAbits           
TXSTA1              
TXSTA1bits          RES 1     ; 0xFAD
TXREG               
TXREG1              RES 1     ; 0xFAE
RCREG               
RCREG1              RES 1     ; 0xFAF
SPBRG               
SPBRG1              RES 1     ; 0xFB0
CTMUICON            
CTMUICONbits        RES 1     ; 0xFB1
CTMUCONL            
CTMUCONLbits        RES 1     ; 0xFB2
CTMUCONH            
CTMUCONHbits        RES 1     ; 0xFB3
CCP2CON             
CCP2CONbits         
ECCP2CON            
ECCP2CONbits        RES 1     ; 0xFB4
CCPR2               
CCPR2L              RES 1     ; 0xFB5
CCPR2H              RES 1     ; 0xFB6
ECCP2DEL            
ECCP2DELbits        
PWM2CON             
PWM2CONbits         RES 1     ; 0xFB7
ECCP2AS             
ECCP2ASbits         RES 1     ; 0xFB8
PSTR2CON            
PSTR2CONbits        RES 1     ; 0xFB9
CCP1CON             
CCP1CONbits         
ECCP1CON            
ECCP1CONbits        RES 1     ; 0xFBA
CCPR1               
CCPR1L              RES 1     ; 0xFBB
CCPR1H              RES 1     ; 0xFBC
ECCP1DEL            
ECCP1DELbits        
PWM1CON             
PWM1CONbits         RES 1     ; 0xFBD
ECCP1AS             
ECCP1ASbits         RES 1     ; 0xFBE
PSTR1CON            
PSTR1CONbits        RES 1     ; 0xFBF
WDTCON              
WDTCONbits          RES 1     ; 0xFC0
ADCON1              
ADCON1bits          RES 1     ; 0xFC1
ADCON0              
ADCON0bits          RES 1     ; 0xFC2
ADRES               
ADRESL              RES 1     ; 0xFC3
ADRESH              RES 1     ; 0xFC4
SSP1CON2            
SSP1CON2bits        
SSPCON2             
SSPCON2bits         RES 1     ; 0xFC5
SSP1CON1            
SSP1CON1bits        
SSPCON1             
SSPCON1bits         RES 1     ; 0xFC6
SSP1STAT            
SSP1STATbits        
SSPSTAT             
SSPSTATbits         RES 1     ; 0xFC7
SSP1ADD             
SSP1MSK             
SSP1MSKbits         
SSPADD              RES 1     ; 0xFC8
SSP1BUF             
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
CM2CON              
CM2CONbits          
CM2CON1             
CM2CON1bits         RES 1     ; 0xFD1
CM1CON              
CM1CONbits          
CM1CON1             
CM1CON1bits         RES 1     ; 0xFD2
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

SFR_BANKED0         UDATA H'EB8'

ADCTRIG             
ADCTRIGbits         RES 1     ; 0xEB8
PD0                 
PD0bits             
PMDIS0              
PMDIS0bits          RES 1     ; 0xEB9
PD1                 
PD1bits             
PMDIS1              
PMDIS1bits          RES 1     ; 0xEBA
PD2                 
PD2bits             
PMDIS2              
PMDIS2bits          RES 1     ; 0xEBB
PD3                 
PD3bits             
PMDIS3              
PMDIS3bits          RES 1     ; 0xEBC

SFR_BANKED1         UDATA H'EBF'
PPSCON              
PPSCONbits          RES 1     ; 0xEBF
RPOR0               RES 1     ; 0xEC0
RPOR1               RES 1     ; 0xEC1
RPOR2               RES 1     ; 0xEC2
RPOR3               RES 1     ; 0xEC3
RPOR4               RES 1     ; 0xEC4
RPOR5               RES 1     ; 0xEC5
RPOR6               RES 1     ; 0xEC6
RPOR7               RES 1     ; 0xEC7
RPOR8               RES 1     ; 0xEC8
RPOR9               RES 1     ; 0xEC9
RPOR10              RES 1     ; 0xECA
RPOR11              RES 1     ; 0xECB
RPOR12              RES 1     ; 0xECC
RPOR13              RES 1     ; 0xECD
RPOR14              RES 1     ; 0xECE
RPOR15              RES 1     ; 0xECF
RPOR16              RES 1     ; 0xED0
RPOR17              RES 1     ; 0xED1
RPOR18              RES 1     ; 0xED2
RPOR19              RES 1     ; 0xED3
RPOR20              RES 1     ; 0xED4
RPOR21              RES 1     ; 0xED5
RPOR22              RES 1     ; 0xED6
RPOR23              RES 1     ; 0xED7
RPOR24              RES 1     ; 0xED8

SFR_BANKED2         UDATA H'EE1'
RPINR1              RES 1     ; 0xEE1
RPINR2              RES 1     ; 0xEE2
RPINR3              RES 1     ; 0xEE3
RPINR4              RES 1     ; 0xEE4

SFR_BANKED3         UDATA H'EE6'
RPINR6              RES 1     ; 0xEE6
RPINR15             RES 1     ; 0xEE7
RPINR7              RES 1     ; 0xEE8
RPINR8              RES 1     ; 0xEE9
RPINR9              RES 1     ; 0xEEA

SFR_BANKED4         UDATA H'EF2'
RPINR12             RES 1     ; 0xEF2
RPINR13             RES 1     ; 0xEF3
RPINR14             RES 1     ; 0xEF4

SFR_BANKED5         UDATA H'EF7'
RPINR16             RES 1     ; 0xEF7
RPINR17             RES 1     ; 0xEF8

SFR_BANKED6         UDATA H'EFC'
RPINR21             RES 1     ; 0xEFC
RPINR22             RES 1     ; 0xEFD
RPINR23             RES 1     ; 0xEFE
RPINR24             RES 1     ; 0xEFF

SFR_BANKED7         UDATA H'F00'
CCP10CON            
CCP10CONbits        RES 1     ; 0xF00
CCPR10L             RES 1     ; 0xF01
CCPR10H             RES 1     ; 0xF02
CCP9CON             
CCP9CONbits         RES 1     ; 0xF03
CCPR9L              RES 1     ; 0xF04
CCPR9H              RES 1     ; 0xF05
CCP8CON             
CCP8CONbits         RES 1     ; 0xF06
CCPR8L              RES 1     ; 0xF07
CCPR8H              RES 1     ; 0xF08
CCP7CON             
CCP7CONbits         RES 1     ; 0xF09
CCPR7L              RES 1     ; 0xF0A
CCPR7H              RES 1     ; 0xF0B
CCP6CON             
CCP6CONbits         RES 1     ; 0xF0C
CCPR6L              RES 1     ; 0xF0D
CCPR6H              RES 1     ; 0xF0E
CCP5CON             
CCP5CONbits         RES 1     ; 0xF0F
CCPR5L              RES 1     ; 0xF10
CCPR5H              RES 1     ; 0xF11
CCP4CON             
CCP4CONbits         RES 1     ; 0xF12
CCPR4L              RES 1     ; 0xF13
CCPR4H              RES 1     ; 0xF14
CCP3CON             
CCP3CONbits         RES 1     ; 0xF15
CCPR3L              RES 1     ; 0xF16
CCPR3H              RES 1     ; 0xF17
ECCP3DEL            
ECCP3DELbits        RES 1     ; 0xF18
ECCP3AS             
ECCP3ASbits         RES 1     ; 0xF19
PSTR3CON            
PSTR3CONbits        RES 1     ; 0xF1A
T8CON               
T8CONbits           RES 1     ; 0xF1B
PR8                 RES 1     ; 0xF1C
TMR8                RES 1     ; 0xF1D
T6CON               
T6CONbits           RES 1     ; 0xF1E
PR6                 RES 1     ; 0xF1F
TMR6                RES 1     ; 0xF20
T5GCON              
T5GCONbits          RES 1     ; 0xF21
T5CON               
T5CONbits           RES 1     ; 0xF22
TMR5L               RES 1     ; 0xF23
TMR5H               RES 1     ; 0xF24
CM3CON              
CM3CONbits          RES 1     ; 0xF25

SFR_BANKED8         UDATA H'F3A'
RTCVALL             RES 1     ; 0xF3A
RTCVALH             RES 1     ; 0xF3B
PADCFG1             
PADCFG1bits         RES 1     ; 0xF3C
REFOCON             
REFOCONbits         RES 1     ; 0xF3D
RTCCAL              
RTCCALbits          RES 1     ; 0xF3E
RTCCFG              
RTCCFGbits          RES 1     ; 0xF3F
ODCON3              
ODCON3bits          RES 1     ; 0xF40
ODCON2              
ODCON2bits          RES 1     ; 0xF41
ODCON1              
ODCON1bits          RES 1     ; 0xF42

SFR_BANKED9         UDATA H'F44'
ALRMVALL            RES 1     ; 0xF44
ALRMVALH            RES 1     ; 0xF45
ALRMRPT             
ALRMRPTbits         RES 1     ; 0xF46
ALRMCFG             
ALRMCFGbits         RES 1     ; 0xF47
ANCON0              
ANCON0bits          RES 1     ; 0xF48
ANCON1              
ANCON1bits          RES 1     ; 0xF49
DSWAKEL             
DSWAKELbits         RES 1     ; 0xF4A
DSWAKEH             
DSWAKEHbits         RES 1     ; 0xF4B
DSCONL              
DSCONLbits          RES 1     ; 0xF4C
DSCONH              
DSCONHbits          RES 1     ; 0xF4D
DSGPR0              RES 1     ; 0xF4E
DSGPR1              RES 1     ; 0xF4F
CCPTMRS2            
CCPTMRS2bits        RES 1     ; 0xF50
CCPTMRS1            
CCPTMRS1bits        RES 1     ; 0xF51
CCPTMRS0            
CCPTMRS0bits        RES 1     ; 0xF52
CVRCON              
CVRCONbits          RES 1     ; 0xF53
PMSTATL             
PMSTATLbits         RES 1     ; 0xF54
PMSTATH             
PMSTATHbits         RES 1     ; 0xF55
PMEL                
PMELbits            RES 1     ; 0xF56
PMEH                
PMEHbits            RES 1     ; 0xF57
PMDIN2L             RES 1     ; 0xF58
PMDIN2H             RES 1     ; 0xF59
PMDOUT2L            RES 1     ; 0xF5A
PMDOUT2H            RES 1     ; 0xF5B
PMMODEL             
PMMODELbits         RES 1     ; 0xF5C
PMMODEH             
PMMODEHbits         RES 1     ; 0xF5D
PMCONL              
PMCONLbits          RES 1     ; 0xF5E
PMCONH              
PMCONHbits          RES 1     ; 0xF5F

;*** Set all of memory to zeroes ***/
; Use FSR0 to increment through memory from address 0x0
; to the end of the last bank skipping sfrs and
; unimplemented addresses.

    CODE
__zero_memory
    movlw  0xB0      ;low byte of (end address + 1)
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

        GLOBAL ADCTRIGbits
        GLOBAL ADCTRIG
        GLOBAL PD0bits
        GLOBAL PD0
        GLOBAL PMDIS0bits
        GLOBAL PMDIS0
        GLOBAL PD1bits
        GLOBAL PD1
        GLOBAL PMDIS1bits
        GLOBAL PMDIS1
        GLOBAL PD2bits
        GLOBAL PD2
        GLOBAL PMDIS2bits
        GLOBAL PMDIS2
        GLOBAL PD3bits
        GLOBAL PD3
        GLOBAL PMDIS3bits
        GLOBAL PMDIS3
        GLOBAL PPSCONbits
        GLOBAL PPSCON
        GLOBAL RPOR0
        GLOBAL RPOR1
        GLOBAL RPOR2
        GLOBAL RPOR3
        GLOBAL RPOR4
        GLOBAL RPOR5
        GLOBAL RPOR6
        GLOBAL RPOR7
        GLOBAL RPOR8
        GLOBAL RPOR9
        GLOBAL RPOR10
        GLOBAL RPOR11
        GLOBAL RPOR12
        GLOBAL RPOR13
        GLOBAL RPOR14
        GLOBAL RPOR15
        GLOBAL RPOR16
        GLOBAL RPOR17
        GLOBAL RPOR18
        GLOBAL RPOR19
        GLOBAL RPOR20
        GLOBAL RPOR21
        GLOBAL RPOR22
        GLOBAL RPOR23
        GLOBAL RPOR24
        GLOBAL RPINR1
        GLOBAL RPINR2
        GLOBAL RPINR3
        GLOBAL RPINR4
        GLOBAL RPINR6
        GLOBAL RPINR15
        GLOBAL RPINR7
        GLOBAL RPINR8
        GLOBAL RPINR9
        GLOBAL RPINR12
        GLOBAL RPINR13
        GLOBAL RPINR14
        GLOBAL RPINR16
        GLOBAL RPINR17
        GLOBAL RPINR21
        GLOBAL RPINR22
        GLOBAL RPINR23
        GLOBAL RPINR24
        GLOBAL CCP10CONbits
        GLOBAL CCP10CON
        GLOBAL CCPR10L
        GLOBAL CCPR10H
        GLOBAL CCP9CONbits
        GLOBAL CCP9CON
        GLOBAL CCPR9L
        GLOBAL CCPR9H
        GLOBAL CCP8CONbits
        GLOBAL CCP8CON
        GLOBAL CCPR8L
        GLOBAL CCPR8H
        GLOBAL CCP7CONbits
        GLOBAL CCP7CON
        GLOBAL CCPR7L
        GLOBAL CCPR7H
        GLOBAL CCP6CONbits
        GLOBAL CCP6CON
        GLOBAL CCPR6L
        GLOBAL CCPR6H
        GLOBAL CCP5CONbits
        GLOBAL CCP5CON
        GLOBAL CCPR5L
        GLOBAL CCPR5H
        GLOBAL CCP4CONbits
        GLOBAL CCP4CON
        GLOBAL CCPR4L
        GLOBAL CCPR4H
        GLOBAL CCP3CONbits
        GLOBAL CCP3CON
        GLOBAL CCPR3L
        GLOBAL CCPR3H
        GLOBAL ECCP3DELbits
        GLOBAL ECCP3DEL
        GLOBAL ECCP3ASbits
        GLOBAL ECCP3AS
        GLOBAL PSTR3CONbits
        GLOBAL PSTR3CON
        GLOBAL T8CONbits
        GLOBAL T8CON
        GLOBAL PR8
        GLOBAL TMR8
        GLOBAL T6CONbits
        GLOBAL T6CON
        GLOBAL PR6
        GLOBAL TMR6
        GLOBAL T5GCONbits
        GLOBAL T5GCON
        GLOBAL T5CONbits
        GLOBAL T5CON
        GLOBAL TMR5L
        GLOBAL TMR5H
        GLOBAL CM3CONbits
        GLOBAL CM3CON
        GLOBAL RTCVALL
        GLOBAL RTCVALH
        GLOBAL PADCFG1bits
        GLOBAL PADCFG1
        GLOBAL REFOCONbits
        GLOBAL REFOCON
        GLOBAL RTCCALbits
        GLOBAL RTCCAL
        GLOBAL RTCCFGbits
        GLOBAL RTCCFG
        GLOBAL ODCON3bits
        GLOBAL ODCON3
        GLOBAL ODCON2bits
        GLOBAL ODCON2
        GLOBAL ODCON1bits
        GLOBAL ODCON1
        GLOBAL ALRMVALL
        GLOBAL ALRMVALH
        GLOBAL ALRMRPTbits
        GLOBAL ALRMRPT
        GLOBAL ALRMCFGbits
        GLOBAL ALRMCFG
        GLOBAL ANCON0bits
        GLOBAL ANCON0
        GLOBAL ANCON1bits
        GLOBAL ANCON1
        GLOBAL DSWAKELbits
        GLOBAL DSWAKEL
        GLOBAL DSWAKEHbits
        GLOBAL DSWAKEH
        GLOBAL DSCONLbits
        GLOBAL DSCONL
        GLOBAL DSCONHbits
        GLOBAL DSCONH
        GLOBAL DSGPR0
        GLOBAL DSGPR1
        GLOBAL CCPTMRS2bits
        GLOBAL CCPTMRS2
        GLOBAL CCPTMRS1bits
        GLOBAL CCPTMRS1
        GLOBAL CCPTMRS0bits
        GLOBAL CCPTMRS0
        GLOBAL CVRCONbits
        GLOBAL CVRCON
        GLOBAL PMSTATLbits
        GLOBAL PMSTATL
        GLOBAL PMSTATHbits
        GLOBAL PMSTATH
        GLOBAL PMELbits
        GLOBAL PMEL
        GLOBAL PMEHbits
        GLOBAL PMEH
        GLOBAL PMDIN2L
        GLOBAL PMDIN2H
        GLOBAL PMDOUT2L
        GLOBAL PMDOUT2H
        GLOBAL PMMODELbits
        GLOBAL PMMODEL
        GLOBAL PMMODEHbits
        GLOBAL PMMODEH
        GLOBAL PMCONLbits
        GLOBAL PMCONL
        GLOBAL PMCONHbits
        GLOBAL PMCONH
        GLOBAL DMABCHbits
        GLOBAL DMABCH
        GLOBAL DMABCL
        GLOBAL RXADDRHbits
        GLOBAL RXADDRH
        GLOBAL RXADDRL
        GLOBAL TXADDRHbits
        GLOBAL TXADDRH
        GLOBAL TXADDRL
        GLOBAL PMDIN1L
        GLOBAL PMDIN1H
        GLOBAL PMADDRL
        GLOBAL PMDOUT1L
        GLOBAL PMADDRHbits
        GLOBAL PMADDRH
        GLOBAL PMDOUT1H
        GLOBAL CMSTATbits
        GLOBAL CMSTAT
        GLOBAL CMSTATUSbits
        GLOBAL CMSTATUS
        GLOBAL SSP2CON2bits
        GLOBAL SSP2CON2
        GLOBAL SSP2CON1bits
        GLOBAL SSP2CON1
        GLOBAL SSP2STATbits
        GLOBAL SSP2STAT
        GLOBAL SSP2ADD
        GLOBAL SSP2MSKbits
        GLOBAL SSP2MSK
        GLOBAL SSP2BUF
        GLOBAL T4CONbits
        GLOBAL T4CON
        GLOBAL PR4
        GLOBAL TMR4
        GLOBAL T3CONbits
        GLOBAL T3CON
        GLOBAL TMR3L
        GLOBAL TMR3H
        GLOBAL BAUDCON2bits
        GLOBAL BAUDCON2
        GLOBAL SPBRGH2
        GLOBAL BAUDCONbits
        GLOBAL BAUDCON
        GLOBAL BAUDCON1bits
        GLOBAL BAUDCON1
        GLOBAL BAUDCTLbits
        GLOBAL BAUDCTL
        GLOBAL SPBRGH
        GLOBAL SPBRGH1
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
        GLOBAL HLVDCONbits
        GLOBAL HLVDCON
        GLOBAL DMACON2bits
        GLOBAL DMACON2
        GLOBAL OSCCON2bits
        GLOBAL OSCCON2
        GLOBAL DMACON1bits
        GLOBAL DMACON1
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
        GLOBAL PIE4bits
        GLOBAL PIE4
        GLOBAL PIR4bits
        GLOBAL PIR4
        GLOBAL IPR4bits
        GLOBAL IPR4
        GLOBAL PIE5bits
        GLOBAL PIE5
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
        GLOBAL T3GCONbits
        GLOBAL T3GCON
        GLOBAL PIR5bits
        GLOBAL PIR5
        GLOBAL IPR5bits
        GLOBAL IPR5
        GLOBAL T1GCONbits
        GLOBAL T1GCON
        GLOBAL OSCTUNEbits
        GLOBAL OSCTUNE
        GLOBAL RCSTA2bits
        GLOBAL RCSTA2
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
        GLOBAL TXSTA2bits
        GLOBAL TXSTA2
        GLOBAL TXREG2
        GLOBAL RCREG2
        GLOBAL SPBRG2
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
        GLOBAL CTMUICONbits
        GLOBAL CTMUICON
        GLOBAL CTMUCONLbits
        GLOBAL CTMUCONL
        GLOBAL CTMUCONHbits
        GLOBAL CTMUCONH
        GLOBAL CCP2CONbits
        GLOBAL CCP2CON
        GLOBAL ECCP2CONbits
        GLOBAL ECCP2CON
        GLOBAL CCPR2
        GLOBAL CCPR2L
        GLOBAL CCPR2H
        GLOBAL ECCP2DELbits
        GLOBAL ECCP2DEL
        GLOBAL PWM2CONbits
        GLOBAL PWM2CON
        GLOBAL ECCP2ASbits
        GLOBAL ECCP2AS
        GLOBAL PSTR2CONbits
        GLOBAL PSTR2CON
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
        GLOBAL PSTR1CONbits
        GLOBAL PSTR1CON
        GLOBAL WDTCONbits
        GLOBAL WDTCON
        GLOBAL ADCON1bits
        GLOBAL ADCON1
        GLOBAL ADCON0bits
        GLOBAL ADCON0
        GLOBAL ADRES
        GLOBAL ADRESL
        GLOBAL ADRESH
        GLOBAL SSP1CON2bits
        GLOBAL SSP1CON2
        GLOBAL SSPCON2bits
        GLOBAL SSPCON2
        GLOBAL SSP1CON1bits
        GLOBAL SSP1CON1
        GLOBAL SSPCON1bits
        GLOBAL SSPCON1
        GLOBAL SSP1STATbits
        GLOBAL SSP1STAT
        GLOBAL SSPSTATbits
        GLOBAL SSPSTAT
        GLOBAL SSP1ADD
        GLOBAL SSP1MSKbits
        GLOBAL SSP1MSK
        GLOBAL SSPADD
        GLOBAL SSP1BUF
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
        GLOBAL CM2CONbits
        GLOBAL CM2CON
        GLOBAL CM2CON1bits
        GLOBAL CM2CON1
        GLOBAL CM1CONbits
        GLOBAL CM1CON
        GLOBAL CM1CON1bits
        GLOBAL CM1CON1
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
