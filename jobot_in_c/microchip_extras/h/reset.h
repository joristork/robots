/* $Id: reset.h,v 1.5 2008/12/19 08:33:30 jagadish Exp $ */
#ifndef __RESET_H
#define __RESET_H

#include <pconfig.h>

/* PIC18 Reset-related operations */

#define WDT_ENABLED
#define STVR_ENABLED    /*Stack Over/ Under flow reset enable control */

#if defined(BOR_V1)
#define BOR_ENABLED
#endif

char isMCLR(void);       /* MCLR reset?                   */
void StatusReset(void);  /* Reset the POR and BOR bits    */
char isPOR(void);        /* POR reset?                    */
char isWU(void);         /* Wakeup during sleep?          */

#if defined(BOR_ENABLED)
char isBOR(void);        /* BOR reset?                    */
#endif

#if defined(WDT_ENABLED)
char isWDTTO(void);      /* WDT timeout during operation? */
char isWDTWU(void);      /* WDT timeout during sleep?     */
#endif

#if defined (LVD_V1)
char isLVD(void);        /*LVD-- Low voltage detect?      */
#endif

#endif /* __RESET_H */
