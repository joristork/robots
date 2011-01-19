rem 	This batch file list out the subsystems to build for 
rem 	Various Devices. 
rem 	Currently all devices will have all subsystems built
rem 	special cases can be listed at the 2 markers below
rem 	and the environmental variable SUBSYSTEM_LIST
rem 	should be set apropriately.


rem 	Add Special cases devices here               <- Marker 1
if "%1"=="18F1XYZ" goto 18F1XYZ
if "%1"=="18F2XYZ" goto 18F2XYZ

rem These devices have minimal library support at this time 


rem	If the control reaches here then this is a 
rem	Generic device
rem	Generic case: compile all subsystems.
set SUBSYSTEM_LIST=ADC ancom CAN2510 CCP CMP CTMU DPSLP EEP Flash i2c mwire pcpwm PORTB ProMPT PWM PMP reset rtcc SW_RTCC SPI SW_I2C SW_SPI SW_UART Timers USART XLCD
goto EOF

rem Handle your special device as shown in the 
rem example below for 18F1XYZ and 18F2XYZ.           <- Marker 2

rem 18F1XYZ
:18F1XYZ
set SUBSYSTEM_LIST=BASIC
goto EOF

rem 18F2XYZ
:18F2XYZ
set SUBSYSTEM_LIST=BASIC
goto EOF


:EOF


