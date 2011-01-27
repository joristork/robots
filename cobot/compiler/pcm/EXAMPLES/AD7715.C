////               Driver routines for the AD7715 chip

#define ADC_CS    PIN_C7
#define ADC_RESET PIN_C6
#define ADC_DRDY  PIN_C2
#define ADC_DI    PIN_C4
#define ADC_DO    PIN_C5
#define ADC_CLK   PIN_C3

adc_init()
{
	output_low(ADC_RESET);
        output_high(ADC_CLK);
	output_high(ADC_CS);	//Set low to AD7715 chip select low pin
	output_high(ADC_RESET);	//Set high to AD7715 reset low pin
	delay_ms(3000);

	write_adc_byte( 0x12 );			//Communications Register
	write_adc_byte( 0x62 );			//Setup Register info here
}

void write_adc_byte(byte data) {
   byte i;

   output_low(ADC_CS);
   for(i=1;i<=8;++i) {
      output_low(ADC_CLK);
      output_bit(ADC_DI, shift_left(&data,1,0));
      output_high(ADC_CLK);
   }
   output_high(ADC_CS);
}


long int read_adc_word() {
   byte i;
   long data;

   output_low(ADC_CS);
   for(i=1;i<=16;++i) {
      output_low(ADC_CLK);
      output_high(ADC_CLK);
      shift_left(&data,2,input(ADC_DO));
   }
   output_high(ADC_CS);
   return data;
}



long int read_adc_value() {
        long int value;

	while ( input(ADC_DRDY) );
	while ( !input(ADC_DRDY) );
	while ( input(ADC_DRDY) );

	write_adc_byte(0x3a);
	value=read_adc_word();

	return value;
}

