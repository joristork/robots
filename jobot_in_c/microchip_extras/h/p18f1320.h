/*-------------------------------------------------------------------------
 * MPLAB-Cxx  PIC18F1320 processor header
 *
 * (c) Copyright 1999-2010 Microchip Technology, All rights reserved
 *-------------------------------------------------------------------------*/

#ifndef __18F1320_H
#define __18F1320_H

extern volatile near unsigned char       PORTA;
extern volatile near union {
  struct {
    unsigned RA0:1;
    unsigned RA1:1;
    unsigned RA2:1;
    unsigned RA3:1;
    unsigned RA4:1;
    unsigned RA5:1;
    unsigned RA6:1;
    unsigned RA7:1;
  };
  struct {
    unsigned AN0:1;
    unsigned AN1:1;
    unsigned AN2:1;
    unsigned AN3:1;
    unsigned :1;
    unsigned MCLR:1;
    unsigned CLKO:1;
    unsigned CLKI:1;
  };
  struct {
    unsigned :1;
    unsigned LVDIN:1;
    unsigned VREFM:1;
    unsigned VREFP:1;
    unsigned T0CKI:1;
    unsigned :1;
    unsigned OSC2:1;
    unsigned OSC1:1;
  };
  struct {
    unsigned :5;
    unsigned NOT_MCLR:1;
    unsigned CLKOUT:1;
    unsigned CLKIN:1;
  };
} PORTAbits;
extern volatile near unsigned char       PORTB;
extern volatile near union {
  struct {
    unsigned RB0:1;
    unsigned RB1:1;
    unsigned RB2:1;
    unsigned RB3:1;
    unsigned RB4:1;
    unsigned RB5:1;
    unsigned RB6:1;
    unsigned RB7:1;
  };
  struct {
    unsigned AN4:1;
    unsigned AN5:1;
    unsigned :1;
    unsigned CCP1:1;
    unsigned AN6:1;
    unsigned PGM:1;
    unsigned PGC:1;
    unsigned PGD:1;
  };
  struct {
    unsigned INT0:1;
    unsigned INT1:1;
    unsigned INT2:1;
    unsigned :1;
    unsigned KBI0:1;
    unsigned KBI1:1;
    unsigned KBI2:1;
    unsigned KBI3:1;
  };
  struct {
    unsigned :1;
    unsigned CK:1;
    unsigned P1B:1;
    unsigned P1A:1;
    unsigned DT:1;
    unsigned :1;
    unsigned P1C:1;
    unsigned P1D:1;
  };
  struct {
    unsigned :1;
    unsigned TX:1;
    unsigned :2;
    unsigned RX:1;
    unsigned :1;
    unsigned T1OSO:1;
    unsigned T1OSI:1;
  };
  struct {
    unsigned :6;
    unsigned T13CKI:1;
  };
  struct {
    unsigned :6;
    unsigned T1CKI:1;
  };
} PORTBbits;
extern volatile near unsigned char       LATA;
extern volatile near struct {
  unsigned LATA0:1;
  unsigned LATA1:1;
  unsigned LATA2:1;
  unsigned LATA3:1;
  unsigned LATA4:1;
  unsigned :1;
  unsigned LATA6:1;
  unsigned LATA7:1;
} LATAbits;
extern volatile near unsigned char       LATB;
extern volatile near struct {
  unsigned LATB0:1;
  unsigned LATB1:1;
  unsigned LATB2:1;
  unsigned LATB3:1;
  unsigned LATB4:1;
  unsigned LATB5:1;
  unsigned LATB6:1;
  unsigned LATB7:1;
} LATBbits;
extern volatile near unsigned char       DDRA;
extern volatile near union {
  struct {
    unsigned TRISA0:1;
    unsigned TRISA1:1;
    unsigned TRISA2:1;
    unsigned TRISA3:1;
    unsigned TRISA4:1;
    unsigned :1;
    unsigned TRISA6:1;
    unsigned TRISA7:1;
  };
  struct {
    unsigned RA0:1;
    unsigned RA1:1;
    unsigned RA2:1;
    unsigned RA3:1;
    unsigned RA4:1;
    unsigned :1;
    unsigned RA6:1;
    unsigned RA7:1;
  };
} DDRAbits;
extern volatile near unsigned char       TRISA;
extern volatile near union {
  struct {
    unsigned TRISA0:1;
    unsigned TRISA1:1;
    unsigned TRISA2:1;
    unsigned TRISA3:1;
    unsigned TRISA4:1;
    unsigned :1;
    unsigned TRISA6:1;
    unsigned TRISA7:1;
  };
  struct {
    unsigned RA0:1;
    unsigned RA1:1;
    unsigned RA2:1;
    unsigned RA3:1;
    unsigned RA4:1;
    unsigned :1;
    unsigned RA6:1;
    unsigned RA7:1;
  };
} TRISAbits;
extern volatile near unsigned char       DDRB;
extern volatile near union {
  struct {
    unsigned TRISB0:1;
    unsigned TRISB1:1;
    unsigned TRISB2:1;
    unsigned TRISB3:1;
    unsigned TRISB4:1;
    unsigned TRISB5:1;
    unsigned TRISB6:1;
    unsigned TRISB7:1;
  };
  struct {
    unsigned RB0:1;
    unsigned RB1:1;
    unsigned RB2:1;
    unsigned RB3:1;
    unsigned RB4:1;
    unsigned RB5:1;
    unsigned RB6:1;
    unsigned RB7:1;
  };
} DDRBbits;
extern volatile near unsigned char       TRISB;
extern volatile near union {
  struct {
    unsigned TRISB0:1;
    unsigned TRISB1:1;
    unsigned TRISB2:1;
    unsigned TRISB3:1;
    unsigned TRISB4:1;
    unsigned TRISB5:1;
    unsigned TRISB6:1;
    unsigned TRISB7:1;
  };
  struct {
    unsigned RB0:1;
    unsigned RB1:1;
    unsigned RB2:1;
    unsigned RB3:1;
    unsigned RB4:1;
    unsigned RB5:1;
    unsigned RB6:1;
    unsigned RB7:1;
  };
} TRISBbits;
extern volatile near unsigned char       OSCTUNE;
extern volatile near union {
  struct {
    unsigned TUN:6;
  };
  struct {
    unsigned TUN0:1;
    unsigned TUN1:1;
    unsigned TUN2:1;
    unsigned TUN3:1;
    unsigned TUN4:1;
    unsigned TUN5:1;
  };
} OSCTUNEbits;
extern volatile near unsigned char       PIE1;
extern volatile near struct {
  unsigned TMR1IE:1;
  unsigned TMR2IE:1;
  unsigned CCP1IE:1;
  unsigned :1;
  unsigned TXIE:1;
  unsigned RCIE:1;
  unsigned ADIE:1;
} PIE1bits;
extern volatile near unsigned char       PIR1;
extern volatile near struct {
  unsigned TMR1IF:1;
  unsigned TMR2IF:1;
  unsigned CCP1IF:1;
  unsigned :1;
  unsigned TXIF:1;
  unsigned RCIF:1;
  unsigned ADIF:1;
} PIR1bits;
extern volatile near unsigned char       IPR1;
extern volatile near struct {
  unsigned TMR1IP:1;
  unsigned TMR2IP:1;
  unsigned CCP1IP:1;
  unsigned :1;
  unsigned TXIP:1;
  unsigned RCIP:1;
  unsigned ADIP:1;
} IPR1bits;
extern volatile near unsigned char       PIE2;
extern volatile near struct {
  unsigned :1;
  unsigned TMR3IE:1;
  unsigned LVDIE:1;
  unsigned :1;
  unsigned EEIE:1;
  unsigned :2;
  unsigned OSCFIE:1;
} PIE2bits;
extern volatile near unsigned char       PIR2;
extern volatile near struct {
  unsigned :1;
  unsigned TMR3IF:1;
  unsigned LVDIF:1;
  unsigned :1;
  unsigned EEIF:1;
  unsigned :2;
  unsigned OSCFIF:1;
} PIR2bits;
extern volatile near unsigned char       IPR2;
extern volatile near struct {
  unsigned :1;
  unsigned TMR3IP:1;
  unsigned LVDIP:1;
  unsigned :1;
  unsigned EEIP:1;
  unsigned :2;
  unsigned OSCFIP:1;
} IPR2bits;
extern volatile near unsigned char       EECON1;
extern volatile near struct {
  unsigned RD:1;
  unsigned WR:1;
  unsigned WREN:1;
  unsigned WRERR:1;
  unsigned FREE:1;
  unsigned :1;
  unsigned CFGS:1;
  unsigned EEPGD:1;
} EECON1bits;
extern volatile near unsigned char       EECON2;
extern volatile near unsigned char       EEDATA;
extern volatile near unsigned char       EEADR;
extern volatile near unsigned char       BAUDCTL;
extern volatile near union {
  struct {
    unsigned ABDEN:1;
    unsigned WUE:1;
    unsigned :1;
    unsigned BRG16:1;
    unsigned SCKP:1;
    unsigned :1;
    unsigned RCIDL:1;
  };
  struct {
    unsigned :6;
    unsigned RCMT:1;
  };
} BAUDCTLbits;
extern volatile near unsigned char       RCSTA;
extern volatile near union {
  struct {
    unsigned RX9D:1;
    unsigned OERR:1;
    unsigned FERR:1;
    unsigned ADDEN:1;
    unsigned CREN:1;
    unsigned SREN:1;
    unsigned RX9:1;
    unsigned SPEN:1;
  };
  struct {
    unsigned :3;
    unsigned ADEN:1;
  };
} RCSTAbits;
extern volatile near unsigned char       TXSTA;
extern volatile near struct {
  unsigned TX9D:1;
  unsigned TRMT:1;
  unsigned BRGH:1;
  unsigned SENDB:1;
  unsigned SYNC:1;
  unsigned TXEN:1;
  unsigned TX9:1;
  unsigned CSRC:1;
} TXSTAbits;
extern volatile near unsigned char       TXREG;
extern volatile near unsigned char       RCREG;
extern volatile near unsigned char       SPBRG;
extern volatile near unsigned char       SPBRGH;
extern volatile near unsigned char       T3CON;
extern volatile near union {
  struct {
    unsigned TMR3ON:1;
    unsigned TMR3CS:1;
    unsigned NOT_T3SYNC:1;
    unsigned T3CCP1:1;
    unsigned T3CKPS:2;
    unsigned T3CCP2:1;
    unsigned RD16:1;
  };
  struct {
    unsigned :2;
    unsigned T3SYNC:1;
    unsigned :1;
    unsigned T3CKPS0:1;
    unsigned T3CKPS1:1;
  };
} T3CONbits;
extern volatile near unsigned char       TMR3L;
extern volatile near unsigned char       TMR3H;
extern volatile near unsigned char       ECCPAS;
extern volatile near union {
  struct {
    unsigned PSSBD:2;
    unsigned PSSAC:2;
    unsigned ECCPAS:3;
    unsigned ECCPASE:1;
  };
  struct {
    unsigned PSSBD0:1;
    unsigned PSSBD1:1;
    unsigned PSSAC0:1;
    unsigned PSSAC1:1;
    unsigned ECCPAS0:1;
    unsigned ECCPAS1:1;
    unsigned ECCPAS2:1;
  };
} ECCPASbits;
extern volatile near unsigned char       PWM1CON;
extern volatile near union {
  struct {
    unsigned PDC:7;
    unsigned PRSEN:1;
  };
  struct {
    unsigned PDC0:1;
    unsigned PDC1:1;
    unsigned PDC2:1;
    unsigned PDC3:1;
    unsigned PDC4:1;
    unsigned PDC5:1;
    unsigned PDC6:1;
  };
} PWM1CONbits;
extern volatile near unsigned char       CCP1CON;
extern volatile near union {
  struct {
    unsigned CCP1M:4;
    unsigned DC1B:2;
    unsigned P1M:2;
  };
  struct {
    unsigned CCP1M0:1;
    unsigned CCP1M1:1;
    unsigned CCP1M2:1;
    unsigned CCP1M3:1;
    unsigned DC1B0:1;
    unsigned DC1B1:1;
    unsigned P1M0:1;
    unsigned P1M1:1;
  };
  struct {
    unsigned :4;
    unsigned CCP1Y:1;
    unsigned CCP1X:1;
  };
} CCP1CONbits;
extern volatile near unsigned            CCPR1;
extern volatile near unsigned char       CCPR1L;
extern volatile near unsigned char       CCPR1H;
extern volatile near unsigned char       ADCON2;
extern volatile near union {
  struct {
    unsigned ADCS:3;
    unsigned ACQT:3;
    unsigned :1;
    unsigned ADFM:1;
  };
  struct {
    unsigned ADCS0:1;
    unsigned ADCS1:1;
    unsigned ADCS2:1;
    unsigned ACQT0:1;
    unsigned ACQT1:1;
    unsigned ACQT2:1;
  };
} ADCON2bits;
extern volatile near unsigned char       ADCON1;
extern volatile near union {
  struct {
    unsigned PCFG:7;
  };
  struct {
    unsigned PCFG0:1;
    unsigned PCFG1:1;
    unsigned PCFG2:1;
    unsigned PCFG3:1;
    unsigned PCFG4:1;
    unsigned PCFG5:1;
    unsigned PCFG6:1;
  };
} ADCON1bits;
extern volatile near unsigned char       ADCON0;
extern volatile near union {
  struct {
    unsigned ADON:1;
    unsigned GO_NOT_DONE:1;
    unsigned CHS:3;
    unsigned :1;
    unsigned VCFG:2;
  };
  struct {
    unsigned :1;
    unsigned GO:1;
    unsigned CHS0:1;
    unsigned CHS1:1;
    unsigned CHS2:1;
    unsigned :1;
    unsigned VCFG0:1;
    unsigned VCFG1:1;
  };
  struct {
    unsigned :1;
    unsigned DONE:1;
  };
  struct {
    unsigned :1;
    unsigned NOT_DONE:1;
  };
  struct {
    unsigned :1;
    unsigned GO_DONE:1;
  };
} ADCON0bits;
extern volatile near unsigned            ADRES;
extern volatile near unsigned char       ADRESL;
extern volatile near unsigned char       ADRESH;
extern volatile near unsigned char       T2CON;
extern volatile near union {
  struct {
    unsigned T2CKPS:2;
    unsigned TMR2ON:1;
    unsigned TOUTPS:4;
  };
  struct {
    unsigned T2CKPS0:1;
    unsigned T2CKPS1:1;
    unsigned :1;
    unsigned TOUTPS0:1;
    unsigned TOUTPS1:1;
    unsigned TOUTPS2:1;
    unsigned TOUTPS3:1;
  };
} T2CONbits;
extern volatile near unsigned char       PR2;
extern volatile near unsigned char       TMR2;
extern volatile near unsigned char       T1CON;
extern volatile near union {
  struct {
    unsigned TMR1ON:1;
    unsigned TMR1CS:1;
    unsigned NOT_T1SYNC:1;
    unsigned T1OSCEN:1;
    unsigned T1CKPS:2;
    unsigned T1RUN:1;
    unsigned RD16:1;
  };
  struct {
    unsigned :4;
    unsigned T1CKPS0:1;
    unsigned T1CKPS1:1;
  };
  struct {
    unsigned :2;
    unsigned T1SYNC:1;
  };
} T1CONbits;
extern volatile near unsigned char       TMR1L;
extern volatile near unsigned char       TMR1H;
extern volatile near unsigned char       RCON;
extern volatile near union {
  struct {
    unsigned NOT_BOR:1;
    unsigned NOT_POR:1;
    unsigned NOT_PD:1;
    unsigned NOT_TO:1;
    unsigned NOT_RI:1;
    unsigned :2;
    unsigned IPEN:1;
  };
  struct {
    unsigned BOR:1;
    unsigned POR:1;
    unsigned PD:1;
    unsigned TO:1;
    unsigned RI:1;
  };
} RCONbits;
extern volatile near unsigned char       WDTCON;
extern volatile near union {
  struct {
    unsigned SWDTEN:1;
  };
  struct {
    unsigned SWDTE:1;
  };
} WDTCONbits;
extern volatile near unsigned char       LVDCON;
extern volatile near union {
  struct {
    unsigned LVDL:4;
    unsigned LVDEN:1;
    unsigned IRVST:1;
  };
  struct {
    unsigned LVDL0:1;
    unsigned LVDL1:1;
    unsigned LVDL2:1;
    unsigned LVDL3:1;
    unsigned :1;
    unsigned IVRST:1;
  };
} LVDCONbits;
extern volatile near unsigned char       OSCCON;
extern volatile near union {
  struct {
    unsigned SCS0:1;
    unsigned SCS1:1;
    unsigned IOFS:1;
    unsigned OSTS:1;
    unsigned IRCF:3;
    unsigned IDLEN:1;
  };
  struct {
    unsigned SCS:1;
    unsigned :1;
    unsigned FLTS:1;
    unsigned :1;
    unsigned IRCF0:1;
    unsigned IRCF1:1;
    unsigned IRCF2:1;
  };
} OSCCONbits;
extern volatile near unsigned char       T0CON;
extern volatile near union {
  struct {
    unsigned T0PS:3;
    unsigned PSA:1;
    unsigned T0SE:1;
    unsigned T0CS:1;
    unsigned T08BIT:1;
    unsigned TMR0ON:1;
  };
  struct {
    unsigned T0PS0:1;
    unsigned T0PS1:1;
    unsigned T0PS2:1;
    unsigned :3;
    unsigned T016BIT:1;
  };
} T0CONbits;
extern volatile near unsigned char       TMR0L;
extern volatile near unsigned char       TMR0H;
extern          near unsigned char       STATUS;
extern          near struct {
  unsigned C:1;
  unsigned DC:1;
  unsigned Z:1;
  unsigned OV:1;
  unsigned N:1;
} STATUSbits;
extern          near unsigned            FSR2;
extern          near unsigned char       FSR2L;
extern          near unsigned char       FSR2H;
extern volatile near unsigned char       PLUSW2;
extern volatile near unsigned char       PREINC2;
extern volatile near unsigned char       POSTDEC2;
extern volatile near unsigned char       POSTINC2;
extern          near unsigned char       INDF2;
extern          near unsigned char       BSR;
extern          near unsigned            FSR1;
extern          near unsigned char       FSR1L;
extern          near unsigned char       FSR1H;
extern volatile near unsigned char       PLUSW1;
extern volatile near unsigned char       PREINC1;
extern volatile near unsigned char       POSTDEC1;
extern volatile near unsigned char       POSTINC1;
extern          near unsigned char       INDF1;
extern          near unsigned char       W;
extern          near unsigned char       WREG;
extern          near unsigned            FSR0;
extern          near unsigned char       FSR0L;
extern          near unsigned char       FSR0H;
extern volatile near unsigned char       PLUSW0;
extern volatile near unsigned char       PREINC0;
extern volatile near unsigned char       POSTDEC0;
extern volatile near unsigned char       POSTINC0;
extern          near unsigned char       INDF0;
extern volatile near unsigned char       INTCON3;
extern volatile near union {
  struct {
    unsigned INT1IF:1;
    unsigned INT2IF:1;
    unsigned :1;
    unsigned INT1IE:1;
    unsigned INT2IE:1;
    unsigned :1;
    unsigned INT1IP:1;
    unsigned INT2IP:1;
  };
  struct {
    unsigned INT1F:1;
    unsigned INT2F:1;
    unsigned :1;
    unsigned INT1E:1;
    unsigned INT2E:1;
    unsigned :1;
    unsigned INT1P:1;
    unsigned INT2P:1;
  };
} INTCON3bits;
extern volatile near unsigned char       INTCON2;
extern volatile near union {
  struct {
    unsigned RBIP:1;
    unsigned :1;
    unsigned TMR0IP:1;
    unsigned :1;
    unsigned INTEDG2:1;
    unsigned INTEDG1:1;
    unsigned INTEDG0:1;
    unsigned NOT_RBPU:1;
  };
  struct {
    unsigned :2;
    unsigned T0IP:1;
    unsigned :4;
    unsigned RBPU:1;
  };
} INTCON2bits;
extern volatile near unsigned char       INTCON;
extern volatile near union {
  struct {
    unsigned RBIF:1;
    unsigned INT0IF:1;
    unsigned TMR0IF:1;
    unsigned RBIE:1;
    unsigned INT0IE:1;
    unsigned TMR0IE:1;
    unsigned PEIE_GIEL:1;
    unsigned GIE_GIEH:1;
  };
  struct {
    unsigned :1;
    unsigned INT0F:1;
    unsigned T0IF:1;
    unsigned :1;
    unsigned INT0E:1;
    unsigned T0IE:1;
    unsigned PEIE:1;
    unsigned GIE:1;
  };
  struct {
    unsigned :1;
    unsigned INTF:1;
    unsigned :2;
    unsigned INTE:1;
    unsigned :1;
    unsigned GIEL:1;
    unsigned GIEH:1;
  };
} INTCONbits;
extern          near unsigned            PROD;
extern          near unsigned char       PRODL;
extern          near unsigned char       PRODH;
extern volatile near unsigned char       TABLAT;
extern volatile near unsigned short long TBLPTR;
extern volatile near unsigned char       TBLPTRL;
extern volatile near unsigned char       TBLPTRH;
extern volatile near unsigned char       TBLPTRU;
extern volatile near unsigned short long PC;
extern volatile near unsigned char       PCL;
extern volatile near unsigned char       PCLATH;
extern volatile near unsigned char       PCLATU;
extern volatile near unsigned char       STKPTR;
extern volatile near union {
  struct {
    unsigned STKPTR:5;
    unsigned :1;
    unsigned STKUNF:1;
    unsigned STKFUL:1;
  };
  struct {
    unsigned STKPTR0:1;
    unsigned STKPTR1:1;
    unsigned STKPTR2:1;
    unsigned STKPTR3:1;
    unsigned STKPTR4:1;
    unsigned :2;
    unsigned STKOVF:1;
  };
  struct {
    unsigned SP0:1;
    unsigned SP1:1;
    unsigned SP2:1;
    unsigned SP3:1;
    unsigned SP4:1;
  };
} STKPTRbits;
extern          near unsigned short long TOS;
extern          near unsigned char       TOSL;
extern          near unsigned char       TOSH;
extern          near unsigned char       TOSU;


/*-------------------------------------------------------------------------
 * Some useful defines for inline assembly stuff
 *-------------------------------------------------------------------------*/
#define ACCESS 0
#define BANKED 1

/*-------------------------------------------------------------------------
 * Some useful macros for inline assembly stuff
 *-------------------------------------------------------------------------*/
#define Nop()    {_asm nop _endasm}
#define ClrWdt() {_asm clrwdt _endasm}
#define Sleep()  {_asm sleep _endasm}
#define Reset()  {_asm reset _endasm}

#define Rlcf(f,dest,access)  {_asm movlb f rlcf f,dest,access _endasm}
#define Rlncf(f,dest,access) {_asm movlb f rlncf f,dest,access _endasm}
#define Rrcf(f,dest,access)  {_asm movlb f rrcf f,dest,access _endasm}
#define Rrncf(f,dest,access) {_asm movlb f rrncf f,dest,access _endasm}
#define Swapf(f,dest,access) {_asm movlb f swapf f,dest,access _endasm }

/*-------------------------------------------------------------------------
 * A fairly inclusive set of registers to save for interrupts.
 * These are locations which are commonly used by the compiler.
 *-------------------------------------------------------------------------*/
#define INTSAVELOCS TBLPTR, TABLAT, PROD

/*-------------------------------------------------------------------------
 * IMPORTANT:  The _CONFIG_DECL macro has been deprecated.  Please utilize
 *             the #pragma config directive.  Refer to the "MPLAB C18 C
 *             Compiler User's Guide" for more information relating to the
 *             #pragma config directive.
 *
 * Defines for configuration words:
 *   _CONFIG_DECL should be placed between a #pragma romdata CONFIG
 *   and a #pragma romdata.  For example: ,
 *     #pragma romdata CONFIG
 *     _CONFIG_DECL(...)
 *     #pragma romdata
 * NOTE: This macro only works when using the default linker script files
 *       and the CONFIG section exists.
 *-------------------------------------------------------------------------*/
#define _CONFIG_DECL(_CONFIG1H, \
                     _CONFIG2L, \
                     _CONFIG2H, \
                     _CONFIG3H, \
                     _CONFIG4L, \
                     _CONFIG5L, \
                     _CONFIG5H, \
                     _CONFIG6L, \
                     _CONFIG6H, \
                     _CONFIG7L, \
                     _CONFIG7H) \
  const rom unsigned char _configuration[14] = { \
    0xFF, \
    _CONFIG1H, \
    _CONFIG2L, \
    _CONFIG2H, \
    0xFF, \
    _CONFIG3H, \
    _CONFIG4L, \
    0xFF, \
    _CONFIG5L, \
    _CONFIG5H, \
    _CONFIG6L, \
    _CONFIG6H, \
    _CONFIG7L, \
    _CONFIG7H \
  }

/*-------------------------------------------------------------------------
 *   CONFIG1H (0x300001)
 *-------------------------------------------------------------------------*/
#define _CONFIG1H_DEFAULT    0xFF
#define _OSC_LP_1H           0xF0
#define _OSC_XT_1H           0xF1
#define _OSC_HS_1H           0xF2
#define _OSC_EC_1H           0xF4
#define _OSC_ECIO_1H         0xF5
#define _OSC_HSPLL_1H        0xF6
#define _OSC_RCIO_1H         0xF7
#define _OSC_INTIO2_1H       0xF8
#define _OSC_INTIO1_1H       0xF9
#define _OSC_RC_1H           0xFC

#define _FSCM_OFF_1H         0xBF
#define _FSCM_ON_1H          0xFF

#define _IESO_OFF_1H         0x7F
#define _IESO_ON_1H          0xFF

/*-------------------------------------------------------------------------
 *   CONFIG2L (0x300002)
 *-------------------------------------------------------------------------*/
#define _CONFIG2L_DEFAULT    0xFF
#define _PWRT_ON_2L          0xFE
#define _PWRT_OFF_2L         0xFF

#define _BOR_OFF_2L          0xFD
#define _BOR_ON_2L           0xFF

#define _BORV_45_2L          0xF3
#define _BORV_42_2L          0xF7
#define _BORV_27_2L          0xFB

/*-------------------------------------------------------------------------
 *   CONFIG2H (0x300003)
 *-------------------------------------------------------------------------*/
#define _CONFIG2H_DEFAULT    0xFF
#define _WDT_OFF_2H          0xFE
#define _WDT_ON_2H           0xFF

#define _WDTPS_1_2H          0xE1
#define _WDTPS_2_2H          0xE3
#define _WDTPS_4_2H          0xE5
#define _WDTPS_8_2H          0xE7
#define _WDTPS_16_2H         0xE9
#define _WDTPS_32_2H         0xEB
#define _WDTPS_64_2H         0xED
#define _WDTPS_128_2H        0xEF
#define _WDTPS_256_2H        0xF1
#define _WDTPS_512_2H        0xF3
#define _WDTPS_1024_2H       0xF5
#define _WDTPS_2048_2H       0xF7
#define _WDTPS_4096_2H       0xF9
#define _WDTPS_8192_2H       0xFB
#define _WDTPS_16384_2H      0xFD
#define _WDTPS_32768_2H      0xFF

/*-------------------------------------------------------------------------
 *   CONFIG3H (0x300005)
 *-------------------------------------------------------------------------*/
#define _CONFIG3H_DEFAULT    0xFF
#define _MCLRE_OFF_3H        0x7F
#define _MCLRE_ON_3H         0xFF

/*-------------------------------------------------------------------------
 *   CONFIG4L (0x300006)
 *-------------------------------------------------------------------------*/
#define _CONFIG4L_DEFAULT    0xFF
#define _STVR_OFF_4L         0xFE
#define _STVR_ON_4L          0xFF

#define _LVP_OFF_4L          0xFB
#define _LVP_ON_4L           0xFF

#define _DEBUG_ON_4L         0x7F
#define _DEBUG_OFF_4L        0xFF

/*-------------------------------------------------------------------------
 *   CONFIG5L (0x300008)
 *-------------------------------------------------------------------------*/
#define _CONFIG5L_DEFAULT    0xFF
#define _CP0_ON_5L           0xFE
#define _CP0_OFF_5L          0xFF

#define _CP1_ON_5L           0xFD
#define _CP1_OFF_5L          0xFF

/*-------------------------------------------------------------------------
 *   CONFIG5H (0x300009)
 *-------------------------------------------------------------------------*/
#define _CONFIG5H_DEFAULT    0xFF
#define _CPB_ON_5H           0xBF
#define _CPB_OFF_5H          0xFF

#define _CPD_ON_5H           0x7F
#define _CPD_OFF_5H          0xFF

/*-------------------------------------------------------------------------
 *   CONFIG6L (0x30000a)
 *-------------------------------------------------------------------------*/
#define _CONFIG6L_DEFAULT    0xFF
#define _WRT0_ON_6L          0xFE
#define _WRT0_OFF_6L         0xFF

#define _WRT1_ON_6L          0xFD
#define _WRT1_OFF_6L         0xFF

/*-------------------------------------------------------------------------
 *   CONFIG6H (0x30000b)
 *-------------------------------------------------------------------------*/
#define _CONFIG6H_DEFAULT    0xFF
#define _WRTC_ON_6H          0xDF
#define _WRTC_OFF_6H         0xFF

#define _WRTB_ON_6H          0xBF
#define _WRTB_OFF_6H         0xFF

#define _WRTD_ON_6H          0x7F
#define _WRTD_OFF_6H         0xFF

/*-------------------------------------------------------------------------
 *   CONFIG7L (0x30000c)
 *-------------------------------------------------------------------------*/
#define _CONFIG7L_DEFAULT    0xFF
#define _EBTR0_ON_7L         0xFE
#define _EBTR0_OFF_7L        0xFF

#define _EBTR1_ON_7L         0xFD
#define _EBTR1_OFF_7L        0xFF

/*-------------------------------------------------------------------------
 *   CONFIG7H (0x30000d)
 *-------------------------------------------------------------------------*/
#define _CONFIG7H_DEFAULT    0xFF
#define _EBTRB_ON_7H         0xBF
#define _EBTRB_OFF_7H        0xFF


#endif
