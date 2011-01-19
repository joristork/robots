/*
 * ProMPT.h
 * This file is only for use with the PIC18F2439/4439/2539/4539.
 */

#ifndef __PROMPT_H__
#define __PROMPT_H__

#include <pconfig.h>


#if defined (PROMPT_V1)
/*-------------------------------------------------------------------------
 * ProMPT API for Microchip C18 compiler
 *-------------------------------------------------------------------------*/
extern void ProMPT_ClearTick(void);
extern void ProMPT_DisableBoostMode(void);
extern void ProMPT_EnableBoostMode(void);
extern unsigned char ProMPT_GetAccel(void);
extern unsigned char ProMPT_GetBoostEndModulation(void);
extern unsigned char ProMPT_GetBoostFrequency(void);
extern unsigned char ProMPT_GetBoostStartModulation(void);
extern unsigned char ProMPT_GetBoostTime(void);
extern unsigned char ProMPT_GetDecel(void);
extern unsigned char ProMPT_GetFrequency(void);
extern unsigned char ProMPT_GetModulation(void);
extern unsigned char ProMPT_GetParameter(auto unsigned char parameter);
extern unsigned char ProMPT_GetVFCurve(auto unsigned char point);
extern void ProMPT_Init(auto unsigned char PWMfrequency);
extern void ProMPT_SetAccelRate(auto unsigned char rate);
extern void ProMPT_SetBoostEndModulation(auto unsigned char Modulation);
extern unsigned char ProMPT_SetBoostFrequency(auto unsigned char frequency);
extern void ProMPT_SetBoostStartModulation(auto unsigned char modulation);
extern void ProMPT_SetBoostTime(auto unsigned char time);
extern void ProMPT_SetDecelRate(auto unsigned char rate);
extern void ProMPT_SetFrequency(auto unsigned char frequency);
extern void ProMPT_SetLineVoltage(auto unsigned char voltage);
extern void ProMPT_SetMotorVoltage(auto unsigned char voltage);
extern void ProMPT_SetParameter(auto unsigned char parameter, auto unsigned char value);
extern void ProMPT_SetPWMfrequency(auto unsigned char PWMfrequency);
extern void ProMPT_SetVFCurve(auto unsigned char point, auto unsigned char value);
extern unsigned char ProMPT_SetVFCurveRAM(auto unsigned char point);
extern unsigned char ProMPT_Tick(void);
#else
#error ProMPT module is available only for PIC18F2439/4439/2539/4539
#endif

#endif
