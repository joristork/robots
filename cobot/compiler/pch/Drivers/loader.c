///////////////////////////////////////////////////////////////////////////
////                         loader.c                                  ////
////                                                                   ////
//// This driver will take an Intel 8-bit Hex file over RS232 channels ////
//// and modify the flash program memory with the new code.  A proxy   ////
//// function is required to sit between the real loading function     ////
//// and the main code because the #org preprocessor command could     ////
//// possibly change the order of functions within a defined block.    ////
////                                                                   ////
//// After each good line, the loader sends an ACKLOD character.  The     ////
//// driver uses XON/XOFF flow control.  Also, any buffer on the PC    ////
//// UART must be turned off, or to its lowest setting, otherwise it   ////
//// will miss data.                                                   ////
////                                                                   ////
////                                                                   ////
///////////////////////////////////////////////////////////////////////////
////        (C) Copyright 1996,1997 Custom Computer Services            ////
//// This source code may only bea used by licensed users of the CCS C   ////
//// compiler.  This source code may only be distributed to other       ////
//// licensed users of the CCS C compiler.  No other use, reproduction  ////
//// or distribution is permitted without written permission.           ////
//// Derivative programs created using this software in object code     ////
//// form are not restricted in any way.                                ////
////////////////////////////////////////////////////////////////////////////

#define MAXADDR 0x1D00
#define BUFFER_LEN_LOD 64

int  buffidx;
#byte buffidx = 0xA0
char buffer[BUFFER_LEN_LOD];
#byte buffer = 0xA1

#define ACKLOD    0x06
#define XON    0x11
#define XOFF   0x13

#SEPARATE
unsigned int atoi_b16(char *s);

#ORG 0x1D05, 0x1EFF AUTO=0
void real_loader (void)
{
   short do_ACKLOD, not_done;
   int   i, count, line_type, cs, check_sum;
   long  data,addr;

   set_uart_speed (9600);

   not_done = TRUE;

   while (not_done)  {
      buffidx = 0;
      do_ACKLOD = TRUE;

      do {
         buffer[buffidx++] = getc();
      } while ((buffer[buffidx-1] != 0x0D) && (buffidx<=BUFFER_LEN_LOD));

      putchar (XOFF);

      if (buffer[0] == ':') {
         count = atoi_b16 (&buffer[1]);

         addr = atoi_b16 (&buffer[3]);
         addr = (addr << 8) | atoi_b16 (&buffer[5]);

         line_type = atoi_b16 (&buffer[7]);

         if (addr != 0)
            addr /= 2;

         if (line_type == 1)
            not_done = FALSE;
         else if (addr > MAXADDR)
            do_ACKLOD = TRUE;
         else {
            cs = 0;
            for (i=1; i<(buffidx-3); i+=2)
               cs += atoi_b16 (&buffer[i]);

            cs = 0xFF - cs + 1;
            check_sum = atoi_b16 (&buffer[buffidx-3]);

            if (check_sum != cs)
               do_ACKLOD = FALSE;
            else {
               i = 9;
               while (i < buffidx-3)  {
                  data = atoi_b16 (&buffer[i+2]);
                  data = (data << 8 ) | atoi_b16 (&buffer[i]);

                  write_program_eeprom (addr,data);

                  ++addr;
                  i += 4;
               }
            }
         }
      }

      if (do_ACKLOD)
         putchar (ACKLOD);

      putchar (XON);
   }

   #asm
   MOVLW  0x00
   MOVWF  0x0A
   GOTO   0x00
   #endasm
}

#ORG 0x1D05
unsigned int atoi_b16(char *s)
{
   unsigned int result = 0;
   int i;

   for (i=0; i<2; i++)  {
      if (*s >= 'A' && *s <= 'F')
         result = 16*result + (*s) - 'A' + 10;
      else if (*s >= '0' && *s <= '9')
         result = 16*result + (*s) - '0';

      s++;
   }

   return(result);
}


#ORG 0x1D00,0x1D04
void load_program (void)
{
   real_loader();
}
