//----------------------------------------------------------------
// Internal support for math library.
//----------------------------------------------------------------

#define FLT_BIAS        127
#define FLT_EMIN        (-126)
#define FLT_EMAX        (+127)

#define NaN             (0x7FFFFFFFul)
#define NEGINF          (0xFF800000ul)
#define POSINF          (0x7F800000ul)
#define POSNAN          (0x7FFFFFFFul)
#define ZERO            (0x00000000ul)
#define POSZERO         (0x00000000ul)
#define NEGZERO         (0x80000000ul)

typedef enum tagFTYPE
{
    _ZEROTYPE       = 0x01,
    _FINITETYPE     = 0x02,
    _INFTYPE        = 0x04,
    _NANTYPE        = 0x80,
}FTYPE;

typedef union tagUfloat32
{
    unsigned char ub[4];
    unsigned short us[2];
    unsigned long l;
    float f;
} tUfloat32;
typedef struct
{
    unsigned long sig;
    short         exp;  // biased exponent 
    unsigned char sgn;
} tIEEE;


// Support routines
FTYPE   _UnpkMath(tUfloat32 X, tIEEE *pIEEE);
void    _PackMath( tIEEE *pIEEE, tUfloat32 *pX);
float   _asinacos(float x, unsigned char flag);
float   _sincos(float x, unsigned char flag);
float   _sinhcosh(float y, unsigned char flag);
tUfloat32 _fchop(tUfloat32 x, int k, unsigned char *pChopped);


