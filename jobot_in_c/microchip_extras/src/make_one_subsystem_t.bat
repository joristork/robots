rem @echo off
@echo Making subsystem traditional %2 for %1

cd pmc_common
cd %2
for %%f in (*.o) do del %%f
for %%f in (*.err) do del %%f
for %%f in (*.lst) do del %%f

for %%i in (*.asm) do mpasmwin /rDEC /l- /o /q /dPIC%1 /d__LARGE__ /p%1 %%i
for %%f in (*.ERR) do type %%f
for %%i in (*.c) do mcc18 -ls --no-extended -ml -p=%1 %%i
for %%i in (*.o) do mplib /q /r ..\..\..\lib\p%1.lib %%i

for %%f in (*.o) do del %%f
for %%f in (*.err) do del %%f
for %%f in (*.lst) do del %%f
cd ..
cd ..
