        LIST P=18F44J50
        NOLIST
;-------------------------------------------------------------------------
; MPLAB-Cxx  PIC18F44J50 processor definition module
; 
; (c) Copyright 1999-2010 Microchip Technology, All rights reserved
;-------------------------------------------------------------------------

SFR_UNBANKED0       UDATA_ACS H'F60'

UFRM                
UFRML               
UFRMLbits           RES 1     ; 0xF60
UFRMH               
UFRMHbits           RES 1     ; 0xF61
UIR                 
UIRbits             RES 1     ; 0xF62
UEIR                
UEIRbits            RES 1     ; 0xF63
USTAT               
USTATbits           RES 1     ; 0xF64
UCON                
UCONbits            RES 1     ; 0xF65
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
SSP2ADDbits         RES 1     ; 0xF74
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
                    RES 1
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
ALRMVALL            RES 1     ; 0xF8E
ALRMVALH            RES 1     ; 0xF8F
ALRMRPT             
ALRMRPTbits         RES 1     ; 0xF90
ALRMCFG             
ALRMCFGbits         RES 1     ; 0xF91
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
RTCVALL             RES 1     ; 0xF98
RTCVALH             RES 1     ; 0xF99
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
SSP1ADDbits         
SSPADD              
SSPADDbits          RES 1     ; 0xFC8
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

SFR_BANKED0         UDATA H'EC6'

RPOR0               RES 1     ; 0xEC6
RPOR1               RES 1     ; 0xEC7
RPOR2               RES 1     ; 0xEC8
RPOR3               RES 1     ; 0xEC9
RPOR4               RES 1     ; 0xECA
RPOR5               RES 1     ; 0xECB
RPOR6               RES 1     ; 0xECC
RPOR7               RES 1     ; 0xECD
RPOR8               RES 1     ; 0xECE
RPOR9               RES 1     ; 0xECF
RPOR10              RES 1     ; 0xED0
RPOR11              RES 1     ; 0xED1
RPOR12              RES 1     ; 0xED2
RPOR13              RES 1     ; 0xED3

SFR_BANKED1         UDATA H'ED7'
RPOR17              RES 1     ; 0xED7
RPOR18              RES 1     ; 0xED8
RPOR19              RES 1     ; 0xED9
RPOR20              RES 1     ; 0xEDA
RPOR21              RES 1     ; 0xEDB
RPOR22              RES 1     ; 0xEDC
RPOR23              RES 1     ; 0xEDD
RPOR24              RES 1     ; 0xEDE

SFR_BANKED2         UDATA H'EE7'
RPINR1              RES 1     ; 0xEE7
RPINR2              RES 1     ; 0xEE8
RPINR3              RES 1     ; 0xEE9
RPINR4              RES 1     ; 0xEEA

SFR_BANKED3         UDATA H'EEC'
RPINR6              RES 1     ; 0xEEC
RPINR7              RES 1     ; 0xEED
RPINR8              RES 1     ; 0xEEE

SFR_BANKED4         UDATA H'EF2'
RPINR12             RES 1     ; 0xEF2
RPINR13             RES 1     ; 0xEF3

SFR_BANKED5         UDATA H'EF6'
RPINR16             RES 1     ; 0xEF6
RPINR17             RES 1     ; 0xEF7

SFR_BANKED6         UDATA H'EFB'
RPINR21             RES 1     ; 0xEFB
RPINR22             RES 1     ; 0xEFC
RPINR23             RES 1     ; 0xEFD
RPINR24             RES 1     ; 0xEFE
PPSCON              
PPSCONbits          RES 1     ; 0xEFF

SFR_BANKED7         UDATA H'F26'
UEP0                
UEP0bits            RES 1     ; 0xF26
UEP1                
UEP1bits            RES 1     ; 0xF27
UEP2                
UEP2bits            RES 1     ; 0xF28
UEP3                
UEP3bits            RES 1     ; 0xF29
UEP4                
UEP4bits            RES 1     ; 0xF2A
UEP5                
UEP5bits            RES 1     ; 0xF2B
UEP6                
UEP6bits            RES 1     ; 0xF2C
UEP7                
UEP7bits            RES 1     ; 0xF2D
UEP8                
UEP8bits            RES 1     ; 0xF2E
UEP9                
UEP9bits            RES 1     ; 0xF2F
UEP10               
UEP10bits           RES 1     ; 0xF30
UEP11               
UEP11bits           RES 1     ; 0xF31
UEP12               
UEP12bits           RES 1     ; 0xF32
UEP13               
UEP13bits           RES 1     ; 0xF33
UEP14               
UEP14bits           RES 1     ; 0xF34
UEP15               
UEP15bits           RES 1     ; 0xF35
UIE                 
UIEbits             RES 1     ; 0xF36
UEIE                
UEIEbits            RES 1     ; 0xF37
UADDR               
UADDRbits           RES 1     ; 0xF38
UCFG                
UCFGbits            RES 1     ; 0xF39

SFR_BANKED8         UDATA H'F3C'
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

SFR_BANKED9         UDATA H'F48'
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

SFR_BANKED10        UDATA H'F52'
TCLKCON             
TCLKCONbits         RES 1     ; 0xF52
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
    movlw  0xC0      ;low byte of (end address + 1)
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
        GLOBAL RPINR7
        GLOBAL RPINR8
        GLOBAL RPINR12
        GLOBAL RPINR13
        GLOBAL RPINR16
        GLOBAL RPINR17
        GLOBAL RPINR21
        GLOBAL RPINR22
        GLOBAL RPINR23
        GLOBAL RPINR24
        GLOBAL PPSCONbits
        GLOBAL PPSCON
        GLOBAL UEP0bits
        GLOBAL UEP0
        GLOBAL UEP1bits
        GLOBAL UEP1
        GLOBAL UEP2bits
        GLOBAL UEP2
        GLOBAL UEP3bits
        GLOBAL UEP3
        GLOBAL UEP4bits
        GLOBAL UEP4
        GLOBAL UEP5bits
        GLOBAL UEP5
        GLOBAL UEP6bits
        GLOBAL UEP6
        GLOBAL UEP7bits
        GLOBAL UEP7
        GLOBAL UEP8bits
        GLOBAL UEP8
        GLOBAL UEP9bits
        GLOBAL UEP9
        GLOBAL UEP10bits
        GLOBAL UEP10
        GLOBAL UEP11bits
        GLOBAL UEP11
        GLOBAL UEP12bits
        GLOBAL UEP12
        GLOBAL UEP13bits
        GLOBAL UEP13
        GLOBAL UEP14bits
        GLOBAL UEP14
        GLOBAL UEP15bits
        GLOBAL UEP15
        GLOBAL UIEbits
        GLOBAL UIE
        GLOBAL UEIEbits
        GLOBAL UEIE
        GLOBAL UADDRbits
        GLOBAL UADDR
        GLOBAL UCFGbits
        GLOBAL UCFG
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
        GLOBAL TCLKCONbits
        GLOBAL TCLKCON
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
        GLOBAL UFRM
        GLOBAL UFRMLbits
        GLOBAL UFRML
        GLOBAL UFRMHbits
        GLOBAL UFRMH
        GLOBAL UIRbits
        GLOBAL UIR
        GLOBAL UEIRbits
        GLOBAL UEIR
        GLOBAL USTATbits
        GLOBAL USTAT
        GLOBAL UCONbits
        GLOBAL UCON
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
        GLOBAL SSP2ADDbits
        GLOBAL SSP2ADD
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
        GLOBAL ALRMVALL
        GLOBAL ALRMVALH
        GLOBAL ALRMRPTbits
        GLOBAL ALRMRPT
        GLOBAL ALRMCFGbits
        GLOBAL ALRMCFG
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
        GLOBAL RTCVALL
        GLOBAL RTCVALH
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
        GLOBAL SSP1ADDbits
        GLOBAL SSP1ADD
        GLOBAL SSPADDbits
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
