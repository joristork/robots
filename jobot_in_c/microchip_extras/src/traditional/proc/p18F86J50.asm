        LIST P=18F86J50
        NOLIST
;-------------------------------------------------------------------------
; MPLAB-Cxx  PIC18F86J50 processor definition module
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
PMDIN1              
PMDIN1L             RES 1     ; 0xF66
PMDIN1H             RES 1     ; 0xF67
PMADDR              
PMADDRL             
PMDOUT              
PMDOUT1L            RES 1     ; 0xF68
PMADDRH             
PMADDRHbits         
PMDOUT1H            RES 1     ; 0xF69
CMSTAT              
CMSTATbits          
CMSTATUS            
CMSTATUSbits        RES 1     ; 0xF6A
SSP2CON2            
SSP2CON2bits        RES 1     ; 0xF6B
SSP2CON1            
SSP2CON1bits        RES 1     ; 0xF6C
SSP2STAT            
SSP2STATbits        RES 1     ; 0xF6D
SSP2ADD             
SSP2MSK             
SSP2MSKbits         RES 1     ; 0xF6E
SSP2BUF             RES 1     ; 0xF6F
CCP5CON             
CCP5CONbits         RES 1     ; 0xF70
CCPR5               
CCPR5L              RES 1     ; 0xF71
CCPR5H              RES 1     ; 0xF72
CCP4CON             
CCP4CONbits         RES 1     ; 0xF73
CCPR4               
CCPR4L              RES 1     ; 0xF74
CCPR4H              RES 1     ; 0xF75
T4CON               
T4CONbits           RES 1     ; 0xF76
CVRCON              
CVRCONbits          
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
BAUDCON1bits        RES 1     ; 0xF7E
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
PORTF               
PORTFbits           RES 1     ; 0xF85
PORTG               
PORTGbits           RES 1     ; 0xF86
PORTH               
PORTHbits           RES 1     ; 0xF87
PORTJ               
PORTJbits           RES 1     ; 0xF88
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
LATH                
LATHbits            RES 1     ; 0xF90
LATJ                
LATJbits            RES 1     ; 0xF91
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
DDRF                
DDRFbits            
TRISF               
TRISFbits           RES 1     ; 0xF97
DDRG                
DDRGbits            
TRISG               
TRISGbits           RES 1     ; 0xF98
DDRH                
DDRHbits            
TRISH               
TRISHbits           RES 1     ; 0xF99
DDRJ                
DDRJbits            
TRISJ               
TRISJbits           RES 1     ; 0xF9A
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
CCP3CON             
CCP3CONbits         
ECCP3CON            
ECCP3CONbits        RES 1     ; 0xFB1
CCPR3               
CCPR3L              RES 1     ; 0xFB2
CCPR3H              RES 1     ; 0xFB3
ECCP3DEL            
ECCP3DELbits        RES 1     ; 0xFB4
ECCP3AS             
ECCP3ASbits         RES 1     ; 0xFB5
CCP2CON             
CCP2CONbits         
ECCP2CON            
ECCP2CONbits        RES 1     ; 0xFB6
CCPR2               
CCPR2L              RES 1     ; 0xFB7
CCPR2H              RES 1     ; 0xFB8
ECCP2DEL            
ECCP2DELbits        RES 1     ; 0xFB9
ECCP2AS             
ECCP2ASbits         RES 1     ; 0xFBA
CCP1CON             
CCP1CONbits         
ECCP1CON            
ECCP1CONbits        RES 1     ; 0xFBB
CCPR1               
CCPR1L              RES 1     ; 0xFBC
CCPR1H              RES 1     ; 0xFBD
ECCP1DEL            
ECCP1DELbits        RES 1     ; 0xFBE
ECCP1AS             
ECCP1ASbits         RES 1     ; 0xFBF
WDTCON              
WDTCONbits          RES 1     ; 0xFC0
ADCON1              
ADCON1bits          
ANCON0              
ANCON0bits          RES 1     ; 0xFC1
ADCON0              
ADCON0bits          
ANCON1              
ANCON1bits          RES 1     ; 0xFC2
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
MEMCON              
MEMCONbits          
PR2                 RES 1     ; 0xFCB
PADCFG1             
PADCFG1bits         
TMR2                RES 1     ; 0xFCC
ODCON3              
ODCON3bits          
T1CON               
T1CONbits           RES 1     ; 0xFCD
ODCON2              
ODCON2bits          
TMR1L               RES 1     ; 0xFCE
ODCON1              
ODCON1bits          
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
OSCCONbits          
REFOCON             
REFOCONbits         RES 1     ; 0xFD3
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

SFR_BANKED0         UDATA H'F40'

PMSTAT              
PMSTATL             
PMSTATLbits         RES 1     ; 0xF40
PMSTATH             
PMSTATHbits         RES 1     ; 0xF41
PMEL                
PMELbits            
PMEN                RES 1     ; 0xF42
PMEH                
PMEHbits            RES 1     ; 0xF43
PMDIN2              
PMDIN2L             RES 1     ; 0xF44
PMDIN2H             RES 1     ; 0xF45
PMDOUT2             
PMDOUT2L            RES 1     ; 0xF46
PMDOUT2H            RES 1     ; 0xF47
PMMODE              
PMMODEL             
PMMODELbits         RES 1     ; 0xF48
PMMODEH             
PMMODEHbits         RES 1     ; 0xF49
PMCON               
PMCONL              
PMCONLbits          RES 1     ; 0xF4A
PMCONH              
PMCONHbits          RES 1     ; 0xF4B
UEP0                
UEP0bits            RES 1     ; 0xF4C
UEP1                
UEP1bits            RES 1     ; 0xF4D
UEP2                
UEP2bits            RES 1     ; 0xF4E
UEP3                
UEP3bits            RES 1     ; 0xF4F
UEP4                
UEP4bits            RES 1     ; 0xF50
UEP5                
UEP5bits            RES 1     ; 0xF51
UEP6                
UEP6bits            RES 1     ; 0xF52
UEP7                
UEP7bits            RES 1     ; 0xF53
UEP8                
UEP8bits            RES 1     ; 0xF54
UEP9                
UEP9bits            RES 1     ; 0xF55
UEP10               
UEP10bits           RES 1     ; 0xF56
UEP11               
UEP11bits           RES 1     ; 0xF57
UEP12               
UEP12bits           RES 1     ; 0xF58
UEP13               
UEP13bits           RES 1     ; 0xF59
UEP14               
UEP14bits           RES 1     ; 0xF5A
UEP15               
UEP15bits           RES 1     ; 0xF5B
UIE                 
UIEbits             RES 1     ; 0xF5C
UEIE                
UEIEbits            RES 1     ; 0xF5D
UADDR               
UADDRbits           RES 1     ; 0xF5E
UCFG                
UCFGbits            RES 1     ; 0xF5F

;*** Set all of memory to zeroes ***/
; Use FSR0 to increment through memory from address 0x0
; to the end of the last bank skipping sfrs and
; unimplemented addresses.

    CODE
__zero_memory
    movlw  0x40      ;low byte of (end address + 1)
    movwf  PRODL, 0
    lfsr   0, 0x0    ;start address
    movlw  0xF       ;high byte of (end address + 1)
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

        GLOBAL PMSTAT
        GLOBAL PMSTATLbits
        GLOBAL PMSTATL
        GLOBAL PMSTATHbits
        GLOBAL PMSTATH
        GLOBAL PMELbits
        GLOBAL PMEL
        GLOBAL PMEN
        GLOBAL PMEHbits
        GLOBAL PMEH
        GLOBAL PMDIN2
        GLOBAL PMDIN2L
        GLOBAL PMDIN2H
        GLOBAL PMDOUT2
        GLOBAL PMDOUT2L
        GLOBAL PMDOUT2H
        GLOBAL PMMODE
        GLOBAL PMMODELbits
        GLOBAL PMMODEL
        GLOBAL PMMODEHbits
        GLOBAL PMMODEH
        GLOBAL PMCON
        GLOBAL PMCONLbits
        GLOBAL PMCONL
        GLOBAL PMCONHbits
        GLOBAL PMCONH
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
        GLOBAL PMDIN1
        GLOBAL PMDIN1L
        GLOBAL PMDIN1H
        GLOBAL PMADDR
        GLOBAL PMADDRL
        GLOBAL PMDOUT
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
        GLOBAL T4CONbits
        GLOBAL T4CON
        GLOBAL CVRCONbits
        GLOBAL CVRCON
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
        GLOBAL PORTFbits
        GLOBAL PORTF
        GLOBAL PORTGbits
        GLOBAL PORTG
        GLOBAL PORTHbits
        GLOBAL PORTH
        GLOBAL PORTJbits
        GLOBAL PORTJ
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
        GLOBAL LATHbits
        GLOBAL LATH
        GLOBAL LATJbits
        GLOBAL LATJ
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
        GLOBAL DDRFbits
        GLOBAL DDRF
        GLOBAL TRISFbits
        GLOBAL TRISF
        GLOBAL DDRGbits
        GLOBAL DDRG
        GLOBAL TRISGbits
        GLOBAL TRISG
        GLOBAL DDRHbits
        GLOBAL DDRH
        GLOBAL TRISHbits
        GLOBAL TRISH
        GLOBAL DDRJbits
        GLOBAL DDRJ
        GLOBAL TRISJbits
        GLOBAL TRISJ
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
        GLOBAL CCP3CONbits
        GLOBAL CCP3CON
        GLOBAL ECCP3CONbits
        GLOBAL ECCP3CON
        GLOBAL CCPR3
        GLOBAL CCPR3L
        GLOBAL CCPR3H
        GLOBAL ECCP3DELbits
        GLOBAL ECCP3DEL
        GLOBAL ECCP3ASbits
        GLOBAL ECCP3AS
        GLOBAL CCP2CONbits
        GLOBAL CCP2CON
        GLOBAL ECCP2CONbits
        GLOBAL ECCP2CON
        GLOBAL CCPR2
        GLOBAL CCPR2L
        GLOBAL CCPR2H
        GLOBAL ECCP2DELbits
        GLOBAL ECCP2DEL
        GLOBAL ECCP2ASbits
        GLOBAL ECCP2AS
        GLOBAL CCP1CONbits
        GLOBAL CCP1CON
        GLOBAL ECCP1CONbits
        GLOBAL ECCP1CON
        GLOBAL CCPR1
        GLOBAL CCPR1L
        GLOBAL CCPR1H
        GLOBAL ECCP1DELbits
        GLOBAL ECCP1DEL
        GLOBAL ECCP1ASbits
        GLOBAL ECCP1AS
        GLOBAL WDTCONbits
        GLOBAL WDTCON
        GLOBAL ADCON1bits
        GLOBAL ADCON1
        GLOBAL ANCON0bits
        GLOBAL ANCON0
        GLOBAL ADCON0bits
        GLOBAL ADCON0
        GLOBAL ANCON1bits
        GLOBAL ANCON1
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
        GLOBAL MEMCONbits
        GLOBAL MEMCON
        GLOBAL PR2
        GLOBAL PADCFG1bits
        GLOBAL PADCFG1
        GLOBAL TMR2
        GLOBAL ODCON3bits
        GLOBAL ODCON3
        GLOBAL T1CONbits
        GLOBAL T1CON
        GLOBAL ODCON2bits
        GLOBAL ODCON2
        GLOBAL TMR1L
        GLOBAL ODCON1bits
        GLOBAL ODCON1
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
        GLOBAL REFOCONbits
        GLOBAL REFOCON
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
