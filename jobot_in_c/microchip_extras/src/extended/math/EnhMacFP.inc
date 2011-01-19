;******************************************************************************
; Macros, constants, defines used by enhanced float routines.
;******************************************************************************
;        radix dec

;******************************************************************************
; General stack structure after setup:
;
;        FSR1 --> [20] Unused slot
;               > [19] Previous FSR2H
;               > [18] Previous FSR2L
; LARGE_STK     > [17] temp    fA3
;               > [16] temp    fT0
;               > [15] temp    fT1
; MEDIUM_STK    > [14] temp    fWexpHi
;               > [13] temp    fEexp
;               > [12] temp    fW0
;               > [11] temp    fW1
;               > [10] temp    fW2
;               > [ 9] temp    fW3
; SHORT_STK     > [ 8] temp    fFlg
;               > [ 7] Arg     fAexp,fTemp
;               > [ 6] Arg     fA0  
;               > [ 5] Arg     fA1  
;               > [ 4] Arg     fA2  
;               > [ 3] Arg     fBexp  iHi0
;               > [ 2] Arg     fB0    i1
;               > [ 1] Arg     fB1    i2
;        FSR2 --> [ 0] Arg     fB2    iLo3


#define LARGE_STK   18  ;;  __addFP,__mulFP, __divFP, CastxxFP
#define MEDIUM_STK  15  ;;  __FpCompare
#define SHORT_STK    9  ;;  __CastFPxx


;; Stack offsets relative to FSR2
#define fFlg    8

#define fA3     17
#define fA2     4
#define fA1     5
#define fA0     6
#define fAexp   7
#define fTemp   7       ;; used by CastFPxx for temporary storage

#define fB2     0 
#define fB1     1 
#define fB0     2 
#define fBexp   3 

#define fT1     15  
#define fT0     16  
#define fW3     9  
#define fW2     10  
#define fW1     11  
#define fW0     12  
#define fWexp   13  
#define fWexpHi 14  

;; This group is for integers
#define iHi0    3
#define i1      2
#define i2      1
#define iLo3    0


;; Setup stack: Allocate stack, save FSR2 and set FSR2 to stack base.
;;      NumAllocated = number of bytes of full stack already allocated
;;      NumFullStack = number of bytes of full stack including NumAllocated
;;                but does not include the 2 bytes used to save FSR2
;; No STATUS bits affected. WREG unchanged.
SetupStack macro NumAllocated,NumFullStack

        ;; Allocate temporary locations on stack: FSR1 += NumTempBytes
          stkadj  NumFullStack-NumAllocated              ; added STKADJ PSEUDO-INSTRUCTION for stack analysis
        addfsr  1,NumFullStack-NumAllocated

        ;; Save current value of FSR2. Adds two more bytes to stack.
          stkadj    1                    ; added STKADJ PSEUDO-INSTRUCTION for stack analysis
        movff   FSR2L, POSTINC1
          stkadj    1                    ; added STKADJ PSEUDO-INSTRUCTION for stack analysis
        movff   FSR2H, POSTINC1

        ;; Set up FSR2 to point to the first parameter on the stack.
        movff   FSR1L, FSR2L
        movff   FSR1H, FSR2H
        subfsr  2, NumFullStack+2  ; include saved FSR2

        endm

;; Release stack: restore FSR2 and release stack so that NumAllocated
;;                      bytes remain allocated.
;; WREG unchanged.
ReleaseStack macro NumAllocated,NumFullStack

        ;; Restore FSR2
          stkadj    -1                    ; added STKADJ PSEUDO-INSTRUCTION for stack analysis
        movf    POSTDEC1, F, A
          stkadj    -1                    ; added STKADJ PSEUDO-INSTRUCTION for stack analysis
        movff   POSTDEC1, FSR2H
        movff   INDF1, FSR2L

        ;; Deallocate stack locations.
          stkadj  -NumFullStack+NumAllocated              ; added STKADJ PSEUDO-INSTRUCTION for stack analysis
        subfsr  1, NumFullStack-NumAllocated

        endm


FLT_BIAS        equ      127    ; exponent bias
FLT_SMIN        equ     -149    ; min unbiased subnormal exponent
FLT_EMIN        equ     -126    ; min unbiased normal exponent
FLT_EMAX        equ      127    ; max unbiased normal exponent
       
;; Flags for both A and B arguments

#define FLG_ZERO_A    0  ; A: is zero
#define FLG_INF_A     1  ; A: is infinity
#define FLG_SIGN_A    2  ; A: is negative
#define FLG_NaN_AorB  3  ; A or B is a NaN  (A or B)

#define FLG_ZERO_B    4  ; B: is zero
#define FLG_INF_B     5  ; B: is infinity
#define FLG_SIGN_B    6  ; B: is negative
#define FLG_SIGN_ABW  7  ; ABW: Sign (A xor B) or W

Exchange macro Var0, Var1
        movf    Var0,W
        xorwf   Var1,W
        xorwf   Var0,F
        xorwf   Var1,F
        endm

Clear4 macro Var0, Var1, Var2, Var3
        clrf    Var0
        clrf    Var1
        clrf    Var2
        clrf    Var3
        endm

        ;; Var = Var << 1;      Must initialize _C
ShiftL3C macro Var0,Var1,Var2
        rlcf    Var0,F
        rlcf    Var1,F
        rlcf    Var2,F
        endm

        ;; Var = Var << 1;      Must initialize _C
ShiftL4C macro Var0,Var1,Var2,Var3
        rlcf    Var0,F
        rlcf    Var1,F
        rlcf    Var2,F
        rlcf    Var3,F
        endm

        ;; Var = Var << 1;      Logical shift
ShiftL4 macro Var0,Var1,Var2,Var3
        bcf     STATUS,C
        rlcf    Var0,F
        rlcf    Var1,F
        rlcf    Var2,F
        rlcf    Var3,F
        endm

        ;; Var = Var >> 1;      Must initialize _C
ShiftR2C macro Var0,Var1
        rrcf    Var1,F
        rrcf    Var0,F
        endm


        ;; Var = Var >> 1;      Must initialize _C
ShiftR3C macro Var0,Var1,Var2
        rrcf    Var2,F
        rrcf    Var1,F
        rrcf    Var0,F
        endm

        ;; Var = Var >> 1;      Must initialize _C
ShiftR4C macro Var0,Var1,Var2,Var3
        rrcf    Var3,F
        rrcf    Var2,F
        rrcf    Var1,F
        rrcf    Var0,F
        endm

        ;; Var++, C invalid
Inc2 macro Var0,Var1
        infsnz  Var0,F
        incf    Var1,F
        endm


        ;; Var++, C valid
Inc3 macro Var0,Var1,Var2
        incf    Var0,F
        clrf    WREG
        addwfc  Var1,F
        addwfc  Var2,F
        endm

        ;; Var++, C valid 
Inc4 macro Var0,Var1,Var2,Var3
        incf    Var0,F
        clrf    WREG
        addwfc  Var1,F
        addwfc  Var2,F
        addwfc  Var3,F
        endm
        
        ;; Decrement 2 bytes. WREG unchanged.
Dec2 macro Var0, Var1
        decf    Var0,F
        btfss   STATUS,C
        decf    Var1,F
        endm

        ;; Var = -Var
Neg2 macro Var0,Var1
        negf    Var0
        comf    Var1,F
        clrf    WREG
        addwfc  Var1,F
        endm


        ;; Var = -Var
Neg3 macro Var0,Var1,Var2
        negf    Var0
        comf    Var1,F
        comf    Var2,F
        clrf    WREG
        addwfc  Var1,F
        addwfc  Var2,F
        endm


        ;; Var = -Var
Neg4 macro Var0,Var1,Var2,Var3
        negf    Var0
        comf    Var1,F
        comf    Var2,F
        comf    Var3,F
        clrf    WREG
        addwfc  Var1,F
        addwfc  Var2,F
        addwfc  Var3,F
        endm

        ;; Var = -Var
Neg5 macro Var0,Var1,Var2,Var3,Var4
        negf    Var0
        comf    Var1,F
        comf    Var2,F
        comf    Var3,F
        comf    Var4,F
        clrf    WREG
        addwfc  Var1,F
        addwfc  Var2,F
        addwfc  Var3,F
        addwfc  Var4,F
        endm



        ;; AVar += BVar
Add32 macro AVar0,AVar1,AVar2, BVar0,BVar1
        movf    BVar0,W
        addwf   AVar0,F
        movf    BVar1,W
        addwfc  AVar1,F
        movlw   0
        addwfc  AVar2,F
        endm

        ;; AVar += BVar
Add41 macro AVar0,AVar1,AVar2,AVar3, BVar0
        movf    BVar0,W
        addwf   AVar0,F
        movlw   0
        addwfc  AVar1,F
        addwfc  AVar2,F
        addwfc  AVar3,F
        endm


        ;; AVar += BVar
Add42 macro AVar0,AVar1,AVar2,AVar3, BVar0,BVar1
        movf    BVar0,W
        addwf   AVar0,F
        movf    BVar1,W
        addwfc  AVar1,F
        movlw   0
        addwfc  AVar2,F
        addwfc  AVar3,F
        endm


        ;; AVar += BVar
Add43 macro AVar0,AVar1,AVar2,AVar3, BVar0,BVar1,BVar2
        movf    BVar0,W
        addwf   AVar0,F
        movf    BVar1,W
        addwfc  AVar1,F
        movf    BVar2,W
        addwfc  AVar2,F
        movlw   0
        addwfc  AVar3,F
        endm


        ;; AVar += BVar
Add4 macro AVar0,AVar1,AVar2,AVar3, BVar0,BVar1,BVar2,BVar3
        movf    BVar0,W
        addwf   AVar0,F
        movf    BVar1,W
        addwfc  AVar1,F
        movf    BVar2,W
        addwfc  AVar2,F
        movf    BVar3,W
        addwfc  AVar3,F
        endm


        ;; AVar -= BVar
Sub2 macro AVar0,AVar1, BVar0,BVar1
        movf    BVar0,W
        subwf   AVar0,F
        movf    BVar1,W
        subwfb  AVar1,F
        endm


        ;; AVar -= BVar
Sub3 macro AVar0,AVar1,AVar2, BVar0,BVar1,BVar2
        movf    BVar0,W
        subwf   AVar0,F
        movf    BVar1,W
        subwfb  AVar1,F
        movf    BVar2,W
        subwfb  AVar2,F
        endm


        ;; AVar -= BVar
Sub4 macro AVar0,AVar1,AVar2,AVar3, BVar0,BVar1,BVar2,BVar3
        movf    BVar0,W
        subwf   AVar0,F
        movf    BVar1,W
        subwfb  AVar1,F
        movf    BVar2,W
        subwfb  AVar2,F
        movf    BVar3,W
        subwfb  AVar3,F
        endm

        ;; AVar -= BVar
Sub43 macro AVar0,AVar1,AVar2,AVar3, BVar0,BVar1,BVar2
        movf    BVar0,W
        subwf   AVar0,F
        movf    BVar1,W
        subwfb  AVar1,F
        movf    BVar2,W
        subwfb  AVar2,F
        movlw   0
        subwfb  AVar3,F
        endm
        
        ;; (PRODL,PRODH) = AVar*Bvar
Mul2 macro AVar,BVar
        movf    BVar,W
        mulwf   AVar
        endm


;==========================================================================
;=============== R E L  B R A N C H E S ===================================
;==========================================================================
                

        ;; Branch if bit set
braSet macro Var, Bit, jLabel
        btfsc   Var,Bit
        bra     jLabel
 endm

        ;; Branch if bit clear
braClr macro Var, Bit, jLabel
        btfss   Var,Bit
        bra     jLabel
 endm


        ;; Branch if Var == 0.  WREG unchanged.
braFeqZ macro Var,jZero
        movf    Var,F
        bz      jZero
        endm

        ;; Branch if Var != 0.  WREG unchanged.
braFneZ macro Var,jNotZero
        movf    Var,F
        bnz     jNotZero
        endm

        ;; Branch if Var == Lit.
braFeqL macro   Var,Lit,jLabel
        movlw   Lit
        subwf   Var,W
        bz      jLabel
        endm

        ;; Branch if Var != Lit.
braFneL macro   Var,Lit,jLabel
        movlw   Lit
        subwf   Var,W
        bnz     jLabel
        endm

        ;; Branch if Var <= Lit.
braFleL macro   Var,Lit,jLabel
        movlw   Lit
        cpfsgt  Var
        bra     jLabel
        endm

        ;; Branch if Var >= Lit.
braFgeL macro   Var,Lit,jLabel
        movlw   Lit
        cpfslt  Var
        bra     jLabel
        endm

        ;; Branch if Var1 == Var2
braFeqF macro   Var1,Var2,jLabel
        movf    Var1,W
        subwf   Var2,W
        bz      jLabel
        endm

        ;; Branch if Var1 != Var2
braFneF macro   Var1,Var2,jLabel
        movf    Var1,W
        subwf   Var2,W
        bnz     jLabel
        endm

braFgeF macro   Var1,Var2,jLabel
        movf    Var2,W
        cpfslt  Var1
        bra     jLabel
        endm

braFleF macro   Var1,Var2,jLabel
        movf    Var2,W
        cpfsgt  Var1
        bra     jLabel
        endm

braFltF macro   Var1,Var2,jLabel
        movf    Var1,W
        subwf   Var2,W
        btfss   STATUS,Z
        bc      jLabel
        endm

;==========================================================================
;=============== A B S O L U T E    B R A N C H E S =======================
;==========================================================================

        ;; Go to if bit set
gotoSet macro Var, Bit, jLabel
        btfsc   Var,Bit
        goto     jLabel
 endm

        ;; Go to if bit clear
gotoClr macro Var, Bit, jLabel
        btfss   Var,Bit
        goto     jLabel
 endm



        ;; Go to if F == Lit
gotoFeqL macro   Var,Lit,jLabel
        movlw     Lit
        subwf     Var,W
        btfsc     _Z
        goto      jLabel
        endm

;Go to if F != Lit
gotoFneL macro   Var,Lit,jLabel
        movlw     Lit
        subwf     Var,W
        btfss     _Z
        goto      jLabel
        endm
        

;Go to if F >= Lit
gotoFgeL macro   Var,Lit,jLabel
          movlw     Lit
          cpfslt    Var
          goto      jLabel
          endm

;Go to if F <= Lit
gotoFleL macro   Var,Lit,jLabel
          movlw     Lit
          cpfsgt    Var
          goto      jLabel
          endm

