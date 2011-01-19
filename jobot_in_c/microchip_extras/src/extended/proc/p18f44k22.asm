        LIST P=18F44K22
        NOLIST
;-------------------------------------------------------------------------
; MPLAB-Cxx  PIC18F44K22 processor definition module
; 
; (c) Copyright 1999-2010 Microchip Technology, All rights reserved
;-------------------------------------------------------------------------

SFR_UNBANKED0       UDATA_ACS H'F60'

SLRCON              
SLRCONbits          RES 1     ; 0xF60
WPUB                
WPUBbits            RES 1     ; 0xF61
IOCB                
IOCBbits            RES 1     ; 0xF62
PSTR2CON            
PSTR2CONbits        RES 1     ; 0xF63
CCP2AS              
CCP2ASbits          
ECCP2AS             
ECCP2ASbits         RES 1     ; 0xF64
PWM2CON             
PWM2CONbits         RES 1     ; 0xF65
CCP2CON             
CCP2CONbits         RES 1     ; 0xF66
CCPR2               
CCPR2L              RES 1     ; 0xF67
CCPR2H              RES 1     ; 0xF68
SSP2CON3            
SSP2CON3bits        RES 1     ; 0xF69
SSP2MSK             
SSP2MSKbits         RES 1     ; 0xF6A
SSP2CON2            
SSP2CON2bits        RES 1     ; 0xF6B
SSP2CON1            
SSP2CON1bits        RES 1     ; 0xF6C
SSP2STAT            
SSP2STATbits        RES 1     ; 0xF6D
SSP2ADD             RES 1     ; 0xF6E
SSP2BUF             RES 1     ; 0xF6F
BAUD2CON            
BAUD2CONbits        
BAUDCON2            
BAUDCON2bits        RES 1     ; 0xF70
RC2STA              
RC2STAbits          
RCSTA2              
RCSTA2bits          RES 1     ; 0xF71
TX2STA              
TX2STAbits          
TXSTA2              
TXSTA2bits          RES 1     ; 0xF72
TX2REG              
TXREG2              RES 1     ; 0xF73
RC2REG              
RCREG2              RES 1     ; 0xF74
SP2BRG              
SPBRG2              RES 1     ; 0xF75
SP2BRGH             
SPBRGH2             RES 1     ; 0xF76
CM12CON             
CM12CONbits         
CM2CON1             
CM2CON1bits         RES 1     ; 0xF77
CM2CON              
CM2CONbits          
CM2CON0             
CM2CON0bits         RES 1     ; 0xF78
CM1CON              
CM1CONbits          
CM1CON0             
CM1CON0bits         RES 1     ; 0xF79
PIE4                
PIE4bits            RES 1     ; 0xF7A
PIR4                
PIR4bits            RES 1     ; 0xF7B
IPR4                
IPR4bits            RES 1     ; 0xF7C
PIE5                
PIE5bits            RES 1     ; 0xF7D
PIR5                
PIR5bits            RES 1     ; 0xF7E
IPR5                
IPR5bits            RES 1     ; 0xF7F
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
HLVDCON             
HLVDCONbits         
LVDCON              
LVDCONbits          RES 1     ; 0xF9C
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
EEADR               
EEADRbits           RES 1     ; 0xFA9
                    RES 1
RC1STA              
RC1STAbits          
RCSTA               
RCSTAbits           
RCSTA1              
RCSTA1bits          RES 1     ; 0xFAB
TX1STA              
TX1STAbits          
TXSTA               
TXSTAbits           
TXSTA1              
TXSTA1bits          RES 1     ; 0xFAC
TX1REG              
TX1REGbits          
TXREG               
TXREGbits           
TXREG1              
TXREG1bits          RES 1     ; 0xFAD
RC1REG              
RC1REGbits          
RCREG               
RCREGbits           
RCREG1              
RCREG1bits          RES 1     ; 0xFAE
SP1BRG              
SP1BRGbits          
SPBRG               
SPBRGbits           
SPBRG1              
SPBRG1bits          RES 1     ; 0xFAF
SP1BRGH             
SP1BRGHbits         
SPBRGH              
SPBRGHbits          
SPBRGH1             
SPBRGH1bits         RES 1     ; 0xFB0
T3CON               
T3CONbits           RES 1     ; 0xFB1
TMR3L               RES 1     ; 0xFB2
TMR3H               RES 1     ; 0xFB3
T3GCON              
T3GCONbits          RES 1     ; 0xFB4
                    RES 1
ECCP1AS             
ECCP1ASbits         
ECCPAS              
ECCPASbits          RES 1     ; 0xFB6
PWM1CON             
PWM1CONbits         
PWMCON              
PWMCONbits          RES 1     ; 0xFB7
BAUD1CON            
BAUD1CONbits        
BAUDCON             
BAUDCONbits         
BAUDCON1            
BAUDCON1bits        
BAUDCTL             
BAUDCTLbits         RES 1     ; 0xFB8
PSTR1CON            
PSTR1CONbits        
PSTRCON             
PSTRCONbits         RES 1     ; 0xFB9
T2CON               
T2CONbits           RES 1     ; 0xFBA
PR2                 RES 1     ; 0xFBB
TMR2                RES 1     ; 0xFBC
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
SSP1BUFbits         
SSPBUF              
SSPBUFbits          RES 1     ; 0xFC9
SSP1MSK             
SSP1MSKbits         
SSPMSK              
SSPMSKbits          RES 1     ; 0xFCA
SSP1CON3            
SSP1CON3bits        
SSPCON3             
SSPCON3bits         RES 1     ; 0xFCB
T1GCON              
T1GCONbits          RES 1     ; 0xFCC
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
W                   
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
TBLPTRU             
TBLPTRUbits         RES 1     ; 0xFF8
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

SFR_BANKED0         UDATA H'F38'

ANSELA              
ANSELAbits          RES 1     ; 0xF38
ANSELB              
ANSELBbits          RES 1     ; 0xF39
ANSELC              
ANSELCbits          RES 1     ; 0xF3A
ANSELD              
ANSELDbits          RES 1     ; 0xF3B
ANSELE              
ANSELEbits          RES 1     ; 0xF3C
PMD2                
PMD2bits            RES 1     ; 0xF3D
PMD1                
PMD1bits            RES 1     ; 0xF3E
PMD0                
PMD0bits            RES 1     ; 0xF3F
DACCON1             
DACCON1bits         
VREFCON2            
VREFCON2bits        RES 1     ; 0xF40
DACCON0             
DACCON0bits         
VREFCON1            
VREFCON1bits        RES 1     ; 0xF41
FVRCON              
FVRCONbits          
VREFCON0            
VREFCON0bits        RES 1     ; 0xF42
CTMUICON            
CTMUICONbits        
CTMUICONH           
CTMUICONHbits       RES 1     ; 0xF43
CTMUCON1            
CTMUCON1bits        
CTMUCONL            
CTMUCONLbits        RES 1     ; 0xF44
CTMUCON0            
CTMUCON0bits        
CTMUCONH            
CTMUCONHbits        RES 1     ; 0xF45
SRCON1              
SRCON1bits          RES 1     ; 0xF46
SRCON0              
SRCON0bits          RES 1     ; 0xF47
CCPTMRS1            
CCPTMRS1bits        RES 1     ; 0xF48
CCPTMRS0            
CCPTMRS0bits        RES 1     ; 0xF49
T6CON               
T6CONbits           RES 1     ; 0xF4A
PR6                 RES 1     ; 0xF4B
TMR6                RES 1     ; 0xF4C
T5GCON              
T5GCONbits          RES 1     ; 0xF4D
T5CON               
T5CONbits           RES 1     ; 0xF4E
TMR5L               RES 1     ; 0xF4F
TMR5H               RES 1     ; 0xF50
T4CON               
T4CONbits           RES 1     ; 0xF51
PR4                 RES 1     ; 0xF52
TMR4                RES 1     ; 0xF53
CCP5CON             
CCP5CONbits         RES 1     ; 0xF54
CCPR5               
CCPR5L              RES 1     ; 0xF55
CCPR5H              RES 1     ; 0xF56
CCP4CON             
CCP4CONbits         RES 1     ; 0xF57
CCPR4               
CCPR4L              RES 1     ; 0xF58
CCPR4H              RES 1     ; 0xF59
PSTR3CON            
PSTR3CONbits        RES 1     ; 0xF5A
CCP3AS              
CCP3ASbits          
ECCP3AS             
ECCP3ASbits         RES 1     ; 0xF5B
PWM3CON             
PWM3CONbits         RES 1     ; 0xF5C
CCP3CON             
CCP3CONbits         RES 1     ; 0xF5D
CCPR3               
CCPR3L              RES 1     ; 0xF5E
CCPR3H              RES 1     ; 0xF5F

;*** Set all of memory to zeroes ***/
; Use FSR0 to increment through memory from address 0x0
; to the end of the last bank skipping sfrs and
; unimplemented addresses.

    CODE
__zero_memory
    movlw  0x0       ;low byte of (end address + 1)
    movwf  PRODL, 0
    lfsr   0, 0x0    ;start address
    movlw  0x3       ;high byte of (end address + 1)
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

        GLOBAL ANSELAbits
        GLOBAL ANSELA
        GLOBAL ANSELBbits
        GLOBAL ANSELB
        GLOBAL ANSELCbits
        GLOBAL ANSELC
        GLOBAL ANSELDbits
        GLOBAL ANSELD
        GLOBAL ANSELEbits
        GLOBAL ANSELE
        GLOBAL PMD2bits
        GLOBAL PMD2
        GLOBAL PMD1bits
        GLOBAL PMD1
        GLOBAL PMD0bits
        GLOBAL PMD0
        GLOBAL DACCON1bits
        GLOBAL DACCON1
        GLOBAL VREFCON2bits
        GLOBAL VREFCON2
        GLOBAL DACCON0bits
        GLOBAL DACCON0
        GLOBAL VREFCON1bits
        GLOBAL VREFCON1
        GLOBAL FVRCONbits
        GLOBAL FVRCON
        GLOBAL VREFCON0bits
        GLOBAL VREFCON0
        GLOBAL CTMUICONbits
        GLOBAL CTMUICON
        GLOBAL CTMUICONHbits
        GLOBAL CTMUICONH
        GLOBAL CTMUCON1bits
        GLOBAL CTMUCON1
        GLOBAL CTMUCONLbits
        GLOBAL CTMUCONL
        GLOBAL CTMUCON0bits
        GLOBAL CTMUCON0
        GLOBAL CTMUCONHbits
        GLOBAL CTMUCONH
        GLOBAL SRCON1bits
        GLOBAL SRCON1
        GLOBAL SRCON0bits
        GLOBAL SRCON0
        GLOBAL CCPTMRS1bits
        GLOBAL CCPTMRS1
        GLOBAL CCPTMRS0bits
        GLOBAL CCPTMRS0
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
        GLOBAL T4CONbits
        GLOBAL T4CON
        GLOBAL PR4
        GLOBAL TMR4
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
        GLOBAL PSTR3CONbits
        GLOBAL PSTR3CON
        GLOBAL CCP3ASbits
        GLOBAL CCP3AS
        GLOBAL ECCP3ASbits
        GLOBAL ECCP3AS
        GLOBAL PWM3CONbits
        GLOBAL PWM3CON
        GLOBAL CCP3CONbits
        GLOBAL CCP3CON
        GLOBAL CCPR3
        GLOBAL CCPR3L
        GLOBAL CCPR3H
        GLOBAL SLRCONbits
        GLOBAL SLRCON
        GLOBAL WPUBbits
        GLOBAL WPUB
        GLOBAL IOCBbits
        GLOBAL IOCB
        GLOBAL PSTR2CONbits
        GLOBAL PSTR2CON
        GLOBAL CCP2ASbits
        GLOBAL CCP2AS
        GLOBAL ECCP2ASbits
        GLOBAL ECCP2AS
        GLOBAL PWM2CONbits
        GLOBAL PWM2CON
        GLOBAL CCP2CONbits
        GLOBAL CCP2CON
        GLOBAL CCPR2
        GLOBAL CCPR2L
        GLOBAL CCPR2H
        GLOBAL SSP2CON3bits
        GLOBAL SSP2CON3
        GLOBAL SSP2MSKbits
        GLOBAL SSP2MSK
        GLOBAL SSP2CON2bits
        GLOBAL SSP2CON2
        GLOBAL SSP2CON1bits
        GLOBAL SSP2CON1
        GLOBAL SSP2STATbits
        GLOBAL SSP2STAT
        GLOBAL SSP2ADD
        GLOBAL SSP2BUF
        GLOBAL BAUD2CONbits
        GLOBAL BAUD2CON
        GLOBAL BAUDCON2bits
        GLOBAL BAUDCON2
        GLOBAL RC2STAbits
        GLOBAL RC2STA
        GLOBAL RCSTA2bits
        GLOBAL RCSTA2
        GLOBAL TX2STAbits
        GLOBAL TX2STA
        GLOBAL TXSTA2bits
        GLOBAL TXSTA2
        GLOBAL TX2REG
        GLOBAL TXREG2
        GLOBAL RC2REG
        GLOBAL RCREG2
        GLOBAL SP2BRG
        GLOBAL SPBRG2
        GLOBAL SP2BRGH
        GLOBAL SPBRGH2
        GLOBAL CM12CONbits
        GLOBAL CM12CON
        GLOBAL CM2CON1bits
        GLOBAL CM2CON1
        GLOBAL CM2CONbits
        GLOBAL CM2CON
        GLOBAL CM2CON0bits
        GLOBAL CM2CON0
        GLOBAL CM1CONbits
        GLOBAL CM1CON
        GLOBAL CM1CON0bits
        GLOBAL CM1CON0
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
        GLOBAL IPR5bits
        GLOBAL IPR5
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
        GLOBAL HLVDCONbits
        GLOBAL HLVDCON
        GLOBAL LVDCONbits
        GLOBAL LVDCON
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
        GLOBAL EEADRbits
        GLOBAL EEADR
        GLOBAL RC1STAbits
        GLOBAL RC1STA
        GLOBAL RCSTAbits
        GLOBAL RCSTA
        GLOBAL RCSTA1bits
        GLOBAL RCSTA1
        GLOBAL TX1STAbits
        GLOBAL TX1STA
        GLOBAL TXSTAbits
        GLOBAL TXSTA
        GLOBAL TXSTA1bits
        GLOBAL TXSTA1
        GLOBAL TX1REGbits
        GLOBAL TX1REG
        GLOBAL TXREGbits
        GLOBAL TXREG
        GLOBAL TXREG1bits
        GLOBAL TXREG1
        GLOBAL RC1REGbits
        GLOBAL RC1REG
        GLOBAL RCREGbits
        GLOBAL RCREG
        GLOBAL RCREG1bits
        GLOBAL RCREG1
        GLOBAL SP1BRGbits
        GLOBAL SP1BRG
        GLOBAL SPBRGbits
        GLOBAL SPBRG
        GLOBAL SPBRG1bits
        GLOBAL SPBRG1
        GLOBAL SP1BRGHbits
        GLOBAL SP1BRGH
        GLOBAL SPBRGHbits
        GLOBAL SPBRGH
        GLOBAL SPBRGH1bits
        GLOBAL SPBRGH1
        GLOBAL T3CONbits
        GLOBAL T3CON
        GLOBAL TMR3L
        GLOBAL TMR3H
        GLOBAL T3GCONbits
        GLOBAL T3GCON
        GLOBAL ECCP1ASbits
        GLOBAL ECCP1AS
        GLOBAL ECCPASbits
        GLOBAL ECCPAS
        GLOBAL PWM1CONbits
        GLOBAL PWM1CON
        GLOBAL PWMCONbits
        GLOBAL PWMCON
        GLOBAL BAUD1CONbits
        GLOBAL BAUD1CON
        GLOBAL BAUDCONbits
        GLOBAL BAUDCON
        GLOBAL BAUDCON1bits
        GLOBAL BAUDCON1
        GLOBAL BAUDCTLbits
        GLOBAL BAUDCTL
        GLOBAL PSTR1CONbits
        GLOBAL PSTR1CON
        GLOBAL PSTRCONbits
        GLOBAL PSTRCON
        GLOBAL T2CONbits
        GLOBAL T2CON
        GLOBAL PR2
        GLOBAL TMR2
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
        GLOBAL SSP1BUFbits
        GLOBAL SSP1BUF
        GLOBAL SSPBUFbits
        GLOBAL SSPBUF
        GLOBAL SSP1MSKbits
        GLOBAL SSP1MSK
        GLOBAL SSPMSKbits
        GLOBAL SSPMSK
        GLOBAL SSP1CON3bits
        GLOBAL SSP1CON3
        GLOBAL SSPCON3bits
        GLOBAL SSPCON3
        GLOBAL T1GCONbits
        GLOBAL T1GCON
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
        GLOBAL W
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
        GLOBAL TBLPTRUbits
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
