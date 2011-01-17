/////////////////////////////////////////////////////////////////////////
//
// Filename 	:	Uart_int.c                                            
// Revision 	:	1.0                                                   
// Created  	:	11-07-2002                                            
// Project  	:	Texture                                              
// Device		:	PIC16F876                                          
// Development	:	MPLAB / CCS PCM 
// Tab setting	:	3                                                
//                                                                   
// Author		:	E.H. Steffens
// Department	:	Informatics Institute, Faculteit NWI
// Copyright	:	Universiteit van Amsterdam
// 
//	Description	:	Uart interrupt handler                        
//
// id = "$Id: uart_int.c,v 1.1 2002-07-12 18:38:55+02 edwin Exp edwin $                    
/////////////////////////////////////////////////////////////////////////

// Serial port communications
#define 	SERIAL_BUFFER_SIZE 	16

short command_in = FALSE;  				// Indicate a command is received and ready for processing
short cmd_incoming = FALSE; 				// Indicates a command is being received
char 	serial_in[SERIAL_BUFFER_SIZE+1];
char 	next_in = 0;
char 	temp = 0;


// ------------------------------------------------------------------
// UART receive interrupt handler
// ------------------------------------------------------------------
#int_rda
void uart_receive_handler(void)
{
 
// Put char in temp
 temp = getc();

// Check if this is a new command
	if(cmd_incoming == FALSE){
		cmd_incoming = TRUE;
		next_in = 0;
	}
	// Check for string terminator
	if (temp == '\r'){
	// Set/reset the flags
 		cmd_incoming = FALSE;
 		command_in = TRUE;
 
	// Append 0 byte to string
 		serial_in[next_in] = 0;

		// Reset counter
 		next_in = 0;
	}
	else if(next_in < SERIAL_BUFFER_SIZE){ // Check if buffer is full
	// Put char in buffer
  		serial_in[next_in] = temp;

		// Point to next character
  		next_in++;
	}
}	// End of int_rda

