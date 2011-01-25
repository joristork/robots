Changes since the July-2001 manual:

Arrays may be defined with [] in many cases.  For example:
               const char id[] = {"Hi There"};    // Same as [9]
               int x[];                           // Same as *x
               int x[] = {1,2,3};                 // Same as [3]

The #USE I2C option NOFORCE_SW is still accepted however the new FORCE_HW
    is what will appear in future documentation.

#INT_???? directives now allow a NOCLEAR option to prevent the compiler
    from clearing the interrupt.  For example:
             #INT_RTCC NOCLEAR
             isr() {
                ...
             }

The compiler now accepts trigraph sequences for keyboards that do not have
the following: # [ ] \ ^ { } | ~
The following three character sequences are translated to the indicated one char:
               ??=    #
               ??(    [
               ??/    \
               ??)    ]
               ??'    ^
               ??<    {
               ??!    |
               ??>    }
               ??-    ~

Some new functions have been added to allow for unorthodox jumps.
        x = label_address(label);   Will return the ROM address of the label
        goto_address(x);            Will jump to the ROM address x

        r = setjmp(env);            Standard C function (requires setjmp.h)
        longjmp(env,val);           Standard C function (requires setjmp.h)

     Use these functions with GREAT caution.  Note as well that setjmp and
     longjmp do not clean up the stack on 12 and 14 bit parts.

The standard functions iscntrl,isgraph,isprint and ispunct are now in ctype.h.

The compiler now supports the offsetof() and offsetofbit() functions.  offsetof()
requires stddef.h be included.   These functions return the offset (in bytes and
bits) of the field within a structure.

The defines __FILE__, __LINE__ and __TIME__ are now supported.

#USE RS232 has been updated to allow a stream identifier for the port.  This
identifier may then be used in fgetc,fgets,fprintf,and fputc to indicate what
port to use for the function.  For example:
      #use rs232(baud=9600,xmit=pin_c6,rcv=pin_c7,stream=HOSTPC)
      #use rs232(baud=1200,xmit=pin_b1,rcv=pin_b0,stream=GPS)
      #use rs232(baud=9600,xmit=pin_b3,stream=DEBUG)
      ...
      while(TRUE) {
         c=fgetc(GPS);
         fputc(c,HOSTPC);
         if(c==13)
           fprintf(DEBUG,"Got a CR\r\n");
      }

The following functions have been added to math.h: sinh, cosh, tanh, fabs,
fmod, atan2, frexp, ldexp, modf

The include file "errno.h" may be included to enable error checking in the
math functions.  Since this checking takes extra ROM the checking is only
done if errno.h has been included before math.h.  The function strerror()
may be used to format an error message.

The standard C header files float.h, limits.h, stddef.h and locale.h are provided
to make these compiler constants available to those who would like to use them.

The assert macro is now supplied in assert.h.  It may be used to test for a condition
and if false will output an error on STDERR.  By defualt STDERR is the first RS232
port defined in a program.  If NODEBUG is #defined then no code is generated for the
assert macro.  Example:
                 assert( number_of_entries < TABLE_SIZE );

The built-in function SPRINTF has been added.  For example:
               char s[10];
               long i;

               sprintf(s,"%lu",i);

The built in function getenv() has been added.  The syntax is
      value = getenv(cstring);
             cstring is a constant string with a recognised keyword
             as follows:
                 FUSE_SET:fffff     Returns 1 if fuse fffff is enabled
                 FUSE_VALID:fffff   Returns 1 if fuse fffff is valid
                 INT:iiiii          Returns 1 if the interrupt iiiii is valid
                 ID                 Returns the device ID (set by #ID)
                 DEVICE             Returns the device name string (like "PIC16C74")
                 CLOCK              Returns the Osc clock value (from a #USE DELAY)
                 VERSION            Returns the compiler version as a float
                 VERSION_STRING     Returns the compiler version as a string
                 PROGRAM_MEMORY     Returns the size of memory for code (in words)
                 STACK              Returns the stack size
                 DATA_EEPROM        Returns the number of bytes of data EERPOM
                 READ_PROGRAM       Returns a 1 if the code memory can be read
                 PIN:pb             Returns a 1 if bit b on port p is on this part
                 ADC_CHANNELS       Returns the number of A/D channels
                 ADC_RESOULTION     Returns the number of bits returned from READ_ADC()
                 ICD                Returns a 1 if this is being compiled for a ICD
                 SPI                Returns a 1 if the device has SPI
                 USB                Returns a 1 if the device has USB
                 CAN                Returns a 1 if the device has CAN
                 I2C_SLAVE          Returns a 1 if the device has I2C slave H/W
                 I2C_MASTER         Returns a 1 if the device has I2C master H/W
                 PSP                Returns a 1 if the device has PSP
                 COMP               Returns a 1 if the device has a comparator
                 VREF               Returns a 1 if the device has a voltage reference
                 LCD                Returns a 1 if the device has direct LCD H/W
                 UART               Returns the number of H/W UARTs
                 CCPx               Returns a 1 if the device has CCP number x
                 TIMERx             Returns a 1 if the device has TIMER number x
       Examples:
             #IF  getenv("VERSION")<3.050
             #ERROR  Compiler version too old for this program
             #ENDIF

             for(i=0;i<getenv("DATA_EEPROM");i++)
               write_eeprom(i,0);

             #IF getenv("FUSE_VALID:BROWNOUT")
             #FUSE BROWNOUT
             #ENDIF

The PCW IDE F4 command has been removed and replaced with a feature that
allows you to move your cursor over a (,),{ or } and the matching item
will highlight.


