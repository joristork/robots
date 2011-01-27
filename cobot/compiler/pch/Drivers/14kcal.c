////        (C) Copyright 1996,1997 Custom Computer Services            ////
//// This source code may only be used by licensed users of the CCS C   ////
//// compiler.  This source code may only be distributed to other       ////
//// licensed users of the CCS C compiler.  No other use, reproduction  ////
//// or distribution is permitted without written permission.           ////
//// Derivative programs created using this software in object code     ////
//// form are not restricted in any way.                                ////
////////////////////////////////////////////////////////////////////////////

enum ADC_SOURCE {an0, an1, an2, an3, bandgap, srefh, srefl, itemp, refa,
                                    refb, an4, an5, an6, an7};

float READ_FLOAT_CAL(int n)
{
   int i;
   float data;

   for (i = 0; i < 4; i++)
     *(&data + i) = READ_CALIBRATION(i + n);
   return(data);
}

float READ_CALIBRATED_AD(ADC_SOURCE ch)
{
   #bit TEMP_SENSOR_OFF = 0x8F.1

   float Nch, Noff, Nbg;
   static int const_loaded = 0;
   static float Kref, Kbg, Vtherm, Ktc;

   if (ch == itemp)
      TEMP_SENSOR_OFF = 0;

   if (!const_loaded)
   {
      Kref = READ_FLOAT_CAL(0);
      Kbg = READ_FLOAT_CAL(4);
      Vtherm = READ_FLOAT_CAL(8);
      Ktc = READ_FLOAT_CAL(12);
      const_loaded = 1;
   }

   SET_ADC_CHANNEL(ch);
   Nch = (float)READ_ADC();

   if (Nch < 0.0)
      Nch += 65536.0;

   Noff = 0.0;
   SET_ADC_CHANNEL(srefh);
   Noff -= Kref*(float)READ_ADC();

   SET_ADC_CHANNEL(srefl);
   Noff += (1 + Kref)*(float)READ_ADC();

   SET_ADC_CHANNEL(bandgap);
   Nbg = (float)READ_ADC() - Noff;

   Nch = Kbg*(Nch - Noff)/Nbg;

   if (ch == itemp)
      Nch = (Nch - Vtherm)/Ktc + 25.0;

   return(Nch);
}
