//-----------------------------------------------------------------------
//
// Filename 	:	ADNS2051.c                                            
// Revision 	:	1.0                                                   
// Created  	:	03-05-2006                                            
// Project  	:	Optical mouse sensor for Hemisson                                              
// Device		:	PIC16F876                                          
// Development	:	MPLAB / CCS PCM 
// Tab setting	:	3                                                
//                                                                   
// Author		:	E.H. Steffens
// Department	:	Instituut voor Informatica, Faculteit NWI
// Copyright	:	Universiteit van Amsterdam
// 
//	Description	:	Serial communication ADNS-2051 optical mouse sensor                        
//-----------------------------------------------------------------------


//----------------------------------------------------------------//
//-                Conversion Function                           -//
// -------------------------------------------------------------- //
char chartohex( char valuetoconvert )
{
	char convertedval;
   	if( valuetoconvert >= 'A' ){
      		convertedval = valuetoconvert-'A'+10;
   	}	
   	else{
      		convertedval = valuetoconvert -'0';
   	}
   	return convertedval;
}

//-----------------------------------------------------------------------
//	load_command(command)
//-----------------------------------------------------------------------
//
#use standard_io(c)
void load_command(int j )
{
	int i;
	int command;

	command = j;
	
// Load command
	for(i=0; i < COMMAND_BITS; i++){
		
	// Leading edge of Program clock
		output_low(SCLK);
		
	// put here code to shift command bits out on program data pin
		output_bit(SDIO, shift_left(&command, 1, 0));
		
	// Trailling edge of program clock (data is clocked in ADNS-2051)
		output_high(SCLK);
	
	// Delay
		delay_us(10);
	}
		
}
// -- end of load_command --

//-----------------------------------------------------------------------
//	load_data(int data)
//-----------------------------------------------------------------------
//
void load_data(int data_in)
{
	int i;
	int data;

	data = data_in;

// Load data
	for(i=0; i < DATA_BITS; i++){

	// Leading edge of Program clock
		output_low(SCLK);
		
	// put here code to shift data out on SDIO
		output_bit(SDIO, shift_left(&data, 1, 0 ) );
		
	// Trailling edge of clock (data is clocked in ADNS-2051)
		output_high(SCLK);
		
	// Delay
		delay_us(10);
}
}

//-----------------------------------------------------------------------
//	read_data()
//-----------------------------------------------------------------------
//
long read_data()
{
	int i;
	int data;
	
// Tri-state the SDIO pin
	output_float(SDIO);

// Minium delay between address and reading data
	delay_us(100);
	
// Read data
	for(i=0; i < DATA_BITS; i++){

	// Leading edge of serial clock (data is clocked out ADNS-2051)
		output_low(SCLK);
		
	// Some extra delay before read
		delay_us(10);
		
	// put here code to shift data in on B7
		shift_left(&data, 1, input(SDIO) );
		
	// Trailling edge of serial clock 
		output_high(SCLK);
	}
	return data;
}


//-----------------------------------------------------------------------
//	read_ADNS2051()
//-----------------------------------------------------------------------
//
int read_ADNS2051(int address)
{
	int data;

// Load register address
	load_command(address);
	data = read_data();
	
	return data;
}
// -- end of read_ADNS2051() --

//-----------------------------------------------------------------------
//	write_ADNS2051()
//-----------------------------------------------------------------------
//
void write_ADNS2051(int address, int data)
{
// Load register address
	load_command(WRITE | address);
	load_data(data);
	
}
// -- end of write_ADNS2051() --

//-----------------------------------------------------------------------
//	convert()
//-----------------------------------------------------------------------
//
char convert(char c)
{
	if( c >= '0' && c <= '9'){
		return (c - 0x30);
	}
	else if( c >='A' && c <= 'Z'){
		return (c - 0x37);
	}
	else{
		return (c - 0x57);
	}

}// -- end of convert() --






