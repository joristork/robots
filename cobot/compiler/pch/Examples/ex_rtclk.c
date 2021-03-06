/////////////////////////////////////////////////////////////////////////
////                           EX_RTCLK.C                            ////
////                                                                 ////
////  This program reads and writes to an external Real Time Clock.  ////
////  It communicates to the user using the LCD and keypad.          ////
////                                                                 ////
////  Configure the CCS prototype card as follows:                   ////
////      Install the LCD and keypad.                                ////
////                                                                 ////
////  This example will work with the PCB, PCM and PCH compilers.    ////
////  The following conditional compilation lines are used to        ////
////  include a valid device for each compiler.  Change the device,  ////
////  clock and RS232 pins for your hardware if needed.              ////
/////////////////////////////////////////////////////////////////////////
////        (C) Copyright 1996,2001 Custom Computer Services         ////
//// This source code may only be used by licensed users of the CCS  ////
//// C compiler.  This source code may only be distributed to other  ////
//// licensed users of the CCS C compiler.  No other use,            ////
//// reproduction or distribution is permitted without written       ////
//// permission.  Derivative programs created using this software    ////
//// in object code form are not restricted in any way.              ////
/////////////////////////////////////////////////////////////////////////


#if defined(__PCB__)
#include <16c56.h>
#fuses HS,NOWDT,NOPROTECT
#use delay(clock=20000000)

#elif defined(__PCM__)
#include <16c74.h>
#fuses HS,NOWDT,NOPROTECT
#use delay(clock=20000000)

#elif defined(__PCH__)
#include <18c452.h>
#fuses HS,NOPROTECT
#use delay(clock=20000000)
#endif

#define RTC_CE    PIN_D0
#define RTC_IO    PIN_D1
#define RTC_CLK   PIN_D2
#define RTC_DATA  PIN_D3

#include <ds1302.c>
#include <lcd.c>
#include <kbd.c>


byte get_bcd() {
  char first,second;

  do {
    first=kbd_getc();
  } while ((first<'0') || (first>'9'));
  lcd_putc(first);
  first-='0';

  do {
    second=kbd_getc();
  } while ((second<'0') || (second>'9'));
  lcd_putc(second);

  return((first<<4)|(second-'0'));
}

void set_clock(){
   byte day,mth,year,dow,hour,min;

   lcd_putc("\fYear 19: ");
   year=get_bcd();
   lcd_putc("\fMonth: ");
   mth=get_bcd();
   lcd_putc("\fDay: ");
   day=get_bcd();
   lcd_putc("\fWeekday 1-7: ");
   dow=get_bcd();
   lcd_putc("\fHour: ");
   hour=get_bcd();
   lcd_putc("\fMin: ");
   min=get_bcd();

   rtc_set_datetime(day,mth,year,dow,hour,min);
}


main() {
   char cmd;
   byte day,mth,year,dow,hour,min,sec;

   rtc_init();
   lcd_init();
   kbd_init();

   lcd_putc("\f1: Change, 2: Display");

   do {
      cmd=kbd_getc();
   } while ((cmd!='1')&&(cmd!='2'));

   if(cmd=='1')
     set_clock();

   while (TRUE) {
      lcd_putc('\f');
      rtc_get_date( day, mth, year, dow);
      rtc_get_time( hour, min, sec );
      printf(lcd_putc,"%2X/%2X/%2X\n%2X:%2X:%2X",day,mth,year,hour,min,sec);
      delay_ms(250);
   }
}
