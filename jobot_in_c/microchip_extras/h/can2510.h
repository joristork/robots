/* $Id: can2510.h,v 1.3 2006/10/25 17:31:42 nairnj Exp $ */
#ifndef __CAN2510_H
#define __CAN2510_H

#include <spi.h>

/* PIC18 SPI -> CAN 2510 peripheral support header */

/*****************************************************************************/
/****                                                                     ****/
/****  BUFFERCONFIG - unsigned short long (BYTEU:BYTEH:BYTEL)             ****/
/****                                                                     ****/
/*****************************************************************************/
/****  NO associated CAN2510 Register                            ****/
/****  This bit is stored in (BYTEL<7>)                          ****/
/****  Reset the 2510 chip when initializing                     ****/
#define CAN2510_NORESET       0xFFFFFF /* Don't reset the processor */
#define CAN2510_RESET         0xFFFF7F /* Reset the processor       */

/****  RXB0CTRL Register settings                                ****/
/****  These bits are stored in (BYTEL<6:5>)                     ****/
/****  Specify buffer 0 filtering                                ****/
#define CAN2510_RXB0_USEFILT  0xFFFFFF /* Receive all messages, use filters       */
#define CAN2510_RXB0_STDMSG   0xFFFFDF /* Receive only standard msgs, use filters */
#define CAN2510_RXB0_XTDMSG   0xFFFFBF /* Receive only extended msgs, use filters */
#define CAN2510_RXB0_NOFILT   0xFFFF9F /* Receive all messages, no filters        */

/****  This bit is stored in (BYTEL<2>)                          ****/
/****  Specify if msg will be loaded to RXB1 if RXB0 is full     ****/
#define CAN2510_RXB0_ROLL     0xFFFFFF /* Message will go to RXB1   */
#define CAN2510_RXB0_NOROLL   0xFFFFFB /* Message will wait on RXB0 */

/****  RXB1CTRL Register settings                                ****/
/****  These bits are stored in (BYTEL<1:0>)                     ****/
/****  Specify buffer 1 filtering                                ****/
#define CAN2510_RXB1_USEFILT  0xFFFFFF /* Receive all messages, use filters       */
#define CAN2510_RXB1_STDMSG   0xFFFFFE /* Receive only standard msgs, use filters */
#define CAN2510_RXB1_XTDMSG   0xFFFFFD /* Receive only extended msgs, use filters */
#define CAN2510_RXB1_NOFILT   0xFFFFFC /* Receive all messages, no filters        */

/****  BFPCTRL Register settings                                 ****/
/****  (B0BFS:B0BSE:B0BFM)                                       ****/
/****  These bits are stored in (BYTEH<2>:BYTEH<0>:BYTEL<3>)     ****/
/****  Specify configuration of RX0BF pins                       ****/
#define CAN2510_RX0BF_OFF     0xFFFFFF /* Pins are tri-stated                */
#define CAN2510_RX0BF_INT     0xFFFEF7 /* Pins are interrupt for receive     */
#define CAN2510_RX0BF_GPOUTH  0xFFFAFF /* Pins are digital output High level */
#define CAN2510_RX0BF_GPOUTL  0xFFFEFF /* Pins are digital output Low level  */

/****  (B1BFS:B1BSE:B1BFM)                                       ****/
/****  These bits are stored in (BYTEH<3>:BYTEH<1>:BYTEL<4>)     ****/
/****  Specify configuration of RX1BF pins                       ****/
#define CAN2510_RX1BF_OFF     0xFFFFFF /* Pins are tri-stated                */
#define CAN2510_RX1BF_INT     0xFFFDEF /* Pins are interrupt for receive     */
#define CAN2510_RX1BF_GPOUTH  0xFFF5FF /* Pins are digital output High level */
#define CAN2510_RX1BF_GPOUTL  0xFFFDFF /* Pins are digital output Low level  */


/****  CANCTRL Register settings                                 ****/
/****  (REQOP2:REQOP0)                                           ****/
/****  These bits are stored in (BYTEU<7:5>) - Not complemented  ****/
/****  Specify CAN Module Operating mode                         ****/
#define CAN2510_REQ_NORMAL    0xFFFFFF /* CLKOUT = System clock/8 */
#define CAN2510_REQ_SLEEP     0xDFFFFF /* CLKOUT = System clock/8 */
#define CAN2510_REQ_LOOPBACK  0xBFFFFF /* CLKOUT = System clock/8 */
#define CAN2510_REQ_LISTEN    0x9FFFFF /* CLKOUT = System clock/8 */
#define CAN2510_REQ_CONFIG    0x7FFFFF /* CLKOUT = System clock/8 */

/****  (CLKEN:CLKPRE<1:0>)                                       ****/
/****  These bits are stored in (BYTEU<2:0>)                     ****/
/****  Specify CLKOUT pin setting                                ****/
#define CAN2510_CLKOUT_8      0xFFFFFF /* CLKOUT = System clock/8 */
#define CAN2510_CLKOUT_4      0xFEFFFF /* CLKOUT = System clock/4 */
#define CAN2510_CLKOUT_2      0xFDFFFF /* CLKOUT = System clock/2 */
#define CAN2510_CLKOUT_1      0xFCFFFF /* CLKOUT = System clock   */
#define CAN2510_CLKOUT_OFF    0xF8FFFF /* CLKOUT is disabled      */


/****  TXRTSCTRL Register settings                               ****/
/****  (B2RTSM)                                                  ****/
/****  This bit is stored in (BYTEH<6>)                          ****/
/****  Specify configuration of TX2 pins                         ****/
#define CAN2510_TX2_GPIN      0xFFFFFF /* Pins are digital input        */
#define CAN2510_TX2_RTS       0xFFBFFF /* Pins are used to request XMIT */

/****  (B1RTSM)                                                  ****/
/****  This bit is stored in (BYTEH<5>)                          ****/
/****  Specify configuration of TX1 pins                         ****/
#define CAN2510_TX1_GPIN      0xFFFFFF /* Pins are digital input        */
#define CAN2510_TX1_RTS       0xFFDFFF /* Pins are used to request XMIT */

/****  (B0RTSM)                                                  ****/
/****  This bit is stored in (BYTEH<4>)                          ****/
/****  Specify configuration of TX0 pins                         ****/
#define CAN2510_TX0_GPIN      0xFFFFFF /* Pins are digital input        */
#define CAN2510_TX0_RTS       0xFFEFFF /* Pins are used to request XMIT */




/*****************************************************************************/
/****                                                                     ****/
/****  BITTIMECONFIG - unsigned short long (BYTEU:BYTEH:BYTEL)            ****/
/****                                                                     ****/
/*****************************************************************************/
/****  CNF1 Register settings                                    ****/
/****  (SJW<1:0>)                                                ****/
/****  These bits are stored in (BYTEL<7:6>)                     ****/
/****  Specify synchronization jump width length                 ****/
#define CAN2510_SJW_1TQ       0xFFFFFF /* SJW is 1*TQ */
#define CAN2510_SJW_2TQ       0xFFFFBF /* SJW is 2*TQ */
#define CAN2510_SJW_3TQ       0xFFFF7F /* SJW is 3*TQ */
#define CAN2510_SJW_4TQ       0xFFFF3F /* SJW is 4*TQ */

/****  (BRP<5:0>)                                                ****/
/****  These bits are stored in (BYTEL<5:0>)                     ****/
/****  Specify Baud Rate Prescaler                               ****/
#define CAN2510_BRG_1x        0xFFFFFF /* SJW is 1*TQ */
#define CAN2510_BRG_2x        0xFFFFFE /* SJW is 2*TQ */
#define CAN2510_BRG_3x        0xFFFFFD /* SJW is 3*TQ */
#define CAN2510_BRG_4x        0xFFFFFC /* SJW is 4*TQ */
#define CAN2510_BRG_5x        0xFFFFFB /* SJW is 5*TQ */
#define CAN2510_BRG_6x        0xFFFFFA /* SJW is 6*TQ */
#define CAN2510_BRG_7x        0xFFFFF9 /* SJW is 7*TQ */
#define CAN2510_BRG_8x        0xFFFFF8 /* SJW is 8*TQ */
#define CAN2510_BRG_9x        0xFFFFF7 /* SJW is 9*TQ */
#define CAN2510_BRG_10x       0xFFFFF6 /* SJW is 10*TQ */
#define CAN2510_BRG_11x       0xFFFFF5 /* SJW is 11*TQ */
#define CAN2510_BRG_12x       0xFFFFF4 /* SJW is 12*TQ */
#define CAN2510_BRG_13x       0xFFFFF3 /* SJW is 13*TQ */
#define CAN2510_BRG_14x       0xFFFFF2 /* SJW is 14*TQ */
#define CAN2510_BRG_15x       0xFFFFF1 /* SJW is 15*TQ */
#define CAN2510_BRG_16x       0xFFFFF0 /* SJW is 16*TQ */
#define CAN2510_BRG_17x       0xFFFFEF /* SJW is 17*TQ */
#define CAN2510_BRG_18x       0xFFFFEE /* SJW is 18*TQ */
#define CAN2510_BRG_19x       0xFFFFED /* SJW is 19*TQ */
#define CAN2510_BRG_20x       0xFFFFEC /* SJW is 20*TQ */
#define CAN2510_BRG_21x       0xFFFFEB /* SJW is 21*TQ */
#define CAN2510_BRG_22x       0xFFFFEA /* SJW is 22*TQ */
#define CAN2510_BRG_23x       0xFFFFE9 /* SJW is 23*TQ */
#define CAN2510_BRG_24x       0xFFFFE8 /* SJW is 24*TQ */
#define CAN2510_BRG_25x       0xFFFFE7 /* SJW is 25*TQ */
#define CAN2510_BRG_26x       0xFFFFE6 /* SJW is 26*TQ */
#define CAN2510_BRG_27x       0xFFFFE5 /* SJW is 27*TQ */
#define CAN2510_BRG_28x       0xFFFFE4 /* SJW is 28*TQ */
#define CAN2510_BRG_29x       0xFFFFE3 /* SJW is 29*TQ */
#define CAN2510_BRG_30x       0xFFFFE2 /* SJW is 30*TQ */
#define CAN2510_BRG_31x       0xFFFFE1 /* SJW is 31*TQ */
#define CAN2510_BRG_32x       0xFFFFE0 /* SJW is 32*TQ */
#define CAN2510_BRG_33x       0xFFFFDF /* SJW is 33*TQ */
#define CAN2510_BRG_34x       0xFFFFDE /* SJW is 34*TQ */
#define CAN2510_BRG_35x       0xFFFFDD /* SJW is 35*TQ */
#define CAN2510_BRG_36x       0xFFFFDC /* SJW is 36*TQ */
#define CAN2510_BRG_37x       0xFFFFDB /* SJW is 37*TQ */
#define CAN2510_BRG_38x       0xFFFFDA /* SJW is 38*TQ */
#define CAN2510_BRG_39x       0xFFFFD9 /* SJW is 39*TQ */
#define CAN2510_BRG_40x       0xFFFFD8 /* SJW is 40*TQ */
#define CAN2510_BRG_41x       0xFFFFD7 /* SJW is 41*TQ */
#define CAN2510_BRG_42x       0xFFFFD6 /* SJW is 42*TQ */
#define CAN2510_BRG_43x       0xFFFFD5 /* SJW is 43*TQ */
#define CAN2510_BRG_44x       0xFFFFD4 /* SJW is 44*TQ */
#define CAN2510_BRG_45x       0xFFFFD3 /* SJW is 45*TQ */
#define CAN2510_BRG_46x       0xFFFFD2 /* SJW is 46*TQ */
#define CAN2510_BRG_47x       0xFFFFD1 /* SJW is 47*TQ */
#define CAN2510_BRG_48x       0xFFFFD0 /* SJW is 48*TQ */
#define CAN2510_BRG_49x       0xFFFFCF /* SJW is 49*TQ */
#define CAN2510_BRG_50x       0xFFFFCE /* SJW is 50*TQ */
#define CAN2510_BRG_51x       0xFFFFCD /* SJW is 51*TQ */
#define CAN2510_BRG_52x       0xFFFFCC /* SJW is 52*TQ */
#define CAN2510_BRG_53x       0xFFFFCB /* SJW is 53*TQ */
#define CAN2510_BRG_54x       0xFFFFCA /* SJW is 54*TQ */
#define CAN2510_BRG_55x       0xFFFFC9 /* SJW is 55*TQ */
#define CAN2510_BRG_56x       0xFFFFC8 /* SJW is 56*TQ */
#define CAN2510_BRG_57x       0xFFFFC7 /* SJW is 57*TQ */
#define CAN2510_BRG_58x       0xFFFFC6 /* SJW is 58*TQ */
#define CAN2510_BRG_59x       0xFFFFC5 /* SJW is 59*TQ */
#define CAN2510_BRG_60x       0xFFFFC4 /* SJW is 60*TQ */
#define CAN2510_BRG_61x       0xFFFFC3 /* SJW is 61*TQ */
#define CAN2510_BRG_62x       0xFFFFC2 /* SJW is 62*TQ */
#define CAN2510_BRG_63x       0xFFFFC1 /* SJW is 63*TQ */
#define CAN2510_BRG_64x       0xFFFFC0 /* SJW is 64*TQ */


/****  CNF2 Register settings                                    ****/
/****  (BLTMODE)                                                 ****/
/****  This bit is stored in (BYTEH<7>)                          ****/
/****  Selects the source of the Phase 2 Segment time            ****/
#define CAN2510_PH2SOURCE_PH2 0xFFFFFF /* PH2SEG2:PH2SEG0 is the source */
#define CAN2510_PH2SOURCE_PH1 0xFF7FFF /* Greater of PH1SEG2:PH1SEG0 and 2TQ is the source */

/****  (SAM)                                                     ****/
/****  This bit is stored in (BYTEH<6>)                          ****/
/****  Specifies how many times to sample the Bit                ****/
#define CAN2510_SAMPLE_3x     0xFFFFFF /* 3 times sampling */
#define CAN2510_SAMPLE_1x     0xFFBFFF /* 1 times sampling */

/****  (PH1SEG2:PH1SEG0)                                         ****/
/****  This bit is stored in (BYTEH<5:3>)                        ****/
/* Specify the Phase 1 Segment time */
#define CAN2510_PH1SEG_1TQ    0xFFFFFF /* Phase 1 is 1*TQ, Default mode = all 1's */
#define CAN2510_PH1SEG_2TQ    0xFFF7FF /* Phase 1 is 2*TQ */
#define CAN2510_PH1SEG_3TQ    0xFFEFFF /* Phase 1 is 3*TQ */
#define CAN2510_PH1SEG_4TQ    0xFFE7FF /* Phase 1 is 4*TQ */
#define CAN2510_PH1SEG_5TQ    0xFFDFFF /* Phase 1 is 5*TQ */
#define CAN2510_PH1SEG_6TQ    0xFFD7FF /* Phase 1 is 6*TQ */
#define CAN2510_PH1SEG_7TQ    0xFFCFFF /* Phase 1 is 7*TQ */
#define CAN2510_PH1SEG_8TQ    0xFFC7FF /* Phase 1 is 8*TQ */

/****  (PRSEG2:PRSEG0)                                           ****/
/****  This bit is stored in (BYTEH<2:0>)                        ****/
/* Specify the Propagation Segment time */
#define CAN2510_PROPSEG_1TQ   0xFFFFFF /* Propagation Segment is 1*TQ, Default mode = all 1's */
#define CAN2510_PROPSEG_2TQ   0xFFFEFF /* Propagation Segment is 2*TQ */
#define CAN2510_PROPSEG_3TQ   0xFFFDFF /* Propagation Segment is 3*TQ */
#define CAN2510_PROPSEG_4TQ   0xFFFCFF /* Propagation Segment is 4*TQ */
#define CAN2510_PROPSEG_5TQ   0xFFFBFF /* Propagation Segment is 5*TQ */
#define CAN2510_PROPSEG_6TQ   0xFFFAFF /* Propagation Segment is 6*TQ */
#define CAN2510_PROPSEG_7TQ   0xFFF9FF /* Propagation Segment is 7*TQ */
#define CAN2510_PROPSEG_8TQ   0xFFF8FF /* Propagation Segment is 8*TQ */


/****  CNF3 Register settings                                       ****/
/****  (WAKFIL)                                                     ****/
/****  This bit is stored in (BYTEU<6>)                             ****/
/****  Specify if the RX pin will have a filter when in sleep mode  ****/
#define CAN2510_RX_FILTER     0xFFFFFF /* Input filter on RX pin, Default mode = all 1's */
#define CAN2510_RX_NOFILTER   0xBFFFFF /* No filter on RX pin */

/****  (PH2SEG2:PH2SEG0)                                                       ****/
/****  This bit is stored in (BYTEU<2:0>)                                      ****/
/****   Specify the Phase 2 Segment time                                       ****/
/****   The state of these variables are defined by NPH2SEG2:NPH2SEG1:PH2SEG0  ****/
/****      where the N indicates the complement of the value                   ****/
//  #define CAN2510_PH2SEG_1TQ    0xFEFFFF /* Not Available (Phase 2 is 1*TQ) */
#define CAN2510_PH2SEG_2TQ    0xFFFFFF /* Phase 2 is 2*TQ, Default mode = all 1's */
#define CAN2510_PH2SEG_3TQ    0xFCFFFF /* Phase 2 is 3*TQ */
#define CAN2510_PH2SEG_4TQ    0xFDFFFF /* Phase 2 is 4*TQ */
#define CAN2510_PH2SEG_5TQ    0xFAFFFF /* Phase 2 is 5*TQ */
#define CAN2510_PH2SEG_6TQ    0xFBFFFF /* Phase 2 is 6*TQ */
#define CAN2510_PH2SEG_7TQ    0xF8FFFF /* Phase 2 is 7*TQ */
#define CAN2510_PH2SEG_8TQ    0xF9FFFF /* Phase 2 is 8*TQ */


/*****************************************************************************/
/****                                                                     ****/
/****  INTERRUPTFLAG - unsigned char (BYTE)                               ****/
/****                                                                     ****/
/*****************************************************************************/
/****  CANINTE Register settings                                 ****/
/****  (MERRE:WAKIE:ERRIE:TX2IE:TX1IE:TX0IE:RX1IE:RX0IE)         ****/
/****  These bits are stored in (BYTE)                           ****/
/****  Specify which interrupts are enabled                      ****/
#define CAN2510_INT_MSGERR    0x7F
#define CAN2510_MSGERR_EN     0x7F
#define CAN2510_INT_WAKEUP    0xBF
#define CAN2510_WAKEUP_EN     0xBF
#define CAN2510_INT_ERROR     0xDF
#define CAN2510_ERROR_EN      0xDF
#define CAN2510_INT_XMT2      0xEF
#define CAN2510_TXB2_EN       0xEF
#define CAN2510_INT_XMT1      0xF7
#define CAN2510_TXB1_EN       0xF7
#define CAN2510_INT_XMT0      0xFB
#define CAN2510_TXB0_EN       0xFB
#define CAN2510_INT_RCV1      0xFD
#define CAN2510_RXB1_EN       0xFD
#define CAN2510_INT_RCV0      0xFE
#define CAN2510_RXB0_EN       0xFE
#define CAN2510_INT_NONE      0xFF
#define CAN2510_NONE_EN       0xFF


/*****************************************************************************/
/****                                                                     ****/
/****  SPI_SYNCMODE - unsigned char (BYTE)                                ****/
/****                                                                     ****/
/*****************************************************************************/
/****  PIC SPI Settings                                          ****/
/****  CKE and CKP bits, SSPSTAT and SSPCON1 Register settings   ****/
/****  These bits are stored in (BYTE)                           ****/
/****  Specify which SPI mode is used (mode 00 or 10)            ****/
#define CAN2510_SPI_MODE00    MODE_00             // CKE = 1, CKP = 0   (10)
#define CAN2510_SPI_MODE01    MODE_01             // CKE = 0, CKP = 0   (00)


/*****************************************************************************/
/****                                                                     ****/
/****  SPI_SMPMODE - unsigned char (BYTE)                                 ****/
/****                                                                     ****/
/*****************************************************************************/
/****  PIC SPI Settings                                          ****/
/****  SMP bit, SSPSTAT Register settings                        ****/
/****  These bits are stored in (BYTE)                           ****/
/****  Specify where the data is sampled (middle or end of bit)  ****/
#define CAN2510_SPI_SMPEND    SMPEND              // 1
#define CAN2510_SPI_SMPMID    SMPMID              // 0


/*****************************************************************************/
/****                                                                     ****/
/****  SPI_BUSMODE - unsigned char (BYTE)                                 ****/
/****                                                                     ****/
/*****************************************************************************/
/****  PIC SPI Settings                                          ****/
/****  SSPM3:SSPM0 bits, SSPCON1 Register settings               ****/
/****  These bits are stored in (BYTE)                           ****/
/****  Specify the clock rate of the SPI communication           ****/
#define CAN2510_SPI_FOSC4     SPI_FOSC_4             // 0000
#define CAN2510_SPI_FOSC16    SPI_FOSC_16            // 0001
#define CAN2510_SPI_FOSC64    SPI_FOSC_64            // 0010
#define CAN2510_SPI_FOSCTMR2  SPI_FOSC_TMR2          // 0011
                                                     // 11xx  Not Applicable

/*****************************************************************************/
/****                                                                     ****/
/****      Definitions of Variables for:                                  ****/
/****              Return Values                                          ****/
/****              Values to write (with appropriate mask)                ****/
/****              comparisions                                           ****/
/****                                                                     ****/
/*****************************************************************************/

/* CAN processor mode */
#define CAN2510_MODE_NORMAL   0x00 /* Normal (send and receive messages)   */
#define CAN2510_MODE_SLEEP    0x20 /* Wait for interrupt                   */
#define CAN2510_MODE_LOOPBACK 0x40 /* Testing - messages stay internal     */
#define CAN2510_MODE_LISTEN   0x60 /* Listen only -- don't send            */
#define CAN2510_MODE_CONFIG   0x80 /* Configuration (1XX0 0000 is Config)  */

/* XMIT Message priority */
#define CAN2510_PRI_HIGHEST   0x3
#define CAN2510_PRI_HIGH      0x2
#define CAN2510_PRI_LOW       0x1
#define CAN2510_PRI_LOWEST    0x0

/* No data to be read flag */
#define CAN2510_NO_DATA      0x00  /* No data to be read                            */
#define CAN2510_XTDRTR       0x01  /* Remote transmission request (XTD Message ID)  */
#define CAN2510_STDRTR       0x02  /* Remote transmission request (STD Message ID)  */
#define CAN2510_XTDMSG       0x03  /* Extended format message received              */
#define CAN2510_STDMSG       0x04  /* Standard format message received              */

/* Handy identifiers for buffer numbers */
#define CAN2510_BUF_0        0x00
#define CAN2510_BUF_1        0x01
#define CAN2510_BUF_2        0x02

#define CAN2510_RXB0         0x00
#define CAN2510_RXB1         0x01
#define CAN2510_RXBX         0x02   // Either (or both) of RXB0 and RXB1

#define CAN2510_TXB0         0x00
#define CAN2510_TXB1         0x01
#define CAN2510_TXB2         0x02
//#define CAN2510_TXBX         0x03   // Any (or all) of TXB0, TXB1, and TXB2
#define CAN2510_TXB0_B1      0x03
#define CAN2510_TXB0_B2      0x05
#define CAN2510_TXB1_B2      0x06
#define CAN2510_TXB0_B1_B2   0x07


/* Handy identifiers for buffer masks */
#define CAN2510_RXM0         0x00               // Mask for RXB0
#define CAN2510_RXM1         0x01               // Mask for RXB1

/* Handy identifiers for buffer filters */
#define CAN2510_RXF0         0x00               // Filter 0 for RXB0
#define CAN2510_RXF1         0x01               // Filter 1 for RXB0
#define CAN2510_RXF2         0x02               // Filter 2 for RXB1
#define CAN2510_RXF3         0x03               // Filter 3 for RXB1
#define CAN2510_RXF4         0x04               // Filter 4 for RXB1
#define CAN2510_RXF5         0x05               // Filter 5 for RXB1

/* Error States */
#define CAN2510_BUS_OFF                      0x05
#define BUS_OFF                              0x05
#define CAN2510_ERROR_PASSIVE_TX             0x04
#define ERROR_PASSIVE_TX                     0x04
#define CAN2510_ERROR_PASSIVE_RX             0x03
#define ERROR_PASSIVE_RX                     0x03
#define CAN2510_ERROR_ACTIVE_WITH_TXWARN     0x02
#define ERROR_ACTIVE_WITH_TXWARN             0x02
#define CAN2510_ERROR_ACTIVE_WITH_RXWARN     0x01
#define ERROR_ACTIVE_WITH_RXWARN             0x01
#define CAN2510_ERROR_ACTIVE                 0x00
#define ERROR_ACTIVE                         0x00

/* Interrupt sources */
#define CAN2510_ERROR_INT     0x02
#define CAN2510_WAKEUP_INT    0x04
#define CAN2510_TXB2_INT      0x0A
#define CAN2510_TXB1_INT      0x08
#define CAN2510_TXB0_INT      0x06
#define CAN2510_RXB1_INT      0x0E
#define CAN2510_RXB0_INT      0x0C
#define CAN2510_NO_INTS       0x00



/*****************************************************************************/
/*****************************************************************************/
/****                                                                     ****/
/****                       Function prototypes                           ****/
/****                                                                     ****/
/*****************************************************************************/
/*****************************************************************************/

void CAN2510Initialize( auto unsigned int configuration,
                        auto unsigned char brp,
                        auto unsigned char interruptFlags,
                        auto unsigned char SPI_syncMode,
                        auto unsigned char SPI_busMode,
                        auto unsigned char SPI_smpPhase );

unsigned char CAN2510Init( auto unsigned short long BufferConfig,
                           auto unsigned short long BitTimeConfig,
                           auto unsigned char interruptEnables,
                           auto unsigned char SPI_syncMode,
                           auto unsigned char SPI_busMode,
                           auto unsigned char SPI_smpPhase );

void CAN2510Enable( void );

void CAN2510Disable( void );

void CAN2510Reset( void );

void CAN2510SetMode( auto unsigned char mode );

unsigned char CAN2510ReadMode( void );

unsigned char CAN2510ReadStatus( void );

unsigned char CAN2510ErrorState( void );

unsigned char CAN2510InterruptStatus( void );

void CAN2510InterruptEnable( unsigned char interruptFlags );

unsigned char CAN2510ByteRead( auto unsigned char addr );

void CAN2510ByteWrite( auto unsigned char addr, auto unsigned char value );

void CAN2510SequentialRead( auto unsigned char *DataArray,
                            auto unsigned char CAN2510addr,
                            auto unsigned char numbytes );

void CAN2510SequentialWrite( auto unsigned char *DataArray,
                             auto unsigned char CAN2510addr,
                             auto unsigned char numbytes );

void CAN2510BitModify( auto unsigned char address,
                       auto unsigned char mask,
                       auto unsigned char data );

void CAN2510SetSingleMaskStd( auto unsigned char maskNum, auto unsigned int mask );

void CAN2510SetSingleMaskXtd( auto unsigned char maskNum, auto unsigned long mask );

void CAN2510SetSingleFilterStd( auto unsigned char filterNum, auto unsigned int  filter );

void CAN2510SetSingleFilterXtd( auto unsigned char filterNum, auto unsigned long filter );

unsigned char CAN2510SetMsgFilterStd( auto unsigned char bufferNum,
                                      auto unsigned int  mask,
                                      auto unsigned int  *filters );

unsigned char CAN2510SetMsgFilterXtd( auto unsigned char bufferNum,
                                      auto unsigned long mask,
                                      auto unsigned long *filters );

unsigned char CAN2510WriteStd( auto unsigned int  msgId,
                               auto unsigned char msgPriority,
                               auto unsigned char numBytes,
                               auto unsigned char *data );

unsigned char CAN2510WriteXtd( auto unsigned long msgId,
                               auto unsigned char msgPriority,
                               auto unsigned char numBytes,
                               auto unsigned char *data );

void CAN2510LoadBufferStd( auto unsigned char bufferNum,
                           auto unsigned int  msgId,
                           auto unsigned char numBytes,
                           auto unsigned char *data );

void CAN2510LoadBufferXtd( auto unsigned char bufferNum,
                           auto unsigned long msgId,
                           auto unsigned char numBytes,
                           auto unsigned char *data );

void CAN2510LoadRTRStd( auto unsigned char bufferNum,
                        auto unsigned int  msgId,
                        auto unsigned char numBytes );

void CAN2510LoadRTRXtd( auto unsigned char bufferNum,
                        auto unsigned long msgId,
                        auto unsigned char numBytes );

void CAN2510SetBufferPriority( auto unsigned char bufferNum,
                               auto unsigned char bufferPriority );

void CAN2510SendBuffer( auto unsigned char bufferNumber );

unsigned char CAN2510WriteBuffer( auto unsigned char bufferNum );

unsigned char CAN2510DataReady( auto unsigned char bufferNum );

unsigned char CAN2510DataRead( auto unsigned char bufferNum,
                               auto unsigned long *msgId,
			       auto unsigned char *numBytes,
			       auto unsigned char *data );

#endif  /* __CAN2510_H */
