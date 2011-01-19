/*-------------------------------------------------------------------------
 * MPLAB-Cxx  PIC18F2331 processor header
 *
 * (c) Copyright 1999-2010 Microchip Technology, All rights reserved
 *-------------------------------------------------------------------------*/

#ifndef __18F2331_H
#define __18F2331_H

extern volatile near unsigned char       DFLTCON;
extern volatile near union {
  struct {
    unsigned FLTCK:3;
    unsigned FLT1EN:1;
    unsigned FLT2EN:1;
    unsigned FLT3EN:1;
    unsigned FLT4EN:1;
  };
  struct {
    unsigned FLTCK0:1;
    unsigned FLTCK1:1;
    unsigned FLTCK2:1;
  };
} DFLTCONbits;
extern volatile near unsigned char       CAP3CON;
extern volatile near union {
  struct {
    unsigned CAP3M:4;
    unsigned :1;
    unsigned CAP3TMR:1;
    unsigned CAP3REN:1;
  };
  struct {
    unsigned CAP3M0:1;
    unsigned CAP3M1:1;
    unsigned CAP3M2:1;
    unsigned CAP3M3:1;
  };
} CAP3CONbits;
extern volatile near unsigned char       CAP2CON;
extern volatile near union {
  struct {
    unsigned CAP2M:4;
    unsigned :1;
    unsigned CAP2TMR:1;
    unsigned CAP2REN:1;
  };
  struct {
    unsigned CAP2M0:1;
    unsigned CAP2M1:1;
    unsigned CAP2M2:1;
    unsigned CAP2M3:1;
  };
} CAP2CONbits;
extern volatile near unsigned char       CAP1CON;
extern volatile near union {
  struct {
    unsigned CAP1M:4;
    unsigned :1;
    unsigned CAP1TMR:1;
    unsigned CAP1REN:1;
  };
  struct {
    unsigned CAP1M0:1;
    unsigned CAP1M1:1;
    unsigned CAP1M2:1;
    unsigned CAP1M3:1;
  };
} CAP1CONbits;
extern volatile near unsigned char       CAP3BUFL;
extern volatile near unsigned char       MAXCNTL;
extern volatile near unsigned char       CAP3BUFH;
extern volatile near unsigned char       MAXCNTH;
extern volatile near unsigned char       CAP2BUFL;
extern volatile near unsigned char       POSCNTL;
extern volatile near unsigned char       CAP2BUFH;
extern volatile near unsigned char       POSCNTH;
extern volatile near unsigned char       CAP1BUFL;
extern volatile near unsigned char       VELRL;
extern volatile near unsigned char       CAP1BUFH;
extern volatile near unsigned char       VELRH;
extern volatile near unsigned char       OVDCONS;
extern volatile near union {
  struct {
    unsigned POUT:8;
  };
  struct {
    unsigned POUT0:1;
    unsigned POUT1:1;
    unsigned POUT2:1;
    unsigned POUT3:1;
    unsigned POUT4:1;
    unsigned POUT5:1;
    unsigned POUT6:1;
    unsigned POUT7:1;
  };
} OVDCONSbits;
extern volatile near unsigned char       OVDCOND;
extern volatile near union {
  struct {
    unsigned POVD:8;
  };
  struct {
    unsigned POVD0:1;
    unsigned POVD1:1;
    unsigned POVD2:1;
    unsigned POVD3:1;
    unsigned POVD4:1;
    unsigned POVD5:1;
    unsigned POVD6:1;
    unsigned POVD7:1;
  };
} OVDCONDbits;
extern volatile near unsigned char       FLTCONFIG;
extern volatile near struct {
  unsigned FLTAEN:1;
  unsigned FLTAMOD:1;
  unsigned FLTAS:1;
  unsigned FLTCON:1;
  unsigned FLTBEN:1;
  unsigned FLTBMOD:1;
  unsigned FLTBS:1;
  unsigned BRFEN:1;
} FLTCONFIGbits;
extern volatile near unsigned char       DTCON;
extern volatile near union {
  struct {
    unsigned DTA:6;
    unsigned DTAPS:2;
  };
  struct {
    unsigned DT0:1;
    unsigned DT1:1;
    unsigned DT2:1;
    unsigned DT3:1;
    unsigned DT4:1;
    unsigned DT5:1;
    unsigned DTPS0:1;
    unsigned DTPS1:1;
  };
  struct {
    unsigned DTA0:1;
    unsigned DTA1:1;
    unsigned DTA2:1;
    unsigned DTA3:1;
    unsigned DTA4:1;
    unsigned DTA5:1;
    unsigned DTAPS0:1;
    unsigned DTAPS1:1;
  };
} DTCONbits;
extern volatile near unsigned char       PWMCON1;
extern volatile near union {
  struct {
    unsigned OSYNC:1;
    unsigned UDIS:1;
    unsigned :1;
    unsigned SEVTDIR:1;
    unsigned SEVOPS:4;
  };
  struct {
    unsigned :4;
    unsigned SEVOPS0:1;
    unsigned SEVOPS1:1;
    unsigned SEVOPS2:1;
    unsigned SEVOPS3:1;
  };
} PWMCON1bits;
extern volatile near unsigned char       PWMCON0;
extern volatile near union {
  struct {
    unsigned PMOD:4;
    unsigned PWMEN:3;
  };
  struct {
    unsigned PMOD0:1;
    unsigned PMOD1:1;
    unsigned PMOD2:1;
    unsigned PMOD3:1;
    unsigned PWMEN0:1;
    unsigned PWMEN1:1;
    unsigned PWMEN2:1;
  };
} PWMCON0bits;
extern volatile near unsigned char       SEVTCMPH;
extern volatile near unsigned char       SEVTCMPL;
extern volatile near unsigned char       PDC2H;
extern volatile near unsigned char       PDC2L;
extern volatile near unsigned char       PDC1H;
extern volatile near unsigned char       PDC1L;
extern volatile near unsigned char       PDC0H;
extern volatile near unsigned char       PDC0L;
extern volatile near unsigned char       PTPERH;
extern volatile near unsigned char       PTPERL;
extern volatile near unsigned char       PTMRH;
extern volatile near unsigned char       PTMRL;
extern volatile near unsigned char       PTCON1;
extern volatile near struct {
  unsigned :6;
  unsigned PTDIR:1;
  unsigned PTEN:1;
} PTCON1bits;
extern volatile near unsigned char       PTCON0;
extern volatile near union {
  struct {
    unsigned PTMOD:2;
    unsigned PTCKPS:2;
    unsigned PTOPS:4;
  };
  struct {
    unsigned PTMOD0:1;
    unsigned PTMOD1:1;
    unsigned PTCKPS0:1;
    unsigned PTCKPS1:1;
    unsigned PTOPS0:1;
    unsigned PTOPS1:1;
    unsigned PTOPS2:1;
    unsigned PTOPS3:1;
  };
} PTCON0bits;
extern volatile near unsigned char       PORTA;
extern volatile near union {
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
  struct {
    unsigned AN0:1;
    unsigned AN1:1;
    unsigned AN2:1;
    unsigned AN3:1;
    unsigned AN4:1;
    unsigned :1;
    unsigned OSC2:1;
    unsigned OSC1:1;
  };
  struct {
    unsigned :2;
    unsigned VREFM:1;
    unsigned VREFP:1;
    unsigned :2;
    unsigned CLKO:1;
    unsigned CLKI:1;
  };
} PORTAbits;
extern volatile near unsigned char       PORTB;
extern volatile near struct {
  unsigned RB0:1;
  unsigned RB1:1;
  unsigned RB2:1;
  unsigned RB3:1;
  unsigned RB4:1;
  unsigned RB5:1;
  unsigned RB6:1;
  unsigned RB7:1;
} PORTBbits;
extern volatile near unsigned char       PORTC;
extern volatile near union {
  struct {
    unsigned RC0:1;
    unsigned RC1:1;
    unsigned RC2:1;
    unsigned RC3:1;
    unsigned RC4:1;
    unsigned RC5:1;
    unsigned RC6:1;
    unsigned RC7:1;
  };
  struct {
    unsigned T1OSO:1;
    unsigned T1OSI:1;
    unsigned CCP1:1;
    unsigned INT0:1;
    unsigned INT1:1;
    unsigned INT2:1;
    unsigned TX:1;
    unsigned RX:1;
  };
  struct {
    unsigned T13CKI:1;
    unsigned CCP2:1;
    unsigned :1;
    unsigned T0CKI:1;
    unsigned SDA:1;
    unsigned SCK:1;
    unsigned CK:1;
    unsigned DT:1;
  };
  struct {
    unsigned :1;
    unsigned NOT_FLTA:1;
    unsigned NOT_FLTB:1;
    unsigned T5CKI:1;
    unsigned SDI:1;
    unsigned SCL:1;
    unsigned NOT_SS:1;
    unsigned SDO:1;
  };
  struct {
    unsigned :1;
    unsigned FLTA:1;
    unsigned FLTB:1;
    unsigned :3;
    unsigned SS:1;
  };
} PORTCbits;
extern volatile near unsigned char       PORTE;
extern volatile near union {
  struct {
    unsigned :3;
    unsigned RE3:1;
  };
  struct {
    unsigned :3;
    unsigned NOT_MCLR:1;
  };
  struct {
    unsigned :3;
    unsigned MCLR:1;
  };
} PORTEbits;
extern volatile near unsigned char       TMR5L;
extern volatile near unsigned char       TMR5H;
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
extern volatile near unsigned char       LATC;
extern volatile near struct {
  unsigned LATC0:1;
  unsigned LATC1:1;
  unsigned LATC2:1;
  unsigned LATC3:1;
  unsigned LATC4:1;
  unsigned LATC5:1;
  unsigned LATC6:1;
  unsigned LATC7:1;
} LATCbits;
extern volatile near unsigned            PR5;
extern volatile near unsigned char       PR5L;
extern volatile near unsigned char       PR5H;
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
extern volatile near unsigned char       DDRC;
extern volatile near union {
  struct {
    unsigned TRISC0:1;
    unsigned TRISC1:1;
    unsigned TRISC2:1;
    unsigned TRISC3:1;
    unsigned TRISC4:1;
    unsigned TRISC5:1;
    unsigned TRISC6:1;
    unsigned TRISC7:1;
  };
  struct {
    unsigned RC0:1;
    unsigned RC1:1;
    unsigned RC2:1;
    unsigned RC3:1;
    unsigned RC4:1;
    unsigned RC5:1;
    unsigned RC6:1;
    unsigned RC7:1;
  };
} DDRCbits;
extern volatile near unsigned char       TRISC;
extern volatile near union {
  struct {
    unsigned TRISC0:1;
    unsigned TRISC1:1;
    unsigned TRISC2:1;
    unsigned TRISC3:1;
    unsigned TRISC4:1;
    unsigned TRISC5:1;
    unsigned TRISC6:1;
    unsigned TRISC7:1;
  };
  struct {
    unsigned RC0:1;
    unsigned RC1:1;
    unsigned RC2:1;
    unsigned RC3:1;
    unsigned RC4:1;
    unsigned RC5:1;
    unsigned RC6:1;
    unsigned RC7:1;
  };
} TRISCbits;
extern volatile near unsigned char       ADCHS;
extern volatile near union {
  struct {
    unsigned SASEL:2;
    unsigned SCSEL:2;
    unsigned SBSEL:2;
    unsigned SDSEL:2;
  };
  struct {
    unsigned GASEL0:1;
    unsigned GASEL1:1;
    unsigned GCSEL0:1;
    unsigned GCSEL1:1;
    unsigned GBSEL0:1;
    unsigned GBSEL1:1;
    unsigned GDSEL0:1;
    unsigned GDSEL1:1;
  };
  struct {
    unsigned SASEL0:1;
    unsigned SASEL1:1;
    unsigned SCSEL0:1;
    unsigned SCSEL1:1;
    unsigned SBSEL0:1;
    unsigned SBSEL1:1;
    unsigned SDSEL0:1;
    unsigned SDSEL1:1;
  };
} ADCHSbits;
extern volatile near unsigned char       ADCON3;
extern volatile near union {
  struct {
    unsigned SSRC:5;
    unsigned :1;
    unsigned ADRS:2;
  };
  struct {
    unsigned SSRC0:1;
    unsigned SSRC1:1;
    unsigned SSRC2:1;
    unsigned SSRC3:1;
    unsigned SSRC4:1;
    unsigned :1;
    unsigned ADRS0:1;
    unsigned ADRS1:1;
  };
} ADCON3bits;
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
extern volatile near union {
  struct {
    unsigned TMR1IE:1;
    unsigned TMR2IE:1;
    unsigned CCP1IE:1;
    unsigned SSPIE:1;
    unsigned TXIE:1;
    unsigned RCIE:1;
    unsigned ADIE:1;
  };
  struct {
    unsigned :4;
    unsigned TBIE:1;
  };
} PIE1bits;
extern volatile near unsigned char       PIR1;
extern volatile near union {
  struct {
    unsigned TMR1IF:1;
    unsigned TMR2IF:1;
    unsigned CCP1IF:1;
    unsigned SSPIF:1;
    unsigned TXIF:1;
    unsigned RCIF:1;
    unsigned ADIF:1;
  };
  struct {
    unsigned :4;
    unsigned TBIF:1;
  };
} PIR1bits;
extern volatile near unsigned char       IPR1;
extern volatile near union {
  struct {
    unsigned TMR1IP:1;
    unsigned TMR2IP:1;
    unsigned CCP1IP:1;
    unsigned SSPIP:1;
    unsigned TXIP:1;
    unsigned RCIP:1;
    unsigned ADIP:1;
  };
  struct {
    unsigned :4;
    unsigned TBIP:1;
  };
} IPR1bits;
extern volatile near unsigned char       PIE2;
extern volatile near struct {
  unsigned CCP2IE:1;
  unsigned :1;
  unsigned LVDIE:1;
  unsigned :1;
  unsigned EEIE:1;
  unsigned :2;
  unsigned OSFIE:1;
} PIE2bits;
extern volatile near unsigned char       PIR2;
extern volatile near struct {
  unsigned CCP2IF:1;
  unsigned :1;
  unsigned LVDIF:1;
  unsigned :1;
  unsigned EEIF:1;
  unsigned :2;
  unsigned OSFIF:1;
} PIR2bits;
extern volatile near unsigned char       IPR2;
extern volatile near struct {
  unsigned CCP2IP:1;
  unsigned :1;
  unsigned LVDIP:1;
  unsigned :1;
  unsigned EEIP:1;
  unsigned :2;
  unsigned OSFIP:1;
} IPR2bits;
extern volatile near unsigned char       PIE3;
extern volatile near struct {
  unsigned TMR5IE:1;
  unsigned IC1IE:1;
  unsigned IC2QEIE:1;
  unsigned IC3DRIE:1;
  unsigned PTIE:1;
} PIE3bits;
extern volatile near unsigned char       PIR3;
extern volatile near struct {
  unsigned TMR5IF:1;
  unsigned IC1IF:1;
  unsigned IC2QEIF:1;
  unsigned IC3DRIF:1;
  unsigned PTIF:1;
} PIR3bits;
extern volatile near unsigned char       IPR3;
extern volatile near struct {
  unsigned TMR5IP:1;
  unsigned IC1IP:1;
  unsigned IC2QEIP:1;
  unsigned IC3DRIP:1;
  unsigned PTIP:1;
} IPR3bits;
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
extern volatile near unsigned char       BAUDCON;
extern volatile near union {
  struct {
    unsigned ABDEN:1;
    unsigned WUE:1;
    unsigned :1;
    unsigned BRG16:1;
    unsigned TXCKP:1;
    unsigned RXDTP:1;
    unsigned RCIDL:1;
    unsigned ABDOVF:1;
  };
  struct {
    unsigned :4;
    unsigned SCKP:1;
    unsigned :1;
    unsigned RCMT:1;
  };
} BAUDCONbits;
extern volatile near unsigned char       BAUDCTL;
extern volatile near union {
  struct {
    unsigned ABDEN:1;
    unsigned WUE:1;
    unsigned :1;
    unsigned BRG16:1;
    unsigned TXCKP:1;
    unsigned RXDTP:1;
    unsigned RCIDL:1;
    unsigned ABDOVF:1;
  };
  struct {
    unsigned :4;
    unsigned SCKP:1;
    unsigned :1;
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
extern volatile near unsigned char       QEICON;
extern volatile near union {
  struct {
    unsigned PDEC:2;
    unsigned QEIM:3;
    unsigned UP_NOT_DOWN:1;
    unsigned ERROR:1;
    unsigned NOT_VELM:1;
  };
  struct {
    unsigned PDEC0:1;
    unsigned PDEC1:1;
    unsigned QEIM0:1;
    unsigned QEIM1:1;
    unsigned QEIM2:1;
    unsigned UP_DOWN:1;
    unsigned :1;
    unsigned VELM:1;
  };
  struct {
    unsigned :5;
    unsigned UP:1;
  };
  struct {
    unsigned :5;
    unsigned DOWN:1;
  };
  struct {
    unsigned :5;
    unsigned NOT_DOWN:1;
  };
} QEICONbits;
extern volatile near unsigned char       T5CON;
extern volatile near union {
  struct {
    unsigned TMR5ON:1;
    unsigned TMR5CS:1;
    unsigned NOT_T5SYNC:1;
    unsigned T5PS:2;
    unsigned T5MOD:1;
    unsigned NOT_RESEN:1;
    unsigned T5SEN:1;
  };
  struct {
    unsigned :2;
    unsigned T5SYNC:1;
    unsigned T5PS0:1;
    unsigned T5PS1:1;
    unsigned :1;
    unsigned RESEN:1;
  };
} T5CONbits;
extern volatile near unsigned char       ANSEL0;
extern volatile near struct {
  unsigned ANS0:1;
  unsigned ANS1:1;
  unsigned ANS2:1;
  unsigned ANS3:1;
  unsigned ANS4:1;
} ANSEL0bits;
extern volatile near unsigned char       CCP2CON;
extern volatile near union {
  struct {
    unsigned CCP2M:4;
    unsigned DC2B:2;
  };
  struct {
    unsigned CCP2M0:1;
    unsigned CCP2M1:1;
    unsigned CCP2M2:1;
    unsigned CCP2M3:1;
    unsigned CCP2Y:1;
    unsigned CCP2X:1;
  };
  struct {
    unsigned :4;
    unsigned DC2B0:1;
    unsigned DC2B1:1;
  };
} CCP2CONbits;
extern volatile near unsigned            CCPR2;
extern volatile near unsigned char       CCPR2L;
extern volatile near unsigned char       CCPR2H;
extern volatile near unsigned char       CCP1CON;
extern volatile near union {
  struct {
    unsigned CCP1M:4;
    unsigned DC1B:2;
  };
  struct {
    unsigned CCP1M0:1;
    unsigned CCP1M1:1;
    unsigned CCP1M2:1;
    unsigned CCP1M3:1;
    unsigned CCP1Y:1;
    unsigned CCP1X:1;
  };
  struct {
    unsigned :4;
    unsigned DC1B0:1;
    unsigned DC1B1:1;
  };
} CCP1CONbits;
extern volatile near unsigned            CCPR1;
extern volatile near unsigned char       CCPR1L;
extern volatile near unsigned char       CCPR1H;
extern volatile near unsigned char       ADCON2;
extern volatile near union {
  struct {
    unsigned ADCS:3;
    unsigned ACQT:4;
    unsigned ADFM:1;
  };
  struct {
    unsigned ADCS0:1;
    unsigned ADCS1:1;
    unsigned ADCS2:1;
    unsigned ACQT0:1;
    unsigned ACQT1:1;
    unsigned ACQT2:1;
    unsigned ACQT3:1;
  };
} ADCON2bits;
extern volatile near unsigned char       ADCON1;
extern volatile near union {
  struct {
    unsigned ADPNT:2;
    unsigned BFOVFL:1;
    unsigned BFEMT:1;
    unsigned FIFOEN:1;
    unsigned :1;
    unsigned VCFG:2;
  };
  struct {
    unsigned ADPNT0:1;
    unsigned ADPNT1:1;
    unsigned :4;
    unsigned VCFG0:1;
    unsigned VCFG1:1;
  };
  struct {
    unsigned :2;
    unsigned FFOVFL:1;
  };
} ADCON1bits;
extern volatile near unsigned char       ADCON0;
extern volatile near union {
  struct {
    unsigned ADON:1;
    unsigned GO_NOT_DONE:1;
    unsigned ACMOD:2;
    unsigned ACSCH:1;
    unsigned ACONV:1;
  };
  struct {
    unsigned :1;
    unsigned GO_DONE:1;
    unsigned ACMOD0:1;
    unsigned ACMOD1:1;
  };
  struct {
    unsigned :1;
    unsigned DONE:1;
  };
  struct {
    unsigned :1;
    unsigned GO:1;
  };
  struct {
    unsigned :1;
    unsigned NOT_DONE:1;
  };
} ADCON0bits;
extern volatile near unsigned            ADRES;
extern volatile near unsigned char       ADRESL;
extern volatile near unsigned char       ADRESH;
extern volatile near unsigned char       SSPCON;
extern volatile near union {
  struct {
    unsigned SSPM:4;
    unsigned CKP:1;
    unsigned SSPEN:1;
    unsigned SSPOV:1;
    unsigned WCOL:1;
  };
  struct {
    unsigned SSPM0:1;
    unsigned SSPM1:1;
    unsigned SSPM2:1;
    unsigned SSPM3:1;
  };
} SSPCONbits;
extern volatile near unsigned char       SSPSTAT;
extern volatile near union {
  struct {
    unsigned BF:1;
    unsigned UA:1;
    unsigned R_NOT_W:1;
    unsigned S:1;
    unsigned P:1;
    unsigned D_NOT_A:1;
    unsigned CKE:1;
    unsigned SMP:1;
  };
  struct {
    unsigned :2;
    unsigned R_W:1;
    unsigned :2;
    unsigned D_A:1;
  };
  struct {
    unsigned :2;
    unsigned NOT_W:1;
    unsigned :2;
    unsigned NOT_A:1;
  };
  struct {
    unsigned :2;
    unsigned NOT_WRITE:1;
    unsigned :2;
    unsigned NOT_ADDRESS:1;
  };
  struct {
    unsigned :2;
    unsigned READ_WRITE:1;
    unsigned :2;
    unsigned DATA_ADDRESS:1;
  };
  struct {
    unsigned :2;
    unsigned R:1;
    unsigned :2;
    unsigned D:1;
  };
} SSPSTATbits;
extern volatile near unsigned char       SSPADD;
extern volatile near unsigned char       SSPBUF;
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
    unsigned T2OUTPS0:1;
    unsigned T2OUTPS1:1;
    unsigned T2OUTPS2:1;
    unsigned T2OUTPS3:1;
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
    unsigned :2;
    unsigned T1SYNC:1;
    unsigned :1;
    unsigned T1CKPS0:1;
    unsigned T1CKPS1:1;
  };
  struct {
    unsigned :2;
    unsigned T1INSYNC:1;
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
    unsigned :2;
    unsigned NOT_IPEN:1;
  };
} RCONbits;
extern volatile near unsigned char       WDTCON;
extern volatile near struct {
  unsigned SWDTEN:1;
  unsigned :6;
  unsigned WDTW:1;
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
    unsigned SCS:2;
    unsigned IOFS:1;
    unsigned OSTS:1;
    unsigned IRCF:3;
    unsigned IDLEN:1;
  };
  struct {
    unsigned SCS0:1;
    unsigned SCS1:1;
    unsigned :2;
    unsigned IRCF0:1;
    unsigned IRCF1:1;
    unsigned IRCF2:1;
  };
  struct {
    unsigned :2;
    unsigned FLTS:1;
  };
} OSCCONbits;
extern volatile near unsigned char       T0CON;
extern volatile near union {
  struct {
    unsigned T0PS:3;
    unsigned PSA:1;
    unsigned T0SE:1;
    unsigned T0CS:1;
    unsigned T016BIT:1;
    unsigned TMR0ON:1;
  };
  struct {
    unsigned T0PS0:1;
    unsigned T0PS1:1;
    unsigned T0PS2:1;
    unsigned T0PS3:1;
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
    unsigned :6;
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
                     _CONFIG3L, \
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
    _CONFIG3L, \
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
#define _CONFIG1H_DEFAULT    0xCF
#define _OSC_LP_1H           0xF0
#define _OSC_XT_1H           0xF1
#define _OSC_HS_1H           0xF2
#define _OSC_RC2_1H          0xF3
#define _OSC_EC_1H           0xF4
#define _OSC_ECIO_1H         0xF5
#define _OSC_HSPLL_1H        0xF6
#define _OSC_RCIO_1H         0xF7
#define _OSC_IRCIO_1H        0xF8
#define _OSC_IRC_1H          0xF9
#define _OSC_RC1_1H          0xFA
#define _OSC_RC_1H           0xFC

#define _FCMEN_OFF_1H        0xBF
#define _FCMEN_ON_1H         0xFF

#define _IESO_OFF_1H         0x7F
#define _IESO_ON_1H          0xFF

/*-------------------------------------------------------------------------
 *   CONFIG2L (0x300002)
 *-------------------------------------------------------------------------*/
#define _CONFIG2L_DEFAULT    0x0F
#define _PWRTEN_ON_2L        0xFE
#define _PWRTEN_OFF_2L       0xFF

#define _BOREN_OFF_2L        0xFD
#define _BOREN_ON_2L         0xFF

#define _BORV_45_2L          0xF3
#define _BORV_42_2L          0xF7
#define _BORV_27_2L          0xFB
#define _BORV_20_2L          0xFF

/*-------------------------------------------------------------------------
 *   CONFIG2H (0x300003)
 *-------------------------------------------------------------------------*/
#define _CONFIG2H_DEFAULT    0x3F
#define _WDTEN_OFF_2H        0xFE
#define _WDTEN_ON_2H         0xFF

#define _WDPS_1_2H           0xE1
#define _WDPS_2_2H           0xE3
#define _WDPS_4_2H           0xE5
#define _WDPS_8_2H           0xE7
#define _WDPS_16_2H          0xE9
#define _WDPS_32_2H          0xEB
#define _WDPS_64_2H          0xED
#define _WDPS_128_2H         0xEF
#define _WDPS_256_2H         0xF1
#define _WDPS_512_2H         0xF3
#define _WDPS_1024_2H        0xF5
#define _WDPS_2048_2H        0xF7
#define _WDPS_4096_2H        0xF9
#define _WDPS_8192_2H        0xFB
#define _WDPS_16384_2H       0xFD
#define _WDPS_32768_2H       0xFF

#define _WINEN_ON_2H         0xDF
#define _WINEN_OFF_2H        0xFF

/*-------------------------------------------------------------------------
 *   CONFIG3L (0x300004)
 *-------------------------------------------------------------------------*/
#define _CONFIG3L_DEFAULT    0x3C
#define _PWMPIN_ON_3L        0xFB
#define _PWMPIN_OFF_3L       0xFF

#define _LPOL_LOW_3L         0xF7
#define _LPOL_HIGH_3L        0xFF

#define _HPOL_LOW_3L         0xEF
#define _HPOL_HIGH_3L        0xFF

#define _T1OSCMX_OFF_3L      0xDF
#define _T1OSCMX_ON_3L       0xFF

/*-------------------------------------------------------------------------
 *   CONFIG3H (0x300005)
 *-------------------------------------------------------------------------*/
#define _CONFIG3H_DEFAULT    0x9D
#define _MCLRE_OFF_3H        0x7F
#define _MCLRE_ON_3H         0xFF

/*-------------------------------------------------------------------------
 *   CONFIG4L (0x300006)
 *-------------------------------------------------------------------------*/
#define _CONFIG4L_DEFAULT    0x85
#define _STVREN_OFF_4L       0xFE
#define _STVREN_ON_4L        0xFF

#define _LVP_OFF_4L          0xFB
#define _LVP_ON_4L           0xFF

#define _DEBUG_ON_4L         0x7F
#define _DEBUG_OFF_4L        0xFF

/*-------------------------------------------------------------------------
 *   CONFIG5L (0x300008)
 *-------------------------------------------------------------------------*/
#define _CONFIG5L_DEFAULT    0x0F
#define _CP0_ON_5L           0xFE
#define _CP0_OFF_5L          0xFF

#define _CP1_ON_5L           0xFD
#define _CP1_OFF_5L          0xFF

/*-------------------------------------------------------------------------
 *   CONFIG5H (0x300009)
 *-------------------------------------------------------------------------*/
#define _CONFIG5H_DEFAULT    0xC0
#define _CPB_ON_5H           0xBF
#define _CPB_OFF_5H          0xFF

#define _CPD_ON_5H           0x7F
#define _CPD_OFF_5H          0xFF

/*-------------------------------------------------------------------------
 *   CONFIG6L (0x30000a)
 *-------------------------------------------------------------------------*/
#define _CONFIG6L_DEFAULT    0x0F
#define _WRT0_ON_6L          0xFE
#define _WRT0_OFF_6L         0xFF

#define _WRT1_ON_6L          0xFD
#define _WRT1_OFF_6L         0xFF

/*-------------------------------------------------------------------------
 *   CONFIG6H (0x30000b)
 *-------------------------------------------------------------------------*/
#define _CONFIG6H_DEFAULT    0xE0
#define _WRTC_ON_6H          0xDF
#define _WRTC_OFF_6H         0xFF

#define _WRTB_ON_6H          0xBF
#define _WRTB_OFF_6H         0xFF

#define _WRTD_ON_6H          0x7F
#define _WRTD_OFF_6H         0xFF

/*-------------------------------------------------------------------------
 *   CONFIG7L (0x30000c)
 *-------------------------------------------------------------------------*/
#define _CONFIG7L_DEFAULT    0x0F
#define _EBTR0_ON_7L         0xFE
#define _EBTR0_OFF_7L        0xFF

#define _EBTR1_ON_7L         0xFD
#define _EBTR1_OFF_7L        0xFF

/*-------------------------------------------------------------------------
 *   CONFIG7H (0x30000d)
 *-------------------------------------------------------------------------*/
#define _CONFIG7H_DEFAULT    0x40
#define _EBTRB_ON_7H         0xBF
#define _EBTRB_OFF_7H        0xFF


#endif
