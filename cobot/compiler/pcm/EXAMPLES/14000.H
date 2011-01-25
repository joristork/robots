//////// Standard Header file for the PIC14000 device ////////
#device PIC14000
#nolist
/////////////////////////////// I/O definitions for INPUT() and OUTPUT_xxx()
#define PIN_A0  40
#define PIN_A1  41
#define PIN_A2  42
#define PIN_A3  43

#define PIN_C0  56
#define PIN_C1  57
#define PIN_C2  58
#define PIN_C3  59
#define PIN_C4  60
#define PIN_C5  61
#define PIN_C6  62
#define PIN_C7  63

#define PIN_D0  64
#define PIN_D1  65
#define PIN_D2  66
#define PIN_D3  67
#define PIN_D4  68
#define PIN_D5  69
#define PIN_D6  70
#define PIN_D7  71

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

#define GLOBAL              0x0BC0    // Used for ENABLE/DISABLE INTERRUPTS


///////////////////////////////////// Constants used for SETUP_ADC_PORTS()
#define ALL_ANALOG         0x0   // To setup PORTA and PORTD, 'A'
#define RA0_RA1_RA2_ANALOG 0x1   // and 'D' values should be ORed
#define RA0_RA1_ANALOG     0x2
#define A_NO_ANALOGS       0x3
#define RD4_RD5_RD6_ANALOG 0x4
#define RD4_RD5_ANALOG     0x8
#define D_NO_ANALOGS       0xC
#define NO_ANALOGS         0xf

///////////////////////////////////// Constants used for SETUP_ADC()
#define CURRENT_0          0x00
#define CURRENT_2          0x10
#define CURRENT_4          0x20
#define CURRENT_7          0x30
#define CURRENT_9          0x40
#define CURRENT_11         0x50
#define CURRENT_13         0x60
#define CURRENT_16         0x70
#define CURRENT_18         0x80
#define CURRENT_20         0x90
#define CURRENT_22         0xA0
#define CURRENT_25         0xB0
#define CURRENT_27         0xC0
#define CURRENT_29         0xD0
#define CURRENT_31         0xE0
#define CURRENT_34         0xF0

///////////////////////////////////// 14000 Special
#define INT_RC             0x8C04
#define INT_BUTTON         0x8C10
#define INT_COMP           0x8C80


#list
