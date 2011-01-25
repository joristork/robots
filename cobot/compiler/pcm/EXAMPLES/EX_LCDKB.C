/////////////////////////////////////////////////////////////////////////
////                           EX_LCDKB.C                            ////
////                                                                 ////
////  This program uses both the KBD.C and LCD.C drivers to allow    ////
////  keypad entry and LCD display.  All keys are echoed except *    ////
////  that will clear the display.  Either the kbd_getc or lcd_putc  ////
////  may be replaced with getc or putc to use just one device with  ////
////  the RS-232.                                                    ////
////                                                                 ////
////  Configure the CCS prototype card as follows:                   ////
////      Plug in both the LCD and KEYPAD.                           ////
/////////////////////////////////////////////////////////////////////////
////        (C) Copyright 1996,1997 Custom Computer Services            ////
//// This source code may only be used by licensed users of the CCS C   ////
//// compiler.  This source code may only be distributed to other       ////
//// licensed users of the CCS C compiler.  No other use, reproduction  ////
//// or distribution is permitted without written permission.           ////
//// Derivative programs created using this software in object code     ////
//// form are not restricted in any way.                                ////
////////////////////////////////////////////////////////////////////////////

#include <16C74.H>
#fuses HS,NOPROTECT,NOWDT

#use delay(clock=10000000)
#include <lcd.c>
#include <kbd.c>


main() {
   char k;

   lcd_init();
   kbd_init();

   lcd_putc("\fReady...\n");

   while (TRUE) {
      k=kbd_getc();
      if(k!=0)
        if(k=='*')
          lcd_putc('\f');
        else
          lcd_putc(k);
   }
}
