        LIST P=18F458
        NOLIST
;-------------------------------------------------------------------------
; MPLAB-Cxx  PIC18F458 processor definition module
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
                    RES 6
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
                    RES 1
RCSTA               
RCSTAbits           RES 1     ; 0xFAB
TXSTA               
TXSTAbits           RES 1     ; 0xFAC
TXREG               RES 1     ; 0xFAD
RCREG               RES 1     ; 0xFAE
SPBRG               RES 1     ; 0xFAF
                    RES 1
T3CON               
T3CONbits           RES 1     ; 0xFB1
TMR3L               RES 1     ; 0xFB2
TMR3H               RES 1     ; 0xFB3
CMCON               
CMCONbits           RES 1     ; 0xFB4
CVRCON              
CVRCONbits          RES 1     ; 0xFB5
ECCPAS              
ECCPASbits          RES 1     ; 0xFB6
ECCP1DEL            
ECCP1DELbits        RES 1     ; 0xFB7
                    RES 2
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
                    RES 1
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

SFR_BANKED0         UDATA H'F00'

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
CANSTATRO4          
CANSTATRO4bits      RES 1     ; 0xF2E

SFR_BANKED1         UDATA H'F30'
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
CANSTATRO3          
CANSTATRO3bits      RES 1     ; 0xF3E

SFR_BANKED2         UDATA H'F40'
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
CANSTATRO2          
CANSTATRO2bits      RES 1     ; 0xF4E

SFR_BANKED3         UDATA H'F50'
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
CANSTATRO1          
CANSTATRO1bits      RES 1     ; 0xF5E

;*** Set all of memory to zeroes ***/
; Use FSR0 to increment through memory from address 0x0
; to the end of the last bank skipping sfrs and
; unimplemented addresses.

    CODE
__zero_memory
    movlw  0x0       ;low byte of (end address + 1)
    movwf  PRODL, 0
    lfsr   0, 0x0    ;start address
    movlw  0x6       ;high byte of (end address + 1)
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
        GLOBAL CANSTATRO4bits
        GLOBAL CANSTATRO4
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
        GLOBAL CANSTATRO3bits
        GLOBAL CANSTATRO3
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
        GLOBAL CANSTATRO2bits
        GLOBAL CANSTATRO2
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
        GLOBAL CANSTATRO1bits
        GLOBAL CANSTATRO1
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
        GLOBAL RCSTAbits
        GLOBAL RCSTA
        GLOBAL TXSTAbits
        GLOBAL TXSTA
        GLOBAL TXREG
        GLOBAL RCREG
        GLOBAL SPBRG
        GLOBAL T3CONbits
        GLOBAL T3CON
        GLOBAL TMR3L
        GLOBAL TMR3H
        GLOBAL CMCONbits
        GLOBAL CMCON
        GLOBAL CVRCONbits
        GLOBAL CVRCON
        GLOBAL ECCPASbits
        GLOBAL ECCPAS
        GLOBAL ECCP1DELbits
        GLOBAL ECCP1DEL
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
        GLOBAL INTCON1bits
        GLOBAL INTCON1
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
