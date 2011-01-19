// CAN configuration registers

#define CAN2510_REG_BFPCTRL    0x0C
#define CAN2510_REG_TXRTSCTRL  0x0D
#define CAN2510_REG_CANSTAT    0x0E          // Repeated every 16 locations (1E, 2E, ...)
#define CAN2510_REG_CANCTRL    0x0F          // Repeated every 16 locations (1F, 2F, ...)

#define CAN2510_REG_TEC        0x1C
#define CAN2510_REG_REC        0x1D

#define CAN2510_REG_CNF3       0x28
#define CAN2510_REG_CNF2       0x29
#define CAN2510_REG_CNF1       0x2A
#define CAN2510_REG_CANINTE    0x2B
#define CAN2510_REG_CANINTF    0x2C
#define CAN2510_REG_EFLG       0x2D

//extern    near struct {
typedef union  EFLG {
    unsigned char byte;
    near struct {
        unsigned RX1OVR:1;
        unsigned RX0OVR:1;
        unsigned TXBO:1;
        unsigned TXEP:1;
        unsigned RXEP:1;
        unsigned TXWARN:1;
        unsigned RXWARN:1;
        unsigned EWANN:1;
    };
} CAN2510_REG_EFLGbits_type ;


// CAN Receive Mask/Filter registers
#define CAN2510_REG_RXM0SIDH   0x20
#define CAN2510_REG_RXM0SIDL   0x21
#define CAN2510_REG_RXM0EID8   0x22
#define CAN2510_REG_RXM0EID0   0x23
#define CAN2510_REG_RXM1SIDH   0x24
#define CAN2510_REG_RXM1SIDL   0x25
#define CAN2510_REG_RXM1EID8   0x26
#define CAN2510_REG_RXM1EID0   0x27
#define CAN2510_REG_RXF0SIDH   0x00
#define CAN2510_REG_RXF0SIDL   0x01
#define CAN2510_REG_RXF0EID8   0x02
#define CAN2510_REG_RXF0EID0   0x03
#define CAN2510_REG_RXF1SIDH   0x04
#define CAN2510_REG_RXF1SIDL   0x05
#define CAN2510_REG_RXF1EID8   0x06
#define CAN2510_REG_RXF1EID0   0x07
#define CAN2510_REG_RXF2SIDH   0x08
#define CAN2510_REG_RXF2SIDL   0x09
#define CAN2510_REG_RXF2EID8   0x0A
#define CAN2510_REG_RXF2EID0   0x0B
#define CAN2510_REG_RXF3SIDH   0x10
#define CAN2510_REG_RXF3SIDL   0x11
#define CAN2510_REG_RXF3EID8   0x12
#define CAN2510_REG_RXF3EID0   0x13
#define CAN2510_REG_RXF4SIDH   0x14
#define CAN2510_REG_RXF4SIDL   0x15
#define CAN2510_REG_RXF4EID8   0x16
#define CAN2510_REG_RXF4EID0   0x17
#define CAN2510_REG_RXF5SIDH   0x18
#define CAN2510_REG_RXF5SIDL   0x19
#define CAN2510_REG_RXF5EID8   0x1A
#define CAN2510_REG_RXF5EID0   0x1B

// CAN Transmit Control/Header/Data registers
#define CAN2510_REG_TXB0CTRL   0x30
#define CAN2510_REG_TXB0SIDH   0x31
#define CAN2510_REG_TXB0SIDL   0x32
#define CAN2510_REG_TXB0EID8   0x33
#define CAN2510_REG_TXB0EID0   0x34
#define CAN2510_REG_TXB0DLC    0x35
#define CAN2510_REG_TXB0D0     0x36
#define CAN2510_REG_TXB0D1     0x37
#define CAN2510_REG_TXB0D2     0x38
#define CAN2510_REG_TXB0D3     0x39
#define CAN2510_REG_TXB0D4     0x3A
#define CAN2510_REG_TXB0D5     0x3B
#define CAN2510_REG_TXB0D6     0x3C
#define CAN2510_REG_TXB0D7     0x3D

#define CAN2510_REG_TXB1CTRL   0x40
#define CAN2510_REG_TXB1SIDH   0x41
#define CAN2510_REG_TXB1SIDL   0x42
#define CAN2510_REG_TXB1EID8   0x43
#define CAN2510_REG_TXB1EID0   0x44
#define CAN2510_REG_TXB1DLC    0x45
#define CAN2510_REG_TXB1D0     0x46
#define CAN2510_REG_TXB1D1     0x47
#define CAN2510_REG_TXB1D2     0x48
#define CAN2510_REG_TXB1D3     0x49
#define CAN2510_REG_TXB1D4     0x4A
#define CAN2510_REG_TXB1D5     0x4B
#define CAN2510_REG_TXB1D6     0x4C
#define CAN2510_REG_TXB1D7     0x4D

#define CAN2510_REG_TXB2CTRL   0x50
#define CAN2510_REG_TXB2SIDH   0x51
#define CAN2510_REG_TXB2SIDL   0x52
#define CAN2510_REG_TXB2EID8   0x53
#define CAN2510_REG_TXB2EID0   0x54
#define CAN2510_REG_TXB2DLC    0x55
#define CAN2510_REG_TXB2D0     0x56
#define CAN2510_REG_TXB2D1     0x57
#define CAN2510_REG_TXB2D2     0x58
#define CAN2510_REG_TXB2D3     0x59
#define CAN2510_REG_TXB2D4     0x5A
#define CAN2510_REG_TXB2D5     0x5B
#define CAN2510_REG_TXB2D6     0x5C
#define CAN2510_REG_TXB2D7     0x5D

// CAN Transmit Control/Header/Data registers
#define CAN2510_REG_RXB0CTRL   0x60
#define CAN2510_REG_RXB0SIDH   0x61
#define CAN2510_REG_RXB0SIDL   0x62
#define CAN2510_REG_RXB0EID8   0x63
#define CAN2510_REG_RXB0EID0   0x64
#define CAN2510_REG_RXB0DLC    0x65
#define CAN2510_REG_RXB0D0     0x66
#define CAN2510_REG_RXB0D1     0x67
#define CAN2510_REG_RXB0D2     0x68
#define CAN2510_REG_RXB0D3     0x69
#define CAN2510_REG_RXB0D4     0x6A
#define CAN2510_REG_RXB0D5     0x6B
#define CAN2510_REG_RXB0D6     0x6C
#define CAN2510_REG_RXB0D7     0x6D

#define CAN2510_REG_RXB1CTRL   0x70
#define CAN2510_REG_RXB1SIDH   0x71
#define CAN2510_REG_RXB1SIDL   0x72
#define CAN2510_REG_RXB1EID8   0x73
#define CAN2510_REG_RXB1EID0   0x74
#define CAN2510_REG_RXB1DLC    0x75
#define CAN2510_REG_RXB1D0     0x76
#define CAN2510_REG_RXB1D1     0x77
#define CAN2510_REG_RXB1D2     0x78
#define CAN2510_REG_RXB1D3     0x79
#define CAN2510_REG_RXB1D4     0x7A
#define CAN2510_REG_RXB1D5     0x7B
#define CAN2510_REG_RXB1D6     0x7C
#define CAN2510_REG_RXB1D7     0x7D

#define CAN2510_CMD_RESET      0xC0
#define CAN2510_CMD_WRITE      0x02
#define CAN2510_CMD_READ       0x03
#define CAN2510_CMD_RTS        0x80
#define CAN2510_CMD_BITMOD     0x05
#define CAN2510_CMD_STATUS     0xA0


