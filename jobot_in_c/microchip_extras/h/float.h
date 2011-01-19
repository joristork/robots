/*
 *      float.h
 *
 *      Defines implementation specific limits on type values.
 *
 *      Copyright (c) 2002, Microchip Technology Inc.
 *      2355 W.Chandler Blvd., Chandler, AZ, 85224
 *      All Rights Reserved.
 */

#ifndef __FLOAT_H
#define __FLOAT_H
/* C18 rounds toward zero */
#define FLT_ROUNDS 0
/* C18 does all floating point computation at range and precision of float type*/
#define FLT_EVAL 0 
#define FLT_RADIX 2
#define FLT_MANT_DIG 23 
#define DECIMAL_DIG 17
#define FLT_MIN_EXP -126
#define FLT_MAX_EXP 128
#define FLT_MIN_10_EXP -37
#define FLT_MAX_10_EXP 38
#define FLT_MIN 1.17549435E-38
#define FLT_MAX 6.80564693E+38
#define FLT_EPSILON 1.19209290E-07

#define DBL_MANT_DIG FLT_MANT_DIG
#define DBL_MIN_EXP FLT_MIN_EXP
#define DBL_MAX_EXP FLT_MAX_EXP
#define DBL_MIN_10_EXP FLT_MIN_10_EXP
#define DBL_MAX_10_EXP FLT_MAX_10_EXP
#define DBL_MIN FLT_MIN
#define DBL_MAX FLT_MAX
#define DBL_EPSILON FLT_EPSILON

#define LDBL_MANT_DIG FLT_MANT_DIG
#define LDBL_MIN_EXP FLT_MIN_EXP
#define LDBL_MAX_EXP FLT_MAX_EXP
#define LDBL_MIN_10_EXP FLT_MIN_10_EXP
#define LDBL_MAX_10_EXP FLT_MAX_10_EXP
#define LDBL_MIN FLT_MIN
#define LDBL_MAX FLT_MAX
#define LDBL_EPSILON FLT_EPSILON

#ifdef __TRADITIONAL18__
extern near volatile struct {
  unsigned IOV:1;
  unsigned FOV:1;
  unsigned FUN:1;
  unsigned FDZ:1;
  unsigned NAN:1;
  unsigned DOM:1;
  unsigned RND:1;
  unsigned SAT:1;
} __FPFLAGSbits;
#endif

#endif /* __FLOAT_H */
