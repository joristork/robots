REM ###########################################################################
REM startup
REM ###########################################################################
echo Building startup modules...
cd extended
cd startup
for %%f in (*.o) do del %%f 
for %%f in (*.err) do del %%f 
mcc18 -ls --extended -ml -p=18cxx c018i_e.c
mcc18 -ls --extended -ml -p=18cxx c018iz_e.c
mcc18 -ls --extended -ml -p=18cxx c018_e.c
echo Installing startup modules...
if EXIST ..\..\..\lib\c018i_e.o del ..\..\..\lib\c018i_e.o
if EXIST ..\..\..\lib\c018iz_e.o del ..\..\..\lib\c018iz_e.o
if EXIST ..\..\..\lib\c018_e.o del ..\..\..\lib\c018_e.o
move c018i_e.o ..\..\..\lib
move c018iz_e.o ..\..\..\lib
move c018_e.o ..\..\..\lib
for %%f in (*.err) do del %%f 
cd ..


REM ###########################################################################
REM makeclib. non micro specific library
REM ###########################################################################


REM @echo off

if "%CD%"=="" set MCC_INCLUDE=..\..\..\h
if "%MCC_INCLUDE%"=="" set MCC_INCLUDE=%CD%\..\..\h

if exist clib_e.lib del clib_e.lib
mplib /q /c clib_e.lib

REM Using a "for" loop to delete files is cleaner than using a wildcard
REM because if there are no files to delete, no error message will be
REM displayed.

echo Building library modules...

REM ***Building the math library***
REM The math library must be built with automatic storage class (-sca),
REM which is the default option for the compiler.  This library may not
REM work if compiled with either -scs (static) or -sco (overlay) models.

echo ...math module... 
cd math
for %%f in (*.o) do del %%f
for %%f in (*.err) do del %%f
for %%f in (*.lst) do del %%f

for %%i in (*.asm) do mpasmwin /y /w1 /rDEC /l- /o /q /d__LARGE__ /p18cxx %%i
for %%f in (*.ERR) do type %%f
for %%i in (*.c) do mcc18 -ls --extended -ml -p=18cxx %%i
for %%i in (*.o) do mplib /q /r ..\clib_e.lib %%i

for %%f in (*.o) do del %%f
for %%f in (*.err) do del %%f
for %%f in (*.lst) do del %%f
cd ..

echo ...standard C library module...
cd stdclib
for %%f in (*.o) do del %%f
for %%f in (*.err) do del %%f
for %%f in (*.lst) do del %%f

for %%i in (*.asm) do mpasmwin /y /w1 /rDEC /l- /o /q /d__LARGE__ /p18cxx %%i
for %%f in (*.ERR) do type %%f
for %%i in (*.c) do mcc18 -ls --extended -ml -p=18cxx %%i
for %%i in (*.o) do mplib /q /r ..\clib_e.lib %%i

for %%f in (*.o) do del %%f
for %%f in (*.err) do del %%f
for %%f in (*.lst) do del %%f
cd ..

echo ...delay module...
cd delays
for %%f in (*.o) do del %%f
for %%f in (*.err) do del %%f
for %%f in (*.lst) do del %%f

for %%i in (*.asm) do mpasmwin /y /w1 /rDEC /l- /o /q /d__LARGE__ /p18cxx %%i
for %%f in (*.ERR) do type %%f
for %%i in (*.c) do mcc18 -ls --extended -ml -p=18cxx %%i
for %%i in (*.o) do mplib /q /r ..\clib_e.lib %%i

for %%f in (*.o) do del %%f
for %%f in (*.err) do del %%f
for %%f in (*.lst) do del %%f
cd ..

cd ..
echo Installing library...
cd ..\lib
IF EXIST clib_e.lib del clib_e.lib
move ..\src\extended\clib_e.lib .
cd ..\src\

