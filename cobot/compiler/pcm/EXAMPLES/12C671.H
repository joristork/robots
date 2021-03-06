//////// Standard Header file for the PIC12C671 device ////////
#device PIC12C671
#nolist
/////////////////////////////// I/O definitions for INPUT() and OUTPUT_xxx()
#define PIN_A0  40
#define PIN_A1  41
#define PIN_A2  42
#define PIN_A3  43
#define PIN_A4  44
#define PIN_A5  45

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
#define ALL_ANALOG             0
#define ANALOG_AN1_REF         1
#define AN0_AN1_AN2_ANALOG     2
#define AN0_AN2_ANALOG_AN1_REF 3
#define AN0_AN1_ANALOG         4
#define AN0_ANALOG_AN1_REF     5
#define AN0_ANALOG             6
#define NO_ANALOGS             7
///////////////////////////////////// Constants used for SETUP_ADC()
#define ADC_OFF                0
#define ADC_CLOCK_DIV_2        1
#define ADC_CLOCK_DIV_8     0x41
#define ADC_CLOCK_DIV_32    0x81
#define ADC_CLOCK_INTERNAL  0xc1

#define ADC_DONE            0x40    // Used for ENABLE/DISABLE INTERRUPTS
#define INT_ADC             0x40    // Used for ENABLE/DISABLE INTERRUPTS

#list
