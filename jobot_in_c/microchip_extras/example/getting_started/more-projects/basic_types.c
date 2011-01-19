char gC;
unsigned char guC;
signed char gsC;

int gI;
unsigned int guI;

short int gSI;
unsigned short int guSI;

short long int gSLI;
unsigned short long int guSLI;

long int gLI;
unsigned long int guLI;

float gF;
unsigned float guF;

void main (void)
{
  gC  = 'a';
  guC = 'b';
  gsC = 'c';

  gI  = 10;
  guI = 0xA;

  gSI  = 0b1010;
  guSI = 10u;

  gLI  = 0x1234;
  guLI = 0xFA5A;

  gF  = -1.395;
  guF = 3.14;

  while (1)
    ;
}
