/////////////////////////////////////////////////////////////////////////
////                             EX_RTC.C                            ////
////                                                                 ////
////  This program reads and writes to an external Real Time Clock.  ////
////  It communicates to the user over the RS-232 interface.         ////
////                                                                 ////
////  Configure the CCS prototype card as follows:                   ////
////      Insert jumpers from: 11 to 17 and 12 to 18.                ////
////      NJU6355   Protoboard                                       ////
////         1         50  B3                                        ////
////         2   crystal                                             ////
////         3   crystal                                             ////
////         4         27  gnd                                       ////
////         5         49  B2                                        ////
////         6         48  B1                                        ////
////         7         47  B0                                        ////
////         8         28  +5V                                       ////
////                                                                 ////
////  For a 40 pin part such as the 16C74 add jumpers from           ////
////  8 to 11, 7 to 12, and change the #USE RS232 to:                ////
////      #use rs232(baud=9600, xmit=PIN_C6, rcv=PIN_C7)             ////
/////////////////////////////////////////////////////////////////////////
////        (C) Copyright 1996,1997 Custom Computer Services            ////
//// This source code may only be used by licensed users of the CCS C   ////
//// compiler.  This source code may only be distributed to other       ////
//// licensed users of the CCS C compiler.  No other use, reproduction  ////
//// or distribution is permitted without written permission.           ////
//// Derivative programs created using this software in object code     ////
//// form are not restricted in any way.                                ////
////////////////////////////////////////////////////////////////////////////

#include <16C84.H>

#use delay(clock=10000000)
#use rs232(baud=9600, xmit=PIN_A3, rcv=PIN_A2)

#fuses   HS,NOPROTECT,NOWDT

#include <NJU6355.C>

byte get_bcd() {
  char first,second;

  do {
    first=getc();
  } while ((first<'0') || (first>'9'));
  putc(first);
  first-='0';

  do {
    second=getc();
  } while (((second<'0') || (second>'9')) && (second!='\r'));
  putc(second);

  if(second=='\r')
    return(first);
  else
    return((first<<4)|(second-'0'));
}

void set_clock(){
   byte day,mth,year,dow,hour,min;

   printf("\r\nPress ENTER after 1 digit answers.");
   printf("\r\nYear 19: ");
   year=get_bcd();
   printf("\r\nMonth: ");
   mth=get_bcd();
   printf("\r\nDay: ");
   day=get_bcd();
   printf("\r\nWeekday 1-7: ");
   dow=get_bcd();
   printf("\r\nHour: ");
   hour=get_bcd();
   printf("\r\nMin: ");
   min=get_bcd();

   rtc_set_datetime(day,mth,year,dow,hour,min);
   printf("\r\n\n");
}


void display_bcd( byte n ) {
   putc( (n/16)+'0' );
   putc( (n%16)+'0' );
}

void display_3_bcd( char separator, byte a, byte b, byte c ) {

   display_bcd(a);
   putc(separator);
   display_bcd(b);
   putc(separator);
   display_bcd(c);
}


main() {
   char cmd;
   byte day,mth,year,dow,hour,min,sec;

   rtc_init();

   printf("\r\nPress S to change, D to display: ");

   do {
      cmd=getc()%0xdf;
   } while ((cmd!='D')&&(cmd!='S'));

   if(cmd=='S')
     set_clock();

   printf("\r\n");
   while (TRUE) {
      rtc_get_date( day, mth, year, dow);
      display_3_bcd( '/', day, mth, year );
      printf("    ");
      rtc_get_time( hour, min, sec );
      display_3_bcd( ':', hour, min, sec );
      printf("\r");
      delay_ms(250);
   }
}
