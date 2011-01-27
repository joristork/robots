/////////////////////////////////////////////////////////////////////////
////                                                                 ////
////                           EX_MOUSE.C                            ////
////                                                                 ////
//// An example showing how to make a serial mouse using the CCS C   ////
//// compiler.  Your operating system / computer has to be able      ////
//// to use a Microsoft Serial mouse or a Mouse Systems serial       ////
//// mouse.                                                          ////
////                                                                 ////
//// Requires a special CPC cable connected to Port A.               ////
////                                                                 ////
//// Wiring diagram for prototype board:                             ////
//// -----------------------------------------------------------     ////
//// 40 to 53  (Button 40 becomes left mouse button)                 ////
//// 41 to 54  (Button 41 becomes right mouse buttom)                ////
//// 9 to 15   (Pot 9 becomes X movement)                            ////
//// 10 to 16  (Pot 10 becomes Y movement)                           ////
//// 7 to 12   (PC RxD connects to PIC C6 for Xmit of data)          ////
//// 11 to 47  (PC DTR connects to PIC B0 for reset interrupt)       ////
////                                                                 ////
////  This example will work with the PCB, PCM and PCH compilers.    ////
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
#device ADC=8
#fuses HS,NOWDT,NOPROTECT
#use delay(clock=20000000)
#use rs232(baud=1200, xmit=PIN_A3, rcv=PIN_A2,errors)  // Jumpers: 11 to 17, 12 to 18

#elif defined(__PCM__)
#include <16F877.h>
#device ADC=8
#fuses HS,NOWDT,NOPROTECT,NOLVP,NOPUT
#use delay(clock=20000000)
#use rs232(baud=1200, xmit=PIN_C6, rcv=PIN_C7,errors)  // Jumpers: 8 to 11, 7 to 12

#elif defined(__PCH__)
#include <18c452.h>
#device ADC=8
#fuses HS,NOPROTECT
#use delay(clock=20000000)
#use rs232(baud=1200, xmit=PIN_C6, rcv=PIN_C7,errors)  // Jumpers: 8 to 11, 7 to 12
#endif

#define MICROSOFT TRUE
#define MOUSE_SYSTEMS FALSE   //not tested.  may not be compatable with Windows

#DEFINE LEFT_BUTTON PIN_B6
#DEFINE RIGHT_BUTTON PIN_B7
#DEFINE MIDDLE_BUTTON PIN_B5

#DEFINE X_CHANNEL 0
#DEFINE Y_CHANNEL 1

struct {
   short int delta;
   short int left;
   short int right;
   short int middle;
   signed int x;
   signed int y;
} mouse;

void clear_all_mouse(void) {
   mouse.delta=0;
   mouse.x=0;
   mouse.y=0;
   mouse.left=0;
   mouse.right=0;
   mouse.middle=0;
}

#int_ext
void reset(void) {
 #if MICROSOFT
   clear_all_mouse();
   putc(0x80|'M');
 #endif
}

void get_data(void) {
   int i;
   signed int pos;

   if ((input(LEFT_BUTTON))&&(mouse.left)) {mouse.left=0;mouse.delta=1;}
   if ((input(RIGHT_BUTTON))&&(mouse.right)) {mouse.right=0;mouse.delta=1;}
   if ((input(MIDDLE_BUTTON))&&(mouse.middle)) {mouse.middle=0;mouse.delta=1;}
   
   if ((!input(LEFT_BUTTON))&&(!mouse.left)) {mouse.left=1;mouse.delta=1;}
   if ((!input(RIGHT_BUTTON))&&(!mouse.right)) {mouse.right=1;mouse.delta=1;}
   if ((!input(MIDDLE_BUTTON))&&(!mouse.middle)) {mouse.middle=1;mouse.delta=1;}

   set_adc_channel(X_CHANNEL);
   delay_us(10);
   pos=read_adc() & 0xF8;
   mouse.x=(pos-0x80)/10;

   set_adc_channel(Y_CHANNEL);
   delay_us(10);
   pos=read_adc() & 0xF8;
   mouse.y=(pos-0x80)/10;

   if ((mouse.x)||(mouse.y)) {mouse.delta=1;}
}

void send_data(void) {
#if MICROSOFT
    putc(0xC0 | (mouse.left << 5) | (mouse.right <<4 ) | ((mouse.y >> 4) & 0x0C) | ((mouse.x >> 6) & 0x03));
    putc(0x80 | mouse.x & 0x3F);
    putc(0x80 | mouse.y & 0x3F);
#elif MOUSE_SYSTEMS
   putc(0x80 | (!mouse.left<<2) | (!mouse.middle<<1) | !mouse.right);
   putc(mouse.x);
   mouse.x=0;
   putc(mouse.y);
   mouse.y=0;
   putc(mouse.x);
   putc(mouse.y);
#endif
   mouse.delta=0;
   mouse.x=0;
   mouse.y=0;
}

void main(void) {
   clear_all_mouse();

   setup_adc_ports(RA0_RA1_RA3_ANALOG);
   setup_adc(ADC_CLOCK_DIV_2);
   ext_int_edge(H_TO_L);
   enable_interrupts(int_ext);
   enable_interrupts(global);

   while (TRUE) {
      get_data();
      if (mouse.delta) {send_data();delay_ms(17);}
   }
}
