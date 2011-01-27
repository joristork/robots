////        (C) Copyright 1996,1997 Custom Computer Services            ////
//// This source code may only be used by licensed users of the CCS C   ////
//// compiler.  This source code may only be distributed to other       ////
//// licensed users of the CCS C compiler.  No other use, reproduction  ////
//// or distribution is permitted without written permission.           ////
//// Derivative programs created using this software in object code     ////
//// form are not restricted in any way.                                ////
////////////////////////////////////////////////////////////////////////////
#nolist
#ifndef islower

#define islower(x)  isamoung(x,"abcdefghijklmnopqrstuvwxyz")
#define isupper(x)  isamoung(x,"ABCDEFGHIJKLMNOPQRSTUVWXYZ")
#define isalnum(x)  isamoung(x,"0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz")
#define isalpha(x)  isamoung(x,"ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz")
#define isdigit(x)  isamoung(x,"0123456789")
#define isspace(x)  (x==' ')
#define isxdigit(x) isamoung(x,"0123456789ABCDEFabcdef")

#endif

#list
