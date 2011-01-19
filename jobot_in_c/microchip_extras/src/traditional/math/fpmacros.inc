;******************************************************************************
; Macros, constants, defines and variable externs used by float routines.
;******************************************************************************
;        radix dec

;; Uses traditional global names

        EXTERN __FPFLAGS  
        EXTERN __AARGB3   
        EXTERN __AARGB2   
        EXTERN __AARGB1   
        EXTERN __AARGB0   
        EXTERN __AEXP     
        EXTERN __BARGB3   
        EXTERN __BARGB2   
        EXTERN __BARGB1   
        EXTERN __BARGB0   
        EXTERN __BEXP     
        EXTERN __TEMPB3   
        EXTERN __TEMPB2   
        EXTERN __TEMPB1   
        EXTERN __TEMPB0   
        EXTERN __AARGB7   
        EXTERN __AARGB6   
        EXTERN __AARGB5   
        EXTERN __AARGB4   

#define fFlg  __FPFLAGS  

#define fA3    __AARGB3   
#define fA2    __AARGB2   
#define fA1    __AARGB1   
#define fA0    __AARGB0   
#define fAexp  __AEXP     

#define fB3    __BARGB3   
#define fB2    __BARGB2   
#define fB1    __BARGB1   
#define fB0    __BARGB0   
#define fBexp  __BEXP     

#define fT1     __TEMPB3   
#define fT0     __TEMPB2   
#define fW3     __TEMPB1   
#define fW2     __TEMPB0   
#define fW1     __AARGB7   
#define fW0     __AARGB6   
#define fWexp   __AARGB5   
#define fWexpHi __AARGB4   

#define iA0 __AARGB0   ;msb
#define iA1 __AARGB1
#define iA2 __AARGB2
#define iA3 __AARGB3   ;lsb

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
        movff   Var1,Var0
        movwf   Var1
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

