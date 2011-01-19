#include <p18cxxx.h>
#include <portb.h>

/* Enable PORTB resistors */
#if defined (PTB_V1) || defined (PTB_V2) || defined (PTB_V3) || defined (PTB_V4) || defined (PTB_V6)
#undef EnablePullups
void EnablePullups(void)
{
  INTCON2bits.RBPU=0;        
}
#elif defined (PTB_V5)
#undef EnablePullups
void EnablePullups(void)
{
  INTCON2bits.RABPU=0;
}

#endif
