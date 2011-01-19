rem @echo off
if "%1"=="" goto EOF

if "%2"=="BATCH_BUILD" goto skip_generic

call make_genericlibs_e.bat

:skip_generic
echo Building all peripherals for %1
cd ..\lib
if exist p%1_e.lib del p%1_e.lib
mplib /q /c p%1_e.lib
cd..\src

echo ...processor definition module...
cd extended\proc
for %%f in (*.o) do del %%f
for %%f in (*.err) do del %%f
for %%f in (*.lst) do del %%f

mpasmwin /y /rDEC /l- /o /q /d__LARGE__ /p%1 p%1.asm
for %%f in (*.ERR) do type %%f
mplib /q /r ..\..\..\lib\p%1_e.lib p%1.o

for %%f in (*.o) do del %%f
for %%f in (*.err) do del %%f
for %%f in (*.lst) do del %%f
cd ..\..

rem The following batch file processes the subsystems that need to be compiled. 
rem The environmental variable %SUBSYSTEM_LIST%  will be set bythe batch file.
set SUBSYSTEM_LIST=
call get_subsystems.bat %1
@for %%f in (%SUBSYSTEM_LIST%) do call make_one_subsystem_e.bat %1 %%f
:EOF