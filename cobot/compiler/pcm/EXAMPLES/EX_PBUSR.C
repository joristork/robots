/////////////////////////////////////////////////////////////////////////
////                          EX_PBUSR.C                             ////
////                                                                 ////
////  This program shows a simple PIC to PIC sharred (shadow) RAM    ////
////  using a one wire interface between the PICs.  Any (reasonable) ////
////  number of PICs may be connected to the one wire.  The program  ////
////  also shows how to handle two serial ports within one program.  ////
////                                                                 ////
////  Configure the CCS prototype card as follows:                   ////
////      Insert jumpers from: 11 to 8, 12 to 7.                     ////
////      Install a 1K resistor from 47 (B0) to 30 (+5)              ////
////      Connect 47 (B0) to another unit's 47 (B0)                  ////
////                                                                 ////
////  Optional: To monitor communications at a PC:                   ////
////      Connect 47 (B0) to 14 and plug monitor PC into port B      ////
/////////////////////////////////////////////////////////////////////////

#include <16C74.H>
#fuses   HS,NOPROTECT,NOWDT

#use delay(clock=20000000)

#use rs232(baud=9600,float_high,bits=9,xmit=PIN_B0,rcv=PIN_B0)

#define PBUS_RAM_SIZE 16

byte pbus_ram[PBUS_RAM_SIZE];
enum pbus_states {PBUS_IDLE=0x80,PBUS_NEED_CS=0x84};
byte pbus_state=PBUS_IDLE;
byte pbus_next_loc,pbus_next_value;
short pbus_ram_changed;

#bit ninth_bit = RS232_ERRORS.7
#bit collision = RS232_ERRORS.6
#bit intf = 11.1

#int_ext
void pbus_isr() {
    byte data;

    if(kbhit()) {

      data=getc();

      if(ninth_bit) {
           if(data==0xf2)
              pbus_state=0;
      } else
        if(pbus_state==PBUS_NEED_CS) {
           if(data==(pbus_next_loc^pbus_next_value)) {
               pbus_state=PBUS_IDLE;
               pbus_ram_changed=true;
               pbus_ram[pbus_next_loc]=pbus_next_value;
           }
        } else if(pbus_state==0) {
           pbus_next_loc=data;
           pbus_state=1;
        } else if(pbus_state==1) {
           pbus_next_value=data;
           pbus_state=PBUS_NEED_CS;
      }
    }
}

void pbus_write_ram(byte loc, byte value) {
   byte checksum,i;

   pbus_ram_changed=true;
   retry:
      checksum=loc^value;
      disable_interrupts(GLOBAL);
      pbus_ram[loc]=value;
      collision=false;
      ninth_bit=1;

      putc(0xf2);           if(collision) goto error;
      ninth_bit=0;
      putc(loc);            if(collision) goto error;
      putc(value);          if(collision) goto error;
      putc(checksum);       if(collision) goto error;
      intf=false;
      enable_interrupts(GLOBAL);
      return;

   error:
      delay_ms(16);
      enable_interrupts(GLOBAL);
      goto retry;
}


#use rs232(baud=9600,xmit=PIN_C6,rcv=PIN_C7)
#include <input.c>

main() {
   byte to,i,value;

   printf("\r\nPress S to send.\r\n");

   ext_int_edge( h_to_l );
   enable_interrupts(global);
   enable_interrupts(int_ext);
   pbus_ram_changed=false;

   do {
      if(pbus_ram_changed) {
         printf("\r\nRAM Changed:\r\n");
         pbus_ram_changed=false;
         for(i=0;i<PBUS_RAM_SIZE;++i)
           printf(" %2X",pbus_ram[i]);
      }

      if(kbhit())
        if(toupper(getc())=='S') {
           printf("\r\nLocation: ");
           to=gethex();
           printf("\r\nValue: ");
           value=gethex();
           pbus_write_ram(to,value);
      }

   } while (TRUE);
}
