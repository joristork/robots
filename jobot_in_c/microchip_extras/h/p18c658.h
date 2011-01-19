/*-------------------------------------------------------------------------
 * MPLAB-Cxx  PIC18C658 processor header
 *
 * (c) Copyright 1999-2010 Microchip Technology, All rights reserved
 *-------------------------------------------------------------------------*/

#ifndef __18C658_H
#define __18C658_H

extern volatile far  unsigned char       RXF0SIDH;
extern volatile far  struct {
  unsigned SID3:1;
  unsigned SID4:1;
  unsigned SID5:1;
  unsigned SID6:1;
  unsigned SID7:1;
  unsigned SID8:1;
  unsigned SID9:1;
  unsigned SID10:1;
} RXF0SIDHbits;
extern volatile far  unsigned char       RXF0SIDL;
extern volatile far  union {
  struct {
    unsigned EID16:1;
    unsigned EID17:1;
    unsigned :1;
    unsigned EXIDEN:1;
    unsigned :1;
    unsigned SID0:1;
    unsigned SID1:1;
    unsigned SID2:1;
  };
  struct {
    unsigned :3;
    unsigned EXIDE:1;
  };
} RXF0SIDLbits;
extern volatile far  unsigned char       RXF0EIDH;
extern volatile far  struct {
  unsigned EID8:1;
  unsigned EID9:1;
  unsigned EID10:1;
  unsigned EID11:1;
  unsigned EID12:1;
  unsigned EID13:1;
  unsigned EID14:1;
  unsigned EID15:1;
} RXF0EIDHbits;
extern volatile far  unsigned char       RXF0EIDL;
extern volatile far  struct {
  unsigned EID0:1;
  unsigned EID1:1;
  unsigned EID2:1;
  unsigned EID3:1;
  unsigned EID4:1;
  unsigned EID5:1;
  unsigned EID6:1;
  unsigned EID7:1;
} RXF0EIDLbits;
extern volatile far  unsigned char       RXF1SIDH;
extern volatile far  struct {
  unsigned SID3:1;
  unsigned SID4:1;
  unsigned SID5:1;
  unsigned SID6:1;
  unsigned SID7:1;
  unsigned SID8:1;
  unsigned SID9:1;
  unsigned SID10:1;
} RXF1SIDHbits;
extern volatile far  unsigned char       RXF1SIDL;
extern volatile far  union {
  struct {
    unsigned EID16:1;
    unsigned EID17:1;
    unsigned :1;
    unsigned EXIDEN:1;
    unsigned :1;
    unsigned SID0:1;
    unsigned SID1:1;
    unsigned SID2:1;
  };
  struct {
    unsigned :3;
    unsigned EXIDE:1;
  };
} RXF1SIDLbits;
extern volatile far  unsigned char       RXF1EID8;
extern volatile far  struct {
  unsigned EID8:1;
  unsigned EID9:1;
  unsigned EID10:1;
  unsigned EID11:1;
  unsigned EID12:1;
  unsigned EID13:1;
  unsigned EID14:1;
  unsigned EID15:1;
} RXF1EID8bits;
extern volatile far  unsigned char       RXF1EIDH;
extern volatile far  struct {
  unsigned EID8:1;
  unsigned EID9:1;
  unsigned EID10:1;
  unsigned EID11:1;
  unsigned EID12:1;
  unsigned EID13:1;
  unsigned EID14:1;
  unsigned EID15:1;
} RXF1EIDHbits;
extern volatile far  unsigned char       RXF1EID0;
extern volatile far  struct {
  unsigned EID0:1;
  unsigned EID1:1;
  unsigned EID2:1;
  unsigned EID3:1;
  unsigned EID4:1;
  unsigned EID5:1;
  unsigned EID6:1;
  unsigned EID7:1;
} RXF1EID0bits;
extern volatile far  unsigned char       RXF1EIDL;
extern volatile far  struct {
  unsigned EID0:1;
  unsigned EID1:1;
  unsigned EID2:1;
  unsigned EID3:1;
  unsigned EID4:1;
  unsigned EID5:1;
  unsigned EID6:1;
  unsigned EID7:1;
} RXF1EIDLbits;
extern volatile far  unsigned char       RXF2SIDH;
extern volatile far  struct {
  unsigned SID3:1;
  unsigned SID4:1;
  unsigned SID5:1;
  unsigned SID6:1;
  unsigned SID7:1;
  unsigned SID8:1;
  unsigned SID9:1;
  unsigned SID10:1;
} RXF2SIDHbits;
extern volatile far  unsigned char       RXF2SIDL;
extern volatile far  union {
  struct {
    unsigned EID16:1;
    unsigned EID17:1;
    unsigned :1;
    unsigned EXIDEN:1;
    unsigned :1;
    unsigned SID0:1;
    unsigned SID1:1;
    unsigned SID2:1;
  };
  struct {
    unsigned :3;
    unsigned EXIDE:1;
  };
} RXF2SIDLbits;
extern volatile far  unsigned char       RXF2EID8;
extern volatile far  struct {
  unsigned EID8:1;
  unsigned EID9:1;
  unsigned EID10:1;
  unsigned EID11:1;
  unsigned EID12:1;
  unsigned EID13:1;
  unsigned EID14:1;
  unsigned EID15:1;
} RXF2EID8bits;
extern volatile far  unsigned char       RXF2EIDH;
extern volatile far  struct {
  unsigned EID8:1;
  unsigned EID9:1;
  unsigned EID10:1;
  unsigned EID11:1;
  unsigned EID12:1;
  unsigned EID13:1;
  unsigned EID14:1;
  unsigned EID15:1;
} RXF2EIDHbits;
extern volatile far  unsigned char       RXF2EID0;
extern volatile far  struct {
  unsigned EID0:1;
  unsigned EID1:1;
  unsigned EID2:1;
  unsigned EID3:1;
  unsigned EID4:1;
  unsigned EID5:1;
  unsigned EID6:1;
  unsigned EID7:1;
} RXF2EID0bits;
extern volatile far  unsigned char       RXF2EIDL;
extern volatile far  struct {
  unsigned EID0:1;
  unsigned EID1:1;
  unsigned EID2:1;
  unsigned EID3:1;
  unsigned EID4:1;
  unsigned EID5:1;
  unsigned EID6:1;
  unsigned EID7:1;
} RXF2EIDLbits;
extern volatile far  unsigned char       RXF3SIDH;
extern volatile far  struct {
  unsigned SID3:1;
  unsigned SID4:1;
  unsigned SID5:1;
  unsigned SID6:1;
  unsigned SID7:1;
  unsigned SID8:1;
  unsigned SID9:1;
  unsigned SID10:1;
} RXF3SIDHbits;
extern volatile far  unsigned char       RXF3SIDL;
extern volatile far  union {
  struct {
    unsigned EID16:1;
    unsigned EID17:1;
    unsigned :1;
    unsigned EXIDEN:1;
    unsigned :1;
    unsigned SID0:1;
    unsigned SID1:1;
    unsigned SID2:1;
  };
  struct {
    unsigned :3;
    unsigned EXIDE:1;
  };
} RXF3SIDLbits;
extern volatile far  unsigned char       RXF3EID8;
extern volatile far  struct {
  unsigned EID8:1;
  unsigned EID9:1;
  unsigned EID10:1;
  unsigned EID11:1;
  unsigned EID12:1;
  unsigned EID13:1;
  unsigned EID14:1;
  unsigned EID15:1;
} RXF3EID8bits;
extern volatile far  unsigned char       RXF3EIDH;
extern volatile far  struct {
  unsigned EID8:1;
  unsigned EID9:1;
  unsigned EID10:1;
  unsigned EID11:1;
  unsigned EID12:1;
  unsigned EID13:1;
  unsigned EID14:1;
  unsigned EID15:1;
} RXF3EIDHbits;
extern volatile far  unsigned char       RXF3EID0;
extern volatile far  struct {
  unsigned EID0:1;
  unsigned EID1:1;
  unsigned EID2:1;
  unsigned EID3:1;
  unsigned EID4:1;
  unsigned EID5:1;
  unsigned EID6:1;
  unsigned EID7:1;
} RXF3EID0bits;
extern volatile far  unsigned char       RXF3EIDL;
extern volatile far  struct {
  unsigned EID0:1;
  unsigned EID1:1;
  unsigned EID2:1;
  unsigned EID3:1;
  unsigned EID4:1;
  unsigned EID5:1;
  unsigned EID6:1;
  unsigned EID7:1;
} RXF3EIDLbits;
extern volatile far  unsigned char       RXF4SIDH;
extern volatile far  struct {
  unsigned SID3:1;
  unsigned SID4:1;
  unsigned SID5:1;
  unsigned SID6:1;
  unsigned SID7:1;
  unsigned SID8:1;
  unsigned SID9:1;
  unsigned SID10:1;
} RXF4SIDHbits;
extern volatile far  unsigned char       RXF4SIDL;
extern volatile far  union {
  struct {
    unsigned EID16:1;
    unsigned EID17:1;
    unsigned :1;
    unsigned EXIDEN:1;
    unsigned :1;
    unsigned SID0:1;
    unsigned SID1:1;
    unsigned SID2:1;
  };
  struct {
    unsigned :3;
    unsigned EXIDE:1;
  };
} RXF4SIDLbits;
extern volatile far  unsigned char       RXF4EID8;
extern volatile far  struct {
  unsigned EID8:1;
  unsigned EID9:1;
  unsigned EID10:1;
  unsigned EID11:1;
  unsigned EID12:1;
  unsigned EID13:1;
  unsigned EID14:1;
  unsigned EID15:1;
} RXF4EID8bits;
extern volatile far  unsigned char       RXF4EIDH;
extern volatile far  struct {
  unsigned EID8:1;
  unsigned EID9:1;
  unsigned EID10:1;
  unsigned EID11:1;
  unsigned EID12:1;
  unsigned EID13:1;
  unsigned EID14:1;
  unsigned EID15:1;
} RXF4EIDHbits;
extern volatile far  unsigned char       RXF4EID0;
extern volatile far  struct {
  unsigned EID0:1;
  unsigned EID1:1;
  unsigned EID2:1;
  unsigned EID3:1;
  unsigned EID4:1;
  unsigned EID5:1;
  unsigned EID6:1;
  unsigned EID7:1;
} RXF4EID0bits;
extern volatile far  unsigned char       RXF4EIDL;
extern volatile far  struct {
  unsigned EID0:1;
  unsigned EID1:1;
  unsigned EID2:1;
  unsigned EID3:1;
  unsigned EID4:1;
  unsigned EID5:1;
  unsigned EID6:1;
  unsigned EID7:1;
} RXF4EIDLbits;
extern volatile far  unsigned char       RXF5SIDH;
extern volatile far  struct {
  unsigned SID3:1;
  unsigned SID4:1;
  unsigned SID5:1;
  unsigned SID6:1;
  unsigned SID7:1;
  unsigned SID8:1;
  unsigned SID9:1;
  unsigned SID10:1;
} RXF5SIDHbits;
extern volatile far  unsigned char       RXF5SIDL;
extern volatile far  union {
  struct {
    unsigned EID16:1;
    unsigned EID17:1;
    unsigned :1;
    unsigned EXIDEN:1;
    unsigned :1;
    unsigned SID0:1;
    unsigned SID1:1;
    unsigned SID2:1;
  };
  struct {
    unsigned :3;
    unsigned EXIDE:1;
  };
} RXF5SIDLbits;
extern volatile far  unsigned char       RXF5EID8;
extern volatile far  struct {
  unsigned EID8:1;
  unsigned EID9:1;
  unsigned EID10:1;
  unsigned EID11:1;
  unsigned EID12:1;
  unsigned EID13:1;
  unsigned EID14:1;
  unsigned EID15:1;
} RXF5EID8bits;
extern volatile far  unsigned char       RXF5EIDH;
extern volatile far  struct {
  unsigned EID8:1;
  unsigned EID9:1;
  unsigned EID10:1;
  unsigned EID11:1;
  unsigned EID12:1;
  unsigned EID13:1;
  unsigned EID14:1;
  unsigned EID15:1;
} RXF5EIDHbits;
extern volatile far  unsigned char       RXF5EID0;
extern volatile far  struct {
  unsigned EID0:1;
  unsigned EID1:1;
  unsigned EID2:1;
  unsigned EID3:1;
  unsigned EID4:1;
  unsigned EID5:1;
  unsigned EID6:1;
  unsigned EID7:1;
} RXF5EID0bits;
extern volatile far  unsigned char       RXF5EIDL;
extern volatile far  struct {
  unsigned EID0:1;
  unsigned EID1:1;
  unsigned EID2:1;
  unsigned EID3:1;
  unsigned EID4:1;
  unsigned EID5:1;
  unsigned EID6:1;
  unsigned EID7:1;
} RXF5EIDLbits;
extern volatile far  unsigned char       RXM0SIDH;
extern volatile far  struct {
  unsigned SID3:1;
  unsigned SID4:1;
  unsigned SID5:1;
  unsigned SID6:1;
  unsigned SID7:1;
  unsigned SID8:1;
  unsigned SID9:1;
  unsigned SID10:1;
} RXM0SIDHbits;
extern volatile far  unsigned char       RXM0SIDL;
extern volatile far  struct {
  unsigned EID16:1;
  unsigned EID17:1;
  unsigned :3;
  unsigned SID0:1;
  unsigned SID1:1;
  unsigned SID2:1;
} RXM0SIDLbits;
extern volatile far  unsigned char       RXM0EID8;
extern volatile far  struct {
  unsigned EID8:1;
  unsigned EID9:1;
  unsigned EID10:1;
  unsigned EID11:1;
  unsigned EID12:1;
  unsigned EID13:1;
  unsigned EID14:1;
  unsigned EID15:1;
} RXM0EID8bits;
extern volatile far  unsigned char       RXM0EIDH;
extern volatile far  struct {
  unsigned EID8:1;
  unsigned EID9:1;
  unsigned EID10:1;
  unsigned EID11:1;
  unsigned EID12:1;
  unsigned EID13:1;
  unsigned EID14:1;
  unsigned EID15:1;
} RXM0EIDHbits;
extern volatile far  unsigned char       RXM0EID0;
extern volatile far  struct {
  unsigned EID0:1;
  unsigned EID1:1;
  unsigned EID2:1;
  unsigned EID3:1;
  unsigned EID4:1;
  unsigned EID5:1;
  unsigned EID6:1;
  unsigned EID7:1;
} RXM0EID0bits;
extern volatile far  unsigned char       RXM0EIDL;
extern volatile far  struct {
  unsigned EID0:1;
  unsigned EID1:1;
  unsigned EID2:1;
  unsigned EID3:1;
  unsigned EID4:1;
  unsigned EID5:1;
  unsigned EID6:1;
  unsigned EID7:1;
} RXM0EIDLbits;
extern volatile far  unsigned char       RXM1SIDH;
extern volatile far  struct {
  unsigned SID3:1;
  unsigned SID4:1;
  unsigned SID5:1;
  unsigned SID6:1;
  unsigned SID7:1;
  unsigned SID8:1;
  unsigned SID9:1;
  unsigned SID10:1;
} RXM1SIDHbits;
extern volatile far  unsigned char       RXM1SIDL;
extern volatile far  struct {
  unsigned EID16:1;
  unsigned EID17:1;
  unsigned :3;
  unsigned SID0:1;
  unsigned SID1:1;
  unsigned SID2:1;
} RXM1SIDLbits;
extern volatile far  unsigned char       RXM1EID8;
extern volatile far  struct {
  unsigned EID8:1;
  unsigned EID9:1;
  unsigned EID10:1;
  unsigned EID11:1;
  unsigned EID12:1;
  unsigned EID13:1;
  unsigned EID14:1;
  unsigned EID15:1;
} RXM1EID8bits;
extern volatile far  unsigned char       RXM1EIDH;
extern volatile far  struct {
  unsigned EID8:1;
  unsigned EID9:1;
  unsigned EID10:1;
  unsigned EID11:1;
  unsigned EID12:1;
  unsigned EID13:1;
  unsigned EID14:1;
  unsigned EID15:1;
} RXM1EIDHbits;
extern volatile far  unsigned char       RXM1EID0;
extern volatile far  struct {
  unsigned EID0:1;
  unsigned EID1:1;
  unsigned EID2:1;
  unsigned EID3:1;
  unsigned EID4:1;
  unsigned EID5:1;
  unsigned EID6:1;
  unsigned EID7:1;
} RXM1EID0bits;
extern volatile far  unsigned char       RXM1EIDL;
extern volatile far  struct {
  unsigned EID0:1;
  unsigned EID1:1;
  unsigned EID2:1;
  unsigned EID3:1;
  unsigned EID4:1;
  unsigned EID5:1;
  unsigned EID6:1;
  unsigned EID7:1;
} RXM1EIDLbits;
extern volatile far  unsigned char       TXB2CON;
extern volatile far  struct {
  unsigned TXPRI0:1;
  unsigned TXPRI1:1;
  unsigned :1;
  unsigned TXREQ:1;
  unsigned TXERR:1;
  unsigned TXLARB:1;
  unsigned TXABT:1;
} TXB2CONbits;
extern volatile far  unsigned char       TXB2SIDH;
extern volatile far  struct {
  unsigned SID3:1;
  unsigned SID4:1;
  unsigned SID5:1;
  unsigned SID6:1;
  unsigned SID7:1;
  unsigned SID8:1;
  unsigned SID9:1;
  unsigned SID10:1;
} TXB2SIDHbits;
extern volatile far  unsigned char       TXB2SIDL;
extern volatile far  union {
  struct {
    unsigned EID16:1;
    unsigned EID17:1;
    unsigned :1;
    unsigned EXIDE:1;
    unsigned :1;
    unsigned SID0:1;
    unsigned SID1:1;
    unsigned SID2:1;
  };
  struct {
    unsigned :3;
    unsigned EXID:1;
  };
} TXB2SIDLbits;
extern volatile far  unsigned char       TXB2EIDH;
extern volatile far  struct {
  unsigned EID8:1;
  unsigned EID9:1;
  unsigned EID10:1;
  unsigned EID11:1;
  unsigned EID12:1;
  unsigned EID13:1;
  unsigned EID14:1;
  unsigned EID15:1;
} TXB2EIDHbits;
extern volatile far  unsigned char       TXB2EIDL;
extern volatile far  struct {
  unsigned EID0:1;
  unsigned EID1:1;
  unsigned EID2:1;
  unsigned EID3:1;
  unsigned EID4:1;
  unsigned EID5:1;
  unsigned EID6:1;
  unsigned EID7:1;
} TXB2EIDLbits;
extern volatile far  unsigned char       TXB2DLC;
extern volatile far  union {
  struct {
    unsigned DLC0:1;
    unsigned DLC1:1;
    unsigned DLC2:1;
    unsigned DLC3:1;
    unsigned :2;
    unsigned TXRTR:1;
  };
  struct {
    unsigned DCL0:1;
    unsigned :5;
    unsigned RTR:1;
  };
} TXB2DLCbits;
extern volatile far  unsigned char       TXB2D0;
extern volatile far  struct {
  unsigned TXB2D00:1;
  unsigned TXB2D01:1;
  unsigned TXB2D02:1;
  unsigned TXB2D03:1;
  unsigned TXB2D04:1;
  unsigned TXB2D05:1;
  unsigned TXB2D06:1;
  unsigned TXB2D07:1;
} TXB2D0bits;
extern volatile far  unsigned char       TXB2D1;
extern volatile far  struct {
  unsigned TXB2D10:1;
  unsigned TXB2D11:1;
  unsigned TXB2D12:1;
  unsigned TXB2D13:1;
  unsigned TXB2D14:1;
  unsigned TXB2D15:1;
  unsigned TXB2D16:1;
  unsigned TXB2D17:1;
} TXB2D1bits;
extern volatile far  unsigned char       TXB2D2;
extern volatile far  struct {
  unsigned TXB2D20:1;
  unsigned TXB2D21:1;
  unsigned TXB2D22:1;
  unsigned TXB2D23:1;
  unsigned TXB2D24:1;
  unsigned TXB2D25:1;
  unsigned TXB2D26:1;
  unsigned TXB2D27:1;
} TXB2D2bits;
extern volatile far  unsigned char       TXB2D3;
extern volatile far  struct {
  unsigned TXB2D30:1;
  unsigned TXB2D31:1;
  unsigned TXB2D32:1;
  unsigned TXB2D33:1;
  unsigned TXB2D34:1;
  unsigned TXB2D35:1;
  unsigned TXB2D36:1;
  unsigned TXB2D37:1;
} TXB2D3bits;
extern volatile far  unsigned char       TXB2D4;
extern volatile far  struct {
  unsigned TXB2D40:1;
  unsigned TXB2D41:1;
  unsigned TXB2D42:1;
  unsigned TXB2D43:1;
  unsigned TXB2D44:1;
  unsigned TXB2D45:1;
  unsigned TXB2D46:1;
  unsigned TXB2D47:1;
} TXB2D4bits;
extern volatile far  unsigned char       TXB2D5;
extern volatile far  struct {
  unsigned TXB2D50:1;
  unsigned TXB2D51:1;
  unsigned TXB2D52:1;
  unsigned TXB2D53:1;
  unsigned TXB2D54:1;
  unsigned TXB2D55:1;
  unsigned TXB2D56:1;
  unsigned TXB2D57:1;
} TXB2D5bits;
extern volatile far  unsigned char       TXB2D6;
extern volatile far  struct {
  unsigned TXB2D60:1;
  unsigned TXB2D61:1;
  unsigned TXB2D62:1;
  unsigned TXB2D63:1;
  unsigned TXB2D64:1;
  unsigned TXB2D65:1;
  unsigned TXB2D66:1;
  unsigned TXB2D67:1;
} TXB2D6bits;
extern volatile far  unsigned char       TXB2D7;
extern volatile far  struct {
  unsigned TXB2D70:1;
  unsigned TXB2D71:1;
  unsigned TXB2D72:1;
  unsigned TXB2D73:1;
  unsigned TXB2D74:1;
  unsigned TXB2D75:1;
  unsigned TXB2D76:1;
  unsigned TXB2D77:1;
} TXB2D7bits;
extern volatile far  unsigned char       CANSTATRO3;
extern volatile far  struct {
  unsigned :1;
  unsigned ICODE0:1;
  unsigned ICODE1:1;
  unsigned ICODE2:1;
  unsigned :1;
  unsigned OPMODE0:1;
  unsigned OPMODE1:1;
  unsigned OPMODE2:1;
} CANSTATRO3bits;
extern volatile far  unsigned char       CANSTAT_R04;
extern volatile far  struct {
  unsigned :1;
  unsigned ICODE0:1;
  unsigned ICODE1:1;
  unsigned ICODE2:1;
  unsigned :1;
  unsigned OPMODE0:1;
  unsigned OPMODE1:1;
  unsigned OPMODE2:1;
} CANSTAT_R04bits;
extern volatile far  unsigned char       CANSTAT_RO4;
extern volatile far  struct {
  unsigned :1;
  unsigned ICODE0:1;
  unsigned ICODE1:1;
  unsigned ICODE2:1;
  unsigned :1;
  unsigned OPMODE0:1;
  unsigned OPMODE1:1;
  unsigned OPMODE2:1;
} CANSTAT_RO4bits;
extern volatile far  unsigned char       TXB1CON;
extern volatile far  struct {
  unsigned TXPRI0:1;
  unsigned TXPRI1:1;
  unsigned :1;
  unsigned TXREQ:1;
  unsigned TXERR:1;
  unsigned TXLARB:1;
  unsigned TXABT:1;
} TXB1CONbits;
extern volatile far  unsigned char       TXB1SIDH;
extern volatile far  struct {
  unsigned SID3:1;
  unsigned SID4:1;
  unsigned SID5:1;
  unsigned SID6:1;
  unsigned SID7:1;
  unsigned SID8:1;
  unsigned SID9:1;
  unsigned SID10:1;
} TXB1SIDHbits;
extern volatile far  unsigned char       TXB1SIDL;
extern volatile far  union {
  struct {
    unsigned EID16:1;
    unsigned EID17:1;
    unsigned :1;
    unsigned EXIDE:1;
    unsigned :1;
    unsigned SID0:1;
    unsigned SID1:1;
    unsigned SID2:1;
  };
  struct {
    unsigned :3;
    unsigned EXID:1;
  };
} TXB1SIDLbits;
extern volatile far  unsigned char       TXB1EIDH;
extern volatile far  struct {
  unsigned EID8:1;
  unsigned EID9:1;
  unsigned EID10:1;
  unsigned EID11:1;
  unsigned EID12:1;
  unsigned EID13:1;
  unsigned EID14:1;
  unsigned EID15:1;
} TXB1EIDHbits;
extern volatile far  unsigned char       TXB1EIDL;
extern volatile far  struct {
  unsigned EID0:1;
  unsigned EID1:1;
  unsigned EID2:1;
  unsigned EID3:1;
  unsigned EID4:1;
  unsigned EID5:1;
  unsigned EID6:1;
  unsigned EID7:1;
} TXB1EIDLbits;
extern volatile far  unsigned char       TXB1DLC;
extern volatile far  union {
  struct {
    unsigned DLC0:1;
    unsigned DLC1:1;
    unsigned DLC2:1;
    unsigned DLC3:1;
    unsigned :2;
    unsigned TXRTR:1;
  };
  struct {
    unsigned :6;
    unsigned RTR:1;
  };
} TXB1DLCbits;
extern volatile far  unsigned char       TXB1D0;
extern volatile far  struct {
  unsigned TXB1D00:1;
  unsigned TXB1D01:1;
  unsigned TXB1D02:1;
  unsigned TXB1D03:1;
  unsigned TXB1D04:1;
  unsigned TXB1D05:1;
  unsigned TXB1D06:1;
  unsigned TXB1D07:1;
} TXB1D0bits;
extern volatile far  unsigned char       TXB1D1;
extern volatile far  struct {
  unsigned TXB1D10:1;
  unsigned TXB1D11:1;
  unsigned TXB1D12:1;
  unsigned TXB1D13:1;
  unsigned TXB1D14:1;
  unsigned TXB1D15:1;
  unsigned TXB1D16:1;
  unsigned TXB1D17:1;
} TXB1D1bits;
extern volatile far  unsigned char       TXB1D2;
extern volatile far  union {
  struct {
    unsigned TXB1D20:1;
    unsigned TXB1D21:1;
    unsigned TXB1D22:1;
    unsigned TXB1D23:1;
    unsigned TXB1D24:1;
    unsigned TXB1D25:1;
    unsigned TXB1D26:1;
    unsigned TXB1D27:1;
  };
  struct {
    unsigned :3;
    unsigned TBB1D23:1;
  };
} TXB1D2bits;
extern volatile far  unsigned char       TXB1D3;
extern volatile far  struct {
  unsigned TXB1D30:1;
  unsigned TXB1D31:1;
  unsigned TXB1D32:1;
  unsigned TXB1D33:1;
  unsigned TXB1D34:1;
  unsigned TXB1D35:1;
  unsigned TXB1D36:1;
  unsigned TXB1D37:1;
} TXB1D3bits;
extern volatile far  unsigned char       TXB1D4;
extern volatile far  struct {
  unsigned TXB1D40:1;
  unsigned TXB1D41:1;
  unsigned TXB1D42:1;
  unsigned TXB1D43:1;
  unsigned TXB1D44:1;
  unsigned TXB1D45:1;
  unsigned TXB1D46:1;
  unsigned TXB1D47:1;
} TXB1D4bits;
extern volatile far  unsigned char       TXB1D5;
extern volatile far  struct {
  unsigned TXB1D50:1;
  unsigned TXB1D51:1;
  unsigned TXB1D52:1;
  unsigned TXB1D53:1;
  unsigned TXB1D54:1;
  unsigned TXB1D55:1;
  unsigned TXB1D56:1;
  unsigned TXB1D57:1;
} TXB1D5bits;
extern volatile far  unsigned char       TXB1D6;
extern volatile far  struct {
  unsigned TXB1D60:1;
  unsigned TXB1D61:1;
  unsigned TXB1D62:1;
  unsigned TXB1D63:1;
  unsigned TXB1D64:1;
  unsigned TXB1D65:1;
  unsigned TXB1D66:1;
  unsigned TXB1D67:1;
} TXB1D6bits;
extern volatile far  unsigned char       TXB1D7;
extern volatile far  struct {
  unsigned TXB1D70:1;
  unsigned TXB1D71:1;
  unsigned TXB1D72:1;
  unsigned TXB1D73:1;
  unsigned TXB1D74:1;
  unsigned TXB1D75:1;
  unsigned TXB1D76:1;
  unsigned TXB1D77:1;
} TXB1D7bits;
extern volatile far  unsigned char       CANSTATRO2;
extern volatile far  struct {
  unsigned :1;
  unsigned ICODE0:1;
  unsigned ICODE1:1;
  unsigned ICODE2:1;
  unsigned :1;
  unsigned OPMODE0:1;
  unsigned OPMODE1:1;
  unsigned OPMODE2:1;
} CANSTATRO2bits;
extern volatile far  unsigned char       CANSTAT_R03;
extern volatile far  struct {
  unsigned :1;
  unsigned ICODE0:1;
  unsigned ICODE1:1;
  unsigned ICODE2:1;
  unsigned :1;
  unsigned OPMODE0:1;
  unsigned OPMODE1:1;
  unsigned OPMODE2:1;
} CANSTAT_R03bits;
extern volatile far  unsigned char       CANSTAT_RO3;
extern volatile far  struct {
  unsigned :1;
  unsigned ICODE0:1;
  unsigned ICODE1:1;
  unsigned ICODE2:1;
  unsigned :1;
  unsigned OPMODE0:1;
  unsigned OPMODE1:1;
  unsigned OPMODE2:1;
} CANSTAT_RO3bits;
extern volatile far  unsigned char       TXB0CON;
extern volatile far  struct {
  unsigned TXPRI0:1;
  unsigned TXPRI1:1;
  unsigned :1;
  unsigned TXREQ:1;
  unsigned TXERR:1;
  unsigned TXLARB:1;
  unsigned TXABT:1;
} TXB0CONbits;
extern volatile far  unsigned char       TXB0SIDH;
extern volatile far  struct {
  unsigned SID3:1;
  unsigned SID4:1;
  unsigned SID5:1;
  unsigned SID6:1;
  unsigned SID7:1;
  unsigned SID8:1;
  unsigned SID9:1;
  unsigned SID10:1;
} TXB0SIDHbits;
extern volatile far  unsigned char       TXB0SIDL;
extern volatile far  union {
  struct {
    unsigned EID16:1;
    unsigned EID17:1;
    unsigned :1;
    unsigned EXIDEN:1;
    unsigned :1;
    unsigned SID0:1;
    unsigned SID1:1;
    unsigned SID2:1;
  };
  struct {
    unsigned :3;
    unsigned EXID:1;
  };
} TXB0SIDLbits;
extern volatile far  unsigned char       TXB0EIDH;
extern volatile far  struct {
  unsigned EID8:1;
  unsigned EID9:1;
  unsigned EID10:1;
  unsigned EID11:1;
  unsigned EID12:1;
  unsigned EID13:1;
  unsigned EID14:1;
  unsigned EID15:1;
} TXB0EIDHbits;
extern volatile far  unsigned char       TXB0EIDL;
extern volatile far  struct {
  unsigned EID0:1;
  unsigned EID1:1;
  unsigned EID2:1;
  unsigned EID3:1;
  unsigned EID4:1;
  unsigned EID5:1;
  unsigned EID6:1;
  unsigned EID7:1;
} TXB0EIDLbits;
extern volatile far  unsigned char       TXB0DLC;
extern volatile far  union {
  struct {
    unsigned DLC0:1;
    unsigned DLC1:1;
    unsigned DLC2:1;
    unsigned DLC3:1;
    unsigned :2;
    unsigned TXRTR:1;
  };
  struct {
    unsigned :6;
    unsigned RTR:1;
  };
} TXB0DLCbits;
extern volatile far  unsigned char       TXB0D0;
extern volatile far  struct {
  unsigned TXB0D00:1;
  unsigned TXB0D01:1;
  unsigned TXB0D02:1;
  unsigned TXB0D03:1;
  unsigned TXB0D04:1;
  unsigned TXB0D05:1;
  unsigned TXB0D06:1;
  unsigned TXB0D07:1;
} TXB0D0bits;
extern volatile far  unsigned char       TXB0D1;
extern volatile far  struct {
  unsigned TXB0D10:1;
  unsigned TXB0D11:1;
  unsigned TXB0D12:1;
  unsigned TXB0D13:1;
  unsigned TXB0D14:1;
  unsigned TXB0D15:1;
  unsigned TXB0D16:1;
  unsigned TXB0D17:1;
} TXB0D1bits;
extern volatile far  unsigned char       TXB0D2;
extern volatile far  struct {
  unsigned TXB0D20:1;
  unsigned TXB0D21:1;
  unsigned TXB0D22:1;
  unsigned TXB0D23:1;
  unsigned TXB0D24:1;
  unsigned TXB0D25:1;
  unsigned TXB0D26:1;
  unsigned TXB0D27:1;
} TXB0D2bits;
extern volatile far  unsigned char       TXB0D3;
extern volatile far  struct {
  unsigned TXB0D30:1;
  unsigned TXB0D31:1;
  unsigned TXB0D32:1;
  unsigned TXB0D33:1;
  unsigned TXB0D34:1;
  unsigned TXB0D35:1;
  unsigned TXB0D36:1;
  unsigned TXB0D37:1;
} TXB0D3bits;
extern volatile far  unsigned char       TXB0D4;
extern volatile far  struct {
  unsigned TXB0D40:1;
  unsigned TXB0D41:1;
  unsigned TXB0D42:1;
  unsigned TXB0D43:1;
  unsigned TXB0D44:1;
  unsigned TXB0D45:1;
  unsigned TXB0D46:1;
  unsigned TXB0D47:1;
} TXB0D4bits;
extern volatile far  unsigned char       TXB0D5;
extern volatile far  struct {
  unsigned TXB0D50:1;
  unsigned TXB0D51:1;
  unsigned TXB0D52:1;
  unsigned TXB0D53:1;
  unsigned TXB0D54:1;
  unsigned TXB0D55:1;
  unsigned TXB0D56:1;
  unsigned TXB0D57:1;
} TXB0D5bits;
extern volatile far  unsigned char       TXB0D6;
extern volatile far  struct {
  unsigned TXB0D60:1;
  unsigned TXB0D61:1;
  unsigned TXB0D62:1;
  unsigned TXB0D63:1;
  unsigned TXB0D64:1;
  unsigned TXB0D65:1;
  unsigned TXB0D66:1;
  unsigned TXB0D67:1;
} TXB0D6bits;
extern volatile far  unsigned char       TXB0D7;
extern volatile far  struct {
  unsigned TXB0D70:1;
  unsigned TXB0D71:1;
  unsigned TXB0D72:1;
  unsigned TXB0D73:1;
  unsigned TXB0D74:1;
  unsigned TXB0D75:1;
  unsigned TXB0D76:1;
  unsigned TXB0D77:1;
} TXB0D7bits;
extern volatile far  unsigned char       CANSTATRO1;
extern volatile far  struct {
  unsigned :1;
  unsigned ICODE0:1;
  unsigned ICODE1:1;
  unsigned ICODE2:1;
  unsigned :1;
  unsigned OPMODE0:1;
  unsigned OPMODE1:1;
  unsigned OPMODE2:1;
} CANSTATRO1bits;
extern volatile far  unsigned char       CANSTAT_R02;
extern volatile far  struct {
  unsigned :1;
  unsigned ICODE0:1;
  unsigned ICODE1:1;
  unsigned ICODE2:1;
  unsigned :1;
  unsigned OPMODE0:1;
  unsigned OPMODE1:1;
  unsigned OPMODE2:1;
} CANSTAT_R02bits;
extern volatile far  unsigned char       CANSTAT_RO2;
extern volatile far  struct {
  unsigned :1;
  unsigned ICODE0:1;
  unsigned ICODE1:1;
  unsigned ICODE2:1;
  unsigned :1;
  unsigned OPMODE0:1;
  unsigned OPMODE1:1;
  unsigned OPMODE2:1;
} CANSTAT_RO2bits;
extern volatile far  unsigned char       RXB1CON;
extern volatile far  struct {
  unsigned FILHIT0:1;
  unsigned FILHIT1:1;
  unsigned FILHIT2:1;
  unsigned RXRTRRO:1;
  unsigned :1;
  unsigned RXM0:1;
  unsigned RXM1:1;
  unsigned RXFUL:1;
} RXB1CONbits;
extern volatile far  unsigned char       RXB1SIDH;
extern volatile far  struct {
  unsigned SID3:1;
  unsigned SID4:1;
  unsigned SID5:1;
  unsigned SID6:1;
  unsigned SID7:1;
  unsigned SID8:1;
  unsigned SID9:1;
  unsigned SID10:1;
} RXB1SIDHbits;
extern volatile far  unsigned char       RXB1SIDL;
extern volatile far  struct {
  unsigned EID16:1;
  unsigned EID17:1;
  unsigned :1;
  unsigned EXID:1;
  unsigned SRR:1;
  unsigned SID0:1;
  unsigned SID1:1;
  unsigned SID2:1;
} RXB1SIDLbits;
extern volatile far  unsigned char       RXB1EIDH;
extern volatile far  struct {
  unsigned EID8:1;
  unsigned EID9:1;
  unsigned EID10:1;
  unsigned EID11:1;
  unsigned EID12:1;
  unsigned EID13:1;
  unsigned EID14:1;
  unsigned EID15:1;
} RXB1EIDHbits;
extern volatile far  unsigned char       RXB1EIDL;
extern volatile far  struct {
  unsigned EID0:1;
  unsigned EID1:1;
  unsigned EID2:1;
  unsigned EID3:1;
  unsigned EID4:1;
  unsigned EID5:1;
  unsigned EID6:1;
  unsigned EID7:1;
} RXB1EIDLbits;
extern volatile far  unsigned char       RXB1DLC;
extern volatile far  union {
  struct {
    unsigned DLC0:1;
    unsigned DLC1:1;
    unsigned DLC2:1;
    unsigned DLC3:1;
    unsigned RESB0:1;
    unsigned RESB1:1;
    unsigned RXRTR:1;
  };
  struct {
    unsigned :4;
    unsigned RESBO:1;
    unsigned :1;
    unsigned RTR:1;
  };
} RXB1DLCbits;
extern volatile far  unsigned char       RXB1D0;
extern volatile far  struct {
  unsigned RXB1D00:1;
  unsigned RXB1D01:1;
  unsigned RXB1D02:1;
  unsigned RXB1D03:1;
  unsigned RXB1D04:1;
  unsigned RXB1D05:1;
  unsigned RXB1D06:1;
  unsigned RXB1D07:1;
} RXB1D0bits;
extern volatile far  unsigned char       RXB1D1;
extern volatile far  struct {
  unsigned RXB1D10:1;
  unsigned RXB1D11:1;
  unsigned RXB1D12:1;
  unsigned RXB1D13:1;
  unsigned RXB1D14:1;
  unsigned RXB1D15:1;
  unsigned RXB1D16:1;
  unsigned RXB1D17:1;
} RXB1D1bits;
extern volatile far  unsigned char       RXB1D2;
extern volatile far  struct {
  unsigned RXB1D20:1;
  unsigned RXB1D21:1;
  unsigned RXB1D22:1;
  unsigned RXB1D23:1;
  unsigned RXB1D24:1;
  unsigned RXB1D25:1;
  unsigned RXB1D26:1;
  unsigned RXB1D27:1;
} RXB1D2bits;
extern volatile far  unsigned char       RXB1D3;
extern volatile far  struct {
  unsigned RXB1D30:1;
  unsigned RXB1D31:1;
  unsigned RXB1D32:1;
  unsigned RXB1D33:1;
  unsigned RXB1D34:1;
  unsigned RXB1D35:1;
  unsigned RXB1D36:1;
  unsigned RXB1D37:1;
} RXB1D3bits;
extern volatile far  unsigned char       RXB1D4;
extern volatile far  struct {
  unsigned RXB1D40:1;
  unsigned RXB1D41:1;
  unsigned RXB1D42:1;
  unsigned RXB1D43:1;
  unsigned RXB1D44:1;
  unsigned RXB1D45:1;
  unsigned RXB1D46:1;
  unsigned RXB1D47:1;
} RXB1D4bits;
extern volatile far  unsigned char       RXB1D5;
extern volatile far  struct {
  unsigned RXB1D50:1;
  unsigned RXB1D51:1;
  unsigned RXB1D52:1;
  unsigned RXB1D53:1;
  unsigned RXB1D54:1;
  unsigned RXB1D55:1;
  unsigned RXB1D56:1;
  unsigned RXB1D57:1;
} RXB1D5bits;
extern volatile far  unsigned char       RXB1D6;
extern volatile far  struct {
  unsigned RXB1D60:1;
  unsigned RXB1D61:1;
  unsigned RXB1D62:1;
  unsigned RXB1D63:1;
  unsigned RXB1D64:1;
  unsigned RXB1D65:1;
  unsigned RXB1D66:1;
  unsigned RXB1D67:1;
} RXB1D6bits;
extern volatile far  unsigned char       RXB1D7;
extern volatile far  struct {
  unsigned RXB1D70:1;
  unsigned RXB1D71:1;
  unsigned RXB1D72:1;
  unsigned RXB1D73:1;
  unsigned RXB1D74:1;
  unsigned RXB1D75:1;
  unsigned RXB1D76:1;
  unsigned RXB1D77:1;
} RXB1D7bits;
extern volatile far  unsigned char       CANSTATRO0;
extern volatile far  struct {
  unsigned :1;
  unsigned ICODE0:1;
  unsigned ICODE1:1;
  unsigned ICODE2:1;
  unsigned :1;
  unsigned OPMODE0:1;
  unsigned OPMODE1:1;
  unsigned OPMODE2:1;
} CANSTATRO0bits;
extern volatile far  unsigned char       CANSTAT_R01;
extern volatile far  struct {
  unsigned :1;
  unsigned ICODE0:1;
  unsigned ICODE1:1;
  unsigned ICODE2:1;
  unsigned :1;
  unsigned OPMODE0:1;
  unsigned OPMODE1:1;
  unsigned OPMODE2:1;
} CANSTAT_R01bits;
extern volatile far  unsigned char       CANSTAT_RO1;
extern volatile far  struct {
  unsigned :1;
  unsigned ICODE0:1;
  unsigned ICODE1:1;
  unsigned ICODE2:1;
  unsigned :1;
  unsigned OPMODE0:1;
  unsigned OPMODE1:1;
  unsigned OPMODE2:1;
} CANSTAT_RO1bits;
extern volatile near unsigned char       RXB0CON;
extern volatile near union {
  struct {
    unsigned FILHIT0:1;
    unsigned JTOFF:1;
    unsigned RXB0DBEN:1;
    unsigned RXRTRRO:1;
    unsigned :1;
    unsigned RXM0:1;
    unsigned RXM1:1;
    unsigned RXFUL:1;
  };
  struct {
    unsigned :2;
    unsigned RX0DBEN:1;
  };
} RXB0CONbits;
extern volatile near unsigned char       RXB0SIDH;
extern volatile near struct {
  unsigned SID3:1;
  unsigned SID4:1;
  unsigned SID5:1;
  unsigned SID6:1;
  unsigned SID7:1;
  unsigned SID8:1;
  unsigned SID9:1;
  unsigned SID10:1;
} RXB0SIDHbits;
extern volatile near unsigned char       RXB0SIDL;
extern volatile near struct {
  unsigned EID16:1;
  unsigned EID17:1;
  unsigned :1;
  unsigned EXID:1;
  unsigned SRR:1;
  unsigned SID0:1;
  unsigned SID1:1;
  unsigned SID2:1;
} RXB0SIDLbits;
extern volatile near unsigned char       RXB0EIDH;
extern volatile near struct {
  unsigned EID8:1;
  unsigned EID9:1;
  unsigned EID10:1;
  unsigned EID11:1;
  unsigned EID12:1;
  unsigned EID13:1;
  unsigned EID14:1;
  unsigned EID15:1;
} RXB0EIDHbits;
extern volatile near unsigned char       RXB0EIDL;
extern volatile near struct {
  unsigned EID0:1;
  unsigned EID1:1;
  unsigned EID2:1;
  unsigned EID3:1;
  unsigned EID4:1;
  unsigned EID5:1;
  unsigned EID6:1;
  unsigned EID7:1;
} RXB0EIDLbits;
extern volatile near unsigned char       RXB0DLC;
extern volatile near union {
  struct {
    unsigned DLC0:1;
    unsigned DLC1:1;
    unsigned DLC2:1;
    unsigned DLC3:1;
    unsigned RESB0:1;
    unsigned RESB1:1;
    unsigned RXRTR:1;
  };
  struct {
    unsigned :6;
    unsigned RTR:1;
  };
} RXB0DLCbits;
extern volatile near unsigned char       RXB0D0;
extern volatile near union {
  struct {
    unsigned RXB0D00:1;
    unsigned RXB0D01:1;
    unsigned RXB0D02:1;
    unsigned RXB0D03:1;
    unsigned RXB0D04:1;
    unsigned RXB0D05:1;
    unsigned RXB0D06:1;
    unsigned RXB0D07:1;
  };
  struct {
    unsigned RB0D00:1;
    unsigned RB0D01:1;
    unsigned RB0D02:1;
    unsigned RB0D03:1;
    unsigned RB0D04:1;
    unsigned RB0D05:1;
    unsigned RB0D06:1;
    unsigned RB0D07:1;
  };
} RXB0D0bits;
extern volatile near unsigned char       RXB0D1;
extern volatile near union {
  struct {
    unsigned RXB0D10:1;
    unsigned RXB0D11:1;
    unsigned RXB0D12:1;
    unsigned RXB0D13:1;
    unsigned RXB0D14:1;
    unsigned RXB0D15:1;
    unsigned RXB0D16:1;
    unsigned RXB0D17:1;
  };
  struct {
    unsigned RB0D10:1;
    unsigned RB0D11:1;
    unsigned RB0D12:1;
    unsigned RB0D13:1;
    unsigned RB0D14:1;
    unsigned RB0D15:1;
    unsigned RB0D16:1;
    unsigned RB0D17:1;
  };
} RXB0D1bits;
extern volatile near unsigned char       RXB0D2;
extern volatile near union {
  struct {
    unsigned RXB0D20:1;
    unsigned RXB0D21:1;
    unsigned RXB0D22:1;
    unsigned RXB0D23:1;
    unsigned RXB0D24:1;
    unsigned RXB0D25:1;
    unsigned RXB0D26:1;
    unsigned RXB0D27:1;
  };
  struct {
    unsigned RB0D20:1;
    unsigned RB0D21:1;
    unsigned RB0D22:1;
    unsigned RB0D23:1;
    unsigned RB0D24:1;
    unsigned RB0D25:1;
    unsigned RB0D26:1;
    unsigned RB0D27:1;
  };
} RXB0D2bits;
extern volatile near unsigned char       RXB0D3;
extern volatile near union {
  struct {
    unsigned RXB0D30:1;
    unsigned RXB0D31:1;
    unsigned RXB0D32:1;
    unsigned RXB0D33:1;
    unsigned RXB0D34:1;
    unsigned RXB0D35:1;
    unsigned RXB0D36:1;
    unsigned RXB0D37:1;
  };
  struct {
    unsigned RB0D30:1;
    unsigned RB0D31:1;
    unsigned RB0D32:1;
    unsigned RB0D33:1;
    unsigned RB0D34:1;
    unsigned RB0D35:1;
    unsigned RB0D36:1;
    unsigned RB0D37:1;
  };
} RXB0D3bits;
extern volatile near unsigned char       RXB0D4;
extern volatile near union {
  struct {
    unsigned RXB0D40:1;
    unsigned RXB0D41:1;
    unsigned RXB0D42:1;
    unsigned RXB0D43:1;
    unsigned RXB0D44:1;
    unsigned RXB0D45:1;
    unsigned RXB0D46:1;
    unsigned RXB0D47:1;
  };
  struct {
    unsigned RB0D40:1;
    unsigned RB0D41:1;
    unsigned RB0D42:1;
    unsigned RB0D43:1;
    unsigned RB0D44:1;
    unsigned RB0D45:1;
    unsigned RB0D46:1;
    unsigned RB0D47:1;
  };
} RXB0D4bits;
extern volatile near unsigned char       RXB0D5;
extern volatile near union {
  struct {
    unsigned RXB0D50:1;
    unsigned RXB0D51:1;
    unsigned RXB0D52:1;
    unsigned RXB0D53:1;
    unsigned RXB0D54:1;
    unsigned RXB0D55:1;
    unsigned RXB0D56:1;
    unsigned RXB0D57:1;
  };
  struct {
    unsigned RB0D50:1;
    unsigned RB0D51:1;
    unsigned RB0D52:1;
    unsigned RB0D53:1;
    unsigned RB0D54:1;
    unsigned RB0D55:1;
    unsigned RB0D56:1;
    unsigned RB0D57:1;
  };
} RXB0D5bits;
extern volatile near unsigned char       RXB0D6;
extern volatile near union {
  struct {
    unsigned RXB0D60:1;
    unsigned RXB0D61:1;
    unsigned RXB0D62:1;
    unsigned RXB0D63:1;
    unsigned RXB0D64:1;
    unsigned RXB0D65:1;
    unsigned RXB0D66:1;
    unsigned RXB0D67:1;
  };
  struct {
    unsigned RB0D60:1;
    unsigned RB0D61:1;
    unsigned RB0D62:1;
    unsigned RB0D63:1;
    unsigned RB0D64:1;
    unsigned RB0D65:1;
    unsigned RB0D66:1;
    unsigned RB0D67:1;
  };
} RXB0D6bits;
extern volatile near unsigned char       RXB0D7;
extern volatile near union {
  struct {
    unsigned RXB0D70:1;
    unsigned RXB0D71:1;
    unsigned RXB0D72:1;
    unsigned RXB0D73:1;
    unsigned RXB0D74:1;
    unsigned RXB0D75:1;
    unsigned RXB0D76:1;
    unsigned RXB0D77:1;
  };
  struct {
    unsigned RB0D70:1;
    unsigned RB0D71:1;
    unsigned RB0D72:1;
    unsigned RB0D73:1;
    unsigned RB0D74:1;
    unsigned RB0D75:1;
    unsigned RB0D76:1;
    unsigned RB0D77:1;
  };
} RXB0D7bits;
extern volatile near unsigned char       CANSTAT;
extern volatile near union {
  struct {
    unsigned :1;
    unsigned ICODE0:1;
    unsigned ICODE1:1;
    unsigned ICODE2:1;
    unsigned :1;
    unsigned OPMODE0:1;
    unsigned OPMODE1:1;
    unsigned OPMODE2:1;
  };
  struct {
    unsigned :1;
    unsigned ICOED0:1;
  };
} CANSTATbits;
extern volatile near unsigned char       CANCON;
extern volatile near struct {
  unsigned :1;
  unsigned WIN0:1;
  unsigned WIN1:1;
  unsigned WIN2:1;
  unsigned ABAT:1;
  unsigned REQOP0:1;
  unsigned REQOP1:1;
  unsigned REQOP2:1;
} CANCONbits;
extern volatile near unsigned char       BRGCON1;
extern volatile near struct {
  unsigned BRP0:1;
  unsigned BRP1:1;
  unsigned BRP2:1;
  unsigned BRP3:1;
  unsigned BRP4:1;
  unsigned BRP5:1;
  unsigned SJW0:1;
  unsigned SJW1:1;
} BRGCON1bits;
extern volatile near unsigned char       BRGCON2;
extern volatile near struct {
  unsigned PRSEG0:1;
  unsigned PRSEG1:1;
  unsigned PRSEG2:1;
  unsigned SEG1PH0:1;
  unsigned SEG1PH1:1;
  unsigned SEG1PH2:1;
  unsigned SAM:1;
  unsigned SEG2PHTS:1;
} BRGCON2bits;
extern volatile near unsigned char       BRGCON3;
extern volatile near struct {
  unsigned SEG2PH0:1;
  unsigned SEG2PH1:1;
  unsigned SEG2PH2:1;
  unsigned :3;
  unsigned WAKFIL:1;
} BRGCON3bits;
extern volatile near unsigned char       CIOCON;
extern volatile near struct {
  unsigned :4;
  unsigned CANCAP:1;
  unsigned ENDRHI:1;
  unsigned TX1EN:1;
  unsigned TX1SRC:1;
} CIOCONbits;
extern volatile near unsigned char       COMSTAT;
extern volatile near union {
  struct {
    unsigned EWARN:1;
    unsigned RXWARN:1;
    unsigned TXWARN:1;
    unsigned RXBP:1;
    unsigned TXBP:1;
    unsigned TXBO:1;
    unsigned RXB1OVFL:1;
    unsigned RXB0OVFL:1;
  };
  struct {
    unsigned :6;
    unsigned RX1OVFL:1;
    unsigned RX0OVFL:1;
  };
} COMSTATbits;
extern volatile near unsigned char       RXERRCNT;
extern volatile near struct {
  unsigned REC0:1;
  unsigned REC1:1;
  unsigned REC2:1;
  unsigned REC3:1;
  unsigned REC4:1;
  unsigned REC5:1;
  unsigned REC6:1;
  unsigned REC7:1;
} RXERRCNTbits;
extern volatile near unsigned char       TXERRCNT;
extern volatile near struct {
  unsigned TEC0:1;
  unsigned TEC1:1;
  unsigned TEC2:1;
  unsigned TEC3:1;
  unsigned TEC4:1;
  unsigned TEC5:1;
  unsigned TEC6:1;
  unsigned TEC7:1;
} TXERRCNTbits;
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
  };
  struct {
    unsigned AN0:1;
    unsigned AN1:1;
    unsigned AN2:1;
    unsigned AN3:1;
    unsigned :1;
    unsigned AN4:1;
    unsigned OSC2:1;
  };
  struct {
    unsigned :2;
    unsigned VREFM:1;
    unsigned VREFP:1;
    unsigned T0CKI:1;
    unsigned SS:1;
    unsigned CLK0:1;
  };
  struct {
    unsigned :5;
    unsigned LVDIN:1;
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
    unsigned INT0:1;
    unsigned INT1:1;
    unsigned INT2:1;
    unsigned INT3:1;
  };
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
    unsigned :1;
    unsigned SCK:1;
    unsigned SDI:1;
    unsigned SDO:1;
    unsigned TX:1;
    unsigned RX:1;
  };
  struct {
    unsigned T1CKI:1;
    unsigned :1;
    unsigned CCP1:1;
    unsigned SCL:1;
    unsigned SDA:1;
    unsigned :1;
    unsigned CK:1;
    unsigned DT:1;
  };
} PORTCbits;
extern volatile near unsigned char       PORTD;
extern volatile near union {
  struct {
    unsigned RD0:1;
    unsigned RD1:1;
    unsigned RD2:1;
    unsigned RD3:1;
    unsigned RD4:1;
    unsigned RD5:1;
    unsigned RD6:1;
    unsigned RD7:1;
  };
  struct {
    unsigned PSP0:1;
    unsigned PSP1:1;
    unsigned PSP2:1;
    unsigned PSP3:1;
    unsigned PSP4:1;
    unsigned PSP5:1;
    unsigned PSP6:1;
    unsigned PSP7:1;
  };
} PORTDbits;
extern volatile near unsigned char       PORTE;
extern volatile near union {
  struct {
    unsigned RE0:1;
    unsigned RE1:1;
    unsigned RE2:1;
    unsigned RE3:1;
    unsigned RE4:1;
    unsigned RE5:1;
    unsigned RE6:1;
    unsigned RE7:1;
  };
  struct {
    unsigned NOT_RD:1;
    unsigned NOT_WR:1;
    unsigned NOT_CS:1;
    unsigned :4;
    unsigned CCP2:1;
  };
  struct {
    unsigned AN5:1;
  };
} PORTEbits;
extern volatile near unsigned char       PORTF;
extern volatile near union {
  struct {
    unsigned RF0:1;
    unsigned RF1:1;
    unsigned RF2:1;
    unsigned RF3:1;
    unsigned RF4:1;
    unsigned RF5:1;
    unsigned RF6:1;
    unsigned RF7:1;
  };
  struct {
    unsigned AN5:1;
    unsigned AN6:1;
    unsigned AN7:1;
    unsigned AN8:1;
    unsigned AN9:1;
    unsigned AN10:1;
    unsigned AN11:1;
  };
} PORTFbits;
extern volatile near unsigned char       PORTG;
extern volatile near union {
  struct {
    unsigned RG0:1;
    unsigned RG1:1;
    unsigned RG2:1;
    unsigned RG3:1;
    unsigned RG4:1;
  };
  struct {
    unsigned CANTX1:1;
    unsigned CANTX2:1;
    unsigned CANRX:1;
  };
} PORTGbits;
extern volatile near unsigned char       LATA;
extern volatile near struct {
  unsigned LATA0:1;
  unsigned LATA1:1;
  unsigned LATA2:1;
  unsigned LATA3:1;
  unsigned LATA4:1;
  unsigned LATA5:1;
  unsigned LATA6:1;
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
extern volatile near unsigned char       LATD;
extern volatile near struct {
  unsigned LATD0:1;
  unsigned LATD1:1;
  unsigned LATD2:1;
  unsigned LATD3:1;
  unsigned LATD4:1;
  unsigned LATD5:1;
  unsigned LATD6:1;
  unsigned LATD7:1;
} LATDbits;
extern volatile near unsigned char       LATE;
extern volatile near struct {
  unsigned LATE0:1;
  unsigned LATE1:1;
  unsigned LATE2:1;
  unsigned LATE3:1;
  unsigned LATE4:1;
  unsigned LATE5:1;
  unsigned LATE6:1;
  unsigned LATE7:1;
} LATEbits;
extern volatile near unsigned char       LATF;
extern volatile near struct {
  unsigned LATF0:1;
  unsigned LATF1:1;
  unsigned LATF2:1;
  unsigned LATF3:1;
  unsigned LATF4:1;
  unsigned LATF5:1;
  unsigned LATF6:1;
  unsigned LATF7:1;
} LATFbits;
extern volatile near unsigned char       LATG;
extern volatile near struct {
  unsigned LATG0:1;
  unsigned LATG1:1;
  unsigned LATG2:1;
  unsigned LATG3:1;
  unsigned LATG4:1;
} LATGbits;
extern volatile near unsigned char       DDRA;
extern volatile near union {
  struct {
    unsigned TRISA0:1;
    unsigned TRISA1:1;
    unsigned TRISA2:1;
    unsigned TRISA3:1;
    unsigned TRISA4:1;
    unsigned TRISA5:1;
    unsigned TRISA6:1;
  };
  struct {
    unsigned RA0:1;
    unsigned RA1:1;
    unsigned RA2:1;
    unsigned RA3:1;
    unsigned RA4:1;
    unsigned RA5:1;
    unsigned RA6:1;
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
    unsigned TRISA5:1;
    unsigned TRISA6:1;
  };
  struct {
    unsigned RA0:1;
    unsigned RA1:1;
    unsigned RA2:1;
    unsigned RA3:1;
    unsigned RA4:1;
    unsigned RA5:1;
    unsigned RA6:1;
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
extern volatile near unsigned char       DDRD;
extern volatile near union {
  struct {
    unsigned TRISD0:1;
    unsigned TRISD1:1;
    unsigned TRISD2:1;
    unsigned TRISD3:1;
    unsigned TRISD4:1;
    unsigned TRISD5:1;
    unsigned TRISD6:1;
    unsigned TRISD7:1;
  };
  struct {
    unsigned RD0:1;
    unsigned RD1:1;
    unsigned RD2:1;
    unsigned RD3:1;
    unsigned RD4:1;
    unsigned RD5:1;
    unsigned RD6:1;
    unsigned RD7:1;
  };
} DDRDbits;
extern volatile near unsigned char       TRISD;
extern volatile near union {
  struct {
    unsigned TRISD0:1;
    unsigned TRISD1:1;
    unsigned TRISD2:1;
    unsigned TRISD3:1;
    unsigned TRISD4:1;
    unsigned TRISD5:1;
    unsigned TRISD6:1;
    unsigned TRISD7:1;
  };
  struct {
    unsigned RD0:1;
    unsigned RD1:1;
    unsigned RD2:1;
    unsigned RD3:1;
    unsigned RD4:1;
    unsigned RD5:1;
    unsigned RD6:1;
    unsigned RD7:1;
  };
} TRISDbits;
extern volatile near unsigned char       DDRE;
extern volatile near union {
  struct {
    unsigned TRISE0:1;
    unsigned TRISE1:1;
    unsigned TRISE2:1;
    unsigned TRISE3:1;
    unsigned TRISE4:1;
    unsigned TRISE5:1;
    unsigned TRISE6:1;
    unsigned TRISE7:1;
  };
  struct {
    unsigned RE0:1;
    unsigned RE1:1;
    unsigned RE2:1;
    unsigned RE3:1;
    unsigned RE4:1;
    unsigned RE5:1;
    unsigned RE6:1;
    unsigned RE7:1;
  };
} DDREbits;
extern volatile near unsigned char       TRISE;
extern volatile near union {
  struct {
    unsigned TRISE0:1;
    unsigned TRISE1:1;
    unsigned TRISE2:1;
    unsigned TRISE3:1;
    unsigned TRISE4:1;
    unsigned TRISE5:1;
    unsigned TRISE6:1;
    unsigned TRISE7:1;
  };
  struct {
    unsigned RE0:1;
    unsigned RE1:1;
    unsigned RE2:1;
    unsigned RE3:1;
    unsigned RE4:1;
    unsigned RE5:1;
    unsigned RE6:1;
    unsigned RE7:1;
  };
} TRISEbits;
extern volatile near unsigned char       DDRF;
extern volatile near union {
  struct {
    unsigned TRISF0:1;
    unsigned TRISF1:1;
    unsigned TRISF2:1;
    unsigned TRISF3:1;
    unsigned TRISF4:1;
    unsigned TRISF5:1;
    unsigned TRISF6:1;
    unsigned TRISF7:1;
  };
  struct {
    unsigned RF0:1;
    unsigned RF1:1;
    unsigned RF2:1;
    unsigned RF3:1;
    unsigned RF4:1;
    unsigned RF5:1;
    unsigned RF6:1;
    unsigned RF7:1;
  };
} DDRFbits;
extern volatile near unsigned char       TRISF;
extern volatile near union {
  struct {
    unsigned TRISF0:1;
    unsigned TRISF1:1;
    unsigned TRISF2:1;
    unsigned TRISF3:1;
    unsigned TRISF4:1;
    unsigned TRISF5:1;
    unsigned TRISF6:1;
    unsigned TRISF7:1;
  };
  struct {
    unsigned RF0:1;
    unsigned RF1:1;
    unsigned RF2:1;
    unsigned RF3:1;
    unsigned RF4:1;
    unsigned RF5:1;
    unsigned RF6:1;
    unsigned RF7:1;
  };
} TRISFbits;
extern volatile near unsigned char       DDRG;
extern volatile near union {
  struct {
    unsigned TRISG0:1;
    unsigned TRISG1:1;
    unsigned TRISG2:1;
    unsigned TRISG3:1;
    unsigned TRISG4:1;
  };
  struct {
    unsigned RG0:1;
    unsigned RG1:1;
    unsigned RG2:1;
    unsigned RG3:1;
    unsigned RG4:1;
  };
} DDRGbits;
extern volatile near unsigned char       TRISG;
extern volatile near union {
  struct {
    unsigned TRISG0:1;
    unsigned TRISG1:1;
    unsigned TRISG2:1;
    unsigned TRISG3:1;
    unsigned TRISG4:1;
  };
  struct {
    unsigned RG0:1;
    unsigned RG1:1;
    unsigned RG2:1;
    unsigned RG3:1;
    unsigned RG4:1;
  };
} TRISGbits;
extern volatile near unsigned char       PIE1;
extern volatile near struct {
  unsigned TMR1IE:1;
  unsigned TMR2IE:1;
  unsigned CCP1IE:1;
  unsigned SSPIE:1;
  unsigned TXIE:1;
  unsigned RCIE:1;
  unsigned ADIE:1;
  unsigned PSPIE:1;
} PIE1bits;
extern volatile near unsigned char       PIR1;
extern volatile near struct {
  unsigned TMR1IF:1;
  unsigned TMR2IF:1;
  unsigned CCP1IF:1;
  unsigned SSPIF:1;
  unsigned TXIF:1;
  unsigned RCIF:1;
  unsigned ADIF:1;
  unsigned PSPIF:1;
} PIR1bits;
extern volatile near unsigned char       IPR1;
extern volatile near struct {
  unsigned TMR1IP:1;
  unsigned TMR2IP:1;
  unsigned CCP1IP:1;
  unsigned SSPIP:1;
  unsigned TXIP:1;
  unsigned RCIP:1;
  unsigned ADIP:1;
  unsigned PSPIP:1;
} IPR1bits;
extern volatile near unsigned char       PIE2;
extern volatile near struct {
  unsigned CCP2IE:1;
  unsigned TMR3IE:1;
  unsigned LVDIE:1;
  unsigned BCLIE:1;
  unsigned :2;
  unsigned CMIE:1;
} PIE2bits;
extern volatile near unsigned char       PIR2;
extern volatile near struct {
  unsigned CCP2IF:1;
  unsigned TMR3IF:1;
  unsigned LVDIF:1;
  unsigned BCLIF:1;
  unsigned :2;
  unsigned CMIF:1;
} PIR2bits;
extern volatile near unsigned char       IPR2;
extern volatile near struct {
  unsigned CCP2IP:1;
  unsigned TMR3IP:1;
  unsigned LVDIP:1;
  unsigned BCLIP:1;
  unsigned :2;
  unsigned CMIP:1;
} IPR2bits;
extern volatile near unsigned char       PIE3;
extern volatile near struct {
  unsigned RXB0IE:1;
  unsigned RXB1IE:1;
  unsigned TXB0IE:1;
  unsigned TXB1IE:1;
  unsigned TXB2IE:1;
  unsigned ERRIE:1;
  unsigned WAKIE:1;
  unsigned IRXIE:1;
} PIE3bits;
extern volatile near unsigned char       PIR3;
extern volatile near struct {
  unsigned RXB0IF:1;
  unsigned RXB1IF:1;
  unsigned TXB0IF:1;
  unsigned TXB1IF:1;
  unsigned TXB2IF:1;
  unsigned ERRIF:1;
  unsigned WAKIF:1;
  unsigned IRXIF:1;
} PIR3bits;
extern volatile near unsigned char       IPR3;
extern volatile near struct {
  unsigned RXB0IP:1;
  unsigned RXB1IP:1;
  unsigned TXB0IP:1;
  unsigned TXB1IP:1;
  unsigned TXB2IP:1;
  unsigned ERRIP:1;
  unsigned WAKIP:1;
  unsigned IRXIP:1;
} IPR3bits;
extern volatile near unsigned char       COM1CON;
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
} COM1CONbits;
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
extern volatile near unsigned char       COM1STA;
extern volatile near struct {
  unsigned TX9D:1;
  unsigned TRMT:1;
  unsigned BRGH:1;
  unsigned :1;
  unsigned SYNC:1;
  unsigned TXEN:1;
  unsigned TX9:1;
  unsigned CSRC:1;
} COM1STAbits;
extern volatile near unsigned char       TXSTA;
extern volatile near struct {
  unsigned TX9D:1;
  unsigned TRMT:1;
  unsigned BRGH:1;
  unsigned :1;
  unsigned SYNC:1;
  unsigned TXEN:1;
  unsigned TX9:1;
  unsigned CSRC:1;
} TXSTAbits;
extern volatile near unsigned char       COM1TX;
extern volatile near unsigned char       TXREG;
extern volatile near unsigned char       COM1REC;
extern volatile near unsigned char       RCREG;
extern volatile near unsigned char       COM1BRG;
extern volatile near unsigned char       SPBRG;
extern volatile near unsigned char       PSPCON;
extern volatile near struct {
  unsigned :4;
  unsigned PSPMODE:1;
  unsigned IBOV:1;
  unsigned OBF:1;
  unsigned IBF:1;
} PSPCONbits;
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
  struct {
    unsigned :2;
    unsigned T3INSYNC:1;
  };
} T3CONbits;
extern volatile near unsigned char       TMR3L;
extern volatile near unsigned char       TMR3H;
extern volatile near unsigned char       CMCON;
extern volatile near union {
  struct {
    unsigned CM:3;
    unsigned CIS:1;
    unsigned C1INV:1;
    unsigned C2INV:1;
    unsigned C1OUT:1;
    unsigned C2OUT:1;
  };
  struct {
    unsigned CM0:1;
    unsigned CM1:1;
    unsigned CM2:1;
  };
} CMCONbits;
extern volatile near unsigned char       CVRCON;
extern volatile near union {
  struct {
    unsigned VR:4;
    unsigned VRSS:1;
    unsigned VRR:1;
    unsigned VROEN:1;
    unsigned VREN:1;
  };
  struct {
    unsigned VR0:1;
    unsigned VR1:1;
    unsigned VR2:1;
    unsigned VR3:1;
    unsigned :1;
    unsigned CRR:1;
    unsigned VROE:1;
  };
} CVRCONbits;
extern volatile near unsigned char       VRCON;
extern volatile near union {
  struct {
    unsigned VR:4;
    unsigned VRSS:1;
    unsigned VRR:1;
    unsigned VROEN:1;
    unsigned VREN:1;
  };
  struct {
    unsigned VR0:1;
    unsigned VR1:1;
    unsigned VR2:1;
    unsigned VR3:1;
    unsigned :1;
    unsigned CRR:1;
    unsigned VROE:1;
  };
} VRCONbits;
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
    unsigned DC2B0:1;
    unsigned DC2B1:1;
  };
  struct {
    unsigned :3;
    unsigned CCPM3:1;
    unsigned CCP2Y:1;
    unsigned CCP2X:1;
  };
  struct {
    unsigned :5;
    unsigned DCCPX:1;
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
    unsigned DC1B0:1;
    unsigned DC1B1:1;
  };
  struct {
    unsigned :3;
    unsigned CCPM3:1;
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
    unsigned :4;
    unsigned ADFM:1;
  };
  struct {
    unsigned ADCS0:1;
    unsigned ADCS1:1;
    unsigned ADCS2:1;
  };
} ADCON2bits;
extern volatile near unsigned char       ADCON1;
extern volatile near union {
  struct {
    unsigned PCFG:4;
    unsigned VCFG:2;
  };
  struct {
    unsigned PCFG0:1;
    unsigned PCFG1:1;
    unsigned PCFG2:1;
    unsigned PCFG3:1;
    unsigned VCFG0:1;
    unsigned VCFG1:1;
  };
} ADCON1bits;
extern volatile near unsigned char       ADCON0;
extern volatile near union {
  struct {
    unsigned ADON:1;
    unsigned GO_NOT_DONE:1;
    unsigned CHS:4;
  };
  struct {
    unsigned :1;
    unsigned GO:1;
    unsigned CHS0:1;
    unsigned CHS1:1;
    unsigned CHS2:1;
    unsigned CHS3:1;
  };
  struct {
    unsigned :1;
    unsigned NOT_DONE:1;
  };
  struct {
    unsigned :1;
    unsigned DONE:1;
  };
  struct {
    unsigned :1;
    unsigned GO_DONE:1;
  };
} ADCON0bits;
extern volatile near unsigned            ADRES;
extern volatile near unsigned char       ADRESL;
extern volatile near unsigned char       ADRESH;
extern volatile near unsigned char       SSPCON2;
extern volatile near struct {
  unsigned SEN:1;
  unsigned RSEN:1;
  unsigned PEN:1;
  unsigned RCEN:1;
  unsigned ACKEN:1;
  unsigned ACKDT:1;
  unsigned ACKSTAT:1;
  unsigned GCEN:1;
} SSPCON2bits;
extern volatile near unsigned char       SSPCON1;
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
} SSPCON1bits;
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
    unsigned I2C_READ:1;
    unsigned I2C_START:1;
    unsigned I2C_STOP:1;
    unsigned I2C_DATA:1;
  };
  struct {
    unsigned :2;
    unsigned R:1;
    unsigned :2;
    unsigned D:1;
  };
  struct {
    unsigned :2;
    unsigned READ_WRITE:1;
    unsigned :2;
    unsigned DATA_ADDRESS:1;
  };
  struct {
    unsigned :2;
    unsigned NOT_WRITE:1;
    unsigned :2;
    unsigned NOT_ADDRESS:1;
  };
  struct {
    unsigned :2;
    unsigned NOT_W:1;
    unsigned :2;
    unsigned NOT_A:1;
  };
  struct {
    unsigned :2;
    unsigned R_W:1;
    unsigned :2;
    unsigned D_A:1;
  };
  struct {
    unsigned :5;
    unsigned I2C_DAT:1;
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
    unsigned :1;
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
    unsigned :1;
    unsigned LWRT:1;
    unsigned IPEN:1;
  };
  struct {
    unsigned BOR:1;
    unsigned POR:1;
    unsigned PD:1;
    unsigned TO:1;
    unsigned RI:1;
    unsigned :1;
    unsigned NOT_LWRT:1;
    unsigned NOT_IPEN:1;
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
  };
} LVDCONbits;
extern volatile near unsigned char       OSCCON;
extern volatile near struct {
  unsigned SCS:1;
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
    unsigned T0PS3:1;
    unsigned :2;
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
    unsigned INT3IF:1;
    unsigned INT1IE:1;
    unsigned INT2IE:1;
    unsigned INT3IE:1;
    unsigned INT1IP:1;
    unsigned INT2IP:1;
  };
  struct {
    unsigned INT1F:1;
    unsigned INT2F:1;
    unsigned INT3F:1;
    unsigned INT1E:1;
    unsigned INT2E:1;
    unsigned INT3E:1;
    unsigned INT1P:1;
    unsigned INT2P:1;
  };
} INTCON3bits;
extern volatile near unsigned char       INTCON2;
extern volatile near union {
  struct {
    unsigned RBIP:1;
    unsigned INT3IP:1;
    unsigned TMR0IP:1;
    unsigned INTEDG3:1;
    unsigned INTEDG2:1;
    unsigned INTEDG1:1;
    unsigned INTEDG0:1;
    unsigned NOT_RBPU:1;
  };
  struct {
    unsigned :1;
    unsigned INT3P:1;
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
extern volatile near unsigned char       INTCON1;
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
} INTCON1bits;
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

#pragma varlocate 15 RXF0SIDH
#pragma varlocate 15 RXF0SIDHbits
#pragma varlocate 15 RXF0SIDL
#pragma varlocate 15 RXF0SIDLbits
#pragma varlocate 15 RXF0EIDH
#pragma varlocate 15 RXF0EIDHbits
#pragma varlocate 15 RXF0EIDL
#pragma varlocate 15 RXF0EIDLbits
#pragma varlocate 15 RXF1SIDH
#pragma varlocate 15 RXF1SIDHbits
#pragma varlocate 15 RXF1SIDL
#pragma varlocate 15 RXF1SIDLbits
#pragma varlocate 15 RXF1EID8
#pragma varlocate 15 RXF1EID8bits
#pragma varlocate 15 RXF1EIDH
#pragma varlocate 15 RXF1EIDHbits
#pragma varlocate 15 RXF1EID0
#pragma varlocate 15 RXF1EID0bits
#pragma varlocate 15 RXF1EIDL
#pragma varlocate 15 RXF1EIDLbits
#pragma varlocate 15 RXF2SIDH
#pragma varlocate 15 RXF2SIDHbits
#pragma varlocate 15 RXF2SIDL
#pragma varlocate 15 RXF2SIDLbits
#pragma varlocate 15 RXF2EID8
#pragma varlocate 15 RXF2EID8bits
#pragma varlocate 15 RXF2EIDH
#pragma varlocate 15 RXF2EIDHbits
#pragma varlocate 15 RXF2EID0
#pragma varlocate 15 RXF2EID0bits
#pragma varlocate 15 RXF2EIDL
#pragma varlocate 15 RXF2EIDLbits
#pragma varlocate 15 RXF3SIDH
#pragma varlocate 15 RXF3SIDHbits
#pragma varlocate 15 RXF3SIDL
#pragma varlocate 15 RXF3SIDLbits
#pragma varlocate 15 RXF3EID8
#pragma varlocate 15 RXF3EID8bits
#pragma varlocate 15 RXF3EIDH
#pragma varlocate 15 RXF3EIDHbits
#pragma varlocate 15 RXF3EID0
#pragma varlocate 15 RXF3EID0bits
#pragma varlocate 15 RXF3EIDL
#pragma varlocate 15 RXF3EIDLbits
#pragma varlocate 15 RXF4SIDH
#pragma varlocate 15 RXF4SIDHbits
#pragma varlocate 15 RXF4SIDL
#pragma varlocate 15 RXF4SIDLbits
#pragma varlocate 15 RXF4EID8
#pragma varlocate 15 RXF4EID8bits
#pragma varlocate 15 RXF4EIDH
#pragma varlocate 15 RXF4EIDHbits
#pragma varlocate 15 RXF4EID0
#pragma varlocate 15 RXF4EID0bits
#pragma varlocate 15 RXF4EIDL
#pragma varlocate 15 RXF4EIDLbits
#pragma varlocate 15 RXF5SIDH
#pragma varlocate 15 RXF5SIDHbits
#pragma varlocate 15 RXF5SIDL
#pragma varlocate 15 RXF5SIDLbits
#pragma varlocate 15 RXF5EID8
#pragma varlocate 15 RXF5EID8bits
#pragma varlocate 15 RXF5EIDH
#pragma varlocate 15 RXF5EIDHbits
#pragma varlocate 15 RXF5EID0
#pragma varlocate 15 RXF5EID0bits
#pragma varlocate 15 RXF5EIDL
#pragma varlocate 15 RXF5EIDLbits
#pragma varlocate 15 RXM0SIDH
#pragma varlocate 15 RXM0SIDHbits
#pragma varlocate 15 RXM0SIDL
#pragma varlocate 15 RXM0SIDLbits
#pragma varlocate 15 RXM0EID8
#pragma varlocate 15 RXM0EID8bits
#pragma varlocate 15 RXM0EIDH
#pragma varlocate 15 RXM0EIDHbits
#pragma varlocate 15 RXM0EID0
#pragma varlocate 15 RXM0EID0bits
#pragma varlocate 15 RXM0EIDL
#pragma varlocate 15 RXM0EIDLbits
#pragma varlocate 15 RXM1SIDH
#pragma varlocate 15 RXM1SIDHbits
#pragma varlocate 15 RXM1SIDL
#pragma varlocate 15 RXM1SIDLbits
#pragma varlocate 15 RXM1EID8
#pragma varlocate 15 RXM1EID8bits
#pragma varlocate 15 RXM1EIDH
#pragma varlocate 15 RXM1EIDHbits
#pragma varlocate 15 RXM1EID0
#pragma varlocate 15 RXM1EID0bits
#pragma varlocate 15 RXM1EIDL
#pragma varlocate 15 RXM1EIDLbits
#pragma varlocate 15 TXB2CON
#pragma varlocate 15 TXB2CONbits
#pragma varlocate 15 TXB2SIDH
#pragma varlocate 15 TXB2SIDHbits
#pragma varlocate 15 TXB2SIDL
#pragma varlocate 15 TXB2SIDLbits
#pragma varlocate 15 TXB2EIDH
#pragma varlocate 15 TXB2EIDHbits
#pragma varlocate 15 TXB2EIDL
#pragma varlocate 15 TXB2EIDLbits
#pragma varlocate 15 TXB2DLC
#pragma varlocate 15 TXB2DLCbits
#pragma varlocate 15 TXB2D0
#pragma varlocate 15 TXB2D0bits
#pragma varlocate 15 TXB2D1
#pragma varlocate 15 TXB2D1bits
#pragma varlocate 15 TXB2D2
#pragma varlocate 15 TXB2D2bits
#pragma varlocate 15 TXB2D3
#pragma varlocate 15 TXB2D3bits
#pragma varlocate 15 TXB2D4
#pragma varlocate 15 TXB2D4bits
#pragma varlocate 15 TXB2D5
#pragma varlocate 15 TXB2D5bits
#pragma varlocate 15 TXB2D6
#pragma varlocate 15 TXB2D6bits
#pragma varlocate 15 TXB2D7
#pragma varlocate 15 TXB2D7bits
#pragma varlocate 15 CANSTATRO3
#pragma varlocate 15 CANSTATRO3bits
#pragma varlocate 15 CANSTAT_R04
#pragma varlocate 15 CANSTAT_R04bits
#pragma varlocate 15 CANSTAT_RO4
#pragma varlocate 15 CANSTAT_RO4bits
#pragma varlocate 15 TXB1CON
#pragma varlocate 15 TXB1CONbits
#pragma varlocate 15 TXB1SIDH
#pragma varlocate 15 TXB1SIDHbits
#pragma varlocate 15 TXB1SIDL
#pragma varlocate 15 TXB1SIDLbits
#pragma varlocate 15 TXB1EIDH
#pragma varlocate 15 TXB1EIDHbits
#pragma varlocate 15 TXB1EIDL
#pragma varlocate 15 TXB1EIDLbits
#pragma varlocate 15 TXB1DLC
#pragma varlocate 15 TXB1DLCbits
#pragma varlocate 15 TXB1D0
#pragma varlocate 15 TXB1D0bits
#pragma varlocate 15 TXB1D1
#pragma varlocate 15 TXB1D1bits
#pragma varlocate 15 TXB1D2
#pragma varlocate 15 TXB1D2bits
#pragma varlocate 15 TXB1D3
#pragma varlocate 15 TXB1D3bits
#pragma varlocate 15 TXB1D4
#pragma varlocate 15 TXB1D4bits
#pragma varlocate 15 TXB1D5
#pragma varlocate 15 TXB1D5bits
#pragma varlocate 15 TXB1D6
#pragma varlocate 15 TXB1D6bits
#pragma varlocate 15 TXB1D7
#pragma varlocate 15 TXB1D7bits
#pragma varlocate 15 CANSTATRO2
#pragma varlocate 15 CANSTATRO2bits
#pragma varlocate 15 CANSTAT_R03
#pragma varlocate 15 CANSTAT_R03bits
#pragma varlocate 15 CANSTAT_RO3
#pragma varlocate 15 CANSTAT_RO3bits
#pragma varlocate 15 TXB0CON
#pragma varlocate 15 TXB0CONbits
#pragma varlocate 15 TXB0SIDH
#pragma varlocate 15 TXB0SIDHbits
#pragma varlocate 15 TXB0SIDL
#pragma varlocate 15 TXB0SIDLbits
#pragma varlocate 15 TXB0EIDH
#pragma varlocate 15 TXB0EIDHbits
#pragma varlocate 15 TXB0EIDL
#pragma varlocate 15 TXB0EIDLbits
#pragma varlocate 15 TXB0DLC
#pragma varlocate 15 TXB0DLCbits
#pragma varlocate 15 TXB0D0
#pragma varlocate 15 TXB0D0bits
#pragma varlocate 15 TXB0D1
#pragma varlocate 15 TXB0D1bits
#pragma varlocate 15 TXB0D2
#pragma varlocate 15 TXB0D2bits
#pragma varlocate 15 TXB0D3
#pragma varlocate 15 TXB0D3bits
#pragma varlocate 15 TXB0D4
#pragma varlocate 15 TXB0D4bits
#pragma varlocate 15 TXB0D5
#pragma varlocate 15 TXB0D5bits
#pragma varlocate 15 TXB0D6
#pragma varlocate 15 TXB0D6bits
#pragma varlocate 15 TXB0D7
#pragma varlocate 15 TXB0D7bits
#pragma varlocate 15 CANSTATRO1
#pragma varlocate 15 CANSTATRO1bits
#pragma varlocate 15 CANSTAT_R02
#pragma varlocate 15 CANSTAT_R02bits
#pragma varlocate 15 CANSTAT_RO2
#pragma varlocate 15 CANSTAT_RO2bits
#pragma varlocate 15 RXB1CON
#pragma varlocate 15 RXB1CONbits
#pragma varlocate 15 RXB1SIDH
#pragma varlocate 15 RXB1SIDHbits
#pragma varlocate 15 RXB1SIDL
#pragma varlocate 15 RXB1SIDLbits
#pragma varlocate 15 RXB1EIDH
#pragma varlocate 15 RXB1EIDHbits
#pragma varlocate 15 RXB1EIDL
#pragma varlocate 15 RXB1EIDLbits
#pragma varlocate 15 RXB1DLC
#pragma varlocate 15 RXB1DLCbits
#pragma varlocate 15 RXB1D0
#pragma varlocate 15 RXB1D0bits
#pragma varlocate 15 RXB1D1
#pragma varlocate 15 RXB1D1bits
#pragma varlocate 15 RXB1D2
#pragma varlocate 15 RXB1D2bits
#pragma varlocate 15 RXB1D3
#pragma varlocate 15 RXB1D3bits
#pragma varlocate 15 RXB1D4
#pragma varlocate 15 RXB1D4bits
#pragma varlocate 15 RXB1D5
#pragma varlocate 15 RXB1D5bits
#pragma varlocate 15 RXB1D6
#pragma varlocate 15 RXB1D6bits
#pragma varlocate 15 RXB1D7
#pragma varlocate 15 RXB1D7bits
#pragma varlocate 15 CANSTATRO0
#pragma varlocate 15 CANSTATRO0bits
#pragma varlocate 15 CANSTAT_R01
#pragma varlocate 15 CANSTAT_R01bits
#pragma varlocate 15 CANSTAT_RO1
#pragma varlocate 15 CANSTAT_RO1bits

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
#define _CONFIG_DECL(_CONFIG1L, \
                     _CONFIG1H, \
                     _CONFIG2L, \
                     _CONFIG2H, \
                     _CONFIG4L) \
  const rom unsigned char _configuration[7] = { \
    _CONFIG1L, \
    _CONFIG1H, \
    _CONFIG2L, \
    _CONFIG2H, \
    0xFF, \
    0xFF, \
    _CONFIG4L \
  }

/*-------------------------------------------------------------------------
 *   CONFIG1L (0x300000)
 *-------------------------------------------------------------------------*/
#define _CONFIG1L_DEFAULT    0xFF
#define _CP_ON_1L            0x00
#define _CP_OFF_1L           0xFF

/*-------------------------------------------------------------------------
 *   CONFIG1H (0x300001)
 *-------------------------------------------------------------------------*/
#define _CONFIG1H_DEFAULT    0xFF
#define _OSC_LP_1H           0xF8
#define _OSC_XT_1H           0xF9
#define _OSC_HS_1H           0xFA
#define _OSC_RC_1H           0xFB
#define _OSC_EC_1H           0xFC
#define _OSC_ECIO_1H         0xFD
#define _OSC_HSPLL_1H        0xFE
#define _OSC_RCIO_1H         0xFF

#define _OSCS_ON_1H          0xDF
#define _OSCS_OFF_1H         0xFF

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
#define _BORV_25_2L          0xFF

/*-------------------------------------------------------------------------
 *   CONFIG2H (0x300003)
 *-------------------------------------------------------------------------*/
#define _CONFIG2H_DEFAULT    0xFF
#define _WDT_OFF_2H          0xFE
#define _WDT_ON_2H           0xFF

#define _WDTPS_1_2H          0xF1
#define _WDTPS_2_2H          0xF3
#define _WDTPS_4_2H          0xF5
#define _WDTPS_8_2H          0xF7
#define _WDTPS_16_2H         0xF9
#define _WDTPS_32_2H         0xFB
#define _WDTPS_64_2H         0xFD
#define _WDTPS_128_2H        0xFF

/*-------------------------------------------------------------------------
 *   CONFIG4L (0x300006)
 *-------------------------------------------------------------------------*/
#define _CONFIG4L_DEFAULT    0xFF
#define _STVR_OFF_4L         0xFE
#define _STVR_ON_4L          0xFF


#endif
