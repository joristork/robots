; This file is only for use with the PIC18F2439/4439.

; PRMTX439.ASM 
; ProMPT API for the MicroChip C18 compiler
; version 0.01  05/01/2002

#include "PRMTxx39.inc" 

#ifdef __RUN_PRMTX439
#include <p18F2439.inc>

clearTick:				equ	D'1'
disableBoostMode:		equ	D'2'
enableBoostMode:		equ	D'3'
getFrequency:			equ	D'4'
getModulation:			equ	D'5'
Init:					equ	D'6'
setAccelRate:			equ	D'7'
setBoostEndModulation:	equ	D'8'
setBoostFrequency:		equ	D'9'
setBoostStartModulation: equ D'10'
setBoostTime:			equ	D'11'
setDecelRate:			equ	D'12'
setFrequency:			equ	D'13'
setLineVoltage:			equ	D'14'
setMotorVoltage:		equ	D'15'
setPWMfrequency:		equ	D'16'
setVFCurve:				equ	D'17'
getVFCurve:				equ	D'18'
Start:					equ	D'19'
Stop:					equ	D'20'
Tick:					equ	D'21'
SetParameter:			equ	D'22'
GetParameter:			equ	D'23'
getAccelRate:			equ	D'24'
getBoostEndModulation:	equ	D'25'
getBoostFrequency:		equ	D'26'
getBoostStartModulation: equ D'27'
getBoostTime:			equ	D'28'
getDecelRate:			equ D'29'

PARAM1:			equ	H'02C0'
PARAM2:			equ	H'02C1'
PROMPT:			equ	H'3000'
PROMPT_ISR:		equ	H'3004'

	ORG	H'0008'
	GOTO	PROMPT_ISR

	
	CODE
	
ProMPT_ClearTick:	
	GLOBAL	ProMPT_ClearTick
	movlw	clearTick
	goto	PROMPT
	
ProMPT_DisableBoostMode:
	GLOBAL	ProMPT_DisableBoostMode
	movlw	disableBoostMode
	goto	PROMPT
	
ProMPT_EnableBoostMode:
	GLOBAL	ProMPT_EnableBoostMode
	movlw	enableBoostMode
	goto	PROMPT
	
ProMPT_GetAccelRate:
	GLOBAL	ProMPT_GetAccelRate
	movlw	getAccelRate
	call	PROMPT
	movwf	PRODL,0
	return

ProMPT_GetBoostEndModulation:
	GLOBAL ProMPT_GetBoostEndModulation
	movlw	getBoostEndModulation
	call	PROMPT
	movwf	PRODL,0
	return
	
ProMPT_GetBoostFrequency:
	GLOBAL ProMPT_GetBoostFrequency
	movlw	getBoostFrequency
	call	PROMPT
	movwf	PRODL,0
	return

ProMPT_GetBoostStartModulation:
	GLOBAL	ProMPT_GetBoostStartModulation
	movlw	getBoostStartModulation
	call	PROMPT
	movwf	PRODL,0
	return

ProMPT_GetDecelRate:
	GLOBAL	ProMPT_GetDecelRate
	movlw	getDecelRate
	call	PROMPT
	movwf	PRODL,0
	return				

ProMPT_GetFrequency:
	GLOBAL	ProMPT_GetFrequency
	movlw	getFrequency
	call	PROMPT				;freq is now in w
	movwf	PRODL,0				;8 bit values are returned in PRODL
	return

ProMPT_GetModulation:
	GLOBAL	ProMPT_GetModulation
	movlw	getModulation
	call	PROMPT
	movwf	PRODL,0
	return

ProMPT_GetParameter:
	GLOBAL	ProMPT_GetParameter
	movff	WREG,PARAM1		;save parameter
	movlw	GetParameter
	call	PROMPT
	movwf	PRODL,0				;pass return value
	return

ProMPT_Init:
	GLOBAL	ProMPT_Init
	movlw	H'FF'
	movff	PLUSW1, PARAM1			;Init has a single parameter
	movlw	Init
	goto	PROMPT

ProMPT_SetAccelRate:
	GLOBAL	ProMPT_SetAccelRate
	movlw	H'FF'
	movff	PLUSW1, PARAM1			;SetAccelRate has a single parameter
	movlw	setAccelRate
	goto	PROMPT

ProMPT_SetBoostEndModulation:
	GLOBAL	ProMPT_SetBoostEndModulation
	movlw	H'FF'
	movff	PLUSW1, PARAM1			
	movlw	setBoostEndModulation
	goto	PROMPT
	
ProMPT_SetBoostFrequency:
	GLOBAL	ProMPT_SetBoostFrequency
	movlw	H'FF'
	movff	PLUSW1, PARAM1			
	movlw	setBoostFrequency
	call	PROMPT
	movwf	PRODL,0
	return
	
ProMPT_SetBoostStartModulation:
	GLOBAL	ProMPT_SetBoostStartModulation
	movlw	H'FF'
	movff	PLUSW1, PARAM1			
	movlw	setBoostStartModulation
	goto	PROMPT

ProMPT_SetBoostTime:
	GLOBAL	ProMPT_SetBoostTime
	movlw	H'FF'
	movff	PLUSW1, PARAM1			
	movlw	setBoostTime
	goto	PROMPT

ProMPT_SetDecelRate:
	GLOBAL	ProMPT_SetDecelRate
	movlw	H'FF'
	movff	PLUSW1, PARAM1			
	movlw	setDecelRate
	goto	PROMPT
	
ProMPT_SetFrequency:
	GLOBAL	ProMPT_SetFrequency
	movlw	H'FF'
	movff	PLUSW1, PARAM1			
	movlw	setFrequency
	call	PROMPT
	movwf	PRODL,0
	return

ProMPT_SetLineVoltage:
	GLOBAL	ProMPT_SetLineVoltage
	movlw	H'FF'
	movff	PLUSW1, PARAM1			
	movlw	setLineVoltage
	goto	PROMPT

ProMPT_SetMotorVoltage:
	GLOBAL	ProMPT_SetMotorVoltage
	movlw	H'FF'
	movff	PLUSW1, PARAM1			
	movlw	setMotorVoltage
	goto	PROMPT

ProMPT_SetPWMfrequency:
	GLOBAL	ProMPT_SetPWMfrequency
	movlw	H'FF'
	movff	PLUSW1, PARAM1			
	movlw	setPWMfrequency
	goto	PROMPT

ProMPT_SetVFCurve:
	GLOBAL	ProMPT_SetVFCurve
	movlw	H'FE'
	movff	PLUSW1, PARAM2			;point
	incf	WREG,w
	movff	PLUSW1, PARAM1			;value
	movlw	setVFCurve
	goto	PROMPT

ProMPT_getVFCurve:
	GLOBAL	ProMPT_getVFCurve
	movlw	H'FF'
	movff	PLUSW1, PARAM1
	call	PROMPT
	movwf	PRODL,0
	return
	
ProMPT_SetParameter:
	GLOBAL	ProMPT_SetParameter
	movlw	H'FE'
	movff	PLUSW1,PARAM2			;address to PARAM2
	movlw	H'FF'
	movff	PLUSW1,PARAM1			;value to PARAM1
	movlw	SetParameter
	goto	PROMPT

ProMPT_Start:
	GLOBAL	ProMPT_Start
	movlw	Start
	goto	PROMPT

ProMPT_Stop:
	GLOBAL	ProMPT_Stop
	movlw	Stop
	goto	PROMPT

ProMPT_Tick:
	GLOBAL	ProMPT_Tick
	movlw	Tick
	call	PROMPT
	movwf	PRODL,0
	return
#endif
	END

	