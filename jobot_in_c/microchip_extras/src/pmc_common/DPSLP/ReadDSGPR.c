 
#include "dpslp.h"

#if defined (DPSLP_V1_1) 
/*************************************************************************
 Function Prototype :void ReadDSGPR( CONTEXT* ptr)

 Include            : dpslp.h

 Description        : This function reads context saved in DSGPRx registers and updates in CONTEXT structure.
 
 Arguments          : None
                      
 Return Value       :None.

 
 Remarks            : None.
*************************************************************************/

void ReadDSGPR( CONTEXT* ptr )
{

	ptr->Reg0 = DSGPR0;		//Context saved in DSGPR0 register before deep sleep.
	ptr->Reg1 = DSGPR1;		//Context saved in DSGPR1 register before deep sleep.
		
}

//#else
//#warning "Selected device does not support this function"
#endif
