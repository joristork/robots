/* assert.h*/

#undef assert
#ifdef NDEBUG
   #define assert(test) (0)
#else
   #define assert(test) if(!test) { fprintf(stderr,"Assertion failed:"#test" ,file ");\
                                    fprintf(stderr,__FILE__);\
                                    fprintf(stderr," ,line %lu\r\n",(long)__LINE__);}




#endif

