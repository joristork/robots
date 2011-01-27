//jason jurkowski
//6/15/98
//
// test program for strtok, a function from string.h
//
//

#include <16C74.H>
#use delay(clock=10000000)
#use rs232(baud=9600, xmit=PIN_C6, rcv=PIN_C7)

#include "c:\picc\examples\string.h"

main(){

  char ia[20];
  char ib[20];
  char sep[20];
  char sentence[20];
  char not[5];
  char * c;
  int i;

  strcpy(ia, "aaaannnn");
  strcpy(ib, "aaaannnn");
  strcpy(sep,".,");
  strcpy(sentence,"a.v,b.c[");
  strcpy(not,"vbc");

printf("=======test running: strtok.c====================");

c = strtok(ia,ib);
if(c != '\0')
   puts("test one fails");

c = strtok(sentence,sep);
if(*c != 'a')
   puts("test two fails\n");


i=0;
while(*c != 0){
   c = strtok('\0',sep);
   if(*c != not[i])
      puts("test three fails\n\r");
   i++;
}

if (i == 0 )
   puts("test four fails\n\r");  

printf("=============test complete ========================");
}
