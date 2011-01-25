//////// Standard Header file for the PIC16CR84 device ////////
#device PIC16CR84
#nolist
/////////////////////////////// I/O definitions for INPUT() and OUTPUT_xxx()
#define PIN_A0  40
#define PIN_A1  41
#define PIN_A2  42
#define PIN_A3  43
#define PIN_A4  44

#define PIN_B0  48
#define PIN_B1  49
#define PIN_B2  50
#define PIN_B3  51
#define PIN_B4  52
#define PIN_B5  53
#define PIN_B6  54
#define PIN_B7  55

/////////////////////////////// Useful defines
#define FALSE 0
#define TRUE 1

#define BYTE int
#define BOOLEAN short int

#define getc getch
#define getchar getch
#define puts(s) {printf(s); putchar(13); putchar(10);}
#define putc putchar

/////////////////////////////// Constants used for RESTART_CAUSE()
#define WDT_FROM_SLEEP  0
#define WDT_TIMEOUT     8
#define MCLR_FROM_SLEEP 16
#define NORMAL_POWER_UP 24
/////////////////////////////// Constants used for SETUP_COUNTERS()
#define RTCC_INTERNAL   0
#define RTCC_EXT_L_TO_H 32
#define RTCC_EXT_H_TO_L 48
#define RTCC_DIV_2      0
#define RTCC_DIV_4      1
#define RTCC_DIV_8      2
#define RTCC_DIV_16     3
#define RTCC_DIV_32     4
#define RTCC_DIV_64     5
#define RTCC_DIV_128    6
#define RTCC_DIV_256    7
#define WDT_18MS        8
#define WDT_36MS        9
#define WDT_72MS       10
#define WDT_144MS      11
#define WDT_288MS      12
#define WDT_576MS      13
#define WDT_1152MS     14
#define WDT_2304MS     15
#define L_TO_H              0x40
#define H_TO_L                 0

#define RTCC_ZERO           0x0B20    // Used for ENABLE/DISABLE INTERRUPTS
#define INT_RTCC            0x0B20    // Used for ENABLE/DISABLE INTERRUPTS
#define RB_CHANGE           0x0B08    // Used for ENABLE/DISABLE INTERRUPTS
#define INT_RB              0x0B08    // Used for ENABLE/DISABLE INTERRUPTS
#define EXT_INT             0x0B10    // Used for ENABLE/DISABLE INTERRUPTS
#define INT_EXT             0x0B10    // Used for ENABLE/DISABLE INTERRUPTS

#define GLOBAL              0x0B80    // Used for ENABLE/DISABLE INTERRUPTS
#undef GLOBAL
#define GLOBAL              0x0B80    // Used for ENABLE/DISABLE INTERRUPTS
#define INT_EEPROM          0x0B40    // Used for ENABLE/DISABLE INTERRUPTS

#list
