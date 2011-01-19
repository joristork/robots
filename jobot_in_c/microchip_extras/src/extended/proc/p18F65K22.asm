        LIST P=18F65K22
        NOLIST
;-------------------------------------------------------------------------
; MPLAB-Cxx  PIC18F65K22 processor definition module
; 
; (c) Copyright 1999-2010 Microchip Technology, All rights reserved
;-------------------------------------------------------------------------

SFR_UNBANKED0       UDATA_ACS H'F60'

PIE6                
PIE6bits            RES 1     ; 0xF60
EEDATA              RES 1     ; 0xF61
EEADR               RES 1     ; 0xF62
EEADRH              RES 1     ; 0xF63
OSCCON2             
OSCCON2bits         RES 1     ; 0xF64
BAUDCON             
BAUDCONbits         
BAUDCON1            
BAUDCON1bits        
BAUDCTL             
BAUDCTLbits         RES 1     ; 0xF65
SSP2CON2            
SSP2CON2bits        RES 1     ; 0xF66
SSP2CON1            
SSP2CON1bits        RES 1     ; 0xF67
SSP2STAT            
SSP2STATbits        RES 1     ; 0xF68
SSP2ADD             
SSP2MSK             
SSP2MSKbits         RES 1     ; 0xF69
SSP2BUF             RES 1     ; 0xF6A
T4CON               
T4CONbits           RES 1     ; 0xF6B
PR4                 RES 1     ; 0xF6C
TMR4                RES 1     ; 0xF6D
CCP7CON             
CCP7CONbits         RES 1     ; 0xF6E
CCPR7               
CCPR7L              RES 1     ; 0xF6F
CCPR7H              RES 1     ; 0xF70
CCP6CON             
CCP6CONbits         RES 1     ; 0xF71
CCPR6               
CCPR6L              RES 1     ; 0xF72
CCPR6H              RES 1     ; 0xF73
CCP5CON             
CCP5CONbits         RES 1     ; 0xF74
CCPR5               
CCPR5L              RES 1     ; 0xF75
CCPR5H              RES 1     ; 0xF76
CCP4CON             
CCP4CONbits         RES 1     ; 0xF77
CCPR4               
CCPR4L              RES 1     ; 0xF78
CCPR4H              RES 1     ; 0xF79
T5GCON              
T5GCONbits          RES 1     ; 0xF7A
T5CON               
T5CONbits           RES 1     ; 0xF7B
TMR5L               RES 1     ; 0xF7C
TMR5H               RES 1     ; 0xF7D
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
                    RES 2
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
                    RES 2
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
                    RES 2
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
PIR6                
PIR6bits            RES 1     ; 0xFA6
PSPCON              
PSPCONbits          RES 1     ; 0xFA7
HLVDCON             
HLVDCONbits         RES 1     ; 0xFA8
IPR6                
IPR6bits            RES 1     ; 0xFA9
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
PIE5                
PIE5bits            RES 1     ; 0xFB9
PIR5                
PIR5bits            RES 1     ; 0xFBA
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
WDTCON              
WDTCONbits          RES 1     ; 0xFD1
IPR5                
IPR5bits            RES 1     ; 0xFD2
OSCCON              
OSCCONbits          RES 1     ; 0xFD3
SPBRGH1             RES 1     ; 0xFD4
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

SFR_BANKED0         UDATA H'F16'

PMD3                
PMD3bits            RES 1     ; 0xF16
PMD2                
PMD2bits            RES 1     ; 0xF17
PMD1                
PMD1bits            RES 1     ; 0xF18
PMD0                
PMD0bits            RES 1     ; 0xF19
PSTR3CON            
PSTR3CONbits        RES 1     ; 0xF1A
PSTR2CON            
PSTR2CONbits        RES 1     ; 0xF1B
TXREG2              RES 1     ; 0xF1C
RCREG2              RES 1     ; 0xF1D
SPBRG2              RES 1     ; 0xF1E
SPBRGH2             RES 1     ; 0xF1F
BAUDCON2            
BAUDCON2bits        RES 1     ; 0xF20
TXSTA2              
TXSTA2bits          RES 1     ; 0xF21
RCSTA2              
RCSTA2bits          RES 1     ; 0xF22
ANCON2              
ANCON2bits          RES 1     ; 0xF23
ANCON1              
ANCON1bits          RES 1     ; 0xF24
ANCON0              
ANCON0bits          RES 1     ; 0xF25

SFR_BANKED1         UDATA H'F27'
ODCON3              
ODCON3bits          RES 1     ; 0xF27
ODCON2              
ODCON2bits          RES 1     ; 0xF28
ODCON1              
ODCON1bits          RES 1     ; 0xF29
REFOCON             
REFOCONbits         RES 1     ; 0xF2A
CCPTMRS2            
CCPTMRS2bits        RES 1     ; 0xF2B
CCPTMRS1            
CCPTMRS1bits        RES 1     ; 0xF2C
CCPTMRS0            
CCPTMRS0bits        RES 1     ; 0xF2D
CM3CON              
CM3CONbits          
CM3CON1             
CM3CON1bits         RES 1     ; 0xF2E
CM2CON              
CM2CONbits          
CM2CON1             
CM2CON1bits         RES 1     ; 0xF2F

SFR_BANKED2         UDATA H'F36'
T8CON               
T8CONbits           RES 1     ; 0xF36
PR8                 RES 1     ; 0xF37
TMR8                RES 1     ; 0xF38
T6CON               
T6CONbits           RES 1     ; 0xF39
PR6                 RES 1     ; 0xF3A
TMR6                RES 1     ; 0xF3B

SFR_BANKED3         UDATA H'F46'
CCP8CON             
CCP8CONbits         RES 1     ; 0xF46
CCPR8               
CCPR8L              RES 1     ; 0xF47
CCPR8H              RES 1     ; 0xF48
CCP3CON             
CCP3CONbits         RES 1     ; 0xF49
CCPR3               
CCPR3L              RES 1     ; 0xF4A
CCPR3H              RES 1     ; 0xF4B
ECCP3DEL            
ECCP3DELbits        RES 1     ; 0xF4C
ECCP3AS             
ECCP3ASbits         RES 1     ; 0xF4D
CCP2CON             
CCP2CONbits         
ECCP2CON            
ECCP2CONbits        RES 1     ; 0xF4E
CCPR2               
CCPR2L              RES 1     ; 0xF4F
CCPR2H              RES 1     ; 0xF50
ECCP2DEL            
ECCP2DELbits        
PWM2CON             
PWM2CONbits         RES 1     ; 0xF51
ECCP2AS             
ECCP2ASbits         RES 1     ; 0xF52
PADCFG1             
PADCFG1bits         RES 1     ; 0xF53
CM1CON              
CM1CONbits          
CM1CON1             
CM1CON1bits         RES 1     ; 0xF54
CTMUICON            
CTMUICONbits        RES 1     ; 0xF55
CTMUCONL            
CTMUCONLbits        RES 1     ; 0xF56
CTMUCONH            
CTMUCONHbits        RES 1     ; 0xF57
ALRMVAL             
ALRMVALL            RES 1     ; 0xF58
ALRMVALH            RES 1     ; 0xF59
ALRMRPT             
ALRMRPTbits         RES 1     ; 0xF5A
ALRMCFG             
ALRMCFGbits         RES 1     ; 0xF5B
RTCVAL              
RTCVALL             RES 1     ; 0xF5C
RTCVALH             RES 1     ; 0xF5D
RTCCAL              
RTCCALbits          RES 1     ; 0xF5E
RTCCFG              
RTCCFGbits          RES 1     ; 0xF5F

;*** Set all of memory to zeroes ***/
; Use FSR0 to increment through memory from address 0x0
; to the end of the last bank skipping sfrs and
; unimplemented addresses.

    CODE
__zero_memory
    movlw  0x0       ;low byte of (end address + 1)
    movwf  PRODL, 0
    lfsr   0, 0x0    ;start address
    movlw  0x8       ;high byte of (end address + 1)
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

        GLOBAL PMD3bits
        GLOBAL PMD3
        GLOBAL PMD2bits
        GLOBAL PMD2
        GLOBAL PMD1bits
        GLOBAL PMD1
        GLOBAL PMD0bits
        GLOBAL PMD0
        GLOBAL PSTR3CONbits
        GLOBAL PSTR3CON
        GLOBAL PSTR2CONbits
        GLOBAL PSTR2CON
        GLOBAL TXREG2
        GLOBAL RCREG2
        GLOBAL SPBRG2
        GLOBAL SPBRGH2
        GLOBAL BAUDCON2bits
        GLOBAL BAUDCON2
        GLOBAL TXSTA2bits
        GLOBAL TXSTA2
        GLOBAL RCSTA2bits
        GLOBAL RCSTA2
        GLOBAL ANCON2bits
        GLOBAL ANCON2
        GLOBAL ANCON1bits
        GLOBAL ANCON1
        GLOBAL ANCON0bits
        GLOBAL ANCON0
        GLOBAL ODCON3bits
        GLOBAL ODCON3
        GLOBAL ODCON2bits
        GLOBAL ODCON2
        GLOBAL ODCON1bits
        GLOBAL ODCON1
        GLOBAL REFOCONbits
        GLOBAL REFOCON
        GLOBAL CCPTMRS2bits
        GLOBAL CCPTMRS2
        GLOBAL CCPTMRS1bits
        GLOBAL CCPTMRS1
        GLOBAL CCPTMRS0bits
        GLOBAL CCPTMRS0
        GLOBAL CM3CONbits
        GLOBAL CM3CON
        GLOBAL CM3CON1bits
        GLOBAL CM3CON1
        GLOBAL CM2CONbits
        GLOBAL CM2CON
        GLOBAL CM2CON1bits
        GLOBAL CM2CON1
        GLOBAL T8CONbits
        GLOBAL T8CON
        GLOBAL PR8
        GLOBAL TMR8
        GLOBAL T6CONbits
        GLOBAL T6CON
        GLOBAL PR6
        GLOBAL TMR6
        GLOBAL CCP8CONbits
        GLOBAL CCP8CON
        GLOBAL CCPR8
        GLOBAL CCPR8L
        GLOBAL CCPR8H
        GLOBAL CCP3CONbits
        GLOBAL CCP3CON
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
        GLOBAL PWM2CONbits
        GLOBAL PWM2CON
        GLOBAL ECCP2ASbits
        GLOBAL ECCP2AS
        GLOBAL PADCFG1bits
        GLOBAL PADCFG1
        GLOBAL CM1CONbits
        GLOBAL CM1CON
        GLOBAL CM1CON1bits
        GLOBAL CM1CON1
        GLOBAL CTMUICONbits
        GLOBAL CTMUICON
        GLOBAL CTMUCONLbits
        GLOBAL CTMUCONL
        GLOBAL CTMUCONHbits
        GLOBAL CTMUCONH
        GLOBAL ALRMVAL
        GLOBAL ALRMVALL
        GLOBAL ALRMVALH
        GLOBAL ALRMRPTbits
        GLOBAL ALRMRPT
        GLOBAL ALRMCFGbits
        GLOBAL ALRMCFG
        GLOBAL RTCVAL
        GLOBAL RTCVALL
        GLOBAL RTCVALH
        GLOBAL RTCCALbits
        GLOBAL RTCCAL
        GLOBAL RTCCFGbits
        GLOBAL RTCCFG
        GLOBAL PIE6bits
        GLOBAL PIE6
        GLOBAL EEDATA
        GLOBAL EEADR
        GLOBAL EEADRH
        GLOBAL OSCCON2bits
        GLOBAL OSCCON2
        GLOBAL BAUDCONbits
        GLOBAL BAUDCON
        GLOBAL BAUDCON1bits
        GLOBAL BAUDCON1
        GLOBAL BAUDCTLbits
        GLOBAL BAUDCTL
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
        GLOBAL CCP7CONbits
        GLOBAL CCP7CON
        GLOBAL CCPR7
        GLOBAL CCPR7L
        GLOBAL CCPR7H
        GLOBAL CCP6CONbits
        GLOBAL CCP6CON
        GLOBAL CCPR6
        GLOBAL CCPR6L
        GLOBAL CCPR6H
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
        GLOBAL T5GCONbits
        GLOBAL T5GCON
        GLOBAL T5CONbits
        GLOBAL T5CON
        GLOBAL TMR5L
        GLOBAL TMR5H
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
        GLOBAL PIR6bits
        GLOBAL PIR6
        GLOBAL PSPCONbits
        GLOBAL PSPCON
        GLOBAL HLVDCONbits
        GLOBAL HLVDCON
        GLOBAL IPR6bits
        GLOBAL IPR6
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
        GLOBAL PIE5bits
        GLOBAL PIE5
        GLOBAL PIR5bits
        GLOBAL PIR5
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
        GLOBAL WDTCONbits
        GLOBAL WDTCON
        GLOBAL IPR5bits
        GLOBAL IPR5
        GLOBAL OSCCONbits
        GLOBAL OSCCON
        GLOBAL SPBRGH1
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
