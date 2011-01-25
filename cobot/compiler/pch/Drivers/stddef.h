////        (C) Copyright 1996,2001 Custom Computer Services            ////
//// This source code may only be used by licensed users of the CCS C   ////
//// compiler.  This source code may only be distributed to other       ////
//// licensed users of the CCS C compiler.  No other use, reproduction  ////
//// or distribution is permitted without written permission.           ////
//// Derivative programs created using this software in object code     ////
//// form are not restricted in any way.                                ////
////////////////////////////////////////////////////////////////////////////

#ifndef _STDDEF

#define _STDDEF

#if sizeof(int *)==1
#define ptrdiff_t int
#else
#define ptrdiff_t long
#endif

#define size_t int
#define wchar_t char
#define NULL 0

#define offsetof(s,f) (offsetofbit(s,f)/8)

#endif
