#pragma device PIC16F84
#fuses  xt,nowdt
#use delay(Clock=4000000)

#define FALSE 0
#define TRUE 1
#define BYTE int
#define BOOLEAN short int

#define PIN_3  43
#define PIN_4  44
#define PIN_7  48
#define PIN_8  49
#define PIN_9  50
#define PIN_10 51
#define PIN_11 52
#define PIN_12 53
#define PIN_13 54
#define PIN_14 55
#define RA0    40
#define RA1    41
#define RA2    42

#define WDT_FROM_SLEEP  0
#define WDT_TIMEOUT     8
#define MCLR_FROM_SLEEP 16
#define NORMAL_POWER_UP 24
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

#define EEPROM_DONE 0x40
#define RTCC_ZERO   0x20
#define RB_CHANGE   0x08
#define EXT_INT     0x10
#define GLOBAL      0x80
#define L_TO_H      0x40
#define H_TO_L         0

#define INT_EEPROM INT_ADC

#include <stdio.h>
